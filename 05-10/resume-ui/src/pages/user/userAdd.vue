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

let formItems = [
  {
    "label": "name",
    "name": "姓名",
    "type": "input"
  },{
    "label": "birth",
    "name": "生日",
    "type": "date"
  },{
    "label": "phone",
    "name": "联系方式",
    "type": "input"
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
      axios.get(`/user/view/${this.id}`).then(function ({data}) {
        that.form = data.data;
      });
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
      axios.post("/user/save", this.form).then(function ({data}) {
        VXETable.modal.alert({content: '保存成功', status: 'success'})
        that.$emit("saveSuccess")
      })
    }
  }
}
</script>
<style>
</style>
