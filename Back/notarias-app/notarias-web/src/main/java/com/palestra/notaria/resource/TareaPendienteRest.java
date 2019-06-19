package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.TareaPendienteBo;
import com.palestra.notaria.bo.impl.TareaPendienteBoImpl;
import com.palestra.notaria.envio.TareaPendienteEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.TareaPendiente;
import com.palestra.notaria.util.NotariaUtils;

@Path("/tareaPendiente")
public class TareaPendienteRest {
	
	static Logger logger = Logger.getLogger(TareaPendienteRest.class);
	private TareaPendienteBo tareaPendienteBo = new TareaPendienteBoImpl();
	

	@POST
	@Path("/obtenerTareasPendientes")
	@Produces({ "application/json", "application/xml" })
	public TareaPendienteEnvio obtenerTareasPendientes(TareaPendienteEnvio tareaPendienteEnvio,@Context HttpServletRequest request) throws JSONException{
	
		
		TareaPendienteEnvio respuesta = new TareaPendienteEnvio();
		ArrayList<TareaPendiente> resultado=null;
		
		if (tareaPendienteEnvio == null || tareaPendienteEnvio.getUsuario() == null
				|| tareaPendienteEnvio.getUsuario().getIdusuario() == null
				|| tareaPendienteEnvio.getUsuario().getIdsesionactual() == null) {
			respuesta.setEstatus("Faltan parametros");
			respuesta.setExito(false);
			return respuesta;
		}
		
		//cambiar validacion por idsesion que envie la vista
		if (NotariaUtils.isSesionValida(tareaPendienteEnvio.getUsuario()
				.getIdsesionactual(), tareaPendienteEnvio.getUsuario().getIdusuario())) {
			try{
				List<TareaPendiente> lista = this.tareaPendienteBo.obtenerListaCompleta(tareaPendienteEnvio.getUsuario());
				if(lista!=null)
					resultado = new ArrayList<TareaPendiente>(lista);
				else
					resultado = new ArrayList<TareaPendiente>();
				
				respuesta.setEstatus("Busqueda correcta");
				respuesta.setLista(resultado);
				
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				respuesta.setLista(new ArrayList<TareaPendiente>());
				return respuesta;
			}
			
		}else{
			resultado = new ArrayList<TareaPendiente>();
			respuesta.setEstatus("La sesion no es válida");
			respuesta.setLista(resultado);
			respuesta.setExito(false);
			
		}
		
		return respuesta;
	}
	
	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public TareaPendienteEnvio obtenerPorIdCompleto(TareaPendienteEnvio tareaPendienteEnvio,@Context HttpServletRequest request) throws JSONException{
	
		TareaPendienteEnvio respuesta = new TareaPendienteEnvio();
		TareaPendiente tp=null;
		if (tareaPendienteEnvio == null || tareaPendienteEnvio.getUsuario() == null
				|| tareaPendienteEnvio.getUsuario().getIdusuario() == null
				|| tareaPendienteEnvio.getUsuario().getIdsesionactual() == null
				|| tareaPendienteEnvio.getTareaPendiente()==null) {
			
			respuesta.setEstatus("Faltan parametros");
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(tareaPendienteEnvio.getUsuario()
				.getIdsesionactual(), tareaPendienteEnvio.getUsuario().getIdusuario())){
			
			try{
				tp = this.tareaPendienteBo.buscarPorIdCompleto(tareaPendienteEnvio.getTareaPendiente());
				respuesta.setEstatus("Busqueda correcta");
				respuesta.setTareaPendiente(tp);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}			
		}else{
			
			respuesta.setEstatus("La sesion no es válida");
			respuesta.setExito(false);
		}
		
		return respuesta;
	}
	

}
