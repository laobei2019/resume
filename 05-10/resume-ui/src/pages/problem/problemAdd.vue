<template>
  <resume-form :form-items :data="form"></resume-form>
  <div style="display: grid;place-items: center;">
    <vxe-button @click="save()">保存</vxe-button>
  </div>
</template>
<script>
import resumeForm from "@/components/resume-form.vue";
import axios from "@/axios";
import {VXETable} from "vxe-table";
import util from '@/utils/index'

let formItems = [
  {
    "label": "type",
    "name": "问题类型",
    "type": "select",
    "dict": "卡顿,闪退,黑屏,无法登录"
  }, {
    "label": "title",
    "name": "问题名称",
    "type": "input",
    "dict": ""
  }, {
    "label": "content",
    "name": "反馈内容",
    "type": "textarea"
  },{
    "label": "eventTime",
    "name": "发生时间",
    "type": "datetime"
  }
]
export default {
  components: [
    resumeForm
  ],
  props: ["id"],
  mounted() {
    if (this.id !== '') {
      let that = this;
      axios.get(`/problem/view/${this.id}`).then(function ({data}) {
        that.form = data.data;
      });
    } else {
      this.form.userId = util.getUser().id;
    }
  },
  data() {
    return {
      form: {},
      formItems
    }
  }, methods: {
    save: function () {
      let that = this;
      axios.post("/problem/save", this.form).then(function ({data}) {
        VXETable.modal.alert({content: '保存成功', status: 'success'})
        that.$emit("saveSuccess")
      })
    }
  }
}
</script>
<style>
</style>
