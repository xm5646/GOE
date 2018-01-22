<template>
  <div >
    <x-header :left-options="{showBack: false}">会员管理系统</x-header>
    <div id="img-div">
      <br>
      <img src="../assets/images/goe_logo.png" width="150px" height="150px">
      <group title="">
        <x-input title="用户名" name="username" placeholder="请输入用户编号" is-type="china-name" v-model="account"></x-input>
        <x-input title="密码" type="password" placeholder="请输入密码" v-model="password" :min="6" :max="6" @on-change="change"></x-input>
      </group>
      <br>
      <x-button type="primary" action-type="button" @click.native="login">登陆</x-button>

    </div>
  </div>

</template>
<script>
  import { XHeader, XInput, Group, XButton } from 'vux'
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
        account: ''
      }
    },
    methods: {
      login () {
        this.$router.push({name: 'index'})
      }
    }
  }
</script>

<style lang="less">
  #img-div {
    text-align: center
  }
</style>
