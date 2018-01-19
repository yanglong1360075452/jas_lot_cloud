<template>
  <div class="page_doctor_see_user -e-login_bg top_fixed" v-wechat-title="$route.meta.title">
    <div class="content_box pos_top">

      <div v-if="user_detail.responseEntity" class="my_info bg_cloud float_top_box">
        <div class="head_img">
          <div class="img" v-wx_img="{'base64':user_detail.responseEntity}"></div>
        </div>
        <div class="info">
          <div class="name"><span class="text">{{user_detail.realName}}</span></div>
          <div class="age_sex">
            <span class="age">{{user_detail.birthDate|age_format}}岁</span>
            <span class="sex">{{[,'男','女'][user_detail.sex]}}</span>
            <span class="job">{{user_detail.department}}</span>
            <div class="item_more files" @click="to_tab_files_fn()">
              <i class="item_ico ico_case"></i>
              <span class="text">病历档案</span>
              <i class="next_ico ico_more"></i>
            </div>
          </div>
        </div>
      </div>

      <div class="item_box">

        <div class="item_more  item_box_title">
          <i class="item_ico ico_data"></i>
          <span class="text">康复计划</span>
          <i @click="add_plan_fn()" class="next_ico icon_add"></i>
        </div>

        <info_item :tag="0" :info_item_data="info_item_data"></info_item>

      </div>

      <div class="doctor_btn" v-show="user_detail_plan.status==1">
        <div class="button_box" @click="sel_item_fn2('', user_detail_plan, 2)">
          <mt-button class="button">接受</mt-button>
        </div>
        <div class="button_box" @click="sel_item_fn2('', user_detail_plan, 3)">
          <mt-button class="button danger">拒绝</mt-button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>

  import bottom_tab_box from '../../components/bottom_tab_box.vue'
  import {dateFormat} from 'vux'
  import { Button } from 'mint-ui';
  import info_item from '../../components/info_item.vue'
  import api_main from '../../api/main.js'

  export default {
    data (){
      return {
        info_item_data:'', // 康复计划列表
        user_detail:'', // 病人资料
        user_detail_plan:'', // 相对于申请计划的病人资料
        user_type:'',
      }
    },
    methods:{
      sel_item_fn2(index,item, type){
        api_main.ok_application(item.pId, {'type':type}).then(res=>{ // 接受用户申请
          console.log('接受用户申请',res);
          this.user_detail_plan.status=type;
          if(type==3) this.$router.push({name:'member_list'});
        },err=>{
          console.log(err);
        })
      },
      add_plan_fn(){
        console.log('添加计划');
        $_temp.temp_add_plan=true;
        this.$router.push({name:'exerciseProcess'});
      },
      to_tab_files_fn(){
        this.$router.push({name:'tab_files'});
      },
      to_main_show_fn(){
        this.$router.push({name:'main',params:{'show_main_dialog_box':true}});
        console.log('显示康复记录');
      }
    },
    created(){
      this.user_detail_plan=this.$store.state.temp_data.user_data.data;
      api_main.get_doctor_see_user(this.user_detail_plan.patientId).then(res=>{ // 医生通过病人id查询病人信息
        console.log('医生通过病人id查询病人信息', res);
        this.user_detail=res;
      },res=>{
        console.log(res)
      })
      
      api_main.get_exercise_process(this.user_detail_plan.patientId).then(res=>{ // 康复师看病人的康复计划
        console.log('康复师看病人的康复计划', res);
        this.info_item_data=res;
      },res=>{
        console.log(res)
      })

    },
    filters:{
      age_format: function(val,form){
        val=val && val;
        return dateFormat(new Date(), 'YYYY')-dateFormat(new Date(val), 'YYYY');
      }
    },
    components:{
      'mtButton':Button,
      info_item,
      bottom_tab_box

    },
  }

</script>

<style lang="less">
  @import '~style/page_mine.less';
  .page_doctor_see_user{
    .content_box.pos_top{
      padding-bottom: 0;
    }
    .item_box{
      margin-top: 0.45rem;
    }
    .item_box_title{
      position: relative;
      &:after{
        top: -0.3rem;
        left: 0;
        position: absolute;
        content:'';
        width: 100%;
        display: block;
        height: 0.3rem;
        background: @no_content_bg_color;
      }
      &:before{
        bottom: 0;
        left: 0;
        position: absolute;
        content:'';
        width: 100%;
        display: block;
        height: 1px;
        background: @bottom_line_color;
      }
    }
    .doctor_btn{
      background: @no_content_bg_color;
      padding-top: 0.2rem;
      padding-bottom: 0.2rem;
      .button.danger{
        background-image: linear-gradient(to left, #ef4f4f, #e42323);
        margin-top: 0.2rem;
      }
    }
  }
</style>
