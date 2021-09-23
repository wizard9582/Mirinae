<template>
    <div></div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
//import 123 from '@/'
export default {
    name: 'PopLogout',
    components: {
    },

    setup(){
        const state = reactive({})

        return {state}
    }
};
</script>

<style scoped>
</style>