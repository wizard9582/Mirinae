import { createRouter, createWebHistory } from 'vue-router';

import WelcomePage from '@/views/WelcomePage.vue';
import MainPage from '@/views/MainPage.vue';
import ErrorPage from '@/views/ErrorPage.vue';
import OauthCallback from '@/components/oauth/OauthCallback.vue';
import MainContent from '@/components/main/MainContent.vue';
import UserContent from '@/components/user/UserContent.vue';
import TransactionContent from '@/components/transaction/TransactionContent.vue';
import FundingContent from '@/components/funding/FundingContent.vue';
import FundingCreate from '@/components/funding/FundingCreate.vue';
import FundingEdit from '@/components/funding/FundingEdit.vue';
import FundingConfirm from '@/components/admin/FundingConfirm.vue';

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
        children: [
            {path: ":id/:page", component: MainContent, meta:{ loginRequired: false } },
            {path: "user/:id", component: UserContent , meta:{ loginRequired: true } },
            {path: "tx/:id", component: TransactionContent , meta:{ loginRequired: false } },
            {path: "fund/:id", component: FundingContent , meta:{ loginRequired: false } },
            {path: "fund/create", component: FundingCreate , meta:{ loginRequired: true } },
            {path: "fund/edit/:id", component: FundingEdit , meta:{ loginRequired: true } },
            {path: "admin", component: FundingConfirm , meta:{ loginRequired: true } },
        ],
    },
    {
        path: '/oauth/:portal',
        name: 'Oauth',
        component: OauthCallback,
    },
    {
        path: '/:pathMatch(.*)*',
        redirect: "/404"
    },
    {
        path: '/404',
        name: 'Error',
        component: ErrorPage,  
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

const isLoggedIn = function(){
    return localStorage.getItem('jwt')? true:false;
}

router.beforeEach((to, from, next) => {
    if(to.meta.loginRequired){
        if(isLoggedIn()){
            next()
        }else{
            alert("로그인이 필요합니다!")
            const params = {
                redirectUri: "https://j5a506.p.ssafy.io/oauth/kakao",
            };
            window.Kakao.Auth.authorize(params);
        }
    }else{
        next()
    }
})

export default router;
