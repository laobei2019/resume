<template>
  <div v-for="(data, index) in datas" :key="index">
    <h1 style="font-size: 20px; color: #000000; font-weight: bold;">{{data.title}}</h1>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    发布时间:{{data.sendDate}}
    <div v-html="data.content"></div>
  </div>
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
