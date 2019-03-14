<template>
  <div>
    <x-header :left-options="{showBack: false}">会员管理系统</x-header>
    <div id="img-div" style="width: 90%;margin-left: 5%">
      <img src="../assets/images/touming.png" width="100%">
      <div class="login-form">
        <x-input title="用户名" name="username" placeholder="请输入用户编号" :min="5" :max="20" v-model="account">
          <img slot="label" style="padding-right:10px;display:block;" src="../assets/images/login/user-red.png"
               width="24" height="24">
        </x-input>
        <x-input title="密码" type="password" placeholder="请输入密码" v-model="password" :min="6" :max="12" @on-enter="login">
          <img slot="label" style="padding-right:10px;display:block;" src="../assets/images/login/password-red.png"
               width="24" height="24">
        </x-input>
      </div>
      <br>
      <x-button type="warn" action-type="submit" @click.native="login">登录</x-button>

    </div>
  </div>

</template>
<script>
  import { XHeader, XInput, Group, XButton } from 'vux'
  import GoeConfig from '../../config/goe'

  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton
    },
    data () {
      return {
        msg: 'Login',
        password: '',
        account: '',
        show: false,
        show1: false
      }
    },
    methods: {
      getNotices () {
        const url = GoeConfig.apiServer + '/notice/getShownNotices'
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.success) {
              this.notices = response.body.data
              console.log('获取到的数据长度:' + this.notices.length)
              if (this.notices.length > 0) {
                this.noticeShowStatus = true
              }
              console.log('获取公告信息:' + this.noticeShowStatus)
              this.$router.push({name: 'index', params: {noticePanelShowStatus: this.noticeShowStatus, notices: this.notices}})
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
      login () {
        if (this.account.length > 0 && this.password.length > 5) {
          const url = GoeConfig.apiServer + '/user/login'
          this.$http.post(url,
            {
              account: this.account,
              password: this.password
            },
            {
              _timeout: 3000,
              onTimeout: (request) => {
                this.password = ''
              }
            })
            .then(response => {
              if (response.body.success) {
                const userObj = response.body.data
                window.localStorage.setItem('User', JSON.stringify(userObj))
                if (userObj.passwordReset !== '否') {
                  // TODO 判断用户是否缴纳年费
                  if (this.checkUserIsPayAnnualFee(userObj)) {
                    this.$vux.toast.show({
                      text: '登录成功'
                    })
                    this.getNotices()
                  } else {
                    this.$router.push({name: 'annualFee'})
                  }
                } else {
                  this.$router.push({name: 'initPassword'})
                }
              } else {
                this.$vux.toast.show({
                  type: 'cancel',
                  text: response.body.message
                })
                this.password = ''
              }
            }, responseErr => {
              this.$vux.toast.show({
                type: 'cancel',
                text: '系统异常'
              })
              this.password = ''
            })
        } else {
          this.$vux.toast.show({
            type: 'text',
            width: '10em',
            text: '请完整输入登陆信息'
          })
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
      }
    }
  }
</script>

<style lang="less">
  #img-div {
    text-align: center
  }
  .login-form {
    width: 100%;
    height: 100px;
    border: 1px solid #c0bfc4;
    border-radius: 15px;
  }
</style>
