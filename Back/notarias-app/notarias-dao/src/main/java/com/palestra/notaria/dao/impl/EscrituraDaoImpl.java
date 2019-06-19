package com.palestra.notaria.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.EscrituraDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.EscrituraExterna;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.Usuario;

public class EscrituraDaoImpl extends GenericDaoImpl<Escritura, Integer>
		implements EscrituraDao {

	public EscrituraDaoImpl() {
		super(Escritura.class);
	}
	
	@Override
	public Escritura getByNumeroEscritura(String numescritura) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		Escritura retornoEscritura = new Escritura();
		try{
			TypedQuery<EscrituraExterna> queryExt = em.createQuery("SELECT esc FROM EscrituraExterna esc WHERE esc.dsnumescritura = :numescritura",EscrituraExterna.class);
			queryExt.setParameter("numescritura", numescritura);
			List<EscrituraExterna> escriturasExt = queryExt.getResultList();
			
			if(escriturasExt.size()>1){
				throw new NotariaException("Se encontró más de una escritura");
			}else if(escriturasExt.size()<1){
				TypedQuery<Escritura> queryInt = em.createQuery("SELECT esc FROM Escritura esc WHERE esc.dsnumescritura = :numescritura",Escritura.class);
				queryInt.setParameter("numescritura", numescritura);
				List<Escritura> escriturasInt = queryInt.getResultList();
				
				if(escriturasInt.size()>1){
					throw new NotariaException("Se encontró más de una escritura");
				}else if(escriturasInt.size()<1){
					throw new NotariaException("No existe una escritura con el número proporcionado");
				}else{
					retornoEscritura = escriturasInt.get(0);
					return escriturasInt.get(0);
				}				
			}else{
				EscrituraExterna resultado = escriturasExt.get(0);
				
				retornoEscritura.setIdescritura(resultado.getIdescrituraExterna());
				retornoEscritura.setDsnumescritura(resultado.getDsnumescritura());
				retornoEscritura.setFolioini(resultado.getFolioini());
				retornoEscritura.setFoliofin(resultado.getFoliofin());
				
				return retornoEscritura;
			}
			
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Escritura> findByExpedienteId(String id) throws NotariaException {

		EntityManager em = factory.createEntityManager();

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT es FROM Escritura es");
			sb.append(" WHERE es.expediente.idexpediente=:idexpediente");
			sb.append(" ORDER BY es.fechacreacion, tmstmp");
			Query query = em.createQuery(sb.toString());
			query.setParameter("idexpediente", id);

			List<Escritura> resultados = query.getResultList();

			if (resultados != null && !resultados.isEmpty()) {
				Usuario notario;
				Libro libro;
				Rol rol;
				for (Escritura esc : resultados) {
					esc.setExpediente(null);
					notario = esc.getNotario();
					if (notario instanceof HibernateProxy) {
						notario = (Usuario) ((HibernateProxy) notario)
								.getHibernateLazyInitializer()
								.getImplementation();
						rol = notario.getRol();
						if (rol instanceof HibernateProxy) {
							rol = (Rol) ((HibernateProxy) rol)
									.getHibernateLazyInitializer()
									.getImplementation();
						}
						notario.setRol(rol);
					}
					esc.setNotario(notario);
					libro = esc.getLibro();
					if (libro instanceof HibernateProxy) {
						libro = (Libro) ((HibernateProxy) libro)
								.getHibernateLazyInitializer()
								.getImplementation();
					}
					esc.setLibro(libro);

				}
			}

			return resultados;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public String obtenNumEscritura(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT esc.dsnumescritura FROM Escritura esc ");
			sql.append(" WHERE esc.idescritura = :id");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<String> resultados = query.getResultList();
			if (resultados != null && !resultados.isEmpty()) {
				String numEscritura = resultados.get(0); 
				if(numEscritura == null || numEscritura.trim().isEmpty()){
					return null;
				}
				return numEscritura;
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
	public Escritura buscarPorIdCompleto(Escritura escritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		try {
			if (escritura != null && escritura.getIdescritura() != null) {

				StringBuilder sb = new StringBuilder();
				sb.append("from Escritura e where e.idescritura = :idescritura ");
				Query query = em.createQuery(sb.toString());
				query.setParameter("idescritura", escritura.getIdescritura());
				// Nota: si se usa singleResult y no se encuentra valor lanza
				// excepcion
				List<Escritura> resultado = query.getResultList();
				if (resultado.size() == 0) {
					return null;
				}
				/** Tomar escritura de lista de resultados **/
				Escritura esc = resultado.get(0);

				Expediente expediente = null;
				Libro libro = null;
				Usuario notario = null;
				Rol rol = null;

				if (esc != null) {

					expediente = esc.getExpediente();
					if (expediente instanceof HibernateProxy) {
						expediente = (Expediente) ((HibernateProxy) expediente)
								.getHibernateLazyInitializer()
								.getImplementation();
						expediente.setAbogado(null);
						expediente.setTramite(null);
						//expediente.setListaComentarios(null);
					}
					esc.setExpediente(expediente);

					libro = esc.getLibro();
					if (libro instanceof HibernateProxy) {
						libro = (Libro) ((HibernateProxy) libro)
								.getHibernateLazyInitializer()
								.getImplementation();
					}
					esc.setLibro(libro);

					notario = esc.getNotario();
					if (notario instanceof HibernateProxy) {
						notario = (Usuario) ((HibernateProxy) notario)
								.getHibernateLazyInitializer()
								.getImplementation();
						rol = notario.getRol();
						if (rol instanceof HibernateProxy) {
							rol = (Rol) ((HibernateProxy) rol)
									.getHibernateLazyInitializer()
									.getImplementation();
						}
						notario.setRol(rol);
					}
					esc.setNotario(notario);

					return esc;
				}
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

		/** si no se obtuvo el objeto retorna nullo **/
		return null;
	}

	@Override
	public Boolean registrarEscritura(Escritura escritura,
			List<EscrituraActo> actosDeEscritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;

		try {
			tx = em.getTransaction();
			tx.begin();

			/** Guardar la escritura **/
			em.persist(escritura);

			/** Guadar EscrituraActo **/
			for (EscrituraActo esac : actosDeEscritura) {
				em.persist(esac);
			}

			tx.commit();

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return true;
	}

	@Override
	public Boolean actualizarActosNotario(Escritura escritura,
			List<EscrituraActo> actosDeEscritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			/**
			 * Eliminar registros de relacion escritura-actos. Se volveran a
			 * agregar para hacer actualizacion.
			 **/
			query = em
					.createQuery("DELETE FROM EscrituraActo esac WHERE esac.escritura.idescritura =:idescritura");
			query.setParameter("idescritura", escritura.getIdescritura())
					.executeUpdate();
			// TODO: hacer validacion que los actos enviados no esten asociados
			// a otra escritura, actualmente se hace desde el rest.
			/** Acutalizar el notario **/
			query = em
					.createQuery("update Escritura set notario.idusuario=:idnotario WHERE idescritura =:idescritura");
			query.setParameter("idescritura", escritura.getIdescritura());
			query.setParameter("idnotario", escritura.getNotario()
					.getIdusuario());
			query.executeUpdate();

			/** Agregar nuevos registros EscrituraActo **/
			for (EscrituraActo esac : actosDeEscritura) {
				em.persist(esac);
			}
			tx.commit();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return true;
	}

	@Override
	public Boolean actualizarPropsEscritura(Escritura escritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			/** Acutalizar solo escritura */
			em.merge(escritura);
			tx.commit();
		}catch(RollbackException e){
			if(e.getCause() instanceof ConstraintViolationException);
			{
				e.printStackTrace(System.out);
				throw new NotariaException("El número de escritura proporcionado, ya se encuentra registrado");
			}
			
		}
		catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
			
		}
		finally {
			em.close();
		}
		return true;
	}
	
	@Override
	public Boolean actualizarPropsEscrituraPizarron(Escritura escritura,PizarronElemento pizarron) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			/** Acutalizar solo escritura */
			em.merge(escritura);
			em.merge(pizarron);
			tx.commit();
		}catch(RollbackException e){
			if(e.getCause() instanceof ConstraintViolationException);
			{
				e.printStackTrace(System.out);
				throw new NotariaException("El número de escritura proporcionado, ya se encuentra registrado");
			}
			
		}
		catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
			
		}
		finally {
			em.close();
		}
		return true;
	}
	
	

	@Override
	@SuppressWarnings("unchecked")
	public String getExpedienteIdByEscrituraId(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		try {
			Query query = em
					.createQuery("SELECT e.expediente.idexpediente FROM Escritura e WHERE e.idescritura = :idescritura");
			query.setParameter("idescritura", id);
			List<String> lista = query.getResultList();
			if (lista.size() == 0) {
				return null;
			}

			return lista.get(0);

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public Boolean setFirmaDittoByEscrituraId(String id, Boolean isfirmaditto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			StringBuilder sql = new StringBuilder();
			sql.append(" update Escritura set isfirmaditto = :isfirmaditto ");
			sql.append(" , fechafirmaditto = :fechafirmaditto ");
			sql.append(" where idescritura = :idescritura ");
			query = em.createQuery(sql.toString());
			query.setParameter("isfirmaditto", isfirmaditto);
			query.setParameter("fechafirmaditto", new Date());
			query.setParameter("idescritura", id);

			int result = query.executeUpdate();
			tx.commit();
			if (result > 0) {
				return true;
			}
			return false;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public Boolean eliminarEscritura(String id) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		Query query = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			//TODO: bitacoraFirma apunta a escritura, preguntar si se va a eliminar la bitacora
			
			query = em.createQuery("delete DocumentoNotarialParcial where escritura.idescritura = :idescritura");
			query.setParameter("idescritura", id).executeUpdate();
			
			// Por restriccion fk, primero eliminar EscrituraActo
			query = em.createQuery("delete EscrituraActo where escritura.idescritura = :idescritura");
			query.setParameter("idescritura", id).executeUpdate();

			// Segundi eliminar escritura
			query = em.createQuery("delete Escritura where idescritura = :idescritura");
			query.setParameter("idescritura", id).executeUpdate();

			tx.commit();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Boolean tieneNumEscritura(String idEscritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT dsnumescritura FROM Escritura ");
			sql.append(" WHERE idescritura = :idescritura");
			Query query = em.createQuery(sql.toString());
			query.setParameter("idescritura", idEscritura);
			@SuppressWarnings("unchecked")
			List<String> resultados = query.getResultList();
			if (resultados == null || resultados.isEmpty()) {
				return false;
			}
			String numEscritura = resultados.get(0);
			if (numEscritura == null || numEscritura.isEmpty()) {
				return false;
			}
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public Boolean switchNotario(Escritura escritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		EntityTransaction tx = em.getTransaction();
		StringBuilder sql = null;
		try {

			if (escritura == null || escritura.getIdescritura() == null
					|| escritura.getNotario() == null
					|| escritura.getNotario().getIdusuario() == null
					|| escritura.getNotario().getIdusuario().isEmpty()
					|| escritura.getNotario().getRol() == null
					|| escritura.getNotario().getRol().getIdrol() == null
					|| escritura.getNotario().getRol().getIdrol().isEmpty()) {
				//Faltan datos.
				return false;
			}

			tx.begin();
			/** Obtener el notario asociado. **/
			sql = new StringBuilder();
			sql.append(" from Usuario where idusuario <> :idusuario and rol.idrol = :idrol");
			query = em.createQuery(sql.toString());
			query.setParameter("idusuario", escritura.getNotario()
					.getIdusuario());
			query.setParameter("idrol", escritura.getNotario().getRol()
					.getIdrol());
			@SuppressWarnings("unchecked")
			List<Usuario> resultados = query.getResultList();
			if (resultados == null || resultados.isEmpty()) {
				// No existe otro notario
				return false;
			}

			/**
			 * tomar la primera coincidenci, sabemos que deberia ser 1 notario
			 * asociado.
			 **/
			Usuario usuario = resultados.get(0);

			sql = new StringBuilder();
			sql.append(" update Escritura set notario.idusuario = :idusuario ");
			sql.append(" where idescritura = :idescritura ");
			query = em.createQuery(sql.toString());
			query.setParameter("idusuario", usuario.getIdusuario());
			query.setParameter("idescritura", escritura.getIdescritura());
			int result = query.executeUpdate();
			tx.commit();
			if (result > 0) {
				// Se hizo el update.
				return true;
			}
			// No se hizo el update.
			return false;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String obtenerIdNotarioPorEscrituraId(String idEscritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<String> lista;
		String idnotario= null;
		try {
			query = em.createQuery("select notario.idusuario from Escritura where idEscritura = :idEscritura ");
			query.setParameter("idEscritura", idEscritura);
			lista = query.getResultList();
			if(lista == null || lista.isEmpty()){
				return null;
			}
			idnotario = lista.get(0);
			if(idnotario==null || idnotario.isEmpty()){
				return null;
			}
			return idnotario;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public List<Escritura> getXnumLibroStatus(Long numerolibroInicial,Long numerolibroFinal, Boolean nopaso) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT es FROM Escritura es");
			
			sb.append(" WHERE (es.libro.innumlibro BETWEEN :numerolibroinicial AND :numerolibrofinal) ");
			if(nopaso!=null){
				sb.append("AND es.nopaso =:nopaso");
			}
			sb.append(" ORDER BY es.fechacreacion, tmstmp");
			Query query = em.createQuery(sb.toString());
			query.setParameter("numerolibroinicial", numerolibroInicial);
			query.setParameter("numerolibrofinal", numerolibroFinal);
			if(nopaso!=null){
				query.setParameter("nopaso", nopaso);
			}

			List<Escritura> resultados = query.getResultList();

			if (resultados != null && !resultados.isEmpty()) {
				Libro libro;
				for (Escritura esc : resultados) {
					esc.setExpediente(null);
					esc.setNotario(null);
					libro = esc.getLibro();
					if (libro instanceof HibernateProxy) {
						libro = (Libro) ((HibernateProxy) libro)
								.getHibernateLazyInitializer()
								.getImplementation();
					}
					esc.setLibro(libro);
				}
			}
			return resultados;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}



	
	
	
	
	
}
