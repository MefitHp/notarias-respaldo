package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.GrupoBo;
import com.palestra.notaria.bo.impl.GrupoBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.GrupoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/grupos")
public class GrupoRest {
	
	static Logger logger = Logger.getLogger(GrupoRest.class);
	
	GrupoBo grupoBo;
	
	public GrupoRest(){
		grupoBo = new GrupoBoImpl();
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public GrupoEnvio getGrupos(GrupoEnvio ge) {
		GrupoEnvio respuesta = new GrupoEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			try{
				respuesta.setGrupoList((ArrayList<Grupo>)getGrupoBo().findAll());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getGrupoList()==null){
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
	public GrupoEnvio registraGrupo(GrupoEnvio ge){
		GrupoEnvio respuesta = new GrupoEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()
				|| ge.getGrupo() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			ge.getGrupo().setIdgrupo(GeneradorId.generaId(ge.getGrupo()));
			ge.getGrupo().setIdsesion(ge.getUsuario().getIdsesionactual());
			ge.getGrupo().setIsactivo(true);
			try{
				respuesta.setGrupo(getGrupoBo().save(ge.getGrupo()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getGrupo()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
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
	@Produces({"application/json", "application/xml" })
	public GrupoEnvio actualizar(GrupoEnvio ge) {
		GrupoEnvio respuesta = new GrupoEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()
				|| ge.getGrupo() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			ge.getGrupo().setIdsesion(ge.getUsuario().getIdsesionactual());
			try{
				respuesta.setGrupo(getGrupoBo().update(ge.getGrupo()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getGrupo()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
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
	
//	@POST
//	@Path("/eliminar")
//	@Produces({ "application/json", "application/xml" })
//	public Response eliminar(Grupo grupo) {
//		
//		try{
//			getGrupoBo().delete(grupo);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return Response.status(201).entity(grupo).build();
//
//	}
	
	@POST
	@Path("/obtenerPorId")
	@Produces({ "application/json", "application/xml" })
	public GrupoEnvio obtenerPorId(GrupoEnvio ge) {
		GrupoEnvio respuesta = new GrupoEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()
				|| ge.getGrupo() == null || ge.getGrupo().getIdgrupo() == null
				|| ge.getGrupo().getIdgrupo().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			ge.getGrupo().setIdsesion(ge.getUsuario().getIdsesionactual());
			try{
				respuesta.setGrupo(getGrupoBo().findById(ge.getGrupo().getIdgrupo()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getGrupo()==null){
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}

	}
	
	public GrupoBo getGrupoBo() {
		return grupoBo;
	}
	
	public void setGrupoBo(GrupoBo grupoBo) {
		this.grupoBo = grupoBo;
	}
}
