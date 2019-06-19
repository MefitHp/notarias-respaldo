package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.RegistroRi;

public interface RegistroRiBo extends GenericBo<RegistroRi> {
	
	RegistroRi findFromComparecienteId(String id)throws NotariaException;
	
	String buscarArchivoPorId(String id)throws NotariaException;
	
	boolean actualizarRutaArchivoRi(String idRegistroRi, String ruta)throws NotariaException;
	
	boolean registrarRi(RegistroRi registroRi, String idCompareciente) throws NotariaException;

}
