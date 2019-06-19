require.config({
    paths: {
        'angular': '../lib/angular/angular.min',
        'angular-route': '../lib/angular-route/angular-route.min',
        'angular-resource': '../lib/angular-resource/angular-resource.min',
        'domReady': '../lib/requirejs-domready/domReady',
        'jqueryUI': '../lib/jquery-ui/ui/minified/jquery-ui.min',
        'tareaJs': '../js/tareaJs',
        'initJs': '../js/initJs',
        'dropzone': '../lib/plugins/dropzone/dropzone',
        'easytabs': '../lib/plugins/tab/lib/jquery.easytabs.min',
        'modalDialog': '../lib/plugins/modal/modal',
        'underscore': '../lib/underscore/underscore',
        'datetimepicker': '../lib/plugins/datetimepicker-master/jquery.datetimepicker',
        'uploadFile': '../lib/plugins/uploadFile/uploadFile'
    },
    shim: {
        'angular': {
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular']
        },
        'angular-resource': {
            deps: ['angular']
        },
        'jqueryUI': {
            exports: 'jqueryUI',
            deps: ['jquery']
        },
        'datetimepicker': {
            exports: 'datetimepicker',
            deps: ['jquery', 'jqueryUI']
        },
        'uploadFile': {
            exports: 'uploadFile',
            deps: ['jquery']
        },
        'tareaJs': {
            exports: 'tareaJs',
            deps: ['jquery']
        },
        'initJs': {
            exports: 'initJs',
            deps: ['jquery']
        },
        'dropzone': {
            exports: 'dropzone',
            deps: ['jquery']
        },
        'easytabs': {
            exports: 'easytabs',
            deps: ['jquery']
        },
        'underscore': {
            exports: '_',
        },
        "gridlicius": {
            exports: "gridlicius",
            deps: ['jquery']
        },
        'modalDialog': {
            exports: 'modalDialog',
            deps: ['jquery']
        },
    },
    deps: ['./bootstrap']
});