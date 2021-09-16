import $axios from 'axios'

const REST_API_KEY = '90dd81e93f00e04640a83ff9889069a6';
const REDIRECT_URI = 'https://localhost:8080/oauth/kakao';
const client_secret = 'N6XGWtgyzrtYo4WJmJWxeh5p3bk5BWDL';

export function getKakaoToken ({state}, payload) {
    const url = 'https://kauth.kakao.com/oauth/token'
    const headers = {
        'Authorization': 'aa85fc3c27abac4c4cd3f68be514bf66',
        'Content-type': 'application/x-www-form-urlencoded;charset=utf-8'
    }
    const body = {
        grant_type: 'authorization_code',
        client_id: REST_API_KEY,
        redirect_uri: REDIRECT_URI,
        code: payload.code,
        client_secret: client_secret
    }

    return $axios.post(url, {headers}, body);
}