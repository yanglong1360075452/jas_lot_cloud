<template>
  <div class="main_box">
    <div>
        <img :src="src">
        <vue-core-image-upload :class="['pure-button','pure-button-primary','js-btn-crop']"
           :crop="true"
           text="上传图片"
           url="api/test"
           extensions="png,gif,jpeg,jpg"
           @:imageuploaded="imageuploaded">
        </vue-core-image-upload>
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
// import Uploader from 'vux-uploader'
import VueCoreImageUpload from 'vue-core-image-upload'

export default {
  name: 'components01',
  data (){
    return {
      src:'http://www.qq.com/favicon.ico'
    }
  },
  created(){},
  components: {
    VueCoreImageUpload
  },
  methods:{
    imageuploaded(res) { // 定义上传完成执行的方法
      console.log(res)
    }
  },
  computed: {}
}



</script>
