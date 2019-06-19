package com.palestra.notaria.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.TareaProcessActoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ProcessActo;
import com.palestra.notaria.modelo.TareaProcessActo;

public class TareaProcessActoDaoImpl extends GenericDaoImpl<TareaProcessActo, Integer> implements TareaProcessActoDao {

	public TareaProcessActoDaoImpl() {
		super(TareaProcessActo.class);
	}

	@Override
	public TareaProcessActo getActiveByActo(Acto acto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
//			TODO: para implementar despues la consulta con estatus activo
//			String sql = "SELECT tpa FROM TareaProcessActo tpa WHERE tpa.acto= :acto AND tpa.isactive = true";
			String sql = "SELECT tpa FROM TareaProcessActo tpa WHERE tpa.processActo.acto = :acto AND tpa.isactive=TRUE";
			TypedQuery<TareaProcessActo> query = em.createQuery(sql.toString(), TareaProcessActo.class);
			query.setParameter("acto", acto);
			TareaProcessActo tareaProcessActo = query.getSingleResult();
			return tareaProcessActo;
		}catch(NoResultException e){
			e.printStackTrace();
			return null;
		}catch(PersistenceException e){
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

}
