<template>
    <div class="page-personal-introduction" v-wechat-title="$route.meta.title">
        <div>
            <x-textarea placeholder="请填写个人简介" class="content" v-model="value"></x-textarea>
            <div class="button_box" @click="save">
                <mt-button class="button">保存</mt-button>
            </div>
        </div>
    </div>
  
</template>
<script>
    import { Button} from 'mint-ui';
    import {Group, XInput, XTextarea} from 'vux';
    import api_main from 'api/main.js'
    export default {
        data() {
            return {
                value: ''
            }
        },
        components: {
            XTextarea,
            'mtButton': Button
        },
        created() {
                this.value = this.$store.state.doctor.description;
        },
        methods: {
            save() {
                this.$store.commit('saveIntroduction',this.value);
                this.$router.go(-1);
            }
        }
    }
</script>

<style lang="less">
    @import '~style/variable.less';
    .page-personal-introduction {
        .weui-cell {
            width: 100%;
            margin-top: .2rem;
            padding: 0;
            .fz(30px);
            color: #000;
            border: none;
            .weui-cell__bd {
                textarea{
                    padding: .3rem;
                    height: 2.2rem;
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
    }
</style>
