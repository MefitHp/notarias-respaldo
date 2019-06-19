package com.palestra.notaria.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.BitacoraEscrituraBo;
import com.palestra.notaria.bo.impl.BitacoraEscrituraBoImp;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.BitacoraEscrituraEnvio;
import com.palestra.notaria.envio.EscrituraEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraEscritura;

@Path("/bitacoraescritura")
public class BitacoraEscrituraRest {

	private BitacoraEscrituraBo bebo;
	
	public BitacoraEscrituraRest() {
		bebo = new BitacoraEscrituraBoImp();
	}
	
	@POST
	@Path("/buscarUltimoXEscritura")
	@Produces({ "application/json", "application/xml" })
	public BitacoraEscrituraEnvio buscarUltimoXEscritura(BitacoraEscrituraEnvio envio,
			@Context HttpServletRequest request) {
		
		BitacoraEscrituraEnvio respuesta = new BitacoraEscrituraEnvio();
		try {
			BitacoraEscritura be = bebo.obtenerUltimaBitacoraXEscritura(envio.getEscritura());
			respuesta.setUltimaBitacora(be);
			respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
		} catch (NotariaException e) {
			e.printStackTrace();
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA);
		}
		
		return respuesta;
	}

}
