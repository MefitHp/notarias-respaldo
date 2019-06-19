package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.BitacoraFirmaBo;
import com.palestra.notaria.dao.BitacoraFirmaDao;
import com.palestra.notaria.dao.impl.BitacoraFirmaDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFirma;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Usuario;

public class BitacoraFirmaBoImpl extends GenericBoImpl<BitacoraFirma> implements BitacoraFirmaBo{
	
	private BitacoraFirmaDao bitacoraFirmaDao;
	
	public BitacoraFirmaBoImpl(){
		this.bitacoraFirmaDao = new BitacoraFirmaDaoImpl();
		super.dao = this.bitacoraFirmaDao;
	}
	
	@Override
	public List<BitacoraFirma> obtenerListaBitacoraFirma(
			BitacoraFirma bitacoraFirma) throws NotariaException {
		return this.bitacoraFirmaDao.obtenerListaBitacoraFirma(bitacoraFirma);
	}
	
	@Override
	public Boolean saveUpdateBitacora(ArrayList<Compareciente> comparecientes,
			Usuario usuario, String idescritura, String idexpediente) throws NotariaException {
		return this.bitacoraFirmaDao.saveUpdateBitacora(comparecientes, usuario, idescritura, idexpediente);
	}
}
