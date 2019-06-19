package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.AcuseReciboPersonasDao;
import com.palestra.notaria.modelo.AcuseReciboPersonas;

public class AcuseReciboPeronasDaoImpl extends
		GenericDaoImpl<AcuseReciboPersonas, Integer> implements
		AcuseReciboPersonasDao {

	public AcuseReciboPeronasDaoImpl(){
		super(AcuseReciboPersonas.class);
	}
}
