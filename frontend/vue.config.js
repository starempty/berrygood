const path = require("path");

module.exports = {
  devServer: {
    port: 80,
    open: true,
    proxy: {
      "/api": {
        target: "http://j5d101.p.ssafy.io:8080"
      }
    },
    historyApiFallback: true,
    hot: true,
  },
  configureWebpack: {
    resolve: {
      alias: {
        "@": path.join(__dirname, "src/"),
      },
    },
  },
}