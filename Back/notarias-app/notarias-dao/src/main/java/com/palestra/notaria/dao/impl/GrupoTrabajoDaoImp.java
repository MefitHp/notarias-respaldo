package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.GrupoTrabajoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;

public class GrupoTrabajoDaoImp extends
GenericDaoImpl<GrupoTrabajo, Integer> implements GrupoTrabajoDao{
	

	public GrupoTrabajoDaoImp() {
		super(GrupoTrabajo.class);
	}
	
	
	public Boolean borraGrupo(String idgrupotrabajo)
			throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
				GrupoTrabajo gt = em.find(GrupoTrabajo.class,idgrupotrabajo);
				em.remove(gt);						
			tx.commit();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			try {
				tx.rollback();
			} catch (Exception e2) {
				throw new NotariaException(e.getCause());
			}

		} finally {
			em.close();
		}
		return false;
	}


	@Override
	public List<GrupoTrabajo> buscarXresponsable(Usuario responsable)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			StringBuilder sql = new StringBuilder();
			//sql.append("SELECT ugt.idusuariogrupotrabajo,ugt.usuario.cdusuario,ugt.usuario.dsiniciales,ugt.usuario.rol.dsnombre FROM UsuarioGrupoTrabajo ugt");
			sql.append("SELECT gpotrabajo FROM GrupoTrabajo gpotrabajo where gpotrabajo.responsable.idusuario = :idresponsable");
			TypedQuery<GrupoTrabajo> query = em.createQuery(sql.toString(), GrupoTrabajo.class);
			query.setParameter("idresponsable",responsable.getIdusuario());
			return query.getResultList();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en buscarXresponsable DaoGrupo Trabajo:"+e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public Usuario obtenerResponsable(Usuario miembro)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ugp.grupoTrabajo.responsable FROM UsuarioGrupoTrabajo ugp WHERE ugp.usuario.idusuario = :idmiembro");
			TypedQuery<Usuario> query = em.createQuery(sql.toString(), Usuario.class);
			query.setParameter("idmiembro", miembro.getIdusuario());
			
			return query.getSingleResult();
		}catch(NoResultException e){
			e.printStackTrace();
			return null;
		}catch(NonUniqueResultException e){
			e.printStackTrace();
			throw new NotariaException("Error en GrupoTrabajoDaoImp.obtenerResponsable(): " +e.getMessage());
		}catch(PersistenceException e){
			e.printStackTrace();
			throw new NotariaException("Error en GrupoTrabajoDaoImp.obtenerResponsable(): " +e.getMessage());
		}finally{
			em.close();
		}
	}
	
}


