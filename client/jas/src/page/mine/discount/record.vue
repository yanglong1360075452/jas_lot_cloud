<template>
    <div class="record" v-wechat-title="$route.meta.title">
        <div class="map" v-for="item in obj">
            <img src="../../../assets/img/icon-coupon-left.png" class="map-img">
            <div class="map-item">
                <div class="map-left">
                    <img src="../../../assets/img/icon-coupon-history@2x.png">
                </div>
                <div class="map-center">
                    <p class="map-money"><span>优惠券</span><span>￥{{item.couponMoney}}</span></p>
                    <p class="map-time">有效期至{{item.validity | normalizeDate}}</p>
                </div>
                <div class="map-right">
                    <img src="../../../assets/img/icon-overdue@2x.png" class="map-overdue">
                </div>
            </div>
            <img src="../../../assets/img/icon-coupon-right.png" class="map-img">
        </div>

    </div>
</template>

<script type='text/ecmascript-6'>
import api from 'api/main.js';
import {formatDate} from 'assets/js/common.js';

export default {
    data() {
        return {
            obj: null, // 获取的数据对象
        }
    },
    filters: {
        // 格式化时间
        normalizeDate(date, fmt) {
            var fmt  = 'yyyy.MM.dd',
                date = new Date();

            return formatDate(date,fmt);
        }
    },
    created() {
        api.get_history_coupon().then( res => {
            console.log("查看历史优惠券", res);
            this.obj = res;
        });
    },
}
</script>

<style lang='less'>

.record{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #f6f6f6;
    .map-center{
        text-align: left;
        .map-money{
            span{
                font-size: 17px;
                &:first-child{
                    font-size: 15px;
                    margin-right: .2rem;
                }
            }
        }
        .map-time{
            margin-top: .4rem;
        }
    }
    .map-overdue{
        height: .96rem;
    }
}
 
</style>
