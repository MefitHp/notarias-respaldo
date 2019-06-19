package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.palestra.notaria.dao.LibroDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Libro;

public class LibroDaoImpl extends GenericDaoImpl<Libro, Integer> implements
		LibroDao {

	public LibroDaoImpl() {
		super(Libro.class);
	}

	@Override
	public Libro obtenUltimoLibro() throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT lb FROM Libro lb");
			sql.append(" WHERE lb.innumlibro = (SELECT MAX(lib.innumlibro) from Libro lib)");

			Query query = em.createQuery(sql.toString());

			@SuppressWarnings("unchecked")
			List<Libro> resultados = query.getResultList();
			if (resultados != null && resultados.size() > 0) {
				return resultados.get(0);
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		/** Si no se encontro nada retorna nullo **/
		return null;

	}

	@Override
	public Libro obtenLibroXnumero(Long numero) throws NotariaException {
		EntityManager em = factory.createEntityManager();;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT lb FROM Libro lb");
			sql.append(" WHERE lb.innumlibro = :numlibro");
			
			Query query = em.createQuery(sql.toString());
			query.setParameter("numlibro", numero);
			@SuppressWarnings("unchecked")
			List<Libro> resultados = query.getResultList();
			if (resultados != null && resultados.size() > 0) {
				return resultados.get(0);
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		/** Si no se encontro nada retorna nullo **/
		return null;
	}

	@Override
	public Libro findById(String id){
		StringBuilder sql = new StringBuilder();
		EntityManager em = factory.createEntityManager();
		sql.append(" SELECT lb FROM Libro lb");
		sql.append(" WHERE lb.idlibro = :id");
		
		Query query = em.createQuery(sql.toString());
		query.setParameter("id", id);
		Libro libro = (Libro)query.getSingleResult();
		em.close();
		return libro;
		
	}
}
