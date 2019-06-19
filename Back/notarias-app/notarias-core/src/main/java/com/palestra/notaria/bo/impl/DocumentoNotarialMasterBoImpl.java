package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.DocumentoNotarialMasterBo;
import com.palestra.notaria.dao.DocumentoNotarialMasterDao;
import com.palestra.notaria.dao.impl.DocumentoNotarialMasterDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;

public class DocumentoNotarialMasterBoImpl extends GenericBoImpl<DocumentoNotarialMaster> implements DocumentoNotarialMasterBo{

	private DocumentoNotarialMasterDao documentoNotarialMasterDao;
	
	public DocumentoNotarialMasterBoImpl() {
		this.documentoNotarialMasterDao = new DocumentoNotarialMasterDaoImpl();
		super.dao = this.documentoNotarialMasterDao;
	}
	
	@Override
	public DocumentoNotarialMaster findByEscrituraId(String id)throws NotariaException {
		return this.documentoNotarialMasterDao.findByEscrituraId(id);
	}
	
	@Override
	public Boolean existeMasterDeEscritura(String id)throws NotariaException {
		return this.documentoNotarialMasterDao.existeMasterDeEscritura(id);
	}
	
	
}
