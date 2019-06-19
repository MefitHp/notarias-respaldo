package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoSuboperacion;

public interface DocumentoSuboperacionBo extends
		GenericBo<DocumentoSuboperacion> {

	public List<DocumentoSuboperacion> listarPreviosPorSubopAndLocalidad(String idlocalidad, String idsuboperacion) throws NotariaException;
	public DocumentoSuboperacion encuentraDocumentoSubOperacion(String idsuboperacion, String idlocalidad, String iddocumento, String idformato) throws NotariaException;
	public List<DocumentoSuboperacion> listarPosterioresPorSubopAndLocalidad(String idlocalidad, String idsuboperacion)
			throws NotariaException;
	public List<DocumentoSuboperacion> listarPosterioresPorEscritura(String idescritura,String idlocalidad)throws NotariaException;

}
