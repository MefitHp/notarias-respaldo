package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.PresupuestoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.BitacoraGeneral;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Presupuesto;

public class PresupuestoDaoImpl extends GenericDaoImpl<Presupuesto, Integer> implements PresupuestoDao {

	public PresupuestoDaoImpl() {
		super(Presupuesto.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Presupuesto> buscarPresupuestos(String idexpediente, String idacto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<Presupuesto> lista = null;
		try {
			if (idacto == null)
				query = em.createQuery("from Presupuesto where acto.expediente.idexpediente = '" + idexpediente
						+ "' order by acto.suboperacion.dsnombre, tmstmp");
			else
				query = em.createQuery("from Presupuesto where acto.idacto='" + idacto + "' order by tmstmp ");

			lista = query.getResultList();
			if (lista != null) {
				Acto acto;
				ElementoCatalogo conceptoPago;
				for (Presupuesto p : lista) {
					acto = p.getActo();
					if (acto instanceof HibernateProxy) {
						acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
						acto.setExpediente(null);
						acto.setSuboperacion(null);
					}
					p.setActo(acto);

					conceptoPago = p.getConceptoPago();
					if (conceptoPago instanceof HibernateProxy) {
						conceptoPago = (ElementoCatalogo) ((HibernateProxy) conceptoPago).getHibernateLazyInitializer()
								.getImplementation();
						conceptoPago.setCatalogo(null);
					}
					p.setConceptoPago(conceptoPago);
				}
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return lista;
	}

	@Override
	public Presupuesto buscarPorIdCompleto(String idpresupuesto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Presupuesto presupuesto = null;

		try {
			presupuesto = em.find(Presupuesto.class, idpresupuesto);
			if (presupuesto != null) {
				ElementoCatalogo conceptoPago;
				Acto acto;
				acto = presupuesto.getActo();
				if (acto instanceof HibernateProxy) {
					acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
					acto.setExpediente(null);
					acto.setSuboperacion(null);
				}
				presupuesto.setActo(acto);

				conceptoPago = presupuesto.getConceptoPago();
				if (conceptoPago instanceof HibernateProxy) {
					conceptoPago = (ElementoCatalogo) ((HibernateProxy) conceptoPago).getHibernateLazyInitializer()
							.getImplementation();
					conceptoPago.setCatalogo(null);
				}
				presupuesto.setConceptoPago(conceptoPago);
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return presupuesto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean elimiarPresupuestosPorActo(String idacto, String idusuario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Query query;
		List<Presupuesto> lista = null;
		BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		BitacoraGeneral bg;
		boolean b = false;
		try {
			tx.begin();
			query = em.createQuery("from Presupuesto where acto.idacto='" + idacto + "'");
			lista = query.getResultList();
			if (lista != null) {
				String exp;
				for (Presupuesto p : lista) {
					exp = p.getActo().getExpediente().getIdexpediente();
					em.remove(em.contains(p) ? p : em.merge(p));
					bg = bitGenHelp.crearBitacora(idusuario, exp, null, "Presupuesto", "Eliminar",
							"Eliminacion del presupuesto perteneciente al acto " + p.getActo().getDsnombre());
					em.persist(bg);
				}
			}
			tx.commit();
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

}
