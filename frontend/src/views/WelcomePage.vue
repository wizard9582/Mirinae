<template>
    <div>
        <welcome-content class="bg-local bg-galaxy-pattern bg-no-repeat bg-cover"
        @openKakaoLogin="onOpenKakaoLogin"
        @openNaverLogin="onOpenNaverLogin"
        @openGoogleLogin="onOpenGoogleLogin"/>
        <project-content/>
        <main-footer/>
    </div>
</template>
<script>
import WelcomeContent from '@/components/welcome/WelcomeContent.vue'
import ProjectContent from '@/components/welcome/ProjectContent.vue'
import MainFooter from '@/components/main/MainFooter.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'WelcomePage',
    components: {
        ProjectContent,
        WelcomeContent,
        MainFooter,
    },

    setup(){

        const store = useStore()
        const router = useRouter()
        const state = reactive({
        })

        const onOpenKakaoLogin = ()=>{
            //카카오 로그인
            const params = {
                redirectUri: "https://j5a506.p.ssafy.io/oauth/kakao",
                // redirectUri: "https://localhost:8083/oauth/kakao",
            };
            window.Kakao.Auth.authorize(params);
            //router.push('/main/all')
        }
        const onOpenNaverLogin = ()=>{

        }
        const onOpenGoogleLogin = ()=>{

        }

        return { state, onOpenKakaoLogin, onOpenNaverLogin, onOpenGoogleLogin,  }
    }
};
</script>

<style scoped>
</style>
