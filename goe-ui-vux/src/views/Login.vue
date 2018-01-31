<template>
  <div>
    <x-header :left-options="{showBack: false}" style="background-color: #303135">会员管理系统</x-header>
    <div id="img-div">
      <img src="../assets/images/touming.png" width="100%">
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
  </div>

</template>
<script>
  import { XHeader, XInput, Group, XButton } from 'vux'
  import GoeConfig from '../../config/goe'

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
                this.password = ''
              }
            })
            .then(response => {
              if (response.body.success) {
                window.setTimeout(() => {
                  this.$router.push({name: 'login'})
                  window.localStorage.clear()
                }, 30 * 60 * 1000)
                const userObj = response.body.data
                window.localStorage.setItem('User', JSON.stringify(userObj))
                if (userObj.passwordReset) {
                  this.$vux.toast.show({
                    text: '登陆成功'
                  })
                  this.$router.push({name: 'index'})
                } else {
                  this.$router.push({name: 'initPassword'})
                }
              } else {
                this.$vux.toast.show({
                  type: 'cancel',
                  text: response.body.message
                })
                this.password = ''
              }
            }, responseErr => {
              this.$vux.toast.show({
                type: 'cancel',
                text: '系统异常'
              })
              this.password = ''
            })
        } else {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '请完整输入登陆信息'
          })
        }
      }
    }
  }
</script>

<style lang="less">
  #img-div {
    text-align: center
  }
</style>
