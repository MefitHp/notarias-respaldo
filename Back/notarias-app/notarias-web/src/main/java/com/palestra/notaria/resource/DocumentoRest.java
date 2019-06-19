package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.DocumentoBo;
import com.palestra.notaria.bo.DocumentoVersionesBo;
import com.palestra.notaria.bo.DocumentosOriginalesBo;
import com.palestra.notaria.bo.FormatoPDFBO;
import com.palestra.notaria.bo.impl.DocumentoBoImpl;
import com.palestra.notaria.bo.impl.DocumentoVersionesBoImpl;
import com.palestra.notaria.bo.impl.DocumentosOriginalesBoImpl;
import com.palestra.notaria.bo.impl.FormatoPDFBOImpl;
import com.palestra.notaria.dao.impl.DocumentosOriginalesDaoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.FormatoWrapper;
import com.palestra.notaria.envio.DocumentoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Documento;
import com.palestra.notaria.modelo.DocumentoVersiones;
import com.palestra.notaria.modelo.DocumentosOriginales;
import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

/**
 * Servicio REST que atiende las peticiones CRUD para documentos
 * 
 * @author
 * 
 */
@Path("/formatos")
public class DocumentoRest {

	static Logger logger = Logger.getLogger(DocumentoRest.class);

	private DocumentoBo documentoBo;
	
	private DocumentoVersionesBo docVersionesBo;
	

	public DocumentoRest() {
		this.documentoBo = new DocumentoBoImpl();
		this.docVersionesBo = new DocumentoVersionesBoImpl();
	}

	@POST
	@Path("/listar")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public DocumentoEnvio getDocumento(DocumentoEnvio de) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			try{
				respuesta.setDocPublicadosList((ArrayList<Documento>)getDocumentoBo().findAll());
				respuesta.setDocNoPublicadosList((ArrayList<DocumentoVersiones>) getDocVersionesBo().listarNoPublicados());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getDocumentoList()==null){
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
	@Path("/listarPosteriores")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public DocumentoEnvio listarPosteriores(DocumentoEnvio de) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			try{
				FormatoPDFBO formatoBo = new FormatoPDFBOImpl();
				List<Documento> lista = this.documentoBo.obtenerDocumento("Posterior");
				List<FormatoPDF> formatosWrapper = formatoBo.findAll();
				for(Documento doc:lista){
					FormatoWrapper formato = new FormatoWrapper();
					formato.setIddocumento(doc.getIddocumento());
					formato.setDstitulo(doc.getDstitulo());
					formato.setDsdescripcion(doc.getDsdescripcion());
					formato.setTipodoc(doc.getTipodoc());
					respuesta.getDocumentoList().add(formato);
				}
				for(FormatoPDF pdf: formatosWrapper){
					if(pdf.getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_POSTERIOR)){
						FormatoWrapper formato = new FormatoWrapper();
						formato.setIddocumento(pdf.getIdentificador());
						formato.setDstitulo(pdf.getDstitulo());
						formato.setDsdescripcion(pdf.getDsdescripcion());
						formato.setTipodoc(pdf.getTipodoc());
						respuesta.getDocumentoList().add(formato);
					}
				}

			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getDocumentoList()==null){
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
	@Path("/listarPrevios")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public DocumentoEnvio listarPrevios(DocumentoEnvio de) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			try{
				FormatoPDFBO formatoBo = new FormatoPDFBOImpl();
				List<Documento> lista = this.documentoBo.obtenerDocumento("Previo");
				List<FormatoPDF> formatosWrapper = formatoBo.findAll();
				for(Documento doc:lista){
					FormatoWrapper formato = new FormatoWrapper();
					formato.setIddocumento(doc.getIddocumento());
					formato.setDstitulo(doc.getDstitulo());
					formato.setDsdescripcion(doc.getDsdescripcion());
					formato.setTipodoc(doc.getTipodoc());
					respuesta.getDocumentoList().add(formato);
				}
				for(FormatoPDF pdf: formatosWrapper){
					if(pdf.getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_PREVIO)){
						FormatoWrapper formato = new FormatoWrapper();
						formato.setIddocumento(pdf.getIdentificador());
						formato.setDstitulo(pdf.getDstitulo());
						formato.setDsdescripcion(pdf.getDsdescripcion());
						formato.setTipodoc(pdf.getTipodoc());
						respuesta.getDocumentoList().add(formato);
					}
				}

			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getDocumentoList()==null){
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

	private Documento guardarEnDocumento(DocumentoVersiones docVersion,String operacion) throws NotariaException{
		Documento doc = new Documento();
		doc.setIddocumento(docVersion.getIddocumento());
		doc.setDsdescripcion(docVersion.getDsdescripcion());
		doc.setDstitulo(docVersion.getDstitulo());
		doc.setFecha(docVersion.getFecha());
		doc.setIdsesion(docVersion.getIdsesion());
		doc.setIspublicado(docVersion.getIspublicado());
		doc.setIsrequerido(docVersion.getIsrequerido());
		doc.setNumdiasgestion(docVersion.getNumdiasgestion());
//		TODO: se deben setear el isgestionado y el isvaluado
		doc.setTipodoc(docVersion.getTipodoc());
		doc.setTmstmp(docVersion.getTmstmp());
		doc.setTxplantilla(docVersion.getTxplantilla());
		doc.setVersion(docVersion.getVersion());
		doc.setIsactivo(docVersion.getIsactivo());
		try {
			if(operacion.equals("save"))
				return getDocumentoBo().save(doc);
			else
				return getDocumentoBo().update(doc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotariaException("Fallo al guardar la copia de DocumentoVersion en Documento");
		}
	}
	
	
	@POST
	@Path("/guardarOriginal")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio registraOriginal(DocumentoEnvio de){
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			
			
			de.getDocumentoVersiones().setIdsesion(de.getUsuario().getIdsesionactual());
			de.getDocumentoVersiones().setFecha(new Date());
			
			DocumentosOriginalesBo docOrBo = new DocumentosOriginalesBoImpl();
			DocumentosOriginales docOriginal = de.getDocumentoOriginal();
			docOriginal.setId(GeneradorId.generaId(docOriginal));
			docOriginal.setIdsesion(de.getUsuario().getIdsesion());
			try {
				docOrBo.save(docOriginal);
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
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
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio registraDocumento(DocumentoEnvio de){
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()
				|| de.getDocumentoVersiones() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			de.getDocumentoVersiones().setIddocumento(GeneradorId.generaId(de.getDocumentoVersiones()));
			de.getDocumentoVersiones().setIdsesion(de.getUsuario().getIdsesionactual());
			de.getDocumentoVersiones().setVersion(new Integer(0));
			de.getDocumentoVersiones().setVersionbase(new Integer(0));
			de.getDocumentoVersiones().setFecha(new Date());
			de.getDocumentoVersiones().setIsactivo(true);
			try{
				respuesta.setDocumentoVersiones(getDocVersionesBo().save(de.getDocumentoVersiones()));
				if(de.getDocumentoVersiones().getIspublicado()){
					guardarEnDocumento(respuesta.getDocumentoVersiones(),"save");
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getDocumentoVersiones()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
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
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio actualizar(DocumentoEnvio de) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()
				|| de.getDocumentoVersiones() == null || de.getDocumentoVersiones().getIddocumento() == null
				|| de.getDocumentoVersiones().getIddocumento().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			de.getDocumentoVersiones().setFecha(new Date());
			de.getDocumentoVersiones().setIsactivo(true);
			try{			
				if(de.getDocumentoVersiones().getIspublicado()){
//					Se actualiza el estatus de publicado a false del docversiones anterior
					DocumentoVersiones docReemplazado  = getDocVersionesBo().findByDocumentoPublicado(de.getDocumentoVersiones().getIddocumento());
					if(docReemplazado != null){
						docReemplazado.setIspublicado(false);
						getDocVersionesBo().update(docReemplazado);
					}
//					-------------------------------------------
					de.getDocumentoVersiones().setIdsesion(de.getUsuario().getIdsesionactual());
//					cuando se actualiza y publica un doc la version base es la del documento que se modifico
					de.getDocumentoVersiones().setVersionbase(de.getDocumentoVersiones().getVersion());
//					y se aumenta por cada cambio a +1 la version del doc
					de.getDocumentoVersiones().setVersion(getDocVersionesBo().getMaxVersionOfDoc(de.getDocumentoVersiones().getIddocumento())+1);
//					se guarda el documentoversiones nuevo
					respuesta.setDocumentoVersiones(getDocVersionesBo().save(de.getDocumentoVersiones()));
//					y se actualiza el actual publicado
					guardarEnDocumento(respuesta.getDocumentoVersiones(), "update");		
				}else{
//					Si no se va a publicar el doc solo se guarda en documentoversiones y se aumenta la version a +1
					de.getDocumentoVersiones().setIdsesion(de.getUsuario().getIdsesionactual());
//					se pone de version base la del doc que se selecciono
					de.getDocumentoVersiones().setVersionbase(de.getDocumentoVersiones().getVersion());
					
					de.getDocumentoVersiones().setVersion(getDocVersionesBo().getMaxVersionOfDoc(de.getDocumentoVersiones().getIddocumento())+1);

					respuesta.setDocumentoVersiones(getDocVersionesBo().save(de.getDocumentoVersiones()));
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getDocumentoVersiones()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}

	}

	@POST
	@Path("/eliminar")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio eliminar(DocumentoEnvio de) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()
				|| de.getDocumentoVersiones() == null || de.getDocumentoVersiones().getIddocumento() == null
				|| de.getDocumentoVersiones().getIddocumento().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			de.getDocumentoVersiones().setFecha(new Date());
			de.getDocumentoVersiones().setIdsesion(de.getUsuario().getIdsesionactual());
//			TODO validar que no se encuentre en lista de publicados y deshabilitar solo los no publicados
			try{
				if(getDocumentoBo().findById(de.getDocumentoVersiones().getIddocumento()) == null){
					getDocVersionesBo().setInactivo(de.getDocumentoVersiones().getIddocumento());
				}else{
					respuesta.setEstatus("el documento ya está publicado y no se puede eliminar");
					respuesta.setExito(false);
					return respuesta;
				}
//				guardarEnDocumento(de.getDocumento(), "update");
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getDocumentoList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ELIMINAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}

	}

	@POST
	@Path("/obtenerPorVersionId")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio obtenerPorId(DocumentoEnvio de) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if(de.getUsuario() == null || de.getUsuario().getIdsesionactual().isEmpty() || de.getUsuario().getIdsesionactual()==null
				|| de.getUsuario().getIdusuario().isEmpty() || de.getUsuario().getIdusuario()==null
				|| de.getDocumentoVersiones() == null || de.getDocumentoVersiones().getIddocumento() == null
				|| de.getDocumentoVersiones().getIddocumento().isEmpty() || de.getDocumentoVersiones().getVersion() == null){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			try{
				if(de.getDocumentoVersiones().getIspublicado()){
					respuesta.setDocumento(getDocumentoBo().findBy(de.getDocumentoVersiones().getIddocumento(),de.getDocumentoVersiones().getVersion()));
				}else{
					respuesta.setDocumentoVersiones(getDocVersionesBo().findBy(de.getDocumentoVersiones().getIddocumento(),de.getDocumentoVersiones().getVersion()));
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}

	public DocumentoBo getDocumentoBo() {
		return documentoBo;
	}
	
	public void setDocumentoBo(DocumentoBo documentoBo) {
		this.documentoBo = documentoBo;
	}

	public DocumentoVersionesBo getDocVersionesBo() {
		return docVersionesBo;
	}
	
	public void setDocVersionesBo(DocumentoVersionesBo docVersionesBo) {
		this.docVersionesBo = docVersionesBo;
	}
}