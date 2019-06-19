package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DocumentoBo;
import com.palestra.notaria.dao.DocumentoDao;
import com.palestra.notaria.dao.impl.DocumentoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Documento;

public class DocumentoBoImpl extends GenericBoImpl<Documento> implements DocumentoBo{

	private DocumentoDao documentoDao;
	
	public DocumentoBoImpl() {
		this.documentoDao = new DocumentoDaoImpl();
		super.dao = this.documentoDao;
	}

	@Override
	public List<Documento> obtenerDocumento(String tipo) throws NotariaException{
		return this.documentoDao.obtenerDocumento(tipo);
	}

	public List<Documento> findAll() throws NotariaException {
		return documentoDao.findAll();
	}
	
	@Override
	public Documento findBy(String iddocumento, Integer version) throws NotariaException{
		return this.documentoDao.findBy(iddocumento, version);
	}
}
