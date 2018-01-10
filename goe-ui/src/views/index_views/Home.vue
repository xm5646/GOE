<template>
  <div class="my-page">
    <page-header>
      <header-title style="background-color: orange" :back-link="true">会员管理系统</header-title>
    </page-header>
    <page-content>
      <header class='demos-header'>
        <list style="margin: 0px">
          <list-item>
            <div class="item-media"><img src="../../assets/goe_logo.png" width="80"></div>
            <div class="item-content">
              <div class="item-title-row">
                <div class="item-title">张三</div>
                <div class="item-after">已激活</div>
              </div>
              <div class="item-subtitle">
                【组长】
              </div>
              <div class="item-text">
                距离下次重销日：  10天
              </div>
            </div>
          </list-item>
        </list>
      </header>
      <!--业绩显示-->
      <list>
        <list-item>
          <div class="item-media"><img src="../../assets/images/icon-list.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">累计业绩</div>
            </div>
            <div class="item-subtitle">
              <span class="span-performance">A:12</span><span class="span-performance">B:12</span><span class="span-performance">C:0</span>
            </div>
          </div>
        </list-item>
        <list-item>
          <div class="item-media"><img src="../../assets/images/icon-list.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">新增业绩</div>
            </div>
            <div class="item-subtitle">
              <span class="span-performance">A:12</span><span class="span-performance">B:12</span><span class="span-performance">C:0</span>
            </div>
          </div>
        </list-item>
      </list>


      <list>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content">
            <router-link :to="{ name: '' }">
              <div class="item-title-row">
                <div class="item-title">我的业绩</div>
                <div class="item-after"><div class="link-arrow icon icon-link"></div></div>
              </div>
            </router-link>
          </div>
        </list-item>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content">
            <router-link :to="{ name: '' }">
              <div class="item-title-row">
                <div class="item-title">奖金报单币互转</div>
                <div class="item-after"><div class="link-arrow icon icon-link"></div></div>
              </div>
            </router-link>
          </div>
        </list-item>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content">
            <router-link :to="{ name: '' }">
              <div class="item-title-row">
                <div class="item-title">报单币转账</div>
                <div class="item-after"><div class="link-arrow icon icon-link"></div></div>
              </div>
            </router-link>
          </div>
        </list-item>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content">
            <router-link :to="{ path: '/resetpassword' }">
              <div class="item-title-row">
                <div class="item-title">修改密码</div>
                <div class="item-after"><div class="link-arrow icon icon-link"></div></div>
              </div>
            </router-link>
          </div>
        </list-item>
        <list-item :link="true">
          <div class="item-media">
            <img src="../../assets/images/home/form.png" width="30">
          </div>
          <div class="item-content">
            <router-link :to="{ name: '' }">
              <div class="item-title-row">
                <div class="item-title">退出登录</div>
                <div class="item-after"><div class="link-arrow icon icon-link"></div></div>
              </div>
            </router-link>
          </div>
        </list-item>
      </list>


    </page-content>
  </div>
</template>
<script>
import Grid from '../../../node_modules/vum/src/components/grid'
import { Header, HeaderLink, HeaderTitle } from '../../../node_modules/vum/src/components/header'
import Content from '../../../node_modules/vum/src/components/content'
import {List, ListItem} from '../../../node_modules/vum/src/components/list'

export default {
  components: {
    'page-header': Header,
    HeaderLink,
    HeaderTitle,
    List,
    ListItem,
    'page-content': Content,
    Grid
  },
  methods: {
    linkTo (url) {
      this.$emit('linkTo', url)
    }
  }
}
</script>
<style>
  .span-performance{
    margin-right: 10%;
  }
</style>
