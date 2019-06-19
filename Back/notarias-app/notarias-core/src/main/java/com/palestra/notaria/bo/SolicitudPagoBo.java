package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.SolicitudPago;

public interface SolicitudPagoBo extends GenericBo<SolicitudPago>{
	
	public SolicitudPago obtenerPorIdCompleto(String idsolicitudpago)throws NotariaException;
	
	public List<SolicitudPago> obtenerSolicitudesporExp(String idexpediente)throws NotariaException;
	
	public boolean eliminarSolicitud(SolicitudPago sp, String idusuario,String idexpediente) throws NotariaException;

}
