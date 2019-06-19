package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.ControlFoliosDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ControlFolios;

public class ControlFoliosDaoImpl extends
		GenericDaoImpl<ControlFolios, Integer> implements ControlFoliosDao {

	public ControlFoliosDaoImpl(){
		super(ControlFolios.class);
	}
	
	@Override
	public ControlFolios getUltimoFolio() throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT cf FROM ControlFolios cf ORDER BY tmstmp DESC");
			TypedQuery<ControlFolios> query = em.createQuery(sql.toString(),ControlFolios.class);
			List<ControlFolios> resultados = query.getResultList();	
			if (resultados != null && resultados.size() > 0) {
				return resultados.get(0);
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		/** Si no se encontro nada retorna nullo **/
		return null;	
	}
	
	
	
}
