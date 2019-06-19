package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.VariableDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Variable;

public class VariableDaoImpl extends GenericDaoImpl<Variable, Integer> implements VariableDao {

	public VariableDaoImpl() {
		super(Variable.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Variable> findAllComplete() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em.createQuery("SELECT var FROM Variable var");
			List<Variable> lista = query.getResultList();
			ElementoCatalogo tipoDato = null;
			for (Variable var : lista) {
				tipoDato = var.getTipoDato();
				if (tipoDato instanceof HibernateProxy) {
					tipoDato = (ElementoCatalogo) ((HibernateProxy) tipoDato).getHibernateLazyInitializer()
							.getImplementation();
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
	public Variable findById(String id) throws NotariaException {
		Variable variable;
		variable = super.findById(id);
		ElementoCatalogo tipoDato = variable.getTipoDato();
		// tipoDato.setRol(null);
		tipoDato.setCatalogo(null);

		return variable;
	}

	@Override
	public Variable findByName(String variableName) throws NotariaException {
		List<Variable> variables = super.executeQuery("SELECT v FROM Variable v WHERE v.dsnombre = ?1", variableName);
		if (variables.size() > 0) {
			return variables.get(0);
		}
		return null;
	}

	@Override
	public List<Variable> findAll() throws NotariaException {
		return executeQuery("SELECT v FROM Variable v WHERE v.isactivo=true");
	}

}
