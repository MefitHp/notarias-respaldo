package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.UsuarioGrupoTrabajoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;

public class UsuarioGrupoTrabajoDaoImp extends GenericDaoImpl<UsuarioGrupoTrabajo, Integer>
		implements UsuarioGrupoTrabajoDao {

	public UsuarioGrupoTrabajoDaoImp() {
		super(UsuarioGrupoTrabajo.class);
	}

	@Override
	public boolean borrarUsuarioGrupo(String idUsuarioGrupoTrabajo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			UsuarioGrupoTrabajo gt = em.find(UsuarioGrupoTrabajo.class, idUsuarioGrupoTrabajo);
			em.remove(gt);
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
	public List<UsuarioGrupoTrabajo> findAll() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			// sql.append("SELECT
			// ugt.idusuariogrupotrabajo,ugt.usuario.cdusuario,ugt.usuario.dsiniciales,ugt.usuario.rol.dsnombre
			// FROM UsuarioGrupoTrabajo ugt");
			sql.append("SELECT ugt FROM UsuarioGrupoTrabajo ugt");
			TypedQuery<UsuarioGrupoTrabajo> query = em.createQuery(sql.toString(), UsuarioGrupoTrabajo.class);
			return query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			// throw new NotariaException(String.format("Something went wrong in
			// persisting data , view server log for details [findAll in %s at
			// %s].", clase, dateFormat.format(new Date())), e.getCause());
			throw new NotariaException("Ocurrió un error en findAll(), causado por: " + e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<UsuarioGrupoTrabajo> buscarXgrupo(String idgrupo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			// sql.append("SELECT
			// ugt.idusuariogrupotrabajo,ugt.usuario.cdusuario,ugt.usuario.dsiniciales,ugt.usuario.rol.dsnombre
			// FROM UsuarioGrupoTrabajo ugt");
			sql.append("SELECT ugt FROM UsuarioGrupoTrabajo ugt where ugt.grupoTrabajo.idgrupotrabajo =:idgrupo");
			TypedQuery<UsuarioGrupoTrabajo> query = em.createQuery(sql.toString(), UsuarioGrupoTrabajo.class);
			query.setParameter("idgrupo", idgrupo);
			return query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			// throw new NotariaException(String.format("Something went wrong in
			// persisting data , view server log for details [findAll in %s at
			// %s].", clase, dateFormat.format(new Date())), e.getCause());
			throw new NotariaException("Ocurrió un error en findAll(), causado por: " + e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public UsuarioGrupoTrabajo buscarXusuario(String idusuario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			// sql.append("SELECT
			// ugt.idusuariogrupotrabajo,ugt.usuario.cdusuario,ugt.usuario.dsiniciales,ugt.usuario.rol.dsnombre
			// FROM UsuarioGrupoTrabajo ugt");
			sql.append("SELECT ugt FROM UsuarioGrupoTrabajo ugt where ugt.usuario.idusuario =:idusuario");
			TypedQuery<UsuarioGrupoTrabajo> query = em.createQuery(sql.toString(), UsuarioGrupoTrabajo.class);
			query.setParameter("idusuario", idusuario);
			List<UsuarioGrupoTrabajo> result = query.getResultList();
			if (result.size() > 0) {
				return result.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			// throw new NotariaException(String.format("Something went wrong in
			// persisting data , view server log for details [findAll in %s at
			// %s].", clase, dateFormat.format(new Date())), e.getCause());
			throw new NotariaException(
					"Ocurrió un error en buscar por usuario grupo de trabajo, causado por: " + e.getCause());
		} finally {
			em.close();
		}
	}

}
