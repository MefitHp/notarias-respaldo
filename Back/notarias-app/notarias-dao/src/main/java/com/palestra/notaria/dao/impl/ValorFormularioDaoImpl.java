package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ValorFormularioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.ValorFormulario;

public class ValorFormularioDaoImpl extends GenericDaoImpl<ValorFormulario, Integer> implements ValorFormularioDao {

	public ValorFormularioDaoImpl() {
		super(ValorFormulario.class);
	}

	@Override
	public List<ValorFormulario> findByIdForm(String idForm) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<ValorFormulario> query;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT vf FROM ValorFormulario vf ");
			sb.append(" WHERE vf.formulario.idformulario=:idform");

			query = em.createQuery(sb.toString(), ValorFormulario.class);
			query.setParameter("idform", idForm);

			List<ValorFormulario> lista = query.getResultList();
			if (lista.size() == 0) {
				return null;
			} else {
				Componente componente = null;
				for (ValorFormulario vf : lista) {
					componente = vf.getComponente();
					if (componente instanceof HibernateProxy) {
						componente = (Componente) ((HibernateProxy) componente).getHibernateLazyInitializer()
								.getImplementation();
						componente.setConFormulario(null);
						componente.setSubformulario(null);
						componente.setTipocomponente(null);
					}
					vf.setComponente(componente);
				}
				return lista;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public ValorFormulario getByIdComponente(Formulario formulario, String nombreVariable) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<ValorFormulario> query = em.createNamedQuery("ValorFormulario.findByIdComponente",
					ValorFormulario.class);
			query.setParameter("formulario", formulario);
			query.setParameter("nombreVariable", nombreVariable);
			List<ValorFormulario> valores = query.getResultList();
			if (valores.size() > 0) {
				ValorFormulario valor = valores.get(0);
				Componente componente = valor.getComponente();
				if (componente instanceof HibernateProxy) {
					componente = (Componente) ((HibernateProxy) componente).getHibernateLazyInitializer()
							.getImplementation();
					componente.setConFormulario(null);
					componente.setSubformulario(null);
					componente.setTipocomponente(null);
				}
				valor.setComponente(componente);
				return valor;
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public ValorFormulario getByIdComponenteActo(String idacto, String nombreVariable) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<ValorFormulario> query = em.createNamedQuery("ValorFormulario.findByIdComponenteActo",
					ValorFormulario.class);
			query.setParameter("idacto", idacto);
			query.setParameter("nombreVariable", nombreVariable);
			List<ValorFormulario> valores = query.getResultList();
			if (valores.size() > 0) {
				ValorFormulario valor = valores.get(0);
				Componente componente = valor.getComponente();
				if (componente instanceof HibernateProxy) {
					componente = (Componente) ((HibernateProxy) componente).getHibernateLazyInitializer()
							.getImplementation();
					componente.setConFormulario(null);
					componente.setSubformulario(null);
					componente.setTipocomponente(null);
				}
				valor.setComponente(componente);
				return valor;
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	/**
	 * Metodo para encontrar un objeto de ValorFormulario solamente por id de
	 * Componente, se creó para validar la eliminación de Formulario Dinámico,
	 * por lo que no se toma en cuenta la inicialización de propiedades lazy.
	 * 
	 * @param componente
	 * @return
	 * @throws NotariaException
	 */
	@Override
	public List<ValorFormulario> getOnlyByComponente(Componente componente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<ValorFormulario> query = em.createQuery(
					"SELECT vf FROM ValorFormulario vf WHERE vf.componente.idcomponente = ?1", ValorFormulario.class);
			query.setParameter(1, componente.getIdcomponente());
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}

	@Override
	public void eliminaValorFormulario(Componente componente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<ValorFormulario> query = em.createQuery(
					"SELECT vf FROM ValorFormulario vf WHERE vf.componente.idcomponente = ?1", ValorFormulario.class);
			query.setParameter(1, componente.getIdcomponente());
			List<ValorFormulario> valores = query.getResultList();
			if (valores.size() > 0) {
				em.getTransaction().begin();
				for (ValorFormulario valor : valores) {
					String sql = "DELETE FROM ValorFormulario vf WHERE vf.componente.idcomponente = ?1";
					Query queryDelete = em.createQuery(sql);
					queryDelete.setParameter(1, valor.getComponente().getIdcomponente());
					queryDelete.executeUpdate();
				}
				em.getTransaction().commit();
			}
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}
}
