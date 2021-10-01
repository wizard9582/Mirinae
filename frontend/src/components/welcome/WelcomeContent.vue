<template>
    <div class="w-full h-2xl">
        <img class="absolute right-0 w-md h-md m-3" src="../../assets/logo.gif" alt="logo">
        <div class="absolute top-1/4 left-1/4 text-myfont">
            <p>블록체인 기반 크라우드 펀딩 플랫폼<br/></p>
            <p>미리내</p>
        </div>
        <div class="absolute top-1/4 left-2/3 text-center align-middle w-1/4 h-xl bg-white rounded-3xl">
            <div class="mt-20 mb-5 text-dark bold">
                <p class="mb-5 font-bold text-5xl">미리내</p><br/>
                <p class="font-bold text-xl text-gray">간편하게 로그인하고</p>
                <p class="font-bold text-2xl">다양한 서비스를 이용하세요.</p><br/>
            </div>
            <div class="flex items-center ml-auto mr-auto mb-20 w-1/2 h-sm bg-yellow-300 rounded-full cursor-pointer" @click="clickLogin('kakao')">
                <img class="ml-auto w-8 h-8" src="../../assets/svg/kakao.svg" alt="kakao">
                <p class="ml-2 mr-auto font-bold">카카오톡으로 시작</p>
            </div>
            <p class="text-sm text-gray mb-3">다른 방법으로 시작하기</p>
            <div class="flex items-center mb-10">
                <div class="flex items-center bg-gray-200 w-sm h-sm ml-auto mr-3 rounded-3xl cursor-not-allowed" @click="clickLogin('naver')">
                    <svg class="fill-current text-green ml-auto mr-auto w-6 h-6" enable-background="new 0 0 512 512" height="512" viewBox="0 0 512 512" width="512" xmlns="http://www.w3.org/2000/svg"><g id="N"><path d="m164.828 233.178 182.359 278.822h118.813v-512h-118.623v285.956l-185.215-285.956h-116.162v512h118.828z"/></g></svg>
                </div>
                <div class="flex items-center bg-gray-200 w-sm h-sm rounded-3xl cursor-not-allowed" @click="clickLogin('google')">
                    <img class="ml-auto mr-auto w-6 h-6" src="../../assets/svg/google.svg" alt="google">
                </div>
                <div class="flex items-center bg-gray-200 w-sm h-sm ml-3 mr-auto rounded-3xl cursor-not-allowed" @click="clickLogin('etc')">
                    <img class="ml-auto mr-auto w-6 h-6" src="../../assets/svg/instagram.svg" alt="instagram">
                </div>
            </div>
            <div class="text-right mr-5 cursor-pointer" @click="goHome">
                <p class="text-xs text-gray mb-5">로그인 하지 않고 둘러보기→</p>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'WelcomeContent',

    setup(props, {emit}){
        const store = useStore()
        const router = useRouter()
        const state = reactive({})

        const clickLogin = (where) =>{
            if(where === 'kakao'){
                emit('openKakaoLogin')
            }else{
                alert("개발중입니다!")
            }
            
            // else if(where === 'naver'){
            //     emit('openNaverLogin')
            // }else if(where === 'google'){
            //     emit('openGoogleLogin')
            // }else{
            //     emit('')
            // }
        }
        const goHome = () =>{
            router.push("/main/all/1")
        }
        return { clickLogin, goHome }
    }
};
</script>

<style scoped>
</style>