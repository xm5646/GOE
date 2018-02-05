<template>
  <div style="height:100%;" class="body">
      <router-view class="router-view" style="height:100%;"></router-view>
  </div>
</template>

<script>
  import { ViewBox } from 'vux'

  export default {
    name: 'app',
    components: {
      ViewBox
    },
    data () {
      return {
      }
    },
    computed: {
    }
  }
</script>

<style lang="less">
  @import '~vux/src/styles/reset.less';
  body {
    background-color: #fbf9fe;
  }
  .router-view {
    width: 100%;
    top: 46px;
  }
</style>
