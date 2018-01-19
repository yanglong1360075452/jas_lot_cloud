import axios from 'axios';

export default{
  /*查询用户列表*/
  getList(data){
    return axios.get('/users',{params:data});
  },
  /*查询用户详情*/
  get(data){
    return axios.get('/user/'+data);
  },
  /*查看医生下面所有的康复计划列表*/
  getExerciseProcess(data){
    return axios.get('getExerciseProcess/'+data);
  },
  /*查询康复计划列表*/
  getExerciseProcessList(data){
    return axios.get('exerciseProcess/getExerciseProcessList',{params:data});
  },
  /*查询康复计划列表*/
  findExerciseRecordList(data){
    return axios.get('exerciseProcess/findExerciseRecordList',{params:data});
  },
  /*查询康复计划详情*/
  getExerciseProcessById(data){
    return axios.get('exerciseProcess/getExerciseProcess/'+data);
  },
  /*查询康复计划的评估报告*/
  getReportListById(data){
    return axios.get('exerciseProcess/getReportListById/'+data);
  },
  /*查询用户审核和未审核列表*/
  userExaminePassOnoPass(data){
    return axios.get('/userExaminePassOnoPass',{params:data});
  },
  /*查询康复师注册列表*/
  getuserExamineList(data){
    return axios.get('/userExamine',{params:data});
  },

  /*查询积分列表*/
  getScoresList(data){
    return axios.get('score/scoresList',{params:data});
  },
  /*查询优惠券列表*/
  getCouponList(data){
    return axios.get('score/couponList',{params:data});
  },
  /*导入优惠券*/
  importCoupon(data){
    return axios.post('/score/importCoupon',data);
  },
  /*查询病人病例档案*/
  getMedicalArchival(data){
    return axios.get('/getMedicalArchival/'+data);
  },
  /*查看病人病例档案详情*/
  getMedicalArchivalDetail(data){
    return axios.get('/getMedicalArchivalDetail/'+data);
  },
  /*查看病人病例档案图片*/
  getClinicalData(data){
    return axios.get('/getClinicalDataList/'+data);
  },
  /*查看图片*/
  getImg(data){
    return axios.get('/getImg',{params:data});
  },
  /*查看病人绑定的所有设备*/
  getDeviceList(data){
    return axios.get('/device/getDeviceList/'+data);
  },
  /*查看所有日志列表*/
  findOperationLogList(data){
    return axios.get('/findOperationLogList/',{params:data});
  },
  /*根据Id查询日志*/
  getOperationLogById(data){
    return axios.get('/getOperationLogById/'+data);
  },





}
