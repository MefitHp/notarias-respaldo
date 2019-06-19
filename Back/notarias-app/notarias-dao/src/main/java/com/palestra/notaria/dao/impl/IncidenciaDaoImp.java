package com.palestra.notaria.dao.impl;

import java.util.List;

import com.palestra.notaria.dao.IncidenciaDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Documento;
import com.palestra.notaria.modelo.Incidencia;

public class IncidenciaDaoImp extends GenericDaoImpl<Incidencia,Integer> implements IncidenciaDao {

	public IncidenciaDaoImp() {
		super(Incidencia.class);
		
	}

	
	

}
