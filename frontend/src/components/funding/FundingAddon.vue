<template>
    <div class="md:fixed md:right-20 md:bottom-20 pb-4 bg-gray-700 text-myfont opacity-80 md:rounded-full text-center">
        <label for="amount">금액:</label>
        <input id="amount" type="number" class="rounded w-3/5 m-3" v-model="state.amount" @change="checkClick">
        <button class="bg-gray-200 hover:bg-gray-500 cursor-wait text-white font-bold py-2 px-4 rounded" :class="{'bg-blue-500':state.clickable, 'hover:bg-blue-700':state.clickable}" @click="joinFunding">
            {{state.ment}}
        </button>
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
            clickable : false,
            amount : 0,
            ment : "후원금을 입력!"
        })

        const joinFunding = () =>{
            let fundingId = router.currentRoute.value.params.id
            if(store.getters['root/isLoggedIn'] && state.amount > 0){
                store.dispatch('root/joinFunding', {jwt:store.getters['root/getAuthToken'], fundingId:fundingId, amount: state.amount})
                .then((result)=>{

                })
                .catch()
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

        return {state, joinFunding, checkClick}
    }
};
</script>

<style scoped>
</style>