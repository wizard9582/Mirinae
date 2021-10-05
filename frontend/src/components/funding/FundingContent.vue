<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white shadow-md">
            <div class="m-8 divide-y divide-black">
                <div class="w-full flex justify-between">
                    <div>
                        <p>{{state.fundingTitle}}</p>
                    </div>
                    <div class="p-4 text-right">
                        개설자 : {{state.userName}}
                    </div>
                </div>
                <div class="p-4">
                    <div class="p-4 flex justify-between">
                        <p class="mx-10">펀딩 시작일 : {{state.startDate}}</p>
                        <p class="mx-10">펀딩 종료일 : {{state.endDate}}</p>
                    </div>
                    <div class="mx-10 flex items-center">
                        <div class="relative w-full">
                            <div class="overflow-hidden h-4 text-xs flex rounded bg-green-200">
                                <div style="width:`{{state.proceed}}`%" class="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-green-500"></div>
                                <p class="mx-10">{{state.proceed}} %</p>
                            </div>
                        </div>
                    </div>

                    <div class="p-4 flex justify-between">
                        <p class="mx-10">현재 모금액 : {{state.balance}}</p>
                        <p class="mx-10">목표액 : {{state.goal}}</p>
                    </div>
                    <div class="mx-10 flex items-center">
                        <div class="relative w-full">
                            <div class="overflow-hidden h-4 text-xs flex rounded bg-red-200">
                                <div style="width:`{{state.percentage}}`%" class="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-red-500"></div>
                                <p class="mx-10">{{state.percentage}} %</p>
                            </div>
                        </div>
                    </div>
                    <div class="w-1/2 mx-auto my-10">
                        <img :src="state.imgSrc" :alt="state.imgAlt" class="w-full h-full object-center object-cover lg:w-full lg:h-full" />
                    </div>
                    <div>
                        {{state.content}}
                    </div>
                </div>
                <div>
                    <div class="flex justify-between pt-8">
                        <div class="w-1/3 mx-auto">
                            <p>참가자 목록</p>
                            <div v-for="donator in state.donators" :key="donator.id" class="group relative mx-auto">
                                <div class="w-12 h-12 rounded-full bg-white cursor-pointer text-center">
                                    <img :src="donator.imgSrc" :alt="donator.imgAlt" class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                                    <p class="text-sm text-gray-700">{{donator.name}}</p>
                                </div>
                            </div>
                        </div>
                        <ranking :rankingData = "state.rankingData" :rankingTitle = "state.rankingTitle"/>
                    </div>
                </div>
            </div>
        </div>
        <funding-addon/>
    </div>
</template>

<script>
import FundingAddon from '@/components/funding/FundingAddon.vue'
import Ranking from '@/components/ranking/Ranking.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

//import 123 from '@/'
export default {
    name: 'FundingContent',
    components: {
        FundingAddon,
        Ranking
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            isOwn:false,
            rankingTitle: "펀딩 참가자 랭킹",
            rankingData: [],
            fundingTitle:"",
            content:"",
            balance:0,
            goal:0,
            percentage:0,
            imgSrc: "",
            imgAlt: "",
            userName: "",
            fundingId:0,
            startDate:"",
            endDate:"",
            proceed:0,
        })

        const init=()=>{
            state.fundingId = router.currentRoute.value.params.id
            store.dispatch('root/detailFunding', {fundingId: state.fundingId})
            .then((result)=>{
                //console.log(result)
                state.balance = result.data.balance
                state.goal = result.data.goal
                state.percentage = (parseFloat(state.balance) / parseFloat(state.goal)) * 100
                state.content = result.data.content
                state.fundingTitle = result.data.title
                state.userName = result.data.userNickNam
                state.startDate = result.data.startDatetime
                state.endDate = result.data.endDatetime
                state.imgAlt = result.data.title
                state.imgSrc = result.data.image
            })
            .catch()

            store.dispatch('root/getFundingRanking', {fundingId: state.fundingId})
            .then((result)=>{
                let no = 1
                result.data.forEach(item => {
                    let ranking = {id:no++, userThumbnail:"", name:"", amount:0}

                    ranking.userThumbnail = item.userThumbnail
                    ranking.name = item.userNickname
                    ranking.amount = item.amount

                    state.rankingData.push(ranking)
                })
            })
            .catch()

            store.dispatch('root/ckeckFundingOwner', {jwt:store.getters['root/getAuthToken'], fundingId: state.fundingId})
            .then((result)=>{
                // console.log(result)
                if(result.data.status === 200){
                    state.isOwn = true
                }
            })
            .catch()
        }

        init()
        return {state}
    }
};
</script>

<style scoped>
</style>