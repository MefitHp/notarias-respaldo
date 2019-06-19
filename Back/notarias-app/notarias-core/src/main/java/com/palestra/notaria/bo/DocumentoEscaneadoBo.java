package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoEscaneado;

public interface DocumentoEscaneadoBo extends GenericBo<DocumentoEscaneado>{
	
	public List<DocumentoEscaneado> obtenerDocEscaneados(String idexpediente) throws NotariaException;
	
	public String buscarArchivoPorId(String idDocumento)throws NotariaException;
}
