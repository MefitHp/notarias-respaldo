package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Documento;

public interface DocumentoBo extends GenericBo<Documento>{
	
	@Override
	public List<Documento> findAll() throws NotariaException;

	public List<Documento> obtenerDocumento(String tipo) throws NotariaException;

	public Documento findBy(String iddocumento, Integer version) throws NotariaException;
}
