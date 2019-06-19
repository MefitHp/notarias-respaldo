package com.palestra.notaria.uif.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.palestra.notaria.uif.core.models.Persona;
import com.palestra.notaria.uif.core.models.Uif;
import com.palestra.notaria.uif.exceptions.NotariaException;

public class UifDaoImp extends GenericDaoImpl<Uif, Integer> implements UifDao {

	private static Long result;

	
	public UifDaoImp() {
		super(Uif.class);
		// TODO Auto-generated constructor stub
		result = 20L;

	}

	public Long getTotalUifs() throws NotariaException {
		// TODO Auto-generated method stub
		EntityManager em = factory.createEntityManager();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(u) FROM Uif u");
		try {
			TypedQuery<Long> tq = em.createQuery(sb.toString(),Long.class);
			return (Long) tq.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			throw new NotariaException("Ocurrió un erro al solicitar los UIFS");
		} finally {
			em.close();
		}
		
	}

	public Long getTotalPage() throws NotariaException {
		Long respuesta = 0L;
		Long totaluifs = getTotalUifs();
		
		respuesta = totaluifs / result;
		if(totaluifs%result>0){
			respuesta++;
		}
		
		 return respuesta;
	}

	public Uif findByNombrePersona(String nombrecompleto) throws NotariaException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Uif> buscarXEscritura(Long escritura) throws NotariaException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Uif> buscarXPagina(Long pagina) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT u FROM Uif u ORDER BY u.escritura DESC");
		try {
			TypedQuery<Uif> tq = em.createQuery(sb.toString(),Uif.class);
			int setFirstResult = pagina.intValue() *result.intValue();
			tq.setFirstResult(setFirstResult);
			tq.setMaxResults(result.intValue());
			List<Uif> resultado = tq.getResultList();
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotariaException("Ocurrió un erro al solicitar los UIFS");
		}finally {
			em.close();
		}
		
	}
	
	public void borrar(Uif uif) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		uif = em.find(uif.getClass(), uif.getId());
		try {
			if (uif != null) {
				StringBuilder idpersona = new StringBuilder(uif.getPersona().getIdpersona());
				em.remove(uif);
				
				TypedQuery<Uif> query = em.createQuery("Select u from Uif u where u.persona.idpersona =:idpersona",Uif.class);
				query.setParameter("idpersona", idpersona.toString());
				int uifspersona = query.getResultList().size();
				// SI YA NO  HAY UIF ASOCIADOS ELIMINO A LA PERSONA TENER CUIDADO EN LA INTEGRACIÓN CON GONNOTARIAS
				if(uifspersona<1){
					Persona persona = em.find(Persona.class, idpersona.toString());
					em.remove(persona);
				}
				
			} else {
				System.out.println("=====> The object does not exists in the unit persistence " + uif.toString());
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			em.getTransaction().rollback();
			// throw new NotariaException(String.format("Something went wrong in
			// persisting data , view server log for details [delete %s at
			// %s].", obj.toString(), dateFormat.format(new Date())),
			// e.getCause());
			throw new NotariaException("Ocurrió un error al eliminar, causado por: " + e.getCause());
		} finally {
			em.close();
		}
		
		
	}

	public List<Uif> buscar(String tipo,String param) throws NotariaException {
		
		StringBuilder query = new StringBuilder("SELECT u FROM Uif u ");
		EntityManager em = factory.createEntityManager();
		try {
			
		
		boolean islong = false;		
		switch (tipo) {
		case "escritura":
			query.append("WHERE u.escritura=:parametro");
			islong = true;
			break;
		case "rfc":	
			query.append("WHERE u.persona.dsrfc='"+param+"'");
			break;
		case "curp":
			query.append("WHERE u.persona.dscurp="+param+"'");
			break;
		case "fechas":
				//todo: Falta implementacion
			break;

		default:
			query.append("WHERE u.persona.dsnombrecompleto LIKE '%"+param+"%'");
			}
		
		TypedQuery<Uif> Tquery = em.createQuery(query.toString(),Uif.class);
		if(islong){
			Long parametroLong = Long.parseLong(param,10);
			Tquery.setParameter("parametro",parametroLong);
		}
		
		
			return Tquery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotariaException();
		}finally{
			em.close();
		}

	}

}
