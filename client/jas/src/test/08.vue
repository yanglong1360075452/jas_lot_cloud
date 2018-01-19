<template>
  <div class="main_box">
    <div>
       <tab :line-width=2 active-color='#fc378c' v-model="index">
        <tab-item class="vux-center" :selected="demo2 === item" v-for="(item, index) in list2" @click="demo2 = item" :key="index">{{item}}</tab-item>
      </tab>
      <swiper v-model="index" height="100px" :show-dots="false">
        <swiper-item v-for="(item, index) in list2" :key="index">
          <div class="tab-swiper vux-center">{{item}} Container</div>
        </swiper-item>
      </swiper>
    </div>
  </div>
</template>

<style lang="less" scoped>
  .main_box{
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    background: #fff;
  }
</style>

<script>
  import { Tab, TabItem, Sticky, Divider, Swiper, SwiperItem } from 'vux'
  const list = () => ['积分明细', '积分明细']
  export default {
    components: {
      Tab,
      TabItem,
      Sticky,
      Divider,
      Swiper,
      SwiperItem
    },
    data () {
      return {
        list2: list(),
        demo2: '术前',
        index: 0
      }
    },
    methods: {
    }
  }

</script>
