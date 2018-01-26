<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="收货地址管理">
      <cell title='添加收货地址' is-link @click.native="addAddress">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/plus.png">
      </cell>
    </group>
    <div v-for="address in Addresses">
      <div class="weui-cells">
        <div class="weui-cell">
          <div class="weui-cell__bd">
            <p>{{address.receivedName}}&nbsp;&nbsp;{{address.tel}}</p>
          </div>
        </div>
        <p class="weui-cells__title" style="">{{address.address[0]}}{{address.address[1]}}{{address.address[2]}}{{address.detail}}</p>
        <div class="weui-cell weui-cell_swiped">
          <div class="weui-cell__bd" style="transform: translateX(0px)">
            <div class="weui-cell">
            </div>
          </div>
          <div class="weui-cell__ft">
            <a class="weui-swiped-btn weui-swiped-btn_warn" href="javascript:">编辑</a>
            <a class="weui-swiped-btn weui-swiped-btn_warn" href="javascript:">删除</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import { XHeader, Group, Panel, Divider, Card, Cell, FormPreview } from 'vux'
  export default {
    mounted: function () {
    },
    components: {
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
        Addresses: [{
          receivedName: '李晓明',
          tel: '135****0169',
          address: [ '北京市', '市辖区', '海淀区' ],
          detail: '清河小营东路15号院中国电力科学研究院'
        }, {
          receivedName: '李晓明',
          tel: '135****0169',
          address: [ '北京市', '市辖区', '海淀区' ],
          detail: '清河小营东路15号院中国电力科学研究院'
        }, {
          receivedName: '李晓明',
          tel: '135****0169',
          address: [ '北京市', '市辖区', '海淀区' ],
          detail: '清河小营东路15号院中国电力科学研究院'
        }, {
          receivedName: '李晓明',
          tel: '135****0169',
          address: [ '北京市', '市辖区', '海淀区' ],
          detail: '清河小营东路15号院中国电力科学研究院'
        }]
      }
    },
    methods: {
      addAddress () {
        this.$router.push({name: 'addAddress'})
      }
    },
    computed: {},
    watch: {}
  }
</script>

<style lang="less" scoped>
</style>
