package com.palestra.notaria.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import com.palestra.notaria.dao.DatoFiscalPagoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.BitacoraGeneral;
import com.palestra.notaria.modelo.DatoFiscalPago;
import com.palestra.notaria.modelo.DatoPago;

public class DatoFiscalPagoDaoImpl extends GenericDaoImpl<DatoFiscalPago, Integer> implements DatoFiscalPagoDao{

	public DatoFiscalPagoDaoImpl() {
		super(DatoFiscalPago.class);
	}

	@Override
	public List<DatoFiscalPago> buscarPagadoresAnticipo(String idsolanticipo) throws NotariaException{
		EntityManager em = factory.createEntityManager();		
		List<DatoFiscalPago> lista = null;
		try {
			TypedQuery<DatoFiscalPago> query = em.createQuery("FROM DatoFiscalPago WHERE solicitudPago.idsolpago = :idsolanticipo ORDER BY tmstmp", DatoFiscalPago.class);
			query.setParameter("idsolanticipo", idsolanticipo);
			lista = query.getResultList();
			if(lista!=null){
				for(DatoFiscalPago dfp:lista){
					dfp.setListaDatoPago(null);
					dfp.setSolicitudPago(null);
				}
			}
			return lista;	
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public boolean eliminarPagador(DatoFiscalPago dfp, String idusuario, String idexpediente) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		boolean b = false;
		try {
			em.getTransaction().begin();
			TypedQuery<DatoPago> query = em.createQuery("from DatoPago where datofiscapago.iddatofiscapago = :iddatofiscalpago", DatoPago.class);
			query.setParameter("iddatofiscalpago", dfp.getIddatofiscapago());
			List<DatoPago> lista = query.getResultList();
			BitacoraGeneral bg = null; 
			if (lista != null && !lista.isEmpty()) {				
				for (DatoPago dp : lista) {					
//					TODO: cambiar forma de eliminar sin merge
					em.remove(em.contains(dp) ? dp : em.merge(dp));
					bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "DatoPago", "Eliminar", "Eliminacion de DatoPago ");
					em.persist(bg);
				}
			}			
			em.remove(em.contains(dfp) ? dfp : em.merge(dfp));			
			bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "DatoFiscalPago", "Eliminar", "Eliminacion de DatoFiscalPago ");
			em.persist(bg);			
			em.getTransaction().commit();
			b = true;
			return b;
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		
	}

	@Override
	public Integer obtenerNumeroPagador(String idsolicitud) throws NotariaException{
		EntityManager em = factory.createEntityManager();		
		try {
			Integer retorno = -1;
			TypedQuery<Integer> query = em.createQuery("select max(innumpago) from DatoFiscalPago where solicitudPago.idsolpago = :idsolpago", Integer.class);
			query.setParameter("idsolpago", idsolicitud);
			if(!query.getResultList().isEmpty()){
				retorno = query.getSingleResult();				
			}
			return retorno;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
}