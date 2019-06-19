define(['../../module'], function(controllers) {
    'use strict';

    controllers.controller("ctrl_expedientes", function indexController($scope, $http, con_users, con_rol) {

        //datosResource lo tenemos disponible en la vista gracias a $scope
        //$scope.usuarios = conexiones.query();
        $scope.roles = con_rol.listar();

        function refreshList() {
            $scope.user_lista = con_users.listar();
        }


        $scope.lanzaForm = function() {

            con_users.agregar($scope.user, function(data) {

                refreshList();

            }, function(err) {
                console.log("dos");
                console.info(err.status);
            });
        }


        $scope.get = function() {
            refreshList();
        }

        $scope.delete = function(identificador) {

            con_users.delete({idusuario: identificador}, function(data) {
                refreshList();

            }, function(err) {
                console.log("dos");
                console.info(err.status);
            });
        }

        $scope.get_x_id = function(identificador) {

            con_users.get_x_id({idusuario: identificador}, function(data) {

                // HAY QUE VER QUE HACER
                alert("aun no hago nada");

            }, function(err) {
                console.log("dos");
                console.info(err.status);
            });
        }


    });



});



