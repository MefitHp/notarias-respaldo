package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.ContactoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Contacto;

public class ContactoDaoImpl extends GenericDaoImpl<Contacto, Integer>
		implements ContactoDao {

	public ContactoDaoImpl(){
		super(Contacto.class);
	}

	@Override
	public Contacto findByPersona(String idpersona) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		Contacto con= null;
		try{
			TypedQuery<Contacto> query = em.createQuery("SELECT c from Contacto c where c.persona.idpersona = :idpersona",Contacto.class);
			query.setParameter("idpersona",idpersona);
			List<Contacto> lista = query.getResultList();
			if(lista!=null && !lista.isEmpty()){
				con = lista.get(0);
				con.setPersona(null);
			}
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally{
			em.close();
		}
		
		return con;
	}
	
	
}
