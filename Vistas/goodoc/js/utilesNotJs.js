console.log("entrando a utilesNotJs");
var meses = "enero_febrero_marzo_abril_mayo_junio_julio_agosto_septiembre_octubre_noviembre_diciembre".split("_");
var dias = "domingo_lunes_martes_miércoles_jueves_viernes_sábado".split("_");
var alerta_container = $("#alertaContainer");
var usr_global;
var generalCount = 0;
var cerrarSesion = false;

function getTipoTarea(tipo){
    var respuesta = "";
    if(tipo.indexOf("Tachado de firmas")>-1){
        respuesta = "exp";
    }else if(tipo.indexOf("La escritura No.")>-1){
        respuesta = "esc";
    }else if(tipo.indexOf("Liberar pago")>-1){
        respuesta= "liberacaja";
    }else if(tipo.indexOf("Mesa de control")>-1 || tipo.indexOf("Subir acuse")>-1 || tipo.indexOf("Ingresa DIM")>-1){
        respuesta= "gestionaimpuesto";
    }
    return respuesta;
}

function parseFecha(fecha) {
    console.info("MI DATO ENTRO");
    if (typeof fecha == "undefined" || fecha == null || fecha == "" || fecha == "null")
    {
        fecha = "01-01-1970"
    }
    from = fecha.split("-");
    if (from[2].indexOf("T") != -1) {
        tparse = from[2].substring(0, from[2].indexOf("T"))
        tparseFin = from[0];
    } else {
        console.log("no tengo")
        tparse = from[2];
        tparseFin = from[0];
    }
    f = tparse + "-" + from[1] + "-" + tparseFin;
    return f;
}
function force_redirect(url) {
    window.location.href = url;
}
function viewSearch() {
    $("#breadCrumbs").slideToggle("slow");
    $("#busqueda_bread").find("#buscador").slideToggle("slow");
    $("#search_avanzada").delay(600).slideToggle("slow");
    if ($("#search_avanzada").is(":visible")) {
        btn_search = "";
    } else {
        btn_search = "top #05a742 url(img/assets/btns/spr_search.png)";
    }
    $("#btn_search").attr("style", "background:" + btn_search);
    rm_activeBtn("#search_avanzadaLista");
}

var vbitacora = false;
function viewBitacora(){
    btn_search = (vbitacora)?"":"top #05a742 url(img/assets/btns/spr_conf.png)";;
    vbitacora = (vbitacora)?false:true;
    $("#btn_bitacora").attr("style", "background:" + btn_search);
    $("#bitacora_usuario").slideToggle("slow");
}



function activeBtn(elemento) {
    $(elemento).parent().find(".active").removeClass("active");
    $(elemento).addClass("active");
}
function rm_activeBtn(contenedor) {
    $(contenedor).find(".active").removeClass("active");
}
activeBtn("#search_avanzadaLista li");
centrarElemento('#wra_login', "#login");
function centrarElemento(contenedor, elemento) {
    w = $(window).height();
    f = $("footer").height() + 2;
    c = $(contenedor);
    e = $(elemento);
    tc = (w - f);
    me = (tc - (e.height())) * .15;
    if (w > (c.height() + f)) {
    } else {
    }
}
function switchElemento(e1, e2) {
    var e1 = $(e1);
    var e2 = $(e2);
    var vel = "slow";
    e1.slideToggle(vel, function () {
        e2.slideToggle(vel);
    });
}
var posIniAlert = "-2000px";
var posFinalert = "10px";
function muestraAlerta() {
    if (alerta_container.is(":animated"))
        return false;
    alerta_container.css("right", posIniAlert);
    alerta_container.animate({"right": posFinalert}, "slow", function () {
        setTimeout(escondeAlerta, 5000);
    });
}
function escondeAlerta() {
    alerta_container.animate({"right": posIniAlert}, "slow");
}
function ejecutaAlerta(tipo, mensaje) {
    switch (tipo) {
        case 1:
            var bg = "green";
            break;
        case 2:
            var bg = "red";
            break;
        default:
            var bg = "orange";
    }
    alerta_container.css({"background": bg});
    alerta_container.find("h2").text(mensaje);
    muestraAlerta();
}
function escondeElementos(e, cb) {
    $(e).each(function () {
        $(this).hide("fast");
    })
    if (typeof (cb) !== "undefined") {
        cb();
    }
}
function muestraAcordeon(e) {
    $(e).parent().find('.wrap_subBtn').slideToggle("slow");
}
function acordeonMenu(e) {
    escondeElementos('.wrap_subBtn', function () {
        muestraAcordeon(e);
    });
}

var editor;
function removeCK()
{

   // console.info("**********************" + CKEDITOR.instances);
    CKEDITOR.instances['plan_ck'].destroy();
   // console.info(CKEDITOR.instances);
    $(".pla_for_ck_content").html('');
}




function creaCK(wrapper) {

    $(".pla_for_ck_content").html(" ");
    $(wrapper).find(".pla_for_ck_content").html('<textarea id="plan_ck"></textarea>');

    CKEDITOR.replace('plan_ck', {
        extraPlugins: 'vars,guardar,print,validador_plantilla',
        resize_enabled: false,
        height: 370
    })

}


function lanzaOverlay(callback) {
    $('.overlay').fadeToggle("slow", callback);
}
function pla_for_cambia_paso(inicio, fin) {
    $(".etabs li:nth-of-type(" + inicio + ")").removeClass('active');
    $(".etabs li:nth-of-type(" + fin + ")").addClass('active');
}
function listAcordeon(trigger) {
    $("#wrapp_operaciones").find(".wra_content_tarea").slideUp("slow");
    if ($(trigger).parent().parent().find(".wra_content_tarea").is(":visible")) {
        $(trigger).parent().parent().find(".wra_content_tarea").slideUp("slow");
        return false;
    }
    ;
    genericAcordeon($(trigger).parent().parent().find(".wra_content_tarea"));
}
function actoAcordeon(trigger) {
    t = $(trigger).parent().parent().find(".dd_expe_wrap");
    $(".wrap_subOperaciones").find(".dd_expe_wrap").slideUp("slow");
    if (t.is(":visible")) {
        t.slideUp("slow");
        return false;
    }
    genericAcordeon(t);
    pos = ($(trigger).is(":visible")) ? "bottom" : "top";
    pos = ($(trigger).find(".tbl_icon").css("background-position") == "50% 0%") ? "bottom" : "top";
    $(trigger).find(".tbl_icon").attr("style", "background-position:" + pos + " !important");
}
function calendarAcordeon(trigger) {
    var t = $(trigger).parent().parent().parent().parent().parent().find(".cita_detalle");
    if (t.is(":visible")) {
        t.slideUp("slow");
        return;
    }
    $(".cita_detalle").slideUp("slow", function () {
        setTimeout(function () {
            t.slideDown("slow");
        }, 500);
    });
}
function genericAcordeon(content) {
    content.slideToggle("slow");
}
function showNavExp(url) {
    var arrExp = ["/asignacion_operaciones", "/comparecientes", "/comparecientes_edicion", "/comparecientes_moral", "/comparecientes_fisica", "/comparecientes_representantes", "/escrituras", "/escrituras_agregar", "/escrituras_consulta", "/inmuebles", "/inmueble_agregar", "/inmuebles_captura_domicilio", "/inmuebles_captura_inmueble", "/presupuesto", "/presupuesto_agregar", "/tarjeta_amarilla", "/tarjeta_amarilla_agregar", "/documentos", "/documentos_registro", "/testimonios", "/testimonios_validacion", "/lista_solicitud_de_pago", "/antecedentes", "/antecedentes_detalle", "/antecedentes_documentos", "/antecedentes_operativos"];
    var isExpediente = $.inArray(url, arrExp);
    n = $(".navExpediente_wrap");
    if (isExpediente != -1) {
        n.slideDown("slow");
        return true;
    } else {
        n.slideUp("slow");
        return false;
    }
}
function formato_numero(numero, decimales, separador_decimal, separador_miles) {
    numero = parseFloat(numero);
    if (isNaN(numero)) {
        return"";
    }
    if (decimales !== undefined) {
        numero = numero.toFixed(decimales);
    }
    numero = numero.toString().replace(".", separador_decimal !== undefined ? separador_decimal : ",");
    if (separador_miles) {
        var miles = new RegExp("(-?[0-9]+)([0-9]{3})");
        while (miles.test(numero)) {
            numero = numero.replace(miles, "$1" + separador_miles + "$2");
        }
    }
    return numero;
}
(function () {
    var convertBase = function (num) {
        this.from = function (baseFrom) {
            this.to = function (baseTo) {
                return parseInt(num, baseFrom).toString(baseTo);
            };
            return this;
        };
        return this;
    };
    this.bin2dec = function (num) {
        return convertBase(num).from(2).to(10);
    };
    this.bin2hex = function (num) {
        return convertBase(num).from(2).to(16);
    };
    this.dec2bin = function (num) {
        return convertBase(num).from(10).to(2);
    };
    this.dec2hex = function (num) {
        return convertBase(num).from(10).to(16);
    };
    this.hex2bin = function (num) {
        return convertBase(num).from(16).to(2);
    };
    this.hex2dec = function (num) {
        return convertBase(num).from(16).to(10);
    };
    return this;
})();
function deformato_numero(numero) {
    n = numero.toString();
    n = n.replace(/,/g, "")
    return parseFloat(n);
}
function reset_campos(formulario) {
    document.forms[formulario].reset();
}
function is_asist(checked) {
    if (!checked) {
        $("#content_asist").slideUp("slow");
        $("#d_completo").removeAttr("disabled");
    } else {
        $("#content_asist").slideDown("slow");
        $("#d_completo").attr("disabled", "disabled");
    }
}
function guardar_escritura() {
    console.info("aqui andamos en el utiles")
    angular.element($("#wrap_prin_center").scope().$apply(function (scope) {
    	console.info(scope);
        scope.guarda_escritura(0);
    }))
}
function validar_plantilla() {
    angular.element($("#content_admin").scope().$apply(function (scope) {
        scope.validar_plantilla();
    }))
}
function get_usr_global() {
    return usr_global;
}
function set_usr_global(usr) {
    usr_global = usr;
}
function subioArchivo() {
    angular.element($("#panel_lateral_content").scope().$apply(function (scope) {
        scope.popLateral('', 2, false, true);
    }))
}
function pon_fecha(campo) {
    $(campo).text(moment().format("dddd DD  MMMM HH:mm"));
}
function pon_valor(objeto) {
    $("#txt_" + objeto.id).text(objeto.value);
}
function insertStringAt(src, index, str) {
    return src.substr(0, index) + str + src.substr(index)
}
function obtieneTiempo(pos, total_componentes, segundos) {
    var tiempo = 0;
    tiempo = pos * -1;
    tiempo = parseInt(total_componentes) + parseInt(tiempo);
    tiempo = tiempo * parseInt(segundos);
    return tiempo;
}

function getQueryParams(qs) {
    qs = qs.split('+').join(' ');

    var params = {},
        tokens,
        re = /[?&]?([^=]+)=([^&]*)/g;

    while (tokens = re.exec(qs)) {
        params[decodeURIComponent(tokens[1])] = decodeURIComponent(tokens[2]);
    }

    return params;
}
 
