package com.palestra.notarias.helper;

import java.sql.Timestamp;
import java.util.Date;

import com.palestra.notaria.bo.impl.BitacoraVersionesEscrituraBoImpl;
import com.palestra.notaria.modelo.BitacoraVersionesEscritura;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.util.GeneradorId;

/**
 * Helper para hacer las operaciones que involucran BitacoraVersionesEscrituras
 * 
 * @author sofia
 * 
 */
public class BitacoraVersionesEscrituraHelper {

	private BitacoraVersionesEscrituraBoImpl bitacoraVersionesEscrituraBo;
	
	public BitacoraVersionesEscrituraHelper(){
		if(bitacoraVersionesEscrituraBo==null){
			bitacoraVersionesEscrituraBo = new BitacoraVersionesEscrituraBoImpl();
		}
	}

	/**
	 * Registra en la bitacoraVersionesEscritura que se ha creado un documento
	 * 
	 * @param idEscritura
	 * @param documentoPath
	 * @param fileName
	 * @param version
	 */
	public void registraBitacora(String idEscritura, String documentoPath,
			String fileName, String version, String idNodoAlfresco) {

		try {
			Escritura escritura = new Escritura();
			escritura.setIdescritura(idEscritura);

			BitacoraVersionesEscritura bitacoraVersionesEscritura = new BitacoraVersionesEscritura();
			bitacoraVersionesEscritura.setEscritura(escritura);
			bitacoraVersionesEscritura.setIdbitversesc(GeneradorId
					.generaId(bitacoraVersionesEscritura));
			bitacoraVersionesEscritura.setDsnombre(fileName);
			bitacoraVersionesEscritura.setDsruta(documentoPath);
			bitacoraVersionesEscritura.setDsversion(version);
			bitacoraVersionesEscritura.setIdnodoalfresco(idNodoAlfresco);
			bitacoraVersionesEscritura.setTmstmp(new Timestamp((new Date())
					.getTime()));
			// TODO: ver como pasan sesion mas adelante. PENDIENTE
			bitacoraVersionesEscritura.setIdsesion("");
			
			/** guardar la bitacora **/
			bitacoraVersionesEscrituraBo.save(bitacoraVersionesEscritura);
			
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	/**
	 * Eliminar los registros de versiones de documentos por id de escritura.
	 * 
	 * @param idEscritura
	 * @return
	 */
	public void eliminaBitacora(String idEscritura) {
		try {
			bitacoraVersionesEscrituraBo.deleteByEscrituraId(idEscritura);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
