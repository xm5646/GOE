<template>
  <div class="page">
    <simple-header style="background-color: orange" title="会员管理系统" :back-link="true"></simple-header>
    <page-content>
      <div class='content-padded'>
        <h1 class="demos-title">重复消费</h1>
        <list>
          <list-item>
            <div class="item-media"><img src="../../assets/images/icon-list.png" width="44"></div>
            <div class="item-content">
              <div class="item-title-row">
                <div class="item-title">奖金余额</div>
              </div>
              <div class="item-subtitle">
                <span class="span-performance">3333元</span>
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
                <span class="span-performance">690元</span>
              </div>
            </div>
          </list-item>
        </list>
        当前奖金：  33333    当前报单币：3333
        <form-list>
          <br>
          <div class="row">
            <div class="col-50"><input type="text" placeholder="请输入购买的数量" v-model="buyNumber"></div>
            <div class="col-50">
              <m-button size="large">购买</m-button>
            </div>
          </div>
          <m-button type="warning" size="large" @click.native="doLogin">购买</m-button>
          <toast text="完成!" ref="t1"></toast>
          <toast text="失败!" type="error" ref="t2"></toast>
        </form-list>
      </div>
    </page-content>
  </div>
</template>

<script>
  import { Button, ButtonGroup } from '../../../node_modules/vum/src/components/buttons'
  import { SimpleHeader } from '../../../node_modules/vum/src/components/header'
  import Content from '../../../node_modules/vum/src/components/content'
  import { Form, FormItem } from '../../../node_modules/vum/src/components/form'
  import { List, ListItem } from '../../../node_modules/vum/src/components/list'
  import Column from '../../../node_modules/vum/src/components/column'

  export default {
    components: {
      SimpleHeader,
      'page-content': Content,
      ButtonGroup,
      FormItem,
      List,
      ListItem,
      Column,
      'form-list': Form,
      'm-button': Button
    },
    data () {
      return {
        buyNumber: ''
      }
    },
    methods: {}
  }
</script>
