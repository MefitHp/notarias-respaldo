package com.palestra.notaria.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.BitacoraFoliosDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFolios;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;

public class BitacoraFoliosDaoImpl extends
		GenericDaoImpl<BitacoraFolios, Integer> implements
		BitacoraFoliosDao {

	public BitacoraFoliosDaoImpl(){
		super(BitacoraFolios.class);
	}
	
	/*
	@Override
	public List<DevolucionFolios> listarDevueltos() throws NotariaException{
		EntityManager em = factory.createEntityManager();
		List<DevolucionFolios> devoluciones = new ArrayList<DevolucionFolios>();
		try{
			TypedQuery<SolicitudPrestamoFolios> queryPrestamoFolios = em.createQuery("SELECT s FROM SolicitudPrestamoFolios s" +
					" WHERE s.isPrestamoTerminado = true", SolicitudPrestamoFolios.class);
			TypedQuery<DevolucionFolios> queryDevoluciones = em.createQuery("SELECT d FROM DevolucionFolios d WHERE d.ispendiente = false ",
					DevolucionFolios.class);
			List<SolicitudPrestamoFolios> prestamoFolios = queryPrestamoFolios.getResultList();
			devoluciones = queryDevoluciones.getResultList();
			for(SolicitudPrestamoFolios solPrest:prestamoFolios){
				for(DevolucionFolios devolFol: devoluciones){
					if(devolFol.getInfolioinicio() >= solPrest.getInfolioinicio() 
							&& devolFol.getInfoliofin() <= solPrest.getInfoliofin()){
//						si los folios de la devolucion están dentro de los que se hayan prestado anteriormente se setean
//						las fechas de la solicitud
						devolFol.setFechaEntrega(solPrest.getFechaEntrega());
						devolFol.setFechaDevolucion(solPrest.getFechaDevolucion());
					}
				}
			}
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}
		return devoluciones;
	}*/
	
	
	// VICTOR: SE COMENTO EL METODO ANTERIOR DEBIDO A QUE NO SE PRESTA POR FOLIO:HOJA SI NO SE PRESTA POR ESCRITURA
	/*@Override
	public List<SolicitudPrestamoFolios> listarDevueltos() throws NotariaException{
		EntityManager em = factory.createEntityManager();
		List<SolicitudPrestamoFolios> prestamoFolios = null;
		try{
			TypedQuery<SolicitudPrestamoFolios> queryPrestamoFolios = em.createQuery("SELECT s FROM SolicitudPrestamoFolios s" +
					" WHERE s.isPrestamoTerminado = true", SolicitudPrestamoFolios.class);
			prestamoFolios = queryPrestamoFolios.getResultList();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}
		return prestamoFolios;
	}*/

	@Override
	public List<BitacoraFolios> listarDevueltos() throws NotariaException{
		EntityManager em = factory.createEntityManager();
		List<BitacoraFolios> prestamoFolios = null;
		try{
			TypedQuery<BitacoraFolios> queryPrestamoFolios = em.createQuery("SELECT bf FROM BitacoraFolios bf" +
					" WHERE bf.tipooperacion = 'DEVO'", BitacoraFolios.class);
			prestamoFolios = queryPrestamoFolios.getResultList();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		return prestamoFolios;
	}
	
	
	
	
	
	@Override
	public List<BitacoraFolios> listarXnumEsc(String numesc)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		List<BitacoraFolios> prestamoFolios = null;
		try{
			TypedQuery<BitacoraFolios> queryPrestamoFolios = em.createQuery("SELECT bf FROM BitacoraFolios bf" +
					" WHERE bf.numeroescritura=:numesc ORDER BY bf.fechaoperacion DESC", BitacoraFolios.class);
			queryPrestamoFolios.setParameter("numesc", numesc);
			prestamoFolios = queryPrestamoFolios.getResultList();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		return prestamoFolios;
	}
	
	@Override
	public List<BitacoraFolios> listarXusuario(Usuario usu) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		List<BitacoraFolios> prestamoFolios = null;
		try{
			TypedQuery<BitacoraFolios> queryPrestamoFolios = em.createQuery("SELECT bf FROM BitacoraFolios bf"+" WHERE bf.usuarioEntrega.idusuario = :usu OR bf.usuarioRecibe.idusuario=:usu ORDER BY bf.fechaoperacion DESC", BitacoraFolios.class);
			queryPrestamoFolios.setParameter("usu", usu.getIdusuario());
			prestamoFolios = queryPrestamoFolios.getResultList();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		return prestamoFolios;
	}
	
	
	@Override
	public List<BitacoraFolios> listarXRangoFecha(Timestamp inicio, Timestamp fin) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		List<BitacoraFolios> prestamoFolios = null;
		try{
			TypedQuery<BitacoraFolios> queryPrestamoFolios = em.createQuery("SELECT bf FROM BitacoraFolios bf" +
					" WHERE bf.fechaoperacion BETWEEN :inicio AND :fin ORDER BY bf.fechaoperacion DESC", BitacoraFolios.class);
			queryPrestamoFolios.setParameter("inicio", inicio);
			queryPrestamoFolios.setParameter("fin", fin);
			prestamoFolios = queryPrestamoFolios.getResultList();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		return prestamoFolios;
	}
	
	
	@Override
	public List<BitacoraFolios> listarDevueltosPorAbogado(String idusuario) throws NotariaException{
		return executeQuery("SELECT d FROM DevolucionFolios d WHERE d.ispendiente = false" +
				" AND d.usuarioEntrega.idusuario = ?1", idusuario);
	}
}
