<template>
    <div></div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
//import 123 from '@/'
export default {
    name: 'FundingConfirm',
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