package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.FiltroActoComparecienteDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FiltroActoCompareciente;
import com.palestra.notaria.modelo.Operacion;

public class FiltroActoComparecienteDaoImpl extends GenericDaoImpl<FiltroActoCompareciente, Integer> implements
FiltroActoComparecienteDao {

	public FiltroActoComparecienteDaoImpl() {
		super(FiltroActoCompareciente.class);
	}

	@Override
	public List<FiltroActoCompareciente> comparecientesXSubop(Operacion sop)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<FiltroActoCompareciente> respuesta = null;
		try {
			TypedQuery<FiltroActoCompareciente> query = em.createNamedQuery("FiltroActoCompareciente.filtrarXSubop", FiltroActoCompareciente.class);
			query.setParameter("identificador", sop.getIdoperacion());
			respuesta = query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return respuesta;
	}

	@Override
	public void borrar(FiltroActoCompareciente filtro)
			throws NotariaException {
		
		EntityManager em = factory.createEntityManager();
		
		try {
			FiltroActoCompareciente tmpFind = em.find(FiltroActoCompareciente.class, filtro.getIdfiltroActoCompareciente());
			em.getTransaction().begin();
			em.remove(tmpFind);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		
	}
}
