package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.InmuebleDao;
import com.palestra.notaria.dato.DatoInmueble;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Domicilio;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Inmueble;
import com.palestra.notaria.modelo.Suboperacion;

public class InmuebleDaoImpl extends GenericDaoImpl<Inmueble, Integer>
		implements InmuebleDao {

	public InmuebleDaoImpl() {
		super(Inmueble.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inmueble> obtenerInmueblesPorExpediente(String expedienteId) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery("SELECT inmu FROM Inmueble inmu "
					+ "    WHERE inmu.tbbsnm32.idexpediente = :idexpediente");
	
			query.setParameter("idexpediente", expedienteId);
	
			return query.getResultList();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Inmueble buscarPorIdCompleto(Inmueble rInmueble) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {

			StringBuilder hql = new StringBuilder();
			hql.append(" SELECT inm FROM Inmueble inm");
			hql.append(" WHERE inm.idinmueble=:id");
			Query query = em.createQuery(hql.toString());
			query.setParameter("id", rInmueble.getIdinmueble());

			List<Inmueble> lista = (List<Inmueble>) query.getResultList();

			if (lista.size() == 0) {
				return null;
			}

			/**
			 * Tomar elemento de la lista, sabemos que solo deberia ser 1
			 * resultado.
			 **/
			Inmueble inmueble = lista.get(0);

			ElementoCatalogo vocterreno = null;
			ElementoCatalogo vochabitacional = null;
			ElementoCatalogo voccomercial = null;
			Domicilio domicilio = null;
			Acto acto = null;
			Expediente expediente = null;

			if (inmueble != null) {
				vocterreno = inmueble.getVocterreno();
				if (vocterreno instanceof HibernateProxy) {
					vocterreno = (ElementoCatalogo) ((HibernateProxy) vocterreno)
							.getHibernateLazyInitializer().getImplementation();
					vocterreno.setCatalogo(null);
				}
				inmueble.setVocterreno(vocterreno);

				vochabitacional = inmueble.getVochabitacional();
				if (vochabitacional instanceof HibernateProxy) {
					vochabitacional = (ElementoCatalogo) ((HibernateProxy) vochabitacional)
							.getHibernateLazyInitializer().getImplementation();
					vochabitacional.setCatalogo(null);
				}
				inmueble.setVochabitacional(vochabitacional);

				voccomercial = inmueble.getVoccomercial();
				if (voccomercial instanceof HibernateProxy) {
					voccomercial = (ElementoCatalogo) ((HibernateProxy) voccomercial)
							.getHibernateLazyInitializer().getImplementation();
					voccomercial.setCatalogo(null);
				}
				inmueble.setVoccomercial(voccomercial);

				domicilio = inmueble.getDomicilio();
				if (domicilio instanceof HibernateProxy) {
					domicilio = (Domicilio) ((HibernateProxy) domicilio)
							.getHibernateLazyInitializer().getImplementation();
				}
				inmueble.setDomicilio(domicilio);

				acto = inmueble.getActo();
				if (acto instanceof HibernateProxy) {
					acto = (Acto) ((HibernateProxy) acto)
							.getHibernateLazyInitializer().getImplementation();
					Suboperacion suboperacion = acto.getSuboperacion();
					if (suboperacion instanceof HibernateProxy) {
						suboperacion = (Suboperacion) ((HibernateProxy) suboperacion)
								.getHibernateLazyInitializer()
								.getImplementation();
						suboperacion.setOperacion(null);
					}
					acto.setSuboperacion(suboperacion);
					expediente = acto.getExpediente();
					if (expediente instanceof HibernateProxy) {
						expediente = (Expediente) ((HibernateProxy) expediente)
								.getHibernateLazyInitializer()
								.getImplementation();
						//expediente.setListaComentarios(null);
						expediente.setTramite(null);
						expediente.setAbogado(null);
					}
					acto.setExpediente(expediente);

				}
				inmueble.setActo(acto);
			}
			return inmueble;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public Boolean registrarInmueble(Inmueble inmueble) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;

		try {
			tx = em.getTransaction();
			tx.begin();

			// Registrar el domicilio
			em.persist(inmueble.getDomicilio());

			// Registrar el inmueble
			em.persist(inmueble);

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
	public Boolean eliminarInmueble(Inmueble inmueble) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			
			//Por restriccion fk, primero eliminar inmueble
			query = em
					.createQuery("delete Inmueble where idinmueble = :idinmueble");
			query.setParameter("idinmueble", inmueble.getIdinmueble()).executeUpdate();

			//Segundo eliminar domicilio
			query = em
					.createQuery("delete Domicilio where iddomicilio = :iddomicilio");
			query.setParameter("iddomicilio", inmueble.getDomicilio()
					.getIddomicilio()).executeUpdate();
			
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

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoInmueble> obtenListaInmuebles(Inmueble inmueble) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {

			// TODO: cambiar
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT inm.idinmueble, inm.valcatastral, inm.dsdescripcion");
			sql.append(" ,inm.acto.idacto, inm.acto.suboperacion.dsnombre, inm.acto.suboperacion.dsdescripcion");
			sql.append(" FROM Inmueble inm ");

			Map<String, Object> params = new HashMap<String, Object>();

			boolean first = true;

			if (inmueble.getActo() != null
					&& inmueble.getActo().getIdacto() != null) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("inm.acto.idacto = :idacto ");
				params.put("idacto", inmueble.getActo().getIdacto());
				first = false;
			}

			if (inmueble.getActo() != null
					&& inmueble.getActo().getExpediente() != null
					&& inmueble.getActo().getExpediente().getIdexpediente() != null) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("inm.acto.expediente.idexpediente = :idexpediente ");
				params.put("idexpediente", inmueble.getActo().getExpediente()
						.getIdexpediente());
				first = false;
			}

			/** ordernar **/
			sql.append(" ORDER BY inm.tmstmp ");

			Query query = em.createQuery(sql.toString());
			for (String param : params.keySet()) {
				query.setParameter(param, params.get(param));
			}

			List<Object[]> lista = (List<Object[]>) query.getResultList();

			if (lista.size() == 0) {
				return null;
			}

			List<DatoInmueble> resultados = new ArrayList<DatoInmueble>();
			for (Object[] obj : lista) {
				DatoInmueble dinm = new DatoInmueble();
				dinm.setIdinmueble((String) obj[0]);
				dinm.setValcatastral((Double) obj[1]);
				dinm.setDsdescripcion((String) obj[2]);
				dinm.setIdacto((String) obj[3]);
				dinm.setActoNombre((String) obj[4]);
				dinm.setActoDescripcion((String) obj[5]);

				resultados.add(dinm);
			}

			return resultados;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}
}
