package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.EditorTextoBo;
import com.palestra.notaria.bo.impl.EditorTextoBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.EditorTextoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.EditorTexto;
import com.palestra.notaria.util.NotariaUtils;

@Path("/editorTexto")
public class EditorTextoRest {
	
	static Logger logger = Logger.getLogger(DocumentoRest.class);
	
	private EditorTextoBo editorBo;
	
	public EditorTextoRest(){
		this.editorBo = new EditorTextoBoImpl();
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public EditorTextoEnvio getGrupos(EditorTextoEnvio ge) {
		EditorTextoEnvio respuesta = new EditorTextoEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			try{
				respuesta.setEditorTextoList((ArrayList<EditorTexto>)getEditorBo().findAll());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getEditorTextoList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public EditorTextoEnvio guardar(EditorTextoEnvio ge) {
		EditorTextoEnvio respuesta = new EditorTextoEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()
				|| ge.getEditorTexto() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			try{
				respuesta.setEditorTexto(getEditorBo().save(ge.getEditorTexto()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getEditorTextoList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}	
	
	@POST
	@Path("/actualizar")
	@Produces(MediaType.APPLICATION_JSON)
	public EditorTextoEnvio actualizar(EditorTextoEnvio ge) {
		EditorTextoEnvio respuesta = new EditorTextoEnvio();
		if (ge == null || ge.getUsuario() == null
				|| ge.getUsuario().getIdusuario() == null || ge.getUsuario().getIdusuario().isEmpty()
				|| ge.getUsuario().getIdsesionactual() == null || ge.getUsuario().getIdsesionactual().isEmpty()
				|| ge.getEditorTexto() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ge.getUsuario().getIdsesionactual(),ge.getUsuario().getIdusuario())){
			try{
				respuesta.setEditorTexto(getEditorBo().update(ge.getEditorTexto()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getEditorTextoList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	public EditorTextoBo getEditorBo() {
		return editorBo;
	}
	
	public void setEditorBo(EditorTextoBo editorBo) {
		this.editorBo = editorBo;
	}
}
