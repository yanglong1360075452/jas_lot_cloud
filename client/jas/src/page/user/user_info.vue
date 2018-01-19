<template>
  <div class="page_user_info -e-login_bg top_fixed" v-wechat-title="$route.meta.title">
    <div class="content_box pos_top">

      <div class="my_info bg_cloud float_top_box">
        <div class="head_img">
          <div class="img"></div>
        </div>
        <div class="info">
          <div class="name"><span class="text">{{user_detail.realName}}</span></div>
          <div class="age_sex">
            <span class="age">{{user_detail.birthDate|age_format}}岁</span><span class="sex">{{[,'男','女'][user_detail.sex]}}</span>
            <div class="item_more menu_files" @click="to_tab_files_fn">
              <i class="item_ico ico_case"></i>
              <span class="text">病历档案</span>
              <i class="next_ico ico_more"></i>
            </div>  
          </div>
          
        </div>
      </div>
      <div class="item_box">
        <div class="dialog_box">

          <div class="item_more  dialog_box_title">
            <i class="item_ico ico_data"></i>
            <span class="text">康复计划</span>
            <i @click="add_plan_fn()" class="next_ico icon_add"></i>
          </div>

          <div class="event_box">
            <info_item :tag="0" :info_item_data="2"></info_item>
          </div>

        </div>
      </div>

    </div>
  </div>
</template>

<script>

  import {dateFormat} from 'vux'
  import info_item from '../../../src/components/info_item.vue'

  export default {
    data (){
      return {
        user_detail:'' , // 用户信息
      }
    },
    created(){

      var store_arr=[
        'user_detail',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

    },
    methods:{
      to_tab_files_fn(){
        console.log('Ta的病历档案');
        this.$router.push({name:'tab_files'});
      },
      add_plan_fn(){
        console.log('添加计划');
        this.$router.push({name:'exerciseProcess'});
      },
    },
    components:{
      info_item,
    },
    filters:{
      age_format: function(val,form){
        val=val && val;
        return dateFormat(new Date(), 'YYYY')-dateFormat(new Date(val), 'YYYY');
      }
    },
  }

</script>

<style lang="less">
  @import '~style/page_mine.less';
  .page_user_info{
    .content_box{
      padding-bottom: 0 !important;
      border-bottom: .76rem solid @no_content_bg_color;
      .item_box{
        padding-top: 2.2rem !important;
      }
    }
    .age_sex{
      position: relative;
    }
    .menu_files{
      position: absolute;
      display: inline-block;
      width: 2.5rem;
      float: right;
      right: -0.3rem;
      bottom: -0.2rem;
    }
    .dialog_box{
      background: @no_content_bg_color;
      overflow: hidden;
      .dialog_box_title{
        margin-top: 0.2rem;
        position: relative;
        &:after{
          position: absolute;
          width: 100%;
          bottom: 0;
          content:'';
          height: 1px;
          background: @no_content_bg_color;
        }
      }
      .event_box{
        background: #fff;
        .info_item:last-of-type{
          &:after{
            display: none;
          }
        }
      }
    }
  }
</style>
