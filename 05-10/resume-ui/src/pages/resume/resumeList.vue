<template>
  <vxe-grid v-bind="gridOptions">
    <template #toolbar>
      <vxe-button @click="editRow()">创建新简历</vxe-button>
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
      <vxe-button status="primary" @click="dialogFormVisible = true;resumeId=row.id;">生成简历</vxe-button>
      <vxe-button @click="historySHow = true;previewResumeId=row.id;">查看历史</vxe-button>
    </template>
  </vxe-grid>

  <vxe-modal v-model="showEdit" :title="operationId===''?'新增':'编辑'" width="80%" height="100%" :draggable="false"
             v-if="showEdit">
    <resume-add :id="operationId" v-if="showEdit" @refresh="list()"></resume-add>
  </vxe-modal>

  <vxe-modal v-model="historySHow" title="查看历史" width="90%" height="100%" :draggable="false">
    <resume-history-view :resume-id="previewResumeId" v-if="historySHow"></resume-history-view>
  </vxe-modal>

  <el-dialog v-model="dialogFormVisible" title="选择模板">
    <el-form>
      <el-form-item label="模板" label-width="模板">
        <el-select v-model="formwork" placeholder="请选择模板">
          <el-option v-for="item in formworks" :label="item.name" :value="item.id"/>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="geneResume">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {reactive, ref} from 'vue'
import axios from "@/axios";
import {VXETable} from 'vxe-table'
import ResumeAdd from "@/pages/resume/resumeAdd.vue";
import router from '@/router'
import ResumeHistoryView from "@/pages/resume/resumeHistoryView.vue";
import util from '@/utils/index'

let showEdit = ref(false);
let historySHow = ref(false);
let operationId = ref("");
let dialogFormVisible = ref(false);
let formwork = ref("");
let resumeId = ref("");
let previewResumeId = ref("");
let formworks = reactive([]);
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
    {field: 'name', title: '简历名称'},
    {title: '操作', slots: {default: 'operate'}}
  ]
})

//查询
function list() {
  gridOptions.loading = true;
  axios.post(`/resume?page=${tablePage.currentPage}&limit=${tablePage.pageSize}&userId=${util.getUser().id}`).then(function ({data}) {
    data = data.data;
    tablePage.total = data.total
    gridOptions.data = data.records
    gridOptions.loading = false
  });

  axios.post(`/formwork?page=0&limit=99999`).then(function ({data}) {
    formworks = data.data.records;
  });
}

function editRow(id) {
  showEdit.value = true;
  operationId.value = id || '';
}

function deleteRow(id) {
  axios.delete("/resume/delete", {data: [id]}).then(function ({data}) {
    VXETable.modal.alert({content: '删除成功', status: 'success'})
    list();
  })
}

function geneResume() {

  let v1 = resumeId.value;
  let v2 = formwork.value;
  if (v1 !== '' && v2 !== '') {
    const href = router.resolve({
      path: `/resume/view/${v1}/${v2}`,
    }).href;
    window.open(href, "_blank");
  }

}

const handlePageChange = ({currentPage, pageSize}) => {
  tablePage.currentPage = currentPage
  tablePage.pageSize = pageSize
  list()
}
list()
</script>
