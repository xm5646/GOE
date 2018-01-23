<template>
  <div class="my-page">
    <x-header :left-options="{showBack: false}" style="background-color: #303135;">会员管理系统</x-header>
    <div class="clearboth"></div>
    <panel :list="list" :type="type"></panel>

    <card :header="{title: '业绩信息'}">
      <p slot="content" class="card-padding performance-info">累计业绩:&nbsp;&nbsp;A:{{content}}&nbsp;&nbsp; B:{{content}}&nbsp;&nbsp; C:{{content}}</p>
      <p slot="content" class="card-padding performance-info">新增业绩:&nbsp;&nbsp;A:{{content}}&nbsp;&nbsp; B:{{content}}&nbsp;&nbsp; C:{{content}}</p>
    </card>

    <group title='常用功能'>
      <cell title='奖金转换报单币' is-link @click.native="goTo('convertConsumeCoin')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
      <cell title='报单币转账' is-link @click.native="goTo('transferConsumeCoin')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
      <cell title='重复消费' is-link @click.native="goTo('reConsume')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
      <cell title='修改登录密码' is-link @click.native="goTo('resetPassword')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
      <cell title='修改交易密码' is-link @click.native="goTo('resetPayPassword')">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
      <cell title='退出登录' is-link @click.native="logout">
        <img slot="icon" width="20" style="display:block;margin-right:5px;" src="../../assets/images/home/form.png">
      </cell>
    </group>
    <br>
    <br>
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
        type: '1',
        list: [{
          src: require('../../assets/images/person.png'),
          title: '张三 [组长]',
          desc: '<strong>已通过考核</strong><br>下次考核日期:2018-01-20'
        }],
        content: '12'
      }
    },
    methods: {
      logout () {
        this.$router.push({name: 'login'})
      },
      goTo (url) {
        this.$router.push({name: url})
      }
    }
  }
</script>
<style scoped lang="less">
  @import '../../../node_modules/vux/src/styles/1px.less';

  .card-demo-flex {
    display: flex;
  }

  .card-demo-content01 {
    padding: 10px 0;
  }

  .card-padding {
    padding: 15px;
  }

  .card-demo-flex > div {
    flex: 1;
    text-align: center;
    font-size: 12px;
  }

  .card-demo-flex span {
    color: #f74c31;
  }

  .performance-info {
    color: gray;
  }

  /*.nav {*/
  /*position: fixed; !* 绝对定位，fixed是相对于浏览器窗口定位。 *!*/
  /*top: 0; !* 距离窗口顶部距离 *!*/
  /*left: 0; !* 距离窗口左边的距离 *!*/
  /*width: 100%; !* 宽度设置为100% *!*/
  /*z-index: 99; !* 层叠顺序，数值越大就越高。页面滚动的时候就不会被其他内容所遮挡。 *!*/
  /*margin-bottom: auto;*/
  /*}*/
</style>
