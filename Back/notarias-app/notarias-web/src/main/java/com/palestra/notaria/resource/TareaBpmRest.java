package com.palestra.notaria.resource;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.TareaBpmBo;
import com.palestra.notaria.bo.impl.TareaBpmBoImp;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.TareasEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.NotariaUtils;


@Path("/tareas")
public class TareaBpmRest {

	private TareaBpmBo tarbo =null;
	
		
	static Logger logger = Logger.getLogger(TareaBpmRest.class);
	
	
	@POST
	@Path("/asignadas")
	@Produces(MediaType.APPLICATION_JSON)
	public TareasEnvio obtieneMesaCtrlId(TareasEnvio te){
		TareasEnvio respuesta = new TareasEnvio();
		if (te == null || te.getUsuario() == null
				|| te.getUsuario().getIdusuario() == null || te.getUsuario().getIdusuario().isEmpty()
				|| te.getUsuario().getIdsesionactual() == null || te.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(te.getUsuario().getIdsesionactual(), te.getUsuario().getIdusuario())){
			try {
				// OBTENGO EL USUARIO DE LA BPM Y SI REGRESA QUE ES UN ABOGADO ASIGNO EL USUARIO DE GON
				String usrbmp = NotariaUtils.getUsrBpm(te.getUsuario().getIdusuario(), te.getUsuario().getRol().getDsprefijo());
				if(usrbmp.equals("abog")){
					usrbmp = te.getUsuario().getCdusuario();
				}
				respuesta.setTareasasignadas(getTarbo(usrbmp).getTareasAsignadas());
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
	@Path("/ejecuta")
	@Produces(MediaType.APPLICATION_JSON)
	public TareasEnvio ejecuta(TareasEnvio te){
		TareasEnvio respuesta = new TareasEnvio();
		if (te == null || te.getUsuario() == null
				|| te.getUsuario().getIdusuario() == null || te.getUsuario().getIdusuario().isEmpty()
				|| te.getUsuario().getIdsesionactual() == null || te.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(te.getUsuario().getIdsesionactual(), te.getUsuario().getIdusuario())){
			try {
				
				//Coloco las variables asi por que no supe como cachar un Serializable desde el json de la vista entonces
				// se tuvo que hacer 1 x 1
				Map<String, Serializable>inputs = new HashMap<String, Serializable>();
				
				if(te.getProrroga()!=null){
					inputs.put("prorroga", te.getProrroga());
				}

				
				if(te.getPaso()!=null){
					inputs.put("paso", te.getPaso());
				}
				
				
				// OBTENGO EL USUARIO DE LA BPM Y SI REGRESA QUE ES UN ABOGADO ASIGNO EL USUARIO DE GON
				String usrbmp = NotariaUtils.getUsrBpm(te.getUsuario().getIdusuario(), te.getUsuario().getRol().getDsprefijo());
				if(usrbmp.equals("abog")){
					usrbmp = te.getUsuario().getCdusuario();
				}
				getTarbo(usrbmp).executeTarea(te.getIdTarea(),inputs);
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
	}
	
	
	

	public TareaBpmBo getTarbo(String usr) throws NotariaException {
		if (tarbo != null) {
			return tarbo;
		}else{
			tarbo = new TareaBpmBoImp(usr,usr);
			return tarbo;
		}
	}

	public void setTarbo(TareaBpmBo tarbo) {
		this.tarbo = tarbo;
	}
	
}
