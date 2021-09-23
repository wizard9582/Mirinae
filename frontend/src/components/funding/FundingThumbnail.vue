<template>
    <div class="group relative shadow-md p-4">
        <div class="w-full min-h-80 bg-gray-200 aspect-w-1 aspect-h-1 rounded-md overflow-hidden group-hover:opacity-75 lg:h-60 lg:aspect-none">
            <img :src="state.funding.imgSrc" :alt="state.funding.imgAlt" class="w-full h-full object-center object-cover lg:w-full lg:h-full" />
        </div>
        <div class="mt-4">
            <h3 class="text-sm text-gray-700 font-bold">
                {{ state.funding.title }}
            </h3>
        </div>
        <div class="mt-4 flex justify-between">
            <div>
                <h3 class="text-sm text-gray-700">
                    <span aria-hidden="true" class="absolute inset-0" />
                    {{ state.funding.balance }}
                </h3>
            </div>
            <div>
                <p class="text-sm font-medium text-gray-900">{{ state.funding.goal }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
//import 123 from '@/'
export default {
    name: 'FundingThumbnail',
    components: {
    },

    setup(){
        const state = reactive({
            funding:{
                    id: 1,
                    title: "funding_title",
                    imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
                    imgAlt: "funding_id",
                    balance: 0,
                    goal: 100,
            }
        })

        return {state}
    }
};
</script>

<style scoped>
</style>