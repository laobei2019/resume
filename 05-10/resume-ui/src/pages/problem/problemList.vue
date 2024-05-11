<template>
  <vxe-grid v-bind="gridOptions">
    <template #toolbar>
      <vxe-button @click="editRow()">新增</vxe-button>
    </template>
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
    <template #operate="{ row }">
      <vxe-button content="编辑" @click="editRow(row.id)" v-if="row.userId==util.getUser().id"></vxe-button>
      <vxe-button content="反馈" @click="back(row.id)" status="primary" v-if="util.isAdmin()&&(row.backUserId==util.getUser().id||row.backUserId==undefined)"></vxe-button>
      <vxe-button status="danger" content="删除" @click="deleteRow(row.id)"></vxe-button>
    </template>
  </vxe-grid>

  <vxe-modal v-model="showEdit" :title="operationId===''?'新增':'编辑'" width="80%" height="100%" :draggable="false"
             v-if="showEdit">
    <problem-add :id="operationId" v-if="showEdit" @refresh="list()" @saveSuccess="showEdit=false;list()"></problem-add>
  </vxe-modal>

  <vxe-modal v-model="showBack" :title="operationBackId===''?'新增':'编辑'" width="80%" height="100%" :draggable="false"
             v-if="showBack">
    <problem-back :id="operationBackId" v-if="showBack" @refresh="list()" @saveSuccess="showEdit=false;list()"></problem-back>
  </vxe-modal>

</template>

<script setup>
import {reactive, ref} from 'vue'
import axios from "@/axios";
import {VXETable} from 'vxe-table'
import problemAdd from "@/pages/problem/problemAdd.vue";
import problemBack from "@/pages/problem/problemBack.vue";
import util from '@/utils/index'
let showEdit = ref(false);
let showBack = ref(false);
let operationId = ref("");
let operationBackId = ref("");
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
    {field: 'type', title: '问题类型'},
    {field: 'title', title: '问题标题'},
    {field: 'eventTime', title: '反馈时间'},
    {title: '操作', slots: {default: 'operate'}}
  ]
})

//查询
function list() {
  gridOptions.loading = true;
  let url=`/problem?page=${tablePage.currentPage}&limit=${tablePage.pageSize}`;
  if(!util.isAdmin()){
    url+='&userId='+util.getUser().id;
  }
  axios.post(url).then(function ({data}) {
    data = data.data;
    tablePage.total = data.total
    gridOptions.data = data.records
    gridOptions.loading = false
  });

}

function back(id){
  showBack.value = true;
  operationBackId.value = id || '';
}
function editRow(id) {
  showEdit.value = true;
  operationId.value = id || '';
}

function deleteRow(id) {
  axios.delete("/problem/delete", {data: [id]}).then(function ({data}) {
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
