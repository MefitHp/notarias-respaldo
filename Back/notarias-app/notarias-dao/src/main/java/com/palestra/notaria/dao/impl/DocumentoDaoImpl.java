package com.palestra.notaria.dao.impl;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.DocumentoDao;
import com.palestra.notaria.dao.ManejoSesionDao;
import com.palestra.notaria.dao.UsuarioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Documento;
import com.palestra.notaria.modelo.ManejoSesion;
import com.palestra.notaria.modelo.Usuario;

public class DocumentoDaoImpl extends GenericDaoImpl<Documento, Integer> implements DocumentoDao {

	private ManejoSesionDao msDao;
	
	private UsuarioDao usuarioDao;
	
	public DocumentoDaoImpl() {
		super(Documento.class);
		msDao = new ManejoSesionDaoImpl();
		usuarioDao = new UsuarioDaoImpl();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> obtenerDocumento(String tipo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Documento> lista;
		try{
			Query query = em.createQuery("from Documento where tipodoc.dselemento = '"+tipo+"'");

			lista = query.getResultList();
			if(lista!=null && !lista.isEmpty()){
				for(Documento doc:lista){
					doc.setTipodoc(null);
				}
			}
			
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
		
		return lista;
	}

	public List<Documento> findAll() throws NotariaException {
		 List<Documento> docs = executeQuery("SELECT d FROM Documento d WHERE isactivo = true");
		 List<Documento> lista = new ArrayList<Documento>();
		 for(Documento doc:docs){
			 ManejoSesion ms = msDao.findBySesionAndUser(doc.getIdsesion());
			 Usuario usuario = usuarioDao.findById(ms.getIdusuario());
			 doc.setCreador(usuario.getDsnombre()+" "+usuario.getDspaterno()+" "+usuario.getDsmaterno());
			 lista.add(doc);
		 }
		return lista;
	}
	
	@Override
	public Documento findBy(String iddocumento, Integer version) throws NotariaException{
		List<Documento> doc = executeQuery("SELECT c FROM Documento c WHERE c.iddocumento=?1 AND c.version = ?2", iddocumento,version);
		if(doc.size()==1){
			return doc.get(0);
		}else{
			throw new NotariaException("Se encontró mas de 1 resultado o no existe un registro con los parámetros proporcionados");
		}
	}

}
