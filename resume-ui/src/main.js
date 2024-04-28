import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import router from './router'
import VXETable from 'vxe-table'
import 'vxe-table/lib/style.css'


const app = createApp(App)

app.use(ElementPlus, {
    locale: zhCn,
})

function useTable(app) {
    app.use(VXETable)
}


app.use(router)
app.use(useTable)
app.mount('#app')