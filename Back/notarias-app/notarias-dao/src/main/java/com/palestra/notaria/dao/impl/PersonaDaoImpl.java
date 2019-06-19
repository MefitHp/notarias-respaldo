package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.PersonaDao;
import com.palestra.notaria.dato.DatoBusquedaPersona;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Persona;

public class PersonaDaoImpl extends GenericDaoImpl<Persona, Integer> implements
		PersonaDao {

	public PersonaDaoImpl() {
		super(Persona.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Persona> buscarPersonaPorNombre(String subCadena) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery("SELECT per FROM Persona per WHERE per.dsnombrecompleto LIKE :nombre ");
			query.setParameter("nombre", "%"+subCadena+"%");
			query.setMaxResults(30);
			List<Persona> pers = query.getResultList();
			for(Persona per:pers){
				System.out.println("persona found "+per.getDsnombre());
				this.lazyInitializers(per);
//				@omarete OBTENER DOMICILIO LO PIDIO EL VIC (por si me cagan (con autorizacion del cafaray (rey mides)))
				ComparecienteDaoImpl compa = new ComparecienteDaoImpl();
				List<Compareciente> comp = compa.getByIdPersona(per.getIdpersona());
				if(comp.size()>0){
					per.setDomicilio(comp.get(0).getDomicilio());
				}
			}
			return query.getResultList();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	// @Override
	public List<Persona> obtenerListaCompleta() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery("from Persona");
	
			@SuppressWarnings("unchecked")
			List<Persona> lista = query.getResultList();
	
			for(Persona p:lista){
				this.lazyInitializers(p);
			}
			return lista;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}

	}

	@Override
	public Persona buscarPorIdCompleto(Persona persona) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
			Query query = em.createQuery("from Persona where idpersona = ?1");
			query.setParameter(1, persona.getIdpersona());
	
			Persona p = (Persona) query.getSingleResult();
//			SE ELIMINO EL SETEO DEL COMPARECIENTE A PERSONA
			this.lazyInitializers(p);
	
			return p;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@Override
	public List<DatoBusquedaPersona> findPersonaByName(String nombre) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		try {
//			StringBuilder sb = new StringBuilder();
//			sb.append(" SELECT per.dsnombre, per.dsapellidopat,per.dsapellidomat");
//			sb.append(", per.idpersona ,comp.idcompareciente, tc.dsnombre as 'tcompareciente', tram.idtramite");
//			sb.append(", tt.dsnombre as 'tramite', acto.idacto, exp.idexpediente, exp.numexpediente");
//			sb.append(", (CASE WHEN exp.idexpediente IS NOT NULL THEN exp.idabogado ");
//			sb.append(" ELSE tram.idabogado ");
//			sb.append(" END) AS 'idabogado' ");
//			sb.append(" FROM tbbsnm28 per ");
//			sb.append(" LEFT JOIN tbbsnm21 comp ");
//			sb.append(" ON ");
//			sb.append(" per.idpersona = comp.idpersona ");
//			sb.append(" LEFT JOIN tbbsnm40 tram ");
//			sb.append(" ON ");
//			sb.append(" per.idpersona = tram.idcliente ");
//			sb.append(" LEFT JOIN tbbsnm18 acto ");
//			sb.append(" ON comp.idacto = acto.idacto ");
//			sb.append(" LEFT JOIN tbbsnm31 tc ");
//			sb.append(" ON comp.idtipocompareciente = tc.idtipocompareciente ");
//			sb.append(" LEFT JOIN tbbsnm32 exp ");
//			sb.append(" ON exp.idexpediente = acto.idexpediente ");
//			sb.append(" OR exp.idtramite = tram.idtramite ");
//			sb.append(" LEFT JOIN tbbsnm41 tt ");
//			sb.append(" ON tram.idstatus = tt.idetatra ");
//			sb.append(" WHERE per.dsnombrecompleto LIKE '%"+ nombre + "%'");
// 			Nota:Se opto por usar SQL nativo por la complejidad del query y
// 			la falta de relacion entre alguna de las tablas.
//			Query query = em.createNativeQuery(sb.toString());
//			List<Object[]> lista = query.getResultList();
//			if (lista.size() == 0) {
//				return null;
//			}
//
//			List<DatoBusquedaPersona> resultados = new ArrayList<DatoBusquedaPersona>();
//
//			for (Object[] obj : lista) {
//				DatoBusquedaPersona dbp = new DatoBusquedaPersona();
//				dbp.setDsnombre((String) obj[0]);
//				dbp.setDsapellidopat((String) obj[1]);
//				dbp.setDsapellidomat((String) obj[2]);
//				dbp.setIdpersona((String) obj[3]);
//				dbp.setIdcompareciente((String) obj[4]);
//				dbp.setTipocompareciente((String) obj[5]);
//				dbp.setIdtramite((String) obj[6]);
//				dbp.setEtapa((String) obj[7]);
//				dbp.setIdacto((String) obj[8]);
//				dbp.setIdexpediente((String) obj[9]);
//				dbp.setNumexpediente((String) obj[10]);
//				dbp.setIdabogado((String) obj[11]);
//				resultados.add(dbp);
//			}
//			return resultados;
			
			TypedQuery<Persona> typedQuery = em.createNamedQuery("Persona.FindByNombreCompleto", Persona.class);
			typedQuery.setParameter("nombreCompleto", "%"+nombre+"%");
			List<Persona> personas = typedQuery.getResultList();
//			omar 7/Mayo/2018 se modifica la busqueda de personas, ya que en empresas los campos apellido estan nulos y 
//			al parecer eso hace que al correr el typed query no devuelva resultados por eso se busca solo por nombre
			if(personas.size() == 0){
				Query query = em.createQuery("from Persona p where p.dsnombre like :nombre");
				query.setParameter("nombre", "%"+nombre+"%");
				personas = query.getResultList();
			}
			if(personas!=null && personas.size()>0){
				List<DatoBusquedaPersona> listado = new ArrayList<DatoBusquedaPersona>();
				for(Persona persona:personas){
					DatoBusquedaPersona p = new DatoBusquedaPersona();
					p.setDsapellidomat(persona.getDsapellidomat());
					p.setDsapellidopat(persona.getDsapellidopat());
					p.setDsnombre(persona.getDsnombre());
					p.setIdpersona(persona.getIdpersona());
					p.setRfc(persona.getDsrfc());
					listado.add(p);
				}
				return listado;
			} else {
				return new ArrayList<DatoBusquedaPersona>();				
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("No se lograron consultar las personas.", e.getCause());
		}finally {
			em.close();
		}
	}
	
	public void lazyInitializers(Persona persona){
		ElementoCatalogo nacionalidad;
		ElementoCatalogo tipoPersona;

		tipoPersona = persona.getTipopersona();
		if (tipoPersona instanceof HibernateProxy) {
			tipoPersona = (ElementoCatalogo) ((HibernateProxy) tipoPersona).getHibernateLazyInitializer().getImplementation();
			tipoPersona.setCatalogo(null);
		}
		persona.setTipopersona(tipoPersona);

		nacionalidad = persona.getNacionalidad();
		if (nacionalidad instanceof HibernateProxy) {
			nacionalidad = (ElementoCatalogo) ((HibernateProxy) nacionalidad)
					.getHibernateLazyInitializer().getImplementation();
			// nacionalidad.setTbcfgm07s(null);
			nacionalidad.setCatalogo(null);

		}
		persona.setNacionalidad(nacionalidad);

	}

	@Override
	public void eliminaPersona(Persona persona) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			
			// Segundo eliminar compareciente
			em.remove(em.contains(persona) ? persona : em.merge(persona));

			tx.commit();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		
	}
}
