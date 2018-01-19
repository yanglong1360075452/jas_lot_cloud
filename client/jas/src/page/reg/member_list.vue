<template>
  <div class="page_member_list top_fixed no_content_bg_color" v-wechat-title="$route.meta.title">
    <div class="list_box">
      <swipeout>
        <swipeout-item :key="index" v-for="(item, index) in member_list_data" class="tag_li"
          @on-close="handleEvents('on-close')"
          @on-open="handleEvents('on-open')"
          transition-mode="follow">

          <div slot="right-menu" v-show="user_type==1">
            <swipeout-button @click.native="sel_item_fn2(index, item, '-1')" type="warn">拒绝</swipeout-button>
          </div>

          <div @click="info_item_fn(index, item)" slot="content" class="member_item vux-1px-t">
            <div class="img icon_head" v-wx_img="{'base64':item.responseEntity}"></div>
            <div class="txt">
              <div class="name"v-if="user_type==0">{{item.realName}}</div>
              <div class="name" v-if="user_type==1">

                {{item.userName}}

                <span class="plan">
                  {{
                    [,'左','右'][item.postion]+''+ 
                    [,'肘关节', '腕关节', '膝关节', '裸关节', '其他'][item.arthro]+'康复计划 - '+ 
                    [,'伸展','屈曲'][item.direction]+(item.overdue>'0' ? ' (历史)':'') 
                  }} 
                </span>
                
              </div>
              <div class="des"v-if="user_type==0">{{item.description}}</div>
              <div class="des" v-if="user_type==1">
                时间段 {{item.startTime | date_format('MM.DD')}}-{{item.endTime | date_format('MM.DD')}}
              </div>
            </div>
            <!-- 病人登录时按钮样式 -->
            <div class="btn jas_btn" v-show="user_type=='0'" 
              :class="{'accept': item.sta}" 
              @click.stop="sel_item_fn(index, item)"> {{item.sta ? '' : '选择'}}
            </div>
            <!-- 医生登录时按钮样式 -->
            <div class="btn jas_btn doctor" v-show="user_type=='1'" 
              :class="{'accept': item.isComfirmed=='1'}" 
              @click.stop="sel_item_fn2(index, item, '1')"> {{item.isComfirmed=='1' ? '已接受' : '接受'}}
            </div>
          </div>

        </swipeout-item>
      </swipeout>

    </div>
    <div v-show="show_no_data" class="temp_no_content no_data"></div>
  </div>
</template>

<style lang="less">
  @import '../../style/variable.less';
  @import '../../style/page_member_list.less';
</style>
<script>

  import api_login_reg from '../../api/login_reg.js'
  import api_main from '../../api/main.js'
  import {date_format, info_item_fn} from '../../assets/js/common.js'
  import { GroupTitle, Swipeout, SwipeoutItem, SwipeoutButton, XButton } from 'vux'


  export default {

    data () {
      return {
        show_no_data:false,
        disabled: false,
        medicalArchival: '', // 病历档案表
        member_list_data:[], // 成员列表
        user_type:'', 

      }
    },
    filters:{
      date_format:function(val,form){
        return date_format(val,form)
      }
    },
    components: {
      GroupTitle,
      Swipeout,
      SwipeoutItem,
      SwipeoutButton,
      XButton
    },
    methods: {
      info_item_fn(index,item){

        if(this.user_type==1) this.$router.push({name:'data_info', params:{plan_id:item.id}});
        
        let obj=this.$store.state.temp_data;

        item.dis_xw=true; // 新患者标志
        obj.plan_info={
          data:item
        };
        this.$store.commit('temp_data_up',obj)
      },
      sel_item_fn(index,item){ /*病人*/
        // this.member_list_data[index].sta=!this.member_list_data[index].sta; // 取反

        this.member_list_data.forEach(function(value){value.sta=false});
        this.member_list_data[index].sta=true; // 仅选择

        console.log('sel_item_fn',index,item);
        this.$store.commit('member_list_data_up',this.member_list_data);
        this.$router.replace({name:'exerciseProcess', params:{sel_member:item}});

      },
      sel_item_fn2(index,item, isComfirmed){ /*医生*/
        api_main.ok_application({'epId':item.id, 'isComfirmed':isComfirmed}).then(res=>{ // 接受或拒绝用户申请
          console.log('接受或拒绝用户申请',res);
          this.member_list_data.splice(index,1);
          if(this.member_list_data.length==0) this.$router.push({name:'main'}) ;
        },err=>{
          console.log(err);
        })
      },
      onButtonClick (type) {
        alert('on button click ' + type)
      },
      handleEvents (type) {
        console.log('event: ', type)
      },
      item_fn(name,code){
        this.$router.push({name:this.$route.params.prev_page, params: {'department':{'name':name, 'code':code}} });
      }
    },
    mounted(){
    },
    watch:{
    },
    computed:{
    },
    created(){
      this.user_type = this.$store.state.user_type;

      this.page_title=['康复师列表','新的患者'][this.$store.state.user_type];
      console.log(this.page_title);

      var store_arr=[
      'member_list_data',
      'medicalArchival',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      // api_login_reg.get_departments().then(res=>{/*获取科室列表*/ this.medicalArchival.department=res;},err=>{})

      if(this.$route.params.hospital && this.user_type==0){
        var code=this.$route.params.hospital.code;
        console.log('选择的医院',code);

        api_login_reg.get_doctor(code).then(res=>{ // 通过医院选择医生
          console.log('通过医院选择医生',res);
          this.member_list_data=res;
        },err=>{
          console.log(err);
        })
      }

      if(this.page_title='新的患者' && this.user_type==1){
        this.$route.meta.title='新的患者';
        api_main.get_application(0,{filter:''}).then( res => { // 医生查询患者 未验证
          console.log("医生查询患者 未验证",  res );
          this.member_list_data=res;
          // this.re_new_user_fn(this);
        },res=>{
          console.log('res', res)
        })


      }

    },
  }

</script>

