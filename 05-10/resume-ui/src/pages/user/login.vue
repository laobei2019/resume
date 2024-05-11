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
        <el-form-item style="align-content: center;justify-content: center">
          <el-button type="primary" @click="login" style="margin-left: 50%">登录</el-button>
          <el-button @click="toRegister">注册</el-button>
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
    toRegister:function (){
      router.push("/register")
    },
    login: function () {
      axios.post(`/user/login`, this.form, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then(function ({data}) {
        if (data.status === 'fail') {
          VXETable.modal.message(data.msg);
        } else {
          localStorage.setItem("user", JSON.stringify(data.data))
          setTimeout(function (){
            router.push("/index")
          },1000)
        }
      });
    }
  }
}
</script>
<style>

</style>
