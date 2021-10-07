<template>
<div class="w-full xl:w-4/12 px-4">
    <div class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-lg rounded">
        <div class="rounded-t mb-0 px-4 py-3 border-0">
            <div class="flex flex-wrap items-center">
                <div class="relative w-full px-4 max-w-full flex-grow flex-1">
                <h3 class="font-semibold text-base text-blueGray-700">
                    {{rankingTitle}}
                </h3>
                </div>
            </div>
        </div>
        <div class="block w-full overflow-x-auto">
            <table class="items-center w-full bg-transparent border-collapse">
                <thead class="thead-light">
                    <tr>
                        <th class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left">
                        순위
                        </th>
                        <th class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left">
                        
                        </th>
                        <th class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left">
                        
                        </th>
                        <th
                        class="px-6 bg-blueGray-50 text-blueGray-500 align-middle border border-solid border-blueGray-100 py-3 text-xs uppercase border-l-0 border-r-0 whitespace-nowrap font-semibold text-left" style="min-width:140px">
                        참여금액
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="ranking in rankingData" :key="ranking.id">
                        <th class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
                        {{ranking.id}}
                        </th>
                        <td class="border-t-0 pl-2 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
                            <img :src="ranking.userThumbnail" alt="..." class="shadow rounded-full max-w-full h-auto align-middle border-none" />
                        </td>
                        <td class="border-t-0 pr-2 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4 text-left">
                        {{ranking.name}}
                        </td>
                        <td class="border-t-0 px-6 align-middle border-l-0 border-r-0 text-xs whitespace-nowrap p-4">
                            <div class="flex items-center">
                                <span class="mr-2">{{ranking.amount}}</span>
                                <div class="relative w-full">
                                    <div class="overflow-hidden h-2 text-xs flex rounded bg-red-200">
                                        <div :style="{width:`${100 - ((ranking.id-1)*10)}%`}" class="shadow-none flex flex-col text-center whitespace-nowrap text-white justify-center bg-red-500"></div>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</template>
<script>
import { reactive } from 'vue'
export default {
    name: 'Ranking',
    components: {
    },
    props:{
        rankingData:[],
        rankingTitle:"",
    },
    setup(){
        const state = reactive({

        })
        return {state}
    }
};
</script>

<style scoped>
</style>