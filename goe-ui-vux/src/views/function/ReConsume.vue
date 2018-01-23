<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '重复消费'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{bonusCoin}}</span>
          <br/>
          奖金
        </div>
        <div class="vux-1px-r">
          ￥<span>{{consumeCoin}}</span>
          <br/>
          报单币
        </div>
      </div>
    </card>
    <div v-if="!isEnableAccess">
      <message title="未达到考核日期" description="请在考核日当天进行重销" :buttons="buttons"></message>
    </div>
    <div v-else-if="isEnableBuy">
      <message title="报单币余额不足" description="请使用奖金转换报单币,或进行报单币充值" :buttons="buttons"></message>
    </div>
    <div v-else="">
      <group>
        <form-preview :body-items="list2" :footer-buttons="buttons2"
                      name="demo"></form-preview>
      </group>
    </div>
  </div>
</template>

<script>
  import { XHeader, XInput, Group, XButton, Card, FormPreview } from 'vux'
  import Message from '../../components/msg'
  import GoeConfig from '../../../config/goe'

  export default {
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview,
      Message
    },
    data () {
      return {
        bonusCoin: 2000,
        consumeCoin: 2000,
        isEnableAccess: true,
        price: GoeConfig.goe.price,
        reConsumePrice: GoeConfig.goe.reConsumePrice,
        buttons2: [{
          style: 'primary',
          text: '购买',
          onButtonClick: (name) => {
            alert(`clicking ${name}`)
          }
        }]
      }
    },
    methods: {},
    computed: {
      list2: function () {
        const list = [{
          label: '商品原价',
          value: '报单币￥' + this.price
        }, {
          label: '重销优惠价格',
          value: '报单币￥' + this.reConsumePrice
        }]
        return list
      },
      isEnableBuy: function () {
        return Number(this.consumeCoin) < Number(this.reConsumePrice)
      }
    }
  }
</script>
<style lang="less" scoped>
  @import '../../../node_modules/vux/src/styles/1px.less';

  .card-demo-flex {
    display: flex;
  }

  .card-demo-content01 {
    padding: 10px 0;
  }

  .card-padding {
    padding: 15px;
  }

  .card-demo-flex > div {
    flex: 1;
    text-align: center;
    font-size: 12px;
  }

  .card-demo-flex span {
    color: #f74c31;
  }
</style>
