<template>
  <div class="page_login" v-wechat-title="$route.meta.title">

      <x-input v-model="userInfo.username" class="weui-cell_vcode" placeholder="请输入用户名">
        <div class="ico_left name" slot="label" ></div>
      </x-input>

      <x-input v-model="userInfo.password" :type="input_type" :show-clear="false" class="weui-cell_vcode" placeholder="请输入密码">
        <div class="ico_left pass" slot="label" ></div>
        <div @click="switch_password" :class="showpass ? 'showpass' : 'showpass2'" class=" ico_left showpass" slot="right" ></div>
      </x-input>

      <div class="enter submit" @click="submit">
        登录
      </div>


      <div class="test">
        <p class="p1" @click="p1_fn">p1</p>
        <p class="p2">p2</p>
      </div>

      <Toast class="login_toast" v-show="show_Toast" v-model="show_Toast" type="text">{{text_Toast}}</Toast>

  </div>
</template>

<script>


  import userService from '../../service/user.js'
  import tokenService from '../../service/token.js'
  import loginService from '../../service/login.js'
  import iconSvg from '../../directive/icon-svg.vue'
  import {XInput, dateFormat, Toast } from 'vux'

  export default {
    name: 'index',
    data (){
      return {
        input_type:'password',
        showpass:true,
        show_Toast:false,
        text_Toast:'',
        userInfo:{
          username:'', // luomo
          password:'', // 1111
          openId:''
        }
      }
    },
    created(){
      // console.log(this.userInfo.openId)
      // console.log(this.userInfo.openId ? '未绑定' : '已绑定')
    },
    components:{XInput, iconSvg, Toast},
    methods:{
      p1_fn(){

        this.$store.commit('num_add',10);
        console.log(this.$store.state.jas_test);
      },
      submit () {
        this.userInfo.openId = 'openId' // this.$store.state.openId; // 异步取得的 oppenId
        if(this.userInfo.password == '' || this.userInfo.username == ''){
          this.text_Toast="请输入用户和密码"
          this.show_Toast=true
          return
        }

        //loginService.login_fn({username:this.username,password:this.password});
        //location='#/app/routine';
        //console.log(this.$router);



        // 1. 临时通过
        // this.$router.push({name:'application',params:{type:'normal'}});

        // 2. 下面用来开启登录验证
        loginService.login_fn(this.userInfo).then(token => {
          console.log("token",token.token);
          tokenService.setToken(token.token);
          this.$router.push({name:'application',params:{type:'normal'}});
        },res=>{
          console.log('res', res)
          this.show_Toast=true
          this.text_Toast=res
        })

      },
      switch_password(){
        this.showpass=!this.showpass
          console.log(this.input_type)
        if(this.input_type=='password'){
          this.input_type='';
        }else{
          this.input_type='password'
        }
      }
    }
  }

</script>

<style lang="less">
  .test{
    .p1{
      color: #f00;
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
