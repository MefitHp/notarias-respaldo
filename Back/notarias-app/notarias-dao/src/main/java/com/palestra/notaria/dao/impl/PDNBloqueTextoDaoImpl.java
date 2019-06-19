package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.PDNBloqueTextoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.PDNBloqueTexto;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;
import com.palestra.notaria.util.GeneradorId;

public class PDNBloqueTextoDaoImpl extends GenericDaoImpl<PDNBloqueTexto, Integer> implements
		PDNBloqueTextoDao {
	
	EntityManager em;
	
	public PDNBloqueTextoDaoImpl(){
		super(PDNBloqueTexto.class);
	}
	
	PDNBloqueTextoDaoImpl(Class<PDNBloqueTexto> klass) {
		super(klass);
	}
	
	@Override
	public PDNBloqueTexto save(PDNBloqueTexto bloqueTexto) throws NotariaException{
		bloqueTexto.setIdentificador(GeneradorId.generaId(bloqueTexto));
		return super.save(bloqueTexto);
	}
	
	@Override
	public List<PDNBloqueTexto> texto(PlantillaDocumentoNotarial plantilla) throws NotariaException {
		em = factory.createEntityManager();
		try{
			TypedQuery<PDNBloqueTexto> sentencia = em.createNamedQuery("PDNBloqueTexto.findByPlantilla", PDNBloqueTexto.class);
			sentencia.setParameter("plantilla", plantilla);
			List<PDNBloqueTexto> listado = sentencia.getResultList();
			if(listado!=null && listado.size()>0){
				return listado;
			} else {
				return new ArrayList<PDNBloqueTexto>();
			}
		} catch(NullPointerException e){
			throw new NotariaException("Ocurrio una excepción en la unidad de persistencia. Imposible listar los bloques de texto de la plantilla [NULL].");
		} catch(PersistenceException e){
			throw new NotariaException("Ocurrio una excepción en la unidad de persistencia. Imposible listar los bloques de texto de la plantilla.");
		}finally{
			em.close();
		}
	}
	
}
