package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.BitacoraVersionesEscrituraDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraVersionesEscritura;

public class BitacoraVersionesEscrituraDaoImpl extends
		GenericDaoImpl<BitacoraVersionesEscritura, Integer> implements
		BitacoraVersionesEscrituraDao {

	public BitacoraVersionesEscrituraDaoImpl() {
		super(BitacoraVersionesEscritura.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BitacoraVersionesEscritura> findByEscrituraId(String idEscritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery(
							"SELECT bve FROM BitacoraVersionesEscritura bve WHERE bve.escritura.idescritura =:idEscritura");
			query.setParameter("idEscritura", idEscritura);
	
			return query.getResultList();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}

	}

	@Override
	public int deleteByEscrituraId(String idEscritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		int deleted = 0;

		try {
			Query query = em.createQuery(
							"DELETE FROM BitacoraVersionesEscritura bve WHERE bve.escritura.idescritura =:idEscritura");
			deleted = query.setParameter("idEscritura", idEscritura)
					.executeUpdate();
			tx.commit();
			return deleted;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
}
