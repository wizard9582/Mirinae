<template>
    <div>
        <content-welcome class="bg-local bg-galaxy-pattern bg-no-repeat bg-cover"
        @openKakaoLogin="onOpenKakaoLogin"
        @openNaverLogin="onOpenNaverLogin"
        @openGoogleLogin="onOpenGoogleLogin"/>
        <main-footer/>
    </div>
</template>
<script>
import ContentWelcome from '@/components/welcome/ContentWelcome.vue'
import MainFooter from '@/components/main/MainFooter.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'WelcomePage',
    components: {
        ContentWelcome,
        MainFooter,
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
        })

        const onOpenKakaoLogin = ()=>{
            
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
