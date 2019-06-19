package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ComponenteDao;
import com.palestra.notaria.dato.DatoVariableFormulario;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConSubFormulario;

public class ComponenteDaoImpl extends GenericDaoImpl<Componente, Integer>
		implements ComponenteDao {

	public ComponenteDaoImpl() {
		super(Componente.class);
	}

	@Override
	public Componente buscarPorNombreCortoConForm(String nombreCorto,
			String nombreComponente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		try {
			StringBuilder sb = new StringBuilder();
//			sb.append("select compo from Componente compo ");
//			sb.append(" where compo.conFormulario.dsnombrecorto = '"
//					+ nombreCorto + "'");
//			sb.append(" and compo.conFormulario.ispublicado=1");
//			sb.append(" and compo.dsnombrevariable = '" + nombreComponente
//					+ "'");
//			System.out.println("SQL ******** "+sb.toString());
//			query = em.createQuery(sb.toString());
			sb.append("SELECT c FROM Componente c WHERE ");
			sb.append(" c.dsnombrevariable = ?1");
			sb.append(" AND c.conFormulario.dsnombrecorto = ?2");
			sb.append(" AND c.conFormulario.ispublicado=true");
			
			System.out.println("SQL ******** "+sb.toString());
			query = em.createQuery(sb.toString());
			query.setParameter(1, nombreComponente);
			query.setParameter(2, nombreCorto);

			@SuppressWarnings("unchecked")
			List<Componente> resultados = query.getResultList();
			if (resultados.size() == 0) {
				System.out.println("entra if de size 0");
				return null;
			}
			// Sabemos que es una concidencia
			Componente componente = resultados.get(0);
			System.out.println("componente found "+componente.getDsnombrevariable());
			ConFormulario conFormulario = null;
			if (componente != null) {
//				System.out.println("nombre componente "+componente.getDsnombrevariable());
				conFormulario = componente.getConFormulario();
				if (conFormulario instanceof HibernateProxy) {
					conFormulario = (ConFormulario) ((HibernateProxy) conFormulario)
							.getHibernateLazyInitializer().getImplementation();
				}
				componente.setConFormulario(conFormulario);
				
//				@omarete: se setea la expresion al componente si es que tiene
//				query = em.createQuery("SELECT e FROM Expresion e WHERE e.variable.componente.idcomponente = ?1",Expresion.class);
//				query.setParameter(1, componente.getIdcomponente());
//				Expresion exp = (Expresion)query.getSingleResult();
//				
//				componente.setExpresion(exp);
			}

			return componente;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<Componente> listarPorSuboperacion(String idsuboperacion)
			throws NotariaException {
		// return
		// executeQuery("SELECT comp FROM Componente comp WHERE comp.conFormulario IN "
		// +
		// "(SELECT cf FROM ActoFormulario af INNER JOIN af.conFormulario cf WHERE "
		// +
		// "af.suboperacion.idsuboperacion = ?1)", idsuboperacion);
		//
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery(
					"SELECT comp FROM Componente comp WHERE comp.conFormulario IN "
							+ "(SELECT af.conFormulario FROM ActoFormulario af WHERE af.suboperacion.idsuboperacion = ?1)");
			query.setParameter(1, idsuboperacion);
			@SuppressWarnings("unchecked")
			List<Componente> componentes = query.getResultList();
	//		@omarete: se setea expresion para el componente si es que tiene
			for(Componente comp:componentes){
				ConFormulario conFormulario = comp.getConFormulario();
				if (conFormulario instanceof HibernateProxy) {
					conFormulario = (ConFormulario) ((HibernateProxy) conFormulario)
							.getHibernateLazyInitializer().getImplementation();
					conFormulario.setListaActoFormularios(null);
					conFormulario.setListaComponentes(null);
					conFormulario.setListaPermisosRol(null);
					
				}
				comp.setConFormulario(conFormulario);
	//			query = em.createQuery("SELECT e FROM Expresion e WHERE e.variable.componente.idcomponente = ?1",Expresion.class);
	//			query.setParameter(1, comp.getIdcomponente());
	//			Expresion expresion;
	//			try{
	//				expresion = (Expresion)query.getSingleResult();
	//			}catch(NoResultException e){
	//				e.printStackTrace();
	//				expresion = null;
	//			}
	//			comp.setExpresion(expresion);
	//			if(comp.getExpresion()!=null){
	//				comp.getExpresion().setVariable(null);
	//			}
			}
	
			return componentes;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoVariableFormulario> listarVariableComponente() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT c FROM Componente c");
			Query query = em.createQuery(sql.toString());
			List<Componente> lista = query.getResultList();
			if (lista == null || lista.isEmpty()) {
				return null;
			}
			List<DatoVariableFormulario> variablesCompo = new ArrayList<DatoVariableFormulario>();
			for (Componente compo : lista) {
				DatoVariableFormulario dvf = new DatoVariableFormulario();
				if (compo != null && compo.getConFormulario() != null
						&& compo.getConFormulario().getDsnombrecorto() != null
						&& compo.getDsnombrevariable() != null) {
//					@omarete: se agrega nombre completo del formulario y version para el listado
					dvf.setNombreFormulario(compo.getConFormulario().getDstitulo());
					dvf.setVersionFormulario(compo.getConFormulario().getId().getVersion());
//					@omarete FIN -------------------------------------------------------
					dvf.setDesc(compo.getDsayuda());
					dvf.setNombre(compo.getConFormulario().getDsnombrecorto()
							+ "[" + compo.getDsnombrevariable() + "]");
					dvf.setIdentificador(compo.getIdcomponente());
					variablesCompo.add(dvf);
				}
			}
			
			return variablesCompo;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DatoVariableFormulario> listarComponentesSubformulario() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em.createQuery("SELECT DISTINCT c FROM Componente c");
			List<Componente> componentesList = query.getResultList();
			if(componentesList == null || componentesList.isEmpty()){
				return null;
			}
			List<DatoVariableFormulario> componentesSubform = new ArrayList<DatoVariableFormulario>();
			for(Componente compo:componentesList){
				DatoVariableFormulario datoForm = new DatoVariableFormulario();
				if(compo.getSubformulario()!=null){
					datoForm.setNombreFormulario(compo.getSubformulario().getDsnombrecorto());
					datoForm.setDesc(compo.getDsayuda());
					datoForm.setNombre(compo.getSubformulario().getDsnombrecorto()+"["+compo.getDsnombrevariable()+"]");
					datoForm.setIdentificador(compo.getIdcomponente());
					componentesSubform.add(datoForm);
				}
			}
			return componentesSubform;
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public void delete(Componente componente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("DELETE FROM Componente c WHERE c.idcomponente = ?1");
			query.setParameter(1, componente.getIdcomponente());
			query.executeUpdate();
			em.getTransaction().commit();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@Override
	public List<Componente> obtenerComponenteXSubformulario(ConSubFormulario objeto)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Componente> query = em.createQuery("SELECT comp FROM Componente comp WHERE comp.subformulario = :idconsubfrm",Componente.class);
			query.setParameter("idconsubfrm", objeto);
			List<Componente> componentesList = query.getResultList();
			return componentesList;

		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
		
	}

}
