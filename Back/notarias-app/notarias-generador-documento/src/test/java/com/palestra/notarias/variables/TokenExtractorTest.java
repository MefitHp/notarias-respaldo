package com.palestra.notarias.variables;

import com.palestra.notarias.enums.EnumVariables;

public class TokenExtractorTest {

	public static void main(String[] args) {
		TokenExtractorTest te = new TokenExtractorTest();
		//te.extractVariableGrupo();
		//te.extractTablas();
		te.extractFrm();
	}

	public void extractVariableGrupo() {
		// TODO: por cambios de formularios dinamico se quito validacion que no
		// se cierre la variable.
		 String sourceText =
		 "1234567890${var:numero_libro(funcion=texto)} ${000} abcdefg ${var:numero_libro} XYZ ${var:fecha_hoy(funcion=texto)} ${gpo:direccion}";
		TokenExtractor tokenExtractor = new TokenExtractor();
		String[] tokensPlantilla = tokenExtractor.obtenTokensPlantilla(
				sourceText, EnumVariables.VAR_PREFIX.toString(),
				EnumVariables.VAR_PREFIX.toString());
		for (String s : tokensPlantilla) {
			System.out.println(s);
		}

		String[] permitidos = { EnumVariables.VAR_IDENTIFIER.toString(),
				EnumVariables.GPO_IDENTIFIER.toString() };
		String[] tokensToReplace = tokenExtractor.obtenTokensPermitidos(
				tokensPlantilla, permitidos);
		for (String s : tokensToReplace) {
			System.out.println(s);
		}
	}
	
	public void extractTablas(){
		String sourceText = "$(tbl:hijas[${frm:familia[nombre]} ${frm:familia[apellidopaterno]} ${frm:familia[apellidomaterno]} obteniendo el ${frm:familia[porcentaje]}])";
		TokenExtractor tokenExtractor = new TokenExtractor();
		String[] tokensPlantilla = tokenExtractor.obtenTokensPlantilla(
				sourceText, EnumVariables.TBL_PREFIX.toString(),
				EnumVariables.TBL_SUFIX.toString());
		for (String s : tokensPlantilla) {
			System.out.println(s);
		}
		String[] permitidos = { EnumVariables.TBL_IDENTIFIER.toString()};
		String[] tokensToReplace = tokenExtractor.obtenTokensPermitidos(
				tokensPlantilla, permitidos);
		for (String s : tokensToReplace) {
			System.out.println(s);
		}
	}
	
	public void extractFrm(){
		String sourceText = " ${var:notaria_notario} ${frm:antecedente_compraventa[vendedor_conyugue]}";
		TokenExtractor tokenExtractor = new TokenExtractor();
		String[] tokensPlantilla = tokenExtractor.obtenTokensPlantilla(
				sourceText, EnumVariables.VAR_PREFIX.toString() + EnumVariables.FRM_IDENTIFIER,
				EnumVariables.VAR_FRM_SUFIX.toString());
		for (String s : tokensPlantilla) {
			System.out.println(s);
		}
		String[] permitidos = { EnumVariables.FRM_IDENTIFIER.toString()};
		String[] tokensToReplace = tokenExtractor.obtenTokensPermitidos(
				tokensPlantilla, permitidos);
		for (String s : tokensToReplace) {
			System.out.println(s);
		}
	}
}
