import {createApp,ref,provide} from 'vue'
import Cookies from 'js-cookie'
import App from './App.vue'
import  router from './router/router'
import '@/assets/styles/index.scss' // global css
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import {createPinia} from "pinia"
// element-plus >=2.3 之后推荐直接从 es 包路径导入语言
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import './permission'

// 分页组件
import Pagination from '@/components/Pagination'

const app = createApp(App)

app.component('Pagination', Pagination)


for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}




app.use(router)
app.use(createPinia())
app.use(ElementPlus, {
    locale: zhCn,
    // 支持 large、default、small
    size: Cookies.get('size') || 'default'
})
app.mount('#app')

