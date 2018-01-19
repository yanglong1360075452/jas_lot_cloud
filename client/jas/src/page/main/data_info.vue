<template>
  <div class="page_data_info -e-login_bg top_fixed" v-wechat-title="$route.meta.title">
    
    <div class="top_switch">
      <div class="show_data_item">
        <div v-if="plan_id_to_user_info.responseEntity" v-wx_img="{'base64':plan_id_to_user_info.responseEntity}" class="head_img icon_head"></div>
        <div class="week_month">
          <span class="week" :class="{'week_month_cur':week_month===0}" @click="week_month_fn(0)">周</span>
          <span class="month" :class="{'week_month_cur':week_month===1}" @click="week_month_fn(1)">月</span>
        </div>
        <i class="switch_table icon_compare" @click="change_chart()"></i>
      </div>
    </div>
    <div class="date_switch">
      <div class="prev" @click="precChart()"></div>
      <div class="data_info">{{showTime}}</div>
      <div class="next" @click="nextChart()"></div>
    </div>
    <!-- <div :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}" class="cylinderChart"></div> -->
    <div v-show="!switch_chart">
      <chart v-show="!no_table_data" class="cylinderChart" :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}" :options="chart_opts2"></chart>
      <div v-show="no_table_data" class="cylinderChart null" :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}"><span>(无训练数据)</span></div>
    </div>
    <div v-show="switch_chart" class="table_line">
      <chart v-show="!no_table_data" class="cylinderChart" :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}" :options="chart_opts_line"></chart>
      <div v-show="no_table_data" class="cylinderChart null" :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}"><span>(无训练数据)</span></div>
    </div>
    <div class="log_box">
      <div class="log_tab">
        <tab :animate="false" v-model="show_log">
          <tab-item :selected="show_log==0" @on-item-click="switch_log_fn">训练记录</tab-item>
          <tab-item :selected="show_log==1" @on-item-click="switch_log_fn">评估记录</tab-item>
        </tab>
      </div>
      <div class="log_det">
        <div v-if="show_log===0" class="train_log">
          <div class="log_item" v-for="item in exercise_log" @click="to_data_info2_fn(item)">
            <div class="ico_box"><i class="ico icon_angle"></i></div>
            <div class="txt_box">
              <div class="time">{{item.createTime | date_format('MM月DD日')}}</div>
              <div class="info">
                <div class="item_li">{{item.trainingLengthDesc}}</div>
                <div class="item_li">角度变化 ∠{{item.angleChange}}°</div>
                <div class="item_li">SR次数 {{item.sr}}</div>
              </div>
            </div>
          </div>
        </div>
        <div v-if="show_log===1" class="ass_log">
          <div class="edit_btn_float" v-show="show_edit_btn_float==='1' && !(doctor_see_user && doctor_see_user.data.dis_xw==true)" @click="to_edit_comment_fn"></div>
          <div class="save_ass_log_item_height">两<br>行<!-- 保存一个高度供对比 --></div>
          <div class="log_item" v-for="(item, index) in comment">
            <div class="time">时间段 {{item.startTime | date_format('MM月DD日')}}-{{item.endTime | date_format('MM月DD日')}}</div>
            <div class="state_box"><i class="ico" :class="[[,'icon_keep', 'icon_gaisahan', 'icon_worsen', 'icon_tuoluo'][item.verdict]]"></i><span class="state">{{item.verdict | code2name(dict_appraisal)}}</span></div>
            <div class="des">
              <span>{{item.content}}</span>
              <span class="show_more" @click="show_more_fn(index)">...展开</span>
            </div>
          </div>
          <div class="data-none" v-if="show_edit_btn_float==='1' && comment.length == 0">
            <div class="none-img"></div>
            <p class="result">无评估报告</p>
            <p class="title" v-show="!(doctor_see_user && doctor_see_user.data.dis_xw==true)">点右下角"增加"评估报告</p>
            <p class="add-click" @click="to_edit_comment_fn" v-show="!(doctor_see_user && doctor_see_user.data.dis_xw==true)">或点此去写评估</p>
          </div>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script>
  import api_main from '../../api/main.js'
  import { Tab, TabItem } from 'vux'
  import {code2name, code2ico, date_format, to_echa_data_lines, to_echa_data_line} from '../../assets/js/common.js'
  // var echarts = require('echarts/lib/echarts');
  // require('echarts/lib/chart/bar');
  var moment = require('moment'); 
  export default {
    data (){
      return {
        no_table_data:true, // 无图表数据
        plan_id_to_user_info:'', // 通过计划查询病人详情
        doctor_see_user:'', // 从医生角度得到的用户数据

        chart_opts_line:{}, // 叠加图数据
        switch_chart:false, // 切换图表

        show_edit_btn_float:'', // 患者不显示编辑按钮
        week_month:0, // 周月切换
        showTime: '', //时间显示
        startTime: '',//开始时间
        endTime: '', //结束时间
        show_log:0, // 记录切换
        comment:'', // 评估记录
        dict_appraisal:'', // 评估结论列表
        epId: '',  //康复计划Id
        exercise_data: [],   //锻炼记录数据
        barWidth:'25%', //柱形宽度
        exercise_log: [], //训练记录
        chart_opts2: {},
        chart_opts3: {},
        showChart: true, //图标切换
      }
    },
    created(){

      /*同步本地获取数据*/
      var store_arr=[
        'dict_appraisal',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      this.show_edit_btn_float=this.$store.state.user_type;
      this.doctor_see_user=this.$store.state.temp_data.plan_info;

      api_main.get_doctor_see_user(this.doctor_see_user.data.patientId).then(res=>{ // 医生通过病人id查询病人信息
        console.log('医生通过病人id查询病人信息', res);
        this.plan_id_to_user_info=res;
      },res=>{
        console.log(res)
      })


      if(this.$route.params.plan_id){
        this.epId=this.$route.params.plan_id;

        var vm=this;
        api_main.get_index_table(vm.epId).then( res => { // 获取计划的第一条训练数据
          vm.res_data=res;
          console.log("获取计划的第一条训练数据", res , res.length );
          if(vm.res_data.length>0) {
            this.no_table_data=false;
            vm.endTime=vm.res_data[0].endTime;
            vm.startTime = moment(vm.endTime).subtract(1,"weeks").add(1,"days").valueOf();
            vm.get_exercise_record(vm.epId,vm.startTime,vm.endTime);
            vm.get_exercise_list(vm.epId,vm.startTime,vm.endTime);
            vm.to_table_fn(vm.epId, vm.startTime, vm.endTime);
          }else{
            console.log('无训练数据');
            return
          };
        },res=>{
          console.log('res', res)
        })

      }

      api_main.get_comment(this.$store.state.temp_data.plan_info.data.id).then(res=>{
        this.comment=res.reverse();
        console.log(res);

        // 显示评估
        if(this.$route.params.new_comment) this.switch_log_fn(1);

      },res=>{console.log(res)})


      

      // 显示评估
      if(this.$route.params.new_comment) this.show_log=1;
      
    },
    components:{
       Tab, TabItem 
    },
    filters:{
      code2name(code, arr){
        return code2name(code, arr);
      },
      date_format(val, format){
        return date_format(val, format);
      }
      
    },
    mounted(){
      // this.drawChart();
    },
    methods:{
      to_table_fn(epId, startTime, endTime){
        // 创建叠加图
        // var query_obj={'epId':1, 'startTime':1502503438313, 'endTime':1504503438313 };
        var query_obj={'epId':epId, 'startTime':startTime, 'endTime':endTime };
        let _arr=[]; /*取出图表数据*/
        api_main.get_every_data(query_obj).then(res=>{ // 获取指定时间所有单次图表数据
          console.log('获取指定时间所有单次图表数据', res)
          
          for(var index=0; index<res.length; index++){
            _arr.push(to_echa_data_line(res[index].jsonArray));
          }

          this.init_table_line(_arr);
        },res=>{console.log(res)})
      },

      init_table_line(table_arr){
        var vm=this;

        var color_arr=[];
        var table_data=to_echa_data_lines(table_arr, color_arr, {'line_width':1});

        drawLine(table_data);

        function drawLine(_table_data) {
          var _table_data= _table_data ? _table_data : {};
          console.log('_table_data', _table_data)
          var y_min=Math.min.apply(null, _table_data.y_min);;
          vm.chart_opts_line = {
            // animation:false,
            xAxis: {
              // min: 0,
              // max: 35,
              splitNumber:7,
              type: 'value',

              splitLine: {
                lineStyle: {
                  color: ['#dfe8ff']
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
        };

      },
      get_exercise_record(epId, startTime, endTime){
        api_main.get_exercise_week_month(epId,startTime,endTime).then(res=>{
          res.reverse();
          this.exercise_data = res;
          this.drawChart();
        });
      },
      get_exercise_list(epId, startTime, endTime) {
        api_main.get_list_week_month(epId, startTime, endTime).then(res => {
          this.exercise_log = res;
        }, res => {
          console.log(res);
        });
      },
      drawChart() {
        // let cylinderChart = echarts.init(document.getElementById('cylinderChart'));

        // 去除重复数据
        Array.prototype.method1 = function(){  
            var arr = [];
            for(var i = 0; i < this.length; i++){
                if(arr.indexOf(this[i]) == -1){  
                    arr.push(this[i]);  
                }  
            }  
            return arr;  
        } 
        // 获取数组数组中的最大值
        function getMaxFunc(arr) {
          return Math.max.apply(null, arr);
        }
        // 获取数组数组中的最小值
        function getMinFunc(arr) {
          return Math.min.apply(null, arr);
        }

        var time = [];
        var startAngle = [];
        var endAngle = [];
        var resultAngle = [];
        this.exercise_data.forEach(function(element) {
          time.push(element.time.substring(5,7)+"."+element.time.substring(8,10));
          startAngle.push(element.startAngle);
          endAngle.push(element.endAngle);
          resultAngle.push(element.endAngle-element.startAngle);  
        });
        
        //x轴坐标
        var xAxis = time.method1();
        var angle = startAngle.concat(endAngle);
        var yMin = getMinFunc(angle);
        var yMax = getMaxFunc(angle);
        this.showTime = moment(this.startTime).format("MM.DD") + '-' + moment(this.endTime).format("MM.DD");
        

        // 柱状图添加数据
        this.chart_opts2={
            grid: {
              top: 20,
              bottom: 40
            },
            xAxis: {
                type : 'category',
                splitLine: {show:false},
                axisTick: {
                  interval:0
                },
                axisLine: {
                  lineStyle: {
                    color: '#fff'
                  }
                },
                data : xAxis
            },
            yAxis: {
                type : 'value',
                min: yMin,
                axisLine: {
                  lineStyle: {
                    color: '#fff'
                  }
                },
                splitLine: {show:false},
            },
            series: [
                {
                    type: 'bar',
                    barWidth: this.barWidth,
                    stack: '第一天',
                    itemStyle: {
                        normal: {
                            barBorderColor: 'rgba(0,0,0,0)',
                            color: 'rgba(0,0,0,0)',
                            barBorderRadius: 5
                        }
                    },
                    data: startAngle
                },
                {
                    type: 'bar',
                    barWidth: this.barWidth,
                    stack: '第一天',
                    itemStyle: {
                      normal: {
                          barBorderColor: 'rgba(0,0,0,0)',
                          color: '#fff',
                          barBorderRadius: 5
                      }
                    },
                    data: resultAngle
                },
                // {
                //     type: 'bar',
                //     barWidth: '30%',
                //     stack: "第二天",
                //     itemStyle: {
                //         normal: {
                //             barBorderColor: 'rgba(0,0,0,0)',
                //             color: 'rgba(0,0,0,0)',
                //             barBorderRadius: 5
                //         },
                //         emphasis: {
                //             barBorderColor: 'rgba(0,0,0,0)',
                //             color: '#f00',
                //             barBorderRadius: 5
                //         }
                //     },
                //     data: [10,10,12,18,20,22,28]
                // },
                // {
                //     type: 'bar',
                //     barWidth: '30%',
                //     stack: "第二天",
                //     itemStyle: {
                //         normal: {
                //             barBorderColor: 'rgba(0,0,0,0)',
                //             color: '#f00',
                //             barBorderRadius: 5
                //         }
                //     },
                //     data: [10,13,18,22,30,28,22]
                // }              
            ]
        }
        this.chart_opts3 = {
          grid: {
              top: 20,
              bottom: 40
            },
            xAxis: {
                type : 'category',
                splitLine: {show:false},
                axisTick: {
                  interval:0
                },
                axisLine: {
                  lineStyle: {
                    color: '#fff'
                  }
                },
                data : xAxis
            },
            yAxis: {
                type : 'value',
                min: yMin,
                axisLine: {
                  lineStyle: {
                    color: '#fff'
                  }
                },
                splitLine: {show:false},
            },
          series: [
            {
              type: 'line',
              areaStyle: {
                normal: {
                  color: {
                    type: 'linear',
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [{
                      offset: 0, color: 'rgba(79,127,242,.5)'
                    }, {
                      offset: 0.5, color: 'rgba(79,127,242,.2)'
                    }, {
                      offset: 1, color: 'rgba(79,127,242,0)'
                    }]
                  }
                }
              },
              // data: resultAngle
            }
          ]
        }
        //折线图添加数据
        
        // cylinderChart.setOption();
      },
      precChart() {
        if(this.week_month == 1) {
          this.endTime = moment(this.startTime).subtract(1,"days").valueOf();
          this.startTime = moment(this.endTime).subtract(1,"months").add(1,"days").valueOf();
          this.get_exercise_record(this.epId, this.startTime,this.endTime);
          this.get_exercise_list(this.epId, this.startTime, this.endTime);
          this.to_table_fn(this.epId, this.startTime, this.endTime);

        }else {
          this.endTime = moment(this.startTime).subtract(1,"days").valueOf();
          this.startTime = moment(this.endTime).subtract(1,"weeks").add(1,"days").valueOf();
          this.get_exercise_record(this.epId, this.startTime,this.endTime);
          this.get_exercise_list(this.epId, this.startTime, this.endTime);
          this.to_table_fn(this.epId, this.startTime, this.endTime);
        }
      },
      nextChart() {
        if(this.week_month == 1) {
          this.startTime = moment(this.endTime).add(1,"days").valueOf();
          this.endTime = moment(this.startTime).add(1,"months").subtract(1,"days").valueOf();
          this.get_exercise_record(this.epId, this.startTime,this.endTime);
          this.get_exercise_list(this.epId, this.startTime, this.endTime)
          this.to_table_fn(this.epId, this.startTime, this.endTime);
        }else {
          this.startTime = moment(this.endTime).add(1,"days").valueOf();
          this.endTime = moment(this.startTime).add(1,"weeks").subtract(1,"days").valueOf();
          this.get_exercise_record(this.epId, this.startTime,this.endTime);
          this.get_exercise_list(this.epId, this.startTime, this.endTime)
          this.to_table_fn(this.epId, this.startTime, this.endTime);
        }
      },
      to_data_info2_fn(item){
        this.$router.push({name:'data_info2', params:{'item_data_info':item}});
      },
      to_edit_comment_fn(){ /*编辑评估记录*/
        this.$router.push({name:'edit_comment'});
      },
      show_more_fn(_index){
        $j('.page_data_info .log_box .log_det .ass_log .log_item .des').eq(_index).removeClass('min').height('auto');
      },
      week_month_fn(index){ 

        this.week_month = index;
        if(index == 1) {
          this.barWidth = '25%';
          this.startTime = moment(this.endTime).subtract(1,"months").add(1,"days").valueOf();
          this.get_exercise_record('1',this.startTime,this.endTime);
          this.get_exercise_list(this.epId, this.startTime, this.endTime);
          this.to_table_fn(this.epId, this.startTime, this.endTime);
        }else if(index == 0) {
          this.barWidth = '25%';
          this.startTime = moment(this.endTime).subtract(1,"weeks").add(1,"days").valueOf();
          this.get_exercise_record('1',this.startTime,this.endTime);
          this.get_exercise_list(this.epId, this.startTime, this.endTime);
          this.to_table_fn(this.epId, this.startTime, this.endTime);
          
        }
      },
      switch_log_fn (index) {
        this.show_log=index;
        
        this.$nextTick(function () {
          var dom=$j('.page_data_info .log_box .log_det .ass_log .log_item .des');
          var save_height=$j('.save_ass_log_item_height').height();
          dom.each(function(i,j){
            console.log($j(j).height(), save_height)
            if($j(j).height()>save_height){
              $j(j).height(save_height);
              $j(j).addClass('min');
            }
          })
        })

        this.get_exercise_list(this.epId, this.startTime, this.endTime);
        console.log('on item click:', index)
      },
      change_chart() {
        this.switch_chart=!this.switch_chart;
        console.log('图表切换');
      }
      
    }
  }
</script>

<style lang="less">
  @import '../../style/common.less';
  @import '../../style/variable.less';
  .page_data_info{
    .table_line{
      background: @no_content_bg_color;
    }
    .log_box{
      background: #fff;
      .log_tab{
        height: 1.0rem;
        border-bottom: 1px solid @bottom_line_color;
        .vux-tab-item{
          height: 1.0rem;
          line-height: 1.0rem;
          border: none;
          border-width: 0 !important;
          background: none;
          color: @accept_color;
          .fz(28px);
        }
        .vux-tab-selected{
          border: none;
          border-width: 0 !important;
          background: none;
          position: relative;
          color: @theme_color;
          &:after{
            position: absolute;
            content:'';
            background: @theme_color;
            border-radius:0.05rem;
            width: 0.3rem;
            height: 0.05rem;
            left: 0;
            bottom: 0;
            right: 0;
            margin: auto;
          }
        }
      }
      .log_det{
        width: 7.5rem;
        margin: auto;
        .train_log{
          .log_item{
            height: 1.59rem;
            .ico_box{
              float: left;
              width: 1.3rem;
              height: 100%;
              position: relative;
              .ico{
                position: absolute;
                background-size: cover;
                background-repeat: no-repeat;
                border-radius:50%;
                width: 0.6rem;
                height: 0.6rem;
                margin: auto;
                top: 0;
                right: 0;
                bottom: 0;
                left: 0;
              }
            }
            .txt_box{
              float: left;
              width: 5.9rem;
              height: 1.59rem;
              overflow: hidden;
              border-bottom: 1px solid @bottom_line_color;
              .time{
                color: #a7b5cb;
                .fz(28px);
                padding-top: 0.30rem;
                padding-bottom: 0.2rem;
              }
              .info{
                width: 100%;
                display: flex;
                .item_li{
                  flex:1;
                  white-space: nowrap;
                  text-align: right;
                  &:nth-child(1){
                    text-align: left;
                  }
                }
              }
            }
          }

        }
        .ass_log{
          background: @no_content_bg_color;
          overflow: hidden;
          .save_ass_log_item_height{
            display: none;
          }
          .log_item{
            margin-top: 0.2rem;
            background: #fff;
            padding-bottom: 0.4rem;
            .time{
              height: 0.89rem;
              line-height: 0.89rem;
              border-bottom: 1px solid @bottom_line_color;

              padding-left: 0.3rem;
              padding-right: 0.3rem;
            }
            .state_box{
              padding-left: 0.3rem;
              padding-right: 0.3rem;
              padding-top: 0.3rem;
              padding-bottom: 0.2rem;
              display: flex;
              align-items:center;
              .ico{
                display: inline-block;
                width: 0.6rem;
                height: 0.6rem;
                background-size: cover;
                // background-color: #ccc;
                border-radius:50%;
                margin-right: 0.3rem;
                vertical-align: middle;

              }
              .state{
                .fz(40px);
                color: #4576f0;
              }
            }
            .des{
              padding-left: 0.3rem;
              padding-right: 0.3rem;
              .show_more{
                display: none;
              }
              &.min{
                position: relative;
                overflow: hidden;
                .show_more{
                  position: absolute;
                  white-space: nowrap;
                  display: inline-block;
                  vertical-align: bottom;
                  color: #4576f0;
                  right: 0;
                  bottom: 0;
                  background: #fff;
                }
              }
            }
          }
          .data-none {
            padding-bottom: .6rem;
            .none-img {
              width: 1.8rem;
              height: 1.8rem;
              margin: 1.1rem auto .3rem auto;
              background: url(../../assets/img/icon-page-none@2x.jpg) no-repeat center center /cover;
              border-radius: 50%;
              overflow: hidden;
            }
            p {
              line-height: .5rem;
              text-align: center;
              .fz(24);
              
            }
            .title,
            .result {
              color: #a7b5cb;
            }
            .add-click {
              color: #4576f0;
              
            }
            
          }
        }
      }
    }
    .table_box{
      height: 4.4rem;
      width: 7.5rem;
      background: url(../../assets/img/record@2x_56.png);
      background-size: cover;
      margin: auto;

    }

    .cylinderChart.null{
      position: relative;
      span{
        display: block;
        position: absolute;
        width: 3rem;
        height: 1rem;
        text-align: center;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        margin: auto;
        color: #ccc;
      }
    }

    .date_switch{
      position: relative;
      .prev, .next{
        position: absolute;
        width: 0.4rem;
        height: 0.4rem;
        background-image: url(../../assets/img/icon_more@2x_20.png);
        background-size: cover;
      }
      .prev{
        left: 0.3rem;
        top: 0;
        transform: rotate(180deg);
      }
      .next{
        right: 0.3rem;
        top: 0;
      }
      .data_info{
        height: 0.48rem;
        line-height: 0.48rem;
        text-align: center;
        border-radius:0.25rem;
        border: 1px solid #7da0f5;
        opacity: .6;
        width: 2rem;
        .fz(24px);
        margin: auto;
        white-space: nowrap;
        color: #fff;
      }
    }
    .top_switch{
      height: 1.0rem;
      line-height: 1rem;
      margin-left: 0.3rem;
      margin-right: 0.3rem;
      width: 6.8rem;
      margin: auto;
      .switch_table{
        width: 0.4rem;
        height: 0.4rem;
        display: block;
        float: left;
        background-size: cover;
        margin-top: 0.3rem;
      }
      .head_img{
        width: 0.6rem;
        height: 0.6rem;
        margin-top: 0.2rem;
        border-radius:50%;
        background-color: @accept_color;
        background-size: cover;
        background-repeat: no-repeat;
        float: left;
      }
      .week_month{
        width: 5.6rem;
        float: left;
        text-align: center;
        span{
          padding: 0 0.6rem;
          opacity: .5;
          color: #fff;
          padding-bottom: 0.12rem;
        }
        .week_month_cur{
          position: relative;
          color: #fff;
          opacity:1 !important;
          &:after{
            content:'';
            position: absolute;
            margin: auto;
            left: 0;
            right: 0;
            bottom: 0;
            width: 0.29rem;
            height: 0.05rem;
            background-color: #fff;
            border-radius:0.05rem;
          }
        }
      }
    }
    &:before{
      position: fixed;
      width: 100%;
      height: 100%;
      top: 5.9rem;
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
