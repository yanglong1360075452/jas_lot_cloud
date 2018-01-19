<template>
    <div class="compile-photograph" v-wechat-title="$route.meta.title" v-if="boxItem.length!==0">
        <div class="classify">
            <span class="arrow" @click="back"><</span>
            <span class="compile-classify">编辑照片分类（{{boxItem.length}}）</span>
            <button class="btn jas_btn" @click="submit">完成({{boxItem.length}})</button>
        </div>
        <div class="photo">
            <scroller lock-y :scrollbar-x=false class="small-img">
                <div class="box" ref="box" :style="{width:boxWidth}">
                    <div class="box-item" v-for="(item,index) in boxItem" @click="selectBoxItem($event,index)" :class="{boxItemActive:item.type!=='未分类'}">                     
                        <div class="des">{{item.type}}</div>
                        <img :class="{defaultIndex:boxItemIndex===index?true:false}" :src="item.src">
                    </div>
                </div>
            </scroller>
            <div class="big-img">
                <img :src="defaultSrc" ref="bigImg">
            </div>
        </div>
        <datetime v-model="date" @on-change="changeDate" title="日期（非必填）" class="date"></datetime>
        <div class="label">
            <checker default-item-class="jas_btn jas_label">
                <checker-item :value="item" v-for="(item, index) in checkerItem" :key="index" @on-item-click="selectDes(index,item.name)" :class="{checkerActive:index==checkerIndex}">{{item.name}}</checker-item>
            </checker>
        </div>
    </div>
</template>

<script type='text/ecmascript-6'>
import {Checker, CheckerItem, Group, Datetime, Scroller} from 'vux';
import {mapGetters} from 'vuex';
import { formatDate } from 'assets/js/common.js';
import api from 'api/main.js';

export default {
    data() {
        return {
            date: '', // 病例相册时间
            boxWidth: 0,
            boxItemIndex: 0, // boxItem默认显示索引值
            checkerIndex: -1, // checkerItem默认显示索引值('未分类')
            checkerItem: [],  // 病例相册分类
            boxItem: [], //病例相册
            defaultSrc : ""
        }
    },
    components: {
        Checker,
        CheckerItem,
        Group,
        Datetime,
        Scroller
    },
    created() {
        this.boxItem = this.$store.state.medicalArchival.boxItem;

        this.boxItemIndex = this.$route.query.index;
        this.defaultSrc = this.boxItem[this.boxItemIndex].src;

        if(this.boxItem.length !== 0){
            this.checkerIndex = this.boxItem[this.boxItemIndex].indexType;
            if(this.boxItem[this.boxItemIndex].time != ''){
                this.date = formatDate(new Date(this.boxItem[this.boxItemIndex].time),'yyyy-MM-dd');
            }

            // 获取相片分类接口
            api.get_classify().then(res => {
                this.checkerItem = res;
                console.log(this.checkerItem);
            });
        }
    },
    mounted() {
        if(this.boxItem.length !== 0){
            var box = this.$refs.box,
                boxItemWidth = 1.12, // box-item：width
                boxItemLength = box.children.length; // box-item：length

            this.boxWidth = (boxItemLength * boxItemWidth).toFixed(1) + 'rem';
        }
    },
    methods: {
        changeDate(date) {
            console.log('changeDate：', date);
            this.boxItem[this.boxItemIndex]['time'] = date;
        },
        selectBoxItem(e, index) {
            var smallImg = e.target.parentNode.childNodes[2],
                bigImg = this.$refs.bigImg,
                text = e.target.parentNode.childNodes[1];
            bigImg.src = smallImg.src;
            this.boxItemIndex = index;

            if(this.boxItem[index].time != ''){
                this.date = formatDate(new Date(this.boxItem[index].time),'yyyy-MM-dd');
            }else{
                this.date = ''
            }
            
            if(text === '未分类'){
                this.checkerIndex = -1;
            }else{
                this.checkerIndex = this.boxItem[this.boxItemIndex]['indexType'];
            }
        },
        selectDes(index,des){
            var boxItem = this.boxItem[this.boxItemIndex];
            if(index===this.checkerIndex){
                this.checkerIndex = -1;
                boxItem['indexType'] = -1;
                boxItem.type = '未分类';
                return
            }
            
            boxItem.type = des;
            boxItem['indexType'] = index;
            this.checkerIndex = index;
        },
        back() {
            this.$router.back(1);
        },
        submit(){
            console.log(this.boxItem);

            var editClinicalDataDtoList = [];
            var addClinicalDataDtoList = [];
            
            // 编辑 - 添加图片
            for(var i=0; i<this.boxItem.length; i++){
                if(this.boxItem[i].id != undefined){
                    editClinicalDataDtoList.push(this.boxItem[i]);
                }
            }

            this.$store.commit('compile_editClinicalDataDtoList', editClinicalDataDtoList);
            
            if(this.$store.state.medicalArchival.aedclinicalDataDtoList[0] == null){
                this.$store.commit('compile_addClinicalDataDtoList', []);
            }
            if(this.$store.state.medicalArchival.aedclinicalDataDtoList[2] == null){
                this.$store.commit('compile_delClinicalDataDtoList', []);
            }

            console.log(this.$store.state.medicalArchival.aedclinicalDataDtoList);

            this.$store.commit('compile_photograph',this.boxItem);
            this.$router.replace({path: '/app/reg/medicalArchival'});
        }
    }
}
</script>

<style lang='less'>

.compile-photograph{
    position: fixed;
    overflow: scroll;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 99;
    background: #fff;
    .box{
        height: 1.44rem;
        position: relative;
    }
    .box-item {
        // height: 1.44rem;
        display:inline-block;
        margin-left: .3rem;
        float: left;
    }
    .box-item:first-child {
        margin-left: 0;
    }
    .vux-checker-box{
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 .1rem;
        height: 100%;
    }
    .jas_label{
        flex: 1;
        margin-top: 0;
        padding: .1rem .2rem;
        border: none;
        border-radius: .4rem;
        font-size: 14px;
        color: #253e67;
    }
    .checkerActive{
        font-size: 14px;
        background: #4576f0;
        color: #fff;
    }
    .arrow{
        font-size: 18px;
        color: #000;
    }
    .classify{
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: .2rem .3rem;
        border-bottom: .01rem solid #dfe8ff;
        .compile-classify{
            font-size: 15px;
            color: #000;
            position: absolute;
            left: 50%;
            transform: translate(-50%,0);
        }
        .btn{
            color: #fff;
            background: #4576f0;
            font-size: 12px;
            line-height: 12px;
            padding: 0 .3rem;
            margin-top: 0;
        }
    }
    .photo{
        .small-img{
            margin: .1rem 0;
            .box-item{
                float: left;
                margin-left: .1rem;
                overflow: hidden;
                box-sizing: border-box;
                background: #ff6a4e;
                .des{
                    text-align: center;
                    color: #fff;
                    font-size: 12px;
                    padding: .1rem 0;
                }
                img{
                    box-sizing: border-box;
                    width: 1rem;
                    height: 1rem;
                    border: none;
                }
                .defaultIndex{
                    border: .02rem solid transparent;
                }
            }
            .boxItemActive{
                background: #4576f0;
            }
        }
        .big-img{
            img{
                width: 100%;
                height: 7.5rem;
            }
        }
    }
    .date{
        margin: 0 .3rem;
        padding: 0;
        height: 1rem;
        line-height: 1rem;
        border-bottom: .01rem solid #dfe8ff;
        &:first-child{
            vertical-align: middle;
            font-size: 15px;
        }
        .arrow{
            float: right;
            color: #a7b5cb;
        }
    }
    .label{
        margin: .2rem .3rem;
        height: 1rem;
        border: .01rem solid #dfe8ff;
        border-radius: .5rem;
    }
}
</style>
