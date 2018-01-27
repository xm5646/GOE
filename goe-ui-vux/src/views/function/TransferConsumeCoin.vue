<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <card :header="{title: '报单币转账'}">
      <div slot="content" class="card-demo-flex card-demo-content01">
        <div class="vux-1px-r">
          ￥<span>{{consumeCoin}}</span>
          <br/>
          报单币
        </div>
      </div>
    </card>
    <divider title="收款用户查询">收款用户查询</divider>
    <group title="" class="weui-cells_form">
      <x-input title="" class="weui-vcode" placeholder="请输入收款用的编号">
        <x-button slot="right" type="primary" mini @click.native="findUser">查找</x-button>
      </x-input>
    </group>
    <div v-if="isFinded">
      <panel :list="list" :type="type" ></panel>
      <x-input title="￥" type="number" placeholder="请输入转帐金额" v-model="transferNumber" :min="1" :max="12" ></x-input>
    </div>
    <div v-if="isNotFinded">
      <message title="未找到用户" description="请确认要查找的用户编号是否正确" :buttons="buttons"></message>
    </div>
    <div v-if="isInput">
      <form-preview header-label="转账金额" :header-value="showConvertNumber" :body-items="list2" :footer-buttons="buttons2" name="demo"></form-preview>
    </div>
    <pay-password :showPayPasswordInput="showPayPasswordStatus" @paySubmitEvent="submitPay" @cancelPayEvent="cancelPay"></pay-password>
  </div>
</template>

<script>
  import { XHeader, XInput, Group, XButton, Card, FormPreview, Search, Divider, Panel, Msg } from 'vux'
  import Message from '../../components/msg'
  import PayPassword from '../../components/PayPassword'
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
      Search,
      Divider,
      Panel,
      Msg,
      Message,
      PayPassword
    },
    data () {
      return {
        consumeCoin: 2000,
        isFinded: false,
        isNotFinded: false,
        transferNumber: '',
        showPayPasswordStatus: false,
        type: '1',
        list: [{
          src: require('../../assets/images/person.png'),
          title: '张三 [组长]',
          desc: 'A:20  B:20:  C:0'
        }],
        list2: [{
          label: '收款用户',
          value: 'admin'
        }],
        buttons2: [{
          style: 'primary',
          text: '转账',
          onButtonClick: (name) => {
            this.showPayPasswordStatus = true
          }
        }]
      }
    },
    methods: {
      findUser () {
        this.isFinded = true
      },
      submitPay (payPassword) {
        this.showPayPasswordStatus = false
        console.log(payPassword)
      },
      cancelPay () {
        console.log('close')
        this.showPayPasswordStatus = false
      }
    },
    computed: {
      isInput: function () {
        return (Number(this.transferNumber) > 0 && this.transferNumber <= this.consumeCoin)
      }
    },
    watch: {
      transferNumber (newValue, oldValue) {
        this.showConvertNumber = '￥' + newValue
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
