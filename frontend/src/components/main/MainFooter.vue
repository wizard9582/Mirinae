<template>
    <div class="md:flex md:justify-between p-8 w-full md:h-lg bg-dark text-myfont">
        <div clsss="text-left whitespace-nowrap">       
            <div class="ml-6 mt-6 text-xl font-semibold">
                {{state.time}}까지<br/>
                <br/>
                {{state.totalFunding}}개의 펀딩에 {{state.donator}}명의 후원자께서<br/>
                {{state.amount}} ETC를 후원하셨습니다.<br/>
                <br/>
                지금 {{state.nowFunding}}개의 펀딩이 진행중입니다.<br>
                <br/>
            </div>
        </div>
        <div clsss="text-right whitespace-nowrap">
            <div class="text-md font-semibold">
                <p class="text-right">
                    <br/>
                    <br/>
                    <br/>
                    SSAFY 5기 블록체인 특화 프로젝트<br/>
                    <br/>
                    서울 5반 6팀 미리내<br/>
                    <br/>
                    Copyright 2021<br/>
                    <a href="https://j5a506.p.ssafy.io/main/admin">관리자페이지</a>
                </p>
            </div>
            <div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'MainFooter',
    components: {
    },

    setup(){
        const state = reactive({
            time: "2021.00.00",
            totalFunding: 0,
            donator: 0,
            amount: 0,
            nowFunding: 0,
        })

        const setValue = () => {
            let today = new Date()
            state.time = today.getFullYear() + "." + parseInt(today.getMonth()+1) + "." + today.getDate() + " " + today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds()
        }

        setValue()
        return { state }
    }
};
</script>

<style scoped>
</style>