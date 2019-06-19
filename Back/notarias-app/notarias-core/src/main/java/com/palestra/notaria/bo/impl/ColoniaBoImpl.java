package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.ColoniaBo;
import com.palestra.notaria.dao.ColoniaDao;
import com.palestra.notaria.dao.impl.ColoniaDaoImpl;
import com.palestra.notaria.modelo.Colonia;

public class ColoniaBoImpl extends GenericBoImpl<Colonia> implements ColoniaBo {

	private ColoniaDao coloniaDao;

	public ColoniaBoImpl() {
		this.coloniaDao = new ColoniaDaoImpl();
		super.dao = this.coloniaDao;
	}

}
