package com.palestra.notaria.bo.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.palestra.notaria.bo.RolBo;
import com.palestra.notaria.bo.UsuarioBO;
import com.palestra.notaria.dao.UsuarioDao;
import com.palestra.notaria.dao.UsuarioRestaurarDao;
import com.palestra.notaria.dao.impl.UsuarioDaoImpl;
import com.palestra.notaria.dao.impl.UsuarioRestaurarDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioRestaurar;
import com.palestra.notaria.util.GeneradorId;

public class UsuarioBoImpl extends GenericBoImpl<Usuario> implements UsuarioBO {
	
	private UsuarioDao usuarioDao;

	public UsuarioBoImpl(){
		usuarioDao = new UsuarioDaoImpl();		
		super.dao = this.usuarioDao;
	}
	
//	public Usuario save(Usuario usuario){
//		Usuario user = null;
//		try{
//			user = usuarioDao.save(usuario);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return user;
//	}
	
	@Override
	public Usuario authenticate(String user, String password) throws NotariaException {
		Usuario usuario = null;
		try{
			return usuarioDao.authenticate(user, password);
		}catch(NotariaException e){
			e.printStackTrace();
			return usuario;
		}	
	}

	@Override
	public List<Usuario> listarPorRol(Rol rol) throws NotariaException {
		List<Usuario> userList = null;
			userList = usuarioDao.listarPorRol(rol);
		return userList;
	}
	
	@Override
	public List<Usuario> listarParaAsignar() throws NotariaException {
		/*List<Usuario> userList = new ArrayList<Usuario>();
		RolBo manRol = new RolBoImpl();
		
	
		// POR EL MOMENTO QUEDAn LOS PREFIJOS DE ROLES EN CODIGO DURO DEBIDO A QUE NO HAY TIEMPO PERO SE DEBE MEJORAR
		//Víctor Espinosa
		//im.vicoy@gmail.com
		try {
			List<String> rolDatos = Arrays.asList("jpool", "jpas","pas","proto","rev");
			
			for(String dato:rolDatos){
				Rol datoRolSeach = manRol.rolByPrefijo(dato);
				List<Usuario> datoUsuarios=listarPorRol(datoRolSeach);
				userList.addAll(datoUsuarios);
			}
				
		} catch (NotariaException e) {
			throw new NotariaException("Ocurrió un error al obtener los usuarios para la asignación.",e);
		}*/
		
		return findAll();
	}
	
	@Override
	public List<Usuario> findAll() throws NotariaException{
			return usuarioDao.findAll();
	}

	@Override
	public String buscarIniciales(String idUsuario) {
		try{
			return usuarioDao.buscarIniciales(idUsuario);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Usuario findById(String idusuario) throws NotariaException{
			return usuarioDao.findById(idusuario);
	}
	
	@Override
	public UsuarioRestaurar reseteaContrasenia(String correo, String token)throws NotariaException{
		if(token.length()>=64 || correo.contains("@")){
			//localiza la peticion del usuario.
			Usuario usuario = buscarUsuarioPorCorreo(correo);
			if(usuario!=null){
				UsuarioRestaurarDao daoPeticion = new UsuarioRestaurarDaoImpl();
				UsuarioRestaurar peticion = daoPeticion.ultimaPeticionActiva(usuario);
				if(token.endsWith(peticion.getNuevaContrasenia())&&token.startsWith(peticion.getIdpeticion())){
					//la peticion es valida:
					peticion.setEstatus("A");
					daoPeticion.update(peticion);
					return peticion;
				}else{
					throw new NotariaException("La peticion realizada no existe o no es valida.");
				}			
			}else{
				throw new NotariaException(String.format("Al parecer el usuario con el correo %s no existe.", correo));
			}
		}else{
			throw new NotariaException("No se enviaron los parametros necesarios para poder iniciar el proceso de restauración.");
		}
	}
	
	@Override
	public Usuario buscarUsuarioPorCorreo(String correo)throws NotariaException{
		UsuarioDao dao = new UsuarioDaoImpl();
		List<Usuario> usuarios = dao.buscaPorCorreo(correo);
		if(usuarios.size()>1){
			throw new NotariaException(String.format("Se ha localizado a más de un usuario con el correo %s, no se puede iniciar el proceso de recuperación.", correo));
		}else if(usuarios.size()==1){
				return usuarios.get(0);
		}else{
			throw new NotariaException(String.format("Al parecer el usuario con el correo %s no existe.", correo));
		}
	}
	
	@Override 
	public Usuario actualizaContrasenia(Usuario usuario, String token) throws NotariaException{
		//localiza al usuario
		Usuario u = buscarUsuarioPorCorreo(usuario.getDscorreo());
		if(u!=null){
			//valida que el token exista:
			UsuarioRestaurarDao daoPeticiones = new UsuarioRestaurarDaoImpl();
			UsuarioRestaurar peticion = daoPeticiones.ultimaPeticionActiva(u);
			if(token.startsWith(peticion.getIdpeticion()) && token.endsWith(peticion.getNuevaContrasenia())){
				//es valida, actualizamos el password y luego terminamos la peticion
				u.setDsvalenc(usuario.getDsvalenc());
				u.setIsactualizapwd(false);
				UsuarioDao dao = new UsuarioDaoImpl();
				dao.update(u);
				peticion.setEstatus("I");
				daoPeticiones.update(peticion);
				return usuario;
			}else{
				throw new NotariaException("No se localizo la petición, probablemente expiro.");
			}
		}else{
			throw new NotariaException("El usuario no exite en la unidad de persistencia.");
		}
	}
	
	@Override
	public UsuarioRestaurar peticionRestauraContrasenia(String correo)throws NotariaException{		
			Usuario usuario = buscarUsuarioPorCorreo(correo);
			if(usuario!=null){
				//almacena la petición en caso de que no exista una pendiente de aplicar:
				List<UsuarioRestaurar> peticionesActiva = usuarioDao.buscaPeticionesActivasPorUsuario(usuario);
				if(peticionesActiva.size()>0){
					throw new NotariaException("Se han localizado peticiones activas, no se puede registrar una nueva.");
				}else{
					//Registra la peticion solo si no hay peticione activas:
					UsuarioRestaurar peticion = new UsuarioRestaurar();
					peticion.setEstatus("P");
					peticion.setFecha(new Timestamp(new java.util.Date().getTime()));
					peticion.setIdpeticion(GeneradorId.generaId(peticion));
					peticion.setNuevaContrasenia(GeneradorId.generaId(usuario));
					peticion.setTmstmp(new Timestamp(new java.util.Date().getTime()));
					peticion.setUsuario(usuario);
					Calendar dtVencimiento = Calendar.getInstance();
					dtVencimiento.add(Calendar.HOUR, 36);
					Timestamp vencimiento = new Timestamp(dtVencimiento.getTimeInMillis());
					peticion.setVencimiento(vencimiento);
					UsuarioRestaurarDao restaurarDao = new UsuarioRestaurarDaoImpl();
					restaurarDao.save(peticion);
					//TODO enviar por correo el token con la peticion
					
					return peticion;
				}
			}else{
				throw new NotariaException(String.format("Al parecer el usuario con el correo %s no existe.", correo));
			}
	}
	
	@Override
	public Integer eliminaPeticion(UsuarioRestaurar peticion)throws NotariaException{
		UsuarioRestaurarDao dao = new UsuarioRestaurarDaoImpl();
		dao.delete(peticion);
		return 1;
	}
	
	@Override
	public Usuario save(Usuario usuario)throws NotariaException{
		usuario.setIdusuario(GeneradorId.generaId(usuario));
		usuario.setInestatus(true);
		usuario.setIsactualizapwd(false);
		usuario.setIsactivo(true);
		return usuarioDao.save(usuario);
	}
}
