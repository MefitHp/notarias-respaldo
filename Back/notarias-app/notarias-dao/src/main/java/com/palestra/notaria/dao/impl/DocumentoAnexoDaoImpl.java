package com.palestra.notaria.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.DocumentoAnexoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.DocumentoAnexo;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;
import com.palestra.notaria.modelo.Escritura;

public class DocumentoAnexoDaoImpl extends
		GenericDaoImpl<DocumentoAnexo, Integer> implements DocumentoAnexoDao {

	public DocumentoAnexoDaoImpl() {
		super(DocumentoAnexo.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoNotarialMaster> obtenMasterAgendados(CalendarioCita cc) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<DocumentoNotarialMaster> lista;
		if (cc == null) {
			return null;
		}
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" select ds.docnotmas from DocumentoAnexo ds ");

			Map<String, Object> params = new HashMap<String, Object>();
			boolean first = true;

			if (cc.getId().getIdcita() != null && !cc.getId().getIdcita().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("ds.cita.id.idcita=:idcita ");
				params.put("idcita", cc.getId().getIdcita());
				first = false;
			}
			
			if (cc.getId().getIdcita() != null && !cc.getId().getIdcita().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("ds.cita.id.version=:version ");
				params.put("version", cc.getId().getVersion());
				first = false;
			}

			if (cc.getExpediente() != null
					&& cc.getExpediente().getIdexpediente() != null
					&& !cc.getExpediente().getIdexpediente().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("ds.docnotmas.escritura.expediente.idexpediente=:idexpediente ");
				params.put("idexpediente", cc.getExpediente().getIdexpediente());
				first = false;
			}

			sql.append(" ORDER BY ds.tmstmp ");

			Query query = em.createQuery(sql.toString());
			for (String param : params.keySet()) {
				query.setParameter(param, params.get(param));
			}
			lista = (List<DocumentoNotarialMaster>) query.getResultList();
			if (lista != null && lista.size() == 0) {
				return null;
			}
			if (lista != null) {
				for (DocumentoNotarialMaster dnm : lista) {
					this.hibernateLazyInitializer(dnm);
				}
			}
			return lista;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<DocumentoNotarialMaster> obtenMasterDisponibles(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT dnm FROM DocumentoNotarialMaster dnm ");
			sql.append(" WHERE dnm.escritura.expediente.idexpediente = :idexpediente");
			
			Query query = em.createQuery(sql.toString());
			query.setParameter("idexpediente", id);

			@SuppressWarnings("unchecked")
			List<DocumentoNotarialMaster> resultados = query.getResultList();
			if (resultados != null && resultados.size() == 0) {
				return null;
			}
			for (DocumentoNotarialMaster dnm : resultados) {
				this.hibernateLazyInitializer(dnm);
			}
			
			return resultados;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}


	private void hibernateLazyInitializer(DocumentoNotarialMaster dnm) {
		Escritura escritura = null;
		escritura = dnm.getEscritura();
		if (escritura instanceof HibernateProxy) {
			escritura = (Escritura) ((HibernateProxy) escritura)
					.getHibernateLazyInitializer().getImplementation();
			escritura.setLibro(null);
			escritura.setExpediente(null);
			escritura.setNotario(null);
		}
		dnm.setEscritura(escritura);
		dnm.setTxtdoc(null);
	}
}
