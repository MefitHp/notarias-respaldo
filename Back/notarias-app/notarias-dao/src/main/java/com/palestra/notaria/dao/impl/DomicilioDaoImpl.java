package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.DomicilioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Domicilio;

public class DomicilioDaoImpl extends GenericDaoImpl<Domicilio, Integer> implements
		DomicilioDao {

	public DomicilioDaoImpl() {
		super(Domicilio.class);
	}
	
	@Override
	public List<Domicilio> listarDomiciliosDeActo(String idacto) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<Compareciente> comparecienteQuery;
			List<Compareciente> comparecientes;
			List<Domicilio> domicilios = new ArrayList<Domicilio>();
			comparecienteQuery = em.createQuery("SELECT c FROM Compareciente c WHERE c.acto.idacto = ?1", Compareciente.class);
			comparecienteQuery.setParameter(1, idacto);
			comparecientes = comparecienteQuery.getResultList();
			
			for(Compareciente comp:comparecientes){
				if(comp.getDomicilio() !=null && comp.getDomicilio().getDsdircompleta()!=null){
					String dirCompleta = comp.getDomicilio().getDsdircompleta();
					if(!dirCompleta.equals("") && !dirCompleta.equals("...Domicilio aún no registrado")){
						if(!domicilios.contains(comp.getDomicilio())) { 
							domicilios.add(comp.getDomicilio());
						}
					}
				} 				
			}
			return domicilios;
		
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

}
