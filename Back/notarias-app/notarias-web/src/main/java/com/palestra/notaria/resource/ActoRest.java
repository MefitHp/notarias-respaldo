package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.enums.EnumStatusDoc;
import com.palestra.notaria.envio.ActoDocumentoEnvio;
import com.palestra.notaria.envio.ActoEnvio;
import com.palestra.notaria.envio.MesaCtlEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.ProcessActo;
import com.palestra.notaria.modelo.ProcessActoPk;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

import pojos.pojos.BonitaCommonBean;
import pojos.pojos.TareaBean;

@Path("/actos")
public class ActoRest {
	
	static Logger logger = Logger.getLogger(ActoRest.class);
	
	private ActoBo actoBo;
	
	public ActoRest(){
		actoBo  = new ActoBoImpl();
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoEnvio getActos(ActoEnvio ae) {
		ActoEnvio respuesta = new ActoEnvio();
		if (ae == null || ae.getUsuario() == null
				|| ae.getUsuario().getIdusuario() == null || ae.getUsuario().getIdusuario().isEmpty()
				|| ae.getUsuario().getIdsesionactual() == null || ae.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			try {
				respuesta.setActoList((ArrayList<Acto>)getActoBo().findAll());
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public Acto registraActo(Acto acto,@Context HttpServletRequest request){
		Date fecha = new Date();
		acto.setIdacto(GeneradorId.generaId(acto));
		acto.setIdsesion(request.getSession().getId());
		acto.setTmstmp(new Timestamp(fecha.getTime()));

		try {
			return getActoBo().save(acto);
		} catch (NotariaException e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	@POST
	@Path("/actualizar")
	@Produces({"application/json", "application/xml" })
	public Acto actualizar(Acto acto,@Context HttpServletRequest request) {
		Date fecha = new Date();
		acto.setIdsesion(request.getSession().getId());
		acto.setTmstmp(new Timestamp(fecha.getTime()));

		try {
			return getActoBo().update(acto);
		} catch (NotariaException e) {
			e.printStackTrace(System.out);
			return null;
		}

	}
	
//	@POST
//	@Path("/eliminar")
//	@Produces({ "application/json", "application/xml" })
//	public boolean eliminar(Acto acto) {
//		
//		try {
//			return getActoBo().delete(acto);
//		} catch (NotariaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@POST
	@Path("/obtenerPorId")
	@Produces({ "application/json", "application/xml" })
	public ActoEnvio obtenerPorId(ActoEnvio datoEnvio,
			@Context HttpServletRequest request) {
		
		ActoEnvio respuesta = new ActoEnvio();
		
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getActo()==null || datoEnvio.getActo().getIdacto()==null || datoEnvio.getActo().getIdacto().isEmpty()) {
			
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(
				datoEnvio.getUsuario().getIdsesionactual(), datoEnvio
						.getUsuario().getIdusuario())) {
			try {
				Acto aux = this.actoBo.findById(datoEnvio.getActo().getIdacto());
				aux.setExpediente(null);
				aux.setSuboperacion(null);
				respuesta.setActo(aux);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				
			} catch (NotariaException e) {
				e.printStackTrace();
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {

			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}

		return respuesta;

	}	
	
	@POST
	@Path("/setNoDimAnexo5")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoEnvio setDim(ActoEnvio ae){	
		ActoEnvio respuesta = new ActoEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ae)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			try {
				Acto acto = getActoBo().findById(ae.getActo().getIdacto());
				acto.setHasdim(ae.getActo().getHasdim());
				acto.setHasanexo5(ae.getActo().getHasanexo5());
				getActoBo().update(acto);
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus("ocurrio un error al asignar el gestor "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/saveBonitaProcess")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoEnvio saveBonitaProcess(BonitaCommonBean bonitaBean){
		
		ActoEnvio respuesta = new ActoEnvio();		
		try{
		
		if (bonitaBean == null || bonitaBean.getIdusuario() == null
				|| bonitaBean.getIdsesionactual() == null || 
					bonitaBean.getIdActo() == null || bonitaBean.getIdproceso() == null
					|| bonitaBean.getIdtarea() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(bonitaBean.getIdsesionactual(), bonitaBean.getIdusuario())){
			getActoBo().saveBonitaProcess(bonitaBean);
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		}catch(NotariaException e){
			e.printStackTrace();
			respuesta.setEstatus("ocurrio un error al guardar proceso del BPM "+e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/saveTask")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoEnvio saveTask(TareaBean tareaBean){
		
		ActoEnvio respuesta = new ActoEnvio();		
		try{
		
//		if (tareaBean == null || tareaBean.getIdusuario() == null
//				|| tareaBean.getIdsesionactual() == null || 
//					tareaBean.getIdActo() == null || tareaBean.getIdproceso() == null
//					|| tareaBean.getIdtarea() == null) {
//			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
//			respuesta.setExito(false);
//			return respuesta;
//		}
		if(NotariaUtils.isSesionValida(tareaBean.getIdsesionactual(), tareaBean.getIdusuario())){
			getActoBo().saveBonitaTask(tareaBean);
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		}catch(NotariaException e){
			e.printStackTrace();
			respuesta.setEstatus("ocurrio un error al guardar proceso del BPM "+e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	public ActoBo getActoBo() {
		return actoBo;
	}
	
	public void setActoBo(ActoBo actoBo) {
		this.actoBo = actoBo;
	}
}
