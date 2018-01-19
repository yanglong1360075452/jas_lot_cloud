<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>
      <div class="search-box" style="positin:fixed;width:100%;">
        <div style="width: 25%; height: 40px; margin-left: 4%; padding-top: 5px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">患者:</label>
          <template>
            <el-input placeholder="请输入患者姓名" style="width:300px"  v-model="PageData.patientName"> </el-input>
          </template>
        </div>

        <div style="width: 21%; height: 40px; margin-top: -40px; margin-left: 26%;" >
          <label style="float: left;margin-right:8px;margin-top:6px;font-size:16px">康复师:</label>
          <template>
            <el-input placeholder="请输入康复师姓名" style="width:300px"  v-model="PageData.doctorName"> </el-input>
          </template>
        </div>

        <div style="width: 20%; margin-left: 48%; height: 40px; margin-top: -40px;" >
          <span style="margin-right:10px;font-size:16px">导入时间:</span>
            <template>
              <el-date-picker v-model="time" type="daterange" placeholder="选择日期范围">
              </el-date-picker>
            </template>
        </div>

        <div style="width: 25%; margin-left: 64%; height: 40px; margin-top: -40px;" >
          <el-button type="primary" style="margin-left:20%" @click="handleIconClick">查询</el-button>
        </div>
      </div>
      <div class="container-content">
        <el-table :data="exerciseProcessList" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="id" label="ID" width="100" align="center"> </el-table-column>
          <el-table-column prop="exerciseProcessName" label="训练计划名" width="260" align="center">
            <template scope="scope">
              <router-link :to="{ name: 'exerciseProcessDetail', params:  { id: scope.row.id}}" style="color: #20A0FF">
                {{scope.row.exerciseProcessName}}
              </router-link>
            </template>
          </el-table-column>
          <el-table-column prop="patientName" label="患者姓名" width="200" align="center"> </el-table-column>
          <el-table-column prop="trainingCycle" label="训练周期" width="100" align="center"> </el-table-column>
          <el-table-column prop="startTime" label="时间段" width="380" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.startTime | date}}-{{scope.row.endTime | date}}
            </template>
          </el-table-column>
          <el-table-column prop="arthroDesc" label="训练关节" width="120" show-overflow-tooltip align="center"></el-table-column>
          <el-table-column prop="postionDesc" label="训练部位" width="120" align="center"> </el-table-column>
          <el-table-column prop="directionDesc" label="训练方向" width="100" align="center"></el-table-column>
          <el-table-column prop="exerciseRecordCount" label="训练记录" width="100" align="center"> </el-table-column>
          <el-table-column prop="doctorName" label="康复师" width="198" align="center"> </el-table-column>
        </el-table>
      </div>
      <div class="page-wrap">
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
  import types from '../../store/mutation-types';
  export default {
    name: 'list',
    data() {
      return {
        currentPage: 1,
        totalPage: 0,
        startPageSize:0,
        endPageSize: 15,
        totalCount: 0,
        key_word: '',
        exerciseProcessList: [],
        time:null,
        PageData:{
          patientName:"",
          startTime:"",
          endTime:"",
          doctorName:"",
        },
      }
    },
    created () {
      this.fetchData();
    },
    watch: {
      '$route': 'fetchData'
    },
    computed: {

    },
    methods: {
      handleIconClick () {
        if(this.time){
          if(this.time[0] !=null && this.time[1] !=null){
            this.PageData.startTime = +new Date(this.time[0]);
            this.PageData.endTime = +new Date(this.time[1]);
          }
        }else{
          this.PageData.startTime = null;
          this.PageData.endTime = null;
        }
        this.fetchData();
      },
      pagination(pageNo, pageSize, array) {
        var offset = (pageNo-1) * pageSize;
        return (offset + pageSize >= array.length) ? array.slice(offset, array.length) : array.slice(offset, offset + pageSize);
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.fetchData();
      },

      /**获取用户列表数据*/
      fetchData (route) {
        var tab = route ? route.query.tab : this.$route.query.tab;
        let that = this;
        setTimeout(() => {
          UserService.getExerciseProcessList({
            patientName: that.PageData.patientName,
            doctorName: that.PageData.doctorName,
            startTime: that.PageData.startTime,
            endTime: that.PageData.endTime,
          }).then(function (response) {
            if (null != response.data && response.code == 0) {
                that.exerciseProcessList = response.data;
                that.PageData.startTime = null;
                that.PageData.endTime = null;
                that.totalCount = that.exerciseProcessList.length;
                that.totalPage = that.totalCount/that.startPageSize;
                that.exerciseProcessList = that.pagination(that.currentPage,that.endPageSize,that.exerciseProcessList);
            }
            else that.$message({type: 'error', message: response.reason});
          });
        }, 300);
      }
    }
  }
</script>
<style lang="scss">
  @import "../../assets/scss/define";

  .container-content {
    @extend %pa;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 70px 20px;
    overflow-x: hidden;
    overflow-y: auto;
  }

  .search-box {
    @extend %pa;
    top: 20px;
    right: 40px;
    z-index: 1;
    width: 360px;
  }

  .container-box {
    @extend %h100;
  }

  .page-wrap {
    @extend %pa;
    @extend %tac;
    background-color: #fff;
    z-index: 1;
    left: 0;
    right: 0;
    padding: 10px 0 20px;
    bottom: 0;
  }
</style>
