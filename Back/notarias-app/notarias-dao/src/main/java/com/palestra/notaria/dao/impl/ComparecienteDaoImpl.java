package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ComparecienteDao;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoBusquedaCompareciente;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notaria.modelo.ComparecienteRepresentante;
import com.palestra.notaria.modelo.Contacto;
import com.palestra.notaria.modelo.Domicilio;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EtapaTramite;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Firma;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.RegistroRi;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.modelo.TipoCompareciente;
import com.palestra.notaria.modelo.Tramite;

public class ComparecienteDaoImpl extends
		GenericDaoImpl<Compareciente, Integer> implements ComparecienteDao {

	private static final Boolean IS_REPRESENTANTE = true;

	public ComparecienteDaoImpl() {
		super(Compareciente.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Compareciente> listadoComparecientes(
			Compareciente compareciente, String... tipoComp)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Compareciente> comparecienteList;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT c FROM Compareciente c ");

			Map<String, Object> params = new HashMap<String, Object>();
			boolean first = true;
			if (compareciente.getActo() != null
					&& compareciente.getActo().getIdacto() != null) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("c.acto.idacto = :idacto ");
				params.put("idacto", compareciente.getActo().getIdacto());
				first = false;
			}
			if (compareciente.getActo() != null
					&& compareciente.getActo().getExpediente() != null
					&& compareciente.getActo().getExpediente()
							.getIdexpediente() != null) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("c.acto.expediente.idexpediente = :idexpediente ");
				params.put("idexpediente", compareciente.getActo()
						.getExpediente().getIdexpediente());
				first = false;
			}
			// @omarete separar si se listan compradores y vendedores ó
			// representantes y autorizantes por nombre
			if (tipoComp.length != 0) {
				sql.append("AND c.tipoCompareciente.dsnombre = :tipoComp ");
				params.put("tipoComp", tipoComp[0]);
			} else {
				sql.append("AND c.tipoCompareciente.idtipocompareciente IN (:vendedor, :comprador, :acreedor, :deudor,:garante,:autorizante,:apoderado,"
						+ ":poderdante,:socio,:ratificante,:testador,:esposa,:compareciente,:solidario,:acreditado,:acreditadoygarante,:cancelahipoteca,"
						+ ":banco,:demandado,:juzgado) ");
				params.put("comprador", Constantes.ID_COMPRADOR);
				params.put("vendedor", Constantes.ID_VENDEDOR);
				params.put("acreedor", Constantes.ID_ACREEDOR);
				params.put("deudor", Constantes.ID_DEUDOR);
				params.put("garante", Constantes.ID_GARANTE);
				params.put("autorizante", Constantes.ID_AUTORIZANTE);
				params.put("apoderado", Constantes.ID_APODERADO);
				params.put("poderdante", Constantes.ID_PODERDANTE);
				params.put("socio", Constantes.ID_SOCIO);
				params.put("ratificante", Constantes.ID_RATIFICANTE);
				params.put("testador", Constantes.ID_TESTADOR);
				params.put("esposa", Constantes.ID_ESPOSA);
				params.put("compareciente", Constantes.ID_COMPARECIENTE);
				params.put("solidario", Constantes.ID_SOLIDARIO);
				params.put("acreditado", Constantes.ID_ACREDITADO);
				params.put("acreditadoygarante", Constantes.ID_ACREDITADOYGARANTE);
				params.put("cancelahipoteca", Constantes.ID_CANCELAHIPOTECA);
				params.put("banco", Constantes.ID_BANCO);
				params.put("demandado", Constantes.ID_DEMANDADO);
				params.put("juzgado", Constantes.ID_JUZGADO);
			}
			/** ordernar **/
			sql.append(" ORDER BY c.persona.dsnombrecompleto ");
			System.out.println("SQL *************** " + sql);
			Query query = em.createQuery(sql.toString());
			for (String param : params.keySet()) {
				query.setParameter(param, params.get(param));
			}
			comparecienteList = (List<Compareciente>) query.getResultList();

			if (comparecienteList != null) {
				for (Compareciente c : comparecienteList) {
					this.hibernateLazyInitializer(c);
				}
			}
			return comparecienteList;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<Compareciente> listadoComparecientes(String acto,
			String tipoCompareciente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Compareciente> comparecienteList;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT c FROM Compareciente c ");

			Map<String, Object> params = new HashMap<String, Object>();
			boolean first = true;
			if (acto != null && !acto.isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("c.acto.idacto = :idacto ");
				params.put("idacto", acto);
				first = false;
			}
			// @omarete separar si se listan compradores y vendedores ó
			// representantes y autorizantes por nombre
			if (tipoCompareciente.length() > 0) {
				sql.append("AND c.tipoCompareciente.dsnombre = :tipoComp ");
				params.put("tipoComp", tipoCompareciente);
			} else {
				sql.append("AND c.tipoCompareciente.idtipocompareciente IN (:vendedor, :comprador) ");
				params.put("comprador", Constantes.ID_COMPRADOR);
				params.put("vendedor", Constantes.ID_VENDEDOR);
			}
			/** ordernar **/
			sql.append(" ORDER BY c.persona.dsnombrecompleto ");

			TypedQuery<Compareciente> query = em.createQuery(sql.toString(),
					Compareciente.class);
			for (String param : params.keySet()) {
				query.setParameter(param, params.get(param));
			}
			comparecienteList = query.getResultList();

			if (comparecienteList != null) {
				for (Compareciente c : comparecienteList) {
					this.hibernateLazyInitializer(c);
				}
			}
			return comparecienteList;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<Compareciente> listadoComparecientes(String acto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Compareciente> comparecienteList;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT c FROM Compareciente c WHERE c.acto.idacto = :idacto");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("idacto", acto);
			
			/** ordernar **/
			sql.append(" ORDER BY c.persona.dsnombrecompleto ");

			TypedQuery<Compareciente> query = em.createQuery(sql.toString(),
					Compareciente.class);
			for (String param : params.keySet()) {
				query.setParameter(param, params.get(param));
			}
			comparecienteList = query.getResultList();

			if (comparecienteList != null) {
				for (Compareciente c : comparecienteList) {
					this.hibernateLazyInitializer(c);
				}
			}
			return comparecienteList;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	

	@Override
	public List<String> nombresCompareciente(String idacto, String tipo)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<String> query = em
					.createQuery(
							"SELECT com.persona.dsnombrecompleto FROM Compareciente com WHERE com.acto.idacto = :idacto AND com.tipoCompareciente.dsnombre = :tipo",
							String.class);
			query.setParameter("idacto", idacto);
			query.setParameter("tipo", tipo);
			List<String> vuelta = query.getResultList();
			return vuelta;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoBusquedaCompareciente> findByActoId(String id)
			throws NotariaException {

		EntityManager em = factory.createEntityManager();

		try {

			StringBuilder sb = new StringBuilder();

			sb.append(" SELECT com.persona.idpersona, com.persona.dsnombre, com.persona.dsapellidopat ");
			sb.append(" , com.persona.dsapellidomat, com.persona.dsnombrecompleto, com.persona.dsrfc ");
			sb.append(" FROM Compareciente com");
			sb.append(" WHERE com.acto.idacto =:idacto");
			sb.append(" ORDER BY com.persona.dsnombrecompleto ");

			Query query = em.createQuery(sb.toString());
			query.setParameter("idacto", id);
			List<Object[]> lista = query.getResultList();
			System.out.println("Se encontraron: " + lista.size()
					+ " resultados.");
			if (lista.size() == 0) {
				return null;
			}

			List<DatoBusquedaCompareciente> resultados = new ArrayList<DatoBusquedaCompareciente>();

			for (Object[] obj : lista) {
				DatoBusquedaCompareciente dbc = new DatoBusquedaCompareciente();
				dbc.setIdpersona((String) obj[0]);
				dbc.setDsnombre((String) obj[1]);
				dbc.setDsapellidopat((String) obj[2]);
				dbc.setDsapellidomat((String) obj[3]);
				dbc.setDsnombrecompleto((String) obj[4]);
				dbc.setDsrfc((String) obj[5]);
				resultados.add(dbc);
			}

			return resultados;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Compareciente buscarPorIdCompleto(Compareciente compareciente)
			throws NotariaException {

		if (compareciente == null) {
			return null;
		}

		EntityManager em = factory.createEntityManager();
		try {
			if (compareciente.getIdcompareciente() != null) {

				StringBuilder hql = new StringBuilder();
				hql.append("SELECT c FROM Compareciente c WHERE c.idcompareciente=:id");

				Query query = em.createQuery(hql.toString());
				query.setParameter("id", compareciente.getIdcompareciente());

				@SuppressWarnings("unchecked")
				List<Compareciente> lista = (List<Compareciente>) query
						.getResultList();
				if (lista.size() == 0) {
					return null;
				}
				/**
				 * Tomar elemento de la lista, sabemos que solo deberia ser 1
				 * resultado.
				 **/
				Compareciente c = lista.get(0);
				/** Inicializar propiedades de tipo lazy **/
				this.hibernateLazyInitializer(c);
				c.getActo().getExpediente().setTramite(null);
				System.out.println("nacionalidad comp en dao "
						+ c.getPersona().getNacionalidad());
				return c;

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

	private void hibernateLazyInitializer(Compareciente c) {
		Persona persona = null;
		Acto acto = null;
		ElementoCatalogo tipoPersona = null;
		ElementoCatalogo nacionalidad = null;
		TipoCompareciente tipoCompareciente = null;
		Expediente expediente = null;
		Tramite tramite = null;
		EtapaTramite status = null;
		RegistroRi registroRi = null;
		ElementoCatalogo expedidopor = null;
		ElementoCatalogo tipo = null;
		Suboperacion suboperacion = null;
		Contacto contacto = null;
		Firma firma = null;

		
		firma = c.getFirma();
		if (firma instanceof HibernateProxy) {
			firma = (Firma) ((HibernateProxy) firma)
					.getHibernateLazyInitializer().getImplementation();
			Compareciente comp = new Compareciente();
			comp.setIdcompareciente(firma.getCompareciente().getIdcompareciente());
			firma.setCompareciente(comp);			
		}
		
		c.setFirma(firma);
		
		persona = c.getPersona();
		if (persona instanceof HibernateProxy) {
			persona = (Persona) ((HibernateProxy) persona)
					.getHibernateLazyInitializer().getImplementation();
		}
		
		
		
		tipoPersona = persona.getTipopersona();
		if (tipoPersona instanceof HibernateProxy) {
			tipoPersona = (ElementoCatalogo) ((HibernateProxy) tipoPersona)
					.getHibernateLazyInitializer().getImplementation();
			tipoPersona.setCatalogo(null);
		}
		persona.setTipopersona(tipoPersona);
		nacionalidad = persona.getNacionalidad();
		if (nacionalidad instanceof HibernateProxy) {
			System.out.println("entra a if nacionalidad");
			nacionalidad = (ElementoCatalogo) ((HibernateProxy) nacionalidad)
					.getHibernateLazyInitializer().getImplementation();
			nacionalidad.setCatalogo(null);
		}
		persona.setNacionalidad(nacionalidad);

		c.setPersona(persona);
		tipoCompareciente = c.getTipoCompareciente();
		if (tipoCompareciente instanceof HibernateProxy) {
			tipoCompareciente = (TipoCompareciente) ((HibernateProxy) tipoCompareciente)
					.getHibernateLazyInitializer().getImplementation();
		}
		c.setTipoCompareciente(tipoCompareciente);

		contacto = c.getContacto();
		if (contacto instanceof HibernateProxy) {
			contacto = (Contacto) ((HibernateProxy) contacto)
					.getHibernateLazyInitializer().getImplementation();
			contacto.getPersona().setNacionalidad(nacionalidad);
			contacto.getPersona().setTipopersona(tipoPersona);
		}
		if (contacto != null) {
			contacto.getPersona().setNacionalidad(nacionalidad);
			contacto.getPersona().setTipopersona(tipoPersona);
		}

		c.setContacto(contacto);

		Domicilio domicilio = c.getDomicilio();
		if (domicilio instanceof HibernateProxy) {
			domicilio = (Domicilio) ((HibernateProxy) domicilio)
					.getHibernateLazyInitializer().getImplementation();
			c.setDomicilio(domicilio);
		}

		acto = c.getActo();
		if (acto instanceof HibernateProxy) {
			acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer()
					.getImplementation();
			expediente = acto.getExpediente();
			if (expediente instanceof HibernateProxy) {
				expediente = (Expediente) ((HibernateProxy) expediente)
						.getHibernateLazyInitializer().getImplementation();
				tramite = expediente.getTramite();
				if (tramite instanceof HibernateProxy) {
					
					tramite = (Tramite) ((HibernateProxy) tramite)
							.getHibernateLazyInitializer().getImplementation();
					status = tramite.getStatus();
					if (status instanceof HibernateProxy) {
						status = (EtapaTramite) ((HibernateProxy) status)
								.getHibernateLazyInitializer()
								.getImplementation();
					}
					tramite.setStatus(status);
					tramite.setCliente(null);
					tramite.setAbogado(null);
					tramite.setLocacion(null);
				}
				if(tramite.getCliente().getTipopersona() instanceof HibernateProxy){
					tramite.setCliente(null);
				}
				expediente.setTramite(tramite);
				expediente.setAbogado(null);
				//expediente.setListaComentarios(null);
			}
			suboperacion = acto.getSuboperacion();
			if (suboperacion instanceof HibernateProxy) {
				suboperacion = (Suboperacion) ((HibernateProxy) suboperacion)
						.getHibernateLazyInitializer().getImplementation();
			}

			acto.setExpediente(expediente);
			acto.setSuboperacion(suboperacion);
		}
		c.setActo(acto);

		registroRi = c.getRegistroRi();
		if (registroRi instanceof HibernateProxy) {
			registroRi = (RegistroRi) ((HibernateProxy) registroRi)
					.getHibernateLazyInitializer().getImplementation();
			expedidopor = registroRi.getExpedidopor();
			if (expedidopor instanceof HibernateProxy) {
				expedidopor = (ElementoCatalogo) ((HibernateProxy) expedidopor)
						.getHibernateLazyInitializer().getImplementation();
				expedidopor.setCatalogo(null);
			}
			registroRi.setExpedidopor(expedidopor);
			tipo = registroRi.getTipo();
			if (tipo instanceof HibernateProxy) {
				tipo = (ElementoCatalogo) ((HibernateProxy) tipo)
						.getHibernateLazyInitializer().getImplementation();
				tipo.setCatalogo(null);
			}
			registroRi.setTipo(tipo);
		}
		c.setRegistroRi(registroRi);
	}

	@Override
	public Boolean registrarCompareciente(Compareciente compareciente,
			List<ComparecienteRepresentante> cr) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;

		try {
			tx = em.getTransaction();
			tx.begin();

			// Registrar el compareciente
			em.persist(compareciente);

			if (cr != null) {
				// Guardar Compareciente-Representante
				for (ComparecienteRepresentante compRep : cr) {
					em.persist(compRep);
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
	public Boolean eliminarCompareciente(Compareciente compareciente)
			throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			// Por restriccion fk, primero eliminar ComparecienteRepresentante
			
			
			query = em
					.createQuery("delete ComparecienteRepresentante where representante.idcompareciente = :idrepresentante");
			query.setParameter("idrepresentante",
					compareciente.getIdcompareciente()).executeUpdate();

			// Por restriccion fk eliminar InvitadoCalendarioCita
			query = em
					.createQuery("delete InvitadoCalendarioCita where compareciente.idcompareciente = :idcompareciente");
			query.setParameter("idcompareciente",
					compareciente.getIdcompareciente()).executeUpdate();

			// ELIMINO CONYUGE
			TypedQuery<ComparecienteConyuge> queryComp = em.createNamedQuery("buscar", ComparecienteConyuge.class);
			queryComp.setParameter("idcompareciente", compareciente.getIdcompareciente());
			
			List<ComparecienteConyuge> Listcompconyuge = queryComp.getResultList();
			if(Listcompconyuge.size()>0){
				ComparecienteConyuge compconyuge= Listcompconyuge.get(0);
				
				// VERIFICO QUE CONYUGE SE QUIERE ELIMINAR
				if(compconyuge.getConyugeActual() !=null && compconyuge.getConyugeActual().getIdcompareciente().equals(compareciente.getIdcompareciente())){
						compconyuge.setConyugeActual(null);
				}else if(compconyuge.getConyugeCompra() !=null && compconyuge.getConyugeCompra().getIdcompareciente().equals(compareciente.getIdcompareciente())){
						if(compconyuge.getSujeto().getAmboscompran()){
							compconyuge.getSujeto().setAmboscompran(false);
						}
						compconyuge.setConyugeCompra(null);
						em.merge(compconyuge.getSujeto());
				}

				//VERIFICO QUE YA NO TENGA NINGUN CONYUGE Y BORRO LA RELACION O ACTUALIZO
				if(compconyuge.getConyugeActual()==null && compconyuge.getConyugeCompra()==null){
					em.remove(compconyuge);
				}else{
					em.merge(compconyuge);
				}
				
				
			}
			

			
			// ELIMINO FIRMA
						
						TypedQuery<Firma> firmars = em.createNamedQuery("Firma.findByCompareciente", Firma.class);
						firmars.setParameter("identificador", compareciente.getIdcompareciente());
						List<Firma> frs = firmars.getResultList();
						if(frs.size()>0){
							Firma firma = frs.get(0);
							firma = em.find(Firma.class, firma.getIdfirma());
							em.remove(firma);
						}
						
						
						
			
			// Por restriccion fk eliminar BitacoraFirla
			query = em
					.createQuery("delete BitacoraFirma where compareciente.idcompareciente = :idcompareciente");
			query.setParameter("idcompareciente",
					compareciente.getIdcompareciente()).executeUpdate();

			// Eliminar de DatoFiscalPago
			query = em
					.createQuery("delete DatoFiscalPago where compareciente.idcompareciente = :idcompareciente");
			query.setParameter("idcompareciente",
					compareciente.getIdcompareciente()).executeUpdate();
			
			// Segundo eliminar compareciente
			em.remove(em.contains(compareciente) ? compareciente : em
					.merge(compareciente));

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
	public Boolean actualizarCompareciente(Compareciente compareciente,
			List<ComparecienteRepresentante> cr) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;

		try {
			tx = em.getTransaction();
			tx.begin();
			/**
			 * Eliminar registros de relacion ComparecienteRepresentante. Se
			 * volveran a agregar para hacer actualizacion.
			 **/
			if (cr.size() > 0) {
				query = em
						.createQuery("delete ComparecienteRepresentante where representante.idcompareciente = :idrepresentante");
				query.setParameter("idrepresentante",
						compareciente.getIdcompareciente()).executeUpdate();
				/**
				 * Actualizar compareciente. nota:compareciente.persona y
				 * persona.domicilio tienen cascade type merge.
				 **/
				em.merge(compareciente);
				/*
				 * if (cr == null) { tx.commit(); return true; } /** Agregar
				 * update registros ComparecienteRepresentante *
				 */
				for (ComparecienteRepresentante compRep : cr) {
					em.persist(compRep);
				}
			} else {
				em.merge(compareciente);
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
	public Boolean actualizarRepresentantes(Compareciente compareciente,
			Compareciente representante, List<ComparecienteRepresentante> cr)
			throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			/**
			 * Eliminar registros de relacion ComparecienteRepresentante. Se
			 * volveran a agregar para hacer actualizacion.
			 **/
			query = em
					.createQuery("delete ComparecienteRepresentante where representado.idcompareciente = :idcompareciente"
							+ " AND representante.idcompareciente = :idrepresentante");
			query.setParameter("idcompareciente",
					compareciente.getIdcompareciente());
			query.setParameter("idrepresentante",
					representante.getIdcompareciente());
			query.executeUpdate();

			StringBuilder sb = new StringBuilder();
			sb.append(" update Compareciente set isrepresentante=:isrepresentante ");
			sb.append(" , idsesion = :idsesion ");
			sb.append(" , tmstmp = :tmstmp");
			sb.append(" where  idcompareciente = :idcompareciente ");
			query = em.createQuery(sb.toString());
			query.setParameter("idcompareciente",
					compareciente.getIdcompareciente());
			query.setParameter("idsesion", compareciente.getIdsesion());
			query.setParameter("tmstmp", compareciente.getTmstmp());

			if (cr == null || cr.isEmpty()) {
				/** Si no tiene representados, poner isrepresentante = false **/
				query.setParameter("isrepresentante", IS_REPRESENTANTE);
				query.executeUpdate();
				tx.commit();
				return true;
			}
			/** si tiene representados, poner isrepresenante = true **/
			query.setParameter("isrepresentante", !IS_REPRESENTANTE);
			query.executeUpdate();
			/** Agregar update registros ComparecienteRepresentante **/
			for (ComparecienteRepresentante compRep : cr) {
				em.persist(compRep);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Compareciente> disponiblesPorActoEscritura(String id)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Compareciente> comparecienteList;

		if (id == null || id.isEmpty()) {
			return null;
		}
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" select c from Compareciente c, EscrituraActo escact  ");
			sql.append(" where  c.acto.idacto = escact.acto.idacto");
			sql.append(" and  escact.escritura.idescritura=:id");
			sql.append(" and c.idcompareciente not in ");
			sql.append(" (select compareciente.idcompareciente from BitacoraFirma where escritura.idescritura = :id) ");
			sql.append(" ORDER BY c.persona.dsnombrecompleto ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);
			comparecienteList = (List<Compareciente>) query.getResultList();
			if (comparecienteList != null) {
				for (Compareciente c : comparecienteList) {
					// inicializar las propiedades lazy comunes.
					this.hibernateLazyInitializer(c);
					c.getActo().getExpediente().setTramite(null);
				}
			}
			return comparecienteList;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Boolean actualizaRegistroRi(String idcompareciente)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			StringBuilder sb = new StringBuilder();
			sb.append(" update Compareciente set registroRi=:registroRi ");
			sb.append(" where  idcompareciente = :idcompareciente ");
			query = em.createQuery(sb.toString());
			query.setParameter("idcompareciente", idcompareciente);
			query.setParameter("registroRi", null);
			query.executeUpdate();
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

	@SuppressWarnings("unchecked")
	public List<Compareciente> obtenerPorExpediente(String idexpediente)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em
					.createQuery("SELECT comp FROM Compareciente comp WHERE comp.acto.expediente.idexpediente=?1");
			query.setParameter(1, idexpediente);
			List<Compareciente> compas = query.getResultList();
			for (Compareciente comp : compas) {
				comp.setRegimen(null);
				comp.setRegistroRi(null);
				// comp.setTipoCompareciente(null);
				comp.setTratamiento(null);
				Persona per = comp.getPersona();
				Acto acto = comp.getActo();
				TipoCompareciente tipo = comp.getTipoCompareciente();
				if (tipo instanceof HibernateProxy) {
					tipo = (TipoCompareciente) ((HibernateProxy) tipo)
							.getHibernateLazyInitializer().getImplementation();
				}
				if (per instanceof HibernateProxy) {
					per = (Persona) ((HibernateProxy) per)
							.getHibernateLazyInitializer().getImplementation();
					per.setNacionalidad(null);
					per.setTipopersona(null);
				}
				if (acto instanceof HibernateProxy) {
					acto = (Acto) ((HibernateProxy) acto)
							.getHibernateLazyInitializer().getImplementation();
					acto.setExpediente(null);
					acto.setSuboperacion(null);
				}
				comp.setPersona(per);
				comp.setTipoCompareciente(tipo);
				comp.setActo(acto);
				if (tipo != null)
					System.out.println("tipo comp " + tipo.getDsnombre());
				System.out.println("id acto " + comp.getActo().getIdacto());
				System.out.println(comp.getPersona().getDsnombre() + " "
						+ comp.getPersona().getDsapellidopat());
			}
			return compas;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Compareciente> getByIdPersona(String idpersona)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em
					.createQuery("SELECT c FROM Compareciente c WHERE c.persona.idpersona = ?1");
			query.setParameter(1, idpersona);
			List<Compareciente> comparecientes = query.getResultList();
			for (Compareciente comp : comparecientes) {
				comp.setRegimen(null);
				comp.setRegistroRi(null);
				// comp.setTipoCompareciente(null);
				comp.setTratamiento(null);
				Persona per = comp.getPersona();
				Acto acto = comp.getActo();
				TipoCompareciente tipo = comp.getTipoCompareciente();
				if (tipo instanceof HibernateProxy) {
					tipo = (TipoCompareciente) ((HibernateProxy) tipo)
							.getHibernateLazyInitializer().getImplementation();
				}
				if (per instanceof HibernateProxy) {
					per = (Persona) ((HibernateProxy) per)
							.getHibernateLazyInitializer().getImplementation();
					per.setNacionalidad(null);
					per.setTipopersona(null);
				}
				if (acto instanceof HibernateProxy) {
					acto = (Acto) ((HibernateProxy) acto)
							.getHibernateLazyInitializer().getImplementation();
					acto.setExpediente(null);
					acto.setSuboperacion(null);
				}
				comp.setPersona(per);
				comp.setTipoCompareciente(tipo);
				comp.setActo(acto);
			}
			return comparecientes;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}

}
