<template>
  <div style=" white-space: nowrap; overflow: auto;">
    <el-row>
      <el-col :span="1">
        <el-affix :offset="60">
          <el-anchor :offset="10" ref="anchor">
            <el-anchor-link v-for="(title,index) in titles" :href="`#${title.id}`" :title="title.name" style="width: 50px">
            </el-anchor-link>
            <el-col :span="10" :offset="1">
        <el-affix :offset="60"><br><br>
          <el-form-item label="简历名称" style="margin-left: -10px;"><br>
            <el-input v-model="name" style="width: 200px;margin-left: -70px;"></el-input>
          </el-form-item>
          <el-form-item label="简历模板" style="margin-left: -10px;"><br>
          <el-select v-model="formwork" placeholder="请选择模板" style="width: 200px;margin-left: -70px;">
          <el-option v-for="item in formworks" :label="item.name" :value="item.id"/></el-select>
      </el-form-item>
            </el-affix>
            </el-col>
            <br><br><br>选择<br>模板<br>
            <el-icon style="font-size: 30px;"><DocumentRemove /></el-icon><br>
            <el-icon style="font-size: 30px;"><Bottom /></el-icon><br>
            编辑<br>信息<br>
            <el-icon style="font-size: 30px;"><Document /></el-icon><br>
            <el-icon style="font-size: 30px;"><Bottom /></el-icon><br>
            保存<br>打印<br>
            <el-icon style="font-size: 30px;"><Printer /></el-icon>
            
          </el-anchor>
        </el-affix>
      </el-col>
      <el-col :span="10" :offset="1">
        <el-affix :offset="60">
    </el-affix>
        <div style="margin-top: 20px">
          <el-card v-for="(title,index) in titles" :id="title.id" ref="anchor-div" style="margin-bottom: 20px;margin-left: -20px;">
            <template #header>
              <div class="card-header">
              <el-row>
                <el-col :span="2">{{ title.name }}</el-col>
                <el-col :span="16"></el-col>
                <el-col :span="6">
                  <el-button v-if="title.type==='Multiple'&&!title.active" @click="activeEdit(index)">编辑</el-button>
                  <el-button v-else-if="!title.active" @click="activeEdit(index)">编辑</el-button>
                  <el-button v-else="!title.active" @click="activeEdit(index)">退出编辑</el-button>
                </el-col>
              </el-row>
            </div>
            </template>
            <title-form :title="title" v-if="title.active" @activeEdit="title.active=false;save()">
            </title-form>
            <title-view :title="title" v-else @deleteItem="save();">
          </title-view>
          </el-card>
        </div>
      </el-col>
      <el-col :span="10" style="display: flex; align-items: flex-start;">
        <div style="margin-top: 0px; margin-left: 20px;">
          <el-card style="margin-bottom: 00px;">
            
            <iframe :src="iframeSrc()" style="width:680px; height:900px;" v-if="showPreview"></iframe>
          </el-card>
        </div>
      </el-col>
    </el-row>
  </div>
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
    this.resetResume();
    let that = this;
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
      this.$refs.anchor.scrollTo(`#${this.titles[index].id}`)
      this.titles[index].active ^= true;
      if (this.titles[index].type === 'Multiple') {
        if(this.titles[index].active!==0) this.titles[index].datas.push({});
        else {
          this.titles[index].datas.pop();
        }
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
    }, resetResume:function (){
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
    }
  }
}
</script>
<style>
</style>
