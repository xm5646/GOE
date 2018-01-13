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
          <br>
          <div class="row">
            <div class="col-50">原价：</div>
            <div class="col-50">{{ discotePrice }}</div>
          </div>
          <div class="row">
            <div class="col-50">重消优惠价格：</div>
            <div class="col-50">{{ discotePrice }}</div>
          </div>
          <div class="row">
            <div class="col-20">数量</div>
            <div class="col-50"><input type="number" v-model="buyNumber" disabled="true"></div>
            <div class="col-10"><m-button @click.native="increaseNumber">+</m-button></div>
            <div class="col-10"><m-button @click.native="decreaseNumber">-</m-button></div>
          </div>
          <div class="row">
            <div class="col-50">总计： {{ discotePrice * buyNumber }}元</div>
            <div class="col-50"> <m-button :disabled="buyButton" >购买 </m-button></div>
          </div>


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
  import Toast from '../../../node_modules/vum/src/components/toast'
  import GOE from '../../../config/goe'

  export default {
    components: {
      SimpleHeader,
      Toast,
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
        price: GOE.goe.price,
        bonusCoin: 4000,
        consumeCoin: 2000,
        buyNumber: 1
      }
    },
    methods: {
      increaseNumber () {
        this.buyNumber += 1
      },
      decreaseNumber () {
        if (this.buyNumber >= 1) {
          this.buyNumber -= 1
        }
      }
    },
    computed: {
      discotePrice: function () {
        return (this.price * 0.7).toFixed(2)
      },
      finalPrice: function () { return this.discotePrice * this.buyNumber },
      buyButton: function () {
        return (this.finalPrice > this.consumeCoin || this.buyNumber === 0)
      }
    }
  }
</script>
<style >
  .row{
    margin-bottom: 2%;
  }
</style>
