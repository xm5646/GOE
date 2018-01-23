<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="修改登录密码">
      <x-input placeholder="请输入原登录密码" type="password" :min="6" :max="12" v-model="oldPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-input placeholder="请输入新密码" :min="6" :max="12" v-model="newPassword">
        <img slot="label" style="padding-right:10px;display:block;" src="../../assets/images/form/i-form-password.png" width="24" height="24">
      </x-input>
      <x-input placeholder="请再次输入新密码" :min="6" :max="12" v-model="confirmPassword">
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
        newPassword: '',
        confirmPassword: ''
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
