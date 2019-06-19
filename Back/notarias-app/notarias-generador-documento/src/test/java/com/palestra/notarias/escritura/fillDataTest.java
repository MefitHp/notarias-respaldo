package com.palestra.notarias.escritura;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.IOUtils;

import com.palestra.notaria.exceptions.NotariaException;

public class fillDataTest {

	public fillDataTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws NotariaException {
		System.out.println("Hola mundo");
		//FillTemplateWithDataTest test = new FillTemplateWithDataTest();
		//est.getTemplateWithDataReader();
		
		//VariableToTokenTransformer vt = new VariableToTokenTransformer();
		//String texto = vt.evaluaFuncionVariable("texto","65,643");
		//System.out.println("VALOR:"+texto);
		
		//TEST REMPLAZA VARIABLES SISTEMA GENERALES
		FillTemplateWithData fillTemplate = new FillTemplateWithData();
		String htmlText = " siendo el dia ${fecha_hoy} en el cual fue creado el mundo";
		
		Reader reader = fillTemplate.remplazaVariablesGenerales(htmlText);
		
		
		if (reader != null) {
			System.out.println("********************************");
			String texto = null;
			try {
				texto = IOUtils.toString(reader);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(texto);
			System.out.println("********************************");
		}
		

	}

}
