<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>
      <div class="search-box" style="positin:fixed;width:100%;">
        <div style="width: 30%; height: 40px; margin-left: 4%; padding-top: 5px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">用户:</label>
          <template>
            <el-input placeholder="请输入用户名称" style="width:400px"  v-model="PageData.name"> </el-input>
          </template>
        </div>
        <div style="width: 26%; margin-left: 34%; height: 40px; margin-top: -40px;" >
          <span style="margin-right:10px;font-size:16px">导入时间:</span>
            <template>
              <el-date-picker v-model="time" type="daterange" placeholder="选择日期范围">
              </el-date-picker>
            </template>
        </div>
        <div style="width: 24%; margin-left: 54%; height: 40px; margin-top: -40px;" >
        <template>
          <el-select v-model="PageData.status" clearable placeholder="请选择">
            <el-option v-for="item in statusList" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </template>
          <label style="float: left;margin-right:8px;margin-top:6px;font-size:16px">状态:</label>
          <el-button type="primary" style="margin-left:15%" @click="handleIconClick">查询</el-button>
        </div>
        <div style="width: 5%; margin-left: 77%; height: 40px; margin-top: -40px;" >
          <el-button type="primary" style="margin-left:20%" @click="download">导出模板</el-button>
        </div>
        <div style="width: 5%; margin-left: 85%; height: 40px; margin-top: -40px;" >
          <el-upload  action="" class="upload-demo" :file-list="importCouponFile"
                      :on-change="importExcel"
                      :show-file-list="false" :auto-upload="false" ref="importCoupon">
            <el-button type="primary" slot="trigger" style="margin-left:20%">导入</el-button>
          </el-upload>
        </div>
      </div>
      <div class="container-content">
        <el-table :data="couponList" ref="couponTable" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column  label="ID" width="150" align="center">
            <template scope="scope">
              {{scope.row.id}}
            </template>
          </el-table-column>
          <el-table-column prop="couponMoney" label="优惠券金额" width="200" align="center"> </el-table-column>
          <el-table-column prop="couponPassword" label="优惠券密码" width="200" align="center"> </el-table-column>
          <el-table-column label="优惠券导入时间" width="180" align="center">
            <template scope="scope">
              {{scope.row.createTime | date}}
            </template>
          </el-table-column>
          <el-table-column prop="validity" label="优惠券有效期" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.validity | date}}
            </template>
          </el-table-column>
          <el-table-column  label="姓名" width="200" align="center">
            <template scope="scope">
              {{scope.row.name}}
            </template>
          </el-table-column>
          <el-table-column  prop="redeemScore" label="兑换的积分值" width="250" align="center"></el-table-column>
          <el-table-column  label="状态" width="180" align="center">
            <template scope="scope">
              {{scope.row.validity>=new Date()?'有效':'无效'}}
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
        couponList: [],
        importCouponFile:[],
        importCouponList:[],
        time:null,
        PageData:{
          name:"",
          startTime:"",
          endTime:"",
          status:"",
        },
        //状态列表
        statusList: [
          {
            value: '',
            label: '请选择'
          }, {
          value: '0',
          label: '有效'
        }, {
          value: '1',
          label: '无效'
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

    },
    methods: {
      //下载模板
      download(){
        let columns = this.$refs.couponTable.$children.filter(t => t.prop != null);
        const fields = columns.map(t => t.prop);
        const fieldNames = columns.map(t => t.label);
        Util.download(null, fields, fieldNames, '优惠券模板')
      },
      //导入清单
      importExcel(file, fileList) {
        let that = this;
        if (fileList.length > 1) {
          fileList.splice(0, 1);//文件列表保留最后一个文件
        }
        var uploadFiles = this.$refs["importCoupon"].uploadFiles;
        if(uploadFiles.length == 0){
            return;
        }else {
          let finalFile = uploadFiles[0],
            types = finalFile.name.split('.')[1],
            fileType = ["xlsx", "xlc", "xlm", "xls", "xlt", "xlw", "csv"].some(item => item === types);

          if (!fileType) {
            this.$message({type: 'info', message: "文件格式有误,请重新选择!"});
            return;
          }
          readExcel(finalFile.raw).then(tabJson => {
              var uploadData = [];
            if (tabJson && tabJson.length > 0) {
              let columns = this.$refs.couponTable.$children.filter(t => t.prop != null);
              for (var i = 0; i < tabJson.length; i++) {
                var coupon = {};
                for (var prop in tabJson[i]) {
                  for (var j = 0; j < columns.length; j++) {
                    if (prop == columns[j].label) {
                      coupon[columns[j].prop] = tabJson[i][prop];
                      break;
                    }
                  }
                }
                uploadData.push(coupon);
              }
              that.importExcelCoupon(uploadData);
            }
          });
        }
      },
      importExcelCoupon(importCouponFile){
        let that = this;
        if(importCouponFile.length>0){
          for(var i = 0;i<importCouponFile.length;i++){
            let coupon ={}
            if(importCouponFile[i].createTime){
              var createTime = Date.parse(new Date(importCouponFile[i].createTime));
              importCouponFile[i].createTime = that.formatDate(new Date(createTime))
            }
            if(importCouponFile[i].validity){
              var validity = Date.parse(new Date(importCouponFile[i].validity));
              importCouponFile[i].validity = that.formatDate(new Date(validity));
            }
            coupon.couponPassword = importCouponFile[i].couponPassword;
            coupon.redeemScore = importCouponFile[i].redeemScore;
            coupon.couponMoney = importCouponFile[i].couponMoney;
            coupon.validity = importCouponFile[i].validity;
//            coupon.createTime = importCouponFile[i].createTime;
            that.importCouponList.push(coupon)
          }

          console.log(that.importCouponList)
          UserService.importCoupon(that.importCouponList).then(function (response) {
            if (response.code == 0) {
              that.fetchData();
              that.$message({type: 'success', message: "优惠券导入成功"});
              that.importCouponList=[];
            }
          })
        }
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
          UserService.getCouponList({
            userName: that.PageData.name,
            startTime: that.PageData.startTime,
            endTime: that.PageData.endTime,
            status: that.PageData.status,
          }).then(function (response) {
            if (null != response.data && response.code == 0) {
                that.couponList = response.data;
                that.PageData.startTime = null;
                that.PageData.endTime = null;
                that.totalCount = that.couponList.length;
                that.totalPage = that.totalCount/that.startPageSize;
                that.couponList = that.pagination(that.currentPage,that.endPageSize,that.couponList);
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
