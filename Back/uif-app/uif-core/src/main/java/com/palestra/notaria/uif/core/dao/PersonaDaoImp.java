package com.palestra.notaria.uif.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.uif.core.models.Persona;
import com.palestra.notaria.uif.exceptions.NotariaException;



public class PersonaDaoImp extends GenericDaoImpl<Persona, Integer> implements PersonaDao{

	public PersonaDaoImp() {
		super(Persona.class);
		// TODO Auto-generated constructor stub
	}

	
	public List<Persona> buscarPersonaPorNombre(String subCadena) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery("SELECT per FROM Persona per WHERE per.dsnombrecompleto LIKE :nombre ");
			query.setParameter("nombre", "%"+subCadena+"%");
			List<Persona> pers = query.getResultList();
			for(Persona per:pers){
				System.out.println("persona found "+per.getDsnombre());
			/*	this.lazyInitializers(per);
//				@omarete OBTENER DOMICILIO LO PIDIO EL VIC (por si me cagan (con autorizacion del cafaray (rey mides)))
				ComparecienteDaoImpl compa = new ComparecienteDaoImpl();
				List<Compareciente> comp = compa.getByIdPersona(per.getIdpersona());
				if(comp.size()>0){
					per.setDomicilio(comp.get(0).getDomicilio());
				}*/
			}
			return query.getResultList();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	public List<Persona> obtenerListaCompleta() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery("from Persona");
	
			@SuppressWarnings("unchecked")
			List<Persona> lista = query.getResultList();
	
			/*for(Persona p:lista){
				this.lazyInitializers(p);
			}*/
			
			return lista;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}

	}

	public Persona buscarPorIdCompleto(Persona persona) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery("from Persona where idpersona = ?1");
			query.setParameter(1, persona.getIdpersona());
	
			Persona p = (Persona) query.getSingleResult();
//			SE ELIMINO EL SETEO DEL COMPARECIENTE A PERSONA
			//this.lazyInitializers(p);
	
			return p;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	
	
	/*public void lazyInitializers(Persona persona){
		ElementoCatalogo nacionalidad;
		ElementoCatalogo tipoPersona;

		tipoPersona = persona.getTipopersona();
		if (tipoPersona instanceof HibernateProxy) {
			tipoPersona = (ElementoCatalogo) ((HibernateProxy) tipoPersona).getHibernateLazyInitializer().getImplementation();
			tipoPersona.setCatalogo(null);
		}
		persona.setTipopersona(tipoPersona);

		nacionalidad = persona.getNacionalidad();
		if (nacionalidad instanceof HibernateProxy) {
			nacionalidad = (ElementoCatalogo) ((HibernateProxy) nacionalidad)
					.getHibernateLazyInitializer().getImplementation();
			// nacionalidad.setTbcfgm07s(null);
			nacionalidad.setCatalogo(null);

		}
		persona.setNacionalidad(nacionalidad);

	}*/

	public void eliminaPersona(Persona persona) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			
			// Segundo eliminar compareciente
			em.remove(em.contains(persona) ? persona : em.merge(persona));

			tx.commit();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		
	}

	

}
