<template>
  <div class="my-page">
    <keep-alive>
      <component :is="currentView" @linkTo="changeView" ref="nowView"></component>
    </keep-alive>
    <tabbar style="position: fixed;">
      <tabbar-item selected @click.native="changeView('home')">
        <img slot="icon" src="../assets/images/icon/home.png">
        <img slot="icon-active" src="../assets/images/icon/home_active.png">
        <span slot="label">首页</span>
      </tabbar-item>
      <tabbar-item @click.native="changeView('performance')">
        <img slot="icon" src="../assets/images/icon/performance.png">
        <img slot="icon-active" src="../assets/images/icon/performance_active.png">
        <span slot="label">我的业绩</span>
      </tabbar-item>
      <tabbar-item @click.native="changeView('wallet')">
        <img slot="icon" src="../assets/images/icon/wallet.png">
        <img slot="icon-active" src="../assets/images/icon/wallet_active.png">
        <span slot="label">我的钱包</span>
      </tabbar-item>
      <tabbar-item @click.native="changeView('salary')">
        <img slot="icon" src="../assets/images/icon/salary.png">
        <img slot="icon-active" src="../assets/images/icon/salary_active.png">
        <span slot="label">工资记录</span>
      </tabbar-item>
      <tabbar-item @click.native="changeView('consume')">
        <img slot="icon" src="../assets/images/icon/consume.png">
        <img slot="icon-active" src="../assets/images/icon/consume_active.png">
        <span slot="label">消费记录</span>
      </tabbar-item>
    </tabbar>
  </div>
</template>

<script>
  import { XHeader, Group, Tabbar, TabbarItem, Cell, Scroller } from 'vux'
  import Home from './index_views/Home'
  import Performance from './index_views/Performance'
  import Wallet from './index_views/Wallet'
  import Salary from './index_views/Salary'
  import Consume from './index_views/Consume'
  import GoeConfig from '../../config/goe'

  export default {
    mounted: function () {
      // 判断是否是从 创建用户页面创建成功之后传过来的
      if (this.$route.params.view === 'performance') {
        this.currentView = 'performance'
        delete this.$route.params.view
      }
    },
    components: {
      XHeader,
      Group,
      Tabbar,
      TabbarItem,
      Cell,
      Scroller,
      'home': Home,
      'performance': Performance,
      'wallet': Wallet,
      'salary': Salary,
      'consume': Consume
    },
    data () {
      return {
        currentView: 'home'
      }
    },
    methods: {
      changeView (view) {
        if (view === 'wallet' || view === 'home') {
          const url = GoeConfig.apiServer + '/user/findByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
          this.$http.get(url,
            {
              _timeout: 3000,
              onTimeout: (request) => {
              }
            })
            .then(response => {
              if (response.body.success) {
                window.localStorage.setItem('User', JSON.stringify(response.body.data))
                this.$refs.nowView.update()
              } else {
                this.$vux.toast.show({
                  text: response.body.message
                })
              }
            }, responseErr => {
              this.$vux.toast.show({
                type: 'cancel',
                text: '系统异常'
              })
            })
        }
//        if (view === 'salary') {
//          this.$refs.nowView.update()
//        }
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
  .weui-tabbar{
    position: absolute;
    z-index: 500;
    bottom: 0;
  }
</style>
