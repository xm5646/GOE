<template>
  <div class="page">
    <content>
      <form-list>
        <form-item>
          <div class="item-content">
            <div class="item-input">
              <input type="text" placeholder="请输入用户编号" v-model="checkUserAccount" @keyup.enter="checkUser" @focus="userInput">
            </div>
            <div class="item-title label">
              <m-button type="warning" size="large" @click.native="checkUser">查询</m-button>
            </div>
          </div>
        </form-item>
      </form-list>
      <div v-if="notFoundUser">
        <m-button type="light" >未找到该用户</m-button>
      </div>
      <list >
        <list-item>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title"> 当前查看用户：{{ ViewUser.account}}
                <span>(A:{{ ViewUser.performanceA}} &nbsp;B:{{ ViewUser.performanceB}} &nbsp;C:{{ ViewUser.performanceC}})</span>
              </div>
            </div>
          </div>
        </list-item>
        <list-item :link="true" @click.native="ViewUserA" v-if="hasUserA">
          <div class="item-media"><img src="../assets/images/partABC/A.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title"> {{ ViewUser.departUserA.account }}</div>
            </div>
            <div class="item-subtitle">
              <span class="performence-span">A:{{ ViewUser.departUserA.performanceA }}</span>&nbsp;&nbsp;
              <span class="performence-span">B:{{ ViewUser.departUserA.performanceB }}</span>&nbsp;&nbsp;
              <span class="performence-span">C:{{ ViewUser.departUserA.performanceC }}</span>&nbsp;&nbsp;
            </div>
          </div>
        </list-item>
        <list-item v-if="!hasUserA">
          <div class="item-media"><img src="../assets/images/partABC/A.png" width="44"></div>
          &nbsp;&nbsp;&nbsp;<m-button size="warning" @click.native="AddUserA">新增</m-button>
        </list-item>
        <list-item :link="true" @click.native="ViewUserB" v-if="hasUserB">
          <div class="item-media"><img src="../assets/images/partABC/B.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">{{ ViewUser.departUserB.account }}</div>
            </div>
            <div class="item-subtitle">
              <span class="performence-span">A:{{ ViewUser.departUserB.performanceA }}</span>&nbsp;&nbsp;
              <span class="performence-span">B:{{ ViewUser.departUserB.performanceB }}</span>&nbsp;&nbsp;
              <span class="performence-span">C:{{ ViewUser.departUserB.performanceC }}</span>&nbsp;&nbsp;
            </div>
          </div>
        </list-item>
        <list-item v-if="!hasUserB">
          <div class="item-media"><img src="../assets/images/partABC/B.png" width="44"></div>
          &nbsp;&nbsp;&nbsp;<m-button size="warning" @click.native="AddUserB">新增</m-button>
        </list-item>
        <list-item :link="true" @click.native="ViewUserC" v-if="hasUserC">
          <div class="item-media"><img src="../assets/images/partABC/C.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">{{ ViewUser.departUserC.account }}</div>
            </div>
            <div class="item-subtitle">
              <span class="performence-span">A:{{ ViewUser.departUserC.performanceA }}</span>&nbsp;&nbsp;
              <span class="performence-span">B:{{ ViewUser.departUserC.performanceB }}</span>&nbsp;&nbsp;
              <span class="performence-span">C:{{ ViewUser.departUserC.performanceC }}</span>&nbsp;&nbsp;
            </div>
          </div>
        </list-item>
        <list-item v-if="!hasUserC">
          <div class="item-media"><img src="../assets/images/partABC/C.png" width="44"></div>
          &nbsp;&nbsp;&nbsp;<m-button size="warning" @click.native="AddUserC">新增</m-button>
        </list-item>
      </list>
      <m-button type="warning" size="warning" @click.native="goBackMyPer">返回我的业绩</m-button>
    </content>
  </div>

</template>

<script>
  //  import vum components
  import { SimpleHeader } from '../../node_modules/vum/src/components/header'
  import Content from '../../node_modules/vum/src/components/content'
  import { Button } from '../../node_modules/vum/src/components/buttons'
  import { Form, FormItem } from '../../node_modules/vum/src/components/form'
  import { Footer, Item, FooterItem } from '../../node_modules/vum/src/components/footer'
  import Page from '../../node_modules/vum/src/components/page'
  import { List, ListItem } from '../../node_modules/vum/src/components/list'

  console.log(FooterItem)
  export default {
    props: ['user', 'notFoundUser'],
    components: {
      Page,
      Content,
      List,
      ListItem,
      SimpleHeader,
      'form-list': Form,
      FormItem,
      'm-button': Button,
      'page-footer': Footer,
      'footer-item': Item
    },
    data () {
      return {
        ViewUser: this.user,
        checkUserAccount: ''
      }
    },
    methods: {
      userInput () {
        this.notFoundUser = false
      },
      ViewUserA () {
        this.checkUserAccount = ''
        this.$emit('viewUsereEvent', this.ViewUser.departUserA.account)
      },
      ViewUserB () {
        this.checkUserAccount = ''
        this.$emit('viewUsereEvent', this.ViewUser.departUserB.account)
      },
      ViewUserC () {
        this.checkUserAccount = ''
        this.$emit('viewUsereEvent', this.ViewUser.departUserC.account)
      },
      AddUserA () {
        this.$emit('addUserEvent', {
          'parentAccount': this.ViewUser.account,
          'departPlace': 'A'
        })
      },
      AddUserB () {
        this.$emit('addUserEvent', {
          'parentAccount': this.ViewUser.account,
          'departPlace': 'B'
        })
      },
      AddUserC () {
        this.$emit('addUserEvent', {
          'parentAccount': this.ViewUser.account,
          'departPlace': 'C'
        })
      },
      checkUser () {
        if (this.checkUserAccount !== '') {
          this.$emit('viewUsereEvent', this.checkUserAccount)
        } else {
        }
      },
      goBackMyPer () {
        this.$emit('backMyUser')
      }
    },
    computed: {
      hasUserA: function () {
        return (this.ViewUser.departUserA.account !== '')
      },
      hasUserB: function () {
        return (this.ViewUser.departUserB.account !== '')
      },
      hasUserC: function () {
        return (this.ViewUser.departUserC.account !== '')
      }
    }
  }
</script>

<style lang="less">
  .row {
    margin: 0px;
  }

  .list form-list {
    margin: 0px;
  }
</style>
