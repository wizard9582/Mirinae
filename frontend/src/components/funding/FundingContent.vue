<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white shadow-md">
            <div class="m-8 divide-y divide-black">
                <div class="w-full flex justify-between">
                    <div>
                        <p>{{state.fundingTitle}}</p>
                    </div>
                    <div>
                        개설자 : {{state.userName}}
                    </div>
                    <div class="m-4 w-12 h-12 rounded-full bg-white cursor-pointer text-center">
                        <img src="https://www.creative-tim.com/learning-lab/tailwind-starter-kit/img/team-2-800x800.jpg" alt="..." class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                    </div>
                </div>
                <div class="p-4">
                    <p class="mx-10">현재 모금액 : {{state.amount}}</p>
                    <p class="mx-10">목표액 : {{state.goal}}</p>
                    <div class="mx-10 flex items-center">
                        <div class="relative w-full">
                            <div class="overflow-hidden h-4 text-xs flex rounded bg-red-200">
                                <div style="width:60%" class="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-red-500"></div>
                                <p class="mx-10">{{state.percentage}} %</p>
                            </div>
                        </div>
                    </div>
                    <div class="w-1/2 mx-auto my-10">
                        <img :src="state.imgSrc" :alt="state.imgAlt" class="w-full h-full object-center object-cover lg:w-full lg:h-full" />
                    </div>
                    <div>
                        설명
                    </div>
                </div>
                <div>
                    <div>
                        참가자들
                        <div v-for="donator in state.donators" :key="donator.id" class="group relative mx-auto">
                            <div class="w-12 h-12 rounded-full bg-white cursor-pointer text-center">
                                <img :src="donator.imgSrc" :alt="donator.imgAlt" class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                                <p class="text-sm text-gray-700">{{donator.name}}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <funding-addon/>
    </div>
</template>

<script>
import FundingAddon from '@/components/funding/FundingAddon.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

//import 123 from '@/'
export default {
    name: 'FundingContent',
    components: {
        FundingAddon
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            fundingTitle:"title",
            amount:100,
            goal:200,
            percentage:50,
            imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
            imgAlt: "funding_id",
            userName: "name",
        })

        return {state}
    }
};
</script>

<style scoped>
</style>