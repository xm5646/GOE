<template>
  <div class="my-page">
    <page-header>
      <header-title style="background-color: orange" :back-link="true">会员管理系统</header-title>
    </page-header>
    <page-content>

      <performence-view :user="ViewUser"
                        :notFoundUser="notFoundUser"
                        @addUserEvent="AddUser"
                        @viewUsereEvent="changeUser"
                        @backMyUser="ShowMyPerformance">
      </performence-view>
    </page-content>
  </div>
</template>
<script>
  import PerformenceView from '../../components/PerformenceView'
  import Grid from '../../../node_modules/vum/src/components/grid'
  import { Header, HeaderLink, HeaderTitle } from '../../../node_modules/vum/src/components/header'
  import Content from '../../../node_modules/vum/src/components/content'
  import { List, ListItem } from '../../../node_modules/vum/src/components/list'
  import { FormList, FormItem } from '../../../node_modules/vum/src/components/form'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
      this.getPerformance(JSON.parse(window.localStorage.getItem('User')).account)
    },
    data () {
      return {
        notFoundUser: false,
        CurrentUser: {
          account: JSON.parse(window.localStorage.getItem('User')).account
        },
        ViewUser: {
          account: 'lixiaoming',
          performanceA: 12,
          performanceB: 12,
          performanceC: 0,
          departUserA: {
            account: 'lxmA',
            performanceA: 12,
            performanceB: 6,
            performanceC: 0
          },
          departUserB: {
            account: 'lxmB',
            performanceA: 12,
            performanceB: 6,
            performanceC: 0
          },
          departUserC: {
            account: '',
            performanceA: 0,
            performanceB: 0,
            performanceC: 0
          }
        }
      }
    },
    components: {
      PerformenceView,
      'page-header': Header,
      HeaderLink,
      HeaderTitle,
      List,
      ListItem,
      FormList,
      FormItem,
      'page-content': Content,
      Grid
    },
    methods: {
      changeUser (userId) {
        this.getPerformance(userId)
      },
      ShowMyPerformance () {
        this.getPerformance(this.CurrentUser.account)
      },
      AddUser (data) {
        console.log('上级ID' + data.parertUser + '放置节点位置:' + data.departMent)
        console.log('当前登录用户账号' + this.CurrentUser.account)
        this.$router.push({name: 'createUser', params: { parentUser: data.parertUser, recomendUser: this.CurrentUser.account, departMent: data.departMent }})
      },
      getPerformance (account) {
        const url = GoeConfig.apiServer + '/performance/findUserAndFollowerPerformance?account=' + account
        console.log('url' + url)
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
//              this.errMsg = '请求超时超时'
//              this.password = ''
          }
        })
          .then(response => {
            if (response.body.success) {
              this.notFoundUser = false
              const ViewUserTemp = response.body.data
              this.ViewUser.account = ViewUserTemp.account
              this.ViewUser.performanceA = ViewUserTemp.performanceA
              this.ViewUser.performanceB = ViewUserTemp.performanceB
              this.ViewUser.performanceC = ViewUserTemp.performanceC
              if (ViewUserTemp.departUserA == null) {
                this.ViewUser.departUserA.account = ''
              } else {
                this.ViewUser.departUserA.account = ViewUserTemp.departUserA.account
                this.ViewUser.departUserA.performanceA = ViewUserTemp.departUserA.performanceA
                this.ViewUser.departUserA.performanceB = ViewUserTemp.departUserA.performanceB
                this.ViewUser.departUserA.performanceC = ViewUserTemp.departUserA.performanceC
              }
              if (ViewUserTemp.departUserB == null) {
                this.ViewUser.departUserB.account = ''
              } else {
                this.ViewUser.departUserB.account = ViewUserTemp.departUserB.account
                this.ViewUser.departUserB.performanceA = ViewUserTemp.departUserB.performanceA
                this.ViewUser.departUserB.performanceB = ViewUserTemp.departUserB.performanceB
                this.ViewUser.departUserB.performanceC = ViewUserTemp.departUserB.performanceC
              }
              if (ViewUserTemp.departUserC == null) {
                this.ViewUser.departUserC.account = ''
              } else {
                this.ViewUser.departUserC.account = ViewUserTemp.departUserC.account
                this.ViewUser.departUserC.performanceA = ViewUserTemp.departUserC.performanceA
                this.ViewUser.departUserC.performanceB = ViewUserTemp.departUserC.performanceB
                this.ViewUser.departUserC.performanceC = ViewUserTemp.departUserC.performanceC
              }
              console.log(response.body.data)
            } else {
              this.notFoundUser = true
            }
          }, responseErr => {
            console.log(responseErr.body)
          })
      }
    }
  }
</script>
