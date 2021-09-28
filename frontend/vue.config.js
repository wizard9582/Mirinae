const kakaoAPI = 'https://kapi.kakao.com/'
const backendAPI = 'https://localhost:8080/'

module.exports = {
    devServer: {
        https: true,
        port: 8083,
        open: true,
        proxy: {
            '^/api': {
                target: backendAPI,
                changeOrigin: true
            },
            '/v1': {
                target: kakaoAPI,
                changeOrigin: true
            },
            '/v2': {
                target: kakaoAPI,
                changeOrigin: true
            },
        },
        historyApiFallback: true,
        hot: true
    },
    lintOnSave: false,
}