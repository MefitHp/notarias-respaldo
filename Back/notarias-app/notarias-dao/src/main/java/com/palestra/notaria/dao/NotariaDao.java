package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Notaria;

public interface NotariaDao extends GenericDao<Notaria, Integer> {
	
	String obtenerNumNotariaByInicialesNotario(String inicialesNotario) throws NotariaException;

}
