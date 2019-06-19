package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoVersiones;

public interface DocumentoVersionesBo extends GenericBo<DocumentoVersiones> {

	public DocumentoVersiones findByDocumentoPublicado(String iddocumento) throws NotariaException;
	
	public List<DocumentoVersiones> listarNoPublicados() throws NotariaException;
	
	public void setInactivo(String iddocumento) throws NotariaException;
	
	public Integer getMaxVersionOfDoc(String iddocumento) throws NotariaException;

	public DocumentoVersiones findBy(String iddocumento, Integer version) throws NotariaException;
}
