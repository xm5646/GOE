<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
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
      <x-button type="primary" action-type="button" @click.native="submit"> 提交</x-button>
    </group>
  </div>
</template>

<script>
  import {XHeader, XInput, Group, XButton} from 'vux'
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
        console.log('submit')
      }
    },
    computed: {
    }
  }
</script>

<style lang="less" scoped>
</style>
