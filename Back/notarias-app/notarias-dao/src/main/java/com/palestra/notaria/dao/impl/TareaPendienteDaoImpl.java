package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.TareaPendienteDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.TareaPendiente;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;

public class TareaPendienteDaoImpl extends GenericDaoImpl<TareaPendiente, Integer> implements TareaPendienteDao {

	public TareaPendienteDaoImpl() {
		super(TareaPendiente.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TareaPendiente> obtenerListaCompleta(Usuario usuario) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		List<TareaPendiente> lista = null;

		try {
			Query query = em.createQuery(
					"from TareaPendiente where usuariorecibe.idusuario = '" + usuario.getIdusuario() + "'");

			lista = query.getResultList();

			if (lista != null) {
				Usuario usuarioAsigna;
				Usuario usuarioRecibe;
				Rol rol;

				for (TareaPendiente tp : lista) {

					usuarioAsigna = tp.getUsuarioasigna();
					if (usuarioAsigna instanceof HibernateProxy) {
						usuarioAsigna = (Usuario) ((HibernateProxy) usuarioAsigna).getHibernateLazyInitializer()
								.getImplementation();
						rol = usuarioAsigna.getRol();
						if (rol instanceof HibernateProxy) {
							rol = (Rol) ((HibernateProxy) rol).getHibernateLazyInitializer().getImplementation();
							rol.setIdsesion(null);
							rol.setTmstmp(null);
							rol.setDsdescripcion(null);
						}
						usuarioAsigna.setRol(rol);
						usuarioAsigna.setDsultimoacceso(null);
						usuarioAsigna.setTmstmp(null);
						usuarioAsigna.setIsactualizapwd(null);
						usuarioAsigna.setIsactivo(null);
						usuarioAsigna.setInestatus(null);
						usuarioAsigna.setIdsesion(null);
						usuarioAsigna.setDsvalenc(null);
						usuarioAsigna.setDsultimoacceso(null);
						usuarioAsigna.setDsrfc(null);

					}
					tp.setUsuarioasigna(usuarioAsigna);

					usuarioRecibe = tp.getUsuariorecibe();
					if (usuarioRecibe instanceof HibernateProxy) {
						usuarioRecibe = (Usuario) ((HibernateProxy) usuarioRecibe).getHibernateLazyInitializer()
								.getImplementation();
						rol = usuarioRecibe.getRol();
						if (rol instanceof HibernateProxy) {
							rol = (Rol) ((HibernateProxy) rol).getHibernateLazyInitializer().getImplementation();
							rol.setIdsesion(null);
							rol.setTmstmp(null);
							rol.setDsdescripcion(null);
						}
						usuarioRecibe.setRol(rol);
						usuarioRecibe.setDsultimoacceso(null);
						usuarioRecibe.setTmstmp(null);
						usuarioRecibe.setIsactualizapwd(null);
						usuarioRecibe.setIsactivo(null);
						usuarioRecibe.setInestatus(null);
						usuarioRecibe.setIdsesion(null);
						usuarioRecibe.setDsvalenc(null);
						usuarioRecibe.setDsultimoacceso(null);
						usuarioRecibe.setDsrfc(null);

					}
					tp.setUsuariorecibe(usuarioRecibe);
					tp.setTramite(null);
					tp.setExpediente(null);
					tp.setTmstmp(null);
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
	public TareaPendiente buscarPorIdCompleto(TareaPendiente tareaPendiente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TareaPendiente tp = null;
		try {

			Query query = em
					.createQuery("from TareaPendiente where idtareapend = '" + tareaPendiente.getIdtareapend() + "'");

			tp = (TareaPendiente) query.getSingleResult();
			Usuario usuarioAsigna;
			Usuario usuarioRecibe;
			Usuario abogado;
			Rol rol;
			Expediente exp;
			Tramite tram;
			usuarioAsigna = tp.getUsuarioasigna();
			if (usuarioAsigna instanceof HibernateProxy) {
				usuarioAsigna = (Usuario) ((HibernateProxy) usuarioAsigna).getHibernateLazyInitializer()
						.getImplementation();
				rol = usuarioAsigna.getRol();
				if (rol instanceof HibernateProxy) {
					rol = (Rol) ((HibernateProxy) rol).getHibernateLazyInitializer().getImplementation();
					rol.setIdsesion(null);
					rol.setTmstmp(null);
					rol.setDsdescripcion(null);
				}
				usuarioAsigna.setRol(rol);
				usuarioAsigna.setDsultimoacceso(null);
				usuarioAsigna.setTmstmp(null);
				usuarioAsigna.setIsactualizapwd(null);
				usuarioAsigna.setIsactivo(null);
				usuarioAsigna.setInestatus(null);
				usuarioAsigna.setIdsesion(null);
				usuarioAsigna.setDsvalenc(null);
				usuarioAsigna.setDsultimoacceso(null);
				usuarioAsigna.setDsrfc(null);

			}
			tp.setUsuarioasigna(usuarioAsigna);

			usuarioRecibe = tp.getUsuariorecibe();
			if (usuarioRecibe instanceof HibernateProxy) {
				usuarioRecibe = (Usuario) ((HibernateProxy) usuarioRecibe).getHibernateLazyInitializer()
						.getImplementation();
				rol = usuarioRecibe.getRol();
				if (rol instanceof HibernateProxy) {
					rol = (Rol) ((HibernateProxy) rol).getHibernateLazyInitializer().getImplementation();
					rol.setIdsesion(null);
					rol.setTmstmp(null);
					rol.setDsdescripcion(null);
				}
				usuarioRecibe.setRol(rol);
				usuarioRecibe.setDsultimoacceso(null);
				usuarioRecibe.setTmstmp(null);
				usuarioRecibe.setIsactualizapwd(null);
				usuarioRecibe.setIsactivo(null);
				usuarioRecibe.setInestatus(null);
				usuarioRecibe.setIdsesion(null);
				usuarioRecibe.setDsvalenc(null);
				usuarioRecibe.setDsultimoacceso(null);
				usuarioRecibe.setDsrfc(null);

			}
			tp.setUsuariorecibe(usuarioRecibe);
			tp.setTmstmp(null);
			if (tp.getIsmanual() == 0) {
				exp = tp.getExpediente();
				if (exp instanceof HibernateProxy) {
					exp = (Expediente) ((HibernateProxy) exp).getHibernateLazyInitializer().getImplementation();
					exp.setAbogado(null);
					// exp.setListaComentarios(null);
					exp.setTramite(null);
				}
				tp.setExpediente(exp);
				if (exp == null) {
					tram = tp.getTramite();
					if (tram instanceof HibernateProxy) {
						tram = (Tramite) ((HibernateProxy) tram).getHibernateLazyInitializer().getImplementation();
						abogado = tram.getAbogado();
						if (abogado instanceof HibernateProxy) {
							abogado = (Usuario) ((HibernateProxy) abogado).getHibernateLazyInitializer()
									.getImplementation();
							abogado.setDsultimoacceso(null);
							abogado.setTmstmp(null);
							abogado.setIsactualizapwd(null);
							abogado.setIsactivo(null);
							abogado.setInestatus(null);
							abogado.setIdsesion(null);
							abogado.setDsvalenc(null);
							abogado.setDsultimoacceso(null);
							abogado.setDsrfc(null);
						}
						tram.setAbogado(abogado);
						tram.setCliente(null);
						tram.setLocacion(null);
						tram.setStatus(null);
						tram.setTmstmp(null);

					}
					tp.setTramite(tram);
				}

			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return tp;
	}
}
