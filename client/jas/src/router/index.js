import App from '../page/app'

export default [
    {

      path: '/app',
      component: App, // 打开任一页面都必须先做微信授权，所以应将授权放在根路由中
      children: [
        { // 登录与注册
          path: 'reg',
          name: 'reg',
          meta: {
            title: '注册'
          },
          component: r => require.ensure([], () => r(require('../page/reg/reg.vue')), 'reg'),
          children:[
            { // 注册协议
              name:'protocol',
              path: 'protocol',
              meta: {
                title: '注册协议'
              },
              component: r => require.ensure([], () => r(require('../page/reg/protocol.vue')), 'protocol'),
            },
            { // 完善信息
              path: 'reg_step2',
              name: 'reg_step2',
              meta: {
                title: '完善信息'
              },
              component: r => require.ensure([], () => r(require('../page/reg/reg_step2.vue')), 'reg_step2'),
            },
            { // 选择医院
              name:'select_hospital',
              path: 'select_hospital',
              meta: {
                title: '选择医院'
              },
              component: r => require.ensure([], () => r(require('../page/reg/select_hospital.vue')), 'select_hospital'),
            },
            { // 选择科室
              name:'select_department',
              path: 'select_department',
              meta: {
                title: '选择科室'
              },
              component: r => require.ensure([], () => r(require('../page/reg/select_department.vue')), 'select_department'),
            },
            { // 医生和患者列表
              name:'member_list',
              path: 'member_list',
              meta: {
                title: '医生和患者列表'
              },
              component: r => require.ensure([], () => r(require('../page/reg/member_list.vue')), 'member_list'),
            },
            { // 病历档案
              name:'medicalArchival',
              path: 'medicalArchival',
              meta: {
                title: '编辑病例档案'
              },
              component: r => require.ensure([], () => r(require('../page/user/medicalArchival.vue')), 'medicalArchival'),
              children: [
                // 编辑病人案例信息 - 照片
                {
                  name: 'compilePhotograph',
                  path: 'compilePhotograph',
                  meta: {
                    title: '编辑病例信息'
                  },
                  component: r => require.ensure([], () => r(require('../page/user/compilePhotograph.vue')), 'compilePhotograph')
                }
              ]
            },
            { // 康复计划
              name: 'exerciseProcess',
              path: 'exerciseProcess',
              meta: {
                title: '编辑康复计划'
              },
              component: r => require.ensure([], () => r(require('../page/user/exerciseProcess.vue')), 'exerciseProcess'),
            },
          ]
        },
        { // 主页
          name: 'main',
          path: 'main',
          meta: {
            title: '主页'
          },
          component: r => require.ensure([], () => r(require('../page/main/main.vue')), 'main'),
          children:[
            { // 患者详情
              name: 'doctor_see_user',
              path: 'doctor_see_user',
              meta: {
                title: '患者详情'
              },
              component: r => require.ensure([], () => r(require('../page/mine/doctor_see_user.vue')), 'doctor_see_user'),
            },
            { // 我的设备
              name: 'tab_dev',
              path: 'tab_dev',
              meta: {
                title: '设备详情'
              },
              component: r => require.ensure([], () => r(require('../page/dev/tab_dev.vue')), 'tab_dev'),
              children:[
                { // 常见问题
                  name: 'fqa',
                  path: 'fqa',
                  meta: {
                    title: '常见问题'
                  },
                  component: r => require.ensure([], () => r(require('../page/dev/fqa.vue')), 'fqa'),
                },
                { // 设备使用指南
                  name: 'dev_help',
                  path: 'dev_help',
                  meta: {
                    title: '设备使用指南'
                  },
                  component: r => require.ensure([], () => r(require('../page/dev/dev_help.vue')), 'dev_help'),
                },
                { // 套具选型和测量方式
                  name: 'select_met',
                  path: 'select_met',
                  meta: {
                    title: '套具选型和测量方式'
                  },
                  component: r => require.ensure([], () => r(require('../page/dev/select_met.vue')), 'select_met'),
                },
              ]
            },
            { // 我的康复师
              name: 'tab_doctor',
              path: 'tab_doctor',
              meta: {
                title: '康复师详情'
              },
              component: r => require.ensure([], () => r(require('../page/main/tab_doctor.vue')), 'tab_doctor'),
            },
            { // 病历档案
              name: 'tab_files',
              path: 'tab_files',
              meta: {
                title: 'Ta的病例档案'
              },
              component: r => require.ensure([], () => r(require('../page/main/tab_files.vue')), 'tab_files'),
            },
            { // 康复数据
              name: 'data_info',
              path: 'data_info',
              meta: {
                title: '康复数据'
              },
              component: r => require.ensure([], () => r(require('../page/main/data_info.vue')), 'data_info'),
            },
            { // 康复数据
              name: 'data_info2',
              path: 'data_info2',
              meta: {
                title: '康复数据'
              },
              component: r => require.ensure([], () => r(require('../page/main/data_info2.vue')), 'data_info2'),
            },
            { // 档案详情
              name: 'files_info',
              path: 'files_info',
              meta: {
                title: '档案详情'
              },
              component: r => require.ensure([], () => r(require('../page/main/files_info.vue')), 'files_info'),
              children: [
                { // 照片详情
                  name: 'userPhotograph',
                  path: 'userPhotograph',
                  meta: {
                    title: '病人照片'
                  },
                  component: r => require.ensure([], () => r(require('../page/main/userPhotograph.vue')), 'userPhotograph'),
                }
              ]
            },
            { // 填写评估记录
              name: 'edit_comment',
              path: 'edit_comment',
              meta: {
                title: '填写评估记录'
              },
              component: r => require.ensure([], () => r(require('../page/main/edit_comment.vue')), 'edit_comment'),
            },
            { // 患者详情
              name: 'user_info',
              path: 'user_info',
              meta: {
                title: '患者详情'
              },
              component: r => require.ensure([], () => r(require('../page/user/user_info.vue')), 'user_info'),
            },
          ]
        },
        { // 我的
          name: 'mine',
          path: 'mine',
          meta: {
            title: '我的'
          },
          component: require('../page/mine/mine.vue')
          // r => require.ensure([], () => r(require('../page/mine/mine.vue')), 'mine'),
        },
        { // 我的积分
          path: 'credits',
          meta: {
            title: '积分'
          },
          component: r => require.ensure([], () => r(require('../page/mine/credits.vue')), 'credits'),
        },
        { //账户设置
          name: 'setting',
          path: 'setting',
          meta: {
            title: '设置'
          },
          component: r => require.ensure([], () => r(require('../page/mine/account_setting/setting.vue')), 'setting'),
        },
        {
          // 绑定手机
          name: 'phone',
          path: 'phone',
          meta: {
            title: '修改手机'
          },
          component: r => require.ensure([], () => r(require('../page/mine/account_setting/phone.vue')), 'phone'),
        },
        { //个人信息
          name: 'personalInfo',
          path: 'personalInfo',
          meta: {
            title: '个人资料'
          },
          component: r => require.ensure([], () => r(require('../page/mine/account_setting/personal_info.vue')), 'personalInfo'),
        },
        {//个人信息简介
          name: 'introduction',
          path: 'introduction',
          meta: {
            title: '简介修改'
          },
          component: r => require.ensure([], () => r(require('../page/mine/account_setting/introduction.vue')), 'introduction'),
        },
        { // 我的优惠券 error
          name: 'discount',
          path: 'discount',
          meta: {
            title: '我的优惠券'
          },
          component: r => require.ensure([], () => r(require('../page/mine/discount/discount.vue')), 'discount'),
          children: [
            {
              name: 'record',
              path: 'record',
              meta: {
                title: '历史优惠券'
              },
              component: r => require.ensure([], () => r(require('../page/mine/discount/record.vue')), 'discount'),
            }
          ]
        },
        { // 组件测试页面
          path: 'test',
          component: r => require.ensure([], () => r(require('../test/test.vue')), 'test'),
          children: [
            {
              path: 'sum01',
              component: r => require.ensure([], () => r(require('../test/sum01.vue')), 'sum01')
            },
            {
              path: 'sum02',
              component: r => require.ensure([], () => r(require('../test/sum02.vue')), 'sum02')
            },
            {
              path: '01',
              component: r => require.ensure([], () => r(require('../test/01.vue')), '01')
            },
            {
              path: '02',
              component: r => require.ensure([], () => r(require('../test/02.vue')), '02')
            },
            {
              path: '03',
              component: r => require.ensure([], () => r(require('../test/03.vue')), '03')
            },
            {
              path: '04',
              component: r => require.ensure([], () => r(require('../test/04.vue')), '04')
            },
            {
              path: '05',
              component: r => require.ensure([], () => r(require('../test/05.vue')), '05')
            },
            {
              path: '06',
              component: r => require.ensure([], () => r(require('../test/06.vue')), '06')
            },
            {
              path: '07',
              component: r => require.ensure([], () => r(require('../test/07.vue')), '07')
            },
            {
              path: '08',
              component: r => require.ensure([], () => r(require('../test/08.vue')), '08')
            },
            {
              path: '09',
              component: r => require.ensure([], () => r(require('../test/09.vue')), '09')
            },
            {
              path: '10',
              component: r => require.ensure([], () => r(require('../test/10.vue')), '10')
            },
            {
              path: '11',
              component: r => require.ensure([], () => r(require('../test/11.vue')), '11')
            },
            {
              path: '12',
              component: r => require.ensure([], () => r(require('../test/12.vue')), '12')
            },
            {
              path: '13',
              component: r => require.ensure([], () => r(require('../test/13.vue')), '13')
            },
            {
              path: '14',
              component: r => require.ensure([], () => r(require('../test/14.vue')), '14')
            }
          ]
        }
      ]
    },
    // { path: '*', redirect: '/app/reg' }
  ]
