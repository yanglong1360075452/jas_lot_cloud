<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>
      <div class="search-box" style="positin:fixed;width:100%;">
        <div style="width: 30%; height: 40px; margin-left: 4%; padding-top: 5px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">设备名称:</label>
          <template>
            <el-input placeholder="请输入设备名称" style="width:400px"  v-model="PageData.deviceName"> </el-input>
          </template>
        </div>
        <div style="width: 30%; margin-left: 34%; height: 40px; margin-top: -40px;" >
          <span style="margin-right:10px;font-size:16px">绑定人名称:</span>
            <template>
              <el-input placeholder="请输入绑定人名称" style="width:400px"  v-model="PageData.patientName"> </el-input>
            </template>
        </div>
        <div style="width: 25%; margin-left: 64%; height: 40px; margin-top: -40px;" >
          <template>
            <el-select v-model="PageData.status" clearable placeholder="请选择">
              <el-option v-for="item in statusList" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </template>
          <label style="float: left;margin-right:8px;margin-top:6px;font-size:16px">状态:</label>
          <el-button type="primary" style="margin-left:3%" @click="handleIconClick">查询</el-button>
          <div style="width: 5%; margin-left: 78%; height: 40px; margin-top: -37px;" >
            <el-button type="primary" style="margin-left:20%" @click="download">导出模板</el-button>
          </div>
          <div style="width: 5%; margin-left: 100%; height: 40px; margin-top: -40px;" >
            <el-upload  action="" class="upload-demo" :file-list="importDeviceFile"
                       :on-change="importExcel"
                       :show-file-list="false" :auto-upload="false" ref="importDevice">
              <el-button type="primary" slot="trigger" style="margin-left:20%">导入</el-button>
            </el-upload>
          </div>
        </div>
      </div>

      <div class="container-content">
        <el-table :data="deviceList" ref="deviceTable" border stripe tooltip-effect="dark" style="width: 92%">
          <el-table-column  label="ID" width="80" align="center">
            <template scope="scope">
              <router-link :to="{ name: 'devices',params:  { id: scope.row.id}}" style="color: #20A0FF">
                {{scope.row.id}}
              </router-link>
            </template>
          </el-table-column>
          <el-table-column prop="config" label="设备名称" width="180" align="center"> </el-table-column>
          <el-table-column prop="produceDate" label="生产日期" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.produceDate | date}}
            </template>
          </el-table-column>
          <el-table-column prop="activateDate" label="激活日期" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.activateDate | date}}
            </template>
          </el-table-column>
          <el-table-column  label="是否绑定" width="100" align="center">
            <template scope="scope">
              {{scope.row.userId!=null?'是':'否'}}
            </template>
          </el-table-column>
          <el-table-column  prop="model"  label="设备型号" width="120" align="center"></el-table-column>
          <el-table-column prop="sn"  label="设备序列号" width="120" align="center"></el-table-column>
          <el-table-column prop="scrapDate"  label="报废日期" width="180" align="center">
            <template scope="scope">
              {{scope.row.scrapDate | date}}
            </template>
          </el-table-column>
          <el-table-column  label="绑定人姓名" width="120" align="center">
            <template scope="scope">
              {{scope.row.patientName}}
            </template>
          </el-table-column>

          <el-table-column  label="绑定时间" width="180" show-overflow-tooltip align="center">
            <template scope="scope">
              {{scope.row.updateTime | date}}
            </template>
          </el-table-column>
          <el-table-column  label="状态" width="100" align="center">
            <template scope="scope">
              {{scope.row.userId==null?'空闲':'使用中'}}
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
  import DeviceService from '../../service/device';
  import types from '../../store/mutation-types';
  import {readExcel, exportExcel} from '@/utils/excelUtils';
  import Util from '@/utils/util';
  export default {
    name: 'list',
    data() {
      return {
        showD:false,
        currentPage: 1,
        totalPage: 0,
        startPageSize:0,
        endPageSize: 15,
        totalCount: 0,
        key_word: '',
        deviceList: [],
        importDeviceFile:[],
        importDeviceList:[],
        PageData:{
          deviceName:null,
          patientName:null,
          status:null,
        },
        //状态列表
        statusList: [
          {
            value: '',
            label: '请选择'
          }, {
            value: '0',
            label: '使用中'
          }, {
            value: '1',
            label: '空闲'
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
        let columns = this.$refs.deviceTable.$children.filter(t => t.prop != null);
        const fields = columns.map(t => t.prop);
        const fieldNames = columns.map(t => t.label);
        Util.download(null, fields, fieldNames, '设备模板')
      },
      //导入清单
      importExcel(file, fileList) {
          let that = this;
        if (fileList.length > 1) {
          fileList.splice(0, 1);//文件列表保留最后一个文件
        }
        var uploadFiles = that.$refs["importDevice"].uploadFiles;
        if (uploadFiles.length == 0) {
          return;
        }else{
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
              let columns = this.$refs.deviceTable.$children.filter(t => t.prop != null);
              for (var i = 0; i < tabJson.length; i++) {
                var device = {};
                for (var prop in tabJson[i]) {
                  for (var j = 0; j < columns.length; j++) {
                    if (prop == columns[j].label) {
                      device[columns[j].prop] = tabJson[i][prop];
                      break;
                    }
                  }
                }
                uploadData.push(device);
              }
              that.importExcelDevice(uploadData);
            }
          });
        }

      },
      importExcelDevice(importDeviceFile){
        let that = this;
        if(importDeviceFile.length>0){
          for(var i = 0;i<importDeviceFile.length;i++){
              let device ={}
              if(importDeviceFile[i].produceDate){
                var produceDate = Date.parse(new Date(importDeviceFile[i].produceDate));
                importDeviceFile[i].produceDate = that.formatDate(new Date(produceDate))
              }
              if(importDeviceFile[i].activateDate){
                var activateDate = Date.parse(new Date(importDeviceFile[i].activateDate));
                importDeviceFile[i].activateDate = that.formatDate(new Date(activateDate))
              }
              if(importDeviceFile[i].scrapDate){
                var scrapDate = Date.parse(new Date(importDeviceFile[i].scrapDate));
                importDeviceFile[i].scrapDate = that.formatDate(new Date(scrapDate))
              }
              device.model = importDeviceFile[i].model;
              device.sn = importDeviceFile[i].sn;
              device.config = importDeviceFile[i].config;
              device.scrapDate = importDeviceFile[i].scrapDate;
              device.activateDate = importDeviceFile[i].activateDate;
              device.produceDate = importDeviceFile[i].produceDate;
              that.importDeviceList.push(device)
          }
          DeviceService.importDevice(that.importDeviceList).then(function (response) {
            if (response.code == 0) {
              that.fetchData();
              that.$message({type: 'success', message: "设备导入成功"});
              that.importDeviceList = [];
            }
          })
        }
      },
    formatDate(now) {
    var year=now.getFullYear();
    var month=now.getMonth()+1;
    var date=now.getDate();
    return year+"-"+month+"-"+date;
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
          DeviceService.findDeviceList({
            deviceName: that.PageData.deviceName,
            patientName: that.PageData.patientName,
            status: that.PageData.status,
          }).then(function (response) {
            if (null != response.data && response.code == 0) {
              that.deviceList = response.data;
                that.totalCount = that.deviceList.length;
                that.totalPage = that.totalCount/that.startPageSize;
                that.deviceList = that.pagination(that.currentPage,that.endPageSize,that.deviceList);
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
