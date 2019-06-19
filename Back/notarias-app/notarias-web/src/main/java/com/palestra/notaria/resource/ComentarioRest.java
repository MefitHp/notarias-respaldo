package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.CatalogoBo;
import com.palestra.notaria.bo.ColoniaBo;
import com.palestra.notaria.bo.ComentarioBo;
import com.palestra.notaria.bo.ElementoCatalogoBo;
import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.CatalogoBoImpl;
import com.palestra.notaria.bo.impl.ColoniaBoImpl;
import com.palestra.notaria.bo.impl.ComentarioBoImpl;
import com.palestra.notaria.bo.impl.ElementoCatalogoBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.BitacoraUsuarioEnvio;
import com.palestra.notaria.envio.CatalogoEnvio;
import com.palestra.notaria.envio.ColoniaEnvio;
import com.palestra.notaria.envio.ComentarioEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.BitacoraUsuario;
import com.palestra.notaria.modelo.Catalogo;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/comentario")
public class ComentarioRest {
	
	static Logger logger = Logger.getLogger(CatalogoRest.class);
	
	ComentarioBo comentarioBo;

	public ComentarioRest() {
		this.comentarioBo = new ComentarioBoImpl();
	}

	@POST
	@Path("/listarPorObjeto")
	@Produces(MediaType.APPLICATION_JSON)
	public ComentarioEnvio getComentarios(ComentarioEnvio comentarioRest) {
		
		ComentarioEnvio respuesta = new ComentarioEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(comentarioRest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(comentarioRest.getUsuario()
				.getIdsesionactual(), comentarioRest.getUsuario()
				.getIdusuario())) {
			
			
			try {
				List<Comentario> comentarios = comentarioBo.buscarPorObjeto(comentarioRest.getComentario().getIdobjeto());
				respuesta.setComentarios(comentarios);
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				return respuesta;
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			
			
			
			
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public ComentarioEnvio guardarComentarios(ComentarioEnvio comentarioRest) {
		
		ComentarioEnvio respuesta = new ComentarioEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(comentarioRest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(comentarioRest.getUsuario()
				.getIdsesionactual(), comentarioRest.getUsuario()
				.getIdusuario())) {
			
			Comentario com = comentarioRest.getComentario();	
			com.setIdcomentario(GeneradorId.generaId(comentarioRest));
			com.setIdsesion(comentarioRest.getUsuario().getIdsesionactual());
			com.setUsuario(comentarioRest.getUsuario());
			try {
				com = this.comentarioBo.save(com);
				
				if(comentarioRest.getSetBitacora()){
					BitacoraUsuario bitacora = new BitacoraUsuario();
					BitacoraUsuarioEnvio bitenv = new BitacoraUsuarioEnvio();
					BitacoraUsuarioRest bitRes = new BitacoraUsuarioRest();
					
					bitacora.setIdobjeto(com.getIdobjeto());
					bitacora.setIdtarea("0");
					bitacora.setIdbitusu(com.getUsuario().getIdusuario());
					bitacora.setActive(true);
					bitenv.setUsuario(comentarioRest.getUsuario());
					if(comentarioRest.getExpediente()!=null){
						bitacora.setIdexpediente(comentarioRest.getExpediente().getIdexpediente());
						comentarioRest.getExpediente().getAbogado().setIdsesionactual(comentarioRest.getUsuario().getIdsesionactual());
						bitenv.setUsuario(comentarioRest.getExpediente().getAbogado());
						
					}
					bitacora.setNumexp(comentarioRest.getExpediente().getNumexpediente());
					bitacora.setStatus("MESSAGE");
					bitacora.setTipo("mensaje");
					bitacora.setTexto(com.getDstexto());
					bitacora.setIdbitusu(GeneradorId.generaId(bitacora));
					
					bitenv.setBitacorausuario(bitacora);
					bitenv = bitRes.save(bitenv);
					if(!bitenv.isExito()){
						//this.comentarioBo.(com);
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
						respuesta.setExito(false);
						return respuesta;
					}
					
					
				}
				
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			
			
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/tienecomentdoc")
	@Produces(MediaType.APPLICATION_JSON)
	public ComentarioEnvio actualizaDoc(ComentarioEnvio comentarioRest) {
		ActoDocumentoBo actodocBo = new ActoDocumentoBoImpl();
		ComentarioEnvio respuesta = new ComentarioEnvio();
		
		if (comentarioRest.getComentario()==null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		try {
			ActoDocumento actodoc = actodocBo.findById(comentarioRest.getComentario().getIdobjeto());
			actodoc.setTienecomments(true);
			actodocBo.update(actodoc);
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			respuesta.setExito(true);
			return respuesta;
		} catch (NotariaException e) {
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			respuesta.setExito(false);
			return respuesta;
		}
		
	}
	
	@POST
	@Path("/tienecomentexp")
	@Produces(MediaType.APPLICATION_JSON)
	public ComentarioEnvio actualizaExp(ComentarioEnvio comentarioRest) {
		ExpedienteBo expedienteBo = new ExpedienteBoImpl();
		ComentarioEnvio respuesta = new ComentarioEnvio();
		
		try {
			Expediente expediente = expedienteBo.findById(comentarioRest.getComentario().getIdobjeto());
			expediente.setTienecomments(true);
			expedienteBo.update(expediente);
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			respuesta.setExito(true);
			return respuesta;
		} catch (NotariaException e) {
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	@POST
	@Path("/editar")
	@Produces(MediaType.APPLICATION_JSON)
	public ComentarioEnvio editarComentarios(ComentarioEnvio comentarioRest) {
		
		
		return null;
	}
	
	@POST
	@Path("/eliminar")
	@Produces(MediaType.APPLICATION_JSON)
	public ComentarioEnvio eliminarComentarios(ComentarioEnvio comentarioRest) {
		
		
		return null;
	}
	

}
