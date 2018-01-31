<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="收货地址管理">
      <cell title='添加收货地址' is-link @click.native="addAddress">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/plus.png">
      </cell>
    </group>
    <div v-if="isHasAddress">
      <div v-for="(address,index) in addresses">
        <div class="weui-cells">
          <div class="weui-cell">
            <div class="weui-cell__bd">
              <p>{{address.receivedName}}&nbsp;&nbsp;{{address.tel}}</p>
            </div>
          </div>
          <p class="weui-cells__title" style="">{{address.addressShowName}}&nbsp;&nbsp;{{address.detail}}</p>
          <div class="weui-cell weui-cell_swiped">
            <div class="weui-cell__bd" style="transform: translateX(0px)">
              <div class="weui-cell">
              </div>
            </div>
            <div class="weui-cell__ft">
              <x-button mini @click.native="editAddress(index)">编辑</x-button>
              <x-button mini @click.native="deleteAddress(index)">删除</x-button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <message title="未添加收货地址"></message>
    </div>
  </div>
</template>
<script>
  import {
    XHeader,
    Group,
    Panel,
    Divider,
    Card,
    Cell,
    FormPreview,
    Value2nameFilter as value2name,
    ChinaAddressV4Data
  } from 'vux'
  import XButton from '../../../node_modules/vux/src/components/x-button/index.vue'
  import GoeConfig from '../../../config/goe'
  import Message from '../../components/msg'

  export default {
    mounted: function () {
      this.getAddressesByAccount()
    },
    components: {
      XButton,
      XHeader,
      Group,
      Panel,
      Divider,
      Card,
      Cell,
      Message,
      FormPreview
    },
    data () {
      return {
        type: '6',
        isHasAddress: true,
        Addresses1: [{
          id: '11',
          receivedName: '李晓明',
          tel: '13520580169',
          addressArray: ['430000', '430400', '430407'],
          addressShowName: '',
          detail: '清河小营东路15号院中国电力科学研究院'
        }],
        addresses: []
      }
    },
    methods: {
      addAddress () {
        this.$router.push({name: 'addAddress'})
      },
      editAddress (index) {
        this.$router.push({name: 'editAddress', params: {address: this.addresses[index]}})
      },
      deleteAddress (index) {
        console.log('delete' + index)
      },
      getAddressesByAccount () {
        const url = GoeConfig.apiServer + '/expressAddress/findExpressAddressesByAccount?account=' + JSON.parse(window.localStorage.getItem('User')).account
        this.$http.get(url, {
          _timeout: 3000,
          onTimeout: (request) => {
          }
        })
          .then(response => {
            if (response.body.totalElements > 0) {
              const addressesInfo = response.body.content
              console.log(addressesInfo)
              this.addresses.splice(0, this.addresses.length)
              for (let i = 0; i < addressesInfo.length; i++) {
                var address = {}
                address.id = addressesInfo[i].expressId
                address.receivedName = addressesInfo[i].receiverName
                address.tel = addressesInfo[i].phone
                var addressIdArras = new Array([])
                addressIdArras[0] = addressesInfo[i].province
                addressIdArras[1] = addressesInfo[i].city
                addressIdArras[2] = addressesInfo[i].district
                address.addressArray = addressIdArras
                address.addressShowName = value2name(addressIdArras, ChinaAddressV4Data)
                address.detail = addressesInfo[i].detailAddress
                this.addresses[i] = address
              }
              console.log(this.addresses)
            } else {
              this.addresses.splice(0, this.addresses.length)
              console.log('no cards')
            }
          }, responseErr => {
            this.$vux.toast.show({
              type: 'cancel',
              text: '系统异常'
            })
          })
      }
    },
    watch: {}
  }
</script>

<style lang="less" scoped>
</style>
