package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.MunicipioBo;
import com.palestra.notaria.bo.impl.MunicipioBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.MunicipioEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Municipio;
import com.palestra.notaria.util.NotariaUtils;

/**
 * Clase que atiende las peticiones CRUD para el catalogo de 
 * municipio.
 * 
 * @author Sofia
 *
 */
@Path("/municipio")
public class MunicipioRest {
	
	static Logger logger = Logger.getLogger(MunicipioRest.class);
	
	MunicipioBo municipioBo;
	
	public MunicipioRest(){
		municipioBo = new MunicipioBoImpl();
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public MunicipioEnvio getMunicipios(MunicipioEnvio municipioRequest) {
		
		MunicipioEnvio respuesta = new MunicipioEnvio();
		
		if ( NotariaUtils.faltanRequeridosUsuario(municipioRequest) ) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if(NotariaUtils.isSesionValida(municipioRequest.getUsuario().getIdsesionactual(), municipioRequest.getUsuario().getIdusuario())){
			try{
				List<Municipio> lista = this.municipioBo.findAll();
				respuesta.setListaMunicipios(new ArrayList<Municipio>(lista));
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
