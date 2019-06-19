package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.palestra.notaria.dao.DocumentoExpedienteDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoExpediente;

public class DocumentoExpedienteDaoImpl extends
		GenericDaoImpl<DocumentoExpediente, Integer> implements
		DocumentoExpedienteDao {

	static Logger logger = Logger.getLogger(DocumentoExpediente.class);
	
	public DocumentoExpedienteDaoImpl() {
		super(DocumentoExpediente.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoExpediente> findByExpedienteId(String expedienteId) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery(
							"SELECT de FROM DocumentoExpediente de WHERE de.expediente.idexpediente = :idexpediente");
			query.setParameter("idexpediente", expedienteId);
	
			return query.getResultList();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoExpediente> findByActoId(String idActo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery(
							"SELECT de FROM DocumentoExpediente de WHERE de.acto.idacto = :idacto");
			query.setParameter("idacto", idActo);
			return query.getResultList();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
}
