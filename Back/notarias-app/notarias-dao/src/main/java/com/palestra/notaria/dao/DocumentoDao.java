package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Documento;

public interface DocumentoDao extends GenericDao<Documento, Integer> {
	
	public List<Documento> obtenerDocumento(String tipo) throws NotariaException;
	
	@Override
	public List<Documento> findAll() throws NotariaException;

	public Documento findBy(String iddocumento, Integer version) throws NotariaException;

}
