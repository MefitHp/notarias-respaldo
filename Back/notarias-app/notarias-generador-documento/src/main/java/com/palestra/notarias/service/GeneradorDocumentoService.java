package com.palestra.notarias.service;

import org.apache.log4j.Logger;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notarias.escritura.EscrituraMasterGenerator;
import com.palestra.notarias.escritura.EscrituraParcialGenerator;

/**
 * 
 * Servicio para generar un documento notarial en .DOC de un expediente a partir
 * de una plantilla de documento en HTML.
 * 
 * @author sofia
 * 
 */
public class GeneradorDocumentoService {

	static Logger logger = Logger.getLogger(GeneradorDocumentoService.class);

	/** Llamdas helper **/
	EscrituraParcialGenerator escrituraParcialGenerator;
	EscrituraMasterGenerator escrituraMasterGenerator;

	public GeneradorDocumentoService() {
	}

	/**
	 * Genera version inicial de un documento notarial parcial.
	 * 
	 * @param idEscritura
	 */
	public void versionInicialDocParcial(String idEscritura) throws NotariaException {
		try{
			escrituraParcialGenerator = new EscrituraParcialGenerator();
			escrituraParcialGenerator.guardaDocumentoNotarialDeEscritura(idEscritura,false);			
		}catch(NullPointerException e){
			throw new NotariaException("No se encontró un elemento que se esperaba. [NULL]", e);
		}catch(Exception e){
			e.printStackTrace();
			throw new NotariaException("Ocurrió una excepción no manejada por el sistema. Reporte a sistemas. ", e);
		}
		
	}

	/**
	 * Genera documento master a partir del texto en html 
	 * dado y lo sube a alfresco por escritura.
	 * Nota: Como definicion del sistema el texto dado se captura desde CKEditor.
	 *
	 * @param escritura
	 * 		Objeto de escritura, de ahi se toma el el id escritura e
	 *  id expediente.
	 * @param textoDocumento 
	 *     Texto en html del documento.
	 * @param idusuario
	 */
	public boolean generaMasterPorEscritura(Escritura escritura, String textoDocumento, String idusuario) throws NotariaException {
		escrituraMasterGenerator = new EscrituraMasterGenerator();
		/*if (escritura != null && escritura.getIdescritura() != null
				&& !escritura.getIdescritura().isEmpty()
				&& escritura.getExpediente() != null
				&& escritura.getExpediente().getIdexpediente() != null
				&& !escritura.getExpediente().getIdexpediente().isEmpty()) {
			//Victor 20160204
			//Lo comento para generar la escritura de manera local 
			//throw new NotariaException("Parametros no validos. Se requiere del numero de escritura y expediente.");
		}*/
		try {
			return escrituraMasterGenerator.generaMasterCompletoPorEscritura(escritura, escritura.getExpediente().getIdexpediente(), textoDocumento, idusuario); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotariaException("Ha ocurrido una excepción al generar el documento Master. Revise el log de operaciones para más información.");
		}

	}

}