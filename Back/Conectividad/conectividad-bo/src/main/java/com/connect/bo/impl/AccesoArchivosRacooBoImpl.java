package com.connect.bo.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.connect.bo.AccesoArchivosRacooBo;

public class AccesoArchivosRacooBoImpl implements AccesoArchivosRacooBo {

	@Override
	public List<String> listaArchivos(String carpeta,String rutaRacoo) throws IOException{
		String[] elements = carpeta.split("_");
		String estructura = "";
		for(int i=0;i<elements.length;i++){
			estructura = estructura.concat("/").concat(elements[i]);
		}
		List<String> nombresArchivo = new ArrayList<String>();
		File ruta = new File(rutaRacoo+estructura);
		File[] archivos = ruta.listFiles(); 
		System.out.println("ruta "+ruta.toString());
			if(archivos !=null){
			 for(int i=0;i<archivos.length;i++){
		    	if(archivos[i].isFile()){
		    		String [] doc = archivos[i].getName().split("\\.");
		    		if(doc.length==2){
			    		String extension = doc[1];
			    		if(extension.equalsIgnoreCase("doc") || extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("docx")||extension.equalsIgnoreCase("xls")){
			    			nombresArchivo.add(archivos[i].getName());
			    			System.out.println("archivo "+archivos[i].getName());
			    		}
		    		}
		    	}
			 }
			 }
	    return nombresArchivo;
	}

}
