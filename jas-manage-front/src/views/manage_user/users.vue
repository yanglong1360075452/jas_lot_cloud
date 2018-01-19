<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>
      <div class="search-box" style="positin:fixed;width:100%;">
        <div style="width: 20%;height:40px; margin-left: 6%; padding-top: 10px; font-size: 16px;">
          <label style="float: left;margin-right:8px">用户类别:</label>
          <template>
            <el-checkbox v-model="patients" style="fonti-size:16px" @change="findType()">患者</el-checkbox>
            <el-checkbox v-model="doctors" style="fonti-size:16px" @change="findTypeO()">康复师</el-checkbox>
          </template>
        </div>
        <div style="width: 40%; margin-left: 25%;height: 40px; margin-top: -45px;" >
          <span style="margin-right:10px;font-size:16px">用户名:</span>
          <el-input placeholder="请输入关键字" style="width:400px" icon="search" v-model="PageData.name" :on-icon-click="handleIconClick"> </el-input>
        </div>
      </div>
      <div class="container-content">
        <el-table :data="users" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="id" label="ID" width="150" align="center"></el-table-column>

          <el-table-column prop="realName" label="用户名" width="200" align="center">
            <template scope="scope">
              <router-link :to="{ name: 'user_patient', params:  { id: scope.row.id}}" style="color: #20A0FF" v-if="scope.row.type=='病人'">
                {{scope.row.realName}}
              </router-link>
              <router-link :to="{ name: 'user_doctor', params:  { id: scope.row.id}}" style="color: #20A0FF" v-if="scope.row.type=='康复师'">
                {{scope.row.realName}}
              </router-link>
            </template>
          </el-table-column>
          <el-table-column prop="sex" label="性别" width="180" align="center">
            <template scope="scope">
              {{scope.row.sex == 1?"男":"女"}}
            </template>
          </el-table-column>
          <el-table-column prop="type" label="用户类型" width="180" align="center"></el-table-column>
          <el-table-column prop="phone" label="手机号码" width="250" align="center"></el-table-column>
          <el-table-column prop="createTime" label="注册时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.createTime | date}}
            </template>
          </el-table-column>
          <!--<el-table-column prop="statusDesc" label="状态" width="180" align="center"></el-table-column>-->
          <el-table-column label="操作" align="center">
            <template scope="scope">
              <!--<el-button @click="updateUserStatus(scope.row)" type="info" size="small">-->
              <!--{{scope.row.status == 1 ? '禁用' : '激活'}}-->
              <!--</el-button>-->

              <!--<router-link :to="{ name: 'user', params:  { id: scope.row.id,readOnly:false}}" style="color: white">-->
              <el-button type="info" size="small">激活</el-button>
              <el-button type="error" size="small">禁用</el-button>
              <!--</router-link>-->

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
        users: [],
        PageData:{
          patient:"",
          doctor:"",
          name:"",
        },
        patients:"",
        doctors:""
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
      findType(){
        this.patients==""?1:"";
        if(this.patients){
          this.PageData.patient=0;
        }else{
          this.PageData.patient="";
        }
        this.fetchData();
      },
      findTypeO(){
        this.doctor==""?1:"";
        if(this.doctors){
          this.PageData.doctor=1;
        }else{
          this.PageData.doctor="";
        }
        this.fetchData();
      },
      handleIconClick () {
        if (!this.PageData.name) {
          this.$message({type: 'info', message: '请输入用户名'});
          return;
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
      /**更新用户状态*/
      updateUserStatus (user) {
        var title = '';
        if (user.status == 1) {
          title = "禁用";
        } else {
          title = "激活";
        }
        this.$confirm('是否' + title + user.username + '?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (user.status == 1) {
            user.status = 2;
          } else {
            user.status = 1;
          }
          let that = this;
          UserService.update(user).then(function (response) {
            if (response.code == 0) {
              that.$message({type: 'success', message: "操作成功"});
              that.fetchData();
            } else {
              that.$message({type: 'fail', message: response.reason});
            }
          });
        }).catch(() => {
          this.$message({type: 'info', message: '已取消操作'});
        });
      },
      /**获取用户列表数据*/
      fetchData (route) {
        var tab = route ? route.query.tab : this.$route.query.tab;
        let that = this;
        setTimeout(() => {
          UserService.getList({
            patient: that.PageData.patient,
            doctor: that.PageData.doctor,
            name: that.PageData.name,
          }).then(function (response) {
            if (null != response.data && response.code == 0) {
              that.users = response.data;
                that.totalCount = that.users.length;
                that.totalPage = that.totalCount/that.startPageSize;
                that.users = that.pagination(that.currentPage,that.endPageSize,that.users);
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
