define(['./module'], function (filters) {
    'use strict';

    return filters.filter('interpolate', ['version', function (version) {
        return function (text) {
            return String(text).replace(/\%VERS1ON\%/mg, version);
        }
    }]);
});
