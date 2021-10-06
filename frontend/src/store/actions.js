import $axios from 'axios'

//Login Oauth 영역

//카카오 영역

const kakaoLogin = {
    REST_API_KEY: '90dd81e93f00e04640a83ff9889069a6',
    // REDIRECT_URI: 'https://localhost:8083/oauth/kakao',
    REDIRECT_URI: 'https://j5a506.p.ssafy.io/oauth/kakao',
    AUTHORIZATION: 'aa85fc3c27abac4c4cd3f68be514bf66',
    CONTENT_TYPE: 'application/x-www-form-urlencoded;charset=utf-8'
}

export function getKakaoCode () {
    const url = 'https://kauth.kakao.com/oauth/authorize?client_id='+kakaoLogin.REST_API_KEY+'&redirect_uri='+kakaoLogin.REDIRECT_URI+'&response_type=code'
    return $axios.get(url);
}

export function getKakaoToken ({state}, payload) {
    //console.log(payload.code)
    const url = 'https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id='+kakaoLogin.REST_API_KEY+'&redirect_uri='+kakaoLogin.REDIRECT_URI+'&code='+payload.code
    const headers = {
        'Authorization': 'aa85fc3c27abac4c4cd3f68be514bf66',
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
    }

    return $axios.post(url, {headers});
}

export function checkKakaoToken ({state}, payload) {
    const url = '/v1/user/access_token_info'
    const headers = {
        'Authorization': 'Bearer ' + payload.access_token,
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
    }
    return $axios.get(url, {headers});
}

export function getKakaoInfo ({state}, payload) {
    const url = '/v2/user/me'
    const headers = {
        'Authorization': 'Bearer ' + payload.access_token,
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
    }
    return $axios.get(url, {headers});
}

export function KakaoLogout ({state}, payload) {
    const url = '/v1/user/logout'

    const headers = {
        'Authorization': 'Bearer ' + payload.access_token,
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
    }
    return $axios.post(url, {headers});
}

//네이버 영역
const naverLogin = {

}

//구글 영역
const googleLogin = {
    
}

//회원정보 API
export function getUserInfo({state}, payload){
    const url = '/api/user/'
    const headers = {
        'jwt':  payload.jwt,
    }

    return $axios.get(url, {headers});
}

export function saveWallet({state}, payload){
    const url = '/api/user/wallet'
    const headers = {
        'jwt':  payload.jwt,
    }
    const body = {
        'walletAddress' : payload.walletAddress,
    }

    return $axios.patch(url, {headers}, body);
}

export function login({state}, payload){
    const url = '/api/user/login'
    const body = payload

    return $axios.post(url, body);
}

export function updateUser({state}, payload){
    const url = '/api/user'
    const headers = {
        'jwt':  payload.jwt,
    }
    const body = {
        'email': payload.email,
        'nickname': payload.nickname,
    }
    return $axios.patch(url, {headers}, body);
}

export function deleteUser({state}, payload){
    const url = '/api/user'
    const headers = {
        'jwt':  payload.jwt,
    }
    return $axios.delete(url,{headers});
}

export function getMyDonation({state}, payload){
    const url = '/api/user/donation'
    const headers = {
        'jwt':  payload.jwt,
    }
    return $axios.get(url, {headers});
}

export function getMyFunding({state}, payload){
    const url = '/api/user/funding'
    const headers = {
        'jwt':  payload.jwt,
    }
    return $axios.get(url, {headers});
}
//펀딩 /API

export function createFunding({state}, payload){
    const url = '/api/funding' 
    const headers = {
        'jwt':  payload.jwt,
    }
    const body = {
        'title' : payload.title,
        'categoryName' : payload.categoryName,
        'content' : payload.content,
        'goal' : payload.goal,
        'thumbnail' : payload.thumbnail,
        'image' : payload.image,
        'startDatatime': payload.startDatatime,
        'endDatetime': payload.endDatetime,
    }
    return $axios.post(url, {headers}, body);
}

export function getFundingList({state}, payload){
    const url = '/api/funding/' + payload.category + '?size=' + payload.size + '&page=' + payload.page

    return $axios.get(url);
}

export function getCategoryList(){
    const url = '/api/funding/category'

    return $axios.get(url);
}

export function joinFunding({state}, payload){
    const url = '/api/funding/join'
    const headers = {
        'jwt':  payload.jwt,
    }
    const body = {
        'fundingId' : payload.fundingId,
        'amount' : payload.amount
    }
    return $axios.post(url, {headers}, body);
}

export function ckeckFundingOwner({state}, payload){
    const url = '/api/funding/owner/'+ payload.fundingId
    const headers = {
        'jwt':  payload.jwt,
    }
    return $axios.get(url, {headers});
}

export function detailFunding({state}, payload){
    const url = '/api/funding/detail/'+ payload.fundingId

    return $axios.get(url);
}

export function deleteFunding({state}, payload){
    const url = '/api/funding/'+ payload.fundingId
    const headers = {
        'jwt':  payload.jwt,
    }
    return $axios.delete(url,{headers});
}

//블록체인 /API

export function getTransaction({state}, payload){
    const url = '/api/funding/tx?id=' + payload.txId

    return $axios.get(url);
}

//랭킹 /API

export function getFundingRanking({state}, payload){
    const url = '/api/ranking/funding/'+ payload.fundingId

    return $axios.get(url);
}

export function getCategoryRanking({state}, payload){
    const url = '/api/ranking/category/'+ payload.categoryId

    return $axios.get(url);
}

//관리자 /API

export function getNotAcceptedFundingList({state}, payload){
    const url = '/api/admin/funding'
    const headers = {
        'jwt':  payload.jwt,
    }
    return $axios.get(url,{headers});
}

export function fundingStateChange({state}, payload){
    const url = '/api/admin/' + payload.fundingId + '/' + payload.action
    const headers = {
        'jwt':  payload.jwt,
    }
    return $axios.patch(url, {headers});
}

//이미지 업로드 /API

export function uploadProfileImg({state}, payload){
    const url = '/api/upload/' + payload.usage
    const headers = {
        'Content-Type': 'multipart/form-data'
    }
    const body = payload.formdata
    
    return $axios.post( url, {headers}, body);
}