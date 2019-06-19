define(['../module'],function(directives){'use strict';directives.directive('loadscript',function(){return{restrict:'E',scope:{url:"@",},link:function(scope,element){var dirctrls="js/controllers/data/",dir;switch(shim){case"_adm":dir="CRUD/"
break;default:dir="APP/";}
angular.element('<script src="'+dirctrls+dir+scope.url+'"></script>').appendTo(element);}};});});