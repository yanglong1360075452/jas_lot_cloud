<template>
  <div class="main_box">
    <group :title="'中文日期'">
      <datetime v-model="value5" :placeholder="'请选择'" :min-year=1900 :max-year=2017 format="YYYY-MM-DD" @on-change="change" :title="'出生日期'" year-row="{value}年" month-row="{value}月" day-row="{value}日" hour-row="{value}点" minute-row="{value}分" confirm-text="完成" cancel-text="取消"></datetime>
    </group>
  </div>
</template>

<style lang="less" scoped>
  .main_box{
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    background: #fff;
  }
</style>

<script>
import { Datetime, Group} from 'vux'

export default {
  name: 'components01',
  data (){
    return {
      value5: '1995-05-05',
    }
  },
  created(){},
  components: {
    Datetime,
    Group,
  },
  methods:{
    change (value) {
      console.log('change', value)
      console.log(23344,this.$store.state.user)
      this.$store.commit('user_up',{birth:value});
    },
  },
  computed: {}
}



</script>
