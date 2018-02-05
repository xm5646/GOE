<template>
  <div class="page">
    <x-header :left-options="{showBack: true}">会员管理系统</x-header>
    <group title="修改支付密码">
      <x-input required placeholder="请输入登录密码" type="password" :min="6" :max="12" v-model="oldPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-input required placeholder="请输入手机号码" type="tel" is-type="china-mobile" v-model="phoneNumber">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-tel.png" width="24" height="24">
      </x-input>
      <x-input required placeholder="请输入原交易密码" type="password" :min="6" :max="6" v-model="oldPayPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-input required placeholder="请输入新交易密码" type="password" :min="6" :max="6" v-model="newPayPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-input required placeholder="请再次输入新交易密码" type="password" :min="6" :max="6" v-model="confirmPayPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-button type="warn" action-type="button" @click.native="submit"> 提交</x-button>
    </group>
  </div>
</template>

<script>
  import {XHeader, XInput, Group, XButton} from 'vux'
  import GoeConfig from '../../../config/goe'
  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton
    },
    data () {
      return {
        oldPassword: '',
        phoneNumber: '',
        oldPayPassword: '',
        newPayPassword: '',
        confirmPayPassword: ''
      }
    },
    methods: {
      submit () {
        if (this.oldPassword === '' || this.phoneNumber === '' || this.oldPayPassword === '' || this.newPayPassword === '' || this.confirmPayPassword === '') {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '请完整输入相关信息'
          })
        } else if (this.phoneNumber.length !== 11) {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '手机号码格式不正确'
          })
        } else if (this.newPayPassword !== this.confirmPayPassword) {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '两次输入的新交易密码不一致'
          })
        } else if (this.oldPassword.length !== 6 || this.newPayPassword.length !== 6 || this.confirmPayPassword.length !== 6) {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '交易密码为6位数字'
          })
        } else {
          this.doReset()
        }
      },
      doReset () {
        const url = GoeConfig.apiServer + '/user/updatePaymentPassword'
        this.$http.post(url,
          {
            account: JSON.parse(window.localStorage.getItem('User')).account,
            loginPassword: this.oldPassword,
            oldPaymentpassword: this.oldPayPassword,
            newPaymentPassword: this.newPayPassword,
            phone: this.phoneNumber
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.oldPassword = ''
              this.newPassword = ''
              this.confirmPassword = ''
              this.$vux.toast.show({
                text: '修改成功'
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
      }
    },
    computed: {
    }
  }
</script>

<style lang="less" scoped>
</style>
