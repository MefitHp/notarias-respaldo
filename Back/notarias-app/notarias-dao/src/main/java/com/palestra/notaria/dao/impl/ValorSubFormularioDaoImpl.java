package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.ValorSubFormularioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.ValorFormulario;
import com.palestra.notaria.modelo.ValorSubFormulario;

public class ValorSubFormularioDaoImpl extends GenericDaoImpl<ValorSubFormulario, Integer>
		implements ValorSubFormularioDao {

	public ValorSubFormularioDaoImpl() {
		super(ValorSubFormulario.class);
	}

	@Override
	public Integer obtenerNumeroRegistrosTabla(String idConSubForm, String idForm) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<Integer> query;
		try {

			// EL IDCONSUBFORM SE UTILIZA PARA BUSCAR LAS COINCIDENCIAS DEL
			// NÚMERO MÁXIMO DEL FORMULARIO EN CASO DE EXISTIR
			// MÁS DE UN SUBFORMULARIO EN EL MISMO FORMULARIO
			// SI NO ENCUENTRA RESULTADOS VERIFICAR LA TABLA 51 EN DONDE EL ID
			// SUBFORMULARIO NO DEBE SER NULO...
			StringBuilder sb = new StringBuilder();

			sb.append("SELECT MAX(vsf.registro) FROM ValorSubFormulario vsf ");
			sb.append(" WHERE vsf.componente.subformulario.idconsubform= :idConSubForm");
			sb.append(" AND vsf.formulario.idformulario=:idForm");
			// sb.append("WHERE vsf.formulario.idformulario=:idForm");
			query = em.createQuery(sb.toString(), Integer.class);
			query.setParameter("idConSubForm", idConSubForm);
			query.setParameter("idForm", idForm);
			List<Integer> listado = query.getResultList();
			Integer numRegistros = 0;
			if (listado != null && listado.size() > 0) {
				numRegistros = listado.get(0);
			} else {
				numRegistros = -1;
			}
			return numRegistros;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(
					"obtenerNumeroRegistrosTabla, no se lograron obtener los registros de la tabla del subformulario.",
					e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<ValorSubFormulario> findByIdForm(String idform) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<ValorSubFormulario> query;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT vsf FROM ValorSubFormulario vsf ");
			sb.append(" WHERE vsf.formulario.idformulario=:idform ORDER BY vsf.componente.inposicion, vsf.registro");

			query = em.createQuery(sb.toString(), ValorSubFormulario.class);
			query.setParameter("idform", idform);

			List<ValorSubFormulario> lista = query.getResultList();
			if (lista.size() == 0) {
				return null;
			}
			System.out.printf("******* Registros subform ********* %n");
			Componente componente = null;
			for (ValorSubFormulario vsf : lista) {

				System.out.printf("******* componentePosicion: %d - Registro %d %n",
						vsf.getComponente().getInposicion(), vsf.getRegistro());
				componente = vsf.getComponente();
				if (componente instanceof HibernateProxy) {
					componente = (Componente) ((HibernateProxy) componente).getHibernateLazyInitializer()
							.getImplementation();
					componente.setConFormulario(null);
					componente.setSubformulario(null);
					componente.setTipocomponente(null);
				}
				/*
				 * if(vsf.getComponente().getConFormulario()==null){
				 * System.out.println("No existe el componente ..."); continue;
				 * }
				 */
				vsf.setComponente(componente);
				Formulario formulario = vsf.getFormulario();
				if (formulario instanceof HibernateProxy) {
					formulario = (Formulario) ((HibernateProxy) formulario).getHibernateLazyInitializer()
							.getImplementation();
					vsf.setFormulario(formulario);
					Acto acto = formulario.getActo();
					if (acto instanceof HibernateProxy) {
						acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
						vsf.getFormulario().setActo(acto);
					}
				}

			}

			return lista;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(
					"findByIdForm. Algo inesperado sucedio al recuperar los subformularios por identificador de formulario. ",
					e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public void eliminaValorSubFrm(Componente componente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<ValorSubFormulario> query = em.createQuery(
					"SELECT vf FROM ValorSubFormulario vf WHERE vf.componente.idcomponente = ?1",
					ValorSubFormulario.class);
			query.setParameter(1, componente.getIdcomponente());
			List<ValorSubFormulario> valores = query.getResultList();
			if (valores.size() > 0) {
				em.getTransaction().begin();
				for (ValorSubFormulario valor : valores) {
					String sql = "DELETE FROM ValorSubFormulario vf WHERE vf.componente.idcomponente = ?1";
					Query queryDelete = em.createQuery(sql);
					queryDelete.setParameter(1, valor.getComponente().getIdcomponente());
					queryDelete.executeUpdate();
				}
				em.getTransaction().commit();
			}
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}

}
