
var Modal_Componente = function(){
    
    var conf_style = {
    "height": $(window).height(),
    "width": "100%",
    "position": "fixed",
    "top": "0",
    "left": "0",
    "z-index": "1",
    "background": "white",
    "opacity": ".9",
    "display": "none",
    "padding-top": "10%"
    };
   
    var config_loader_img = '<div class="contener_loader_18"><div class="rond_sup"><div class="rond_mil"><div class="rond_petit"><div class="rond_mini"></div></div></div></div></div>';
    
    this.modal = $('<div>').css(conf_style);
    this.modal.attr("id","mimodal_component");
    this.modal.html(config_loader_img);
   
    this.setImg = function(bol){
        if(bol) this.modal.html(config_loader_img);
        else this.modal.html("");
    };
    this.setColor = function(color){
        this.modal.css("background",color);
    };
    
    this.create = function(){
        $("body").append(this.modal);
        console.info("Creo modal",this.modal);
    };
    
    this.show=function(){
       $("#mimodal_component").fadeIn("fast");
    };
    this.destroy=function(){
        $("#mimodal_component").fadeOut("fast");
    };    
};

	