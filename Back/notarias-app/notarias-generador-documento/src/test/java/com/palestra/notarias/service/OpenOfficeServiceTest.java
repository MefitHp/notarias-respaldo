package com.palestra.notarias.service;

public class OpenOfficeServiceTest {
	
	public static void main(String [] args){
		OpenOfficeService osrv = new OpenOfficeService();
		String sourceFile = "/home/sofia/Documentos/proyectos/notarias/pruebas/dummy_plantillas/source_documeto_objeto.html";
		String targetFile = "/home/sofia/Documentos/proyectos/notarias/pruebas/dummy_plantillas/target.doc";
		osrv.convertFile(sourceFile, targetFile);
		//osrv.convertFile(sourceFile, targetFile);
	}

}
