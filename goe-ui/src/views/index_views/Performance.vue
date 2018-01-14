<template>
  <div class="my-page">
    <page-header>
      <header-title style="background-color: orange" :back-link="true">会员管理系统</header-title>
    </page-header>
    <page-content>
      <performence-view :user="ViewUser" @viewUsereEvent="chengeUser" @backMyUser="ShowMyPerformance"></performence-view>
    </page-content>
  </div>
</template>
<script>
  import PerformenceView from '../../components/PerformenceView'
  import Grid from '../../../node_modules/vum/src/components/grid'
  import { Header, HeaderLink, HeaderTitle } from '../../../node_modules/vum/src/components/header'
  import Content from '../../../node_modules/vum/src/components/content'
  import {List, ListItem} from '../../../node_modules/vum/src/components/list'

  export default {
    created: function () {
    },
    data () {
      return {
        CurrentUser: {
          account: 'loginUser'
        },
        ViewUser: {
          account: 'lixiaoming',
          performanceA: 12,
          performanceB: 12,
          performanceC: 0,
          departUserA: {
            account: 'lmxA1',
            performanceA: 12,
            performanceB: 6,
            performanceC: 0
          },
          departUserB: {
            account: 'lmxB1',
            performanceA: 12,
            performanceB: 6,
            performanceC: 0
          },
          departUserC: {
            account: 'lmxC1',
            performanceA: 12,
            performanceB: 6,
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
      'page-content': Content,
      Grid
    },
    methods: {
      chengeUser (userId) {
        this.ViewUser.account = userId
      },
      ShowMyPerformance () {
        this.ViewUser.account = this.CurrentUser.account
      }
    }
  }
</script>
