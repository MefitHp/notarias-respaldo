package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.dato.DatoInmueble;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Inmueble;

public interface InmuebleDao extends GenericDao<Inmueble, Integer> {
	
	List<Inmueble> obtenerInmueblesPorExpediente(String expedienteId) throws NotariaException;
	
	Inmueble buscarPorIdCompleto(Inmueble inmueble) throws NotariaException;
	
	Boolean registrarInmueble(Inmueble inmueble) throws NotariaException; 
	
	Boolean eliminarInmueble(Inmueble inmueble) throws NotariaException;
	
	List<DatoInmueble> obtenListaInmuebles(Inmueble inmueble) throws NotariaException;

}
