<template>
<div class="modern-layout">
  <el-container>
    <el-aside style="box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);">
  <el-menu
    default-active="/index"
    class="el-menu-vertical-demo"
    router
    background-color="#1e1e1e"
    text-color="#fff"
    active-text-color="#409eff"
  >
    <el-menu-item index="/index">
      <i class="el-icon-house"></i>
      <span class="vertical-text">首</span>
      <span class="vertical-text">页</span>
    </el-menu-item>
    <el-menu-item index="/formwork/list" v-if="util.isAdmin()">
      <i class="el-icon-document"></i>
      <span class="vertical-text">模</span>
      <span class="vertical-text">板</span>
    </el-menu-item>
    <el-menu-item index="/resume/list">
      <i class="el-icon-user"></i>
      <span class="vertical-text">简</span>
      <span class="vertical-text">历</span>
    </el-menu-item>
    <el-menu-item index="/user/list" v-if="util.isAdmin()">
      <i class="el-icon-user-solid"></i>
      <span class="vertical-text">用</span>
      <span class="vertical-text">户</span>
    </el-menu-item>
  </el-menu>
</el-aside>
    <el-container>
      <el-header>
        <el-row justify="end">
          <el-menu>
            <el-sub-menu>
              <template #title>{{ username }}</template>
              <el-menu-item @click="loginOut()">
                <i class="el-icon-switch-button"></i> 退出登录
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-row>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</div>


</template>

<script lang="js" setup>
import util from '@/utils/index'
import {ref} from "vue";
import router from "@/router/index.js";
const username = ref(util.getUser().name);

function loginOut() {
  localStorage.clear();
  router.push("/login")
}
</script>
<style>
</style>