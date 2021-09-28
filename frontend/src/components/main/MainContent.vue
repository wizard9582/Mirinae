<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white flex justify-between">
            <div v-for="category in state.categories" :key="category.id" class="group relative mx-auto">
                <div class="w-12 h-12 rounded-full bg-white cursor-pointer text-center" @click="clickCategory">
                    <img :src="category.imgSrc" :alt="category.imgAlt" class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                    <p class="text-sm text-gray-700">{{category.name}}</p>
                </div>
            </div>
        </div>
        <div class="max-w-2xl mt-1 mx-auto py-4 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-t-0 border-4 border-black bg-white divide-y divide-black">
            <h2 class="text-2xl font-extrabold tracking-tight text-gray-900">{{state.category}}</h2>
            <div class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3  sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8">
                <funding-thumbnail v-for="funding in state.fundings" :key="funding.id" @click="clickFunding"/>
            </div>
            <div class="bg-white mt-10 px-4 pt-6 pb-3 flex items-center justify-between border-t border-gray-200 sm:px-6">
                <div class="flex-1 flex justify-between sm:hidden">
                <a href="#" class="relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
                    Previous
                </a>
                <a href="#" class="ml-3 relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">
                    Next
                </a>
                </div>
                <div class="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
                <div>
                    <p class="text-sm text-gray-700">
                        {{state.index}} of {{state.page}} pages
                    </p>
                </div>
                <div>
                    <nav class="relative z-0 inline-flex rounded-md shadow-sm -space-x-px" aria-label="Pagination">
                    <a href="#" class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                        &lt;&lt;
                    </a>
                    <!-- Current: "z-10 bg-indigo-50 border-indigo-500 text-indigo-600", Default: "bg-white border-gray-300 text-gray-500 hover:bg-gray-50" -->
                    <a href="#" class="bg-white border-gray-300 text-gray-500 hover:bg-gray-50 relative inline-flex items-center px-4 py-2 border text-sm font-medium">
                        1
                    </a>
                    <a href="#" class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50">
                        &gt;&gt;
                    </a>
                    </nav>
                </div>
                </div>
            </div>
        </div>
        <div class="max-w-2xl mx-auto mt-4 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white flex justify-between">
            <ranking/>
            <ranking/>
            <ranking/>
        </div>
    </div>
</template>

<script>
import Ranking from '@/components/ranking/Ranking.vue'
import FundingThumbnail from '@/components/funding/FundingThumbnail.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'MainContent',
    components:{
        Ranking,
        FundingThumbnail,
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            category: "카테고리명",
            page: 1,
            index: 1,
            categories:[
                {
                    id: 1,
                    name: "category",
                    imgSrc: 'https://www.creative-tim.com/learning-lab/tailwind-starter-kit/img/team-2-800x800.jpg',
                    imgAlt: "funding_id",
                }
            ],
            fundings: [
                {
                    id: 1,
                    title: "funding_title",
                    imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
                    imgAlt: "funding_id",
                    balance: 0,
                    goal: 100,
                },
                                {
                    id: 1,
                    title: "funding_title",
                    imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
                    imgAlt: "funding_id",
                    balance: 0,
                    goal: 100,
                },
                                {
                    id: 1,
                    title: "funding_title",
                    imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
                    imgAlt: "funding_id",
                    balance: 0,
                    goal: 100,
                },
                                {
                    id: 1,
                    title: "funding_title",
                    imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
                    imgAlt: "funding_id",
                    balance: 0,
                    goal: 100,
                },
                                {
                    id: 1,
                    title: "funding_title",
                    imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
                    imgAlt: "funding_id",
                    balance: 0,
                    goal: 100,
                },
            ]
        })

        const clickFunding = () =>{
            router.push("/main/fund/id")
        }

        return { state, clickFunding}
    }
};
</script>

<style scoped>
</style>