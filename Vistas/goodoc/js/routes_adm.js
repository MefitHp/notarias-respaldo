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

directorioVistas = "views/_adm/";    

define(['./app'], function (app) {
    'use strict';
    
    
    return app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when("/", {
        templateUrl : directorioVistas+"datos_cliente.html"
        }).when("/datos_cliente", {
        templateUrl : directorioVistas+"datos_cliente.html",
        }).when("/lista_clientes", {
        templateUrl : directorioVistas+"lista_clientes.html",
        }).when("/adjuntar_logotipo", {
        templateUrl : directorioVistas+"adj_logo.html",
        }).when("/usuarios", {
        templateUrl : directorioVistas+"usuarios.html",
        }).when("/gruposusuarios", {
        templateUrl : directorioVistas+"gruposusuarios.html",
        }).when("/asociargrupousuarios", {
        templateUrl : directorioVistas+"asociargrupousuarios.html",
        })
                .when("/roles", {
        templateUrl : directorioVistas+"roles.html",
        }).when("/operaciones", {
        templateUrl : directorioVistas+"operaciones.html",
        /*}).when("/formatos_explorador", {
        templateUrl : directorioVistas+"for_explorador.html",
        }).when("/formatos_agregar", {
        templateUrl : directorioVistas+"for_agregar.html",
        controller : "ctrl_formatos"
        }).when("/formatos_actualizar", {
        templateUrl : directorioVistas+"for_actualizar.html",
        controller : "ctrl_formatos"*/
        }).when("/catalogo_generales", {
        templateUrl : directorioVistas+"ca_generales.html",
        }).when("/catalogo_variables", {
        templateUrl : directorioVistas+"ca_variables.html",
        }).when("/catalogo_variables_exp", {
        templateUrl : directorioVistas+"ca_variables_exp.html",
        }).when("/catalogo_variables_def", {
        templateUrl : directorioVistas+"ca_variables_def.html",
        })
        /*.when("/catalogo_grupos", {
        templateUrl : directorioVistas+"ca_grupos.html",
        controller : "ctrl_catalogos"
        }).when("/catalogo_grupos_definicion", {
        templateUrl : directorioVistas+"ca_grupos_def.html",
        controller : "ctrl_grupos_def"
        })*/.when("/plantillas_explorador", {
        templateUrl : directorioVistas+"pla_explorador.html",
        }).when("/plantillas_actualizar", {
        templateUrl : directorioVistas+"pla_actualizar.html",
        }).when("/plantillas_agregar", {
        templateUrl : directorioVistas+"pla_agregar.html",
        })
                
        //// DOCUMENTO OBJETO            
        .when("/documento_objeto", {
        templateUrl : directorioVistas+"doc_obj.html",
        })
    
        //// FORMULARIOS DINAMICOS          
           .when("/formulario_dinamico", {
           templateUrl : directorioVistas+"frm_dinamico.html",
           
           })
           .when("/lista_formulario_dinamico", {
           templateUrl : directorioVistas+"lista_frm_dinamico.html"          
           })
           
           
         
        /// GESTORES
        
         .when("/gestores", {
           templateUrl : directorioVistas+"gestores.html",
           })
        /// VALUADORES
        
         .when("/valuadores", {
           templateUrl : directorioVistas+"valuadores.html",
           })
         
         // GESTIÓN DE DOCUMENTOS PREVIOS Y POSTERIORES
         .when("/previos_posteriores", {
           templateUrl : directorioVistas+"gestor_docs.html",
           })
           
           .when("/formato_pdf", {
           templateUrl : directorioVistas+"formato_pdf.html",
           })
           
            .when("/listar_formato_pdf", {
           templateUrl : directorioVistas+"listar_formato_pdf.html",
           })
           .when("/folios", {
           templateUrl : directorioVistas+"registroFolios.html",
           })
            .when("/agregar_esc_ext", {
           templateUrl : directorioVistas+"registroEscrituraExterna.html",
           })
     .when("/logs", {
           templateUrl : directorioVistas+"logs.html",
           })
           .when("/alerta_tipo", {
           templateUrl : directorioVistas+"alerta_tipo.html",
           })
           .when("/alerta_pasos", {
           templateUrl : directorioVistas+"alerta_pasos.html",
           })
           
            .when("/filtroActoCompareciente", {
           templateUrl : directorioVistas+"filtroActoCompareciente.html",
           })
    
    
     $routeProvider.when("/error",{
        templateUrl : directorioVistas+"../error.html"
    }).otherwise({

       redirectTo:"/error"
    });
    
    
    }]);
});



