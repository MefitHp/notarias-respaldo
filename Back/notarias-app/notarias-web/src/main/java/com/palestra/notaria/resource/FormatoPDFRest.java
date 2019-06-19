package com.palestra.notaria.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.palestra.notaria.bo.FormatoPDFBO;
import com.palestra.notaria.bo.impl.FormatoPDFBOImpl;
import com.palestra.notaria.dao.FormatoPDFDao;
import com.palestra.notaria.dao.impl.FormatoPDFDaoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.FormatoPDFDetalleEnvio;
import com.palestra.notaria.envio.FormatoPDFDetallesEnvio;
import com.palestra.notaria.envio.FormatoPDFEnvio;
import com.palestra.notaria.envio.FormatoPDFListaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.modelo.FormatoPDFDetalle;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("/formatoPDF")
public class FormatoPDFRest {

	Logger logger = Logger.getLogger(FormatoPDFRest.class);
	
	public FormatoPDFRest() {}

	/**
	 * Metodo para guardar formatoPDF con su detalle
	 * @param datoEnvio Wrapper que viene desde pantalla y es asignado por jersey
	 * @return FormatoPDFEnvio
	 * @throws NotariaException
	 */
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public FormatoPDFEnvio guardar(FormatoPDFEnvio datoEnvio){

		FormatoPDFEnvio respuesta = new FormatoPDFEnvio();

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getFormatoPdf() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			try{
				FormatoPDFBO bo = new FormatoPDFBOImpl();
				datoEnvio.getFormatoPdf().setIdentificador(
						GeneradorId.generaId(datoEnvio));
				datoEnvio.getFormatoPdf().setIdsesion(
						datoEnvio.getUsuario().getIdsesionactual());
				for(FormatoPDFDetalle detalle:datoEnvio.getFormatoPdf().getDetalleList()){
					detalle.setIdftopdf(datoEnvio.getFormatoPdf());
				}
				bo.save(datoEnvio.getFormatoPdf());
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	/**
	 * Metodo que actualiza el formatoPDF
	 * 
	 * @param datoEnvio
	 * @return
	 * @throws NotariaException
	 */
	@POST
	@Path("/actualizar")
	@Produces(MediaType.APPLICATION_JSON)
	public FormatoPDFEnvio actualizar(FormatoPDFEnvio datoEnvio){

		FormatoPDFEnvio respuesta = new FormatoPDFEnvio();

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getFormatoPdf() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
			.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			try{
				FormatoPDFBO bo = new FormatoPDFBOImpl();
	//			datoEnvio.getFormatoPdf().setIdentificador(
	//					GeneradorId.generaId(datoEnvio));
				datoEnvio.getFormatoPdf().setIdsesion(
						datoEnvio.getUsuario().getIdsesionactual());
				
				//FormatoPDF pdf = bo.findById(datoEnvio.getFormatoPdf().getIdentificador());
				FormatoPDFDao dao = new FormatoPDFDaoImpl();
				FormatoPDF pdf = dao.findById(datoEnvio.getFormatoPdf().getIdentificador());
				logger.info("=====> findById::: "+ pdf.getIdentificador());
				dao.eliminaDetalles(pdf.getIdentificador());
				logger.info("=====> eliminoDetalles de ::: "+ pdf.getIdentificador());

				logger.info("=====> FormatoPDFDetalle::: pdf detalle list "+pdf.getDetalleList().size());
				for(FormatoPDFDetalle detalle:datoEnvio.getFormatoPdf().getDetalleList()){
					logger.info("=====> FormatoPDFDetalle::: agregando detalle "+detalle.getIdentificador()+"-"+detalle.getDscampo());
					detalle.setIdftopdf(datoEnvio.getFormatoPdf());
				}
				pdf.setDetalleList(datoEnvio.getFormatoPdf().getDetalleList());
				pdf.setDsdescripcion(datoEnvio.getFormatoPdf().getDsdescripcion());
				pdf.setDsruta(datoEnvio.getFormatoPdf().getDsruta());
				pdf.setDstitulo(datoEnvio.getFormatoPdf().getDstitulo());
				pdf.setIsgestionado(datoEnvio.getFormatoPdf().getIsgestionado());
				pdf.setIspagorequire(datoEnvio.getFormatoPdf().getIspagorequire());
				pdf.setTipodoc(datoEnvio.getFormatoPdf().getTipodoc());				
				pdf.setTipoalerta(datoEnvio.getFormatoPdf().getTipoalerta());
				pdf.setIsonline(datoEnvio.getFormatoPdf().getIsonline());
				pdf.setDsruta(datoEnvio.getFormatoPdf().getDsruta());
				bo.update(pdf);
				logger.info("=====> FormatoPDFDetalle::: pdf detalle list se actualizo "+pdf.getDetalleList().size());
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
		} else {
			datoEnvio.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
			datoEnvio.setExito(false);
			return datoEnvio;
		}
	}

	@Path("/eliminar")
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public FormatoPDFEnvio eliminar(FormatoPDFEnvio datoEnvio)
			throws NotariaException {

		FormatoPDFEnvio respuesta = new FormatoPDFEnvio();

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getFormatoPdf() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			FormatoPDFBO bo = new FormatoPDFBOImpl();

			datoEnvio.getFormatoPdf().setIdsesion(
					datoEnvio.getUsuario().getIdsesionactual());
//			bo.eliminaDetalles(datoEnvio.getFormatoPdf().getIdentificador());
			bo.delete(datoEnvio.getFormatoPdf());
			datoEnvio.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			datoEnvio.setExito(true);
			return datoEnvio;
		} else {
			datoEnvio.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			datoEnvio.setExito(false);
			return datoEnvio;
		}
	}
/**
 * Metodo que lista indiscriminadamente los formatosPDF sean previos o posteriores
 * 
 * @param datoEnvio
 * @return
 * @throws NotariaException
 */
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public FormatoPDFListaEnvio listar(FormatoPDFListaEnvio datoEnvio)
			throws NotariaException {

		FormatoPDFListaEnvio respuesta = new FormatoPDFListaEnvio();

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			FormatoPDFBO bo = new FormatoPDFBOImpl();
			for(FormatoPDF formato:bo.findAll()){
				if(formato.getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_PREVIO)){
					datoEnvio.getPrevios().add(formato);
				}else{
					datoEnvio.getPosteriores().add(formato);
				}
			}
			datoEnvio.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			datoEnvio.setExito(true);
			return datoEnvio;
		} else {
			datoEnvio.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			datoEnvio.setExito(false);
			return datoEnvio;
		}
	}

	@Path("/detalles")
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public FormatoPDFDetallesEnvio detalles(FormatoPDFDetallesEnvio datoEnvio)
			throws NotariaException {
		FormatoPDFDetallesEnvio respuesta = new FormatoPDFDetallesEnvio();

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getFormato() == null
				|| datoEnvio.getFormato().getIdentificador().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
//			FormatoPDFBO bo = new FormatoPDFBOImpl();
//			datoEnvio.setDetalles(bo.listarFormatoDetalle(datoEnvio
//					.getFormato().getIdentificador()));
			datoEnvio.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			datoEnvio.setExito(true);
			return datoEnvio;
		} else {
			datoEnvio.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			datoEnvio.setExito(false);
			return datoEnvio;
		}
	}

	@Path("/detalles/agregar")
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public FormatoPDFDetallesEnvio detallesAgregar(
			FormatoPDFDetallesEnvio datoEnvio) throws NotariaException {
		FormatoPDFDetallesEnvio respuesta = new FormatoPDFDetallesEnvio();

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getFormato() == null
				|| datoEnvio.getFormato().getIdentificador().isEmpty()
				|| datoEnvio.getDetalles() == null
				|| datoEnvio.getDetalles().size()<1) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
//			FormatoPDFBO bo = new FormatoPDFBOImpl();
//			datoEnvio.setDetalles(bo.agregarDetalle(datoEnvio.getDetalles()));
			datoEnvio.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			datoEnvio.setExito(true);
			return datoEnvio;
		} else {
			datoEnvio.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			datoEnvio.setExito(false);
			return datoEnvio;
		}
	}

	@Path("/detalles/insertar")
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public FormatoPDFDetalleEnvio detallesInsertar(
			FormatoPDFDetalleEnvio datoEnvio) throws NotariaException {
		FormatoPDFDetalleEnvio respuesta = new FormatoPDFDetalleEnvio();

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getDetalle() == null
//				|| datoEnvio.getDetalle().getIdftopdf()==null
//				|| datoEnvio.getDetalle().getIdftopdf().getIdentificador()==null
//				|| datoEnvio.getDetalle().getIdftopdf().getIdentificador().isEmpty()
				) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
//			FormatoPDFBO bo = new FormatoPDFBOImpl();
//			datoEnvio.setDetalles(bo.agregarDetalle(datoEnvio.getDetalle()));
			datoEnvio.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			datoEnvio.setExito(true);
			return datoEnvio;
		} else {
			datoEnvio.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			datoEnvio.setExito(false);
			return datoEnvio;
		}
	}
	
	/**
	 * Metodo para cargar un pdf que será un previo o posterior y se guardarán en la carpeta ftos
	 * 
	 * @param fileInputStream
	 * @param body
	 * @param contentDispositionHeader
	 * @param idsesionactual
	 * @param idusuario
	 * @param idformatopdf
	 * @param tipodoc
	 * @param context
	 * @return
	 * @throws NotariaException
	 */
	@POST
	@Path("/upload")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public FormatoPDFDetalleEnvio upload(
			@FormDataParam("file") InputStream fileInputStream, 
			@FormDataParam("file") FormDataBodyPart body,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader,
			@FormDataParam("idsesionactual")String idsesionactual, 
			@FormDataParam("idusuario")String idusuario,
			@FormDataParam("idformatopdf")String idformatopdf,
			@FormDataParam("tipodoc")String tipodoc,
			@Context ServletContext context) throws NotariaException {
		FormatoPDFDetalleEnvio respuesta = new FormatoPDFDetalleEnvio();
		if (idusuario == null || idusuario.isEmpty() || idsesionactual == null
				|| idsesionactual.isEmpty()){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		System.out.println("MEDIA TYPE "+body.getMediaType().toString());
		if (NotariaUtils.isSesionValida(idsesionactual, idusuario)) {
			if (!body.getMediaType().toString().equals("application/pdf")
					&& !body.getMediaType().toString().equals("application/msword")
					&& !body.getMediaType().toString().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
				respuesta.setEstatus("Solo se aceptan formatos .doc y pdf");
				respuesta.setExito(false);
				return respuesta;
			}
			try{
//				obtener el formatopdf por id
				FormatoPDFBO pdfBo = new FormatoPDFBOImpl();
				FormatoPDF formatoPdf = pdfBo.findById(idformatopdf);
				String carpeta=null;
				if(tipodoc.equals("Previo")){
					carpeta = Constantes.PREVIOS_HOME;
				}else if(tipodoc.equals("Posterior")){
					carpeta = Constantes.POSTERIORES_HOME;
				}else{
					respuesta.setEstatus("No existe el tipo de formato: "+tipodoc);
					respuesta.setExito(false);
					return respuesta;
				}
				File dir = new File(carpeta);
				dir.setReadable(true);
				if (!dir.isDirectory()) {
					FileUtils.forceMkdir(dir);
					logger.info("Se creo folder:" + carpeta);
				}
				String tipo="";
				if(body.getMediaType().toString().equals("application/pdf")){
					tipo=".pdf";
				}else if(body.getMediaType().toString().equals("application/msword")){
					tipo=".doc";
				}else if(body.getMediaType().toString().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
					tipo=".docx";
				}
				String ruta = carpeta+File.separator+formatoPdf.getDstitulo()+tipo;
				NotariaUtils.writeToFile(fileInputStream, ruta);
				formatoPdf.setDsruta(ruta);
				for(FormatoPDFDetalle detalle:formatoPdf.getDetalleList()){
					detalle.setIdftopdf(formatoPdf);
				}
				pdfBo.update(formatoPdf);
				System.out.println("RUTA *** "+ruta);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
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
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	
}
