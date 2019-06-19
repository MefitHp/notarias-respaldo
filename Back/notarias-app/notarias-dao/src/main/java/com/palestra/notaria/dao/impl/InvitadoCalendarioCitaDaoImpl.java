package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.InvitadoCalendarioCitaDao;
import com.palestra.notaria.dato.DatoInvitadoCita;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.InvitadoCalendarioCita;

public class InvitadoCalendarioCitaDaoImpl extends
		GenericDaoImpl<InvitadoCalendarioCita, Integer> implements
		InvitadoCalendarioCitaDao {

	private static final Boolean IS_ACTIVO = true;

	public InvitadoCalendarioCitaDaoImpl() {
		super(InvitadoCalendarioCita.class);
	}

	@Override
	public List<DatoInvitadoCita> findInvitadosAgendados(CalendarioCita cc) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<DatoInvitadoCita> invitados = new ArrayList<DatoInvitadoCita>();
		try {

			if (cc == null || cc.getId()==null || cc.getId().getIdcita() == null
					|| cc.getId().getIdcita().isEmpty()) {
				return null;
			}
			/** obtener invitados de tipo usuario **/
			this.usuariosAgendados(cc, invitados, em);
			/** obtener invitados de tipo compareciente **/
			this.comparecientesAgendados(cc, invitados, em);
			/** Si no se encontraron invitados **/
			if (invitados == null || invitados.size() == 0) {
				return null;
			}
			return invitados;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<DatoInvitadoCita> findInvitadosDisponibles(String idExpediente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<DatoInvitadoCita> invitados = new ArrayList<DatoInvitadoCita>();
		try {
			if (idExpediente == null || idExpediente.isEmpty()) {
				return null;
			}
			/** obtener invitados de tipo usuario **/
			this.usuariosDisponibles(invitados, em);
			/** obtener invitados de tipo compareciente **/
			this.comparecientesDisponibles(idExpediente, invitados, em);
			/** Si no se encontraron invitados **/
			if (invitados == null || invitados.size() == 0) {
				return null;
			}
			return invitados;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

	}

	/**
	 * Obtiene la lista de invitados de tipo usuarios.
	 * 
	 * @param cc
	 * @param lista
	 */
	private void usuariosAgendados(CalendarioCita cc,
			List<DatoInvitadoCita> invitados, EntityManager em)
			throws NotariaException {

		/** Obtener usuarios que seran invitados **/
		StringBuilder sqlusr = new StringBuilder();
		sqlusr.append(" SELECT icc.usuario.idusuario, icc.usuario.dsnombre, icc.usuario.dspaterno");
		sqlusr.append(" , icc.usuario.dsmaterno, icc.usuario.dscorreo ");
		sqlusr.append(" FROM InvitadoCalendarioCita icc ");
		sqlusr.append(" WHERE icc.cita.id.idcita=:idcita ");
		sqlusr.append(" AND icc.cita.id.version=:version");

		Query query = em.createQuery(sqlusr.toString());
		query.setParameter("idcita", cc.getId().getIdcita());
		query.setParameter("version", cc.getId().getVersion());
		@SuppressWarnings("unchecked")
		List<Object[]> listaUsr = query.getResultList();

		this.transformaUsuario(listaUsr, invitados, true);

	}

	/***
	 * Obtiene los invitados de tipo comparecientes de la cita o el expediente.
	 * 
	 * @param cc
	 * @param invitados
	 * @param em
	 * @throws Exception
	 */
	private void comparecientesAgendados(CalendarioCita cc,
			List<DatoInvitadoCita> invitados, EntityManager em)
			throws NotariaException {

		/** Obtener compareciente que seran invitados **/
		StringBuilder sqlcomp = new StringBuilder();
		sqlcomp.append(" SELECT icc.compareciente.idcompareciente, icc.compareciente.persona.dsnombre ");
		sqlcomp.append(" , icc.compareciente.persona.dsapellidopat, icc.compareciente.persona.dsapellidomat");
		sqlcomp.append(" , icc.compareciente.persona.dscorreoelectronico ");
		sqlcomp.append(" FROM InvitadoCalendarioCita icc ");
		sqlcomp.append(" WHERE icc.cita.id.idcita=:idcita ");
		sqlcomp.append(" AND icc.cita.id.version=:version ");

		Query query = em.createQuery(sqlcomp.toString());
		query.setParameter("idcita", cc.getId().getIdcita());
		query.setParameter("version", cc.getId().getVersion());

		@SuppressWarnings("unchecked")
		List<Object[]> listaComp = query.getResultList();

		this.transformaUsuario(listaComp, invitados, false);

	}

	private void usuariosDisponibles(List<DatoInvitadoCita> invitados,
			EntityManager em) throws NotariaException {

		/** Obtener usuarios que seran invitados **/
		StringBuilder sqlusr = new StringBuilder();
		sqlusr.append(" SELECT usr.idusuario, usr.dsnombre, usr.dspaterno");
		sqlusr.append(" , usr.dsmaterno, usr.dscorreo ");
		sqlusr.append(" FROM Usuario usr");
		sqlusr.append(" WHERE usr.isactivo=:isactivo");

		Query query = em.createQuery(sqlusr.toString());
		query.setParameter("isactivo", IS_ACTIVO);
		@SuppressWarnings("unchecked")
		List<Object[]> listaUsr = query.getResultList();
		this.transformaUsuario(listaUsr, invitados, true);

	}

	private void comparecientesDisponibles(String idExpediente,
			List<DatoInvitadoCita> invitados, EntityManager em)
			throws NotariaException {

		/** Obtener compareciente que seran invitados **/
		StringBuilder sqlcomp = new StringBuilder();
		sqlcomp.append(" SELECT c.idcompareciente, c.persona.dsnombre ");
		sqlcomp.append(" , c.persona.dsapellidopat, c.persona.dsapellidomat");
		sqlcomp.append(" , c.persona.dscorreoelectronico ");
		sqlcomp.append(" FROM Compareciente c ");
		sqlcomp.append(" WHERE c.acto.expediente.idexpediente = :idexpediente ");

		Query query = em.createQuery(sqlcomp.toString());
		query.setParameter("idexpediente", idExpediente);
		@SuppressWarnings("unchecked")
		List<Object[]> listaComp = query.getResultList();

		this.transformaUsuario(listaComp, invitados, false);

	}

	/**
	 * Transforma los resultados del query para obtener datoInvitadoCita
	 * 
	 * @param resultados
	 * @param invitados
	 */
	private void transformaUsuario(List<Object[]> resultados,
			List<DatoInvitadoCita> invitados, boolean isUsuario) {

		if (resultados != null) {
			DatoInvitadoCita invitado = null;
			for (Object[] res : resultados) {
				if (res != null) {
					invitado = new DatoInvitadoCita();
					invitado.setId((String) res[0]);
					invitado.setDsnombre((String) res[1]);
					invitado.setDspaterno((String) res[2]);
					invitado.setDsmaterno((String) res[3]);
					invitado.setDscorreoelectronico((String) res[4]);
					if (isUsuario) {
						invitado.setTipo("usuario");
					} else {
						invitado.setTipo("compareciente");
					}

					invitados.add(invitado);
				}
			}
		}
	}
}
