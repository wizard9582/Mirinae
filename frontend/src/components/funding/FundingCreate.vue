<template>
    <div class="w-full pt-10 pb-20 bg-main-200">
        <div class="max-w-2xl mx-auto mb-0 py-8 px-4 sm:py-12 sm:px-6 lg:max-w-7xl lg:px-8 border-4 border-black bg-white shadow-md">
            <div class="m-8 divide-y divide-black">
                <div>
                    펀딩 열기
                </div>
                <div>
                    <div class="sm:w-full md:w-1/2 h-lg mx-auto my-8 border-4 border-black text-center">
                        <img v-if="!imageFlag" class="mx-auto mt-auto w-16 h-16" src="../../assets/svg/image.svg" alt="image">
                        <img v-if="imageFlag" class="mx-auto mt-auto w-16 h-16" :src="imageFile" alt="image">
                        설명이미지 업로드<br/>
                        <label for="imageInput" class="bg-gray-200 hover:bg-gray-500 text-white font-bold py-2 px-4 rounded cursor-pointer">파일찾기</label>
                        <input ref="imageRoot" id="imageInput" type="file" name="image" accept="image/*" class="hidden" @input="uploadImage">
                    </div>
                    <div class="md:flex md:justify-between text-center">
                        <div class="sm:w-full md:w-1/4 h-lg mx-auto p-8 border-4 border-black">
                            <img v-if="!thumbFlag" class="mx-auto mt-auto w-16 h-16" src="../../assets/svg/image.svg" alt="image">
                            <img v-if="thumbFlag" class="mx-auto mt-auto w-16 h-16" :src="thumbFile" alt="image">
                            썸네일 업로드<br/>
                            <label for="thumbInput" class="bg-gray-200 hover:bg-gray-500 text-white font-bold py-2 px-4 rounded cursor-pointer">파일찾기</label>
                            <input ref="thumbRoot" id="thumbInput" type="file" name="image" accept="image/*" class="hidden" @input="uploadThumb">
                        </div>
                        <div class="sm:w-full md:w-1/2 mt-6">
                            <div class="w-full p-4 flex justify-between">
                                <p>타이틀 :</p>
                                <input type="text" class="rounded w-3/5" v-model="title" @change="validationCheck">
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>카테고리 :</p>
                                <select class="rounded w-3/5 multiple" v-model="categoryId" @change="validationCheck">
                                    <option v-for="category in state.categories" :key="category.id" :value="category.category_id">
                                        {{ category.category_name }}
                                    </option>
                                </select>
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>목표금액 :</p>
                                <input type="number" class="rounded w-3/5" v-model="goal"  @change="validationCheck">
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>시작날짜 :</p>
                                <input type="date" class="rounded w-3/5" v-model="startDatetime" @change="validationCheck">
                            </div>
                            <div class="w-full p-4 flex justify-between">
                                <p>종료날짜 :</p>
                                <input type="date" class="rounded w-3/5" v-model="endDatetime" @change="validationCheck">
                            </div>
                        </div>
                    </div>
                    <div class="w-full mt-6 mb-4 md:flex">
                        <div>설명글 작성</div>
                        <br>
                        <input type="text" class="rounded sm:w-full md:w-3/4 h-lg mx-auto" v-model="content" @change="validationCheck">
                    </div>
                </div>
                <div class="md:flex md:justify-end">
                    <div v-if="!clickable" class="m-2">누락된 입력이 존재하거나 날짜가 맞지 않습니다.</div>
                    <button class="bg-gray-200 text-white font-bold py-2 px-4 m-2 rounded" :class="{'bg-green-500':clickable, 'hover:bg-green-700':clickable}" @click = "submit">작성</button>
                    <button class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 m-2 rounded" @click = "cancel">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { computed, watch, reactive } from 'vue'
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
            categories: [],
        })
        const init = () =>{
            store.dispatch('root/getCategoryList')
            .then((result)=>{
                //console.log("category data----->")
                //console.log(result)
                state.categories = result.data
            })
            .catch()
        }
        init()
        return { state }
    },
    data() {
        return {
            dateCheckFlag: false,
            clickable: false,
            imageFile: "",
            imageFlag: false,
            thumbFile: "",
            thumbFlag: false,
            title : "",
            categoryId : "",
            content : "",
            goal : 0,
            startDatetime: "",
            endDatetime: "",
        }
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
            // console.log(file)
            // console.log(formdata)
            axios({
                method: 'post',
                url: 'https://j5a506.p.ssafy.io/api/upload/fundingInfo',
                headers:{
                    'Content-Type': 'multipart/form-data'
                },
                data:formdata
            })
            .then((result)=>{
                // console.log("----->image")
                // console.log(result)
                this.imageFile = result.data
                this.imageFlag = true
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

            let file = this.$refs.thumbRoot.files[0]
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
                // console.log("----->image")
                // console.log(result)
                this.thumbFile = result.data
                this.thumbFlag = true
            })
            .catch()
        },
            submit(){
            //validation() 체크시에만 버튼 활성

            let startArr = this.startDatetime.split('-')
            let endArr = this.endDatetime.split('-')

            let startDate = startArr[0] + '-' + startArr[1] + '-' + startArr[2] + 'T' + "00:00:00"
            let endDate = endArr[0] + '-' + endArr[1] + '-' + endArr[2] + 'T' + "00:00:00"
            let payload = {
                'title' : this.title,
                'categoryId' : this.categoryId,
                'content' : this.content,
                'goal' : this.goal,
                'thumbnail' : this.thumbFile,
                'image' : this.imageFile,
                'startDatetime': startDate,
                'endDatetime': endDate,
            }
            axios({
                method: 'post',
                url: 'https://j5a506.p.ssafy.io/api/funding/',
                headers:{
                    'jwt':  localStorage.getItem('jwt'),
                },
                data:payload
            })
            .then((result)=>{
                //console.log("----->funding create")
                //console.log(result)
                if(result.status == 200){
                    alert("펀딩이 성공적으로 등록되었습니다. 승인을 기다려주세요")
                    this.$router.push("/main/all/1")
                }
            })
            .catch()
        },
        dateCheck (){
            let startArr = this.startDatetime.split('-')
            let endArr = this.endDatetime.split('-')
            
            let start = new Date(startArr[0],startArr[1],startArr[2])
            let end = new Date(endArr[0],endArr[1],endArr[2])
            // console.log(start.getTime())
            // console.log(end.getTime())
            // console.log(start.getTime() < end.getTime())

            if(start.getTime() < end.getTime()){
                this.dateCheckFlag = true
            }else{
                this.dateCheckFlag = false
            }
        },
        validationCheck(){
            this.dateCheck()
            if(this.imageFlag && this.thumbFlag && this.title!="" && this.content!="" && this.goal!=0 && this.startDatetime!="" && this.endDatetime!="" && this.dateCheckFlag){
                this.clickable = true
            }else{
                this.clickable = false
            }
        },
        cancel(){
            router.push("/main/all/1")
        },
    }
};
</script>

<style scoped>
</style>