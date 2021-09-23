<template>
    <div>
        <main-header/>
        <main-content/>
        <main-footer/>
    </div>
</template>
<script>
import MainHeader from '@/components/main/MainHeader.vue'
import MainContent from '@/components/main/MainContent.vue'
import MainFooter from '@/components/main/MainFooter.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'MainPage',
    components: {
        MainHeader,
        MainContent,
        MainFooter,
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
