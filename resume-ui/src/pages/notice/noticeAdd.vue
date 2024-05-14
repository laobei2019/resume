<template>
  <resume-form :form-items :data="form"></resume-form>
  <div style="display: grid;place-items: center;">
    <vxe-button @click="save()">发布</vxe-button>
  </div>
</template>
<script>
import resumeForm from "@/components/resume-form.vue";
import axios from "@/axios";
import {VXETable} from "vxe-table";
import util from '@/utils/index'

let formItems = [
  {
    "label": "title",
    "name": "标题",
    "type": "input"
  },{
    "label": "content",
    "name": "公告内容",
    "type": "textarea"
  },{
    "label": "sendDate",
    "name": "发布时间",
    "type": "date"
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
      axios.get(`/notice/view/${this.id}`).then(function ({data}) {
        that.form = data.data;
      });
    }else {
      this.form.username=util.getUser().name;
    }
  },
  data() {
    return {
      form: {},
      formItems
    }
  }, methods: {
    save: function () {
      let that=this;
      axios.post("/notice/save", this.form).then(function ({data}) {
        VXETable.modal.alert({content: '保存成功', status: 'success'})
        that.$emit("saveSuccess")
      })
    }
  }
}
</script>
<style>
</style>
