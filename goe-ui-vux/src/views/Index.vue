<template>
  <div class="my-page">
    <keep-alive>
      <component :is="currentView" @linkTo="changeView" ref="nowView"></component>
    </keep-alive>
    <tabbar style="position: fixed;">
      <tabbar-item selected @click.native="changeView('home')">
        <img slot="icon" src="../assets/images/home/home.png">
        <span slot="label">首页</span>
      </tabbar-item>
      <tabbar-item @click.native="changeView('performance')">
        <img slot="icon" src="../assets/images/home/person.png">
        <span slot="label">我的业绩</span>
      </tabbar-item>
      <tabbar-item @click.native="changeView('wallet')">
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
  import { XHeader, Group, Tabbar, TabbarItem, Cell, Scroller } from 'vux'
  import Home from './index_views/Home'
  import Performance from './index_views/Performance'
  import Wallet from './index_views/Wallet'
  import Salary from './index_views/Salary'
  import Consume from './index_views/Consume'
  import GoeConfig from '../../config/goe'

  export default {
    mounted: function () {
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
                alert('请求超时')
              }
            })
            .then(response => {
              if (response.body.success) {
                window.localStorage.setItem('User', JSON.stringify(response.body.data))
                this.$refs.nowView.update()
              } else {
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
  .weui-tabbar{
    position: absolute;
    z-index: 500;
    bottom: 0;
  }
</style>
