import axios from 'axios';

export default{
  getList(data){
    return axios.get('/lives',{params:data});
  },
  get(data){
    return axios.get('/lives/'+data);
  },
  getAudience(data){
    return axios.get('/lives/liveAudience?liveId='+data);
  },
  getHistoryAudience(data){
    return axios.get('/lives/historyAudience?liveId='+data);
  }
}
