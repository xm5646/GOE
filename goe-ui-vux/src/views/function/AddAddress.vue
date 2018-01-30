<template>
  <div >
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="添加收货地址">
      <x-input title="收货人" placeholder="" is-type="china-name" v-model="receiveName">
      </x-input>
      <x-address  title="" v-model="addressArray" :list="addressData" placeholder="请选择地址" @on-shadow-change="changeAddress">
        <template slot="title" slot-scope="props"><!-- use scope="props" when vue < 2.5.0 -->
          <span :class="props.labelClass" :style="props.labelStyle" style="height:24px;">
          <span class="demo-icon demo-icon-big" style="font-size:20px;vertical-align:middle;">
            <img src="../../assets/images/home/location.png" width="20px" height="20px"/>
          </span>
          <span style="vertical-align:middle;">地区</span>
        </span>
        </template>
      </x-address>
      <x-input title="详细地址" placeholder="" type="text" v-model="detail">
      </x-input>
      <x-input title="手机号码" type="number" is-type="china-mobile"  v-model="tel" >
      </x-input>
    </group>
    <br>
    <x-button @click.native="addAddress">添加</x-button>
  </div>
</template>
<script>
  import { XHeader, XInput, Group, XButton, Selector, XAddress, ChinaAddressV4Data } from 'vux'
  export default {
    mounted: function () {
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
        msg: 'Login',
        addressData: ChinaAddressV4Data,
        addressArray: [],
        addressIds: [],
        addressNameArray: '',
        receiveName: '',
        detail: '',
        tel: ''
      }
    },
    methods: {
      login () {
        console.log(this.password)
        this.$router.push({name: 'index'})
      },
      changeBank (bank) {
        this.bankName = bank
      },
      changeAddress (ids, names) {
        this.addressIds = ids
        this.addressNameArray = names
      },
      addAddress () {
        this.$vux.toast.show({
          type: 'text',
          width: '15em',
          text: '地址管理功能暂时不可用'
        })
      }
    }
  }
</script>

<style lang="less">
  #img-div {
    text-align: center
  }
</style>
