package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.UsuarioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioRestaurar;

public class UsuarioDaoImpl extends GenericDaoImpl<Usuario, Integer> implements UsuarioDao {

	public UsuarioDaoImpl() {
		super(Usuario.class);
	}

	@Override
	public Usuario authenticate(String user, String password) throws NotariaException {
		List<Usuario> usuarioList;
		try {
			usuarioList = executeQuery(
					"select usr from Usuario usr where usr.cdusuario = ?1 and usr.dsvalenc = ?2 and usr.isactivo=true and usr.inestatus=true",
					user, password);

			if (usuarioList != null && !usuarioList.isEmpty())
				return usuarioList.get(0);
			else
				throw new NotariaException("El usuario o contraseña son incorrectos");
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}

	}

	// @Override
	// public Usuario save(Usuario usuario){
	// try {
	// Usuario user = super.save(usuario);
	// return user;
	// } catch (NotariaException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }

	// @Override
	// public Usuario update(Usuario usuario){
	// try{
	// Usuario user = super.update(usuario);
	// return user;
	// }catch(NotariaException e){
	// e.printStackTrace();
	// return null;
	// }
	// }

	// @Override
	// public Usuario save(Usuario usuario) throws Exception{
	// getEntityManager().getTransaction().begin();
	// executeNativeQuery("INSERT INTO tbcfgm03 SET
	// idusuario='"+usuario.getIdusuario()+"',cdusuario='"+usuario.getCdusuario()+
	// "',dscorreo='"+usuario.getDscorreo()+"',dsmaterno='"+usuario.getDsmaterno()+"',dsnombre='"+usuario.getDsnombre()+
	// "',dspaterno='"+usuario.getDspaterno()+"',dsreferencia='"+usuario.getDsreferencia()+"',dsrfc='"+usuario.getDsrfc()+
	// "',dsultimoacceso='"+usuario.getDsultimoacceso()+"',dsvalenc=PASSWORD('"+usuario.getDsvalenc()+"')"+
	// ",idsesion='"+usuario.getIdsesion()+"',isactivo="+usuario.getIsactivo()+",idrol='"+usuario.getRol().getIdrol()+
	// "',inestatus="+usuario.getInestatus()+",idnotaria='"+usuario.getNotaria().getIdnotaria()+"',fchinicio='"+usuario.getFchinicio()+
	// "',fchfin='"+usuario.getFchfin()+"',dsiniciales='"+usuario.getDsiniciales()+"'");
	//
	//
	// getEntityManager().getTransaction().commit();
	// Usuario usuarioGuardado = findById(usuario.getIdusuario());
	//
	// getEntityManager().close();
	// if(usuarioGuardado != null){
	// Notaria notaria = usuarioGuardado.getNotaria();
	// Rol rol = usuarioGuardado.getRol();
	//
	//
	// if(notaria instanceof HibernateProxy){
	// notaria = (Notaria)
	// ((HibernateProxy)notaria).getHibernateLazyInitializer().getImplementation();
	// notaria.setDomicilio(null);
	// }
	//
	// if(rol instanceof HibernateProxy)
	// rol = (Rol)
	// ((HibernateProxy)rol).getHibernateLazyInitializer().getImplementation();
	//
	// usuarioGuardado.setRol(rol);
	// usuarioGuardado.setNotaria(notaria);
	// }
	//
	// return usuarioGuardado;
	// }

	@Override
	public List<Usuario> listarPorRol(Rol rol) throws NotariaException {
		try {
			List<Usuario> usuarioList = executeQuery("SELECT u FROM Usuario u WHERE u.rol.dsprefijo = ?1",
					rol.getDsprefijo());
			return usuarioList;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}
	}

	// @Override
	// public Usuario update(Usuario usuario) throws Exception{
	// getEntityManager().getTransaction().begin();
	// executeNativeQuery("UPDATE tbcfgm03 SET
	// cdusuario='"+usuario.getCdusuario()+"',dscorreo='"+usuario.getDscorreo()+"',dsmaterno='"+usuario.getDsmaterno()+
	// "',dsnombre='"+usuario.getDsnombre()+"',dspaterno='"+usuario.getDspaterno()+"',dsreferencia='"+usuario.getDsreferencia()+
	// "',dsrfc='"+usuario.getDsrfc()+"',"
	// +"dsultimoacceso='"+usuario.getDsultimoacceso()+"',dsvalenc=PASSWORD('"+usuario.getDsvalenc()+
	// "'),isactivo="+usuario.getIsactivo()+",isactualizapwd="+usuario.getIsactualizapwd()+",idrol='"+usuario.getRol().getIdrol()+
	// "',inestatus="+usuario.getInestatus()+",idnotaria='"+usuario.getNotaria().getIdnotaria()+"',fchinicio='"+usuario.getFchinicio()+
	// "',fchfin='"+usuario.getFchfin()+"',dsiniciales='"+usuario.getDsiniciales()+"'
	// WHERE idusuario='"+usuario.getIdusuario()+"'");
	// getEntityManager().getTransaction().commit();
	//
	//
	// Usuario usuarioGuardado = findById(usuario.getIdusuario());
	// getEntityManager().close();
	// if(usuarioGuardado != null){
	// Notaria notaria = usuarioGuardado.getNotaria();
	// Rol rol = usuarioGuardado.getRol();
	//
	//
	// if(notaria instanceof HibernateProxy){
	// notaria = (Notaria)
	// ((HibernateProxy)notaria).getHibernateLazyInitializer().getImplementation();
	// notaria.setDomicilio(null);
	// }
	//
	// if(rol instanceof HibernateProxy)
	// rol = (Rol)
	// ((HibernateProxy)rol).getHibernateLazyInitializer().getImplementation();
	//
	// usuarioGuardado.setRol(rol);
	// usuarioGuardado.setNotaria(notaria);
	// }
	// return usuarioGuardado;
	// }

	@Override
	public List<Usuario> findAll() throws NotariaException {
		try {
			List<Usuario> usuarioList = executeQuery("SELECT u FROM Usuario u WHERE u.isactivo=true");
			return usuarioList;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}
	}

	@Override
	public String buscarIniciales(String idUsuario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		String resp = null;
		try {
			Query query = em.createQuery("select dsiniciales from Usuario where idusuario = '" + idUsuario + "'");

			resp = (String) query.getSingleResult();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return resp;
	}

	@Override
	public List<Usuario> buscaPorCorreo(String correo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE dscorreo=:correo", Usuario.class);
			query.setParameter("correo", correo);
			return query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Ha ocurrido un fallo al encontrar el usuario.", e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<UsuarioRestaurar> buscaPeticionesActivasPorUsuario(Usuario usuario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<UsuarioRestaurar> query = em.createNamedQuery("UsuarioRestaurar.encuentraPeticionActivaUsuario",
					UsuarioRestaurar.class);
			query.setParameter("usuario", usuario);
			List<UsuarioRestaurar> listado = query.getResultList();
			return listado;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException("Fallo al localizar las peticiones de restauración de usuario", e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<UsuarioRestaurar> buscaPeticionActivaPorCorreo(String correo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<UsuarioRestaurar> query = em.createNamedQuery("UsuarioRestaurar.encuentraPeticionCorreo",
					UsuarioRestaurar.class);
			query.setParameter("correo", correo);
			return query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public Usuario findByIniciales(String iniciales) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE dsiniciales = :iniciales",
					Usuario.class);
			query.setParameter("iniciales", iniciales);
			List<Usuario> usuarios = query.getResultList();
			if (usuarios != null && usuarios.size() > 0) {
				return usuarios.get(0);
			} else {
				return null;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

}
