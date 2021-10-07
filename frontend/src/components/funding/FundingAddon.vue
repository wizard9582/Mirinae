<template>
    <div class="md:fixed md:right-20 md:bottom-20 pb-4 bg-gray-700 text-myfont opacity-80 md:rounded-full text-center">
        <label for="amount">금액:</label>
        <input id="amount" type="number" class="rounded w-3/5 m-3" v-model="state.amount" @change="checkClick">
        <button class="bg-gray-200 hover:bg-gray-500 cursor-wait text-white font-bold py-2 px-4 rounded" :class="{'bg-blue-500':state.clickable, 'hover:bg-blue-700':state.clickable}" @click="joinFunding">
            {{state.ment}}
        </button>
    </div>
    <div v-if="state.popupOpen" class="md:fixed inline-block align-bottom bg-white text-center rounded-lg overflow-hidden sm:my-8 sm:align-middle sm:max-w-lg sm:w-full md:left-1/3 md:top-1/3 border-2 border-black">
        <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
            <div class="sm:flex sm:items-start">
                <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                    <h3 class="text-lg leading-6 text-black font-bold">
                        주의! 개인 키를 입력하면 송금은 되돌릴 수 없습니다.
                    </h3>
                    <div class="mt-2">
                    </div>
                </div>
            </div>
        </div>
        <label for="pk">개인키 : </label>
        <input id="pk" type="text" v-model="state.privateKey" class="rounded-md w-3/4 my-4">
        <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
            <button type="button" class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-red-600 text-base font-medium text-white hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 sm:ml-3 sm:w-auto sm:text-sm" @click="sendTx">
                참여하기
            </button>
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
        const state = reactive({
            popupOpen: false,
            clickable : false,
            amount : 0,
            ment : "후원금을 입력!",
            privateKey: ""
        })

        const joinFunding = () =>{
            let fundingId = router.currentRoute.value.params.id
            if(store.getters['root/isLoggedIn'] && state.clickable){
                state.popupOpen = true
            }else{
                alert("로그인이 필요합니다!")
                //로그인 화면으로 이동
                const params = {
                    redirectUri: "https://j5a506.p.ssafy.io/oauth/kakao",
                };
                window.Kakao.Auth.authorize(params);
            }
        }
        const checkClick = () =>{
            if(state.amount > 0){
                state.clickable = true
                state.ment = "펀딩 참여하기"
            }else{
                state.clickable = false
                state.ment = "후원금을 입력!"
            }
        }
        const sendTx = ()=>{
            let fundingId = router.currentRoute.value.params.id
            state.popupOpen = false
            store.dispatch('root/joinFunding', {jwt:store.getters['root/getAuthToken'], fundingId:fundingId, amount: state.amount, key:state.privateKey})
            .then((result)=>{
                state.privateKey = ""
            })
            .catch()
        }

        return {state, joinFunding, checkClick, sendTx}
    }
};
</script>

<style scoped>
</style>