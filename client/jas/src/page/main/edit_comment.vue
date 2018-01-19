<template>
  <div class="page_exerciseProcess top_fixed no_content_bg_color" v-wechat-title="$route.meta.title">

    <!-- 训练周期 时间段 -->
    <div class="ipt_box">
      <div class="item_box">

        <datetime
          class="w_item_more"
          year-row="{value}年"
          month-row="{value}月"
          v-model="startTime"
          day-row="{value}日"
          hour-row="{value}点"
          minute-row="{value}分"
          @on-change="change" :title="'Birthday'">
          <div class="item_more">  <span class="text">开始时间</span> <div class="text2">{{startTime}}</div> <i class="next_ico ico_more"></i> </div>
        </datetime>

        <datetime
          class="w_item_more"
          year-row="{value}年"
          month-row="{value}月"
          v-model="endTime"
          day-row="{value}日"
          hour-row="{value}点"
          minute-row="{value}分"
          @on-change="change" :title="'Birthday'">
          <div class="item_more">  <span class="text">结束时间</span> <div class="text2">{{endTime}}</div> <i class="next_ico ico_more"></i> </div>
        </datetime>

      </div>
    </div>

    <!-- 评估结论 -->
    <div class="ipt_box">
      <div class="select_btn_box">
        <div class="title">评估结论</div>
        <div class="btn_box">
          <checker v-model="sel_dict_appraisal" default-item-class="jas_btn" selected-item-class="active">
            <checker-item :value="item.code" v-for="(item, index) in dict_appraisal" :key="index">{{item.name}}</checker-item>
          </checker>

        </div>
      </div>
    </div>

    <div class="ipt_box des_img">
      <!-- 描述 -->
      <div class="xtextarea_box">
        <XTextarea :autosize="true" placeholder="请输入评估内容" v-model="note"></XTextarea>
      </div>
    </div>

    <div class="button_box" @click="next_fn()">
      <mt-button class="button">提交评估</mt-button>
    </div>
    <!-- <actionsheet v-model="show_menu" :menus="menu_txt" @on-click-menu="menu_fn" show-cancel></actionsheet> -->
    <Toast v-if="toast_show" type="text" v-model="toast_show">{{toast_text}}</Toast>
  </div>
</template>

<script>
  import {Toast, XTextarea, Checker, CheckerItem, Actionsheet, XSwitch , Datetime, XButton } from 'vux'
  import { Button, MessageBox } from 'mint-ui';
  import api_login_reg from '../../api/login_reg.js'
  import api_main from '../../api/main.js'
  import { dateFormat } from 'vux'
  export default {
    data (){
      return {
        temp_edit_plan:false,
        temp_add_plan:false,

        toast_show:false,
        toast_text:'',
        user:'', // 用户表

        startTime:'',  // 开始时间
        endTime:'',  // 结束时间
        note:'',  // 备注
        dict_appraisal:'', // 获取结论列表
        sel_dict_appraisal:'', 
      }
    },
    created(){
      var store_arr=[
      'user',

      'dict_appraisal',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      // this.sel_dict_appraisal=this.dict_appraisal[0].code; // 默认值
      this.startTime=dateFormat(new Date(), 'YYYY-MM-DD');
      var date2=new Date(+new Date()+24*3600*1000*90);
      this.endTime=dateFormat(date2, 'YYYY-MM-DD');

    },
    components:{
      Toast,
      XTextarea,
      'mtButton':Button,
      Actionsheet,
      Checker,
      CheckerItem,
      Datetime,
      XSwitch
    },
    methods:{
      change (value) {
        // this.medicalArchival.startTime=value;
        console.log('change', value);
      },
      next_fn(){

        var data= {
            "startTime":+new Date(this.startTime),  //开始时间
            "endTime":+new Date(this.endTime),  //结束时间
            "verdict":this.sel_dict_appraisal,  //结论
            "content":this.note  //备注
          }

        if(data.verdict==''){
          this.toast_show=true;
          this.toast_text='请输入结论';
          return
        }

        console.log('提交评估',data)
        let plan_id=this.$store.state.temp_data.plan_info.data.id;

        api_main.add_user_assess(plan_id,data).then(res=>{
          console.log('提交评估成功',data);
          // this.toast_show=true;
          // this.toast_text='提交评估成功';
          this.$router.push({name:'data_info', params:{new_comment:true}})
        },err=>{
          console.log('提交评估失败',data);
          this.toast_show=true;
          this.toast_text='提交评估失败';
        })
      },
    }
  }

</script>

<style lang="less">
  @import '../../style/page_exerciseProcess.less';
</style>
