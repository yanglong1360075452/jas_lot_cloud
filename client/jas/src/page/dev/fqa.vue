<template>
  <div class="page_fqa no_content_bg_color top_fixed" v-wechat-title="$route.meta.title">
    <div class="fqa_box">
      <dl>
        <dt>1.该设备哪些功能t？</dt>
        <dd>计步，计睡眠，心率监测、闹钟、久坐提醒功能不需要一直开启蓝牙，锻炼时只需要戴手环，锻炼完成后将手环和手机连接同步查看数据即可；故需要打开手机蓝牙，保持手环与手机的链接。</dd>
      </dl>
      <dl>
        <dt>2.佩戴该设备蓝牙需要一直开启吗？</dt>
        <dd>计步，计睡眠，心率监测、闹钟、久坐提醒功能不需要一直开启蓝牙，锻炼时只需要戴手环，锻炼完成后将手环和手机连接同步查看数据即可；故需要打开手机蓝牙，保持手环与手机的链接。</dd>
      </dl>
      <dl>
        <dt>3.连接设备时提示搜索不到该设备怎么办？</dt>
        <dd>计步，计睡眠，心率监测、闹钟、久坐提醒功能不需要一直开启蓝牙，锻炼时只需要戴手环，锻炼完成后将手环和手机连接同步查看数据即可；故需要打开手机蓝牙，保持手环与手机的链接。</dd>
      </dl>
      <dl>
        <dt>4.设备充满电大概需要多久？</dt>
        <dd>计步，计睡眠，心率监测、闹钟、久坐提醒功能不需要一直开启蓝牙，锻炼时只需要戴手环，锻炼完成后将手环和手机连接同步查看数据即可；故需要打开手机蓝牙，保持手环与手机的链接。</dd>
      </dl>
    </div>
  </div>
</template>

<script>
  import bottom_tab_box from '../../components/bottom_tab_box.vue'
  import { Tabbar, TabbarItem, Search, Cell } from 'vux';
  import api_login_reg from '../../api/login_reg.js'
  import api_main from '../../api/main.js'
  export default {
    data (){
      return {}
    },
    created(){},
    components:{},
    filters:{},
    methods:{}
  }
</script>

<style lang="less">
  @import "../../style/variable.less";
  @import "../../style/common.less";
  body .page_fqa{
    background: #fff;
    .fqa_box{
      padding: 0.3rem;
      padding-top: 0;
      dt{
        .fz(30px);
        padding-top: 0.3rem;
        padding-bottom: 0.3rem;
      }
      dd{
        border-radius:0.1rem;
        padding: 0.3rem;
        margin-bottom: 0.2rem;
        background: #f8fbff;
        color: #a7b5cb;
        position: relative;
        &:after{
          position: absolute;
          content:'';
          height: 0;
          border: 0.2rem solid #f8fbff;
          border-top: none;
          border-right-color: transparent;
          border-left-color: transparent;
          top: -.2rem;
          left: .5rem;
        }
      }
    }
  }
</style>
