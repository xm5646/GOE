<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="修改登录密码">
      <x-input placeholder="请输入原登录密码" type="password" :min="6" :max="12" v-model="oldPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-input placeholder="请输入新密码" type="password" :min="6" :max="12" v-model="newPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-input placeholder="请再次输入新密码" type="password" :min="6" :max="12" v-model="confirmPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-button type="primary" action-type="button" @click.native="submit"> 提交</x-button>
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
        newPassword: '',
        confirmPassword: ''
      }
    },
    methods: {
      submit () {
        if (this.oldPassword === '' || this.newPassword === '' | this.confirmPassword === '') {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '请完整输入相关信息'
          })
        } else if (this.oldPassword.length < 6 || this.newPassword.length < 6 || this.confirmPassword.length < 6) {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '密码长度为6到12位字符'
          })
        } else if (this.newPassword !== this.confirmPassword) {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '两次输入的新密码不一致'
          })
        } else {
          const url = GoeConfig.apiServer + '/user/updateLoginPassword'
          this.$http.post(url,
            {
              account: JSON.parse(window.localStorage.getItem('User')).account,
              oldPassword: this.oldPassword,
              newPassword: this.newPassword
            },
            {
              _timeout: 3000,
              onTimeout: (request) => {
              }
            })
            .then(response => {
              if (response.body.success) {
                this.$vux.toast.show({
                  text: '密码修改成功'
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
      }
    },
    computed: {
    }
  }
</script>

<style lang="less" scoped>
</style>
