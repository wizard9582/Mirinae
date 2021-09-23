<template>
    <div class="w-full h-2xl">
    </div>
</template>

<script>
import Ranking from '@/components/ranking/Ranking.vue'
import FundingThumbnail from '@/components/funding/FundingThumbnail.vue'

export default {
    name: 'MainContent',
    components:{
        Ranking,
        FundingThumbnail
    },

    setup(props, {emit}){
        return { }
    }
};
</script>

<style scoped>
</style>