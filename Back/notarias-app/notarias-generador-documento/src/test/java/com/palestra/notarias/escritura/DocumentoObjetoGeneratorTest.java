package com.palestra.notarias.escritura;

import java.net.URL;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notarias.utils.ArchivoUtils;

public class DocumentoObjetoGeneratorTest {
	
	public static void main(String [] args) throws NotariaException{
		
		DocumentoObjetoGenerator docObjGenerator = new DocumentoObjetoGenerator();
		
		Acto acto = new Acto();
		acto.setIdacto("5ce24c0bb221a975adf39215e1acdc61");
		URL url = docObjGenerator.getClass().getResource("/test_documeto_objeto.html");
		String htmlText = ArchivoUtils
				.fileToString(url.getPath());
//		String htmlText = "Hola texto sin variables ";
		
		String resultado = docObjGenerator.reemplazaVariablesFormularios(acto.getIdacto(), htmlText);
		//TODO: aun no se agrega metodo al rest.
		if(resultado!=null){
			System.out.println(":::::::::::::::::::::::::::::::::::");
			System.out.println(resultado);
		}
	}
}
