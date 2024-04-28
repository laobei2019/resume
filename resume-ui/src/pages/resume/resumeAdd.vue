<template>

  <el-row>
    <el-col :span="2">
      <el-affix>
        <el-anchor :offset="70" ref="anchor">
          <el-anchor-link v-for="(title,index) in titles" :href="`#${title.id}`" :title="title.name">
          </el-anchor-link>
        </el-anchor>
      </el-affix>
    </el-col>

    <el-col :span="11">
      <el-form-item label="简历名称">
        <!--输入框-->
        <el-input v-model="name"></el-input>
      </el-form-item>
      <el-form-item label="简历模板">
        <el-select v-model="formwork" placeholder="请选择模板">
          <el-option v-for="item in formworks" :label="item.name" :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-card v-for="(title,index) in titles" :id="title.id" ref="anchor-div"
               style="margin-bottom: 20px;">
        <template #header>
          <div class="card-header">
            <el-row>
              <el-col :span="2">{{ title.name }}</el-col>
              <el-col :span="20"></el-col>
              <el-col :span="2">
                <span v-if="title.type==='Multiple'&&!title.active" @click="activeEdit(index)">编辑</span>
                <span v-else-if="!title.active" @click="activeEdit(index)">编辑</span>
              </el-col>
            </el-row>
          </div>
        </template>
        <title-form :title="title" v-if="title.active" @activeEdit="title.active=false;save()">
        </title-form>
        <title-view :title="title" v-else>
        </title-view>
      </el-card>
    </el-col>
    <el-col :span="11">
      <iframe :src="iframeSrc()" style="width:100%; height:100vh;border: 0px;"
              v-if="showPreview"></iframe>
    </el-col>
  </el-row>
</template>
<script>
import titles from '/public/titles.json';
import titleForm from '@/components/titleForm.vue'
import titleView from '@/components/titleView.vue'
import axios from "@/axios/index.js";
import {VXETable} from "vxe-table";
import {ElMessage} from 'element-plus'
import util from '@/utils/index'

export default {
  components: [
    titleForm, titleView
  ],
  props: ["id", "showEdit", "formworkId"],
  mounted() {
    this.titles = titles;
    let that = this;
    if (this.id !== '' && this.id !== undefined) {
      axios.get(`/resume/view/${this.id}`).then(function ({data}) {
        data = data.data
        if (data.context !== undefined) {
          that.titles = JSON.parse(data.context);
        }
        that.name = data.name;
      });
    }
    axios.post(`/formwork?page=0&limit=99999`).then(function ({data}) {
      that.formworks = data.data.records;
    });
    this.formwork = this.formworkId;
  },
  unmounted() {
    this.$emit("refresh")
  },
  data() {
    return {
      titles: [],
      name: '',
      dataId: this.id,
      formworks: [],
      formwork: '',
      showPreview: true
    }
  }, methods: {
    log: function () {
      console.log(this.titles);
    }, activeEdit: function (index) {
      this.titles[index].active ^= true;
      if (this.titles[index].type === 'Multiple') {
        this.titles[index].datas.push({});
      }
    }, save: function () {
      this.showPreview=false;
      let that = this;
      let form = {
        name: this.name,
        context: JSON.stringify(this.titles),
        id: this.id || this.dataId,
        userId: util.getUser().id
      };
      axios.post("/resume/save", form).then(function ({data}) {
        that.dataId = data.id;
        ElMessage('保存成功')
        that.showPreview=true;
      })
    }, iframeSrc: function () {
      let id = this.id || this.dataId;
      if (this.formwork === '' || id == '' || id === undefined || this.formwork === undefined) return '';
      return `http://localhost:8080/resume/preview/${id}/${this.formwork}`
    }
  }
}
</script>
<style>
</style>
