package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.InmuebleBo;
import com.palestra.notaria.dao.InmuebleDao;
import com.palestra.notaria.dao.impl.InmuebleDaoImpl;
import com.palestra.notaria.dato.DatoInmueble;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Inmueble;

public class InmuebleBoImpl extends GenericBoImpl<Inmueble> implements
		InmuebleBo {

	private InmuebleDao inmuebleDao;

	public InmuebleBoImpl() {
		this.inmuebleDao = new InmuebleDaoImpl();
		super.dao = this.inmuebleDao;
	}

	@Override
	public List<Inmueble> obtenerInmueblesPorExpediente(String expedienteId)throws NotariaException {

		return this.inmuebleDao.obtenerInmueblesPorExpediente(expedienteId);
	}

	@Override
	public Inmueble buscarPorIdCompleto(Inmueble inmueble)throws NotariaException {
		return this.inmuebleDao.buscarPorIdCompleto(inmueble);
	}

	@Override
	public Boolean registrarInmueble(Inmueble inmueble) throws NotariaException{
		return this.inmuebleDao.registrarInmueble(inmueble);
	}

	@Override
	public Boolean eliminarInmueble(Inmueble inmueble) throws NotariaException{
		return this.inmuebleDao.eliminarInmueble(inmueble);
	}
	
	@Override
	public List<DatoInmueble> obtenListaInmuebles(Inmueble inmueble) throws NotariaException{
		return this.inmuebleDao.obtenListaInmuebles(inmueble);
	}
}
