<template>
  <div class="page">
    <simple-header style="background-color: orange" title="会员管理系统" :back-link="true"></simple-header>
    <page-content>
      <div class='content-padded'>
        <h1 class="demos-title">新增用户</h1>

        <form-list>
          <form-item>
            <div class="item-media">
              <img src="../../assets/images/form/i-form-name.png" alt="" width="30"/>
            </div>
            <div class="item-content">
              <div class="item-input">
                <input type="text" placeholder="请输入用户账户" class="" v-model="account">
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
                <input type="password" placeholder="请再次输入新密码" class="" v-model="SecondPassword">
              </div>
            </div>
          </form-item>
          <br>
          <m-button type="warning" size="large" @click.native="createUser">创建</m-button>
          <toast text="完成!" ref="t1"></toast>
          <toast text="失败!" type="error" ref="t2"></toast>
        </form-list>
      </div>
      上级用户: {{ parentAccount }}
      推荐用户: {{ recommendAccount }}
      存放位置: {{ departPlace }}
      创建用户编号:  {{ account }}
      新用户密码: {{ password }}
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
      this.parentAccount = this.$route.params.parentAccount
      this.departPlace = this.$route.params.departPlace
      this.recommendAccount = JSON.parse(window.localStorage.getItem('User')).account
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
        password: '',
        SecondPassword: '',
        account: '',
        parentAccount: '',
        recommendAccount: '',
        departPlace: ''
      }
    },
    methods: {
      createUser () {
        if (this.account === '' || this.password === '' || this.SecondPassword === '') {
          console.log('用户信息输入不完整')
        } else if (this.password !== this.SecondPassword) {
          console.log('密码输入不一致')
        } else {
          const url = GoeConfig.apiServer + '/user/create'
          this.$http.post(url,
            {
              account: this.username,
              password: this.password,
              parentAccount: this.parentAccount,
              departPlace: this.departPlace,
              recommendAccount: this.recommendAccount
            },
            {
              _timeout: 3000,
              onTimeout: (request) => {
                this.errMsg = '登陆超时'
                this.password = ''
              }
            })
            .then(response => {
              console.log(response.body)
              if (response.body.success) {
                this.$router.push({name: 'index', params: { LoginUser: response.body.data }})
                console.log('获取的data数据类型' + typeof response.body.data)
                window.localStorage.setItem('User', JSON.stringify(response.body.data))
              } else {
                this.errMsg = response.body.message
                this.password = ''
              }
            }, responseErr => {
              console.log(responseErr.body)
            })
        }
      }
    }
  }
</script>

<style lang="less" scoped>
  p {
    text-align: center;
  }
</style>
