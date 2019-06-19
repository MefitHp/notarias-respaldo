package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.AlertaObjetoBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.AlertaObjetoBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.ActoEnvio;
import com.palestra.notaria.envio.AlertaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.AlertaObjeto;
import com.palestra.notaria.util.NotariaUtils;

//import alertas.AlertaDto;
//import enums.Respuestas;

@Path("/alertas")
public class AlertaObjetoRest {

static Logger logger = Logger.getLogger(AlertaObjetoRest.class);
	
	private AlertaObjetoBo alertaBo;
	
	public AlertaObjetoRest(){
		alertaBo  = new AlertaObjetoBoImpl();
	}
	
	
	@POST
	@Path("/updatestatus")
	@Produces(MediaType.APPLICATION_JSON)
	public AlertaEnvio actualizaEstatus(AlertaEnvio ae){
		AlertaEnvio respuesta = new AlertaEnvio();
		try {
			AlertaObjeto alerta= getBoAlerta().getByObjeto(ae.getAlertaobjeto().getIdalertaobjeto());
			alerta.setStatusalerta(ae.getAlertaobjeto().getStatusalerta());
			if(alerta!=null){
				getBoAlerta().update(alerta);
				ae.setExito(true);
				respuesta.setExito(true);
				//respuesta.setEstatus(Respuestas.ACTUALIZACION_EXITOSO.toString());
			}else{
				throw new NotariaException();
			}
		} catch (NotariaException e) {
			respuesta.setExito(false);
			//respuesta.setEstatus(Respuestas.ACTUALIZACION_FALLO.toString()+ ":: alerta-objeto");
		}
		
		return respuesta;
		
	}
	
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	public AlertaEnvio saveAlerta(AlertaEnvio ae) {
		AlertaEnvio respuesta = new AlertaEnvio();
		/*
		 * DESHABILITO LA VALIDACION DE USUARIO POR QUE SERA UN PROCESO AUTONOMO
		 * if (ae == null || ae.getUsuario() == null
				|| ae.getUsuario().getIdusuario() == null || ae.getUsuario().getIdusuario().isEmpty()
				|| ae.getUsuario().getIdsesionactual() == null || ae.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){*/
			
			
			try {
				getBoAlerta().save(ae.getAlertaobjeto());
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			
		/*}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}*/
	
	}
	
	private AlertaObjetoBo getBoAlerta(){
		return this.alertaBo;
	}
}
