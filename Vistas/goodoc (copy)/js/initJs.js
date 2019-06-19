/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initJQ() {

    $("#nav_admin .btn").click(function() {
        activeBtn($(this));
    });

    $(".btnMenuAdmin >p").click(function() {
        acordeonMenu($(this));

    });
    setTimeout(function() {
        $(".btnMenuAdmin >p").first().trigger("click");
    }, 500)
    
    $("#horayfechaactual").text(moment().format("dddd DD  MMMM HH:mm"))

}