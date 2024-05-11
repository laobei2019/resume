<template>
  <el-row style="margin-top: 300px">
    <el-col :span="12" :offset="6">
      <el-form :model="form" label-width="auto" style="max-width: 600px">
        <el-form-item label="账号">
          <el-input v-model="form.account"/>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password"/>
        </el-form-item>
        <el-form-item label="再次输入密码">
          <el-input v-model="form.password2" type="password"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="register()" style="margin-left: 50%">注册</el-button>
        </el-form-item>
      </el-form>

    </el-col>
  </el-row>
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

</style>
