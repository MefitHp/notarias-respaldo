package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ConSubFormularioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;

public class ConSubFormularioDaoImpl extends
		GenericDaoImpl<ConSubFormulario, Integer> implements
		ConSubFormularioDao {

	public ConSubFormularioDaoImpl() {
		super(ConSubFormulario.class);
	}
	
	
	@Override
	public void delete(ConSubFormulario objeto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		try{
			//if(em.find(obj.getClass() , obj)!=null){
				//System.out.println("=====> Object found, it exists in the unit persistence as " + obj.toString());s			
			
			ConSubFormulario objBorrar = em.find(ConSubFormulario.class, objeto.getIdconsubform());
				em.remove(objBorrar);
				em.flush();
			//}else{
				//System.out.println("=====> The object does not exists in the unit persistence "+obj.toString());
			//}
			em.getTransaction().commit();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
//			throw new NotariaException(String.format("Something went wrong in persisting data , view server log for details [delete %s at %s].", obj.toString(), dateFormat.format(new Date())), e.getCause());
			throw new NotariaException("Ocurrió un error al eliminar, causado por: "+e.getCause());
		}finally{
			em.close();
		}
	
		
	};
	
	
	
	@Override
	public String obtenerIdConFormularioByIdSubFormulario(String idSubFormulario)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT consf.conFormulario.id.idconFormulario FROM ConSubFormulario consf WHERE consf.idconsubform = :idSubFormulario ");

			TypedQuery<String> query = em.createQuery(sql.toString(), String.class);
			query.setParameter("idSubFormulario", idSubFormulario);
			
			List<String> resultados = query.getResultList();
			if (resultados.size() == 0) {
				return null;
			}
			// Sabemos que es una concidencia
			return resultados.get(0);
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public String obtenerIdConSubFormByShortName(String shortName) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT consf.idconsubform FROM ConSubFormulario consf WHERE consf.dsnombrecorto = :shortname ORDER BY versionform DESC");

			Query query = em.createQuery(sql.toString());
			query.setParameter("shortname", shortName);

			@SuppressWarnings("unchecked")
			List<String> resultados = query.getResultList();
			if (resultados.size() == 0) {
				return null;
			}
			// Sabemos que es una concidencia
			return resultados.get(0);

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	// Victor Obtengo el subformulario por nombrecorto y por localidad
	@Override
	public List<ConSubFormulario> obtenerConSubFormsByShortName(String shortName) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT consf FROM ConSubFormulario consf WHERE consf.dsnombrecorto = :shortname  ORDER BY versionform DESC");

			Query query = em.createQuery(sql.toString());
			query.setParameter("shortname", shortName);

			@SuppressWarnings("unchecked")
			List<ConSubFormulario> resultados = query.getResultList();
			if (resultados.size() == 0) {
				return null;
			}
			
			for(ConSubFormulario csf:resultados){
				ConFormulario conFormulario = csf.getConFormulario();
				if (conFormulario instanceof HibernateProxy) {
					conFormulario = (ConFormulario) ((HibernateProxy) conFormulario)
							.getHibernateLazyInitializer().getImplementation();
					conFormulario.setListaActoFormularios(null);
					conFormulario.setListaComponentes(null);
					conFormulario.setListaPermisosRol(null);
					csf.setConFormulario(conFormulario);
				}
			}
			
			
			
			// Sabemos que es una concidencia
			return resultados;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	

	@Override
	public List<ConSubFormulario> findByIdConFormulario(ConFormularioPK idConFormulario)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<ConSubFormulario> query = em.createNamedQuery("ConSubFormulario.findByIdConFormulario", ConSubFormulario.class);
			query.setParameter("idconformulario", idConFormulario.getIdconFormulario());
			query.setParameter("version", idConFormulario.getVersion());
			List<ConSubFormulario> subForms = query.getResultList();
			if(subForms!=null && subForms.size()>0){
				return subForms;
			} else {
				return new ArrayList<ConSubFormulario>();				
			}
		}catch(PersistenceException e){
			e.printStackTrace();
			throw new NotariaException("No se ha logrado listar los subformularios del Formulario. Revise el log de operaciones para más detalle.");
		}finally {
			em.close();
		}
	}

	@Override
	public ConSubFormulario obtenerPorPosicion(Integer posicion,
			ConFormularioPK conFormulario) throws NotariaException {
		
		EntityManager em = factory.createEntityManager();
		try{
			em = factory.createEntityManager();
			TypedQuery<ConSubFormulario> query = em.createNamedQuery("ConSubFormulario.findByPosicion", ConSubFormulario.class);
			query.setParameter("idconformulario", conFormulario.getIdconFormulario());
			query.setParameter("version", conFormulario.getVersion());
			query.setParameter("inposicion", posicion);
			
			List<ConSubFormulario> subForms = query.getResultList();
			if(subForms!=null && subForms.size()>0){
				return subForms.get(0);
			} else {
				return null;				
			}
		}catch(PersistenceException e){
			e.printStackTrace();
			throw new NotariaException("No se ha logrado encontrar el subformulario por posición.");
		}finally {
			em.close();
		}
	
	}


	@Override
	public List<ConSubFormulario> findByFormulario(ConFormularioPK formularioId)throws NotariaException  {
		
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<ConSubFormulario> query = em.createNamedQuery("ConSubFormulario.findByFormulario", ConSubFormulario.class);
			query.setParameter("idconformulario", formularioId.getIdconFormulario());
			query.setParameter("version", formularioId.getVersion());
			
			List<ConSubFormulario> subForms = query.getResultList();
			return subForms;

		}catch(PersistenceException e){
			e.printStackTrace();
			throw new NotariaException("No se ha logrado encontrar el subformulario por posición.");
		}finally {
			em.close();
		}
		
	}


	

}
