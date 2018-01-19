<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>
      <div class="search-box" style="positin:fixed;width:100%;">
        <div style="width: 40%; margin-left: 4%; height: 40px; margin-top: 5px;" >
          <span style="margin-right:10px;font-size:16px">用户名:</span>
          <el-input placeholder="请输入用户名" style="width:400px"  v-model="score.userName"> </el-input>
        </div>
        <div style="width: 40%; height: 40px; margin-left: 37%; margin-top: -40px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:7px">日期范围:</label>
          <template>
              <el-date-picker v-model="time" type="daterange" placeholder="选择日期范围">
              </el-date-picker>
          </template>
          <el-button type="primary" style="margin-left:20%" @click="handleIconClick">查询</el-button>
        </div>

      </div>
      <div class="container-content">
        <el-table :data="scoreList" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="id" label="ID" width="150" align="center"></el-table-column>
          <el-table-column prop="userName" label="用户名" width="200" align="center"></el-table-column>
          <el-table-column prop="type" label="积分变化操作" width="180" align="center"> </el-table-column>
          <el-table-column prop="changeValue" label="变化值" width="180" align="center"></el-table-column>
          <el-table-column prop="scoreValue" label="剩余积分" width="250" align="center"></el-table-column>
          <el-table-column prop="createTime" label="创建时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.createTime | date}}
            </template>
          </el-table-column>
          <el-table-column prop="updaetTime" label="更新时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.updaetTime | date}}
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
        scoreList: [],
        time:null,
        score:{
         userName:"",
         startTime:null,
         endTime:null,
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
              this.score.startTime = +new Date(this.time[0]);
              this.score.endTime = +new Date(this.time[1]);
            }
        }else{
          this.score.startTime = null;
          this.score.endTime = null;
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
          UserService.getScoresList({
            userName:that.score.userName,
            startTime:that.score.startTime,
            endTime:that.score.endTime
          }).then(function (response) {
            if (null != response.data && response.code == 0) {
              that.scoreList = response.data;
              that.score.startTime = null;
              that.score.endTime = null;
                that.totalCount = that.scoreList.length;
                that.totalPage = that.totalCount/that.startPageSize;
                that.scoreList = that.pagination(that.currentPage,that.endPageSize,that.scoreList);
                that.users = that.scoreList.slice(that.startPageSize,that.endPageSize);
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
