package com.palestra.notarias.variables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class VariableComplejaProcessor {

	public static void main(String[] args) {
		try {
			URL defaultSQL = ClassLoader.class.getResource("/querys_variables.xml");
			File file = new File(defaultSQL.toURI());
			
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(file);
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			String nodeId = "compareciente_nombre";

			System.out.println("*************************");
			String expression = "/queries/query[@id='"+nodeId+"']/hql";
			System.out.println(expression);
			String hql = xPath.compile(expression).evaluate(xmlDocument);
			System.out.println(hql);

			System.out.println("*************************");
			expression = "/queries/query[@id='"+nodeId+"']/campos";
			System.out.println(expression);
			Node node = (Node) xPath.compile(expression).evaluate(xmlDocument,
					XPathConstants.NODE);
			if (null != node) {
				NodeList nodeList = node.getChildNodes();
				for (int i = 0; null != nodeList && i < nodeList.getLength(); i++) {
					Node nod = nodeList.item(i);
					if (nod.getNodeType() == Node.ELEMENT_NODE) {
						System.out.println(nodeList.item(i).getNodeName()
								+ " : " + nod.getFirstChild().getNodeValue());
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
