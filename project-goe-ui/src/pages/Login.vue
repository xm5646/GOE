<template>
  <div class="page">
    <simple-header title="GOE会员管理系统"></simple-header>
    <content>
      <br><br>
      <div id="img-div">
        <img src="../assets/goe_logo.png" width="150px" height="150px">
      </div>
      <form-list>
        <form-item>
          <div class="item-media">
            <img src="../assets/images/form/i-form-name.png" alt="" width="30" />
          </div>
          <div class="item-content">
            <div class="item-title label">用户名</div>
            <div class="item-input">
              <input type="text" placeholder="请输入您的账号">
            </div>
          </div>
        </form-item>
        <form-item>
          <div class="item-media">
            <img src="../assets/images/form/i-form-password.png" alt="" width="30" />
          </div>
          <div class="item-content">
            <div class="item-title label">密码</div>
            <div class="item-input">
              <input type="password" placeholder="请输入密码" class="">
            </div>
          </div>
        </form-item>
        <br>
        <m-button type="warning" size="large">登录</m-button>
      </form-list>
    </content>
  </div>

</template>

<script>
import Page from '../components/page'
import {SimpleHeader} from '../components/header'
import Content from '../components/content'
import { Button } from '../components/buttons'
import { Form, FormItem } from '../components/form'
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
    }
  },
  methods: {
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
