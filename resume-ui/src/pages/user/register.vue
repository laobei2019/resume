<template>
  <div class="background-container" style="width: 100%;height: 900px;">
  <el-row >
    <el-col :span="12" :offset="6" style="margin-top: 250px;">
      <el-form :model="form" label-width="auto" >
        <el-form-item label="账号" style="max-width: 300px;margin-left: -50px;">
          <el-input v-model="form.account"/>
        </el-form-item>

        <el-form-item label="名称" style="max-width: 300px;margin-left: -50px;">
          <el-input v-model="form.name"/>
        </el-form-item>

        <el-form-item label="密码" style="max-width: 300px;margin-left: -50px;">
          <el-input v-model="form.password" type="password"/>
        </el-form-item>
        <el-form-item label="再次输入密码" style="max-width: 300px;margin-left: -50px;">
          <el-input v-model="form.password2" type="password"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="register()" style="margin-left: 20%;margin-left: 50px;">注册</el-button>
        </el-form-item>
      </el-form>

    </el-col>
  </el-row>
  </div>
</template>
<script>
import resumeForm from "@/components/resume-form.vue";
import axios from "@/axios";
import {VXETable} from "vxe-table";
import router from "@/router/index.js";

export default {
  components: [
    resumeForm
  ],
  data() {
    return {form: {}}
  }, methods: {
register: function () {
  if (this.form.password !== this.form.password2) {
    VXETable.modal.message("两次输入的密码不一致");
    return false;
  }
  axios.post(`/user/register`, this.form).then(function ({data}) {
    if (data.status === 'fail') {
      VXETable.modal.message("注册失败,账号已存在"); // 显示错误消息
    } else {
      VXETable.modal.message("注册成功,2s后跳转到登录页面");
      setTimeout(function () {
        router.push("/login")
      }, 2000);
    }
  });
}
    
  }
}
</script>
<style>
.background-container {
  background-image: url('src/assets/pg.png');
  background-size: auto;
  background-position: auto;
}
</style>
