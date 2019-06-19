package com.palestra.notaria.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import com.palestra.notaria.dao.FuncionDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Funcion;
import com.palestra.notaria.modelo.FuncionParametro;

public class FuncionDaoImpl extends GenericDaoImpl<Funcion, Integer> implements
		FuncionDao {

	Logger logger = Logger.getLogger(FuncionDaoImpl.class);
	
	public FuncionDaoImpl() {
		super(Funcion.class);
	}

	@Override
	public List<FuncionParametro> listaParametros(String identificador)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<FuncionParametro> query = em.createNamedQuery(
					"Funcion.listaParametros", FuncionParametro.class);
			query.setParameter("identificador", identificador);
			return query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public List<FuncionParametro> listaParametros(Funcion funcion)
			throws NotariaException {
		return listaParametros(funcion.getIdentificador());
	}

	@Override
	public void eliminaParametros(String identificador) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			Query query = em.createNamedQuery("FuncionParametro.eliminaParametros");
			query.setParameter("identificador", identificador);
			tx.begin();
			query.executeUpdate();
			tx.commit();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public void eliminaParametros(Funcion funcion) throws NotariaException {
		eliminaParametros(funcion.getIdentificador());
	}

	@Override
	public List<Funcion> listaFunciones()throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<Funcion> query = em.createNamedQuery("Funcion.findAll", Funcion.class);
			List<Funcion> funciones = query.getResultList();
			for (Funcion funcion:funciones){
				for (FuncionParametro param : funcion.getDetalleList()){
					param.setIdfuncion(null);
				}
			}
			return funciones;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@Override
	public Integer actualizaParametros(Funcion funcion)
			throws NotariaException {
		List<FuncionParametro> params = funcion.getDetalleList();
		eliminaParametros(funcion.getIdentificador());
		logger.info("=====> Se eliminaron los parametros de la funcion "+funcion.getIdentificador());
		Funcion nuevaFuncion = this.findById(funcion.getIdentificador());		
		if (nuevaFuncion !=null){
			logger.info("=====> Se localizo al objeto en la unidad de persistencia "+ nuevaFuncion.getIdentificador()+"["+params.size()+"]");
			//nuevaFuncion.setDetalleList(null);
			for(FuncionParametro param: params){
				param.setIdfuncion(nuevaFuncion);
				param.setIdsesion(nuevaFuncion.getIdsesion());
				param.setTmstmp(new Timestamp(new Date().getTime()));
				nuevaFuncion.getDetalleList().add(param);
				logger.info(String.format("=====>\t\tSe agregan detalles al objeto %s en la unidad de persistencia [%s]", nuevaFuncion.getIdentificador(), param.getParametro()));
			}
			logger.info(String.format("=====>Se cargaron en la lista los parametros, inicia persistencia de %s%n",funcion.getNombre()));
			nuevaFuncion.setDescripcion(funcion.getDescripcion());
			nuevaFuncion.setForma(funcion.getForma());
			nuevaFuncion.setNombre(funcion.getNombre());
			nuevaFuncion.setRetorno(funcion.getRetorno());
			this.update(nuevaFuncion);
			logger.info(String.format("=====>Persistio el objeto correctamente %s.%n",nuevaFuncion.getNombre()));
			return params.size()+1;
		}else{
			String message = String.format("El objeto enviado no se localizo en la unidad de persistencia (%s)", funcion.getIdentificador());
			logger.error(message);
			throw new NotariaException(message);
		}
	}
	
	@Override
	public Funcion findByNombre(String nombreFuncion)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<Funcion> query = em.createNamedQuery("Funcion.findByNombre", Funcion.class);
			query.setParameter("nombre", nombreFuncion);
			List<Funcion> funciones = query.getResultList();
			if(funciones!=null && funciones.size()>0){
				for(FuncionParametro fp: funciones.get(0).getDetalleList()){
					fp.setIdfuncion(null);
				}
				return funciones.get(0);
			}else{
				return null;
			}		
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
}
