package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.ValuadorBo;
import com.palestra.notaria.bo.impl.ValuadorBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.ValuadorEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Valuador;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/valuador")
public class ValuadorRest {
	
	private ValuadorBo valuadorBo;
	
	public ValuadorRest(){
		this.valuadorBo = new ValuadorBoImpl();
	}

	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public ValuadorEnvio listar(ValuadorEnvio ge){
		ValuadorEnvio respuesta = new ValuadorEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			try{
				respuesta.setValuadorList((ArrayList<Valuador>)getValuadorBo().findAll());

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
	public ValuadorEnvio guardar(ValuadorEnvio ge){
		ValuadorEnvio respuesta = new ValuadorEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			ge.getValuador().setIdvaluador(GeneradorId.generaId(ge.getValuador()));
			ge.getValuador().setIdsesion(ge.getUsuario().getIdsesionactual());
			ge.getValuador().setInestatus(true);
			try{
				respuesta.setValuador(getValuadorBo().save(ge.getValuador()));

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
	public ValuadorEnvio actualizar(ValuadorEnvio ge){
		ValuadorEnvio respuesta = new ValuadorEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			ge.getValuador().setIdsesion(ge.getUsuario().getIdsesionactual());
			try{
				respuesta.setValuador(getValuadorBo().update(ge.getValuador()));

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
	
	public ValuadorBo getValuadorBo() {
		return valuadorBo;
	}
}
