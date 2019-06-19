package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DocumentoObjetoBo;
import com.palestra.notaria.dao.DocumentoObjetoDao;
import com.palestra.notaria.dao.impl.DocumentoObjetoDaoImpl;
import com.palestra.notaria.dato.DatoDocumentoObjeto;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoObjeto;

public class DocumentoObjetoBoImpl extends GenericBoImpl<DocumentoObjeto> implements DocumentoObjetoBo{
	
	private DocumentoObjetoDao documentoObjetoDao;
	
	public DocumentoObjetoBoImpl(){
		this.documentoObjetoDao = new DocumentoObjetoDaoImpl();
		super.dao = this.documentoObjetoDao;
	}
	
	@Override
	public List<DatoDocumentoObjeto> obtenListaDocumentoObjeto()throws NotariaException {
		return this.documentoObjetoDao.obtenListaDocumentoObjeto();
	}
	
	@Override
	public DocumentoObjeto buscarPorIdCompleto(String id, Integer version)throws NotariaException {
		return this.documentoObjetoDao.buscarPorIdCompleto(id, version);
	}
	
	@Override
	public String obtenPlantillaPorId(String id, Integer version)throws NotariaException {
		return this.documentoObjetoDao.obtenPlantillaPorId(id, version);
	}

	@Override
	public List<DocumentoObjeto> getPublicados() throws NotariaException{
		return this.documentoObjetoDao.getPublicados();
	}
	
	@Override
	public List<DocumentoObjeto> getNoPublicados() throws NotariaException{
		return this.documentoObjetoDao.getNoPublicados();
	}
	
	@Override
	public DocumentoObjeto findDocumentoPublicado(String iddocobjeto) throws NotariaException{
		return this.documentoObjetoDao.findDocumentoPublicado(iddocobjeto);
	}
	
	@Override
	public Integer findMaxVersion(String iddocobjeto) throws NotariaException{
		return this.documentoObjetoDao.findMaxVersion(iddocobjeto);
	}
}

