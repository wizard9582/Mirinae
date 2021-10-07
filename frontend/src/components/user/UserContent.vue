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
                    <div v-if="state.privateFlag">
                        개인 키는 최초 지갑 생성시에만 보여집니다. <br>
                        {{state.privateKey}}
                    </div>
                </div>
                <div class="w-full">
                    닉네임 : {{state.userName}} <br>
                    메일 : {{state.userMail}} <br>
                    ETH {{state.userBalance}}
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
        <button class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded" @click="deleteUser">탈퇴하기</button>
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
        const router = useRouter()
        const state = reactive({
            privateFlag: false,
            privateKey: "",
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
                        let fundingThumb = {id:0, title:"", imgSrc:"", imgAlt:"", goal:0, balance:0, state: ""}

                        fundingThumb.id = item.fundingId
                        fundingThumb.title = item.title
                        fundingThumb.imgSrc = item.thumbnail
                        fundingThumb.imgAlt = item.title
                        fundingThumb.goal = item.goal
                        fundingThumb.balance = item.balance
                        
                        let startDate = new Date(item.startDatetime[0]+"-"+item.startDatetime[1]+"-"+item.startDatetime[2])
                        let endDate = new Date(item.endDatetime[0]+"-"+item.endDatetime[1]+"-"+item.endDatetime[2])

                        if(today.getTime() < startDate.getTime()){
                            fundingThumb.state = "prepare"
                        }else if(today.getTime() > endDate.getTime()){
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
                        let fundingThumb = {id:0, title:"", imgSrc:"", imgAlt:"", goal:0, balance:0, state: ""}

                        fundingThumb.id = item.fundingId
                        fundingThumb.title = item.title
                        fundingThumb.imgSrc = item.thumbnail
                        fundingThumb.imgAlt = item.title
                        fundingThumb.goal = item.goal
                        fundingThumb.balance = item.balance
                        
                        let startDate = new Date(item.startDatetime[0]+"-"+item.startDatetime[1]+"-"+item.startDatetime[2])
                        let endDate = new Date(item.endDatetime[0]+"-"+item.endDatetime[1]+"-"+item.endDatetime[2])

                        if(today.getTime() < startDate.getTime()){
                            fundingThumb.state = "prepare"
                        }else if(today.getTime() > endDate.getTime()){
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
            const Web3 = require('web3');
            const ENDPOINT = 'http://j5a5061.p.ssafy.io:8000';
            const web3 = new Web3(new Web3.providers.HttpProvider(ENDPOINT));

            alert("지갑이 개설되고 출력되는 개인 키를 꼭 저장해 주세요. 개인 키 분실시 다시 확인 할 수 없습니다!")

            var account =  web3.eth.accounts.create();
            // console.log('account: ', account);
            console.log('-----아이디 생성-----');
            console.log('account.address: ', account.address);
            console.log('account.privKey: ', account.privateKey);

            state.privateFlag = true
            state.privateKey = account.privateKey

            // // ---------------------root 계정에서 보내기 ------------------
            // web3.eth.defaultAccount = '0x1d34ac7ad89f33ebc663d6ed6234cf9e80db5f7d';
            // // console.log("F",web3.eth.defaultAccount);
            // web3.eth.personal.unlockAccount(web3.eth.defaultAccount,"test");
            // web3.eth.sendTransaction({
            //     from: web3.eth.defaultAccount,
            //     Flag: account.address,
            //     Keyue: web3.utils.toWei('100','ether')
            // });

            let walletAddress = account.address
            store.dispatch('root/saveWallet', {jwt:store.getters['root/getAuthToken'], walletAddress: walletAddress})
            .then((result)=>{
                console.log("----->wallet", result)
                state.walletFlag = true
                state.walletAddress = walletAddress
            })
            .catch()
        }
        const deleteUser = ()=>{
            store.dispatch('root/deleteUser', {jwt:store.getters['root/getAuthToken']})
            .then((result)=>{
                router.push('/main/all/1')
            })
            .catch()
        }
        init()
        return {state, makeWallet, deleteUser}
    }
};
</script>

<style scoped>
</style>