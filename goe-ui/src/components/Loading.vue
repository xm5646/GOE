<template>
  <div>
    <overlay :show="LoadingStatus" :transparent="true"></overlay>
    <transition name="preloader-modal">
      <div class="preloader-modal" v-if="LoadingStatus">
        <span class="preloader preloader-white"></span>
      </div>
    </transition>
  </div>
</template>

<script>
  import PageLoader from '../../node_modules/vum/src/components/preloader'
  import Overlay from '../../node_modules/vum/src/components/overlay'

  export default {
    props: ['LoadingStatus'],
    data () {
      return {
      }
    },
    components: {
      PageLoader,
      Overlay
    }
  }
</script>

