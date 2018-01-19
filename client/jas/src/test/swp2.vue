<template>
  <div class="top_fixed no_content_bg_color ">
    <h3 @click="swiperItemIndex=3">设置为 3</h3>
    <swiper loop 
      :list="demo06_list" 
      :index="demo06_index" 
      :show-dots="false"
      v-model="swiperItemIndex"
      @on-index-change="demo06_onIndexChange">
      <h3>内容</h3>
    </swiper>

    <swiper v-model="swiperItemIndex" :interval=2000 class="text-scroll" :show-dots="false">
      <swiper-item><p>义务爱了 完成传奇世界H5-王者归来任务 获得20金币</p></swiper-item>
      <swiper-item><p>基本世神 兑换《传奇世界H5》畅玩级礼包 消耗30金币</p></swiper-item>
      <swiper-item><p>零哥章魚 完成传奇世界H5-王者归来任务 获得30金币</p></swiper-item>
      <swiper-item><p>做迎而為 兑换【饿了么】畅享美食红包 消耗20金币</p></swiper-item>
      <swiper-item><p>只知道不知道 兑换【饿了么】畅享美食红包 消耗20金币</p></swiper-item>
      <swiper-item><p>基本世神 兑换《传奇世界H5》畅玩级礼包 消耗30金币</p></swiper-item>
    </swiper>

  </div>
</template>

<script>
  import { Swiper,SwiperItem } from 'vux'

  const baseList = [{
    url: 'javascript:',
    img: 'https://static.vux.li/demo/1.jpg',
    title: '送你一朵fua'
  }, {
    url: 'javascript:',
    img: 'https://static.vux.li/demo/2.jpg',
    title: '送你一辆车'
  }, {
    url: 'javascript:',
    img: 'https://static.vux.li/demo/3.jpg',
    title: '送你一次旅行'
  }]
  
  export default {
    data() {
      return {
        swiperItemIndex: 1,
        demo06_index: 0,
        demo06_list: urlList,
      }
    },
    components:{
      Swiper,SwiperItem
    },
    methods:{
     demo06_onIndexChange (index) {
      console.log(this.demo06_index);
       this.demo06_index = index
     }, 
    }
  }

  const urlList = baseList.map((item, index) => ({
    url: 'http://m.baidu.com',
    img: item.img,
    title: `(可点击)${item.title}`
  }))

</script>

<style lang="less" scoped>

</style>
