<template>
  <div class="page_load">
    <!-- <div class="main_loading" v-show="loading" v-transfer-dom>
      <span style="vertical-align:middle;display:inline-block;font-size:14px;">加载中&nbsp;</span>
      <inline-loading></inline-loading>
    </div> -->

    <div v-transfer-dom>
      <loading class="root_loading" v-show="loading" :text="text1"></loading>
    </div>

    <router-view></router-view>

  </div>
</template>

<script>
  import {Loading,TransferDomDirective as TransferDom} from 'vux'
  import {mapState} from 'vuex'
  import { InlineLoading } from 'vux'

  export default {
    name: 'render',
    data(){
      return {
        show1: true,
        text1: '加载中...'
      }
    },
    methods: {
    },
    directives: {
      TransferDom
    },
    components: {Loading, InlineLoading},
    computed: mapState([
      // 映射 this.loading 为 store.state.loading
      'loading'
    ])

  }
</script>

<style lang="less">
  .main {
    height: 100%;
  }
  
  .root_loading{
    position: fixed;
    z-index: 99999 ;
  }
  
  // .main_loading{
  //   color: #ccc;
  //   text-align: center;
  //   width: 50%;
  //   margin: auto;
  //   position: absolute;
  //   position: fixed;
  //   top: 50%; left: 50%;
  //   z-index: 999;
  //   -webkit-transform: translate(-50%,-50%);
  //       -ms-transform: translate(-50%,-50%);
  //           transform: translate(-50%,-50%);
  // }

</style>
