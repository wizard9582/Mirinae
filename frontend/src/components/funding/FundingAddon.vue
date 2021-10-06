<template>
    <div class="fixed opacity-80 right-40 bottom-36 h-20 w-40 bg-gray-700 text-myfont rounded-full text-center cursor-pointer" @click="joinFunding">
        <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
            펀딩 참여하기
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
            amount : 0,
        })

        const joinFunding = () =>{
            let fundingId = router.currentRoute.value.params.id
            if(store.getters['root/isLoggedIn']){
                store.dispatch('root/joinFunding', {jwt:store.getters['root/getAuthToken'], fundingId:fundingId, amount: state.amount})
                .then((result)=>{

                })
                .catch()
            }else{
                //로그인 화면으로 이동
                const params = {
                    redirectUri: "https://j5a506.p.ssafy.io/oauth/kakao",
                };
                window.Kakao.Auth.authorize(params);
            }
        }

        return {state, joinFunding}
    }
};
</script>

<style scoped>
</style>