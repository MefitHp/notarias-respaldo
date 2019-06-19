package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.DocumentoNotarialMasterDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;
import com.palestra.notaria.modelo.Escritura;

public class DocumentoNotarialMasterDaoImpl extends
		GenericDaoImpl<DocumentoNotarialMaster, Integer> implements
		DocumentoNotarialMasterDao {

	public DocumentoNotarialMasterDaoImpl() {
		super(DocumentoNotarialMaster.class);
	}

	/**
	 * Retorna un documeno notarial master filrado por escritura
	 * 
	 * @param id
	 *            idEscritura
	 * @throws NotariaException 
	 */
	@Override
	public DocumentoNotarialMaster findByEscrituraId(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT dnm FROM DocumentoNotarialMaster dnm ");
			sql.append(" WHERE dnm.escritura.idescritura = :id");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<DocumentoNotarialMaster> resultados = query.getResultList();
			if (resultados == null || resultados.isEmpty()) {
				return null;
			}

			DocumentoNotarialMaster master = resultados.get(0);
			Escritura escritura = null;

			escritura = master.getEscritura();
			if (escritura instanceof HibernateProxy) {
				escritura = (Escritura) ((HibernateProxy) escritura)
						.getHibernateLazyInitializer().getImplementation();
				escritura.setLibro(null);
				escritura.setExpediente(null);
				escritura.setNotario(null);
			}
			master.setEscritura(escritura);
//			return resultados.get(0);
			return master;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public Boolean existeMasterDeEscritura(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT dnm.iddocnotmas FROM DocumentoNotarialMaster dnm ");
			sql.append(" WHERE dnm.escritura.idescritura = :id");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<String> resultados = query.getResultList();
			if (resultados == null || resultados.isEmpty()) {
				return false;
			}
			String idDocNotMaster = resultados.get(0);
			if(idDocNotMaster == null || idDocNotMaster.isEmpty()){
				return false;
			}
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
}
