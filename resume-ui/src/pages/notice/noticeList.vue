<template>
  <vxe-grid v-bind="gridOptions">
    <template #toolbar>
      <vxe-button @click="editRow()"></vxe-button>
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
      <vxe-button content="编辑" @click="editRow(row.id)"></vxe-button>
      <vxe-button status="danger" content="删除" @click="deleteRow(row.id)"></vxe-button>
    </template>
  </vxe-grid>

  <vxe-modal v-model="showEdit" :title="operationId===''?'新增':'编辑'" width="80%" height="100%" :draggable="false"
             v-if="showEdit">
    <notice-add :id="operationId" v-if="showEdit" @refresh="list()" @saveSuccess="showEdit=false;list()"></notice-add>
  </vxe-modal>


</template>

<script setup>
import {reactive, ref} from 'vue'
import axios from "@/axios";
import {VXETable} from 'vxe-table'
import NoticeAdd from "@/pages/notice/noticeAdd.vue";

let showEdit = ref(false);
let operationId = ref("");
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
    {type: 'seq', width: 60,title: ' '},
    {type: 'checkbox', width: 50},
    {field: 'title', title: '标题'},
    {field: 'username', title: '发布人'},
    {field: 'sendDate', title: '发布时间'},
    {title: '操作', slots: {default: 'operate'}}
  ]
})

//查询
function list() {
  gridOptions.loading = true;
  axios.post(`/notice?page=${tablePage.currentPage}&limit=${tablePage.pageSize}`).then(function ({data}) {
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
  axios.delete("/notice/delete", {data: [id]}).then(function ({data}) {
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
