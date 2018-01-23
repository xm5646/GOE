<template>
  <div >
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="添加银行卡">
      <x-input title="持卡人姓名" placeholder="" is-type="china-name" v-model="ownerName">
      </x-input>
      <selector v-model="bankName" placeholder="请选择银行"  :options="bankList" @on-change="changeBank"></selector>
      <x-input title="卡号" type="number"   v-model="cardNumber" :min="16" :max="19">
      </x-input>
    </group>
    <br>
    <x-button @click.native="viewMyPerformance">添加</x-button>
  </div>
</template>
<script>
  import { XHeader, XInput, Group, XButton, Selector } from 'vux'
  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Selector
    },
    data () {
      return {
        msg: 'Login',
        cardNumber: '',
        ownerName: '',
        bankName: '',
        bankList: ['农业银行', '建设银行', '工商银行', '中国银行', '中国邮政储蓄银行']
      }
    },
    methods: {
      login () {
        console.log(this.password)
        this.$router.push({name: 'index'})
      },
      changeBank (bank) {
        this.bankName = bank
      }
    }
  }
</script>

<style lang="less">
  #img-div {
    text-align: center
  }
</style>
