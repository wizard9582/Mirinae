
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

export function setImage (state, src) {
    state.image = src
}

export function setThumb (state, src) {
    state.thumb = src
}