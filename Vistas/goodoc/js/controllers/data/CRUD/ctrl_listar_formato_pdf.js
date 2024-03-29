function ctrl_listar_formato_pdf($scope, $rootScope, conexion_app, conexion, catalogos) {
    conexion_app.listar_docPdf({
        "usuario": $scope.usr_global
    }, function(data) {
        if (data.exito) {
            $scope.previos = data.previos;
            $scope.posteriores = data.posteriores;
        } else {
            ejecutaAlerta(2, "Ocurrió un error: " + data.estatus)
        }
    }, function(error) {
        alert("Ocurrió un error al obtener los documentos: " + error.status);
    });
    $scope.edit_doc = function(doc) {
        $rootScope.editDocPdf = doc;
        $scope.goTo("/formato_pdf");
    };
    $scope.upload = function(documento) {
        Avgrund.show("#changeKey");
        $("#idformatopdf").val(documento.identificador);
        $("#idusuario").val($scope.usr_global.idusuario);
        $("#tipodoc").val(documento.tipodoc.dselemento);
        $("#idsesionactual").val($scope.usr_global.idsesionactual);
        $("#archivo").attr("action", "http://" + contexto + "/notaria/formatoPDF/upload");
    }
}