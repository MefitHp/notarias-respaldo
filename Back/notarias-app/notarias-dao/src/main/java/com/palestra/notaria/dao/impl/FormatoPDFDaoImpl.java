package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.FormatoPDFDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.modelo.FormatoPDFDetalle;

public class FormatoPDFDaoImpl extends GenericDaoImpl<FormatoPDF, Integer> implements FormatoPDFDao {


	public FormatoPDFDaoImpl(){
		super(FormatoPDF.class);		
	}
	
	
	@Override
	public List<FormatoPDF> findAll() throws NotariaException {
		List<FormatoPDF> lista = super.findAll();
		for(FormatoPDF formato:lista){
			for(FormatoPDFDetalle detalle: formato.getDetalleList()){
				detalle.setIdftopdf(null);
			}
		}
		return lista;
	}
	
	@Override
	public FormatoPDF findById(String id) throws NotariaException {
		FormatoPDF formato = super.findById(id);
		if(formato == null){
			return null;
		}
		for(FormatoPDFDetalle detalle:formato.getDetalleList()){
			detalle.setIdftopdf(null);
		}
		return formato;
	}
	
	@Override
	public List<FormatoPDFDetalle> formatoDetalle(String identificador)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<FormatoPDFDetalle> detalles = em.createNamedQuery("FormatoPDF.findDetalle", FormatoPDFDetalle.class);		
			detalles.setParameter("identificador", identificador);
			return detalles.getResultList();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
				em.close();
		}
	}



	@Override
	public void eliminaDetalles(String identificador) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try{
			tx.begin();
			//TypedQuery<Integer> query = em.createNamedQuery("FormatoPDF.EliminarDetalle", Integer.class);
			Query query = em.createQuery("DELETE FROM FormatoPDFDetalle d WHERE d.idftopdf.identificador = :identificador");
			query.setParameter("identificador", identificador);
			query.executeUpdate();
			tx.commit();
		}catch(TransactionRequiredException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());		
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally{
				em.close();
		}
	}


	@Override
	public FormatoPDF buscarXNombre(String nombre)throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		FormatoPDF respuesta = null;
		try{
			
			TypedQuery<FormatoPDF> query = em.createQuery("SELECT pdf FROM FormatoPDF pdf WHERE pdf.dstitulo=:nombre ORDER BY pdf.tmstmp DESC",FormatoPDF.class);
			query.setParameter("nombre",nombre);
			List<FormatoPDF> lista = query.getResultList();
			if(lista.size()>0){
				respuesta = lista.get(0);
			}
		}catch(TransactionRequiredException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());		
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally{
				em.close();
		}
		return respuesta;
	}

}
