package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.GuardiaBo;
import com.palestra.notaria.dao.GuardiaDao;
import com.palestra.notaria.dao.impl.GuardiaDaoImpl;
import com.palestra.notaria.modelo.Guardia;

public class GuardiaBoImpl extends GenericBoImpl<Guardia> implements GuardiaBo{
	
	public GuardiaDao guardiaDao;
	
	public GuardiaBoImpl() {
		this.guardiaDao = new GuardiaDaoImpl();
		super.dao = this.guardiaDao;
	}

}
