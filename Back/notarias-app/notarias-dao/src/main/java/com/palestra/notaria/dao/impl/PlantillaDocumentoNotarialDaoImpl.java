package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.PlantillaDocumentoNotarialDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarialPK;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarialSubOperacion;
import com.palestra.notaria.modelo.Suboperacion;

public class PlantillaDocumentoNotarialDaoImpl extends
		GenericDaoImpl<PlantillaDocumentoNotarial, Integer> implements
		PlantillaDocumentoNotarialDao {

	public PlantillaDocumentoNotarialDaoImpl() {
		super(PlantillaDocumentoNotarial.class);
	}

	@Override
	public PlantillaDocumentoNotarial getPublishBySubOperacionId(String id) throws NotariaException {

		EntityManager em = factory.createEntityManager();

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pdn FROM PlantillaDocumentoNotarial pdn ");
			sql.append(" WHERE pdn.suboperacion.idsuboperacion = :idsuboperacion");
			sql.append(" AND pdn.id.inversion = ");
			sql.append(" (SELECT MAX(plan.id.inversion) FROM PlantillaDocumentoNotarial plan WHERE plan.suboperacion.idsuboperacion = :idsuboperacion AND ispublicado = :ispublicado)");
			//sql.append(" AND pdn.id.inversion = 1");
			
			TypedQuery<PlantillaDocumentoNotarial> query = em.createQuery(sql.toString(), PlantillaDocumentoNotarial.class);
			query.setParameter("idsuboperacion", id);
			query.setParameter("ispublicado", true);
			
			List<PlantillaDocumentoNotarial> resultados = query.getResultList();
			if (resultados.size() == 0) {
				return null;
			}
			return resultados.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public PlantillaDocumentoNotarial getLastVersion(String idplantilla) throws NotariaException{
		List<PlantillaDocumentoNotarial> plantillas = executeQuery("SELECT p FROM PlantillaDocumentoNotarial p WHERE p.id.iddocnot = ?1 AND " +
					"p.id.inversion = (SELECT MAX(doc.id.inversion) FROM PlantillaDocumentoNotarial doc " +
					"WHERE doc.id.iddocnot = ?1)	", idplantilla);
		if(plantillas == null || plantillas.size()==0){
			throw new NotariaException("Ocurrió un error al recuperar o no se encontró ningún resultado");
		}else if(plantillas.size()>1){
			throw new NotariaException("Se encontró mas de 1 resultado");
		}else{
			return plantillas.get(0);
		}
	}
	
	@Override
	public List<PlantillaDocumentoNotarial> getNoPublicados() throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			List<PlantillaDocumentoNotarial> plantillas;
			TypedQuery<PlantillaDocumentoNotarial> query = em.createQuery("SELECT p FROM PlantillaDocumentoNotarial p WHERE p.id.inversion = " +
					"(SELECT MAX(doc.id.inversion) FROM PlantillaDocumentoNotarial doc WHERE doc.id.iddocnot = p.id.iddocnot) " +
					"AND p.ispublicado = false ORDER BY p.dstitulo", PlantillaDocumentoNotarial.class); 
			plantillas = query.getResultList();
			for(PlantillaDocumentoNotarial plantilla:plantillas){
				List<PlantillaDocumentoNotarialSubOperacion> suboperaciones = plantilla.getListaPlantillaDocumentoNotarialSubOperacion();
				//List<PlantillaDocumentoNotarialSubOperacion> suboperacionesInicializadas = new ArrayList<PlantillaDocumentoNotarialSubOperacion>();
				for(int x=0;x<suboperaciones.size();x++){
					PlantillaDocumentoNotarialSubOperacion suboperacion = suboperaciones.get(x);
					Suboperacion subOperacion = suboperacion.getSuboperacion();
					if(subOperacion instanceof HibernateProxy){
						subOperacion = (Suboperacion)((HibernateProxy)subOperacion).getHibernateLazyInitializer().getImplementation();						
						suboperacion.setSuboperacion(subOperacion);
						suboperacion.setPlantillaDocumentoNotarial(null);						
					}
				}
				//plantilla.setListaPlantillaDocumentoNotarialSubOperacion(suboperacionesInicializadas);
			}				
			return plantillas;
		}catch(PersistenceException e) {
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<PlantillaDocumentoNotarial> getPublicados() throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			List<PlantillaDocumentoNotarial> plantillas;
			TypedQuery<PlantillaDocumentoNotarial> query = em.createQuery("SELECT p FROM PlantillaDocumentoNotarial p WHERE p.ispublicado = true ORDER BY p.dstitulo", PlantillaDocumentoNotarial.class); 
			plantillas = query.getResultList();
			for(PlantillaDocumentoNotarial plantilla:plantillas){
				List<PlantillaDocumentoNotarialSubOperacion> suboperaciones = plantilla.getListaPlantillaDocumentoNotarialSubOperacion();
				//List<PlantillaDocumentoNotarialSubOperacion> suboperacionesInicializadas = new ArrayList<PlantillaDocumentoNotarialSubOperacion>();
				for(int x=0;x<suboperaciones.size();x++){
					PlantillaDocumentoNotarialSubOperacion suboperacion = suboperaciones.get(x);
					Suboperacion subOperacion = suboperacion.getSuboperacion();
					if(subOperacion instanceof HibernateProxy){
						subOperacion = (Suboperacion)((HibernateProxy)subOperacion).getHibernateLazyInitializer().getImplementation();						
						suboperacion.setSuboperacion(subOperacion);
						suboperacion.setPlantillaDocumentoNotarial(null);						
					}
				}
				//plantilla.setListaPlantillaDocumentoNotarialSubOperacion(suboperacionesInicializadas);
			}			
			return plantillas;
		}catch(PersistenceException e) {
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}
	
	@Override
	public PlantillaDocumentoNotarial findById(PlantillaDocumentoNotarialPK id) throws NotariaException {
		List<PlantillaDocumentoNotarial> plantillas;
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<PlantillaDocumentoNotarial> query = em.createQuery("SELECT p FROM PlantillaDocumentoNotarial p WHERE p.id.iddocnot = :identificador AND p.id.inversion = :version", PlantillaDocumentoNotarial.class);
			query.setParameter("identificador", id.getIddocnot());
			query.setParameter("version", id.getInversion());
			plantillas = query.getResultList();
			if(plantillas == null || plantillas.size() <=0){
				throw new NotariaException("No se encontro ningun registro");
			}else{
				PlantillaDocumentoNotarial plantilla = plantillas.get(0);
				List<PlantillaDocumentoNotarialSubOperacion> suboperaciones = plantilla.getListaPlantillaDocumentoNotarialSubOperacion();
				for(int x=0;x<suboperaciones.size();x++){
					PlantillaDocumentoNotarialSubOperacion suboperacion = suboperaciones.get(x);
					Suboperacion subOperacion = suboperacion.getSuboperacion();
					if(subOperacion instanceof HibernateProxy){
						subOperacion = (Suboperacion)((HibernateProxy)subOperacion).getHibernateLazyInitializer().getImplementation();
						suboperacion.setSuboperacion(subOperacion);
						suboperacion.setPlantillaDocumentoNotarial(null);
					}
				}	
				return plantilla;
			}
		}catch(PersistenceException e) {
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Integer findMaxVersion(String iddocnot) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query q = em.createNativeQuery("SELECT MAX(inversion) FROM tbbsnm08 WHERE iddocnot='"+iddocnot+"'");
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
	public PlantillaDocumentoNotarial findDocumentoPublicado(String iddocnot) throws NotariaException {
		List<PlantillaDocumentoNotarial> lista = executeQuery("SELECT d FROM PlantillaDocumentoNotarial d WHERE d.id.iddocnot = ?1 " +
				"AND d.ispublicado = true", iddocnot);
		if(lista.size()>1)
			throw new NotariaException("Se encontró más de una coincidencia");
		else if(!lista.isEmpty())
			return lista.get(0);
		else 
			return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlantillaDocumentoNotarial buscarPorIdCompleto(String iddocnot,Integer inversion) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT docu FROM PlantillaDocumentoNotarial docu ");
			sql.append(" WHERE docu.id.iddocnot=:iddocnot ");
			sql.append(" AND docu.id.inversion=:inversion ");
			
			Query query = em
					.createQuery(sql.toString());
			query.setParameter("iddocnot",iddocnot);
			query.setParameter("inversion", inversion);
			
			List<PlantillaDocumentoNotarial> lista = query.getResultList();
			
			if(lista.size()==0){
				return null;
			}
			
			/** Sabemos que solo es un resultado **/
			PlantillaDocumentoNotarial documento = (PlantillaDocumentoNotarial) lista.get(0);			
			
			if(documento == null){
				return null;
			}
			
			return documento;
			
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public void delete(PlantillaDocumentoNotarial plantilla){
		EntityManager em = factory.createEntityManager();
		try{
			String sql = "DELETE FROM PlantillaDocumentoNotarial plantilla WHERE plantilla.id.iddocnot = :idplantilla AND plantilla.id.inversion = :version";
			Query query = em.createQuery(sql);
			query.setParameter("idplantilla", plantilla.getId().getIddocnot());
			query.setParameter("version", plantilla.getId().getInversion());
			em.getTransaction().begin();
			query.executeUpdate();
			em.getTransaction().commit();
		} catch(PersistenceException e) {
			System.out.print("=====> PlantillaDocumentoNotarial.delete .:: No se logro eliminar la plantilla.");
			e.printStackTrace(System.out);
		}finally {
			em.close();
		}
	}
	
	@Override
	public PlantillaDocumentoNotarial getPublishBySubOperacionId(String id,
			ElementoCatalogo locacion) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pdn FROM PlantillaDocumentoNotarial pdn ");
			sql.append(" WHERE pdn.suboperacion.idsuboperacion = :idsuboperacion");
			sql.append(" AND pdn.locacion = :locacion");
			sql.append(" AND pdn.id.inversion = ");
			sql.append(" (SELECT MAX(plan.id.inversion) FROM PlantillaDocumentoNotarial plan WHERE plan.suboperacion.idsuboperacion = :idsuboperacion AND plan.locacion = :locacion AND ispublicado = :ispublicado)");
			//sql.append(" AND pdn.id.inversion = 1");
			
			TypedQuery<PlantillaDocumentoNotarial> query = em.createQuery(sql.toString(), PlantillaDocumentoNotarial.class);
			query.setParameter("idsuboperacion", id);
			query.setParameter("ispublicado", true);
			query.setParameter("locacion", locacion);
			List<PlantillaDocumentoNotarial> resultados = query.getResultList();
			if (resultados.size() == 0) {
				return null;
			}
			return resultados.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public PlantillaDocumentoNotarial buscarPorSuboperacionLocacion(List<PlantillaDocumentoNotarialSubOperacion> suboperaciones, ElementoCatalogo locacion)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<PlantillaDocumentoNotarial> query = em.createNamedQuery("PlantillaDocumentoNotarial.findByLocacion", PlantillaDocumentoNotarial.class);			
			query.setParameter("idlocacion", locacion.getIdelemento());
			query.setParameter("publicado", true);			
			List<PlantillaDocumentoNotarial> listado = query.getResultList();
			if(listado.size()>0){
				boolean isRepetido = false;
				for(PlantillaDocumentoNotarial plantilla:listado){
					if(suboperaciones.size()>0){
						TypedQuery<PlantillaDocumentoNotarialSubOperacion> querySuboperacion = em.createNamedQuery("PlantillaDocumentoNotarialSubOperacion.findByPlantilla", PlantillaDocumentoNotarialSubOperacion.class);							
						querySuboperacion.setParameter("iddocumentonotarial", plantilla.getId().getIddocnot());
						querySuboperacion.setParameter("inversion", plantilla.getId().getInversion());
						List<PlantillaDocumentoNotarialSubOperacion> existe = querySuboperacion.getResultList();
						if(existe.size()==suboperaciones.size()){
							//solo hay que validar las suboperaciones
							
							isRepetido = true;
							for(PlantillaDocumentoNotarialSubOperacion subOperacion:suboperaciones){
								for (int index=0;index<existe.size();index++){
									isRepetido = subOperacion.getSuboperacion().getIdsuboperacion().equals(existe.get(index).getSuboperacion().getIdsuboperacion());
									if(isRepetido) break;
								}
							}								
							if(isRepetido){
								return plantilla;								
							}							
						} else {
							isRepetido = false;
						}
					}
				}

				return null;
			} else {
				return null;
			}
		}catch(PersistenceException e){
			throw new NotariaException("No se logro hacer la busqueda de Plantilla por suboperacion-locacion. Revise el log de salida.",e);
		}finally {
			em.close();
		}
	}
	
}
