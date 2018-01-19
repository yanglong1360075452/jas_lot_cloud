<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>
      <div class="search-box" style="positin:fixed;width:100%;">
        <div style="width: 20%; height: 40px;margin-left:4%" >
          <span style="margin-right:10px;font-size:16px">导入时间:</span>
          <template>
            <el-date-picker v-model="time" type="daterange" placeholder="选择日期范围">
            </el-date-picker>
          </template>
        </div>

        <div style="width: 15%; height: 40px; margin-left: 23%;margin-top:-40px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">患者姓名:</label>
          <template>
            <el-input placeholder="请输入患者姓名" style="width:150px"  v-model="exerciseRecordDto.patientName"> </el-input>
          </template>
        </div>

        <div style="width: 18%; margin-left: 38%; height: 40px; margin-top: -40px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">关节部位:</label>
          <template>
            <el-select v-model="exerciseRecordDto.arthro" clearable placeholder="请选择">
              <el-option v-for="item in arthroList" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </template>
        </div>

        <div style="width: 18%; margin-left: 56%; height: 40px; margin-top: -40px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">左右:</label>
          <template>
            <el-select v-model="exerciseRecordDto.postion" clearable placeholder="请选择">
              <el-option v-for="item in postionList" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </template>
        </div>
        <div style="width: 18%; margin-left: 72%; height: 40px; margin-top: -40px; font-size: 16px;">
          <label style="float: left;margin-right:8px;margin-top:5px">屈伸方向:</label>
          <template>
            <el-select v-model="exerciseRecordDto.direction" clearable placeholder="请选择">
              <el-option v-for="item in directionList" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </template>
        </div>

        <div style="width: 5%; margin-left: 88%; height: 40px; margin-top: -40px;" >
          <el-button type="primary" style="margin-left:20%" @click="handleIconClick">查询</el-button>
        </div>
        <div style="width: 5%; margin-left: 93%; height: 40px; margin-top: -40px;" >
          <el-button type="primary" style="margin-left:20%" @click="download">导出</el-button>
        </div>
      </div>
      <div class="container-content">
        <el-table :data="exerciseRecordList" ref="exerciseRecordTable" border stripe tooltip-effect="dark" style="width: 100%">
          <el-table-column prop="id" label="ID" width="100" align="center"> </el-table-column>
          <el-table-column prop="patientName" label="患者姓名" width="200" align="center"> </el-table-column>
          <el-table-column prop="startTime" label="训练日期"  width="120" show-overflow-tooltip align="center"> </el-table-column>
          <el-table-column prop="endTime" label="训练时间"  width="120" format='yyyy-MM-dd' show-overflow-tooltip align="center"> </el-table-column>
          <el-table-column prop="trainingLength" label="训练时长" width="100" align="center"> </el-table-column>
          <el-table-column prop="arthroDesc" label="训练关节" width="120" show-overflow-tooltip align="center"></el-table-column>
          <el-table-column prop="postionDesc" label="训练部位" width="120" align="center"> </el-table-column>
          <el-table-column prop="directionDesc" label="训练方向" width="100" align="center"></el-table-column>
          <el-table-column prop="startAngle" label="起始角度" width="100" align="center"></el-table-column>
          <el-table-column prop="endAngle" label="结束角度" width="100" align="center"></el-table-column>
          <el-table-column prop="geolocation" label="地理位置" width="300" align="center"> </el-table-column>
          <el-table-column prop="sr" label="sr" width="198" align="center"> </el-table-column>
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
        exerciseRecordList: [],
        time:null,
        exerciseRecordDto:{
          patientName:null,
          arthro:null,
          postion:null,
          direction:null,
          startTime:null,
          endTime:null,
        },
        //关节部位列表
        arthroList: [
          {
            value: '',
            label: '请选择'
          },{
            value: '1',
            label: '肘关节'
          }, {
            value: '2',
            label: '腕关节'
          }, {
            value: '3',
            label: '膝关节'
          }, {
            value: '4',
            label: '裸关节'
          }, {
            value: '5',
            label: '其他'
          }],
        //部位列表
        postionList: [
          {
            value: '',
            label: '请选择'
          },{
            value: '1',
            label: '左'
          },{
            value: '2',
            label: '右'
          }],
        //方向列表
        directionList: [
          {
            value: '',
            label: '请选择'
          },{
            value: '1',
            label: '伸展'
          },{
            value: '2',
            label: '屈曲'
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
        let columns = this.$refs.exerciseRecordTable.$children.filter(t => t.prop != null);
        const fields = columns.map(t => t.prop);
        const fieldNames = columns.map(t => t.label);
        let that = this;
        setTimeout(() => {
          UserService.findExerciseRecordList({
            patientName:that.exerciseRecordDto.patientName,
            arthro:that.exerciseRecordDto.arthro,
            postion:that.exerciseRecordDto.postion,
            direction:that.exerciseRecordDto.direction,
            startTime:that.exerciseRecordDto.startTime,
            endTime:that.exerciseRecordDto.endTime,
          }).then(function (response) {
            if (null != response.data && response.code == 0) {
              that.exerciseRecordList = response.data;
              that.exerciseRecordDto.startTime = null;
              that.exerciseRecordDto.endTime = null;
              for(var i=0;i<that.exerciseRecordList.length;i++){
                var startTime = new Date(that.exerciseRecordList[i].startTime);
                var endTime = new Date(that.exerciseRecordList[i].endTime);
                that.exerciseRecordList[i].endTime = that.formatDate(endTime, 'hh:mm:ss');
                that.exerciseRecordList[i].startTime = that.formatDate(startTime, 'yyyy-MM-dd');
              }
              Util.download(that.exerciseRecordList, fields, fieldNames, '训练记录')
            }
            else that.$message({type: 'error', message: response.reason});
          });
        }, 300);

      },
      handleIconClick () {
        if(this.time){
          if(this.time[0] !=null && this.time[1] !=null){
            this.exerciseRecordDto.startTime = +new Date(this.time[0]);
            this.exerciseRecordDto.endTime = +new Date(this.time[1]);
          }
        }else{
          this.exerciseRecordDto.startTime = null;
          this.exerciseRecordDto.endTime = null;
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
      },
      /**获取用户列表数据*/
      fetchData (route) {
        var tab = route ? route.query.tab : this.$route.query.tab;
        let that = this;
        setTimeout(() => {
          UserService.findExerciseRecordList({
            patientName:that.exerciseRecordDto.patientName,
            arthro:that.exerciseRecordDto.arthro,
            postion:that.exerciseRecordDto.postion,
            direction:that.exerciseRecordDto.direction,
            startTime:that.exerciseRecordDto.startTime,
            endTime:that.exerciseRecordDto.endTime,
          }).then(function (response) {
            if (null != response.data && response.code == 0) {
                that.exerciseRecordList = response.data;
                that.exerciseRecordDto.startTime = null;
                that.exerciseRecordDto.endTime = null;
                that.totalCount = that.exerciseRecordList.length;
                that.totalPage = that.totalCount/that.startPageSize;
                that.exerciseRecordList = that.pagination(that.currentPage,that.endPageSize,that.exerciseRecordList);
                for(var i=0;i<that.exerciseRecordList.length;i++){
                  var startTime = new Date(that.exerciseRecordList[i].startTime);
                  var endTime = new Date(that.exerciseRecordList[i].endTime);
                  that.exerciseRecordList[i].endTime = that.formatDate(endTime, 'hh:mm:ss');
                  that.exerciseRecordList[i].startTime = that.formatDate(startTime, 'yyyy-MM-dd');
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
