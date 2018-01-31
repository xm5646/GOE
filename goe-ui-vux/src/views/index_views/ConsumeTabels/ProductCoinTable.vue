<template>
  <div class="divFont">
    <x-table :cell-bordered="false" :content-bordered="true" style="background-color:#fff;">
      <thead>
      <tr style="background-color: darkgray">
        <th>积分兑换</th>
        <th>奖金</th>
        <th>产品积分</th>
        <th>综合费</th>
        <th>发放日期</th>
      </tr>
      </thead>
      <tbody>
      <tr>
      </tr>
      </tbody>
    </x-table>
    <pager :value="1" title="" fillable :min="1" :max="list.totalPageNum" @on-change="change"></pager>
  </div>
</template>
<script>
  import { XHeader, Grid, Panel, Divider, XTable, Tab, TabItem } from 'vux'
  import pager from '../../../components/Pager'
  import XButton from '../../../../node_modules/vux/src/components/x-button/index.vue'
  import GridItem from '../../../../node_modules/vux/src/components/grid/grid-item.vue'
  import Group from '../../../../node_modules/vux/src/components/group/index.vue'

  export default {
    props: ['list'],
    data () {
      return {
        currentViewTable: '',
        totalPageNum: 1,
        rows: []
      }
    },
    components: {
      Group,
      GridItem,
      XButton,
      XHeader,
      Grid,
      Panel,
      Divider,
      XTable,
      pager,
      Tab,
      TabItem
    },
    methods: {
      change (val) {
        if (val === '') {
        } else {
          console.log('change', val)
        }
      }
    }
  }
</script>
<style scoped>
  .divFont {
    font-size: small;
  }
</style>
