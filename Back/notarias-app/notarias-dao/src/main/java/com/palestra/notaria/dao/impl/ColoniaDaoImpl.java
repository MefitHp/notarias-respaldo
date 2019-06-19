package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.ColoniaDao;
import com.palestra.notaria.modelo.Colonia;

public class ColoniaDaoImpl extends GenericDaoImpl<Colonia, Integer> implements ColoniaDao {

	public ColoniaDaoImpl(){
		super(Colonia.class);
	}
}
