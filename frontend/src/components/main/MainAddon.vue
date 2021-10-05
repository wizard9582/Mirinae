<template>
    <div class="w-10 h-10 bg-red-800 text-gray">
        ?
    </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'MainAddon',
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