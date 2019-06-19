package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.RolGestionCompetenciaDAO;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.RolGestionCompetencia;
import com.palestra.notaria.util.GeneradorId;

public class RolGestionCompetenciasDAOImpl extends
		GenericDaoImpl<RolGestionCompetencia, String> implements
		RolGestionCompetenciaDAO {

	EntityManager em;
	
	public RolGestionCompetenciasDAOImpl(Class<RolGestionCompetencia> clase) {
		super(clase);
	}

	@Override
	public RolGestionCompetencia agregar(RolGestionCompetencia competencia)
			throws NotariaException {
		em = factory.createEntityManager();
		try{
			TypedQuery<RolGestionCompetencia> sentencia = em.createNamedQuery("RolGestionCompetencia.findByRolPantalla", RolGestionCompetencia.class);
			sentencia.setParameter("idrol", competencia.getRol().getIdrol());
			sentencia.setParameter("pantalla", competencia.getPantalla());
			List<RolGestionCompetencia> competencias = sentencia.getResultList();
			if(competencias.size()>0){
				for(RolGestionCompetencia gc:competencias){
					quitarCompetencia(gc.getIdentificador());
				}
			} 
			competencia.setIdentificador(GeneradorId.generaId(RolGestionCompetencia.class));
			return save(competencia);
			
		} catch (PersistenceException e) {
			throw new NotariaException("Se genero una excepción al establecer la persistencia. Revise el log para ás detalle", e);
		} finally {
			em.close();
		}	
	}

	@Override
	public RolGestionCompetencia findById(String identificador) throws NotariaException {
		em = factory.createEntityManager();
		try{
			TypedQuery<RolGestionCompetencia> sentencia = em.createNamedQuery("RolGestionCompetencia.findById", RolGestionCompetencia.class);
			sentencia.setParameter("identificador", identificador);
			List<RolGestionCompetencia> competencias = sentencia.getResultList();
			if(competencias.size()<=0){
				throw new NotariaException("La competencia no existe en la unidad de persistencia.");
			} else {
				return competencias.get(0);
			}
		} catch (PersistenceException e) {
			throw new NotariaException("Se genero una excepción al establecer la persistencia. Revise el log para ás detalle", e);
		} finally {
			em.close();
		}	
	}
	
	@Override
	public RolGestionCompetencia actualizarModo(String identificador, int modo)
			throws NotariaException {
		try{
			RolGestionCompetencia actual = findById(identificador);
			if(actual==null){
				throw new NotariaException("No existe una competencia asociada al identificador enviado en la unidad de persistencia.");
			} else {
				actual.setModo(modo);
				return update(actual);
			}
		} catch(PersistenceException e) {
			throw new NotariaException("Se genero una excepción al establecer la persistencia. Revise el log para ás detalle", e);
		}
	}

	@Override
	public void quitarCompetencia(String identificador) throws NotariaException {
		em = factory.createEntityManager();
		try{
			RolGestionCompetencia actual = findById(identificador);
			if(actual==null){
				throw new NotariaException("No existe una competencia asociada al identificador enviado en la unidad de persistencia.");
			} else {
				String sql = "DELETE FROM RolGestionCompetencias gc WHERE gc.identificador = :identificador";
				Query query = em.createQuery(sql);
				query.setParameter("identificador", identificador);
				query.executeUpdate();
			}
		} catch(PersistenceException e) {
			throw new NotariaException("Se genero una excepción al establecer la persistencia. Revise el log para ás detalle", e);
		} finally {
			em.close();
		}
	}

	@Override
	public List<RolGestionCompetencia> competencias(String idrol)
			throws NotariaException {
		em = factory.createEntityManager();
		try{
			TypedQuery<RolGestionCompetencia> sentencia = em.createNamedQuery("RolGestionCompetencia.findByIdRol", RolGestionCompetencia.class);
			sentencia.setParameter("idrol", idrol);
			return sentencia.getResultList();
		} catch(PersistenceException e) {
			throw new NotariaException("Se genero una excepción al establecer la persistencia. Revise el log para ás detalle", e);
		}finally {
			em.close();
		}
	}

	@Override
	public int compentenciaPantalla(String idrol, String pantalla) throws NotariaException {
		em = factory.createEntityManager();
		try{
			TypedQuery<RolGestionCompetencia> sentencia = em.createNamedQuery("RolGestionCompetencia.findByRolPantalla", RolGestionCompetencia.class);
			sentencia.setParameter("idrol", idrol);
			sentencia.setParameter("pantalla",pantalla);
			List<RolGestionCompetencia> competencias = sentencia.getResultList();
			if(competencias.size()<=0){
				return 0;
			} else {
				return competencias.get(0).getModo();
			}
		} catch (PersistenceException e) {
			throw new NotariaException("Se genero una excepción al establecer la persistencia. Revise el log para ás detalle", e);
		}finally {
			em.close();
		}	
	}
	
}
