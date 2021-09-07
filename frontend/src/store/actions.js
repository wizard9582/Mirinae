import $axios from 'axios'

export function requestLogin (payload) {
    //console.log('requestLogin', state, payload)
    const url = '/auth/login'
    let body = payload
    return $axios.post(url, body)
}