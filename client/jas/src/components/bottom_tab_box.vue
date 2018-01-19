<template>
  <tabbar>
    <tabbar-item :class="{'weui-bar__item_on':on_index==0}" link="/app/main">
      <img slot="icon" :src="on_index==0 ? tab_home_sel : tab_home_nor ">
      <span slot="label">首页</span>
    </tabbar-item>
    <tabbar-item :class="{'weui-bar__item_on':on_index==1}" link="/app/mine">
      <img slot="icon" :src="on_index==1 ? tab_my_cur : tab_my_nor ">
      <span slot="label">我的</span>
    </tabbar-item>
  </tabbar>
</template>

<script>
  import tab_home_nor from '../assets/img/tab_home_sel@2x_65.png'
  import tab_home_sel from '../assets/img/tab_home_sel@2x.png'
  import tab_my_nor from '../assets/img/tab_my_nor@2x.png'
  import tab_my_cur from '../assets/img/tab_my_nor@2x_22.png'
  import { Tabbar, TabbarItem, Cell } from 'vux'
  export default {
    props: {
      on_index:[Number]
    },
    data () {
      return {
        'tab_home_nor':tab_home_nor,
        'tab_home_sel':tab_home_sel,
        'tab_my_nor':tab_my_nor,
        'tab_my_cur':tab_my_cur,
      }
    },
    components:{
      Tabbar,
      TabbarItem, Cell
    },
  }
</script>

<style lang="less">
  @import '../style/variable.less';
  .weui-tabbar{
    height: 0.98rem;
    position: fixed !important;
    .weui-tabbar__icon{
      width: 0.4rem;
      height: 0.4rem;
      margin-bottom: .05rem;
      margin-top: .08rem;
    }
    .weui-tabbar__item{
      margin-top: 0;
      padding-top: 0.1rem;
    }
    .weui-tabbar__label{
      margin-top: 0;
      padding-top: 0;
      line-height: 0.3rem;
      .fz(18px);
    }
  }
</style>
