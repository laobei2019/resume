<template>
  <el-collapse v-model="activeNames">
    <el-collapse-item v-for="(data,index) in datas" :name="index">
      <template #title>
        标题:{{data.title}}
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        发布时间:{{data.sendDate}}
      </template>
      <div v-html="data.content"></div>
    </el-collapse-item>
  </el-collapse>
</template>

<script setup>
import {reactive, ref} from 'vue'
import axios from "@/axios";

let datas=reactive([])
const activeNames = ref([0])

//查询
function list() {
  axios.post(`/notice/list`).then(function ({data}) {
    for (let record of data.data.records){
      datas.push(record)
    }
  });

}

list()

</script>
