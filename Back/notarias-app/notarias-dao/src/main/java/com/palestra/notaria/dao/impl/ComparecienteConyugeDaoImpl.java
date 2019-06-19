package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ComparecienteConyugeDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Firma;
import com.palestra.notaria.modelo.Persona;

public class ComparecienteConyugeDaoImpl extends
		GenericDaoImpl<ComparecienteConyuge, Integer> implements
		ComparecienteConyugeDao {

	public ComparecienteConyugeDaoImpl(){
		super(ComparecienteConyuge.class);
	}
	
	@Override
	public ComparecienteConyuge getRelacionInversa(ComparecienteConyuge comp) throws NotariaException{
		return executeQuery("SELECT c FROM ComparecienteConyuge c WHERE c.sujeto.idcompareciente = ?1", comp.getSujeto().getIdcompareciente()).get(0);
	}
	
	@Override 
	public ComparecienteConyuge findComparecienteConyuge(String idcompareciente) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		Query query = em.createNamedQuery("buscar", ComparecienteConyuge.class);
		query.setParameter("idcompareciente", idcompareciente);
		
		try{
			@SuppressWarnings("unchecked")
			List<ComparecienteConyuge> list = query.getResultList();
			if(list.size()>0){
				ComparecienteConyuge comconyu = list.get(0);
				Acto acto = null;
				comconyu.getConyugeCompra().setActo(acto);
				boolean hascompraacto = false;
				boolean hasactualacto = false;
				
				if(comconyu.getConyugeCompra()!=null && comconyu.getConyugeCompra().getActo()!=null){
					acto = comconyu.getConyugeCompra().getActo();
					hascompraacto = true;
				}
				if(comconyu.getConyugeActual()!=null && comconyu.getConyugeActual().getActo()!=null){
					acto = comconyu.getConyugeCompra().getActo();
					hasactualacto = true;
				}
				if(acto instanceof HibernateProxy){
					acto = (Acto) ((HibernateProxy) acto)
							.getHibernateLazyInitializer().getImplementation();
					
					if(hascompraacto){
						comconyu.getConyugeCompra().setActo(acto);
					}
					if(hasactualacto){
						comconyu.getConyugeActual().setActo(acto);
					}
				}
				
				return comconyu;
			}else
		return null;
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getMessage());
		}finally{
			em.close();
		}
	}
	
	@Override
	public ComparecienteConyuge findById(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			ComparecienteConyuge sujeto = em.find(ComparecienteConyuge.class, id);
			if(sujeto != null){
				Persona sujetoPersona = sujeto.getSujeto().getPersona();
				Persona conyugeActualPersona = null;
				Persona conyugeCompraPersona = null;
				if(sujeto.getConyugeActual() != null){
					conyugeActualPersona = sujeto.getConyugeActual().getPersona();
					sujeto.getConyugeActual().setActo(null);
					sujeto.getConyugeActual().setRegistroRi(null);
					sujeto.getConyugeActual().setTipoCompareciente(null);
				}
				if(sujeto.getConyugeCompra() != null){
					conyugeCompraPersona = sujeto.getConyugeCompra().getPersona();
					sujeto.getConyugeCompra().setActo(null);
					sujeto.getConyugeCompra().setRegistroRi(null);
					sujeto.getConyugeCompra().setTipoCompareciente(null);
				}
				if(sujetoPersona instanceof HibernateProxy){
					sujetoPersona = (Persona) ((HibernateProxy) sujetoPersona).getHibernateLazyInitializer().getImplementation();
					sujetoPersona.setTipopersona(null);
					sujetoPersona.setNacionalidad(null);
				}
				sujetoPersona.setTipopersona(null);
				sujetoPersona.setNacionalidad(null);
				
				if(conyugeActualPersona instanceof HibernateProxy){
					conyugeActualPersona = (Persona) ((HibernateProxy) conyugeActualPersona).getHibernateLazyInitializer().getImplementation();
					conyugeActualPersona.setTipopersona(null);
					conyugeActualPersona.setNacionalidad(null);
				}	
	
				if(conyugeCompraPersona instanceof HibernateProxy){
					conyugeCompraPersona = (Persona) ((HibernateProxy) conyugeCompraPersona).getHibernateLazyInitializer().getImplementation();
					conyugeCompraPersona.setTipopersona(null);
 					conyugeCompraPersona.setNacionalidad(null);
				}
				
				if(conyugeCompraPersona !=null){
					conyugeCompraPersona.setTipopersona(null);
					conyugeCompraPersona.setNacionalidad(null);
				}
				
				sujeto.getSujeto().setTipoCompareciente(null);
				sujeto.getSujeto().setFirma(null);
				sujeto.getSujeto().setActo(null);
								
					if (sujeto.getConyugeCompra()!=null && sujeto.getConyugeCompra().getFirma() instanceof HibernateProxy) {
						Firma firma = null;
						firma = sujeto.getConyugeCompra().getFirma();
						firma = (Firma) ((HibernateProxy) firma)
								.getHibernateLazyInitializer().getImplementation();
						Compareciente comp = new Compareciente();
						comp.setIdcompareciente(firma.getCompareciente().getIdcompareciente());
						firma.setCompareciente(comp);
						Escritura tmpEscritura = new Escritura();
						
						sujeto.getConyugeCompra().setFirma(firma);
					}
					
					if (sujeto.getConyugeActual() !=null && sujeto.getConyugeActual().getFirma() instanceof HibernateProxy) {
						Firma firma = null;
						firma = sujeto.getConyugeActual().getFirma();
						firma = (Firma) ((HibernateProxy) firma)
								.getHibernateLazyInitializer().getImplementation();
						Compareciente comp = new Compareciente();
						comp.setIdcompareciente(firma.getCompareciente().getIdcompareciente());
						firma.setCompareciente(comp);
						Escritura tmpEscritura = new Escritura();
						sujeto.getConyugeActual().setFirma(firma);
					}
				
				
				sujeto.getSujeto().setRegistroRi(null);
				
				
				sujeto.getSujeto().setPersona(sujetoPersona);
				if(sujeto.getConyugeActual() != null){
					sujeto.getConyugeActual().setPersona(conyugeActualPersona);
				}
				if(sujeto.getConyugeCompra() != null){
					sujeto.getConyugeCompra().setPersona(conyugeCompraPersona);
				}
				return sujeto;
			}else
				return null;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
}
