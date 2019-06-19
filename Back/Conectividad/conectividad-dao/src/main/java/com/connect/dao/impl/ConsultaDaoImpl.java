package com.connect.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;

import com.connect.dao.ConsultaDao;
import com.connect.dao.exception.ConectividadDaoException;
import com.connect.modelo.VistaComparecientes;
import com.connect.modelo.VistaExpediente;

public class ConsultaDaoImpl implements ConsultaDao {

	protected static EntityManagerFactory factory = Persistence.createEntityManagerFactory("conexion");
	
	
	@Override
	public int totalPrestados() throws ConectividadDaoException{
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<VistaExpediente> query;
			query = em.createQuery("SELECT exp FROM VistaExpediente exp", VistaExpediente.class);
			List<VistaExpediente> exps = query.getResultList();			
			return exps.size();
		}catch(Exception e){
			e.printStackTrace(System.out);
			throw new ConectividadDaoException(e.getMessage(),e.getCause());
		}finally{
			em.close();
		}
		
	}
	
	@Override
	public List<VistaExpediente> buscarComparecientesPorExpediente(String expediente) throws ConectividadDaoException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<VistaExpediente> query;
			query = em.createQuery("SELECT exp FROM VistaExpediente exp WHERE exp.expediente = ?1", VistaExpediente.class);
			query.setParameter(1, expediente);
			List<VistaExpediente> exps = query.getResultList();
			for(VistaExpediente expedienteLoop: exps){
				List<VistaComparecientes> compas = expedienteLoop.getComparecientes();
				Hibernate.initialize(compas);
				expedienteLoop.setComparecientes(compas);
			}
			return exps;
		}catch(Exception e){
			e.printStackTrace(System.out);
			throw new ConectividadDaoException(e.getMessage(),e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<VistaExpediente> listarVistaExpediente() throws ConectividadDaoException{
		EntityManager em = factory.createEntityManager();
		try{
			int results=100;
			TypedQuery<VistaExpediente> query;
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT exp FROM VistaExpediente exp ORDER BY exp.esc");
			query = em.createQuery(sql.toString(), VistaExpediente.class);
			query.setMaxResults(results);
			query.setFirstResult(5);
			return query.getResultList();
		}catch(Exception e){
			e.printStackTrace(System.out);
			throw new ConectividadDaoException(e.getMessage(),e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<VistaExpediente> buscarExpedientes(VistaExpediente exp, Integer resultadosPorPagina) throws ConectividadDaoException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<VistaExpediente> query;
			StringBuilder sql = new StringBuilder();
			Map<String, Object> params = new HashMap<String, Object>();
			sql.append("SELECT exp FROM VistaExpediente exp ");
			boolean first = true;
			if(exp.getExpediente() != null && !exp.getExpediente().isEmpty()){
				sql.append(first ? "WHERE " : "AND ");
				sql.append(" exp.expediente = :numexp ");
				params.put("numexp", exp.getExpediente());
				first = false;
			}
			if(exp.getEscritura()!= null){
				sql.append(first ? "WHERE " : "AND ");
				sql.append(" exp.escritura = :esc ");
				params.put("esc", exp.getEscritura());
				first = false;
			}
			if(exp.getOpe()!= null && !exp.getOpe().isEmpty()){
				sql.append(first ? "WHERE ":"AND ");
				sql.append(" exp.ope = :ope ");
				params.put("ope", exp.getOpe());
				first = false;
			}
			if(exp.getFechaFirma()!=null && !exp.getFechaFirma().isEmpty()){
				sql.append(first ? "WHERE ":"AND ");
				sql.append(" exp.fechaFirma = :fechaFirma");
				params.put("fechaFirma", exp.getFechaFirma());
				first = false;
			}
			if(exp.getIniciales()!=null && !exp.getIniciales().isEmpty()){
				sql.append(first ? "WHERE ":"AND ");
				sql.append(" exp.iniciales = :iniciales");
				params.put("iniciales", exp.getIniciales());
				first = false;
			}
			if(exp.getRuta()!=null && !exp.getRuta().isEmpty()){
				sql.append(first ? "WHERE ":"AND ");
				sql.append(" exp.ruta = :ruta");
				params.put("ruta",exp.getRuta());
				first = false;
			}
			sql.append(" ORDER BY exp.escritura");
			query = em.createQuery(sql.toString(), VistaExpediente.class);
			System.out.println(sql.toString());
			for (String param : params.keySet()) {
			System.out.println("parametro "+param);
			System.out.println("param "+params.get(param));
			query.setParameter(param, params.get(param));
		}
			query.setMaxResults(resultadosPorPagina);
			query.setFirstResult(resultadosPorPagina*(exp.getPag()));
			return  query.getResultList();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new ConectividadDaoException(e.getMessage(),e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public Long obtenerTotalRegistrosBusqueda(VistaExpediente exp) throws ConectividadDaoException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<Long> query;
			StringBuilder sql = new StringBuilder();
			Map<String, Object> params = new HashMap<String, Object>();
			sql.append("SELECT COUNT(*) FROM VistaExpediente exp ");
			boolean first = true;
			if(exp.getExpediente() != null && !exp.getExpediente().isEmpty()){
				sql.append(first ? "WHERE " : "AND ");
				sql.append(" exp.expediente = :numexp ");
				params.put("numexp", exp.getExpediente());
				first = false;
			}
			if(exp.getEscritura()!= null){
				sql.append(first ? "WHERE " : "AND ");
				sql.append(" exp.escritura = :esc ");
				params.put("esc", exp.getEscritura());
				first = false;
			}
			if(exp.getOpe()!= null && !exp.getOpe().isEmpty()){
				sql.append(first ? "WHERE ":"AND ");
				sql.append(" exp.ope = :ope ");
				params.put("ope", exp.getOpe());
				first = false;
			}
			if(exp.getFechaFirma()!=null && !exp.getFechaFirma().isEmpty()){
				sql.append(first ? "WHERE ":"AND ");
				sql.append(" exp.fechaFirma = :fechaFirma");
				params.put("fechaFirma", exp.getFechaFirma());
				first = false;
			}
			if(exp.getIniciales()!=null && !exp.getIniciales().isEmpty()){
				sql.append(first ? "WHERE ":"AND ");
				sql.append(" exp.iniciales = :iniciales");
				params.put("iniciales", exp.getIniciales());
				first = false;
			}
			if(exp.getRuta()!=null && !exp.getRuta().isEmpty()){
				sql.append(first ? "WHERE ":"AND ");
				sql.append(" exp.ruta = :ruta");
				params.put("ruta",exp.getRuta());
				first = false;
			}
			query = em.createQuery(sql.toString(), Long.class);
			System.out.println(sql.toString());
			for (String param : params.keySet()) {
			System.out.println("parametro "+param);
			System.out.println("param "+params.get(param));
			query.setParameter(param, params.get(param));
		}		
			return query.getSingleResult();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new ConectividadDaoException(e.getMessage(),e.getCause());
		}finally{
			em.close();
		}
	}

}
