package com.palestra.notaria.uif.core.dao;

import java.util.List;

import com.palestra.notaria.uif.core.models.Persona;
import com.palestra.notaria.uif.exceptions.NotariaException;



public interface PersonaDao extends GenericDao<Persona, Integer> {
	
	public List<Persona> buscarPersonaPorNombre(String subCadena) throws NotariaException;
	
	public List<Persona> obtenerListaCompleta() throws NotariaException;
	
	public Persona buscarPorIdCompleto(Persona persona) throws NotariaException;
		
	public void eliminaPersona (Persona persona)throws NotariaException;
	

}
