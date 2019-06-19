function ctrl_documentos($scope,$http,con_users,con_rol){$scope.roles=con_rol.listar();function refreshList(){$scope.user_lista=con_users.listar();}
$scope.lanzaForm=function(){con_users.agregar($scope.user,function(data){refreshList();},function(err){console.log("dos");console.info(err.status);});}
$scope.get=function(){refreshList();}
$scope.delete=function(identificador){con_users.delete({idusuario:identificador},function(data){refreshList();},function(err){console.log("dos");console.info(err.status);});}
$scope.get_x_id=function(identificador){con_users.get_x_id({idusuario:identificador},function(data){alert("aun no hago nada");},function(err){console.log("dos");console.info(err.status);});}}