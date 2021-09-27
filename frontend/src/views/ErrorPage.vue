<template>
    <div>
        <div>Oops! 페이지를 찾을 수 없습니다.</div>
        <div>이전 페이지로 돌아가기</div>
    </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'ErrorPage',
    components: {
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({})

        return {state}
    }
};
</script>

<style scoped>
</style>