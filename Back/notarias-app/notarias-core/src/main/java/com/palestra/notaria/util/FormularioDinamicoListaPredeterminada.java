package com.palestra.notaria.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FormularioDinamicoListaPredeterminada {

	private final static String ARCHIVO_LISTA_PREDETERMINADA = File.separator+"notaria"+File.separator+
			".config"+File.separator+"predquerys.xml";

	public static String extraerHQL(String id)
			throws ParserConfigurationException, SAXException, IOException {

		// URL ruta =
		// FormularioDinamicoListaPredeterminada.class.getClassLoader()
		// .getResource(ARCHIVO_LISTA_PREDETERMINADA);
		String path = System.getProperty("user.home");
		// if(path.contains("file:")){
		// path = path.substring(path.indexOf("file:/")+5);
		// }
		path += ARCHIVO_LISTA_PREDETERMINADA;
		System.out.println("=====> The path to the config files is: " + path);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = null;
		try {
			document = builder.parse(new File(path));
		} catch (FileNotFoundException e) {
			System.out.println("La ruta del archivo no se localizo en " + path);
			return null;
		}
		if (document != null) {
			Element element = document.getDocumentElement();
			NodeList list = element.getChildNodes();
			if (list != null && list.getLength() > 0) {
				for (int i = 1; i < list.getLength(); i++) {
					if (list.item(i).getAttributes() != null) {
						if (list.item(i).getAttributes().item(0).getNodeValue()
								.equals(id)) {
							NodeList attrs = list.item(i).getChildNodes();
							for (int n = 1; n < attrs.getLength(); n++) {
								if (attrs.item(n).getNodeName().equals("hql")){
									return attrs.item(n).getTextContent();
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

}
