package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.DocumentoVersionesDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoVersiones;

public class DocumentoVersionesDaoImpl extends
		GenericDaoImpl<DocumentoVersiones, Integer> implements
		DocumentoVersionesDao {
	
	public DocumentoVersionesDaoImpl(){
		super(DocumentoVersiones.class);
	}
	
	@Override
	public DocumentoVersiones findByDocumentoPublicado(String iddocumento) throws NotariaException {
		List<DocumentoVersiones> docEncontrado = executeQuery("SELECT d FROM DocumentoVersiones d WHERE iddocumento = ?1 AND ispublicado = true AND isactivo = true", iddocumento);
		if(docEncontrado.size()>1)
			throw new NotariaException("Se encontró más de un documento publicado");
		else if(!docEncontrado.isEmpty()){
			System.out.println("DOCUMENTO ENCONTRADO "+docEncontrado);
			return docEncontrado.get(0);
		}else
			throw new NotariaException("No se recuperó ningún documento "+docEncontrado);
	}

	@Override
	public List<DocumentoVersiones> listarNoPublicados() throws NotariaException {
		return executeQuery("SELECT d FROM DocumentoVersiones d WHERE ispublicado=false AND isactivo=true");
	}
	
	@Override
	public void setInactivo(String iddocumento) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			em.getTransaction().begin();
			Query q = em.createNativeQuery("UPDATE tbbsnm22b SET isactivo=false WHERE iddocumento='"+iddocumento+"'");
			System.out.println("executeUpdate = "+q.executeUpdate());
			em.getTransaction().commit();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@Override
	public Integer getMaxVersionOfDoc(String iddocumento) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query q = em.createNativeQuery("SELECT MAX(version) FROM tbbsnm22b WHERE iddocumento='"+iddocumento+"'");
			System.out.println("RESULT "+q.getSingleResult());
			return (Integer)q.getSingleResult();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}

	}
	
	@Override
	public DocumentoVersiones findBy(String iddocumento, Integer version) throws NotariaException{
		List<DocumentoVersiones> doc = executeQuery("SELECT c FROM DocumentoVersiones c WHERE c.iddocumento=?1 AND c.version = ?2", iddocumento,version);
		if(doc.size()==1){
			return doc.get(0);
		}else{
			throw new NotariaException("Se encontró mas de 1 resultado o no existe un registro con los parámetros proporcionados");
		}
	}
}
