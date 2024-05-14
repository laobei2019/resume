<template>
  <el-form :model="form">
    <el-form-item label="模板名称">
      <el-input v-model="form.name" style="width: 300px;"/>
    </el-form-item>
    <el-row>
      <el-col :span="12">
        <el-form-item label="模板" style="width: 200px;margin-left: 50px; background-color: #f5f7fa;padding: 30px;">
          <el-upload
              action="http://localhost:8080/file/upload"
              :before-upload="beforeUpload"
              :on-success="uploadSuccess"
              :on-preview="previewFile"
              accept=".doc,.docx"
              limit="1"
              v-model:file-list="form.fileList"
          >
            <el-button type="primary">上传</el-button>
            <template #tip>
              <div class="el-upload__tip">
                只允许上传doc或docx文件
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="预览图" style="width: 200px;margin-right: 100px; background-color: #f5f7fa;padding: 30px;">
          <el-upload
              action="http://localhost:8080/file/upload"
              :on-success="uploadImageSuccess"
              accept="png"
              limit="1"
              v-model:file-list="form.imageList"
          >
            <el-button type="primary">上传</el-button>
            <template #tip>
              <div class="el-upload__tip">
                请上传图片
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
      <el-button type="primary" style="margin-left: 300px;margin-top: auto;" @click="save()">保存</el-button>
</template>
<script>
import resumeForm from "@/components/resume-form.vue";
import util from '@/utils/index'
import axios from "@/axios";
import {VXETable} from "vxe-table";

export default {
  components: [
    resumeForm
  ],
  props: ["id"],
  mounted() {
    if (this.id !== '') {
      let that = this;
      axios.get(`/formwork/view/${this.id}`).then(function ({data}) {
        that.form = data.data;
        that.form.fileList = [{
          "name": that.form.filename,
          "url": that.form.fileUrl,
          "percentage": 100,
          "status": "success",
        }]
        that.form.imageList = [{
          "name": '预览图片',
          "url": that.form.image,
          "percentage": 100,
          "status": "success",
        }]
      });
    }
  },
  data() {
    return {
      form: {
        userId: util.getUser().id
      },

    }
  }, methods: {
    beforeUpload: function ({name}) {
      if (!(name.endsWith(".docx") || name.endsWith(".doc"))) {
        VXETable.modal.alert({content: '只允许上传doc或者docx文件', status: 'error'})
        return false;
      }
      return true;
    },
    uploadSuccess: function ({file, url}) {
      this.form.filename = file;
      this.form.fileUrl = url;
    },
    previewFile: function ({url}) {
      window.open(url)
    },
    uploadImageSuccess: function ({file, url}) {
      this.form.image = url;
    },
    save: function () {
      let that = this;
      axios.post("/formwork/save", this.form).then(function ({data}) {
        VXETable.modal.alert({content: '保存成功', status: 'success'})
        that.$emit("saveSuccess")
      })
    }
  }
}
</script>
<style>
</style>
