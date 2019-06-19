package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.DatoPagoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DatoPago;
import com.palestra.notaria.modelo.ElementoCatalogo;

public class DatoPagoDaoImpl extends GenericDaoImpl<DatoPago, Integer> implements DatoPagoDao{

	public DatoPagoDaoImpl() {
		super(DatoPago.class);
	}
	
	@Override
	public Boolean guardarDatoPago(DatoPago datoPago, String idDatoFisca,
			ElementoCatalogo status) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query=null;
		StringBuilder sql = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			// Registrar el datoPago
			em.persist(datoPago);

			sql = new StringBuilder();
			sql.append(" update DatoFiscalPago set status.idelemento = :idelemento ");
			sql.append(" where iddatofiscapago = :iddatofiscapago ");
			query = em.createQuery(sql.toString());
			query.setParameter("idelemento", status.getIdelemento());
			query.setParameter("iddatofiscapago", idDatoFisca);
			query.executeUpdate();
			
			tx.commit();
			return true;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}
	
	@Override
	public Double sumaImportePagoPorSoliciutd(String idSolPago) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT SUM(importepago) FROM DatoPago ");
			sql.append(" WHERE datofiscapago.solicitudPago.idsolpago = :idsolpago");
						
			Query query = em.createQuery(sql.toString());
			query.setParameter("idsolpago", idSolPago);
			
			@SuppressWarnings("unchecked")
			List<Double> lista  = (List<Double>) query.getResultList();
			if(lista==null || lista.isEmpty()){
				return null;
			}
			Double total = lista.get(0);
			if(total==null){
				return null;
			}
			return total;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
}
