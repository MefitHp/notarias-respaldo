package com.palestra.notarias.escritura;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notarias.pojo.FiltroVariables;
import com.palestra.notarias.pojo.VariablesTokens;
import com.palestra.notarias.utils.ArchivoUtils;
import com.palestra.notarias.variables.VariableToTokenTransformer;

public class FillTemplateWithDataTest {

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

	public void getTemplateWithDataReader() {
		try {
			
			FillTemplateWithData fillTemplate = new FillTemplateWithData();
			Acto acto = new Acto();
			acto.setIdacto("5ce24c0bb221a975adf39215e1acdc61");
			URL url = fillTemplate.getClass().getResource("/plantilla_compraventa.html");
			String htmlText = ArchivoUtils
					.fileToString(url.getPath());
//			String htmlText = "Texto sin variables";
			
			FiltroVariables fvariables = new FiltroVariables();
			fvariables.setIdacto(acto.getIdacto());
		//	fvariables.setI
			Reader reader = fillTemplate
					.reemplazarVariablesSistema(fvariables, htmlText, true);
			
			if (reader != null) {
				System.out.println("********************************");
				String texto = IOUtils.toString(reader);
				System.out.println(texto);
				System.out.println("********************************");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
