<template>
  <div style="background-color: black;padding: 1px;">
    <Toolbar :editor="editorRef" :mode="mode" :defaultConfig="toolbarConfig"
    />
    <Editor
        :mode="mode"
        :defaultConfig="state.editorConfig"
        :style="{ height }"
        v-model="state.editorVal"
        @onCreated="handleCreated"
        @onChange="handleChange"
    />
  </div>
</template>

<script setup>
import '@wangeditor/editor/dist/css/style.css';
import {reactive, shallowRef, watch, onBeforeUnmount} from 'vue';
import {Toolbar, Editor} from '@wangeditor/editor-for-vue';

const props = defineProps({
  // 内容框默认 placeholder
  placeholder: {
    type: String,
    default: () => '请输入内容...',
  },
  mode: {
    type: String,
    default: () => 'default',
  },
  // 高度
  height: {
    type: String,
    default: () => '310px',
  },
  getHtml: String,
  getText: String,
});

const emit = defineEmits(['update:getHtml', 'update:getText']);

const editorRef = shallowRef();
const state = reactive({
  editorConfig: {
    placeholder: props.placeholder,
  },
  editorVal: props.getHtml,
});
const toolbarConfig = reactive({
  excludeKeys: ['group-image', 'group-video', "fullScreen"]
})

// 编辑器回调函数
const handleCreated = (editor) => {
  editorRef.value = editor;
};
// 编辑器内容改变时
const handleChange = (editor) => {
  emit('update:getHtml', editor.getHtml());
  emit('update:getText', editor.getText());
};

// 页面销毁时
onBeforeUnmount(() => {
  const editor = editorRef.value;
  if (editor == null) return;
  editor.destroy();
});
watch(
    () => props.disable,
    (bool) => {
      const editor = editorRef.value;
      if (editor == null) return;
      bool ? editor.disable() : editor.enable();
    },
    {
      deep: true,
    }
);
// 监听双向绑定值改变，用于回显
watch(
    () => props.getHtml,
    (val) => {
      state.editorVal = val;
    },
    {
      deep: true,
    }
);
</script>