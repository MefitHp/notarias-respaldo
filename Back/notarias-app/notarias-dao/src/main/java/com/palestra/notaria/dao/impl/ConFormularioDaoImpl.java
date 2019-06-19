package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ActoDao;
import com.palestra.notaria.dao.ConFormularioDao;
import com.palestra.notaria.dao.SuboperacionDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoFormulario;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Expresion;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.PermisoRol;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.modelo.Tramite;

public class ConFormularioDaoImpl extends
		GenericDaoImpl<ConFormulario, Integer> implements ConFormularioDao {

	public ConFormularioDaoImpl() {
		super(ConFormulario.class);
	}

	@Override
	public ConFormulario buscarFormularioCompleto(ConFormularioPK id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		ConFormulario form = null;
		try {
			form = em.find(ConFormulario.class, id);
			Suboperacion suboperacion;
			Rol rol;
			// listaActoFormularios =form.getListaActoFormularios();
			for (ActoFormulario af : form.getListaActoFormularios()) {
				af.setConFormulario(null);
				suboperacion = af.getSuboperacion();
				if (suboperacion instanceof HibernateProxy) {
					suboperacion = (Suboperacion) ((HibernateProxy) suboperacion).getHibernateLazyInitializer().getImplementation();
					suboperacion.setOperacion(null);
				}
				af.setSuboperacion(suboperacion);
			}
			for (PermisoRol pr : form.getListaPermisosRol()) {
				pr.setConFormulario(null);
				rol = pr.getRol();
				if (rol instanceof HibernateProxy) {
					rol = (Rol) ((HibernateProxy) rol).getHibernateLazyInitializer().getImplementation();
				}
				pr.setRol(rol);
			}

			for (Componente c : form.getListaComponentes()) {
				c.setConFormulario(null);
				c.setSubformulario(null);
				c.getTipocomponente().setCatalogo(null);
//				@omarete: se setea la expresion al componente
				TypedQuery<Expresion> query = em.createQuery("SELECT e FROM Expresion e WHERE e.variable.componente.idcomponente = ?1",Expresion.class);
				query.setParameter(1, c.getIdcomponente());
				Expresion expresion;
				try{
					List<Expresion> explist = query.getResultList();
					if(explist.size()>0){
						expresion = explist.get(0);
					}else{
						expresion = null;
					}
				}catch(NoResultException e){
					//System.out.println("catch manejado");
					expresion = null;
				}
				c.setExpresion(expresion);
				if(c.getExpresion()!=null){
					c.getExpresion().setVariable(null);
				}
//				@omarete: FIN ------------------------------------
				/*
				 * cfa 040614 --> esto era por una falacia Victorina, pues
				 * resulta que no me estaba enviando el inposicion en el
				 * subformulario ya lo manda desde el Front al momento de
				 * perisistir, por ende no es necesario buscarlo
				 * if(c.getInposicion()!=null){ //es un subformulario String
				 * idsubform = c.getSubformulario().getIdconsubform();
				 * for(ConSubFormulario csf:form.getListaSubFormularios()){
				 * if(csf.getIdconsubform().equals(idsubform)){
				 * csf.setInposicion(c.getInposicion()); } } } cfa 040614 <--
				 */
				
			}			
			
			Map<Integer, ConSubFormulario> subComps = new TreeMap<>();
			for (ConSubFormulario csf : form.getListaSubFormularios()) {
				if (csf instanceof HibernateProxy) {
					csf = (ConSubFormulario) ((HibernateProxy) csf).getHibernateLazyInitializer().getImplementation();
				}				
				csf.setConFormulario(null);
				// System.out.println("Subformulario " + csf.getIdconsubform() + " tienen " + csf.getListaComponentes().size() + " componentes");
				for (Componente c : csf.getListaComponentes()) {
					c.setConFormulario(null);
					c.setSubformulario(null);
					c.getTipocomponente().setCatalogo(null);
//					@omarete se setea la expresion para el componente de subformulario
					ExpresionDaoImpl expresionDao = new ExpresionDaoImpl();
					Expresion exp = expresionDao.getExpresionByIdComp(c.getIdcomponente());
					if(exp != null)
						exp.setVariable(null);
					c.setExpresion(exp);
//					@omarete FIN
				}
				// csf.setListaComponentes(listaComponentes);
				/*
				 * cfa: 030614 --> esto es una falacia "taburetina", es decir
				 * que funciona una vez sí, y otras 99 no, es decir una relación
				 * 1 a 99 listaComponentes = csf.getListaComponentes();
				 */
				//System.out.printf("key %d con %d componentes%n", csf.getInposicion(), csf.getListaComponentes().size());
				subComps.put(csf.getInposicion(), csf);
			}
			// System.out.println("subComps: " + subComps.size());
			form.getListaSubFormularios().clear();			
			for (Integer key : subComps.keySet()) {
				 System.out.printf("key %d con %d componentes%n", key, subComps.get(key).getListaComponentes().size());
				form.getListaSubFormularios().add(subComps.get(key));
			}
			// <--
			// System.out.printf("subformularios = %d%n", form.getListaSubFormularios().size());
			// form.setListaSubFormularios(listaSubFormularios);
			// System.out.println("acto " + form.getListaActoFormularios().size() + " pemisos " + form.getListaPermisosRol().size());
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return form;
	}

	@Override
	public Integer buscarUltimaVersion(String idconformulario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Integer version = null;
		TypedQuery<Integer> query;
		try {
			query = em
					.createQuery(
							"SELECT MAX(id.version) FROM ConFormulario WHERE id.idconFormulario = :idconformulario",
							Integer.class);
			query.setParameter("idconformulario", idconformulario);
			version = query.getSingleResult();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

		return version;
	}
	
	@Override
	public ConFormulario findByName(String nombrecorto) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		Query query;
		try{
			query = em.createQuery("SELECT c FROM ConFormulario c WHERE c.dsnombrecorto = ?1 AND c.id.version =" +
					"(SELECT MAX(c.id.version) FROM ConFormulario WHERE c.dsnombrecorto = ?1)");
			query.setParameter(1, nombrecorto);
			query.setMaxResults(1);
			return (ConFormulario)query.getSingleResult();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@Override
	public boolean publicarFormulario(String idformulario, Integer version) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean b = false;
		Query query;
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			/***
			 * CFA 130514. Al parecer este código afecta más al performance que
			 * el que se esta dejando en sustitución query = em.createQuery(
			 * "FROM ConFormulario WHERE id.idconFormulario = :idconFormulario AND ispublicado = :ispublicado"
			 * ); query.setParameter("ispublicado", true);
			 * query.setParameter("idconFormulario", idformulario);
			 * List<ConFormulario> lista = query.getResultList(); if(lista!=null
			 * && !lista.isEmpty()){ for(ConFormulario f:lista){ query =
			 * em.createQuery(
			 * "update ConFormulario set ispublicado = :ispublicado, fechapublicacion = :fechapublicacion where id.idconFormulario = :idform and id.version = :version"
			 * ); query.setParameter("ispublicado", false);
			 * query.setParameter("idform", f.getId().getIdconFormulario());
			 * query.setParameter("version", f.getId().getVersion());
			 * query.setParameter("fechapublicacion", null); int result =
			 * query.executeUpdate(); } }
			 ***/
			// --> CFA 130514. optimización del código
			query = em
					.createQuery("UPDATE ConFormulario SET "
							+ "ispublicado = :ispublicado WHERE id.idconFormulario = :idformulario AND ispublicado = :publicado");
			query.setParameter("ispublicado", false);
			query.setParameter("idformulario", idformulario);
			query.setParameter("publicado", true);
			query.executeUpdate();
			// <-- CFA
			query = em
					.createQuery("UPDATE ConFormulario SET "
							+ "ispublicado = :ispublicado, fechapublicacion = :fechapublicacion WHERE id.idconFormulario = :idform and id.version = :version");
			query.setParameter("ispublicado", true);
			query.setParameter("idform", idformulario);
			query.setParameter("version", version);
			query.setParameter("fechapublicacion", new Date(),
					TemporalType.DATE);

			int result = query.executeUpdate();
			tx.commit();
			b = (result > 0);
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean elimninarFormulario(String idformulario, Integer version) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean b = false;
		Query query;
		EntityTransaction tx = em.getTransaction();
		try {

			tx.begin();
			List<ActoFormulario> listaActoFormularios;
			List<PermisoRol> listaPermisosRol;
			List<ConSubFormulario> listaSubFormularios;
			List<Componente> listaComponentes;
			ConFormularioPK id = new ConFormularioPK();
			id.setIdconFormulario(idformulario);
			id.setVersion(version);
			ConFormulario formulario = new ConFormulario();
			formulario.setId(id);

			query = em
					.createQuery("from ActoFormulario where conFormulario.id = :id");
			query.setParameter("id", id);
			// query.setParameter("idconFormulario", idformulario);
			listaActoFormularios = query.getResultList();
			if (listaActoFormularios != null && !listaActoFormularios.isEmpty()) {
				for (ActoFormulario af : listaActoFormularios) {
					em.remove(em.contains(af) ? af : em.merge(af));
				}
			}

			query = em
					.createQuery("from PermisoRol where conFormulario.id = :id");
			query.setParameter("id", id);
			listaPermisosRol = query.getResultList();
			if (listaPermisosRol != null && !listaPermisosRol.isEmpty()) {
				for (PermisoRol aux : listaPermisosRol) {
					em.remove(em.contains(aux) ? aux : em.merge(aux));
				}
			}

			query = em
					.createQuery("from Componente where conFormulario.id = :id");
			query.setParameter("id", id);
			listaComponentes = query.getResultList();
			if (listaComponentes != null && !listaComponentes.isEmpty()) {
				for (Componente aux : listaComponentes) {
					em.remove(em.contains(aux) ? aux : em.merge(aux));
				}
			}

			query = em
					.createQuery("from ConSubFormulario where conFormulario.id = :id");
			query.setParameter("id", id);
			listaSubFormularios = query.getResultList();
			if (listaSubFormularios != null && !listaSubFormularios.isEmpty()) {
				for (ConSubFormulario aux : listaSubFormularios) {

					query = em
							.createQuery("from Componente where subformulario.idconsubform = :idsubFormulario ");
					query.setParameter("idsubFormulario", aux.getIdconsubform());

					listaComponentes = query.getResultList();
					if (listaComponentes != null && !listaComponentes.isEmpty()) {
						for (Componente aux2 : listaComponentes) {
							em.remove(em.contains(aux2) ? aux2 : em.merge(aux2));
						}
					}

					em.remove(em.contains(aux) ? aux : em.merge(aux));
				}
			}
			em.remove(em.contains(formulario) ? formulario : em
					.merge(formulario));

			tx.commit();
			// if(result > 0)
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
	public List<ActoFormulario> formulariosPorExpediente(String idexpediente,
			String idacto) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		TypedQuery<ActoFormulario> query;
		List<ActoFormulario> lista;
		Suboperacion suboperacion;
		ConFormulario formulario;
		try {
			Acto acto = null;
			if (idacto != null) {
				acto = em.find(Acto.class, idacto);
				suboperacion = acto.getSuboperacion();
				if (suboperacion instanceof HibernateProxy) {
					suboperacion = (Suboperacion) ((HibernateProxy) suboperacion)
							.getHibernateLazyInitializer().getImplementation();
					suboperacion.setOperacion(null);
				}
				acto.setSuboperacion(suboperacion);
				Expediente expediente = acto.getExpediente();
				if(expediente instanceof HibernateProxy){
					expediente = (Expediente)((HibernateProxy)expediente).getHibernateLazyInitializer().getImplementation();
					acto.setExpediente(expediente);
					Tramite tramite = expediente.getTramite();
					if(tramite instanceof HibernateProxy){
						tramite = (Tramite)((HibernateProxy)tramite).getHibernateLazyInitializer().getImplementation();
						acto.getExpediente().setTramite(tramite);
						ElementoCatalogo locacion = tramite.getLocacion();
						if(locacion instanceof HibernateProxy){
							locacion = (ElementoCatalogo)((HibernateProxy)locacion).getHibernateLazyInitializer().getImplementation();
							acto.getExpediente().getTramite().setLocacion(locacion);
						}
					}
				}
			}

			if (acto != null) {
				query = em
						.createQuery(
								"FROM ActoFormulario WHERE suboperacion.idsuboperacion = :idSubOperacion "
								+ "AND conFormulario.ispublicado = :ispublicado AND conFormulario.locacion.idelemento = :locacion "
										+ "AND conFormulario.id.idconFormulario NOT IN (SELECT form.conFormulario.id.idconFormulario FROM Formulario form WHERE form.acto.idacto = :idacto)"
										+ "ORDER BY inposicion",
								ActoFormulario.class);
				query.setParameter("ispublicado", new Boolean(true));
				query.setParameter("idSubOperacion", acto.getSuboperacion().getIdsuboperacion());
				query.setParameter("idacto", acto.getIdacto());
				query.setParameter("locacion", acto.getExpediente().getTramite().getLocacion().getIdelemento());
			} else {
				query = em
						.createQuery(
								"FROM ActoFormulario WHERE suboperacion.idsuboperacion IN (SELECT suboperacion.idsuboperacion FROM Acto WHERE expediente.idexpediente = :idexpediente) "
										+ "AND conFormulario.ispublicado = :ispublicado "
										+ "AND conFormulario.id.idconFormulario NOT IN (SELECT f.conFormulario.id.idconFormulario FROM Formulario f WHERE acto.expediente.idexpediente = :idexpediente)"
										+ "ORDER BY inposicion",
								ActoFormulario.class);
				query.setParameter("ispublicado", new Boolean(true));
				query.setParameter("idexpediente", idexpediente);
				// query =
				// em.createQuery("FROM ActoFormulario WHERE suboperacion.idsuboperacion IN "
				// +
				// "(SELECT suboperacion.idsuboperacion FROM Acto WHERE expediente.idexpediente = :idexpediente) "
				// +
				// " AND conFormulario.ispublicado = :ispublicado ORDER BY inposicion",
				// ActoFormulario.class);
				// query.setParameter("idexpediente", idexpediente);
				// query.setParameter("ispublicado", new Boolean(true));
			}

			lista = query.getResultList();

			TypedQuery<ActoFormulario> qFormularios = em
					.createQuery(
							"SELECT af FROM ActoFormulario af WHERE "
									+ "af.conFormulario.id IN (SELECT f.conFormulario.id FROM Formulario f WHERE f.acto.expediente.idexpediente = :idexpediente AND f.acto.idacto = :idacto ) "									
									+ "AND af.suboperacion.idsuboperacion IN (SELECT suboperacion.idsuboperacion FROM Acto WHERE expediente.idexpediente = :idexpediente)",
							ActoFormulario.class);
			qFormularios.setParameter("idexpediente", idexpediente);
			qFormularios.setParameter("idacto", idacto);
			List<ActoFormulario> formularios = qFormularios.getResultList();
			if (formularios != null && formularios.size() > 0) {
				// System.out.println("Hay " + formularios.size() + " formularios.");
//				for (ActoFormulario form : formularios) {
//					System.out.printf("=====> Formulario=%s    id=%s    version=%d%n",
//							form.getConFormulario().getDsnombrecorto(), form
//									.getConFormulario().getId()
//									.getIdconFormulario(), form
//									.getConFormulario().getId().getVersion());
//				}
			}

			lista.addAll(formularios);
			if (lista != null) {
				for (ActoFormulario af : lista) {
					suboperacion = af.getSuboperacion();
					if (suboperacion instanceof HibernateProxy) {
						suboperacion = (Suboperacion) ((HibernateProxy) suboperacion)
								.getHibernateLazyInitializer()
								.getImplementation();
						suboperacion.setOperacion(null);
					}
					af.setSuboperacion(suboperacion);
					formulario = af.getConFormulario();
					if (formulario instanceof HibernateProxy) {
						formulario = (ConFormulario) ((HibernateProxy) formulario)
								.getHibernateLazyInitializer()
								.getImplementation();
						formulario.setListaActoFormularios(null);
						formulario.setListaComponentes(null);
						formulario.setListaPermisosRol(null);
						formulario.setListaSubFormularios(null);						
					}
					af.setConFormulario(formulario);										
					TypedQuery<Formulario> form = em.createQuery("SELECT f FROM Formulario f WHERE f.conFormulario.id = :configuracion AND f.acto.expediente.idexpediente = :idexpediente", Formulario.class);
					form.setParameter("configuracion", formulario.getId());
					form.setParameter("idexpediente", idexpediente);
					List<Formulario> forms = form.getResultList();
					if (forms.size() > 0) {
						af.setInestatus("F");
					} else {
						af.setInestatus("E");
					}
					System.out.println("=====> **** Formulario: "
							+ af.getConFormulario().getDsnombrecorto() + "-"
							+ af.getInposicion() + "[" + af.getInestatus()
							+ "]");
				}
			}
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

		
		return lista;
	}

	@Override
	public List<ActoFormulario> formulariosPorExpediente(String idexpediente,
			String idacto, String tipoForm) throws NotariaException {
		List<ActoFormulario> forms = formulariosPorExpediente(idexpediente,
				idacto);
		
		ActoDao acdato = new ActoDaoImpl();
		
		
		//VICTOR: TUVE QUE OBTENER LA SUBOPERACIÓN PARA EVITAR LA DUPLICIDAD EN EL LISTADO DE FORMULARIOS
		Acto acto = acdato.buscarPorIdCompleto(idacto);
		StringBuilder idSubForm = new StringBuilder(acto.getSuboperacion().getIdsuboperacion());
		
		//System.out.println("LISTA:*********************** " + forms);
		List<ActoFormulario> newList = new ArrayList<ActoFormulario>();
		for (ActoFormulario form : forms) {
			if (form.getConFormulario().getTipoform()
					.equals(tipoForm.toUpperCase())
					|| form.getConFormulario().getTipoform().trim().equals("")) {
				if(form.getSuboperacion().getIdsuboperacion().equals(idSubForm.toString())){
					newList.add(form);
				}
				
			}
		}
		
		
		
		return newList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> obtieneListado(String consulta) throws NotariaException {
		List<String> vuelta = new ArrayList<>();
		EntityManager em = factory.createEntityManager();
		Query query;
		try {
			query = em.createQuery(consulta);
			vuelta = query.getResultList();
			return vuelta;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e);
		} finally {
			em.close();
		}
	}

	@Override
	public List<ConFormulario> listarPorActo(String idacto)
			throws NotariaException {
		List<ConFormulario> vuelta = executeQuery(
				"SELECT cf FROM ActoFormulario af INNER JOIN af.conFormulario cf WHERE "
						+ "af.suboperacion.idsuboperacion = ?1 AND cf.ispublicado = 1 ORDER BY af.conFormulario.locacion.dselemento, af.conFormulario.dstitulo",
				idacto);
		try{
			for (ConFormulario cf : vuelta) {
				List<Componente> componentes = cf.getListaComponentes();
				componentes = null;
				cf.setListaComponentes(componentes);
				List<ConSubFormulario> subformularios = cf.getListaSubFormularios();
				subformularios = null;
				cf.setListaSubFormularios(subformularios);
				List<ActoFormulario> actosFormulario = cf.getListaActoFormularios();
				actosFormulario = null;
				cf.setListaActoFormularios(actosFormulario);
				List<PermisoRol> listaPermisos = cf.getListaPermisosRol();
				listaPermisos = null;
				cf.setListaPermisosRol(listaPermisos);
	
			}
	
			return vuelta;
		}catch (Exception e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}
	}
	
	@Override
	public Map<String, String> recuperaListaPredeterminada(String hql, String acto)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			if(hql.contains("{:idacto}")){
				hql = hql.replace("{:idacto}", ":idacto");
			}
			TypedQuery<Object[]> query = em.createQuery(hql, Object[].class);
			//System.out.println("=====> Parameter-name::: "+query.getParameter(1).getName());
			if(hql.contains(":idacto")){
				query.setParameter("idacto", acto);
			}
			List<Object[]> lista = query.getResultList();
			if(lista!=null && lista.size()>0){
				Map<String, String> vuelta = new TreeMap<String, String>();
				Iterator<Object[]> iterador = lista.iterator();
				while(iterador.hasNext()){
					Object[] obj = iterador.next();
					if(obj.length!=2){
						throw new NotariaException(String.format("=====> recuperaListaPredeterminada: la longitud del ql localizado no coincide con la estructura campo:valor [%d]", obj.length)); 
					}else{
						//System.out.println(obj[0]+"::"+obj[1]);
						vuelta.put((String)obj[0], (String)obj[1]);
					}
				}
				return vuelta;
			}else{
				return null;
			}
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(String.format("Ocurrio una excepcion al ejecutar la consulta predeterminada: %s.%n", e.getMessage()),e);
		}finally {
			em.close();
		}
		
	}

	//funciones para manejo de ActoFormulario:
	@Override
	public List<ActoFormulario> findAllActoFormulario(ConFormularioPK idConFormulario) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<ActoFormulario> sentencia = em.createNamedQuery("ActoFormulario.findAll", ActoFormulario.class);
			sentencia.setParameter("idconformulario", idConFormulario.getIdconFormulario());
			sentencia.setParameter("version", idConFormulario.getVersion());
			List<ActoFormulario> listado = sentencia.getResultList();
			if(listado!=null && listado.size()>0){
				return listado;
			} else {
				return new ArrayList<ActoFormulario>();
			}
		}catch(PersistenceException e){
			throw new NotariaException("No se ha logrado obtener el listado de actos-formulario. Revise el log de operaciones para mayor información.",e);
		} finally {
			em.close();
		}
	}
	
	@Override
	public ActoFormulario findActoFormularioById(String identificador) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<ActoFormulario> sentencia = em.createNamedQuery("ActoFormulario.findById", ActoFormulario.class);
			sentencia.setParameter("identificador", identificador);
			List<ActoFormulario> listado = sentencia.getResultList();
			if(listado!=null && listado.size()>0){
				return listado.get(0);
			} else {
				return null;
			}
		}catch(PersistenceException e){
			throw new NotariaException("No se ha logrado obtener el listado de actos-formulario. Revise el log de operaciones para mayor información.",e);
		} finally {
			em.close();
		}
	}
	
	@Override
	public int removeAllActoFormulario(ConFormularioPK idConFormulario) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			
			TypedQuery<ActoFormulario> sentencia = em.createNamedQuery("ActoFormulario.findAll", ActoFormulario.class);
			sentencia.setParameter("idconformulario", idConFormulario.getIdconFormulario());
			sentencia.setParameter("version", idConFormulario.getVersion());
			List<ActoFormulario> listado = sentencia.getResultList();
			int afectados = 0;
			for(ActoFormulario af:listado){
				em.getTransaction().begin();
				em.remove(af);
				em.getTransaction().commit();
				afectados++;
			}
			return afectados;		
		} catch(PersistenceException e){
			throw new NotariaException("No se ha logrado eliminar los actos-formulario. Revise el log de operaciones para mayor información.",e);
		} finally {
			em.close();
		}
	}
	
	@Override
	public int removeActoFormularioById(String identificador) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			
			TypedQuery<ActoFormulario> sentencia = em.createNamedQuery("ActoFormulario.findById", ActoFormulario.class);
			sentencia.setParameter("identificador", identificador);
			List<ActoFormulario> listado = sentencia.getResultList();
			int afectados = 0;
			if(listado!=null && listado.size()>0){				
				em.getTransaction().begin();
				em.remove(listado.get(0));
				em.getTransaction().commit();
				afectados++;
			}
			return afectados;
		} catch(PersistenceException e){
			throw new NotariaException("No se ha logrado eliminar el actos-formulario. Revise el log de operaciones para mayor información.",e);
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<ConFormulario> localizaPorNombreCorto(String nombreCorto)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<ConFormulario> sentencia = em.createNamedQuery("ConFormulario.findByNombreCorto", ConFormulario.class);
			sentencia.setParameter("nombrecorto", nombreCorto);
			List<ConFormulario> formularios = sentencia.getResultList();
			if(formularios!=null && formularios.size()>0){				
				return formularios;
			} else {
				return new ArrayList<ConFormulario>();
			}
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException("No se logrado obtener el Formulario a través del nombre corto. Revise el log de operaciones para mayos información.",e);
		}finally {
			em.close();
		}	
	}
	
	@Override
	public List<ConFormulario> localizaDuplicado(String nombreCorto, ElementoCatalogo locacion)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<ConFormulario> sentencia = em.createNamedQuery("ConFormulario.findDuplicate", ConFormulario.class);
			sentencia.setParameter("nombrecorto", nombreCorto);
			sentencia.setParameter("idlocacion", locacion.getIdelemento());
			List<ConFormulario> formularios = sentencia.getResultList();
			if(formularios!=null && formularios.size()>0){				
				return formularios;
			} else {
				return new ArrayList<ConFormulario>();
			}
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException("No se logrado obtener el Formulario a través del nombre corto. Revise el log de operaciones para mayos información.",e);
		}finally {
			em.close();
		}		
	}
	@Override
	public ActoFormulario saveActoFormulario(ActoFormulario actoFormulario)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			em.getTransaction().begin();
			em.persist(actoFormulario);
			em.getTransaction().commit();
			em.refresh(actoFormulario);
			return actoFormulario;
		} catch(RollbackException e){
			em.getTransaction().rollback();
			throw new NotariaException("Algo ocurrió al persistir el elemento de ActoFormulario. Revise el log de operaciones para más información.", e);
		} catch(PersistenceException e){
			em.getTransaction().rollback();
			throw new NotariaException("Algo ocurrió al persistir el elemento de ActoFormulario. Revise el log de operaciones para más información.", e);
		}finally {
			em.close();
		}
		
	}
	
}
