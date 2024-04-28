<template>
  <div v-if="title.type==='None'">
    <editor v-model:get-html="title.text"></editor>
  </div>
  <div v-else-if="title.type==='Single'">
    <resume-form :data="formData(title)" :formItems="title.formItems"></resume-form>
  </div>

  <div v-else-if="title.type==='Multiple'">
    <resume-form :data="formData(title)" :formItems="title.formItems"></resume-form>
  </div>
  <div style="text-align: -webkit-center">
    <el-button type="primary" @click="$emit('activeEdit')">保存</el-button>
  </div>

</template>

<script>
import editor from "./editor.vue";
import resumeForm from "./resume-form.vue";

export default {
  components: [editor, resumeForm],
  props: ['title'],
  created() {
  }, methods: {
    formData: function (title) {
      if (title.datas.length === 0) title.datas.push({});
      else if (JSON.stringify(title.datas[title.length - 1]) == '{}') title.datas.push({});
      return title.datas[title.datas.length - 1];
    }
  }
}
</script>

<style scoped>
</style>