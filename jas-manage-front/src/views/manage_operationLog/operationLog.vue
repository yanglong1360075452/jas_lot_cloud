<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>
      <div class="grid-content bg-purple-dark" align="">
        <el-dialog :visible.sync="operation" size="small" title="响应内容">
            <div style="width:100%;height:100%">
              <el-form>
                <el-form-item label="响应内容" style="height:100%;width:100%">
                    <el-input autosize type="textarea"  v-model="reponseContent"></el-input>
                </el-form-item>
              </el-form>
            </div>
        </el-dialog>
      </div>
      <div class="search-box" style="positin:fixed;width:100%;">
        <div style="width: 30%; height: 40px; margin-left: 4%; padding-top: 5px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">用户名:</label>
          <template>
            <el-input placeholder="请输入用户名称" style="width:400px"  v-model="PageData.userName"> </el-input>
          </template>
        </div>
        <div style="width: 30%; height: 40px; margin-left: 34%; margin-top: -40px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">访问IP:</label>
          <template>
            <el-input placeholder="请输入访问IP" style="width:400px"  v-model="PageData.ip"> </el-input>
          </template>
        </div>
        <div style="width: 26%; margin-left: 64%; height: 40px; margin-top: -40px;" >
          <span style="margin-right:10px;font-size:16px">导入时间:</span>
            <template>
              <el-date-picker v-model="time" type="daterange" placeholder="选择日期范围">
              </el-date-picker>
            </template>
        </div>
        <div style="width: 24%; margin-left: 84%; height: 40px; margin-top: -40px;" >
          <el-button type="primary" style="margin-left:15%" @click="handleIconClick">查询</el-button>
        </div>
      </div>
      <div class="container-content">
        <el-table :data="operationLogList" ref="couponTable" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="createtime" label="操作时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.createtime | date}}
            </template>
          </el-table-column>
          <el-table-column prop="userId" label="用户ID" width="100" align="center"> </el-table-column>
          <el-table-column prop="userName" label="用户姓名" width="150" align="center"> </el-table-column>
          <el-table-column prop="ipAddress" label="访问IP" width="200" align="center"> </el-table-column>
          <el-table-column  prop="event" label="访问事件" width="280" align="center"></el-table-column>
          <el-table-column  prop="statusCode" label="状态码" width="150" align="center"></el-table-column>
          <el-table-column  prop="requestAddress" label="请求地址及参数" width="350" align="center"></el-table-column>
          <el-table-column  prop="reponseContent" label="响应内容" width="250" align="center">
            <template scope="scope">
              <p @click="show(scope.row.id)" style="color:#0000FF;cursor: pointer;">{{scope.row.reponseContent}}</p>
            </template>
          </el-table-column>
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
  import {readExcel, exportExcel} from '@/utils/excelUtils';
  import Util from '@/utils/util';
  import ElForm from "../../../node_modules/element-ui/packages/form/src/form";
  export default {
    components: {ElForm},
    name: 'list',
    data() {
      return {
        reponseContent:"",
        operation:false,
        currentPage: 1,
        totalPage: 0,
        startPageSize:0,
        endPageSize: 15,
        totalCount: 0,
        key_word: '',
        operationLogList: [],
        time:null,
        PageData:{
          userName:"",
          startTime:"",
          endTime:"",
          ip:"",
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
      show(id){
        this.operation = true;
        let that = this;
        UserService.getOperationLogById(id).then(function (response) {
          if(response.code == 0) {
            that.reponseContent = response.data.reponseContent;
          } else {
            this.$message({type: 'error', message: response.reason});
          }
        })
      },
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
      formatDate(now) {
        var year=now.getFullYear();
        var month=now.getMonth()+1;
        var date=now.getDate();
        return year+"-"+month+"-"+date;
      },
      /**获取用户列表数据*/
      fetchData (route) {
        var tab = route ? route.query.tab : this.$route.query.tab;
        let that = this;
        setTimeout(() => {
          UserService.findOperationLogList({
            userName: that.PageData.userName,
            startTime: that.PageData.startTime,
            endTime: that.PageData.endTime,
            ip: that.PageData.ip,
          }).then(function (response) {
            if (null != response.data && response.code == 0) {
                that.operationLogList = response.data;
                that.PageData.startTime = null;
                that.PageData.endTime = null;
                that.totalCount = that.operationLogList.length;
                that.totalPage = that.totalCount/that.startPageSize;
                that.operationLogList = that.pagination(that.currentPage,that.endPageSize,that.operationLogList);
                for(var i=0;i<that.operationLogList.length;i++){
                    if(that.operationLogList[i].reponseContent !=null){
                      that.operationLogList[i].reponseContent = that.operationLogList[i].reponseContent.substr(0,30)+"....";
                    }
                }
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
