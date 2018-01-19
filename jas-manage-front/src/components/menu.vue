<template>
    <div class="menu-wrap">
      <el-menu :default-active="tabIndex" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-document"></i>数据查询</template>
          <el-menu-item-group>
            <el-menu-item index="1-1"><a href="#/users">用户信息</a></el-menu-item>
            <el-menu-item index="1-2"><a href="#/device">设备列表</a></el-menu-item>
          </el-menu-item-group>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-message"></i>训练计划列表</template>
            <el-menu-item-group>
              <el-menu-item index="2-1"><a href="#/exerciseProcess">训练计划列表</a></el-menu-item>
              <el-menu-item index="2-2"><a href="#/exerciseRecord">训练记录列表</a></el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-message"></i>积分优惠券列表</template>
            <el-menu-item-group>
              <el-menu-item index="3-1"><a href="#/scores">积分列表</a></el-menu-item>
              <el-menu-item index="3-2"><a href="#/coupon">优惠券列表</a></el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-submenu>
        <el-submenu index="4">
          <template slot="title"><i class="el-icon-message"></i>日志管理</template>
          <el-menu-item-group>
            <el-menu-item index="4-1"><a href="#/operationLog">访问日志</a></el-menu-item>
          </el-menu-item-group>
        </el-submenu>

        <el-menu-item index="5"><a href="#/userExamine">用户注册审核</a></el-menu-item>
      </el-menu>
    </div>
</template>
<script>
    export default {
        name: 'menu',
        computed: {
            tabIndex(){
                return this.$store.state.tab_index;
            }
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>
<style lang="scss">
    @import "../assets/scss/define";
    .menu-wrap{
        @extend %pa;
        top: 60px;
        left: 0;
        width: 200px;
        bottom: 0;
        background-color: #EFF2F7;
    }
    .el-menu-item{
        @extend %pr;
        a{
            @extend %pa;
            @extend %db;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            padding: 0 45px;
            color: inherit;
        }
    }
</style>
