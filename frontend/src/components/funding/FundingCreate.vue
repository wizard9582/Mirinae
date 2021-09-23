<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white shadow-md">
            <div class="m-8 divide-y divide-black">
                <div>
                    펀딩 열기
                </div>
                <div>
                    <div class="flex justify-between">
                        <div class="w-1/2 h-lg m-8 p-4 border-4 border-black">
                            이미지 업로드(드래그앤 드롭)<br/>
                            파일찾기
                        </div>
                        <div class="w-1/2 mt-6">
                            <div class="w-full p-4 flex justify-between">
                                <p>펀딩 타이틀 :</p>
                                <input type="text" class="rounded w-3/4">
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>카테고리 :</p>
                                <select class="rounded w-3/4 multiple"></select>
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>목표금액 :</p>
                                <input type="number" class="rounded w-3/4">
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>종료날짜 :</p>
                                <input type="date" class="rounded w-3/4">
                            </div>
                        </div>
                    </div>
                    <div class="w-full mt-6 flex">
                        <div>설명글 작성</div>
                        <input type="text" class="rounded w-3/4 h-lg m-8">
                    </div>
                </div>
                <div class="flex justify-end">
                    <button class="">작성</button>
                    <button class="">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
//import 123 from '@/'
export default {
    name: 'FundingCreate',
    components: {
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({

        })

        return {state}
    }
};
</script>

<style scoped>
</style>