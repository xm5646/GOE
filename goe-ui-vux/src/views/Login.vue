<template>
  <div >
    <x-header :left-options="{showBack: false}" style="background-color: #303135">会员管理系统</x-header>
    <div id="img-div">
      <br>
      <img src="../assets/images/logo1.png" width="100%">
      <group title="">
        <x-input title="用户名" name="username" placeholder="请输入用户编号" is-type="china-name" v-model="account">
          <img slot="label" style="padding-right:10px;display:block;" src="../assets/images/form/i-form-name.png" width="24" height="24">
        </x-input>
        <x-input title="密码" type="password" placeholder="请输入密码" v-model="password" :min="6" :max="12"  @on-enter="login">
          <img slot="label" style="padding-right:10px;display:block;" src="../assets/images/form/i-form-password.png" width="24" height="24">
        </x-input>
      </group>
      <br>
      <x-button type="primary" action-type="submit" @click.native="login">登陆</x-button>

    </div>
    <loading></loading>
    <div v-transfer-dom>
      <confirm v-model="show1"
               title="Confirm deleting the item"
               @on-cancel="onCancel"
               @on-confirm="onConfirm">
        <p style="text-align:center;">确定吗?</p>
      </confirm>
    </div>
  </div>

</template>
<script>
  import { XHeader, XInput, Group, XButton, Loading, Confirm, TransferDomDirective as TransferDom } from 'vux'
  export default {
    directives: {
      TransferDom
    },
    mounted: function () {
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Loading,
      Confirm
    },
    data () {
      return {
        msg: 'Login',
        password: '',
        account: '',
        show: false,
        show1: false,
        request: {
          url: 'saveuser',
          params: {
            'account': 'admin',
            'password': 'admin'
          }
        }
      }
    },
    methods: {
      login () {
        if (this.account.length > 0 && this.password.length >= 6) {
          this.request = {
            url: '/user/login',
            params: {
              account: this.account,
              password: this.password
            }
          }
          this.api('login')
//          if (result !== '') {
//            this.$router.push({name: 'index'})
//          }
//        this.show = true
//        n
//        console.log(this.password)
//        this.$router.push({name: 'index'})
        } else {
          console.log('信息不完整')
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
  }
</script>

<style lang="less">
  #img-div {
    text-align: center
  }
</style>
