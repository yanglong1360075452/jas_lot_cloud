<template>
  <div class="page_enter top_fixed">
    <router-view v-show="is_wx"></router-view>
    <Toast v-if="Toast_show" type="text" v-model="Toast_show">{{Toast_text}}</Toast>
  </div>
</template>

<script>
  import Vue from 'vue'
  import { Toast } from 'vux'

  import {cookie, url_tool, int_dict} from '../../assets/js/common.js'
  import api_login_reg from '../../api/login_reg.js'
  import api_main from 'api/main.js'
  export default {
    name: 'app',
    data(){
      return{
        Toast_show:false,
        Toast_text:'',
        is_wx:true, 

        login_info:'', // 登录接口
      }
    },
    beforeRouteEnter(to, from, next) {

      (async () => {
        try {
          let user_type = url_tool.getWechatQuery().user_type[0];
          let code = url_tool.getWechatQuery().code;
          // let user_type = url_tool.getParams('user_type');
          // let code = url_tool.getParams("code");
          if(user_type && code){
            cookie.set('user_type', user_type, 10);
            let res = await api_login_reg.is_reg(code);

            next(vm=>{
              if(res.token){
                if(user_type==1 && res.status!=undefined && res.status!=1){
                  vm.$router.push({name:'reg', params:{res}});
                }

                cookie.set('token', res.token, 10);
                vm.$store.commit('token_up',res.token);
                cookie.set('user_type', user_type, 10);
                vm.$store.commit('user_type_up',user_type);
                cookie.set('wx_img', res.img, 10);
                vm.$store.commit('wx_img_up',res.img);
                int_dict(vm);
              }else{
                cookie.del('token');
                cookie.set('user_type', user_type, 10);
                vm.$store.commit('user_type_up',user_type);
                vm.$store.commit('openid_up',res.openId);

                cookie.set('wx_img', res.img, 10);
                vm.$store.commit('wx_img_up',res.img);
                
                vm.$router.push('reg');
              }
            })
          }else if(cookie.get('token') && cookie.get('user_type')){
            next(vm => {
              vm.$store.commit('token_up',cookie.get('token'));
              vm.$store.commit('user_type_up',cookie.get('user_type'));
              vm.$store.commit('wx_img_up',cookie.get('wx_img'));
              int_dict(vm);
            })
          }

        } catch (err) {
          console.log("err", err);
          next(vm => {})
        }
      })()

    },
    components:{
      Toast
    },
    methods:{},
    mounted(){
    },


  }
</script>
