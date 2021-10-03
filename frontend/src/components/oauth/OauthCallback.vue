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
                //console.log(result)
                let access_token = result.data.access_token
                let refresh_token = result.data.refresh_token

                localStorage.setItem('kakao_access', access_token)
                localStorage.setItem('kakao_refresh', refresh_token)

                store.dispatch('root/getKakaoInfo', { access_token: access_token })
                .then((result)=>{
                    //console.log(result)
                    let email = result.data.kakao_account.email
                    let nickname = result.data.kakao_account.profile.nickname
                    let oauthType = 'KAKAO'

                    store.dispatch('root/login', { email:email, nickname:nickname, oauthType:oauthType })
                    .then((result)=>{
                        console.log(result)
                        if(result.status === 200){
                            store.commit('root/setToken', result.data.jwt)
                            store.commit('root/setUserId', email)
                            router.push('/main/all/1')
                        }else{
                            router.push('/error')
                        }
                    })
                    .catch()
                })
                .catch()
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