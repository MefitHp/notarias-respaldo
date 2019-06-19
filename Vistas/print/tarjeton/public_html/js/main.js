$(function(){
    
    Handlebars.registerHelper("prettifyDate", function(timestamp) {
    return new Date(timestamp).toLocaleString('en-ES');
    });

    
    var intlData = {
        "locales": "es-ES"
    };
    
    //var base_url = window.location.origin;
    var base_url = "http://localhost";

    
    var data = {
        "usuario":{
           "idusuario" : $.urlParam("idusuario"),
           "idsesionactual" : $.urlParam("idsesionactual")  
        },
        "tramite":{
            "idtramite" : $.urlParam("idtramite")
        }
    };
   $.ajax({
        type: "POST",
        url: base_url+":9090/notarias-web/notaria/tramite/obtenerDatosTarjetonCompleto",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        success: function(data){
            var tarjetonutils = Tarjeton;
            var datostarjeton = tarjetonutils.organizaDatosTarjeton(data.datosFormulariosTarjeton);
            var d = new Date();
            var fecha = d.toLocaleString('en-ES');
            var expediente = data.expediente;
            var tramite = expediente.tramite;
            var cliente = tramite.cliente.dsnombrecompleto;
           
            var abogado = expediente.abogado.dsnombre +" "+expediente.abogado.dspaterno+" "+expediente.abogado.dsmaterno;
            var context = {
                "datostarjeton":datostarjeton,
                "comparecientes":data.comparecientesCompletos,
                "comentarios":data.comentariosExpediente,
                "documentos":data.documentos,
                "date":fecha,
                "actos":data.actos,
                "cliente":cliente,
                "abogado":abogado,
                "expediente":expediente.numexpediente,
                "localidad":tramite.locacion.dselemento
                
            }
            tarjetonutils.compilaDatos("valores-tarjeton-template","valores-tarjeton-output",context);
            tarjetonutils.compilaDatos("valores-documentos-template","documentos-output",context);
            tarjetonutils.compilaDatos("compareciente-template","compareciente-output",context);
            tarjetonutils.compilaDatos("fecha-template","fecha-output",context);
            tarjetonutils.compilaDatos("title-template","title-output",context);
            tarjetonutils.compilaDatos("comentarios-template","comentarios-output",context);
            
        }
      });

})

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
       return null;
    }
    else{
       return results[1] || 0;
    }
}

var Tarjeton = {
    compilaDatos : function(idtemplate,idcontenedor,datos){
        // Grab the template script
        var theTemplateScript = $("#"+idtemplate).html();

        // Compile the template
        var theTemplate = Handlebars.compile(theTemplateScript);
         // Pass our data to the template
        var theCompiledHtml = theTemplate(datos);

        // Add the compiled html to the page
        $('#'+idcontenedor).html(theCompiledHtml);
        
    },
    organizaDatosTarjeton:function(datostarjeton){
        
        var response = [];
        $.each(datostarjeton, function( formulario, valoresform ) {
                var dato = {titulo:formulario};
                var valores = [];
                 /**
                    * Parseo la informacion
                    */
                $.each(valoresform, function( dato, valor ) {
                    var valorform = {"nombre":dato,"valor":valor};
                    valores.push(valorform);
                });
                dato.valores = valores;
                response.push(dato);
              });
              return response;
    }
};


