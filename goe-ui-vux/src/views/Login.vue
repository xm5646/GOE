<template>
  <div>
    <x-header :left-options="{showBack: false}" style="background-color: #303135">会员管理系统</x-header>
    <div id="img-div">
      <img src="../assets/images/logo1.png" width="100%">
      <br>
      <group title="">
        <x-input title="用户名" name="username" placeholder="请输入用户编号" :min="5" :max="20" v-model="account">
          <img slot="label" style="padding-right:10px;display:block;" src="../assets/images/form/i-form-name.png"
               width="24" height="24">
        </x-input>
        <x-input title="密码" type="password" placeholder="请输入密码" v-model="password" :min="6" :max="12" @on-enter="login">
          <img slot="label" style="padding-right:10px;display:block;" src="../assets/images/form/i-form-password.png"
               width="24" height="24">
        </x-input>
      </group>
      <br>
      <x-button type="primary" action-type="submit" @click.native="login">登陆</x-button>

    </div>
    <loading></loading>
  </div>

</template>
<script>
  import { XHeader, XInput, Group, XButton, Loading } from 'vux'
  import GoeConfig from '../../config/goe'

  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Loading
    },
    data () {
      return {
        msg: 'Login',
        password: '',
        account: '',
        show: false,
        show1: false
      }
    },
    methods: {
      login () {
        if (this.account.length > 0 && this.password.length > 5) {
          const url = GoeConfig.apiServer + '/user/login'
          this.$http.post(url,
            {
              account: this.account,
              password: this.password
            },
            {
              _timeout: 3000,
              onTimeout: (request) => {
                alert('登陆超时')
                this.password = ''
              }
            })
            .then(response => {
              if (response.body.success) {
                const userObj = response.body.data
                window.localStorage.setItem('User', JSON.stringify(userObj))
                if (userObj.passwordReset) {
                  this.$router.push({name: 'index'})
                } else {
                  this.$router.push({name: 'initPassword'})
                }
              } else {
                alert(response.body.message)
                this.password = ''
              }
            }, responseErr => {
              alert('未知错误')
              this.password = ''
            })
        } else {
          alert('信息不完整')
        }
      }
    },
    onCancel () {
      console.log('on cancel')
    },
    onConfirm (msg) {
      console.log('on confirm')
      if (msg) {
        alert(msg)
      }
    }
  }
</script>

<style lang="less">
  #img-div {
    text-align: center
  }
</style>
