define(['./module'], function (controllers) {
    'use strict';

    controllers.controller('MyCtrl1', function ($scope) {
    	$scope.tels = [
            {'name': 'Nexus S',
            'snippet': 'Fast just got faster with Nexus S.'},
            {'name': 'Motorola XOOM™ with Wi-Fi',
            'snippet': 'The Next, Next Generation tablet.'},
            {'name': 'MOTOROLA XOOM™',
            'snippet': 'The Next, Next Generation tablet.'}
            ];


    });
});
