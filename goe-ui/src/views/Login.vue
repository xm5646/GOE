<template>
  <div class="page">
    <simple-header title="会员管理系统"></simple-header>
    <content>
      <br><br>
      <div id="img-div">
        <br>
        <img src="../assets/goe_logo.png" width="150px" height="150px">
      </div>
      <form-list>
        <form-item>
          <div class="item-media">
            <img src="../assets/images/form/i-form-name.png" alt="" width="30" />
          </div>
          <div class="item-content">
            &nbsp;&nbsp;
            <div class="item-input">
              <input type="text" placeholder="请输入您的账号" v-model="username">
            </div>
          </div>
        </form-item>
        <form-item>
          <div class="item-media">
            <img src="../assets/images/form/i-form-password.png" alt="" width="30" />
          </div>
          <div class="item-content">
            &nbsp;&nbsp;
            <div class="item-input">
              <input type="password" placeholder="请输入密码" class="" v-model="password">
            </div>
          </div>
        </form-item>
        <br>
        <m-button type="warning" size="large" @click.native="doLogin">登录</m-button>
      </form-list>
      {{ username }}
    </content>
  </div>

</template>

<script>
import Page from '../../node_modules/vum/src/components/page'
import {SimpleHeader} from '../../node_modules/vum/src/components/header'
import Content from '../../node_modules/vum/src/components/content'
import { Button } from '../../node_modules/vum/src/components/buttons'
import { Form, FormItem } from '../../node_modules/vum/src/components/form'
export default {
  components: {
    Page,
    Content,
    SimpleHeader,
    'form-list': Form,
    FormItem,
    'm-button': Button
  },
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    doLogin () {
      this.$http.get('http://192.168.123.21:8088/user/login', {
        _timeout: 3000,
        onTimeout: (request) => {
          console.log('访问超时')
        }
      }).then(response => {
        console.log(response.body)
      }, responseErr => {
        console.log(responseErr.body)
      })
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
  text-align:center
}
</style>
