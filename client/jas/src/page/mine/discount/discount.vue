<template>
    <div class="discount" v-wechat-title="$route.meta.title">
        <div class="button-bar">
            <span class="coupon" @click="_coupon" :class="{active:show}">优惠券</span><span class="exchange" @click="_exchange" :class="{active:!show}">兑换</span>
            <router-link to="./credits" class="check">查看积分</router-link>
        </div>
        <div class="all-map">
            <!-- 查看已兑换的优惠券 -->
            <div class="map" v-for="item in pre_obj" v-show="show" :id="item.couponId">
                <img src="../../../assets/img/icon-coupon-left.png" class="map-img">
                <div class="map-item">
                    <div class="map-left">
                        <img src="../../../assets/img/icon-coupon@2x.png">
                    </div>
                    <div class="map-center">
                        <p class="map-discount">优惠券</p>
                        <p class="map-time">{{item.validity | normalizeDate}}到期</p>
                        <p class="map-id"> 券码 23f96sd7 (限康复城使用)</p>
                    </div>
                    <div class="map-right">
                        <p class="map-integral">￥{{item.couponMoney}}</p>
                    </div>
                </div>
                <img src="../../../assets/img/icon-coupon-right.png" class="map-img">
            </div>

            <!-- 查看可兑换的优惠券 -->
            <div class="map" v-for="item in next_obj" v-if="item.couponCount" v-show="!show">
                <img src="../../../assets/img/icon-coupon-left.png" class="map-img">
                <div class="map-item">
                    <div class="map-left">
                        <img src="../../../assets/img/icon-coupon@2x.png">
                    </div>
                    <div class="map-center">
                        <p class="map-discount">优惠券</p>
                        <p class="map-money">使用{{item.redeemScore}}积分兑换</p>
                    </div>
                    <div class="map-right">
                        <p class="map-integral">￥{{item.couponMoney}}</p>
                        <img class="map-btn" src="../../../assets/img/btn-exchange@2x.png" :data-id="item.id" @click="_submit($event)">
                    </div>
                </div>
                <img src="../../../assets/img/icon-coupon-right.png" class="map-img">
            </div>
        </div>
        <toast v-model="toast_show" type="text">{{toast_text}}</toast>

        <div class="map-link">
            <a href="#/app/discount/record">历史优惠券</a>
            <span>&nbsp;|&nbsp;</span> 
            <a href="javascript:void(0)">逛逛康复城</a>
        </div>
        

        <!-- <keep-alive> -->
            <router-view></router-view>
        <!-- </keep-alive> -->

    </div>
</template>

<script>
import api from 'api/main.js';
import { formatDate } from 'assets/js/common.js';
import { MessageBox } from 'mint-ui';
import {Toast} from 'vux'

export default {
    data () {
        return{
            btn_coupon: true, // 优惠券active
            show: true, // 兑换active
            toast_text: '', // 弹出的信息
            toast_show: false, // 是否显示弹窗
            pre_obj: null, // 存放已经兑换优惠券信息的对象
            next_obj: null, // 存放可兑换优惠券信息的对象
            scoreChange: 2, // 积分操作变化原因必须传2
        }
    },
    components: {
        Toast
    },
    created() {
        this._getCoupon();
        this._getExchange();
    },
    filters: {
        // 格式化时间
        normalizeDate(date){
            var fmt = 'yyyy.MM.dd',
                date = new Date(date);

            return formatDate(date, fmt);
        }
    },
    methods:{
        // 查看已兑换的优惠券
        _getCoupon() {
            api.get_coupon().then( res => {
                console.log("查看已兑换的优惠券", res);
                this.pre_obj = res;
            });
        },
        // 查看可以兑换的优惠券
        _getExchange() {
            api.get_exchange_coupon().then( res => {
                console.log("查看可以兑换的优惠券", res);
                this.next_obj = res;
            });
        },
        // 兑换优惠券
        _setCoupon(scoreChange, couponId) {
            api.set_coupon(scoreChange, couponId).then( res => {

                this.toast_text = res.result;
                this.toast_show = true;

                if(res.result === '兑换成功'){
                    
                    this._getCoupon();
                    this._getExchange();
                }

            });
        },
        _coupon() {
            this.show = true;
        },
        _exchange() { 
            this.show = false;
        },
        _submit(e) {
            var id = e.target.getAttribute('data-id');

            MessageBox.confirm('', {
                title: '',
                message: '是否兑换此优惠券',
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                closeOnClickModal: false
            }).then(action => {
                if (action == 'confirm') {
                    this._setCoupon(2, id);
                }
            })

        }
    }
}
</script>

<style lang='less'>

.discount{
    .button-bar{
        margin: .3rem 0;
        text-align: center;
        line-height: .6rem;
        color: #4576f0;
        .coupon,
        .exchange{
            display: inline-block;
            width: 1.8rem;
            text-align: center;
            border: 1px solid #4576f0;
            font-size: 14px;
            background: #fff;
            color: #4576f0;
        }
        .coupon{
            border-radius: 15px 0 0 15px;
        }
        .exchange{
            border-radius: 0 15px 15px 0;
        }
        .active{
            background: #4576f0;
            color: #fff;
        }
        .check{
            position: absolute;
            right: .3rem;
            color: #4677f0;
        }
    }

    .map{
        display: flex;
        height: 2.1rem;
        margin: .2rem .3rem 0 .3rem;
        .map-img{
            width: .5rem;
        }
        .map-item{
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            align-items: center;
            flex: 1;
            background: #fff;
            .map-left{
                img{
                    height: .5rem;
                }
            }
            .map-center{
                flex: 1;
                margin-left: .6rem;
                .map-discount{
                    margin-bottom: .22rem;
                    font-size: 15px;
                }
                .map-id,
                .map-time,
                .map-money{
                    font-size: 12px;
                    color: #a7b5cb;
                }
            }
            .map-right{
                .map-integral{
                    font-size: 20px;
                    color: #4576f0;
                }
                .map-btn{
                    margin-top: 0.3rem;
                    height: .4rem;
                }
            }
        }
    }
    
    .map-link{
        position: absolute;
        bottom: .5rem;
        left: 50%;
        transform: translate(-50%,0);
        font-size: 12px;
        color: #a7b5cb;
        a{
            color: #a7b5cb;
        }
    }
}
</style>
