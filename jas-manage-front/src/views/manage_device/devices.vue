<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="container-content">
        <el-row>
          <el-col :span="12">
            <div class="grid-content bg-purple-dark">
              <el-tag style="font-size: large" color="#20A0FF">设备详情</el-tag>
            </div>
          </el-col>
        </el-row>
        <div class="grid-content bg-purple-dark" align="">
          <el-button type="info" @click="editDevice = true" style="width:100px;margin-left:70%">编辑设备</el-button>
          <el-dialog :visible.sync="editDevice" size="small" title="编辑设备">
            <el-form :model="devices"  ref="devices" >
              <div style="width:100%;height:60px">
                <el-form-item label="设备ID" style="float:left;width:50%">
                  <el-input v-model="devices.id" placeholder="设备ID" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="设备型号">
                  <el-input v-model="devices.model" placeholder="设备型号"  style="width: 200px"></el-input>
                </el-form-item>
              </div>

              <div style="width:100%;height:60px">
                <el-form-item label="设备状态" style="width: 50%;float:left">
                  <el-select v-model="status" :disabled="devices.userId==null?true:false">
                    <el-option style="width:100%;" v-for="item in statusList" :label="item.label" :value="item.value"> </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="设备序列号"  style="width:50%;float:left;">
                  <el-input  v-model="devices.sn" placeholder="设备序列号" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
              </div>

              <div style="width:100%;height:60px">
                <el-form-item label="设备名称" style="float:left;width:50%">
                  <el-input v-model="devices.config"  placeholder="设备配置信息"  style="width: 200px"></el-input>
                </el-form-item>
                <span>生产日期</span>
                <el-date-picker  type="date" placeholder="生产日期" format='yyyy-MM-dd' v-model="devices.produceDate" ></el-date-picker>
              </div>


              <div style="width:100%;;height:60px">
                <div style="float:left;width:50%">
                  <span>激活日期</span>
                  <el-date-picker  type="date"  placeholder="激活日期" format='yyyy-MM-dd' v-model="devices.activateDate" ></el-date-picker>
                </div>
                <div style="float:left;width:50%">
                  <span>报废日期</span>
                  <el-date-picker  type="date" placeholder="报废日期" format='yyyy-MM-dd' v-model="devices.scrapDate" ></el-date-picker>
                </div>
              </div>


              <div style="width:100%;height:60px">
                <el-form-item label="设备类型" style="float:left;width:50%">
                  <el-input v-model="devices.type"  placeholder="设备类型"  style="width: 200px"></el-input>
                </el-form-item>
                <span>创建时间</span>
                <el-date-picker  type="date" placeholder="创建时间" format='yyyy-MM-dd' v-model="devices.createTime" ></el-date-picker>
              </div>

              <div style="width:100%;height:60px">
                <span>更新时间</span>
                <el-date-picker  type="date" placeholder="创建时间" format='yyyy-MM-dd' v-model="devices.updateTime" ></el-date-picker>
                <el-form-item label="设备位置" style="float:left;width:50%">
                  <el-input v-model="devices.type"  placeholder="设备位置"  style="width: 200px"></el-input>
                </el-form-item>
              </div>

            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button type="primary" @click="updateDevice()">提交</el-button>
              <el-button @click="editDevice = false">取消</el-button>
            </div>
          </el-dialog>
        </div>
        <div style="margin: 20px"></div>
        <div class="grid-content" style="">
          <el-row>
            <el-col :span="12">
              <div style="margin: 20px;"></div>
              <div class="grid-content">
                <el-form :inline="true" :model="devices" class="demo-form-inline" label-width="100px">
                  <el-form-item label="设备ID">
                    <el-input v-model="devices.id" placeholder="设备ID" :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                  <el-form-item label="设备型号">
                    <el-input v-model="devices.model" placeholder="设备型号" :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                  <br>
                  <el-form-item label="设备状态">
                    <el-input  placeholder="设备状态" :value="devices.userId==null?'空闲':'使用中'"  :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                  <el-form-item label="设备序列号">
                    <el-input  v-model="devices.sn" placeholder="设备序列号" :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                  <el-form-item label="设备名称">
                    <el-input v-model="devices.config"  placeholder="设备名称" :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>

                  <el-form-item label="生产日期">
                    <el-input v-model="devices.produceDate"   :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                  <el-form-item label="激活日期">
                    <el-input v-model="devices.activateDate"  :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                  <el-form-item label="报废日期">
                    <el-input v-model="devices.scrapDate"   :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                  <el-form-item label="设备类型">
                    <el-input v-model="devices.type"  placeholder="备注" :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                  <el-form-item label="创建时间">
                    <el-input v-model="devices.createTime"  placeholder="备注" :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                  <el-form-item label="更新时间">
                    <el-input v-model="devices.updateTime"  placeholder="备注" :readonly="true" style="width: 200px"></el-input>
                  </el-form-item>
                </el-form>
              </div>
            </el-col>
          </el-row>
        </div>
        <div style="width:1300px;height:40px;text-align: center;font-size:18px;font-weight:bold;"><span>设备履历</span></div>
        <div class="grid-content">
          <el-table :data="deviceRecordList" border stripe tooltip-effect="dark" style="width: 80%">
            <el-table-column prop="id" label="ID" width="150" align="center"></el-table-column>
            <el-table-column prop="deviceId" label="设备ID" width="200" align="center"></el-table-column>
            <el-table-column prop="deviceName" label="设备名" width="200" align="center"> </el-table-column>
            <el-table-column prop="userName" label="绑定用户名" width="180" align="center"></el-table-column>
            <el-table-column prop="event" label="事件" width="197" align="center"> </el-table-column>
            <el-table-column prop="details" label="事件详情" width="200" align="center"> </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="200" show-overflow-tooltip align="center">
              <template scope="scope">
                {{scope.row.createTime | date}}
              </template>
            </el-table-column>
            <!--<el-table-column prop="updaetTime" label="更新时间" width="200" show-overflow-tooltip align="center">-->
            <!--<template scope="scope">-->
            <!--{{scope.row.updaetTime | date}}-->
            <!--</template>-->
            <!--</el-table-column>-->

          </el-table>
        </div>

      </div>
      <div class="page-wrap" style="width:1300px">
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
  import UserService from '../../service/user';
  import Util from '../../assets/lib/util';
  export default {
    data() {
      return {
        currentPage: 1,
        totalPage: 0,
        startPageSize:0,
        endPageSize: 10,
        totalCount: 0,
        readOnly: true,
        devices: {},
        deviceRecordList:[],
        editDevice:false,
        //状态列表
        statusList: [
          {
            value: '0',
            label: '使用中'
          }, {
            value: '1',
            label: '空闲'
          }],
        status:null
      }
    },
    created () {
      this.devices.id = this.$route.params.id;
      this.readOnly = this.$route.params.readOnly;
      this.getDevices(this.devices.id);
      this.getDeviceRecordList(this.devices.id);
    },

    methods: {
      closeDevice(){
        this.editDevice=true;
      },
      updateDevice(){
        this.devices.status = this.status;
        this.devices.produceDate = new Date(this.devices.produceDate);
        this.devices.produceDate= new Date(this.devices.produceDate);
        this.devices.activateDate= new Date(this.devices.activateDate);
        this.devices.scrapDate= new Date(this.devices.scrapDate);
        this.devices.createTime= new Date(this.devices.createTime);
        this.devices.updateTime= new Date(this.devices.updateTime);
        DeviceService.updateDevice(this.devices).then(response => {
          if (response.code == 0) {
            this.$message({type: 'success', message: "编辑设备成功"});
            this.editDevice=false;
            this.getDevices(this.devices.id);
            this.getDeviceRecordList(this.devices.id);
          } else {
            this.$message({type: 'error', message: "创建样本失败"});
            return;
          }
        });
      },
      getDevices(id){
        let that = this;
        DeviceService.getDeviceDetail(id).then(function (response) {
          if(response.code == 0) {
            that.devices = response.data.data;
            var dateP = new Date(that.devices.produceDate);
            var dateA = new Date(that.devices.activateDate);
            var dateS = new Date(that.devices.scrapDate);
            var dateC = new Date(that.devices.createTime);
            var dateU = new Date(that.devices.updateTime);
            that.devices.produceDate = that.formatDate(dateP, 'yyyy-MM-dd');
            that.devices.activateDate = that.formatDate(dateA, 'yyyy-MM-dd');
            that.devices.scrapDate = that.formatDate(dateS, 'yyyy-MM-dd');
            that.devices.createTime = that.formatDate(dateC, 'yyyy-MM-dd');
            that.devices.updateTime = that.formatDate(dateU, 'yyyy-MM-dd');
            if(null !=that.devices.userId){
              that.status="0";
            }else{
              that.status="1";
            }
          } else {
            that.$message({type: 'error', message: response.reason});
          }
        })
      },
      getDeviceRecordList(deviceId){
        let that = this;
        DeviceService.getDeviceRecordList(deviceId).then(function (response) {
          if(response.code == 0) {
            that.deviceRecordList = response.data;
            if(that.deviceRecordList.length>0){
                console.log(that.deviceRecordList)
              that.totalCount = that.deviceRecordList.length;
              that.totalPage = that.totalCount/that.startPageSize;
              that.deviceRecordList = that.pagination(that.currentPage,that.endPageSize,that.deviceRecordList);
              that.deviceRecordList = that.deviceRecordList.slice(that.startPageSize,that.endPageSize);
            }
          } else {
            that.$message({type: 'error', message: response.reason});
          }
        })
      },
      pagination(pageNo, pageSize, array) {
        var offset = (pageNo-1) * pageSize;
        return (offset + pageSize >= array.length) ? array.slice(offset, array.length) : array.slice(offset, offset + pageSize);
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.getDeviceRecordList(this.devices.id);
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
