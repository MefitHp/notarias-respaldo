package com.palestra.notaria.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.LibroBo;
import com.palestra.notaria.bo.impl.LibroBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.LibroEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.util.NotariaUtils;

/**
 * Clase que atiente peticiones CRUD para Libro
 * 
 * @author sofia
 * 
 */
@Path("/libro")
public class LibroRest {

	static Logger logger = Logger.getLogger(EscrituraRest.class);
	
	LibroBo libroBo;
	
	public LibroRest(){
		libroBo = new LibroBoImpl();
	}
	
	@POST
	@Path("/obtenUltimoLibro")
	@Produces({ "application/json", "application/xml" })
	public LibroEnvio obtenerUltimoLibro(LibroEnvio libroRequest,
			@Context HttpServletRequest request) throws JSONException {
		
		LibroEnvio libroResponse = new LibroEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(libroRequest)) {
			libroResponse.setExito(false);
			libroResponse.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return libroResponse;
		}
		
		if (NotariaUtils.isSesionValida(libroRequest.getUsuario()
				.getIdsesionactual(), libroRequest.getUsuario().getIdusuario())) {
			
			try {
				libroResponse.setLibro(libroBo.obtenUltimoLibro());
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				libroResponse.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
				libroResponse.setExito(false);
				return libroResponse;
			}
			
		} else {
			libroResponse.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			libroResponse.setExito(false);
		}

		return libroResponse;
	}
}
