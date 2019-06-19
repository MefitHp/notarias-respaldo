/**
 * attach services to this module
 * if you get 'unknown {x}Provider' errors from angular, be sure they are
 * properly referenced in one of the module dependencies in the array.
 * below, you can see we bring in our services and constants modules 
 * which avails each service of, for example, the `config` constants object.
 **/

//de esta forma tan sencilla consumimos con $resource en AngularJS


    var port = "9090";
    var ip = window.location.hostname;
    var contexto = ip+":"+port+"/notarias-web";
    var contexto_alertas = ip+":"+port+"/alertas";
    var urlConsumir = "http://"+contexto+"/notaria/:tipo/:listController:id";
    var urlAlertas = "http://"+contexto_alertas+"/api/v1/:tipo/:id";
    var urlConectividad = "http://"+ip+":"+port+"/"+"conectividad-rest/:tipo/:listController";
    //var urlConsumir = "data.json";

define(['angular'], function (ng) {
    'use strict';
    return ng.module('app.services', []);
});
