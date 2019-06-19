package com.palestra.notarias.helper;

import java.sql.Timestamp;
import java.util.Date;

import com.palestra.notaria.bo.impl.DocumentoNotarialParcialBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.util.GeneradorId;

/**
 * Clase helper para operaciones realizadas con un DocumentoNotarialHelper
 * 
 * @author sofia
 * 
 */
public class DocNotParcialHelper {

	static final Integer VERSION_INICIAL = 0;

	/** Llamdas BO **/
	DocumentoNotarialParcialBoImpl docNotParcialBo;

	public DocNotParcialHelper() {
		if (docNotParcialBo == null) {
			docNotParcialBo = new DocumentoNotarialParcialBoImpl();
		}
	}

	/**
	 * Registra un documento notarial parcial en la BD para la primera version
	 * la cual por regla es cerrada.
	 * 
	 * @param docNotHtml
	 * @param idEscritura
	 */
	public void guardaPrimeraVersionDocParcial(String docNotHtml,
			String idEscritura) {
		try {
			// TODO: obtener el usuario en sesion
			/** siempre es la version inicial **/
			this.guardaParcialUpdatedVersion(docNotHtml, idEscritura,
					VERSION_INICIAL, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean guardarParcial(String txtdoc, String idEscritura,
			String idsusuario) {
		try {
			Integer lastVersion = this.docNotParcialBo
					.getActualVersion(idEscritura);
			this.guardaParcialUpdatedVersion(txtdoc, idEscritura,
					lastVersion, idsusuario);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Guardar un documento notarial a partir de un texto dada una escritura y
	 * su version.
	 * 
	 * @param txtdoc
	 * @param idEscritura
	 * @param version
	 * @param idusuario
	 */
	private void guardaParcialUpdatedVersion(String txtdoc, String idEscritura,
			Integer version, String idusuario) throws NotariaException,
			Exception {

		Escritura escritura = new Escritura();
		escritura.setIdescritura(idEscritura);

		DocumentoNotarialParcial docNotParcial = new DocumentoNotarialParcial();

		docNotParcial.setIddocnotpar(GeneradorId.generaId(docNotParcial));
		docNotParcial.setEscritura(escritura);
		docNotParcial.setVersion(version);
		docNotParcial.setFecha(new Date());
		docNotParcial.setTmstmp(new Timestamp((new Date()).getTime()));
		/* Si se llama a este helper es true */
		docNotParcial.setIscerrado(false);
		docNotParcial.setIdsesion(idusuario);		
		docNotParcial.setTxtdoc(txtdoc);
		
		try {
			docNotParcialBo.save(docNotParcial);
		} catch (NotariaException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelbe el documento notarial en su ultima version.
	 * 
	 * @param idEscritura
	 * @return
	 * @throws NotariaException 
	 */
	public DocumentoNotarialParcial getDocumentoLastVersion(String idEscritura) throws NotariaException {
		return docNotParcialBo.getLastVersion(idEscritura);
	}

}
