<template>
  <div class="page_exerciseProcess top_fixed no_content_bg_color" v-wechat-title="$route.meta.title">

    <!-- 顶部提示 -->
    <div class="msg_top">
      <div class="txt"><span>*训练计划请认真填写</span><a href="javascript:">查看说明</a></div>
      <div v-show="!(temp_edit_plan || temp_add_plan)" class="btn jas_btn" @click="to_main_fn()">跳过</div>
    </div>

    <!-- 训练周期 时间段 -->
    <div class="ipt_box">
      <div class="item_box">
        <div class="item_more" @click="openPrompt()">
          <span class="text">训练周期</span> <i class="next_ico ico_more"></i>
          <div class="text2">{{exe_day}}天</div>
        </div>

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

    <!-- 训练关节 -->
    <div class="ipt_box arthro">
      <div class="select_btn_box">
        <div class="title">训练关节</div>
        <div class="btn_box">

          <ul>
            <li class="tag_li" :class="{'cur_arthro':sel_dict_arthro==dict_arthro[index].code}" v-for="(item, index) in dict_arthro" @click="exerciseProcess_arthro_fn(dict_arthro[index].code)" :key="index" :code="item.code">
              <div class="sel_item" :class="[item.active ? item.ico+'_sel' : item.ico]"></div>
              <span class="txt">{{item.name}}</span>
            </li>

          </ul>

        </div>
      </div>
    </div>

    <!-- 训练部位 -->
    <div class="ipt_box">
      <div class="select_btn_box">
        <div class="title">训练部位</div>
        <div class="btn_box">

          <checker v-model="sel_dict_postion" default-item-class="jas_btn" selected-item-class="active">
            <checker-item :value="item.code" v-for="(item, index) in dict_postion" :key="index">{{item.name}}</checker-item>
          </checker>

        </div>
      </div>
    </div>

    <!-- 训练方向 -->
    <div class="ipt_box">
      <div class="select_btn_box">
        <div class="title">训练方向</div>
        <div class="btn_box">

          <checker v-model="sel_dict_direction" default-item-class="jas_btn" selected-item-class="active">
            <checker-item :value="item.code" v-for="(item, index) in dict_direction" :key="index">{{item.name}}</checker-item>
          </checker>

        </div>
      </div>
    </div>

    <!-- 医院信息 -->
    <div class="ipt_box">
      <div class="item_box">
        <div class="item_more" @click="to_member_list_fn()">
          <span class="text">康复师</span> <i class="next_ico ico_more"></i>
          <div v-if="(sel_member && sel_member.realName) || (doctor && doctor.doctorName)" class="text2">{{sel_member.realName || doctor.doctorName}}</div>
        </div>
      </div>
    </div>

    <div class="ipt_box des_img">
      <!-- 描述 -->
      <div class="xtextarea_box">
        <XTextarea :autosize="true" placeholder="添加一段备注" v-model="note"></XTextarea>
      </div>
    </div>

    <div class="button_box" @click="next_fn()">
      <mt-button class="button">完成</mt-button>
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
  console.log(dateFormat(new Date(), 'YYYY-MM-DD'),'YYYY-MM-DD');
  export default {
    data (){
      return {

        temp_edit_plan:false,
        temp_add_plan:false,

        sel_member:{}, // 用户选择的康复师
        toast_show:false,
        toast_text:'',
        // cur_arthro:'', // 数据变更辅助检测
        user:'', // 用户表
        doctor:{}, // 治疗师表
        exerciseProcess:'', // 训练计划

        exe_day:'0', // 计划天数
        startTime:'',  // 开始时间
        endTime:'',  // 结束时间
        note:'',  // 备注
        dict_arthro:'', // 获取关节列表
        sel_dict_arthro:'', 
        dict_postion:'', // 获取部位列表
        sel_dict_postion:'', 
        dict_direction:'', // 获取方向列表
        sel_dict_direction:'', 
      }
    },
    created(){
      var store_arr=[
      'user',
      'doctor',

      'dict_arthro',
      'dict_postion',
      'dict_direction',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };


      this.exerciseProcess_arthro_fn(5);
      this.sel_dict_postion=this.dict_postion[0].code;
      this.sel_dict_direction=this.dict_direction[1].code;
      this.startTime=dateFormat(new Date(), 'YYYY-MM-DD');
      var date2=new Date(+new Date()+24*3600*1000*90);
      this.endTime=dateFormat(date2, 'YYYY-MM-DD');

      // 康复师选择
      this.sel_member=this.$route.params.sel_member ? this.$route.params.sel_member : {};
      if(this.sel_member || $_temp.loaded_select_hospital){
        if($_temp.exerciseProcess){
          this.startTime=$_temp.exerciseProcess.startTime;
          this.endTime=$_temp.exerciseProcess.endTime;
          this.note=$_temp.exerciseProcess.note;

          if(this.sel_member==undefined && $_temp.loaded_select_hospital==true){ /*如果用户没用选择信息就使用原来的信息*/
            this.sel_member =$_temp.exerciseProcess.sel_member;
          }

          this.sel_dict_arthro=$_temp.exerciseProcess.sel_dict_arthro;
          this.sel_dict_postion=$_temp.exerciseProcess.sel_dict_postion;
          this.sel_dict_direction=$_temp.exerciseProcess.sel_dict_direction;

          $_temp.loaded_select_hospital=false
          delete $_temp.exerciseProcess
        }
        this.exerciseProcess_arthro_fn(this.sel_dict_arthro);
      }else{
        this.sel_member={};
        if(this.$store.state.user_type==1) this.sel_member={ /*医生添加计划*/
          doctorId:this.$store.state.user_detail.id,
          realName:this.$store.state.user_detail.realName,
        };
      }

      // 添加计划
      if($_temp.temp_add_plan){
        this.temp_add_plan=true;
      }

      // 编辑计划
      var plan_data = this.$route.params.info_item && this.$route.params.info_item.item;
      if(plan_data){
        $_temp.plan_data_id=plan_data.id;
      };

      if($_temp.edit_plan && plan_data){
        this.temp_edit_plan=true

        console.log('plan_data 编辑计划', plan_data);
        // this.startTime=dateFormat(plan_data.startTime, 'YYYY-MM-DD');
        // this.endTime=dateFormat(plan_data.endTime, 'YYYY-MM-DD');
        // this.sel_dict_arthro=plan_data.arthro-1; /*训练关节*/
        // this.exe.lefAndrig.code=plan_data.postion; /*训练部位*/
        // this.exe.depAndret.code=plan_data.direction; /*训练方向*/
        // this.exe.describe=plan_data.note; /*备注*/

        this.startTime=dateFormat(plan_data.startTime, 'YYYY-MM-DD');
        this.endTime=dateFormat(plan_data.endTime, 'YYYY-MM-DD');
        this.note=plan_data.note;
        this.sel_dict_arthro=plan_data.arthro;
        this.sel_member = {'doctorId':plan_data.doctorId, 'realName':plan_data.realName};
        //this.sel_member = {'doctorId':plan_data.doctorId, 'doctorId':plan_data.doctorId};
        this.exerciseProcess_arthro_fn(this.sel_dict_arthro);
        this.sel_dict_postion=plan_data.postion;
        this.sel_dict_direction=plan_data.direction;

        this.doctor=this.$route.params.info_item.doctor;

      };



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
    watch:{
      'startTime':function(){
        this.exe_day=this.date2day_fn();
      },
      'endTime':function(){
        this.exe_day=this.date2day_fn();
      },
    },
    methods:{
      to_member_list_fn(){
        if(this.$store.state.user_type==1) return;

        var save_data={
          exerciseProcess:{
            startTime:this.startTime,
            endTime:this.endTime,
            note:this.note,
            sel_member :this.sel_member,
            sel_dict_arthro:this.sel_dict_arthro,
            sel_dict_postion:this.sel_dict_postion,
            sel_dict_direction:this.sel_dict_direction,
          }
        };
        $_temp.exerciseProcess=save_data.exerciseProcess;
        console.log('$_temp.exerciseProcess',$_temp.exerciseProcess);
        this.$router.push({name:'select_hospital',params:{prev_page:'exerciseProcess'}});
      },
      exerciseProcess_arthro_fn(code){
        // this.exerciseProcess.arthro[code].active=!this.exerciseProcess.arthro[code].active; // 多选
        this.dict_arthro.forEach(value=>{
          value.active=false;
          if(value.code==code){
            value.active=true;
          }
        });
        this.sel_dict_arthro=code;
      },
      date2day_fn(){ // 计算时间相差天数
        var startTime=this.startTime;
        var endTime=this.endTime;
        var date1 = startTime;  //开始时间
        var date2 = new Date(endTime);    //结束时间
        var date3 = date2.getTime() - new Date(date1).getTime();   //时间差的毫秒数

        //计算出相差天数
        var days=Math.floor(date3/(24*3600*1000))
        return days;
      },
      change (value) {
        // this.medicalArchival.startTime=value;
        console.log('change', value);
      },
      openPrompt() {
        MessageBox.prompt(' ', '训练天数').then(({ value }) => {
          if (value) {
            this.exe_day=value;
            var new_date=+new Date(this.startTime)+24*3600*1000*value;
            this.endTime=dateFormat(new_date, 'YYYY-MM-DD');
          }
        });
      },
      to_main_fn(){
        this.$router.push({name:'main'});
      },
      next_fn(){
        var data= {
            "doctorId":this.sel_member.doctorId,  //医生id
            "arthro":this.sel_dict_arthro,   //关节
            "postion":this.sel_dict_postion,  //部位
            "direction":this.sel_dict_direction,  //方向
            "startTime":+new Date(this.startTime),  //开始时间
            "endTime":+new Date(this.endTime),  //结束时间
            "note":this.note  //备注
          }
        // var plan_data = this.$route.params.info_item && this.$route.params.info_item.item;
        if($_temp.edit_plan){

          // console.log('编辑计划',data)
          // return
          api_main.up_exerciseProcess($_temp.plan_data_id,data).then(res=>{
            console.log('编辑计划成功',data, $_temp.plan_data_id);
            // this.toast_show=true;
            // this.toast_text='编辑计划成功';
            delete $_temp.edit_plan;
            delete $_temp.plan_data_id;
            delete $_temp.exerciseProcess
            this.$router.push({name:'main'})
          },err=>{
            console.log('编辑计划失败',data);
            this.toast_show=true;
            this.toast_text='编辑计划失败';
          })
          
        }else{

          // console.log('添加计划',data)
          // return
          let patientId=this.$store.state.user_type==1 ? this.$store.state.temp_data.user_data.data.patientId : '';
          api_login_reg.create_plan(patientId,data).then(res=>{
            console.log('添加计划成功',data);
            // this.toast_show=true;
            // this.toast_text='添加计划成功';
            this.$router.push({name:'main'})
            delete $_temp.exerciseProcess
          },err=>{
            console.log('添加计划失败',data);
            this.toast_show=true;
            this.toast_text='添加计划失败';
          })

        }



      },
    }
  }

</script>

<style lang="less">
  @import '../../style/page_exerciseProcess.less';
</style>

<style lang="less" scoped>
</style>
