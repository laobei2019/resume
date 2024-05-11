<template>
  <vxe-grid v-bind="gridOptions">

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

</template>

<script setup>
import {reactive, ref} from 'vue'
import axios from "@/axios";
import {VXETable} from 'vxe-table'

let showEdit = ref(false);
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
    {field: 'createTime', title: '修改时间'},
  ]
})

//查询
function list() {
  gridOptions.loading = true;
  axios.post(`/resumeHistory?page=${tablePage.currentPage}&limit=${tablePage.pageSize}`).then(function ({data}) {
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
  axios.delete("/resumeHistory/delete", {data: [id]}).then(function ({data}) {
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
