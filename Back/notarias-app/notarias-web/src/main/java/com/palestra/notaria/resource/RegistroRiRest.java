package com.palestra.notaria.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.RegistroRiBo;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.RegistroRiBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.RegistroRiEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.RegistroRi;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

/**
 * Clase que atiende las peticiones crud de RegistroRi
 * 
 * @author sofia
 * 
 */
@Path("/registrori")
public class RegistroRiRest {

	static Logger logger = Logger.getLogger(RegistroRiRest.class);
	private RegistroRiBo registroRiBo = null;
	private BitacoraGeneralHelper bitacoraGeneralHelper = null;

	public RegistroRiRest() {
		registroRiBo = new RegistroRiBoImpl();
		bitacoraGeneralHelper = new BitacoraGeneralHelper();
	}

	@POST
	@Path("/obtenerRegistroRi")
	@Produces({ "application/json", "application/xml" })
	public RegistroRiEnvio obtenerRegistroRi(RegistroRiEnvio rie,
			@Context HttpServletRequest request) throws JSONException {
		Compareciente compareciente = rie.getCompareciente();
		RegistroRiEnvio respuesta = new RegistroRiEnvio();

		if (compareciente == null || compareciente.getIdcompareciente() == null
				|| compareciente.getIdcompareciente().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(rie)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(rie.getUsuario().getIdsesionactual(),
				rie.getUsuario().getIdusuario())) {
			try {
				RegistroRi riPersisted = this.registroRiBo
						.findFromComparecienteId(compareciente
								.getIdcompareciente());
				if (riPersisted == null) {
					respuesta
							.setEstatus("El compareciente no tiene registro ri.");
					respuesta.setExito(false);
					respuesta.setCompareciente(compareciente);
					return respuesta;
				}

				/**
				 * al objeto compareciente enviado asignar el registro ri y
				 * retornar
				 **/
				compareciente.setRegistroRi(riPersisted);
				respuesta.setCompareciente(compareciente);
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
	@Path("/guardar")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public RegistroRiEnvio guardar(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("file") FormDataBodyPart body,
			@FormDataParam("idsesionactual") String idsesionactual,
			@FormDataParam("idusuario") String idusuario,
			@FormDataParam("idcompareciente") String idcompareciente,
			@FormDataParam("idexpediente") String idexpediente,
			@FormDataParam("idtipodocumento") String idtipodocumento,
			@FormDataParam("idexpedidopor") String idexpedidopor,
			@FormDataParam("dsnombre") String dsnombre,
			@FormDataParam("numeroclave") String numeroclave,
			@FormDataParam("idtramite") String idtramite,
			@FormDataParam("isvalidadonotario_check") String isvalidadonotario_check,
			@FormDataParam("isvalidadonotario") Boolean isvalidadonotario,
			@Context ServletContext context) {
		RegistroRiEnvio respuesta = new RegistroRiEnvio();
		try {

			if (idcompareciente == null || idcompareciente.isEmpty()
					|| idexpediente == null || idexpediente.isEmpty()
					|| idusuario == null || idusuario.isEmpty()
					|| idsesionactual == null || idsesionactual.isEmpty()
					|| idtramite == null || idtramite.isEmpty()
					|| isvalidadonotario == null) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}

//			if (!isvalidadonotario
//					&& !body.getMediaType().toString()
//							.equals("application/pdf")
//					&& !body.getMediaType().toString().equals("image/jpeg")
//					&& !body.getMediaType().toString().equals("image/png")) {
//				respuesta.setEstatus("El formato no es imagen o pdf");
//				respuesta.setExito(false);
//				return respuesta;
//			}

//			if (cPersisted != null && cPersisted.getRegistroRi() != null) {
//				/** quitar la referencia del registro ri, para poder elminarlo **/
//				comparecienteBo.actualizaRegistroRi(idcompareciente);
//				registroRiBo.delete(cPersisted.getRegistroRi());
//				String rutaArchivo = cPersisted.getRegistroRi().getDsruta();
//				if (rutaArchivo != null) {
					
//				}
//			}
			if (NotariaUtils.isSesionValida(idsesionactual, idusuario)) {
				/** Obtener compareciente para verificar si tiene ri **/
				ComparecienteBo comparecienteBo = new ComparecienteBoImpl();
				RegistroRiBo registroRiBo = new RegistroRiBoImpl();
//				Compareciente cFiltro = new Compareciente();
//				cFiltro.setIdcompareciente(idcompareciente);
				Compareciente cPersisted = new Compareciente();
				cPersisted.setIdcompareciente(idcompareciente);
				cPersisted = comparecienteBo.buscarPorIdCompleto(cPersisted);
				boolean b = true;
				String uploadedFileLocation = null;
				RegistroRi registroRi = new RegistroRi();
				
				if(cPersisted.getRegistroRi()!=null){
					System.out.println("existe ri, update");
//					@omarete ya tiene ri, se actualizara el registro de ri
					RegistroRi registroUpdate = registroRiBo.findById(cPersisted.getRegistroRi().getIdregri());
					registroUpdate.setDsnombre(cPersisted.getPersona().getDsnombrecompleto());
					registroUpdate.setExpedidopor(new ElementoCatalogo(idexpedidopor));
					registroUpdate.setTipo(new ElementoCatalogo(idtipodocumento));
					registroUpdate.setIdsesion(idsesionactual);
					registroUpdate.setIsvalidadonotario(isvalidadonotario);
					registroUpdate.setNumclave(numeroclave);
					registroUpdate.setFechaadjuntado(new Date());
					if(isvalidadonotario){
						if(cPersisted.getRegistroRi().getDsruta()!=null){
							NotariaUtils.eliminarArchivo(cPersisted.getRegistroRi().getDsruta());
							registroUpdate.setDsruta("");
						}
						registroUpdate.setExpedidopor(null);
						registroUpdate.setTipo(null);
					}else{
						Date fecha = new Date();
						String nuevoNombre = fecha.getTime() + "_"
								+ fileDetail.getFileName();
						String tramiteFolder = Constantes.EXPEDIENTES_HOME
								+ File.separator + idtramite;
						File dir = new File(tramiteFolder);
						dir.setReadable(true);
						if (!dir.exists() || !dir.isDirectory()) {
							logger.info("============> Se creo folder:"
									+ tramiteFolder);
							FileUtils.forceMkdir(dir);
						}
						uploadedFileLocation = tramiteFolder
								+ File.separator + nuevoNombre;
						b = NotariaUtils.writeToFile(uploadedInputStream,
								uploadedFileLocation);
						System.out.println("file detail "+fileDetail.getFileName());
						if(fileDetail.getFileName() != null){
							registroUpdate.setDsruta(uploadedFileLocation);
						}
					}
					registroRiBo.update(registroUpdate);
					respuesta.setEstatus("registro correcto");
					respuesta.setExito(true);
					return respuesta;
				}else{
					System.out.println("nuevo ri");
					/** Crear objetoRi **/
					registroRi.setIsvalidadonotario(isvalidadonotario);
					/** asignar propiedades que no se capturan en pantalla **/
					registroRi.setIdregri(GeneradorId.generaId(registroRi));
					registroRi.setIdsesion(idusuario);
					registroRi.setTmstmp(new Timestamp((new Date()).getTime()));
	
	
					if (!isvalidadonotario) {
						/** si no es validado notario **/
						registroRi.setDsnombre(dsnombre);
						ElementoCatalogo tipoDocumento = new ElementoCatalogo();
						tipoDocumento.setIdelemento(idtipodocumento);
						registroRi.setTipo(tipoDocumento);
						registroRi.setExpedidopor(new ElementoCatalogo(idexpedidopor));
						ElementoCatalogo expedidoPor = new ElementoCatalogo();
						expedidoPor.setIdelemento(idexpedidopor);
						registroRi.setNumclave(numeroclave);
						
						/** subir archivo **/
						Date fecha = new Date();
						String nuevoNombre = fecha.getTime() + "_"
								+ fileDetail.getFileName();
						String tramiteFolder = context.getRealPath("") + File.separator + "uploaded"
								+ File.separator + idtramite;
						File dir = new File(tramiteFolder);
						dir.setReadable(true);
						if (!dir.exists() || !dir.isDirectory()) {
							logger.info("============> Se creo folder:"
									+ tramiteFolder);
							FileUtils.forceMkdir(dir);
						}
						uploadedFileLocation = tramiteFolder
								+ File.separator + nuevoNombre;
						b = NotariaUtils.writeToFile(uploadedInputStream,
								uploadedFileLocation);
					}
					if (b) {
						System.out.println("file detail "+fileDetail.getFileName());
						if(uploadedFileLocation!=null && fileDetail.getFileName() != null){
							registroRi.setDsruta(uploadedFileLocation);
						}
						/**
						 * registrar ri y actualizar compareciente con el registro
						 * ri
						 **/
						Boolean registroOk = registroRiBo.registrarRi(registroRi,
								idcompareciente);
						if (!registroOk) {
							respuesta.setExito(false);
							respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
							return respuesta;
						}
						/** registrar en bitacora, el registro de compareciente **/
						bitacoraGeneralHelper.registrarEnBitacora(idusuario,
								idexpediente, null, "RegistroRi",
								Constantes.OPERACION_REGISTRO,
								"Se guarda un registro ri.");
						respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						return respuesta;
					} else {
						uploadedInputStream = null;
						respuesta.setExito(false);
						return respuesta;
					}
				}

			} else {
				respuesta.setExito(false);
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				return respuesta;
			}
		} catch(NotariaException e){
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
	}
}