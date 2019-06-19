package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoExpediente;

public interface DocumentoExpedienteBo extends GenericBo<DocumentoExpediente>{
	
	public List<DocumentoExpediente> findByExpedienteId(String expedienteId) throws NotariaException;
	
	public List<DocumentoExpediente> findByActoId(String actoId)throws NotariaException;

}
