<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white flex justify-between">
            <div v-for="category in state.categories" :key="category.id" class="group relative mx-auto">
                <div class="rounded-full bg-white cursor-pointer text-center" @click="clickCategory(category.category_id)">
                    <img :src="state.imgSrc[category.category_id]" :alt="category.category_name" class="shadow rounded-full w-16 h-16 align-middle border-none" />
                    <p class="text-sm text-gray-700">{{category.category_name}}</p>
                </div>
            </div>
        </div>
        <div class="max-w-2xl mt-1 mx-auto py-4 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-t-0 border-4 border-black bg-white divide-y divide-black">
            <h2 class="text-2xl font-extrabold tracking-tight text-gray-900">{{state.category}}</h2>
            <div class="mt-6 grid grid-cols-1 gap-y-10 gap-x-6 pt-3  sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8">
                <funding-thumbnail v-for="funding in state.fundings" :key="funding.id" @click="clickFunding(funding.id)"/>
            </div>
            <div class="bg-white mt-10 px-4 pt-6 pb-3 flex items-center justify-between border-t border-gray-200 sm:px-6">
                <div class="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
                    <div>
                        <p class="text-sm text-gray-700">
                            {{state.page}} of {{state.index}} pages
                        </p>
                    </div>
                <div>
                    <nav class="relative z-0 inline-flex rounded-md shadow-sm -space-x-px" aria-label="Pagination">
                    <div class="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50" @click="clickDir(-1)">
                        &lt;
                    </div>
                    <!-- Current: "z-10 bg-indigo-50 border-indigo-500 text-indigo-600", Default: "bg-white border-gray-300 text-gray-500 hover:bg-gray-50" -->
                    <div v-for="page in state.pages" :key="page.id" class="border-gray-300 text-gray-500 hover:bg-gray-50 relative inline-flex items-center px-4 py-2 border text-sm font-medium" @click="clickDir(page)">
                        <div v-if="page == state.page" class="font-bold"> {{page}}</div>
                        <div v-else> {{page}}</div>
                    </div>
                    <div class="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50" @click="clickDir(0)">
                        &gt;
                    </div>
                    </nav>
                </div>
                </div>
            </div>
        </div>
        <div class="max-w-2xl mx-auto mt-4 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white md:flex md:justify-between">
            <ranking :rankingData = "state.rankingData1" :rankingTitle = "state.rankingTitle1"/>
            <ranking :rankingData = "state.rankingData2" :rankingTitle = "state.rankingTitle2"/>
            <ranking :rankingData = "state.rankingData3" :rankingTitle = "state.rankingTitle3"/>
        </div>
    </div>
</template>

<script>
import Ranking from '@/components/ranking/Ranking.vue'
import FundingThumbnail from '@/components/funding/FundingThumbnail.vue'
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
    name: 'MainContent',
    components:{
        Ranking,
        FundingThumbnail,
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            imgSrc:["","https://ifh.cc/g/Mq7685.jpg","https://ifh.cc/g/m035va.png","https://ifh.cc/g/IPp9oQ.png","https://ifh.cc/g/SoeRsG.jpg",],
            category: "카테고리명",
            size: 10,
            page: 1,
            index: 1,
            categories:[
            ],
            fundings: [
            ],
            pages:[

            ],
            rankingData1:[],
            rankingData2:[],
            rankingData3:[],
            rankingTitle1: "연예인 팬덤 랭킹",
            rankingTitle2: "재난피해 돕기 랭킹",
            rankingTitle3: "불우이웃 돕기 랭킹",
        })

        const init = () => {
            let today = new Date();

            let categoryId = router.currentRoute.value.params.id
            state.page = router.currentRoute.value.params.page
            
            let i = Math.max(parseInt(state.page - 2), 1)
            let k = Math.min(parseInt(state.page) + 2, state.index)

            for(var j = i; j <= k; j++){
                state.pages.push(j)
            }

            store.dispatch('root/getCategoryList')
            .then((result)=>{
                //console.log("category data----->")
                //console.log(result)
                state.categories = result.data
                result.data.forEach(item=>{
                    if(item.category_id == categoryId){
                        state.category = item.category_name
                    }
                })
            })
            .catch()

            store.dispatch('root/getFundingList', { category: state.category, size: state.size, page: state.page })
            .then((result)=>{
                // console.log("fundingList data----->")
                // console.log(result)

                //funding state : prepare, open, finished
                result.data.forEach(item => {
                    let fundingThumb = {id:0, title:"", imgSrc:"", imgAlt:"", goal:0, balance:0, state: ""}

                    fundingThumb.id = item.id
                    fundingThumb.title = item.title
                    fundingThumb.imgSrc = item.thumbnail
                    fundingThumb.imgAlt = item.title
                    fundingThumb.goal = item.goal
                    fundingThumb.balance = item.balance
                    
                    let startDate = item.startDatetime
                    let endDate = item.endDatetime

                    if(today.getTime() < startDate.getTime()){
                        fundingThumb.state = "prepare"
                    }else if(todat.getTime() > endDate.getTime()){
                        fundingThumb.state = "end"
                    }else{
                        fundingThumb.state = "open"
                    }

                    state.fundings.push(fundingThumb)
                })
            })
            .catch()

            store.dispatch('root/getCategoryRanking', {categoryId: "1"})
            .then((result)=>{
                //console.log("category Ranking1 data----->")
                //console.log(result)
                state.rankingTitle1 = "테스트 1"
                let no = 1
                result.data.forEach(item => {
                    let ranking = {id:no++, userThumbnail:"", name:"", amount:0}

                    ranking.userThumbnail = item.userThumbnail
                    ranking.name = item.userNickname
                    ranking.amount = item.amount

                    state.rankingData1.push(ranking)
                })
            })
            .catch()

            store.dispatch('root/getCategoryRanking', {categoryId: "2"})
            .then((result)=>{
                //console.log("category Ranking2 data----->")
                //console.log(result)
                state.rankingTitle2 = "테스트 2"
                let no = 1
                result.data.forEach(item => {
                    let ranking = {id:no++, userThumbnail:"", name:"", amount:0}

                    ranking.userThumbnail = item.userThumbnail
                    ranking.name = item.userNickname
                    ranking.amount = item.amount

                    state.rankingData2.push(ranking)
                })
            })
            .catch()

            store.dispatch('root/getCategoryRanking', {categoryId: "3"})
            .then((result)=>{
                // console.log("category Ranking3 data----->")
                // console.log(result)
                state.rankingTitle3 = "테스트 3"
                let no = 1
                result.data.forEach(item => {
                    let ranking = {id:no++, userThumbnail:"", name:"", amount:0}

                    ranking.userThumbnail = item.userThumbnail
                    ranking.name = item.userNickname
                    ranking.amount = item.amount

                    state.rankingData3.push(ranking)
                })
            })
            .catch()
        }

        const clickFunding = (fundingId) =>{
            router.push("/main/fund/" + fundingId)
        }

        const clickCategory = (categoryName) =>{
            router.push("/main/" + categoryName + "/1")
        }

        const clickDir = (dir) =>{
            if(dir == -1){
                if(state.page != 1){
                    router.push("/main/" + state.category + "/" + parseInt(state.page-1))
                }
            }else if(dir == 0){
                if(state.page < state.index){
                    state.page++
                    router.push("/main/" + state.category + "/" + state.page)
                }
            }else{
                router.push("/main/" + state.category + "/" + dir)
            }
        }

        init()
        return { state, clickFunding, clickCategory, clickDir}
    }
};
</script>

<style scoped>
</style>