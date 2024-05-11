<template>
  <div v-if="title.type==='None'" v-html="title.text">
  </div>
  <div v-else-if="title.type==='Single'">
    <div v-for="item of titleTrans(title,0)">
      <el-space wrap>
        <el-text>{{ item.label }}</el-text>
        <el-text>:</el-text>
        <el-text>
          <span v-html="item.val"></span>
        </el-text>
      </el-space>
    </div>
  </div>
  <div v-else>
    <el-card v-for="(data,index) in title.datas">
      <el-button @click="deleteItem(index)">删除</el-button>
      <div v-for="item of titleTrans(title,index)">
        <el-space wrap>
          <el-text>{{ item.label }}</el-text>
          <el-text>:</el-text>
          <el-text>
            <span v-html="item.val"></span>
          </el-text>
        </el-space>
      </div>
    </el-card>
  </div>
</template>

<script>
import editor from "./editor.vue";

export default {
  components: [editor],
  props: ['title'],
  created() {
  }, methods: {
    titleTrans({formItems, datas}, index) {
      if (datas.length < index) return [];

      let res = [];

      for (let item of formItems) {
        let key = item.label;
        if (datas[index] !== undefined) {

          if (datas[index][key] !== undefined) {
            //通用处理
            res.push({"label": key, "val": datas[index][key]});
          } else if (item.type === 'rangeDate') {
            //日期范围
            let start = datas[index][`${key}_start`] || '',
                end = datas[index][`${key}_end`] || '';
            if (start !== '' || end !== '') {
              res.push({"label": key, "val": `${start}至${end}`});
            }
          }
        }
      }
      return res;
    },
    deleteItem(index){
      this.title.datas.splice(index,1);
      this.$emit('deleteItem')
    }
  }
}
</script>

<style>

</style>