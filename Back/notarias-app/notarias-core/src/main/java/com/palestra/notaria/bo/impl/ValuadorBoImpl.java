package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.ValuadorBo;
import com.palestra.notaria.dao.ValuadorDao;
import com.palestra.notaria.dao.impl.ValuadorDaoImpl;
import com.palestra.notaria.modelo.Valuador;

public class ValuadorBoImpl extends GenericBoImpl<Valuador> implements ValuadorBo {

	private ValuadorDao valuadorDao;
	
	public ValuadorBoImpl(){
		this.valuadorDao = new ValuadorDaoImpl();
		super.dao = this.valuadorDao;
	}
}
