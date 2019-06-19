
package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.DomicilioBo;
import com.palestra.notaria.bo.NotariaBo;
import com.palestra.notaria.bo.UsuarioBO;
import com.palestra.notaria.bo.impl.DomicilioBoImpl;
import com.palestra.notaria.bo.impl.NotariaBoImpl;
import com.palestra.notaria.bo.impl.UsuarioBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.NotariaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Notaria;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/notarias")
public class NotariaRest {
	
	static Logger logger = Logger.getLogger(NotariaRest.class);
	
	NotariaBo notariaBo;
	
	DomicilioBo domicilioBo;


	
	public NotariaRest(){
		notariaBo = new NotariaBoImpl();
		domicilioBo = new DomicilioBoImpl();
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public NotariaEnvio getNotarias(NotariaEnvio notariaEnvio) {
		NotariaEnvio respuesta = new NotariaEnvio();
		if (notariaEnvio == null || notariaEnvio.getUsuario() == null
				|| notariaEnvio.getUsuario().getIdusuario() == null || notariaEnvio.getUsuario().getIdusuario().isEmpty()
				|| notariaEnvio.getUsuario().getIdsesionactual() == null || notariaEnvio.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(notariaEnvio.getUsuario().getIdsesionactual(),notariaEnvio.getUsuario().getIdusuario())){
			try{
				respuesta.setNotariaList((ArrayList<Notaria>)getNotariaBo().findAll());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getNotariaList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
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
	
//	TODO setear aqui rol de notario antes de enviar el usuarioenvio al usuariorest
	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public NotariaEnvio registraNotaria(NotariaEnvio notariaEnvio){
		NotariaEnvio respuesta = new NotariaEnvio();
		if (notariaEnvio == null 
				|| notariaEnvio.getUsuario() == null
				|| notariaEnvio.getUsuario().getIdusuario() == null 
				|| notariaEnvio.getUsuario().getIdusuario().isEmpty()
				|| notariaEnvio.getUsuario().getIdsesionactual() == null 
				|| notariaEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| notariaEnvio.getNotaria() == null
				|| notariaEnvio.getNotaria().getUsuario() == null				
				|| notariaEnvio.getNotaria().getDomicilio() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if(NotariaUtils.isSesionValida(notariaEnvio.getUsuario().getIdsesionactual(), notariaEnvio.getUsuario().getIdusuario())){
			
			notariaEnvio.getNotaria().setIdsesion(notariaEnvio.getUsuario().getIdsesionactual());
			notariaEnvio.getNotaria().setIdnotaria(GeneradorId.generaId(notariaEnvio.getNotaria()));
			notariaEnvio.getNotaria().getDomicilio().setIddomicilio(GeneradorId.generaId(notariaEnvio.getNotaria().getDomicilio()));
			notariaEnvio.getNotaria().getDomicilio().setIdsesion(notariaEnvio.getUsuario().getIdsesionactual());
			//UsuarioRest usrRest = new UsuarioRest();
			UsuarioBO usuarioBo = new UsuarioBoImpl();
			Usuario usuario = notariaEnvio.getNotaria().getUsuario();
			usuario.setDsvalenc(Constantes.VALENC);
			//UsuarioEnvio usrEnvio = new UsuarioEnvio();
			try{
				//usrEnvio.setUsuario(notariaEnvio.getUsuario());				
				//usrEnvio = usrRest.registraUsuario(usrEnvio);
				usuario.setIdsesion(notariaEnvio.getUsuario().getIdsesionactual());
				usuarioBo.save(usuario);
				//notariaEnvio.getNotaria().setDomicilio(getDomicilioBo().save(notariaEnvio.getNotaria().getDomicilio()));
				getDomicilioBo().save(notariaEnvio.getNotaria().getDomicilio());
				//notariaEnvio.getNotaria().setUsuario(usrEnvio.getUsuario());
				//respuesta.setNotaria(getNotariaBo().save(notariaEnvio.getNotaria()));
				getNotariaBo().save(notariaEnvio.getNotaria());
				respuesta.setNotaria(notariaEnvio.getNotaria());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getNotaria()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/actualizar")
	@Produces({"application/json", "application/xml" })
	public NotariaEnvio actualizar(NotariaEnvio notariaEnvio) {
		NotariaEnvio respuesta = new NotariaEnvio();
		if (notariaEnvio == null 
				|| notariaEnvio.getUsuario() == null
				|| notariaEnvio.getUsuario().getIdusuario() == null 
				|| notariaEnvio.getUsuario().getIdusuario().isEmpty()
				|| notariaEnvio.getUsuario().getIdsesionactual() == null 
				|| notariaEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| notariaEnvio.getNotaria() == null 
				|| notariaEnvio.getNotaria().getUsuario() == null
				|| notariaEnvio.getNotaria().getDomicilio() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		notariaEnvio.getNotaria().setIdsesion(notariaEnvio.getUsuario().getIdsesionactual());
		notariaEnvio.getNotaria().getDomicilio().setIdsesion(notariaEnvio.getUsuario().getIdsesionactual());
		notariaEnvio.getNotaria().getUsuario().setIdsesion(notariaEnvio.getUsuario().getIdsesionactual());
		if(NotariaUtils.isSesionValida(notariaEnvio.getUsuario().getIdsesionactual(),notariaEnvio.getUsuario().getIdusuario())){
			try{				
				UsuarioBO usuarioBo = new UsuarioBoImpl();
				usuarioBo.update(notariaEnvio.getNotaria().getUsuario());
				//ne.getNotaria().setDomicilio(getDomicilioBo().update(ne.getNotaria().getDomicilio()));
				getDomicilioBo().update(notariaEnvio.getNotaria().getDomicilio());
				//respuesta.setNotaria(getNotariaBo().update(ne.getNotaria()));
				getNotariaBo().update(notariaEnvio.getNotaria());
				respuesta.setNotaria(notariaEnvio.getNotaria());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getNotaria()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
			
	}
	
//	@POST
//	@Path("/eliminar")
//	@Produces({ "application/json", "application/xml" })
//	public boolean eliminar(Notaria cliente) {
//		
//		try {
//			return getNotariaBo().delete(cliente);
//		} catch (NotariaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@POST
	@Path("/obtenerPorId")
	@Produces({ "application/json", "application/xml" })
	public Notaria obtenerPorId(Notaria cliente) {

		try {
			return getNotariaBo().findById(cliente.getIdnotaria());
		} catch (NotariaException e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public NotariaBo getNotariaBo() {
		return notariaBo;
	}
	
	public void setNotariaBo(NotariaBo notariaBo) {
		this.notariaBo = notariaBo;
	}
	
	public DomicilioBo getDomicilioBo() {
		return domicilioBo;
	}
	
	public void setDomicilioBo(DomicilioBo domicilioBo) {
		this.domicilioBo = domicilioBo;
	}
}

