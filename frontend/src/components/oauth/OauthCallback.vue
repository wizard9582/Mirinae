<template>
    <div>
        로그인이 진행중입니다...
    </div>
</template>

<script>
import { useStore } from 'vuex'
import { useRouter } from 'vue-router';

export default {
    name: 'OauthCallback',
    components: {
    },

    setup(){
        const router = useRouter()
        const store = useStore()

        //console.log(router.currentRoute)
        let portal = router.currentRoute.value.params.portal

        if(portal==="kakao"){
            let code = router.currentRoute.value.query.code
            store.dispatch('root/getKakaoToken', { code: code })
            .then((result)=>{
                console.log(result)
            })
            .catch()

        }else if(portal==="naver"){

        }else if(portal==="google"){

        }

        return {}
    }
};
</script>

<style scoped>
</style>