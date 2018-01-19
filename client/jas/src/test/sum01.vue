<template>
  <div class="page_mine -e-login_bg top_fixed" v-title data-title="示例控件">
    <div class="content_box pos_top">

      <div class="my_info bg_cloud float_top_box">
        <div class="head_img">
          <div class="img"></div>
        </div>
        <div class="info">
          <div class="name"><span class="text">示例控件</span><span v-show="false" class="edit active_bg_color"></span></div>
          <div class="age_sex" v-show="false"><span class="age">18岁</span><span class="sex">女</span></div>
        </div>
      </div>
      <div class="item_box">

        <router-link :to="{path:'01'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">城市选择</span> {{medicalArchival.department.txt}} <i class="next_ico ico_more"></i> </router-link>
        <router-link :to="{path:'02'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">编辑头像（需集成微信JSSDK）</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link :to="{path:'03'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">出生日期</span> {{user.birth}} <i class="next_ico ico_more"></i> </router-link>
        <router-link :to="{path:'04'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">时间段选择</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link :to="{path:'05'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">图表</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link :to="{path:'06'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">搜索</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link :to="{path:'08'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">积分明细</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link :to="{path:'09'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">积分规则、是否兑换、编辑昵称</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link :to="{path:'10'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">显示上拉按钮菜单</span> <i class="next_ico ico_more"></i> </router-link>
        <router-link :to="{path:'11'}" class="item_more" tag="div"> <i class="item_ico ico_setting"></i> <span class="text">swiper</span> <i class="next_ico ico_more"></i> </router-link>


      </div>

    </div>
  </div>
</template>

<script>

  import bottom_tab_box from '../components/bottom_tab_box.vue'

  export default {
    name: 'index',
    data (){
      return {
        medicalArchival:'',
        user:'',
      }
    },
    created(){
      var store_arr=[
        'medicalArchival',
        'user'
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };
    },
    components:{bottom_tab_box},
    methods:{
    }
  }

</script>

<style lang="less" scoped>
  @import '../style/variable.less';
  .page_mine{
    .content_box{
      background: #fff;
      padding-top: 0;
      .float_top_box{
        top: -2.45rem;
      }
      .item_box{
        padding-top: 2rem;
      }
    }
    .pos_er_div{
      &:after{
        background: #fff !important;
        margin-top: 2.4rem !important;
      }
    }
    &:before{
      background: #fff !important;
    }
    .item_box{
      .item_more{
        margin-top: .3rem;
        .fz(30px);
        text-indent: .36rem;
      }
      .next_ico ico_more{
        right: .15rem;
      }
    }
  }
  .my_info {
    height: 2.5rem;
    margin-bottom: 2.4rem;
    &.bg_cloud{ // 白云背景
      background-image: url(../assets/img/bg_cloud@2x.png);
      background-repeat: no-repeat;
      background-size:contain;
      background-position: bottom center;
    }
    .head_img{
      padding-top: 1.46rem;
      width: 100%;
      .img{
        width: 1.5rem;
        height: 1.5rem;
        margin: auto;
        border-radius:50%;
        background-image: url(../assets/img/head@2x.png);
        background-size: cover;
        box-shadow: 7px 12.1px 38px 0 rgba(97, 139, 244, 0.2);
      }
    }
    .info{
      padding:0 0.3rem;
      .name{
        width: 100%;
        height: 0.84rem;
        line-height: 0.84rem;
        .text{
          .fz(40px);
          display: inline-block;
          float: left;
        }
        .edit{
          display: inline-block;
          float: right;
          width: 0.4rem;
          height: 0.4rem;
          background-image: url(../assets/img/-e-nav_icon_edit@2x.png);
          background-repeat: no-repeat;
          background-size: cover;
          margin-top:0.2rem ;
        }
      }
      .age_sex{
        .age{
          padding-right: 0.3rem;
        }
      }
    }
  }



</style>

<style lang="less" scoped>
  .test{
    .p2{
      color: #00f;
    }
  }
</style>
