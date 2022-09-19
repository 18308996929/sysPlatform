import Vue from 'vue';
import Router from 'vue-router';
Vue.use(Router);
// 路由数组
const routes = [
    {
        path: '',
        component: ()=>import('@/components/login/index')
    },
    {
        path: '/library',
        component: ()=>import('@/components/myLibraty/index.vue')
    },
]

const router = new  Router({
    routes
})
export default router;