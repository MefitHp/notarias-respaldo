package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.dato.DatoDocumentoObjeto;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoObjeto;

public interface DocumentoObjetoDao extends GenericDao<DocumentoObjeto, Integer>{
	
	List<DatoDocumentoObjeto> obtenListaDocumentoObjeto() throws NotariaException;
	
	DocumentoObjeto buscarPorIdCompleto(String id, Integer version) throws NotariaException;
	
	String obtenPlantillaPorId(String id, Integer version) throws NotariaException;

	public List<DocumentoObjeto> getPublicados() throws NotariaException;

	public List<DocumentoObjeto> getNoPublicados() throws NotariaException;

	public DocumentoObjeto findDocumentoPublicado(String iddocobjeto) throws NotariaException;

	public Integer findMaxVersion(String iddocobjeto) throws NotariaException;

}
