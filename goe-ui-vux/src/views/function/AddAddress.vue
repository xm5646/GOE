<template>
  <div >
    <x-header :left-options="{showBack: true}">会员管理系统</x-header>
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
  import GoeConfig from '../../../config/goe'
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
      backToWallet () {
        this.$router.push({name: 'index', params: {view: 'wallet'}})
      },
      login () {
        console.log(this.password)
        this.$router.push({name: 'index'})
      },
      changeBank (bank) {
        this.bankName = bank
      },
      changeAddress (ids, names) {
        this.addressIds = ids
        console.log(this.addressArray)
        this.addressNameArray = names
      },
      addAddress () {
        if (this.receiveName === '' || this.detail === '' || this.tel === '') {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '请输入完整的收货地址信息'
          })
        } else if (this.tel.length !== 11) {
          this.$vux.toast.show({
            type: 'text',
            width: '15em',
            text: '手机号码格式不正确'
          })
        } else {
          this.doAddAddress()
        }
      },
      doAddAddress () {
        console.log('add address')
        const url = GoeConfig.apiServer + '/expressAddress/save'
        this.$http.post(url,
          {
            account: JSON.parse(window.localStorage.getItem('User')).account,
            receiverName: this.receiveName,
            province: this.addressIds[0],
            city: this.addressIds[1],
            district: this.addressIds[2],
            detailAddress: this.detail,
            phone: this.tel,
            defaultAddress: 0
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.$vux.toast.show({
                text: '添加成功'
              })
              window.history.go(-1)
            } else {
              this.$vux.toast.show({
                type: 'cancel',
                text: (response.body.message || '系统异常')
              })
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
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
