<template>
    
    <div class="page-personal-info" v-wechat-title="$route.meta.title">
        <div class="top">
            <div class="item-more" @click="changeUserImg">
                <span class="text">头像</span>
                <div class="img-wrap">
                    <img :src="userHeadImg">
                </div>
                <i class="next_ico"></i>
            </div>
        </div>
        <div class="input-list">
            <div class="item_box">
                <div class="item_more" @click="openMessageBox">
                <span class="text">姓名</span> <i class="next_ico ico_more"></i>
                <div class="text2">{{userName}}</div>
                </div>
            </div>
            <div class="item_box">
                <div class="item_more" @click="chooseSex">
                    <span class="text">性别</span> <i class="next_ico ico_more"></i>
                    <div class="text2">{{userSex == 1 ? '男':'女'}}</div> 
                </div>
                <actionsheet v-model="showSex" :menus="menusSex" show-cancel @on-click-menu="changeSex"></actionsheet>
            
            </div>
            <div class="item_box">
                <datetime class="w_item_more" title="年龄" :min-year="1900"  v-model="time1" @on-change="timeChange">
                    <div class="item_more"><span class="text">生日</span> <div class="text2">{{userAge}}</div> <i class="next_ico ico_more"></i> </div>
                </datetime>
            </div>
            <div v-if="showHospital">
                <div class="item_box" @click="selectHospital">
                    <div class="item_more">
                    <span class="text">医院</span> <i class="next_ico ico_more"></i>
                    <div class="text2">{{userHospital | code2name(hospitalList)}}</div>
                    </div>
                </div>
                <div class="item_box" @click="selctJob">
                    <div class="item_more">
                    <span class="text">职称</span> <i class="next_ico ico_more"></i>
                    <div class="text2">{{professiona | jobFormat}}</div>
                    </div>
                    <div v-transfer-dom>
                        <popup v-model="popupVisible">
                            <popup-header
                            left-text="取消"
                            right-text="确定"
                            title=" "
                            :show-bottom-border="false"
                            @on-click-left="popupVisible = false"
                            @on-click-right="chooseJob1"></popup-header>
                            <group gutter="0">
                                <radio :options="jobList1" @on-change="change"></radio>
                            </group>
                        </popup>
                    </div>

                    
                </div>
                <div class="item_box">  
                    <div class="item_more">
                        <span class="text">简介</span> <router-link to="/app/introduction"><i class="next_ico ico_more"></i></router-link>
                        <div class="text2 introduction-text">{{description}}</div>
                    </div>
                </div>
            </div>
        </div>
        <!-- <router-link to="/app/setting"> -->
            <div class="button_box" @click="save">
                <mt-button class="button">保存</mt-button>
            </div>
        <!-- </router-link> -->
        <router-view></router-view>
    </div>
</template>
<script>
    import {Group, Actionsheet,Datetime,TransferDom,PopupHeader, Popup, Radio } from 'vux'
    import { Button, MessageBox} from 'mint-ui';
    import api_main from 'api/main.js'
    import {code2name} from '../../../assets/js/common.js'
     var moment = require('moment'); 
export default {
    data() {
        return {
            showImage: false,
            userName: '',
            showSex: false,
            menusSex: {
                '1': '男',
                '2': '女'
            },
            userHeadImg: '',
            userSex: '',
            time1: '',
            userAge: '',
            maxYear: '',
            showHospital: '',
            userHospital: '',
            hospitalList: '',
            popupVisible: false,
            jobList1: [],
            professional:'',
            transProfessiona: '',
            description: '',
            uploadImg: false, // 是否上传图片
            serverId: ''
        }
    },
    components: {
        'mtButton': Button,
        Group,
        Actionsheet,
        Datetime,
        PopupHeader,
        Popup,
        Radio
    },
    directives: {
        TransferDom
    },    
    created(){
        this.init();
        this.resetHospital();
        // 获取微信授权接口
        var href = window.location.href || document.location.href;
        var url = href.split('#')[0];

        var wxObject = {};
        api_main.get_wx_config(url).then( res => {
            wxObject = Object.assign(res,{
                debug: false, //开启微信调试模式
                jsApiList: [    //需要使用的js接口列表
                    'chooseImage',
                    'uploadImage',
                ] 
            });

            wx.config(wxObject);

        }).catch(error => {
            console.log(error);
        });
        
        this.maxYear = moment().format("YYYY");
    },
    mounted(){
    },
    methods: {
        init() {
            if( this.$store.state.user_type == 0) { //患者
                this.showHospital = false;
                api_main.get_user_detail().then(res => {


                    if(res.responseEntity && res.responseEntity.body){
                      this.userHeadImg = 'data:image/jpg;base64,'+res.responseEntity.body;  
                    }else{
                      this.userHeadImg = '';
                    }

                    // this.userHeadImg = 'data:image/jpg;base64,'+res.responseEntity.body;
                    this.userName = res.realName;
                    this.userSex = res.sex;
                    this.userAge = moment(res.birthDate).format("YYYY-MM-DD");
                }, res => {
                    console.log(res);
                });

            }else if( this.$store.state.user_type == 1){    //医生
                this.hospitalList = this.$store.state.dict_hospitals;
                this.showHospital = true;

                if(this.$store.state.doctor.responseEntity && this.$store.state.doctor.responseEntity.body){
                  this.userHeadImg = 'data:image/jpg;base64,'+this.$store.state.doctor.responseEntity.body;
                }else{
                  this.userHeadImg = '';
                }

                // this.userHeadImg = 'data:image/jpg;base64,'+this.$store.state.doctor.responseEntity.body;
                this.userName = this.$store.state.doctor.realName;
                this.userSex = this.$store.state.doctor.sex;
                this.userAge = moment(this.$store.state.doctor.birthDate).format("YYYY-MM-DD");
                this.userHospital = this.$store.state.doctor.hospital;
                this.professiona = this.$store.state.doctor.professiona;
                this.description = this.$store.state.doctor.description;
                this.code = this.$store.state.doctor.hospital;
                api_main.get_doctor_professiona().then(res => {
                    var jobs1 = [];
                    for(let i = 0, n = res.length; i < n; i++){
                        var obj1 = {};
                        obj1.key = res[i].code;
                        obj1.value = res[i].name;
                        jobs1.push(obj1);
                    }
                    this.jobList1 = jobs1;
                }, res => {
                    console.log("res错误");
                    console.log(res);
                });
            }
            //用户其他信息获取

        },
        resetHospital() {
            if(this.$route.params.hospital != undefined) {
                this.userHospital = this.$route.params.hospital.code;
            }
        },
        changeUserImg() {
            this.showImage = true;
            this.uploadImg = true; 
            var _this = this;

            // 微信选择图片接口
            wx.chooseImage({
                count: 1,
                sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                success: function(res){
                    // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                    _this.userHeadImg = res.localIds[0];
                    _this.uploadImg = true;
                }
            });
        },
        openMessageBox() {
            
            MessageBox.prompt(' ', '编辑昵称',{
                showInput: true,
                inputPlaceholder: '编辑昵称',
                inputValue: this.userName,
            }).then(({value,action}) => {
                if (action == 'confirm') {
                    this.userName = value;
                }
            }).catch(err => {
                if (err == 'cancel') {
                }
            });
            
        },
        chooseSex(){
            this.showSex = true;
        },
        changeSex(key,value){
            this.userSex = key;
        },
        timeChange(value) {
            this.userAge = value;
        },
        selectHospital(value){
            this.$router.push({name:'select_hospital',params:{prev_page:'personalInfo'}});
        },
        selctJob(){
            this.popupVisible = true;
        },
        canclePopup(){
            this.popupVisible = false;
        },
        confirmPopup(){
            this.popupVisible = false;
        },
        chooseJob1(value) {
            this.professiona = this.transProfessiona;
            this.popupVisible = false
        },
        change(value, label) {
            this.transProfessiona = value;
        },
        save() {
            var dataObj = {
                headPortrait: this.userHeadImg,
                realName: this.userName,
                sex: this.userSex,
                birthDate: Date.parse(new Date(this.userAge))
            }

            if( this.$store.state.user_type != 0) {
                dataObj = Object.assign(dataObj,{
                    hospital: this.userHospital,
                    professiona: this.professiona,
                    description: this.description,
                });
            }

            if(this.uploadImg){
                var _this = this;
                // 微信上传图片接口
                wx.uploadImage({
                    localId: this.userHeadImg, //需要上传的图片本地的id，由chooseImage接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function(res){
                        _this.serverId = res.serverId;
                        dataObj.headPortrait = _this.serverId;
                        _this.sendData(_this, dataObj);
                    }
                });
            }else{
                dataObj = Object.assign(dataObj,{
                  headPortrait: ''
                });
                this.sendData(this, dataObj);
                
            }
            
        },
        sendData(_this, dataObj) {
            api_main.update_user_detail(dataObj, _this.userHospital).then(res => {
                console.log(res);
                if( _this.$store.state.user_type == 0) {
                    _this.$store.commit("user_detail_up",dataObj);
                }else {
                    _this.$store.commit("doctor_detail_up",dataObj);
                }
                api_main.get_user_detail().then( res => { // 用户查询积分明细
                    console.log("用户查询积分明细", res );
                    _this.user_detail=res;
                    _this.$store.commit('user_detail_up',res);
                    if(_this.$store.state.routerRecord == "setting") {
                        _this.$router.push('/app/setting');
                    }else if(_this.$store.state.routerRecord == "mine") {
                        _this.$router.push('/app/mine');
                    }
                    },res=>{
                    console.log('res', res)
                })
            }, res => {});
        }
    },
    filters: {
        code2name: function(val, obj){
            return code2name(val,obj)
        },
        dateFormate: function(value) {
            return Math.floor((Date.parse(new Date())-Date.parse(new Date(value)))/(365*24*60*60*1000));
        },
        jobFormat: function(value) {
            var str = '';
            if(value == 1) {
                str = '助理医师';
            }else if(value == 2) {
                str = '住院医师';
            }else if(value == 3) {
                str = '主治医师';
            }else if(value == 4) {
                str = '副主任医师';
            }else if(value == 5) {
                str = '主任医师';
            }else if(value == 6) {
                str = '治疗士';
            }else if(value == 7) {
                str = '治疗师';
            }else if(value == 8) {
                str = '主管治疗师';
            }else if(value == 9) {
                str = '副主任治疗师';
            }else if(value == 10) {
                str = '主任治疗师';
            }
            return str;
        }
    }
}
</script>
<style lang="less" scoped>
@import '~style/variable.less';

// 弹窗
.mint-msgbox-message{
    p{
        font-size: 14px;
        &:last-child{
            font-size: 12px;
            color: #a7b5cb;
        }
    }
}

.vux-popup-dialog {
    z-index: 1001;
}
.vux-popup-mask.vux-popup-show {
    z-index: 1000 !important;
}
.page-personal-info {
    .top {
        margin-top: .2rem;
        background-color: #fff;
        .item-more {
            height: 1.6rem;
            line-height: 1.6rem;
            padding: 0 .3rem;
            .img-wrap {
                display: inline-block;
                width: 1rem;
                height: 1rem;
                margin-top: .3rem;
                margin-right: .5rem;
                border-radius:50%;
                overflow: hidden;
                float: right;
            }
            img{
                width: 100%;
                height: 100%;
            }
            .next_ico{
                position: absolute;
                right:0.3rem;
                margin-top: 0.25rem;
                position: absolute;
                display: inline-block;
                width: 0.4rem;
                height: 0.4rem;
                background-image: url(../../../assets/img/icon_more@2x_20.png);
                background-repeat: no-repeat;
                background-position: center center;
                background-size: contain;
            }
            .next_ico {
                margin-top: .6rem;
            }
        }
    }
    .input-list {
        margin-top: .2rem;
        .item_box {
            background-color: #fff;
            padding: 0 .3rem;
            &:last-child {
                .item_more {
                    border-bottom: none;
                }
            }
            .item_more {
                padding: 0;
                border-bottom: 1px solid #dfe8ff;
                
                .next_ico {
                    right: 0;
                }
                .text2{
                    display: inline-block;
                    float: right;
                    padding-right: 0.5rem;
                    color: @input_color;
                }
            }
            .w_item_more {
                display: block;
                padding: 0;
            }
        }
    }
    .button_box {
        margin: 0.3rem;
        .button {
            width: 100% !important;
            .base_button();
        }
    }
    .introduction-text {
        text-align: right;
        width: 4.46rem;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    // 职称
    .mint-popup {
        width: 100%;
        text-align: center;
        .dp-header {
            width: 100%;
        }
    }
    
}
</style>
