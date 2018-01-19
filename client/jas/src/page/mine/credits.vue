<template>
  <div class="page-credits" v-wechat-title="$route.meta.title">
    <div class="top-banner">
        <router-link to="./discount"><p class="coupon">优惠券</p></router-link>
        
        <div class="main">
            <span class="num">{{scoreValue || 0}}</span>
            <div class="right-wrap">
              <div class="icon-top" @click="openCreditsRules()"></div>
              <p class="aText">/个</p>
            </div>
            <p class="text">积分余额</p>
        </div>
    </div>
    <div class="navbar-main">
        <tab :animate="false" :line-width="1" custom-bar-width="60px" active-color="#4576f0" bar-active-color="#4576f0">
            <tab-item selected @on-item-click="changeTab">积分明细</tab-item>
            <tab-item @on-item-click="changeTab">兑换记录</tab-item>
        </tab>
    </div>
    <div class="creditsLog" v-show="showTab">
        <div class="list" v-for=" item in scoreList" :key="item.id">
            <div class="icom-img"><img :src="item.imgUrl"></div>
            <div class="right">
                <div class="content">
                    <p class="title">{{item.type}}</p>
                    <p class="time">{{item.createTime}}</p>
                </div>
                <div class="score">{{item.changeValue}}</div>
            </div>
        </div>
    </div>
    <div class="changeLog" v-show="!showTab">
        <div class="list" v-for=" item in exchangeList" :key="item.id">
            <div class="icom-img"><img src="../../assets/img/icon-exchange@2x.png"></div>
            <div class="right">
                <div class="content">
                    <p class="title">{{item.couponMoneys}}</p>
                    <p class="time">{{item.createTime}}</p>
                </div>
                <div class="score">{{item.redeemScores}}</div>
            </div>
        </div>
        
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
  import { Tab, TabItem, Sticky, Divider, XButton, Swiper, SwiperItem } from 'vux'
  import { MessageBox } from 'mint-ui';
  import api_main from 'api/main.js'
  import icon_rigester from 'assets/img/icon-exchange@2x.png'
  import icon_exchange from 'assets/img/icon-exchange@2x.png'
  import icon_sport from 'assets/img/icon-sport@2x.png'
  import icon_share from 'assets/img/icon-share@2x.png'
  

  export default {
    data (){
      return {
          showTab: true,
		  scoreValue: '',
		  scoreList: '',
		  exchangeList: ''
      }
    },
    components: {
        Tab,
        TabItem,
        Sticky,
        Divider,
        XButton,
        Swiper,
        SwiperItem
    },
    created(){
      this.init();
    },
    methods:{
      init() {
        // 积分总数
        api_main.get_user_detail().then( res => {
          this.scoreValue = res.scoreValue;
        });
		this.get_score_list();
        this.get_exchange_list();
	  },
	  // 积分明细
	  get_score_list() {
		  
        api_main.get_scoredetail().then( res => {
			for(var i = 0; i < res.length; i++ ) {
				// 格式化日期
				var dateStr= new Date(res[i].createTime);
				var year = dateStr.getFullYear();
				var month = dateStr.getMonth();
				var day = dateStr.getDate() < 10 ? '0' + dateStr.getDate() : dateStr.getDate();
				res[i].createTime = year + '.' + month + '.' + day;
			}
            this.scoreList = res;
            this.scoreList.forEach(item => {
                this.$set(item,"imgUrl",require('assets/img/'+item.ico+'@2x.png'))
            });
        }, res=>{
		});
	  },
	  // 积分兑换记录
	  get_exchange_list() {
		  api_main.get_change_list().then(res => {
			for(var i = 0; i < res.length; i++ ) {
				// 格式化日期
				var dateStr= new Date(res[i].createTime);
				var year = dateStr.getFullYear();
				var month = dateStr.getMonth();
				var day = dateStr.getDate() < 10 ? '0' + dateStr.getDate() : dateStr.getDate();
				res[i].createTime = year + '.' + month + '.' + day;
			}
			this.exchangeList = res;
		}, res => {
		});
	  },
	//tab切换   
      changeTab(index) {
		  if(index) {
              this.showTab = false;
              this.$route.meta.title = '兑换记录';
		  }else {
              this.showTab = true;
              this.$route.meta.title = '积分';
		  }
      },
      openCreditsRules() {
          var message = '<div class="content"><span>1. </span><span>JAS会在每年6月1日对您账户上一年未使用积分进行清零作废</span></div>'+
          '<div class="content"><span>2. </span><span>说话的方式简单点不需要从前二该配合你演出的我演视而不见</span></div>'+
          '<div class="content"><span>3. </span><span>说话的方式简单点递进的情绪请省略</span></div>'+
          '<p class="warn">*说话的方式简单点,不需要从前二该配合你演出的我演视而不见不对重新来.少年被风催大容颜。</p>';
          
          MessageBox({
              title: '积分规则',
              message: message,
              confirmButtonText: '知道了',
              closeOnClickModal: false
          });
      }
    }
  }

</script>

<style lang="less">
@import '~style/variable.less';

.mint-msgbox-message{
    .content{
        display: flex;
        font-size: 14px;
        padding: 0 .35rem 0 .3rem;
        text-align: left;
        line-height: 25px;
        margin-bottom: .3rem;
        span{
            &:first-child{
                margin-right: .2rem;
            }
        }
    }
    .warn{
        font-size: 12px;
        padding: 0 .35rem;
        line-height: 20px;
        color: #a7b5cb;
    }
}

.page-credits{
    .top-banner {
        text-align: right;
        color: #fff;
        background: url(../../assets/img/credit_banner_img.jpg);
        .coupon {
            display: inline-block;
            width: 1.2rem;
            height: .5rem;
            margin: .25rem .2rem .35rem 0;
            line-height: .5rem;
            text-align: center;
            .fz(24px);
            color: #fff;
            border: 1px solid #fff;
            border-radius: .25rem;
        }
        .main {
            position: relative;
            text-align: center;
            .num {
                line-height: 1.3rem;
                .fz(172px);
            }
            .right-wrap {
                position: absolute;
                display: inline-block;
                    height: 100%;
                    margin-left: .15rem;
                .icon-top {
                    position: relative;
                    width: .26rem;
                    height: .26rem;
                    background: url(../../assets/img/icon-wen@2x.png) no-repeat center center /cover;
                }
                .aText {
                    margin-top: .7rem;
                    .fz(28px);
                }
            }
            
            .text {
                margin: .4rem auto .94rem 0;
                .fz(28px);
            }
        }
        
    }
    .creditsLog,
    .changeLog {
        padding: 0 .3rem;
        background-color: #fff;
        .list {
            display: flex;
            justify-content: flex-start;
            align-items: center;
            height: 1.6rem;
            .icom-img {
                width: .44rem;
                height: .44rem;
                margin-right: .4rem;
                img {
                    width: 100%;
                    height: 100%;
                }
            }
            .right {
                display: flex;
                align-items: center;
                justify-content: space-between;
                flex: 1;
                height: 100%;
                border-bottom: 1px solid @bottom_line_color;
                .content {
                    height: 100%;
                    .title {
                        margin: .3rem 0 .2rem 0;
                        .fz(30px);
                        color: @base_text_color;
                    }
                    .time {
                        .fz(24);
                        color: #a7b5cb;
                    }
                }
                .score {
                    .fz(40);
                    color: #4677f0;
                }
            }
            
        }
    }

    //   样式重置
    .vux-tab {
        height: 1rem;
        border-bottom: .01rem solid #dfe8ff;
        .fz(28);
        .vux-tab-item {
            height: 1rem;
            line-height: 1rem;
            background: none;
        }
        .vux-tab-selected {
            border-bottom: none !important;
            &::after {
                position: absolute;
                bottom: 0;
                content: '';
                height: .03rem;
                width: .3rem;
                margin-left: -.8rem;
                background-color: #4576f0;
            }
        }
    }
    
}
</style>
