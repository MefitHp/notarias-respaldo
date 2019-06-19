package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoExpediente;

public interface DocumentoExpedienteDao extends GenericDao<DocumentoExpediente, Integer>{
	
	public List<DocumentoExpediente> findByExpedienteId(String idExpediente) throws NotariaException;
	
	public List<DocumentoExpediente> findByActoId(String idActo) throws NotariaException;

}
