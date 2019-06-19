package com.palestra.notaria.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.FirmaDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Firma;

public class FirmaDaoImpl extends GenericDaoImpl<Firma, Integer> implements FirmaDao {

	public FirmaDaoImpl() {
		super(Firma.class);
	}

	@Override
	public void borrar(Firma firma) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			Firma tmpfirma = em.find(Firma.class, firma.getIdfirma());
			Compareciente tmpcompareciente = em.find(Compareciente.class,
					firma.getCompareciente().getIdcompareciente());
			tmpcompareciente.setFirma(null);
			em.merge(tmpcompareciente);
			em.remove(tmpfirma);
			tx.commit();
			return;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			try {
				tx.rollback();
			} catch (Exception e2) {
				throw new NotariaException(e.getCause());
			}

		} finally {
			em.close();
		}

	}

	@Override
	public Firma obtenerFirmaPorCompareciente(Compareciente compareciente) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		Firma firma = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT f FROM Firma f WHERE f.compareciente.idcompareciente = :idcompareciente");

			TypedQuery<Firma> query = em.createQuery(sql.toString(), Firma.class);
			query.setParameter("idcompareciente", compareciente.getIdcompareciente());
			if (query.getResultList().size() > 0) {
				firma = query.getResultList().get(0);
			}

			/*
			 * if (comparecienteList != null) { for (Compareciente c :
			 * comparecienteList) { this.hibernateLazyInitializer(c); } }
			 */
			return firma;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Firma save(Firma firma) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.persist(firma);
			Compareciente comp = em.find(Compareciente.class, firma.getCompareciente().getIdcompareciente());
			comp.setFirma(firma);
			em.merge(comp);

			tx.commit();
			return firma;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			try {
				tx.rollback();
			} catch (Exception e2) {
				throw new NotariaException(e.getCause());
			}

		} finally {
			em.close();
		}
		return firma;
	}

	/*
	 * EntityManager em = factory.createEntityManager();
	 * em.getTransaction().begin(); try{ em.persist(obj);
	 * em.getTransaction().commit(); em.refresh(obj); return obj; }
	 * catch(PersistenceException e){
	 * 
	 * e.printStackTrace(System.out); // throw new NotariaException(String.
	 * format("Something went wrong in persisting data , view server log for details [save %s at %s]."
	 * , obj.toString(), dateFormat.format(new Date())), e.getCause());
	 * 
	 * throw new
	 * NotariaException("Ocurrió un error al guardar, causado por: "+e.getCause(
	 * )); }finally{ em.close(); }
	 */

}
