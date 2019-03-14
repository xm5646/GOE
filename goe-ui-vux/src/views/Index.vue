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
      <tabbar-item :selected="performanceSelected" @click.native="changeView('performance')">
        <img slot="icon" src="../assets/images/icon/performance.png">
        <img slot="icon-active" src="../assets/images/icon/performance_active.png">
        <span slot="label">我的业绩</span>
      </tabbar-item>
      <tabbar-item :selected="walletSelect" @click.native="changeView('wallet')">
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
      // 判断用户是否缴纳年费
      this.checkUserAnnualFeeStatus()
      // 判断是否是从 创建用户页面创建成功之后传过来的
      console.log('检查是否存在指定View: ' + this.$route.params.view)
      if (this.$route.params.view === 'performance') {
        this.currentView = 'performance'
        this.performanceSelected = true
        this.changeView('performance')
//        delete this.$route.params.view
      }
      if (this.$route.params.view === 'wallet') {
        console.log('恢复钱包视图')
        this.currentView = 'wallet'
        this.walletSelect = true
        this.changeView('wallet')
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
        currentView: 'home',
        walletSelect: false,
        performanceSelected: false
      }
    },
    methods: {
      checkUserAnnualFeeStatus () {
        if (this.checkUserIsPayAnnualFee(JSON.parse(window.localStorage.getItem('User')))) {
        } else {
          this.$router.push({name: 'annualFee'})
        }
      },
      checkUserIsPayAnnualFee (userObj) {
        var payAnnualYear = userObj.annualFeeYear
        var dates = userObj.createTime
        var datestr = dates.split('-')

        var needPayMonth = parseInt(datestr[1])
        var needPayDay = parseInt(datestr[2])
        var now = new Date()
        var nowDay = now.getDate()
        var nowMonth = now.getMonth() + 1
        var nowYear = now.getFullYear()
        // 判断本年度是否缴费
        if (nowYear === parseInt(payAnnualYear)) {
          return true
        } else {
          // 本年度未缴费, 判断月份和日
          if (nowMonth < needPayMonth) {
            // 当前月份小于创建日期月份,不需要缴费
            return true
          } else if (nowMonth > needPayMonth) {
            // 当前月份超过了创建日期月份,  需要缴费
            return false
          } else if (nowMonth === needPayMonth) {
            // 当前月份等于创建日期月份, 比对日期
            return nowDay <= needPayDay
          }
        }
      },
      changeView (view) {
        if (view === 'wallet' || view === 'home') {
          console.log('触发视图更新,更新数据')
          const url = GoeConfig.apiServer + '/user/findByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
          this.$http.get(url,
            {
              _timeout: 5000,
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
        // 切换视图
        this.currentView = view
        this.$nextTick(() => {
          if (view === 'salary') {
            this.$refs.nowView.update()
          }
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

  .weui-tabbar {
    position: absolute;
    z-index: 500;
    bottom: 0;
  }
</style>
