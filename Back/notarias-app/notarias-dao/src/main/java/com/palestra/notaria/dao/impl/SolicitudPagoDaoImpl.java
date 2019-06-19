package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.SolicitudPagoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.BitacoraGeneral;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.DatoFiscalPago;
import com.palestra.notaria.modelo.DatoPago;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.SolicitudPago;
import com.palestra.notaria.modelo.Usuario;

public class SolicitudPagoDaoImpl extends GenericDaoImpl<SolicitudPago, Integer> implements SolicitudPagoDao {

	public SolicitudPagoDaoImpl() {
		super(SolicitudPago.class);
	}

	@Override
	public SolicitudPago obtenerPorIdCompleto(String idsolicitudpago) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		SolicitudPago solicitud = null;

		try {
			solicitud = em.find(SolicitudPago.class, idsolicitudpago);
			if (solicitud != null) {
				this.hibernateInicialization(solicitud);
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return solicitud;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudPago> obtenerSolicitudesporExp(String idexpediente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<SolicitudPago> lista = null;
		try {
			query = em.createQuery(
					"from SolicitudPago where expediente.idexpediente = '" + idexpediente + "' order by tmstmp");

			lista = query.getResultList();
			if (lista != null) {
				for (SolicitudPago sp : lista) {
					this.hibernateInicialization(sp);
				}
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return lista;
	}

	/**
	 * Inicializar propiedades lazy
	 * 
	 * @param sp
	 */
	private void hibernateInicialization(SolicitudPago sp) {

		List<DatoFiscalPago> listaDatosFiscalPago;
		List<DatoPago> listaDatoPago;
		Usuario usuarioelaboro;
		ElementoCatalogo mediopago;
		ElementoCatalogo status;
		Compareciente compareciente;
		Persona persona;
		Usuario abogado;

		Expediente exp;
		exp = sp.getExpediente();
		if (exp instanceof HibernateProxy) {
			exp = (Expediente) ((HibernateProxy) exp).getHibernateLazyInitializer().getImplementation();
			abogado = exp.getAbogado();
			if (abogado instanceof HibernateProxy) {
				abogado = (Usuario) ((HibernateProxy) abogado).getHibernateLazyInitializer().getImplementation();
				abogado.setRol(null);
			}
			exp.setAbogado(abogado);
			exp.setTramite(null);
			// exp.setListaComentarios(null);
		}
		sp.setExpediente(exp);
		listaDatosFiscalPago = sp.getListaDatosFiscalPago();
		if (listaDatosFiscalPago != null && !listaDatosFiscalPago.isEmpty()) {
			for (DatoFiscalPago dfp : listaDatosFiscalPago) {
				dfp.setSolicitudPago(null);
				listaDatoPago = dfp.getListaDatoPago();
				dfp.setListaDatoPago(listaDatoPago);
				status = dfp.getStatus();
				if (status instanceof HibernateProxy) {
					status = (ElementoCatalogo) ((HibernateProxy) status).getHibernateLazyInitializer()
							.getImplementation();
					status.setCatalogo(null);
				}
				dfp.setStatus(status);
				compareciente = dfp.getCompareciente();
				if (compareciente instanceof HibernateProxy) {
					compareciente = (Compareciente) ((HibernateProxy) compareciente).getHibernateLazyInitializer()
							.getImplementation();
					compareciente.setActo(null);
					persona = compareciente.getPersona();
					if (persona instanceof HibernateProxy) {
						persona = (Persona) ((HibernateProxy) persona).getHibernateLazyInitializer()
								.getImplementation();
						persona.setTipopersona(null);
						persona.setNacionalidad(null);
					}
					compareciente.setPersona(persona);
					compareciente.setTipoCompareciente(null);
					compareciente.setRegistroRi(null);
				}
				dfp.setCompareciente(compareciente);

				if (listaDatoPago != null && !listaDatoPago.isEmpty()) {
					for (DatoPago dp : listaDatoPago) {
						dp.setDatofiscapago(null);
						usuarioelaboro = dp.getUsuarioelaboro();
						if (usuarioelaboro instanceof HibernateProxy) {
							usuarioelaboro = (Usuario) ((HibernateProxy) usuarioelaboro).getHibernateLazyInitializer()
									.getImplementation();
							usuarioelaboro.setRol(null);
						}
						dp.setUsuarioelaboro(usuarioelaboro);

						mediopago = dp.getMediopago();
						if (mediopago instanceof HibernateProxy) {
							mediopago = (ElementoCatalogo) ((HibernateProxy) mediopago).getHibernateLazyInitializer()
									.getImplementation();
							mediopago.setCatalogo(null);
						}
						dp.setMediopago(mediopago);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean eliminarSolicitud(SolicitudPago sp, String idusuario, String idexpediente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		boolean b = false;
		Query query;

		EntityTransaction tx = em.getTransaction();
		List<DatoFiscalPago> lista;
		List<DatoPago> listaDatoPago;
		BitacoraGeneral bg;
		try {

			tx.begin();

			query = em.createQuery("from DatoFiscalPago where solicitudPago.idsolpago = '" + sp.getIdsolpago() + "'");
			lista = query.getResultList();
			if (lista != null && !lista.isEmpty()) {

				for (DatoFiscalPago dfp : lista) {

					listaDatoPago = dfp.getListaDatoPago();
					if (listaDatoPago != null && !listaDatoPago.isEmpty()) {
						for (DatoPago dp : listaDatoPago) {
							em.remove(em.contains(dp) ? dp : em.merge(dp));
							bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "DatoPago", "Eliminar",
									"Eliminacion de DatoPago ");
							em.persist(bg);
						}
					}

					em.remove(em.contains(dfp) ? dfp : em.merge(dfp));
					bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "DatoFiscalPago", "Eliminar",
							"Eliminacion de DatoFiscalPago ");
					em.persist(bg);
				}
			}

			em.remove(em.contains(sp) ? sp : em.merge(sp));

			bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "SolicitudPago", "Eliminar",
					"Eliminacion de SolicitudPago ");
			em.persist(bg);

			tx.commit();
			b = true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return b;
	}
}
