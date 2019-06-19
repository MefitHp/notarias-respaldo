package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;

public interface SolicitudPrestamoFoliosBo extends
		GenericBo<SolicitudPrestamoFolios> {

	public List<SolicitudPrestamoFolios> listarPrestados() throws NotariaException;

	public List<SolicitudPrestamoFolios> listarPrestadosPorAbogado(String idusuario)
			throws NotariaException;

	public List<SolicitudPrestamoFolios> listarSolicitudesNoAtendidas()
			throws NotariaException;

	public List<SolicitudPrestamoFolios> buscarPrestamosPorAbogado(Usuario abogado) throws NotariaException;

	public void aprobarSolicitud(SolicitudPrestamoFolios solicitudPrestamo)throws NotariaException;


	public void aprobarDevolucion(SolicitudPrestamoFolios solicitudPrestamo,
			String comentario) throws NotariaException;

	public List<SolicitudPrestamoFolios> buscarSolicitudesPorAbogado(
			String idusuario)throws NotariaException;

	public void borrar(SolicitudPrestamoFolios solicitudPrestamo)throws NotariaException;


}
