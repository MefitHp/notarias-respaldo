package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoSuboperacion;

public interface DocumentoSuboperacionDao extends
		GenericDao<DocumentoSuboperacion, Integer> {

	List<DocumentoSuboperacion> listarPreviosPorSubopAndLocalidad(String idlocalidad, String idsuboperacion) throws NotariaException;
	DocumentoSuboperacion getUnique(String idsuboperacion, String idlocalidad, String iddocumento, String idformato)throws NotariaException;
	/***
	 * localiza el registro de documento asociado a la suboperación por mediod del identificador del documento
	 * @param suboperacion suboperacion del acto documento
	 * @param localidad el identificador del elemento de localidad del acto
	 * @param iddocumento puede ser la cadena de un identificador para el Formato-Documento o Formato-PDF, cualquier de los dos
	 * @return com.palestra.notaria.modelo.DocumentoSuboperacion
	 * @throws com.palestra.notaria.exceptions.NotariaException
	 */
	DocumentoSuboperacion getDocumentoSubOperacion(String suboperacion,
			String localidad, String iddocumento) throws NotariaException;
	
	DocumentoSuboperacion findById(Integer identificador) throws NotariaException;
	boolean delete(Integer identificador) throws NotariaException;
	List<DocumentoSuboperacion> listarPosterioresPorSubopAndLocalidad(String idlocalidad, String idsuboperacion) throws NotariaException;
	
}
