<template>
  <div >
    <x-header :left-options="{showBack: true}">会员管理系统</x-header>
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
    <x-button @click.native="confirmEdit(address)">修改</x-button>
  </div>
</template>
<script>
  import { XHeader, XInput, Group, XButton, Selector, XAddress, ChinaAddressV4Data } from 'vux'
  import GoeConfig from '../../../config/goe'
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
      confirmEdit (address) {
        const _this = this // 需要注意 onCancel 和 onConfirm 的 this 指向
        this.$vux.confirm.show({
          // 组件除show外的属性
          title: '确定修改吗?',
          onCancel () {
            console.log(this) // 非当前 vm
            console.log(_this) // 当前 vm
          },
          onConfirm () {
            _this.submitEdit(address)
          }
        })
      },
      submitEdit (address) {
        console.log('edit address')
        console.log(address)
        const url = GoeConfig.apiServer + '/expressAddress/update'
        this.$http.post(url,
          {
            account: JSON.parse(window.localStorage.getItem('User')).account,
            receiverName: address.receivedName,
            province: address.addressArray[0],
            city: address.addressArray[1],
            district: address.addressArray[2],
            detailAddress: address.detail,
            phone: address.tel,
            defaultAddress: 0,
            expressId: address.id
          },
          {
            _timeout: 3000,
            onTimeout: (request) => {
            }
          })
          .then(response => {
            if (response.body.success) {
              this.$vux.toast.show({
                text: '修改成功'
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
