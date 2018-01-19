<template>
  <div class="page_main_login -e-login_bg top_fixed" v-wechat-title="$route.meta.title" v-if="show_page_main_login">
    <div class="content_box user pos_top patient" v-if="show_page==='0'">

      <!-- -e-login_bg -e-login_bg_and_null -->
        <div class="top_tab_box float_top_box">
          <router-link :to="{path:'/app/main/tab_dev'}" class="tab tab_dev" tag="div">我的设备</router-link>
          <router-link :to="{path:'/app/main/tab_doctor'}" class="tab tab_doctor" tag="div">我的康复师</router-link>
          <router-link :to="{path:'/app/main/tab_files'}" class="tab tab_files" tag="div">病历档案</router-link>
        </div>

        <div v-transfer-dom >
          <x-dialog v-model="show_dialog" class="page_main_login_dialog" hide-on-blur>
            <div class="dialog_box">

              <div class="item_more  dialog_box_title">
                <i class="item_ico ico_data"></i>
                <span class="text">康复计划</span>
                <i @click="add_plan_fn()" class="next_ico icon_add"></i>
              </div>

              <div  class="event_box" @click="show_dialog=false">
                <info_item :tag="0" :info_item_data="dat_plan"></info_item>
              </div>

            </div>
          </x-dialog>
        </div>

        <div class="middle_box" >

          <!-- <router-link to="/app/reg/reg_step2" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">完善信息</span> <i class="next_ico ico_more"></i> </router-link> -->

          <div class="item_more log_more new_msg" @click="show_dialog=true">
            <i class="item_ico ico_data"></i>
            <span class="text">康复记录</span>
            <i class="next_ico ico_more"></i>
          </div>

          <!-- 图表 -->

          <div class="log_tab_box" v-if="dat_plan && dat_plan.length>0">
            <div class="item_more log_more table_title">
              <span class="text">
                {{
                  [,'左','右'][dat_plan[0].postion]+''+ 
                  [,'肘关节', '腕关节', '膝关节', '裸关节', '其他'][dat_plan[0].arthro]+'康复计划 - '+ 
                  [,'伸展','屈曲'][dat_plan[0].direction]+(dat_plan[0].overdue>'0' ? ' (历史)':'') 
                }} 
              </span>
            </div>
            <!-- <div @click="to_data_info_fn()" class="log_tab"></div> -->
            <!-- <div @click="to_data_info_fn2(dat_plan[0])" :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}" class="myChart"> -->
              <!-- <div class="msg">(无训练数据)</div> -->
            <!-- </div> -->
            <div @click="to_data_info_fn2(dat_plan[0])">

              <div v-show="is_table===false" :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}" class="myChart">
                <div class="msg">(无训练数据)</div>
              </div>

              <chart v-show="is_table===true" class="myChart" :style="{width: '7.5rem', height: '4.1rem', margin:'auto'}" :options="chart_opts"><div class="msg">(无训练数据)</div></chart>  

            </div>
            

            <info_item class="index_plan_2" :tag="0" v-if="dat_plan.length!=1" :info_item_data="dat_plan&&[dat_plan[0]]"></info_item>
          </div>

        </div>

        <!-- 积分 -->
        <div class="bottom_box">
          <div class="item_more log_more " @click="$router.push({path:'/app/credits'})">
            <i class="item_ico ico_integral"></i>
            <!-- <img src="~assets/img/bd.jpg" alt=""> -->
            <span class="text">我的积分</span>
            <i class="next_ico ico_more"></i>
          </div>
          <div class="cell_box">
            <router-link to="/app/credits" class="cell">
              <span class="data">{{user_detail.scoreValue | num_format}}</span> <span class="unit">积分</span>
            </router-link>
            <router-link to="/app/discount" class="cell">
              <span class="data">{{coupon}}</span> <span class="unit">张券</span>
            </router-link>
            <!-- <div class="cell"><span class="data">{{user_detail.scoreValue | num_format}}</span> <span class="unit">积分</span></div> -->
            <!-- <div class="cell"><span class="data">{{coupon}}</span> <span class="unit">张券</span></div> -->
          </div>
        </div>

        <!-- <div class="bottom_tab_box_pos"></div> -->
    </div>

    <div class="content_box doctor no_content_bg_color top_fixed" v-if="show_page==='1'">
      <div class="box">

        <search
        @result-click="resultClick"
        @on-change="getResult"
        :results="results"
        v-model="temp_value"
        position="absolute"
        auto-scroll-to-top top="0px"
        @on-focus="onFocus"
        @on-cancel="onCancel"
        @on-submit="onSubmit"
        ref="search">
        </search>

      </div>

      <div class="top_msg">
        <div class="top_msg_item"><i class="ico icon_doctor"></i><div class="txt">我的患者</div></div>
      </div>

      <div class="page_member_list" v-show="show_member_list">
        <div class="list_box">
          <div v-show="new_user_list.length" class="tag_li new_user" @click="to_member_list_fn()">
            <div class="member_item">
              <div class="img icon_patient"></div>
              <div class="txt">
                <div class="name">新的患者</div>
              </div>
              <i class="ico_new_msg"></i>
            </div>
          </div>
          <div class="tag_li" @click="info_item_fn(index, item)" v-for="(item, index) in member_list_data">
            <div class="member_item">
              <div class="img icon_head" v-wx_img="{'base64': item.responseEntity}"></div>
              <div class="txt">
                <div class="name" v-if="user_type==1">{{item.userName}}</div>
                <div class="des" v-if="user_type==1">时间段 {{item.startTime | date_format('MM.DD')}}-{{item.endTime | date_format('MM.DD')}}</div>
              </div>
              <i class="ico_new_msg" v-if="item==1"></i>
            </div>
          </div>
        </div>
      </div>

    </div>

    <router-view></router-view>
    <bottom_tab_box :class="[is_page_main ? 'page_main_bottom_tab_box' : '']" :on_index="0"></bottom_tab_box>
  </div>
</template>

<script>
  import { dateFormat, XDialog, TransferDomDirective as TransferDom } from 'vux'
  import bottom_tab_box from '../../components/bottom_tab_box.vue'
  import info_item from '../../components/info_item.vue'
  import { Tabbar, Search, Cell } from 'vux';
  import api_login_reg from '../../api/login_reg.js'
  import api_main from '../../api/main.js'
  import {cookie, to_echa_data_lines, to_echa_data_line, info_item_fn} from 'assets/js/common.js'

  // import echarts from 'echarts/lib/echarts';
  // import  'echarts/lib/chart/line';

  export default {
    data (){
      return {
        is_table:false, // 是否有图表

        show_member_list:true, // 显示患者列表
        new_user_list:[], // 新的患者
        member_list_data:'', // 患者列表

        user_type:'',
        doctor:'', // 医生信息  
        is_dev:false, // 是否是开发环境
        show_dialog:false, // 显示记录弹窗
        user:'', // 用户表
        results: [],
        user_detail:'', // 用户详情
        temp_value: '',
        res_data:'', // 图表数据
        coupon:'0', // 优惠券
        show_page_main_login:true, /*是否显示页面*/
        show_page:'', // 要显示的页面
        dat_plan:'', // 康复计划列表\
        chart_opts:{} // 图表数据
      }
    },
    created(){
      this.user_type=cookie.get('user_type')||this.$store.state.user_type;
      $_temp={};

      /*同步本地获取数据*/
      var store_arr=[
        'user',
        'doctor',
        'user_detail',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      this.show_page=cookie.get('user_type')||this.$store.state.user_type;
      this.reg_ed(this);


    },
    components:{
      Search,
      XDialog,
      info_item,
      Cell, bottom_tab_box
    },
    filters:{
      num_format(val){
        return parseFloat((val ? val : 0)).toLocaleString().replace(/,/g,'，');
      },
      date_format: function(val,form){
        return dateFormat(new Date(val), form);
      }
    },

    directives: {
      TransferDom
    },
    watch:{
      '$route':function(){
        // this.is_page_main = this.$route.name == 'main' ?  true : false;
      }
    },
    computed:{
      is_page_main:function(){
        return this.$route.name == 'main' ?  true : false;
      }
    },

    mounted() {

      
    },
    methods:{
      to_member_list_fn(){
        this.$router.push({name:'member_list'})
      },
      info_item_fn(index,item){
		// common.js里的函数
        info_item_fn(index,item,this);
      },
      //上面是医生方法


      get_user_detail(vm){
        var vm=this;
        api_main.get_user_detail().then( res => { // 用户查询积分明细
          console.log("用户查询积分明细",  res );
          vm.user_detail=res;
          vm.$store.commit('user_detail_up',res);
        },res=>{
          console.log('res', res)
        })

      },
      drawLine(_table_data) {
        var _table_data= _table_data ? _table_data : {};
        _table_data.series[0].areaStyle={
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
        };
        // let myChart = echarts.init(document.getElementById('myChart'))
        console.log('_table_data', _table_data)
        var y_min=Math.min.apply(null, _table_data.y_min);;
        this.chart_opts = {
          // animation:false,
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
        // myChart.setOption(this.chart_opts);
      },
      add_plan_fn(){
        console.log('添加计划');
        $_temp.temp_add_plan=true;
        this.$router.push({name:'exerciseProcess'});
      },
      edit_plan_fn(){
        console.log('编辑计划');
      },
      
      to_data_info_fn(item){
        console.log('to_data_info_fn', item);
        this.$router.push({name:'data_info', params:{plan_id:item.id}})
      },

      to_data_info_fn2(item){
        if(this.is_table==false) return;

        console.log('to_data_info_fn', item);
        this.$router.push({name:'data_info', params:{plan_id:item.id, res_data: this.res_data}})

        let obj=this.$store.state.temp_data;
        obj.plan_info={
          data:item
        };
        this.$store.commit('temp_data_up',obj)

      },

      setFocus () {
        this.$refs.search.setFocus()
      },
      resultClick (item) {
        console.log('item',item)
        info_item_fn('',item.item,this);
        // window.alert('you click the result item: ' + JSON.stringify(item))
        // this.$router.push({name:'doctor_see_user', params:{user_data:JSON.stringify(item)}})
      },
      getResult (val) {
        api_main.get_application(1,{filter:val}).then((res) => {
          console.log('医生查询患者 已验证',res);
          this.results = val ? getResult(_.uniqBy(res, 'patientId')) : [];
        });
      },
      onSubmit () {
        this.$refs.search.setBlur()
        this.$vux.toast.show({
          type: 'text',
          position: 'top',
          text: 'on submit'
        })
      },
      onFocus () {
        console.log('on focus');
        this.show_member_list=false;
      },
      onCancel () {
        console.log('on cancel');
        this.show_member_list=true;
        this.results = getResult([]);
      },

      reg_ed(vm){
        vm.show_page_main_login=true;
        vm.get_user_detail(vm);
        if(vm.show_page==='0'){ /*病人*/
          console.log('病人')
          /*当页所需请求信息*/

          api_main.get_coupon().then( res => { // 用户查询优惠券的总数量
            console.log("用户查询优惠券的总数量",  res );
            vm.coupon= res===null ? '0' : res.length;
          },res=>{
            console.log('res', res)
          })

          api_main.get_plan().then( res => { // 病人获取康复计划列表
            console.log("病人获取康复计划列表",  res );
            vm.dat_plan=res.reverse();

            let plan_id=vm.dat_plan[0].id;
            api_main.get_index_table(plan_id).then( res => { // 根据康复计划Id显示主页第一条最新的康复记录数据
            // api_main.get_index_table(1).then( res => { // 根据康复计划Id显示主页第一条最新的康复记录数据
              vm.res_data=res;
              console.log("根据康复计划Id显示主页第一条最新的康复记录数据", vm.dat_plan,  res , res.length );
              if(vm.res_data.length<=0) {
                console.log('无训练数据');
                return
              }else{
                vm.is_table=true;
                vm.$nextTick(function(){
                  var table_data=to_echa_data_lines([to_echa_data_line(vm.res_data[0].jsonArray)],['#4f7ff2'], {'line_width':3});
                  if(vm.show_page==0 && vm.dat_plan.length>0) vm.drawLine(table_data);
                })
              };
            },res=>{
              console.log('res', res)
            })


          },res=>{
            console.log('res', res)
          })

          // 从我的页面进入显示记录
          vm.show_dialog = vm.$route.params.show_main_dialog_box;

        }else{ /*医生*/
          console.log('医生')

          api_main.get_doctor_detail().then( res => { //保存医生信息
            vm.doctor=res;
            vm.$store.commit('doctor_detail_up',res);
          },res=>{
            console.log('res', res)
          })

          api_main.get_application(1,{filter:''}).then( res => { // 医生查询患者 已验证
            console.log("医生查询患者 已验证",  res );
            this.member_list_data=_.uniqBy(res, 'patientId');;
          },res=>{
            console.log('res', res)
          })
          
          api_main.get_application(0,{filter:''}).then( res => { // 医生查询患者 未验证
            console.log("医生查询患者 未验证",  res );
            this.new_user_list=res;
          },res=>{
            console.log('res', res)
          })

        }
      },

    }
  }
  function getResult (val) {
    let rs = []
    for (let i = 0; i < val.length; i++) {
      rs.push({
        title: `${val[i].userName}`,
        item:val[i],
      })
    }
    return rs
  }
</script>

<style lang="less">
  @import "../../style/common.less";
  @import '../../style/page_member_list.less';
  body .weui-bar__item_on span{
    color: @theme_color;
  }
  body{
    .page_main_bottom_tab_box{
      position: fixed;
      z-index: 999;
    }
    .page_main_login_dialog{
      .weui-dialog{
        overflow: scroll;
        width: 6.9rem;
        max-height: 8.1rem;
        max-width: none ;
        text-align: left;
        border-radius:0.12rem;
        .dialog_box_title{
          .next_ico{
            right: 0.3rem;
          }
        }
      }
    }
  }

  .page_main_login{
    overflow: scroll;
    .content_box.user{
      .index_plan_2{
        margin-top: 0.15rem;
        &>div{
          &:before{
            content:'';
            display: block;
            height: 1px;
            background: #dfe8ff;
          }
          &:after{
            display: none;
          }
        }
      }
      .myChart{
        position: relative;
        text-align: center;
        .msg{
          color: @placeholder_color;
          width: 100%;
          height: 1rem;
          position: absolute;
          top: 0;
          right: 0;
          bottom: 0;
          right: 0;
          margin: auto;
        }
      }
      .top_tab_box{

        overflow: hidden;
        padding-top: 0.8rem;
        height: 2.2rem;
        width: 6.9rem;
        background: #fff;
        margin: auto;
        margin-top: 0.34rem;
        display:flex;
        border-radius: 0.12rem;
        box-shadow: 0 0 1.286667rem 0.106667rem rgba(97, 139, 244, 0.2);
        color:@placeholder_color;
        .tab{
          flex:1;
          padding-top: 1rem;
          position: relative;
          text-align: center;
          .fz(28px);
          &:after{
            content: '';
            width: 0.9rem;
            height: 0.9rem;
            display: inline-block;
            background-repeat: no-repeat;
            background-position: center center;
            background-size: contain;
            position: absolute;
            left: 0;
            top: 0;
            right: 0;
            margin: auto;
          }
          &.tab_dev:after{
            background-image: url(../../assets/img/-e-icon_scan@2x.png);
          }
          &.tab_doctor:after{
            background-image: url(../../assets/img/-e-icon_doctor@2x.png);
          }
          &.tab_files:after{
            background-image: url(../../assets/img/-e-icon_archives@2x.png);
          }
        }
      }
      .middle_box, .bottom_box{
        background: #fff;
      }
      .middle_box{
        margin-top: 0.3rem;
        margin-bottom: 0.3rem;
        .log_tab{
          background-image:url(../../assets/img/log_tab_box@2x.png);
          width: 7.5rem;
          height: 4.1rem;
          margin: auto;
          background-size: cover;
          position: relative;
          &:after{
            position: absolute;
            display: block;
            content:'';
            width: 6.8rem;
            height: 1px;
            background: @bottom_line_color;
            margin-left: 0.3rem;
            bottom: 0;
          }
        }

        .table_title{
          text-indent: 0;
          // height: auto;
          // line-height: normal;
        }

      }
      .bottom_box{
        width: 7.5rem;
        margin: auto;
        .cell_box{
          height: 2rem;
          position: relative;
          &:after{
            position: absolute;
            content:'';
            display: block;
            width: 1px;
            height: 1rem;
            background: @placeholder_color;
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            left: 0;
            margin: auto;
          }
          .cell{
            height: 2rem;
            line-height: 2rem;
            text-align: center;
            width: 3.75rem;
            float: left;
            color: @theme_color;
            background-size: contain;
            .data{
              .fz(50px);
            }
            &:nth-child(1){
              background-image: url(../../assets/img/icon_integral2@2x.png);
            }
            &:nth-child(2){
              background-image: url(../../assets/img/icon_coupon@2x.png);
            }
          }
        }
      }
    }
    .content_box.doctor{
      .page_member_list{
        padding-bottom: 1.3rem;
      }
      .vux-search-box{
        height: 100%;
        .vux-search_show{
          font-size: 15px;
        }
      }
      .weui-search-bar__label{
        border-radius: .6rem;
      }
      .weui-cells.vux-search_show{
        max-height: none;
      }
      .weui-search-bar__form:after{
        border-radius: .6rem;
      }
    }

  }


</style>

