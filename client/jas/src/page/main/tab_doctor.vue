<template>
  <div class="page_tab_doctor -e-login_bg top_fixed" v-wechat-title="$route.meta.title">

    <div class="top_index">
      <div class="swiper-container swp1" ref='swiper_container'>
        <div class="swiper-wrapper">
          <div v-for="(item, index) in swp_data" class="swiper-slide">
            <div class="bg icon_head" v-wx_img="{'base64':item.responseEntity}" ></div>
          </div>
        </div>
      </div>
    </div>
    
    <div class="bottom_index">
      <div class="swiper-container swp2" ref='swiper_container2'>
        <div class="swiper-wrapper">
          <div v-for="(item, index) in swp_data" class="swiper-slide">
            <div class="slide_box">
              
              <div class="top_center">
                <div class="name">{{item.doctorName}}</div>
                <div class="age">{{item.age}}岁</div>
                <div class="sex">{{[,'男','女',][item.sex]}}</div>
              </div>
              <div class="doctor_tag">
                <div class="position"><div class="ico icon_position"></div>{{item.professiona}}</div><!-- 职称 -->
                <div class="hospital"><div class="ico icon_hospital"></div>{{item.hospitalDesc}}</div><!-- 医院 -->
                <div class="department"><div class="ico icon_department"></div>{{item.departmentDesc}}</div><!-- 科室 -->
              </div>
              <div class="des_box">
                <div class="title">简介</div>
                <div class="des">{{item.description}}</div>
              </div>

              <div class="item_more  dialog_box_title">
                <i class="item_ico ico_data"></i>
                <span class="text">康复计划</span>
                <i class="next_ico icon_add" @click="add_plan_fn({ev:'add'})"></i>
              </div>
              
              <div class="info_item_box">
                <info_item :tag="1" :doctor="item" :info_item_data="swp_plan[item.doctorId]"></info_item>
              </div>

            </div>
          </div>
        </div>
        <div class="bottom_index_info" v-show="swp_data.length>0">
          {{swp_data_index+1}} / {{swp_data.length}}
        </div>
      </div>
    </div>
    <div v-show="false" class="edit_btn_float" @click="edit_doctor_fn()"></div>
  </div>
</template>

<script>
  import api_main from '../../api/main.js'
  import info_item from '../../components/info_item.vue'
  import Vue from 'vue'
  import { dateFormat } from 'vux'
  export default {
    data (){
      return {
        swp_data:[], /*康复师数据*/
        swp_data_index:0, /*当前索引*/
        swp_plan:{}, /*康复师对应的计划*/
        swp_img:{}, /*康复师头像*/
      }
    },
    created(){
      var vm=this;
      api_main.get_doctors_list().then(res=>{
        vm.swp_data=res;
        setTimeout(vm.int_swp,0);
        res.forEach(function(val){
          console.log(val.doctorId);
          api_main.get_doctors_plan(val.doctorId).then(res=>{
            Vue.set(vm.swp_plan, val.doctorId, res);
          },res=>{
            console.log(res);
          });
          // api_main.get_head_img(val.openId).then(res=>{
            // Vue.set(vm.swp_img, val.openId, res);
            // console.log(val,'get_head_img',res);
          // },res=>{
            // console.log(res);
          // });
        })
      },res=>{
        console.log(res);
      });
    },
    components:{
      info_item
    },
    mounted(){
    },
    filters:{
      date_format: function(val,form){
        return dateFormat(new Date(val), form);
      }
    },
    methods:{
      edit_doctor_fn(){
        console.log('修改医生');
        $_temp.edit_doctor={
          ev:true,
          data:{}
        };
      },
      add_plan_fn(ev){
        console.log('添加计划');
        this.$router.push({name:'exerciseProcess', params:{'data':ev}});
      },
      int_swp(){
        var vm=this;
        var swp_dom=this.$refs.swiper_container;
        var swiper1 = new Swiper(swp_dom, {
          onInit:function(swiper){
            console.log('swp1');
          },
          slidesPerView: 5, /*显示多少个*/
          initialSlide:(vm.swp_data.length/2|0), // 初始化索引， 居中 vm.swp_data.length/2|0
          centeredSlides: true, /*当前位置居中*/
          onSlideChangeEnd: function(swiper){ // 过度结束
            console.log(swiper.activeIndex) //切换结束时，告诉我现在是第几个slide
            swiper2  && swiper2.slideTo(swiper.activeIndex, 1000, false);//切换到第一个slide，速度为1秒
          },
          breakpoints: { /*响应式*/
            1024: {
              spaceBetween: 40 /*项间隙*/
            },
            768: {
              spaceBetween: 10
            },
            640: {
              spaceBetween: 20
            },
            320: {
              spaceBetween: 10
            }
          }
        });
        swiper1.destroy(false); /*禁用滑动*/
        var swp_dom2=this.$refs.swiper_container2;
        var swiper2 = new Swiper(swp_dom2, {
          slidesPerView: 1,
          onInit:function(swiper){
            console.log('swp2');
          },
          initialSlide:(vm.swp_data.length/2|0), // 初始化索引
          breakpoints: { /*响应式*/
            1024: {
              spaceBetween: 40 /*项间隙*/
            },
            768: {
              spaceBetween: 30
            },
            640: {
              spaceBetween: 20
            },
            320: {
              spaceBetween: 10
            }
          },
          onSlideChangeEnd: function(swiper){ // 过度结束
            console.log(swiper.activeIndex) //切换结束时，告诉我现在是第几个slide
            vm.swp_data_index=swiper.activeIndex;
            swiper1.slideTo(swiper.activeIndex, 1000, false);//切换到第一个slide，速度为1秒
          },

        });
      }
    }
  }
</script>

<style lang="less">
  @import '../../style/common.less';
  @import '../../style/variable.less';
  body .page_tab_doctor{
    .edit_btn_float{
      background-image: url(../../assets/img/-e-btn_add@2x.png);
      background-repeat: no-repeat;
      background-size: cover;
      &:after{
        display: none;
      }
      &:before{
        display: none;
      }
    }
    &:after{
      .bg_cloud ;
      background-position: bottom left;
      background-repeat:repeat-x;
      position: fixed;
      width: 100%;
      height: 1rem;
      top: 3.6rem;
      left: 0;
      overflow: scroll;
      z-index: 999;
      content: '';
      // background: #fff;
      background-repeat: no-repeat;
      background-position: bottom;
      background-size: 100%;
      z-index: -2;
    }
    &:before{
      position: fixed;
      width: 100%;
      height: 100%;
      top: 4.53rem;
      left: 0;
      overflow: scroll;
      z-index: 999;
      content: '';
      background: #fff;
      background-repeat: no-repeat;
      background-position: bottom;
      background-size: 100%;
      z-index: -2;
    }
    .top_index{
      margin-top: 0.3rem;
      margin-bottom: 0.3rem;
      padding-left: 0.3rem;
      .swiper-container{
        padding-left: 0.3rem;
      }
      .swp1{
        .swiper-slide{
          width: 1.5rem;
          height: 1.5rem;
          line-height: 1.5rem;
          position: relative;
          text-align: center;
          opacity: .5;
          .bg{
            position: absolute;
            top: 0;
            left: -.7rem;
            bottom: 0;
            right: 0;
            margin: auto;
            width: 0.8rem;
            height: 0.8rem;
            background-color: #000;
            background-size: cover;
            background-repeat: no-repeat;
            border-radius: 50%;
          }
        }
        .swiper-slide-active{
          opacity: 1;
          .bg{
            background-color: #ccc;
            background-size: cover;
            background-repeat: no-repeat;
            width: 1.5rem;
            height: 1.5rem;
          }
        }
      }
    }
    .bottom_index{
      width: 100%;

      .swp2{
        overflow: hidden;
        width: 6.9rem;
        padding-left: 0.3rem;
        padding-right: 0.3rem;
        height: 11rem;
        margin: auto;
        .swiper-slide {
          box-shadow: 0px 20px 60px 0 rgba(83, 129, 239, 0.24);
          border-radius:0.3rem;
          width: 6.9rem;
          height: 9.15rem;
          overflow: hidden;
          background: #fff;
          .slide_box{
            overflow: scroll;
            height: 100%;
          }
        }
        .bottom_index_info{
          position: absolute;
          .fz(28px);
          bottom: 1.1rem;
          width: 6.8rem;
          text-align: center;
        }
        .fz(28px);
        .top_center{
          padding-top: 0.45rem;
          text-align: center;
          .name{
            .fz(40px);
            margin-bottom: 0.15rem;
          }
          .age,.sex{
            display: inline-block;
          }
        }
        .doctor_tag{
          overflow: hidden;
          padding-left: 0.3rem;
          padding-right: 0.3rem;
          padding-top: 0.4rem;
          &>div{
            position: relative;
            .ico{
              position: absolute;
              top: .1rem;
              right: 0;
              left: 0;
              margin: auto;
              width: 0.4rem;
              height: 0.4rem;
              background-repeat: no-repeat;
              background-size: cover;
            }
            padding-top: 0.72rem;
            text-align: center;
            width: 1.2rem;
            float: left;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
          }
          .hospital{
            margin-left: 0.5rem;
            margin-right: 0.5rem;
            width: 2.8rem;
          }

        }
        .des_box{
          padding-right: 0.3rem;
          padding-left: 0.3rem;
          padding-top: 0.5rem;
          .des{
            padding-top: 0.2rem;
            padding-bottom: 0.3rem;
            color: @placeholder_color;
          }
        }
        .item_more{
          &:after{
            content:'';
            height: 1px;
            background: @bottom_line_color;
            display: block;
          }
        }
      }
    }
  }

</style>
