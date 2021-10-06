<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white shadow-md">
            <div class="m-8 divide-y divide-black">
                <div>
                    펀딩 열기
                </div>
                <div>
                    <div class="w-1/2 h-lg mx-auto my-8 border-4 border-black">
                        <img v-if="!state.imageFlag" class="mx-auto mt-auto w-16 h-16" src="../../assets/svg/image.svg" alt="instagram">
                        <img v-if="state.imageFlag" class="mx-auto mt-auto w-16 h-16" :src="state.imageFile" alt="instagram">
                        <div class="text-center">
                            설명이미지 업로드(드래그앤 드롭)<br/>
                            <label for="imageInput" class="border-4 rounded-t-md cursor-pointer">파일찾기</label>
                        </div>
                        <input ref="imageRoot" id="imageInput" type="file" name="image" accept="image/*" class="hidden" @input="uploadImage">
                    </div>
                    <div class="flex justify-between">
                        <div class="w-1/4 h-lg mx-auto p-8 border-4 border-black">
                            <img v-if="!state.thumbFlag" class="mx-auto mt-auto w-16 h-16" src="../../assets/svg/image.svg" alt="instagram">
                            <img v-if="state.thumbFlag" class="mx-auto mt-auto w-16 h-16" :src="state.thumbFile" alt="instagram">
                            <div class="text-center">
                                썸네일 업로드(드래그앤 드롭)<br/>
                                <label for="thumbInput" class="border-4 rounded-t-md cursor-pointer">파일찾기</label>
                            </div>
                            <input ref="thumbRoot" id="thumbInput" type="file" name="image" accept="image/*" class="hidden" @input="uploadThumb">
                        </div>
                        <div class="w-1/2 mt-6">
                            <div class="w-full p-4 flex justify-between">
                                <p>펀딩 타이틀 :</p>
                                <input type="text" class="rounded w-3/4" v-model="state.title">
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>카테고리 :</p>
                                <select class="rounded w-3/4 multiple" v-model="state.categoryName"></select>
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>목표금액 :</p>
                                <input type="number" class="rounded w-3/4" v-model="state.goal">
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>시작날짜 :</p>
                                <input type="date" class="rounded w-3/4" v-model="state.startDatatime">
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>종료날짜 :</p>
                                <input type="date" class="rounded w-3/4" v-model="state.endDatetime">
                            </div>
                        </div>
                    </div>
                    <div class="w-full mt-6 flex">
                        <div>설명글 작성</div>
                        <input type="text" class="rounded w-3/4 h-lg m-8" v-model="state.content">
                    </div>
                </div>
                <div class="flex justify-end">
                    <button class="" @click = "submit">작성</button>
                    <button class="" @click = "cancel">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import axios from 'axios'
//import 123 from '@/'
export default {
    name: 'FundingCreate',
    components: {
    },

    setup(){
        const store = useStore()
        const router = useRouter()
        const state = reactive({
            imageFile: "https://ifh.cc/g/2zLHyH.jpg",
            imageFlag: false,
            thumbFile: "https://ifh.cc/g/2zLHyH.jpg",
            thumbFlag: false,
            title : "",
            categoryName : "",
            content : "",
            goal : "",
            thumbnail : "",
            image : "",
            startDatatime: "",
            endDatetime: "",
        })
        const submit = ()=>{
            //validation() 체크 후

            let payload = {
                'title' : state.title,
                'categoryName' : state.categoryName,
                'content' : state.content,
                'goal' : state.goal,
                'thumbnail' : state.thumbnailFile,
                'image' : state.imageFile,
                'startDatatime': state.startDatatime,
                'endDatetime': state.endDatetime,
            }
            store.dispatch('root/createFunding', payload)
            .then((result)=>{
                console.log(result)
                //state.uploadImage = result에서 url 찾아서 대입
            })
            .catch()
        }
        return { state, store, submit }
    },
    methods:{
        uploadImage(){
            // let store = useStore()
            // let file = this.$refs.imageRoot.files[0]
            // let formdata = new FormData()
            // formdata.append('image',file)
            // store.dispatch('root/uploadProfileImg', {usage:'fundingInfo', formdata: formdata})
            // .then((result)=>{
            //     console.log("----->image")
            //     console.log(result)
            //     //state.uploadImage = result에서 url 찾아서 대입
            // })
            // .catch()

            let file = this.$refs.imageRoot.files[0]
            let formdata = new FormData()
            formdata.append('file', file)
            console.log(file)
            console.log(formdata)
            axios({
                method: 'post',
                url: 'https://j5a506.p.ssafy.io/api/upload/fundingInfo',
                headers:{
                    'Content-Type': 'multipart/form-data'
                },
                data:formdata
            })
            .then((result)=>{
                console.log("----->image")
                console.log(result)
                //state.uploadImage = result에서 url 찾아서 대입
            })
            .catch()
        },
        uploadThumb(){
            // let store = useStore()
            // let file = this.$refs.imageRoot.files[0]
            // let formdata = new FormData()
            // formdata.append('image',file)
            // store.dispatch('root/uploadProfileImg', {usage:'fundingThumbnail', formdata: formdata})
            // .then((result)=>{
            //     console.log("----->thumb")
            //     console.log(result)
            //     //state.uploadThumb = result에서 url 찾아서 대입
            // })
            // .catch()

            let file = this.$refs.imageRoot.files[0]
            let formdata = new FormData()
            formdata.append('file',file)
            axios({
                method: 'post',
                url: 'https://j5a506.p.ssafy.io/api/upload/fundingThumbnail',
                headers:{
                    'Content-Type': 'multipart/form-data'
                },
                data:formdata
            })
            .then((result)=>{
                console.log("----->image")
                console.log(result)
                //state.uploadImage = result에서 url 찾아서 대입
            })
            .catch()
        }
    }
};
</script>

<style scoped>
</style>