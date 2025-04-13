// webpack.config.js 或其他配置文件
module.exports = {
    // ... 其他配置 ...
    devServer: {
        // ... 其他devServer配置 ...
        allowedHosts: [
            'localhost', // 允许localhost访问
            '127.0.0.1', // 允许IPv4本地地址访问
            // 如果有需要，可以添加其他非空字符串，如 'your-domain.com'
        ],
        // ... 其他devServer配置 ...
    },
    // ... 其他配置 ...
};