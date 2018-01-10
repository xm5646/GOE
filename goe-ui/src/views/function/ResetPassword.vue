<template>
  <div class="page">
    <simple-header style="background-color: orange" title="GOE会员管理系统" :back-link="true"></simple-header>
    <page-content>
      <div class='content-padded'>
        <h1 class="demos-title">密码重置</h1>

        <form-list>
          <form-item>
            <div class="item-media">
              <img src="../../assets/images/form/i-form-password.png" alt="" width="30"/>
            </div>
            <div class="item-content">
              <div class="item-input">
                <input type="password" placeholder="请输入原密码" class="" v-model="password">
              </div>
            </div>
          </form-item>

          <form-item>
            <div class="item-media">
              <img src="../../assets/images/form/i-form-password.png" alt="" width="30"/>
            </div>
            <div class="item-content">
              <div class="item-input">
                <input type="password" placeholder="请输入新密码" class="" v-model="password">
              </div>
            </div>
          </form-item>

          <form-item>
            <div class="item-media">
              <img src="../../assets/images/form/i-form-password.png" alt="" width="30"/>
            </div>
            <div class="item-content">
              <div class="item-input">
                <input type="password" placeholder="请再次输入新密码" class="" v-model="password">
              </div>
            </div>
          </form-item>
          <br>
          <m-button type="warning" size="large" @click.native="doLogin">更改</m-button>
          <toast text="完成!" ref="t1"></toast>
          <toast text="失败!" type="error" ref="t2"></toast>
        </form-list>
      </div>
    </page-content>
  </div>
</template>

<script>
  import { Button, ButtonGroup } from '../../../node_modules/vum/src/components/buttons'
  import { SimpleHeader } from '../../../node_modules/vum/src/components/header'
  import Content from '../../../node_modules/vum/src/components/content'
  import { Form, FormItem } from '../../../node_modules/vum/src/components/form'
  import Toast from '../../../node_modules/vum/src/components/toast'
  export default {
    components: {
      SimpleHeader,
      'page-content': Content,
      ButtonGroup,
      FormItem,
      Toast,
      'form-list': Form,
      'm-button': Button
    },
    data () {
      return {
        password: '',
        newPassword: ''
      }
    },
    methods: {
      doLogin () {
        this.$refs.t1.open()
      }
    }
  }
</script>

<style lang="less" scoped>
  p {
    text-align: center;
  }
</style>
