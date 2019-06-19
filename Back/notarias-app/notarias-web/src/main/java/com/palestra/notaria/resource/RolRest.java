package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ElementoCatalogoBo;
import com.palestra.notaria.bo.ProcesoRolBo;
import com.palestra.notaria.bo.RolBo;
import com.palestra.notaria.bo.impl.ElementoCatalogoBoImpl;
import com.palestra.notaria.bo.impl.ProcesoRolBoImpl;
import com.palestra.notaria.bo.impl.RolBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.RolEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ProcesoRol;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.RolGestionCompetencia;
import com.palestra.notaria.util.NotariaUtils;

@Path("/rol")
public class RolRest {
	
	static Logger logger = Logger.getLogger(RolRest.class);
	
	private RolBo rolBo;
	
	private ProcesoRolBo procesoRolBo;
	
	private ElementoCatalogoBo elementoBo;
	
	public ElementoCatalogoBo getElementoBo() {
		return elementoBo;
	}

	public void setElementoBo(ElementoCatalogoBo elementoBo) {
		this.elementoBo = elementoBo;
	}

	public RolRest(){
		rolBo = new RolBoImpl();
		procesoRolBo = new ProcesoRolBoImpl();
		elementoBo = new ElementoCatalogoBoImpl();
	}

	@POST
	@Path("/listar")
	@Produces({ "application/json", "application/xml" })
	public RolEnvio getRoles(RolEnvio ue) {
		RolEnvio respuesta = new RolEnvio();
		if (ue == null || ue.getUsuario() == null
				|| ue.getUsuario().getIdusuario() == null || ue.getUsuario().getIdusuario().isEmpty()
				|| ue.getUsuario().getIdsesionactual() == null || ue.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ue.getUsuario().getIdsesionactual(),ue.getUsuario().getIdusuario())){
			try{
				respuesta.setRolesList((ArrayList<Rol>) getRolBo().findAll());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getRolesList()==null){
				respuesta.setEstatus("ocurrio un error al listar");
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus("listado correcto");
			return respuesta;
		}else{
			respuesta.setExito(false);
			respuesta.setEstatus("sesion invalida");
			return respuesta;
		}
	}
	
	@POST
	@Path("/competencias/registrar")
	@Produces({"application/json", "application/xml"})
	public RolEnvio registrarCompetencia(RolEnvio envio) {
		
		try{
			RolEnvio respuesta = new RolEnvio();
		
			if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null || envio.getUsuario().getIdusuario().isEmpty()
				|| envio.getUsuario().getIdsesionactual() == null || envio.getUsuario().getIdsesionactual().isEmpty()
				|| envio.getUsuario().getRol() == null) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if(NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),envio.getUsuario().getIdusuario())){
				RolBo bo = new RolBoImpl();
				int registros = bo.registraCompetencias(envio.getCompetencias());
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA+" Se han agregado "+registros+" competencias.");
				respuesta.setExito(true);
				return respuesta;
			} else {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
		
		}catch(NotariaException e){
			List<CodigoError> errores = new ArrayList<CodigoError>();
			CodigoError error = new CodigoError();
			error.setCodigo("50001");
			error.setMensaje(e.getMessage());
			envio.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			envio.setErrores(errores);
			return envio;
		}
	}

	@POST
	@Path("/competencias/valida")
	@Produces({"application/json", "application/xml"})
	public RolEnvio validaCompetencia(RolEnvio envio) {
		
		try{
			RolEnvio respuesta = new RolEnvio();
			
			if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null || envio.getUsuario().getIdusuario().isEmpty()
				|| envio.getUsuario().getIdsesionactual() == null || envio.getUsuario().getIdsesionactual().isEmpty()
				|| envio.getUsuario().getRol() == null) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if(NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),envio.getUsuario().getIdusuario())){
				RolBo bo = new RolBoImpl();
				int modo = bo.validaCompetencia(envio.getUsuario().getRol(), envio.getCompetenciaPantalla());
				respuesta.setEstatus(":"+modo);
				respuesta.setExito(true);
				return respuesta;
			} else {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
		
		}catch(NotariaException e){
			List<CodigoError> errores = new ArrayList<CodigoError>();
			CodigoError error = new CodigoError();
			error.setCodigo("50001");
			error.setMensaje(e.getMessage());
			envio.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			envio.setErrores(errores);
			return envio;
		}
	}
	
	
	@POST
	@Path("/competencias/lista")
	@Produces({"application/json", "application/xml"})
	public RolEnvio listaCompetencias(RolEnvio envio) {
		
		try{
			RolEnvio respuesta = new RolEnvio();			
			if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null || envio.getUsuario().getIdusuario().isEmpty()
				|| envio.getUsuario().getIdsesionactual() == null || envio.getUsuario().getIdsesionactual().isEmpty()
				|| envio.getUsuario().getRol() == null) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if(NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),envio.getUsuario().getIdusuario())){
				RolBo bo = new RolBoImpl();
				List<RolGestionCompetencia> competencias = bo.competencias(envio.getUsuario().getRol());
				respuesta.setCompetencias(competencias);
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA+" Se han listado "+competencias.size()+" competencias.");
				respuesta.setExito(true);
				return respuesta;
			} else {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
		}catch(NotariaException e){
			List<CodigoError> errores = new ArrayList<CodigoError>();
			CodigoError error = new CodigoError();
			error.setCodigo("50001");
			error.setMensaje(e.getMessage());
			envio.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			envio.setErrores(errores);
			return envio;
		}
	}	
	
	@POST
	@Path("/competencias/lista/rol")
	@Produces({"application/json", "application/xml"})
	public RolEnvio listaCompetenciasRol(RolEnvio envio) {
		
		try{
			RolEnvio respuesta = new RolEnvio();			
			if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null || envio.getUsuario().getIdusuario().isEmpty()
				|| envio.getUsuario().getIdsesionactual() == null || envio.getUsuario().getIdsesionactual().isEmpty()
				|| envio.getUsuario().getRol() == null) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if(NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),envio.getUsuario().getIdusuario())){
				RolBo bo = new RolBoImpl();
				List<RolGestionCompetencia> competencias = bo.competencias(envio.getRol());
				respuesta.setCompetencias(competencias);
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA+" Se han listado "+competencias.size()+" competencias.");
				respuesta.setExito(true);
				return respuesta;
			} else {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
		}catch(NotariaException e){
			List<CodigoError> errores = new ArrayList<CodigoError>();
			CodigoError error = new CodigoError();
			error.setCodigo("50001");
			error.setMensaje(e.getMessage());
			envio.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			envio.setErrores(errores);
			return envio;
		}
	}	
	
	@POST
	@Path("/obtenerPorId")
	@Produces({ "application/json", "application/xml" })
	public Rol obtenerPorId(String id) {
		try {
			return getRolBo().findById(id);
		} catch (NotariaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
	
	@POST
	@Path("/listarProcesos")
	@Produces({ "application/json", "application/xml" })
	public RolEnvio listarProcesosPorRol(RolEnvio ue){
		RolEnvio respuesta = new RolEnvio();
		if (ue == null || ue.getUsuario() == null
				|| ue.getUsuario().getIdusuario() == null || ue.getUsuario().getIdusuario().isEmpty()
				|| ue.getUsuario().getIdsesionactual() == null || ue.getUsuario().getIdsesionactual().isEmpty()
				|| ue.getUsuario().getRol() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ue.getUsuario().getIdsesionactual(),ue.getUsuario().getIdusuario())){
			try{
				respuesta.setProcesoList((ArrayList<ProcesoRol>)getProcesoRolBo().listarProcesosPorRol(ue.getUsuario().getRol()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getProcesoList()==null){
				respuesta.setEstatus("ocurrio un error al listar");
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus("listado correcto");
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus("sesion invalida");
			respuesta.setExito(false);
			return respuesta;
		}
			
	}

	public RolBo getRolBo() {
		return rolBo;
	}
	
	public void setRolBo(RolBo rolBo) {
		this.rolBo = rolBo;
	}
	
	public void setProcesoRolBo(ProcesoRolBo procesoRolBo) {
		this.procesoRolBo = procesoRolBo;
	}
	
	public ProcesoRolBo getProcesoRolBo() {
		return procesoRolBo;
	}

}
