<template>
<div class="userPhotograph" v-wechat-title="$route.meta.title">
    <div class="classify">
        <!-- <div class="arrow" @click="back"><</div> -->
        <div style="width: 100%;overflow:scroll;-webkit-overflow-scrolling:touch;" ref="scroll">
            <tab style="width:500px;" :line-width=2 active-color='#4576f0' v-model="index">
                <tab-item class="vux-center" v-for="(item, index) in classify"
                        :key="index" @on-item-click="selectTabItem">{{item.name}}({{item.count}})
                </tab-item>
            </tab>
        </div>

        <scroller lock-y class="smallImg">
            <div class="box" ref="box" :style="{width:boxWidth}">
                <div class="box-item" :class="{active:item.indexType!==-1,selected:index===boxItemIndex}" 
                    v-for="(item,index) in lists" @click="seleceSmallImg(index)" :key="index">
                    <div class="img">
                        <div class="sector">
                            <span>{{item.type | substr}}</span>
                        </div>
                        <img :src="item.src">
                    </div>
                    <div class="date" v-if="item.time">{{item.time | normalizeDate}}</div>
                </div>
            </div>
        </scroller>

        <div class="bigImg">
            <img :src="imgSrc" v-show="imgSrc">
        </div>    
    </div>
</div>
</template>

<script type='text/ecmascript-6'>
import { Tab, TabItem, Scroller} from 'vux'
import { formatDate } from 'assets/js/common.js';
import api from 'api/main.js'

export default {
    data() {
        return {
            classify: [],
            boxItem: [
                // {
                //     indexType: -1,
                //     des: '未分',
                //     date: '2017-10-22',
                //     src: "static/img@2x.jpg"
                // },
                // {
                //     indexType: 0,
                //     des: '术前',
                //     date: '2017-09-02',
                //     src: "static/img@2x.jpg"
                // },
                // {
                //     indexType: 1,
                //     des: '术后',
                //     date: '2017-10-12',
                //     src: "static/img@2x.jpg"
                // },
            ],
            obj: {
                // '0': [
                //     {
                //         indexType: 0,
                //         des: '术前',
                //         date: '2017-09-02',
                //         src: "static/img@2x.jpg"
                //     },{},{},{},{}
                // ],
                // '1': [
                //     {
                //         indexType: 1,
                //         des: '术后',
                //         date: '2017-10-12',
                //         src: "static/img@2x.jpg"
                //     }
                // ],
                // '-1': [
                //     {
                //         indexType: -1,
                //         des: '未分',
                //         date: '2017-10-22',
                //         src: "static/img@2x.jpg"
                //     }
                // ]
            },
            lists: [ // 展示对应索引值的图片
                //     {
                //         indexType: 0,
                //         des: '术前',
                //         date: '2017-09-02',
                //         src: "static/img@2x.jpg"
                //     },{},{},{},{}
            ], 
            index: 0, // 选择滑块的索引值
            boxWidth: 0, //scroll滑动的总宽度
            boxItemIndex: 0, // 选择图片boxItem的索引值
            imgSrc: '', //当前显示的大图src
            
        }
    },
    created() {
        this.index = parseInt(this.$route.query.key);
        if(this.index == -1){
            this.index = 4;
        }
    },
    async mounted() {

        await this.getBoxItem();

        this.selectTabItem(this.index);

        // 不好的方法解决
        let count = 0;
        this.boxItem.forEach(function(item){
            console.log(item.indexType);
            if(item.indexType == 0){
                count++;
            }
        });

        let box = this.$refs.box,
        boxItemWidth = 2.2, // box-item：width
        boxItemLength = count; // box-item：length

        this.boxWidth = (boxItemLength * boxItemWidth).toFixed(1) + 'rem';
    },
    filters: {
        // 格式化时间
        normalizeDate(date){
            var fmt = 'yyyy.MM.dd',
                date = new Date(date);

            return formatDate(date, fmt);
        },
        substr(content){
            return content.substr(0,2);
        }  
    }, 
    components: {
        Tab,
        TabItem,
        Scroller,
    },
    methods: {
        getBoxItem() {
            return new Promise((resolve, reject) => {
                let _this = this;

                api.get_classify().then((res) => {

                    _this.classify = res;
                    _this.classify.push({
                        'code': res.length,
                        'name': '未分类'
                    });

                    _this.boxItem = _this.$store.state.medicalArchival.boxItem;

                    _this.boxItem.forEach(function(item){
                        if(_this.obj[item.indexType] === undefined){
                            _this.obj[item.indexType] = [];
                        }
                        _this.obj[item.indexType].push(item);
                    });

                    _this.classify.forEach(function(item, index){
                        if(index === _this.classify.length-1){
                            index = -1;
                        }

                        if(_this.obj[index] == undefined){
                            item.count = 0;
                        }else{
                            item.count = _this.obj[index].length;
                        }
                    })

    
                    _this.lists = _this.obj[0];
                    if(_this.lists != undefined){
                        _this.imgSrc = _this.lists[0].src;
                    }

                    _this.imgSrc = '';
                    resolve();
                });
            });
        },
        selectTabItem(index) {
            this.$refs.scroll.scrollLeft = 100 * (index-1);

            if(index === this.classify.length -1){
                index = -1;
            }

            this.lists = this.obj[index];

            if(this.lists == undefined){
                this.imgSrc = '';
            }else{
                this.imgSrc = this.lists[0].src;
            }

            this.boxItemIndex = 0;
        },
        seleceSmallImg(index){
            this.boxItemIndex = index;
            this.imgSrc = this.lists[index].src;
        }
    },
}
</script>

<style lang='less'>
.userPhotograph{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    overflow: hidden;
    z-index: 99;
    background: #fff;
    .classify{
        height: 100%;
        display: flex;
        flex-direction: column;
        .smallImg{
            margin: .1rem 0;
        }
        .box{
            height: 2.1rem;
            position: relative;
            .box-item{
                margin-left: 0.1rem;
                width: 2.1rem;
                height: 2.1rem;
                overflow: hidden;
                float: left;
                position: relative;
                background: #ff6a4e;
                .img{
                    height: 100%;
                    .sector{
                        position: absolute;
                        left: 0;
                        top: 0;
                        z-index: 99;
                        width: .825rem;
                        height: .825rem;
                        background: #ff6a4e;
                        border-radius: 0 0 .825rem 0;
                        span{
                            display: block;
                            line-height: .48rem;
                            transform: rotate(-45deg);
                            color: #fff;
                        }
                    }
                    img{
                        height: 100%;
                        width: 100%;
                    }
                }
                .date{
                    width: 100%;
                    height: 0.4rem;
                    line-height: 0.4rem;
                    color: #fff;
                    bottom: 0;
                    background: #ff6a4e;
                    text-align: center;
                    position: absolute;
                }
            }
            .active{
                background: rgba(69, 118, 240, 0.6);
                .img{
                    .sector{
                        background: rgba(69, 118, 240, 0.6);
                    }
                }
                .date{
                    background: rgba(69, 118, 240, 0.6);
                }
            }
            .selected{
                border: .04rem solid transparent;
                box-sizing: border-box;
            }
        }
        .bigImg{
            flex: 1;
            overflow: hidden;
            img{
                width: 100%;
                height: 100%;
            }
        }
    }
}
</style>
