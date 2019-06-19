package com.palestra.notaria.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.palestra.notaria.dao.DocumentosOriginalesDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.DocumentosOriginales;
import com.palestra.notaria.modelo.ManejoSesion;

public class DocumentosOriginalesDaoImpl extends
		GenericDaoImpl<DocumentosOriginales, Integer> implements
		DocumentosOriginalesDao {

	public DocumentosOriginalesDaoImpl() {
		super(DocumentosOriginales.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentosOriginales> obtenerListaOriginales(String idacto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<DocumentosOriginales> lista;
		try {
			query = em
					.createQuery("from DocumentosOriginales docoriginal where idacto = '"
							+ idacto + "' order by dsnombre ");
			lista = query.getResultList();
			if (lista != null) {
				/*
				 * VICTOR: SE COMENTA POR QUE YA NO EXISTE AMARRADO SOLO AL EXPEDIENTE, SI NO AL ACTO
				 * for (DocumentosOriginales e : lista) {
					e.setExpediente(null);
				}*/
				for (DocumentosOriginales e : lista) {
					e.setActo(new Acto());
					e.getActo().setIdacto(idacto);
					
				}
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String buscarArchivoPorId(String idDocumento) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<String> lista;
		String archivo = null;
		try {
			
				query = em
						.createQuery("select dsruta from DocumentosOriginales where id = '"
								+ idDocumento + "'");
			
			lista = query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				archivo = lista.get(0);
			}
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return archivo;
	}

	@Override
	public boolean actualizarRutaArchivoOriginal(String iddocor, String ruta) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean b = false;
		Query query;
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			
				query = em
						.createQuery("update DocumentosOriginales set dsruta = :ruta where id = :id");
				
			

			query.setParameter("ruta", ruta);
			query.setParameter("id", iddocor);

			int result = query.executeUpdate();

			tx.commit();
			if (result > 0)
				b = true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String obtenerTramitePorOriginal(String iddocor) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<String> lista;
		String idtramite = null;
		try {
			query = em
					.createQuery("select acto.expediente.numexpediente from DocumentosOriginales where id = :id ");
			query.setParameter("id", iddocor);
			lista = query.getResultList();
			if (lista == null || lista.isEmpty()) {
				return null;
			}
			idtramite = lista.get(0);
			if (idtramite == null || idtramite.isEmpty()) {
				return null;
			}
			return idtramite;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

	}

	@Override
	public void borrar(String iddocori) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			DocumentosOriginales msf = em.find(DocumentosOriginales.class,iddocori);
			em.remove(msf);
	        em.getTransaction().commit();
		} catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException("Ha ocurrido un fallo al encontrar el usuario.", e.getCause());
		}finally{
			em.close();
		}
	}
}
