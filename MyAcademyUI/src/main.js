import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import BootstrapVue3 from 'bootstrap-vue-3'
import { createRouter, createWebHashHistory } from 'vue-router'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

import {defineComponent} from "vue";


import AcademyInputForm from "@/components/AcademyInputForm.vue";
import MainHome from "@/components/MainHome.vue";

export default defineComponent({
    components: {MainHome, AcademyInputForm}
})


const routes = [
    { path: '/', component: MainHome },
    { path: '/inputform', component: AcademyInputForm },
]

// 3. `routes`를 옵션으로 전달해 라우터 인스턴스를 생성.
// 여기에 추가 옵션을 전달할 수 있지만, 지금은 간단하게 나타냄.
const router = createRouter({
    // 4. 사용할 히스토리 모드 정의. 여기서는 간단하게 해시 모드를 사용.
    history: createWebHashHistory(),
    routes, // `routes: routes`와 같음
})

const app = createApp(App)
app.use(BootstrapVue3)
app.use(router)
app.mount('#app')
