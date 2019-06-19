package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.TareaAtendidaDao;
import com.palestra.notaria.modelo.TareaAtendida;

public class TareaAtendidaDaoImpl extends GenericDaoImpl<TareaAtendida, Integer> implements TareaAtendidaDao {

	public TareaAtendidaDaoImpl(){
		super(TareaAtendida.class);
	}
}
