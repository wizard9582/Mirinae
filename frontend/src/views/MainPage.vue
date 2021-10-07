<template>
    <div>
        <main-header/>
        <router-view :key="$route.fullPath"/>
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


        return { }
    }
};
</script>

<style scoped>
</style>
