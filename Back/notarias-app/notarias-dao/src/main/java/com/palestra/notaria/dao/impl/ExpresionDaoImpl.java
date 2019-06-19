package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.ExpresionDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expresion;

public class ExpresionDaoImpl extends GenericDaoImpl<Expresion, Integer> implements ExpresionDao {

	public ExpresionDaoImpl() {
		super(Expresion.class);
	}

	@Override
	public List<Expresion> listarPorVariable(String idvariable) throws NotariaException {
		List<Expresion> expresiones = executeQuery(
				"SELECT e FROM Expresion e WHERE e.variable.variable.idvariable = ?1", idvariable);
		if (expresiones.size() == 0) {
			expresiones = executeQuery("SELECT e FROM Expresion e WHERE e.variable.componente.idcomponente = ?1",
					idvariable);
			if (expresiones.size() == 0) {
				return null;
			} else {
				for (Expresion exp : expresiones) {
					exp.getVariable().getComponente().setConFormulario(null);
					exp.getVariable().getComponente().setSubformulario(null);
				}
				return expresiones;
			}
		} else {
			return expresiones;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Expresion getExpresionByIdComp(String idcomp) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			Query query = em.createQuery("SELECT e FROM Expresion e WHERE e.variable.componente.idcomponente = ?1");
			query.setParameter(1, idcomp);
			List<Expresion> expList = query.getResultList();
			if (expList.size() == 1) {
				return expList.get(0);
			} else if (expList.size() > 1) {
				throw new NotariaException("Se encontró mas de 1 expresión para el componente");
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			return null;
		}finally {
			em.close();
		}
	}

	@Override
	public List<Expresion> getByComponente(String idcomponente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Expresion> query = em.createNamedQuery("Expresion.getByComponente", Expresion.class);
			query.setParameter(1, idcomponente);
			List<Expresion> expresiones = query.getResultList();
			for (Expresion expresion : expresiones) {
				expresion.setVariable(null);
			}
			return expresiones;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
}
