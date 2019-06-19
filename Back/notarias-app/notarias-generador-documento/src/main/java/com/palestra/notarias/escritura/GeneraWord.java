package com.palestra.notarias.escritura;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.docx4j.convert.in.xhtml.XHTMLImporter;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;

/// FOOTER AND HEADER IMPORTS

import org.docx4j.convert.out.flatOpcXml.FlatOpcXmlCreator;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.P;
import org.docx4j.wml.R;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.TramiteBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;


/**
 * 
 * @author Víctor Espinosa
 * 
 */

/**
 * Genera un archivo .doc a partir del texto en html con los requerimientos básicos para que el macro 
 * se ejecute de manera correcta
 * 
 * @param textoEscritura
 *            Texto en html del formato
 * @param numeroEscritura
 * 			El número de escritura que se mostrará en el documento
 * @param numeroExpediente carpeta en la que se guarda el documento
 * 
 * @return ruta del archivo recien generado.
 */


public class GeneraWord {

	private static ObjectFactory objectFactory = new ObjectFactory();    
    
    
    public String creaEscrituraWord(String textoEscritura,String numeroEscritura,String idtramite)throws JAXBException, Docx4JException, IOException,Exception{
    	StringBuilder html = new StringBuilder();
    	
    	//LIMPIO LAS CABECERAS DEL TEXTO DE LA ESCRITURAS
    	int headDebug = textoEscritura.indexOf("--dm--", 0);
        if(headDebug>0){
        	headDebug = textoEscritura.indexOf("</p>",headDebug);
        	textoEscritura = textoEscritura.substring((headDebug+4),textoEscritura.length());
        	textoEscritura = textoEscritura.replace("<p>&nbsp;</p>", "");
        }
    	// GENERO EL TEXTO A MOSTRAR EN LA ESCRITURA
    	StringBuilder contentBuilder = new StringBuilder("<html><head><title>Import me</title></head><body>");
        contentBuilder.append(textoEscritura);
        contentBuilder.append("</body></html>");
        System.out.println(contentBuilder.toString());
        //LIMPIO LOS CARACTERES ESPECIALES
        html.append(org.apache.commons.lang.StringEscapeUtils.unescapeHtml(contentBuilder.toString()));
        
        // GENERO EL WORD Y OBTENGO EL MACHOTE
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage();
        
       //WordprocessingMLPackage wpackage = wordMLPackage.load(new java.io.File(this.url+"escritura.docx"));
        
	
        // GENERO LA CABECERA
    	MainDocumentPart mdp = wordMLPackage.getMainDocumentPart();
    	Relationship styleRel = mdp.getStyleDefinitionsPart().getSourceRelationships().get(0);
    	mdp.getRelationshipsPart().removeRelationship(styleRel);

    	//Seteo la cabecera
    	Relationship relationship = createHeaderPart(wordMLPackage,numeroEscritura);
    	createHeaderReference(wordMLPackage, relationship);
    	FlatOpcXmlCreator worker = new FlatOpcXmlCreator(wordMLPackage);
    	worker.marshal(System.out);
        NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
        wordMLPackage.getMainDocumentPart().addTargetPart(ndp);	
        ndp.unmarshalDefaultNumbering();
 
        List<Object> listObj = XHTMLImporter.convert(html.toString(), null, wordMLPackage);
        System.out.println(listObj);
        wordMLPackage.getMainDocumentPart().getContent().addAll(listObj);
 
        
		//GENERO EL WORD Y LO GUARDO
        
          String tramiteFolder = Constantes.EXPEDIENTES_HOME + File.separator
					+ idtramite;
			File dir = new File(tramiteFolder);
			dir.setReadable(true);
			if (!dir.exists() || !dir.isDirectory()) {
				FileUtils.forceMkdir(dir);
				System.out.println("=========> Se creo foler:" + tramiteFolder);
			}
		
			wordMLPackage.save(new java.io.File(tramiteFolder+File.separator+"escritura_"+numeroEscritura+".docx"));
    	    	
    	return tramiteFolder+File.separator+"escritura_"+numeroEscritura+".docx";
    }

	 



private static Relationship createHeaderPart(
		WordprocessingMLPackage wordprocessingMLPackage,String numeroEscritura)
		throws Exception {

	HeaderPart headerPart = new HeaderPart();
	Relationship rel =  wordprocessingMLPackage.getMainDocumentPart()
			.addTargetPart(headerPart);

	// After addTargetPart, so image can be added properly
	headerPart.setJaxbElement(getHdr(wordprocessingMLPackage, headerPart,numeroEscritura));

	return rel;
}

private static void createHeaderReference(
		WordprocessingMLPackage wordprocessingMLPackage,
		Relationship relationship )
		throws InvalidFormatException {

	List<SectionWrapper> sections = wordprocessingMLPackage.getDocumentModel().getSections();

	SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
	// There is always a section wrapper, but it might not contain a sectPr
	if (sectPr==null ) {
		sectPr = objectFactory.createSectPr();
		wordprocessingMLPackage.getMainDocumentPart().addObject(sectPr);
		sections.get(sections.size() - 1).setSectPr(sectPr);
	}

	HeaderReference headerReference = objectFactory.createHeaderReference();
	headerReference.setId(relationship.getId());
	headerReference.setType(HdrFtrRef.DEFAULT);
	sectPr.getEGHdrFtrReferences().add(headerReference);// add header or
	// footer references

}

private static Hdr getHdr(WordprocessingMLPackage wordprocessingMLPackage,
		Part sourcePart,String numeroEscritura) throws Exception {

	Hdr hdr = objectFactory.createHdr();
	
	hdr.getContent().add(makeParagraph(sourcePart,numeroEscritura));

	//hdr.getContent().add();
	return hdr;
}


private static P makeParagraph(Part part, String text) {
    P p = objectFactory.createP();
    R r = objectFactory.createR();
    p.getContent().add(r);
    org.docx4j.wml.Text t = objectFactory.createText();
    r.getContent().add(t);
    t.setValue(text);
    return p;
}
	
	
	
			
}
