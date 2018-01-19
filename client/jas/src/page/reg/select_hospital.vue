<template>
  <div class="page_select_hospital top_fixed no_content_bg_color" v-wechat-title="$route.meta.title">

    <div class="box">

      <search
      @result-click="resultClick"
      @on-change="getResult"
      :results="results"
      v-model="value"
      position="absolute"
      auto-scroll-to-top top="0px"
      placeholder="搜索医院"
      @on-focus="onFocus"
      @on-cancel="onCancel"
      @on-submit="onSubmit"
      ref="search"></search>

    </div>

    <div class="city_box" v-show="show_city_box">
      <index-list>

        <cell class="all_hospital" title="所有医院"></cell>

        <div v-for="(group_data, key) in hospitals_list" :key="key" class="group_data">
          <index-section :index="key">
            <div v-for="arr_data in group_data">
              <cell :title="arr_data.name" ><div class="cell_val" @click="data_fn(arr_data.name, arr_data.code)" ></div></cell>
            </div>
          </index-section>
        </div>

      </index-list>
    </div>


  </div>
</template>

<style lang="less">

  @import "../../style/variable.less";

  .page_select_hospital{
    .all_hospital{
      color: @placeholder_color;
      .mint-cell-wrapper{
        .fz(24px);
      }
    }
    .weui-search-bar__label{
      border-radius: .6rem;
    }
    .weui-cells.vux-search_show{
      max-height: none;
    }
    .weui-search-bar__form:after{
      border-radius: .6rem;
    }
    .mint-cell{ /* 不加下边线的 */
      &:nth-last-of-type(1){
        .mint-cell-value{
          &:after{
            border-bottom: 1px solid rgba(0,0,0,0);
          }
        }
      }

    }
    .mint-cell-value{ /* 添加下边线 */
      position: relative;
      &:after{
        content:'';
        width: 100%;
        border-bottom: 1px solid @bottom_line_color;
        bottom: 0;
        position: absolute;
      }
    }
    .mint-indexsection-index{
      height: 0.5rem;
      padding: 0;
      background: @no_content_bg_color;
      padding-left: 0.3rem;
      line-height: 0.5rem;
      .fz(28px);
      font-weight: bold;

    }

    .main_box{
      position: fixed;
      width: 100%;
      height: 100%;
      top: 0;
      background: #fff;
      .item_box{
        width: auto !important;
      }
      .item_box.hot, .item_box.auto{
        overflow: hidden;
        li{
          float: left;
          background: #ccc;
          margin-right: 10px;
        }
      }
    }

    .mint-cell-wrapper{
      position: relative;

      .mint-cell-value{
        position: absolute;
        width: 100%;
        height: 100%;

      }
      .cell_val{
        position: absolute;
        width: 100%;
        height: 100%;
        right: 0;
        z-index: 9;
      }

    }
  }

</style>
<script>

  import { Search } from 'vux'
  import { IndexList, IndexSection, Cell } from 'mint-ui';
  import api_login_reg from '../../api/login_reg.js'

  export default {
    components: {
      Search,
      IndexList, IndexSection, Cell
    },
    methods: {
      data_fn(name,code){
        console.log('click item',name,code);
        // this.doctor.hospital={'txt':txt,'id':id};
        // this.$store.commit('doctor_up',this.doctor);
        // this.$router.push({name:'reg_step2', params: {'txt':txt,'id':id} });

        this.$route.params.prev_page = this.$route.params.prev_page == 'exerciseProcess' ? 'member_list' : this.$route.params.prev_page ;

        let obj=this.$store.state.temp_data;
        if(obj.prev_page && this.$route.params.prev_page==undefined ){
          console.log('member_list')
          this.$route.params.prev_page = obj.prev_page.data;
        }else{
          obj.prev_page={
            data:this.$route.params.prev_page
          };
          this.$store.commit('temp_data_up',obj)
        }
        
        this.$router.replace({name:this.$route.params.prev_page, params: {'hospital':{'name':name, 'code':code}} });

      },
      setFocus () {
        this.$refs.search.setFocus()
      },
      resultClick (item) {
        console.log('点击结果',item);
        // this.$router.push({name:'reg_step2', params: {'txt':item.txt,'id':item.id} });
        this.$route.params.prev_page = this.$route.params.prev_page == 'exerciseProcess' ? 'member_list' : this.$route.params.prev_page ;
        this.$router.replace({name:this.$route.params.prev_page, params: {'hospital':{'name':item.name, 'code':item.code}} });
      },
      getResult (val) {
        console.log(1,val)
        api_login_reg.hospitals_list({filter:val}).then(res=>{
          console.log(res, group_data(res));
          // this.hospitals_list=group_data(res);
          this.results = val ? getResult(res) : []
        },err=>{
          console.log(err);
        })

      },
      onSubmit () {
        this.$refs.search.setBlur()
        this.$vux.toast.show({
          type: 'text',
          position: 'top',
          text: 'on submit'
        })
      },
      onFocus () {
        console.log('on focus');
        this.show_city_box=false;
      },
      onCancel () {
        console.log('on cancel');
        this.show_city_box=true;
      }
    },
    created(){
      $_temp.loaded_select_hospital=true;

      var store_arr=[
      'doctor',
      ];
      for(var i in store_arr){
        this[store_arr[i]]=this.$store.state[store_arr[i]];
      };

      api_login_reg.hospitals_list({filter:''}).then(res=>{
        console.log(res, group_data(res));
        this.hospitals_list=group_data(res);
      },err=>{
        console.log(err);
      })
      // console.log(group_data(data.data));

    },
    data () {
      return {
        results: [],
        hospitals_list: [], // 医院列表
        show_city_box:true, // 是否显示
        value: '',
        doctor: '' // 治疗师表
      }
    }
  }
  function getResult (val) {
    let rs = []
    for (let i = 0; i < val.length; i++) {
      rs.push({
        title: `${val[i].name}`,
        name: `${val[i].name}`, /*医院名*/
        group: `${val[i].group}`, /*医院分组名*/
        code: `${val[i].code}` /*医院code*/
      })
    }
    return rs
  }

  function group_data(data){
    // 根据数组中的对象的 group 属性分组
    var data2={};
    var str='ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    var i=0;
    for(i;i<str.length;i++){
      // console.log(str[i], data[i] );
      var j=0;
      data2[str[i]]=[]; // 创建分数据的盒子

      for(j;j<data.length;j++){ // 数据分组
        if(data[j] && data[j].group == str[i] ){
          // console.log(1, data[j], str[i]);
          data2[str[i]].push(data[j]);
        }
      }

      if(!data2[str[i]].length){ // 删除没有数据有盒子
          delete data2[str[i]];
      }
    }
    // console.log(data2)
    return data2;
  }

</script>

