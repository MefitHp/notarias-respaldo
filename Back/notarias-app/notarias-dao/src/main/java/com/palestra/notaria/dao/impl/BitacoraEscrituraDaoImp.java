package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.BitacoraEscrituraDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraEscritura;
import com.palestra.notaria.modelo.BitacoraExpediente;
import com.palestra.notaria.modelo.Escritura;

public class BitacoraEscrituraDaoImp extends GenericDaoImpl<BitacoraEscritura,Integer> implements BitacoraEscrituraDao {

	public BitacoraEscrituraDaoImp() {
		super(BitacoraEscritura.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BitacoraEscritura obtenerUltimaBitacoraXEscritura(Escritura escritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		BitacoraEscritura respuesta = null;
		try {
			TypedQuery<BitacoraEscritura> query =  em.createNamedQuery("obtenerUltimaXEscritura", BitacoraEscritura.class);
			query.setParameter("idescritura", escritura.getIdescritura());
			List<BitacoraEscritura> bitacoras = query.getResultList();
			if(bitacoras.size()>0){
				respuesta =  bitacoras.get(0);
				respuesta.setEscritura(escritura);
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		
		// TODO Auto-generated method stub
		return respuesta;
	}
	
	

}
