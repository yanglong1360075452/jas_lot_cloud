<template>
  <div class="page_reg page_login_s2 -e-login_bg" v-wechat-title="$route.meta.title">
    <div class="page_reg_box">

      <group class="item_box">
        <div class="item img">
          <div class="title">设置头像</div>
          <img class="img_box" @click="changeUserImg" :src="userHeadImg.url"/>
          <!-- <img v-if="load_img" class="img_box" @click="changeUserImg"  :style="{fontSize:'30px', backgroundImage:'url('+userHeadImg.url+')' }" :src="userHeadImg.url" alt="头像"/> -->
          <!-- <div v-if="load_img" class="img_box" @click="changeUserImg"  :style="{fontSize:'30px', backgroundImage:'url('+userHeadImg.url+')' }"> </div> -->
        </div>
        <div class="item sex">
          <div class="title">选择性别</div>
          <div class="radio_box">
            <div class="radio_item" @click="user_sex_fn(2)" :class="{'active': user.sex==2}">女</div>
            <div class="radio_item" @click="user_sex_fn(1)" :class="{'active': user.sex==1}">男</div>
          </div>
        </div>
        <div class="item date">
          <div class="title">出生年月</div>

          <datetime v-model="user.birth" :placeholder="'请选择'"
            default-selected-value="1988-8-8"
            :min-year=1900
            :max-year=2017 format="YYYY-MM-DD"
            @on-change="change"
            :title="'出生日期'"
            year-row="{value}年"
            month-row="{value}月"
            day-row="{value}日"
            hour-row="{value}点"
            minute-row="{value}分"
            confirm-text="完成"
            cancel-text="取消">
            <x-input :disabled="true"  placeholder="请选择年龄" :show-clear="false" v-model="user.birth"></x-input>
          </datetime>

        </div>
        <div class="item hospital" v-if="show_page==1">
          <div class="title">医院</div>
          <div class="event_box" @click="select_hospital_fn()">
            <x-input :disabled="true" placeholder="请选择医院" :show-clear="false" v-model="doctor.hospital.name"></x-input>
          </div>
        </div>
        <mt-button @click="next_fn()" class="button" type="danger">确定</mt-button>
      </group>

      <router-view></router-view>
      <toast v-model="toast_show" type="text">{{toast_text}}</toast>
      <alert v-model="doctor_msg_show" :title="doctor_msg_title">{{doctor_msg_content}}</alert>
    </div>
  </div>
</template>

<script>
  import {setTitle} from './../../assets/js/common.js'
  import { Button } from 'mint-ui';
  import { XInput, Group, Alert, Countdown, Toast, Datetime  } from 'vux'
  import VueCoreImageUpload from 'vue-core-image-upload'
  import api_main from 'api/main.js'
  import api_login_reg from '../../api/login_reg.js'

  export default {
    data (){
      return {
        load_img:true,
        test_info:{},
        uploadImg: false, // 是否上传图片
        showImage:false,
        uploadImg:false,
        serverId:'', // serverId
        userHeadImg:{'url' : ''}, // localId

        doctor_msg_title:'提示',
        doctor_msg_content:'正在等待审核...',
        doctor_msg_show:false, // 医生显示等待审核信息

        toast_show:false,
        toast_text:'',
        user:'', // 用户表
        doctor:'', // 治疗师表
      }
    },
    created(){

      var store_arr=[
      'user',
      'doctor',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      this.show_page=this.$store.state.user_type; // 要显示的页面

      if(this.$route.params.hospital){
        this.doctor.hospital=this.$route.params.hospital ;
      }

      // this.userHeadImg.url=this.$store.state.wx_img;

      api_main.AvatarDefault(this.$store.state.openid).then( res => { // 获取默认头像
        console.log("获取默认头像",  res );;

        this.userHeadImg.url=res.img;

        var vm=this;
        vm.load_img = false;
        vm.$nextTick(function () {
           vm.load_img = true        });

      },res=>{
        console.log('res', res)
      })

      this.$router.push({name:'reg_step2'})


      // 获取微信授权接口
      var href = window.location.href || document.location.href;
      var url = href.split('#')[0];

      var wxObject = {};
      api_main.get_wx_config(url).then( res => {
          wxObject = Object.assign(res,{
              debug: false, //开启微信调试模式
              jsApiList: [    //需要使用的js接口列表
                  'chooseImage',
                  'uploadImage',
              ] 
          });

          wx.config(wxObject);

      }).catch(error => {
          console.log('error',error);
      });

    },
    components:{
      'mtButton':Button,
      Alert,
      Group,
      XInput,
      Countdown,
      Toast,
      Datetime,
      VueCoreImageUpload
    },
    watch:{
      '$route':function(){
        console.log(this.$route.path=='/app/login');
      }
    },
    methods:{
      changeUserImg() {
          var vm = this;
          console.log('changeUserImg')
          vm.showImage = true;
          vm.uploadImg = true; 
          // 微信选择图片接口
          wx.chooseImage({
              count: 1,
              sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
              sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
              success: function(res){
                  //rebuild dom
                  vm.load_img = false;
                  vm.$nextTick(function () {
                     vm.load_img = true;
                  });

                  // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                  vm.userHeadImg.url = res.localIds[0];
                  vm.uploadImg = true;
              }
          });
      },
      select_hospital_fn(){
        this.$router.push({name:'select_hospital' ,params:{prev_page:'reg_step2'}})
        // this.$router.push({path:"/app/login/select_hospital"});
      },
      change (value) {
        this.user.birth=value;
        console.log('change', value);
      },
      next_fn(){
        var vm=this;
        var data={
          "sex":this.user.sex, // 性别
          "birthDate":+new Date(this.user.birth), // 生日
          "headPortrait":this.user.headPortrait // 头像
        };


        if(vm.uploadImg){
            // 微信上传图片接口
            wx.uploadImage({
                localId: vm.userHeadImg.url, //需要上传的图片本地的id，由chooseImage接口获得
                isShowProgressTips: 1, // 默认为1，显示进度提示
                success: function(res){
                    vm.serverId = res.serverId;
                    data.headPortrait = vm.serverId;
                    vm.test_info=data;
                    console.log('uploadImage', vm, data);
                    up_data(vm);
                }
            });
        }else{
            vm.test_info=data;
            console.log('', vm, data);
            up_data(vm);
        }

        this.$store.commit('user_up',this.user);
        console.log(this.$store.state.user);
        this.$store.commit('doctor_up',this.doctor);
        console.log(this.$store.state.doctor);


        console.log(data);

        function up_data(vm){
          api_login_reg.user_info(vm.doctor.hospital.code, data).then(res=>{
            console.log(res);
            console.log('信息已更新',res);
            // vm.toast_text='信息已更新';
            // vm.toast_show=true;

            console.log(vm.$store.state.user_type)
            if(vm.$store.state.user_type!='0'){ // 如果是医生就跳转到医生页面
              console.log('医生主页')
              /*
                如果是医生，提示需要等待审核，停止跳入主页。
                因为入口api通过 code 判断是否注册过并且通过审核，code只能使用一次，所以这里不进行查询。
              */
              vm.doctor_msg_show=true
              // vm.$router.push({path:"/app/main"});
            }else{ // 病人进入病人档案页面
              console.log('病历档案')
              vm.$router.push({name:"medicalArchival"});
            }

          },err=>{
            console.log(err);
          })
        }


      },
      user_sex_fn(select){
        this.user.sex=select;
      },
      imageuploaded(res) { // 定义上传完成执行的方法
        console.log(res);
      },
    }
  }

</script>

<style lang="less">

  @import "./../../style/variable.less";

  .weui-cells.vux-no-group-title{
    background: none;
  }
  .weui-dialog__btn.weui-dialog__btn_primary{
    color: @theme_color;
  }
  .page_reg{

    .weui-toast_text{
      white-space: nowrap;
      width: auto !important;
      padding: 0 0.45rem;
    }
    .vux-no-group-title{
      margin-top: 0;
      padding-top: 1.17647059em;
    }

    .page_reg_box{
      position: relative;
      height: 100%;

      .item_box{
        width: 5.4rem;
        margin: auto;
        color: #fff;
        .fz(30px);
        .vux-x-input{
          padding-left: 0;
          .fz(40px);
        }


        .item{
          height: 1.76rem;
          border-bottom: 1px solid rgba(255,255,255,.5);
          .title{
            padding-top: 0.34rem;
            .fz(30px);
          }
        }
        .item.code{
          .vux-x-input{
            width: 2.6rem;
            float: left;
            padding-right: 0;
          }
          .re_code{
            padding: 0 0.22rem;
            height: 0.6rem;
            line-height: 0.6rem;
            text-align: center;
            .fz(24px);
            color: #fff;
            border-radius: 0.3rem;
            margin-top: 0.15rem;
            border: solid 1px rgba(255,255,255,.5);
            float: right;
          }
        }
        .weui-cell{
          &:before{
            display: none;
          }
        }
        .vux-no-group-title{
          &:before,&:after{
            display: none;
          }
        }
        .button{
          margin-top: 0.5rem;
          .base_button(@button_text_color1,linear-gradient(to left, #fff, #fff));
          .fz(38px);
        }
      }
      .protocol{
        position: absolute;
        bottom: 0;
        display: block;
        text-align: center;
        width: 100%;
        .fz(24px);
        color: #fff;
        margin-bottom: 0.5rem;
        a{
          color: #fff;
          display: inline-block;
          &:after{
            content:'';
            width: 100%;
            display: block;
            border-bottom: 1px solid #fff;
          }
        }
      }

    }
  }

  .page_login_s2{
    .item_box{
      /* 设置头像 */
      .item.img{
        position: relative;
        .img_box{
          position: absolute;
          border: 0;
          right: 0;
          top: 0;
          width: 1.4rem;
          height: 1.4rem;
          border-radius:50%;
          background-image: url(../../assets/img/head@2x.png);
          background-repeat: no-repeat;
          background-size: cover;
          .g-core-image-upload-form{
            width: 1.4rem !important;
            height: 1.4rem !important;
          }
          .img{
            width: 1.4rem;
            height: 1.4rem;
          }
        }
      }
      /* 选择性别 */
      .item.sex{
        position: relative;
        .radio_box{
          .radio_item{
            position: absolute;
            top: 0.38rem;
            width: 1.0rem;
            height: 1.0rem;
            text-align: center;
            line-height: 1.0rem;
            .fz(40px);
            background: @theme_color;
            color: #fff;
            border-radius:50%;
            &:nth-child(1){
              left: 2.2rem;
            }
            &:nth-child(2){
              right: 0;
            }
            &.active{
              background: #fff;
              color: @theme_color;
            }
          }
        }
      }

      /* 出生年月 */
      .item.date{
        .vux-datetime{
          color: #fff;
          padding: 0;
          .weui-cell__ft:after{
            display: none;
          }
        }
        .weui-cell_access:active{
          background: none;
        }
      }
    }
  }



</style>

<style lang="less" scoped>
</style>
