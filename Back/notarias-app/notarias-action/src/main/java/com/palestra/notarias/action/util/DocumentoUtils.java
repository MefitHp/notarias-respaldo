package com.palestra.notarias.action.util;


public class DocumentoUtils {

	/**
	 * Devuelve el id del expediente. Dado el un nombre de un nodo de la forma:
	 * 		'numeroEscritura.doc'
	 * @param nodeName
	 * @return idEscritura
	 *     Cadena de 32 characteres, id de la escritura a l que pertenece el documento.
	 */
	public static String getEscrituraId(String fileName) {
		//TODO: validar la mascara de nombre del nodo
		String idEscritura = fileName.replace(".doc", "");
		return idEscritura;
		
	}

}
