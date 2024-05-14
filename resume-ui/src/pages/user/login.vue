<template>
  <el-row>
    <div class="background-container" style="width: 1900px;height: 920px;">
    <el-col :span="12" :offset="6" style="margin-top: 300px;">
      <el-form :model="form" label-width="auto" style="max-width: 600px">
        <el-form-item label="账号" style="max-width: 200px">
          <el-input v-model="form.account"/>
        </el-form-item>
        <el-form-item label="密码" style="max-width: 200px">
          <el-input v-model="form.password" type="password"/>
        </el-form-item>
        <el-form-item style="align-content: center;justify-content: center" >
          <el-button type="primary" @click="login" style="margin-left: 50px">登录</el-button>
          <el-button @click="toRegister">注册</el-button>
        </el-form-item>
      </el-form>

    </el-col>
    </div>
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
.background-container {
  background-image: url('src/assets/pg.png');
  background-size: auto;
  background-position: auto;
}
</style>
