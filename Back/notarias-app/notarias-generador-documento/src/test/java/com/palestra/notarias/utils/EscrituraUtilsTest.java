package com.palestra.notarias.utils;

import java.net.URL;

import com.palestra.notaria.exceptions.NotariaException;

public class EscrituraUtilsTest {

	public static void main(String args[]) throws NotariaException {
		EscrituraUtilsTest eut = new EscrituraUtilsTest();
		String archivo = "/reemplazarLETRAS.html";
		archivo = "/numeros.html";
		URL url = eut.getClass().getResource(archivo);
		String input = ArchivoUtils
				.fileToString(url.getPath());
//		String input = "foo #LETRA bar #LETRA foo #LETRA";
		System.out.println(EscrituraUtills.replaceMakerRomano(input,"\\[#A\\]"));
		

	}
	
	
}
