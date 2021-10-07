<template>
    <div class="m-0 pl-0 w-full h-40 bg-gray-500">
        <div class="w-full flex justify-between">
            <div class="m-4 w-8 h-8 cursor-pointer" @click="clickHome">
                <img class="h-6" src="../../assets/svg/home.svg" alt="home">
            </div>
            <div>
                <p class="text-3xl text-gray-700">미리내</p>
            </div>
            <div v-if="state.isLoggedIn" class="m-4 w-12 h-12 rounded-full bg-white cursor-pointer text-center" @click="openUser">
                <img :src="state.userProfile" alt="..." class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                <p class="text-sm text-gray-700">{{state.userName}}</p>
            </div>
            <div v-else class="m-4 w-12 h-12 rounded-full bg-white cursor-pointer text-center" @click="clickLogin">
                <img src="../../assets/svg/login.svg" alt="..." class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                <p class="text-sm text-gray-700">로그인</p>
            </div>
        </div>
        <div class="ml-10 flex items-end font-bold sm:text-xs md:text-lg text-light-gray">
            <div @click="clickFundingList">
                <p class="cursor-pointer">펀딩리스트</p>
            </div>
            <div @click="clickFundingOpen">
                <p class="ml-10 cursor-pointer">펀딩열기</p>
            </div>
            <div v-if="false">
                <a href="https://localhost:8083/main/tx/id" class="ml-10 cursor-pointer">트랜잭션 조회 페이지(dev)</a>
            </div>
        </div>
        <div v-if="state.userPop" class="absolute right-4 w-48 h-32 text-right bg-transparent bg-gray-100 border-collapse shadow-lg rounded divide-y divide-gray-300">
            <div class="h-1/3 pr-4" @click="goUser">
                내 정보
            </div>
            <div class="h-1/3 pr-4" @click="goLogout">
                로그아웃
            </div>
            <div class="h-1/3 pr-4" @click="closeUser">
                x
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
            userPop: false,
            loginPop: false,
            isLoggedIn: false,
            userMail: "",
            userName: "",
            userProfile: "",
            userWallet: "",
            userBalance: "",
        })
        const init = ()=>{
            // console.log('login check --->', store.getters['root/isLoggedIn'])
            // console.log('token check --->', store.getters['root/getAuthToken'])
            let jwt = localStorage.getItem('jwt')
            if(jwt){
                store.commit('root/setToken', jwt)
            }
            if(store.getters['root/isLoggedIn']){
                store.dispatch('root/getUserInfo', {jwt: store.getters['root/getAuthToken']})
                .then((result)=>{
                    // console.log(result)
                    state.isLoggedIn = true
                    if(result.data.image === null){
                        setProfile()
                    }
                    state.userName = result.data.nickname
                    state.userProfile = result.data.image
                })
                .catch()
            }
        }
        const setProfile = ()=>{
                let access_token = localStorage.getItem('kakao_access')
                store.dispatch('root/getKakaoInfo', { access_token: access_token })
                .then((result)=>{
                    // console.log('------> kakao return data')
                    // console.log(result)
                    let nickname = result.data.kakao_account.profile.nickname
                    let image = result.data.kakao_account.profile.thumbnail_image_url
                    state.userProfile = result.data.kakao_account.profile.thumbnail_image_url
                    store.dispatch('root/updateUser', { jwt: store.getters['root/getAuthToken'], image:image, nickname:nickname })
                    .then((result)=>{

                    })
                    .catch()
                })
                .catch()
        }
        const clickLogin = ()=>{
            const params = {
                redirectUri: "https://j5a506.p.ssafy.io/oauth/kakao",
            };
            window.Kakao.Auth.authorize(params);
        }
        const clickHome = ()=>{
            router.push('/main/all/1')
        }
        const openUser = ()=>{
            state.userPop = true
        }
        const closeUser = ()=>{
            state.userPop = false
        }
        const clickFundingList = ()=>{
            router.push('/main/all/1')
        }
        const clickFundingOpen = ()=>{
            router.push('/main/fund/create')
        }
        const goUser = ()=>{
            state.userPop = false
            let access_token = localStorage.getItem('kakao_access')
            store.dispatch('root/getKakaoInfo', { access_token: access_token })
            .then((result)=>{
                // console.log('------> kakao return data')
                // console.log(result)
                let email = result.data.kakao_account.email
                store.commit('root/setUserId', email)
                router.push('/main/user/' + store.getters['root/getUserId'])
            })
            .catch()
        }
        const goLogout = ()=>{
            state.userPop = false
            store.commit('root/logout')
            localStorage.removeItem('jwt')
            router.push("/main/all/1")
        }
        init()
        return {state, clickHome, openUser, closeUser, clickLogin, clickFundingList, clickFundingOpen , goUser, goLogout}
    }
};
</script>

<style scoped>
</style>