/**
 * configure RequireJS
 * prefer named modules to long paths, especially for version mgt
 * or 3rd party libraries
 */
require.config({

    paths: {
        'angular': '../lib/angular/angular',
        'angular-route': '../lib/angular-route/angular-route',
        'angular-resource': '../lib/angular-resource/angular-resource',
        'domReady': '../lib/requirejs-domready/domReady',
        'less':'../lib/less/dist/less-1.6.0.min'
    },

    /**
     * for libs that either do not support AMD out of the box, or
     * require some fine tuning to dependency mgt'
     */
    shim: {
        'angular': {
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular']
        },
        'angular-resource': {
            exports: 'resource',
            deps: ['angular']
        },
        'less':{
            exports:'less'
        }
    },

    deps: [
        // kick start application... see bootstrap.js
        './bootstrap'
    ]
});


