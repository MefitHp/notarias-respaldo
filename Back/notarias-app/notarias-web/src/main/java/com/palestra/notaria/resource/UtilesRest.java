package com.palestra.notaria.resource;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.modelo.Notaria;
import com.palestra.notaria.util.NotariaUtils;


/**
 * Clase del tipo util para el sistema notarial
 * 
 * @author Víctor Espinosa
 * 
 */
@Path("/utiles")
public class UtilesRest {
	
	
	@GET
	@Path("/obtieneLog")
	@Produces("text/plain")	
	public String descargarDocumento(@QueryParam("lines") int lines,
			@QueryParam("idusuario") String idusuario,
			@QueryParam("idsesionactual") String idsesionactual,
			@Context HttpServletResponse response) {
		
		boolean sesion = NotariaUtils.isSesionValida(idsesionactual, idusuario);
		if (sesion) {
			
			if (lines<1) lines = 500;
			
			File catalina = new File(System.getProperty("catalina.base"), "./logs/catalina.out");
			return NotariaUtils.tail(catalina, lines);
		} else {
			return Constantes.ESTATUS_SESION_INVALIDA;
		}
		
	}
	
	

}
