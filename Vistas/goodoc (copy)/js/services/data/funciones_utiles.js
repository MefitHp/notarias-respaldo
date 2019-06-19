define(['../module', 'jquery'], function(service) {
    'use strict';

    service.factory('funcionesutiles', function($rootScope,$templateCache) {
        return{
            popLateral: function(url_cargar, columnas, lpop) {
                    
                this.carga_contenido(url_cargar);
                var g = $("#contenedor_general");
                var p = $("#contenedor_popUp");
                var wh = $(window).height();
                $("#contenedor_general").css({"overflow": "hidden", "height": wh});
                $("#contenedor_popUp").css({"height": (wh) - 5});

                var alat;
                var agen;

                $("#contenedor_popUp").addClass("panelLateral" + columnas);

                switch (columnas) {
                    case 1:
                        alat = 240;
                        break;
                    case 2:
                        alat = 470;
                        break;

                    default:
                        alat = 240;
                        break;
                }

                agen = ($(window).width()) - alat;

                if ($("#contenedor_general").is(":animated") || $("#contenedor_popUp").is(":animated"))
                    return false;


                var pw = (lpop) ? alat + "px" : "0px";
                var gw = (lpop) ? agen + "px" : "100%";

                if (lpop) {

                    $("#contenedor_general").animate({"width": gw}, "fast", function() {
                        $("#contenedor_popUp").animate({"width": pw}, "slow");
                        $(".allContentPanel").fadeIn(1500);
                    });

                } else {
                    $(".allContentPanel").fadeOut("slow", function() {
                        
                        $("#contenedor_popUp").animate({"width": pw}, 1000, function() {
                            $("#contenedor_general").animate({"width": gw}, 1010);
                        });
                        $("#contenedor_popUp").removeClass("panelLateral" + columnas);
                    });
                }
                $("#contenedor_general").css({"overflow": "auto"});
            },
            
            carga_contenido: function(url) {
                if(url==""){
                    setTimeout(function(){
                        $rootScope.template = "views/lateral/subir_documento.html";
                        console.info("borro el template" , $rootScope.template);
                    },1000)
                }else{
                    $rootScope.template = "views/lateral/" + url + '.html?c='+moment();
                    console.info("actualizo el template" , $rootScope.template + moment());
                }
            },
            
        fecaltoTimeStamp : function(fecha) {
            var tmp_f = fecha.split("/");
            tmp_f[0] = parseFecha(tmp_f[0]);
            tmp_f[0] = tmp_f[0].replace(/ /gi, "");
            return tmp_f[0] + "" + tmp_f[1] + ":00";
        },
        
        fecaltoUnix : function(fecha) {
            var tmp_f = fecha.split("/");
            tmp_f[0] = parseFecha(tmp_f[0]);
            tmp_f[0] = tmp_f[0].replace(/ /gi, "");
            return moment(tmp_f[0] + " " + tmp_f[1]).format("X");
        },
        
        fecalUnixtoDate:function(fecha){
           console.info(fecha);
           return moment.unix(fecha).format("DD-MM-YYYY HH:ss");
           
        }

        }

    });

});
    