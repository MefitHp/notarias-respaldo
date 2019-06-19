package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.ImpuestoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Impuesto;

public class ImpuestoDaoImpl extends GenericDaoImpl<Impuesto, Integer> implements ImpuestoDao{

	public ImpuestoDaoImpl() {
		super(Impuesto.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Impuesto obtenerImpuestoById(String idimpuesto, String sigla) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Impuesto impuesto = null;
		try {
			if(idimpuesto!=null)
				impuesto = em.find(Impuesto.class, idimpuesto);
			else{
				Query query = em
						.createQuery("from Impuesto where dssiglas = '"
								+ sigla + "'");

				List<Impuesto> lista = query.getResultList();
				if (lista != null && lista.size() > 0)
					impuesto = lista.get(0);
				else
					impuesto = null;
			}
				
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		return impuesto;
	}

}
