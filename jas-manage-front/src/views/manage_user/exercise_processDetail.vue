<template>

  <div class="container-wrap">
    <div class="container-box">
      <div class="container-content">
        <el-row>
          <el-col :span="12">
            <div class="grid-content bg-purple-dark">
              <el-tag style="font-size: large" color="#20A0FF">训练计划详情</el-tag>
            </div>
          </el-col>
        </el-row>
        <div style="margin-left:78%">
          <router-link :to="{ name: 'exerciseRecord'}" style="color: #20A0FF">
            <el-button type="info" size="">训练记录</el-button>
          </router-link>
        </div>
        <div style="margin: 20px"></div>
        <el-row>
          <el-col>
            <div style="margin: 20px;"></div>
            <div class="grid-content" style="width: 78%;margin-left:5%;background-color: #F2F2F2">
              <el-form :inline="true" :model="exerciseProcess" class="demo-form-inline" label-width="100px">
                <el-form-item label="患者姓名">
                  <el-input v-model="exerciseProcess.patientName" placeholder="用户名" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="训练周期">
                  <el-input v-model="exerciseProcess.trainingCycle" placeholder="" :disabled="true" style="width: 200px"> </el-input>
                </el-form-item>

                <el-form-item label="开始时间">
                  <el-input v-model="exerciseProcess.startTime" placeholder="" :disabled="true" style="width: 200px"> </el-input>
                </el-form-item>
                <el-form-item label="结束时间">
                  <el-input v-model="exerciseProcess.endTime" placeholder="" :disabled="true" style="width: 200px"> </el-input>
                </el-form-item>
                <el-form-item label="关节">
                  <el-input v-model="exerciseProcess.arthroDesc" placeholder="" :disabled="true" style="width: 200px"> </el-input>
                </el-form-item>
                <el-form-item label="部位">
                  <el-input v-model="exerciseProcess.postionDesc" placeholder="" :disabled="true" style="width: 200px"> </el-input>
                </el-form-item>
                <el-form-item label="方向">
                  <el-input v-model="exerciseProcess.directionDesc" placeholder="" :disabled="true" style="width: 200px"> </el-input>
                </el-form-item>
                <el-form-item label="康复师">
                  <el-input v-model="exerciseProcess.doctorName" placeholder="" :disabled="true" style="width: 200px"> </el-input>
                </el-form-item>
                <el-form-item label="创建人">
                  <el-input v-model="exerciseProcess.patientName" placeholder="" :disabled="true" style="width: 200px"> </el-input>
                </el-form-item>
                <el-form-item label="更新人">
                  <el-input v-model="exerciseProcess.updateByName" placeholder="" :disabled="true" style="width: 200px"> </el-input>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
        </el-row>
        <div style="margin-left:7%;margin-top:3%;width:100px;height:30px">
          <div style="text-align:center;width: 120px;font-size:16px;height: 40px; background-color: rgb(143, 148, 154); border: 1px solid black;">
            <span style="line-height:39px">评估记录</span>
          </div>
        </div>
      </div>
      <div style="margin-top:27%;width:78%;margin-left:6%">
        <el-table :data="reportList" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
          <el-table-column prop="doctorName" label="康复师姓名" width="198" align="center"> </el-table-column>
          <el-table-column prop="startTime" label="开始时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.startTime | date}}
            </template>
          </el-table-column>
          <el-table-column prop="endTime" label="结束时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.endTime | date}}
            </template>
          </el-table-column>
          <el-table-column prop="content" label="评估内容" width="200" align="center"> </el-table-column>
          <el-table-column prop="verdictDesc" label="评估结论" width="120" align="center"></el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.createTime | date}}
            </template>
          </el-table-column>
          <el-table-column prop="updateTime" label="更新时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.updateTime | date}}
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="page-wrap" style="width:83%">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="endPageSize"
          layout="total, prev, pager, next, jumper"
          :total="totalCount">
        </el-pagination>
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
        exerciseProcess: {},
        readonly:true,
        reportList:[],
        currentPage: 1,
        totalPage: 0,
        startPageSize:0,
        endPageSize: 15,
        totalCount: 0
      }
    },
    created () {
      this.exerciseProcess.id = this.$route.params.id;
      this.getExerciseProcessById(this.exerciseProcess.id);
      this.getReportListById(this.exerciseProcess.id);
    },
    methods: {
      getExerciseProcessById(id){
        let that = this;
        UserService.getExerciseProcessById(id).then(function (response) {
          if (response.code == 0) {
            that.exerciseProcess = response.data[0];
            var startTime = new Date(that.exerciseProcess.startTime);
            var endTime = new Date(that.exerciseProcess.endTime);
            that.exerciseProcess.startTime = that.formatDate(startTime, 'yyyy-MM-dd hh:mm');
            that.exerciseProcess.endTime = that.formatDate(endTime, 'yyyy-MM-dd hh:mm');
          }
        })
      },
      getReportListById(id){
        let that = this;
        UserService.getReportListById(id).then(function (response) {
          if (response.code == 0) {
            that.reportList = response.data;
            if(that.reportList.length>0){
              that.totalCount = that.reportList.length;
              that.totalPage = that.totalCount/that.startPageSize;
              that.reportList = that.pagination(that.currentPage,that.endPageSize,that.reportList);
                for(var i=0;i<that.reportList.length;i++){
                  if(that.reportList[i].content.length>10){
                    that.reportList[i].content = that.reportList[i].content.substr(0,10)+"....";
                  }
                  if(that.reportList[i].verdict==1){
                    that.reportList[i].verdictDesc = "保持";
                  }else if(that.reportList[i].verdict==2){
                    that.reportList[i].verdictDesc = "改善";
                  }else if(that.reportList[i].verdict==3) {
                    that.reportList[i].verdictDesc = "恶化";
                  }else if(that.reportList[i].verdict==4){
                    that.reportList[i].verdictDesc = "脱落";
                  }
                }
            }
          }
        })
      },
      pagination(pageNo, pageSize, array) {
        var offset = (pageNo-1) * pageSize;
        return (offset + pageSize >= array.length) ? array.slice(offset, array.length) : array.slice(offset, offset + pageSize);
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.getReportListById(this.exerciseProcess.id);
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
