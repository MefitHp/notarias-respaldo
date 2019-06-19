package com.palestra.notarias.helper;

import java.sql.Timestamp;
import java.util.Date;

import com.palestra.notaria.bo.impl.DocumentoNotarialMasterBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.util.GeneradorId;

/**
 * Clase helper para hacer las operaciones que involucran
 * DocumentoNotarialMaster
 * 
 * @author sofia
 * 
 */
public class DocNotMasterHelper {

	/** Llamdas BO **/
	DocumentoNotarialMasterBoImpl docNotMasterBo;

	public DocNotMasterHelper() {
		if (docNotMasterBo == null) {
			docNotMasterBo = new DocumentoNotarialMasterBoImpl();
		}
	}

	/**
	 * Registra un documento notarial master en la BD.
	 * 
	 * @param htmltxt
	 * @param idEscritura
	 */
	public void guardaDocumentoNotarialMaster(String htmltxt, String idEscritura)
			throws NotariaException {

		DocumentoNotarialMaster docNotMaster = new DocumentoNotarialMaster();

		Escritura escritura = new Escritura();
		escritura.setIdescritura(idEscritura);

		docNotMaster.setIddocnotmas(GeneradorId.generaId(docNotMaster));
		docNotMaster.setEscritura(escritura);
		docNotMaster.setFecha(new Date());
		docNotMaster.setTmstmp(new Timestamp((new Date()).getTime()));
		docNotMaster.setTxtdoc(htmltxt);

		// TODO:ver lo de la session, pasarlo como argumento
		docNotMaster.setIdsesion("");

		/** Guardar el documento **/
		docNotMasterBo.save(docNotMaster);
	}

	/**
	 * Actualiza un documento notarial master en la BD.
	 * 
	 * @param htmltxt
	 * @param documentoMaster
	 */
	public void editaDocumentoNotarialMaster(String htmltxt,
			DocumentoNotarialMaster documentoMaster) throws NotariaException {
		// TODO:revisar
		documentoMaster.setTxtdoc(htmltxt);
		documentoMaster.setFecha(new Date());
		documentoMaster.setTmstmp(new Timestamp((new Date()).getTime()));
		// TODO:ver lo de la session, pasarlo como argumento
		documentoMaster.setIdsesion("");

		/** Actualizar el documento **/
		docNotMasterBo.update(documentoMaster);

	}

	/**
	 * Retorna el documento notarial master asociado al id escritura dado
	 * 
	 * @param idEscritura
	 * @return Documento notarial master
	 * @throws NotariaException 
	 */
	public DocumentoNotarialMaster getDocumentoMaster(String idEscritura) throws NotariaException {
		return docNotMasterBo.findByEscrituraId(idEscritura);
	}

}
