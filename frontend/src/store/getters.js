
export function isLoggedIn(state){
    return state.jwt ? true : false ;
}

export function getAuthToken(state){
    return state.jwt;
}

export function getUserId(state){
    return state.userId;
}