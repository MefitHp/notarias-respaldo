package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.SolicitudPago;

public interface SolicitudPagoDao extends GenericDao<SolicitudPago, Integer> {
	
	public SolicitudPago obtenerPorIdCompleto(String idsolicitudpago) throws NotariaException;
	
	public List<SolicitudPago> obtenerSolicitudesporExp(String idexpediente) throws NotariaException;
	
	public boolean eliminarSolicitud(SolicitudPago sp, String idusuario, String idexpediente) throws NotariaException;

}
