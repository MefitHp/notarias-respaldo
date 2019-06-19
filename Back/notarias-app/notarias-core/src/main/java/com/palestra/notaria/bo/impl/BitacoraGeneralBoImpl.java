package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.BitacoraGeneralBo;
import com.palestra.notaria.dao.BitacoraGeneralDao;
import com.palestra.notaria.dao.impl.BitacoraGeneralDaoImpl;
import com.palestra.notaria.modelo.BitacoraGeneral;

public class BitacoraGeneralBoImpl extends GenericBoImpl<BitacoraGeneral> implements BitacoraGeneralBo{

	private BitacoraGeneralDao bitacoraGeneralDao;
	
	public BitacoraGeneralBoImpl() {
		this.bitacoraGeneralDao = new BitacoraGeneralDaoImpl();
		super.dao = this.bitacoraGeneralDao;
	}

}
