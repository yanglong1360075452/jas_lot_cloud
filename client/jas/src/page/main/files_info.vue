<template>
  <div class="page_files_info top_fixed no_content_bg_color" v-wechat-title="$route.meta.title">

    <router-view></router-view>

    <div class="msg_top">
      <div class="txt"><span>*病历信息</span><a href="javascript:"><!-- 查看说明 --></a></div>
      <div class="btn jas_btn" @click="to_medicalArchival_fn(page_tab_files_data)">编辑</div>
    </div>

    <div class="item_box item_box1">
      <div class="item_more">
        <span class="text">开始时间</span> <i class="next_ico "></i>
        <!-- <div class="text2">2016.11.11</div> -->
        <div class="text2">{{page_tab_files_data.startTime | date_format('YYYY.MM.DD')}}</div>
      </div>

      <div class="item_more">
        <span class="text">病情说明</span> <i class="next_ico "></i>
        <!-- <div class="text2">近期手术</div> -->
        <div class="text2">{{page_tab_files_data.illnessState | code2name(dict_illnessState)}}</div>
      </div>

      <div class="item_more">
        <span class="text">活动不足因素</span> <i class="next_ico "></i>
        <!-- <div class="text2">长时间固定</div> -->
        <div class="text2">{{page_tab_files_data.activity | code2name(dict_nactivityFactor)}}</div>
      </div>

      <div class="item_more">
        <span class="text">病程</span> <i class="next_ico "></i>
        <!-- <div class="text2">1周</div> -->
        <div class="text2">{{page_tab_files_data.process | code2name(dict_diseaseCourse)}}</div>
      </div>

      <div class="item_more">
        <span class="text">手术医院</span> <i class="next_ico "></i>
        <!-- <div class="text2">邵逸夫医院</div> -->
        <div class="text2">{{page_tab_files_data.hospital | code2name(dict_hospitals)}}</div>
      </div>

      <div class="item_more">
        <span class="text">手术科室</span> <i class="next_ico "></i>
        <!-- <div class="text2">外科</div> -->
        <div class="text2">{{page_tab_files_data.department | code2name(dict_departments)}}</div>
      </div>

      <div class="item_more">
        <span class="text">主刀医生</span> <i class="next_ico "></i>
        <!-- <div class="text2">马云华</div> -->
        <div class="text2">{{page_tab_files_data.doctorName}}</div>
      </div>

    </div>
    <div class="item_box2">
      
      <div class="item_more des">
        <span class="text">病历描述</span> <i class="next_ico "></i>
        <!-- <div class="text2">患者过去有癫痫病史。从事长时间高强度工作后，经常发作，发作时口吐白沫，不省人事。体检身体欠佳，精神不振，营养不良。建议该患者住院观察。</div> -->
        <div class="text2">{{page_tab_files_data.content}}</div>
      </div>

      <div class="img_box img_up_pre">
        <div class="imgs" v-for="(item, key) in obj" @click="checkPhotograph(key)">
          <img :src="item[0].src">
          <div class="title" :class="{boxItemActive:key!=='-1'?true:false}">{{item[0].type}}({{item.length}}张)</div>
        </div>
      </div>

    </div>

  </div>
</template>

<script>

  var temp={
    activity:1,
    content:"不用治疗",
    createTime:1504519975000,
    department:1,
    doctorName:"王二小",
    hospital:11,
    id:72,
    illnessState:1,
    patientId:17,
    process:1,
    startTime:1505260800000,
    surgeryTime:null,
    updateTime:1504519975000,          
  }

  import api_login_reg from '../../api/login_reg.js'
  import {date_format, code2name} from 'assets/js/common.js'
  import api_main from '../../api/main.js'
  import {dateFormat} from 'vux'
  export default {
    data(){
      return {
        page_tab_files_data:'', /*病历信息*/

        dict_illnessState:'', // 病情说明列表
        dict_nactivityFactor:'', // 活动不足因素列表
        dict_diseaseCourse:'', // 病程列表查询
        dict_hospitals:'', // 医院列表
        dict_departments:'', // 科室列表
        dict_boxItem: [ // 图片列表
          // {
          //   indexType: -1,
          //   type: '未分',
          //   time: '2017-10-22',
          //   src: "static/img@2x.jpg"
          // },
        ],
        obj: {},
      }
    },
    created(){

      var store_arr=[
        'dict_illnessState',
        'dict_nactivityFactor',
        'dict_diseaseCourse',
        'dict_hospitals',
        'dict_departments',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      if(this.$store.state.temp_data.files){
        this.page_tab_files_data=this.$store.state.temp_data.files.data;
      }

      if(this.page_tab_files_data.clinicalDataDtoList != undefined){
        for(var i=0; i<this.page_tab_files_data.clinicalDataDtoList.length; i++){
          this.page_tab_files_data.clinicalDataDtoList[i].src = 'data:image/jpg;base64,' + this.page_tab_files_data.clinicalDataDtoList[i].responseEntity.body;
        }
        this.dict_boxItem = this.page_tab_files_data.clinicalDataDtoList;
        this.dict_boxItem.forEach((item)=>{
          if(this.obj[item.indexType] == undefined){
            this.obj[item.indexType] = [];
          }
          this.obj[item.indexType].push(item);
        });

        console.log(this.page_tab_files_data.clinicalDataDtoList)
        
        this.$store.commit('compile_photograph', this.page_tab_files_data.clinicalDataDtoList);
      }else{
        this.$store.commit('compile_photograph', []);
      }
      
    },
    components:{},
    filters:{
      date_format: function(val,form){
        return dateFormat(new Date(val), form);
      },
      code2name: function(code,arr){
        return code2name(code, arr);
      },
      age_format: function(val,form){
        val=val && val;
        return dateFormat(new Date(), 'YYYY')-dateFormat(new Date(val), 'YYYY');
      }
    },
    methods:{
      to_medicalArchival_fn(page_tab_files_data){
        this.$router.replace({name:'medicalArchival', params:{'page_tab_files_data':page_tab_files_data}})
      },
      checkPhotograph(key) {
        this.$router.replace({
          name: 'userPhotograph',
          query: {
            key : key
          }
        });
      },
    }
  }
</script>

<style lang="less">
  @import '../../style/common.less';
  @import '../../style/variable.less';
  .page_files_info{
    .item_more{
      &:after{
        content: '';
        display: block;
        position: absolute;
        width: 92%;
        height: 1px;
        bottom: 0;
        background: #dfe8ff;
      }
      .next_ico {
        right: .22rem;
      }
    }

    .item_box1{
      .item_more{
        &:last-of-type{
          &:after{
            display: none;
          }
        }
      }
    }
    .item_box2{
      margin-top: 0.2rem;
      background: #fff;
      .item_more{
        height: auto;
        overflow: hidden;
        line-height: normal;
        padding-top: 0.28rem;
        padding-bottom: 0.4rem;
        .next_ico{
          top: 0;
        }
        .text{
          float: left;
          width: 18%;
          white-space: nowrap;
        }
        .text2{
          display: block;
          float: right;
          width: 70%;
          text-indent: 0;
          line-height: normal;
          padding-right: 0.5rem;
        }
      }
      .img_box{
        padding-left: 0.3rem;
        padding-right: 0.3rem;
        .imgs{
          .boxItemActive{
            background: #4576f0;
          }
          img{
            width: 100%;
          }
        }
      }

    }
  }
</style>
