package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.bcel.generic.INSTANCEOF;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.GrupoTrabajoBo;
import com.palestra.notaria.bo.impl.GrupoTrabajoBoImp;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.GrupoTrabajoEnvio;
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
@Path("/grupotrabajo")
public class GrupoTrabajoRest {
	
	
	GrupoTrabajoBo grupoTrabajoBo;
	
	public GrupoTrabajoRest(){
		grupoTrabajoBo = new GrupoTrabajoBoImp();
	}
	
	
	
	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public GrupoTrabajoEnvio guardarGrupo(GrupoTrabajoEnvio GrupoTrabajoEnvioRequest,
			@Context HttpServletRequest request) throws JSONException {
		
		GrupoTrabajoEnvio grupoTrabajoResponse = new GrupoTrabajoEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(GrupoTrabajoEnvioRequest) || GrupoTrabajoEnvioRequest.getGrupotrabajo()==null) {
			grupoTrabajoResponse.setExito(false);
			grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return grupoTrabajoResponse;
		}
		
		if (NotariaUtils.isSesionValida(GrupoTrabajoEnvioRequest.getUsuario()
				.getIdsesionactual(), GrupoTrabajoEnvioRequest.getUsuario().getIdusuario())) {
			
			
			try {
				GrupoTrabajoEnvioRequest.getGrupotrabajo().setIdsesion(GrupoTrabajoEnvioRequest.getUsuario().getIdsesionactual());
				GrupoTrabajoEnvioRequest.getGrupotrabajo().setTmstmp(new Timestamp((new Date()).getTime()));
				
				
				if(GrupoTrabajoEnvioRequest.getGrupotrabajo().getIdgrupotrabajo() !=null){
					this.grupoTrabajoBo.update(GrupoTrabajoEnvioRequest.getGrupotrabajo());
					grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				}else{
					GrupoTrabajoEnvioRequest.getGrupotrabajo().setIdgrupotrabajo(GeneradorId.generaId(GrupoTrabajoEnvioRequest));
					this.grupoTrabajoBo.save(GrupoTrabajoEnvioRequest.getGrupotrabajo());
					grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				}
				
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
	public GrupoTrabajoEnvio actualizarGrupo(GrupoTrabajoEnvio GrupoTrabajoEnvioRequest,
			@Context HttpServletRequest request) throws JSONException {
		
		GrupoTrabajoEnvio grupoTrabajoResponse = new GrupoTrabajoEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(GrupoTrabajoEnvioRequest) || GrupoTrabajoEnvioRequest.getGrupotrabajo()==null) {
			grupoTrabajoResponse.setExito(false);
			grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return grupoTrabajoResponse;
		}
		
		if (NotariaUtils.isSesionValida(GrupoTrabajoEnvioRequest.getUsuario()
				.getIdsesionactual(), GrupoTrabajoEnvioRequest.getUsuario().getIdusuario())) {
			
			
			try {
				
				if(!this.grupoTrabajoBo.delete(GrupoTrabajoEnvioRequest.getGrupotrabajo().getIdgrupotrabajo())){
					throw new NotariaException("No se logro eliminar el elemento");
				};
				grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
				return grupoTrabajoResponse;
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				grupoTrabajoResponse.setEstatus(e.getMessage());
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
	public GrupoTrabajoEnvio listarGrupo(GrupoTrabajoEnvio GrupoTrabajoEnvioRequest,
			@Context HttpServletRequest request) throws JSONException {
		
		GrupoTrabajoEnvio grupoTrabajoResponse = new GrupoTrabajoEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(GrupoTrabajoEnvioRequest)	) {
			grupoTrabajoResponse.setExito(false);
			grupoTrabajoResponse.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return grupoTrabajoResponse;
		}
		
		if (NotariaUtils.isSesionValida(GrupoTrabajoEnvioRequest.getUsuario()
				.getIdsesionactual(), GrupoTrabajoEnvioRequest.getUsuario().getIdusuario())) {
			
			
			try {
				grupoTrabajoResponse.setGrupos(grupoTrabajoBo.findAll());
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
	
	

}
