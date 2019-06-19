package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DocumentoEscaneadoBo;
import com.palestra.notaria.dao.DocumentoEscaneadoDao;
import com.palestra.notaria.dao.impl.DocumentoEscaneadoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoEscaneado;

public class DocumentoEscaneadoBoImpl extends GenericBoImpl<DocumentoEscaneado> implements DocumentoEscaneadoBo{

	private DocumentoEscaneadoDao documentoEscaneadoDao;
	
	public DocumentoEscaneadoBoImpl() {
		this.documentoEscaneadoDao = new DocumentoEscaneadoDaoImpl();
		super.dao = this.documentoEscaneadoDao;
	}

	@Override
	public List<DocumentoEscaneado> obtenerDocEscaneados(String idexpediente)throws NotariaException {
		return this.documentoEscaneadoDao.obtenerDocEscaneados(idexpediente);
	}

	@Override
	public String buscarArchivoPorId(String idDocumento)throws NotariaException {
		return this.documentoEscaneadoDao.buscarArchivoPorId(idDocumento);
	}

}
