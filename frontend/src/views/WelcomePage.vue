<template>
    <div>
        <welcome-content class="bg-local bg-galaxy-pattern bg-no-repeat bg-cover"
        @openKakaoLogin="onOpenKakaoLogin"
        @openNaverLogin="onOpenNaverLogin"
        @openGoogleLogin="onOpenGoogleLogin"/>
        <main-footer/>
    </div>
</template>
<script>
import WelcomeContent from '@/components/welcome/WelcomeContent.vue'
import MainFooter from '@/components/main/MainFooter.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'WelcomePage',
    components: {
        WelcomeContent,
        MainFooter,
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
        })

        const onOpenKakaoLogin = ()=>{
            // 카카오 로그인 주석처리해놓음
            // const params = {
            //     redirectUri: "https://localhost:8080/oauth/kakao",
            // };
            // window.Kakao.Auth.authorize(params);
            router.push('/main/all')
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
