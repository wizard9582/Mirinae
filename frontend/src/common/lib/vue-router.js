import { createRouter, createWebHistory } from 'vue-router';
//import { useStore } from 'vuex'

import WelcomePage from '@/views/WelcomePage.vue';
import MainPage from '@/views/MainPage.vue';
import RankingPage from '@/views/RankingPage.vue';
import ErrorPage from '@/views/ErrorPage.vue';

const routes = [
    {
        path: '/',
        name: 'Welcome',
        component: WelcomePage,
        meta:{ loginRequired: false }
    },
    {
        path: '/main',
        name: 'Main',
        component: MainPage,
    },
    {
        path: '/rank',
        name: 'Ranking',
        component: RankingPage,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// const store = useStore()

// const isLoggedIn = function(){
//     store.getters['root/isLoggedIn']
// }

// router.beforeEach((to, from, next) => {
//     if(to.meta.loginRequired){
//         if(isLoggedIn()){
//         next()
//         }else{
//         alert("로그인이 필요합니다!")
//         next("/")
//         }
//     }else{
//         next()
//     }
// })

export default router;
