package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.PDNTokenDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.PDNToken;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;
import com.palestra.notaria.util.GeneradorId;

public class PDNTokenDaoImpl extends GenericDaoImpl<PDNToken, Integer> implements
		PDNTokenDao {
	
	EntityManager em;
	
	public PDNTokenDaoImpl() {
		super(PDNToken.class);
	}
	
	PDNTokenDaoImpl(Class<PDNToken> klass) {
		super(klass);		
	}

	@Override
	public PDNToken save(PDNToken token) throws NotariaException{
		token.setIdentificador(GeneradorId.generaId(token));
		return super.save(token);
	}

	@Override
	public List<PDNToken> tokens(PlantillaDocumentoNotarial plantilla) throws NotariaException {
		em = factory.createEntityManager();
		try{
			TypedQuery<PDNToken> sentencia = em.createNamedQuery("PDNToken.findByPlantilla", PDNToken.class);
			sentencia.setParameter("plantilla", plantilla);
			List<PDNToken> listado = sentencia.getResultList();
			if(listado!=null && listado.size()>0){
				return listado;
			} else {
				return new ArrayList<PDNToken>();
			}
		} catch(NullPointerException e){
			throw new NotariaException("Ocurrio una excepción en la unidad de persistencia. Imposible listar los tokens de la plantilla [NULL].");
		} catch(PersistenceException e){
			throw new NotariaException("Ocurrio una excepción en la unidad de persistencia. Imposible listar los tokens de la plantilla.");
		}finally{
			em.close();
		}
	}
	
}
