<template>
    <div class="m-0 pl-0 w-full h-40 bg-gray-500">
        <div class="w-full flex justify-between">
            <div class="m-4 w-sm h-sm cursor-pointer" @click="clickHome">
                <img class="h-8" src="../../assets/svg/home.svg" alt="home">
            </div>
            <div>
                <p class="text-3xl text-gray-700">미리내</p>
            </div>
            <div class="m-4 w-12 h-12 rounded-full bg-white cursor-pointer text-center" @click="clickUser">
                <img src="https://www.creative-tim.com/learning-lab/tailwind-starter-kit/img/team-2-800x800.jpg" alt="..." class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                <p class="text-sm text-gray-700">{{state.userName}}</p>
            </div>
        </div>
        <div class="ml-10 flex items-end">
            <div @click="clickFundingList">
                <p class="text-sm text-gray-700">펀딩리스트</p>
            </div>
            <div @click="clickFundingOpen">
                <p class="ml-10 text-sm text-gray-700">펀딩열기</p>
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
        const state = reactive({
            userName: "이름"
        })
        const clickHome = ()=>{
            router.push('/main/all')
        }
        const clickUser = ()=>{
            router.push('/user')
        }
        const clickFundingList = ()=>{
            router.push('/main/all')
        }
        const clickFundingOpen = ()=>{
            router.push('/funding/open')
        }
        return {state, clickHome, clickUser, clickFundingList, clickFundingOpen}
    }
};
</script>

<style scoped>
</style>