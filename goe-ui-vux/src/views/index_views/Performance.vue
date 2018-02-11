<template>
  <div class="my-page">
    <x-header :left-options="{showBack: false}">会员管理系统</x-header>
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
    <div v-if="!isSearching">
      <div v-if="inSearchStatus">
        <br>
      </div>
      <performance-view :header="currentView"
                        :list="list"
                        :type="type"
                        @createUserEvent="createUser"
                        @on-click-item="viewUser">

      </performance-view>
      <br>
      <!--<x-button @click.native="viewUpLevel" type="warn">返回上一层</x-button>-->
      <x-button @click.native="viewMyPerformance" type="warn">返回我的业绩</x-button>
    </div>
    <div v-if="isSearching">
      <br>
      <br>
      <message title="" description="正在查找指定用户"></message>
    </div>
  </div>
</template>
<script>
  import { XHeader, Group, Divider, Card, Cell, Search, XButton } from 'vux'
  import PerformanceView from '../../components/ViewPerformance'
  import GoeConfig from '../../../config/goe'
  import Message from '../../components/msg'

  export default {
    mounted: function () {
      if (this.$route.params.parentAccount == null) {
        const userObj = JSON.parse(window.localStorage.getItem('User'))
        this.user = userObj
        this.currentUser = userObj.account
        this.getPerformance(userObj.account)
      } else {
        this.$vux.loading.hide()
        console.log('改变当前业绩显示用户为:' + this.$route.params.parentAccount)
        this.changeUser(this.$route.params.parentAccount)
        this.currentUser = this.$route.params.parentAccount
      }
    },
    data () {
      return {
        user: '',
        findAccount: '',
        currentUser: '',
        upLevelAccount: '',
        isSearching: false,
        inSearchStatus: false,
        isNotFoundUser: false,
        currentView: '当前查看用户:  (A:0 B:0 C:0)',
        type: '1',
        list: [{
          isHasUser: true,
          src: require('../../assets/images/icon/A.png'),
          title: '用户A',
          desc: 'A:12  B:12  C:0',
          disableCreate: false,
          place: 'A'
        }, {
          isHasUser: true,
          src: require('../../assets/images/icon/B.png'),
          title: '用户B',
          desc: 'A:12  B:12  C:0',
          disableCreate: false,
          place: 'B'
        }, {
          isHasUser: false,
          src: require('../../assets/images/icon/C.png'),
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
      Search,
      Message
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
        if (this.findAccount.length > 0) {
          this.getPerformance(this.findAccount)
        } else {
          this.$vux.toast.show({
            type: 'text',
            width: '20em',
            text: '请输入要查找的用户编号'
          })
        }
      },
      onFocus () {
        this.inSearchStatus = true
        this.isSearching = true
        console.log('on focus')
      },
      onCancel () {
        this.inSearchStatus = false
        this.isSearching = false
        console.log('on cancel')
      },
      createUser (item) {
        const parentAccount = this.currentUser
        const recommendAccount = JSON.parse(window.localStorage.getItem('User')).account
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
      changeUser (account) {
        this.getPerformance(account)
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
      viewUpLevel () {
      },
      viewMyPerformance () {
        this.findAccount = ''
        this.getPerformance(JSON.parse(window.localStorage.getItem('User')).account)
      },
      getPerformance (account) {
        const url = GoeConfig.apiServer + '/performance/findUserAndFollowerPerformance?account=' + account
        console.log('url' + url)
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.success) {
              this.isSearching = false
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
              this.$vux.toast.show({
                type: 'cancel',
                text: response.body.message
              })
            }
          }, responseErr => {
            console.log(responseErr.body)
          })
      }
    }
  }
</script>
