package com.palestra.notaria.bo.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.palestra.notaria.bo.SolicitudPrestamoFoliosBo;
import com.palestra.notaria.dao.SolicitudPrestamoFoliosDao;
import com.palestra.notaria.dao.impl.SolicitudPrestamoFoliosDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFolios;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;

public class SolicitudPrestamoFoliosBoImpl extends
		GenericBoImpl<SolicitudPrestamoFolios> implements
		SolicitudPrestamoFoliosBo {

	SolicitudPrestamoFoliosDao solPresDao;
	
	public SolicitudPrestamoFoliosBoImpl(){
		solPresDao = new SolicitudPrestamoFoliosDaoImpl();
		super.dao = solPresDao;
	}

	@Override
	public List<SolicitudPrestamoFolios> listarPrestados() throws NotariaException {
		List<SolicitudPrestamoFolios> solPres =  solPresDao.listarPrestados();
		expireFol(solPres);
		return solPres;
	}
	
	@Override
	public List<SolicitudPrestamoFolios> listarPrestadosPorAbogado(String idusuario) throws NotariaException{
		return solPresDao.listarPrestadosPorAbogado(idusuario);
	}
	
	@Override
	public List<SolicitudPrestamoFolios> listarSolicitudesNoAtendidas() throws NotariaException{
		return solPresDao.listarSolicitudesNoAtendidas();
	}

	@Override
	public List<SolicitudPrestamoFolios> buscarPrestamosPorAbogado(
			Usuario abogado) throws NotariaException {
		
		List<SolicitudPrestamoFolios> listaPrestados = solPresDao.buscarPrestamosPorAbogado(abogado);
		expireFol(listaPrestados);
		return listaPrestados;
	}
	
	@Override
	public SolicitudPrestamoFolios save(SolicitudPrestamoFolios solfol) throws NotariaException{
		
		//VICTOR: Se comenta la validación de folios debido a que ahora el prestamo se hace por número de escritura y no por folio (Hoja)
		//boolean validaFolios = solPresDao.validaRagoFolios(solfol.getInfolioinicio(), solfol.getInfoliofin());
				
		
		boolean validaEscritura = solPresDao.validaEscituraPrestada(solfol);
		
		if(validaEscritura){
			solfol = solPresDao.save(solfol);
		}else{
			throw new NotariaException("La escritura por el momento no se encuentra disponible");
		}		
		return solfol;
	}

	@Override
	public void aprobarSolicitud(SolicitudPrestamoFolios solicitudPrestamo)
			throws NotariaException {
			
			//VICTOR: GENERO LA BITÁCORA DE LA TRANSACCIÓN PARA SEGUIMIENTO DE LOS DOCUMENTOS
			BitacoraFolios bitFol = new BitacoraFolios();
			bitFol.setNumeroescritura(solicitudPrestamo.getNumeroEscritura());
			bitFol.setUsuarioEntrega(solicitudPrestamo.getUsuarioPrestador());
			bitFol.setUsuarioRecibe(solicitudPrestamo.getUsuarioRecibe());
			java.util.Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			bitFol.setFechaoperacion(timestamp);
			bitFol.setTipooperacion("PRES");
			bitFol.setIddevolucionfolio(GeneradorId.generaId(bitFol));
			bitFol.setIdsesion(solicitudPrestamo.getIdsesion());

		
			solPresDao.generaTransacion(solicitudPrestamo,bitFol);
			
	}
	
	@Override
	public void aprobarDevolucion(SolicitudPrestamoFolios solicitudPrestamo, String comentario)
			throws NotariaException {
			
			//VICTOR: GENERO LA BITÁCORA DE LA TRANSACCIÓN PARA SEGUIMIENTO DE LOS DOCUMENTOS
			BitacoraFolios bitFol = new BitacoraFolios();
			bitFol.setNumeroescritura(solicitudPrestamo.getNumeroEscritura());
			bitFol.setUsuarioEntrega(solicitudPrestamo.getUsuarioPrestador());
			bitFol.setUsuarioRecibe(solicitudPrestamo.getUsuarioRecibe());
			java.util.Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			bitFol.setFechaoperacion(timestamp);
			bitFol.setTipooperacion("DEVO");
			bitFol.setDscomentario(comentario);
			bitFol.setIddevolucionfolio(GeneradorId.generaId(bitFol));
			bitFol.setIdsesion(solicitudPrestamo.getIdsesion());
			solPresDao.generaDevolucion(solicitudPrestamo,bitFol);
			
	}

	@Override
	public List<SolicitudPrestamoFolios> buscarSolicitudesPorAbogado(
			String idusuario) throws NotariaException {
			
		return solPresDao.listarSolicitadosPorAbogado(idusuario);

	}
	
	

	@Override
	public void borrar(SolicitudPrestamoFolios solicitudPrestamo)
			throws NotariaException {
			
			solPresDao.borrar(solicitudPrestamo);
		
	}
	
	/*
	 *	Método que sirve para saber el estatus actual del prestamo
	 * 
	 * */
	private void expireFol(List<SolicitudPrestamoFolios> lista){
		
		for(SolicitudPrestamoFolios sol :lista){
			if(sol.getFechaDevolucion().before(new Date()))
			{
				sol.setStatus('E');
			}else{
				sol.setStatus('O');
			}
		}
		
	}

	
	
}
