const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    proxy: {
      "/": {
        target: "http://localhost:30000/",
        changeOrigin: true,
        ws: false,
        // pathRewrite: {
        //   '^/uac': ''
        // },
        logLevel: 'debug'
      },
    },
  }
})
