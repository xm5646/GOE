<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '奖金转报单币'}">
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
    <x-input title="￥" type="number" placeholder="请输入转换金额" v-model="convertNumber" :min="1" :max="12"></x-input>
    <div v-if="isInput">
      <form-preview header-label="转换金额" :header-value="showConvertNumber" :body-items="list" :footer-buttons="buttons2"
                    name="demo"></form-preview>
    </div>
    <div v-transfer-dom>
      <confirm v-model="showConfirm"
               title="提示"
               @on-cancel="onCancel"
               @on-confirm="onConfirm">
        <p style="text-align:center;">奖金转换成报单币后不可转回成奖金</p>
      </confirm>
    </div>
    <pay-password :showPayPasswordInput="showPayPasswordStatus" @paySubmitEvent="submitPay" @cancelPayEvent="cancelPay"></pay-password>
  </div>
</template>
<script>
  import { XHeader, XInput, Group, XButton, Card, FormPreview, Confirm, TransferDomDirective as TransferDom } from 'vux'
  import GoeConfig from '../../../config/goe'
  import PayPassword from '../../components/PayPassword'
  export default {
    directives: {
      TransferDom
    },
    mounted: function () {
      const userObj = JSON.parse(window.localStorage.getItem('User'))
      this.user = userObj
      this.bonusCoin = userObj.bonusCoin
      this.consumeCoin = userObj.consumeCoin
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview,
      Confirm,
      PayPassword
    },
    data () {
      return {
        user: '',
        bonusCoin: 20000,
        consumeCoin: 1000,
        convertNumber: '',
        showConvertNumber: '',
        showPayPasswordStatus: false,
        payPassword: '',
        showConfirm: false,
        list: [{
          label: '转换后奖金余额',
          value: '2000'
        }, {
          label: '转换后报单币余额',
          value: '3000'
        }],
        buttons2: [{
          style: 'primary',
          text: '转换',
          onButtonClick: (name) => {
            this.showConfirm = true
          }
        }]
      }
    },
    methods: {
      onCancel () {
        console.log('on cancel')
      },
      onConfirm () {
        this.showPayPasswordStatus = true
      },
      submitPay (payPassword) {
        this.showPayPasswordStatus = false
        this.payPassword = payPassword
        this.doConvert()
      },
      cancelPay () {
        console.log('close')
        this.showPayPasswordStatus = false
      },
      doConvert () {
        const url = GoeConfig.apiServer + '/consumerRecord/save'
        this.$http.post(url,
          {
            sendUserAccount: this.user.account,
            receiveUserAccount: this.user.account,
            consumeTypeCode: 2,
            consumeNumber: this.convertNumber,
            paymentPassword: this.payPassword,
            description: '用户:' + this.user.account + '使用奖金转换报单币' + this.showConvertNumber
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.convertNumber = ''
              this.updateUser()
              this.$vux.toast.show({
                text: '转换成功'
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
      updateUser () {
        const url = GoeConfig.apiServer + '/user/findByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
        this.$http.get(url,
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.user = response.body.data
              this.bonusCoin = this.user.bonusCoin
              this.consumeCoin = this.user.consumeCoin
              window.localStorage.setItem('User', JSON.stringify(response.body.data))
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
      isInput: function () {
        return (Number(this.convertNumber) > 0 && this.convertNumber <= this.bonusCoin)
      }
    },
    watch: {
      convertNumber (newValue, oldValue) {
        this.showConvertNumber = '￥' + newValue
        this.list[0].value = '￥' + (this.bonusCoin - newValue)
        this.list[1].value = '￥' + (Number(this.consumeCoin) + Number(newValue))
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
