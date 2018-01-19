<template>
  <div class="page_medicalArchival top_fixed no_content_bg_color" v-wechat-title="$route.meta.title">

    <router-view></router-view>

    <!-- 顶部提示 -->
    <div class="msg_top">
      <div class="txt">*病历信息请认真填写</div>
      <div v-show="!(temp_add_files || temp_edit_files)" class="btn jas_btn" @click="to_exerciseProcess_fn()">跳过</div>
      <div v-show="temp_edit_files && user_type!='1'" class="btn jas_btn" @click="del_files_fn()">删除</div>
    </div>

    <div class="ipt_box">

      <!-- 开始时间 -->
      <div class="item_box">
        <datetime
          class="w_item_more"
          year-row="{value}年"
          month-row="{value}月"
          v-model="save_data.startTime"
          day-row="{value}日"
          hour-row="{value}点"
          minute-row="{value}分"
          @on-change="change" :title="'Birthday'">
          <div class="item_more">  <span class="text">开始时间</span> <div class="text2">{{save_data.startTime}}</div> <i class="next_ico ico_more"></i> </div>
        </datetime>
      </div>

      <!-- 病情说明 -->
      <div class="select_btn_box">
        <div class="title">病情说明</div>
        <div class="btn_box">

          <checker v-model="save_data.explain" default-item-class="jas_btn" selected-item-class="active">
            <checker-item :value="item.code" v-for="(item, index) in dict_illnessState" :key="index">{{item.name}}</checker-item>
          </checker>

        </div>
      </div>
    </div>

    <!-- 活动不足因素 -->
    <div class="ipt_box">
      <div class="select_btn_box">
        <div class="title">活动不足因素</div>
        <div class="btn_box">

          <checker v-model="save_data.activity" default-item-class="jas_btn" selected-item-class="active">
            <checker-item :value="item.code" v-for="(item, index) in dict_nactivityFactor" :key="index">{{item.name}}</checker-item>
          </checker>

        </div>
      </div>
    </div>

    <!-- 病程 -->
    <div class="ipt_box">
      <div class="select_btn_box">
        <div class="title">病程</div>
        <div class="btn_box">

          <checker v-model="save_data.process" default-item-class="jas_btn" selected-item-class="active">
            <checker-item :value="item.code" v-for="(item, index) in dict_diseaseCourse" :key="index">{{item.name}}</checker-item>
          </checker>

        </div>
      </div>
    </div>

    <!-- 医院信息 -->
    <div class="ipt_box">
      <div class="item_box">
        <div class="item_more" @click="to_select_hospital_fn()">
          <span class="text">手术医院</span> <span class="text2">{{save_data.hospital | code2name(dict_hospitals)}}</span><i class="next_ico ico_more"></i>
        </div>
        <div class="item_more" @click="to_select_department_fn()">
          <span class="text">手术科室</span> <span class="text2">{{save_data.department | code2name(dict_departments)}}</span> <i class="next_ico ico_more"></i>
        </div>
        <div class="item_more" @click="openPrompt()">
          <span class="text">主刀医生</span>
          <span class="text2">{{save_data.doctorName}}</span>
          <i class="next_ico ico_more"></i>
        </div>
      </div>
    </div>

    <div class="ipt_box des_img">
      <!-- 描述 -->
      <div class="xtextarea_box">
        <XTextarea :autosize="true" placeholder="请用一段简短的话描述" v-model="save_data.describe"></XTextarea>
      </div>

      <!-- 图片 -->
      <div class="img_box img_up_pre">
        <div class="imgs" @click="compile_photograph(index)" v-for="(item,index) in boxItem" v-show="boxItem">
          <div class="img">
            <img :src="item.src" class="image">
            <div class="close" @click.prevent.stop="deleteImg(index)"></div>
          </div>
          <div class="title" :class="{active:item.type!=='未分类'}">{{item.type}}</div>
        </div>
        <div @click="menu_fn" class="imgs null" v-if="boxItem.length<9"></div>
      </div>
    </div>

    <div class="button_box" @click="next_fn">
      <mt-button class="button">{{(temp_add_files || temp_edit_files) ? '完成' : '下一步'}}</mt-button>
    </div>

    <Toast v-if="toast_show" type="text" v-model="toast_show">{{toast_text}}</Toast>
  </div>
</template>

<script>
  import { Toast, XTextarea, Checker, CheckerItem, XSwitch , Datetime, XButton } from 'vux'
  import { Button, MessageBox } from 'mint-ui';
  import api_main from 'api/main.js';
  import { date_format, code2name } from 'assets/js/common.js';
  import api_login_reg from '../../api/login_reg.js'
  import api from 'api/main.js';
  
  export default {
    data (){
      return {
        user_type:'',

        page_tab_files_data:'',
        temp_add_files:false,
        temp_edit_files:false,

        dict_illnessState:'', /*病情说明列表*/
        dict_nactivityFactor:'', /*活动不足因素列表*/
        dict_diseaseCourse:'', /*病程列表查询*/
        dict_hospitals:'', /*病程列表查询*/
        dict_departments:'', /*病程列表查询*/

        save_data:{
          startTime:'', /*开始时间*/
          explain:'', /*病情说明*/
          activity:'', /*活动不足因素*/
          process:'', /*病程*/
          hospital:'', /*手术医院*/
          department:'', /*手术科室*/
          doctorName:'', /*主刀医生*/
          describe:'', /*描述*/
          clinicalDataDtoList: [], /*创建图片*/
          aedclinicalDataDtoList: [ /*编辑图片 0增加 1修改 2删除*/ 
            [], 
            [],
            []
          ] 
        },
        toast_show:false,
        toast_text:'',
        user:'', // 用户表
        doctor:'', // 治疗师表
        medicalArchival:'', // 病历档案表
        v_if_medicalArchival:true,
        medicalArchival_temp:'',
        boxItem: [], // 病例档案表-相册
        localIds: [
					// "static/img@2x.jpg"
        ],
        maxNum: 9,
        albums: 0,
        newAlbums: 0, //新上传图片数量
      }
    },
    created(){

      var store_arr=[
        'user',
        'doctor',
        'medicalArchival',
        'medicalArchival_temp',

        'dict_illnessState',
        'dict_nactivityFactor',
        'dict_diseaseCourse',
        'dict_hospitals',
        'dict_departments',
      ];

      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      this.user_type = this.$store.state.user_type;

      if(this.$route.params.department){
        this.up_temp_data();
        this.save_data.department=this.$route.params.department.code ;
      }

      if(this.$route.params.hospital){
        this.up_temp_data();
        this.save_data.hospital=this.$route.params.hospital.code ;
      }

      this.temp_add_files=$_temp.add_files;
      this.temp_edit_files=this.$store.state.temp_data.files ? this.$store.state.temp_data.files.edit_files : undefined;

      if($_temp.page_tab_files_data2){

      }else if(this.temp_edit_files){
        
        let data=this.$store.state.temp_data.files.data;
        // this.page_tab_files_data=$_temp.page_tab_files_data;
        // this.save_data.page_tab_files_data=page_tab_files_data;
        this.save_data.startTime=date_format(data.startTime, 'YYYY-MM-DD');
        this.save_data.explain=data.illnessState;
        this.save_data.activity=data.activity;
        this.save_data.process=data.process;
        this.save_data.hospital=data.hospital;
        this.save_data.department=data.department;
        this.save_data.doctorName=data.doctorName;
				this.save_data.describe=data.content;
				// this.save_data.clinicalDataDtoList=data.clinicalDataDtoList;
        $_temp.page_tab_files_data2=data;
        delete $_temp.page_tab_files_data;
      };
      
      this.boxItem = this.$store.state.medicalArchival.boxItem;
      this.albums = this.boxItem.length;
      this.$store.commit('compile_clinicalDataDtoList',[]);

      console.log(this.boxItem);

      this.dict_illnessState=this.$store.state.dict_illnessState ;/*病情说明列表*/
      this.dict_nactivityFactor=this.$store.state.dict_nactivityFactor ;/*活动不足因素列表*/
      this.dict_diseaseCourse=this.$store.state.dict_diseaseCourse ;/*病程列表查询*/
      // this.save_data.aedclinicalDataDtoList = this.$store.state.medicalArchival.aedclinicalDataDtoList;

      this.up_temp_data();

      // 获取微信授权接口
      var href = window.location.href || document.location.href;
      var url = href.split('#')[0];
      var wxObject = {};
      api.get_wx_config(url).then(res => {
          // console.log(res);
          wxObject = Object.assign(res,{
              debug: false, //是否开启微信调试模式
              jsApiList: [    //需要使用的js接口列表
                  'chooseImage',
                  'uploadImage',
              ] 
          });

					wx.config(wxObject);

      }).catch(error => {
          console.log(error);
      });
    },
    filters:{ 
      date_format:function(val, form){
        return date_format(val, form)
      },
      code2name:function(code, arr){
        return code2name(code, arr)
      }
    },
    components:{
      XTextarea,
      'mtButton':Button,
      Datetime,
      XButton,
      Checker,
      CheckerItem,
      Toast,
      XSwitch
    },
    methods:{
      openConfirm() {
        MessageBox.confirm('', {
          title: '',
          message: '是否删除此病历',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false
        }).then(action => {
          if (action == 'confirm') {
            console.log('删除了',)
            this.del_files_fn(true);
          }
        }).catch(err => {
            if (err == 'cancel') {
            }
        });
      },
      del_files_fn(is){
        if(!is) {
          this.openConfirm();
        } else{
          api_main.del_files(this.$store.state.temp_data.files.data.id,{}).then(res=>{
            console.log('删除病历成功', this.$store.state.temp_data.files.data.id);
            delete $_temp.medicalArchival;
            delete $_temp.page_tab_files_data2;
            // this.toast_show=true;
            // this.toast_text='编辑病历成功';
            this.$router.push({name:'tab_files'});
          },err=>{
            console.log('编辑病历失败');
            this.toast_show=true;
            this.toast_text='编辑病历失败';
          })
        } ;
      },
      up_temp_data(){
        if($_temp.medicalArchival){
          this.save_data=$_temp.medicalArchival;
        }
      },
      openPrompt() {
        MessageBox.prompt(' ', '主刀医生名字',{
          inputValue: this.save_data.doctorName ? this.save_data.doctorName : '',
        }).then(({ value }) => {
          if (value) {
            this.save_data.doctorName=value;
          }
        }).catch(err => {
          if (err == 'cancel') {
          }
        });
      },
      change (value) {
        // this.medicalArchival.startTime=value;
        console.log('change', value);
      },
      to_exerciseProcess_fn(){
        this.$router.push('exerciseProcess');
			},
			uploadImage(localId) {
				return new Promise((resolve,reject)=>{
					// 微信上传图片
					wx.uploadImage({
							localId: localId, //需要上传的图片本地的id，由chooseImage接口获得
							isShowProgressTips: 1, // 默认为1，显示进度提示
							success: function(res){
									resolve(res.serverId);
							}
					});
				});
			},

      async ulLoadToWechat(){
        
        var nextLength = this.newAlbums; // 本次上传的图片数量
        var preLength = this.save_data.clinicalDataDtoList.length-nextLength; //之前拥有的图片数量

        if(nextLength != 0){
          for(var i=0; i<nextLength; i++){
            this.save_data.clinicalDataDtoList[preLength+i].src = await this.uploadImage(this.localIds[i]);
          }
        }else{
          return
        }
			},

      async next_fn(){

        // this.boxItem = this.$store.state.medicalArchival.boxItem;
        this.save_data.clinicalDataDtoList = this.$store.state.medicalArchival.boxItem;

				await this.ulLoadToWechat();

        console.log(this.save_data.clinicalDataDtoList);

        var data= {
           "startTime":+new Date(this.save_data.startTime), // 开始时间
           "illnessState":this.save_data.explain, // 病情说明
           "activity":this.save_data.activity, // 活动因素不足
           "process":this.save_data.process, // 病程
           "department":this.save_data.department, // 科室
           "hospital":this.save_data.hospital, // 医院
           "doctorName":this.save_data.doctorName, // 主刀医生名字
           "content":this.save_data.describe, // 病例描述
          }

        if(this.temp_edit_files){ /*编辑病历*/

          var res = this.save_data.aedclinicalDataDtoList[1];

          this.save_data.aedclinicalDataDtoList = this.$store.state.medicalArchival.aedclinicalDataDtoList;
          this.save_data.aedclinicalDataDtoList[0] = [];

          for(var i=0; i<this.save_data.clinicalDataDtoList.length; i++){
            if(this.save_data.clinicalDataDtoList[i].id == undefined){
              this.save_data.aedclinicalDataDtoList[0].push(this.save_data.clinicalDataDtoList[i]);
            }else{
              continue
            }
          }

          if(this.save_data.aedclinicalDataDtoList[0] == undefined){
            this.save_data.aedclinicalDataDtoList[0] = [];
          }

          if(this.save_data.aedclinicalDataDtoList[1] == undefined){
            this.save_data.aedclinicalDataDtoList[1] = [];
          }else{
            if(this.save_data.aedclinicalDataDtoList[1].length != 0){
              for(var i=0; i<this.save_data.aedclinicalDataDtoList[1].length; i++){
                this.save_data.aedclinicalDataDtoList[1][i].responseEntity = null;
              }
            }
          }

          if(this.save_data.aedclinicalDataDtoList[2] == undefined){
            this.save_data.aedclinicalDataDtoList[2] = [];
          }

          console.log(this.save_data.aedclinicalDataDtoList);

          data['aedClinicalDataDtoList'] = this.save_data.aedclinicalDataDtoList; // 图片

          console.log(data);

          console.log('编辑病历', data);
          api_main.up_files($_temp.page_tab_files_data2.id, data).then(res=>{
            data.patientId = undefined; /*病人id*/
            data.surgeryTime = undefined; /*手术时间*/

            this.save_data.aedclinicalDataDtoList[1] = res;

            console.log('编辑病历成功', $_temp.page_tab_files_data2.id);
            delete $_temp.medicalArchival;
            delete $_temp.page_tab_files_data2;
            // this.toast_show=true;
            // this.toast_text='编辑病历成功';
            this.$router.replace({name:'tab_files'});
          },err=>{
            console.log('编辑病历失败');
            this.toast_show=true;
            this.toast_text='编辑病历失败';
          })


        }else{ /*添加病历*/

          data['clinicalDataDtoList'] = this.save_data.clinicalDataDtoList // 图片

          console.log(data);

          console.log('添加病历');
          let patientId=this.user_type==1 ? this.$store.state.temp_data.user_data.data.patientId : '';
          api_login_reg.create_case(patientId,data).then(res=>{
            console.log('添加病历成功');
            delete $_temp.medicalArchival;
            // this.toast_show=true;
            // this.toast_text='添加病历成功';
            if(this.temp_add_files){
              this.$router.replace({name:'tab_files'});
            }else{
              this.$router.replace('exerciseProcess');
            }
          },err=>{
            console.log('添加病历失败');
            this.toast_show=true;
            this.toast_text='添加病历失败';
          })

        }

      },
      menu_fn() {

        var _this = this;

        // 微信选择图片接口
        wx.chooseImage({
          count: _this.maxNum - _this.albums, // 图片最多数量
          sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
          sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
          success: function(res){

            var imgList = [];

            _this.newAlbums = res.localIds.length;

            _this.localIds = _this.localIds.length == 0 ? res.localIds : [..._this.localIds,...res.localIds];

            // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            res.localIds.forEach((localId) => {
              var obj = {
                  indexType: -1,
                  type: '未分类',
                  time: '',
                  src: localId,
              };
              imgList.push(obj);
            });

            _this.albums = _this.localIds.length;

            _this.boxItem = [..._this.boxItem, ...imgList];
						_this.$store.commit('compile_photograph', _this.boxItem);
            _this.$router.replace({
              name: 'compilePhotograph'
            });

						console.log(_this.boxItem);
						
          }
        });
      },

      to_select_hospital_fn(){
        $_temp.medicalArchival=this.save_data;
        this.$router.replace({name:'select_hospital' ,params:{prev_page:'medicalArchival'}});
      },
      to_select_department_fn(){
        $_temp.medicalArchival=this.save_data;
        this.$router.replace({name:'select_department' ,params:{prev_page:'medicalArchival'}});
      },
      compile_photograph(index){
        this.$router.replace({name: 'compilePhotograph', query:{index: index}});
      },
      //删除照片
      deleteImg(index) {

        // 编辑 - 删除有id的图片
        if(this.boxItem[index].id != undefined){
          
          var boxItem_obj = {
            id: this.boxItem[index].id
          };

          this.save_data.aedclinicalDataDtoList[2].push(boxItem_obj);
          this.$store.commit('compile_delClinicalDataDtoList', this.save_data.aedclinicalDataDtoList[2]);
          
          this.save_data.aedclinicalDataDtoList[1] = this.$store.state.medicalArchival.aedclinicalDataDtoList[1];

          if(this.save_data.aedclinicalDataDtoList[1] != undefined){
            var arr = []; // 存放删除的图片id

            for(var i=0; i<this.save_data.aedclinicalDataDtoList[1].length; i++){
              for(var j=0; j<this.save_data.aedclinicalDataDtoList[2].length; j++){
                if(this.save_data.aedclinicalDataDtoList[1][i].id === this.save_data.aedclinicalDataDtoList[2][j].id){
                  arr.push(i);
                }else{
                  continue;
                }
              }
            }

            for(var k in arr){
              this.save_data.aedclinicalDataDtoList[1].splice(arr[k], 1);
            }
            
          }else{
            this.save_data.aedclinicalDataDtoList[1] = [];
          }

          this.$store.commit('compile_editClinicalDataDtoList', this.save_data.aedclinicalDataDtoList[1]);
          
          console.log(this.save_data.aedclinicalDataDtoList);

        }

        this.localIds.splice(index, 1);
        this.boxItem.splice(index, 1);
        this.$store.commit('compile_photograph',this.boxItem);
        console.log(this.boxItem);
      }
    }
  }

</script>

<style lang="less">
  @import '../../style/page_exerciseProcess.less';
</style>

<style lang="less" scoped>
</style>
