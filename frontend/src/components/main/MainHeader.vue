<template>
    <div class="m-0 pl-0 w-full h-40 bg-gray-500">
        <div class="w-full flex justify-between">
            <div class="m-4 w-8 h-8 cursor-pointer" @click="clickHome">
                <img class="h-6" src="../../assets/svg/home.svg" alt="home">
            </div>
            <div>
                <p class="text-3xl text-gray-700">미리내</p>
            </div>
            <div v-if="state.isLoggedIn" class="m-4 w-12 h-12 rounded-full bg-white cursor-pointer text-center" @click="clickUser">
                <img :src="state.userprofile" alt="..." class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                <p class="text-sm text-gray-700">{{state.userName}}</p>
            </div>
            <div v-else class="m-4 w-12 h-12 rounded-full bg-white cursor-pointer text-center" @click="clickLogin">
                <img src="../../assets/svg/login.svg" alt="..." class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                <p class="text-sm text-gray-700">로그인</p>
            </div>
        </div>
        <div class="ml-10 flex items-end">
            <div @click="clickFundingList">
                <p class="text-sm text-gray-700 cursor-pointer">펀딩리스트</p>
            </div>
            <div @click="clickFundingOpen">
                <p class="ml-10 text-sm text-gray-700 cursor-pointer">펀딩열기</p>
            </div>
            <div>
                <a href="https://localhost:8083/main/tx/id" class="ml-10 text-sm text-gray-700 cursor-pointer">트랜잭션 조회 페이지(dev)</a>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
export default {
    name: 'MainHeader',
    components: {
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            loginPop: false,
            isLoggedIn: false,
            userMail: "",
            userName: "",
            userProfile: "",
            userWallet: "",
            userBalance: "",
        })
        const init = ()=>{
            if(store.getters['root/isLoggedIn']){
                store.dispatch('root/getUserInfo', store.getters['root/getAuthToken'])
                .then((result)=>{
                    //console.log(result)
                    state.isLoggedIn = true
                })
                .catch()
            }
        }
        const clickLogin = ()=>{
            state.loginPop = true
        }
        const clickHome = ()=>{
            router.push('/main/all')
        }
        const clickUser = ()=>{
            router.push('/main/user/id')
        }
        const clickFundingList = ()=>{
            router.push('/main/all')
        }
        const clickFundingOpen = ()=>{
            router.push('/main/fund/create')
        }
        init()
        return {state, clickHome, clickUser, clickLogin, clickFundingList, clickFundingOpen}
    }
};
</script>

<style scoped>
</style>