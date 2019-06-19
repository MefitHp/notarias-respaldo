package com.palestra.notaria.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.palestra.notaria.dao.GrupoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.VariableGrupo;

public class GrupoDaoImpl extends GenericDaoImpl<Grupo, Integer> implements GrupoDao {

	static Logger logger = Logger.getLogger(GrupoDaoImpl.class);

	public GrupoDaoImpl() {
		super(Grupo.class);
	}

	@Override
	public List<Grupo> findByProperties(Grupo grupo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			long inicio = System.currentTimeMillis();

			StringBuilder buffer = new StringBuilder("SELECT g FROM Grupo g ");

			Map<String, Object> params = new HashMap<String, Object>();

			boolean first = true;

			if (grupo.getDsgrupo() != null) {
				buffer.append(first ? "WHERE " : "AND ");
				buffer.append("g.dsgrupo =:dsgrupo ");
				params.put("dsgrupo", grupo.getDsgrupo());
				first = false;
			}

			logger.info("Ejecutando búsqueda: {" + buffer.toString() + "}");
			Query q = em.createQuery(buffer.toString());
			for (String param : params.keySet()) {
				q.setParameter(param, params.get(param));
			}

			@SuppressWarnings("unchecked")
			List<Grupo> grupos = q.getResultList();
			logger.info("Parametros: " + params.toString());

			logger.info("Búsqueda ejecutada en " + (System.currentTimeMillis() - inicio) + "ms. Encontrados "
					+ grupos.size() + " resultados.");
			return grupos;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VariableGrupo> getVariablesByGrupo(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		try {
			Query query = em.createQuery("SELECT vg FROM VariableGrupo vg WHERE vg.grupo.idgrupo =:idgrupo");
			query.setParameter("idgrupo", id);

			List<VariableGrupo> resultados = query.getResultList();
			if (resultados.size() == 0) {
				return null;
			}

			return resultados;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}

	@Override
	public List<Grupo> findAll() throws NotariaException {
		return executeQuery("SELECT d FROM Grupo d WHERE d.isactivo=true");
	}

}
