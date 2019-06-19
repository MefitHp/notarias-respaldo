<html>
<head>
<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
<script type="text/javascript">

	function enviar(){
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url :  "/notarias-web/notaria/notarias/cargarLogo",
			data: ,
			beforeSend:function(){
				console.info(doc);
				console.info({"dsnombre":$('#nombre').val()});
				
			},
			success : function(result) {
				// alert(JSON.stringify(result));
				$("#tabla-documentos").html("");
				var html=[];	
				//html.push("<table border='1'>");
				$.each(result, function(i, item) {
					//alert(item.nombre);
					html.push("<tr><td>"+item.nombre+"</td></tr>");
				});
				html.push("</table>");				
				$("#tabla-documentos").html(html.join(""));
			},
			error : function(httpRequest, textStatus, errorThrown) {
				alert("status=" + textStatus + ",error=" + errorThrown);
			}
		});
	}

</script>
</head>
<body>
<h2>Hello World!</h2>
<form action="notaria/notarias/cargarLogo" method="post" enctype="multipart/form-data">
 
	   <p>
		Select a file : <input type="file" name="file" size="45" />
	   </p>
 		<input type="hidden" name="idsesionactual" value="DE7AFB7672D2FA538927182BADD42777"/>
 		<input type="hidden" name="idusuario" value="e4da3b7fbbce2345d7772b0674a318d5"/>
 		
	   <input type="submit" value="aceptar" />
	</form>

</body>
</html>
