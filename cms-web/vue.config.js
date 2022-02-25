const webpack = require("webpack")

module.exports = {
    lintOnSave: false,
    publicPath: '/',
    assetsDir: 'static',
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery",
                jquery: "jquery",
                "window.jQuery": "jquery"
            })]
    }
}
