package com.palestra.notaria.util;

import java.io.File;
import java.io.IOException;








import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class LectorXML {

	private File file;
	
	public LectorXML(File file) {
		setFile(file);
	}
	
	public String getHQL(String nombre)throws IOException{
		try{
			lecturaXML();
			return "";
		}catch (JDOMException e) {
			throw new IOException(String.format("JDOMException al leer el archivo xml %s", file), e);
		}
	}
	
	private void lecturaXML()throws IOException, JDOMException{
		if(file!=null){
			if(file.exists()){
				SAXBuilder builder = new SAXBuilder();
				Document documento = (Document)builder.build(file);
				Element rootNode = documento.getRootElement();
				List lista = rootNode.getChildren("query");
				for(Object elemento:lista){
					Element query = (Element)elemento;
					String identificador =query.getAttributeValue("identificador");
					if(identificador.equals("")){}
				}
				
			}
		}
	}
	
	public File getFile(){
		return this.file;		
	}

	public void setFile(File file){
		this.file = file;
	}
	
}
