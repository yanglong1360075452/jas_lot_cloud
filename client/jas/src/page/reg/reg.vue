<template>
  <div class="page_reg -e-login_bg top_fixed" v-wechat-title="$route.meta.title">
    <div class="page_reg_box">
      <group class="item_box">
        <div class="item name">
          <div class="title">姓名</div>
          <x-input :show-clear="false" v-model="user.realname"></x-input>
        </div>
        <div class="item phone">
          <div class="title">手机</div>
          <x-input :show-clear="false" type="number" v-model="user.phone"></x-input>
        </div>
        <div class="item code">
          <div  class="title">验证码</div>
          <x-input  type="number" :show-clear="false" class="xinput" v-model="code"></x-input>
          <div class="re_code" @click="send_code">
            {{re_code_text}}
            <countdown :start="re_code_time_start" v-model="re_code_time" @on-finish="re_code_time_end_fn" v-show="re_code_time>0"></countdown>
            <span v-show="re_code_time>0">s</span>
          </div>
        </div>
        <mt-button @click="next_fn" class="button" type="danger">下一步</mt-button>
      </group>

      <div class="protocol">注册即表示同意<a href="javascript:" @click="protocol_fn">JAS服务条款</a></div>
      <toast v-model="toast_show" type="text">{{toast_text}}</toast>
      <alert :button-text="''" v-model="doctor_msg_show" :title="doctor_msg_title">{{doctor_msg_content}}</alert>

    </div>
    <router-view></router-view>
  </div>
</template>

<script>
  import {int_dict} from './../../assets/js/common.js'
  import { Button } from 'mint-ui';
  import { XInput, Alert, Group, Countdown, Toast } from 'vux'
  import api_login_reg from '../../api/login_reg.js'
  import api_main from '../../api/main.js'
  export default {
    data (){
      return {
        doctor_msg_title:'提示',
        doctor_msg_content:'正在等待审核...',
        doctor_msg_show:false, // 医生显示等待审核信息
        doctor_status:undefined, // 医生的审核状态

        code:'', // 001206
        show_content:false,
        re_code_text:'获取验证码',
        re_code_time:0,
        re_code_time_start:false,
        toast_show: false,
        user:'', // 用户表
        toast_text:'验证码错误'
      }
    },
    beforeRouteLeave (to, from, next) {
      if(this.doctor_status!=undefined && this.doctor_status!=1){
        history.pushState(null, null, document.URL);
        window.addEventListener('popstate', function () {
          history.pushState(null, null, document.URL);
        });  
      }else{
        next()
      }
    },
    created(){

      /*获取数据*/
      var store_arr=[
        'user',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };


      /*审核消息*/
      if(this.$route.params.res && this.$route.params.res.status!=undefined){
        console.log('this.$route.params',this.$route.params);
        this.doctor_status=this.$route.params.res.status;

        this.doctor_msg_content=['正在等待审核...','已同意','申请被拒绝！','其他'][this.doctor_status];
        this.doctor_msg_show=true;
      }

    },
    components:{
      'mtButton':Button,
      Group, XInput, Countdown, Toast, Alert
    },
    watch:{
      '$route':function(){
        console.log(this.$route.path=='/app/login');
      }
    },
    methods:{
      test_fn(){ // 数据验证

        if(!this.user.realname.replace(/\s+/,'')){
          this.toast_text='姓名不能为空';
          this.toast_show=true;
          return 'err'
        }

        if(!(/^1\d{10}$/.test(this.user.phone))){
          this.toast_text='手机号格式错误';
          this.toast_show=true;
          return 'err'
        }

        return 'ok'

      },
      next_fn(){ // 下一步
        if(this.doctor_status!=undefined){
          this.doctor_msg_content=['正在等待审核...','已同意','申请被拒绝！','其他'][this.doctor_status];
          this.doctor_msg_show=true;
          return
        }

        if(this.test_fn()=='err'){ // 判断数据是否符合要求
          return
        }

        if(!this.code.replace(/\s+/,'')){

          this.toast_text='验证码不能为空';
          this.toast_show=true;

          return
        }


        var obj={
          "openId":this.$store.state.openid, //用户标识
          "realName":this.user.realname, //真实姓名
          "phone":this.user.phone, //手机号码
          "captcha":this.code, //验证码
          "type": this.$store.state.user_type //类型 0代表病人 1代表医生
        };
        
        this.$router.push({name:'reg_step2'})
        // return;
        api_login_reg.register(obj).then( res => {
          console.log('注册成功',res);
          this.toast_text='注册成功';
          this.toast_show=true;
          console.log('下一步');
          this.$store.commit('user_up',this.user);
          this.$store.commit('token_up',res.token);
          console.log(this.$store.state.user);
          int_dict(this);

        },err=>{
          console.log('注册失败',err);
          this.toast_text=err;
          this.toast_show=true;
          // this.re_code_time=0;
        })

      },
      send_code(){ // 发送验证码
        if(this.test_fn()=='err'){
          return
        }else{

          if( this.re_code_time_start ) { // 如果正在计时就退出
            console.log('正在计时')
            return
          }else{ // 否则重新计时
            this.re_code_text='重新获取';
            console.log('发送验证码', this.user.phone);
            api_login_reg.send_code(this.user.phone).then( res => {
              console.log('发送验证码',res);
              this.toast_text='验证码发送成功';
              this.toast_show=true;
            },err=>{
              console.log('验证码发送失败',err);
              this.toast_text='验证码发送失败';
              this.toast_show=true;
              this.re_code_time=0;
              this.re_code_time_end_fn();
            })

            this.re_code_time=60;
            this.re_code_time_start=true;
          }

        }

      },
      re_code_time_end_fn(){ // 关闭验证码计时
        this.re_code_time_start=false;
      },
      protocol_fn(){ // 显示注册协议
        this.show_content=!this.show_content;
        this.$router.push({name:'protocol'});
      }
    }
  }

</script>

<style lang="less">

  @import "./../../style/variable.less";

  .weui-cells.vux-no-group-title{
    background: none;
  }
  .page_reg{

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

</style>

<style lang="less" scoped>
</style>
