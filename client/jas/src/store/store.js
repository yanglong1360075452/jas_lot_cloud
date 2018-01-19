/**
 * Created by yonghuapro on 2017/4/28.
 */
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


function concat_obj(target, source) { // 合并对象
  for (var i in source) {
    target[i] = source[i];
  }
  return target;
}


export default new Vuex.Store({
  state:{
    loading: false, // 显示 loading
    user_type:'', // 用户类型 0 病人， 1 医生
    token:'', // token
    wx_img:'', // 微信头像
    openid:'', // openid
    medicalArchival_temp:{ // 编辑病历档案
      explain:{}, // 病情说明
      activity:{}, // 活动不足因素
      process:{}, // 病程
    },
    member_list_data:[], // 成员列表
    medicalArchival:{ // 病历档案表
      id: '', // 主键id
      patient_id: '', // 病人id（外键）
      surgery_time: '', // 手术时间
      startTime: '', // 初诊时间
      explain: [], // 病情说明
      activity: [], // 活动不足
      process: [], // 病程
      department: [], // 科室
      hospital: '', // 手术医院
      doctorName: '', // 主刀医生
      describe: '', // 病历描述
      createtime: '', // 创建时间
      updatetime: '', // 更新时间
      boxItem: [  // 添加相册
        // {
        //   indexType: -1, // 描述图片的索引值
        //   type: '未分类',
        //   time: '2017-09-02',
        //   src: "static/img@2x.jpg",
        // },
        // {
        //   indexType: 0,
        //   type: '术前',
        //   time: '2017-09-02',
        //   src: "static/img@2x.jpg", // serverId
        // },
      ],
      aedclinicalDataDtoList: [
        [],
        [],
        []
      ] //0添加 1修改 2删除
    },
    exerciseProcess:{ // 训练计划表
      id: '', // 主键id
      patient_id: '', // 病人id（外键）
      doctor_id: '', // 治疗师id（外键）
      depAndret: [], // 屈伸-方向
      lefAndrig: [], // 部位-左右
      arthro:[], // 关节列表
      startTime: '', // 训练过程开始时间
      endTime: '', // 训练过程结束时间
      create_by: '', // 创建人
      update_by: '', // 更新人
      sign: '', // 治疗师有没有读到
      createtime: '', // 创建时间
      updatetime: '' // 更新时间
    },
    user:{ // 用户表
      id: '', // 主键id
      openid: '', // 用户标识
      realname: '', // 真实姓名 // 李会长
      sex:2, // 性别
      phone: '', // 手机号码 // 18817352333
      birth: '', // 出生年月
      scoreValue: '', // 积分分值
      headPortrait: '', // 用户头像
      createtime: '', // 创建时间
      updatetime: '', // 更新时间
      is_new_user_xw: true, // 是否新用户
    },
    doctor:{ // 治疗师表
      id: '', // 主键id
      userId: '', // 用户id（外键）
      hospital: '', // 医院
      professiona: '', // 职称
      description: '', // 简介
      department: '', // 科室
    },
    user_detail:{}, // 用户详情
    routerRecord: '', //路由记录
    
    /*字典*/
    dict_hospitals:'', /*医院列表*/
    dict_departments:'', /*科室列表*/
    dict_appraisal:'', /*评估结论列表*/
    dict_illnessState:'', // 病情说明列表
    dict_nactivityFactor:'', // 活动不足因素列表
    dict_diseaseCourse:'', // 病程列表查询
    dict_pictureLabel:'', // 照片标签/分类
    dict_arthro:'', // 获取关节列表
    dict_postion:'', // 获取部位列表
    dict_direction:'', // 获取方向列表

    /*暂存*/
    temp_data:{},
  },
  mutations: {
    wx_img_up(state,url) {
      state.wx_img = url;
    },
    saveIntroduction(state,value) {
      state.doctor.description = value;
    },
    startLoading (state) {
      state.loading = true;
    },
    stopLoading (state) {
      state.loading = false;
    },
    user_up (state, obj) {
      concat_obj(state.user, obj);;
    },
    token_up (state, token) {
      state.token=token;
    },
    medicalArchival_up (state, obj) {
      concat_obj(state.medicalArchival, obj);;
    },
    openid_up (state, openid) {
      state.openid=openid;
    },
    user_type_up (state, type) {
      state.user_type=type;
    },
    doctor_up (state, obj) {
      concat_obj(state.doctor, obj);;
    },
    member_list_data_up (state, obj) {
      concat_obj(state.member_list_data, obj);;
    },
    user_detail_up(state,obj){
      concat_obj(state.user_detail, obj);;
    },
    doctor_detail_up(state,obj){
      concat_obj(state.doctor, obj);;
    },
    compile_photograph(state, boxItem){ //设置病例档案相册
      state.medicalArchival.boxItem = boxItem;
    },
    route_record(state, param) {
      state.routerRecord = param;
    },
    compile_clinicalDataDtoList(state, aedclinicalDataDtoList){
      state.medicalArchival.aedclinicalDataDtoList = aedclinicalDataDtoList;
    },
    compile_addClinicalDataDtoList(state, addClinicalDataDtoList){ //添加
      state.medicalArchival.aedclinicalDataDtoList[0] = addClinicalDataDtoList;
    },
    compile_editClinicalDataDtoList(state, editClinicalDataDtoList){ //修改
      state.medicalArchival.aedclinicalDataDtoList[1] = editClinicalDataDtoList;
    },
    compile_delClinicalDataDtoList(state, delClinicalDataDtoList){ //删除
      state.medicalArchival.aedclinicalDataDtoList[2] = delClinicalDataDtoList;
    },

    /*字典*/
    dict_hospitals_up(state,obj){state.dict_hospitals=obj},
    dict_departments_up(state,obj){state.dict_departments=obj},
    dict_appraisal_up(state,obj){state.dict_appraisal=obj},
    dict_illnessState_up(state,obj){state.dict_illnessState=obj},
    dict_nactivityFactor_up(state,obj){state.dict_nactivityFactor=obj},
    dict_diseaseCourse_up(state,obj){state.dict_diseaseCourse=obj},
    dict_pictureLabel_up(state,obj){state.dict_pictureLabel=obj},
    dict_arthro_up(state,obj){state.dict_arthro=obj},
    dict_postion_up(state,obj){state.dict_postion=obj},
    dict_direction_up(state,obj){state.dict_direction=obj},

    /*暂存*/
    temp_data_up(state,obj){state.temp_data=obj},
  }
})
