/**
 * Created by yonghuapro on 2017/4/26.
 */
import config from './index'
export default {
  init({$store,$router,$http}){
    $http.defaults.baseURL = config.apiUrl;
    $http.defaults.timeout = 20000;
    $http.defaults.headers.Accept='application/json';
    $http.defaults.headers['Content-Type']='application/json;charset=UTF-8';
    $http.interceptors.request.use(function (config) {
      let token = $store.state.token;
      config.headers.common['Authorization']=token;
      $store.commit('startLoading');
      return config;
    }, function (error) {
      return Promise.reject(error);
    });
    $http.interceptors.response.use(function (response) {
      $store.commit('stopLoading');
      if(response.data.code === 0){
        return response.data.data;
      }else{
        return Promise.reject(response.data.reason);
      }
    }, function (error) {
      $store.commit('stopLoading');
      if(error.response.status === 401){

        $router.push("reg");
      }
      return Promise.reject(error.response);
    });
  }
}
