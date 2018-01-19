<template>
  <div class="top_fixed no_content_bg_color ">
    <swiper loop auto :list="demo06_list" :index="demo06_index" @on-index-change="demo06_onIndexChange"></swiper>
  </div>
</template>

<script>
  import { Swiper } from 'vux'

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
        demo06_index: 0,
        demo06_list: urlList,
      }
    },
    components:{
      Swiper
    },
    methods:{
     demo06_onIndexChange (index) {
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
