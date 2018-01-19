import Vue from 'vue'
export default{
  is_reg(code){ // 是否注册
    // 开发 users
    // 微信 user
    return Vue.http.get('/wechat/auth/users/'+code)
  },
  send_code(phone){ // 发送验证码
    return Vue.http.post('/wechat/authCode/'+phone,{})
  },
  register(obj){ // 用户注册
    return Vue.http.post('/wechat/register',obj)
  },
  hospitals_list(par){ // 医院列表
    return Vue.http.get('/wechat/dict/hospitals',{params:par})
  },
  user_info(code, data){ // 医生用户信息完善
    var url= code ? '/wechat/user/update?code='+code : '/wechat/user/update';
    return Vue.http.post(url, data);
  },
  get_departments(){ // 获取科室列表
    return Vue.http.get('/wechat/dict/departments');
  },
  get_doctor(code){ // 通过医院选择医生
    return Vue.http.get('/wechat/user/doctor/'+code);
  },


  /*病历档案--------------*/
  // get_illnessState(){ // 病情说明列表
  //   return Vue.http.get('/wechat/dict/illnessState');
  // },
  // get_nactivityFactor(){ // 活动不足因素列表
  //   return Vue.http.get('/wechat/dict/nactivityFactor');
  // },
  // get_diseaseCourse(){ // 病程列表查询
  //   return Vue.http.get('/wechat/dict/diseaseCourse');
  // },
  // get_pictureLabel(){ // 照片标签/分类
  //   return Vue.http.get('/wechat/dict/pictureLabel');
  // },
  // get_arthro(){ // 获取关节列表
  //   return Vue.http.get('/wechat/dict/arthro');
  // },
  // get_postion(){ // 获取部位列表
  //   return Vue.http.get('/wechat/dict/postion');
  // },
  // get_direction(){ // 获取方向列表
  //   return Vue.http.get('/wechat/dict/direction');
  // },
  /*病历档案--------------*/


  create_case(patientId,data){ // 创建病例档案
    var url= patientId ? '/wechat/ma?patientId='+patientId : '/wechat/ma?patientId';
    return Vue.http.post(url, data);
  },
  create_plan(patientId,data){ // 创建康复计划
    var url= patientId ? '/wechat/exerciseProcess?patientId='+patientId : '/wechat/exerciseProcess?patientId';
    return Vue.http.post(url, data);
  }
}
