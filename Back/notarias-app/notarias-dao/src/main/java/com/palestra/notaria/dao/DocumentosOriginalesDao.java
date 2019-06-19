package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentosOriginales;

public interface DocumentosOriginalesDao extends GenericDao<DocumentosOriginales, Integer> {

	public List<DocumentosOriginales> obtenerListaOriginales(String idexpediente) throws NotariaException;
		
	public boolean actualizarRutaArchivoOriginal(String iddocor, String ruta) throws NotariaException;
	
	public String obtenerTramitePorOriginal(String iddoccor) throws NotariaException;
	
	public void borrar(String iddocori)throws NotariaException;

	String buscarArchivoPorId(String idDocumento) throws NotariaException;
}
