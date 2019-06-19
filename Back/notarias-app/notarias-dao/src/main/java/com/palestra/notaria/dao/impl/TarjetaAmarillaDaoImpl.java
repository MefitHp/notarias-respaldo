package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.TarjetaAmarillaDao;
import com.palestra.notaria.dato.DatoActoDeTarjeta;
import com.palestra.notaria.dato.DatoTarjetaAmarilla;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.TarjetaAmarilla;
import com.palestra.notaria.modelo.Usuario;

public class TarjetaAmarillaDaoImpl extends GenericDaoImpl<TarjetaAmarilla, Integer> implements TarjetaAmarillaDao {

	public TarjetaAmarillaDaoImpl() {
		super(TarjetaAmarilla.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DatoActoDeTarjeta obtenDataFromActo(String id) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" select escact.escritura.idescritura, escact.acto.expediente.idexpediente");
			sql.append(" , escact.escritura.dsnumescritura,escact.acto.suboperacion.operacion.dsnombre ");
			sql.append(" , escact.acto.suboperacion.operacion.dsdescripcion ");
			sql.append(" from EscrituraActo escact ");
			sql.append(" where escact.acto.idacto =:idacto");

			Query query = em.createQuery(sql.toString());
			query.setParameter("idacto", id);

			List<Object[]> lista = query.getResultList();

			if (lista.size() == 0) {
				return null;
			}

			for (Object[] obj : lista) {
				DatoActoDeTarjeta actoData = new DatoActoDeTarjeta();
				actoData.setIdEscritura((String) obj[0]);
				actoData.setIdExpediente((String) obj[1]);
				actoData.setNumEscritura((String) obj[2]);
				actoData.setOperacionNombre((String) obj[3]);
				actoData.setOperacionDescripcion((String) obj[4]);

				/** retornar primera coincidencia, que deberia ser la unica. **/
				return actoData;
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		/** si no se pudo obtener los datos retorna null **/
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TarjetaAmarilla buscarPorIdCompleto(TarjetaAmarilla tarjetaAmarilla) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {

			StringBuilder hql = new StringBuilder();
			hql.append(" SELECT ta FROM TarjetaAmarilla ta ");
			hql.append(" WHERE ta.idtrjamarilla=:id");
			Query query = em.createQuery(hql.toString());
			query.setParameter("id", tarjetaAmarilla.getIdtrjamarilla());

			List<TarjetaAmarilla> lista = (List<TarjetaAmarilla>) query.getResultList();

			if (lista.size() == 0) {
				return null;
			}

			/**
			 * Tomar elemento de la lista, sabemos que solo deberia ser 1
			 * resultado.
			 **/
			TarjetaAmarilla tarjeta = lista.get(0);

			Acto acto = null;
			Persona persona = null;
			Usuario usuario = null;
			Expediente expediente = null;

			if (tarjeta != null) {
				acto = tarjeta.getActo();
				if (acto instanceof HibernateProxy) {
					acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
					acto.setSuboperacion(null);
					expediente = acto.getExpediente();
					if (expediente instanceof HibernateProxy) {
						expediente = (Expediente) ((HibernateProxy) expediente).getHibernateLazyInitializer()
								.getImplementation();
						// expediente.setListaComentarios(null);
						expediente.setTramite(null);
						expediente.setAbogado(null);
					}
					acto.setExpediente(expediente);
				}
				tarjeta.setActo(acto);

				persona = tarjeta.getPersona();
				if (persona instanceof HibernateProxy) {
					persona = (Persona) ((HibernateProxy) persona).getHibernateLazyInitializer().getImplementation();
					persona.setTipopersona(null);
					persona.setNacionalidad(null);
				}
				tarjeta.setPersona(persona);

				usuario = tarjeta.getUsuarioelab();
				if (usuario instanceof HibernateProxy) {
					usuario = (Usuario) ((HibernateProxy) usuario).getHibernateLazyInitializer().getImplementation();
					usuario.setRol(null);
				}
				tarjeta.setUsuarioelab(usuario);
			}

			return tarjeta;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoTarjetaAmarilla> obtenListaTarjetas(TarjetaAmarilla tarjetaAmarilla) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT tar.idtrjamarilla, tar.dsnomcliente");
			sql.append(" ,tar.acto.idacto, tar.acto.dsnombre");
			sql.append(" ,tar.usuarioelab.dsnombre, tar.usuarioelab.dspaterno, tar.total ,tar.fecha");
			sql.append(" FROM TarjetaAmarilla tar ");

			Map<String, Object> params = new HashMap<String, Object>();

			boolean first = true;

			if (tarjetaAmarilla.getActo() != null && tarjetaAmarilla.getActo().getIdacto() != null) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("tar.acto.idacto = :idacto ");
				params.put("idacto", tarjetaAmarilla.getActo().getIdacto());
				first = false;
			}

			if (tarjetaAmarilla.getActo() != null && tarjetaAmarilla.getActo().getExpediente() != null
					&& tarjetaAmarilla.getActo().getExpediente().getIdexpediente() != null) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("tar.acto.expediente.idexpediente = :idexpediente ");
				params.put("idexpediente", tarjetaAmarilla.getActo().getExpediente().getIdexpediente());
				first = false;
			}

			/** ordernar **/
			sql.append(" ORDER BY tar.tmstmp ");

			Query query = em.createQuery(sql.toString());
			for (String param : params.keySet()) {
				query.setParameter(param, params.get(param));
			}

			List<Object[]> lista = (List<Object[]>) query.getResultList();

			if (lista.size() == 0) {
				return null;
			}

			List<DatoTarjetaAmarilla> resultados = new ArrayList<DatoTarjetaAmarilla>();

			for (Object[] obj : lista) {
				DatoTarjetaAmarilla dta = new DatoTarjetaAmarilla();
				dta.setIdtrjamarilla((String) obj[0]);
				dta.setNombreCliente((String) obj[1]);
				dta.setIdacto((String) obj[2]);
				dta.setActoNombre((String) obj[3]);
				String nombre = (String) obj[4];
				String apellidoPaterno = obj[5] != null ? (String) obj[5] : "";
				dta.setUsuarioElaboro(nombre + " " + apellidoPaterno);
				dta.setTotal((Double) obj[6]);
				dta.setFecha((Date) obj[7]);

				resultados.add(dta);
			}

			return resultados;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

}