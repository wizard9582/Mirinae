import $axios from 'axios'

const kakaoLogin = {
    REST_API_KEY: '90dd81e93f00e04640a83ff9889069a6',
    REDIRECT_URI: 'https://localhost:8080/oauth/kakao',
    AUTHORIZATION: 'aa85fc3c27abac4c4cd3f68be514bf66',
    CONTENT_TYPE: 'application/x-www-form-urlencoded;charset=utf-8'
}

export function getKakaoCode () {
    const url = 'https://kauth.kakao.com/oauth/authorize?client_id='+kakaoLogin.REST_API_KEY+'&redirect_uri='+kakaoLogin.REDIRECT_URI+'&response_type=code'
    return $axios.get(url);
}

export function getKakaoToken (payload) {
    const url = 'https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id='+kakaoLogin.REST_API_KEY+'&redirect_uri='+kakaoLogin.REDIRECT_URI+'&code='+payload.code
    const headers = {
        'Authorization': 'aa85fc3c27abac4c4cd3f68be514bf66',
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
    }

    return $axios.post(url, {headers});
}

export function KakaoLogout (payload) {
    const url = 'https://kapi.kakao.com/v1/user/logout'

    const headers = {
        'Authorization': 'Bearer ' + payload.access_token,
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
    }
    return $axios.post(url, {headers});
}