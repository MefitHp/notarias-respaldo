package com.palestra.notaria.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.ProcessActoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ProcessActo;

public class ProcessActoDaoImp extends GenericDaoImpl<ProcessActo, Integer> implements ProcessActoDao {

	public ProcessActoDaoImp() {
		super(ProcessActo.class);
		
	}

	@Override
	public ProcessActo findByActo(Acto acto)throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			String sql = "SELECT pa FROM ProcessActo pa WHERE pa.acto= :acto";
			TypedQuery<ProcessActo> query = em.createQuery(sql.toString(), ProcessActo.class);
			query.setParameter("acto", acto);
			ProcessActo processActo = query.getSingleResult();
			return processActo;
		}catch(NoResultException e){
			e.printStackTrace();
			return null;
		}catch(NonUniqueResultException e){
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		}catch(PersistenceException e){
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}


}
