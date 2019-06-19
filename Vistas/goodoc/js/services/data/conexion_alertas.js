define(['../module'],function(service){
    'use strict';
    service.factory("con_tipo",ser_tipo);
    service.factory("con_step",ser_step);
});
    
var ser_tipo = function($resource){
    return $resource(urlAlertas,{tipo:"tipoalertas",id:"@id"},{
       //tipo_save:{method:'POST',params:{},isArray:false},
       //tipo_delete:{method:'POST',params:{tipo:"tipoalertas"},isArray:false},
    
    });
};
var ser_step = function($resource){
    return $resource(urlAlertas,{tipo:"steps",id:"@id"},{
       /*step_listar:{method:'GET',params:{tipo:"steps"},isArray:true},
       step_save:{method:'POST',params:{tipo:"steps"},isArray:false},
       step_delete:{method:'POST',params:{tipo:"steps"},isArray:false}*/
    });
};