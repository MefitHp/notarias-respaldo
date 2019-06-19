function ctrl_ayuda($scope, $timeout) {
    $scope.videoactual = {}
    $scope.videos = [
        {"titulo": "INGRESO AL SISTEMA",
            "descripcion": "Guía para acceder al Sistema Notarial y muestra de sus módulos generales.",
            "icon": "fa-key",
            "urlwebm": "videos/webm/login.webm",
            "urlmp4": "videos/mp4/login.mp4"
        },
        {"titulo": "TRÁMITE",
            "descripcion": "Guía para la generación inicial de un trámite y asignación de su localidad, con actos.",
            "icon": "fa-folder-open-o",    
            "urlwebm": "videos/webm/tramite.webm",
            "urlmp4": "videos/mp4/tramite.mp4"
        },
        {"titulo": "TRÁMITE CON BUSCADOR",
            "descripcion": "Guía de la busqueda de un cliente ya inscrito en el Sistema Notarial y la asignación a un nuevo trámite.",
            "icon": "fa-search",
            "urlwebm": "videos/webm/tramite_buscador.webm",
            "urlmp4": "videos/mp4/tramite_buscador.mp4"},
        {"titulo": "TARJETÓN",
            "descripcion": "Guía para acceder al tarjetón y muestra de sus componentes.",
            "icon": "fa-file-text-o",
            
            "urlwebm": "videos/webm/tarjeton.webm",
            "urlmp4": "videos/mp4/tarjeton.mp4"},
        {"titulo": "OTORGANTES",
            "descripcion": "Guía para dar de alta un nuevo otorgante y la asignación de un cliente ya existente como otorgante en un acto nuevo.",
            "icon": "fa-users",
            
            "urlwebm": "videos/webm/otorgantes.webm",
            "urlmp4": "videos/mp4/otorgantes.mp4"},
        {"titulo": "FORMULARIOS",
            "descripcion": "Guía de captura de datos en el tarjetón para la generación de los documentos notariales.",
            "icon": "fa-pencil-square-o",
            
            "urlwebm": "videos/webm/formularios.webm",
            "urlmp4": "videos/mp4/formularios.mp4"},
        {"titulo": "GENERACIÓN DE LA ESCRITURA",
            "descripcion": "Guía para la generación de una escritura, asignación de un Notario y actualización de datos.",
            "icon": "fa-file-text-o",
            
            "urlwebm": "videos/webm/login.webm",
            "urlmp4": "videos/mp4/login.mp4"},
        {"titulo": "FORMATO DE ESCRITURA",
            "descripcion": "Guía de asignación de datos a escritura (fecha de pase y número) y la generación de una escritura lista para impresión.",
            "icon": "fa-file-text-o",
            
            "urlwebm": "videos/webm/login.webm",
            "urlmp4": "videos/mp4/login.mp4"},
        {"titulo": "DOCUMENTOS",
            "descripcion": "Guía de generación y uso de documentos previos, posteriores y evidencias.",
            "icon": "fa-file-o",
            
            "urlwebm": "videos/webm/login.webm",
            "urlmp4": "videos/mp4/login.mp4"},
        {"titulo": "OTORGANTE AUTORIZANTE",
            "descripcion": "Guía para la captura de un autorizante dentro de un acto.",
            "icon": "fa-home",
            
            "urlwebm": "videos/webm/login.webm",
            "urlmp4": "videos/mp4/login.mp4"}
    ]



    $timeout(function () {

        $("#container_videos").fadeIn("fast")

        $("#container_videos").gridalicious({
            animate: true,
            width: "250px",
            gutter: 30,
            animationOptions: {
                queue: true,
                speed: 200,
                duration: 300,
                effect: 'fadeInOnAppear',
            }
        });
    }, 100)



    $scope.showVideo = function (video) {
        $("#video_popup").html("<div id='close' onclick='Avgrund.hide()'><i class='fa fa-close'></i></div>");
 
        $('<video/>', {
               "width":"100%",
               "controls":true,
               "autoplay":true
            }).append('<source src="'+video.urlwebm+'" type="video/webm">')
                    .appendTo('#video_popup');
        
        
        $scope.videoactual = {
            "webm":video.urlwebm,
            "mp4":video.urlmp4,
        }
        Avgrund.show("#video_popup");
        $("#video_popup").fadeIn("slow");
    }


}

    