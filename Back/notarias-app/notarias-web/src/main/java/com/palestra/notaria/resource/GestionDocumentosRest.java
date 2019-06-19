package com.palestra.notaria.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.exceptions.COSVisitorException;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.ComentarioBo;
import com.palestra.notaria.bo.DocumentosOriginalesBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.bo.MesaCtrlBo;
import com.palestra.notaria.bo.RegistroRiBo;
import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.ComentarioBoImpl;
import com.palestra.notaria.bo.impl.DocumentosOriginalesBoImpl;
import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
import com.palestra.notaria.bo.impl.FormularioBoImpl;
import com.palestra.notaria.bo.impl.MesaCtrlBoImpl;
import com.palestra.notaria.bo.impl.RegistroRiBoImpl;
import com.palestra.notaria.bo.impl.TramiteBoImpl;
import com.palestra.notaria.bo.impl.UploadDropbox;
import com.palestra.notaria.bo.impl.UsuarioBoImpl;
import com.palestra.notaria.dao.ActoDocumentoDao;
import com.palestra.notaria.dao.TramiteDao;
import com.palestra.notaria.dao.impl.ActoDocumentoDaoImpl;
import com.palestra.notaria.dao.impl.TramiteDaoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.enums.EnumStatusDoc;
import com.palestra.notaria.envio.DocumentoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.DocumentosOriginales;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.FormatoPDFDetalle;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.RegistroRi;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.DocxForm;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;
import com.palestra.notaria.util.PDFForms;
import com.palestra.notaria.util.TramiteFileUtils;
import com.palestra.notarias.escritura.FillTemplateWithData;
import com.palestra.notarias.escritura.FormatosGenerator;
import com.palestra.notarias.pojo.FiltroVariables;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

import pojos.pojos.BonitaCommonBean;
import utiles.utilidades.victor.BotMail;

@Path("/gestionDocumentos")
public class GestionDocumentosRest {

	static Logger logger = Logger.getLogger(GestionDocumentosRest.class);
	private DocumentosOriginalesBo documentosOriginalesBo = new DocumentosOriginalesBoImpl();
	private ActoDocumentoBo actoDocumentoBo = new ActoDocumentoBoImpl();
	private BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();

	// private ExpedienteBo expedienteBo = new ExpedienteBoImpl();

	@POST
	@Path("/obtenerPrevios")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio obtenerPrevios(DocumentoEnvio documentoEnvio,
			@Context HttpServletRequest request) {
		DocumentoEnvio respuesta = new DocumentoEnvio();

		if (documentoEnvio == null || documentoEnvio.getUsuario() == null
				|| documentoEnvio.getUsuario().getIdusuario() == null
				|| documentoEnvio.getUsuario().getIdusuario().isEmpty()
				|| documentoEnvio.getUsuario().getIdsesionactual() == null
				|| documentoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| documentoEnvio.getIdExpediente() == null
				|| documentoEnvio.getIdExpediente().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(documentoEnvio.getUsuario()
				.getIdsesionactual(), documentoEnvio.getUsuario()
				.getIdusuario())) {
			List<DatoActoDocumento> lista;
			try {
				System.out.println("entra obtenerPrevios");
				lista = this.actoDocumentoBo.obtenerPrevios(
						documentoEnvio.getIdExpediente(),
						documentoEnvio.getIdacto());
				if (lista != null) {
					respuesta.setListaPrevios(new ArrayList<DatoActoDocumento>(
							lista));
				} else {
					respuesta
							.setListaPrevios(new ArrayList<DatoActoDocumento>());
				}
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);

			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	@Path("/marcarDocumentoAprovado")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio marcarDocumentoAprovado(
			DocumentoEnvio documentoEnvio, @Context HttpServletRequest request) {
		DocumentoEnvio respuesta = new DocumentoEnvio();

		if (documentoEnvio == null || documentoEnvio.getUsuario() == null
				|| documentoEnvio.getUsuario().getIdusuario() == null
				|| documentoEnvio.getUsuario().getIdusuario().isEmpty()
				|| documentoEnvio.getUsuario().getIdsesionactual() == null
				|| documentoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| documentoEnvio.getIdactodoc() == null
				|| documentoEnvio.getIdactodoc().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(documentoEnvio.getUsuario()
				.getIdsesionactual(), documentoEnvio.getUsuario()
				.getIdusuario())) {
			try {
				boolean b = this.actoDocumentoBo.marcarDocumentoAprovado(
						documentoEnvio.getIdactodoc(), false);
				if (b) {
					String exp = this.actoDocumentoBo
							.buscarIdExpPorDocumento(documentoEnvio
									.getIdactodoc());
					this.bitacoraGeneralHelper.registrarEnBitacora(
							documentoEnvio.getUsuario().getIdusuario(), exp,
							null, "ActoDocumento",
							Constantes.OPERACION_ACTUALIZACION,
							"Se registra la solicitud de un documento");
					validaFlujoBPM(documentoEnvio, "firma");
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				} else {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
				}
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	@Path("/marcarEntregaDocumento")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio marcarEntregaDocumento(DocumentoEnvio documentoEnvio,
			@Context HttpServletRequest request) {
		DocumentoEnvio respuesta = new DocumentoEnvio();

		if (documentoEnvio == null || documentoEnvio.getUsuario() == null
				|| documentoEnvio.getUsuario().getIdusuario() == null
				|| documentoEnvio.getUsuario().getIdusuario().isEmpty()
				|| documentoEnvio.getUsuario().getIdsesionactual() == null
				|| documentoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| documentoEnvio.getIdactodoc() == null
				|| documentoEnvio.getIdactodoc().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(documentoEnvio.getUsuario()
				.getIdsesionactual(), documentoEnvio.getUsuario()
				.getIdusuario())) {
			try {

				boolean b = this.actoDocumentoBo.marcarDocumentoEntregado(
						documentoEnvio.getIdactodoc(), false);
				if (b) {
					String exp = this.actoDocumentoBo
							.buscarIdExpPorDocumento(documentoEnvio
									.getIdactodoc());
					this.bitacoraGeneralHelper.registrarEnBitacora(
							documentoEnvio.getUsuario().getIdusuario(), exp,
							null, "ActoDocumento",
							Constantes.OPERACION_ACTUALIZACION,
							"Se registra la entrega de un documento");
					validaFlujoBPM(documentoEnvio, "entrega");
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				} else {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
				}
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {

			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}

	public void validaFlujoBPM(DocumentoEnvio docEnvio, String desde){
		ActoDocumentoDao actdocimpl= new ActoDocumentoDaoImpl();
		ActoDocumento actoDoc=null;
		try {
			actoDoc = actdocimpl.obtenerCompletoPorId(docEnvio.getIdactodoc());
			if(actoDoc.getActo().getSuboperacion().getOperacion().getDsnombre().equals("Expediente Judicial")){
				ActoBo actoBo = new ActoBoImpl();
				String idexpediente = actoBo.getExpedienteIdByActoId(actoDoc.getActo().getIdacto());
				BonitaCommonBean bonitaBean = new BonitaCommonBean();
				Comentario comentario = new Comentario();
				if(desde.equals("entrega")){
					comentario.setDstexto("Entrada automática de sistema: Se entregó escrito "+actoDoc.getFormatoPdf().getDstitulo()
							+ " a despacho");
				}else{
					comentario.setDstexto("Entrada automática de sistema: Escrito "+actoDoc.getFormatoPdf().getDstitulo()+" firmado por notario");
				}
				comentario.setIdobjeto(idexpediente);
				comentario.setIdcomentario(GeneradorId.generaId(idexpediente));
				comentario.setIdsesion(docEnvio.getUsuario().getIdsesionactual());
				comentario.setUsuario(docEnvio.getUsuario());
				ComentarioBo comentBo = new ComentarioBoImpl();
				comentBo.save(comentario);
				bonitaBean.setIdActo(actoDoc.getActo().getIdacto());
				ActoDocumentoBo actoDocBo = new ActoDocumentoBoImpl();
				actoDocBo.startBonitaJudicialesFlow(bonitaBean);
			}
		} catch (NotariaException e) {
			e.printStackTrace();
		}
		
	}
	@POST
	@Path("/subirArchivoDocumento")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoEnvio subirArchivoDocumento(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("file") FormDataBodyPart body,
			@FormDataParam("idsesionactual") String idsesionactual,
			@FormDataParam("idusuario") String idusuario,
			@FormDataParam("idactodoc") String idactodoc,
			@FormDataParam("desde") String desde,
			@FormDataParam("idtarea") String idtarea,
			@Context HttpServletRequest request, @Context ServletContext context) {
		DocumentoEnvio respuesta = new DocumentoEnvio();

		try {
			if (idsesionactual == null || idsesionactual.isEmpty()
					|| idusuario == null || idusuario.isEmpty()) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if (NotariaUtils.isSesionValida(idsesionactual, idusuario)) {
				if (!body.getMediaType().toString().equals("application/pdf")
						&& !body.getMediaType().toString().equals("image/jpeg")
						&& !body.getMediaType().toString().equals("image/png")) {
					respuesta.setEstatus("El formato no es imagen o pdf");
					respuesta.setExito(false);
					return respuesta;
				}
				Date fecha = new Date();
				String archivoExistente = this.actoDocumentoBo
						.buscarArchivoPorId(idactodoc);
				
				String nuevoNombre = fecha.getTime() + "_";
				
				
				if(desde!=null || !desde.isEmpty() || !desde.equals("")){
					nuevoNombre += desde+fileDetail.getFileName().substring(fileDetail.getFileName().lastIndexOf("."));  

				}else{
					nuevoNombre += fileDetail.getFileName();
				}
				
				
				/** obtener ruta donde se colocara el archivo **/
				// String idtramite = this.actoDocumentoBo
				// .obtenerIdTramitePorDocumento(idactodoc);
				// if (idtramite == null) {
				// respuesta.setEstatus(Constantes.ESTATUS_ERROR_NO_EXISTE_TRA);
				// respuesta.setExito(false);
				// return respuesta;
				// }
				// String tramiteFolder = Constantes.RUTA_REAL + File.separator
				// + idtramite;
				// File dir = new File(tramiteFolder);
				// dir.setReadable(true);
				// if (!dir.exists() || !dir.isDirectory()) {
				// logger.info("============> Se creo folder:" + tramiteFolder);
				// FileUtils.forceMkdir(dir);
				// }
				
				/**Victor: Se comenta el manejo de documentos por tráite ya que no cumplia con los requerimientos de la notaria**/
				/*String tramiteFolder = TramiteFileUtils
						.obtenRutaFolderByActDoc(idactodoc);*/
				
				String expedienteFolder = TramiteFileUtils.obtieneRutaFolderByActoDocExp(idactodoc);
				
				/**Victor: Se comenta el manejo de documentos por tráite ya que no cumplia con los requerimientos de la notaria**/
				/*
				if (tramiteFolder == null) {
					respuesta.setExito(false);
					respuesta
							.setEstatus("No se pudo obtener el folder del tramite.");
					return respuesta;
				}*/
				
				if(expedienteFolder ==null){
					respuesta.setExito(false);
					respuesta
							.setEstatus("No se pudo obtener el folder del expediente.");
					return respuesta;
				}
				
				/**Victor: Se comenta el manejo de documentos por tráite ya que no cumplia con los requerimientos de la notaria**/
				/*String uploadedFileLocation = tramiteFolder + File.separator
						+ nuevoNombre;*/
				
				String uploadedFileLocationExp = expedienteFolder + File.separator + nuevoNombre;
				
				/**Victor: Se comenta el manejo de documentos por tráite ya que no cumplia con los requerimientos de la notaria**/
				/*boolean b = NotariaUtils.writeToFile(uploadedInputStream,
						uploadedFileLocation);*/
				
				boolean bexp = NotariaUtils.writeToFile(uploadedInputStream, uploadedFileLocationExp);
				
				if (bexp) {
					//VICTOR:
					//ToDo: Realizar Gestor documentos pues ahora no elimina los archivos subidos.
					//if (archivoExistente != null && !archivoExistente.isEmpty())
						//NotariaUtils.eliminarArchivo(archivoExistente);
					String exp = this.actoDocumentoBo
							.buscarIdExpPorDocumento(idactodoc);

					this.bitacoraGeneralHelper.registrarEnBitacora(idusuario,
							exp, null, "ActoDocumento",
							Constantes.OPERACION_ACTUALIZACION,
							Constantes.OPERACION_ADJUNTAR_DOC_DIGITAL
									+ uploadedFileLocationExp);

					bexp = this.actoDocumentoBo.actualizarRutaArchivo(idactodoc,
							uploadedFileLocationExp);
					uploadedInputStream = null;
					UsuarioBoImpl usrBo = new UsuarioBoImpl();
					Usuario usr = usrBo.findById(idusuario);
					if(idtarea!=null){
						if(!idtarea.isEmpty() || !idtarea.equals("")){
						MesaCtrlBo mesaBo = new MesaCtrlBoImpl();
						MesaCtrl mesa = mesaBo.findByActoDocumento(idactodoc);
//						UsuarioBoImpl usrBo = new UsuarioBoImpl();
//						Usuario usr = usrBo.findById(idusuario);
						mesa.setEstatusdoc(EnumStatusDoc.DEVUELTO);
						mesaBo.update(mesa,usr);
						}
						
					}
					ActoDocumentoDao actdocimpl = new ActoDocumentoDaoImpl();
					ActoDocumento actoDoc = actdocimpl.obtenerCompletoPorId(idactodoc);
					
					if(actoDoc.getActo().getSuboperacion().getOperacion().getDsnombre().equals("Expediente Judicial")){
						BonitaCommonBean commonBean = new BonitaCommonBean();
						commonBean.setIdusuario(idusuario);
						commonBean.setIdsesionactual(idsesionactual);
						commonBean.setUsuario(usr.getCdusuario());
						uploadToDropboxAndSendMail(actoDoc, "evidencia",uploadedFileLocationExp,commonBean);
//						TODO: GUARDAR COMENTARIO AQUI
						Comentario comentario = new Comentario();
						comentario.setDstexto("Entrada automática de sistema: Se subió evidencia del documento: "+desde);
						comentario.setIdobjeto(exp);
						comentario.setIdcomentario(GeneradorId.generaId(exp));
						comentario.setIdsesion(idsesionactual);
						comentario.setUsuario(usr);
						ComentarioBo comentBo = new ComentarioBoImpl();
						comentBo.save(comentario);
					}
					respuesta.setRutaArchivoDescarga(uploadedFileLocationExp);
					respuesta.setExito(true);
					return respuesta;
				} else {
					uploadedInputStream = null;
					respuesta.setExito(false);
					return respuesta;
				}
				
				
				/**Victor: Se comenta el manejo de documentos por tráite ya que no cumplia con los requerimientos de la notaria**/
				/***if (b) {
					if (archivoExistente != null && !archivoExistente.isEmpty())
						NotariaUtils.eliminarArchivo(archivoExistente);
					String exp = this.actoDocumentoBo
							.buscarIdExpPorDocumento(idactodoc);

					this.bitacoraGeneralHelper.registrarEnBitacora(idusuario,
							exp, null, "ActoDocumento",
							Constantes.OPERACION_ACTUALIZACION,
							Constantes.OPERACION_ADJUNTAR_DOC_DIGITAL
									+ uploadedFileLocation);

					b = this.actoDocumentoBo.actualizarRutaArchivo(idactodoc,
							uploadedFileLocation);
					uploadedInputStream = null;

					respuesta.setRutaArchivoDescarga(uploadedFileLocation);
					respuesta.setExito(true);
					return respuesta;
				} else {
					uploadedInputStream = null;
					respuesta.setExito(false);
					return respuesta;
				}*/
				
				
				
				
				
				
			} else {
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				respuesta.setExito(false);
				return respuesta;
			}
		} catch (Exception e) {
			e.printStackTrace();
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_SUBIR_DOC + " "
					+ e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}

	/**
	 * Se encarga de cargar un archivo a Dropbox y enviar un Mail a las partes interesadas.
	 * 
	 * @param actoDoc
	 * @param desde
	 * @param rutaArchivo
	 * @return boolean
	 * @throws NotariaException 
	 */
	protected boolean uploadToDropboxAndSendMail(ActoDocumento actoDoc, String desde,
						String rutaArchivo,BonitaCommonBean commonBean) throws NotariaException{

		ActoDocumentoDao actdocimpl = new ActoDocumentoDaoImpl();
		
		TramiteDao tramiteDao = new TramiteDaoImpl();

//		ActoDocumento actoDoc = actdocimpl.obtenerCompletoPorId(idactodoc);
		String idTramite = actdocimpl.obtenerIdTramitePorDocumento(actoDoc.getIdactodoc());
		Tramite tramite = new Tramite();
		tramite.setIdtramite(idTramite);
		tramite = tramiteDao.buscarPorIdCompleto(tramite);
		String urlDropbox=null;
		try {
			if(desde.equals("evidencia")){
				String dropboxPath = NotariaUtils.getProperties("dropbox.path");
				urlDropbox=UploadDropbox.uploadToDropboxAndRetrieveLink(rutaArchivo, rutaArchivo.substring(rutaArchivo.lastIndexOf("/")),dropboxPath);
			}else{
				urlDropbox= "este correo";
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new NotariaException(e.getMessage());
		}
		FormularioBo formularioBo = new FormularioBoImpl();
		List<Formulario> formularios = formularioBo
				.buscarFormulariosPorActo(actoDoc.getActo().getIdacto());
		StringBuilder stringVars = new StringBuilder();
		for (FormatoPDFDetalle var : actoDoc.getFormatoPdf().getDetalleList()) {
			stringVars.append(var.getDsvariable()).append(" ");
		}
		FillTemplateWithData dataObj = new FillTemplateWithData();
		Map<String,String> tokensValor = dataObj.getPairVariableValue(formularios, stringVars.toString(), new FiltroVariables(), false);
		String expJudicial = tokensValor.get("judicial[expediente");
		String demandado = tokensValor.get("judicial[demandado");
		StringBuilder mensaje = new StringBuilder();
		if(desde.equals("evidencia")){
			mensaje.append("Se cargó la evidencia del escrito: "+actoDoc.getFormatoPdf().getDstitulo());

		}else if(desde.equals("escrito")){
			mensaje.append("Se genero el escrito: "+actoDoc.getFormatoPdf().getDstitulo());
		}
		mensaje.append(" del Expediente Judicial: "+expJudicial);
		mensaje.append("\n \n y expediente: "+tramite.getDsdirectorio()+" de Gon");
		mensaje.append("\n \n");
		mensaje.append("Está disponible en: "+urlDropbox);
		mensaje.append("\n \n \n \n Este es un mensaje automático del sistema, por favor no responda a este correo");
		if(desde.equals("evidencia")){
			if(tramite.getCliente().getEmails().equals(null) || 
					tramite.getCliente().getEmails().equals("")){
				logger.info("Cliente sin emails para recibir evidencia cargada");
				
			}else{
				BotMail.enviaMail(demandado,mensaje.toString(), tramite.getCliente().getEmails());
			}
	
		}else if(desde.equals("escrito")){

			String emails=NotariaUtils.getProperties("expjudicial.emails");
			BotMail.enviaMail(demandado,mensaje.toString(), emails,rutaArchivo);
			
		}
//		inicio proceso bonita
		if(actoDoc.getFormatoPdf().getDstitulo().equals("Promocion de Autorizacion")
				|| desde.equals("evidencia")){
			commonBean.setExpedienteJudicial(expJudicial);
			commonBean.setNombreDemandado(demandado);
			commonBean.setIdActo(actoDoc.getActo().getIdacto());
			commonBean.setNombreOperacion("Expediente Judicial");
			String textVar = "";
			if(desde.equals("evidencia")){
				textVar="presentó";
			}else{
				textVar="generó";
			}
			StringBuilder recordatorioText = new StringBuilder();
			recordatorioText.append("Este es un recordatorio de que se ha cumplido el plazo desde que se "+textVar+" el escrito: ");
			recordatorioText.append(actoDoc.getFormatoPdf().getDstitulo());
			recordatorioText.append("<br> Del expediente: "+expJudicial);
			recordatorioText.append("<br> A nombre de: "+demandado);
			commonBean.setMensajeCorreo(recordatorioText.toString());
//			commonBean.setNumExpediente(expedienteEnvio.getExpediente().getNumexpediente());
//			commonBean.setIsCompraventa(Boolean.TRUE);
			
			actoDocumentoBo.startBonitaJudicialesFlow(commonBean);		
		}
		return true;
	}
	
	@GET
	@Path("/mostrarDocumento")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response descargarDocumento(@QueryParam("id") String id,
			@QueryParam("idusuario") String idusuario,
			@QueryParam("idsesionactual") String idsesionactual,
			@QueryParam("tipo") String tipo, @Context ServletContext context,
			@Context HttpServletResponse response) {

		if (idsesionactual == null || idsesionactual.isEmpty()
				|| idusuario == null || idusuario.isEmpty() || id == null
				|| id.isEmpty()) {

			Response.ok(Constantes.ESTATUS_FALTAN_PARAMETROS).build();
		}

		if (NotariaUtils.isSesionValida(idsesionactual, idusuario)) {
			RegistroRi regRi = null;
			ActoDocumento actoDoc = null;
			String archivo;
			try {
				if (tipo.equals("ri")) {
					RegistroRiBo regBo = new RegistroRiBoImpl();
					regRi = regBo.findById(id);
					archivo = regRi.getDsruta();
					NotariaUtils.recuperaDoc(archivo, response);
				} else if (tipo.equals("doc") || tipo.equals("evidencia")) {
					actoDoc = this.actoDocumentoBo.findById(id);
					if (tipo.equals("doc")) {
						archivo = actoDoc.getDsrutaformato();
						NotariaUtils.recuperaDoc(archivo, response);
					} else {
						archivo = actoDoc.getDsruta();
						NotariaUtils.recuperaDoc(archivo, response);
					}
				}else if(tipo.equals("original")){
					
					archivo = this.documentosOriginalesBo.buscarArchivoPorId(id);
					if(archivo !=null){
						NotariaUtils.recuperaDoc(archivo, response);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			Response.status(200).entity(Constantes.ESTATUS_SESION_INVALIDA)
					.build();
		}
		return null;
	}

	@POST
	@Path("/obtenerPosteriores")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio obtenerPosteriores(DocumentoEnvio documentoEnvio,
			@Context HttpServletRequest request) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (documentoEnvio == null || documentoEnvio.getUsuario() == null
				|| documentoEnvio.getUsuario().getIdusuario() == null
				|| documentoEnvio.getUsuario().getIdusuario().isEmpty()
				|| documentoEnvio.getUsuario().getIdsesionactual() == null
				|| documentoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| documentoEnvio.getIdExpediente() == null
				|| documentoEnvio.getIdExpediente().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(documentoEnvio.getUsuario()
				.getIdsesionactual(), documentoEnvio.getUsuario()
				.getIdusuario())) {
			
			// Valido que exista una escritura ya que muchos documentos posteriores se tiene que calcular el notario de la misma
			
			EscrituraActoBo escrituraActo = new EscrituraActoBoImpl();
			
			try {
				String numEsc = escrituraActo.obtenIdEscrituraPorActoId(documentoEnvio.getIdacto());
				List<DatoActoDocumento> lista;

				//if(numEsc != null && !numEsc.isEmpty()){
					
					try {
						lista = this.actoDocumentoBo.obtenerPosteriores(
								documentoEnvio.getIdExpediente(),
								documentoEnvio.getIdacto());
						if (lista != null) {
							ActoBo actoBo = new ActoBoImpl();
							Acto acto = actoBo.findById(documentoEnvio.getIdacto());
							if(acto!=null){
								respuesta.setHasanexo5(acto.getHasanexo5());
								respuesta.setHasdim(acto.getHasdim());
							}
							
							
							respuesta
									.setListaPosteriores(new ArrayList<DatoActoDocumento>(
											lista));
						} else {
							respuesta
									.setListaPosteriores(new ArrayList<DatoActoDocumento>());
						}

					} catch (NotariaException e) {
						e.printStackTrace(System.out);
						respuesta.setEstatus(e.getMessage());
						respuesta.setExito(false);
						return respuesta;
					}	
					
					
					
				/*}else {
					respuesta
					.setListaPosteriores(new ArrayList<DatoActoDocumento>());
				}*/
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	@Path("/obtenerPosterioresExpediente")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoEnvio obtenerPosterioresExpediente(DocumentoEnvio dse){
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (dse == null || dse.getUsuario() == null
				|| dse.getUsuario().getIdusuario() == null || dse.getUsuario().getIdusuario().isEmpty()
				|| dse.getUsuario().getIdsesionactual() == null || dse.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(dse.getUsuario().getIdsesionactual(),dse.getUsuario().getIdusuario())){
			try{
				
				ActoDocumentoBo actodocBo= new ActoDocumentoBoImpl();
				ArrayList<DatoActoDocumento> docs= (ArrayList<DatoActoDocumento>) actodocBo.obtenerPosteriores(dse.getIdExpediente());
				respuesta.setListaPosteriores(docs);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
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
	@Path("/obtenerDocXNombre")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoEnvio obtenerDocXNombreActo(DocumentoEnvio dse){
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (dse == null || dse.getUsuario() == null
				|| dse.getUsuario().getIdusuario() == null || dse.getUsuario().getIdusuario().isEmpty()
				|| dse.getUsuario().getIdsesionactual() == null || dse.getUsuario().getIdsesionactual().isEmpty()
				|| dse.getIdacto() == null || dse.getIdacto().isEmpty()
				) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(dse.getUsuario().getIdsesionactual(),dse.getUsuario().getIdusuario())){
			try{
				ActoDocumentoBo actodocBo= new ActoDocumentoBoImpl();
				ArrayList<DatoActoDocumento> docs= (ArrayList<DatoActoDocumento>) actodocBo.obtenerDocXnombre(dse.getNombreDoc(), dse.getIdExpediente(), dse.getIdacto());
				respuesta.setListaPosteriores(docs);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
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
	@Path("/obtenerOriginales")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio obtenerOriginales(DocumentoEnvio documentoEnvio,
			@Context HttpServletRequest request) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (documentoEnvio == null || documentoEnvio.getUsuario() == null
				|| documentoEnvio.getUsuario().getIdusuario() == null
				|| documentoEnvio.getUsuario().getIdusuario().isEmpty()
				|| documentoEnvio.getUsuario().getIdsesionactual() == null
				|| documentoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| documentoEnvio.getIdacto() == null
				|| documentoEnvio.getIdacto().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(documentoEnvio.getUsuario()
				.getIdsesionactual(), documentoEnvio.getUsuario()
				.getIdusuario())) {

			try {

				List<DocumentosOriginales> lista;
				lista = this.documentosOriginalesBo
						.obtenerListaOriginales(documentoEnvio
								.getIdacto());
				if (lista != null)
					respuesta
							.setListaOrigiales(new ArrayList<DocumentosOriginales>(lista));
				else
					respuesta
							.setListaOrigiales(new ArrayList<DocumentosOriginales>());

				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);

			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	@Path("/eliminarActoDocumento")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio eliminarActoDocumento(DocumentoEnvio documentoEnvio,
			@Context HttpServletRequest request) {
		DocumentoEnvio respuesta = new DocumentoEnvio();

		if (documentoEnvio == null || documentoEnvio.getUsuario() == null
				|| documentoEnvio.getUsuario().getIdusuario() == null
				|| documentoEnvio.getUsuario().getIdsesionactual() == null
				|| documentoEnvio.getIdactodoc() == null
				|| documentoEnvio.getIdactodoc().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(documentoEnvio.getUsuario()
				.getIdsesionactual(), documentoEnvio.getUsuario()
				.getIdusuario())) {

			try {
				BitacoraGeneralHelper bg = new BitacoraGeneralHelper();
				Expediente exp = new Expediente();
				exp.setIdexpediente(documentoEnvio.getIdExpediente());

				/** obtener objeto completo **/
				ActoDocumento actoDocumento = new ActoDocumento();
				actoDocumento = this.actoDocumentoBo.findById(documentoEnvio
						.getIdactodoc());
				if (actoDocumento == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				/** si ya se ha aprovado y entregado, no se puede eliminar **/
				if (actoDocumento.getIsentregado() != null
						&& actoDocumento.getIsentregado()
						&& actoDocumento.getIsaprovado() != null
						&& actoDocumento.getIsaprovado()) {
					respuesta
							.setEstatus(Constantes.ESTATUS_ACTODOCUMENTO_ELIMINACION_NOPERMITIDA);
					respuesta.setExito(false);
					return respuesta;
				}
				String archivo = actoDocumento.getDsruta();
				/** eliminar **/
				this.actoDocumentoBo.borrar(actoDocumento);
			
					if (archivo != null) {
						File ruta = new File(archivo);
						ruta.delete();
					}
					respuesta
							.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
					bg.registrarEnBitacora(documentoEnvio.getUsuario()
							.getIdusuario(), documentoEnvio.getIdExpediente(),
							null, "DocumentoEscaneado",
							Constantes.OPERACION_ELIMINACION,
							"Se elimina un DocumentoEscaneado");

				

			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	@Path("/buscarOriginalPorId")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio buscarOriginalPorId(DocumentoEnvio documentoEnvio,
			@Context HttpServletRequest request) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		/*if (documentoEnvio == null || documentoEnvio.getUsuario() == null
				|| documentoEnvio.getUsuario().getIdusuario() == null
				|| documentoEnvio.getUsuario().getIdsesionactual() == null
				|| documentoEnvio.getIdExpediente() == null
				|| documentoEnvio.getOriginal() == null
				|| documentoEnvio.getOriginal().getIddocor() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(documentoEnvio.getUsuario()
				.getIdsesionactual(), documentoEnvio.getUsuario()
				.getIdusuario())) {

			try {
				DocumentosOriginales docor;
				docor = this.documentosOriginalesBo.findById(documentoEnvio
						.getOriginal().getIddocor());
				docor.setExpediente(null);
				respuesta.setOriginal(docor);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {

			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}*/
		return respuesta;
	}

	@POST
	@Path("/guardarOriginal")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio guardarOriginal(DocumentoEnvio de,
			@Context HttpServletRequest request) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			
			de.getDocumentoOriginal().setIdsesion(de.getUsuario().getIdsesionactual());
			
			DocumentosOriginalesBo docOrBo = new DocumentosOriginalesBoImpl();
			DocumentosOriginales docOriginal = de.getDocumentoOriginal();
			docOriginal.setId(GeneradorId.generaId(docOriginal));
			docOriginal.setIdsesion(de.getUsuario().getIdsesion());
			docOriginal.setIsentregado(false);
			docOriginal.setIsvalidado(false);
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
	@Path("/actualizarOriginal")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio actualizarOriginal(DocumentoEnvio de,
			@Context HttpServletRequest request) {
	
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			
			de.getDocumentoOriginal().setIdsesion(de.getUsuario().getIdsesionactual());
			
			DocumentosOriginalesBo docOrBo = new DocumentosOriginalesBoImpl();
			DocumentosOriginales docOriginal = de.getDocumentoOriginal();
			docOriginal.setIdsesion(de.getUsuario().getIdsesion());
			try {
				docOrBo.update(docOriginal);
				docOrBo.validaEnvioExpJudiciales(docOriginal, de.getUsuario());
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
	@Path("/eliminarOriginal")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio eliminarOriginal(DocumentoEnvio de,
			@Context HttpServletRequest request) {
		DocumentoEnvio respuesta = new DocumentoEnvio();
		if (de == null || de.getUsuario() == null
				|| de.getUsuario().getIdusuario() == null || de.getUsuario().getIdusuario().isEmpty()
				|| de.getUsuario().getIdsesionactual() == null || de.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(de.getUsuario().getIdsesionactual(),de.getUsuario().getIdusuario())){
			
			DocumentosOriginalesBo docOrBo = new DocumentosOriginalesBoImpl();
			DocumentosOriginales docOriginal = de.getDocumentoOriginal();
			try {
				docOrBo.borrar(docOriginal.getId());
			} catch (NotariaException e) {
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
	@Path("/subirOriginal")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoEnvio subirArchivoOriginal(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("file") FormDataBodyPart body,
			@FormDataParam("iddocor") String iddocor,
			@FormDataParam("idsesionactual") String idsesionactual,
			@FormDataParam("idusuario") String idusuario,
			@FormDataParam("desde") String desde,
			@Context ServletContext context) {

		DocumentoEnvio respuesta = new DocumentoEnvio();

		if (idsesionactual == null || idsesionactual.isEmpty()
				|| idusuario == null || idusuario.isEmpty() || iddocor == null
				|| iddocor.isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(idsesionactual, idusuario)) {
			try {
				if (!body.getMediaType().toString().equals("application/pdf")
						&& !body.getMediaType().toString().equals("image/jpeg")
						&& !body.getMediaType().toString().equals("image/png")) {
					respuesta.setEstatus("El formato no es imagen o pdf");
					respuesta.setExito(false);
					return respuesta;
				}
				Date fecha = new Date();
				String archivoExistente = this.documentosOriginalesBo
						.buscarArchivoPorId(iddocor);
				if (archivoExistente != null && !archivoExistente.isEmpty()) {
					NotariaUtils.eliminarArchivo(archivoExistente);
				}
				String nuevoNombre = fecha.getTime() + "_";
				if(desde!=null || !desde.isEmpty() || !desde.equals("")){
					nuevoNombre += desde;  

				}else{
					nuevoNombre += fileDetail.getFileName();
				}

				String tramiteFolder = TramiteFileUtils
						.obtenRutaFolderByOriginalId(iddocor);
				if (tramiteFolder == null) {
					respuesta
							.setEstatus("No se pudo obtener el folder de tramite.");
					respuesta.setExito(false);
					return respuesta;
				}
				String uploadedFileLocation = tramiteFolder + File.separator
						+ nuevoNombre;

				boolean b = NotariaUtils.writeToFile(uploadedInputStream,
						uploadedFileLocation);

				if (b) {
					/**
					 * Cuando suben un archivo se guarda el campo 'fechaEntrega'
					 **/
					boolean isEntrega = true;
					b = this.documentosOriginalesBo
							.actualizarRutaArchivoOriginal(iddocor,
									uploadedFileLocation);
					uploadedInputStream = null;
					respuesta.setRutaArchivoDescarga(uploadedFileLocation);
					respuesta.setExito(true);
					return respuesta;
				} else {
					uploadedInputStream = null;
					respuesta.setRutaArchivoDescarga("");
					respuesta.setExito(false);
					return respuesta;
				}
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA + " "
						+ e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@GET
	@Path("/descargarOriginal")
	public Response descargarOriginal(
			@QueryParam("idEvidencia") String idEvidencia,
			@QueryParam("idsesion") String idsesion,
			@QueryParam("idusuario") String idusuario,
			@Context ServletContext context) {

		if (idEvidencia == null || idEvidencia.isEmpty() || idsesion == null
				|| idsesion.isEmpty() || idusuario == null
				|| idusuario.isEmpty()) {

			return Response.ok(Constantes.ESTATUS_FALTAN_PARAMETROS).build();
		}
		if (NotariaUtils.isSesionValida(idsesion, idusuario)) {
			try {
				String archivo = this.documentosOriginalesBo
						.buscarArchivoPorId(idEvidencia);
				ResponseBuilder response = null;

				if (archivo != null)
					response = NotariaUtils.obtenerArchivo(archivo,
							context.getRealPath(""));
				if (response != null)
					return response.build();
				else
					return Response.status(200)
							.entity(Constantes.ESTATUS_ERROR_CONSULTAR_ARCHIVO)
							.build();
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				return Response
						.status(500)
						.entity(Constantes.ESTATUS_ERROR_CONSULTA + " "
								+ e.getMessage()).build();
			}
		} else {
			return Response.status(200)
					.entity(Constantes.ESTATUS_SESION_INVALIDA).build();
		}

	}

	@POST
	@Path("/reemplazaVariablesPorValor")
	@Produces({ "application/json", "application/xml" })
	public DocumentoEnvio reemplazaVariablesPorValor(
			DocumentoEnvio documentoEnvio, @Context HttpServletRequest request,
			@Context ServletContext context) {
		DocumentoEnvio respuesta = new DocumentoEnvio();

		if (documentoEnvio == null || documentoEnvio.getUsuario() == null
				|| documentoEnvio.getUsuario().getIdusuario() == null
				|| documentoEnvio.getUsuario().getIdsesionactual() == null
				|| documentoEnvio.getIdactodoc() == null
				|| documentoEnvio.getIdactodoc().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(documentoEnvio.getUsuario()
				.getIdsesionactual(), documentoEnvio.getUsuario()
				.getIdusuario())) {
			try {
				ActoDocumento actDoc = this.actoDocumentoBo
						.obtenerCompletoPorId(documentoEnvio.getIdactodoc());
				if (actDoc == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				if (actDoc.getActo() == null
						|| actDoc.getActo().getIdacto() == null
						|| actDoc.getActo().getIdacto().isEmpty()) {
					respuesta
							.setEstatus(Constantes.ESTATUS_NO_SE_ENCONTRO_ACTO);
					respuesta.setExito(false);
					return respuesta;

				}
				// validacion innecesaria porque ahora busca en formato pdf
				// tambien
				// if (actDoc.getDocumento() == null
				// || actDoc.getDocumento().getTxplantilla() == null
				// || actDoc.getDocumento().getTxplantilla().isEmpty()) {
				// respuesta
				// .setEstatus(Constantes.ESTATUS_NO_EXISTE_PLANTILLA);
				// respuesta.setExito(false);
				// return respuesta;
				// }
				// EscrituraParcialGenerator pGenerator = new
				// EscrituraParcialGenerator();
				FiltroVariables fvariable = new FiltroVariables();
				fvariable.setIdacto(actDoc.getActo().getIdacto());
				// if(actDoc.getDocumento()==null){
				// String texto = pGenerator.reemplazarVariables(fvariable,
				// actDoc
				// .getDocumento().getTxplantilla(), false);
				// if (texto == null) {
				// respuesta
				// .setEstatus(Constantes.ESTATUS_ERROR_GENERACION_TEXTO);
				// respuesta.setExito(false);
				// }
				// // carajo hay que poner nombres coherentes a las variables
				// boolean b = this.actoDocumentoBo.actualizaTexto(
				// documentoEnvio.getIdactodoc(), texto);
				// if (!b) {
				// respuesta
				// .setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				// respuesta.setExito(false);
				// return respuesta;
				// }
				// }
				FormatosGenerator fGenerator = new FormatosGenerator();
				String rutaFormato = "";
				String idtramite = null;
				if (actDoc.getDocumento() != null
						&& actDoc.getFormatoPdf() == null) {
					rutaFormato = fGenerator.generaDocumentoDeFormato(
							documentoEnvio.getIdactodoc(),
							context.getRealPath(""));
				} else if (actDoc.getFormatoPdf() != null) {
					List<String> camposPDF = new ArrayList<>();
					
//					TODO: omar 26mar18 se modifica de donde se obtiene el mapa de variables 
//					dependiendo si es pdf o docx para que cargue el archivo y lea los campos
					
					Map<String, String> mapaVariables = null;
					Boolean isPdf = null;
					if(actDoc.getFormatoPdf().getDsruta().contains(".pdf")){	
						mapaVariables = fGenerator.extraerPdf(
								actDoc, camposPDF);
						isPdf=Boolean.TRUE;
					}else if(actDoc.getFormatoPdf().getDsruta().contains(".docx")){
						mapaVariables = fGenerator.extraerDocx(actDoc);
						isPdf=Boolean.FALSE;
					}
					PDFForms pdfForm = new PDFForms();
					System.out.println("mapa variables " + mapaVariables);
					System.out.println("ruta origen archivo "
							+ actDoc.getFormatoPdf().getDsruta());
					
					
					TramiteBo tramiteBo = new TramiteBoImpl();
					Tramite tramite = new Tramite();
					idtramite = actoDocumentoBo
							.obtenerIdTramitePorDocumento(actDoc.getIdactodoc());
					tramite.setIdtramite(idtramite);
					tramite = tramiteBo.buscarPorIdCompleto(tramite);
					
					/*SE MODIFICÓ EL MANEJO DE DOCUMENTOS*/
					/*String carpeta = Constantes.EXPEDIENTES_HOME
							+ File.separator + idtramite;*/
					String carpeta = Constantes.EXPEDIENTES_HOME
							+ File.separator + tramite.getDsdirectorio();
					File dir = new File(carpeta);
					dir.setReadable(true);
					if (!dir.isDirectory()) {
						FileUtils.forceMkdir(dir);
					}
					
					String rutaDocumento = actDoc.getFormatoPdf().getDsruta();
					String extension = null;
					if(isPdf){
						extension = ".pdf";
					}else{
						extension = ".docx";
					}
					String rutaAbsoluta = carpeta +File.separator + actDoc.getFormatoPdf().getDstitulo() + extension;
					
					// VICTOR filtro de variables por suboperación para evitar valores nulos
					
					List<FormatoPDFDetalle> listaFiltrada = new ArrayList<FormatoPDFDetalle>();
					String IdsubOperacion = actDoc.getActo().getSuboperacion().getIdsuboperacion();
					for(FormatoPDFDetalle fpdf_det: actDoc.getFormatoPdf().getDetalleList()){
						if(fpdf_det.getSuboperacion()!=null){
							if(fpdf_det.getSuboperacion().getIdsuboperacion().equals(IdsubOperacion)){
								listaFiltrada.add(fpdf_det);
							}
						}else{
								listaFiltrada.add(fpdf_det);
						}
						
					}
					DocxForm docxForm = new DocxForm();
					if(isPdf){
						rutaFormato = pdfForm.fillFields(rutaDocumento, mapaVariables,rutaAbsoluta,listaFiltrada);
					}else{
						rutaFormato = docxForm.replaceFields(rutaDocumento, mapaVariables,rutaAbsoluta,listaFiltrada);
					}
				}
				if (rutaFormato == null) {
					respuesta
							.setEstatus("Error al generar documento del formato correctamente");
					respuesta.setExito(false);
					return respuesta;
				}
				/**
				 * si se vuelve a generar el .doc del formato, eliminar el viejo
				 **/
				String rutaOldFormatFile = actDoc.getDsrutaformato();
				if (rutaOldFormatFile != null) {
					NotariaUtils.eliminarArchivo(rutaOldFormatFile);
				}
				/** inficar la ruta donde se guardo el archivo **/
				actoDocumentoBo.actualizarRutaArchivoFormato(
						documentoEnvio.getIdactodoc(), rutaFormato);
				if(actDoc.getActo().getSuboperacion().getOperacion().getDsnombre().equals("Expediente Judicial")){
					BonitaCommonBean commonBean = new BonitaCommonBean();
					commonBean.setIdusuario(documentoEnvio.getUsuario().getIdusuario());
					commonBean.setIdsesionactual(documentoEnvio.getUsuario().getIdsesionactual());
					commonBean.setUsuario(documentoEnvio.getUsuario().getCdusuario());
					uploadToDropboxAndSendMail(actDoc, "escrito", rutaFormato,commonBean);
					TramiteBo tramiteBo = new TramiteBoImpl();
					Expediente exp = tramiteBo.buscarIdExpediente(idtramite);
					Comentario comentario = new Comentario();
					comentario.setDstexto("Entrada automática de sistema: Se generó el escrito de: "+actDoc.getFormatoPdf().getDstitulo());
					comentario.setIdobjeto(exp.getIdexpediente());
					comentario.setIdcomentario(GeneradorId.generaId(exp.getIdexpediente()));
					comentario.setIdsesion(documentoEnvio.getUsuario().getIdsesionactual());
					comentario.setUsuario(documentoEnvio.getUsuario());
					ComentarioBo comentBo = new ComentarioBoImpl();
					comentBo.save(comentario);
				}
				respuesta.setRutaArchivoDescarga(rutaFormato);
				respuesta
						.setEstatus("Se generó el documento del formato correctamente");
				return respuesta;

			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} catch (COSVisitorException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} catch (IOException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

}
