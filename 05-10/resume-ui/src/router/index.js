import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/', name: 'home', component: () => import('../pages/system/home.vue'), children: [
            {path: 'user/list', name: 'userList', component: () => import('../pages/user/userList.vue')},
            {path: 'index', name: 'systemIndex', component: () => import('../pages/system/index.vue')},
            {path: 'resume/list', name: 'resumeList', component: () => import('../pages/resume/resumeList.vue')},
            {path: 'notice/list', name: 'noticeList', component: () => import('../pages/notice/noticeList.vue')},
            {path: 'problem/list', name: 'problemList', component: () => import('../pages/problem/problemList.vue')},
            {path: 'user/notice/list', name: 'userNoticeList', component: () => import('../pages/notice/noticeUserList.vue')},
            {
                path: 'formwork/list',
                name: 'formworkList',
                component: () => import('../pages/formwork/formworkList.vue')
            },
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('../pages/user/login.vue')
    }, {
        path: '/register',
        name: 'register',
        component: () => import('../pages/user/register.vue')
    },
    {
        path: '/resume/view/:resume/:formwork',
        name: 'resumeView',
        component: () => import('../pages/resume/resumeView.vue')
    },
    {
        path: '/resumeHistory/list',
        name: 'resumeHistoryList',
        component: () => import('../pages/resumeHistory/resumeHistoryList.vue')
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    let user = localStorage.getItem('user');
    let fullPath = to.fullPath;
    if (["/login", "/register"].includes(fullPath)) {
        next();
    } else {
        if (user === null) {
            router.push("/login")
        }
        next();
    }

})
export default router