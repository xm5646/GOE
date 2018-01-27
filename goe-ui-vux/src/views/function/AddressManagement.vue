<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="收货地址管理">
      <cell title='添加收货地址' is-link @click.native="addAddress">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/plus.png">
      </cell>
    </group>
    <div v-if="isHasAddress">
      <div v-for="(address,index) in showAddresses">
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
      没有地址
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

  export default {
    mounted: function () {
    },
    components: {
      XButton,
      XHeader,
      Group,
      Panel,
      Divider,
      Card,
      Cell,
      FormPreview
    },
    data () {
      return {
        type: '6',
        isHasAddress: true,
        Addresses: [{
          id: '11',
          receivedName: '李晓明',
          tel: '13520580169',
          addressArray: ['430000', '430400', '430407'],
          addressShowName: '',
          detail: '清河小营东路15号院中国电力科学研究院'
        }]
      }
    },
    methods: {
      addAddress () {
        this.$router.push({name: 'addAddress'})
      },
      editAddress (index) {
        this.$router.push({name: 'editAddress', params: {address: this.Addresses[index]}})
      },
      deleteAddress (index) {
        console.log('delete' + index)
      }
    },
    computed: {
      showAddresses: function () {
        if (this.Addresses != null) {
          var show = this.Addresses
          for (var i = 0; i < show.length; i++) {
            const idArray = show[i].addressArray
            const name = value2name(idArray, ChinaAddressV4Data)

            show[i].addressShowName = name
          }
          return show
        } else {
          this.isHasAddress = false
        }
      }
    },
    watch: {}
  }
</script>

<style lang="less" scoped>
</style>
