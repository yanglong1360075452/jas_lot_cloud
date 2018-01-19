<template>
  <div class="main_box">
    <div class="page-msgbox">
      <h1 class="page-title"></h1>
      <div class="page-msgbox-wrapper">
        <button @click="openAlert" size="large">积分规则</button>
        <button @click="openConfirm" size="large">是否兑换此优惠券</button>
        <button @click="openPrompt" size="large">编辑昵称</button>
      </div>
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
  import { MessageBox } from 'mint-ui';
  // 点击取消会报错
  export default {
    
    methods: {
      openAlert() {
        MessageBox({
          title: '积分规则',
          message: '<h1>一二三</h1><h1>一二三</h1><h1>一二三</h1>',
          confirmButtonText: '知道了',
        });
      },
      openConfirm() {
        MessageBox.confirm(' ', '是否兑换此优惠券').then(() => {
          alert('兑换了')
        });
      },
      openPrompt() {
        MessageBox.prompt(' ', '编辑昵称').then(({ value }) => {
          if (value) {
            MessageBox.alert(`你的名字是 ${ value }`, '输入成功');
          }
        });
      }
    }
  };



</script>
