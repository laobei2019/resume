<template>
  <div class="common-layout">
    <el-container>
      <el-container>
        <el-aside style="background-color: #9fa5ad; width: 200px; height: 98vh;">


          <div class="page-header">
            <h1 style="color: #007BFF; font-size: 24px; text-shadow: 2px 2px 2px black;">
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;简历生成器</h1>
          </div>
          <el-menu default-active="/index" class="el-menu-vertical-demo" router background-color="#545c64"
            text-color="#fff" active-text-color="#ffd04b">
            <el-menu-item index="/index">
              <el-icon>
                <House />
              </el-icon>
              首页</el-menu-item>
            <el-menu-item index="/formwork/list" v-if="util.isAdmin()">
              <el-icon>
                <Grid />
              </el-icon>
              模板</el-menu-item>
            <el-menu-item index="/resume/list">
              <el-icon>
                <DocumentRemove />
              </el-icon>
              简历</el-menu-item>
            <el-menu-item index="/notice/list" v-if="util.isAdmin()">
              <el-icon>
                <Bell />
              </el-icon>
              公告</el-menu-item>
            <el-menu-item index="/user/notice/list" v-if="!util.isAdmin()">
              <el-icon>
                <Bell />
              </el-icon>
              公告</el-menu-item>
            <el-menu-item index="/problem/list">
              <el-icon>
                <ChatDotRound />
              </el-icon>
              问题反馈</el-menu-item>
            <el-menu-item index="/user/list" v-if="util.isAdmin()">
              <el-icon>
                <User />
              </el-icon>
              用户</el-menu-item>
          </el-menu>
        </el-aside>
        <el-container>
          <el-header>
            <el-row justify="end">

              <el-dropdown >
                <span class="el-dropdown-link">
                  {{ username }}
                  <el-icon class="el-icon--right">
                    <arrow-down />
                  </el-icon>
                </span>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="showUpdate = true">修改密码</el-dropdown-item>
                    <el-dropdown-item @click="loginOut()">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>

            </el-row>
          </el-header>
          <el-main>
            <router-view></router-view>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>

  <vxe-modal v-model="showUpdate" title="修改密码" width="80%" height="100%" :draggable="false" v-if="showUpdate">
    <update-password @saveSuccess="showUpdate = false"></update-password>
  </vxe-modal>

</template>

<script lang="js" setup>
import util from '@/utils/index'
import { ref } from "vue";
import router from "@/router/index.js";
import UpdatePassword from "@/pages/user/updatePassword.vue";
import { ArrowDown } from '@element-plus/icons-vue'

const username = ref(util.getUser().name);
let showUpdate = ref(false);

function loginOut() {
  localStorage.clear();
  router.push("/login")
}
</script>
<style scoped>
.example-showcase .el-dropdown+.el-dropdown {
  margin-left: 15px;
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>