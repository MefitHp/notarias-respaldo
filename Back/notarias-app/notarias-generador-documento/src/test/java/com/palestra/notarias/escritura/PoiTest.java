package com.palestra.notarias.escritura;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.palestra.notarias.utils.ArchivoUtils;
import com.palestra.notarias.utils.WordUtils;
public class PoiTest {
   public static void main(String[] args) throws Exception {
	   String url = "/home/yomero/docs/";
	   String urldir = "/home/yomero/docs/";
	   WordUtils word;
	try {
		ArrayList<File> words = ArchivoUtils.wordsInDir(urldir);
		File masterExpediente = ArchivoUtils.getUltimaVersionExpediente("17-8-400", words);
		System.out.println("ULTIMA VERSION EXPEDIENTE:"+masterExpediente.getName());
		word = new WordUtils(masterExpediente.getPath(),39);
		System.out.println("PAGINAS:"+word.getpaginas());
		System.out.println("FILAS:"+word.getFilas());
		System.out.println("PORCENTAJE DE LA ULTIMA PAGINA:"+word.getPorcentajeUltimaPag(word.getFilas())+"%");
		
		 
	} catch (InvalidFormatException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}  