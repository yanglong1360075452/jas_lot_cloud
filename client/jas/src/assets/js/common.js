import {dateFormat} from 'vux'
import api_main from '../../api/main.js'
const obj2par=(a) => { /*对转url参数*/
    var s = [],
        rbracket = /\[\]$/,
        isArray = function(obj) {
            return Object.prototype.toString.call(obj) === '[object Array]';
        },
        add = function(k, v) {
            v = typeof v === 'function' ? v() : v === null ? '' : v === undefined ? '' : v;
            s[s.length] = encodeURIComponent(k) + '=' + encodeURIComponent(v);
        },
        buildParams = function(prefix, obj) {
            var i, len, key;

            if (prefix) {
                if (isArray(obj)) {
                    for (i = 0, len = obj.length; i < len; i++) {
                        if (rbracket.test(prefix)) {
                            add(prefix, obj[i]);
                        } else {
                            buildParams(prefix + '[' + (typeof obj[i] === 'object' ? i : '') + ']', obj[i]);
                        }
                    }
                } else if (obj && String(obj) === '[object Object]') {
                    for (key in obj) {
                        buildParams(prefix + '[' + key + ']', obj[key]);
                    }
                } else {
                    add(prefix, obj);
                }
            } else if (isArray(obj)) {
                for (i = 0, len = obj.length; i < len; i++) {
                    add(obj[i].name, obj[i].value);
                }
            } else {
                for (key in obj) {
                    buildParams(key, obj[key]);
                }
            }
            return s;
        };

    return buildParams('', a).join('&').replace(/%20/g, '+');
}
const setTitle = (t) => {
  console.log('setTitle')
  document.title = t;
  var i = document.createElement('iframe');
  i.src = '//m.baidu.com/favicon.ico';
  i.style.display = 'none';
  i.onload = function() {
    setTimeout(function(){
      i.remove();
    }, 9)
  }
  document.body.appendChild(i);
}

const cookie = { 
  set: function (name, value, days) {
    var d = new Date;
    d.setTime(d.getTime() + 24*60*60*1000*days);
    window.document.cookie = name + "=" + value + ";path=/;expires=" + d.toGMTString();
  },

  get: function (name) {
    var v = window.document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return v ? v[2] : null;
  },

  del: function (name) {
    this.set(name, '', -1);
  }
};

const url_tool = { 
  getParams(name){
    function UrlSearch()
    {
      var name,value;
      var str=location.href; //取得整个地址栏
      var num=str.indexOf("?")
      str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]

      var arr=str.split("&"); //各个参数放到数组里
      for(var i=0;i < arr.length;i++){
        num=arr[i].indexOf("=");
        if(num>0){
          name=arr[i].substring(0,num);
          value=arr[i].substr(num+1);
          this[name]=value;
        }
      }
    }
    var Request=new UrlSearch(); //实例化
    return Request[name];

  },
  getWechatQuery(){
    let state = this.getParams("state");
    let code = this.getParams("code");
    let queryList = decodeURIComponent(state).split(";");
    let obj = {code:code};
    queryList.forEach(item => {
      let list = item.split(":");
      obj[list[0]] = list[1];
    });

    return obj;
  }

};
const info_item_fn = (index,item, vm) =>{ /*查询患者资料*/
  console.log('info_item_fn',index,item)
  if(vm.user_type==1){
    console.log('查询患者资料');
    let obj=vm.$store.state.temp_data;
    obj.user_data={data:item};
    vm.$store.commit('temp_data_up',obj)
    vm.$router.push({name:'doctor_see_user', params:{user_data:item}})
  }
};
const to_echa_data_lines = (data_arr, color_arr, config_obj) =>{ /*给转换后的数据添加额外配置*/
  console.log('data_arr', data_arr)

  var temp_data=[]; /*分组*/
  for(var i=0; i<data_arr.length; i++){
    temp_data.push([data_arr[i].todo, data_arr[i].pause, data_arr[i].y_min]);
  }

  var series=[]; /*表数据*/
  var legend_data=[]; /*表名*/
  var y_min=[]; /*最小值*/
  for(var i=0; i<temp_data.length; i++){
    legend_data.push('xw_'+i+''+0, 'xw_'+i+''+1); /*todo 和 pause 的标题*/
    y_min.push(temp_data[i][2], temp_data[i][2]); /*最小值*/
    series.push(
      {
        name:'xw_'+i+''+0,
        type: 'line',
        data: temp_data[i][0],
        itemStyle:{
          normal:{
            opacity:0,
            color:color_arr[i],
          }
        },
        lineStyle:{
          normal:{
            width:config_obj.line_width,
            color:color_arr[i],
          }
        },
      },
      {
        name:'xw_'+i+''+1,
        type: 'line',
        data: temp_data[i][1],
        itemStyle:{
          normal:{
            opacity:0,
            color:color_arr[i],
          }
        },
        lineStyle:{
          normal:{
            width:config_obj.line_width,
            color:color_arr[i],
            type:'dotted'
          }
        }
      },
    )
  }
  return {'y_min':y_min, 'series':series, 'legend_data':legend_data};
};

const to_echa_data_line =(arr) => { /*转换单条线数据*/
  var temp_data={
    todo:[], // 训练中
    pause:[], // 暂停
    y_min:'', // y轴最小值
  };
  var y_min_arr=[];
  for(var index = 0;index<arr.length;index++){

    var _t2=+new Date(arr[0].time.time)/60000;
    var mm=parseInt( +new Date(arr[index].time.time)/60000 - _t2); // 分
    if(index < arr.length-1){
      var _index=parseInt(index)+1;
    }
    var mm2=parseInt( +new Date(arr[_index].time.time)/60000 - _t2);

    // 训练中
    if(arr[index].angle && !!!arr[index].event){
      y_min_arr.push(arr[index].angle);

      var arr1=[mm, arr[index].angle];
      var _index=parseInt(index)+1;
      var arr2=[mm2, arr[index].angle];
      temp_data.todo.push(arr1, arr2);
    }
    // 结束
    if(arr[index].event && arr[index].event=='end'){
      // console.log('结束',arr[index])
    }
    // 暂停
    temp_data.pause.push([null,null]);
    if(arr[index].event && arr[index].event=='pause'){
      console.log('暂停',arr[index]);
      var arr1=[mm, null];
      var _index=parseInt(index)+1;
      var arr2=[mm2, arr[index-1].angle];
      temp_data.todo.push(arr1, arr2);
      temp_data.pause.push([mm, arr[index-1].angle], arr2);
    }
  }
  temp_data.y_min=Math.min.apply(null, y_min_arr);
  return temp_data;
};

const code2name =  (code, arr) => { 
  var i; for (i in arr) {if(code===arr[i].code) return arr[i].name };
};

const code2ico = (code, arr) => { 
  var i; for (i in arr) {if(code===arr[i].code) return arr[i].ico };
};

const date_format = (val, format) => { 
  return dateFormat(new Date(val), format);
};

const img_data = {
    appraisal:[ /*评估结论列表*/
            {
              "code": 1,
              "name": "维持",
              "ico": "icon_keep",
            },
            {
              "code": 2,
              "name": "改善",
              "ico": "icon_gaisahan",
            },
            {
              "code": 3,
              "name": "恶化",
              "ico": "icon_worsen",
            },
            {
              "code": 4,
              "name": "脱落",
              "ico": "icon_tuoluo",
            }
          ],
};

// 时间格式化
const formatDate = (date, fmt) => {

  if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
  }
  let o = {
      'M+': date.getMonth() + 1,
      'd+': date.getDate(),
      'h+': date.getHours(),
      'm+': date.getMinutes(),
      's+': date.getSeconds()
  };
  for (let k in o) {
      if (new RegExp(`(${k})`).test(fmt)) {
          let str = o[k] + '';
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
      }
  }
  return fmt;

};

function padLeftZero (str) {
  return ('00' + str).substr(str.length);
};

const int_dict = (vm) => {/*字典*/
  api_main.get_dict_hospitals('').then(res=>{console.log('医院列表',res);vm.$store.commit('dict_hospitals_up',res);},res=>{console.log('res',res)});
  api_main.get_dict_departments('').then(res=>{console.log('科室列表',res);vm.$store.commit('dict_departments_up',res);},res=>{console.log('res',res)});
  api_main.get_dict_appraisal('').then(res=>{console.log('评估结论列表',res);vm.$store.commit('dict_appraisal_up',res);},res=>{console.log('res',res)});
  api_main.get_dict_illnessState().then(res=>{console.log('病情说明列表',res);vm.$store.commit('dict_illnessState_up',res);},res=>{console.log('res',res)})
  api_main.get_dict_nactivityFactor().then(res=>{console.log('活动不足因素列表',res);vm.$store.commit('dict_nactivityFactor_up',res);},res=>{console.log('res',res)})
  api_main.get_dict_diseaseCourse().then(res=>{console.log('病程列表查询',res);vm.$store.commit('dict_diseaseCourse_up',res);},res=>{console.log('res',res)})
  api_main.get_dict_pictureLabel().then(res=>{console.log('照片标签/分类',res);vm.$store.commit('dict_pictureLabel_up',res);},res=>{console.log('res',res)})
  api_main.get_dict_arthro().then(res=>{console.log('获取关节列表',res);vm.$store.commit('dict_arthro_up',res);},res=>{console.log('res',res)})
  api_main.get_dict_postion().then(res=>{console.log('获取部位列表',res);vm.$store.commit('dict_postion_up',res);},res=>{console.log('res',res)})
  api_main.get_dict_direction().then(res=>{console.log('获取方向列表',res);vm.$store.commit('dict_direction_up',res);},res=>{console.log('res',res)})
};
export {
  info_item_fn,
  obj2par,
  int_dict,
  to_echa_data_lines,
  to_echa_data_line,
  cookie,
  url_tool,
  code2name,
  img_data,
  date_format,
  setTitle,
  formatDate,
};
