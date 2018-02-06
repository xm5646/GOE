<template>
  <div class="my-page">
    <x-header :left-options="{showBack: false}">会员管理系统</x-header>
    <div class="clearboth"></div>
    <panel :list="list" :type="type"></panel>

    <card :header="{title: '业绩信息'}">
      <p slot="content" class="card-padding performance-info">累计业绩:&nbsp;&nbsp;A:{{performance.totalPerformanceA}}&nbsp;&nbsp; B:{{performance.totalPerformanceB}}&nbsp;&nbsp; C:{{performance.totalPerformanceC}}</p>
      <p slot="content" class="card-padding performance-info">新增业绩:&nbsp;&nbsp;A:{{performance.newPerformanceA}}&nbsp;&nbsp; B:{{performance.newPerformanceB}}&nbsp;&nbsp; C:{{performance.newPerformanceC}}</p>
    </card>

    <group title='常用功能'>
      <cell title='奖金转换报单币' is-link @click.native="goTo('convertConsumeCoin')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/icon/convert.png">
      </cell>
      <cell title='报单币转账' is-link @click.native="goTo('transferConsumeCoin')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/icon/transfer.png">
      </cell>
      <cell title='重复消费' is-link @click.native="goTo('reConsume')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;"
             src="../../assets/images/icon/reconsume.png">
      </cell>
      <cell title='订单查询' is-link @click.native="goTo('orderView')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/icon/order.png">
      </cell>
      <cell title='修改登录密码' is-link @click.native="goTo('resetPassword')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/icon/password.png">
      </cell>
      <cell title='修改交易密码' is-link @click.native="goTo('resetPayPassword')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;"
             src="../../assets/images/icon/payPassword.png">
      </cell>
      <cell title='退出登录' is-link @click.native="confirmLogout">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/icon/log-out.png">
      </cell>
    </group>
    <br>
    <br>
  </div>
</template>
<script>
  import { XHeader, Group, Panel, Divider, Card, Cell } from 'vux'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
      const userOjb = JSON.parse(window.localStorage.getItem('User'))
      this.user = userOjb
      if (userOjb.userStatus === '未激活') {
        this.list[0].title = userOjb.nickName + ' [' + userOjb.userLevel + '] -未激活'
      } else {
        this.list[0].title = userOjb.nickName + ' [' + userOjb.userLevel + ']'
      }
      if (userOjb.assessStatus === '未通过考核') {
        this.list[0].desc = '<strong>' + userOjb.assessStatus + '</strong><br>' + '下次考核日期:' + userOjb.assessDate
      } else {
        this.list[0].desc = '<strong>' + userOjb.assessStatus + '</strong><br>' + '下次考核日期:' + userOjb.assessDate
      }
      this.getPerformance()
    },
    components: {
      XHeader,
      Group,
      Panel,
      Divider,
      Card,
      Cell
    },
    data () {
      return {
        user: '',
        type: '1',
        list: [{
          src: require('../../assets/images/icon/user.png'),
          title: '张三 [组长]',
          desc: '<strong>已通过考核</strong><br>下次考核日期:2018-01-20'
        }],
        performance: {
          totalPerformanceA: 0,
          totalPerformanceB: 0,
          totalPerformanceC: 0,
          newPerformanceA: 0,
          newPerformanceB: 0,
          newPerformanceC: 0
        }
      }
    },
    methods: {
      confirmLogout () {
        const _this = this // 需要注意 onCancel 和 onConfirm 的 this 指向
        this.$vux.confirm.show({
          // 组件除show外的属性
          title: '确定要退出吗?',
          onCancel () {
            console.log(this) // 非当前 vm
            console.log(_this) // 当前 vm
          },
          onConfirm () {
            _this.logout()
          }
        })
      },
      logout () {
        window.localStorage.clear()
        this.$router.push({name: 'login'})
        var exp = new Date()
        exp.setTime(exp.getTime() - 1)
        document.cookie = 'autoLogin' + '=' + ';expires=' + exp.toGMTString()
      },
      goTo (url) {
        this.$router.push({name: url})
      },
      getPerformance () {
        const url = GoeConfig.apiServer + '/user/performance?account=' + this.user.account
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.success) {
              const performanceObj = response.body.data.performance
              console.log(performanceObj)
              this.performance.totalPerformanceA = performanceObj.departAcount
              this.performance.totalPerformanceB = performanceObj.departBcount
              this.performance.totalPerformanceC = performanceObj.departCcount
              this.performance.newPerformanceA = performanceObj.addDepartAcount
              this.performance.newPerformanceB = performanceObj.addDepartBcount
              this.performance.newPerformanceC = performanceObj.addDepartCcount
              console.log(this.performance)
            } else {
              this.$vux.toast.show({
                type: 'cancel',
                text: (response.body.message || '系统异常')
              })
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      },
      getUserInfo () {
        const url = GoeConfig.apiServer + '/user/findByAccount?account=' + this.user.account
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.success) {
              const result = response.body.data
              console.log('getuserinfo' + result)
              window.localStorage.setItem('User', JSON.stringify(result))
            } else {
              this.$vux.toast.show({
                type: 'cancel',
                text: (response.body.message || '系统异常')
              })
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      },
      update () {
        console.log('home update')
        const userOjb = JSON.parse(window.localStorage.getItem('User'))
        this.user = userOjb
        if (userOjb.userStatus === '未激活') {
          this.list[0].title = userOjb.nickName + ' [' + userOjb.userLevel + '] -未激活'
        } else {
          this.list[0].title = userOjb.nickName + ' [' + userOjb.userLevel + ']'
        }
        if (userOjb.assessStatus === '未通过考核') {
          this.list[0].desc = '<strong>' + userOjb.assessStatus + '</strong><br>' + '下次考核日期:' + userOjb.assessDate
        } else {
          this.list[0].desc = '<strong>' + userOjb.assessStatus + '</strong><br>' + '下次考核日期:' + userOjb.assessDate
        }
        this.getPerformance()
      }
    }
  }
</script>
<style scoped lang="less">
  @import '../../../node_modules/vux/src/styles/1px.less';

  .card-demo-flex {
    display: flex;
  }

  .card-demo-content01 {
    padding: 10px 0;
  }

  .card-padding {
    padding: 15px;
  }

  .card-demo-flex > div {
    flex: 1;
    text-align: center;
    font-size: 12px;
  }

  .card-demo-flex span {
    color: #f74c31;
  }

  .performance-info {
    color: gray;
  }

  /*.nav {*/
  /*position: fixed; !* 绝对定位，fixed是相对于浏览器窗口定位。 *!*/
  /*top: 0; !* 距离窗口顶部距离 *!*/
  /*left: 0; !* 距离窗口左边的距离 *!*/
  /*width: 100%; !* 宽度设置为100% *!*/
  /*z-index: 99; !* 层叠顺序，数值越大就越高。页面滚动的时候就不会被其他内容所遮挡。 *!*/
  /*margin-bottom: auto;*/
  /*}*/
</style>
