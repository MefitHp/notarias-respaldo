//desde aquí hacemos uso del objeto app que es nuestro modulo
//y de esta forma creamos un controlador, en este caso el controlador indexController,
//como vemos, hacemos uso de scope y sencillamente creamos un array y colocamos 
//dos objetos dentro de él

//con dataResource inyectamos la factoría
app.controller("appController", function ($scope, $http, datosPrueba) {
    //hacemos uso de $http para obtener los datos del json
    
})


app.controller("ctrl_index", function indexController($scope, $http, datosPrueba){
    
    //datosResource lo tenemos disponible en la vista gracias a $scope
    $scope.usuarios = dataResource.get();
   
})
 
//más de lo mismo, pero en este caso creamos una variable llamada saludo y una función
//que gracias al objeto location y al método url nos redirigirá al login al hacer uso de ella
app.controller("ctrl_home", function homeController($scope, $location){
    $scope.saludo = "Hola desde el controlador home";
    $scope.toLogin = function(){
        
        console.log($scope);
        
    }
});
 
app.controller("ctrl_login", function loginController($scope, $location){
    $scope.saludo = "Hola desde el controlador login";
    $scope.toHome = function(){
        $location.url("/home");
    }
})

app.controller("ctrl_tablas", function loginController($scope, $location){
   $scope.myData = [{Nombre: "Moroni", Edad: 50},
                 {Nombre: "Tiancum", Edad: 43},
                 {Nombre: "Jacob", Edad: 27},
                 {Nombre: "Nephi", Edad: 29},
                 {Nombre: "Enos", Edad: 34}];
             
  $scope.gridOptions = { data: 'myData' };
})

app.controller("ctrl_post", function indexController($scope, $http, datosPrueba){
    
    //datosResource lo tenemos disponible en la vista gracias a $scope
    $scope.usuarios = datosPrueba.query();
    
    console.info($scope.usuarios);
    
    
    $scope.lanzaForm=function(){
        
        datosPrueba.agregar($scope.usuario);
    }
   
})

app.controller("ctrl_get", function indexController($scope, $http, datosPrueba){
    
    
   
})