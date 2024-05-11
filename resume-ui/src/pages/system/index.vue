<template>

  <template v-if="util.isAdmin()">
    <div id="main" style="width: 100%;height: 500px"></div>
  </template>
  <div v-else>
    <el-row>
      <el-col :span="10" v-for="(formwork, index) in form.formworks" :key="formwork.id" :offset="2">
        <el-card :body-style="{ padding: '0px', position: 'relative', display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }">
          <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 1;">
            <el-button type="text" class="button" @click="useFormwork(formwork.id)"  style="width: 290px; height: 100px; font-size: 50px; background-color: #409eff; color: #fff; border: none;  padding: 0; text-shadow: 2px 2px 2px black;">使用此模板</el-button>
          </div>
          <el-image style="width: 400px; height: 600px" :src="formwork.image" fit="cover" hide-on-click-modal="true"
                    :preview-src-list="[formwork.image]"/>
          <div style="padding: 14px; text-align: center;">
            <span>{{ formwork.name }}</span>
            <div>
              <time class="time">共使用{{ formwork.useCnt }}次</time>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <vxe-modal v-model="showEdit" :title="'新增'" width="83%" height="100%" :draggable="false"
               v-if="showEdit">
      <resume-add :formworkId="formworkId" v-if="showEdit" @refresh="list()"></resume-add>
    </vxe-modal>
  </div>
</template>

<script setup>
import util from '@/utils/index'
import {reactive, ref, onMounted} from "vue";
import axios from "@/axios";
import ResumeAdd from "@/pages/resume/resumeAdd.vue";
import * as echarts from 'echarts';

let showEdit = ref(false);
let formworkId = ref('');
let form = reactive({
  "formworks": []
});

let myChart = null;


function list() {
  axios.post(`/formwork?page=0&limit=100`).then(function ({data}) {
    form.formworks = data.data.records;
    if (util.isAdmin()) {
      let x = [], y = [], that = this;
      data.data.records.forEach(item => {
        x.push(item.name);
        y.push(item.useCnt);
      })
      initMyChart(x, y);
    }
  });
}

function useFormwork(id) {
  showEdit.value = true;
  formworkId.value = id;
}

list();


// // 基于准备好的dom，初始化echarts实例
onMounted(function () {
  let main = document.getElementById('main');
  if (main !== null)
    myChart = echarts.init(main);
})

function initMyChart(x, y) {
  console.log(x, y)
  myChart.setOption({
    title: {
      text: '模板使用情况'
    },
    tooltip: {},
    xAxis: {
      data: x
    },
    yAxis: {},
    series: [
      {
        name: '使用量',
        type: 'bar',
        data: y
      }
    ]
  });
}
</script>
<style>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 10px;
  float: right;
}


.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>