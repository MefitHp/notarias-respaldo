package com.palestra.notarias.utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.docx4j.openpackaging.exceptions.Docx4JException;

import com.palestra.notarias.escritura.GeneraWord;


public class GeneraWordTest {

	public static void main(String[] args) throws JAXBException, Docx4JException, IOException,Exception {
		   GeneraWord gw = new GeneraWord();
		   StringBuilder contentBuilder = new StringBuilder();
		   String url = "/home/yomero/tmp/pruebas/";
		   try {
	        	BufferedReader in = new BufferedReader(new FileReader(url+"texto.html"));
	            String str;
	            while ((str = in.readLine()) != null) {
	                contentBuilder.append(str);
	            }	            
	            in.close();
	        } catch (IOException e) {
	        	
	        }
		   
		   gw.creaEscrituraWord(contentBuilder.toString(), "0010", "0ba67ac7309f72060abe03ca145ad811");
		   
	 
	    }
	
}
