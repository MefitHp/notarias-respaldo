package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.PermisoRolDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.PermisoRol;

public class PermisoRolDaoImpl extends GenericDaoImpl<PermisoRol, Integer> implements PermisoRolDao{

	public PermisoRolDaoImpl() {
		super(PermisoRol.class);
	}

	@Override
	public List<PermisoRol> findAll(ConFormularioPK idConFormulario)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<PermisoRol> sentencia = em.createNamedQuery("PermisoRol.findAll", PermisoRol.class);
			sentencia.setParameter("idconformulario", idConFormulario.getIdconFormulario());
			sentencia.setParameter("version", idConFormulario.getVersion());
			List<PermisoRol> listado = sentencia.getResultList();
			if(listado!=null && listado.size()>0){
				return listado;
			} else {
				return new ArrayList<PermisoRol>();
			}
		}catch(PersistenceException e){
			throw new NotariaException("No se ha logrado obtener el listado de persmisos rol-formulario. Revise el log de operaciones para mayor información.",e);
		} finally {
			em.close();
		}
	}

	@Override
	public PermisoRol findById(String identificador) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<PermisoRol> sentencia = em.createNamedQuery("ActoFormulario.findById", PermisoRol.class);
			sentencia.setParameter("identificador", identificador);
			List<PermisoRol> listado = sentencia.getResultList();
			if(listado!=null && listado.size()>0){
				return listado.get(0);
			} else {
				return null;
			}
		}catch(PersistenceException e){
			throw new NotariaException("No se ha logrado obtener el listado de permiso rol-formulario. Revise el log de operaciones para mayor información.",e);
		} finally {
			em.close();
		}
	}
	
	@Override
	public int removeById(String identificador) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			
			TypedQuery<PermisoRol> sentencia = em.createNamedQuery("PermisoRol.findById", PermisoRol.class);
			sentencia.setParameter("identificador", identificador);			
			List<PermisoRol> listado = sentencia.getResultList();
			if(listado!=null && listado.size()>0){
				em.getTransaction().begin();
				em.remove(listado.get(0));
				em.getTransaction().commit();
				return 1;
			}
			return 0;
		} catch(PersistenceException e){
			throw new NotariaException("No se ha logrado eliminar el permiso de rol-formulario. Revise el log de operaciones para mayor información.",e);
		} finally {
			em.close();
		}
	}

	@Override
	public int removeAll(ConFormularioPK idConFormulario)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			
			TypedQuery<PermisoRol> sentencia = em.createNamedQuery("PermisoRol.findAll", PermisoRol.class);
			sentencia.setParameter("idconformulario", idConFormulario.getIdconFormulario());
			sentencia.setParameter("version", idConFormulario.getVersion());
			List<PermisoRol> listado = sentencia.getResultList();
			int afectados = 0;
			if(listado!=null && listado.size()>0){
				for(PermisoRol permisoRol:listado){
					afectados+=removeById(permisoRol.getIdpermisorol());
				}
			}									
			return afectados;		
		} catch(PersistenceException e){
			throw new NotariaException("No se ha logrado eliminar los permisos de rol-formulario. Revise el log de operaciones para mayor información.",e);
		} finally {
			em.close();
		}
	}
}
