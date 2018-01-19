<template>
  <div>
    
    <div v-for="(item, index) in info_item_data" class="info_item" @click="to_data_info_fn(item)">
      <div class="title " :class="{'history':item.overdue!='0', 'new':user_type=='0'&&item.report=='1'||user_type=='1'&&item.record=='1', }">
        {{
          [,'左','右'][item.postion]+''+ 
          [,'肘关节', '腕关节', '膝关节', '裸关节', '其他'][item.arthro]+'康复计划 - '+ 
          [,'伸展','屈曲'][item.direction]+(item.overdue>'0' ? ' (历史)':'') 
        }} 
      </div>
      <div @click.stop="edit_plan_fn(item, doctor)" class="btn ico_edit"></div>
      <div class="attr">
        <div class="txt_left">
          <span class="time">时间段 {{item.startTime | date_format('MM.DD')}} - {{item.endTime | date_format('MM.DD')}}</span>
          <!-- -1 拒绝,  0 未验证, 1 同意  -->
          <span class="status" v-show="(''+item.isComfirmed!='undefined') && (''+item.isComfirmed!='null')">{{['(已拒绝)','(未验证)',''][item.isComfirmed+1]}}</span>
        </div>
        <div v-if="user_type==0" class="txt_right">康复师 {{tag==1 ? doctor.doctorName : item.realName}}</div>
      </div>
    </div>
    
  </div>
</template>

<script>
  import { dateFormat } from 'vux'
  export default {
    props:[
      'doctor', /*康复师*/
      'info_item_data', /*计划*/
      'tag', /*数据处理方式*/
    ],
    data () {
      return {
        user_type:'',
        doctor_see_user:'',
      } 
    },
    created(){
      this.user_type=this.$store.state.user_type;
      this.doctor_see_user=this.$store.state.temp_data.user_data;
    },
    methods:{
      to_data_info_fn(item){
        console.log('to_data_info_fn', item);
        this.$router.push({name:'data_info', params:{plan_id:item.id}})


        let obj=this.$store.state.temp_data;
        obj.plan_info={
          data:item
        };
        this.$store.commit('temp_data_up',obj)

      },
      edit_plan_fn(item, doctor){
        this.$router.push({name:'exerciseProcess',params:{'info_item':{'item':item, 'doctor':doctor}}});
        $_temp.edit_plan=true;
        console.log('编辑计划', item);
      },
    },
    filters:{
      date_format: function(val,form){
        return dateFormat(new Date(val), form);
      }
    },
    components:{
    },
  }
</script>

<style lang="less" scoped>
  @import '../style/variable.less';
  
</style>
