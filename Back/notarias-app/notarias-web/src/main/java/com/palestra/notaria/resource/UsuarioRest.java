package com.palestra.notaria.resource;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.UsuarioBO;
import com.palestra.notaria.bo.impl.UsuarioBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.UsuarioEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioRestaurar;
import com.palestra.notaria.util.EnviaCorreo;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/usuarios")
public class UsuarioRest {

	static Logger logger = Logger.getLogger(UsuarioRest.class);
	@Context UriInfo uriInfo;
	private UsuarioBO usuarioBo;
	
	public UsuarioRest(){
		usuarioBo = new UsuarioBoImpl();
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioEnvio getUsuario(UsuarioEnvio ue) {
		UsuarioEnvio respuesta = new UsuarioEnvio();
		if (ue == null || ue.getUsuario() == null
				|| ue.getUsuario().getIdusuario() == null || ue.getUsuario().getIdusuario().isEmpty()
				|| ue.getUsuario().getIdsesionactual() == null || ue.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ue.getUsuario().getIdsesionactual(),ue.getUsuario().getIdusuario())){
			try{
				respuesta.setUsuarioList((ArrayList<Usuario>)getUsuarioBo().findAll());

			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getUsuarioList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
			
	}
	
	@POST
	@Path("/listarParaAsignar")
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioEnvio listarParaAsignar(UsuarioEnvio ue) {
		UsuarioEnvio respuesta = new UsuarioEnvio();
		UsuarioBO usuBo = getUsuarioBo();
		
		if (ue == null || ue.getUsuario() == null
				|| ue.getUsuario().getIdusuario() == null || ue.getUsuario().getIdusuario().isEmpty()
				|| ue.getUsuario().getIdsesionactual() == null || ue.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ue.getUsuario().getIdsesionactual(),ue.getUsuario().getIdusuario())){
			try{
				List<Usuario>  listausuarios = usuBo.listarParaAsignar();
				respuesta.setUsuarioList((ArrayList<Usuario>) listausuarios);

			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getUsuarioList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
			
	}
	
	
	
	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public UsuarioEnvio registraUsuario(UsuarioEnvio ue){
		UsuarioEnvio respuesta = new UsuarioEnvio();
		if (ue == null || ue.getUsuario() == null
				|| ue.getUsuario().getIdusuario() == null || ue.getUsuario().getIdusuario().isEmpty()
				|| ue.getUsuario().getIdsesionactual() == null || ue.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		Usuario user = ue.getUsuario();
		
		if(NotariaUtils.isSesionValida(ue.getUsuario().getIdsesionactual(),ue.getUsuario().getIdusuario())){
			user.setIdsesion(ue.getUsuario().getIdsesionactual());
			user.setIdusuario(GeneradorId.generaId(ue));
			user.setDsvalenc(NotariaUtils.getStringMessageDigest(ue.getUsuario().getDsvalenc()));
			user.setInestatus(true);
			user.setIsactualizapwd(false);
			user.setIsactivo(true);
			System.out.println("fch inicio "+user.getFchinicio());
			try{
				respuesta.setUsuario(getUsuarioBo().save(user));
			}catch(Exception e){
				e.printStackTrace();
				ArrayList<CodigoError> errorList = new ArrayList<>();
				if(e instanceof SQLException){
					SQLException ex = (SQLException)e;
					if(ex.getErrorCode()==1062){
						CodigoError ce = new CodigoError();
						ce.setCodigo(ErrorCodigoMensaje.CODIGO_E03B1);
						ce.setMensaje(ErrorCodigoMensaje.MENSAJE_E03B1);
						errorList.add(ce);
					}else{
						CodigoError ce = new CodigoError();
						ce.setCodigo(ErrorCodigoMensaje.CODIGO_E57B4);
						ce.setMensaje(ErrorCodigoMensaje.MENSAJE_E57B4);
						errorList.add(ce);
					}
				} else if(e.getCause().toString().contains(": Duplicate entry")){
					respuesta.setEstatus("Usuario duplicado");
					CodigoError ce = new CodigoError();
					ce.setCodigo(ErrorCodigoMensaje.CODIGO_E03B1);
					ce.setMensaje(ErrorCodigoMensaje.MENSAJE_E03B1);
					errorList.add(ce);
					respuesta.setErrores(errorList);
				}else{
					e.printStackTrace(System.out);
					respuesta.setEstatus(e.getMessage());
					respuesta.setExito(false);
				}

				return respuesta;
			}

			if(respuesta.getUsuario()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/actualizar")
	@Produces({"application/json", "application/xml" })
	public UsuarioEnvio actualizar(UsuarioEnvio ue){
		UsuarioEnvio respuesta = new UsuarioEnvio();
		if (ue == null || ue.getUsuario() == null
				|| ue.getUsuario().getIdusuario() == null || ue.getUsuario().getIdusuario().isEmpty()
				|| ue.getUsuario().getIdsesionactual() == null || ue.getUsuario().getIdsesionactual().isEmpty()
				|| ue.getIdusuarioactualiza() == null || ue.getIdusuarioactualiza().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		Usuario usuario = ue.getUsuario();
		if(NotariaUtils.isSesionValida(ue.getUsuario().getIdsesionactual(),ue.getIdusuarioactualiza())){
			try{
				usuario.setIdsesion(ue.getUsuario().getIdsesionactual());
				if(ue.getUsuario().getDsvalenc()!=null)
					usuario.setDsvalenc(NotariaUtils.getStringMessageDigest(ue.getUsuario().getDsvalenc()));
				respuesta.setUsuario(getUsuarioBo().update(usuario));

			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getUsuario()== null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}	
	
	@POST
	@Path("/obtenerPorId")
	@Produces({ "application/json", "application/xml" })
	public UsuarioEnvio obtenerPorId(UsuarioEnvio ue) {
		UsuarioEnvio respuesta = new UsuarioEnvio();
		if (ue == null || ue.getUsuario() == null
				|| ue.getUsuario().getIdusuario() == null || ue.getUsuario().getIdusuario().isEmpty()
				|| ue.getUsuario().getIdsesionactual() == null || ue.getUsuario().getIdsesionactual().isEmpty()
				|| ue.getIdusuarioactualiza() == null || ue.getIdusuarioactualiza().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ue.getUsuario().getIdsesionactual(),ue.getIdusuarioactualiza())){
			try{
				respuesta.setUsuario(getUsuarioBo().findById(ue.getUsuario().getIdusuario()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getUsuario()==null){
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
	}		
	
	@POST
	@Path("/listarPorRol")
	@Produces({ "application/json", "application/xml" })
	public UsuarioEnvio listarUsuariosPorRol(UsuarioEnvio ue) {
		UsuarioEnvio respuesta = new UsuarioEnvio();
		if (ue == null || ue.getUsuario() == null
				|| ue.getUsuario().getIdusuario() == null || ue.getUsuario().getIdusuario().isEmpty()
				|| ue.getUsuario().getIdsesionactual() == null || ue.getUsuario().getIdsesionactual().isEmpty()
				|| ue.getRol() == null || ue.getRol().getIdrol().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		Rol rol = ue.getRol();
		if(NotariaUtils.isSesionValida(ue.getUsuario().getIdsesionactual(),ue.getUsuario().getIdusuario())){
			try{
			respuesta.setUsuarioList((ArrayList<Usuario>) getUsuarioBo().listarPorRol(rol));

			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getUsuarioList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/eliminar")
	@Produces({ "application/json", "application/xml" })
	public UsuarioEnvio eliminar(UsuarioEnvio ue) {
		UsuarioEnvio respuesta = new UsuarioEnvio();
		if (ue == null || ue.getUsuario() == null
				|| ue.getUsuario().getIdusuario() == null || ue.getUsuario().getIdusuario().isEmpty()
				|| ue.getUsuario().getIdsesionactual() == null || ue.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ue.getUsuario().getIdsesionactual(),ue.getUsuario().getIdusuario())){
			try{
				if(getUsuarioBo().delete(ue.getUsuario())){
					respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
					respuesta.setExito(true);
					return respuesta;
				}else{
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_ELIMINAR);
					respuesta.setExito(false);
					return respuesta;
				}

			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}			
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}		
	
	@POST
	@Path("/checkUsuario")
	@Produces(MediaType.APPLICATION_JSON)
	public Usuario checkUsuario(Usuario usuario){
		
		if(NotariaUtils.isSesionValida(usuario.getIdsesionactual(),usuario.getIdusuario())){
			try{
				Usuario user = getUsuarioBo().findById(usuario.getIdusuario());
				user.setIdsesionactual(usuario.getIdsesionactual());
				return user;
			}catch(Exception e){
				e.printStackTrace(System.out);
				return null;
			}
		} else {
			return null;
		}
	}

	@POST
	@Path("/actualizaContrasenia")
	@Produces(MediaType.APPLICATION_JSON)
		public UsuarioEnvio actualizaContrasenia(UsuarioEnvio usuarioEnvio){
			UsuarioEnvio respuesta = new UsuarioEnvio();
		try{
			if (usuarioEnvio== null 
			|| usuarioEnvio.getUsuario()==null
			|| usuarioEnvio.getUsuario().getDscorreo()==null
			|| usuarioEnvio.getUsuario().getDscorreo().isEmpty()
			|| usuarioEnvio.getTokenRestore()==null
			|| usuarioEnvio.getTokenRestore().isEmpty()) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			//290714 cafaray --> nuevamente, por no saber que los REST no son el negocio se coloco el generador de password en esta app
			usuarioEnvio.getUsuario().setDsvalenc(NotariaUtils.getStringMessageDigest(usuarioEnvio.getUsuario().getDsvalenc()));
			Usuario usuario = usuarioBo.actualizaContrasenia(usuarioEnvio.getUsuario(), usuarioEnvio.getTokenRestore());
			if(usuario!=null){				
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				return respuesta;
			}else{
				respuesta.setExito(false);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				return respuesta;
			}
		}catch(NotariaException e){
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/peticionRestauraContrasenia")
	@Produces(MediaType.APPLICATION_JSON)	
	public UsuarioEnvio peticionRestauraContrasenia(UsuarioEnvio usuarioEnvio){
		UsuarioEnvio respuesta = new UsuarioEnvio();
		try{
			if (usuarioEnvio== null 
			|| usuarioEnvio.getUsuario()==null
			|| usuarioEnvio.getUsuario().getDscorreo()==null
			|| usuarioEnvio.getUsuario().getDscorreo().isEmpty()) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			UsuarioRestaurar peticion = usuarioBo.peticionRestauraContrasenia(usuarioEnvio.getUsuario().getDscorreo());
			if(peticion!=null){
				//290714 --> cafaray La razon por la que se coloca aqui este codigo es debido a que los elementos de negocio los dejaron en el WEB y en BO (que pendejadas!!!)
				StringBuilder mensaje = new StringBuilder("Se ha realizado la petici&oacute;n de restaurar tu contrase&ntilde;a. ");
				mensaje.append("En caso de ser as&iacute;, el siguiente enlace te permite iniciar el procedimiento ");
				URI uri = uriInfo.getRequestUri();
				String host = uri.getHost();
				StringBuilder query = new StringBuilder("?");
				query.append("usuario=").append(peticion.getUsuario().getDscorreo()).append("&");
				query.append("token=").append(peticion.getIdpeticion()).append(peticion.getNuevaContrasenia());
				StringBuilder url = new StringBuilder("http://");
				url.append(host).append("/goodoc/restore.html#/");
				url.append(query);
				System.out.println("=====> gonotaria.restorePassword-url "+url.toString());
				mensaje.append("<br /><bold>").append(url).append("</bold>");
				mensaje.append("<p>").append("En caso de no haber s&iacute;do as&iacute;, no hagas caso de este correo.").append("</p>");
				try {
					EnviaCorreo.enviar(peticion.getUsuario().getDscorreo(), mensaje.toString(), "goNotarias:::recuperar contraseña", true);
				} catch (Exception e) {					
					//eliminar la peticion:
					usuarioBo.eliminaPeticion(peticion);
					respuesta.setExito(false);
					respuesta.setEstatus("Ocurrio un error al enviar el correo de confirmación, no se realizo la peticion.");
					e.printStackTrace(System.out);
					return respuesta;
				}
				//290714 <--
				respuesta.setExito(true);
				respuesta.setEstatus("Se te ha enviado un correo electrónico con el procedimiento para restaurar tu contraseña.");
				respuesta.setTokenRestore(peticion.getIdpeticion());
				return respuesta;
			}else{
				respuesta.setExito(false);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION+" No se genero la petición.");
				return respuesta;
			}						
		}catch(NotariaException e){
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION+" "+e.getMessage());
			e.printStackTrace(System.out);
			return respuesta;
		}
	}
	
	public UsuarioBO getUsuarioBo() {
		return usuarioBo;
	}

	public void setUsuarioService(UsuarioBO usuarioBo) {
		this.usuarioBo = usuarioBo;
	}		
}
