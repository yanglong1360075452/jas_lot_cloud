import axios from 'axios';

export default{
  getDeviceRecordList(data){
    return axios.get('/device/getDeviceRecordList/'+data);
  },
  getDeviceDetail(data){
    return axios.get('/device/getDeviceDetail/'+data);
  },
  //后台根据条件查询设备
  findDeviceList(data){
    return axios.get('/device/findDeviceList',{params:data});
  },
  updateDevice(data){
    return axios.put('/device/updateDevice',data);
  },
  importDevice(data){
    return axios.post('/device/importDevice',data);
  },
}
