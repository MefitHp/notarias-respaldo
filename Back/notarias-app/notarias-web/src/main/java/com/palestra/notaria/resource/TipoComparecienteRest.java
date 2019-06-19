package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.FiltroActoComparecienteBo;
import com.palestra.notaria.bo.TipoComparecienteBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.FiltroActoComparecienteBoImpl;
import com.palestra.notaria.bo.impl.TipoComparecienteBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.ComparecienteEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.FiltroActoCompareciente;
import com.palestra.notaria.modelo.TipoCompareciente;
import com.palestra.notaria.util.NotariaUtils;

@Path("listarTipoCompareciente")
public class TipoComparecienteRest {
	
	private TipoComparecienteBo tipoComparecienteBo;
	
	public TipoComparecienteRest(){
		this.tipoComparecienteBo = new TipoComparecienteBoImpl();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio getEstados(ComparecienteEnvio estadoRequest) {
		
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		
		if ( NotariaUtils.faltanRequeridosUsuario(estadoRequest) ) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils
				.isSesionValida(estadoRequest.getUsuario().getIdsesionactual(), estadoRequest.getUsuario().getIdusuario())){
			try{
				respuesta.setTipoComparecienteList((ArrayList<TipoCompareciente>)getTipoComparecienteBo().findAll());
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
	
	
	@POST
	@Path("/comparecienteXsuboperacion")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio comparecienteXsuboperacion(ComparecienteEnvio estadoRequest) {
		
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		
		if ( NotariaUtils.faltanRequeridosUsuario(estadoRequest) ) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils
				.isSesionValida(estadoRequest.getUsuario().getIdsesionactual(), estadoRequest.getUsuario().getIdusuario())){
			try{
				FiltroActoComparecienteBo filCompaBo = new FiltroActoComparecienteBoImpl();
				ArrayList<TipoCompareciente> respuestaFiltrada = new ArrayList<TipoCompareciente>();
				ActoBo actoBo = new ActoBoImpl();
				Acto actoDato = actoBo.buscarPorIdCompleto(estadoRequest.getActo().getIdacto());
				List<FiltroActoCompareciente> listaFiltro = filCompaBo.comparecientesXSubop(actoDato.getSuboperacion().getOperacion());
				for(FiltroActoCompareciente fc:listaFiltro){
					respuestaFiltrada.add(fc.getTipoCompareciente());
				}
				respuesta.setTipoComparecienteList(respuestaFiltrada);
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
	
	
	
	public TipoComparecienteBo getTipoComparecienteBo() {
		return tipoComparecienteBo;
	}
	
	public void setTipoComparecienteBo(TipoComparecienteBo tipoComparecienteBo) {
		this.tipoComparecienteBo = tipoComparecienteBo;
	}

}
