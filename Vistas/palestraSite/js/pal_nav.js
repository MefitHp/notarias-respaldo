
function slideDefault(slide) {

    if (slide != 0) {
        $("#logo-link").fadeOut("slow");
        document.getElementById("audio-home").pause();
        $("#sound-button").removeClass("sound-on").addClass("sound-off")
    } else {
        $("#logo-link").fadeIn("slow");
        document.getElementById("audio-home").play();
        $("#sound-button").removeClass("sound-off").addClass("sound-on");
    }

}


function muestraLogo(elementos) {
    tiempo = 1000;
    $(elementos).each(function(i) {
        $(this).delay(tiempo * (i / 2)).fadeIn("slow");
    })
}

function comporSlide(slide) {

    escondeElemets("#beyond-journey div div img", false);
    escondeElemets("#slide3-middle", false);
    escondeElemets("#slide4-middle", false);
    escondeElemets(".wra_clientes div img", false);

    switch (slide) {
        case 0:

            break;

        case 1:
            btns_pops();
            break;

        case 2:
            slideOneImg("#slide3-middle");
            document.getElementById("video-business").play();
            break;

        case 3:
            slideOneImg("#slide4-middle");
            break;

        case 4:
            slideOneImg("#slide5-middle");
            break;

        case 5:
            setTimeout(function() {
                muestraLogo(".wra_clientes div img");
            }, 1000);

            break;

        case 6:
            document.getElementById("video-contact").play();
            break;

        case 5:
            muestraLogo(".wra_clientes div img");
            break;

        default:

            break;

    }


    slideDefault(slide);

}


function escondeElemets(selector, tipo, fn) {
    if (typeof fn == "undefined" || fn == null || fn == undefined) {
        fn = function() {
        };
    }
    ;
    if (tipo) {
        $(selector).fadeIn("slow", fn);
    } else {
        $(selector).fadeOut("slow", fn);
    }
}


function btns_pops() {
    setTimeout(function() {
        $("#beyond-journey div div img").each(function(i) {
            t = 700 * i;
            $(this).delay(t).fadeIn("slow");
        })
    }, 1000);

}


function slideOneImg(selector) {

    m = $(selector);
    t = m.find("h2");
    p = m.find("p");
    b = m.find(".button");

    m.hide("fast");
    t.hide("fast");
    p.hide("fast");
    b.hide("fast");

    setTimeout(function() {
        escondeElemets(m, true, function() {
            escondeElemets(t, true, function() {
                escondeElemets(p, true, function() {
                    escondeElemets(b, true);
                });
            });
        });
    }, 1000);

}


var padding = 20;
var paddingTotal = padding * 2;
function creaDivDetalle() {
    var div = $("#detalle_content");
    div.attr("style", "height:" + ($(window).height()) + "px;")
}
function muestraDetalle(m, color, callback) {
    var w_detalle = $("#detalle_content");
    var setColor = getColor(color);
    w_detalle.css("background-color", setColor);

    if (m) {
        w_detalle.animate({"width": $(window).width() + "px"}, 1000, function() {
            if (typeof callback != "undefined")
                callback();
            $(".close_detalle").fadeIn("slow");
        });
    }
    else {
        $(".close_detalle").hide("slow");
        $("#contenido_detalle").fadeOut("slow", function() {

            w_detalle.animate({"width": "0"}, 1000, function() {
                if (typeof callback != "undefined")
                    callback();
            });
        })

    }
}

function getColor(color) {
    var colors = ["", "#6A4E87", "#00ACA3", "#6B6556"]
    return colors[color];
}
function loadDetalle(carga, color) {
    var dir = "secciones/";
    var extension = ".html";
    var div_contenido = $("#contenido_detalle");
    div_contenido.load(dir + carga + extension, muestraDetalle(true, color, function() {
        div_contenido.fadeIn("slow");
    }));
}
            
/*
    $("#video-home").bind("ended", function() {
        document.getElementById('video-home').play();
        alert("inicio denuevo")
    });
    */