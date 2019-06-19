package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DocumentoAnexoBo;
import com.palestra.notaria.dao.DocumentoAnexoDao;
import com.palestra.notaria.dao.impl.DocumentoAnexoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.DocumentoAnexo;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;

public class DocumentoAnexoBoImpl extends GenericBoImpl<DocumentoAnexo>
		implements DocumentoAnexoBo {

	private DocumentoAnexoDao documentoAnexoDao;

	public DocumentoAnexoBoImpl() {
		this.documentoAnexoDao = new DocumentoAnexoDaoImpl();
		super.dao = this.documentoAnexoDao;
	}
	
	@Override
	public List<DocumentoNotarialMaster> obtenMasterAgendados(CalendarioCita cc)throws NotariaException {
		return this.documentoAnexoDao.obtenMasterAgendados(cc);
	}
	
	@Override
	public List<DocumentoNotarialMaster> obtenMasterDisponibles(String id)throws NotariaException {
		return this.documentoAnexoDao.obtenMasterDisponibles(id);
	}
}
