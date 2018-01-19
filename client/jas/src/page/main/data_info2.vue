<template>
  <div class="page_data_info2 -e-login_bg top_fixed" v-wechat-title="$route.meta.title">
    
    <div class="top_switch"></div>
    <div class="table_box" style="background:none">
      <chart id="myChart" :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}" :options="chart_opts"><div class="msg">(无图表数据)</div></chart>  
    </div>
    <div class="log_box">
      <div class="item">
        <div class="ico_box">
          <div class="ico icon_time"></div>
        </div>
        <div class="info_box">

          <div class="td1">
            <div class="span">训练时长(分)</div>
            <div class="val">{{exercise_record_detail.trainingLength}}</div>
          </div>
          <div class="td2">
            <div class="l1">
              <div class="txt">开始时间 <span class="val">{{exercise_record_detail.startTime | date_format('hh:mm')}}</span></div>
            </div>
            <div class="l2">
              <div class="txt">结束时间 <span class="val">{{exercise_record_detail.endTime | date_format('hh:mm')}}</span></div>
            </div>
          </div>

        </div>
      </div>

      <div class="item">
        <div class="ico_box">
          <div class="ico icon_angle2"></div>
        </div>
        <div class="info_box">
          <div class="td1">
            <div class="span">角度变化(∠)</div>
            <div class="val">{{exercise_record_detail.angleChange}}°</div>
          </div>
          <div class="td2">
            <div class="l1">
              <div class="ico"></div>
            </div>
            <div class="l2">
              <div class="txt">开始角度 <span class="val">∠{{exercise_record_detail.startAngle}}°</span></div>
              <div class="txt">结束角度 <span class="val">∠{{exercise_record_detail.endAngle}}°</span></div>
            </div>
            
          </div>
        </div>
      </div>
      <div class="item">
        <div class="ico_box">
          <div class="ico icon_sr"></div>
        </div>
        <div class="info_box">
          <div class="td1">
            <div class="span">SR次数</div>
            <div class="val">{{exercise_record_detail.sr}}</div>
          </div>
          <div class="td2">
            <div class="l1">
              <div class="txt">平均时间 <span class="val">{{exercise_record_detail.averageTime}}m</span></div>
            </div>
            <div class="l2">
              <div class="txt">最长时间 <span class="val">{{exercise_record_detail.maximumTime}}m</span></div>
              <div class="txt">最短时间 <span class="val">{{exercise_record_detail.minimumTime}}m</span></div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
  import api_login_reg from '../../api/login_reg.js'
  import {to_echa_data_line, to_echa_data_lines, date_format} from '../../assets/js/common.js'
  import api_main from '../../api/main.js'
  import { XCircle } from 'vux'
  export default {
    name: 'index',
    data (){
      return {
        percent1: 20, // 圆圈百度比
        chart_opts:{}, // 图表数据
        exercise_record_detail:'', // 详情数据
      }
    },
    created(){
      var vm=this;
      var item_data=this.$route.params.item_data_info;

      api_main.get_exercise_record_detail({erId:1}).then( res => { // 通过康复记录Id获取单次训练记录明细
        console.log("通过康复记录Id获取单次训练记录明细_data_info2", res);
        vm.exercise_record_detail=res;
        if(vm.exercise_record_detail.length<=0) {
          console.log('无图表数据');
          return
        }else{
          vm.$nextTick(function(){
            var table_data=to_echa_data_lines([to_echa_data_line(vm.exercise_record_detail.jsonArray)],['#d2defc'],['']);
            vm.drawLine(table_data);
          })
        };

      },res=>{
        console.log('res', res)
      })



    },
    components:{
      XCircle
    },
    filters:{
      date_format(val, from){
        return date_format(val, from)
      }
    },
    mounted(){
    },
    methods:{

      drawLine(_table_data) {
        var _table_data= _table_data ? _table_data : {};
        var y_min=Math.min.apply(null, _table_data.y_min);
        _table_data.series[0].areaStyle={
          normal: {
            color: {
              type: 'linear',
              x: 0,
              y: 0,
              x2: 0,
              y2: 1,
              colorStops: [{
                offset: 0, color: 'rgba(255,255,255,.3)'
              }, {
                offset: 0.5, color: 'rgba(255,255,255,.2)'
              }, {
                offset: 1, color: 'rgba(255,255,255,0)'
              }]
            }
          }
        };
        // let myChart = echarts.init(document.getElementById('myChart'))
        this.chart_opts = {
          // animation:false,
          xAxis: {
            // min: 0,
            // max: 35,
            splitNumber:7,
            type: 'value',

            splitLine: {
              lineStyle: {
                color: ['#628cf2']
              }
            },
            axisLine:{
              show:false,
              lineStyle:{
                color:'#dfe8ff'
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
                color:'#dfe8ff',
              }
            },
            splitLine: {show: false}
          },
          series: _table_data.series
        }
        // myChart.setOption(this.chart_opts);
      },
    }
  }
</script>

<style lang="less">
  @import '../../style/common.less';
  @import '../../style/variable.less';
  .page_data_info2{
    .log_box{
      background: #fff;
      .item{
        padding-left: 0.3rem;
        padding-right: 0.3rem;
        height: 2.2rem;
        .ico_box{
          float: left;
          width: 18%;
          height: 100%;
          position: relative;
          .ico{
            position: absolute;
            left: 0;
            bottom: 0;
            top: 0;
            margin: auto;
            width: 0.4rem;
            height: 0.4rem;
            // background-color: #ccc;
            background-repeat: no-repeat;
            background-size: contain;
            border-radius:50%;

          }
        }
        .info_box{
          float: left;
          width: 82%;
          border-bottom: 1px solid #dfe8ff;
          padding: 0.48rem 0;
          padding-bottom: 0.4rem;
          .fz(24px);
          .td1{
            width: 25%;
            float: left;
            white-space: nowrap;
            .span{
              color: #a7b5cb;
            }
            .val{
              .fz(60px);
              padding-top: 0.2rem;
            }
          }
          .td2{
            width: 75%;
            float: left;
            text-align: right;
            color: #a7b5cb;
            .l1{
              height: 1rem;
              position: relative;
              .ico{
                position: absolute;
                right: 0;
                top: 0.15rem;
                .circle{
                  width: 0.8rem;
                }
              }
              .txt{
                display: inline;
              }
              .val{
                color: #253e67;
                .fz(28px);
              }
            }
            .l2{
              .txt{
                display: inline;
              }
              .val{
                color: #253e67;
                .fz(28px);
              }
            }
          }
        }
      }
    }
    .table_box{
      height: 4.79rem;
      width: 7.5rem;
      background: url(../../assets/img/record@2x.png);
      background-size: cover;
      background-repeat: no-repeat;
      margin: auto;
    }
    .top_switch{
      height: .6rem;
      margin-left: 0.3rem;
      margin-right: 0.3rem;
      width: 6.8rem;
      margin: auto;
    }
    &:before{
      position: fixed;
      width: 100%;
      height: 100%;
      top: 5.4rem;
      left: 0;
      overflow: scroll;
      z-index: 999;
      content: '';
      background: #fff;
      background-repeat: no-repeat;
      background-position: bottom;
      background-size: 100%;
      z-index: -1;
    }
  }

</style>
