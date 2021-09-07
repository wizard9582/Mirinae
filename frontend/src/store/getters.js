
export function isLoggedIn(state){
    return state.authToken ? true : false ;
}

export function getAuthToken(state){
    return state.authToken;
}

export function getUserId(state){
    return state.userId;
}