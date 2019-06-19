package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.DocumentoNotarialMasterBo;
import com.palestra.notaria.bo.DocumentoNotarialParcialBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.impl.DocumentoNotarialMasterBoImpl;
import com.palestra.notaria.bo.impl.DocumentoNotarialParcialBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.DocumentoNotarialParcialEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;
import com.palestra.notarias.service.GeneradorDocumentoService;

/**
 * Clase que atiende las peticiones CRUD para Documento Notarial Parcial
 * 
 * @author sofia
 * 
 */
@Path("/documentoNotarialParcial")
public class DocumentoNotarialParcialRest {

	static Logger logger = Logger.getLogger(DocumentoNotarialParcialRest.class);

	private DocumentoNotarialParcialBo notarialParcialBo;
	private EscrituraBo escrituraBo;
	private BitacoraGeneralHelper bitacoraGeneralHelper;
	private DocumentoNotarialMasterBo docMasterBo;

	public DocumentoNotarialParcialRest() {
		this.notarialParcialBo = new DocumentoNotarialParcialBoImpl();
		this.escrituraBo = new EscrituraBoImpl();
		this.bitacoraGeneralHelper = new BitacoraGeneralHelper();
		this.docMasterBo = new DocumentoNotarialMasterBoImpl();
		
	}

	@POST
	@Path("/obtenerUltimaVersion")
	@Produces({ "application/json", "application/xml" })
	public DocumentoNotarialParcialEnvio obtenerUltimaVersion(
			DocumentoNotarialParcialEnvio parcialRequest,
			@Context HttpServletRequest request) throws JSONException {

		DocumentoNotarialParcial parcial = parcialRequest.getDocumentoParcial();
		DocumentoNotarialParcialEnvio respuesta = new DocumentoNotarialParcialEnvio();

		if (parcial == null || parcial.getEscritura() == null
				|| parcial.getEscritura().getIdescritura() == null
				|| parcial.getEscritura().getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(parcialRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(parcialRequest.getUsuario()
				.getIdsesionactual(), parcialRequest.getUsuario().getIdusuario())) {
			try {

				DocumentoNotarialParcial parcialLast = notarialParcialBo.getLastVersion(parcial.getEscritura().getIdescritura());
				if(parcialLast==null){
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				// asingar nullo, no es necesario.
				parcialLast.setEscritura(null);
				
				respuesta.setDocumentoParcial(parcialLast);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				return respuesta;

			} catch(NotariaException e){
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
	
	@POST
	@Path("/listarTodos")
	@Produces({ "application/json", "application/xml" })
	public DocumentoNotarialParcialEnvio listarDocNotParcial(DocumentoNotarialParcialEnvio parcialRequest) {

//		DocumentoNotarialParcial parcial = parcialRequest.getDocumentoParcial();
		DocumentoNotarialParcialEnvio respuesta = new DocumentoNotarialParcialEnvio();

		if (NotariaUtils.faltanRequeridosUsuario(parcialRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(parcialRequest.getUsuario()
				.getIdsesionactual(), parcialRequest.getUsuario().getIdusuario())) {
			try {	
				respuesta.setDocumentoParcialList((ArrayList<DocumentoNotarialParcial>)notarialParcialBo.findAll());
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else {
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				respuesta.setExito(false);
				return respuesta;
			}
		}
	
	@POST
	@Path("/listarPorEscritura")
	@Produces({ "application/json", "application/xml" })
	public DocumentoNotarialParcialEnvio listarPorEscritura(DocumentoNotarialParcialEnvio parcialRequest) {

		DocumentoNotarialParcial parcial = parcialRequest.getDocumentoParcial();
		DocumentoNotarialParcialEnvio respuesta = new DocumentoNotarialParcialEnvio();

		if (parcial == null || parcial.getEscritura() == null
				|| parcial.getEscritura().getIdescritura() == null
				|| parcial.getEscritura().getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(parcialRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(parcialRequest.getUsuario()
				.getIdsesionactual(), parcialRequest.getUsuario().getIdusuario())) {
			try {	
				respuesta.setDocumentoParcialList((ArrayList<DocumentoNotarialParcial>)notarialParcialBo.getByEscritura(parcial.getEscritura()));
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else {
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				respuesta.setExito(false);
				return respuesta;
			}
		}	
		

	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public DocumentoNotarialParcialEnvio guardar(
			DocumentoNotarialParcialEnvio parcialRequest,
			@Context HttpServletRequest request) throws JSONException {

		DocumentoNotarialParcial parcial = parcialRequest.getDocumentoParcial();
		DocumentoNotarialParcialEnvio respuesta = new DocumentoNotarialParcialEnvio();

		if (parcial == null || parcial.getEscritura() == null
				|| parcial.getEscritura().getIdescritura() == null
				|| parcial.getEscritura().getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(parcialRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(parcialRequest.getUsuario()
				.getIdsesionactual(), parcialRequest.getUsuario().getIdusuario())) {
			try {

				respuesta = this.validarRequeridos(parcialRequest);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}
				
				// Obtener expediente del acto, es necesario para bitacora
				String idExpediente = escrituraBo
						.getExpedienteIdByEscrituraId(parcial.getEscritura()
								.getIdescritura());
//				recuperar de bd el docnotparcial
				DocumentoNotarialParcial docRecuperado = notarialParcialBo.findById(parcial.getIddocnotpar());
				DocumentoNotarialMaster docMaster = docMasterBo.findByEscrituraId(docRecuperado.getEscritura().getIdescritura());
				
				//VICTOR 
				//DOC MASTER ES QUE YA SE ENCUENTRA UNA MASTER GENERADO Y NO SE PODRÁ MODIFICAR SE COMENTO PARA GENERAR DOCUMENTO EN LOCAL
				// 
				/*if(docMaster != null){
					respuesta.setEstatus("no se puede modificar, ya existe un master de este documento");
					respuesta.setExito(false);
					return respuesta;
				}*/
				/** asignar valores que no se capturan desde pantalla **/

//				parcial.setEscritura(parcial.getEscritura());
				parcial.setFecha(new Date());
				parcial.setTmstmp(new Timestamp((new Date()).getTime()));
				parcial.setIdsesion(parcialRequest.getUsuario().getIdsesionactual());

				if(parcial.getIsGenerarMaster()){
					if (!escrituraBo.tieneNumEscritura(parcial.getEscritura().getIdescritura())) {
						
						/*respuesta.setEstatus(Constantes.ESTATUS_NO_TIENE_NUMESCRITURA);
						respuesta.setExito(false);
						return respuesta;*/
						
					}
					
					System.out.println("ES MASTER");
	//				cuando un docnotarialparcial con la version cerrada se modifica, entonces se crea una nueva version 
					//if(docRecuperado.getIscerrado()){
						System.out.println("ES DOC CERRADO");
						parcial.setIddocnotpar(GeneradorId.generaId(parcial));
						parcial.setVersion(notarialParcialBo.getActualVersion(parcial.getEscritura().getIdescritura()));
						parcial.setIscerrado(false);
						/** Guardar **/
						String id = notarialParcialBo.save(parcial).getIddocnotpar();
						parcial = notarialParcialBo.findById(id);
						GeneradorDocumentoService service = new GeneradorDocumentoService();
						service.generaMasterPorEscritura(parcial.getEscritura(), parcial.getTxtdoc(), parcialRequest.getUsuario().getIdsesionactual());
					/*}else{
						System.out.println("ES DOC ABIERTO");
						parcial.setIscerrado(true);
	//					si no esta cerrada la version entonces se actualizan los cambios sobre la misma 
						parcial = notarialParcialBo.update(parcial);
						GeneradorDocumentoService service = new GeneradorDocumentoService();
						service.generaMasterPorEscritura(parcial.getEscritura(), parcial.getTxtdoc(), parcialRequest.getUsuario().getIdsesionactual());
					}*/
					
				}else{

					System.out.println("NO ES MASTER");
	//				cuando un docnotarialparcial con la version cerrada se modifica, entonces se crea una nueva version 
					 if(!docRecuperado.getIscerrado()){
							System.out.println("ES DOC ABIERTO");
//							parcial.setIddocnotpar(GeneradorId.generaId(parcial));
		//					si no esta cerrada la version entonces se actualizan los cambios sobre la misma 
							notarialParcialBo.update(parcial);	
					}else if(docRecuperado.getIscerrado()){
						
						System.out.println("ES DOC CERRADO");
						parcial.setIddocnotpar(GeneradorId.generaId(parcial));
						parcial.setVersion(notarialParcialBo.getActualVersion(parcial.getEscritura().getIdescritura()));
						
						parcial = notarialParcialBo.save(parcial);
//						parcial.setIscerrado(false);
						/** Guardar **/
//						notarialParcialBo.save(parcial);
					}
				}
					
				if (idExpediente != null && !idExpediente.isEmpty()) {
					/** registrar en bitacora, el registro de documento notarial master **/
					bitacoraGeneralHelper.registrarEnBitacora(parcialRequest
							.getUsuario().getIdusuario(), idExpediente, null,
							"Documento Notarial Parcial", Constantes.OPERACION_REGISTRO,
							"Se guarda Documento Notarial Parcial");
				}
				else{
					logger.info("No se pudo obtener el id expediente y por lo que no se registró en bitácora.");
				}

				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;

			} catch(NotariaException e){
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

	/**
	 * Valida los campos que son requeridos a nivel de BD para persistencia
	 * 
	 * @param parcialRequest
	 * 
	 * @return
	 * 
	 */
	private DocumentoNotarialParcialEnvio validarRequeridos(
			DocumentoNotarialParcialEnvio parcialRequest) {

		DocumentoNotarialParcial parcial = parcialRequest.getDocumentoParcial();

		DocumentoNotarialParcialEnvio respuesta = new DocumentoNotarialParcialEnvio();

		ArrayList<CodigoError> errores = new ArrayList<CodigoError>();
		CodigoError error = null;

		if (parcial == null || parcial.getTxtdoc() == null
				|| parcial.getTxtdoc().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E33B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E33B1);
			errores.add(error);
			respuesta.setExito(false);
		}

		respuesta.setErrores(errores);

		return respuesta;
	}

}
