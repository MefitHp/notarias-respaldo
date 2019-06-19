package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ComparecienteRepresentanteDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteRepresentante;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Firma;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.TipoCompareciente;

public class ComparecienteRepresentanteDaoImpl extends
		GenericDaoImpl<ComparecienteRepresentante, Integer> implements
		ComparecienteRepresentanteDao {

	public ComparecienteRepresentanteDaoImpl() {
		super(ComparecienteRepresentante.class);
	}
	
	@Override
	public void delete(ComparecienteRepresentante obj) throws NotariaException {
		System.out.println("CUSTOM DELETE");
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			
			ComparecienteRepresentante corep = em.find(ComparecienteRepresentante.class, obj.getIdcomrep());
			
			System.out.println("ID OBJ "+obj.getIdcomrep());
		Compareciente rep =  corep.getRepresentante();
		if(rep instanceof HibernateProxy){
			rep = (Compareciente) ((HibernateProxy) rep).getHibernateLazyInitializer().getImplementation();
			
			TypedQuery<Firma> firmars = em.createNamedQuery("Firma.findByCompareciente", Firma.class);
			firmars.setParameter("identificador", rep.getIdcompareciente());
			List<Firma> frs = firmars.getResultList();
			if(frs.size()>0){
				Firma firma = frs.get(0);
				firma = em.find(Firma.class, firma.getIdfirma());
				em.remove(firma);
				Compareciente tmprep = em.find(Compareciente.class, rep.getIdcompareciente());
				tmprep.setFirma(null);
			}
			
		}
		Query query = em.createQuery("DELETE FROM ComparecienteRepresentante c WHERE idcomrep = ?1");
		query.setParameter(1, obj.getIdcomrep());
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
	public ComparecienteRepresentante findBy(Compareciente representado, Compareciente representante) throws NotariaException{
		List<ComparecienteRepresentante> list = executeQuery("SELECT cr FROM ComparecienteRepresentante cr WHERE cr.representante.idcompareciente = ?1 " +
				"AND cr.representado.idcompareciente = ?2", representante.getIdcompareciente(),representado.getIdcompareciente());
		if(list.size()>0){
			return list.get(0);
		}else
			return null;
	}

	@Override
	public Compareciente calculaRepresentadoPorIdRepresentante(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em
					.createQuery("SELECT cr.representado FROM ComparecienteRepresentante cr WHERE cr.representante.idcompareciente=:id");
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
	public List<Compareciente> findByRepresentadoId(String id) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {
			Query query = em
					.createQuery("SELECT cr.representante FROM ComparecienteRepresentante cr WHERE cr.representado.idcompareciente=:id");
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
						
						firma = representante.getFirma();
						if (firma instanceof HibernateProxy) {
							firma = (Firma) ((HibernateProxy) firma)
									.getHibernateLazyInitializer()
									.getImplementation();
							Compareciente comp = new Compareciente();
							comp.setIdcompareciente(firma.getCompareciente().getIdcompareciente());
							firma.setCompareciente(comp);
						
							
						}
						representante.setFirma(firma);
						representante.setActo(acto);
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
