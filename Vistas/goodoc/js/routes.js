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
dirCon = "_con/";
dirFol = "_fol/";


define(['./app'], function (app) {
    'use strict';

    return app.config(['$routeProvider', function ($routeProvider) {

            $routeProvider.when("/", {
                templateUrl: directorioVistas + "index.html",
                controller: "ctrl_index"
            })
                    .when("/resultados", {
                        templateUrl: directorioVistas + "search_resultados.html",
                    }).when("/search_avanzado", {
                templateUrl: directorioVistas + "search_avanzado.html",
            }).when("/search_historico", {
                templateUrl: directorioVistas + "search_historico.html",
            })


                    /////GENERALES

                    .when("/ayuda", {
                        templateUrl: directorioVistas + dirApp + "generales/videos.html",
                    })

                    .when("/asignacion_operaciones", {
                        templateUrl: directorioVistas + dirApp + "generales/asignacion_operaciones.html",
                    })
                    .when("/creacion_expediente", {
                        templateUrl: directorioVistas + dirApp + "generales/crea_expediente.html",
                    })

                    .when("/expedientes_listar", {
                        templateUrl: directorioVistas + dirApp + "generales/expedientes_listar.html",
                        // controller : "ctrl_expedientes_listar"
                    })
                    .when("/expedientes_compartir", {
                        templateUrl: directorioVistas + dirApp + "generales/expedientes_compartir.html",
                    })

                    .when("/antecedentes", {
                        templateUrl: directorioVistas + dirApp + "generales/antecedentes.html",
                    })
                    .when("/antecedentes_documentos", {
                        templateUrl: directorioVistas + dirApp + "generales/antecedentes_documentos.html",
                    })
                    .when("/antecedentes_operativos", {
                        templateUrl: directorioVistas + dirApp + "generales/antecedentes_operativos.html",
                    })

                    .when("/antecedentes_detalle", {
                        templateUrl: directorioVistas + dirApp + "generales/antecedentes_detalle.html",
                    })


                    /// COMPARECIENTES
                    .when("/comparecientes", {
                        templateUrl: directorioVistas + dirApp + "comparecientes/co_lista.html",
                    }).when("/comparecientes_registrar", {
                templateUrl: directorioVistas + dirApp + "comparecientes/co_registrar.html",
            }).when("/comparecientes_domicilio", {
                templateUrl: directorioVistas + dirApp + "comparecientes/co_domicilio.html",
            })
                    /*.when("/comparecientes_representantes", {
                     templateUrl : directorioVistas+dirApp+"comparecientes/co_representado.html",
                     //controller : "ctrl_comparecientes"
                     })*/
                    .when("/comparecientes_conyuge", {
                        templateUrl: directorioVistas + dirApp + "comparecientes/co_conyuge.html",
                    }).when("/comparecientes_hijo", {
                        templateUrl: directorioVistas + dirApp + "comparecientes/co_hijos.html",
                    }).when("/comparecientes_conyuge_domicilio", {
                        templateUrl: directorioVistas + dirApp + "comparecientes/co_domicilio_con.html",
                    }).when("/comparecientes_registrar_ri", {
                templateUrl: directorioVistas + dirApp + "comparecientes/co_registrar_ri.html",
            })

                    /////ESCRITURAS
                    .when("/escrituras", {
                        templateUrl: directorioVistas + dirApp + "escrituras/es_lista.html",
                    }).when("/escrituras_agregar", {
                templateUrl: directorioVistas + dirApp + "escrituras/es_agregar.html",
            }).when("/escrituras_editar", {
                templateUrl: directorioVistas + dirApp + "escrituras/es_edicion.html",
            })
                    .when("/escrituras_elaboracion", {
                        templateUrl: directorioVistas + dirApp + "escrituras/es_elaboracion.html",
                    }) .when("/escritura_nopaso", {
                        templateUrl: directorioVistas + dirApp + "escrituras/es_nopaso.html",
                    })

                    /////INMUEBLES
                    /*.when("/inmuebles", {
                        templateUrl: directorioVistas + dirApp + "inmuebles/in_lista.html",
                        controller: "ctrl_lista_inmuebles"
                    }).when("/inmueble_agregar", {
                templateUrl: directorioVistas + dirApp + "inmuebles/in_agregar.html",
                controller: "ctrl_inmuebles"
            }).when("/inmuebles_captura_inmueble", {
                templateUrl: directorioVistas + dirApp + "inmuebles/in_paso1.html",
                controller: "ctrl_inmuebles"
            }).when("/inmuebles_captura_domicilio", {
                templateUrl: directorioVistas + dirApp + "inmuebles/in_paso2.html",
                controller: "ctrl_inmuebles"
            })*/

                    /////PRESUPUESTO

               /*     .when("/presupuesto", {
                        templateUrl: directorioVistas + dirApp + "presupuesto/pre_lista.html",
                        controller: "ctrl_presupuesto"
                    }).when("/presupuesto_registro", {
                templateUrl: directorioVistas + dirApp + "presupuesto/pre_registro.html",
                controller: "ctrl_presupuesto"
            }).when("/presupuesto_edicion", {
                templateUrl: directorioVistas + dirApp + "presupuesto/pre_edicion.html",
                controller: "ctrl_presupuesto"
            })*/

                    /////TARJETA AMARILLA

                   /* .when("/tarjeta_amarilla", {
                        templateUrl: directorioVistas + dirApp + "tarjeta_amarilla/ta_lista.html",
                        controller: "ctrl_list_tarjeta_am"
                    }).when("/tarjeta_amarilla_agregar", {
                templateUrl: directorioVistas + dirApp + "tarjeta_amarilla/ta_agregar.html",
                controller: "ctrl_tarjeta_amarilla"
            })*/



                    /////TARJETA TESTIMONIOS

                   /* .when("/testimonios", {
                        templateUrl: directorioVistas + dirApp + "testimonios/test_adj_doc.html",
                        controller: "ctrl_testimonio"
                    }).when("/testimonios_validacion", {
                templateUrl: directorioVistas + dirApp + "testimonios/test_val_doc.html",
                controller: "ctrl_testimonio"
            })*/

                    //// BUSCAR CLIENTES
                    /*.when("/clientes_buscar", {
                        templateUrl: directorioVistas + dirApp + "cliente/cliente_busqueda.html",
                        controller: "ctrl_busquedas"
                    })*/

                    /*//// TACHADO DE FIRMA
                    .when("/tachado_de_firmas", {
                        templateUrl: directorioVistas + dirApp + "firmas/tachado_firmas.html",
                        controller: "ctrl_tachado_firmas"
                    })*/

                    /////TARJETA DOCUMENTOS

                    .when("/documentos", {
                        templateUrl: directorioVistas + dirApp + "documentos/doc_listado.html",
                    })/*.when("/documentos_registro", {
                templateUrl: directorioVistas + dirApp + "documentos/doc_registro.html",
                controller: "ctrl_documento_registro"
            })*/


                    ////SUBIR DOCUMENTO
                    /*.when("/subir_documento", {
                        templateUrl: directorioVistas + dirApp + "documentos/subir_documento.html",
                        controller: "ctrl_testimonio"
                    })*/
                    ////
                    /*.when("/documentos_elaborados", {
                        templateUrl: directorioVistas + dirApp + "documentos/documentos_elaborados.html",
                        controller: "ctrl_testimonio"
                    })*/



                    ////ELABORACION DE CARATULA
                   /* .when("/elaboracion_caratula", {
                        templateUrl: directorioVistas + dirApp + "documentos/elaboracion_caratula.html",
                    })*/
                    ////INICIO DE TAREA
                    /*.when("/inicio_de_tarea", {
                        templateUrl: directorioVistas + dirApp + "tareas/inicio_tarea.html",
                    })

                    ////FINALIZAR TAREA
                    .when("/finalizar_tarea", {
                        templateUrl: directorioVistas + dirApp + "tareas/finalizar_tarea.html",
                    })*/
                    ////CITAS
                    /*.when("/agenda_citas", {
                        templateUrl: directorioVistas + dirApp + "calendario/citas.html",
                        controller: "ctrl_citas"
                    })*/

                    ////NUEVO PROCESO
                    .when("/nuevo_proceso", {
                        templateUrl: directorioVistas + dirApp + "generales/nuevo_proceso.html",
                    })
                    
                    .when("/edit_tramite", {
                        templateUrl: directorioVistas + dirApp + "generales/edit_tramite.html",
                    })
                     .when("/clone_expediente", {
                        templateUrl: directorioVistas + dirApp + "generales/clone_expediente.html",
                    })

                    ////PAGOS
                    /*.when("/lista_solicitud_de_pago", {
                        templateUrl: directorioVistas + dirApp + "pagos/lista_solicitud_de_pago.html",
                        controller: "ctrl_lista_pago_solicitud"
                    })
                    .when("/lista_solicitud_de_pago_pendientes", {
                        templateUrl: directorioVistas + dirApp + "pagos/lista_solicitud_de_pago_p.html",
                        controller: "ctrl_lista_pago_solicitud"
                    })
                    .when("/solicitud_de_pago", {
                        templateUrl: directorioVistas + dirApp + "pagos/solicitud_de_pago.html",
                        controller: "ctrl_pago_solicitud"
                    })
                    .when("/recepcion_de_pago1", {
                        templateUrl: directorioVistas + dirApp + "pagos/recepcion_de_pago1.html",
                        controller: "ctrl_pago1"
                    })
                    .when("/recepcion_de_pago2", {
                        templateUrl: directorioVistas + dirApp + "pagos/recepcion_de_pago2.html",
                        controller: "ctrl_pago2"

                    })
                    .when("/recepcion_de_pago3", {
                        templateUrl: directorioVistas + dirApp + "pagos/recepcion_de_pago3.html",
                        controller: "ctrl_pago3"

                    })*/

                    // DIRECTORIO DE ABOGADOS
                    .when("/directorio_abogados", {
                        templateUrl: directorioVistas + dirApp + "dir_abogados/abogados.html",
                       // controller: "ctrl_dir_abogados"
                    })
                    // DIRECTORIO DE GESTORES
                    /*.when("/gestores", {
                        templateUrl: directorioVistas + dirApp + "dir_gestores/gestores.html",
                        //controller: "ctrl_dir_gestores"
                    })
                    // DIRECTORIO DE VALUADORES
                    .when("/valuadores", {
                        templateUrl: directorioVistas + dirApp + "dir_valuadores/valuadores.html",
                        controller: "ctrl_dir_valuadores"
                    })*/
                        .when("/mesa_control", {
                        templateUrl: directorioVistas + dirApp + "mesa_control/mesactrl.html"
                    }) .when("/mesa_control_detalle", {
                        templateUrl: directorioVistas + dirApp + "mesa_control/detalle_mesactrl.html"
                    }).when("/pagos", {
                        templateUrl: directorioVistas + dirApp + "pagos/pagos_lista.html"
                    }).when("/tareasbo", {
                        templateUrl: directorioVistas + dirApp + "tareas/asignacion_de_tarea.html"
                    })



                    // CONECTIVIDAD 

                    .when("/conectividad/expedientes", {
                        templateUrl: directorioVistas + dirCon + "index.html",
                        //controller: "ctrl_conectividad"
                    })


                    .when("/conectividad/expedientes/detalle", {
                        templateUrl: directorioVistas + dirCon + "detalle.html",
                        //controller: "ctrl_conectividad_detalle"
                    })



                    ///OPERACIÓN FOLIOS

                    .when("/folios/prestados", {
                        templateUrl: directorioVistas + dirFol + "/operacion/prestados.html",
                    })
                    .when("/folios/solicitados", {
                        templateUrl: directorioVistas + dirFol + "/operacion/solicitados.html",
                    })
                    .when("/folios/devueltos", {
                        templateUrl: directorioVistas + dirFol + "/operacion/devueltos.html",
                    })
                    .when("/folios/solicitud_folios", {
                        templateUrl: directorioVistas + dirFol + "/operacion/solicitudFolio.html",
                    })
                            .when("/folios/solicitud_folios_abogado", {
                        templateUrl: directorioVistas + dirFol + "/operacion/solicitudFolioAbogado.html",
                    })

                    .when("/folios/reportes", {
                        templateUrl: directorioVistas + dirFol + "/operacion/reporteFolios.html",
                        controller: ""
                    })

                    .when("/folios/buscador", {
                        templateUrl: directorioVistas + dirFol + "/operacion/buscador_folios.html"
                    })
                    
                
                    
                    





            $routeProvider.when("/error", {
                templateUrl: directorioVistas + "error.html"
            }).otherwise({
                redirectTo: "/error"
            });




        }]);
});



