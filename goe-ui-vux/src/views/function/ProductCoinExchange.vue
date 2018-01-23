<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '产品积分兑换'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{productCoin}}</span>
          <br/>
          产品积分
        </div>
      </div>
    </card>
    <div v-if="!isEnableBuy">
      <message title="无法兑换" description="积分余额不足"></message>
    </div>
    <div v-else>
      <group>
        <group>
          <x-number  title="兑换数量"  :min="1" :max="MaxNumber" v-model="exchangeNumber" @on-change="changeNumber"></x-number>
        </group>
        <div>
          <form-preview header-label="单价" :header-value="showConvertNumber" :body-items="orderInfo" :footer-buttons="buttons2" name="demo"></form-preview>
        </div>
      </group>
    </div>
  </div>
</template>
<script>
  import {XHeader, XInput, Group, XButton, Card, FormPreview, XNumber} from 'vux'
  import GoeConfig from '../../../config/goe'
  import Message from '../../components/msg'

  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Card,
      FormPreview,
      XNumber,
      Message
    },
    data () {
      return {
        productCoin: 4000,
        exchangeNumber: 1,
        orderInfo: [{
          label: '兑换数量',
          value: '1'
        }, {
          label: '总计',
          value: '￥680'
        }],
        buttons2: [{
          style: 'primary',
          text: '兑换',
          onButtonClick: (name) => {
            alert(`clicking ${name}`)
          }
        }]
      }
    },
    methods: {
      changeNumber (val) {
        this.orderInfo[1].value = '￥' + val * GoeConfig.goe.price
      }
    },
    computed: {
      showConvertNumber: function () {
        return '积分￥' + GoeConfig.goe.price
      },
      isEnableBuy: function () {
        return this.productCoin > GoeConfig.goe.price
      },
      MaxNumber: function () {
        return parseInt(this.productCoin / GoeConfig.goe.price)
      }
    },
    watch: {
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
