<template>
  <el-row style="margin-top: 300px">
    <el-col :span="12" :offset="6">

      <el-form :model="form" style="max-width: 600px">
        <el-form-item label="姓名" >
          <el-input v-model="form.name" type="input" disabled/>
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="form.account" disabled/>
        </el-form-item>
        <el-form-item label="原密码">
          <el-input v-model="form.password" type="password"/>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="form.newPassword" type="password"/>
        </el-form-item>
        <el-form-item label="再次输入新密码">
          <el-input v-model="form.newPassword2" type="password"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="register()" style="margin-left: 50%">修改密码</el-button>
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
import util from '@/utils/index'

export default {
  components: [
    resumeForm
  ],
  data() {
    return {form: {}}
  },mounted() {
    this.form=util.getUser();
    console.log(util.getUser())
  }, methods: {
    register: function () {
      if (this.form.newPassword !== this.form.newPassword2) {
        VXETable.modal.message("两次输入的新密码不一致");
        return false;
      }
      var that=this;
      axios.post(`/user/updatePassword?pwd=${this.form.newPassword}`, this.form).then(function ({data}) {
        if (data.status === 'fail') {
          VXETable.modal.message(data.data);
        } else {
          VXETable.modal.message(data.data);
          that.$emit("saveSuccess")
          localStorage.clear()
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
