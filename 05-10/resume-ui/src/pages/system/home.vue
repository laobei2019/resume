<template>
  <div class="common-layout">
    <el-container>
      <el-container>
        <el-aside style=" background-color:#545c64">
          <h5 class="mb-2" style="text-color:#fff">系统</h5>
          <el-menu
              default-active="/index"
              class="el-menu-vertical-demo"
              router
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#ffd04b"

          >
            <el-menu-item index="/index">首页</el-menu-item>
            <el-menu-item index="/formwork/list" v-if="util.isAdmin()">模板</el-menu-item>
            <el-menu-item index="/resume/list">简历</el-menu-item>
            <el-menu-item index="/notice/list" v-if="util.isAdmin()">公告</el-menu-item>
            <el-menu-item index="/user/notice/list" v-if="!util.isAdmin()">公告</el-menu-item>
            <el-menu-item index="/problem/list">问题反馈</el-menu-item>
            <el-menu-item index="/user/list" v-if="util.isAdmin()">用户</el-menu-item>
          </el-menu>
        </el-aside>
        <el-container>

          <el-header>
            <el-row justify="end">
              <el-dropdown>
                {{ username }}
                <arrow-down/>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="showUpdate=true">修改密码</el-dropdown-item>
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

  <vxe-modal v-model="showUpdate" title="修改密码" width="80%" height="100%" :draggable="false"
             v-if="showUpdate">
    <update-password @saveSuccess="showUpdate=false"></update-password>
  </vxe-modal>

</template>

<script lang="js" setup>
import util from '@/utils/index'
import {ref} from "vue";
import router from "@/router/index.js";
import UpdatePassword from "@/pages/user/updatePassword.vue";
import {ArrowDown} from '@element-plus/icons-vue'

const username = ref(util.getUser().name);
let showUpdate = ref(false);

function loginOut() {
  localStorage.clear();
  router.push("/login")
}
</script>
<style scoped>
.example-showcase .el-dropdown + .el-dropdown {
  margin-left: 15px;
}

.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>