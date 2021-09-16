module.exports = {
    devServer: {
        https: true,
        port: 8080,
        open: true,
        // proxy: {
        //   '^/api/v1': {
        //     target: 'https://localhost:8443/',
        //     changeOrigin: true
        //   },
        //   '/websocket':{
        //     target: 'https://localhost:8443/',
        //     changeOrigin: true
        //   },
        // },
        historyApiFallback: true,
        hot: true
    },
    lintOnSave: false,
    outputDir: '../dist'
    
}