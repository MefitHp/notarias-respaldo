/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//realizamos la configuración del enrutado dependiendo de la url
//con el objeto $routeProvider haciendo uso de when
//en este caso, cuando estemos en la página principal, le decimos que
//cargue el archivo templates/index.html y que haga uso del controlador
//indexController, así en todos los casos

directorioVistas = "views/";

dirApp = "_app/";

define(['./app'], function (app) {
    'use strict';
    
    return app.config(['$routeProvider', function ($routeProvider) {
        
        $routeProvider.when("/", {

        templateUrl : directorioVistas+"index.html",
        controller : "ctrl_index"
    })
    .when("/login", {
        templateUrl : directorioVistas+"login.html",
        controller : "ctrl_login"
    }).when("/logout", {
        templateUrl : directorioVistas+"login.html",
        controller : "ctrl_login"
    })
    
    
    
     $routeProvider.when("/error",{
        templateUrl : directorioVistas+"error.html"
    }).otherwise({

       redirectTo:"/error"
    });
    
    
        
        
    }]);
});



