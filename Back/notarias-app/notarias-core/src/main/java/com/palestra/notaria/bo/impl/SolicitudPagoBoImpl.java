package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.SolicitudPagoBo;
import com.palestra.notaria.dao.SolicitudPagoDao;
import com.palestra.notaria.dao.impl.SolicitudPagoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.SolicitudPago;

public class SolicitudPagoBoImpl extends GenericBoImpl<SolicitudPago> implements SolicitudPagoBo{

	private SolicitudPagoDao solicitudPagoDao;
	
	public SolicitudPagoBoImpl() {
		this.solicitudPagoDao = new SolicitudPagoDaoImpl();
		super.dao = this.solicitudPagoDao;
	}

	@Override
	public SolicitudPago obtenerPorIdCompleto(String idsolicitudpago)throws NotariaException {
		return this.solicitudPagoDao.obtenerPorIdCompleto(idsolicitudpago);
	}

	@Override
	public List<SolicitudPago> obtenerSolicitudesporExp(String idexpediente)throws NotariaException {
		return this.solicitudPagoDao.obtenerSolicitudesporExp(idexpediente);
	}

	@Override
	public boolean eliminarSolicitud(SolicitudPago sp, String idusuario,
			String idexpediente)throws NotariaException {
		return this.solicitudPagoDao.eliminarSolicitud(sp, idusuario, idexpediente);
	}
}
