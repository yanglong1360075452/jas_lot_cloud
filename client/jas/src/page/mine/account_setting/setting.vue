<template>
    <div class="page-setting" v-wechat-title="$route.meta.title">
        <div class="list">
            <div class="item_more" @click="routerPeosonal">
                <span class="text">个人资料</span>
                    <i class="next_ico ico_more"></i>
            </div>
            <div class="item_more" @click="routerPhone">
                <span class="text">手机绑定</span>
                    <i class="next_ico ico_more"></i>
            </div>
        </div>
        <router-link to="/app/mine">
            <div class="button_box">
                <mt-button class="button">退出</mt-button>
            </div>
        </router-link>
        <router-view></router-view>
    </div>
</template>
<script>
import { Button } from 'mint-ui';

export default {
    data() {
        return {
        }
    },
    components: {
        'mtButton': Button
    },
    methods: {
        routerPeosonal() {
            this.$store.commit('route_record', 'setting');
            this.$router.push({
                name: 'personalInfo'
            });
        },
        routerPhone() {
            this.$router.push({
                name: 'phone'
            });
        }
    }
}
</script>
<style lang="less">
@import '../../../style/variable.less';

.page-setting {
    .list {
        background: #fff;
        .item_more {
            height: 1rem;
            line-height: 1rem;
            padding: 0;
            margin: 0 .3rem;
            .fz(30px);
            &:first-child {
                margin: .2rem .3rem 0 .3rem;
                border-bottom: 1px solid #dfe8ff;
            }
            .next_ico, 
            .ico_more {
                right: 0;
            }
        }
    }
}
</style>
