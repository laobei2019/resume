<template>
  <vxe-grid v-bind="gridOptions">
    <template #toolbar ><div style="text-align: center;">
      <vxe-button @click="editRow()" style="width: 150px; height: 50px; background-color: #e6f7ff;"><el-icon><DocumentAdd /></el-icon>添加简历模板</vxe-button>
    </div></template>
    <template #pager>
      <!--使用 pager 插槽-->
      <vxe-pager
          :layouts="['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total']"
          v-model:current-page="tablePage.currentPage"
          v-model:page-size="tablePage.pageSize"
          :total="tablePage.total"
          @page-change="handlePageChange">
      </vxe-pager>
    </template>
    <template #img_default="{row}">
      <img :src="row.image" width='200px'>
    </template>
    <template #operate="{ row }">
      <formwork-add :id="row.id" @saveSuccess="list()"></formwork-add>
      <vxe-button status="danger" content="删除" @click="deleteRow(row.id)"style="margin-left: 40px;"></vxe-button>
    </template>
  </vxe-grid>

  <vxe-modal v-model="showEdit" :title="operationId===''?'新增':'编辑'" width="40%" height="40%" :draggable="false"
             v-if="showEdit" style="margin-left: -300px;margin-top: 500px;">
    <formwork-add :id="operationId" @saveSuccess="showEdit=false;list()"></formwork-add>
  </vxe-modal>
</template>

<script setup>
import {reactive, ref} from 'vue'
import axios from "@/axios";
import {VXETable} from 'vxe-table'
import formworkAdd from "@/pages/formwork/formworkAdd.vue";
import util from '@/utils/index'


let showEdit = ref(false);
let isAdmin = ref(util.isAdmin());

let operationId = ref("");
//分页
const tablePage = reactive({
  total: 0,
  currentPage: 1,
  pageSize: 10
})

//表格
const gridOptions = reactive({
  border: true,
  loading: false,
  columnConfig: {
    resizable: true
  },
  data: [],
  columns: [
    {type: 'seq', width: 60},
    {type: 'checkbox', width: 50},
    {field: 'name', title: '模板名称',width:200},
    {field: 'img', title: '模板预览',width:400,slots: {"default":"img_default"}},
    {title: '操作', slots: {default: 'operate'}}
  ]
})

//查询
function list() {
  gridOptions.loading = true;
  axios.post(`/formwork?page=${tablePage.currentPage}&limit=${tablePage.pageSize}`).then(function ({data}) {
    data = data.data;
    tablePage.total = data.total
    gridOptions.data = data.records
    gridOptions.loading = false
  });
}

function editRow(id) {
  showEdit.value = true;
  operationId.value = id || '';
}

function deleteRow(id) {
  axios.delete("/formwork/delete", {data: [id]}).then(function ({data}) {
    VXETable.modal.alert({content: '删除成功', status: 'success'})
    list();
  })
}

const handlePageChange = ({currentPage, pageSize}) => {
  tablePage.currentPage = currentPage
  tablePage.pageSize = pageSize
  list()
}
list()
</script>
