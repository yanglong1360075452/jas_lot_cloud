<template>
  <div class="main_box">
    <chart id="myChart" :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}" :options="chart_opts"></chart>  
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
    // var echarts = require('echarts/lib/echarts');
    // require('echarts/lib/chart/line');
    import {to_echa_data_line, to_echa_data_lines} from '../assets/js/common.js'

    export default {

      name: 'hello',
      data() {
        return {
          chart_opts:{}, 
          msg: 'Welcome to Your Vue.js App'
        }
      },
      mounted() {
        var vm=this;
        var d1=[
                {"angle": 50, "event": "", "level": 1, "timeDesc": "2017-08-31 11:00:00"},
                {"angle": 54, "event": "", "level": 1, "timeDesc": "2017-08-31 11:05:34"},
                {"angle": 60, "event": "", "level": 1, "timeDesc": "2017-08-31 11:08:34"},
                {"angle": 68, "event": "", "level": 1, "timeDesc": "2017-08-31 11:15:34"}, 
                {"angle": 63, "event": "", "level": 2, "timeDesc": "2017-08-31 11:30:34"}, 
                {"angle": 89, "event": "", "level": 2, "timeDesc": "2017-08-31 11:33:34"}, 
                {"angle": 95, "event": "pause", "level": 2, "timeDesc": "2017-08-31 11:43:34"}, 
                {"angle": 105, "event": "", "level": 2, "timeDesc": "2017-08-31 11:50:34"}, 
                {"angle": 0, "event": "end", "level": 0, "timeDesc": "2017-08-31 11:55:00"} 
               ];

        var d2=[
                {"angle": 11, "event": "", "level": 1, "timeDesc": "2017-08-31 11:00:00"},
                {"angle": 11, "event": "", "level": 1, "timeDesc": "2017-08-31 11:05:34"},
                {"angle": 15, "event": "", "level": 1, "timeDesc": "2017-08-31 11:08:34"},
                {"angle": 11, "event": "", "level": 1, "timeDesc": "2017-08-31 11:15:34"}, 
                {"angle": 38, "event": "", "level": 2, "timeDesc": "2017-08-31 11:30:34"}, 
                {"angle": 39, "event": "", "level": 2, "timeDesc": "2017-08-31 11:33:34"}, 
                {"angle": 99, "event": "pause", "level": 2, "timeDesc": "2017-08-31 11:43:34"}, 
                {"angle": 105, "event": "", "level": 2, "timeDesc": "2017-08-31 11:50:34"}, 
                {"angle": 0, "event": "end", "level": 0, "timeDesc": "2017-08-31 11:55:00"} 
               ];


        console.log('d1', d1)

        var table_data=to_echa_data_lines([to_echa_data_line(d1), to_echa_data_line(d2)],['#f00', '#0f0']);
        vm.drawLine(table_data);
      },
      methods: {
        drawLine: function(_table_data) {
          var _table_data= _table_data ? _table_data : {};
          console.log('_table_data', _table_data)
          var y_min=Math.min.apply(null, _table_data.y_min);;
          this.chart_opts = {
            animation:false,
            xAxis: {
              // min: 0,
              // max: 35,
              splitNumber:7,
              type: 'value',

              splitLine: {
                lineStyle: {
                  color: ['#e8e8e8']
                }
              },
              axisLine:{
                show:false,
                lineStyle:{
                  color:'#a7b5cb'
                },
              }
            },
            grid: {
                top: 26,
                bottom:30,
                // left: 30,
                // right: 30,
                left: 50,
                right: 30,
                // height: 0
            },
            yAxis: {
              min: y_min,
              // max: 180,
              splitNumber:5,
              type: 'value',
              axisTick:{
                show:false
              },
              axisLine:{
                show:false,
                lineStyle:{
                  opacity:0,
                  color:'#a7b5cb',
                }
              },
              splitLine: {show: false}
            },
            series: _table_data.series
          }
        },
        
      }
      
    }
</script>
