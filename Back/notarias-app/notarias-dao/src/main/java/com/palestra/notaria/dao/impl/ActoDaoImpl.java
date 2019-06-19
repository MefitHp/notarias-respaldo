package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ActoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.Suboperacion;

public class ActoDaoImpl extends GenericDaoImpl<Acto, Integer> implements
		ActoDao {

	public ActoDaoImpl() {
		super(Acto.class);
	}
	
	@Override
	public Acto desactivarActo(String idacto) throws NotariaException{
		ActoDao actoDao = new ActoDaoImpl();
		Acto acto = actoDao.buscarPorIdCompleto(idacto);
		if(acto!=null){
			acto.setIsactivo(Boolean.FALSE);
			acto = actoDao.update(acto);
			return acto;
		}else{
			return acto;
		}
	}

	@Override
	public List<Acto> filterActoByIdExpediente(String idExpediente) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Acto> query = em.createQuery("SELECT a FROM Acto a WHERE a.expediente.idexpediente = :idexpediente", Acto.class);
			query.setParameter("idexpediente", idExpediente);
			List<Acto> actos = query.getResultList();
			for(Acto acto:actos){
				Suboperacion subop = acto.getSuboperacion();
				if(subop instanceof HibernateProxy){
					subop = (Suboperacion) ((HibernateProxy)subop).getHibernateLazyInitializer().getImplementation();
					Operacion op = subop.getOperacion();
					if(op instanceof HibernateProxy){
						op = (Operacion)((HibernateProxy)op).getHibernateLazyInitializer().getImplementation();
						subop.setOperacion(op);
					}
				}
				acto.setSuboperacion(subop);
				acto.setExpediente(null);
			}
			return actos;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public String getExpedienteIdByActoId(String idActo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<String> query = em.createQuery("SELECT a.expediente.idexpediente FROM Acto a WHERE a.idacto = :idacto", String.class);
			query.setParameter("idacto", idActo);
			List<String> lista = query.getResultList();
			if (lista.size() == 0) {
				return null;
			}else{
				return lista.get(0);
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Acto buscarPorIdCompleto(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			String sql = "SELECT a FROM Acto a WHERE a.idacto = :idacto";
			TypedQuery<Acto> query = em.createQuery(sql.toString(), Acto.class);
			query.setParameter("idacto", id);
			List<Acto> lista = query.getResultList();
			if (lista.size() == 0) {
				return null;
			} else {
				Acto acto = lista.get(0);
				Suboperacion suboperacion = acto.getSuboperacion();
				if (suboperacion instanceof HibernateProxy) {
					suboperacion = (Suboperacion) ((HibernateProxy) suboperacion)
						.getHibernateLazyInitializer().getImplementation();
				}
				acto.setSuboperacion(suboperacion);
				return acto;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public Operacion getOperacionPorActo(String acto)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try {
			String sql = "SELECT a.suboperacion.operacion FROM Acto a WHERE a.idacto= :acto";
			TypedQuery<Operacion> query = em.createQuery(sql.toString(), Operacion.class);
			query.setParameter("acto", acto);
			Operacion operacion = query.getSingleResult();
			return operacion;
		}catch(NonUniqueResultException e){
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		}catch(NoResultException e){
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		}catch(PersistenceException e){
			e.printStackTrace();
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<Acto> findAll() throws NotariaException {
		List<Acto> actos = super.findAll();
		List<Acto> result = new ArrayList<Acto>();
		
			for(Acto act:actos){
				act.setExpediente(null);
				act.setSuboperacion(null);
				result.add(act);
			}
		return result;
	}
}
