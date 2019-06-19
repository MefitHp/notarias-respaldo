package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.RegistroRiDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.RegistroRi;

public class RegistroRiDaoImpl extends GenericDaoImpl<RegistroRi, Integer>
		implements RegistroRiDao {

	public RegistroRiDaoImpl() {
		super(RegistroRi.class);
	}

	@Override
	public RegistroRi findFromComparecienteId(String id) throws NotariaException {
		if (id == null || id.isEmpty()) {
			return null;
		}
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("SELECT c.registroRi FROM Compareciente c WHERE c.idcompareciente=:id");

			Query query = em.createQuery(hql.toString());
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<RegistroRi> lista = (List<RegistroRi>) query.getResultList();
			if (lista.size() == 0) {
				return null;
			}
			/**
			 * Tomar elemento de la lista, sabemos que solo deberia ser 1
			 * resultado.
			 **/
			RegistroRi ri = lista.get(0);
			/** Inicializar propiedades de tipo lazy **/
			ElementoCatalogo expedidopor = null;
			ElementoCatalogo tipo = null;
			if (ri != null) {
				expedidopor = ri.getExpedidopor();
				if (expedidopor instanceof HibernateProxy) {
					expedidopor = (ElementoCatalogo) ((HibernateProxy) expedidopor)
							.getHibernateLazyInitializer().getImplementation();
					expedidopor.setCatalogo(null);
				}
				ri.setExpedidopor(expedidopor);
				tipo = ri.getTipo();
				if (tipo instanceof HibernateProxy) {
					tipo = (ElementoCatalogo) ((HibernateProxy) tipo)
							.getHibernateLazyInitializer().getImplementation();
					tipo.setCatalogo(null);
				}
				ri.setTipo(tipo);
			}
			return ri;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String buscarArchivoPorId(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em
					.createQuery("select dsruta from RegistroRi where idregri =:id");
			query.setParameter("id", id);

			List<String> lista = query.getResultList();
			if (lista == null || lista.isEmpty()) {
				return null;
			}
			return lista.get(0);

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public boolean actualizarRutaArchivoRi(String idRegistroRi, String ruta) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			query = em
					.createQuery("update RegistroRi set dsruta = :ruta where idregri = :idregri");
			query.setParameter("ruta", ruta);
			query.setParameter("idregri", idRegistroRi);

			int result = query.executeUpdate();
			tx.commit();
			if (result > 0) {
				return true;
			}
			return false;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public boolean registrarRi(RegistroRi registroRi, String idCompareciente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			// Guardar registroRo
			em.persist(registroRi);
			tx.commit();
			
			//Se inicio otra tx por que lanzaba error al hacer update despues de persist
			//TODO: revisar por que pasa esto.
			tx.begin();
			query = em
					.createQuery("update Compareciente set registroRi.idregri = :idregri where idcompareciente = :idcompareciente");
			 query.setParameter("idregri", registroRi.getIdregri());
			 query.setParameter("idcompareciente", idCompareciente );
			query.executeUpdate();
			tx.commit();
			
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

}
