package com.palestra.notaria.resource;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.TestimonioBo;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.TestimonioBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoTestimonio;
import com.palestra.notaria.envio.TestimonioEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Testimonio;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.NotariaUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("/testimonio")
public class TestimonioRest {

	static Logger logger = Logger.getLogger(TestimonioRest.class);
	private TestimonioBo testimonioBo = new TestimonioBoImpl();
	
	@POST
	@Path("/obtenerTestimonioPorId")
	@Produces({ "application/json", "application/xml" })
	public TestimonioEnvio obtenerTestimonioPorId(TestimonioEnvio datoEnvio) {
		TestimonioEnvio respuesta = new TestimonioEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null || datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null || datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getIdtestimonio()==null 
				|| datoEnvio.getIdtestimonio().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario().getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			
			DatoTestimonio testimonio = null;
			try {
				testimonio = this.testimonioBo.obtenerPorIdCompleto(datoEnvio.getIdtestimonio());
				respuesta.setTestimonio(testimonio.getTestimonio());
				respuesta.setListaEtapas(testimonio.getListaEtapas());
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
			} catch(NotariaException e){
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
	@Path("/aprobarEtapaTestimonio")
	@Produces({ "application/json", "application/xml" })
	public TestimonioEnvio aprobarEtapaTestimonio(TestimonioEnvio datoEnvio) {
		TestimonioEnvio respuesta = new TestimonioEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null || datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null || datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getIdreletapatesti()==null 
				|| datoEnvio.getIdreletapatesti().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario().getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			try {
				boolean b = this.testimonioBo.aprobarEtapa(datoEnvio.getIdreletapatesti(), datoEnvio.getUsuario().getIdusuario());
				if(b)
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				else
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			} catch(NotariaException e){
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
	@Path("/guardarTestimonioConArchivos")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public TestimonioEnvio guardarTestimonio(
			@FormDataParam("fileRutaEscritura") InputStream uploadedInputStreamRE,
			@FormDataParam("fileRutaEscritura") FormDataContentDisposition fileDetailRE,
			@FormDataParam("fileRutaEscritura") FormDataBodyPart bodyRE,
			@FormDataParam("fileRutaCaratula") InputStream uploadedInputStreamRC,
			@FormDataParam("fileRutaCaratula") FormDataContentDisposition fileDetailRC,
			@FormDataParam("fileRutaCaratula") FormDataBodyPart bodyRC,
			@FormDataParam("fileCodigoBarras") InputStream uploadedInputStreamCB,
			@FormDataParam("fileCodigoBarras") FormDataContentDisposition fileDetailCB,
			@FormDataParam("fileCodigoBarras") FormDataBodyPart bodyCB,
			@FormDataParam("idsesionactual") String idsesionactual,
			@FormDataParam("idusuario") String idusuario,
			@FormDataParam("isgenerado") Boolean isgenerado,
			@FormDataParam("idexpediente") String idexpediente,
			@FormDataParam("idescritura") String idescritura,
			@Context HttpServletRequest request,
			@Context ServletContext context) {
		
		logger.info("========> Escritura media type: " + bodyRE.getMediaType());
		logger.info("========> Caraturla media type: " + bodyRC.getMediaType() );
		logger.info("========> CodigoBarras media type: " + bodyCB.getMediaType() );
		
		
		TestimonioEnvio respuesta = new TestimonioEnvio();
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		respuesta.setErrores(listaErrores);
		CodigoError error;
		if (idusuario == null || idusuario.isEmpty() || idsesionactual == null
				|| idsesionactual.isEmpty() || idexpediente == null
				|| idexpediente.isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (uploadedInputStreamRE == null || uploadedInputStreamRC == null
				|| uploadedInputStreamCB == null) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E05B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E05B1);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if (isgenerado == null) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E05B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E05B1);
			respuesta.setEstatus("El campo isgenerado es requerido");
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if (!bodyRE.getMediaType().toString().equals("application/pdf")
				&& !bodyRE.getMediaType().toString().equals("image/jpeg")
				&& !bodyRE.getMediaType().toString().equals("image/png")) {
			respuesta.setEstatus("El archivo escritura no es imagen o pdf");
			respuesta.setExito(false);
			return respuesta;
		}
		if (!bodyRC.getMediaType().toString().equals("application/pdf")
				&& !bodyRC.getMediaType().toString().equals("image/jpeg")
				&& !bodyRC.getMediaType().toString().equals("image/png")) {
			respuesta.setEstatus("El archivo carátula no es imagen o pdf");
			respuesta.setExito(false);
			return respuesta;
		}
		if (!bodyCB.getMediaType().toString().equals("application/pdf")
				&& !bodyCB.getMediaType().toString().equals("image/jpeg")
				&& !bodyCB.getMediaType().toString().equals("image/png")) {
			respuesta
					.setEstatus("El archivo de código de barras no es imagen o pdf");
			respuesta.setExito(false);
			return respuesta;
		}
		if (!respuesta.isExito()) {
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(idsesionactual, idusuario)) {
			try {
				/** instanciar Bo **/
				ExpedienteBo expedienteBo = new ExpedienteBoImpl();
				EscrituraBo escrituraBo = new EscrituraBoImpl();
				// TODO
				// Testimonio testimonio = datoEnvio.getTestimonio();
				Testimonio testimonio = new Testimonio();
				testimonio.setIsgenerado(isgenerado);
				Escritura escritura = new Escritura();
				escritura.setIdescritura(idescritura);
				testimonio.setEscritura(escritura);
				String idnotario = escrituraBo.obtenerIdNotarioPorEscrituraId(idescritura);
				if (idnotario == null || idnotario.isEmpty()) {
					respuesta.setEstatus("No se pudo obtener notario");
					respuesta.setExito(false);
					return respuesta;
				}
				Usuario notario = new Usuario();
				notario.setIdusuario(idusuario);
				testimonio.setNotario(notario);
				Usuario usuarioelaboro = new Usuario();
				usuarioelaboro.setIdusuario(idusuario);
				testimonio.setUsuarioelaboro(usuarioelaboro);
				Date fecha = new Date();
				Expediente exp = new Expediente();
				exp.setIdexpediente(idexpediente);
				// TODO:
				/** Obtener el tramite por el expediente dado **/
				String idtramite = expedienteBo
						.obtenerTramitePorExpedienteId(idexpediente);
				if (idtramite == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_NO_EXISTE_TRA);
					respuesta.setExito(false);
					return respuesta;
				}
				String tramiteFolder = context.getRealPath("")+File.separator+"uploaded" + File.separator
						+ idtramite;
				Boolean b = false;

				String nombreFileEscritura = fecha.getTime() + "_"
						+ fileDetailRE.getFileName();
				testimonio.setDsrutaescritura(tramiteFolder + File.separator
						+ nombreFileEscritura);
				b = NotariaUtils.guardarArchivoServidor(tramiteFolder,
						nombreFileEscritura, uploadedInputStreamRE);
				if (!b) {
					uploadedInputStreamRE = null;
					respuesta.setExito(false);
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_SUBIR_DOC);
					return respuesta;
				}

				String nombreFileCaratula = fecha.getTime() + "_"
						+ fileDetailRC.getFileName();
				testimonio.setDsrutacaratula(tramiteFolder + File.separator
						+ nombreFileCaratula);
				b = NotariaUtils.guardarArchivoServidor(tramiteFolder,
						nombreFileCaratula, uploadedInputStreamRC);
				if (!b) {
					uploadedInputStreamRE = null;
					respuesta.setExito(false);
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_SUBIR_DOC);
					return respuesta;
				}
				String nombreCodigoBarras = fecha.getTime() + "_"
						+ fileDetailCB.getFileName();
				testimonio.setDscodigobarras(tramiteFolder + File.separator
						+ nombreCodigoBarras);
				b = NotariaUtils.guardarArchivoServidor(tramiteFolder,
						nombreCodigoBarras, uploadedInputStreamCB);
				if (!b) {
					uploadedInputStreamRE = null;
					respuesta.setExito(false);
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_SUBIR_DOC);
					return respuesta;
				}
				boolean aux = this.testimonioBo.registrarTestimonio(testimonio,
						idexpediente, idusuario, idsesionactual);
				if (aux) {
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				} else {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				}
			} catch(NotariaException e){
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
	@Path("/subirArchivoTemporal")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response subirTestimonio(
			@FormDataParam("file") InputStream uploadedInputStream,@FormDataParam("file") FormDataContentDisposition fileDetail,
			@QueryParam("idsesion") String idsesion, @QueryParam("idusuario") String idusuario) {
		if (idsesion == null 
				|| idsesion.isEmpty()
				|| idusuario == null
				|| idusuario.isEmpty()) {
			return Response.ok(Constantes.ESTATUS_FALTAN_PARAMETROS).build();
		}
		if (NotariaUtils.isSesionValida(idsesion, idusuario)) {
			Date fecha = new Date();
			String uploadedFileLocation = Constantes.RUTA_TEMPORAL
					+ fecha.getTime() + "_" + fileDetail.getFileName();

			boolean b = NotariaUtils.writeToFile(uploadedInputStream,
					uploadedFileLocation);

			if (b) {
				uploadedInputStream = null;
				return Response.status(200).entity(uploadedFileLocation).build();
			} else {
				uploadedInputStream = null;
				return Response.status(200).entity("").build();
			}
		}else{
			return Response.status(200).entity(Constantes.ESTATUS_SESION_INVALIDA).build();
		}
	}
	

}
