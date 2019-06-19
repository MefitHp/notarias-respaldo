package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.UbicacionVarEstaticaDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.UbicacionVarEstatica;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.util.GeneradorId;

public class UbicacionVarEstaticaDaoImpl extends GenericDaoImpl<UbicacionVarEstatica, Integer>
		implements UbicacionVarEstaticaDao {

	static Logger logger = Logger.getLogger(UbicacionVarEstaticaDaoImpl.class);

	public UbicacionVarEstaticaDaoImpl() {
		super(UbicacionVarEstatica.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> obtenerValorVarEstaticas(UbicacionVarEstatica ubicacionVar, Object valorFiltro)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder buffer = new StringBuilder();
			buffer.append(" SELECT obj." + ubicacionVar.getDspropiedad());
			buffer.append(" FROM " + ubicacionVar.getDsentidad() + " obj");

			if (valorFiltro != null) {
				buffer.append(" WHERE obj." + ubicacionVar.getDsfiltro() + " = :valorFiltro");

				/** Ejecutar query con clausula where **/
				Query q = em.createQuery(buffer.toString());
				q.setParameter("valorFiltro", valorFiltro);

				List<Object> resultado = q.getResultList();

				return resultado;

			}

			/** Ejecutar query sin clausula WHERE **/
			Query q = em.createQuery(buffer.toString());
			return q.getResultList();

		} catch (IllegalArgumentException iae) {
			logger.info("Error de parametros de query. Revisar tabla Ubicacion Variables");
			iae.printStackTrace(System.out);
			throw new NotariaException(iae.getCause());
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}

	/**
	 * Obtiene una lista de valores de acuerdo al query enviado
	 * 
	 * @throws NotariaException
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerValoresFromHqlQuery(String query) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Object[]> resultados = null;

		try {
			Query q = em.createQuery(query);
			// Se retorna una lista, en caso de no existir datos marca un error
			resultados = (List<Object[]>) q.getResultList();
			return resultados;

		} catch (IllegalArgumentException iae) {
			logger.info("Error de hql query.");
			System.out.println(iae);
			iae.printStackTrace(System.out);
			throw new NotariaException(iae.getCause());
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getSingleValFromHqlQuery(String query) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Object> resultados = null;
		try {
			Query q = em.createQuery(query);
			// Se retorna una lista, en caso de no existir datos marca un error
			resultados = (List<Object>) q.getResultList();
			if (resultados.size() == 0) {
				return null;
			}
			return resultados;
		} catch (IllegalArgumentException iae) {
			logger.info("Error de hql query.");
			System.out.println(iae);
			iae.printStackTrace(System.out);
			throw new NotariaException(iae.getCause());
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> obtenerValoresFromSqlQuery(String query) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Object[]> resultados = null;

		try {
			Query q = em.createNativeQuery(query);
			// Se retorna una lista, en caso de no existir datos marca un error
			resultados = (List<Object[]>) q.getResultList();

			if (resultados.size() == 0) {
				return null;
			}

			return resultados;

		} catch (IllegalArgumentException iae) {
			logger.info("Error de  sql query. ");
			iae.printStackTrace(System.out);
			throw new NotariaException(iae.getCause());
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UbicacionVarEstatica> findAllComplete() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em.createQuery("SELECT uve FROM UbicacionVarEstatica uve");
			List<UbicacionVarEstatica> lista = query.getResultList();
			Variable variable = null;
			for (UbicacionVarEstatica uve : lista) {
				variable = uve.getVariable();
				if (variable instanceof HibernateProxy) {
					variable = (Variable) ((HibernateProxy) variable).getHibernateLazyInitializer().getImplementation();
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
	public UbicacionVarEstatica guardar(UbicacionVarEstatica ubicacionVariable) throws NotariaException {
		String id = GeneradorId.generaId(ubicacionVariable);
		ubicacionVariable.setIdvarest(id);
		this.save(ubicacionVariable);
		return this.findById(id);
	}

	@Override
	public UbicacionVarEstatica actualizar(UbicacionVarEstatica ubicacionVariable) throws NotariaException {
		String id = ubicacionVariable.getIdvarest();
		this.update(ubicacionVariable);
		return this.findById(id);
	}

	@Override
	public List<String> listarEntidades() throws NotariaException {
		List<String> entidades = new ArrayList<>();
		entidades.add(Escritura.class.getName());
		entidades.add(Acto.class.getName());
		// TODO falta incluir el elemento objeto
		return entidades;
	}

	@Override
	public UbicacionVarEstatica findByVariable(String idvariable) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<UbicacionVarEstatica> query = em.createNamedQuery("UbicacionVarEstatica.findByVariable",
					UbicacionVarEstatica.class);
			query.setParameter("idvariable", idvariable);
			List<UbicacionVarEstatica> calculos = query.getResultList();
			if (calculos != null && calculos.size() > 0) {
				// regresamos el primero que nos encontremos, ya que siempre
				// debe existir de 0 a 1
				calculos.get(0).setVariable(null);
				return calculos.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public UbicacionVarEstatica findByNombre(String nombre) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<UbicacionVarEstatica> query = em.createNamedQuery("UbicacionVarEstatica.findByNombre",
					UbicacionVarEstatica.class);
			query.setParameter("dsnombre", nombre);
			List<UbicacionVarEstatica> variables = query.getResultList();
			if (variables != null && variables.size() > 0) {
				Variable variable = variables.get(0).getVariable();
				if (variable instanceof HibernateProxy) {
					variable = (Variable) ((HibernateProxy) variable).getHibernateLazyInitializer().getImplementation();
					variables.get(0).setVariable(variable);
				}
				return variables.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
}
