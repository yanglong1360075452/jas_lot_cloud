<template>

  <div class="container-wrap">
    <div class="container-box">
      <div class="container-content">
        <el-row>
          <el-col :span="12">
            <div class="grid-content bg-purple-dark">
              <el-tag style="font-size: large" color="#20A0FF">康复师详情</el-tag>
            </div>
          </el-col>
        </el-row>
        <div style="margin-left:67%">
          <router-link :to="{ name: 'scores'}" style="color: #20A0FF">
            <el-button type="info" size="">我的积分</el-button>
          </router-link>
          <router-link :to="{ name: 'coupon'}" style="color: #20A0FF">
            <el-button type="info" size="">我的优惠券</el-button>
          </router-link>
        </div>
        <div style="margin: 20px"></div>
        <el-row>
          <el-col>
            <div style="margin: 20px;"></div>
            <div class="grid-content" style="width:100%">
              <el-form :inline="true" :model="user" class="demo-form-inline" label-width="100px">
                <el-col :span="4" style="margin-left:130px">
                  <img v-bind:src="imgUrl"  style="width:200px;height: 220px">
                </el-col>
                <el-form-item label="用户名">
                  <el-input v-model="user.realName" placeholder="用户名" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="性别">
                  <el-input v-model="user.sex" placeholder="性别" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="手机号码">
                  <el-input v-model="user.phone" placeholder="手机号码"  :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
              <br/>
                <el-form-item label="出生年月">
                  <el-input v-model="user.birthDate" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="医院">
                  <el-input v-model="user.hospital" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
        </el-row>
        <div style="margin-left:7%;margin-top:3%;width:100px;height:30px">
          <div style="text-align:center;width: 120px;font-size:16px;height: 40px; background-color: rgb(143, 148, 154); border: 1px solid black;">
            <span style="line-height:39px">我的患者</span>
          </div>
        </div>
      </div>

      <div class="container-content" style="margin-top:25%;width:75%;height:100%;margin-left:6%">
        <el-table :data="patient" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="id" label="ID" width="100" align="center"></el-table-column>
          <el-table-column prop="userName" label="病人姓名" width="120" align="center"></el-table-column>
          <el-table-column prop="sex" label="性别" width="150" align="center"></el-table-column>
          <el-table-column prop="age" label="年龄" width="140" align="center"></el-table-column>
          <el-table-column prop="phone" label="手机号" width="300" align="center"></el-table-column>
          <el-table-column prop="exerciseProcessDesc" label="康复计划" width="478" align="center">
            <template scope="scope">
              <router-link :to="{ name: 'exerciseProcessDetail', params:  { id: scope.row.id}}" style="color: #20A0FF">
                {{scope.row.exerciseProcessDesc}}
              </router-link>
            </template>
          </el-table-column>
        </el-table>
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
        patient:[],
        readonly:true
      }
    },
    created () {
      this.user.id = this.$route.params.id;
      this.getUser(this.user.id);
    },
    methods: {
      getUser(id){
        let that = this;
        UserService.get(id).then(function (response) {
          if (response.code == 0) {
            that.user = response.data;
            that.getExerciseProcess(that.user.doctorId);
            that.imgUrl = that.user.responseEntity;
            that.imgUrl = "data:image/jpg;base64,"+that.imgUrl.body;
            var date = new Date(that.user.birthDate);
            that.user.sex = that.user.sex==1?'男':'女';
            that.user.birthDate = that.formatDate(date, 'yyyy-MM-dd hh:mm');
          }
        })
      },
      getExerciseProcess(id){
        let that = this;
        UserService.getExerciseProcess(id).then(function (response) {
          if (response.code == 0) {
            that.patient = response.data;
            console.log(that.patient)
            for(var i=0;i<that.patient.length;i++){
                if(that.patient[i].sex == "1"){
                  that.patient[i].sex = "男";
                }else if(that.patient[i].sex == "2"){
                  that.patient[i].sex = "女";
                }
            }

          }
        })
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.getUserFriends(this.pageData);
      },
      handleCurrentLivesChange (val) {
        console.log(val)
        this.pageLivesData.currentPage = val;
        this.getLives(this.pageLivesData);
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
