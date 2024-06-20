<template>
  <el-form :model="data" label-width="auto">
    <el-form-item v-for="formItem in formItems" :label="formItem.name||formItem.label">
      <!--输入框-->
      <el-input v-if="formItem.type==='input'" v-model="data[`${formItem.label}`]" :disabled="formItem.disable"></el-input>

      <!--单选-->
      <el-radio-group v-model="data[`${formItem.label}`]" v-else-if="formItem.type==='radio'" :disabled="formItem.disable">
        <el-radio v-for="dict in formItem.dict.split(',')" :value="dict">{{ dict }}</el-radio>
      </el-radio-group>
      <!--下拉单选-->
      <el-select v-else-if="formItem.type==='select'" v-model="data[`${formItem.label}`]" :disabled="formItem.disable">
        <el-option v-for="dict in formItem.dict.split(',')" :label="dict"
                   :value="dict"></el-option>
      </el-select>
      <!--多选-->
      <el-checkbox-group v-else-if="formItem.type==='checkbox'" v-model="data[`${formItem.label}`]" :disabled="formItem.disable">
        <el-checkbox v-for="dict in formItem.dict.split(',')" :label="dict"
                     :value="dict"></el-checkbox>
      </el-checkbox-group>

      <!--日期选择-->
      <el-date-picker v-else-if="formItem.type==='date'"
                      v-model="data[`${formItem.label}`]"
                      type="date"
                      format="YYYY-MM-DD"
                      value-format="YYYY-MM-DD"
                      placeholder="选择日期"
                      >
      </el-date-picker>

      <!--日期选择-->
      <el-date-picker v-else-if="formItem.type==='datetime'"
                      v-model="data[`${formItem.label}`]"
                      type="datetime"
                      format="YYYY-MM-DD hh:mm:ss"
                      value-format="YYYY-MM-DD hh:mm:ss"
                      placeholder="选择日期"
                      :disabled="formItem.disable">
      </el-date-picker>

      <!--富文本-->
      <template v-else-if="formItem.type==='textarea'">
        <div v-html="data[`${formItem.label}`]" v-if="formItem.disable"></div>
        <editor v-model:get-html="data[`${formItem.label}`]" v-else ></editor>
      </template>

      <!--范围日期-->
      <template v-else-if="formItem.type==='rangeDate'">
        <el-col :span="11">
          <el-date-picker
              v-model="data[`${formItem.label}_start`]"
              type="date"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              placeholder="选择开始日期">
          </el-date-picker>
        </el-col>
        <el-col :span="11">
          <el-date-picker
              v-model="data[`${formItem.label}_end`]"
              type="date"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              placeholder="选择结束日期">
          </el-date-picker>
        </el-col>
      </template>

    </el-form-item>
  </el-form>
</template>

<script>
import editor from "./editor.vue";

export default {
  components: [editor],
  props: ['formItems', 'data'],
  created() {
  }
}
</script>


<style scoped>
</style>