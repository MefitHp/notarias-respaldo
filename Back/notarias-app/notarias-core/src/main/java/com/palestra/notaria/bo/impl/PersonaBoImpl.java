package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.PersonaBo;
import com.palestra.notaria.dao.PersonaDao;
import com.palestra.notaria.dao.impl.PersonaDaoImpl;
import com.palestra.notaria.dato.DatoBusquedaPersona;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Persona;

public class PersonaBoImpl extends GenericBoImpl<Persona> implements PersonaBo{
	
	private PersonaDao personaDao;
	
	public PersonaBoImpl() {
		this.personaDao = new PersonaDaoImpl();
		super.dao = this.personaDao;
	}

	@Override
	public List<Persona> buscarPersonaPorNombre(String subCadena) throws NotariaException {
		List<Persona> lista = this.personaDao.buscarPersonaPorNombre(subCadena);
		if(lista!=null)
			
			return new ArrayList<Persona>(lista);
		else
			return new ArrayList<Persona>();
	}

	@Override
	public List<Persona> obtenerListaCompleta() throws NotariaException{
		List<Persona> lista = this.personaDao.obtenerListaCompleta();
		if(lista!=null)
			return new ArrayList<Persona>(lista);
		else
			return new ArrayList<Persona>();
	}

	@Override
	public Persona buscarPorIdCompleto(Persona persona) throws NotariaException {
		Persona per = this.personaDao.buscarPorIdCompleto(persona);
		return per;
	}
	
	@Override
	public List<DatoBusquedaPersona> findPersonaByName(String nombre)throws NotariaException {
		return this.personaDao.findPersonaByName(nombre);
	}

	@Override
	public void eliminaPersona(Persona persona) throws NotariaException {
		this.personaDao.eliminaPersona(persona);
		
	}
    

}
