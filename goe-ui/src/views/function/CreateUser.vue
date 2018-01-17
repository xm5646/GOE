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
                <input type="text" placeholder="请输入用户账户" class="" v-model="account" @focus="userInput">
              </div>
            </div>
          </form-item>
          <form-item>
            <div class="item-media">
              <img src="../../assets/images/form/i-form-password.png" alt="" width="30"/>
            </div>
            <div class="item-content">
              <div class="item-input">
                <input type="password" placeholder="请输入新密码" class="" v-model="password" @focus="userInput">
              </div>
            </div>
          </form-item>
          <form-item>
            <div class="item-media">
              <img src="../../assets/images/form/i-form-password.png" alt="" width="30"/>
            </div>
            <div class="item-content">
              <div class="item-input">
                <input type="password" placeholder="请再次输入新密码" class="" v-model="SecondPassword" @focus="userInput">
              </div>
            </div>
          </form-item>
          <div v-if="isErr">
            <m-button type="warning" :disabled="true">{{ errMsg }}</m-button>
          </div>
          <list>
            <list-item>
              <div class="item-media"><img src="../../assets/images/coin.png" width="44"></div>
              <div class="item-content">
                <div class="item-title-row">
                  <div class="item-title">报单币余额:  {{ consumeCoin }}元</div>
                </div>
              </div>
            </list-item>
          </list>
          <br>
          <m-button type="warning" size="large" @click.native="createUser" :disabled="cantCreate">创建</m-button>
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
  import { List, ListItem } from '../../../node_modules/vum/src/components/list'
  import Toast from '../../../node_modules/vum/src/components/toast'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
      console.log('设置创建用户参数')
      this.parentAccount = this.$route.params.parentAccount
      this.departPlace = this.$route.params.departPlace
      this.recommendAccount = this.$route.params.recommendAccount
    },
    components: {
      SimpleHeader,
      'page-content': Content,
      ButtonGroup,
      FormItem,
      List,
      ListItem,
      Toast,
      'form-list': Form,
      'm-button': Button
    },
    data () {
      return {
        password: '',
        SecondPassword: '',
        account: '',
        consumeCoin: 6880,
        parentAccount: '',
        recommendAccount: '',
        departPlace: '',
        isErr: false,
        errMsg: ''
      }
    },
    methods: {
      userInput () {
        this.isErr = false
      },
      createUser () {
        if (this.cantCreate) {
        } else {
          if (this.account === '' || this.password === '' || this.SecondPassword === '') {
            this.isErr = true
            this.errMsg = '信息输入不完整'
          } else if (this.password !== this.SecondPassword) {
            this.isErr = true
            this.errMsg = '密码输入不一致'
          } else {
            const url = GoeConfig.apiServer + '/user/save'
            this.isErr = false
            this.$http.post(url,
              {
                account: this.account,
                password: this.password,
                parentAccount: this.parentAccount,
                position: this.departPlace,
                recommendAccount: this.recommendAccount
              },
              {
                _timeout: 3000,
                onTimeout: (request) => {
                  this.isErr = true
                  this.errMsg = '请求超时'
                  this.password = ''
                  this.SecondPassword = ''
                }
              })
              .then(response => {
                console.log(response.body)
                if (response.body.success) {
                  this.$refs.t1.open()
                  this.$router.push({name: 'index', params: {view: 'performance', parentAccount: this.parentAccount}})
                } else {
                  this.isErr = true
                  this.errMsg = response.body.message
                  this.password = ''
                  this.SecondPassword = ''
                }
              }, responseErr => {
                this.isErr = true
                this.errMsg = '未知错误'
                this.password = ''
                this.SecondPassword = ''
              })
          }
        }
      }
    },
    computed: {
      cantCreate: function () {
        return this.consumeCoin < 660
      }
    }
  }
</script>

<style lang="less" scoped>
  p {
    text-align: center;
  }
</style>
