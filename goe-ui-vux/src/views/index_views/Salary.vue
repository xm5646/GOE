<template>
  <div class="page">
    <x-header :left-options="{showBack: false}" style="background-color: #303135">会员管理系统</x-header>
    <group>
      <div>
        <x-table :cell-bordered="false" :content-bordered="true" style="background-color:#fff; font-size: small">
          <thead>
          <tr style="background-color: darkgray">
            <th>发放总额</th>
            <th>奖金</th>
            <th>产品积分</th>
            <th>综合费</th>
            <th>发放日期</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>100</td>
            <td>70</td>
            <td>20</td>
            <td>10</td>
            <td>2018-01-20</td>
          </tr>
          <tr>
            <td>100</td>
            <td>70</td>
            <td>20</td>
            <td>10</td>
            <td>2018-01-20</td>
          </tr>
          <tr>
            <td>100</td>
            <td>70</td>
            <td>20</td>
            <td>10</td>
            <td>2018-01-20</td>
          </tr>
          <tr>
            <td>100</td>
            <td>70</td>
            <td>20</td>
            <td>10</td>
            <td>2018-01-20</td>
          </tr>
          <tr>
            <td>100</td>
            <td>70</td>
            <td>20</td>
            <td>10</td>
            <td>2018-01-20</td>
          </tr>
          </tbody>
        </x-table>
      </div>
      <pager :value="1" title="" fillable :min="1" :max="totalPageNum" @on-change="change"></pager>
    </group>
  </div>
</template>

<script>
  import { XHeader, Grid, Panel, Divider, XTable } from 'vux'
  import pager from '../../components/Pager'
  import XButton from '../../../node_modules/vux/src/components/x-button/index.vue'
  import GridItem from '../../../node_modules/vux/src/components/grid/grid-item.vue'
  import Group from '../../../node_modules/vux/src/components/group/index.vue'

  export default {
    components: {
      Group,
      GridItem,
      XButton,
      XHeader,
      Grid,
      Panel,
      Divider,
      XTable,
      pager
    },
    data () {
      return {
        page: 1,
        totalPageNum: 14
      }
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
