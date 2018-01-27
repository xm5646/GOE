<template>
  <div >
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="修改收货地址">
      <x-input title="收货人" placeholder="" is-type="china-name" v-model="address.receivedName">
      </x-input>
      <x-address title="选择地区" v-model="address.addressArray" :list="addressData" placeholder="请选择地址">
        <template slot="title" slot-scope="props"><!-- use scope="props" when vue < 2.5.0 -->
          <span :class="props.labelClass" :style="props.labelStyle" style="height:24px;">
          <span class="demo-icon demo-icon-big" style="font-size:20px;vertical-align:middle;">
            <img src="../../assets/images/home/location.png" width="20px" height="20px"/>
          </span>
          <span style="vertical-align:middle;">地区</span>
        </span>
        </template>
      </x-address>
      <x-input title="详细地址" placeholder="" type="text" v-model="address.detail">
      </x-input>
      <x-input title="手机号码" type="text" is-type="china-mobile"  v-model="address.tel" >
      </x-input>
    </group>
    <br>
    <x-button @click.native="viewMyPerformance">修改</x-button>
  </div>
</template>
<script>
  import { XHeader, XInput, Group, XButton, Selector, XAddress, ChinaAddressV4Data } from 'vux'
  export default {
    props: ['Address'],
    mounted: function () {
      this.address = this.$route.params.address
    },
    components: {
      XHeader,
      XInput,
      Group,
      XButton,
      Selector,
      XAddress
    },
    data () {
      return {
        addressData: ChinaAddressV4Data,
        address: {
          id: '',
          receivedName: '',
          tel: '',
          addressArray: [ '', '', '' ],
          detail: ''
        }
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
