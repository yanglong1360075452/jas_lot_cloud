// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import FastClick from 'fastclick'
import VueRouter from 'vue-router'
import routes from './router'
import { WechatPlugin } from 'vux'
import { AjaxPlugin } from 'vux'
import store from './store/store.js'
import Render from './Render'
import httpinterceptor from './config/httpinterceptor'
import './style/main.less'
import ECharts from 'vue-echarts/components/ECharts.vue'
import { MessageBox } from 'mint-ui';

// import ECharts modules manually to reduce bundle size
import 'echarts/lib/chart/bar'
import 'echarts/lib/chart/line'

Vue.use(require('vue-wechat-title'))
Vue.use(AjaxPlugin)
Vue.use(VueRouter)
Vue.use(WechatPlugin)


const router = new VueRouter({
  routes
})
router.afterEach(route => {
  MessageBox.close();
})

FastClick.attach(document.body);
Vue.config.productionTip = false;

Vue.directive('wx_img', {
  inserted: function (el, binding) {
    console.log('binding',binding)
    if(binding.value && binding.value.base64 && binding.value.base64.body && binding.value.base64.body.length>0){
      let img=binding.value.base64.body;
      let base_head='data:image/png;base64,';
      el.style.backgroundImage='url('+base_head+img+')';
    }else if(binding.value && binding.value.url && binding.value.url.length>0){
      let img=binding.value.url;
      el.style.backgroundImage='url('+img+')';
    }
  }
})

var app = new Vue({
  created(){
    httpinterceptor.init(this);
  },
  router,
  store,
  render: h => h(Render)
}).$mount('#app-box')

Vue.component('chart', ECharts)
