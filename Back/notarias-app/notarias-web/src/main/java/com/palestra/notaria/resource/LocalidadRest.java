package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.LocalidadBo;
import com.palestra.notaria.bo.impl.LocalidadBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.LocalidadEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Localidad;
import com.palestra.notaria.util.NotariaUtils;

/**
 * Clase que atiende las peticiones CRUD para el catalogo
 * Localidad.
 * 
 * @author sofia
 *
 */
@Path("/localidad")
public class LocalidadRest {
	
	static Logger logger = Logger.getLogger(LocalidadRest.class);
	
	LocalidadBo localidadBo;
	
	public LocalidadRest(){
		localidadBo = new LocalidadBoImpl();
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public LocalidadEnvio getLocalidades(LocalidadEnvio localidadRequest) {
		
		LocalidadEnvio respuesta = new LocalidadEnvio();
		
		if ( NotariaUtils.faltanRequeridosUsuario(localidadRequest) ) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if(NotariaUtils.isSesionValida(localidadRequest.getUsuario().getIdsesionactual(), localidadRequest.getUsuario().getIdusuario())){
			try{
				List<Localidad> lista = this.localidadBo.findAll();
				respuesta.setListaLocalidades(new ArrayList<Localidad>(lista));
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
