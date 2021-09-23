<template>
    <div class="fixed opacity-80 bottom-0 left-0 w-full h-24 bg-gray-200">
        <div class="mr-10 ml-auto mt-10 h-1/2 w-40 bg-gray-700 opacity-100 text-myfont">
            펀딩 참여하기
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'FundingAddon',
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