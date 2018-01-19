<template>
  <div class="protocol_page top_fixed" v-wechat-title="$route.meta.title">
        <h1>声明</h1>
        <div class="content_div">
          <h2>一、协议范围</h2>
          <div class="article">
            欢迎访问JAS平台(简称本平台)。为了规范网站管理，依法保障本平台和会员的合法权利，本平台特作出以下声明。本声明之条款和条件适用于所有访问和使用本平台或注册成为会员的个人或单位。
          </div>
        </div>
        <div class="content_div">
          <h2>二、使用规则</h2>
          <div class="article">
            1.  任何访问和使用本平台的个人或单位必须遵守有关法律法规，不得借助本平台的传播、宣传等功能，从事非法活动。

            2.  凡通过本平台发布交易信息、进行交易行为的个人或单位，务必保证所发布信息的真实性和合法性，并如约履行交易合同。如因发布虚假信息、进行虚假交易等行为导致的法律后果，由行为人自行承担，本平台保留依法追偿等相关权利。

            3.  任何单位或个人认为本平台中的内容可能涉嫌侵犯其合法权益，可及时向本平台书面反馈，并提供身份证明、权属证明及详细侵权情况证明，本平台在收到上述文件后，将尽快核实、处理。

            4.  所有与本平台链接的网站及其内容和版权由相应的提供者与所有者负责，本平台不对其内容或形式或性质承担任何直接或间接的商业或法律责任。

            5.  本平台及关联公司保留在中华人民共和国法律允许的范围内自主决定是否拒绝服务、关闭账户、清除或编辑信息以及取消权限等权利。

            6.  无论因何种原因（包括但不限于疏忽、过失），由本平台上的信息或本平台链接的信息，或其他与本平台链接的网站信息所导致的损失或损害（包括直接或间接、轻微或严重的损失或损害，例如收入或利润之损失，电脑系统之损坏或数据丢失等后果），后果均由使用者自行承担。

            7.  本平台内容来源于网络，转载仅为传播信息促进医药行业发展，如果我们的行为侵犯了您的权益，请及时与我们联系，我们将在收到通知后妥善处理该部分内容。

            8.  本平台对任何直接、间接、偶然、特殊及突然的损害不负责任，这些损害可能来自：不正当使用网路服务；在网上购买商品或进行同类型服务；在网上进行产品交易，非法使用网路服务或用户传送的资讯有所变动。

            9.  本平台不对用户所发布资讯的删除或储存失败负责。本平台有判定用户的行为是否符合本平台服务条款的要求和精神的保留权利，如果用户违背了服务条款的规定，本平台有中断对其提供网路服务的权利。

            10. 本平台对本平台上所有由第三方提供的信息、内容和服务，不提供明示或暗示的担保。用户在他们发表的资讯中加入宣传资料或参与广告策划，在本平台的免费服务上展示他们的产品，任何这类促销方法，包括运输货物、付款、服务、商业条件、担保及与信息有关的描述都只是在相应的用户和信息提供者之间发生。本平台对使用上述信息、内容和服务所造成的任何损失不承担责任，包括直接损失和间接损失。

            11. 设备使用人必须负责保证乙方所提供设备的妥善保管。设备租用使用人不得人为损坏，不得遗失，否则照价赔偿。

            12. 设备使用人应当按照医生知道和说明书操作，如非设备质量问题导致的设备损坏，医疗问题，均由设备使用人承担。

            13. 本公司负责对售（租）设备安装、调试及正常使用、培训、指导按照正规的操作程序使用设备。维修、维护、相关费用以合同为准。

            14. 本平台认为，一切网民在进入主页及各层页面以及完成会员注册时已经仔细看过本条款并完全同意。

          </div>
        </div>
      </div>
</template>

<script>
  import { Button } from 'mint-ui';
  import { XInput, Group } from 'vux'
  export default {
    name: 'index',
    data (){
      return {
        name:'李会长',
        phone:'188 1735 2333',
        code:'001206',
        show_content:false,
      }
    },
    created(){
    },
    components:{
      'mtButton':Button,
      Group,
      XInput
    },
    methods:{
      re_code_fn(){
        console.log('re_code')
      },
      protocol_fn(){
        this.show_content=!this.show_content;
        // querySelector
      }
    }
  }

</script>

<style lang="less" scoped>

  @import "./../../style/variable.less";

  .weui-cells.vux-no-group-title{
    background: none;
  }
  .page_reg{
    .protocol_page{
      background: @no_content_bg_color;
      overflow: scroll;
      h1{
        .fz(34px);
        width: 100%;
        text-align: center;
        margin-top: 0.49rem;
        font-weight: bold;
      }
      .content_div{
        padding: 0 0.4rem;
        white-space: pre-line;
        h2{
          margin-top: 0.5rem;
          font-weight: bold;
          .fz(28px);
        }
      }
    }
  }

</style>

<style lang="less" scoped>
</style>
