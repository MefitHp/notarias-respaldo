package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DocumentoVersionesBo;
import com.palestra.notaria.dao.DocumentoVersionesDao;
import com.palestra.notaria.dao.impl.DocumentoVersionesDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoVersiones;

public class DocumentoVersionesBoImpl extends GenericBoImpl<DocumentoVersiones>
		implements DocumentoVersionesBo {

	DocumentoVersionesDao docVersionesDao;
	
	public DocumentoVersionesBoImpl(){
		this.docVersionesDao = new DocumentoVersionesDaoImpl();
		super.dao = this.docVersionesDao;
	}

	@Override
	public DocumentoVersiones findByDocumentoPublicado(String iddocumento) throws NotariaException {
		return this.docVersionesDao.findByDocumentoPublicado(iddocumento);
	}

	@Override
	public List<DocumentoVersiones> listarNoPublicados() throws NotariaException {
		return this.docVersionesDao.listarNoPublicados();
	}

	@Override
	public void setInactivo(String iddocumento)throws NotariaException {
		 this.docVersionesDao.setInactivo(iddocumento);

	}

	@Override
	public Integer getMaxVersionOfDoc(String iddocumento)
			throws NotariaException {
		return this.docVersionesDao.getMaxVersionOfDoc(iddocumento);
	}
	
	@Override
	public DocumentoVersiones findBy(String iddocumento, Integer version) throws NotariaException{
		return this.docVersionesDao.findBy(iddocumento, version);
	}
}
