package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.AcuseReciboEscrituraDao;
import com.palestra.notaria.modelo.AcuseReciboEscritura;

public class AcuseReciboEscrituraDaoImpl extends
		GenericDaoImpl<AcuseReciboEscritura, Integer> implements
		AcuseReciboEscrituraDao {

	public AcuseReciboEscrituraDaoImpl(){
		super(AcuseReciboEscritura.class);
	}
}
