<template>
  <div class="weui-panel weui-panel_access">
    <div class="weui-panel__hd" v-if="header" @click="onClickHeader" v-html="header">
      <slot name="header"></slot>
    </div>
    <div class="weui-panel__bd">
      <slot name="body">
        <!--type==='1'-->
        <template v-if="type === '1'">
          <div v-for="item in list">
            <div v-if="item.isHasUser">
              <a @click.prevent="onItemClick(item)" class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd" v-if="item.src">
                  <img class="weui-media-box__thumb" :src="item.src" alt="">
                </div>
                <div class="weui-media-box__bd" style="border-bottom: #b2b2b2 solid 1px">
                  <div v-if="item.isHasUser">
                    <h4 class="weui-media-box__title" v-html="item.title"></h4>
                    <p class="weui-media-box__desc" v-html="item.desc"></p>
                  </div>
                  <div v-else >

                  </div>
                </div>
              </a>
            </div>
            <div v-else>
              <div class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd" v-if="item.src">
                  <img class="weui-media-box__thumb" :src="item.src" alt="">
                </div>
                <div class="weui-media-box__bd" style="border-bottom: #b2b2b2 solid 1px">
                  <div v-if="item.isHasUser">
                    <h4 class="weui-media-box__title" v-html="item.title"></h4>
                    <p class="weui-media-box__desc" v-html="item.desc"></p>
                  </div>
                  <div v-else>
                    <div v-if="item.disableCreate">
                      <x-button mini  disabled type="warn">新增用户</x-button>
                    </div>
                    <div v-else>
                      <x-button type="warn" mini v-on:click.native="submitCreateUserEvent(item)">新增用户</x-button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </template>
        <!--type==='2'-->
        <template v-if="type === '2'">
          <div class="weui-media-box weui-media-box_text" v-for="item in list" @click.prevent="onItemClick(item)">
            <h4 class="weui-media-box__title" v-html="item.bankName"></h4>
            <span class="weui-media-box__desc" v-html="item.ownerName" style="float: left"></span>
            <a type="button" style="float: right" class="weui-btn weui-btn_mini weui-btn_default" @click.prevent="delCard(item.id)">删除</a>
            <span class="weui-media-box__desc" v-html="item.cardNumber" style="padding-left: 10px"></span>
          </div>
        </template>
        <template v-if="type === '6'">
          <group>
            <div class="page__bd page__bd_spacing" v-for="item in list" @click.prevent="onItemClick(item)">
              <div class="weui-flex">
                <div class="weui-flex__item">
                  <div class="placeholder" style="text-align: left">weui</div>
                </div>
              </div>
            </div>
          </group>
        </template>
        <!--type==='3'-->
        <template v-if="type === '3'">
          <div class="weui-media-box weui-media-box_small-appmsg">
            <div class="weui-cells">
              <a class="weui-cell weui-cell_access" :href="getUrl(item.url)" v-for="item in list"
                 @click.prevent="onItemClick(item)">
                <div class="weui-cell__hd">
                  <img :src="item.src" alt="" @error="onImgError(item, $event)"
                       style="width:20px;margin-right:5px;display:block">
                </div>
                <div class="weui-cell__bd">
                  <p v-html="item.title"></p>
                </div>
                <span class="weui-cell__ft"></span>
              </a>
            </div>
          </div>
        </template>
        <!--type==='4'-->
        <template v-if="type === '4'">
          <div class="weui-media-box weui-media-box_text" v-for="item in list" @click.prevent="onItemClick(item)">
            <h4 class="weui-media-box__title" v-html="item.title"></h4>
            <p class="weui-media-box__desc" v-html="item.desc"></p>
            <ul class="weui-media-box__info" v-if="item.meta">
              <li class="weui-media-box__info__meta" v-html="item.meta.source"></li>
              <li class="weui-media-box__info__meta" v-html="item.meta.date"></li>
              <li class="weui-media-box__info__meta weui-media-box__info__meta_extra" v-html="item.meta.other"></li>
            </ul>
          </div>
        </template>
        <!--type==='5'-->
        <template v-if="type === '5'">
          <div class="weui-media-box weui-media-box_text" v-for="item in list" @click.prevent="onItemClick(item)">
            <div class="weui-media-box_appmsg">
              <div class="weui-media-box__hd" v-if="item.src">
                <img class="weui-media-box__thumb" @error="onImgError(item, $event)" :src="item.src" alt="">
              </div>
              <div class="weui-media-box__bd">
                <h4 class="weui-media-box__title" v-html="item.title"></h4>
                <p class="weui-media-box__desc" v-html="item.desc"></p>
              </div>
            </div>
            <ul class="weui-media-box__info" v-if="item.meta">
              <li class="weui-media-box__info__meta" v-html="item.meta.source"></li>
              <li class="weui-media-box__info__meta" v-html="item.meta.date"></li>
              <li class="weui-media-box__info__meta weui-media-box__info__meta_extra" v-html="item.meta.other"></li>
            </ul>
          </div>
        </template>
      </slot>
    </div>
    <div class="weui-panel__ft">
      <a class="weui-cell weui-cell_access weui-cell_link" :href="getUrl(footer.url)" v-if="footer && type !== '3'"
         @click.prevent="onClickFooter">
        <div class="weui-cell__bd" v-html="footer.title"></div>
      </a>
    </div>
  </div>
</template>

<script>
  import { go, getUrl } from '../../node_modules/vux/src/libs/router'
  import { XButton, Divider } from 'vux'
  import Group from '../../node_modules/vux/src/components/group/index.vue'

  export default {
    components: {
      Group,
      XButton,
      Divider
    },
    name: 'panel',
    props: {
      header: String,
      footer: Object,
      list: Array,
      type: {
        type: String,
        default: '1'
      }
    },
    methods: {
      onImgError (item, $event) {
        this.$emit('on-img-error', JSON.parse(JSON.stringify(item)), $event)
        if (item.fallbackSrc) {
          $event.target.src = item.fallbackSrc
        }
      },
      getUrl (url) {
        return getUrl(url, this.$router)
      },
      onClickFooter () {
        this.$emit('on-click-footer')
        go(this.footer.url, this.$router)
      },
      onClickHeader () {
        this.$emit('on-click-header')
      },
      onItemClick (item) {
        this.$emit('on-click-item', item)
        go(item.url, this.$router)
      },
      submitCreateUserEvent (item) {
        this.$emit('createUserEvent', item)
      },
      delCard (id) {
        this.$emit('delCardEvent', id)
      }
    }
  }
</script>

<style lang="less">
  @import '../../node_modules/vux/src/styles/weui/widget/weui_cell/weui_cell_global';
  @import '../../node_modules/vux/src/styles/weui/widget/weui_cell/weui_access';
  @import '../../node_modules/vux/src/styles/weui/widget/weui_panel/weui_panel';
  @import '../../node_modules/vux/src/styles/weui/widget/weui_media_box/weui_media_box';

  a.disable-click {
    pointer-events: none;
  }

</style>
