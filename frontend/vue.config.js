const kakaoAPI = 'https://kapi.kakao.com/'

module.exports = {
    devServer: {
        https: true,
        port: 8083,
        open: true,
        proxy: {
            '/api': {
                target: 'https://localhost:8080/',
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
    outputDir: '../dist'
    
}