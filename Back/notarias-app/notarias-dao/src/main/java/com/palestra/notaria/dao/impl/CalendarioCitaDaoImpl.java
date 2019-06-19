package com.palestra.notaria.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.CalendarioCitaDao;
import com.palestra.notaria.enums.EnumCitaEstatus;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.CalendarioCitaPK;
import com.palestra.notaria.modelo.DocumentoAnexo;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.InvitadoCalendarioCita;
import com.palestra.notaria.modelo.Usuario;

public class CalendarioCitaDaoImpl extends
		GenericDaoImpl<CalendarioCita, Integer> implements CalendarioCitaDao {

	private static final Boolean IS_REPROGRAMADA = true;

	public CalendarioCitaDaoImpl() {
		super(CalendarioCita.class);
	}

	@Override
	public CalendarioCita buscarCalendarioCitaCompleto(CalendarioCitaPK id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		CalendarioCita cc = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT cc FROM CalendarioCita cc ");
			sql.append(" WHERE cc.id.idcita =:idcita ");
			sql.append(" AND cc.id.version =:version ");
			/** filtrar siempre las citas pendientes **/
			sql.append(" AND  cc.dsestatus = :dsestatus ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("idcita", id.getIdcita());
			query.setParameter("version", id.getVersion());
			query.setParameter("dsestatus",
					EnumCitaEstatus.PENDIENTE.toString());
			@SuppressWarnings("unchecked")
			List<CalendarioCita> lista = query.getResultList();
			if (lista != null && lista.size() == 0) {
				return null;
			}
			/** sabemos que tendra una sola coindicencia. **/
			cc = lista.get(0);
			this.hibernateLazyInitializer(cc);
			return cc;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CalendarioCita> findCitas(CalendarioCita cc) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<CalendarioCita> lista;
		try {
			if (cc == null) {
				return null;
			}
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT cc FROM CalendarioCita cc ");
			Map<String, Object> params = new HashMap<String, Object>();
			boolean first = true;
			if (cc.getExpediente() != null
					&& cc.getExpediente().getIdexpediente() != null
					&& !cc.getExpediente().getIdexpediente().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append(" cc.expediente.idexpediente = :idexpediente ");
				params.put("idexpediente", cc.getExpediente().getIdexpediente());
				first = false;
			}
			if (cc.getFechainicio() != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(cc.getFechainicio());
				sql.append(first ? "WHERE " : "AND ");
				sql.append(" day(cc.fechainicio) = :date and month(cc.fechainicio) = :month and year(cc.fechainicio) = :year ");
				params.put("date", cal.get(Calendar.DATE));
				params.put("month", cal.get(Calendar.MONTH) + 1);
				params.put("year", cal.get(Calendar.YEAR));

				first = false;
			}
			/** ordenar solos las que estan en estatus PENDIENTE **/
			sql.append(" AND cc.dsestatus = '"+ EnumCitaEstatus.PENDIENTE.toString() + "'");
			/** ordernar **/
			sql.append(" ORDER BY cc.fechainicio, cc.tmstmp ");

			Query query = em.createQuery(sql.toString());
			for (String param : params.keySet()) {
				query.setParameter(param, params.get(param));
			}
			lista = query.getResultList();
			if (lista == null || lista.size() == 0) {
				return null;
			}
			for (CalendarioCita cal : lista) {
				this.hibernateLazyInitializer(cal);
			}
			return lista;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CalendarioCita> findCitasByInvitado(String nombre) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<CalendarioCita> lista;
		List<CalendarioCita> listaTemp;
		Query query = null;
		try {
			if (nombre == null || nombre.isEmpty()) {
				return null;
			}
			//TODO: 
			/** Buscar en compareciente **/
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ic.cita FROM InvitadoCalendarioCita  ic");
			sql.append(" WHERE CONCAT(ic.compareciente.persona.dsnombre,' ',ic.compareciente.persona.dsapellidopat,' ',ic.compareciente.persona.dsapellidomat) like '%"
					+ nombre + "%'");
			sql.append(" AND ic.cita.dsestatus = '"
					+ EnumCitaEstatus.PENDIENTE.toString() + "'");
			query = em.createQuery(sql.toString());
			
			lista = query.getResultList();

			/** Buscar en usuarios, por los join de HQL, se tuvo que separar el query en vez de usar 'OR' **/
			sql = new StringBuilder();
			sql.append(" SELECT ic.cita FROM InvitadoCalendarioCita  ic");
			sql.append(" WHERE CONCAT(ic.usuario.dsnombre,' ',ic.usuario.dspaterno,' ',ic.usuario.dsmaterno) like '%"
					+ nombre + "%'");
			sql.append(" AND ic.cita.dsestatus = '"
					+ EnumCitaEstatus.PENDIENTE.toString() + "'");
			query = em.createQuery(sql.toString());

			/** Eliminar citas repetidas. **/
			listaTemp = query.getResultList();
			for (CalendarioCita x : listaTemp) {
				if (!lista.contains(x)) {
					lista.add(x);
				}
			}
			if (lista == null || lista.size() == 0) {
				return null;
			}
			for (CalendarioCita cal : lista) {
				this.hibernateLazyInitializer(cal);
			}
			return lista;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@Override
	public Boolean registrarCita(CalendarioCita cc,
			List<InvitadoCalendarioCita> lsInvCita,
			List<DocumentoAnexo> lsDocAnexo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			// Registrar la cita
			em.persist(cc);
			if (lsInvCita != null) {
				// Guardar CalendarioCita
				for (InvitadoCalendarioCita icc : lsInvCita) {
					em.persist(icc);
				}
			}
			if (lsDocAnexo != null) {
				// Guardar DocumentoAnexo
				for (DocumentoAnexo da : lsDocAnexo) {
					em.persist(da);
				}
			}
			tx.commit();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	/**
	 * Se inizializan las propiedades lazy
	 * 
	 * @param cc
	 */
	private void hibernateLazyInitializer(CalendarioCita cc) {
		Usuario usuario = null;
		Expediente expediente = null;
		expediente = cc.getExpediente();
		if (expediente instanceof HibernateProxy) {
			expediente = (Expediente) ((HibernateProxy) expediente)
					.getHibernateLazyInitializer().getImplementation();
			//expediente.setListaComentarios(null);
			expediente.setTramite(null);
			expediente.setAbogado(null);
		}
		cc.setExpediente(expediente);
		usuario = cc.getUsragendo();
		if (usuario instanceof HibernateProxy) {
			usuario = (Usuario) ((HibernateProxy) usuario)
					.getHibernateLazyInitializer().getImplementation();
			usuario.setRol(null);
		}
		cc.setUsragendo(usuario);
	}

	@Override
	public Boolean cambiarEstatusCitaPorId(CalendarioCitaPK id, String status) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE CalendarioCita cc set cc.dsestatus=:dsestatus ");
			sql.append(" WHERE cc.id.idcita = :idcita AND cc.id.version = :version");
			query = em.createQuery(sql.toString());
			query.setParameter("idcita", id.getIdcita());
			query.setParameter("version", id.getVersion());
			query.setParameter("dsestatus",status);
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

	@Override
	public Boolean actualizarCita(CalendarioCita cc,
			List<InvitadoCalendarioCita> lsInvCita,
			List<DocumentoAnexo> lsDocAnexo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			/**
			 * Eliminar invitados y documentos, se agregarán nuevamente en
			 * actualización.
			 **/
			query = em
					.createQuery("delete InvitadoCalendarioCita where cita.id.idcita = :idcita and cita.id.version = :version");
			query.setParameter("idcita", cc.getId().getIdcita());
			query.setParameter("version", cc.getId().getVersion());
			query.executeUpdate();

			query = em
					.createQuery("delete DocumentoAnexo where cita.id.idcita = :idcita and cita.id.version = :version");
			query.setParameter("idcita", cc.getId().getIdcita());
			query.setParameter("version", cc.getId().getVersion());
			query.executeUpdate();
			/** actualizar la cita **/
			em.merge(cc);
			if (lsInvCita != null) {
				// Guardar nuevamente CalendarioCita
				for (InvitadoCalendarioCita icc : lsInvCita) {
					em.persist(icc);
				}
			}
			if (lsDocAnexo != null) {
				// Guardar nuevamente DocumentoAnexo
				for (DocumentoAnexo da : lsDocAnexo) {
					em.persist(da);
				}
			}
			tx.commit();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	/**
	 * Al reprogramar una cita, se crea una nueva cita cita con estado
	 * 'pendiente' y la cita anterior se modifica a estado 'cancelado'
	 * @throws NotariaException 
	 */
	@Override
	public Boolean reprogramarCita(CalendarioCita cc,
			List<InvitadoCalendarioCita> lsInvCita,
			List<DocumentoAnexo> lsDocAnexo, CalendarioCitaPK ccPK) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			/** Asignar en estado 'reprogramada' la cita vieja **/
			StringBuilder sql = new StringBuilder(); 
			sql.append("UPDATE CalendarioCita cc set cc.dsestatus=:dsestatus, cc.isreprogramdo = :isreprogramdo, cc.tmstmp = :tmstmp");
			sql.append(" WHERE cc.id.idcita = :idcita AND cc.id.version = :version");
			query = em.createQuery(sql.toString());
			query.setParameter("idcita", ccPK.getIdcita());
			query.setParameter("version", ccPK.getVersion());
			query.setParameter("dsestatus",
					EnumCitaEstatus.REPROGRAMADA.toString());
			query.setParameter("tmstmp", new Timestamp((new Date()).getTime()));
			query.setParameter("isreprogramdo", IS_REPROGRAMADA);
			query.executeUpdate();
			/** Crear una nueva version de cita con estado 'pendiente' **/
			em.persist(cc);
			if (lsInvCita != null) {
				// Guardar CalendarioCita
				for (InvitadoCalendarioCita icc : lsInvCita) {
					em.persist(icc);
				}
			}
			if (lsDocAnexo != null) {
				// Guardar DocumentoAnexo
				for (DocumentoAnexo da : lsDocAnexo) {
					em.persist(da);
				}
			}
			tx.commit();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Integer obtenerUltimaVersion(String idcita) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Integer version = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			version = em.createQuery(
					"select max(cc.id.version) from CalendarioCita cc where cc.id.idcita='"
							+ idcita + "'", Integer.class).getSingleResult();
			tx.commit();
			return version;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
}
