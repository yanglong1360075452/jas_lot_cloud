<template>
  <div class="main_box">
    <div style='width:40px;height:40px;'>
      <span :style="{color: strokeColor2}">{{percent2}}%</span>
      <x-circle :percent="percent2" :stroke-width="20" :trail-width="20" :stroke-color="strokeColor2" trail-color="#ececec">
      </x-circle>
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

  .vux-circle-demo {
    text-align: center;
  }
  .vux-circle-demo > div {
    margin: 0 auto;
  }

</style>

<script>
  import { XCircle } from 'vux'
  export default {
    ready () {
      setInterval(this.update2, 2000)
    },
    components: {
      XCircle
    },
    data () {
      return {
        percent2: 20, // 进度
        strokeColor2: '#3FC7FA'
      }
    },
    methods: {
      update2 () {
        const colorMap = ['#3FC7FA', '#85D262', '#FE8C6A']
        this.percent2 = parseInt(Math.random() * 100, 10)
        this.strokeColor2 = colorMap[parseInt(Math.random() * 3, 10)]
      }
    }
  }

</script>
