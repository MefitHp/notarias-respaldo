package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Contacto;

public interface ContactoDao extends GenericDao<Contacto, Integer> {

	public Contacto findByPersona(String idpersona) throws NotariaException;
		
}
