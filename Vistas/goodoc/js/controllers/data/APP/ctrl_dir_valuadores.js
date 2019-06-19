define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_dir_valuadores", function ctrl_dir_valuadores($scope,$route, conexion_app, conexion, catalogos) {
        
        catalogos.get_valuadores($scope);
        



});

})

