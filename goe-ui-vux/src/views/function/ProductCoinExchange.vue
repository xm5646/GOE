<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '产品积分兑换'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{productCoin}}</span>
          <br/>
          产品积分
        </div>
      </div>
    </card>
    <div v-if="!isEnableBuy">
      <message title="无法兑换" description="积分余额不足"></message>
    </div>
    <div v-else>
      <group>
        <group>
          <x-number  title="兑换数量"  :min="1" :max="MaxNumber" v-model="exchangeNumber" @on-change="changeNumber"></x-number>
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
        </group>
        <div>
          <form-preview header-label="单价" :header-value="showConvertNumber" :body-items="orderInfo" :footer-buttons="buttons2" name="demo"></form-preview>
        </div>
      </group>
    </div>
    <pay-password :showPayPasswordInput="showPayPasswordStatus" @paySubmitEvent="submitPay"
                  @cancelPayEvent="cancelPay"></pay-password>
  </div>
</template>
<script>
  import {XHeader, XInput, Group, XButton, Card, FormPreview, XNumber, Value2nameFilter as value2name,
    ChinaAddressV4Data, Selector} from 'vux'
  import GoeConfig from '../../../config/goe'
  import Message from '../../components/msg'
  import PayPassword from '../../components/PayPassword'
  export default {
    mounted: function () {
      this.getAddressesByAccount()
      this.productCoin = JSON.parse(window.localStorage.getItem('User')).productCoin
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview,
      XNumber,
      Message,
      Selector,
      PayPassword
    },
    data () {
      return {
        productCoin: 0,
        selectExpressId: '',
        showPayPasswordStatus: false,
        exchangeNumber: 1,
        addresses: [],
        selectedAddress: false,
        address: {},
        orderInfo: [{
          label: '兑换数量',
          value: '1'
        }, {
          label: '总计',
          value: '￥680'
        }],
        buttons2: [{
          style: 'primary',
          text: '兑换',
          onButtonClick: (name) => {
            this.confirmReConsume()
          }
        }]
      }
    },
    methods: {
      changeNumber (val) {
        this.orderInfo[1].value = '￥' + val * GoeConfig.goe.price
      },
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
            title: '是否进行兑换?',
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
        this.doExchange(payPassword)
      },
      cancelPay () {
        console.log('close')
        this.showPayPasswordStatus = false
      },
      doExchange (payPassword) {
        console.log('doexchange')
        const url = GoeConfig.apiServer + '/productCoin/transfer'
        this.$http.post(url,
          {
            account: JSON.parse(window.localStorage.getItem('User')).account,
            paymentPassword: payPassword,
            expressId: this.selectExpressId,
            productCoin: this.exchangeNumber * GoeConfig.goe.price
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.$vux.toast.show({
                text: '兑换成功'
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
      }
    },
    computed: {
      showConvertNumber: function () {
        return '积分￥' + GoeConfig.goe.price
      },
      isEnableBuy: function () {
        return this.productCoin > GoeConfig.goe.price
      },
      MaxNumber: function () {
        return parseInt(this.productCoin / GoeConfig.goe.price)
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
