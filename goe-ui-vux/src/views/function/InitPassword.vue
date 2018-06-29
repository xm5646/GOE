<template>
  <div class="my-page">

    <x-header :left-options="{showBack: false}">会员管理系统</x-header>
    <div style="text-align:left; margin-left: 10px;">
      <h3>设置安全信息</h3>
      <p style="font-size: 8px; color: grey;margin-top: 2px;padding-bottom: 4px;">为了您的账户安全，请重新设置您的登录密码。<br>设置交易密码,账户资金发生变动时，使用交易密码进行身份确认
      </p>
    </div>
    <hr/>

    <group title="员工姓名绑定">
      <x-input placeholder="请输入真实姓名" is-type="china-name" v-model="chinaName">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-name.png"
             width="24" height="24">
      </x-input>
    </group>
    <group title="设置手机号码">
      <x-input title="手机号码" placeholder="请输入手机号码" is-type="china-mobile" v-model="phoneNumber" required>
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-tel.png"
             width="24" height="24">
      </x-input>
      <x-input placeholder="短信验证码" class="weui-vcode" v-model="smsCode"  :min="4" :max="4" type="number">
        <x-button slot="right" type="primary" mini :disabled="getCodeBthIsDisabled"  @click.native="getMesCode">{{getCodeBthText}}</x-button>
      </x-input>
    </group>
    <group title="添加银行卡">
      <selector v-model="bankName" placeholder="请选择银行" :options="bankList" @on-change="changeBank"></selector>
      <x-input title="卡号" type="number" v-model="cardNumber" :min="16" :max="19">
      </x-input>
    </group>
    <group title="重新设置登录密码">
      <x-input placeholder="请输入新登录密码" type="password" :min="6" :max="12" v-model="firstPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png"
             width="24" height="24">
      </x-input>
      <x-input placeholder="请再次输入新登录密码" type="password" :min="6" :max="12" v-model="secondPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png"
             width="24" height="24">
      </x-input>
    </group>
    <group title="设置6位数字交易密码">
      <x-input title="交易密码" type="password" placeholder="请输入交易密码" v-model="firstPayPassword" :min="6" :max="6">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png"
             width="24" height="24">
      </x-input>

      <x-input title="交易密码" type="password" placeholder="请再次输入交易密码" v-model="secondPayPassword" :min="6" :max="6">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png"
             width="24" height="24">
      </x-input>
    </group>
    <br>
    <x-button type="warn" action-type="button" @click.native="submit"> 提交</x-button>
  </div>
</template>
<script>
  import { XHeader, XInput, Group, XButton, Selector } from 'vux'
  import GoeConfig from '../../../config/goe'

  export default {
    data () {
      return {
        firstPassword: '',
        secondPassword: '',
        chinaName: '',
        firstPayPassword: '',
        getCodeBthIsDisabled: false,
        getCodeBthText: '获取短信验证码',
        secondPayPassword: '',
        smsCode: '',
        phoneNumber: '',
        timer: null,
        counter: 119,
        bankList: ['农业银行', '建设银行', '工商银行', '中国银行', '中国邮政储蓄银行'],
        bankName: '',
        cardNumber: ''
      }
    },
    components: {
      XHeader,
      XInput,
      Group,
      Selector,
      XButton
    },
    methods: {
      getMesCode () {
        if (this.phoneNumber.length !== 11) {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '请先输入手机号'
          })
        } else {
          this.getCodeBthIsDisabled = true
          this.getCodeBthText = '120秒后再试'
          this.sendRequestforGetSmsCode()
          this.timer = setInterval(this.changeBtnText, 1000)
        }
      },
      changeBtnText () {
        this.getCodeBthText = '重新获取(' + this.counter + '秒)'
        this.counter --
        if (this.counter < 0) {
          this.getCodeBthIsDisabled = false
          this.getCodeBthText = '获取短信验证码'
          window.clearInterval(this.timer)
          this.counter = 119
        }
      },
      sendRequestforGetSmsCode () {
        let account = JSON.parse(window.localStorage.getItem('User')).account
        let phoneNumber = this.phoneNumber
        const url = GoeConfig.apiServer + '/sms/getCodeByAccount?account=' + account + '&phoneNumber=' + phoneNumber
        this.$http.get(url, {
          _timeout: 10000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.success) {
              this.$vux.toast.show({
                type: 'text',
                text: response.body.message
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
      changeBank (bank) {
        this.bankName = bank
      },
      submit () {
        console.log('firstPassword' + this.firstPassword.length)
        console.log('secondPassword' + this.secondPassword.length)
        console.log('chinaName' + this.chinaName.length)
        console.log('firstPayPassword' + this.firstPayPassword.length)
        console.log('secondPayPassword' + this.secondPayPassword.length)
        console.log('phoneNumber' + this.phoneNumber.length)
        if (this.smsCode === '' || this.firstPassword === '' || this.secondPassword === '' || this.chinaName === '' || this.firstPayPassword === '' || this.secondPayPassword === '' || this.phoneNumber === '') {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '输入信息不完整'
          })
        } else if (this.firstPassword.length < 6 || this.secondPassword.length < 6 || this.firstPayPassword.length !== 6 || this.secondPayPassword.length !== 6 || this.phoneNumber.length !== 11) {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '输入长度不匹配'
          })
        } else if (this.firstPassword !== this.secondPassword) {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '两次输入的登陆密码不一致'
          })
        } else if (this.smsCode.length !== 4) {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '短信验证码格式不正确'
          })
        } else if (this.firstPayPassword !== this.secondPayPassword) {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '两次输入的交易密码不一致'
          })
        } else if (this.bankName === '' || this.cardNumber === '') {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '银行卡信息不完整'
          })
        } else {
          const url = GoeConfig.apiServer + '/user/initUserInfo'
          const currentAccount = JSON.parse(window.localStorage.getItem('User')).account
          this.$http.post(url,
            {
              account: currentAccount,
              newPassword: this.secondPassword,
              paymentPassword: this.secondPayPassword,
              nickName: this.chinaName,
              userPhone: this.phoneNumber,
              bankName: this.bankName,
              cardNumber: this.cardNumber,
              smsCode: this.smsCode
            },
            {
              _timeout: 3000,
              onTimeout: (request) => {
              }
            })
            .then(response => {
              if (response.body.success) {
                const userObj = response.body.data
                this.$vux.toast.show({
                  text: '设置成功'
                })
                window.localStorage.setItem('User', JSON.stringify(userObj))
                this.$router.push({name: 'index'})
              } else {
                this.$vux.toast.show({
                  type: 'text',
                  text: response.body.message
                })
              }
            }, responseErr => {
              this.$vux.toast.show({
                type: 'cancel',
                text: '系统异常'
              })
            })
        }
      }
    }
  }
</script>
