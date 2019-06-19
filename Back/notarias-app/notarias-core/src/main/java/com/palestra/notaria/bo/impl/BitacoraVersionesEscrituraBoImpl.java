package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.BitacoraVersionesEscrituraBo;
import com.palestra.notaria.dao.BitacoraVersionesEscrituraDao;
import com.palestra.notaria.dao.impl.BitacoraVersionesEscrituraDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraVersionesEscritura;

public class BitacoraVersionesEscrituraBoImpl extends
		GenericBoImpl<BitacoraVersionesEscritura> implements
		BitacoraVersionesEscrituraBo {

	private BitacoraVersionesEscrituraDao bitacoraVersionesEscrituraDao;

	public BitacoraVersionesEscrituraBoImpl() {
		this.bitacoraVersionesEscrituraDao = new BitacoraVersionesEscrituraDaoImpl();
		super.dao = this.bitacoraVersionesEscrituraDao;
	}
	
	@Override
	public List<BitacoraVersionesEscritura> findByEscrituraId(String escrituraId) throws NotariaException {
		return bitacoraVersionesEscrituraDao.findByEscrituraId(escrituraId);
	}
	
	@Override
	public int deleteByEscrituraId(String idEscritura) throws NotariaException {
		return bitacoraVersionesEscrituraDao.deleteByEscrituraId(idEscritura);
	}

}
