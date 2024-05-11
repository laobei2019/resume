<template>
  <el-row>
    <el-col :span="24">
      简历名称:{{name}}

      <el-card v-for="(title,index) in titles" :id="title.id" ref="anchor-div"
               style="margin-bottom: 20px;">
        <template #header>
          <div class="card-header">
            <el-row>
              <el-col :span="2">{{ title.name }}</el-col>
              <el-col :span="20"></el-col>
            </el-row>
          </div>
        </template>
        <title-view :title="title">
        </title-view>
      </el-card>
    </el-col>
  </el-row>
</template>
<script>
import titleForm from '@/components/titleForm.vue'
import titleView from '@/components/titleView.vue'

export default {
  components: [
    titleForm, titleView
  ],
  props: ["titlesProp"],
  mounted() {
  },
  data() {
    return {
      name: '',
      titles:this.titlesProp
    }
  }, methods: {

  }
}
</script>
<style>
</style>
