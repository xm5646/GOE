<template>
  <div class="my-page">

    <x-header :left-options="{showBack: false}" style="background-color: #303135">会员管理系统</x-header>
    <div style="text-align:left; margin-left: 10px;">
     <h3>设置安全信息</h3>
      <p style="font-size: 8px; color: grey;margin-top: 2px;padding-bottom: 4px;">为了您的账户安全，请重新设置您的登录密码。<br>设置交易密码,账户资金发生变动时，使用交易密码进行身份确认</p>
    </div>
    <hr/>
    <group title="设置登录密码">
      <x-input placeholder="请输入登录密码" :min="6" :max="12" v-model="firstPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-input placeholder="请再次输入登录密码" :min="6" :max="12" v-model="secondPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
    </group>
    <group title="设置6位数字交易密码">
      <x-input title="交易密码" type="password" placeholder="请输入交易密码" v-model="firstPayPassword" :min="6" :max="6">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>

      <x-input title="交易密码" type="password" placeholder="请再次输入交易密码" v-model="secondPayPassword" :min="6" :max="6">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
    </group>
    <group title="设置手机号码">
      <x-input title="手机号码" name="username" placeholder="请输入手机号码" is-type="china-mobile" v-model="phoneNumber" required>
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-tel.png" width="24" height="24">
      </x-input>
    </group>
    <br>
    <x-button type="primary" action-type="button" @click.native="submit"> 提交</x-button>
  </div>
</template>
<script>
  import {XHeader, XInput, Group, XButton} from 'vux'

  export default {
    data () {
      return {
        firstPassword: '',
        secondPassword: '',
        firstPayPassword: '',
        secondPayPassword: '',
        phoneNumber: ''
      }
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton
    },
    methods: {
      submit () {
        console.log('submit')
      }
    }
  }
</script>
