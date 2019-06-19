package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.DocumentoSuboperacionDao;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoSuboperacion;

public class DocumentoSuboperacionDaoImpl extends
		GenericDaoImpl<DocumentoSuboperacion, Integer> implements
		DocumentoSuboperacionDao {

	public DocumentoSuboperacionDaoImpl(){
		super(DocumentoSuboperacion.class);
	}
	
	@Override
	public List<DocumentoSuboperacion> listarPreviosPorSubopAndLocalidad(String idlocalidad,String idsuboperacion) throws NotariaException{
		return executeQuery("SELECT p FROM DocumentoSuboperacion p WHERE p.suboperacion.idsuboperacion = ?1 " +
				"AND p.localidad.idelemento = ?2 ORDER BY p.inorden", idsuboperacion,idlocalidad);
	}

	@Override
	public DocumentoSuboperacion getUnique(String idsuboperacion, String idlocalidad, String iddocumento, String idformato) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
		List<DocumentoSuboperacion> lista = null;
		TypedQuery<DocumentoSuboperacion> query = null;
		if(iddocumento==null){
			query = em.createQuery("SELECT ds FROM DocumentoSuboperacion ds WHERE ds.suboperacion.idsuboperacion = :idsuboperacion AND ds.localidad.idelemento = :idlocalidad AND ds.formatopdf.identificador = :idformatoPdf", DocumentoSuboperacion.class); 
			query.setParameter("idformatoPdf", idformato);
		}else if(idformato==null){			
			query = em.createQuery("SELECT ds FROM DocumentoSuboperacion ds WHERE ds.suboperacion.idsuboperacion = :idsuboperacion AND ds.localidad.idelemento = :idlocalidad AND ds.documento.iddocumento = :iddocumento", DocumentoSuboperacion.class); 
			query.setParameter("iddocumento", iddocumento);
		}
		query.setParameter("idsuboperacion", idsuboperacion);
		query.setParameter("idlocalidad", idlocalidad);
		lista = query.getResultList();
		if (lista!=null && lista.size()>0){
			return lista.get(0);
		}else{
			return null;
		}
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException("No se ha logrado obtener la lista de documentos para realizar una acción sobre estos. Revise el log de operaciones para más detalle.", e);
		} finally {
			em.close();
		}
	}

	@Override
	public DocumentoSuboperacion getDocumentoSubOperacion(String suboperacion, String localidad, String iddocumento)throws NotariaException{
		System.out.println("dsoDAO.getDocumentoSubOperacion('"+iddocumento+"');");
		List<DocumentoSuboperacion> listado = executeQuery("SELECT dso FROM DocumentoSuboperacion dso WHERE dso.suboperacion.idsuboperacion = ?2 AND dso.localidad.idelemento = ?3 AND (dso.formatopdf.identificador = ?1 OR dso.documento.iddocumento = ?1)", iddocumento, suboperacion, localidad);
		if(listado!=null && listado.size()>0){
			return listado.get(0);
		}else{
			return null;
		}
	}

	@Override
	public DocumentoSuboperacion findById(Integer identificador)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{			
			TypedQuery<DocumentoSuboperacion> query = em.createQuery("SELECT ds FROM DocumentoSuboperacion ds WHERE ds.identificador = :identificador", DocumentoSuboperacion.class);
			query.setParameter("identificador", identificador);
			List<DocumentoSuboperacion> listado = query.getResultList();
			if(listado!=null && listado.size()>0){
				return listado.get(0);
			}else{
				return null;
			}
		}catch(PersistenceException e){
			throw new NotariaException("No se ha logrado obtener el listado de documentos asociados a la operación. Revise el log de operaciones para mas detalle.");
		} finally {
				em.close();
			
		}		
	}

	@Override
	public boolean delete(Integer identificador) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{			
			TypedQuery<DocumentoSuboperacion> query = em.createQuery("SELECT ds FROM DocumentoSuboperacion ds WHERE ds.identificador = :identificador", DocumentoSuboperacion.class);
			query.setParameter("identificador", identificador);
			List<DocumentoSuboperacion> listado = query.getResultList();
			if(listado!=null && listado.size()>0){
				DocumentoSuboperacion ds = listado.get(0);
				em.getTransaction().begin();
				em.remove(ds);
				em.getTransaction().commit();
				return true;
			}else{
				return false;
			}
		}catch(PersistenceException e){
			throw new NotariaException("No se ha logrado obtener el listado de documentos asociados a la operación. Revise el log de operaciones para mas detalle.");
		} finally {
			em.close();
		}	
	}

	@Override
	public List<DocumentoSuboperacion> listarPosterioresPorSubopAndLocalidad(String idlocalidad,
			String idsuboperacion) throws NotariaException{
		
		List<DocumentoSuboperacion> posteriores = executeQuery("SELECT p FROM DocumentoSuboperacion p WHERE p.suboperacion.idsuboperacion = ?1 " +
				"AND p.localidad.idelemento = ?2 AND p.formatopdf.tipodoc.idelemento = ?3 ORDER BY p.inorden", idsuboperacion,idlocalidad,Constantes.ID_DOCUMENTO_POSTERIOR);
		
		for(DocumentoSuboperacion dso: posteriores){
			dso.getFormatopdf().setDetalleList(null);
		}
		
		return posteriores;
	}
	
	/*
	 * 						if(ds.getFormatopdf().getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_PREVIO)){

	 * @Override
	public List<DocumentoSuboperacion> listarPreviosPorSubopAndLocalidad(String idlocalidad,String idsuboperacion) throws NotariaException{
		return executeQuery("SELECT p FROM DocumentoSuboperacion p WHERE p.suboperacion.idsuboperacion = ?1 " +
				"AND p.localidad.idelemento = ?2 ORDER BY p.inorden", idsuboperacion,idlocalidad);
	}
	 * */
	
}
