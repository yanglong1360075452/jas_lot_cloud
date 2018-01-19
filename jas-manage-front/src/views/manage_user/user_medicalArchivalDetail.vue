<template>

  <div class="container-wrap">
    <div class="container-box">
      <div class="container-content">
        <el-row>
          <el-col :span="12">
            <div class="grid-content bg-purple-dark">
              <el-tag style="font-size: large" color="#20A0FF">病历详情</el-tag>
            </div>
          </el-col>

        </el-row>
        <div style="margin: 20px"></div>
        <el-row>
          <el-col :span="20">
            <div style="margin: 20px;"></div>
            <div class="grid-content">
              <el-form :inline="true" :model="MedicalArchivals" class="demo-form-inline" label-width="100px">
                <el-form-item label="开始时间">
                  <el-input v-model="MedicalArchivals.startTime" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="病情说明">
                  <el-input v-model="MedicalArchivals.illnessStateDesc" placeholder="用户名" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="活动不足因素">
                  <el-input v-model="MedicalArchivals.activityDesc" placeholder="手机号码" :disabled="readOnly"style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="病程">
                  <el-input v-model="MedicalArchivals.processDesc" placeholder="手机号码" :disabled="readOnly"style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="手术医院">
                  <el-input v-model="MedicalArchivals.hospitalDesc" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="手术科室">
                  <el-input v-model="MedicalArchivals.departmentDesc" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="主刀医生">
                  <el-input v-model="MedicalArchivals.doctorName" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <br/>
                <el-form-item label="病历描述">
                  <textarea v-model="MedicalArchivals.content" :disabled="true" style="width:1100px;height:200px"></textarea>
                </el-form-item>
              </el-form>
            </div>
          <div style="width:1300px;height:10px;text-align: center;font-size:18px;margin-top:-15px;font-weight:bold;"><span>病历照片列表</span></div>
          </el-col>
        </el-row>
    </div>
      <div class="container-content" style="margin-top:25%;width:72%;height:100%;margin-left:3%">
        <el-table :data="MedicalArchival" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
          <el-table-column prop="src" label="图片" width="300" align="center">
            <template scope="scope">
              <p @click="show(scope.row.src)" style="color:#0000FF;cursor: pointer;">{{scope.row.src}}</p>
            </template>
          </el-table-column>
          <el-table-column prop="type" label="图片类型" width="150" align="center"></el-table-column>
          <el-table-column prop="time" label="图片时间点" width="200" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.time | date}}
            </template>
          </el-table-column>

          <el-table-column prop="createTime" label="图片上传时间" width="245" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.createTime | date}}
            </template>
          </el-table-column>

          <el-table-column prop="updateTime" label="图片更新时间" width="240" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.updateTime | date}}
            </template>
          </el-table-column>
        </el-table>
        <div class="" v-show="imgUrl==''?false:true">
          <img v-bind:src="imgUrl"  style="width:200px;height: 220px">
          <!--<img class="head-img" src="../../assets/images/header-img.jpg" alt="头像" style="height: 220px">-->
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import UserService from '../../service/user';
  import Util from '../../assets/lib/util';
  import ElInput from "../../../node_modules/element-ui/packages/input/src/input";
  import ElButton from "../../../../jas-bam-front/node_modules/element-ui/packages/button/src/button";

  export default {
    components: {
      ElButton,
      ElInput},
    data() {
      return {
        MedicalArchivals: {},
        MedicalArchival:[],
        readOnly:true,
        imgUrl:null
      }
    },
    created () {
        var that = this;
      that.MedicalArchivals.id = that.$route.params.id;
      this.getMedicalArchivalDetail(this.MedicalArchivals.id);
    },
    methods: {
      show(src){
        let that = this;
        UserService.getImg({
          src: src,
        }).then(function (response) {
          if (null != response.data && response.code == 0) {
            that.imgUrl = response.data;
            that.imgUrl = "data:image/jpg;base64,"+that.imgUrl.body
          }
          else that.$message({type: 'error', message: response.reason});
        });
      },
      getMedicalArchivalDetail(id){
        let that = this;
        UserService.getMedicalArchivalDetail(id).then(function (response) {
            if (response.code == 0) {
              that.MedicalArchivals = response.data;
              var date = new Date(that.MedicalArchivals.startTime);
              that.MedicalArchivals.startTime = that.formatDate(date, 'yyyy-MM-dd hh:mm');
              that.getClinicalData(that.MedicalArchivals.id);
            }
          })
      },
      getClinicalData(id){
        let that = this;
        UserService.getClinicalData(id).then(function (response) {
          if (response.code == 0) {
            that.MedicalArchival = response.data;
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
