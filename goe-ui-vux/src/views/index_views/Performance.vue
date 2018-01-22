<template>
  <div class="my-page">
    <x-header :left-options="{showBack: false}">会员管理系统</x-header>
    <search
      @result-click="resultClick"
      @on-change="getResult"
      v-model="findAccount"
      position="absolute"
      auto-scroll-to-top top="46px"
      @on-focus="onFocus"
      @on-cancel="onCancel"
      @on-submit="onSubmit"
      ref="search"></search>
    <performance-view :header="currentView"
                      :list="list"
                      :type="type"
                      @createUserEvent="createUser"
                      @on-click-item="viewUser">

    </performance-view>
    <br>
    <x-button @click.native="viewMyPerformance">返回我的业绩</x-button>
  </div>
</template>
<script>
  import { XHeader, Group, Divider, Card, Cell, Search, XButton } from 'vux'
  import PerformanceView from '../../components/ViewPerformance'
  export default {
    mounted: function () {
    },
    data () {
      return {
        findAccount: '',
        currentView: '当前查看用户: admin (A:12 B:12 C:0)',
        type: '1',
        list: [{
          isHasUser: true,
          src: require('../../assets/images/partABC/A.png'),
          title: '用户A',
          desc: 'A:12  B:12  C:0'
        }, {
          isHasUser: false,
          src: require('../../assets/images/partABC/B.png'),
          title: '用户B',
          desc: 'A:12  B:12  C:0'
        }, {
          isHasUser: false,
          src: require('../../assets/images/partABC/C.png'),
          title: '用户C',
          desc: 'A:12  B:12  C:0'
        }]
      }
    },
    components: {
      XButton,
      XHeader,
      Group,
      PerformanceView,
      Divider,
      Card,
      Cell,
      Search
    },
    methods: {
      setFocus () {
        this.$refs.search.setFocus()
      },
      resultClick (item) {
        window.alert('you click the result item: ' + JSON.stringify(item))
      },
      getResult (val) {
        console.log('on-change', val)
        this.results = val ? this.getResult(this.value) : []
      },
      onSubmit () {
        this.$refs.search.setBlur()
        this.$vux.toast.show({
          type: 'text',
          position: 'top',
          text: 'on submit'
        })
      },
      onFocus () {
        console.log('on focus')
      },
      onCancel () {
        console.log('on cancel')
      },
      createUser () {
        console.log('create user event')
      },
      viewUser (item) {
        if (item.isHasUser) {
          console.log('view user event' + item.title)
        } else {
          console.log('do nothing')
        }
      },
      viewMyPerformance () {
        console.log('view my performance')
      }
    }
  }
</script>
