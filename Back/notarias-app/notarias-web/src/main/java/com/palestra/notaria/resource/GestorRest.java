package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.GestorBo;
import com.palestra.notaria.bo.impl.GestorBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.GestorEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Gestor;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/gestor")
public class GestorRest {
	
	GestorBo gestorBo;
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public GestorEnvio listar(GestorEnvio ge){
		GestorEnvio respuesta = new GestorEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			try{
				respuesta.setGestorList((ArrayList<Gestor>)getGestorBo().findAll());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	@Produces(MediaType.APPLICATION_JSON)
	public GestorEnvio guardar(GestorEnvio ge){
		GestorEnvio respuesta = new GestorEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			ge.getGestor().setIdgestor(GeneradorId.generaId(ge.getGestor()));
			ge.getGestor().setIdsesion(ge.getUsuario().getIdsesionactual());
			ge.getGestor().setInestatus(true);
			try{
				respuesta.setGestor(getGestorBo().save(ge.getGestor()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}	
	}
	
	@POST
	@Path("/actualizar")
	@Produces(MediaType.APPLICATION_JSON)
	public GestorEnvio actualizar(GestorEnvio ge){
		GestorEnvio respuesta = new GestorEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			ge.getGestor().setIdsesion(ge.getUsuario().getIdsesionactual());
			try{
				respuesta.setGestor(getGestorBo().update(ge.getGestor()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	public GestorRest(){
		this.gestorBo = new GestorBoImpl();
	}

	public GestorBo getGestorBo() {
		return gestorBo;
	}
}
