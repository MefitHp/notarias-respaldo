package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoEscaneado;

public interface DocumentoEscaneadoDao extends
		GenericDao<DocumentoEscaneado, Integer> {
	
	public List<DocumentoEscaneado> obtenerDocEscaneados(String idexpediente) throws NotariaException;

	public String buscarArchivoPorId(String idDocumento) throws NotariaException;
}
