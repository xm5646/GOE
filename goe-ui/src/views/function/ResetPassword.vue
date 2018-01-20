<template>
  <div class="page">
    <simple-header style="background-color: orange" title="会员管理系统" :back-link="true"></simple-header>
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
                <input type="password" placeholder="请输入原密码" class="" v-model="oldPassword" @focus="userInput">
              </div>
            </div>
          </form-item>

          <form-item>
            <div class="item-media">
              <img src="../../assets/images/form/i-form-password.png" alt="" width="30"/>
            </div>
            <div class="item-content">
              <div class="item-input">
                <input type="password" placeholder="请输入新密码" class="" v-model="firstPassword" @focus="userInput">
              </div>
            </div>
          </form-item>

          <form-item>
            <div class="item-media">
              <img src="../../assets/images/form/i-form-password.png" alt="" width="30"/>
            </div>
            <div class="item-content">
              <div class="item-input">
                <input type="password" placeholder="请再次输入新密码" class="" v-model="newPassword" @focus="userInput">
              </div>
            </div>
          </form-item>
          <div v-if="isErr">
            <m-button type="warning" :disabled="true">{{ errMsg }}</m-button>
          </div>
          <br>
          <m-button type="warning" size="large" @click.native="changePassword" :disabled="isDisenableBtn">更改</m-button>
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
  import GoeConfig from '../../../config/goe'
  export default {
    mounted: function () {
      this.currentUserAccount = JSON.parse(window.localStorage.getItem('User')).account
    },
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
        oldPassword: '',
        firstPassword: '',
        newPassword: '',
        currentUserAccount: '',
        isErr: false,
        errMsg: ''
      }
    },
    methods: {
      changePassword () {
        if (this.isDisenableBtn) {
          console.log('not do submit')
        } else {
          if (this.firstPassword !== this.newPassword) {
            this.isErr = true
            this.errMsg = '新密码输入不一致'
          } else {
            this.updateUserPassword()
          }
        }
      },
      userInput () {
        this.isErr = false
      },
      updateUserPassword () {
        const url = GoeConfig.apiServer + '/user/updatePassword'
        this.$http.post(url,
          {
            account: this.currentUserAccount,
            oldPassword: this.oldPassword,
            newPassword: this.newPassword
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
              this.isErr = true
              this.errMsg = '请求超时'
              this.$refs.t2.open()
              this.firstPassword = ''
              this.newPassword = ''
              this.oldPassword = ''
            }
          })
          .then(response => {
            if (response.body.success) {
              this.isErr = false
              this.$refs.t1.open()
              this.firstPassword = ''
              this.newPassword = ''
              this.oldPassword = ''
            } else {
              this.isErr = true
              this.$refs.t2.open()
              this.errMsg = (response.body.message || '未知错误')
              this.firstPassword = ''
              this.newPassword = ''
              this.oldPassword = ''
            }
          }, responseErr => {
            this.isErr = true
            this.$refs.t2.open()
            this.errMsg = '未知错误'
            this.firstPassword = ''
            this.newPassword = ''
            this.oldPassword = ''
          })
      }
    },
    computed: {
      isDisenableBtn: function () {
        return (this.oldPassword === '' || this.firstPassword === '' || this.newPassword === '')
      }
    }
  }
</script>

<style lang="less" scoped>
  p {
    text-align: center;
  }
</style>
