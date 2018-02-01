<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '重复消费'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{bonusCoin}}</span>
          <br/>
          奖金
        </div>
        <div class="vux-1px-r">
          ￥<span>{{consumeCoin}}</span>
          <br/>
          报单币
        </div>
      </div>
    </card>
    <selector ref="defaultValueRef" title="选择收货地址" :options="addressesInfo" v-model="selectExpressId"></selector>
    <div v-if="selectedAddress">
      <div class="weui-cells">
        <div class="weui-cell">
          <div class="weui-cell__bd">
            <p>{{address.receivedName}}&nbsp;&nbsp;{{address.tel}}</p>
          </div>
        </div>
        <p class="weui-cells__title" style="">{{address.addressShowName}}&nbsp;&nbsp;{{address.detail}}</p>
      </div>
    </div>
    <div v-if="!isEnableAccess">
      <message title="未达到重销条件" description="请确认是否到达考核级别,或是否达到考核日期"></message>
    </div>
    <div v-else-if="isEnableBuy">
      <message title="报单币余额不足" description="请使用奖金转换报单币,或进行报单币充值"></message>
    </div>
    <div v-else>
      <form-preview :body-items="list2" :footer-buttons="buttons2"
                    name="demo"></form-preview>
    </div>

    <pay-password :showPayPasswordInput="showPayPasswordStatus" @paySubmitEvent="submitPay"
                  @cancelPayEvent="cancelPay"></pay-password>
  </div>
</template>

<script>
  import { XHeader, XInput, Group, XButton, Card, FormPreview, Selector, Value2nameFilter as value2name,
    ChinaAddressV4Data } from 'vux'
  import Message from '../../components/msg'
  import PayPassword from '../../components/PayPassword'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
      this.getAddressesByAccount()
      const userObj = JSON.parse(window.localStorage.getItem('User'))
      this.bonusCoin = userObj.bonusCoin
      this.consumeCoin = userObj.consumeCoin
      const date = new Date()
      var seperator1 = '-'
      var year = date.getFullYear()
      var month = date.getMonth() + 1
      var strDate = date.getDate()
      if (month >= 1 && month <= 9) {
        month = '0' + month
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = '0' + strDate
      }
      const currentdate = year + seperator1 + month + seperator1 + strDate
      if (userObj.assessStatus === '已通过考核') {
        if (currentdate === userObj.assessDate) {
          this.isEnableAccess = true
        } else {
          this.isEnableAccess = false
        }
      }
    },
    components: {
      Selector,
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview,
      Message,
      PayPassword
    },
    data () {
      return {
        bonusCoin: 0,
        consumeCoin: 0,
        selectExpressId: '',
        showPayPasswordStatus: false,
        isEnableAccess: true,
        selectedAddress: false,
        address: {},
        addresses: [],
        price: GoeConfig.goe.price,
        reConsumePrice: GoeConfig.goe.reConsumePrice,
        buttons2: [{
          style: 'primary',
          text: '重复消费',
          onButtonClick: () => {
            this.confirmReConsume()
          }
        }]
      }
    },
    methods: {
      confirmReConsume () {
        if (this.selectExpressId === '') {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '请选择一个收货地址'
          })
        } else {
          const _this = this
          this.$vux.confirm.show({
            // 组件除show外的属性
            title: '确定进行重复消费?',
            onCancel () {
              console.log(this) // 非当前 vm
              console.log(_this) // 当前 vm
            },
            onConfirm () {
              console.log('show payPassword')
              _this.showPayPasswordStatus = true
            }
          })
        }
      },
      submitPay (payPassword) {
        this.showPayPasswordStatus = false
        console.log('收到了支付请求,交易密码:' + payPassword)
        this.doReConsume(payPassword)
      },
      cancelPay () {
        console.log('close')
        this.showPayPasswordStatus = false
      },
      doReConsume (payPassword) {
        const url = GoeConfig.apiServer + '/reconsumeRecord/purchaseReconsume'
        this.$http.post(url,
          {
            account: JSON.parse(window.localStorage.getItem('User')).account,
            paymentPassword: payPassword,
            expressId: this.selectExpressId
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.$vux.toast.show({
                text: '重销成功'
              })
              window.history.go(-1)
            } else {
              this.$vux.toast.show({
                type: 'cancel',
                text: (response.body.message || '系统异常')
              })
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      },
      getAddressesByAccount () {
        const url = GoeConfig.apiServer + '/expressAddress/findExpressAddressesByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
        this.$http.get(url, {
          _timeout: 5000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.totalElements > 0) {
              const addressesInfo = response.body.content
              this.addresses.splice(0, this.addresses.length)
              for (let i = 0; i < addressesInfo.length; i++) {
                var address = {}
                address.id = addressesInfo[i].expressId
                address.receivedName = addressesInfo[i].receiverName
                address.tel = addressesInfo[i].phone
                var addressIdArras = new Array([])
                addressIdArras[0] = addressesInfo[i].province
                addressIdArras[1] = addressesInfo[i].city
                addressIdArras[2] = addressesInfo[i].district
                address.addressArray = addressIdArras
                address.addressShowName = value2name(addressIdArras, ChinaAddressV4Data)
                address.detail = addressesInfo[i].detailAddress
                this.addresses[i] = address
              }
            } else {
              this.addresses.splice(0, this.addresses.length)
              console.log('no cards')
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      },
      updateUserInfo () {
        const url = GoeConfig.apiServer + '/user/findByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.success) {
              const result = response.body.data
              this.bonusCoin = result.bonusCoin
              this.consumeCoin = result.consumeCoin
              window.localStorage.setItem('User', JSON.stringify(result))
            } else {
              this.$vux.toast.show({
                type: 'cancel',
                text: (response.body.message || '系统异常')
              })
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      }
    },
    computed: {
      list2: function () {
        const list = [{
          label: '商品原价',
          value: '报单币￥' + this.price
        }, {
          label: '重销优惠价格',
          value: '报单币￥' + this.reConsumePrice
        }]
        return list
      },
      addressesInfo: function () {
        console.log('computed addressInfo' + this.addresses.length)
        if (this.addresses && this.addresses.constructor === Array && this.addresses.length !== 0) {
          var infos = new Array([])
          const tmpcards = this.addresses
          for (let i = 0; i < tmpcards.length; i++) {
            if (tmpcards[i].detail.length > 10) {
              infos[i] = {
                key: tmpcards[i].id,
                value: tmpcards[i].detail.substr(0, 15) + '...'
              }
            } else {
              infos[i] = {
                key: tmpcards[i].id,
                value: tmpcards[i].detail
              }
            }
          }
          console.log(infos)
          return infos
        } else {
          return []
        }
      },
      isEnableBuy: function () {
        return (Number(this.consumeCoin) < Number(this.reConsumePrice))
      }
    },
    watch: {
      selectExpressId (newValue, oldValue) {
        this.selectedAddress = true
        for (let i = 0; i < this.addresses.length; i++) {
          if (this.selectExpressId === this.addresses[i].id) {
            this.address = this.addresses[i]
          }
        }
      }
    }
  }
</script>
<style lang="less" scoped>
  @import '../../../node_modules/vux/src/styles/1px.less';

  .card-demo-flex {
    display: flex;
  }

  .card-demo-content01 {
    padding: 10px 0;
  }

  .card-padding {
    padding: 15px;
  }

  .card-demo-flex > div {
    flex: 1;
    text-align: center;
    font-size: 12px;
  }

  .card-demo-flex span {
    color: #f74c31;
  }
</style>
