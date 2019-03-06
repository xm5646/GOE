<template>
  <div class="page">
    <x-header :left-options="{showBack: true}">会员管理系统</x-header>
    <card :header="{title: '缴纳会员年费'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{consumeCoin}}</span>
          <br/>
          报单币余额
        </div>
      </div>
    </card>
    <div v-if="consumeCoin < 100">
      <message title="报单币不足" description="保单币100即可续费一年会员年费"></message>
    </div>
    <div v-else>
      <br>
        <div>
          <form-preview header-label="续费信息" :body-items="orderInfo" :footer-buttons="buttons2" name="demo"></form-preview>
        </div>
    </div>

    <pay-password :showPayPasswordInput="showPayPasswordStatus" @paySubmitEvent="submitPay"
                  @cancelPayEvent="cancelPay"></pay-password>
  </div>
</template>

<script>
  import { XHeader, XInput, Group, XButton, Card, FormPreview, Selector, XNumber } from 'vux'
  import Message from '../../components/msg'
  import PayPassword from '../../components/PayPassword'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
      const userObj = JSON.parse(window.localStorage.getItem('User'))
      this.bonusCoin = userObj.bonusCoin
      this.consumeCoin = userObj.consumeCoin
      this.repeatCoin = userObj.repeatCoin
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
      PayPassword,
      XNumber
    },
    data () {
      return {
        bonusCoin: 0,
        consumeCoin: 0,
        repeatCoin: 0,
        showPayPasswordStatus: false,
        isEnableAccess: true,
        orderInfo: [{
          label: '支付方式',
          value: '报单币'
        }, {
          label: '购买年限',
          value: '1'
        }, {
          label: '支付金额',
          value: '￥' + GoeConfig.goe.annualFee
        }],
        price: GoeConfig.goe.price,
        buttons2: [{
          style: 'primary',
          text: '立即支付',
          onButtonClick: () => {
            this.confirmReConsume()
          }
        }]
      }
    },
    methods: {
      getNotices () {
        const url = GoeConfig.apiServer + '/notice/getShownNotices'
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.success) {
              this.notices = response.body.data
              console.log('获取到的数据长度:' + this.notices.length)
              if (this.notices.length > 0) {
                this.noticeShowStatus = true
              }
              console.log('获取公告信息:' + this.noticeShowStatus)
              this.$router.push({name: 'index', params: {noticePanelShowStatus: this.noticeShowStatus, notices: this.notices}})
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
      confirmReConsume () {
        if (GoeConfig.goe.annualFee > this.consumeCoin) {
          console.log('报单币不足')
        } else {
          const _this = this
          this.$vux.confirm.show({
            // 组件除show外的属性
            title: '确定支付会员年费?',
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
        const url = GoeConfig.apiServer + '/user/payAnnualFee'
        this.$http.post(url,
          {
            account: JSON.parse(window.localStorage.getItem('User')).account,
            paymentPassword: payPassword
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.$vux.toast.show({
                text: '支付成功'
              })
              this.getNotices()
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
