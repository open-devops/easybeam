const webpack = require('webpack');
const path = require('path');
module.exports = {
    entry: {
        'vendor': [
            './src/main/webapp/app/vendor',
            '@angular/animations',
            '@angular/common',
            '@angular/compiler',
            '@angular/core',
            '@angular/flex-layout',
            '@angular/forms',
            '@angular/http',
            '@angular/material',
            '@angular/platform-browser',
            '@angular/platform-browser-dynamic',
            '@angular/platform-server',
            '@angular/router',
            '@ng-bootstrap/ng-bootstrap',
            '@covalent/core',
            '@covalent/dynamic-forms',
            '@covalent/highlight',
            '@covalent/http',
            '@covalent/markdown',
            '@swimlane/ngx-charts',
            'angular2-cookie',
            'ngx-infinite-scroll',
            'classlist.js',
            'core-js',
            'd3',
            'hammerjs',
            'flexibility',
            'highlight.js',
            'intl',
            'showdown',
            'reflect-metadata',
            'web-animations-js',
            'jquery',
            'ng-jhipster',
            'ng2-webstorage',
            'sockjs-client',
            'webstomp-client',
            'rxjs'
        ]
    },
    resolve: {
        extensions: ['.ts', '.js'],
        modules: ['node_modules']
    },
    module: {
        exprContextCritical: false,
        rules: [{
                test: /(vendor\.scss|global\.scss|styles\.scss|theme\.scss|platform\.scss)/,
                loaders: ['style-loader', 'css-loader', 'postcss-loader', 'sass-loader']
            },
            {
                test: /\.(jpe?g|png|gif|svg|woff2?|ttf|eot)$/i,
                loaders: ['file-loader?hash=sha512&digest=hex&name=[hash].[ext]']
            }
        ]
    },
    output: {
        filename: '[name].dll.js',
        path: path.resolve('./build/www'),
        library: '[name]'
    },
    plugins: [
        new webpack.DllPlugin({
            name: '[name]',
            path: path.resolve('./build/www/[name].json')
        })
    ]
};
