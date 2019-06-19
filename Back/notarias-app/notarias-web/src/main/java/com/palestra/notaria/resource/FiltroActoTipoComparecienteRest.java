package com.palestra.notaria.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.FiltroActoComparecienteBo;
import com.palestra.notaria.bo.impl.FiltroActoComparecienteBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.FiltroActoTipoCompaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;


@Path("/filtroActoCompareciente")

public class FiltroActoTipoComparecienteRest {

	
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public FiltroActoTipoCompaEnvio guardaFiltro(FiltroActoTipoCompaEnvio filtroCompaEnvio){
		FiltroActoTipoCompaEnvio respuesta = new FiltroActoTipoCompaEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(filtroCompaEnvio)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(filtroCompaEnvio.getUsuario().getIdsesionactual(), filtroCompaEnvio.getUsuario().getIdusuario())){
			try{
				FiltroActoComparecienteBo filtroBo = new FiltroActoComparecienteBoImpl();
				
				//ex.getExpresion().setIdexpresion(GeneradorId.generaId(ex.getExpresion()));
				//ex.getExpresion().setIdsesion(ex.getUsuario().getIdsesionactual());
				filtroCompaEnvio.getFiltroActoCompareciente().setIdfiltroActoCompareciente(GeneradorId.generaId(filtroCompaEnvio.getFiltroActoCompareciente()));
				filtroCompaEnvio.getFiltroActoCompareciente().setIdsesion(filtroCompaEnvio.getUsuario().getIdsesionactual());
				filtroBo.save(filtroCompaEnvio.getFiltroActoCompareciente());
				respuesta.setFiltroActoCompareciente(filtroCompaEnvio.getFiltroActoCompareciente());
				
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
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
	@Path("/borrar")
	@Produces(MediaType.APPLICATION_JSON)
	public FiltroActoTipoCompaEnvio eliminarFiltro(FiltroActoTipoCompaEnvio filtroCompaEnvio){
		FiltroActoTipoCompaEnvio respuesta = new FiltroActoTipoCompaEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(filtroCompaEnvio)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(filtroCompaEnvio.getUsuario().getIdsesionactual(), filtroCompaEnvio.getUsuario().getIdusuario())){
			try{
				FiltroActoComparecienteBo filtroBo = new FiltroActoComparecienteBoImpl();
				filtroBo.borrar(filtroCompaEnvio.getFiltroActoCompareciente());
				respuesta.setFiltroActoCompareciente(filtroCompaEnvio.getFiltroActoCompareciente());
				respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
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
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public FiltroActoTipoCompaEnvio listarFiltros(FiltroActoTipoCompaEnvio filtroCompaEnvio){
		FiltroActoTipoCompaEnvio respuesta = new FiltroActoTipoCompaEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(filtroCompaEnvio)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(filtroCompaEnvio.getUsuario().getIdsesionactual(), filtroCompaEnvio.getUsuario().getIdusuario())){
			try{
				FiltroActoComparecienteBo filtroBo = new FiltroActoComparecienteBoImpl();
				respuesta.setListafiltros(filtroBo.findAll());
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
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
	
	
	
}
