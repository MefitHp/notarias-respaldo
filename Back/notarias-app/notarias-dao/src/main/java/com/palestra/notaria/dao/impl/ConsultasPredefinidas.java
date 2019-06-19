package com.palestra.notaria.dao.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.palestra.notaria.dao.ConFormularioDao;
import com.palestra.notaria.dato.ConsultaPredefinida;
import com.palestra.notaria.exceptions.NotariaException;

public class ConsultasPredefinidas {

	private final static String ARCHIVO_XML_CONSULTAS = "/defQuerys.xml";
	
	private static ConsultasPredefinidas instance = new ConsultasPredefinidas();
	private Document xml;
	
	private ConsultasPredefinidas() {
		try{
			xml = lecturaXML(ARCHIVO_XML_CONSULTAS);			
		}catch(NotariaException e){
			e.printStackTrace(System.out);			
		}
	}
	
	public static ConsultasPredefinidas getInstance(){
		return instance;
	}

	
	/***
	 * listarConsultasPredefinidas devuelve el listado de las consultas predefinidas en el archivo de configuración
	 * @return java.util.List<ConsultarPredefinida> listado de las consultas predefinidas en el documento
	 * @throws NotariaException
	 */
	public List<ConsultaPredefinida> listarConsultasPredefinidas()throws NotariaException{		
		List<ConsultaPredefinida> vuelta = new ArrayList<>();
		//System.out.println("Root element :" + xml.getDocumentElement().getNodeName());		 
		NodeList nList = xml.getElementsByTagName("querys");	 
		for (int temp = 0; temp < nList.getLength(); temp++) {	 
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
//				System.out.println("identificador : " + eElement.getAttribute("id"));
//				System.out.println("nombre : " + eElement.getElementsByTagName("nombre").item(0).getTextContent());
//				System.out.println("ql : " + eElement.getElementsByTagName("hql").item(0).getTextContent());
//				System.out.println("campo : " + eElement.getElementsByTagName("campo").item(0).getTextContent());

				ConsultaPredefinida consultaPredefinida = new ConsultaPredefinida();
				consultaPredefinida.setIdentificador(eElement.getAttribute("identificador"));
				consultaPredefinida.setNombre(eElement.getElementsByTagName("nombre").item(0).getTextContent());
				consultaPredefinida.setHql(eElement.getElementsByTagName("hql").item(0).getTextContent());
				consultaPredefinida.setCampo(eElement.getElementsByTagName("campo").item(0).getTextContent());
				vuelta.add(consultaPredefinida);				
			}
		}
		return vuelta;
	}
	
	/***
	 * consultaPredefinidaPorId Devuelve un elemento de consulta predefinida del archivo de configuración.
	 * @param identificador valor de identificación del elemento del archivo de configuración
	 * @return Objeto de ConsultaPredefinida extraido del archivo de configuración
	 * @throws NotariaException
	 */
	public ConsultaPredefinida consultaPredefinidaPorId(String identificador)
			throws NotariaException {
		NodeList nList = xml.getElementsByTagName(identificador);
		for (int temp = 0; temp < nList.getLength(); temp++) {	 
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
//				System.out.println("identificador : " + eElement.getAttribute("id"));
//				System.out.println("nombre : " + eElement.getElementsByTagName("nombre").item(0).getTextContent());
//				System.out.println("ql : " + eElement.getElementsByTagName("hql").item(0).getTextContent());
//				System.out.println("campo : " + eElement.getElementsByTagName("campo").item(0).getTextContent());

				ConsultaPredefinida consultaPredefinida = new ConsultaPredefinida();
				consultaPredefinida.setIdentificador(eElement.getAttribute("identificador"));
				consultaPredefinida.setNombre(eElement.getElementsByTagName("nombre").item(0).getTextContent());
				consultaPredefinida.setHql(eElement.getElementsByTagName("hql").item(0).getTextContent());
				consultaPredefinida.setCampo(eElement.getElementsByTagName("campo").item(0).getTextContent());
				return consultaPredefinida;				
			}
		}		
		return null;
	} 
	
	
	/***
	 * lecturaXML realiza la lectura de un archivo que debe ser un xml well-formed
	 * @param archivo ubicación del archivo xml que será parseado
	 * @return org.w3c.dom.Document 
	 * @throws NotariaException
	 */
	private Document lecturaXML(String nombreArchivo)throws NotariaException{
		try {
			
			File archivo = new File(ConsultasPredefinidas.class.getClassLoader().getResource(nombreArchivo).getFile());
			if(!archivo.exists()){
				throw new NotariaException(String.format("The file %s does not exists. Impossible extract data.",archivo));
			}
			DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder xmlBuilder;
			xmlBuilder = xmlFactory.newDocumentBuilder();
			Document xml = xmlBuilder.parse(archivo);
			xml.getDocumentElement().normalize();
			return xml;
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e);
		}
	}
	
	/***
	 * obtenerValores obtiene el listado de cadenas que contiene la consulta resultado
	 * @param consulta valor de la consulta que se realizará en la base de datos
	 * @return java.util.List<String> listado de valores resultados
	 * @throws NotariaException
	 */
	public List<String> obtenerValores(String consulta)throws NotariaException{
		ConFormularioDao dao = new ConFormularioDaoImpl();
		return dao.obtieneListado(consulta);
	}
	
}
