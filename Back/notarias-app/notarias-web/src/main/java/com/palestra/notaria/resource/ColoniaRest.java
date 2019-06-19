package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ColoniaBo;
import com.palestra.notaria.bo.impl.ColoniaBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.ColoniaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Colonia;
import com.palestra.notaria.util.NotariaUtils;

/**
 * Clase que atiende las peticiones CRUD para el catalogo de Colonia
 * 
 * @author sofia
 * 
 */
@Path("/colonia")
public class ColoniaRest {

	static Logger logger = Logger.getLogger(ColoniaRest.class);

	ColoniaBo coloniaBo;

	public ColoniaRest() {
		coloniaBo = new ColoniaBoImpl();
	}

	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public ColoniaEnvio getColonias(ColoniaEnvio coloniaRequest) {

		ColoniaEnvio respuesta = new ColoniaEnvio();

		if (NotariaUtils.faltanRequeridosUsuario(coloniaRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(coloniaRequest.getUsuario()
				.getIdsesionactual(), coloniaRequest.getUsuario()
				.getIdusuario())) {
			try {
				List<Colonia> lista = this.coloniaBo.findAll();
				respuesta.setListaColonias(new ArrayList<Colonia>(lista));
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;

			} catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

}
