package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ComparecienteHijoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteHijo;
import com.palestra.notaria.modelo.Firma;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.TipoCompareciente;

public class ComparecienteHijoDaoImpl extends
		GenericDaoImpl<ComparecienteHijo, Integer> implements
		ComparecienteHijoDao {

	public ComparecienteHijoDaoImpl() {
		super(ComparecienteHijo.class);
	}
	
	@Override
	public void delete(ComparecienteHijo obj) throws NotariaException {
		System.out.println("CUSTOM DELETE");
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			System.out.println("ID OBJ "+obj.getIdcomhijo());
		Query query = em.createQuery("DELETE FROM ComparecienteHijo c WHERE idcomhijo = ?1");
		query.setParameter(1, obj.getIdcomhijo());
		query.executeUpdate();
		tx.commit();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
		
	}
	@Override
	public ComparecienteHijo findBy(Compareciente compareciente, Compareciente hijo) throws NotariaException{
		List<ComparecienteHijo> list = executeQuery("SELECT cr FROM ComparecienteHijo cr WHERE cr.hijo.idcompareciente = ?1 " +
				"AND cr.compareciente.idcompareciente = ?2", hijo.getIdcompareciente(),compareciente.getIdcompareciente());
		if(list.size()>0){
			return list.get(0);
		}else
			return null;
	}

	@Override
	public Compareciente calculaComparecientePorIdHijo(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em
					.createQuery("SELECT cr.representado FROM ComparecienteHijo cr WHERE cr.representante.idcompareciente=:id");
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<Compareciente> lista = query.getResultList();

			if (lista.size() == 0) {
				return null;
			}
			Persona persona = null;
			TipoCompareciente tipoCompareciente = null;
			Acto acto = null;

			Compareciente representado = lista.get(0);
			if (representado != null) {
				
				persona = representado.getPersona();
				if(representado.getContacto()!=null){
					representado.getContacto().getPersona().setNacionalidad(null);
					representado.getContacto().getPersona().setTipopersona(null);
				}
				if (persona instanceof HibernateProxy) {
					persona = (Persona) ((HibernateProxy) persona)
							.getHibernateLazyInitializer()
							.getImplementation();
					
				}
				persona.setTipopersona(null);
				persona.setNacionalidad(null);
				representado.setPersona(persona);

				tipoCompareciente = representado.getTipoCompareciente();
				if (tipoCompareciente instanceof HibernateProxy) {
					tipoCompareciente = (TipoCompareciente) ((HibernateProxy) tipoCompareciente)
							.getHibernateLazyInitializer()
							.getImplementation();
				}
				representado.setTipoCompareciente(tipoCompareciente);
				acto = representado.getActo();
				if (acto instanceof HibernateProxy) {
					acto = (Acto) ((HibernateProxy) acto)
							.getHibernateLazyInitializer()
							.getImplementation();
					acto.setSuboperacion(null);
					acto.setExpediente(null);
				}
				representado.setActo(acto);
				representado.setRegistroRi(null);
			}
			return representado;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}
	
	@Override
	public List<Compareciente> findByEsposaId(String id) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {
			Query query = em
					.createQuery("SELECT cr.hijo FROM ComparecienteHijo cr WHERE cr.compareciente.idcompareciente=:id");
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<Compareciente> lista = query.getResultList();

			if (lista.size() == 0) {
				return null;
			}
			Persona persona = null;
			TipoCompareciente tipoCompareciente = null;
			Acto acto = null;
			Firma firma = null;

			for (Compareciente representante : lista) {
				if (representante != null) {
						
						persona = representante.getPersona();
						if(representante.getContacto()!=null){
							representante.getContacto().getPersona().setNacionalidad(null);
							representante.getContacto().getPersona().setTipopersona(null);
						}
						if (persona instanceof HibernateProxy) {
							persona = (Persona) ((HibernateProxy) persona)
									.getHibernateLazyInitializer()
									.getImplementation();
							
						}
						persona.setTipopersona(null);
						persona.setNacionalidad(null);
						representante.setPersona(persona);

						tipoCompareciente = representante.getTipoCompareciente();
						if (tipoCompareciente instanceof HibernateProxy) {
							tipoCompareciente = (TipoCompareciente) ((HibernateProxy) tipoCompareciente)
									.getHibernateLazyInitializer()
									.getImplementation();
						}
						representante.setTipoCompareciente(tipoCompareciente);
						acto = representante.getActo();
						if (acto instanceof HibernateProxy) {
							acto = (Acto) ((HibernateProxy) acto)
									.getHibernateLazyInitializer()
									.getImplementation();
							acto.setSuboperacion(null);
							acto.setExpediente(null);
						}
						representante.setActo(acto);
						
						firma = representante.getFirma();
						if(firma instanceof HibernateProxy){
							firma = (Firma) ((HibernateProxy)firma).getHibernateLazyInitializer().getImplementation();
							Compareciente c = new Compareciente();
							c.setIdcompareciente(representante.getIdcompareciente());
							firma.setCompareciente(c);
							
						}
						
						representante.setFirma(firma);
						
						
						representante.setRegistroRi(null);
					}
			}

			return lista;

		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}


	
}
