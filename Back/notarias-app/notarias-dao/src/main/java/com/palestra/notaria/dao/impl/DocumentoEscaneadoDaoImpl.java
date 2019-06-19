package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.DocumentoEscaneadoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoEscaneado;

public class DocumentoEscaneadoDaoImpl extends GenericDaoImpl<DocumentoEscaneado, Integer> implements DocumentoEscaneadoDao {

	public DocumentoEscaneadoDaoImpl(){
		super(DocumentoEscaneado.class);
	}
	
//	public static void main(String args[]){
//		DocumentoEscaneadoDaoImpl daoIm = new DocumentoEscaneadoDaoImpl();
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoEscaneado> obtenerDocEscaneados(String idexpediente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<DocumentoEscaneado> lista;
		try {
			query = em.createQuery("from DocumentoEscaneado where expediente.idexpediente = '"+ idexpediente + "' order by fecha ");
			
			lista = query.getResultList();
			
			if(lista!=null){
				for(DocumentoEscaneado de:lista){
					de.setExpediente(null);
				}
			}
		
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return lista;

	}

	@SuppressWarnings("unchecked")
	@Override
	public String buscarArchivoPorId(String idDocumento) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<String> lista;
		String archivo= null;
		try {
			query = em.createQuery("select dsruta from DocumentoEscaneado where iddocescaneado = '"+ idDocumento + "'");
			
			lista = query.getResultList();
			
			if(lista!=null && !lista.isEmpty()){
				archivo = lista.get(0);
			}
		
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		return archivo;
	}
}
