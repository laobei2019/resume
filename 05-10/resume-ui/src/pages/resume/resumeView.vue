<template>
  <div>
    <iframe :src="iframeSrc" style="width:90%; height:100vh;border: 0px"></iframe>
    <div style="position: absolute;top: 0;right: 0;background-color: #f0f0f0; padding: 10px;width: 10%">
      <el-button @click="download('docx')">下载word</el-button>
      <el-button @click="download('pdf')">下载pdf</el-button>
    </div>
  </div>
</template>

<script>
export default {
  mounted() {
    let params = this.$route.params;
    this.formwork = params.formwork;
    this.resume = params.resume;
    this.iframeSrc = `http://localhost:8080/resume/preview/${this.resume}/${this.formwork}`
  }, data: function () {
    return {
      formwork: '',
      resume: '',
      iframeSrc: ``
    }
  }, methods: {
    download: function (type) {
      window.open(`http://localhost:8080/resume/download/${this.resume}/${this.formwork}/${type}`)
    }
  }
}
</script>