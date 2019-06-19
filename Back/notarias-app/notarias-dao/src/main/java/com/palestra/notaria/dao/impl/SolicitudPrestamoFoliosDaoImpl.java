package com.palestra.notaria.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.SolicitudPrestamoFoliosDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFolios;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;

public class SolicitudPrestamoFoliosDaoImpl extends GenericDaoImpl<SolicitudPrestamoFolios, Integer>
		implements SolicitudPrestamoFoliosDao {

	public SolicitudPrestamoFoliosDaoImpl() {
		super(SolicitudPrestamoFolios.class);
	}

	@Override
	public boolean validaEscituraPrestada(SolicitudPrestamoFolios sft) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean reg_validos = true;

		try {
			StringBuffer querybtw = new StringBuffer(
					"Select sol from SolicitudPrestamoFolios sol where sol.numeroEscritura =:numesc   AND sol.isPrestamoTerminado <> true ");
			TypedQuery<SolicitudPrestamoFolios> query = em.createQuery(querybtw.toString(),
					SolicitudPrestamoFolios.class);
			query.setParameter("numesc", sft.getNumeroEscritura());
			List<SolicitudPrestamoFolios> lista = query.getResultList();
			if (lista.size() > 0) {
				reg_validos = false;
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return reg_validos;
	}

	@Override
	public List<SolicitudPrestamoFolios> listarPrestados() throws NotariaException {

		return executeQuery("SELECT f FROM SolicitudPrestamoFolios f WHERE f.estanPrestados = true AND "
				+ "f.isPrestamoTerminado = false");
	}

	@Override
	public boolean validaRagoFolios(long inicio, long fin) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean reg_validos = true;

		try {
			StringBuffer querybtw = new StringBuffer(
					"Select sol from SolicitudPrestamoFolios sol where (:inicio BETWEEN sol.infolioinicio AND sol.infoliofin) OR (:fin BETWEEN sol.infolioinicio AND sol.infoliofin) AND sol.isPrestamoTerminado <> true ");
			TypedQuery<SolicitudPrestamoFolios> query = em.createQuery(querybtw.toString(),
					SolicitudPrestamoFolios.class);
			query.setParameter("inicio", inicio);
			query.setParameter("fin", fin);
			List<SolicitudPrestamoFolios> lista = query.getResultList();
			if (lista.size() > 0) {
				reg_validos = false;
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return reg_validos;
	}

	@Override
	public List<SolicitudPrestamoFolios> listarPrestadosPorAbogado(String idusuario) throws NotariaException {
		return executeQuery("SELECT f FROM SolicitudPrestamoFolios f WHERE f.usuarioRecibe.idusuario = ?1"
				+ " AND f.estanPrestados = true AND f.isPrestamoTerminado = false", idusuario);
	}

	@Override
	public List<SolicitudPrestamoFolios> listarSolicitadosPorAbogado(String idusuario) throws NotariaException {
		return executeQuery("SELECT f FROM SolicitudPrestamoFolios f WHERE f.usuarioRecibe.idusuario = ?1"
				+ " AND f.estanPrestados = false AND f.isPrestamoTerminado = false", idusuario);
	}

	@Override
	public List<SolicitudPrestamoFolios> listarSolicitudesNoAtendidas() throws NotariaException {
		return executeQuery("SELECT s FROM SolicitudPrestamoFolios s WHERE s.estanPrestados = false");
	}

	@Override
	public List<SolicitudPrestamoFolios> buscarPrestamosPorAbogado(Usuario abogado) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<SolicitudPrestamoFolios> query;
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT f FROM SolicitudPrestamoFolios f ");
			Map<String, Object> params = new HashMap<String, Object>();
			boolean first = true;
			if (abogado.getDsnombre() != null && !abogado.getDsnombre().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("f.usuarioRecibe.dsnombre LIKE :nombre ");
				params.put("nombre", "%" + abogado.getDsnombre() + "%");
				first = false;
			}
			if (abogado.getDspaterno() != null && !abogado.getDspaterno().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("f.usuarioRecibe.dspaterno LIKE :apaterno ");
				params.put("apaterno", "%" + abogado.getDspaterno() + "%");
				first = false;
			}
			if (abogado.getDsmaterno() != null && !abogado.getDsmaterno().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("f.usuarioRecibe.dsmaterno LIKE :amaterno ");
				params.put("amaterno", "%" + abogado.getDsmaterno() + "%");
				first = false;
			}
			sql.append(" AND f.estanPrestados = true AND f.isPrestamoTerminado = false");
			sql.append(" ORDER BY f.usuarioRecibe.dsnombre");
			query = em.createQuery(sql.toString(), SolicitudPrestamoFolios.class);
			System.out.println("SQL *********** " + query.toString());
			for (String param : params.keySet()) {
				System.out.println("parametro " + param);
				System.out.println("param " + params.get(param));
				query.setParameter(param, params.get(param));
			}
			return query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public void generaTransacion(SolicitudPrestamoFolios solicitudPrestamo, BitacoraFolios bitacora)
			throws NotariaException {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		try {

			em.merge(solicitudPrestamo);
			em.persist(bitacora);
			em.getTransaction().commit();
		} catch (PersistenceException e) {

			e.printStackTrace(System.out);
			// throw new NotariaException(String.format("Something went wrong in
			// persisting data , view server log for details [save %s at %s].",
			// obj.toString(), dateFormat.format(new Date())), e.getCause());

			throw new NotariaException("Ocurrió un error al guardar el folio, causado por: " + e.getCause());
		} finally {
			em.close();
		}

	}

	@Override
	public void generaDevolucion(SolicitudPrestamoFolios solicitudPrestamo, BitacoraFolios bitacora)
			throws NotariaException {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		try {
			solicitudPrestamo = em.merge(solicitudPrestamo);
			em.remove(solicitudPrestamo);
			em.persist(bitacora);
			em.getTransaction().commit();
		} catch (PersistenceException e) {

			e.printStackTrace(System.out);
			// throw new NotariaException(String.format("Something went wrong in
			// persisting data , view server log for details [save %s at %s].",
			// obj.toString(), dateFormat.format(new Date())), e.getCause());

			throw new NotariaException("Ocurrió un error al devolver el folio");
		} finally {
			em.close();
		}

	}

	@Override
	public void borrar(SolicitudPrestamoFolios solicitudPrestamo) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		try {
			solicitudPrestamo = em.merge(solicitudPrestamo);
			em.remove(solicitudPrestamo);
			em.getTransaction().commit();
		} catch (PersistenceException e) {

			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error al eliminar el folio");
		} finally {
			em.close();
		}

	}

}
