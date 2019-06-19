package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.DocumentoObjetoDao;
import com.palestra.notaria.dato.DatoDocumentoObjeto;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoObjeto;
import com.palestra.notaria.modelo.Usuario;

public class DocumentoObjetoDaoImpl extends
		GenericDaoImpl<DocumentoObjeto, Integer> implements DocumentoObjetoDao {
	
	private static final int IS_PUBLICADO = 1;

	public DocumentoObjetoDaoImpl() {
		super(DocumentoObjeto.class);
	}

	@Override
	public List<DatoDocumentoObjeto> obtenListaDocumentoObjeto() throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {

			List<DatoDocumentoObjeto> resultados = new ArrayList<DatoDocumentoObjeto>();

			/** Obtener actos disponibles para seleccion **/
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT doc.id.iddocobjeto, doc.id.version, doc.dsnombre, doc.ispublicada ");
			sql.append(" FROM DocumentoObjeto doc");
			sql.append(" WHERE doc.ispublicada=" + IS_PUBLICADO);
			sql.append(" AND doc.isactivo= true");

			Query query = em.createQuery(sql.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> lista = query.getResultList();

			if (lista.size() == 0) {
				return null;
			}

			for (Object[] obj : lista) {
				DatoDocumentoObjeto ddo = new DatoDocumentoObjeto();
				ddo.setId((String)obj[0]);
				ddo.setVersion((Integer)obj[1]);
				ddo.setDsnombre((String)obj[2]);
				resultados.add(ddo);
			}

			return resultados;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public DocumentoObjeto buscarPorIdCompleto(String id, Integer version) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT docu FROM DocumentoObjeto docu ");
			sql.append(" WHERE docu.id.iddocobjeto=:iddocobjeto ");
			sql.append(" AND docu.id.version=:version ");
			
			Query query = em
					.createQuery(sql.toString());
			query.setParameter("iddocobjeto",id);
			query.setParameter("version", version);
			
			List<DocumentoObjeto> lista = query.getResultList();
			
			if(lista.size()==0){
				return null;
			}
			
			/** Sabemos que solo es un resultado **/
			DocumentoObjeto documento = (DocumentoObjeto) lista.get(0);
			Usuario usuarioPublico = null;
			Usuario usuarioCreoModifico = null;
			
			
			if(documento == null){
				return null;
			}
			usuarioPublico = documento.getUsuarioPublico();
			if (usuarioPublico instanceof HibernateProxy) {
				usuarioPublico = (Usuario) ((HibernateProxy) usuarioPublico)
						.getHibernateLazyInitializer()
						.getImplementation();
				usuarioPublico.setRol(null);
			}
			documento.setUsuarioPublico(usuarioPublico);
			
			usuarioCreoModifico = documento.getUsuarioCreoModifico();
			if (usuarioCreoModifico instanceof HibernateProxy) {
				usuarioCreoModifico = (Usuario) ((HibernateProxy) usuarioCreoModifico)
						.getHibernateLazyInitializer()
						.getImplementation();
				usuarioCreoModifico.setRol(null);
			}
			documento.setUsuarioCreoModifico(usuarioCreoModifico);
			
			return documento;
			
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String obtenPlantillaPorId(String id, Integer version) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT docu.dscontenido FROM DocumentoObjeto docu ");
			sql.append(" WHERE docu.id.iddocobjeto=:iddocobjeto ");
			sql.append(" AND docu.id.version=:version ");
			sql.append(" AND docu.ispublicada=" + IS_PUBLICADO);
			
			Query query = em
					.createQuery(sql.toString());
			query.setParameter("iddocobjeto",id);
			query.setParameter("version", version);
			
			List<String> lista = query.getResultList();
			
			if(lista.size()==0){
				return null;
			}
			
			/** Sabemos que solo es un resultado **/
			return (String)lista.get(0);
			
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<DocumentoObjeto> getNoPublicados() throws NotariaException{
		return executeQuery("SELECT d FROM DocumentoObjeto d WHERE d.id.version = " +
				"(SELECT MAX(doc.id.version) FROM DocumentoObjeto doc WHERE doc.id.iddocobjeto = d.id.iddocobjeto) " +
				"AND d.ispublicada = false");
	}
	
	@Override
	public List<DocumentoObjeto> getPublicados() throws NotariaException{
		return executeQuery("SELECT d FROM DocumentoObjeto d WHERE d.ispublicada = true");
	}
	
	@Override
	public DocumentoObjeto findDocumentoPublicado(String iddocobjeto) throws NotariaException{
		List<DocumentoObjeto> lista = executeQuery("SELECT d FROM DocumentoObjeto d WHERE d.id.iddocobjeto = ?1 " +
				"AND d.ispublicada = true", iddocobjeto);
		if(lista.size()>1)
			throw new NotariaException("Se encontró más de una coincidencia");
		else if(!lista.isEmpty())
			return lista.get(0);
		else 
			return null;
	}
	
	@Override
	public Integer findMaxVersion(String iddocobjeto) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			Query q = em.createNativeQuery("SELECT MAX(version) FROM tbcfgm18 WHERE iddocobjeto='"+iddocobjeto+"'");
			System.out.println("RESULT "+q.getSingleResult());
			return (Integer)q.getSingleResult();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}		
	}
}
