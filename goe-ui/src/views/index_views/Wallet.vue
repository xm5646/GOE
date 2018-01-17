<template>
  <div class="my-page">
    <page-header>
      <header-title style="background-color: orange" :back-link="true">会员管理系统</header-title>
    </page-header>
    <page-content>
      <!--<header class='demos-header'>-->
        <!--<list style="margin: 0px">-->
          <!--<list-item>-->
            <!--<div class="item-media"><img src="../../assets/goe_logo.png" width="80"></div>-->
            <!--<div class="item-content">-->
              <!--<div class="item-title-row">-->
                <!--<div class="item-title">张三</div>-->
                <!--<div class="item-after">已激活</div>-->
              <!--</div>-->
              <!--<div class="item-subtitle">-->
                <!--【组长】-->
              <!--</div>-->
              <!--<div class="item-text">-->
                <!--距离下次重销日：  10天-->
              <!--</div>-->
            <!--</div>-->
          <!--</list-item>-->
        <!--</list>-->
      <!--</header>-->
      <!--业绩显示-->
      <list>
        <list-item>
          <div class="item-media"><img src="../../assets/images/coin.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">奖金余额</div>
            </div>
            <div class="item-subtitle">
              <span class="span-performance">￥{{ bonusCoin }}</span>
            </div>
          </div>
        </list-item>
        <list-item>
          <div class="item-media"><img src="../../assets/images/coin.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">报单币余额</div>
            </div>
            <div class="item-subtitle">
              <span class="span-performance">￥{{ consumeCoin }}</span>
            </div>
          </div>
        </list-item>
        <list-item>
          <div class="item-media"><img src="../../assets/images/coin.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">产品积分余额</div>
            </div>
            <div class="item-subtitle">
              <span class="span-performance">￥{{ productCoin }}</span>
            </div>
          </div>
        </list-item>
      </list>

      <m-button type="warning" size="warning" @click.native="doLogin">奖金提现</m-button>
      <br>
      <m-button type="warning" size="warning" @click.native="doLogin">报单币转账</m-button>
      <br>
      <m-button type="warning" size="warning" @click.native="doLogin">产品积分兑换</m-button>



    </page-content>
  </div>
</template>
<script>
  import Grid from '../../../node_modules/vum/src/components/grid'
  import { Header, HeaderLink, HeaderTitle } from '../../../node_modules/vum/src/components/header'
  import Content from '../../../node_modules/vum/src/components/content'
  import {List, ListItem} from '../../../node_modules/vum/src/components/list'
  import { Button, ButtonGroup } from '../../../node_modules/vum/src/components/buttons'
  import { Form, FormItem } from '../../../node_modules/vum/src/components/form'

  export default {
    mounted: function () {
      const CurrentUser = JSON.parse(window.localStorage.getItem('User'))
      this.bonusCoin = CurrentUser.bonusCoin
      this.consumeCoin = CurrentUser.consumeCoin
      this.productCoin = CurrentUser.productCoin
    },
    components: {
      'page-header': Header,
      HeaderLink,
      HeaderTitle,
      List,
      ListItem,
      FormItem,
      'form-list': Form,
      'page-content': Content,
      Grid,
      'm-button': Button,
      ButtonGroup
    },
    data () {
      return {
        bonusCoin: 3000,
        consumeCoin: 2000,
        productCoin: 10
      }
    },
    methods: {
      linkTo (url) {
        this.$emit('linkTo', url)
      }
    }
  }
</script>
<style>
  .span-performance{
    margin-right: 10%;
  }
</style>
