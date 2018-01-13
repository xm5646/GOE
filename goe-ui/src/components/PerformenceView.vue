<template>
  <div class="page">
    <content>
      <list>
        <list-item>
          <div class="item-content">
            <form-list>
              <div class="row">
                <div class="col-80">
                  <form-item>
                    <input type="text" placeholder="请输入要查看的账号编号" v-model="checkUserAccount"/>
                  </form-item>
                </div>
                <div class="col-20">
                  <form-item>
                    <m-button size="large" @click.native="chencUser">查找</m-button>
                  </form-item>
                </div>
              </div>
            </form-list>

          </div>
        </list-item>
        <list-item>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title"> 当前查看用户：{{ ViewUser.account
                }}  <span>(A:{{ ViewUser.performanceA}} &nbsp;B:{{ ViewUser.performanceB}} &nbsp;C:{{ ViewUser.performanceA}})</span>
              </div>
            </div>
          </div>
        </list-item>
        <list-item :link="true" @click.native="ViewUserA">
          <div class="item-media"><img src="../assets/images/icon-list.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title"> {{ ViewUser.departUserA.account }}</div>
            </div>
            <div class="item-subtitle">
              <span class="performence-span">A:{{ ViewUser.departUserA.performanceA }}</span>&nbsp;&nbsp;
              <span class="performence-span">B:{{ ViewUser.departUserA.performanceB }}</span>&nbsp;&nbsp;
              <span class="performence-span">C:{{ ViewUser.departUserA.performanceC }}</span>&nbsp;&nbsp;
            </div>
          </div>
        </list-item>
        <list-item :link="true" @click.native="ViewUserB">
          <div class="item-media"><img src="../assets/images/icon-list.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">{{ ViewUser.departUserB.account }}</div>
            </div>
            <div class="item-subtitle">
              <span class="performence-span">A:{{ ViewUser.departUserB.performanceA }}</span>&nbsp;&nbsp;
              <span class="performence-span">B:{{ ViewUser.departUserB.performanceB }}</span>&nbsp;&nbsp;
              <span class="performence-span">C:{{ ViewUser.departUserB.performanceC }}</span>&nbsp;&nbsp;
            </div>
          </div>
        </list-item>
        <list-item :link="true" @click.native="ViewUserC">
          <div class="item-media"><img src="../assets/images/icon-list.png" width="44"></div>
          <div class="item-content">
            <div class="item-title-row">
              <div class="item-title">{{ ViewUser.departUserC.account }}</div>
            </div>
            <div class="item-subtitle">
              <span class="performence-span">A:{{ ViewUser.departUserC.performanceA }}</span>&nbsp;&nbsp;
              <span class="performence-span">B:{{ ViewUser.departUserC.performanceB }}</span>&nbsp;&nbsp;
              <span class="performence-span">C:{{ ViewUser.departUserC.performanceC }}</span>&nbsp;&nbsp;

            </div>
          </div>
        </list-item>
      </list>

      <m-button type="warning" size="warning" @click.native="goBackMyPer">返回我的业绩</m-button>
    </content>
  </div>

</template>

<script>
  //  import vum components
  import { SimpleHeader } from '../../node_modules/vum/src/components/header'
  import Content from '../../node_modules/vum/src/components/content'
  import { Button } from '../../node_modules/vum/src/components/buttons'
  import { Form, FormItem } from '../../node_modules/vum/src/components/form'
  import { Footer, Item, FooterItem } from '../../node_modules/vum/src/components/footer'
  import Page from '../../node_modules/vum/src/components/page'
  import { List, ListItem } from '../../node_modules/vum/src/components/list'

  console.log(FooterItem)
  export default {
    props: ['user'],
    components: {
      Page,
      Content,
      List,
      ListItem,
      SimpleHeader,
      'form-list': Form,
      FormItem,
      'm-button': Button,
      'page-footer': Footer,
      'footer-item': Item
    },
    data () {
      return {
        ViewUser: this.user,
        checkUserAccount: ''
      }
    },
    methods: {
      ViewUserA () {
        this.$emit('viewUsereEvent', this.ViewUser.departUserA.account)
      },
      ViewUserB () {
        this.$emit('viewUsereEvent', this.ViewUser.departUserB.account)
      },
      ViewUserC () {
        this.$emit('viewUsereEvent', this.ViewUser.departUserC.account)
      },
      chencUser () {
        if (this.checkUserAccount !== '') {
          this.$emit('viewUsereEvent', this.checkUserAccount)
        } else {
        }
      },
      goBackMyPer () {
        this.$emit('backMyUser')
      }
    }
  }
</script>

<style lang="less">
  .row {
    margin: 0px;
  }

  .list form-list {
    margin: 0px;
  }
</style>
