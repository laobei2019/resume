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
        <el-form-item label="姓名">
          <el-input v-model="form.name" type="input"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio v-for="dict in ['男','女']" :value="dict">{{ dict }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="生日">
          <el-date-picker v-model="form.birth"
                          type="date"
                          format="YYYY-MM-DD"
                          value-format="YYYY-MM-DD"
                          placeholder="选择日期">
          </el-date-picker>
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
