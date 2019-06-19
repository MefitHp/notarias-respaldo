package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.RegistroRiBo;
import com.palestra.notaria.dao.RegistroRiDao;
import com.palestra.notaria.dao.impl.RegistroRiDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.RegistroRi;

public class RegistroRiBoImpl extends GenericBoImpl<RegistroRi> implements
		RegistroRiBo {

	private RegistroRiDao registroRiDao;

	public RegistroRiBoImpl() {
		this.registroRiDao = new RegistroRiDaoImpl();
		super.dao = this.registroRiDao;
	}
	
	@Override
	public RegistroRi findFromComparecienteId(String id) throws NotariaException{
		return this.registroRiDao.findFromComparecienteId(id);
	}
	
	@Override
	public String buscarArchivoPorId(String id) throws NotariaException{
		return this.registroRiDao.buscarArchivoPorId(id);
	}
	
	@Override
	public boolean actualizarRutaArchivoRi(String idRegistroRi, String ruta)throws NotariaException {
		return this.registroRiDao.actualizarRutaArchivoRi(idRegistroRi, ruta);
	}
	
	@Override
	public boolean registrarRi(RegistroRi registroRi, String idCompareciente)throws NotariaException {
		return this.registroRiDao.registrarRi(registroRi, idCompareciente);
	}

}
