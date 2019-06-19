package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentosOriginales;
import com.palestra.notaria.modelo.Usuario;

public interface DocumentosOriginalesBo extends GenericBo<DocumentosOriginales>{
	
	public List<DocumentosOriginales> obtenerListaOriginales(String idexpediente)throws NotariaException;
	public String obtenerTramitePorOriginal(String iddoccor) throws NotariaException;
	void borrar(String iddocori) throws NotariaException;
	String buscarArchivoPorId(String idDocumento) throws NotariaException;
	public boolean actualizarRutaArchivoOriginal(String idevidencia, String ruta)
			throws NotariaException;
	void validaEnvioExpJudiciales(DocumentosOriginales doc, Usuario usuario) throws NotariaException;
}
