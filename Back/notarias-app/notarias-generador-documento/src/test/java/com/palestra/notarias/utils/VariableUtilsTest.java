package com.palestra.notarias.utils;

import com.palestra.notarias.constantes.TipoDato;
import com.palestra.notarias.enums.EnumVariables;

public class VariableUtilsTest {

	public static void main(String[] args) {
		//VariableUtilsTest vut = new VariableUtilsTest();
		//vut.subStringFromDelimiter();
		String resultado = VariableUtils.transformNumberListToText(
				TipoDato.MONEDA, "200.0 y 140.0", "[\\,|y]");
		
		System.out.println("===> resultado: " + resultado );
	}
	
	
	
	public void subStringFromDelimiter(){
		StringBuilder texto = new StringBuilder();

		//texto.append("$(tbl:hijas[${frm:familia[nombre]} ${frm:familia[apellidopaterno]} ${frm:familia[apellidomaterno]} obteniendo el ${frm:familia[porcentaje]}])");

		texto.append("$(token  otro )");
		//String identificador[] = { EnumVariables.TBL_IDENTIFIER.toString() };
		
		//-->  System.out.println(VariableUtils.validIdentificadorExpresion(identificador));
		
		String regex_prefix = EnumVariables.TBL_PREFIX.toString();
		String regex_sufix = EnumVariables.TBL_SUFIX.toString();
		String subString = VariableUtils.subStringFromDelimiter(regex_prefix,
				regex_sufix, texto.toString());
		System.out.println("===>subString: " + subString );

	}

}
