package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.OperacionDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.Suboperacion;

public class OperacionDaoImpl extends GenericDaoImpl<Operacion, Integer> implements OperacionDao {

	public OperacionDaoImpl(){
		super(Operacion.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Suboperacion> OperacionesPorOperacion(String idoperacion) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		
//		String exp= null;
		List<Suboperacion> lista=null;
		try{
			Query query = em.createQuery("from Suboperacion where operacion.idoperacion =  '"+idoperacion+"'");

			lista = query.getResultList();
			
			if(lista!=null && !lista.isEmpty()){
				for(Suboperacion sub:lista){
					sub.setOperacion(null);
				}
			}
			
			
			
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
		return lista;
	}
}
