<template>
  <div class="page">
    <page-header>
      <simple-header style="background-color: orange" :back-link="true">GOE会员管理系统</simple-header>
    </page-header>
    <page-content>
        slllllsssssllls

    </page-content>
  </div>
</template>
<script>
import { Header, HeaderLink, SimpleHeader } from '../../components/header'
import Content from '../../components/content'
import {List, ListItem} from '../../components/list'

export default {
  components: {
    'page-header': Header,
    HeaderLink,
    SimpleHeader,
    List,
    ListItem,
    'page-content': Content
  }
}
</script>

