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
                <input type="text" placeholder="请输入收款人用户编号">
              </div>
              <div class="item-title label">
                <m-button type="warning" size="large" @click.native="findUser">查询</m-button>
              </div>
            </div>
          </form-item>
        </form-list>

          <list v-if="findedUser">
            <list-item >
              <div class="item-media"><img src="../../assets/images/person.png" width="44"></div>
              <div class="item-content">
                <div class="item-title-row">
                  <div class="item-title">username</div>
                </div>
                <div class="item-subtitle">
                  <span class="performence-span">A:1</span>&nbsp;&nbsp;
                  <span class="performence-span">B:2</span>&nbsp;&nbsp;
                  <span class="performence-span">C:3</span>&nbsp;&nbsp;
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
                  <m-button type="warning" size="large" @click.native="findUser" :disabled="transferBtnStatus">转账</m-button>
                </div>
              </div>
            </form-item>
            {{ convertNumber }}
          </form-list>
          </list>
        <div v-if="NotFindUser">
          <m-button type="light" >未找到该用户</m-button>
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

  export default {
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
        bonusCoin: 1500,
        consumeCoin: 2000,
        convertNumber: null,
        findedUser: false,
        NotFindUser: false
      }
    },
    methods: {
      findUser () {
        this.findedUser = true
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
