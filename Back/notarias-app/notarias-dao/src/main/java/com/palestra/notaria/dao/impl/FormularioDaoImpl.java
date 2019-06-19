package com.palestra.notaria.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.FormularioDao;
import com.palestra.notaria.dao.ValorSubFormularioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.ValorFormulario;
import com.palestra.notaria.modelo.ValorSubFormulario;
import com.palestra.notaria.util.GeneradorId;

public class FormularioDaoImpl extends GenericDaoImpl<Formulario, Integer> implements FormularioDao {

	public FormularioDaoImpl() {
		super(Formulario.class);
	}

	@Override
	public List<Formulario> findByActoId(String id) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Formulario> query = em
					.createQuery("SELECT frm FROM Formulario frm WHERE frm.acto.idacto=:idacto", Formulario.class);
			query.setParameter("idacto", id);

			List<Formulario> lista = query.getResultList();
			if (lista.size() == 0) {
				return null;
			} else {
				for (Formulario formulario : lista) {
					ConFormulario formularioDinamico = formulario.getConFormulario();
					if (formularioDinamico instanceof HibernateProxy) {
						formularioDinamico = (ConFormulario) ((HibernateProxy) formularioDinamico)
								.getHibernateLazyInitializer().getImplementation();
						formulario.setConFormulario(formularioDinamico);
					}
				}
			}
			return lista;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<Formulario> buscarFormulariosPorActo(String idacto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Formulario> lista;
		try {
			TypedQuery<Formulario> query = em.createQuery(
					"FROM Formulario WHERE acto.idacto=:idacto ORDER BY conFormulario.posiciontarjeton",
					Formulario.class);
			query.setParameter("idacto", idacto);

			lista = query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				Componente componente;
				Acto acto;
				ConFormulario conformulario;
				List<ValorFormulario> listaValFormulario;
				List<ValorSubFormulario> listaValSubFormulario;
				for (Formulario f : lista) {
					listaValFormulario = f.getListaValFormulario();
					if (listaValFormulario != null && !listaValFormulario.isEmpty()) {
						for (ValorFormulario vf : listaValFormulario) {
							vf.setFormulario(null);
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
					}
					f.setListaValFormulario(listaValFormulario);

					listaValSubFormulario = f.getListaValSubFormulario();
					if (listaValSubFormulario != null && !listaValSubFormulario.isEmpty()) {
						for (ValorSubFormulario vsf : listaValSubFormulario) {
							vsf.setFormulario(null);
							componente = vsf.getComponente();
							if (componente instanceof HibernateProxy) {
								componente = (Componente) ((HibernateProxy) componente).getHibernateLazyInitializer()
										.getImplementation();
								componente.setConFormulario(null);
								componente.setSubformulario(null);
								componente.setTipocomponente(null);
							}
							vsf.setComponente(componente);
						}
					}
					f.setListaValSubFormulario(listaValSubFormulario);
					acto = f.getActo();
					if (acto instanceof HibernateProxy) {
						acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
						acto.setExpediente(null);
						acto.setSuboperacion(null);
					}
					f.setActo(acto);
					conformulario = f.getConFormulario();
					if (conformulario instanceof HibernateProxy) {
						conformulario = (ConFormulario) ((HibernateProxy) conformulario).getHibernateLazyInitializer()
								.getImplementation();
						conformulario.setListaActoFormularios(null);
						conformulario.setListaComponentes(null);
						conformulario.setListaPermisosRol(null);
						conformulario.setListaSubFormularios(null);
					}
					f.setConFormulario(conformulario);
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

	@Override
	public Formulario buscarFormulariosPorActo(String idacto, ConFormularioPK idConFormulario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Formulario> lista;
		try {
			TypedQuery<Formulario> query = em.createQuery(
					"FROM Formulario WHERE acto.idacto=:idacto AND conFormulario.id = :idConFormulario",
					Formulario.class);
			query.setParameter("idacto", idacto);
			query.setParameter("idConFormulario", idConFormulario);

			lista = query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				Componente componente;
				Acto acto;
				ConFormulario conformulario;
				List<ValorFormulario> listaValFormulario;
				List<ValorSubFormulario> listaValSubFormulario;
				for (Formulario f : lista) {
					listaValFormulario = f.getListaValFormulario();
					if (listaValFormulario != null && !listaValFormulario.isEmpty()) {
						for (ValorFormulario vf : listaValFormulario) {
							vf.setFormulario(null);
							componente = vf.getComponente();
							if (componente instanceof HibernateProxy) {
								componente = (Componente) ((HibernateProxy) componente).getHibernateLazyInitializer()
										.getImplementation();
								componente.setConFormulario(null);
								componente.setSubformulario(null);
								componente.setTipocomponente(null);
								System.out.println("======> tablaBusqueda:::" + componente.getDstablabusqueda());
							}
							vf.setComponente(componente);
						}
					}
					f.setListaValFormulario(listaValFormulario);

					// listaValSubFormulario = f.getListaValSubFormulario();
					ValorSubFormularioDao daoSubForm = new ValorSubFormularioDaoImpl();
					listaValSubFormulario = daoSubForm.findByIdForm(f.getIdformulario());
					if (listaValSubFormulario != null && !listaValSubFormulario.isEmpty()) {
						for (ValorSubFormulario vsf : listaValSubFormulario) {
							System.out.printf("******* componentePosicion: %d - Registro %d %n",
									vsf.getComponente().getInposicion(), vsf.getRegistro());
							vsf.setFormulario(null);
							componente = vsf.getComponente();
							if (componente instanceof HibernateProxy) {
								componente = (Componente) ((HibernateProxy) componente).getHibernateLazyInitializer()
										.getImplementation();
								componente.setConFormulario(null);
								componente.setSubformulario(null);
								componente.setTipocomponente(null);
								System.out.println("======> tablaBusqueda:::" + componente.getDstablabusqueda());
							}
							vsf.setComponente(componente);
						}
					}
					f.setListaValSubFormulario(listaValSubFormulario);
					acto = f.getActo();
					if (acto instanceof HibernateProxy) {
						acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
						acto.setExpediente(null);
						acto.setSuboperacion(null);
					}
					f.setActo(acto);
					conformulario = f.getConFormulario();
					if (conformulario instanceof HibernateProxy) {
						conformulario = (ConFormulario) ((HibernateProxy) conformulario).getHibernateLazyInitializer()
								.getImplementation();
						conformulario.setListaActoFormularios(null);
						conformulario.setListaComponentes(null);
						conformulario.setListaPermisosRol(null);
						conformulario.setListaSubFormularios(null);
					}
					f.setConFormulario(conformulario);
				}

			}
			if (lista != null) {
				return lista.size() > 0 ? lista.get(0) : null;
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}

	@Override
	public boolean guardarValoresFormulario(Formulario formulario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		// BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		// Query query;
		// BitacoraGeneral bg;
		boolean b = false;

		EntityTransaction tx = em.getTransaction();

		Date fecha = new Date();
		try {
			List<ValorFormulario> listaValFormulario = formulario.getListaValFormulario();
			List<ValorSubFormulario> listaValSubFormulario = formulario.getListaValSubFormulario();
			if (listaValFormulario != null && !listaValFormulario.isEmpty()) {
				for (ValorFormulario vf : listaValFormulario) {
					vf.setFormulario(formulario);
					vf.setIdvalorform(GeneradorId.generaId(vf));
					vf.setTmstmp(new Timestamp(fecha.getTime()));
					vf.setIdsesion(formulario.getIdsesion());
				}
			}

			if (listaValSubFormulario != null && !listaValSubFormulario.isEmpty()) {
				for (ValorSubFormulario vsf : listaValSubFormulario) {
					vsf.setFormulario(formulario);
					vsf.setIdvalorsubform(GeneradorId.generaId(vsf));
					vsf.setTmstmp(new Timestamp(fecha.getTime()));
					vsf.setIdsesion(formulario.getIdsesion());
				}
			}
			tx.begin();

			em.persist(formulario);

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
	public boolean actualizarValoresFormulario(Formulario formulario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		// BitacoraGeneralHelper bitGenHelps = new BitacoraGeneralHelper();
		boolean b = false;
		Query query;

		EntityTransaction tx = em.getTransaction();
		Date fecha = new Date();
		List<ValorFormulario> listaValFormulario;
		List<ValorSubFormulario> listaValSubFormulario;
		try {
			tx.begin();
			query = em.createQuery("FROM ValorFormulario WHERE formulario.idformulario = :idform");
			query.setParameter("idform", formulario.getIdformulario());
			listaValFormulario = query.getResultList();
			if (listaValFormulario != null && !listaValFormulario.isEmpty()) {
				for (ValorFormulario vf : listaValFormulario)
					em.remove(em.contains(vf) ? vf : em.merge(vf));
			}

			query = em.createQuery("FROM ValorSubFormulario WHERE formulario.idformulario = :idform");
			query.setParameter("idform", formulario.getIdformulario());
			listaValSubFormulario = query.getResultList();
			if (listaValSubFormulario != null && !listaValSubFormulario.isEmpty()) {
				for (ValorSubFormulario vsf : listaValSubFormulario)
					em.remove(em.contains(vsf) ? vsf : em.merge(vsf));
			}

			listaValFormulario = formulario.getListaValFormulario();
			listaValSubFormulario = formulario.getListaValSubFormulario();
			if (listaValFormulario != null && !listaValFormulario.isEmpty()) {
				for (ValorFormulario vf : listaValFormulario) {
					vf.setFormulario(formulario);
					vf.setIdvalorform(GeneradorId.generaId(vf));
					vf.setTmstmp(new Timestamp(fecha.getTime()));
					vf.setIdsesion(formulario.getIdsesion());
				}
			}

			if (listaValSubFormulario != null && !listaValSubFormulario.isEmpty()) {
				for (ValorSubFormulario vsf : listaValSubFormulario) {
					vsf.setFormulario(formulario);
					vsf.setIdvalorsubform(GeneradorId.generaId(vsf));
					vsf.setTmstmp(new Timestamp(fecha.getTime()));
					vsf.setIdsesion(formulario.getIdsesion());
				}
			}
			em.merge(formulario);
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
	public List<Formulario> findByConFormulario(ConFormularioPK pk) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Formulario> sentence = em.createNamedQuery("Formulario.findByConFormulario", Formulario.class);
			sentence.setParameter("identificador", pk.getIdconFormulario());
			sentence.setParameter("version", pk.getVersion());
			List<Formulario> formularios = sentence.getResultList();
			if (!formularios.isEmpty()) {
				for (Formulario formulario : formularios) {
					Acto acto = formulario.getActo();
					if (acto instanceof HibernateProxy) {
						acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
						Expediente expediente = acto.getExpediente();
						if (expediente instanceof HibernateProxy) {
							expediente = (Expediente) ((HibernateProxy) expediente).getHibernateLazyInitializer()
									.getImplementation();
							acto.setExpediente(expediente);
						}
						formulario.setActo(acto);
					}
				}
				return formularios;
			} else {
				return new ArrayList<Formulario>();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new NotariaException(
					"No se logró consultar el formulario a través del conformulario. NullPointerException.",
					e.getCause());
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new NotariaException(
					"PersistenceException, no se logró consultar el formulario a través del conformulario.",
					e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Formulario buscarFormulariosPorActoYnombrecorto(String idacto, String nombrecorto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Formulario respuesta;
		List<Formulario> lista;
		try {
			TypedQuery<Formulario> query = em.createQuery(
					"FROM Formulario WHERE acto.idacto=:idacto AND conFormulario.dsnombrecorto=:nombrecorto ORDER BY conFormulario.posiciontarjeton",
					Formulario.class);
			query.setParameter("idacto", idacto);
			query.setParameter("nombrecorto",nombrecorto);

			lista = query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				ConFormulario conformulario;
				List<ValorSubFormulario> listaValSubFormulario;
				for (Formulario f : lista) {
					conformulario = f.getConFormulario();
					if (conformulario instanceof HibernateProxy) {
						conformulario = (ConFormulario) ((HibernateProxy) conformulario).getHibernateLazyInitializer()
								.getImplementation();
						conformulario.setListaActoFormularios(null);
						conformulario.setListaComponentes(null);
						conformulario.setListaPermisosRol(null);
						conformulario.setListaSubFormularios(null);
					}
					f.setConFormulario(conformulario);
				}
				return lista.get(0);
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return null;
	}

}
