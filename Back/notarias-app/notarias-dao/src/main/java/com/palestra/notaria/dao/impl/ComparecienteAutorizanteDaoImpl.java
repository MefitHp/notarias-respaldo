package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ComparecienteAutorizanteDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ComparecienteAutorizante;
import com.palestra.notaria.modelo.Persona;

public class ComparecienteAutorizanteDaoImpl extends
		GenericDaoImpl<ComparecienteAutorizante, Integer> implements
		ComparecienteAutorizanteDao {

	public ComparecienteAutorizanteDaoImpl() {
		super(ComparecienteAutorizante.class);
	}

	@Override
	public ComparecienteAutorizante findById(Object id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			return em.find(ComparecienteAutorizante.class, id);
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<ComparecienteAutorizante> findByCompareciente(
			String idcompareciente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em
					.createQuery("SELECT ca FROM ComparecienteAutorizante ca WHERE ca.compareciente.idcompareciente = ?1");
			query.setParameter(1, idcompareciente);

			@SuppressWarnings("unchecked")
			List<ComparecienteAutorizante> lista = query.getResultList();
			for (ComparecienteAutorizante ca : lista) {
				Persona autorizante = ca.getAutorizante().getPersona();
				if (autorizante instanceof HibernateProxy) {
					autorizante = (Persona) ((HibernateProxy) autorizante)
							.getHibernateLazyInitializer().getImplementation();
					autorizante.setNacionalidad(null);
					autorizante.setTipopersona(null);
					ca.getAutorizante().setPersona(autorizante);
				}
				
				
				autorizante.setNacionalidad(null);
				autorizante.setTipopersona(null);
				ca.getAutorizante().setPersona(autorizante);
			}

			return lista;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
}
