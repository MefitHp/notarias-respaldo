package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.ManejoSesionDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ManejoSesion;

public class ManejoSesionDaoImpl extends GenericDaoImpl<ManejoSesion, Integer>
		implements ManejoSesionDao {

	public ManejoSesionDaoImpl(){
		super(ManejoSesion.class);
	}
	
	@Override
	public boolean borrar(ManejoSesion ms)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		boolean exito = true;
		try {
			em.getTransaction().begin();
			ManejoSesion msf = em.find(ManejoSesion.class,ms.getId());
			em.remove(msf);
	        em.getTransaction().commit();
		} catch(PersistenceException e){
			e.printStackTrace(System.out);
			exito = false;
			throw new NotariaException("Ha ocurrido un fallo al encontrar el usuario.", e.getCause());
		}finally{
			em.close();
		}
		
        return exito;
		
	}
	
	
	@Override
	public int totalSesiones()throws NotariaException{
		int sesiones = 0;
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<ManejoSesion> query = em.createQuery("SELECT m FROM ManejoSesion m",ManejoSesion.class);
			sesiones = query.getResultList().size();			
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException("Ha ocurrido un fallo al encontrar el usuario.", e.getCause());
		}finally{
			em.close();
		}
		
		return sesiones;
	}
	
	@Override
	public List<ManejoSesion> findSesionByUser(String idusuario)throws NotariaException{
		List<ManejoSesion> ms = executeQuery("SELECT m FROM ManejoSesion m WHERE m.idusuario = ?1", idusuario);
		return ms;
	}
	
	@Override
	public ManejoSesion findBySesionAndUser(String idsesion, String... idusuario) throws NotariaException{
		if(idusuario.length>1){
			throw new NotariaException("No se acepta más de 1 parámetro para idusuario");
		}
		if(idusuario != null && idusuario.length>0){
				List<ManejoSesion> ms = executeQuery("SELECT m FROM ManejoSesion m WHERE m.idsesion = ?1 AND m.idusuario = ?2", idsesion,idusuario[0]);
			if(ms != null && ms.size()==1){
				return ms.get(0);
			}else
				throw new NotariaException("no se encontró el usuario con sesión o el resultado fue mayor a 1 resultado");
		}else{
			List<ManejoSesion> ms = executeQuery("SELECT m FROM ManejoSesion m WHERE m.idsesion = ?1",idsesion);
			if(ms.size()>1 || ms.size()==0){
				throw new NotariaException("Existe mas de 1 registro con el idsesion proporcionado o no se encontró el registro");
			}else{
				return ms.get(0);
			}
		}
	}
	
}
