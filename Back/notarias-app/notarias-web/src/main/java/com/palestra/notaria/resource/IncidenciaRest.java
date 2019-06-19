package com.palestra.notaria.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.IncidenciaBo;
import com.palestra.notaria.bo.impl.IncidenciaBoImp;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.IncidenciaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Incidencia;
import com.palestra.notaria.util.GeneradorId;

@Path("/incidencias")
public class IncidenciaRest {

	
	static Logger logger = Logger.getLogger(IncidenciaRest.class);

	IncidenciaBo inBo;
	
	public IncidenciaRest() {
		inBo = new IncidenciaBoImp();
	}

	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public IncidenciaEnvio getIncidencias() {
		
		List<Incidencia> incidencias;
		IncidenciaEnvio respuesta = new IncidenciaEnvio();

		try {
			incidencias = this.inBo.findAll();
			respuesta.setIncidencias(incidencias);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
		} catch (NotariaException e) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
		}
		return respuesta;
		
		
	}
	
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public IncidenciaEnvio saveIncidencias(IncidenciaEnvio in) {
		IncidenciaEnvio respuesta = new IncidenciaEnvio();
		
		try {
			
			Incidencia incidencia = in.getIncidencia();
			GeneradorId.generaId(incidencia);
			this.inBo.save(incidencia);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);			
			
		} catch (Exception e) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
		}
		
		return respuesta;
		
		
	}
	
	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public IncidenciaEnvio deleteIncidencias(IncidenciaEnvio in) {
		IncidenciaEnvio respuesta = new IncidenciaEnvio();
		try {
			Incidencia incidencia = this.inBo.findById(in.getIncidencia().getIdincidencia());
			this.inBo.delete(incidencia);
			respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
		} catch (Exception e) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA);
		}
		
		return respuesta;

	}
	
	
	@POST
	@Path("/buscarXid")
	@Produces(MediaType.APPLICATION_JSON)
	public IncidenciaEnvio buscarXid(IncidenciaEnvio in) {
		IncidenciaEnvio respuesta = new IncidenciaEnvio();
		try {
			Incidencia incidencia = this.inBo.findById(in.getIncidencia().getIdincidencia());
			respuesta.setIncidencia(incidencia);
			respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
		} catch (NotariaException e) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
		}
		return respuesta;
		
	}
	
	
}

