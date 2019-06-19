package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.NotariaDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Notaria;

public class NotariaDaoImpl extends GenericDaoImpl<Notaria, Integer> implements
		NotariaDao {

	public NotariaDaoImpl() {
		super(Notaria.class);
	}

	@Override
	public String obtenerNumNotariaByInicialesNotario(String inicialesNotario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT innumnot FROM Notaria ");
			sql.append(" WHERE usuario.dsiniciales = :inicialesNotario");

			Query query = em.createQuery(sql.toString());
			query.setParameter("inicialesNotario", inicialesNotario);

			@SuppressWarnings("unchecked")
			List<String> resultados = query.getResultList();
			if (resultados == null || resultados.isEmpty()) {
				return null;
			}
			String numNotaria = resultados.get(0);
			if (numNotaria == null) {
				return null;
			}
			return numNotaria;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

}
