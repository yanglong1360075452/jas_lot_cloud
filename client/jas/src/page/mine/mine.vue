<template>
  <div class="page_mine -e-login_bg top_fixed" v-wechat-title="$route.meta.title">
    <div class="content_box pos_top">

      <div class="my_info bg_cloud float_top_box">
        <div class="head_img">
          <div class="img"  v-wx_img="wx_img"></div>
        </div>
        <div class="info">
          <div class="name">
            <span class="text">{{user_detail.realName}}</span> 
            <span class="edit active_bg_color" @click="to_personalInfo()"></span> 
          </div>
          <div class="age_sex">
            <span class="age">{{user_detail.birthDate|age_format}}岁</span>
            <span class="sex">{{[,'男','女'][user_detail.sex]}}</span>
            <span class="job">{{user_detail.department}}</span>
          </div>
        </div>
      </div>
      <div class="item_box">

        <div class="item_more" @click="to_main_show_fn()" v-if="user_type!=1"> <i class="item_ico ico_data"></i> <span class="text">康复记录</span> <i class="next_ico ico_more"></i> </div>
        <router-link v-if="user_type!=1" to="/app/main/tab_files" class="item_more" tag="div"> <i class="item_ico ico_case"></i> <span class="text">我的病历</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link to="/app/credits" class="item_more" tag="div"> <i class="item_ico ico_integral"></i> <span class="text">我的积分</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link to="/app/discount" class="item_more" tag="div"> <i class="item_ico ico_coupon_small"></i> <span class="text">我的优惠券</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link to="/app/setting" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">账户设置</span> <i class="next_ico ico_more"></i> </router-link>

      </div>

      <bottom_tab_box :on_index="1"></bottom_tab_box>

    </div>
  </div>
</template>

<script>

  import bottom_tab_box from '../../components/bottom_tab_box.vue'
  import {dateFormat} from 'vux'

  export default {
    data (){
      return {
        wx_img:{}, // 我的头像
        user_detail:'',
        user_type:''
      }
    },
    methods:{
      to_personalInfo(){
        this.$store.commit('route_record', 'mine');
        this.$router.push('/app/personalInfo');
      },
      to_main_show_fn(){
        this.$router.push({name:'main',params:{'show_main_dialog_box':true}});
        console.log('显示康复记录');
      }
    },
    created(){
      var store_arr=[
        'user_detail',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      if(this.user_detail.responseEntity && this.user_detail.responseEntity.body && this.user_detail.responseEntity.body.length>0){
        this.wx_img={'base64': this.user_detail.responseEntity};
      }else{
        this.wx_img={'url': this.$store.state.wx_img};
      }

      this.user_type=this.$store.state.user_type;

    },
    filters:{
      age_format: function(val,form){
        val=val && val;
        return dateFormat(new Date(), 'YYYY')-dateFormat(new Date(val), 'YYYY');
      }
    },
    components:{bottom_tab_box},
  }

</script>

<style lang="less">
  @import '~style/page_mine.less';
  // @import '~style/test.less';
  .sex{
    // .change();
  }
</style>
