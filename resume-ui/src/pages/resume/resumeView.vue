<template>
  <div>
    <iframe :src="iframeSrc" style="width:90%; height:97vh;border: 0px"></iframe>
    <div style="position: absolute;top: 0;right: 0;background-color: #f0f0f0; padding: 10px;width: 10%; display: flex; flex-direction: column;">
      <el-button @click="download('docx')" style="height: 410px; width: 100%;margin-left: 0%; background-color: #add8e6; border: 2px solid #d3d3d3; color: #ffffff; font-size: 50px;margin-bottom: 40px;margin-top: 20px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;">下载<br/>word<br/>格式</el-button>
      <el-button @click="download('pdf')" style="height: 410px; width: 100%; margin-left: 0%; background-color: #add8e6; border: 2px solid #d3d3d3; color: #ffffff; font-size: 50px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;">下载<br/>pdf<br/>格式</el-button>
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