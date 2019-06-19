package com.palestra.notaria.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.TestimonioDao;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoTestimonio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.BitacoraGeneral;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EtapaTestimonio;
import com.palestra.notaria.modelo.RelEtapaTestimonio;
import com.palestra.notaria.modelo.Testimonio;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;

public class TestimonioDaoImpl extends GenericDaoImpl<Testimonio, Integer> implements TestimonioDao {

	public TestimonioDaoImpl() {
		super(Testimonio.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean registrarTestimonio(Testimonio test, String idexpediente, String idusuario, String idsesion)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Query query;
		List<EtapaTestimonio> lista = null;
		BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		BitacoraGeneral bg;
		// Testimonio t = null;
		boolean b = false;
		Date fecha = new Date();
		Timestamp tstmp = new Timestamp(fecha.getTime());
		RelEtapaTestimonio ret;
		try {
			tx.begin();
			query = em.createQuery("from EtapaTestimonio order by inorden");
			lista = query.getResultList();
			if (lista != null) {
				// exp =
				// lista.get(0).getTestimonio().getEscritura().getExpediente().getIdexpediente();
				test.setIdtestimonio(GeneradorId.generaId(test));
				test.setIdsesion(idsesion);
				test.setTmstmp(new Timestamp(fecha.getTime()));
				em.persist(test);
				// em.refresh(test);
				bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "Testimonio",
						Constantes.OPERACION_REGISTRO, "Registro de un testimonio ");
				em.persist(bg);
				for (EtapaTestimonio et : lista) {
					ret = new RelEtapaTestimonio();
					ret.setIdreletapatesti(GeneradorId.generaId(ret));
					ret.setIdsesion(idsesion);
					ret.setTmstmp(tstmp);
					ret.setEtapatestimonio(et);
					ret.setIsaprobada(false);
					ret.setTestimonio(test);
					em.persist(ret);
				}
				bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "RelEtapaTestimonio",
						Constantes.OPERACION_REGISTRO, "Registro de la lista de tareas del testimonio ");
				em.persist(bg);
			}
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Testimonio> obtenerTestimoniosPorExp(String idexpediente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<Testimonio> lista;
		// DatoTestimonio resp = new DatoTestimonio();
		try {
			Escritura esc = null;
			Usuario notario = null;
			query = em.createQuery("from Testimonio where escritura.expediente.idexpediente = '" + idexpediente + "'");
			lista = query.getResultList();
			if (lista != null) {
				for (Testimonio t : lista) {
					esc = t.getEscritura();
					if (esc instanceof HibernateProxy) {
						esc = (Escritura) ((HibernateProxy) esc).getHibernateLazyInitializer().getImplementation();
						esc.setExpediente(null);
						esc.setLibro(null);
						esc.setNotario(null);
					}
					t.setEscritura(esc);
					notario = t.getNotario();
					if (notario instanceof HibernateProxy) {
						notario = (Usuario) ((HibernateProxy) notario).getHibernateLazyInitializer()
								.getImplementation();
						notario.setRol(null);
					}
					t.setNotario(notario);
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

	@SuppressWarnings("unchecked")
	@Override
	public DatoTestimonio obtenerPorIdCompleto(String idtestimonio) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<RelEtapaTestimonio> lista;
		DatoTestimonio resp = new DatoTestimonio();
		try {

			Testimonio t = null;
			Escritura esc = null;
			Usuario notario = null;
			Usuario elaboro = null;
			t = em.find(Testimonio.class, idtestimonio);
			resp.setTestimonio(t);
			if (t != null) {
				esc = t.getEscritura();
				if (esc instanceof HibernateProxy) {
					esc = (Escritura) ((HibernateProxy) esc).getHibernateLazyInitializer().getImplementation();
					esc.setExpediente(null);
					esc.setLibro(null);
					esc.setNotario(null);
				}
				t.setEscritura(esc);
				notario = t.getNotario();
				if (notario instanceof HibernateProxy) {
					notario = (Usuario) ((HibernateProxy) notario).getHibernateLazyInitializer().getImplementation();
					notario.setRol(null);
				}
				t.setNotario(notario);

				elaboro = t.getUsuarioelaboro();
				if (elaboro instanceof HibernateProxy) {
					elaboro = (Usuario) ((HibernateProxy) elaboro).getHibernateLazyInitializer().getImplementation();
					elaboro.setRol(null);
				}
				t.setUsuarioelaboro(elaboro);
			}
			query = em.createQuery("from RelEtapaTestimonio where testimonio.idtestimonio = '" + idtestimonio
					+ "' order by etapatestimonio.inorden ");
			lista = query.getResultList();
			EtapaTestimonio et;
			if (lista != null && !lista.isEmpty()) {
				for (RelEtapaTestimonio ret : lista) {
					et = ret.getEtapatestimonio();
					if (et instanceof HibernateProxy) {
						et = (EtapaTestimonio) ((HibernateProxy) et).getHibernateLazyInitializer().getImplementation();
					}
					ret.setEtapatestimonio(et);
					ret.setTestimonio(null);
				}
				resp.setListaEtapas(new ArrayList<RelEtapaTestimonio>(lista));
			} else {
				resp.setListaEtapas(null);
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean elimiarTestimonio(String idtestimonio, String idusuario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Query query;
		List<RelEtapaTestimonio> lista = null;
		BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		BitacoraGeneral bg;
		Testimonio t = new Testimonio();
		t.setIdtestimonio(idtestimonio);
		boolean b = false;
		String exp = null;
		try {
			tx.begin();
			query = em.createQuery("from RelEtapaTestimonio where testimonio.idtestimonio = '" + idtestimonio + "'");
			lista = query.getResultList();
			if (lista != null) {
				exp = lista.get(0).getTestimonio().getEscritura().getExpediente().getIdexpediente();

				for (RelEtapaTestimonio ret : lista) {
					em.remove(em.contains(ret) ? ret : em.merge(ret));
				}
				bg = bitGenHelp.crearBitacora(idusuario, exp, null, "RelEtapaTestimonio",
						Constantes.OPERACION_ELIMINACION, "Eliminacion de la lista de tareas del testimonio ");
				em.persist(bg);
			}
			em.remove(em.contains(t) ? t : em.merge(t));
			bg = bitGenHelp.crearBitacora(idusuario, exp, null, "Testimonio", Constantes.OPERACION_ELIMINACION,
					"Eliminacion de un testimonio ");
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

	@Override
	public boolean aprobarEtapa(String idreletapatesti, String idusuario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean b = false;
		Query query = null;
		EntityTransaction tx = em.getTransaction();
		Date fecha = new Date();
		try {

			tx.begin();
			RelEtapaTestimonio ret = em.find(RelEtapaTestimonio.class, idreletapatesti);
			if (ret != null) {
				if (ret.getIsaprobada()) {
					query = em.createQuery(
							"update RelEtapaTestimonio set isaprobada = :isaprobada, fechaaprobada = :fechaaprobada where idreletapatesti = :id");
					query.setParameter("fechaaprobada", null);
					query.setParameter("isaprobada", false);
					query.setParameter("id", idreletapatesti);
				} else {
					query = em.createQuery(
							"update RelEtapaTestimonio set isaprobada = :isaprobada, fechaaprobada = :fechaaprobada where idreletapatesti = :id");
					query.setParameter("fechaaprobada", new Timestamp(fecha.getTime()), TemporalType.TIMESTAMP);
					query.setParameter("isaprobada", true);
					query.setParameter("id", idreletapatesti);
				}
			}
			int result = query.executeUpdate();

			tx.commit();
			if (result > 0)
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
