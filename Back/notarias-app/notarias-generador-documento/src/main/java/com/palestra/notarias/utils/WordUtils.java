package com.palestra.notarias.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.POIXMLProperties.CustomProperties;
import org.apache.poi.POIXMLProperties.ExtendedProperties;
import org.apache.poi.hpbf.HPBFDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class WordUtils {

	private int lineasxpagina;
	String ext;
	XWPFDocument xdocumento;
	HWPFDocument documento;
	
	
	
	public WordUtils(String fileurl, int lineasxpagina) throws Exception {
		FileInputStream fis;
		this.lineasxpagina = lineasxpagina; 
		fis = new FileInputStream(fileurl);
		ext = FilenameUtils.getExtension(fileurl);
		
		if(ext.equals("doc")){
			documento = new HWPFDocument(fis);
		}else if(ext.equals("docx")){
			xdocumento = new XWPFDocument(OPCPackage.open(fis));	
		}else{
			throw new Exception("No es un documento válido");
		}
		
	}
	
	
	public int getpaginas(){
		
		if(xdocumento!=null){
			return xdocumento.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();			
		}else{
			return documento.getSummaryInformation().getPageCount();
		}
		
	    } 
	
	public int getFilas(){
		if(xdocumento!=null){
			return getFilasDocX();
		}else{
			return getFilasDoc();
		}
	}
	
	private int getFilasDoc() {
		org.apache.poi.hpsf.CustomProperties cp;
		cp = documento.getDocumentSummaryInformation().getCustomProperties();
		if(cp!=null && cp.containsKey("filas")){
			return (int) cp.get("filas");
		}
		return 0;
	}


	private int getFilasDocX(){
	
	CustomProperties cp = xdocumento.getProperties().getCustomProperties();
	
	 if (cp != null) {
	        List<CTProperty> ctProperties = cp.getUnderlyingProperties()
	                .getPropertyList();
	        for (CTProperty ctp : ctProperties) {
	        	if(ctp.getI4()>0 && ctp.getName().equals("filas")){
	        		return ctp.getI4();	    
	        	}
	                    
	        }
	    }
	return 0;
	}
	

	public int getPorcentajeUltimaPag(int lineas){
		int filasLastPag =  (lineas % this.lineasxpagina); 
		return (filasLastPag*100) / this.lineasxpagina;
	}
	

}
