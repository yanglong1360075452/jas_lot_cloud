<template>
  <div class="main_box">
    <actionsheet
      :actions="actions"
      v-model="sheetVisible">
    </actionsheet>
    <!-- https://elemefe.github.io/mint-ui/#/action-sheet -->
    <!-- https://mint-ui.github.io/docs/#/zh-cn2/action-sheet -->
    <xButton @click.native="sheetVisible = true" size="large">点击上拉 action sheet</xButton>
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
  import { Actionsheet, Button } from 'mint-ui';
  // 点击取消会报错
  export default {
    data() {
      return {
        sheetVisible: false,
        actions: []
      };
    },
    components: {
      'actionsheet' : Actionsheet ,
      'xButton' : Button

    },
    methods: {
      takePhoto() {
        console.log('taking photo');
      },
      openAlbum() {
        console.log('opening album');
      },
      goBack() {
        history.go(-1);
      }
    },
    mounted() {
      this.actions = [{
        name: '拍照',
        method: this.takePhoto
      }, {
        name: '从相册中选择',
        method: this.openAlbum
      }];
    }
  };



</script>
