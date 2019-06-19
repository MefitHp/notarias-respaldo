
function ctrl_logs($scope){
    $scope.refresh = function(){
        if(typeof $scope.lines =="undefined"){
            $scope.lines=200;
        }
        var sesion="&idsesionactual="+$scope.usr_global.idsesionactual;
        var usuario="&idusuario="+$scope.usr_global.idusuario;
        var url = "http://"+contexto+"/notaria/utiles/obtieneLog?lines="+$scope.lines+sesion+usuario;
        $.get( url, function( data ) {
            $("#logdata").text(data);
            $('#logdata').scrollTop($('#logdata')[0].scrollHeight);
          });
    }
    
    $scope.refresh();
        
    
    }


