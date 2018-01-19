<template>
  <div class="main_box">
    <p @click="show=!show">开始 {{v1}}</p>
    <p @click="show=!show">结束 {{v2}}</p>
    <picker v-show="show" :data='month' v-model='v1' @on-change='change'></picker>
    <picker v-show="!show" :data='month' v-model='v2' @on-change='change'></picker>

    <!-- <spinner type="snake"></spinner> -->

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
import { Cell, Group, Picker, GroupTitle, XButton } from 'vux'
import { Spinner } from 'mint-ui';

function getDaysInMonth(year,month){
  month = parseInt(month,10)+1;
  var temp = new Date(year+"/"+month+"/0");
  return temp.getDate();
}
getDaysInMonth(new Date().getFullYear(),new Date().getMonth)




let arr = []
for (var i = 1; i <= 31; i++) {
  arr.push({
    name: '10月' + i + '日 星期 x ',
    value: i + ''
  })
}

export default {
  name: 'components01',
  data (){
    return {
     month: [arr],
     v1: [''],
     v2: [''],
     show: true,
    }
  },
  created(){},
  components: {
    Picker,
    Spinner
  },
  methods:{
    change (value) {
      console.log('new Value', value)
    },
  },
  computed: {}
}



</script>
