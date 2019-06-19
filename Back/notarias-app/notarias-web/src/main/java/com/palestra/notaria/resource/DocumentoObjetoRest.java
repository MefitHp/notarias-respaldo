package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.DocumentoObjetoBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.DocumentoObjetoBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoDocumentoObjeto;
import com.palestra.notaria.dato.DatoTemplate;
import com.palestra.notaria.envio.DocumentoObjetoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.DocumentoObjeto;
import com.palestra.notaria.modelo.DocumentoObjetoPK;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;
import com.palestra.notarias.escritura.EscrituraParcialGenerator;

/**
 * Clase que atiende las peticiones CRUD para DocumentoObjeto
 * 
 * @author sofia
 * 
 */
@Path("/documentoObjeto")
public class DocumentoObjetoRest {

	static Logger logger = Logger.getLogger(DocumentoObjetoRest.class);

	private DocumentoObjetoBo documentoObjetoBo;
	private ActoBo actoBo;

	/**
	 * Clase que se encarga de reemplazar variable por valor, a partir de
	 * plantilla y acto
	 **/
	// 010914 --> cafaray: se cambio por la rutina principal que transforma el documento notarial.
	//private DocumentoObjetoGenerator docObjGenerator = new DocumentoObjetoGenerator();
	// 010914 <--

	public DocumentoObjetoRest() {
		documentoObjetoBo = new DocumentoObjetoBoImpl();
		actoBo = new ActoBoImpl();
	}

	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoObjetoEnvio guardar(DocumentoObjetoEnvio oe) {
		DocumentoObjetoEnvio respuesta = new DocumentoObjetoEnvio();
		if (NotariaUtils.faltanRequeridosUsuario(oe) || oe.getDocumento() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(), oe.getUsuario().getIdusuario())){
			try{
				DocumentoObjetoPK id = new DocumentoObjetoPK();
				id.setIddocobjeto(GeneradorId.generaId(oe.getDocumento()));
				id.setVersion(new Integer(0));
				oe.getDocumento().setId(id);
				oe.getDocumento().setIdsesion(oe.getUsuario().getIdsesionactual());
				oe.getDocumento().setIsactivo(true);
				Date date = new Date();
				oe.getDocumento().setFchpublicacion(new Timestamp(date.getTime()));
				respuesta.setDocumento(this.documentoObjetoBo.save(oe.getDocumento()));
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	@Path("/actualizar")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoObjetoEnvio actualizar(DocumentoObjetoEnvio oe) {
		DocumentoObjetoEnvio respuesta = new DocumentoObjetoEnvio();
		if (NotariaUtils.faltanRequeridosUsuario(oe) || oe.getDocumento() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(), oe.getUsuario().getIdusuario())){
			try{
				
//				se valida que la versión a actualizar sea la más actual, esté o no publicada
				
				DocumentoObjeto docOld = this.documentoObjetoBo.buscarPorIdCompleto(oe.getDocumento().getId().getIddocobjeto(), oe.getDocumento().getId().getVersion());
				Integer maxVersion = this.documentoObjetoBo.findMaxVersion(oe.getDocumento().getId().getIddocobjeto());
				if(docOld.getId().getVersion() < maxVersion){
					respuesta.setEstatus("se debe actualizar sobre la versión mas reciente");
					respuesta.setExito(false);
					return respuesta;
				}
				if(oe.getDocumento().getIspublicada()){
//						buscar si existe un doc publicado y actualizarlo a false
					DocumentoObjeto docPublicado = this.documentoObjetoBo.findDocumentoPublicado(oe.getDocumento().getId().getIddocobjeto());
					if(docPublicado != null){
							docPublicado.setIspublicada(false);
							respuesta.setDocumento(this.documentoObjetoBo.update(docPublicado));
						}
//						si en la version anterior del doc estaba publicado se crea nueva version
					if(docOld.getIspublicada()){
	//					se crea nueva version del mismo doc
						DocumentoObjetoPK key = new DocumentoObjetoPK();
						key.setIddocobjeto(oe.getDocumento().getId().getIddocobjeto());
						key.setVersion(maxVersion+1);
						oe.getDocumento().setId(key);
						oe.getDocumento().setIdsesion(oe.getUsuario().getIdsesionactual());
						respuesta.setDocumento(this.documentoObjetoBo.save(oe.getDocumento()));
					}else{
						oe.getDocumento().setIdsesion(oe.getUsuario().getIdsesionactual());
						respuesta.setDocumento(this.documentoObjetoBo.update(oe.getDocumento()));
					}
				}else{
//					si no esta publicada la version anterior solamente se actualizan los cambios en la misma version
					oe.getDocumento().setIdsesion(oe.getUsuario().getIdsesionactual());
//					si el doc recuperado esta publicado se aumenta la version a uno
					if(docOld.getIspublicada())
						oe.getDocumento().getId().setVersion(maxVersion+1);
					respuesta.setDocumento(this.documentoObjetoBo.update(oe.getDocumento()));
				}
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	/**
	 * Devuelbe una lista de los documento objetos en BD, 
	 * englobado en el dto DatoDocumentoObjeto.
	 * 
	 * @param documentoRequest
	 * @return
	 */
	@POST
	@Path("/listarCombo")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoObjetoEnvio getListaCombo(
			DocumentoObjetoEnvio documentoRequest) {

		DocumentoObjetoEnvio respuesta = new DocumentoObjetoEnvio();

		if (NotariaUtils.faltanRequeridosUsuario(documentoRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		try {
			if (NotariaUtils.isSesionValida(documentoRequest.getUsuario()
					.getIdsesionactual(), documentoRequest.getUsuario()
					.getIdusuario())) {
				List<DatoDocumentoObjeto> lista = this.documentoObjetoBo
						.obtenListaDocumentoObjeto();
				if(lista==null){
					respuesta.setExito(false);
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
					return respuesta;
				}
				respuesta.setListaCombo((new ArrayList<DatoDocumentoObjeto>(
						lista)));
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			} else {
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				respuesta.setExito(false);
				return respuesta;
			}
		} catch(NotariaException e){
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoObjetoEnvio obtenerPorIdCompleto(
			DocumentoObjetoEnvio documentoRequest) {

		DocumentoObjetoEnvio respuesta = new DocumentoObjetoEnvio();
		DocumentoObjeto documento = documentoRequest.getDocumento();

		if (NotariaUtils.faltanRequeridosUsuario(documentoRequest)
				|| documento.getId() == null
				|| documento.getId().getIddocobjeto() == null
				|| documento.getId().getIddocobjeto().isEmpty()
				|| documento.getId().getVersion() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		try {
			if (NotariaUtils.isSesionValida(documentoRequest.getUsuario()
					.getIdsesionactual(), documentoRequest.getUsuario()
					.getIdusuario())) {
				DocumentoObjeto dRegistrado = this.documentoObjetoBo
						.buscarPorIdCompleto(
								documento.getId().getIddocobjeto(), documento
										.getId().getVersion());
				if (dRegistrado == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				respuesta.setDocumento(dRegistrado);
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;

			} else {
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				respuesta.setExito(false);
				return respuesta;
			}
		} catch(NotariaException e){
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/llenaPlantilla")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoObjetoEnvio fillTemplateWithData(
			DocumentoObjetoEnvio documentoRequest) {

		DocumentoObjetoEnvio respuesta = new DocumentoObjetoEnvio();
		DocumentoObjeto documento = documentoRequest.getDocumento();

		if (NotariaUtils.faltanRequeridosUsuario(documentoRequest)
				|| documento == null || documento.getId() == null
				|| documento.getId().getIddocobjeto() == null
				|| documento.getId().getIddocobjeto().isEmpty()
				|| documento.getId().getVersion() == null
				|| documentoRequest.getActo() == null
				|| documentoRequest.getActo().getIdacto() == null
				|| documentoRequest.getActo().getIdacto().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		try {
			if (NotariaUtils.isSesionValida(documentoRequest.getUsuario()
					.getIdsesionactual(), documentoRequest.getUsuario()
					.getIdusuario())) {

				String plantillaHtml = documentoObjetoBo.obtenPlantillaPorId(
						documento.getId().getIddocobjeto(), documento.getId()
								.getVersion());
				if (plantillaHtml == null || plantillaHtml.isEmpty()) {
					logger.info("No se pudo obtener la plantilla.");
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
					respuesta.setExito(false);
					return respuesta;
				}
				Acto rActo = actoBo.buscarPorIdCompleto(documentoRequest
						.getActo().getIdacto());
				if (rActo == null) {
					logger.info("No se pudo obtener el acto");
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
					respuesta.setExito(false);
					return respuesta;
				}

				DatoTemplate datoTemplate = new DatoTemplate();
				datoTemplate.setActo(rActo);
				/** Reemplazar las variables de plantilla por los valores del acto **/
//				String resultado = docObjGenerator
//						.reemplazaVariablesFormularios(rActo.getIdacto(),
//								plantillaHtml);
				EscrituraParcialGenerator escrituraParcialGenerator = new EscrituraParcialGenerator();
				String resultado = escrituraParcialGenerator.llenaPlantillaDelActo(rActo, plantillaHtml, false);
						                           
				if (resultado == null || resultado.isEmpty()) {
					logger.info(Constantes.ESTATUS_DOCOBJ_GENERACION_FALLIDA);
					respuesta.setEstatus(Constantes.ESTATUS_DOCOBJ_GENERACION_FALLIDA);
					respuesta.setExito(false);
					return respuesta; 
				}
				if(resultado.equals(Constantes.DocObj1)){
					respuesta.setEstatus(Constantes.MENSAJE_DocObj1);
					respuesta.setExito(false);
					return respuesta;
				}
				
				respuesta.setTxtDocumento(resultado);
				//respuesta.setTxtDocumento(plantillaHtml);
				respuesta.setEstatus(Constantes.ESTATUS_LLENADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;

			} else {
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				respuesta.setExito(false);
				return respuesta;
			}
		} catch(NotariaException e){
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoObjetoEnvio listar(DocumentoObjetoEnvio oe){
		DocumentoObjetoEnvio respuesta = new DocumentoObjetoEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(oe)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(), oe.getUsuario().getIdusuario())){
			try{
				respuesta.setPublicados((ArrayList<DocumentoObjeto>) this.documentoObjetoBo.getPublicados());
				respuesta.setNoPublicados((ArrayList<DocumentoObjeto>) this.documentoObjetoBo.getNoPublicados());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	

}
