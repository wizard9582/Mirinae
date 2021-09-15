<template>
    <div class="w-full h-2xl">
        <img class="absolute right-0 w-md h-md m-3" src="../../assets/logo.gif" alt="logo">
        <div class="absolute top-1/4 left-1/4 text-myfont">
            <p>
                블록체인 기반 기부 펀딩 플랫폼<br/>
            </p>
            <p>
                미리내
            </p>
        </div>
        <div class="absolute top-1/4 left-2/3 text-center align-middle w-1/4 h-xl bg-white rounded-3xl">
            <div class="mt-20 mb-5 text-dark bold">
                <p class="mb-5 font-bold text-5xl">미리내</p><br/>
                <p class="font-bold text-xl text-gray">간편하게 로그인하고</p>
                <p class="font-bold text-2xl">다양한 서비스를 이용하세요.</p><br/>
            </div>
            <div class="flex items-center ml-20 mb-20 w-lg h-sm bg-yellow-300 rounded-full" @click="clickLogin('kakao')">
                <div class="ml-auto">kakao</div>
                <div class="ml-2 mr-auto">카카오톡으로 시작</div>
            </div>
            <p class="text-sm text-gray mb-5">다른 방법으로 시작하기</p>
            <div class="flex items-center">
                <div class="flex items-center bg-gray-300 w-sm h-sm ml-auto mr-3" @click="clickLogin('naver')">네이버</div>
                <div class="flex items-center bg-gray-300 w-sm h-sm" @click="clickLogin('google')">구글</div>
                <div class="flex items-center bg-gray-300 w-sm h-sm ml-3 mr-auto" @click="clickLogin('etc')">?</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'MainWelcome',

    setup(props, {emit}){

        const clickLogin = (where) =>{
            if(where === 'kakao'){
                emit('openKakaoLogin')
            }else if(where === 'naver'){
                emit('openNaverLogin')
            }else if(where === 'google'){
                emit('openGoogleLogin')
            }else{
                emit('')
            }
        }
        return { clickLogin }
    }
};
</script>

<style scoped>
</style>