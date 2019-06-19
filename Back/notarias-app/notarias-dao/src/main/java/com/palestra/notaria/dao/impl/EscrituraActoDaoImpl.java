package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.EscrituraActoDao;
import com.palestra.notaria.dato.DatoActoMultiSelect;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.modelo.Tramite;

public class EscrituraActoDaoImpl extends
		GenericDaoImpl<EscrituraActo, Integer> implements EscrituraActoDao {

	public EscrituraActoDaoImpl() {
		super(EscrituraActo.class);
	}

	@Override
	public List<EscrituraActo> findByEscrituraId(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<EscrituraActo> query = em
					.createQuery("SELECT esac FROM EscrituraActo esac WHERE esac.escritura.idescritura=:idescritura", EscrituraActo.class);
			query.setParameter("idescritura", id);

			List<EscrituraActo> lista = query.getResultList();

			if (lista.size() == 0) {
				return null;
			}

			Acto acto = null;
			Escritura escritura = null;
			Suboperacion suboperacion = null;
			Expediente expediente = null;
			Tramite tramite = null;
			
			for (EscrituraActo escact : lista) {
				if (escact != null) {
					escritura = escact.getEscritura();
					if (escritura instanceof HibernateProxy) {
						escritura = (Escritura) ((HibernateProxy) escritura).getHibernateLazyInitializer().getImplementation();
					}
					escact.setEscritura(escritura);
					
					expediente = escact.getEscritura().getExpediente();
					if(expediente instanceof HibernateProxy){
						expediente = (Expediente)((HibernateProxy)expediente).getHibernateLazyInitializer().getImplementation();
						tramite = expediente.getTramite();
						if(tramite instanceof HibernateProxy){
							tramite = (Tramite)((HibernateProxy)tramite).getHibernateLazyInitializer().getImplementation();
							expediente.setTramite(tramite);
						}
						escact.getEscritura().setExpediente(expediente);
					}

					acto = escact.getActo();
					if (acto instanceof HibernateProxy) {
						acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
						suboperacion = acto.getSuboperacion();
						if (suboperacion instanceof HibernateProxy) {
							suboperacion = (Suboperacion) ((HibernateProxy) suboperacion).getHibernateLazyInitializer().getImplementation();
						}
						acto.setSuboperacion(suboperacion);
					}
					escact.setActo(acto);
				}
			}

			return lista;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatoActoMultiSelect> obtenActoNombrePorEscrituraId(String id) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {

			List<DatoActoMultiSelect> listaMultiSelect = new ArrayList<DatoActoMultiSelect>();
			DatoActoMultiSelect actoSelec = null;

			/** Obtener actos de la escritura **/
			StringBuilder hql = new StringBuilder();
			hql.append(" SELECT esac.acto.idacto, esac.acto.dsnombre, esac.acto.dsdescripcion,");
			hql.append("esac.acto.suboperacion.operacion.dsnombre");
			hql.append(" FROM EscrituraActo esac");
			hql.append(" WHERE esac.escritura.idescritura=:idescritura");

			Query query = em.createQuery(hql.toString());
			query.setParameter("idescritura", id);

			List<Object[]> actosSueltos = query.getResultList();

			if (actosSueltos.size() == 0) {
				return null;
			}
			for (Object[] res : actosSueltos) {
				if (res != null) {
					actoSelec = new DatoActoMultiSelect();
					actoSelec.setIdacto((String) res[0]);
					actoSelec.setNombreActo((String) res[1]);
					actoSelec.setDescripcionActo((String) res[2]);
					actoSelec.setNombreOperacion((String)res[3]);
					actoSelec.setChecked("checked");
					listaMultiSelect.add(actoSelec);
				}
			}
			return listaMultiSelect;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

	}

	@Override
	public String obtenIdEscrituraPorActoId(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT esac.escritura.idescritura FROM EscrituraActo esac WHERE esac.acto.idacto = :id ");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<String> resultados = query.getResultList();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<EscrituraActo> buscarPorEscrituraIdCompleto(
			EscrituraActo escrituraActo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			if (escrituraActo != null && escrituraActo.getEscritura() != null
					&& escrituraActo.getEscritura().getIdescritura() != null) {
				Query query = em
						.createQuery("from EscrituraActo where escritura.idescritura = '"
								+ escrituraActo.getEscritura().getIdescritura()
								+ "'");
				List<EscrituraActo> lista = query.getResultList();
				Acto acto = null;
				for (EscrituraActo escact : lista) {
					if (escact != null) {
						// Para este metodo no se necesita la escritura.
						escact.setEscritura(null);
						acto = escact.getActo();
						if (acto instanceof HibernateProxy) {
							acto = (Acto) ((HibernateProxy) acto)
									.getHibernateLazyInitializer()
									.getImplementation();
							acto.setSuboperacion(null);
							acto.setExpediente(null);
						}
						escact.setActo(acto);
					}
				}
				return lista;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
		return null;

	}

	/**
	 * Busca los actos asociados a la escritura mas los actos disponibles
	 * 
	 * @param id
	 *            del la escritura.
	 * @return List<DatoActoMultiSelect> lista de actos
	 * @throws NotariaException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DatoActoMultiSelect> buscarActosDisponibleParaEscritura(
			String idEscritura, String idExpediente) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		try {
			/** Obtener los actos que ya estan asociados al expediente dado **/
			StringBuilder actosDeEscritura = new StringBuilder();
			actosDeEscritura
					.append(" SELECT esac.acto.idacto, esac.acto.dsnombre, esac.acto.dsdescripcion");
			actosDeEscritura.append(" FROM EscrituraActo esac ");
			actosDeEscritura
					.append(" WHERE esac.escritura.idescritura=:idescritura");

			Query query = em.createQuery(actosDeEscritura.toString());
			query.setParameter("idescritura", idEscritura);

			List<Object[]> actosEscritura = query.getResultList();
			List<DatoActoMultiSelect> listaMultiSelect = new ArrayList<DatoActoMultiSelect>();
			DatoActoMultiSelect actoSelec = null;

			for (Object[] res : actosEscritura) {
				if (res != null) {
					actoSelec = new DatoActoMultiSelect();
					actoSelec.setIdacto((String) res[0]);
					actoSelec.setNombreActo((String) res[1]);
					actoSelec.setDescripcionActo((String) res[2]);
					actoSelec.setChecked("checked");
					listaMultiSelect.add(actoSelec);
				}
			}
			
			// TODO: cambiar filtro para jalas los actos libres del expediente.
			/** Obtener actos disponibles para seleccion **/
//			StringBuilder actosDisponibles = new StringBuilder();
//			actosDisponibles
//					.append(" SELECT act.idacto, act.dsnombre, act.dsdescripcion");
//			actosDisponibles.append(" FROM Acto act");
//			actosDisponibles.append(" WHERE act.expediente.idexpediente = '"
//					+ idExpediente + "' AND act.idacto not in (");
//			actosDisponibles.append(" SELECT esac.acto.idacto");
//			actosDisponibles.append(" FROM EscrituraActo esac");
//			actosDisponibles.append(")");
//
//			query = em.createQuery(actosDisponibles.toString());
//
//			List<Object[]> actosSueltos = query.getResultList();
//
//			for (Object[] res : actosSueltos) {
//				if (res != null) {
//					actoSelec = new DatoActoMultiSelect();
//					actoSelec.setIdacto((String) res[0]);
//					actoSelec.setNombreActo((String) res[1]);
//					actoSelec.setDescripcionActo((String) res[1]);
//					actoSelec.setChecked("");
//					listaMultiSelect.add(actoSelec);
//				}
//			}

			if (listaMultiSelect.size() == 0) {
				return null;
			}

			return listaMultiSelect;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	/**
	 * Devuelve una lista de actos que no esten asociados a ningun expediente.
	 * 
	 * @return Lista DatoActoMultiSelect que contiene los actos disponibles.
	 * @throws NotariaException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DatoActoMultiSelect> buscarActosDisponibles(String idexpediente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {

			List<DatoActoMultiSelect> listaMultiSelect = new ArrayList<DatoActoMultiSelect>();
			DatoActoMultiSelect actoSelec = null;

			/** Obtener actos disponibles para seleccion **/
			StringBuilder actosDisponibles = new StringBuilder();
			actosDisponibles
					.append(" SELECT act.idacto, act.dsnombre, act.dsdescripcion");
			actosDisponibles.append(" FROM Acto act");
			actosDisponibles.append(" WHERE act.isactivo = true AND act.expediente.idexpediente = '"
					+ idexpediente + "' and act.idacto not in (");
			actosDisponibles.append(" SELECT esac.acto.idacto");
			actosDisponibles.append(" FROM EscrituraActo esac");
			actosDisponibles.append(")");
			Query query = em.createQuery(actosDisponibles.toString());

			List<Object[]> actosSueltos = query.getResultList();

			if (actosSueltos.size() == 0) {
				return null;
			}

			for (Object[] res : actosSueltos) {
				if (res != null) {
					actoSelec = new DatoActoMultiSelect();
					actoSelec.setIdacto((String) res[0]);
					actoSelec.setNombreActo((String) res[1]);
					actoSelec.setDescripcionActo((String) res[2]);
					actoSelec.setChecked("checked");
					listaMultiSelect.add(actoSelec);
				}
			}

			return listaMultiSelect;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

	}

	/**
	 * Valida si un acto no esta asociado a otra escritura
	 * 
	 * @param idActos
	 *            Lista de actos que se pretende validar
	 * @return Boolean
	 * @throws NotariaException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean isValidAddActos(String idActos) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			/** Obtener actos disponibles para seleccion **/
			StringBuilder actosDisponibles = new StringBuilder();
			actosDisponibles.append(" select esac.acto.idacto");
			actosDisponibles.append(" from EscrituraActo esac");
			actosDisponibles.append(" where esac.acto.idacto in (" + idActos
					+ ")");
			Query query = em.createQuery(actosDisponibles.toString());
			List<EscrituraActo> resultados = query.getResultList();

			if (resultados.size() == 0) {
				// Actos no se encuentran asociados escritura, se pueden agregar
				return true;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return false;

	}

	@Override
	public int deleteByEscrituraId(String idEscritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		int deleted = 0;

		try {
			tx = em.getTransaction();
			tx.begin();

			Query query = em
					.createQuery("DELETE FROM EscrituraActo esac WHERE esac.escritura.idescritura =:idEscritura");
			deleted = query.setParameter("idEscritura", idEscritura)
					.executeUpdate();
			tx.commit();
			return deleted;

		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
//			@omarete: ya no se puede devolver -1 como estatus pero en cambio se relanza la excepción
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	/**
	 * Obtiene el id de la notaria a la que pertenece el notaria asignado al
	 * expediente
	 * 
	 * @param id
	 *            id Expediente
	 * @param id
	 *            id notaria asociada al expediente
	 * @throws NotariaException 
	 */
	@Override
	public String obtenNotarioDatoByEscrituraId(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(" select escact.escritura.notario.dsiniciales ");
			sql.append(" from EscrituraActo escact ");
			sql.append(" where escact.escritura.idescritura = :id");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<String> resultados = query.getResultList();
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

}
