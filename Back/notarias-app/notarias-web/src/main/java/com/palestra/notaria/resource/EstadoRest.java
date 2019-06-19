package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.EstadoBo;
import com.palestra.notaria.bo.impl.EstadoBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.EstadoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Estado;
import com.palestra.notaria.util.NotariaUtils;

/**
 * Clase que atiende las peticiones CRUD para el catalogo de Estados
 * @author sofia
 *
 */
@Path("/estado")
public class EstadoRest {
	
	static Logger logger = Logger.getLogger(EstadoRest.class);
	
	EstadoBo estadoBo;
	
	public EstadoRest(){
		this.estadoBo = new EstadoBoImpl();
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public EstadoEnvio getEstados(EstadoEnvio estadoRequest) {
		
		EstadoEnvio respuesta = new EstadoEnvio();
		
		if ( NotariaUtils.faltanRequeridosUsuario(estadoRequest) ) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils
				.isSesionValida(estadoRequest.getUsuario().getIdsesionactual(), estadoRequest.getUsuario().getIdusuario())){
			try{
				List<Estado> lista = this.estadoBo.findAll();
				respuesta.setListaEstados(new ArrayList<Estado>(lista));
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
