<template>
  <div class="page_select_department top_fixed no_content_bg_color" v-wechat-title="$route.meta.title">
    <div class="department_box">
      <ul class="item_list tag_ul">
        <li @click="item_fn(item.name, item.code)" v-for="(item, index) in medicalArchival.department" class="tag_li" :key="index"><span class="title">{{item.name}}</span></li>
      </ul>
    </div>
  </div>
</template>

<style lang="less">
  @import '../../style/variable.less';
  .page_select_department{
    .tag_li{
      height: 0.99rem;
      line-height: 0.99rem;
      padding-left: 0.3rem;
      padding-right: 0.3rem;
      .fz(30px);
      background: #fff;
      border-bottom: 1px solid @bottom_line_color;
      &:active{
        background: @active_color;
      }
    }
  }
</style>
<style lang="less" scoped>
</style>
<script>

  import api_login_reg from '../../api/login_reg.js'

  export default {
    components: {
    },
    methods: {
      item_fn(name,code){
        this.$router.replace({name:this.$route.params.prev_page, params: {'department':{'name':name, 'code':code}} });
      }
    },
    created(){
      var store_arr=[
      'medicalArchival',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      api_login_reg.get_departments().then(res=>{/*获取科室列表*/ this.medicalArchival.department=res;},err=>{})

    },
    data () {
      return {
        // prev_page:'', // 上一个页面
        medicalArchival: '' // 病历档案表
      }
    }
  }

</script>

