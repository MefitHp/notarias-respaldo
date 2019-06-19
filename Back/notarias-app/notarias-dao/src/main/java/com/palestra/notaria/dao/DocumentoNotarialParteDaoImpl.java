package com.palestra.notaria.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.impl.GenericDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.modelo.DocumentoNotarialParte;
import com.palestra.notaria.util.GeneradorId;

public class DocumentoNotarialParteDaoImpl extends GenericDaoImpl<DocumentoNotarialParte, Integer>
		implements DocumentoNotarialParteDao {

	EntityManager em;

	public DocumentoNotarialParteDaoImpl() {
		super(DocumentoNotarialParte.class);
	}

	DocumentoNotarialParteDaoImpl(Class<DocumentoNotarialParte> klass) {
		super(klass);
	}

	@Override
	public DocumentoNotarialParte save(DocumentoNotarialParte parte) throws NotariaException {
		parte.setIdentificador(GeneradorId.generaId(parte));
		return super.save(parte);
	}

	@Override
	public DocumentoNotarialParte update(DocumentoNotarialParte parte) throws NotariaException {
		DocumentoNotarialParte persistido = findById(parte.getIdentificador());
		if (persistido != null) {
			persistido = parte;
			return super.update(persistido);
		} else {
			throw new NotariaException(
					"El documento a actualizar no se localizo en la unidad de persistencia, imposible actualizarlo.");
		}
	}

	@Override
	public void delete(DocumentoNotarialParte parte) throws NotariaException {
		DocumentoNotarialParte persistido = super.findById(parte.getDocumento().getIddocnotpar());
		if (persistido != null) {
			super.delete(persistido);
		} else {
			throw new NotariaException(
					"El documento a eliminar no se localizo en la unidad de persistencia, imposible eliminarlo.");
		}
	}

	@Override
	public DocumentoNotarialParte findByOrden(int orden, DocumentoNotarialParcial documento) throws NotariaException {
		em = factory.createEntityManager();
		try {
			TypedQuery<DocumentoNotarialParte> sentencia = em.createNamedQuery("DocumentoNotarialPartes.findByOrden",
					DocumentoNotarialParte.class);
			sentencia.setParameter("iddocumento", documento.getIddocnotpar());
			sentencia.setParameter("orden", orden);
			List<DocumentoNotarialParte> listado = sentencia.getResultList();
			if (listado.size() > 0) {
				return listado.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			throw new NotariaException(
					"No se ha logrado obtener el listado por una excepcion en la unidad de persistencia.", e);
		} finally {
			em.close();
		}
	}

	@Override
	public DocumentoNotarialParte findByReferencia(String referencia, DocumentoNotarialParcial documento)
			throws NotariaException {
		em = factory.createEntityManager();
		try {
			TypedQuery<DocumentoNotarialParte> sentencia = em
					.createNamedQuery("DocumentoNotarialPartes.findByReferencia", DocumentoNotarialParte.class);
			sentencia.setParameter("iddocumento", documento.getIddocnotpar());
			sentencia.setParameter("referencia", referencia);
			List<DocumentoNotarialParte> listado = sentencia.getResultList();
			if (listado.size() > 0) {
				return listado.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			throw new NotariaException(
					"No se ha logrado obtener el listado por una excepcion en la unidad de persistencia.", e);
		} finally {
			em.close();
		}
	}

	@Override
	public List<DocumentoNotarialParte> findByDocumento(DocumentoNotarialParcial documento) throws NotariaException {
		em = factory.createEntityManager();
		try {
			TypedQuery<DocumentoNotarialParte> sentencia = em
					.createNamedQuery("DocumentoNotarialParte.findByIdDocumento", DocumentoNotarialParte.class);
			sentencia.setParameter("documento", documento);
			List<DocumentoNotarialParte> listado = sentencia.getResultList();
			if (listado.size() > 0) {
				return listado;
			} else {
				return new ArrayList<DocumentoNotarialParte>();
			}
		} catch (PersistenceException e) {
			throw new NotariaException(
					"No se ha logrado obtener el listado por una excepcion en la unidad de persistencia.", e);
		} finally {
			em.close();
		}
	}

}
