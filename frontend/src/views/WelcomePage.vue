<template>
    <div>
        <main-header/>
        <main-welcome/>
        <main-footer/>
        <pop-login :open="state.PopLoginOpen" @closePopLogin="onClosePopLogin"/>
        <pop-logout :open="state.PopLogoutOpen" @closePopLogout="onClosePopLogout"/>
    </div>
</template>

<script>
import MainWelcome from '@/components/main/MainWelcome.vue'
import MainHeader from '@/components/main/MainHeader.vue'
import MainFooter from '@/components/main/MainFooter.vue'
import PopLogin from '@/components/main/PopLogin.vue'
import PopLogout from '@/components/main/PopLogout.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'WelcomePage',
    components: {
        MainWelcome,
        MainFooter,
        MainHeader,
        PopLogin,
        PopLogout,
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            PopLoginOpen: false,
            PopLogoutOpen: false,
        })

        const onClosePopLogin = ()=> {
            state.PopLoginOpen = false;
            if(store.getters['root/isLoggedIn']){
                router.push("/main")
            }
        }
        const onClosePopLogout = ()=> {
            state.PopLogoutOpen = false;
        }

        return { state, onClosePopLogin, onClosePopLogout }
    }
};
</script>

<style scoped>
</style>
