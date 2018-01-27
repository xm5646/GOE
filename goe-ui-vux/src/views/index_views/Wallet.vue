<template>
  <div class="my-page">
    <x-header :left-options="{showBack: false}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '我的钱包'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>1130</span>
          <br/>
          奖金
        </div>
        <div class="vux-1px-r">
          ￥<span>15</span>
          <br/>
          报单币
        </div>
        <div class="vux-1px-r">
          ￥<span>0</span>
          <br/>
          产品积分
        </div>
      </div>
    </card>

    <group title='余额相关'>
      <cell title='申请提现' is-link @click.native="goTo('getCash')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
      <cell title='提现记录查询' is-link>
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
      <cell title='银行卡信息管理' is-link @click.native="goTo('bankCardManagement')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
      <cell title='收货地址管理' is-link @click.native="goTo('addressManagement')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
      <cell title='产品积分兑换' is-link @click.native="goTo('productCoinExchange')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
    </group>
  </div>
</template>
<script>
  import { XHeader, Group, Panel, Divider, Card, Cell } from 'vux'
  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      Group,
      Panel,
      Divider,
      Card,
      Cell
    },
    data () {
      return {
        title1: '我的钱包'
      }
    },
    methods: {
      goTo (url) {
        this.$router.push({name: url})
      }
    }
  }
</script>
<style scoped lang="less">
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
