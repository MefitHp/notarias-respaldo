package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.LazyInitializationException;
import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.TramiteDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EtapaTramite;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;

public class TramiteDaoImpl extends GenericDaoImpl<Tramite, Integer> implements TramiteDao {

	public TramiteDaoImpl() {
		super(Tramite.class);
	}

	@Override
	public Tramite buscarPorIdCompleto(Tramite tramite) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		Tramite t;
		try {
			TypedQuery<Tramite> query = em.createQuery("FROM Tramite WHERE idtramite = :idtramite", Tramite.class);
			query.setParameter("idtramite", tramite.getIdtramite());
			t = query.getSingleResult();
			ElementoCatalogo locacion;
			Usuario abogado;
			Persona cliente;
			EtapaTramite status;
			if (t != null) {
				locacion = t.getLocacion();
				if (locacion instanceof HibernateProxy) {
					locacion = (ElementoCatalogo) ((HibernateProxy) locacion).getHibernateLazyInitializer()
							.getImplementation();
					locacion.setCatalogo(null);
					locacion.setCatalogo(null);
				}
				t.setLocacion(locacion);

				abogado = t.getAbogado();
				if (abogado instanceof HibernateProxy) {
					abogado = (Usuario) ((HibernateProxy) abogado).getHibernateLazyInitializer().getImplementation();
					abogado.setAcceso(null);
					abogado.setRol(null);
				}
				t.setAbogado(abogado);

				cliente = t.getCliente();
				// if(cliente instanceof HibernateProxy){
				// cliente =
				// (Persona)((HibernateProxy)cliente).getHibernateLazyInitializer().getImplementation();
				cliente.setNacionalidad(null);
				cliente.setTipopersona(null);
				// }
				t.setCliente(cliente);
				status = t.getStatus();
				if (status instanceof HibernateProxy) {
					status = (EtapaTramite) ((HibernateProxy) status).getHibernateLazyInitializer().getImplementation();
				}
				t.setStatus(status);
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> buscarTramitesPorCliente(String idCliente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		ElementoCatalogo locacion;
		Usuario abogado;
		EtapaTramite status;
		try {
			Query query = em.createQuery("from Tramite where cliente.idpersona = " + idCliente);

			List<Tramite> lista = query.getResultList();

			if (lista != null) {
				for (Tramite t : lista) {
					locacion = t.getLocacion();
					if (locacion instanceof HibernateProxy) {
						locacion = (ElementoCatalogo) ((HibernateProxy) locacion).getHibernateLazyInitializer()
								.getImplementation();
						locacion.setCatalogo(null);
						locacion.setCatalogo(null);
					}
					t.setLocacion(locacion);

					abogado = t.getAbogado();
					if (abogado instanceof HibernateProxy) {
						abogado = (Usuario) ((HibernateProxy) abogado).getHibernateLazyInitializer()
								.getImplementation();
						abogado.setAcceso(null);
						abogado.setRol(null);
					}
					t.setAbogado(abogado);

					status = t.getStatus();
					if (status instanceof HibernateProxy) {
						status = (EtapaTramite) ((HibernateProxy) status).getHibernateLazyInitializer()
								.getImplementation();
					}
					t.setStatus(status);

				}

			}
			return null;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Expediente buscarIdExpediente(String idtramite) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		Expediente exp = null;
		try {
			Query query = em.createQuery("from Expediente where tramite.idtramite =  '" + idtramite + "'");

			List<Expediente> lista = query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				exp = lista.get(0);
				
				// SOLICITO EL NUMERO DE ESCRITURA
				List<Escritura> listesc;
				TypedQuery<String> queryesc = em.createQuery("SELECT dsnumescritura FROM Escritura e WHERE e.expediente.idexpediente=:idexpediente",String.class);
				queryesc.setParameter("idexpediente", exp.getIdexpediente());
				exp.setNumerosescrituras(queryesc.getResultList());
				// INICIALIZO TRAMITE
				Tramite tramite = exp.getTramite();
				if (tramite instanceof HibernateProxy) {
					tramite = (Tramite) ((HibernateProxy) tramite).getHibernateLazyInitializer().getImplementation();
					tramite.getCliente().setNacionalidad(null);
					tramite.getCliente().setTipopersona(null);

					exp.setTramite(tramite);
				}
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return exp;
	}

	@Override
	public List<Tramite> findAll() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		ElementoCatalogo locacion;
		Usuario abogado;
		EtapaTramite status;
		try {
			Query query = em.createQuery("from Tramite ");

			@SuppressWarnings("unchecked")
			List<Tramite> lista = query.getResultList();

			if (lista != null) {
				for (Tramite t : lista) {
					locacion = t.getLocacion();
					if (locacion instanceof HibernateProxy) {
						locacion = (ElementoCatalogo) ((HibernateProxy) locacion).getHibernateLazyInitializer()
								.getImplementation();
						locacion.setCatalogo(null);
						locacion.setCatalogo(null);
					}
					t.setLocacion(locacion);

					abogado = t.getAbogado();
					if (abogado instanceof HibernateProxy) {
						abogado = (Usuario) ((HibernateProxy) abogado).getHibernateLazyInitializer()
								.getImplementation();
						abogado.setAcceso(null);
						abogado.setRol(null);
					}
					t.setAbogado(abogado);

					status = t.getStatus();
					if (status instanceof HibernateProxy) {
						status = (EtapaTramite) ((HibernateProxy) status).getHibernateLazyInitializer()
								.getImplementation();
					}
					t.setStatus(status);
					t.setCliente(null);
				}
				return lista;
			}
			return null;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<Tramite> findByAbogado(String idusuario) throws NotariaException {
		List<Tramite> tramites = executeQuery("SELECT t FROM Tramite t WHERE t.abogado.idusuario = ?1", idusuario);
		for (Tramite tramite : tramites) {
			tramite.setCliente(null);
		}
		return tramites;
	}

	@Override
	public Long buscarPosicionTramitePorAbogado(String idusuario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Long> query = em.createQuery(
				"SELECT COUNT(t) FROM Tramite t WHERE t.abogado.idusuario = '" + idusuario + "'", Long.class);
		Long tramites = query.getSingleResult();
		em.close();
		return tramites;
	}

	@Override
	public Tramite findByExpediente(Expediente expediente) throws NotariaException {
		ElementoCatalogo locacion;
		Usuario abogado;
		EtapaTramite status;
		Persona cliente;
		List<Tramite> tramites=null; 

		tramites = executeQuery("SELECT e.tramite FROM Expediente e WHERE e.idexpediente = ?1",
				expediente.getIdexpediente());

		if (tramites.size() > 0){
//			locacion = tramites.get(0).getLocacion();
//			if (locacion instanceof HibernateProxy) {
//				locacion = (ElementoCatalogo) ((HibernateProxy) locacion).getHibernateLazyInitializer()
//						.getImplementation();
//				locacion.setCatalogo(null);
//				locacion.setCatalogo(null);
//			}
//			tramites.get(0).setLocacion(locacion);
//
//			abogado = tramites.get(0).getAbogado();
//			if (abogado instanceof HibernateProxy) {
//				abogado = (Usuario) ((HibernateProxy) abogado).getHibernateLazyInitializer()
//						.getImplementation();
//				abogado.setAcceso(null);
//				abogado.setRol(null);
//			}
//			tramites.get(0).setAbogado(abogado);
//
//			status = tramites.get(0).getStatus();
//			if (status instanceof HibernateProxy) {
//				status = (EtapaTramite) ((HibernateProxy) status).getHibernateLazyInitializer()
//						.getImplementation();
//			}
//			tramites.get(0).setStatus(status);
//			
			cliente = tramites.get(0).getCliente();
			if(cliente instanceof HibernateProxy){
				cliente = (Persona)((HibernateProxy)cliente).getHibernateLazyInitializer().getImplementation(); 
			}
			tramites.get(0).setCliente(cliente);
//			
			return tramites.get(0);
		}
		return null;
	}
	
		public static void main (String[]args){
			TramiteDaoImpl tram = new TramiteDaoImpl();
			Expediente exp = new Expediente();
			exp.setIdexpediente("28ba57e8657b06fd17cc7b46fa185cb2");
			try {
				Tramite tramite = tram.findByExpediente(exp);
				tramite.getCliente().getEmails();
			} catch (NotariaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
