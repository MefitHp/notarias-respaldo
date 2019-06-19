package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.VariableGrupoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.VariableGrupo;

public class VariableGrupoDaoImpl extends
		GenericDaoImpl<VariableGrupo, Integer> implements VariableGrupoDao {

	public VariableGrupoDaoImpl(){
		super(VariableGrupo.class);
	}
	
	
	@Override
	public List<VariableGrupo> obtenerVariablesEnGrupo(Grupo grupo)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<VariableGrupo> query = em.createQuery("SELECT vg FROM VariableGrupo vg WHERE vg.grupo = :grupo ORDER BY vg.dsorden", VariableGrupo.class);
			query.setParameter("grupo", grupo);
			List<VariableGrupo> variables = query.getResultList();
			return variables;
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrio un error al obtener las variables: "+e.getCause(),e);
		}finally {
			em.close();
		}
	}
	
	@Override
	public void eliminarPorId(String identificador) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{			
			Query query = em.createQuery("DELETE FROM VariableGrupo vg WHERE vg.idgpovars = :identificador");
			query.setParameter("identificador", identificador);
			em.getTransaction().begin();
			query.executeUpdate();
			em.getTransaction().commit();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrio un error al obtener las variables: "+e.getCause(),e);
		}finally {
			em.close();
		}
	}
	
}
