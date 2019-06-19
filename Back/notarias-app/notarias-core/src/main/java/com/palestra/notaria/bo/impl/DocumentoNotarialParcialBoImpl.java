package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DocumentoNotarialParcialBo;
import com.palestra.notaria.dao.DocumentoNotarialParcialDao;
import com.palestra.notaria.dao.impl.DocumentoNotarialParcialDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.modelo.Escritura;

public class DocumentoNotarialParcialBoImpl extends
		GenericBoImpl<DocumentoNotarialParcial> implements
		DocumentoNotarialParcialBo {

	private DocumentoNotarialParcialDao documentoNotarialParcialDao;

	public DocumentoNotarialParcialBoImpl() {
		this.documentoNotarialParcialDao = new DocumentoNotarialParcialDaoImpl();
		super.dao = this.documentoNotarialParcialDao;
	}
	
	@Override
	public DocumentoNotarialParcial getLastVersion(String id)throws NotariaException {
		return documentoNotarialParcialDao.getLastVersion(id);
	}
	
	@Override
	public Integer getActualVersion(String id)throws NotariaException {
		Integer version = this.documentoNotarialParcialDao.getActualVersion(id);
		if (version == null) {
			version = 1;
		} else {
			version++;
		}
		return version;
	}

	@Override
	public List<DocumentoNotarialParcial> findAll() throws NotariaException {
		return documentoNotarialParcialDao.findAll();
	}
	
	@Override
	public DocumentoNotarialParcial findById(String id) throws NotariaException {
		return documentoNotarialParcialDao.findById(id);
	}
	
	@Override
	public List<DocumentoNotarialParcial> getByEscritura(Escritura escritura) throws NotariaException{
		return documentoNotarialParcialDao.getByEscritura(escritura);
	}
 }
