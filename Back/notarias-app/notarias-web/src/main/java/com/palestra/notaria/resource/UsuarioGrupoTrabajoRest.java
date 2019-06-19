package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.UsuarioGrupoTrabajoBo;
import com.palestra.notaria.bo.impl.UsuarioGrupoTrabajoBoImp;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.GrupoTrabajoEnvio;
import com.palestra.notaria.envio.UsuarioGrupoTrabajoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;


/**
 * Clase que atiente peticiones CRUD para Asignar usuarios a grupos de trabajo
 * Esto para el manejo de los expedientes por diferentes personas
 * 
 * @author Víctor Espinosa
 * 
 */
@Path("/usrgrupotrabajo")
public class UsuarioGrupoTrabajoRest {
	
	
	UsuarioGrupoTrabajoBo usuarioGrupoTrabajoBo;
	
	public UsuarioGrupoTrabajoRest(){
		usuarioGrupoTrabajoBo = new UsuarioGrupoTrabajoBoImp();
	}
	
	
	
	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public UsuarioGrupoTrabajoEnvio guardarGrupo(UsuarioGrupoTrabajoEnvio UsuarioGrupoTrabajoEnvioRequest,
			@Context HttpServletRequest request) throws JSONException {
		
		UsuarioGrupoTrabajoEnvio grupoTrabajoResponse = new UsuarioGrupoTrabajoEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(UsuarioGrupoTrabajoEnvioRequest) || UsuarioGrupoTrabajoEnvioRequest.getUsuariogrupotrabajo()==null) {
			grupoTrabajoResponse.setExito(false);
			grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return grupoTrabajoResponse;
		}
		
		if (NotariaUtils.isSesionValida(UsuarioGrupoTrabajoEnvioRequest.getUsuario()
				.getIdsesionactual(), UsuarioGrupoTrabajoEnvioRequest.getUsuario().getIdusuario())) {
			
			
			try {
				UsuarioGrupoTrabajoEnvioRequest.getUsuariogrupotrabajo().setIdsesion(UsuarioGrupoTrabajoEnvioRequest.getUsuario().getIdsesionactual());
				UsuarioGrupoTrabajoEnvioRequest.getUsuariogrupotrabajo().setTmstmp(new Timestamp((new Date()).getTime()));
				
					UsuarioGrupoTrabajoEnvioRequest.getUsuariogrupotrabajo().setIdusuariogrupotrabajo(GeneradorId.generaId(UsuarioGrupoTrabajoEnvioRequest));
					this.usuarioGrupoTrabajoBo.save(UsuarioGrupoTrabajoEnvioRequest.getUsuariogrupotrabajo());
					grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				
				return grupoTrabajoResponse;
				
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
				grupoTrabajoResponse.setExito(false);
				return grupoTrabajoResponse;				
				
			}
		} else {
			grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			grupoTrabajoResponse.setExito(false);
		}

		return grupoTrabajoResponse;
	}
	
	
	@POST
	@Path("/borrar")
	@Produces({ "application/json", "application/xml" })
	public UsuarioGrupoTrabajoEnvio actualizarGrupo(UsuarioGrupoTrabajoEnvio UsuarioGrupoTrabajoEnvioRequest,
			@Context HttpServletRequest request) throws JSONException {
		
		UsuarioGrupoTrabajoEnvio grupoTrabajoResponse = new UsuarioGrupoTrabajoEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(UsuarioGrupoTrabajoEnvioRequest) || UsuarioGrupoTrabajoEnvioRequest.getUsuariogrupotrabajo()==null) {
			grupoTrabajoResponse.setExito(false);
			grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return grupoTrabajoResponse;
		}
		
		if (NotariaUtils.isSesionValida(UsuarioGrupoTrabajoEnvioRequest.getUsuario()
				.getIdsesionactual(), UsuarioGrupoTrabajoEnvioRequest.getUsuario().getIdusuario())) {
			
			
			try {
				
				if(!this.usuarioGrupoTrabajoBo.borrarUsuarioGrupo(UsuarioGrupoTrabajoEnvioRequest.getUsuariogrupotrabajo().getIdusuariogrupotrabajo())){
					throw new NotariaException("No se logro eliminar el elemento");
				};
				grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
				return grupoTrabajoResponse;
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
				grupoTrabajoResponse.setExito(false);
				return grupoTrabajoResponse;				
				
			}
		} else {
			grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			grupoTrabajoResponse.setExito(false);
		}

		return grupoTrabajoResponse;
	}
	
	@POST
	@Path("/listar")
	@Produces({ "application/json", "application/xml" })
	public UsuarioGrupoTrabajoEnvio listarGrupo(UsuarioGrupoTrabajoEnvio UsuarioGrupoTrabajoEnvioRequest,
			@Context HttpServletRequest request) throws JSONException {
		
		UsuarioGrupoTrabajoEnvio usuariogrupoTrabajoResponse = new UsuarioGrupoTrabajoEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(UsuarioGrupoTrabajoEnvioRequest)	) {
			usuariogrupoTrabajoResponse.setExito(false);
			usuariogrupoTrabajoResponse.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return usuariogrupoTrabajoResponse;
		}
		
		if (NotariaUtils.isSesionValida(UsuarioGrupoTrabajoEnvioRequest.getUsuario()
				.getIdsesionactual(), UsuarioGrupoTrabajoEnvioRequest.getUsuario().getIdusuario())) {
			
			
			try {
				usuariogrupoTrabajoResponse.setGruposdetrabajo(this.usuarioGrupoTrabajoBo.findAll());
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				usuariogrupoTrabajoResponse.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
				usuariogrupoTrabajoResponse.setExito(false);
				return usuariogrupoTrabajoResponse;				
				
			}
		} else {
			usuariogrupoTrabajoResponse.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			usuariogrupoTrabajoResponse.setExito(false);
		}

		return usuariogrupoTrabajoResponse;
	}
	
	@POST
	@Path("/listarXgrupo")
	@Produces({ "application/json", "application/xml" })
	public UsuarioGrupoTrabajoEnvio listarGrupoXresponsable(UsuarioGrupoTrabajoEnvio UsuarioGrupoTrabajoEnvioRequest,
			@Context HttpServletRequest request) throws JSONException {
		
		UsuarioGrupoTrabajoEnvio usuariogrupoTrabajoResponse = new UsuarioGrupoTrabajoEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(UsuarioGrupoTrabajoEnvioRequest)	) {
			usuariogrupoTrabajoResponse.setExito(false);
			usuariogrupoTrabajoResponse.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return usuariogrupoTrabajoResponse;
		}
		
		if (NotariaUtils.isSesionValida(UsuarioGrupoTrabajoEnvioRequest.getUsuario()
				.getIdsesionactual(), UsuarioGrupoTrabajoEnvioRequest.getUsuario().getIdusuario())) {
			
			
			try {
				StringBuilder grupoTrabajoId = new StringBuilder(UsuarioGrupoTrabajoEnvioRequest.getUsuariogrupotrabajo().getGrupoTrabajo().getIdgrupotrabajo());
				usuariogrupoTrabajoResponse.setGruposdetrabajo(this.usuarioGrupoTrabajoBo.buscarXgrupo(grupoTrabajoId.toString()));
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				usuariogrupoTrabajoResponse.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
				usuariogrupoTrabajoResponse.setExito(false);
				return usuariogrupoTrabajoResponse;				
				
			}
		} else {
			usuariogrupoTrabajoResponse.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			usuariogrupoTrabajoResponse.setExito(false);
		}

		return usuariogrupoTrabajoResponse;
	}
	
	

}
