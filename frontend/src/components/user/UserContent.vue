<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white shadow-md">
            <div class="m-8 divide-y divide-black">
                <div class="w-full">
                    <p>내 정보</p>
                </div>
            </div>
            <div class="m-8 divide-y divide-black">
                <div class="w-full h-24 md:flex md:justify-between">
                    <div>{{state.userName}} 님의 지갑</div>
                    <div v-if="state.walletFlag">내 지갑주소:{{state.userWallet}}</div>
                    <div v-else>
                        아직 지갑이 없습니다!
                        <button class="bg-gray-200 hover:bg-gray-500 text-white font-bold py-2 px-4 rounded" @click="makeWallet">지갑 만들기</button>
                    </div>
                </div>
                <div class="w-full">
                    닉네임 : {{state.userName}} <br>
                    메일 : {{state.userMail}} <br>
                    ETC {{state.userBalance}}
                </div>
            </div>
            <div class="m-8 divide-y divide-black">
                <div class="w-full">
                    <p>내가 참여한 펀딩</p>
                </div>
                <div v-if="state.myDonationFlag" class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8 w-full h-96 overflow-x-scroll">
                    <funding-thumbnail v-for="funding in state.myDonations" :key="funding.id" :funding = funding />
                </div>
                <div v-else class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8 w-full h-96 overflow-x-scroll">
                    아직 참여한 펀딩이 없습니다.
                </div>
            </div>
            <div class="m-8 divide-y divide-black">
                <div class="w-full">
                    <p>내가 개설한 펀딩</p>
                </div>
                <div v-if="state.myFundingFlag" class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8 w-full h-96 overflow-x-scroll">
                    <div v-for="funding in state.myFundings" :key="funding.id">
                        <funding-thumbnail :funding = funding />
                    </div>
                </div>
                <div v-else class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8 w-full h-96 overflow-x-scroll">
                    아직 개설한 펀딩이 없습니다.
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
            userMail:"email",
            userName:"이름",
            walletFlag: false,
            userWallet:"1234",
            userBalance: 0,
            myFundingFlag: false,
            myDonationFlag: false,
            myDonations:[
            ],
            myFundings:[
            ],
        })

        const init = () =>{

            let today = new Date()

            store.dispatch('root/getUserInfo', {jwt: store.getters['root/getAuthToken']})
            .then((result)=>{
                //console.log(result)
                let userInfo = result.data
                state.userMail = userInfo.email
                state.userName = userInfo.nickname
                state.imgSrc = userInfo.image
                state.userWallet = userInfo.walletAddress
                if(state.userWallet != null){
                    state.userBalance = userInfo.walletBalance
                    state.walletFlag = true
                }else{
                    state.walletFlag = false
                }

                store.dispatch('root/getMyDonation', {jwt: store.getters['root/getAuthToken']})
                .then((result)=>{
                // console.log("fundingList data----->")
                // console.log(result)

                //funding state : prepare, open, finished
                    result.data.forEach(item => {
                        let fundingThumb = {id:0, title:"", imgSrc:"", imgAlt:"", state: ""}

                        fundingThumb.id = item.id
                        fundingThumb.title = item.title
                        fundingThumb.imgSrc = item.thumbnail
                        fundingThumb.imgAlt = item.title
                        
                        let startDate = item.startDatetime
                        let endDate = item.endDatetime

                        if(today.getTime() < startDate.getTime()){
                            fundingThumb.state = "prepare"
                        }else if(todat.getTime() > endDate.getTime()){
                            fundingThumb.state = "end"
                        }else{
                            fundingThumb.state = "open"
                        }
                        state.myDonations.push(fundingThumb)
                    })

                    if(state.myDonations.length > 0){
                        state.myDonationFlag = true
                    }
                })
                .catch()
                store.dispatch('root/getMyFunding', {jwt: store.getters['root/getAuthToken']})
                .then((result)=>{
                // console.log("fundingList data----->")
                // console.log(result)

                //funding state : prepare, open, finished
                    result.data.forEach(item => {
                        let fundingThumb = {id:0, title:"", imgSrc:"", imgAlt:"", state: ""}

                        fundingThumb.id = item.id
                        fundingThumb.title = item.title
                        fundingThumb.imgSrc = item.thumbnail
                        fundingThumb.imgAlt = item.title
                        
                        let startDate = item.startDatetime
                        let endDate = item.endDatetime

                        if(today.getTime() < startDate.getTime()){
                            fundingThumb.state = "prepare"
                        }else if(todat.getTime() > endDate.getTime()){
                            fundingThumb.state = "end"
                        }else{
                            fundingThumb.state = "open"
                        }
                        state.myFundings.push(fundingThumb)
                    })

                    if(state.myFundings.length > 0){
                        state.myFundingFlag = true
                    }
                })
                .catch()
                })
            .catch()
        }

        const makeWallet = () =>{
            //지갑 만들기 관련 api
            //지갑 만들고 주소 받은 다음 api로 db 저장

            let walletAddress = "1234"
            store.dispatch('root/saveWallet', {jwt:store.getters['root/getAuthToken'], walletAddress: walletAddress})
            .then((result)=>{
                init()
            })
            .catch()
        }

        init()
        return {state, makeWallet}
    }
};
</script>

<style scoped>
</style>