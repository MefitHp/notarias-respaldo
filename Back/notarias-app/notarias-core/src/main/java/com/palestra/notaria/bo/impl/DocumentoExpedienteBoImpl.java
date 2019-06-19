package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DocumentoExpedienteBo;
import com.palestra.notaria.dao.DocumentoExpedienteDao;
import com.palestra.notaria.dao.impl.DocumentoExpedienteDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoExpediente;

public class DocumentoExpedienteBoImpl extends GenericBoImpl<DocumentoExpediente> implements DocumentoExpedienteBo{

	private DocumentoExpedienteDao documentoExpedienteDao;
	
	public DocumentoExpedienteBoImpl() {
		this.documentoExpedienteDao = new DocumentoExpedienteDaoImpl();
		super.dao = this.documentoExpedienteDao;
	}
	
	@Override
	public List<DocumentoExpediente> findByExpedienteId(String expedienteId)throws NotariaException {
		return documentoExpedienteDao.findByExpedienteId(expedienteId);
	}
	
	@Override
	public List<DocumentoExpediente> findByActoId(String actoId)throws NotariaException {
		return documentoExpedienteDao.findByActoId(actoId);
	}
}
