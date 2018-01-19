import Vue from 'vue';
import Router from 'vue-router';
import Index from '../views/index/index.vue';
import Login from '../views/login/login.vue';
import Users from '../views/manage_user/users.vue';
import userPatient from '../views/manage_user/user_patient.vue';
import userDoctor from '../views/manage_user/user_doctor.vue';
import Util from '../assets/lib/util';
import Access from '../views/log/access.vue';
import Device from '../views/manage_device/device.vue';
import Devices from '../views/manage_device/devices.vue';
import Note from '../views/log/note.vue';
import System from '../views/log/system.vue';
import userExamine from '../views/manage_user/userExamine.vue';
import scores from '../views/manage_score/scores.vue';
import coupon from '../views/manage_score/coupon.vue';
import MedicalArchivalDetail from '../views/manage_user/user_medicalArchivalDetail.vue';
import exerciseProcess from '../views/manage_user/excrcise_process.vue';
import exerciseProcessDetail from '../views/manage_user/exercise_processDetail.vue';
import exerciseRecord from '../views/manage_user/excrcise_record.vue';
import operationLog from '../views/manage_operationLog/operationLog.vue';
Vue.use(Router);

let router = new Router({
  routes: [
    /**首页*/
    {
      path: '/',
      name: 'index',
      component: Index,
      children: [
        /**用户列表页*/
        {
          path: 'users',
          component: Users,
          name: 'users',
        },
        /*用户审核页*/
        {
          path: 'userExamine',
          component: userExamine,
          name: 'userExamine',
        },
        /** 用户详情页*/
        {
          path: 'userPatient/:id',
          name: 'user_patient',
          component: userPatient
        },
        {
          path: 'userDoctor/:id',
          name: 'user_doctor',
          component: userDoctor
        },
        /*用户积分列表*/
        {
          path: 'scores',
          component: scores,
          name: 'scores',
        },
        /*用户病历档案详细信息*/
        {
          path: 'MedicalArchivalDetail/:id',
          component: MedicalArchivalDetail,
          name: 'medicalArchival_detail',
        },
        /*优惠券列表*/
        {
          path: 'coupon',
          component: coupon,
          name: 'coupon',
        },
        /**设备列表*/
        {
          path: 'device',
          name: 'device',
          component: Device
        },
        /** 设备详情页*/
        {
          path: 'devices/:id',
          name: 'devices',
          component: Devices
        },
        /** 康复计划列表*/
        {
          path: 'exerciseProcess',
          name: 'exerciseProcess',
          component: exerciseProcess
        },
        /** 康复计划详情*/
        {
          path: 'exerciseProcessDetail/:id',
          name: 'exerciseProcessDetail',
          component: exerciseProcessDetail
        },
        /** 康复计划详情*/
        {
          path: 'exerciseRecord',
          name: 'exerciseRecord',
          component: exerciseRecord
        },
        /** 访问日志页*/
        {
          path: 'operationLog',
          name: 'operationLog',
          component: operationLog
        },
      ]
    },
    /**登录页*/
    {
      path: '/login',
      name: 'login',
      component: Login
    }

  ]
});

/**
 * 判断用户是否有登录，没有登录则跳转到登录页面
 * 如果用户已经登录，则回跳到登录的页的时候，要转到首页
 * */
router.beforeEach((to, from, next) => {
  var user = Util.dataToSessionStorageOperate.achieve('user');
  if (!user && to.path != '/login') {
    next('/login');
  } else {
    next();
  }
});
export default router
