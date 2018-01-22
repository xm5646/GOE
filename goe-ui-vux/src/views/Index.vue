<template>
  <div class="page">

    <keep-alive>
      <component :is="currentView" @linkTo="changeView" ref="nowView"></component>
    </keep-alive>
    <tabbar>
      <tabbar-item selected @click.native="changeView('home')">
        <img slot="icon" src="../assets/images/home/home.png">
        <span slot="label">首页</span>
      </tabbar-item>
      <tabbar-item @click.native="changeView('performance')">
        <img slot="icon" src="../assets/images/home/person.png">
        <span slot="label">我的业绩</span>
      </tabbar-item>
      <tabbar-item  @click.native="changeView('wallet')">
        <img slot="icon" src="../assets/images/home/wallet.png">
        <span slot="label">我的钱包</span>
      </tabbar-item>
      <tabbar-item @click.native="changeView('salary')">
        <img slot="icon" src="../assets/images/home/side.png">
        <span slot="label">工资记录</span>
      </tabbar-item>
      <tabbar-item @click.native="changeView('consume')">
        <img slot="icon" src="../assets/images/home/form.png">
        <span slot="label">消费记录</span>
      </tabbar-item>
    </tabbar>
  </div>
</template>

<script>
  import { XHeader, Group, Tabbar, TabbarItem, Cell } from 'vux'
  import Home from './index_views/Home'
  import Performance from './index_views/Performance'
  import Wallet from './index_views/Wallet'
  import Salary from './index_views/Salary'
  import Consume from './index_views/Consume'

  export default {
    mounted: function () {
    },
    components: {
//    'home': Home,
//    'performance': Performance,
//    'wallet': Wallet,
//    'salary': Salary,
//    'consume': Consume,
//    'resetPassword': ResetPassword,
      XHeader,
      Group,
      Tabbar,
      TabbarItem,
      Cell,
      'home': Home,
      'performance': Performance,
      'wallet': Wallet,
      'salary': Salary,
      'consume': Consume
    },
    data () {
      return {
        currentView: 'home'
//      errMsg: '',
//      isLoading: false
      }
    },
    methods: {
      changeView (view) {
        this.currentView = view
//      // update user wallet info
//      if (view === 'wallet' || view === 'home') {
//        const url = GoeConfig.apiServer + '/user/findByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
//        this.$http.get(url,
//          {
//            _timeout: 3000,
//            onTimeout: (request) => {
//              console.log('请求超时')
//              this.errMsg = '请求超时'
//            }
//          })
//          .then(response => {
//            if (response.body.success) {
//              window.localStorage.setItem('User', JSON.stringify(response.body.data))
//              this.$refs.nowView.update()
//            } else {
//              this.errMsg = response.body.message
//            }
//          }, responseErr => {
//            console.log(responseErr)
//            this.errMsg = '未知错误'
//          })
//      }
//      // 切换视图
//      this.currentView = view
      }
    }
  }
</script>

<style lang="less">
  /*.my-page {*/
  /*position: absolute;*/
  /*top: 0;*/
  /*left: 0;*/
  /*width: 100%;*/
  /*bottom: 2.8rem;*/
  /*}*/
  /*.demo-icon-home {*/
  /*background-image: url("../assets/images/home/home.png");*/
  /*background-size: 100%;*/
  /*}*/
  /*.demo-icon-form {*/
  /*background-image: url("../assets/images/home/form.png");*/
  /*background-size: 100%;*/
  /*}*/
  /*.demo-icon-side {*/
  /*background-image: url("../assets/images/home/side.png");*/
  /*background-size: 100%;*/
  /*}*/
  /*.demo-icon-me {*/
  /*background-image: url("../assets/images/home/person.png");*/
  /*background-size: 100%;*/
  /*}*/
  /*.demo-icon-wallet {*/
  /*background-image: url("../assets/images/home/wallet.png");*/
  /*background-size: 100%;*/
  /*}*/
  /*#img-div {*/
  /*text-align:center*/
  /*}*/
</style>
