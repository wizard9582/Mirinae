
export function setToken (state, token) {
    state.jwt = token
}

export function setUserId (state, id) {
    state.userId = id
}

export function logout (state){
    state.jwt = null
    state.userId = null
}