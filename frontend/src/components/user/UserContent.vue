<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white shadow-md">
            <div class="m-8 divide-y divide-black">
                <div class="w-full">
                    <p>내 정보</p>
                </div>
                <div class="w-full h-60"></div>
            </div>
            <div class="m-8 divide-y divide-black">
                <div class="w-full h-24 flex justify-between">
                    <div>{{state.userName}} 님의 지갑</div>
                    <div>내 지갑주소:{{state.userWallet}}</div>
                    <div class="m-4 w-12 h-12 rounded-full bg-white cursor-pointer text-center" @click="clickUser">
                        <img src="https://www.creative-tim.com/learning-lab/tailwind-starter-kit/img/team-2-800x800.jpg" alt="..." class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                        <p class="text-sm text-gray-700">{{state.userName}}</p>
                    </div>
                </div>
                <div class="w-full h-60">
                    ETC {{state.userBalance}}
                </div>
            </div>
            <div class="m-8 divide-y divide-black">
                <div class="w-full">
                    <p>내가 참여한 펀딩</p>
                </div>
                <div class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8 w-full h-96 overflow-x-scroll">
                    <funding-thumbnail v-for="funding in state.myDonations" :key="funding.id" :funding = funding />
                </div>
            </div>
            <div class="m-8 divide-y divide-black">
                <div class="w-full">
                    <p>내가 개설한 펀딩</p>
                </div>
                <div class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8 w-full h-96 overflow-x-scroll">
                    <div v-for="funding in state.myFundings" :key="funding.id">
                        <funding-thumbnail :funding = funding />
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import FundingThumbnail from '@/components/funding/FundingThumbnail.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
//import 123 from '@/'
export default {
    name: 'UserContent',
    components: {
        FundingThumbnail,
    },

    setup(){
        const store = useStore()
        const state = reactive({
            userName:"이름",
            userWallet:"1234",
            userBalance: 100,
            myDonations:[
                {
                    id: 3,
                    title: "funding_title",
                    imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
                    imgAlt: "funding_id",
                    balance: 0,
                    goal: 100,
                },
                {
                    id: 2,
                    title: "funding_title",
                    imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
                    imgAlt: "funding_id",
                    balance: 100,
                    goal: 500,
                },
            ],
            myFundings:[
                {
                    id: 1,
                    title: "funding_title",
                    imgSrc: 'https://tailwindui.com/img/ecommerce-images/product-page-01-related-product-01.jpg',
                    imgAlt: "funding_id",
                    balance: 0,
                    goal: 100,
                },
            ],
        })

        const init = () =>{
            store.dispatch('root/getUserInfo', {jwt: store.getters['root/getAuthToken']})
            .then((result)=>{
                console.log(result)
                //지갑주소 없으면 지갑만들기 버튼 보이게, 있으면 있는거 보여주기ㄴ
            })
            .catch()
        }

        const makeWallet = () =>{
            //지갑 만들기 관련 api
        }

        init()
        return {state, makeWallet}
    }
};
</script>

<style scoped>
</style>