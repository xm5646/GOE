<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
    <group title="收货地址管理">
      <cell title='添加收货地址' is-link @click.native="addAddress">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/plus.png">
      </cell>
    </group>
    <performance-view
      :list="Addresses"
      :type="type">
    </performance-view>


  </div>
</template>
<script>
  import { XHeader, Group, Panel, Divider, Card, Cell } from 'vux'
  import PerformanceView from '../../components/ViewPerformance.vue'
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
      PerformanceView
    },
    data () {
      return {
        type: '6',
        Addresses: [{
          receivePeople: '李晓明 135****0169',
          address: '北京市海淀区清河小营东路15号'
        }, {
          receivePeople: '李晓明 135****0169',
          address: '北京市海淀区清河小营东路15号'
        }, {
          receivePeople: '李晓明 135****0169',
          address: '北京市海淀区清河小营东路15号中国电力科学研究院'
        }, {
          receivePeople: '李晓明 135****0169',
          address: '北京市海淀区清河小营东路15号'
        }]
      }
    },
    methods: {
      addAddress () {
        this.$router.push({name: 'addBankCard'})
      }
    },
    computed: {},
    watch: {}
  }
</script>

<style lang="less" scoped>
</style>
