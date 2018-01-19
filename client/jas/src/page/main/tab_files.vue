<template>
  <div class="page_tab_files -e-login_bg top_fixed" v-wechat-title="$route.meta.title">
    <div class="head_top">
      <div class="img_box">
        <div class="img icon_head" v-wx_img="{'base64':user_detail.responseEntity}"></div>
      </div>
      <div class="txt">
        {{user_detail.realName}}
        {{user_detail.birthDate|age_format}}岁
        {{[,'男','女'][user_detail.sex]}}
        <!-- 会长 18岁 女 -->
      </div>
    </div>
    <div class="bottom_box">
      <div class="item_box" v-for="item in files">
        <div class="time">{{item.createTime|date_format('MM月DD日')}}</div>
        <div class="des">
          <div class="txt">{{item.content}}</div>
          <div class="ico icon_case"><div class="ico_txt" @click="to_files_info_fn(item)">病历</div></div>
        </div>
        <div class="addr_box">
          <div class="addr" v-for="dict_hospitals_item in dict_hospitals" v-if="dict_hospitals_item.code==item.hospital">{{dict_hospitals_item.name}}</div>
          <div class="name">{{item.doctorName}}</div>
        </div>

      </div>
    </div>
    <div class="edit_btn_float" @click="to_medicalArchival"></div>

    <loading class="root_loading" :show="loading != vue_loading" :text="text1"></loading>

  </div>
</template>

<script>
  import api_login_reg from '../../api/login_reg.js'
  import api_main from '../../api/main.js'
  import {mapState} from 'vuex'
  import {dateFormat} from 'vux'
  import {InlineLoading} from 'vux'
  import {Loading,TransferDomDirective as TransferDom} from 'vux'

  export default {
    name: 'index',
    data (){
      return {
        vue_loading:true,
        text1: '加载中...',
        files:'', /*病历档案*/
        user_detail:'', /*用户信息*/
        dict_hospitals:'', /*医院列表*/
      }
    },


    directives: {
      TransferDom
    },

    computed: mapState([
      'loading'
    ]),
    components: {Loading},
    created(){

      var store_arr=[
        'user_detail',
        'dict_hospitals',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      let patientId = this.$store.state.user_type==1 ? this.$store.state.temp_data.user_data.data.patientId : ''; // 病人id
      api_main.get_files(patientId).then(res=>{ /*获取病历档案*/
        this.files=res.reverse();
        console.log('获取病历档案',res);
        this.vue_loading=false
      },res=>{
        console.log(res);
        this.vue_loading=false
      });

      if(this.$store.state.user_type==1){
        
        api_main.get_doctor_see_user(patientId).then(res=>{ // 医生通过病人id查询病人信息
          console.log('医生通过病人id查询病人信息', res);
          this.user_detail=res;
        },res=>{
          console.log(res)
        })
      }


    },
    filters:{
      date_format: function(val,form){
        return dateFormat(new Date(val), form);
      },
      age_format: function(val,form){
        val=val && val;
        return dateFormat(new Date(), 'YYYY')-dateFormat(new Date(val), 'YYYY');
      }
    },
    methods:{
      to_medicalArchival(){
        let obj=this.$store.state.temp_data;
        delete obj.files;
        this.$store.commit('temp_data_up', obj);
        this.$store.commit('compile_photograph', []);
        this.$router.push({name:'medicalArchival'});
        $_temp.add_files=true;
      },
      to_files_info_fn(item){
        $_temp.page_tab_files_data=item;

        let obj=this.$store.state.temp_data;
        obj.files={
          data:item,
          edit_files:true,
        };
        this.$store.commit('temp_data_up',obj)

        this.$router.push({name:'files_info', params:{page_tab_files_data:item}})
      }
    }
  }
</script>

<style lang="less">
  @import '../../style/common.less';
  @import '../../style/variable.less';
  body .page_tab_files{

    &:after{
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
    .head_top{
      height: 2.6rem;
      text-align: center;
      .img_box{
        width: 100%;
        .img{
          display: inline-block;
          width: 1.2rem;
          height: 1.2rem;
          border-radius:50%;
          background-color: #ccc;
          background-size: cover;
          background-repeat: no-repeat;
          margin-top: 0.37rem;
          margin-bottom: 0.25rem;
        }
      }
      .txt{
        display: inline-block;
        .fz(34px);
        color: #fff;
      }
    }
    .bottom_box{
      background: #fff;
      padding-left: 0.43rem;
      padding-right: 0.3rem;
      padding-top: 0.6rem;
      margin-bottom: 1rem;
      .item_box{
        position: relative;
        border-left: 1px solid #dfe8ff;
        padding-left: 0.7rem;
        &:after{
          position: absolute;
          content:'';
          left: -0.13rem;
          top: 0.07rem;
          width: 0.2rem;
          height: 0.2rem;
          border-radius: 50%;
          background: #fff;
          box-shadow: 0 0 0 0.07rem #fff;
          border: 0.05rem solid #e1e9fd;
        }
        .time{
          .fz(28px);
          padding-bottom: 0.2rem;
          color: #a7b5cb;
        }
        .des{
          overflow: hidden;
          .txt{
            width: 4.2rem;
            float: left;
            color: #253e67;
            .fz(28px);
          }
          .ico{
            float: right;
            width: 1.0rem;
            height: 0.96rem;
            background-size: cover;
            background-repeat: no-repeat;
            position: relative;
            .ico_txt{
              color: #a7b5cb;
              width: 0.4rem;
              padding: 0.07rem 0;
              text-align: center;
              position: absolute;
              margin: auto;
              left: 0;
              top: 0;
              bottom: 0;
              right: 0;
            }
          }
        }
        .addr_box{
          padding-top: 0.3rem;
          padding-bottom: 0.82rem;
          overflow: hidden;
          color: #a7b5cb;
          .fz(24px);
          .addr{
            float: left;
          }
          .name{
            float: right;
          }
        
        }
      }
    }
  }
</style>
