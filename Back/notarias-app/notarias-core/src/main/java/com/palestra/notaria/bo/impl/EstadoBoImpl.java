package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.EstadoBo;
import com.palestra.notaria.dao.EstadoDao;
import com.palestra.notaria.dao.impl.EstadoDaoImpl;
import com.palestra.notaria.modelo.Estado;

public class EstadoBoImpl extends GenericBoImpl<Estado> implements EstadoBo{
	
	private EstadoDao estadoDao;
	
	public EstadoBoImpl(){
		this.estadoDao = new EstadoDaoImpl();
		super.dao = this.estadoDao;
	}

}
