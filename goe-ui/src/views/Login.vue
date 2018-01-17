<template>
  <div class="page">
    <page-header>
      <header-title style="background-color: orange" :back-link="true">会员管理系统</header-title>
    </page-header>
    <content>
      <br><br>
      <div id="img-div">
        <br>
        <img src="../assets/goe_logo.png" width="150px" height="150px">
      </div>
      <form-list>
        <form-item>
          <div class="item-media">
            <img src="../assets/images/form/i-form-name.png" alt="" width="30"/>
          </div>
          <div class="item-content">
            &nbsp;&nbsp;
            <div class="item-input">
              <input type="text" placeholder="请输入您的账号" v-model="username" @focus="userInput">
            </div>
          </div>
        </form-item>
        <form-item>
          <div class="item-media">
            <img src="../assets/images/form/i-form-password.png" alt="" width="30"/>
          </div>
          <div class="item-content">
            &nbsp;&nbsp;
            <div class="item-input">
              <input type="password" placeholder="请输入密码" class="" v-model="password" @focus="userInput">
            </div>
          </div>
        </form-item>
        <div v-if="isErr">
          <m-button type="warning" :disabled="true">{{ errMsg }}</m-button>
        </div>

        <br>
        <m-button type="warning" @click.native="doLogin">登录</m-button>
      </form-list>
    </content>
  </div>

</template>

<script>
  import Page from '../../node_modules/vum/src/components/page'
  import { Header, HeaderLink, HeaderTitle } from '../../node_modules/vum/src/components/header'
  import Content from '../../node_modules/vum/src/components/content'
  import { Button } from '../../node_modules/vum/src/components/buttons'
  import { Form, FormItem } from '../../node_modules/vum/src/components/form'
  import GoeConfig from '../../config/goe'

  export default {
    components: {
      Page,
      Content,
      'page-header': Header,
      HeaderLink,
      HeaderTitle,
      'form-list': Form,
      FormItem,
      'm-button': Button
    },
    data () {
      return {
        username: '',
        password: '',
        errMsg: '',
        isErr: false
      }
    },
    methods: {
      userInput () {
        this.isErr = false
      },
      doLogin () {
        if (this.account === '' || this.password === '') {
          this.isErr = true
          this.errMsg = '用户账户输入不完整'
        } else {
          const url = GoeConfig.apiServer + '/user/login'
          this.$http.post(url,
            {
              account: this.username,
              password: this.password
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
                this.$router.push({name: 'index', params: {LoginUser: response.body.data}})
                window.localStorage.setItem('User', JSON.stringify(response.body.data))
              } else {
                this.isErr = true
                this.errMsg = response.body.message
                this.password = ''
              }
            }, responseErr => {
              console.log(responseErr)
              this.isErr = true
              this.errMsg = '未知错误'
              this.password = ''
            })
        }
      }
    }
  }
</script>

<style lang="less">
  .my-page {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    bottom: 2.8rem;
  }

  .demo-icon-home {
    background-image: url("../assets/images/home/home.png");
    background-size: 100%;
  }

  .demo-icon-search {
    background-image: url("../assets/images/home/search.png");
    background-size: 100%;
  }

  .demo-icon-noti {
    background-image: url("../assets/images/home/button.png");
    background-size: 100%;
  }

  .demo-icon-me {
    background-image: url("../assets/images/home/person.png");
    background-size: 100%;
  }

  #img-div {
    text-align: center
  }
</style>
