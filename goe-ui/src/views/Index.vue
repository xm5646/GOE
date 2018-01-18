<template>
  <div class="page">
    <page-footer>
      <footer-item v-bind:class="{ 'active' : currentView === 'home' }" @click.native="changeView('home')">
        <span class="icon demo-icon-home"></span>
        <label>首页</label>
      </footer-item>
      <footer-item v-bind:class="{ 'active' : currentView === 'performance' }" @click.native="changeView('performance')">
        <span class="icon demo-icon-me"></span>
        <label>我的业绩</label>
      </footer-item>
      <footer-item v-bind:class="{ 'active' : currentView === 'wallet' }" @click.native="changeView('wallet')">
        <span class="icon demo-icon-wallet"></span>
        <label>我的钱包</label>
      </footer-item>
      <footer-item v-bind:class="{ 'active' : currentView === 'salary' }" @click.native="changeView('salary')">
        <span class="icon demo-icon-side"></span>
        <label>工资记录</label>
      </footer-item>
      <footer-item v-bind:class="{ 'active' : currentView === 'consume' }" @click.native="changeView('consume')">
        <span class="icon demo-icon-form"></span>
        <label>消费记录</label>
      </footer-item>
    </page-footer>
    <keep-alive>
      <component :is="currentView" @linkTo="changeView" ref="nowView"></component>
    </keep-alive>

  </div>

</template>

<script>
//  import vum components
import {SimpleHeader} from '../../node_modules/vum/src/components/header'
import Content from '../../node_modules/vum/src/components/content'
import { Button } from '../../node_modules/vum/src/components/buttons'
import { Form, FormItem } from '../../node_modules/vum/src/components/form'
import { Footer, Item, FooterItem } from '../../node_modules/vum/src/components/footer'
import Page from '../../node_modules/vum/src/components/page'

// import my vue pages
import Home from './index_views/Home'
import Performance from './index_views/Performance'
import Wallet from './index_views/Wallet'
import Salary from './index_views/Salary'
import Consume from './index_views/Consume'
import ResetPassword from './function/ResetPassword'
import GoeConfig from '../../config/goe'

console.log(FooterItem)
export default {
  mounted: function () {
    console.log(this.$route.params)
    if (this.$route.params.view != null) {
      this.currentView = this.$route.params.view
    } else {
      this.currentView = 'home'
    }
  },
  components: {
    'home': Home,
    'performance': Performance,
    'wallet': Wallet,
    'salary': Salary,
    'consume': Consume,
    'resetPassword': ResetPassword,
    Page,
    Content,
    SimpleHeader,
    'form-list': Form,
    FormItem,
    'm-button': Button,
    'page-footer': Footer,
    'footer-item': Item
  },
  data () {
    return {
      currentView: 'home',
      errMsg: ''
    }
  },
  methods: {
    changeView (view) {
      // update user wallet info
      if (view === 'wallet' || view === 'home') {
        const url = GoeConfig.apiServer + '/user/findByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
        this.$http.get(url,
          {
            _timeout: 3000,
            onTimeout: (request) => {
              console.log('请求超时')
              this.errMsg = '请求超时'
            }
          })
          .then(response => {
            if (response.body.success) {
              console.log(response.body.data)
              window.localStorage.setItem('User', JSON.stringify(response.body.data))
              this.$refs.nowView.update()
            } else {
              console.log('error:' + response.body.message)
              this.errMsg = response.body.message
            }
          }, responseErr => {
            console.log(responseErr)
            this.errMsg = '未知错误'
          })
      }
      // 切换视图
      this.currentView = view
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
.demo-icon-form {
  background-image: url("../assets/images/home/form.png");
  background-size: 100%;
}
.demo-icon-side {
  background-image: url("../assets/images/home/side.png");
  background-size: 100%;
}
.demo-icon-me {
  background-image: url("../assets/images/home/person.png");
  background-size: 100%;
}
.demo-icon-wallet {
  background-image: url("../assets/images/home/wallet.png");
  background-size: 100%;
}
#img-div {
  text-align:center
}
</style>
