<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>
      <div class="search-box" style="positin:fixed;width:100%;">
        <div style="width: 30%; height: 40px; margin-left: 4%; padding-top: 5px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">医院:</label>
          <template>
            <el-input placeholder="请输入医院名称" style="width:400px"  v-model="PageData.hospital"> </el-input>
          </template>
        </div>
        <div style="width: 30%; margin-left: 34%; height: 40px; margin-top: -40px;" >
          <span style="margin-right:10px;font-size:16px">电话:</span>
           <el-input placeholder="请输入电话号码" style="width:400px"  v-model="PageData.phone"> </el-input>
        </div>
        <div style="width: 25%; margin-left: 64%; height: 40px; margin-top: -40px;" >
        <template>
          <el-select v-model="PageData.status" clearable placeholder="请选择">
            <el-option v-for="item in statusList" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </template>
          <label style="float: left;margin-right:8px;margin-top:6px;font-size:16px">状态:</label>
          <el-button type="primary" style="margin-left:20%" @click="handleIconClick">查询</el-button>
        </div>
      </div>
      <div class="container-content">
        <el-table :data="usersExamine" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="id" label="ID" width="150" align="center"></el-table-column>
          <el-table-column prop="realName" label="用户名" width="200" align="center">

          </el-table-column>
          <el-table-column prop="sex" label="性别" width="180" align="center">
          <template scope="scope">
            {{scope.row.sex == 1?"男":"女"}}
          </template>
          </el-table-column>
          <el-table-column prop="birthDate" label="出生年月" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.birthDate | date}}
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="手机号码" width="250" align="center"></el-table-column>
          <el-table-column prop="createTime" label="注册时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.createTime | date}}
            </template>
          </el-table-column>
          <el-table-column prop="statusDesc" label="状态" width="180" align="center"></el-table-column>
          <el-table-column label="操作" align="center">
            <template scope="scope">
              <!--<el-button @click="updateUserStatus(scope.row)" type="info" size="small">-->
                <!--{{scope.row.status == 1 ? '禁用' : '激活'}}-->
              <!--</el-button>-->
                <el-button type="info" size="small" @click="passExamine(scope.row.id)" v-if="scope.row.status==0">通过审核</el-button>
                <el-button type="info" size="small" disabled="true" @click="passExamine(scope.row.id)" v-if="scope.row.status==1">通过审核</el-button>
                <el-button type="info" size="small" @click="passExamine(scope.row.id)" v-if="scope.row.status==2">通过审核</el-button>
                <el-button type="error" size="small" @click="noPassExamine(scope.row.id)" v-if="scope.row.status==0">驳回审核</el-button>
                <el-button type="error" size="small" @click="noPassExamine(scope.row.id)" v-if="scope.row.status==1">驳回审核</el-button>
                <el-button type="error" size="small" disabled="true" @click="noPassExamine(scope.row.id)" v-if="scope.row.status==2">驳回审核</el-button>
              <!--<el-button @click="deleteUser(scope.row)" type="danger" size="small">删除</el-button>-->
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
        usersExamine: [],
        PageData:{
          hospital:"",
          phone:"",
          status:"",
        },
        //状态列表
        statusList: [
          {
            value: '',
            label: '请选择'
          },{
          value: '0',
          label: '审核中'
        }, {
          value: '1',
          label: '审核同意'
        }, {
          value: '2',
          label: '审核拒绝'
        }],
      }
    },
    created () {
      this.fetchData();
    },
    watch: {
      '$route': 'fetchData'
    },
    computed: {
      keyWordUsers () {
        var arr = [];
        if (!this.key_word)
          return this.users;
        else {
          this.users.forEach((item) => {
            if (item.username.indexOf(this.key_word) > -1)
              arr.push(item);
          });
          return arr;
        }
      }
    },
    methods: {
      passExamine(id){
        let that = this;
        setTimeout(() => {
          UserService.userExaminePassOnoPass({
            userId: id,
            status: 1,
          }).then(function (response) {
            if (response.code == 0) {
              that.$message({type: 'info', message: "审核成功"});
              that.fetchData();
            }
            else that.$message({type: 'error', message: "审核失败"});
          });
        }, 300);
      },
      noPassExamine(id){
        let that = this;
        setTimeout(() => {
          UserService.userExaminePassOnoPass({
            userId: id,
            status: 2,
          }).then(function (response) {
            if (response.code == 0) {
              that.$message({type: 'info', message: "申请已驳回"});
              that.fetchData();
            }
            else that.$message({type: 'error', message: "申请驳回失败"});
          });
        }, 300);
      },
      handleIconClick () {
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
          UserService.getuserExamineList({
            hospital: that.PageData.hospital,
            phone: that.PageData.phone,
            status: that.PageData.status,
          }).then(function (response) {
            if (null != response.data && response.code == 0) {
                that.usersExamine = response.data;
                that.totalCount = that.usersExamine.length;
                that.totalPage = that.totalCount/that.startPageSize;
                that.usersExamine = that.pagination(that.currentPage,that.endPageSize,that.usersExamine);
                //that.users = that.users.slice(that.startPageSize,that.endPageSize);
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
