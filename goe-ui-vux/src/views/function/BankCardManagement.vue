<template>
  <div class="page">
    <x-header :left-options="{showBack: true}" style="background-color: #303135">会员管理系统</x-header>
      <group title="银行卡信息管理">

      </group>
  </div>
</template>
<script>
  import { XHeader, Group, Panel, Divider, Card, Cell } from 'vux'
  export default {
    mounted: function () {
    },
    components: {
      XHeader,
      Group,
      Panel,
      Divider,
      Card,
      Cell
    },
    data () {
      return {
      }
    },
    methods: {
    },
    computed: {
    },
    watch: {
    }
  }
</script>

<style lang="less" scoped>
</style>
