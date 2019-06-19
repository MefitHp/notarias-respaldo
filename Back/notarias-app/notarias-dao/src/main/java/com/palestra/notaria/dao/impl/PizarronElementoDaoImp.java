package com.palestra.notaria.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.PizarronElementoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ComparecienteRepresentante;
import com.palestra.notaria.modelo.ControlFolios;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;

public class PizarronElementoDaoImp extends GenericDaoImpl<PizarronElemento, Integer> implements PizarronElementoDao {

	public static Long minnumeroescritura;

	public PizarronElementoDaoImp(Class<PizarronElemento> clase) {
		super(clase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void borrar(String idelementopizarron) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			PizarronElemento pizelemento = em.find(PizarronElemento.class, idelementopizarron);

			em.remove(pizelemento);
			tx.commit();
			return;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			try {
				tx.rollback();
			} catch (Exception e2) {
				throw new NotariaException(e.getCause());
			}

		} finally {
			em.close();
		}

	}

	@Override
	public List<PizarronElemento> findAll(Integer inicio, Integer total) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<PizarronElemento> respuesta;
		try {
			StringBuilder sql = new StringBuilder();
			// sql.append("SELECT
			// ugt.idusuariogrupotrabajo,ugt.usuario.cdusuario,ugt.usuario.dsiniciales,ugt.usuario.rol.dsnombre
			// FROM UsuarioGrupoTrabajo ugt");

			// EN CASO DE QUE NO SE DEFINA NI EL INICIO NI EL TOTAL DE ELEMENTOS
			// DE CALCULAN DE MANERA AUTOMÁTICA
			Long maxelemajuste;
			total = (total < 1) ? 60 : total;

			if (PizarronElementoDaoImp.minnumeroescritura == null) {
				StringBuilder maxsql = new StringBuilder();
				maxsql.append("SELECT min(pielem.numeroescritura) FROM PizarronElemento pielem");
				TypedQuery<Long> maxquery = em.createQuery(maxsql.toString(), Long.class);
				PizarronElementoDaoImp.minnumeroescritura = maxquery.getSingleResult();
			}

			if (inicio != 0 && PizarronElementoDaoImp.minnumeroescritura > inicio) {
				throw new NotariaException("No existen registros anteriores");
			}

			if (inicio < 10) {
				StringBuilder maxsql = new StringBuilder();
				maxsql.append("SELECT max(pielem.numeroescritura) FROM PizarronElemento pielem");
				TypedQuery<Long> maxquery = em.createQuery(maxsql.toString(), Long.class);
				Long maxelem = maxquery.getSingleResult();
				maxelemajuste = (maxelem - (maxelem % 10)) - 50;
				sql.append(
						"SELECT pielem FROM PizarronElemento pielem where pielem.numeroescritura BETWEEN :inicio AND :fin ORDER BY pielem.numeroescritura DESC");

			} else {
				maxelemajuste = (long) inicio;
				sql.append("SELECT pielem FROM PizarronElemento pielem where pielem.numeroescritura"
						+ ">=:inicio AND pielem.numeroescritura<:fin ORDER BY pielem.numeroescritura DESC");
			}
			// FIN DEL CALCULO AUTOMÁTICO

			TypedQuery<PizarronElemento> query = em.createQuery(sql.toString(), PizarronElemento.class);
			query.setParameter("inicio", maxelemajuste);
			query.setParameter("fin", maxelemajuste + total);

			if (total > 0) {
				query.setMaxResults(total);
			}
			respuesta = query.getResultList();
			return respuesta;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error al buscar los elementos del pizarron:" + e.getCause());
		} finally {
			em.close();
		}

	}
	
	@Override
	public void update(List<PizarronElemento> pizarrones, ControlFolios controlfolios) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;

		try {
			tx = em.getTransaction();
			tx.begin();

			em.persist(controlfolios);
			for(PizarronElemento p: pizarrones){
				em.merge(p);
			}
			
			tx.commit();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public void update(List<PizarronElemento> pizarrones) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;

		try {
			tx = em.getTransaction();
			tx.begin();
			for(PizarronElemento p: pizarrones){
				em.merge(p);
			}
			
			tx.commit();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	

	@Override
	public PizarronElemento save(PizarronElemento pizarron, ControlFolios controlfolios) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;

		try {
			tx = em.getTransaction();
			tx.begin();

			// Registrar el pizarron
			em.persist(pizarron);
			em.persist(controlfolios);

			tx.commit();
			return pizarron;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			if(tx.isActive())
				tx.rollback();
			throw new NotariaException(e.getCause());
		} catch(Exception e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getMessage());
		}finally {
			em.close();
		}
	}

	@Override
	public Date getFechaMenor() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT max(pielem.fecha) FROM PizarronElemento pielem");
			TypedQuery<Date> query = em.createQuery(sql.toString(), Date.class);
			return query.getSingleResult();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en getFechaMenor PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<PizarronElemento> buscarPendientes(String idabogado) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pielem FROM PizarronElemento pielem where status = :status ");
			if (idabogado != null && !idabogado.isEmpty()) {
				sql.append("AND pielem.idabogado.idusuario=:idabogado ");
			}
			sql.append("ORDER BY numeroescritura ASC");
			TypedQuery<PizarronElemento> query = em.createQuery(sql.toString(), PizarronElemento.class);
			query.setParameter("status", "lib-pendiente");
			if (idabogado != null && !idabogado.isEmpty()) {
				query.setParameter("idabogado", idabogado);
			}
			return query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en buscarPendientes PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Long getUltimaEscritura() throws NotariaException {
		// TODO Auto-generated method stub
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder maxsql = new StringBuilder();
			maxsql.append("SELECT max(pielem.numeroescritura) FROM PizarronElemento pielem");
			TypedQuery<Long> maxquery = em.createQuery(maxsql.toString(), Long.class);
			return maxquery.getSingleResult();

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en getUltimaEscritura PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}

	}

	@Override
	public PizarronElemento getXLibro(Libro libro) throws NotariaException {
		// TODO Auto-generated method stub
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append(
					"SELECT pielem FROM PizarronElemento pielem where pielem.idlibro = :libro ORDER BY pielem.numeroescritura ASC");
			TypedQuery<PizarronElemento> query = em.createQuery(sqlquery.toString(), PizarronElemento.class);
			query.setParameter("libro", libro);
			if (query.getResultList().size() > 0) {
				return query.getResultList().get(0);
			}
			return null;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en getXLibro PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public PizarronElemento buscarXescritura(Long numesc) throws NotariaException {
		// TODO Auto-generated method stub
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append(
					"SELECT pielem FROM PizarronElemento pielem where pielem.numeroescritura = :numeroescritura");
			TypedQuery<PizarronElemento> query = em.createQuery(sqlquery.toString(), PizarronElemento.class);
			query.setParameter("numeroescritura", numesc);
			if (query.getResultList().size() > 0) {
				return query.getResultList().get(0);
			}
			return null;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en buscarXescritura PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<PizarronElemento> buscarMayorXEscritura(Long numesc,String propiedad)throws NotariaException{		
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append(
					"SELECT pielem FROM PizarronElemento pielem where pielem.numeroescritura > :numeroescritura AND pielem.status = 'lib-pendiente'");
			sqlquery.append(propiedad);
			
			sqlquery.append(" ORDER BY pielem.numeroescritura");
			TypedQuery<PizarronElemento> query = em.createQuery(sqlquery.toString(), PizarronElemento.class);
			query.setParameter("numeroescritura", numesc);
			return query.getResultList();
		

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en buscarMayorXEscritura PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
		
	}
	
	@Override
	public List<PizarronElemento> obtenerAnteriores(Long numesc)throws NotariaException{		
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append(
					"SELECT pielem FROM PizarronElemento pielem where pielem.numeroescritura < :numeroescritura AND pielem.status = 'lib-pendiente'");
			
			sqlquery.append(" ORDER BY pielem.numeroescritura");
			TypedQuery<PizarronElemento> query = em.createQuery(sqlquery.toString(), PizarronElemento.class);
			query.setParameter("numeroescritura", numesc);
			return query.getResultList();
		

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en obtenerAnteriores PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
		
	}
	
	/**
	 * Este metodo revisa si un pizarron tiene como inmediato anterior y posterior escritura pasada, de ser asi no se puede editar
	 * @param numesc
	 * @return true si la escritura no tiene como inmediato anterior y posterior una escritura pasada, false de lo contrario
	 * @throws NotariaException
	 */
	@Override
	public Boolean isPizarronEditable(Integer numesc)throws NotariaException{		
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM PizarronElemento c WHERE c.numeroescritura = :numeroescritura+1"
					+ " AND c.status = 'lib-paso'");
			
			TypedQuery<Boolean> query = em.createQuery(sqlquery.toString(), Boolean.class);
			query.setParameter("numeroescritura", numesc);
			Boolean isSiguientePasada = query.getSingleResult();
			sqlquery = new StringBuilder();
			sqlquery.append("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM PizarronElemento c WHERE c.numeroescritura = :numeroescritura-1"
					+ "AND c.status = 'lib-paso'");
			query=em.createQuery(sqlquery.toString(),Boolean.class);
			query.setParameter("numeroescritura", numesc);
			Boolean isAnteriorPasada = query.getSingleResult();
			if(isSiguientePasada && isAnteriorPasada){
				return false;
			}else
				return true;
		

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en isPizarronEditable PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
		
	}
	
	@Override
	public Boolean existenPosterioresPasados(Long numesc)throws NotariaException{		
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append(
					"SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM PizarronElemento c where c.numeroescritura > :numeroescritura AND c.status = 'lib-paso'");
			
//			sqlquery.append(" ORDER BY pielem.numeroescritura");
			TypedQuery<Boolean> query = em.createQuery(sqlquery.toString(), Boolean.class);
			query.setParameter("numeroescritura", numesc);
			return query.getSingleResult();
		

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en existenPosterioresPasados PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
		
	}

	@Override
	public PizarronElemento obtenerSiguientePasada(Long numesc)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append(
					"select * from tbbsnm78 where numeroescritura > :numeroescritura and status='lib-paso' order by numeroescritura limit 1 ");

			Query query = em.createNativeQuery(sqlquery.toString(), PizarronElemento.class);
			query.setParameter("numeroescritura", numesc);
			return (PizarronElemento)query.getSingleResult();

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en obtenerSiguientePasada PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public PizarronElemento obtenerAnteriorPasada(Long numesc)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append(
					"select * from tbbsnm78 where numeroescritura < :numeroescritura and status='lib-paso' order by numeroescritura desc limit 1 ");

			Query query = em.createNativeQuery(sqlquery.toString(), PizarronElemento.class);
			query.setParameter("numeroescritura", numesc);
			return (PizarronElemento)query.getSingleResult();

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en obtenerAnteriorPasada PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PizarronElemento> getPendientesAntesDeSiguientePasada(Long numesc, boolean hasPosteriores)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append("select * from tbbsnm78 where numeroescritura > :numeroescritura and status='lib-pendiente' ");
			if(hasPosteriores){
				sqlquery.append("and numeroescritura <");
				sqlquery.append("(select numeroescritura from tbbsnm78 where numeroescritura > :numeroescritura and status='lib-paso' limit 1)");
			}
			sqlquery.append("order by numeroescritura");
			
//			sqlquery.append(" ORDER BY pielem.numeroescritura");
			Query query = em.createNativeQuery(sqlquery.toString(), PizarronElemento.class);
			query.setParameter("numeroescritura", numesc);
			return query.getResultList();

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en getPendientesAntesDeSiguientePasada PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PizarronElemento> getPendientesAntesDeAnteriorPasada(Long numesc)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sqlquery = new StringBuilder();
			sqlquery.append("select * from tbbsnm78 where numeroescritura < :numeroescritura and numeroescritura > ");
			sqlquery.append("(select numeroescritura from tbbsnm78 where numeroescritura < :numeroescritura and status='lib-paso' limit 1)");
			sqlquery.append("and status='lib-pendiente' order by numeroescritura desc");
			
//			sqlquery.append(" ORDER BY pielem.numeroescritura");
			Query query = em.createNativeQuery(sqlquery.toString(), PizarronElemento.class);
			query.setParameter("numeroescritura", numesc);
			return query.getResultList();

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ocurrió un error en getPendientesAntesDeSiguientePasada PizarronElementoDao:" + e.getCause());
		} finally {
			em.close();
		}
	}

	public static void main(String[]args){
		PizarronElementoDaoImp obj = new PizarronElementoDaoImp(PizarronElemento.class);
		try {
//			PizarronElemento dato = obj.obtenerSiguientePasada(80440L);
//			System.out.println(dato.getNumeroescritura());
			List<PizarronElemento> lista = obj.getPendientesAntesDeSiguientePasada(80463L,true);
			for(PizarronElemento pe:lista){
				System.out.println(pe.getNumeroescritura());
			}
			
		} catch (NotariaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
