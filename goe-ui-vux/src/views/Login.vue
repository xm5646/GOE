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
        console.log(this.password)
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
