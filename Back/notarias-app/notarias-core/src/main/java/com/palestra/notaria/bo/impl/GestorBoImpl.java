package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.GestorBo;
import com.palestra.notaria.dao.GestorDao;
import com.palestra.notaria.dao.impl.GestorDaoImpl;
import com.palestra.notaria.modelo.Gestor;

public class GestorBoImpl extends GenericBoImpl<Gestor> implements GestorBo {

	private GestorDao gestorDao;
	
	public GestorBoImpl(){
		this.gestorDao = new GestorDaoImpl();
		super.dao = this.gestorDao;
	}
}
