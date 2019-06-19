package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.AvisoDecenaDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AvisoDecena;

public class AvisoDecenaDaoImp extends GenericDaoImpl<AvisoDecena, Integer> implements AvisoDecenaDao {

	public AvisoDecenaDaoImp(Class<AvisoDecena> clase) {
		super(clase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AvisoDecena obtenerXnumeroLibro(Long numeroLibro) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<AvisoDecena> query;
		List<AvisoDecena> lista;
		try {
			query = em.createQuery("select ad from AvisoDecena ad where ad.libroapertura.innumlibro=:numeroLibro",AvisoDecena.class);
			query.setParameter("numeroLibro", numeroLibro);
			lista = query.getResultList();
			if(lista.size()>0){
				return lista.get(0);
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		
		return null;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cierraDecena(Long numeroLibro) throws NotariaException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<AvisoDecena> findAll()throws NotariaException{
		EntityManager em = factory.createEntityManager();
		TypedQuery<AvisoDecena> query;
		List<AvisoDecena> respuesta;
		try {
			query = em.createQuery("select ad from AvisoDecena ad ORDER BY ad.fechaApertura DESC", AvisoDecena.class);
			respuesta = query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		return respuesta;
	}
	

}
