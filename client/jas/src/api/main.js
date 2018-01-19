import Vue from 'vue'
import {obj2par} from '../assets/js/common.js'
export default{
  AvatarDefault(openId) {
    return Vue.http.get('wechat/auth/user/getWechatHeadImg/'+openId);
  },
  init_code_url(url){ // 发送二维码请求
    return Vue.http.get(url);
  },
  get_wx_config(url) {
    return Vue.http.get('/wechat/auth/js',{params:{url}});
  },
  get_scoredetail(){ // 用户查询积分明细
    return Vue.http.get('/wechat/score/getScoreDetail');
  },
  get_change_list(){ // 积分兑换记录
    return Vue.http.get('/wechat/score/getChangeRecordList');
  },
  get_user_detail(){ // 查看用户详细信息
    return Vue.http.get('/wechat/user/userDetail');
  },
  get_doctor_detail(){ // 查看医生详细信息
    return Vue.http.get('wechat/user/doctorDetail');
  },
  update_user_detail(data,code){ // 保存用户详细信息
    return Vue.http.post('wechat/user/update',data,{params:{code}});
  },
  get_coupon(){ // 查看用户已兑换且没有过期的优惠券列表
    return Vue.http.get('/wechat/score/getCouponListByUserId');
  },
  get_history_coupon(){ // 查看用户历史优惠券列表 
    return Vue.http.get('/wechat/score/getHistoryCouponList');
  },
  get_exchange_coupon(){ // 查看所有没有过期且能够兑换的优惠券列表
    return Vue.http.get('/wechat/score/getCouponList');
  },
  set_coupon(scoreChange, couponId){ // 用户兑换优惠券
      return Vue.http.get('/wechat/score/createScore',{params:{
        scoreChange,
        couponId
      }});
  },
  vaild_captcha(captcha){  // 修改手机-之前判断验证码是否正确
    return Vue.http.get('/wechat/user/vaildCaptcha',{params:{
      captcha
    }});
  },
  update_phone(phone, captcha){ // 修改手机-之后判断新手机是否存在以及验证码是否正确
    return Vue.http.get('/wechat/user/vaildCaptcha?',{params:{
      captcha,
      phone
    }});
  },
  get_doctors_list(){ // 根据康复计划获取所有的康复师详细信息
    return Vue.http.get('/wechat/exerciseProcess/doctors');
  },
  get_doctors_plan(doctorId){ // 病人看康复师对应的康复计划
    return Vue.http.get('/wechat/exerciseProcess?doctorId='+doctorId);
  },
  get_head_img(userId){ // 病人和医生详情显示头像
    return Vue.http.get('/wechat/user/getHeadImg?userId='+userId);
  },
  get_plan(){ // 病人获取康复计划列表
    return Vue.http.get('/wechat/exerciseProcess/eps');
  },
  get_files(patientId){ // 获取病历档案
    return Vue.http.get('/wechat/ma?patientId='+patientId);
  },
  get_comment(epId){ // 根据计划id获取评估记录
    return Vue.http.get('/wechat/report/'+epId);
  },
  get_classify(){  // 照片标签、分类
    return Vue.http.get('/wechat/dict/pictureLabel');
  },
  get_dev(){ // 查看我的设备
    return Vue.http.post('/wechat/device/queryDevice');
  },
  del_dev(deviceId){ // 解除设备绑定
    return Vue.http.post('/wechat/device/removeDevice?deviceId='+deviceId);
  },
  up_exerciseProcess(epId,data){ // 更新训练计划
    return Vue.http.put('/wechat/exerciseProcess?epId='+epId, data);
  },
  get_exercise_week_month(epId, startTime,endTime){ // 周训练记录
    return Vue.http.get('wechat/exerciseRecord/getExerciseRecordWeekMonth',{params:{
      epId,
      startTime,
      endTime
    }});
  },
  get_list_week_month(epId, startTime,endTime){ // 通过康复计划id和开始结束时间得到每天的训练记录
    return Vue.http.get('/wechat/exerciseRecord/getExerciseRecordDayByTime',{params:{
      epId,
      startTime,
      endTime
    }});
  },
  up_files(maId,data){ // 编辑病历信息
    return Vue.http.put('/wechat/ma/'+maId, data);
  },
  del_files(maId,data){ // 删除病历档案
    return Vue.http.delete('/wechat/ma/'+maId, data);
  },
  get_index_table(epId){ // 根据康复计划Id显示主页第一条最新的康复记录数据
    return Vue.http.get('/wechat/exerciseRecord/getExerciseRecordByepId?epId='+epId);
  },
  get_application(type,filter){ // 医生查询患者
    return Vue.http.get('/wechat/exerciseProcess/getPatientByPatientId/'+type,{params:filter});
  },
  get_exercise_record_detail(erId){ // 通过康复记录Id获取单次训练记录明细
    return Vue.http.get('/wechat/exerciseRecord/getExerciseRecordDetail',{params:erId});
  },
  get_doctor_see_user(patientId){ // 医生通过病人id查询病人信息
    return Vue.http.get('/wechat/user/'+patientId);
  },
  get_exercise_process(patientId){ // 康复师看病人的康复计划
    return Vue.http.get('/wechat/exerciseProcess/'+patientId);
  },
  ok_application(obj){ // 医生同意病人添加
    // var obj=patientId=18&type=1
    return Vue.http.put('/wechat/exerciseProcess/updateIsComfirmed?'+obj2par(obj), {});
  },
  get_every_data(obj){ // 获取指定时间所有单次图表数据
    // epId=1
    // startTime=1502503438313
    // endTime=1504503438313
    return Vue.http.get('/wechat/exerciseRecord/getExerciseRecordByepIdTime', {params:obj});
  },
  get_doctor_professiona(){ // 获取医生职称列表
    return Vue.http.get('/wechat/dict/professiona');
  },
  add_user_assess(epId, data){ // 创建报告
    return Vue.http.post('/wechat/report/'+epId, data);
  },

  /*字典-注：只有字典类型的接口才写在下面*/
  get_dict_hospitals(filter){ /*医院列表*/
    return Vue.http.get('/wechat/dict/hospitals?filter='+filter);
  },
  get_dict_departments(){  /*科室列表*/
    return Vue.http.get('/wechat/dict/departments');
  },
  get_dict_appraisal(){  /*评估结论列表*/
    return Vue.http.get('/wechat/dict/appraisal');
  },
  get_dict_illnessState(){ // 病情说明列表
    return Vue.http.get('/wechat/dict/illnessState');
  },
  get_dict_nactivityFactor(){ // 活动不足因素列表
    return Vue.http.get('/wechat/dict/nactivityFactor');
  },
  get_dict_diseaseCourse(){ // 病程列表查询
    return Vue.http.get('/wechat/dict/diseaseCourse');
  },
  get_dict_pictureLabel(){ // 照片标签/分类
    return Vue.http.get('/wechat/dict/pictureLabel');
  },
  get_dict_arthro(){ // 获取关节列表
    return Vue.http.get('/wechat/dict/arthro');
  },
  get_dict_postion(){ // 获取部位列表
    return Vue.http.get('/wechat/dict/postion');
  },
  get_dict_direction(){ // 获取方向列表
    return Vue.http.get('/wechat/dict/direction');
  },
}
