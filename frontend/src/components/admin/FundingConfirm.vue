<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white shadow-md">
            <div v-if="state.accessFlag" class="m-8 divide-y divide-black">
                <div class="w-full">
                    <p>승인 대기중인 펀딩</p>
                </div>
                <div v-if="state.fundingFlag" class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8 w-full h-96 overflow-x-scroll">
                    <div v-for="funding in state.fundings" :key="funding.id">
                        <funding-thumbnail :funding = funding />
                        <button class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded" @click="acceptFunding(funding.id)">승인</button>
                        <button class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded" @click="denyFunding(funding.id)">거부</button>
                    </div>
                </div>
                <div v-else class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8 w-full h-96 overflow-x-scroll">
                    승인 대기중인 펀딩이 없습니다.
                </div>
            </div>
            <div v-else class="m-8 divide-y divide-black">
                <div class="w-full">
                    <p>접속번호를 입력해주세요</p>
                    <input type="text" class="rounded w-1/2" v-model="state.accessCode">
                </div>
                <button class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded" @click="access">접속</button>
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
    name: 'FundingConfirm',
    components: {
        FundingThumbnail,
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            accessCode: "",
            accessFlag: false,
            fundingFlag: false,
            fundings:[],
        })

        const init = () => {
            store.dispatch('root/getNotAcceptedFundingList', { jwt: store.getters['root/getAuthToken']})
            .then((result)=>{
                // console.log("fundingList data----->")
                // console.log(result)

                //funding state : prepare, open, finished
                result.data.forEach(item => {
                    let fundingThumb = {id:0, title:"", imgSrc:"", imgAlt:"", goal:0, balance:0, state: ""}

                    fundingThumb.id = item.id
                    fundingThumb.title = item.title
                    fundingThumb.imgSrc = item.thumbnail
                    fundingThumb.imgAlt = item.title
                    fundingThumb.goal = item.goal
                    fundingThumb.balance = item.balance
                    state.fundings.push(fundingThumb)
                })
            })
            .catch()
        }
        const acceptFunding = (fundingId) =>{
            store.dispatch('root/fundingStateChange', { jwt: store.getters['root/getAuthToken'], fundingId: fundingId, action:"accept"})
            .then((result)=>{
                console.log("fundingAccept----->")
                console.log(result)
            })
            .catch()
        }
        const denyFunding = (fundingId) =>{
            store.dispatch('root/fundingStateChange', { jwt: store.getters['root/getAuthToken'], fundingId: fundingId, action:"deny"})
            .then((result)=>{
                console.log("fundingDeny----->")
                console.log(result)
            })
            .catch()
        }
        const access = () => {

        }
        init()
        return {state, acceptFunding, denyFunding, access}
    }
};
</script>

<style scoped>
</style>