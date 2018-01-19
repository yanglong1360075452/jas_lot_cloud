<template>
    <div class="setting-phone" v-wechat-title="$route.meta.title">
        <div class="all-item">
            <div class="item phone">
                <span ref="phoneNumber">原手机号</span>
                <input type="text" placeholder="请输入手机号码" v-model="userPhone" class="pre-number">
                <div class="button_captcha" @click.prevent="_getCaptcha">
                    {{captcha_text}}<countdown :start="time_show" v-model="time" @on-finish="_timeFinish" v-show="time_show"></countdown><span v-show="time_show">s</span>
                </div>
            </div>
            <div class="item captcha">
                <span>验证码</span>
                <input type="text" placeholder="请输入6位验证码" v-model="userCaptcha">
            </div>
        </div>
        <div class="button_box">
            <mt-button class="button" @click='_nextStep' v-if="button_show">下一步</mt-button>
            <mt-button class="button" @click='_finish' v-if="!button_show">完成</mt-button>
        </div>
        <toast v-model="toast_show" type="text">{{toast_text}}</toast>
    </div>
</template>

<script type='text/ecmascript-6'>
import {Button} from 'mint-ui';
import {Toast, Countdown} from 'vux'
import api from 'api/main.js';

export default {
    data (){
        return{
            userCaptcha: '', // 8888
            userPhone: '', // 手机号
            toast_text: '', // 弹出的信息
            toast_show: false, // 是否显示弹窗
            time_show: false, // 是否显示获取验证码后倒计时
            time: 60, // 间隔获取验证码时间(s)
            captcha_text: '获取验证码', 
            button_show: true // “下一步按钮”是否显示
        }
    },
    components: {
        'mtButton': Button,
        'toast': Toast,
        'countdown': Countdown
    },
    methods: {
        // 手机格式验证
        _phoneFormat () {  
            if(!this.userPhone){
                this.toast_text = '请输入手机号';
                this.toast_show = true;
                return
            }

            if(!(/^1\d{10}$/.test(this.userPhone))){
                this.toast_text = '手机号格式错误';
                this.toast_show = true;
                return
            }
        },
        // 获取验证码
        _getCaptcha () {
        
            this._phoneFormat();

            if(this.toast_show){
                return
            }

            if(this.time_show){
                console.log('正在计时...');
                return;
            }else{
                this.captcha_text = '重新获取';
            }

            this.time_show = true;
            this.time = 60;

            // 此处模拟已获取验证码
            this.userCaptcha = '8888';

        },
        // 点击"下一步"按钮
        _nextStep () {
            this._phoneFormat();

            if(this.toast_show){
                return
            }

            // 判断验证码的正确性
            api.vaild_captcha(this.userCaptcha).then( res => {
                if(res.result === "0"){
                    this.button_show = false;
                    this.$refs.phoneNumber.innerHTML = '新手机号';
                    this.captcha_text = '发送验证码';
                    this._timeFinish();
                    this.userPhone = '';
                    this.userCaptcha = '';
                }
            });
        },
        // 点击"完成"按钮
        _finish () {

            this._phoneFormat();

            if(this.toast_show){
                return
            }

            api.update_phone(this.userPhone, this.userCaptcha).then( res => {
                if(res.result === "0"){
                    // 跳转mine页面
                    this.$router.push({name:'mine'})
                }else{
                    // 错误则弹出错误信息
                    this.toast_show = true;
                    this.toast_text = res.result;
                }
            });
        },
        _timeFinish () {
            this.time_show = false; 
        }
    }
}
</script>

<style lang='less'>
@import '../../../style/variable.less';

.setting-phone{
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    margin-top: .2rem;
    font-size: 15px;
    .all-item{
        background: #fff;
    }
    .item{
        display: flex;
        height: 1rem;
        line-height: 1rem;
        margin: 0 .3rem;
        span{
            flex: 1;
        }
        input{
            width: 3.1rem;
            border: none;
            vertical-align: inherit;
            font-size: 15px;
            flex: 3;
        }
        .pre-number{
            flex: 2;
        }
        .button_captcha{
            text-align: right;
        }
        a{
            text-decoration: underline;
            float: right;
        }
    }
    .phone{
        border-bottom: 1px solid #dfe8ff;
        .button_captcha{
            float: right;
            text-decoration: underline;
            color: #4576f0;
            span{
                display: inline;
            }
        }
    }
}
 
</style>
