package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.DocumentoNotarialParcialDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;

public class DocumentoNotarialParcialDaoImpl extends
		GenericDaoImpl<DocumentoNotarialParcial, Integer> implements
		DocumentoNotarialParcialDao {

	public DocumentoNotarialParcialDaoImpl() {
		super(DocumentoNotarialParcial.class);
	}

	/**
	 * Retorna un documeno notarial parcial en su version mas actual
	 * filrado por escritura
	 * @param id
	 * 		idEscritura
	 * @throws NotariaException 
	 */
	@Override
	public DocumentoNotarialParcial getLastVersion(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT dnp FROM DocumentoNotarialParcial dnp ");
			sql.append(" WHERE dnp.escritura.idescritura = :id");
			sql.append(" AND dnp.version = ");
			sql.append(" (SELECT MAX(docpar.version) FROM DocumentoNotarialParcial docpar WHERE docpar.escritura.idescritura = :id) ");
//			sql.append(" AND dnp.iscerrado = true");
			
			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<DocumentoNotarialParcial> resultados = query.getResultList();
			if (resultados != null && resultados.size() > 0) {
				return resultados.get(0);
			}
			
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
		
		/** Si no se encontro nada retorna nullo **/
		return null;

	}
	
	/**
	 * Retorna la ultima version que existe del documento filtrado por idEscritura
	 * @param id 
	 * 		idEscritura
	 * @return
	 * 		Long version actual del documento
	 * @throws NotariaException 
	 */
	@Override
	public Integer getActualVersion(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT MAX(docpar.version) FROM DocumentoNotarialParcial docpar WHERE docpar.escritura.idescritura = :id ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<Integer> resultados = query.getResultList();
			if (resultados != null && resultados.size() > 0) {
				return resultados.get(0);
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
		
		/** Si no se encontro nada retorna nullo **/
		return null;

	}

	@Override
	public List<DocumentoNotarialParcial> findAll() throws NotariaException {
		List<DocumentoNotarialParcial> lista = super.findAll();
		for(DocumentoNotarialParcial doc:lista){
			doc.setEscritura(null);
		}
		return lista;
	}
	
	@Override
	public DocumentoNotarialParcial findById(String id) throws NotariaException {
//		DocumentoNotarialParcial doc = super.findById(id);
		EntityManager em = factory.createEntityManager();
		try{
			DocumentoNotarialParcial doc=null;
			List<DocumentoNotarialParcial> lista=null;
			//doc = em.find(DocumentoNotarialParcial.class, id);
			TypedQuery<DocumentoNotarialParcial> query = em.createQuery("SELECT dnp FROM DocumentoNotarialParcial dnp WHERE dnp.iddocnotpar=:id", DocumentoNotarialParcial.class); 
			//lista = executeQuery("SELECT dnp FROM DocumentoNotarialParcial dnp WHERE dnp.iddocnotpar=?1", id);
			query.setParameter("id", id);
			lista = query.getResultList();
			if(lista!= null && lista.size()>0){
				
				doc = lista.get(0);
				System.out.println("Se localizo al menos un documento: "+doc.getIddocnotpar());
			}
			if(doc != null){	
				Escritura esc = doc.getEscritura();			
				Expediente exp = doc.getEscritura().getExpediente();
				if(esc instanceof HibernateProxy){
					esc = (Escritura)((HibernateProxy)esc).getHibernateLazyInitializer().getImplementation();
					esc.setLibro(null);
					if(exp instanceof HibernateProxy){
						exp = (Expediente)((HibernateProxy)exp).getHibernateLazyInitializer().getImplementation();
						exp.setAbogado(null);
						//exp.setListaComentarios(null);
						exp.setTramite(null);
					}
				}
				doc.setEscritura(esc);
				doc.getEscritura().setExpediente(exp);
			}
			return doc;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public void deleteById(String iddocnotpar)throws NotariaException{
		DocumentoNotarialParcial obj = findById(iddocnotpar);
		EntityManager em = factory.createEntityManager();
		try{
			
			if(obj!=null){
				System.out.print("=====>  Por eliminar el documento notarial parcial "+iddocnotpar+" :");
				
				em.getTransaction().begin();
				
				Query query = em.createQuery("DELETE FROM DocumentoNotarialParcial WHERE iddocnotpar = :id");
				query.setParameter("id", iddocnotpar);
				query.executeUpdate();
				//em.remove(obj);
				em.getTransaction().commit();
				System.out.println("\t\t\t[OK]");
			}else{
				System.out.println("=====>  No se localizo el documento notarial parcial a eliminar: "+iddocnotpar);
			}
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<DocumentoNotarialParcial> getByEscritura(Escritura escritura) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<DocumentoNotarialParcial> query = em.createQuery("SELECT d FROM DocumentoNotarialParcial d WHERE d.escritura=?1", DocumentoNotarialParcial.class);
			query.setParameter(1, escritura);
			List<DocumentoNotarialParcial> lista = query.getResultList();
			Escritura esc;
			for(DocumentoNotarialParcial doc:lista){
				esc = doc.getEscritura();
				if(esc instanceof HibernateProxy){
					esc = (Escritura)((HibernateProxy) esc).getHibernateLazyInitializer().getImplementation(); 
				}
				doc.setEscritura(esc);
				doc.getEscritura().setNotario(null);
				doc.getEscritura().setLibro(null);
				doc.getEscritura().setExpediente(null);
			}
			return lista;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
}
