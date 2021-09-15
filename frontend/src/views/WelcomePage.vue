<template>
    <div>
        <content-welcome/>
        <main-footer/>
    </div>
</template>

<script>
import ContentWelcome from '@/components/welcome/ContentWelcome.vue'
import MainHeader from '@/components/main/MainHeader.vue'
import MainFooter from '@/components/main/MainFooter.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'WelcomePage',
    components: {
        ContentWelcome,
        MainFooter,
        MainHeader,
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
        })

        return { state }
    }
};
</script>

<style scoped>
</style>
