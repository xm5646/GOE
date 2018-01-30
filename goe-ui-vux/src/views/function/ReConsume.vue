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
    <div v-if="!isEnableAccess">
      <message title="未达到重销条件" description="请确认是否到达考核级别,或是否达到考核日期"></message>
    </div>
    <div v-else-if="isEnableBuy">
      <message title="报单币余额不足" description="请使用奖金转换报单币,或进行报单币充值"></message>
    </div>
    <div v-else>
        <form-preview  :body-items="list2" :footer-buttons="buttons2"
                      name="demo"></form-preview>
    </div>

    <pay-password :showPayPasswordInput="showPayPasswordStatus" @paySubmitEvent="submitPay" @cancelPayEvent="cancelPay"></pay-password>
  </div>
</template>

<script>
  import { XHeader, XInput, Group, XButton, Card, FormPreview } from 'vux'
  import Message from '../../components/msg'
  import PayPassword from '../../components/PayPassword'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
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
        showPayPasswordStatus: false,
        isEnableAccess: true,
        price: GoeConfig.goe.price,
        reConsumePrice: GoeConfig.goe.reConsumePrice,
        buttons2: [{
          style: 'primary',
          text: '重复消费',
          onButtonClick: (name) => {
            this.showPayPasswordStatus = true
          }
        }]
      }
    },
    methods: {
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
            expressId: 2
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
      isEnableBuy: function () {
        return Number(this.consumeCoin) < Number(this.reConsumePrice)
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
