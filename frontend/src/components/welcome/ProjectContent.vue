<template>
    <div class="w-full h-xl flex justify-between items-center px-6 lg:px-32 bg-black text-myfont relative">
        <div class="text-4xl italic">
            <div class="font-bold mb-10">
                <span>미리내★</span>
                <span> Mirinae</span>
            </div>
            <div class="font-semibold">
                블록체인 기반 펀딩 플랫폼
            </div>
        </div>
        <p class="transform text-xl text-center hover:scale-120">
            좋은 일인걸 알면서도<br>
            내가 기부한 돈이<br>
            정말 꼭 필요한 사람에게<br>
            쓰이고 있을까?<br>
        </p>
        <p class="transform text-xl text-center hover:scale-120">
            지금 당장<br>
            도움이 필요한 사람에게<br>
            곧바로 전할 수 있는<br>
        </p>
        <p class="transform text-xl text-center hover:scale-120">
            복잡한 중간과정이<br>
            필요 없는<br>
            도움의 손길을<br>
            가장 필요한 곳에
        </p>
    </div>
    <div class="w-full h-xl flex justify-between items-center px-6 lg:px-32 bg-gray-900 text-myfont relative">
        <div class="text-3xl text-center">
            블록체인 네트워크를 통해 쉽게 펀딩을 열고 후원하며<br>
            복잡한 단계를 거치지 않고<br>
            투명한 후원과 기부를 할 수 있다면 얼마나 좋을까?<br>
            <br>
        </div>
        <div class="font-semibold">
            <img id="network" src="../../assets/blockchain.png" alt="network" class="w-96 h-96">
        </div>
    </div>
    <div class="w-full h-xl flex justify-between items-center px-6 lg:px-32 bg-gray-800 text-myfont relative text-center">
        <div :style="{height:`${state.box[0]}%`}" class="transform border-white border-2 border-opacity-30 hover:border-4 hover:border-opacity-50 w-1/6 hover:scale-150 align-middle"
            @mouseenter="mouseOver(0)" @mouseleave="mouseLeave(0)">
            <p class="my-auto text-3xl">보안성</p>
            <p v-if="state.box[0]==48">
                <br>
                블록의 데이터들이 복사되어 노드들에 저장되어 있어 시스템을 공격하기 위해선 절반이상의 노드를 해킹해야하므로 시스템이 안정적이다.
            </p>
        </div>
        <div :style="{height:`${state.box[1]}%`}" class="transform border-white border-2 border-opacity-30 hover:border-4 hover:border-opacity-50 w-1/6 hover:scale-150 align-middle"
            @mouseenter="mouseOver(1)" @mouseleave="mouseLeave(1)">
            <p class="my-auto text-3xl">투명성</p>
            <p v-if="state.box[1]==48">
                <br>
                개인 정보 없이 익명의 계좌와 개인키만으로 거래가 이루어지기 때문에 거래 조회에 제약이 없이 투명하게 정보를 열람할 수 있다.
            </p>
        </div>
        <div :style="{height:`${state.box[2]}%`}" class="transform border-white border-2 border-opacity-30 hover:border-4 hover:border-opacity-50 w-1/6 hover:scale-150 align-middle"
            @mouseenter="mouseOver(2)" @mouseleave="mouseLeave(2)">
            <p class="my-auto text-3xl">무결성</p>
            <p v-if="state.box[2]==48">
                <br>
                해쉬 알고리즘의 특성 상 블록의 거래 하나만 조작되어도 완전히 다른 블록체인을 가리키게 되므로 데이터 변조를 막는다.
            </p>
        </div>
        <div :style="{height:`${state.box[3]}%`}" class="transform border-white border-2 border-opacity-30 hover:border-4 hover:border-opacity-50 w-1/6 hover:scale-150 align-middle"
            @mouseenter="mouseOver(3)" @mouseleave="mouseLeave(3)">
            <p class="my-auto text-3xl">Smart Contract</p>
            <p v-if="state.box[3]==48">
                <br>
                스마트 컨트랙트를 활용해 블록체인 네트워크를 기반으로 다양한 기능을 구현한다.
            </p>
        </div>
    </div>
    <div class="w-full h-xl flex justify-between items-center px-6 lg:px-32 bg-gray-700 text-myfont relative">
        <div class="w-full absolute left-0 top-0 p-3 lg:p-32">
            <div class="flex justify-between">
                <div>
                    <h1 class="text-4xl font-bold">TEAM. A506</h1>
                    <span>김호석과 아이들</span>
                    <span class="italic">.Idle</span>
                </div>
            </div>
        </div>
        <div class="w-full absolute right-0 bottom-0 p-3 lg:p-32">
            <a href="https://j5a506.p.ssafy.io/main/all/1" class="italic underline">미리내로 향하기</a>
        </div>
        <div>
            <div class="">
                <span class="font-bold uppercase tracking-widest"></span>
                <div class="text-xl lg:text-3xl font-bold text-pink-500">.팀장</div><br/>
                <h1 class="text-3xl lg:text-5xl font-bold text-pink-500">김호석</h1><br/>
                <p class="font-bold mb-1">CI/CD Jenkins &amp; 블록체인₿ 담당</p>
                <p>$ geth --datadir=path/to/custom/data/folder...</p>
            </div>
        </div>
        <div>
            <div class="">
                <span class="font-bold uppercase tracking-widest"></span>
                <div class="text-xl lg:text-3xl font-bold text-pink-500">.FE</div><br/>
                <h1 class="text-3xl lg:text-5xl font-bold text-pink-500">노진혁</h1><br/>
                <p class="font-bold mb-1">프론트엔드 담당</p>
                <p>const app = createApp(App)...</p>
            </div>
        </div>
        <div>
            <div class="">
                <span class="font-bold uppercase tracking-widest"></span>
                <div class="text-xl lg:text-3xl font-bold text-pink-500">.BE</div><br/>
                <h1 class="text-3xl lg:text-5xl font-bold text-pink-500">신한규</h1><br/>
                <p class="font-bold mb-1">백엔드 API &amp; DB 담당</p>
                <p>Donation toEntity(user, funding, tx_...</p>
            </div>
        </div>
        <div>
            <div class="">
                <span class="font-bold uppercase tracking-widest"></span>
                <div class="text-xl lg:text-3xl font-bold text-pink-500">.BE</div><br/>
                <h1 class="text-3xl lg:text-5xl font-bold text-pink-500">이예은</h1><br/>
                <p class="font-bold mb-1">백엔드 API &amp; UCC 담당</p>
                <p>spring.jpa.generate-ddl-auto=update...</p>
            </div>
        </div>
        <div>
            <div class="">
                <span class="font-bold uppercase tracking-widest"></span>
                <div class="text-xl lg:text-3xl font-bold text-pink-500">.BE</div><br/>
                <h1 class="text-3xl lg:text-5xl font-bold text-pink-500">이희정</h1><br/>
                <p class="font-bold mb-1">블록체인₿ &amp; SmartContract 담당</p>
                <p>pragma solidity >=0.6.0 0.9.0;...</p>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive } from 'vue'
export default {
    name: 'ProjectContent',
    setup(){
        const state = reactive({
            box:[8,8,8,8]
        })
        const mouseOver = (no)=>{
            state.box[no] = 48
        }
        const mouseLeave = (no)=>{
            state.box[no] = 8
        }
        return { state , mouseOver, mouseLeave}
    }
};
</script>

<style scoped>
</style>