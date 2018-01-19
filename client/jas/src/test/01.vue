<template>
  <div class="main_box">
    <!-- 定位城市 -->
    <div v-if="false" class="item_box auto">
      <div class="title">定位城市</div>
      <div class="content_box">
        <ul>
          <li>上海</li>
        </ul>
      </div>
    </div>
    <div class="item_box">
      <div class="city_box">
        <index-list>

          <div v-if="false" class="title">热门城市</div>
          <index-section v-if="false" class="city_box_hot" index="热">

            <cell title="安阳市"></cell>
            <cell title="阿拉善盟"></cell>
            <cell title="阿坝州"></cell>
            <cell title="安顺市"></cell>
            <cell title="阿里地区"></cell>

          </index-section>

          <div class="title">所有城市</div>

          <index-section index="A">
            <cell title="鞍山市"><div class="cell_val" @click="data_fn('鞍山市', 'data_id_1')"></div></cell>
            <cell title="安庆市"><div class="cell_val" @click="data_fn('安庆市', 'data_id_2')"></div></cell>
            <cell title="安阳市"><div class="cell_val" @click="data_fn('安阳市', 'data_id_3')"></div></cell>
            <cell title="阿拉善盟"></cell>
            <cell title="阿坝州"></cell>
            <cell title="安顺市"></cell>
            <cell title="阿里地区"></cell>
            <cell title="安康市"></cell>
            <cell title="阿克苏地区"></cell>
            <cell title="阿勒泰地区"></cell>
            <cell title="阿拉尔市"></cell>
          </index-section>
          <index-section index="B">
            <cell title="北京市"></cell>
            <cell title="保定市"></cell>
            <cell title="包头市"></cell>
            <cell title="本溪市"></cell>
            <cell title="蚌埠市"></cell>
            <cell title="北海市"></cell>
            <cell title="滨州市"></cell>
            <cell title="宝鸡市"></cell>
            <cell title="亳州市"></cell>
            <cell title="巴彦淖尔市"></cell>
            <cell title="白山市"></cell>
            <cell title="白城市"></cell>
            <cell title="百色市"></cell>
            <cell title="白沙黎族自治县"></cell>
            <cell title="巴中市"></cell>
            <cell title="毕节地区"></cell>
            <cell title="保山市"></cell>
            <cell title="白银市"></cell>
            <cell title="巴音郭楞州"></cell>
            <cell title="博尔塔拉州"></cell>
          </index-section>
        </index-list>
      </div>
    </div>

  </div>
</template>
<style lang="less" scoped>
  .mint-cell-wrapper{
    position: relative;

    .mint-cell-value{
      position: absolute;
      width: 100%;
      height: 100%;

    }
    .cell_val{

      position: absolute;
      background: rgba(0,0,0,.1);
      width: 100%;
      height: 100%;
      right: 0;
      z-index: 9;
    }

  }
</style>
<style lang="less" scoped>
  .main_box{
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    background: #fff;
    .item_box.hot, .item_box.auto{
      overflow: hidden;
      li{
        float: left;
        background: #ccc;
        margin-right: 10px;
      }
    }
  }
</style>

<script>
import { IndexList, IndexSection, Cell } from 'mint-ui';

export default {
  name: 'components01',
  data (){
    return {
    }
  },
  created(){},
  components: {
    IndexList, IndexSection, Cell
  },
  methods:{
    data_fn(txt,id){
      console.log(txt,id)
      this.$store.commit('medicalArchival_up',{department:{'txt':txt,'id':id}});
      console.log(this.$store.state.medicalArchival);
      this.$router.push({path:"/app/test/sum01"});
    }
  },
  computed: {}
}



</script>
