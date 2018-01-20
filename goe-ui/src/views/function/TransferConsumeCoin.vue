<template>
  <div class="page">
    <simple-header style="background-color: orange" title="会员管理系统" :back-link="true"></simple-header>
    <page-content>
      <div class='content-padded'>
        <h1 class="demos-title">报单币转账</h1>
        <list>
          <list-item>
            <div class="item-media"><img src="../../assets/images/rmb.png" width="44"></div>
            <div class="item-content">
              <div class="item-title-row">
                <div class="item-title">报单币余额</div>
              </div>
              <div class="item-subtitle">
                <span class="span-performance">{{ consumeCoin }}元</span>
              </div>
            </div>
          </list-item>
        </list>
        <form-list>
          <form-item>
            <div class="item-content">
              <div class="item-input">
                <input type="text" placeholder="请输入收款人用户编号" v-model="findAccount">
              </div>
              <div class="item-title label">
                <m-button type="warning" size="large" @click.native="findUser">查询</m-button>
              </div>
            </div>
          </form-item>
        </form-list>

          <list v-if="isFindedUser">
            <list-item >
              <div class="item-media"><img src="../../assets/images/person.png" width="44"></div>
              <div class="item-content">
                <div class="item-title-row">
                  <div class="item-title"> {{ user.account }}</div>
                </div>
                <div class="item-subtitle">
                  <span class="performence-span">A:{{user.preformanceA}}</span>&nbsp;&nbsp;
                  <span class="performence-span">B:{{user.preformanceB}}</span>&nbsp;&nbsp;
                  <span class="performence-span">C:{{user.preformanceC}}</span>&nbsp;&nbsp;
                </div>
              </div>
            </list-item>
          <form-list>
            <form-item>
              <div class="item-content">
                <div class="item-input">
                  <input type="number" placeholder="请输入转账金额" v-model="convertNumber">
                </div>
                <div class="item-title label">
                  <m-button type="warning" size="large" @click.native="transfer" :disabled="transferBtnStatus">转账</m-button>
                </div>
              </div>
            </form-item>
          </form-list>
          </list>
        <div v-if="NotFindUser">
          <m-button type="light" >{{ errMsg }}</m-button>
        </div>
        <br>
        <toast text="完成!" ref="t1"></toast>
        <toast text="失败!" type="error" ref="t2"></toast>
      </div>
    </page-content>
  </div>
</template>

<script>
  import { Button, ButtonGroup } from '../../../node_modules/vum/src/components/buttons'
  import { SimpleHeader } from '../../../node_modules/vum/src/components/header'
  import Content from '../../../node_modules/vum/src/components/content'
  import { Form, FormItem } from '../../../node_modules/vum/src/components/form'
  import Toast from '../../../node_modules/vum/src/components/toast'
  import Column from '../../../node_modules/vum/src/components/column'
  import { List, ListItem } from '../../../node_modules/vum/src/components/list'
  import GoeConfig from '../../../config/goe'

  export default {
    mounted: function () {
      console.log('mounted transfer')
      this.consumeCoin = JSON.parse(window.localStorage.getItem('User')).consumeCoin
    },
    components: {
      SimpleHeader,
      'page-content': Content,
      ButtonGroup,
      FormItem,
      Toast,
      Column,
      List,
      ListItem,
      'form-list': Form,
      'm-button': Button
    },
    data () {
      return {
        consumeCoin: 2000,
        convertNumber: null,
        isFindedUser: false,
        user: {
          account: '',
          preformanceA: '',
          preformanceB: '',
          preformanceC: ''
        },
        NotFindUser: false,
        findAccount: '',
        errMsg: ''
      }
    },
    methods: {
      findUser () {
        this.queryUser()
      },
      transfer () {
      },
      queryUser () {
        const url = GoeConfig.apiServer + '/user/performance?account=' + this.findAccount
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
//              this.errMsg = '请求超时超时'
//              this.password = ''
          }
        })
          .then(response => {
            if (response.body.success) {
              this.isFindedUser = true
              this.NotFindUser = false
              this.user.account = this.findAccount
              this.user.preformanceA = response.body.data.departAcount
              this.user.preformanceB = response.body.data.departBcount
              this.user.preformanceC = response.body.data.departCcount
            } else {
              this.isFindedUser = false
              this.NotFindUser = true
              this.errMsg = response.body.message
            }
            this.MyPerformance = response.body.data
          }, responseErr => {
            console.log(responseErr.body)
          })
      }
    },
    computed: {
      convertBtnEnable: function () {
        return (!this.convertNumber > 0 || this.convertNumber > this.bonusCoin)
      },
      finalConsumeCoin: function () {
        return Number(this.consumeCoin) + Number(this.convertNumber)
      },
      transferBtnStatus: function () {
        return (this.convertNumber <= 0 || this.convertNumber > this.consumeCoin)
      }
    }
  }
</script>

<style lang="less" scoped>
  p {
    text-align: center;
  }

  .row {
    margin-bottom: 2%;
  }
</style>
