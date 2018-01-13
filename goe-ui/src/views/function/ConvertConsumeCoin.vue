<template>
  <div class="page">
    <simple-header style="background-color: orange" title="会员管理系统" :back-link="true"></simple-header>
    <page-content>
      <div class='content-padded'>
        <h1 class="demos-title">转换保单币</h1>
        <list>
          <list-item>
            <div class="item-media"><img src="../../assets/images/icon-list.png" width="44"></div>
            <div class="item-content">
              <div class="item-title-row">
                <div class="item-title">奖金余额</div>
              </div>
              <div class="item-subtitle">
                <span class="span-performance">{{ bonusCoin }}元</span>
              </div>
            </div>
          </list-item>
          <list-item>
            <div class="item-media"><img src="../../assets/images/icon-list.png" width="44"></div>
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
            <div class="row">
              <div class="col-60"><span>转换奖金金额￥：</span></div>
              <div class="col-40" ><input type="number" v-model="convertNumber"></div>
            </div>
          </form-item>
          <form-item>
            <div class="row">
              <div class="col-50"><span>剩余奖金金额：{{bonusCoin - convertNumber}}</span></div>
              <div class="col-50" ><span>报单币总金额：{{ finalConsumeCoin }}</span></div>
            </div>
          </form-item>

        </form-list>
        <m-button type="warning" size="large" @click.native="doLogin" :disabled="convertBtnEnable">转换</m-button>
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
        convertNumber: null
      }
    },
    methods: {
      doLogin () {
        this.$refs.t1.open()
      }
    },
    computed: {
      convertBtnEnable: function () {
        return (!this.convertNumber > 0 || this.convertNumber > this.bonusCoin)
      },
      finalConsumeCoin: function () {
        return Number(this.consumeCoin) + Number(this.convertNumber)
      }
    }
  }
</script>

<style lang="less" scoped>
  p {
    text-align: center;
  }
  .row{
    margin-bottom: 2%;
  }
</style>
