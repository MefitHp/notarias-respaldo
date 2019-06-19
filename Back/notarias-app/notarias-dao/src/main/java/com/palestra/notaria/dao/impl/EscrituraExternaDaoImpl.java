package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.EscrituraExternaDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ControlFolios;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraExterna;

public class EscrituraExternaDaoImpl extends GenericDaoImpl<EscrituraExterna, Integer> implements EscrituraExternaDao {

	public EscrituraExternaDaoImpl() {
		super(EscrituraExterna.class);
	}

	@Override
	public EscrituraExterna guardarEscrituraExterna(EscrituraExterna escritura) throws NotariaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EscrituraExterna findByNumero(String numero) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EscrituraExterna escExt = null;

		try {

			TypedQuery<EscrituraExterna> query = em.createQuery(
					"Select ex from EscrituraExterna ex where ex.dsnumescritura= :numero", EscrituraExterna.class);
			query.setParameter("numero", numero);
			List<EscrituraExterna> lista = query.getResultList();
			if (lista.size() > 0) {
				escExt = lista.get(0);
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return escExt;
	}

	/*
	 * @Override public EscrituraExterna
	 * guardarEscrituraExterna(EscrituraExterna escritura) throws
	 * NotariaException { EntityManager em = factory.createEntityManager();
	 * EntityTransaction tx = em.getTransaction(); tx.begin(); try{
	 * em.persist(escritura); // actualizar el folio actual, y folios
	 * disponibles en control folios TypedQuery<ControlFolios> query =
	 * em.createQuery("SELECT c FROM ControlFolios c", ControlFolios.class);
	 * ControlFolios controlFolios = query.getSingleResult(); // el resultado de
	 * foliosRestar debe ser un valor negativo Long foliosRestar =
	 * controlFolios.getFolioActual()-escritura.getFoliofin(); // los nuevos
	 * folios disponibles deben ser >= 0 de lo contrario no alcanzaran para
	 * generar la escritura Long newFoliosDisponibles =
	 * controlFolios.getFoliosDisponibles()+foliosRestar;
	 * if(newFoliosDisponibles>=0)
	 * controlFolios.setFoliosDisponibles(newFoliosDisponibles); else throw new
	 * NotariaException("No existen folios disponibles suficientes para la escritura"
	 * ); controlFolios.setFolioActual(escritura.getFoliofin());
	 * em.merge(controlFolios); tx.commit(); em.refresh(escritura); return
	 * escritura; }catch(PersistenceException e){ e.printStackTrace(System.out);
	 * throw new NotariaException(e.getCause()); }finally{ em.close(); } }
	 */
}
