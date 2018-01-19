<template>

  <div class="container-wrap">
    <div class="container-box">
      <div class="container-content">
        <el-row>
          <el-col :span="12">
            <div class="grid-content bg-purple-dark">
              <el-tag style="font-size: large" color="#20A0FF">患者详情</el-tag>
            </div>
          </el-col>
        </el-row>

        <div style="margin: 20px"></div>
        <div style="margin-left:50%">
          <router-link :to="{ name: 'exerciseProcess'}" style="color: #20A0FF">
            <el-button type="info" size="">我的训练计划</el-button>
          </router-link>
          <router-link :to="{ name: 'exerciseRecord'}" style="color: #20A0FF">
            <el-button type="info" size="">我的训练记录</el-button>
          </router-link>
          <router-link :to="{ name: 'scores'}" style="color: #20A0FF">
            <el-button type="info" size="">我的积分</el-button>
          </router-link>
          <router-link :to="{ name: 'coupon'}" style="color: #20A0FF">
            <el-button type="info" size="">我的优惠券</el-button>
          </router-link>
        </div>
        <el-row>
          <el-col :span="12">
            <div style="margin: 20px;"></div>
            <div class="grid-content">
              <el-form :inline="true" :model="user" class="demo-form-inline" label-width="100px">
                <el-col :span="12" style="margin-left:100px">
                  <div class="grid-content bg-purple-light">
                      <img v-bind:src="imgUrl"  style="width:200px;height: 220px">
                  </div>
                </el-col>
                <el-form-item label="用户名">
                  <el-input v-model="user.realName" placeholder="用户名" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="性别">
                  <el-input v-model="user.sex" placeholder="手机号码" :disabled="readOnly"style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="手机号码">
                  <el-input v-model="user.phone" placeholder="手机号码" :disabled="readOnly"style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="出生年月">
                  <el-input v-model="user.birthDate" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
        </el-row>
    </div>
      <div class="container-content" style="margin-top:20%;width:75%;height:100%;margin-left:4%">
        <el-row style="margin: 20px">
          <el-col :span="24">
            <div class="grid-content bg-purple-dark">
              <el-tabs type="card">
                <el-tab-pane label="病历档案">
                  <el-table :data="MedicalArchival" border stripe style="width: 100%">
                    <el-table-column prop="id" label="ID" width="100" align="center">
                      <template scope="scope">
                        <router-link :to="{ name: 'medicalArchival_detail',params:  { id: scope.row.id}}" style="color: #20A0FF">
                          {{scope.row.id}}
                        </router-link>
                      </template>
                    </el-table-column>
                    <el-table-column prop="patientName" label="病人姓名" width="100" align="center"></el-table-column>
                    <el-table-column prop="startTime" label="初诊时间" width="200" show-overflow-tooltip align="center">
                      <template scope="scope">
                        {{scope.row.startTime | date}}
                      </template>
                    </el-table-column>
                    <el-table-column prop="surgeryTime" label="手术时间" width="200" show-overflow-tooltip align="center">
                      <template scope="scope">
                        {{scope.row.surgeryTime | date}}
                      </template>
                    </el-table-column>
                    <el-table-column prop="illnessStateDesc" label="病情说明" width="150" align="center"></el-table-column>
                    <el-table-column prop="activityDesc" label="活动不足因素" width="140" align="center"></el-table-column>
                    <el-table-column prop="processDesc" label="病程" width="100" align="center"></el-table-column>
                    <el-table-column prop="departmentDesc" label="科室" width="100" align="center"></el-table-column>
                    <el-table-column prop="hospitalDesc" label="手术医院" width="120" align="center"></el-table-column>
                    <el-table-column prop="doctorName" label="主刀医生"N width="100" align="center"></el-table-column>
                    <el-table-column prop="content" label="病例描述" width="150" align="center"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="200" show-overflow-tooltip align="center">
                      <template scope="scope">
                        {{scope.row.createTime | date}}
                      </template>
                    </el-table-column>
                    <el-table-column prop="updateTime" label="更新时间" width="200" show-overflow-tooltip align="center">
                      <template scope="scope">
                        {{scope.row.updateTime | date}}
                      </template>
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
                <el-tab-pane label="绑定的设备">
                  <el-table :data="DeviceList" border stripe tooltip-effect="dark" style="width: 100%">
                    <el-table-column prop="id" label="设备id" align="center">
                      <template scope="scope">
                        <router-link :to="{ name: 'devices',params:  { id: scope.row.id}}" style="color: #20A0FF">
                          {{scope.row.id}}
                        </router-link>
                      </template>
                    </el-table-column>
                    <el-table-column prop="type" label="类型" align="center">
                      <template scope="scope">
                        {{scope.row.type==1?'长租':'短租'}}
                      </template>
                    </el-table-column>
                    <el-table-column prop="sn" label="序列号" show-overflow-tooltip align="center"></el-table-column>
                    <el-table-column prop="produceDate" label="生产日期" align="center">
                      <template scope="scope">
                        {{scope.row.produceDate | date}}
                      </template>
                    </el-table-column>
                    <el-table-column prop="updateTime" label="绑定时间" align="center">
                      <template scope="scope">
                        {{scope.row.updateTime | date}}
                      </template>
                    </el-table-column>
                    <!--<el-table-column prop="endTime" label="解绑时间" align="center">-->
                      <!--<template scope="scope">-->
                        <!--{{scope.row.endTime | date}}-->
                      <!--</template>-->
                    <!--</el-table-column>-->
                  </el-table>
                </el-tab-pane>
              </el-tabs>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
  import UserService from '../../service/user';
  import Util from '../../assets/lib/util';
  import ElInput from "../../../node_modules/element-ui/packages/input/src/input";

  export default {
    components: {ElInput},
    data() {
      return {
        user: {},
        imgUrl:null,
        MedicalArchival:[],
        DeviceList:[],
        readOnly:true
      }
    },
    created () {
      this.user.id = this.$route.params.id;
      this.getUser(this.user.id);
      this.getMedicalArchival();
      this.getDeviceList(this.user.id);
    },
    methods: {
      getUser(id){
        let that = this;
        UserService.get(id).then(function (response) {
            if (response.code == 0) {
              that.user = response.data;
              that.imgUrl = that.user.responseEntity;
              that.imgUrl = "data:image/jpg;base64,"+that.imgUrl.body;
              var date = new Date(that.user.birthDate);
              that.user.sex = that.user.sex==1?'男':'女';
              that.user.birthDate = that.formatDate(date, 'yyyy-MM-dd hh:mm');
            }
          })
      },
      getDeviceList(id){
        let that = this;
        UserService.getDeviceList(id).then(function (response) {
          if (response.code == 0) {
            that.DeviceList = response.data;
            console.log(that.DeviceList);
//            var date = new Date(that.DeviceList.birthDate);
//            that.user.birthDate = that.formatDate(date, 'yyyy-MM-dd hh:mm');
          }
        })
      },
      getMedicalArchival(){
        var id= this.$route.params.id;
        let that = this;
        UserService.getMedicalArchival(id).then(function (response) {
          if (response.code == 0) {
            that.MedicalArchival = response.data;
            for(var i=0;i<that.MedicalArchival.length;i++){
                if(that.MedicalArchival[i].content.length>10){
                  that.MedicalArchival[i].content = that.MedicalArchival[i].content.substr(0,10);
                }

            }
          }
        })
      },
      formatDate (date, fmt) {
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
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : this.padLeftZero(str));
          }
        }
        return fmt;
      },
      padLeftZero (str) {
        return ('00' + str).substr(str.length);
      }
    }
  }
</script>
