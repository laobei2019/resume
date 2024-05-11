<template>
  <el-timeline>
    <el-timeline-item
        v-for="(history, index) in history.datas"
        :key="index"
        :timestamp="history.createTime"
    >
      <el-button @click="history.show=true" v-if="!history.show">预览</el-button>
      <el-button @click="history.show=false" v-if="history.show">取消预览</el-button>
      <resume-preview :titles-prop="JSON.parse(history.context)" v-if="history.show"></resume-preview>
    </el-timeline-item>
  </el-timeline>
</template>

<script setup>
import {reactive, ref} from 'vue'
import axios from "@/axios";
import {VXETable} from 'vxe-table'
import ResumeAdd from "@/pages/resume/resumeAdd.vue";
import router from '@/router'
import ResumePreview from "@/pages/resume/resumePreview.vue";

let history = reactive({"datas": []})
const props = defineProps(['resumeId'])

//查询
function list() {
  axios.post(`/resumeHistory?page=0&limit=9999&resumeId=${props.resumeId}`).then(function ({data}) {
    history.datas = data.data.records;
    console.log(history.datas)
    history.datas.forEach((data)=>{
      data.show=false
    })
  });
}


list()
</script>
