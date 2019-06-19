function creaTarea(e,catalogos,scope) {
    var e = $(e);
    var t = e.find(".wrap_contentList");
    var n = e.find(".wra_content_tarea");
    var r = t.find(".wra_content_comentario");
    var i = e.find(".list_more");
    var o = e.find(".prioridad");
    var u = e.find(".comentario");
    var a = t.find(".btn_comentario");
    var f = function() {
        if (n.is(":visible")) return;
        p();
        setTimeout(function() {
            if (!c) {
                h()
            }
        }, 600)
    };
    var l = function() {
        /*s = $(this).attr("status");
        switch (s) {
            case "0":
                var e = "Desea hacer este expediente prioritario";
                var t = "1";
                break;
            case "1":
                var e = "Desea que este expediente deje de ser prioritario";
                var t = "0";
                break;
            default:
                var e = "La prioridad que tiene este documento aun no esta definida consulte al soporte técnico"
        }
        if (confirm(e)) {
            $(this).removeClass("btn_list_pos" + s).addClass("btn_list_pos" + t);
            $(this).attr("status", t)
        }*/
        
    };
    var c = false;
    var h = function() {
        posComm = !c ? {
            right: "0"
        } : {
            right: "-452px"
        };
        c = !c ? true : false;
        r.animate(posComm, "slow")
    };
    var p = function() {
        listView = n.is(":visible") ? true : false;
        if (c) {
            h()
        }
        btnSpan = i.find("span");
        r.fadeToggle("slow");
        if (listView) {
            btnSpan.removeClass("active");
            borde = {
                "border-radius": "5px"
            }
        } else {
            catalogos.get_datos_tarjeton(scope);
            btnSpan.addClass("active");
            borde = {
                "border-radius": "0px",
                "border-top-left-radius": "5px",
                "border-top-right-radius": "5px"
            }
        }
        e.find(".wrap_list").css(borde);
        n.slideToggle("slow")
    };
    
    //u.click(j);
    i.click(p);
    //o.click(l);
    a.click(h);
    t.find(".btn_cerrar").click(h)
}