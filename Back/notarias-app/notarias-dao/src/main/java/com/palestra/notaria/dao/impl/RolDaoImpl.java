package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.RolDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;

public class RolDaoImpl extends GenericDaoImpl<Rol, Integer> implements RolDao {

	public RolDaoImpl() {
		super(Rol.class);
	}
	
	@Override
	public Rol rolByPrefijo(String prefijo) throws NotariaException{
		
		EntityManager em = factory.createEntityManager();
		List<Rol> roles = null;
		Rol rol = null;
		try {
			TypedQuery<Rol> query = em.createNamedQuery("Rol.findByPrefijo",Rol.class);
			query.setParameter("prefijo",prefijo);
			roles = query.getResultList();
				if(roles.size() > 0){
					rol = roles.get(0);
				}else{
					throw new NotariaException("No se obtuvieron resultados en la busqueda de roles por prefijo");
				}
			
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally{
			em.close();
		}			
		return rol;
		
	}

}
