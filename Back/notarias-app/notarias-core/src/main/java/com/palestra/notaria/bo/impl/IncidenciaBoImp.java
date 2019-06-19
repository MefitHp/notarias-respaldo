package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.IncidenciaBo;
import com.palestra.notaria.dao.IncidenciaDao;
import com.palestra.notaria.dao.impl.IncidenciaDaoImp;
import com.palestra.notaria.modelo.Incidencia;

public class IncidenciaBoImp extends GenericBoImpl<Incidencia> implements IncidenciaBo {

	private IncidenciaDao incidenciaDao;
	
	public IncidenciaBoImp() {
		this.incidenciaDao = new IncidenciaDaoImp();
		super.dao = this.incidenciaDao;
	}

}