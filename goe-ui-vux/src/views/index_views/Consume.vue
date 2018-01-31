<template>
  <div class="my-page">
    <x-header :left-options="{showBack: false}" style="background-color: #303135">会员管理系统</x-header>
    <group>
      <div style="width: 100%;overflow:scroll;-webkit-overflow-scrolling:touch;">
        <tab style="width: 100%; font-size: smaller" bar-active-color="#668599" :line-width="1">
          <tab-item selected @on-item-click="onItemClick">报单记录</tab-item>
          <tab-item @on-item-click="onItemClick">重销记录</tab-item>
          <tab-item @on-item-click="onItemClick">报单币转账</tab-item>
          <tab-item @on-item-click="onItemClick">积分兑换</tab-item>
          <tab-item @on-item-click="onItemClick">奖金转报单币</tab-item>
        </tab>
      </div>
      <keep-alive>
        <component :is="currentViewTable" @linkTo="changeTable" ref="nowViewTable" :list="pageInfo"></component>
      </keep-alive>
    </group>
  </div>
</template>
<script>
  import { XHeader, Grid, Panel, Divider, XTable, Tab, TabItem } from 'vux'
  import pager from '../../components/Pager'
  import XButton from '../../../node_modules/vux/src/components/x-button/index.vue'
  import GridItem from '../../../node_modules/vux/src/components/grid/grid-item.vue'
  import Group from '../../../node_modules/vux/src/components/group/index.vue'
  import {
    ReportOrdrerTable,
    ReConsumeTable,
    ProductCoinTable,
    TransferConsumeTable,
    ConvertConsumeTable
  } from './ConsumeTabels'

  export default {
    data () {
      return {
        currentViewTable: 'reportOrder',
        pageInfo: {
          page: 1,
          totalPageNum: 14
        }
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
      TabItem,
      'reportOrder': ReportOrdrerTable,
      'reConsume': ReConsumeTable,
      'transferConsume': TransferConsumeTable,
      'productCoinTable': ProductCoinTable,
      'convertConsume': ConvertConsumeTable
    },
    methods: {
      onItemClick (index) {
        this.changeTable(index)
      },
      changeTable (index) {
        switch (index) {
          case 0:
            this.currentViewTable = 'reportOrder'
            break
          case 1:
            this.currentViewTable = 'reConsume'
            break
          case 2:
            this.currentViewTable = 'transferConsume'
            break
          case 3:
            this.currentViewTable = 'productCoinTable'
            break
          case 4:
            this.currentViewTable = 'convertConsume'
            break
          default:
        }
      }
    }
  }
</script>
