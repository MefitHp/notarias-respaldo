package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.dato.DatoBusquedaPersona;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Persona;

public interface PersonaDao extends GenericDao<Persona, Integer> {
	
	public List<Persona> buscarPersonaPorNombre(String subCadena) throws NotariaException;
	
	public List<Persona> obtenerListaCompleta() throws NotariaException;
	
	public Persona buscarPorIdCompleto(Persona persona) throws NotariaException;
	
	public List<DatoBusquedaPersona> findPersonaByName(String nombre) throws NotariaException;
	
	public void eliminaPersona (Persona persona)throws NotariaException;
	

}
