package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.TramiteUsuarioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.TramiteUsuario;
import com.palestra.notaria.modelo.Usuario;

public class TramiteUsuarioDaoImpl extends GenericDaoImpl<TramiteUsuario, Integer> implements TramiteUsuarioDao {

	public TramiteUsuarioDaoImpl() {
		super(TramiteUsuario.class);
	}

	@Override
	public void delete(TramiteUsuario tu) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.createQuery("Delete from TramiteUsuario t where t.idtramiteusuario ='" + tu.getIdtramiteusuario() + "'")
					.executeUpdate();
			em.getTransaction().commit();

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error al eliminar, causado por: " + e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<Usuario> findUsuariosByTramite(String idtramite) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {

			TypedQuery<TramiteUsuario> query = em.createQuery(
					"Select t from TramiteUsuario t where t.tramite.idtramite= :idtramite", TramiteUsuario.class);
			query.setParameter("idtramite", idtramite);
			List<TramiteUsuario> lista = query.getResultList();

			for (TramiteUsuario usutra : lista) {
				Usuario usuario = usutra.getUsuario();
				if (usuario instanceof HibernateProxy) {
					usuario = (Usuario) ((HibernateProxy) usuario).getHibernateLazyInitializer().getImplementation();
					usuarios.add(usuario);
				}
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return usuarios;
	}

	@Override
	public ArrayList<Tramite> findByUsuario(String idusuario) throws NotariaException {

		ArrayList<Tramite> tramites = new ArrayList<Tramite>();
		EntityManager em = factory.createEntityManager();

		try {
			TypedQuery<TramiteUsuario> query = em.createQuery(
					"Select t from TramiteUsuario t where t.usuario.idusuario= :idusuario ORDER BY t.tmstmp DESC",
					TramiteUsuario.class);
			query.setParameter("idusuario", idusuario);
			query.setMaxResults(120);
			List<TramiteUsuario> lista = query.getResultList();

			for (TramiteUsuario trausu : lista) {
				Tramite tramite = trausu.getTramite();
				if (tramite instanceof HibernateProxy) {
					tramite = (Tramite) ((HibernateProxy) tramite).getHibernateLazyInitializer().getImplementation();
					tramites.add(tramite);
				}
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		// TODO Auto-generated method stub
		return tramites;
	}

	@Override
	public TramiteUsuario findByUsuarioTramite(String idUsuario, String idTramite) throws NotariaException {

		TramiteUsuario tuFound = new TramiteUsuario();
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<TramiteUsuario> query = em.createQuery(
					"Select t from TramiteUsuario t where t.usuario.idusuario= :idUsuario AND t.tramite.idtramite =:idTramite",
					TramiteUsuario.class);
			query.setParameter("idUsuario", idUsuario);
			query.setParameter("idTramite", idTramite);
			List<TramiteUsuario> lista = query.getResultList();
			if (lista.size() > 0) {
				tuFound = lista.get(0);
			} else {
				throw new NotariaException("No se encontro un tramite usuario con esos parametros");
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return tuFound;

	}

}
