/** attach controllers to this module 
 * if you get 'unknown {x}Provider' errors from angular, be sure they are
 * properly referenced in one of the module dependencies in the array.
 * below, you can see we bring in our services and constants modules 
 * which avails each controller of, for example, the `config` constants object.
 **/


"use strict";


// SWITCH QUE MANEJA LA CARGA DE MÓDULOS HACIA LA APLICACIÓN
var controllers_data;
switch(shim){
        
    case "_log":
        
        controllers_data = ['./data/ctrl_login','./data/ctrl_restore'];
        
        break;
    case "_adm":
        
        controllers_data = [
    './data/ctrl_login',
    './data/ctrl_index',
    //'./data/CRUD/ctrl_actos',
    //'./data/CRUD/ctrl_actosDocumento',
   //YA//'./data/CRUD/ctrl_catalogos',
    //'./data/CRUD/ctrl_docNotarial',
    //YA//'./data/CRUD/ctrl_docobj',
    './data/CRUD/ctrl_docobj_dir',
    //'./data/CRUD/ctrl_documentos',
    //'./data/CRUD/ctrl_expDocumentos',
    //'./data/CRUD/ctrl_expedientes',
    //YA//'./data/CRUD/ctrl_formatopdf',
    //YA//'./data/CRUD/ctrl_formatos',
    './data/CRUD/ctrl_formatos_dir',
    //YA//'./data/CRUD/ctrl_frm_dinamico',
    //YA//'./data/CRUD/ctrl_gestores',
    //NO SE OCUPAN'./data/CRUD/ctrl_grupos_def',
    //YA//'./data/CRUD/ctrl_lista_frm_dinamico',
    //YA//'./data/CRUD/ctrl_listar_formato_pdf',
    //YA//'./data/CRUD/ctrl_notaria',
    //YA//'./data/CRUD/ctrl_operaciones',
    //YA//'./data/CRUD/ctrl_plantillas',
    //YA//'./data/CRUD/ctrl_plantillas_agregar',
    './data/CRUD/ctrl_plantillas_dir',
    //YA//'./data/CRUD/ctrl_prev_post_config',
    //YA//'./data/CRUD/ctrl_roles',
    //YA//'./data/CRUD/ctrl_usuarios',
    //YA//'./data/CRUD/ctrl_valuadores',
    //YA//'./data/CRUD/ctrl_variables',
    //YA//'./data/CRUD/ctrl_variables_def_exp',
    //YA//'./data/CRUD/ctrl_agregar_folios',
    //YA//'./data/CRUD/ctrl_registro_escritura',
        ];
        
        break;
        
    default:
        controllers_data = [
            './data/ctrl_login',
            './data/ctrl_index',
             // ANTECEDENTES
            //YA//'./data/APP/ctrl_antecedentes_documentos',
            //YA//'./data/APP/ctrl_antecedentes_operativos',
            //ASIGNACION OPERACIONES
            //YA// './data/APP/ctrl_asignacion_operaciones',
            //    CLIENTES BUSCAR
            //NO SE DONDE SE OCUPA//'./data/APP/ctrl_busquedas',
            // CITAS // PENDIENTE
            /*'./data/APP/ctrl_citas',
            './data/APP/ctrl_citas_agendar',
            './data/APP/ctrl_citas_editar',*/
            /// COMPARECIENTES
            //YA//'./data/APP/ctrl_comparecientes',
            //YA//
            //'./data/APP/ctrl_conyuge',
            /* Registro de identificación */
            //YA//'./data/APP/ctrl_ri_registro',
            //DIRECTORIOS 

            /**** ABOGADOS ****/
            //YA//'./data/APP/ctrl_dir_abogados',
            /**** GESTORES ****/
            //NO SE OCUPA'./data/APP/ctrl_dir_gestores',
            /**** VALUADORES ****/
            //NO SE OCUPA//'./data/APP/ctrl_dir_valuadores',
            /// FIN DIRECTORIOS


            //GESTIÓN DE DOCUMENTOS

            //'./data/APP/ctrl_documentos',
            //'./data/APP/ctrl_documento_registro',
            // ESCRITURAS
            //YA//'./data/APP/ctrl_escrituras',
            //YA//'./data/APP/ctrl_escrituras_edit',
            //'./data/APP/ctrl_escritura_elaboracion',
            //YA//'./data/APP/ctrl_escritura_doc_obj',
            // EXPEDIENTE
            //'./data/APP/ctrl_expedientes_listar',
            //YA//'./data/APP/ctrl_expedientes_compartir',
            
            //DATOS GENERALES DEL SISTEMA
            //YA//'./data/APP/ctrl_generales',
            // LISTAS DE DATOS
            //YA//'./data/APP/ctrl_list_comparecientes',
            //NO SE OCUPA//'./data/APP/ctrl_list_tarjeta_am',
            //NO SE OCUPA//'./data/APP/ctrl_lista_pago_solicitud',
            // DETALLE OPERACIONES (( PANTALLAASIGNACION DE OPERACIONES ))
            './data/APP/ctrl_operaciones_actos',
            /// PAGOS
            /*'./data/APP/ctrl_pago1',
            './data/APP/ctrl_pago2',
            './data/APP/ctrl_pago3',
            './data/APP/ctrl_pago_solicitud',*/
            // PRESUPUESTO
            //'./data/APP/ctrl_presupuesto',
            // GESTOR DE DOCUMENTOS (PREVIOS Y POSTERIORES)
            //'./data/APP/ctrl_prev_post',
            // ÚTIL PARA SUBIR DOCUMENTOS
            //'./data/APP/ctrl_subir_doc',
            // TACHADO DE FIRMAS
            //'./data/APP/ctrl_tachado_firmas',
            // TARJETA AMARILLA
            //'./data/APP/ctrl_tarjeta_amarilla',
            // TESTIMONIOS
            //'./data/APP/ctrl_testimonio',
            //TRAMITE
            //'./data/APP/ctrl_tramite',
            ////////////
            /// UTILIDADES COMO DOMICILIO
             './data/APP/ctrl_utilidades',
             
             
             
            //CONECTIVIDAD
             //YA//'./data/CONE/ctrl_conectividad',
             //YA//'./data/CONE/ctrl_conectividad_detalle',
             
             // FOLIOS
             
             //'./data/FOL/ctrl_solicitud_folio',
             //YA//'./data/FOL/ctrl_listar_prestados',
             //YA//'./data/FOL/ctrl_listar_devueltos',
             //'./data/FOL/ctrl_autorizar_prestamo',             
             
    
        ]
        
}

define(controllers_data, function() {
});
