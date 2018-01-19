<template>
  <div class="page_tab_dev no_content_bg_color top_fixed" v-wechat-title="$route.meta.title">

    <!-- <div class="top_msg">
      <div class="top_msg_item"><i class="ico icon_model"></i><div class="txt">使用中的设备</div></div>
    </div> -->
    <div @click="readCode" class="top_msg item_more"> <i class="item_ico icon_model"></i> <span class="text">{{dev.length>0 ? '使用中的设备' : '无设备请用“微信”扫描绑定'}}</span> </div>
    <div class="dev_list_box">
      <div class="dev_list" v-for="(item, index) in dev" :key="index">
        <div class="line line1"><div class="title">{{item.config}}</div><div class="attr jas_btn">{{[,'短租','长租'][item.type]}}</div></div>
        <div class="line line2"><div class="attr_more">设备型号 {{item.model}}</div></div>
        <div class="line line2"><div class="attr_more">序 列 号 {{item.sn}}</div></div>
        <div class="btn jas_btn icon_btn_bg" @click="openConfirm(item, index)">解绑</div>
      </div>
      
    </div>
    <div class="item_more_box">
      <!-- <div class="item_more"> <i class="item_ico icon_guide"></i> <span class="text">设备使用指南</span> <i class="next_ico ico_more"></i> </div> -->
      <!-- <div class="item_more"> <i class="item_ico icon_measure"></i> <span class="text">套具选型和测量方式</span> <i class="next_ico ico_more"></i> </div> -->
      <router-link :to="{name:'dev_help'}" class="item_more" tag="div"> <i class="item_ico icon_guide"></i> <span class="text">设备使用指南</span> <i class="next_ico ico_more"></i> </router-link>
      <router-link :to="{name:'select_met'}" class="item_more" tag="div"> <i class="item_ico icon_measure"></i> <span class="text">套具选型和测量方式</span> <i class="next_ico ico_more"></i> </router-link>
      <router-link :to="{name:'fqa'}" class="item_more" tag="div"> <i class="item_ico icon_question"></i> <span class="text">常见问题</span> <i class="next_ico ico_more"></i> </router-link>
    </div>
    <!-- {{`qrCodeData:${JSON.stringify(qrCodeData)}`}} -->
    <router-view></router-view>
    <toast v-model="toast_show" type="text">{{toast_text}}</toast>
  </div>
</template>

<script>
// http://weixin.qq.com/q/02Ovk-NislbE_10000g07r
// http://weixin.qq.com/q/02VpNFMjslbE_10000g07G

  import bottom_tab_box from '../../components/bottom_tab_box.vue'
  import { Toast, Tabbar, TabbarItem, Search, Cell } from 'vux';
  import api_login_reg from '../../api/login_reg.js'
  import api_main from '../../api/main.js'
  import { MessageBox } from 'mint-ui';

  export default {
    data (){
      return {
        qrCodeData:{}, /*扫码结果*/

        toast_show: false,
        toast_text:'',
        dev:'', /*设备数据列表*/
      }
    },
    created(){
      api_main.get_dev().then(res=>{ /*查看我的设备*/
        this.dev=res;
        console.log(res)
      })


      // 获取微信授权接口
      var href = window.location.href || document.location.href;
      var url = href.split('#')[0];

      var wxObject = {};
      api_main.get_wx_config(url).then( res => {
          wxObject = Object.assign(res,{
              debug: false, //开启微信调试模式
              jsApiList: [    //需要使用的js接口列表
                  'scanQRCode',
              ] 
          });

          wx.config(wxObject);

      }).catch(error => {
          console.log('error',error);
      });

    },
    components:{
      Toast
    },
    filters:{},
    methods:{
      readCode(){
        var vm = this;
        vm.qrCodeData={msg:'点击'};
        console.log('readCode')
        // 微信扫码
        wx.scanQRCode(/*{
          success: function (res) {
            alert(res.resultStr);
            vm.qrCodeData=res;
          }
        }*/);

      },
      openConfirm(item, index) {
        MessageBox.confirm('', {
          title: '',
          message: '是否解除此设备的绑定',
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          closeOnClickModal: false
        }).then(action => {
          if (action == 'confirm') {
            api_main.del_dev(item.id).then(res=>{
              this.toast_show=true;
              this.toast_text='解除成功';
              console.log('解除成功',item);
              this.dev.splice(index,1);
            },res=>{
              this.toast_show=true;
              console.log('解除失败',item);
              this.toast_text='解除失败';
            })
          }
        })
      },
    }
  }
</script>

<style lang="less">
  @import "../../style/variable.less";
  @import "../../style/common.less";

  .page_tab_dev{
    .dev_list_box{
      background: #fff;
      margin-bottom: 0.3rem;
    }
    .dev_list{
      padding-right: 0.3rem;
      padding-left: 0.3rem;
      padding-top: 0.24rem;
      background: #fff;
      position: relative;
      margin: auto;
      .btn{
        position: absolute;
        right: 0.3rem;
        background-size: cover;
        background-repeat: no-repeat;
        height: 0.5rem;
        line-height: 0.5rem;
        .fz(24px);
        border: none;
        bottom: 0.25rem;
      }
      &:after{
        margin-top: 0.24rem;
        content:'';
        bottom: 0;
        display: block;
        height: 1px;
        background: @bottom_line_color;
      }
      &:last-of-type:after{
        display: none;
      }
      .line{
        overflow: hidden;
        line-height: 0.5rem;
        .title{
          float: left;
        }
        .attr{
          float: right;
          height: auto !important;
          line-height: normal;
          padding-top: 0;
          padding-bottom: 0;
        }
        &.line1{
          width: 100%;
          .attr{
            .fz(24px);
            color: @accept_color;
          }
          .title{
            .fz(30px);
          }
        }
        &.line2{
          .fz(24px);
          color: @accept_color;
        }
      }
    }
    .top_msg.item_more{
      border-bottom: 1px solid @bottom_line_color;
    }
    .item_more{
      &>.item_ico{
        margin-right: 0.3rem;
      }
      &>.next_ico ico_more{
        right: 0.3rem;
      }
    }
    .item_more_box .item_more{
      height: 1.2rem;
      line-height: 1.2rem;
      &>.item_ico{
        height: 1.2rem;
        line-height: 1.2rem;
        margin-right: 0.3rem;
      }
    }
  }
</style>
