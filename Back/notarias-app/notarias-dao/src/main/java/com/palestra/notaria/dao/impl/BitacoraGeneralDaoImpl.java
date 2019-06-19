package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.BitacoraGeneralDao;
import com.palestra.notaria.modelo.BitacoraGeneral;

public class BitacoraGeneralDaoImpl extends GenericDaoImpl<BitacoraGeneral, Integer> implements BitacoraGeneralDao{

	public BitacoraGeneralDaoImpl() {
		super(BitacoraGeneral.class);
	}

}
