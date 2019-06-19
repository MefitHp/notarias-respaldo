package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFolios;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;

public interface SolicitudPrestamoFoliosDao extends
		GenericDao<SolicitudPrestamoFolios, Integer> {

	public List<SolicitudPrestamoFolios> listarPrestados() throws NotariaException;

	public List<SolicitudPrestamoFolios> listarPrestadosPorAbogado(String idusuario)
			throws NotariaException;

	public List<SolicitudPrestamoFolios> listarSolicitudesNoAtendidas()
			throws NotariaException;

	public List<SolicitudPrestamoFolios> buscarPrestamosPorAbogado(Usuario abogado) throws NotariaException;

	public boolean validaRagoFolios(long inicio, long fin) throws NotariaException;


	public boolean validaEscituraPrestada(SolicitudPrestamoFolios sft)
			throws NotariaException;

	public void generaTransacion(SolicitudPrestamoFolios solicitudPrestamo,
			BitacoraFolios bitacora) throws NotariaException;

	public void generaDevolucion(SolicitudPrestamoFolios solicitudPrestamo,
			BitacoraFolios bitFol)throws NotariaException;
		
	public List<SolicitudPrestamoFolios> listarSolicitadosPorAbogado(
			String idusuario)throws NotariaException;

	public void borrar(SolicitudPrestamoFolios solicitudPrestamo)throws NotariaException;


}
