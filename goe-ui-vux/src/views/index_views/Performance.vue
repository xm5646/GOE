<template>
  <div class="my-page">
    <x-header :left-options="{showBack: false}" style="background-color: #303135">会员管理系统</x-header>
    <search
      @result-click="resultClick"
      @on-change="getResult"
      v-model="findAccount"
      position="absolute"
      auto-scroll-to-top top="46px"
      @on-focus="onFocus"
      @on-cancel="onCancel"
      @on-submit="onSubmit"
      ref="search"></search>
    <performance-view :header="currentView"
                      :list="list"
                      :type="type"
                      @createUserEvent="createUser"
                      @on-click-item="viewUser">

    </performance-view>
    <br>
    <x-button @click.native="viewMyPerformance">返回我的业绩</x-button>
  </div>
</template>
<script>
  import { XHeader, Group, Divider, Card, Cell, Search, XButton } from 'vux'
  import PerformanceView from '../../components/ViewPerformance'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
      const userObj = JSON.parse(window.localStorage.getItem('User'))
      this.user = userObj
      this.currentUser = userObj.account
      this.getPerformance(userObj.account)
    },
    data () {
      return {
        user: '',
        findAccount: '',
        currentUser: '',
        currentView: '当前查看用户:  (A:0 B:0 C:0)',
        ViewUserModel: {
          currentViewAccount: '',
          currentCountA: '',
          CurrentCountB: '',
          currentCountC: '',
          departUserA: {
            userAAccount: '',
            userACountA: '',
            userACountB: '',
            userACountC: ''
          },
          departUserB: {
            userBAccount: '',
            userBCountA: '',
            userBCountB: '',
            userBCountC: ''
          },
          departUserC: {
            userCAccount: '',
            userCCountA: '',
            userCCountB: '',
            userCCountC: ''
          }
        },
        type: '1',
        list: [{
          isHasUser: true,
          src: require('../../assets/images/partABC/A.png'),
          title: '用户A',
          desc: 'A:12  B:12  C:0',
          disableCreate: false,
          place: 'A'
        }, {
          isHasUser: true,
          src: require('../../assets/images/partABC/B.png'),
          title: '用户B',
          desc: 'A:12  B:12  C:0',
          disableCreate: false,
          place: 'B'
        }, {
          isHasUser: false,
          src: require('../../assets/images/partABC/C.png'),
          title: '用户C',
          desc: 'A:12  B:12  C:0',
          disableCreate: false,
          place: 'C'
        }]
      }
    },
    components: {
      XButton,
      XHeader,
      Group,
      PerformanceView,
      Divider,
      Card,
      Cell,
      Search
    },
    methods: {
      setFocus () {
        this.$refs.search.setFocus()
      },
      resultClick (item) {
        window.alert('you click the result item: ' + JSON.stringify(item))
      },
      getResult (val) {
        console.log('on-change', val)
        this.results = val ? this.getResult(this.value) : []
      },
      onSubmit () {
        this.$refs.search.setBlur()
        this.$vux.toast.show({
          type: 'text',
          position: 'top',
          text: 'on submit'
        })
      },
      onFocus () {
        console.log('on focus')
      },
      onCancel () {
        console.log('on cancel')
      },
      createUser (item) {
        const parentAccount = this.currentUser
        const recommendAccount = this.user.account
        const place = item.place
        this.$router.push({
          name: 'addUser',
          params: {
            parentAccount: parentAccount,
            recommendAccount: recommendAccount,
            place: place
          }
        })
      },
      viewUser (item) {
        if (item.isHasUser) {
          console.log('view user event' + item.title)
          this.currentUser = item.title
          this.getPerformance(item.title)
        } else {
          console.log('do nothing')
        }
      },
      viewMyPerformance () {
        this.getPerformance(this.user.account)
      },
      getPerformance (account) {
        const url = GoeConfig.apiServer + '/performance/findUserAndFollowerPerformance?account=' + account
        console.log('url' + url)
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
            alert('请求超时')
          }
        })
          .then(response => {
            if (response.body.success) {
              const flowPerformance = response.body.data
              this.currentView = '当前查看用户: ' + flowPerformance.account + ' (A:' + flowPerformance.performanceA + ' B:' + flowPerformance.performanceB + ' C:' + flowPerformance.performanceC + ')'
              if (flowPerformance.departUserA === null) {
                this.list[0].isHasUser = false
              } else {
                this.list[0].isHasUser = true
                this.list[0].title = flowPerformance.departUserA.account
                this.list[0].desc = 'A:' + flowPerformance.departUserA.performanceA + '  B:' + flowPerformance.departUserA.performanceB + '  C:' + flowPerformance.departUserA.performanceC
              }
              if (flowPerformance.departUserB === null) {
                this.list[1].isHasUser = false
              } else {
                this.list[1].isHasUser = true
                this.list[1].title = flowPerformance.departUserB.account
                this.list[1].desc = 'A:' + flowPerformance.departUserB.performanceA + '  B:' + flowPerformance.departUserB.performanceB + '  C:' + flowPerformance.departUserB.performanceC
              }
              if (flowPerformance.departUserC === null) {
                this.list[2].isHasUser = false
                if (flowPerformance.performanceA < 380 || flowPerformance.performanceB < 380) {
                  this.list[2].disableCreate = true
                } else {
                  this.list[2].disableCreate = false
                }
              } else {
                this.list[2].isHasUser = true
                this.list[2].title = flowPerformance.departUserC.account
                this.list[2].desc = 'A:' + flowPerformance.departUserC.performanceA + '  B:' + flowPerformance.departUserC.performanceB + '  C:' + flowPerformance.departUserC.performanceC
              }
              console.log(flowPerformance)
            } else {
            }
          }, responseErr => {
            console.log(responseErr.body)
          })
      }
    }
  }
</script>
