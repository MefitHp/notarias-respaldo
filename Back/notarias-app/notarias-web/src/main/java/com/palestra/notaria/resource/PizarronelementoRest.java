package com.palestra.notaria.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
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

import org.apache.commons.io.IOUtils;

import com.palestra.notaria.bo.AvisoDecenaFoliosBo;
import com.palestra.notaria.bo.ControlFoliosBo;
import com.palestra.notaria.bo.LibroBo;
import com.palestra.notaria.bo.PizarronElementoBo;
import com.palestra.notaria.bo.impl.AvisoDecenaFoliosBoImpl;
import com.palestra.notaria.bo.impl.ControlFoliosBoImpl;
import com.palestra.notaria.bo.impl.LibroBoImpl;
import com.palestra.notaria.bo.impl.PizarronElementoBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoAvisoDecena;
import com.palestra.notaria.dato.DatoElementoPizarron;
import com.palestra.notaria.envio.DocumentoEnvio;
import com.palestra.notaria.envio.PizarronElementoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AvisoDecena;
import com.palestra.notaria.modelo.ControlFolios;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.util.NotariaUtils;
import com.palestra.notarias.enums.EnumVariables;
import com.palestra.notarias.escritura.DocumentoAvisoDecenaGenerador;
import com.palestra.notarias.escritura.FillTemplateWithData;
import com.palestra.notarias.service.OpenOfficeService;
import com.palestra.notarias.utils.ArchivoUtils;
import com.palestra.notarias.utils.EscrituraUtills;
import com.palestra.notarias.utils.VariableUtils;
import com.palestra.notarias.variables.TokenExtractor;
import com.palestra.notarias.variables.VariableToTokenTransformer;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("/pizarron")
public class PizarronelementoRest {

	PizarronElementoBo pizarronBo;
	public PizarronelementoRest() {
		pizarronBo = new PizarronElementoBoImpl();
		
	}
	
	@POST
	@Path("/listarnumerosxabogado")
	@Produces(MediaType.APPLICATION_JSON)
	public PizarronElementoEnvio listarnumerosxabogado(PizarronElementoEnvio pizenv){
		
		PizarronElementoEnvio respuesta = new PizarronElementoEnvio();
		if (pizenv == null || pizenv.getUsuario() == null
				|| pizenv.getUsuario().getIdusuario() == null || pizenv.getUsuario().getIdusuario().isEmpty()
				|| pizenv.getUsuario().getIdsesionactual() == null || pizenv.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pizenv.getUsuario().getIdsesionactual(),pizenv.getUsuario().getIdusuario())){
			try{
				int inicioelementos =0;
				int totalelementos =0;
				if(pizenv.getDecena()!=null){
					inicioelementos=pizenv.getDecena();
					totalelementos=60;
				}
				ArrayList<DatoElementoPizarron> pizarronesPendientes = (ArrayList<DatoElementoPizarron>) pizarronBo.buscarpendientes(pizenv.getIdabogado());
				respuesta.setPendientes(pizarronesPendientes);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public PizarronElementoEnvio listar(PizarronElementoEnvio pizenv){
		
		PizarronElementoEnvio respuesta = new PizarronElementoEnvio();
		if (pizenv == null || pizenv.getUsuario() == null
				|| pizenv.getUsuario().getIdusuario() == null || pizenv.getUsuario().getIdusuario().isEmpty()
				|| pizenv.getUsuario().getIdsesionactual() == null || pizenv.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pizenv.getUsuario().getIdsesionactual(),pizenv.getUsuario().getIdusuario())){
			try{
				int inicioelementos =0;
				int totalelementos =0;
				
				if(pizenv.getDecena()!=null){
					inicioelementos=pizenv.getDecena();
					totalelementos=60;
				}
				
				ArrayList<DatoElementoPizarron> pizarrones = (ArrayList<DatoElementoPizarron>) pizarronBo.buscartodos(inicioelementos,totalelementos);
				ArrayList<DatoElementoPizarron> pizarronesPendientes = (ArrayList<DatoElementoPizarron>) pizarronBo.buscarpendientes(null);
				int decena = pizarronBo.calculadecena(pizarrones);
				ControlFoliosBo controlFoliosBo = new ControlFoliosBoImpl();
				ControlFolios controlFolio = controlFoliosBo.getUltimo();
				
				LibroBo libroBo = new LibroBoImpl();
				Libro libro = libroBo.obtenUltimoLibro();
				
				respuesta.setUltimafecha(pizarronBo.getUltomaFecha());
				respuesta.setDecena(decena);
				respuesta.setLibro(libro);       
				respuesta.setControlfolios(controlFolio);
				Collections.reverse(pizarrones);
				respuesta.setPizarrones(pizarrones);
				respuesta.setPendientes(pizarronesPendientes);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	
	/*
	 * @Param tipoaviso:Para poder descargar el aviso se debe ingresar el tipo de aviso
	 * @Param libroapertura:Para poder descargar el aviso se debe ingresar el libro inicial 
	 * http://localhost:8080/notarias-web/notaria/pizarron/descargaraviso?idsesionactual=2F4347EE30EFEF5E35586959658051D2&idusuario=9e722bee5a0315310b8dfa4515e8f810&nombreUsuario=Carlos%20Cer%C3%B3n%20Nacif&tipoaviso=avisodecena&libroapertura=1911
	 * */
	@GET
	@Path("/descargaraviso")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response descargarAviso(
			@QueryParam("tipoaviso") String tipoaviso,
			@QueryParam("libroapertura") String libroapertura,	
			@Context HttpServletResponse response) {

		if (	tipoaviso == null || tipoaviso.isEmpty()
				|| libroapertura == null || libroapertura.isEmpty()) {

			Response.ok(Constantes.ESTATUS_FALTAN_PARAMETROS).build();
		}

			try {
				String urlDoc = Constantes.DOCGENERALES_HOME+"/tmp/";
				String nombre = "";
				if(tipoaviso.equals("razoncierre")){
					 nombre = tipoaviso+"_"+((Integer.parseInt(libroapertura)-9)+"_al_"+libroapertura);
				}else{
					 nombre = tipoaviso+"_"+libroapertura+"_al_"+(Integer.parseInt(libroapertura)+9);
				}
				try {
				
					FillTemplateWithData fillTemplate = new FillTemplateWithData();
					String htmlText = null;
					Reader reader = null;
					try {
						
						htmlText = NotariaUtils.recuperaDoctoString(Constantes.DOCGENERALES_HOME+"/"+tipoaviso);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

					
					try {
						TokenExtractor tokenExtractor = new TokenExtractor();
						String[] vars = tokenExtractor.obtenTokensPlantilla(htmlText, EnumVariables.VAR_PREFIX.toString(), EnumVariables.VAR_SUFIX.toString());
						VariableToTokenTransformer vt = new VariableToTokenTransformer();
						HashMap<String, String> valores = new HashMap<>();
						if(vars!=null && vars.length>0){
							
							DocumentoAvisoDecenaGenerador dag = new DocumentoAvisoDecenaGenerador();
							for(String var : vars){
								String variable =  VariableUtils.getNombreVariable(var);
								Method variablemethod = dag.getClass().getMethod(variable.toString(), Long.class);
								Long libroaperturaLong= Long.parseLong(libroapertura, 10);
								String valor  = (String)variablemethod.invoke(dag, libroaperturaLong);
								String funcion =  VariableUtils.obtenDefinicionFuncion(var);
								valor = (funcion!=null && !funcion.isEmpty())?vt.evaluaFuncionVariable(funcion, valor):valor;
								if(!valor.isEmpty()){
									valores.put(var, valor);
								}
							}
							System.out.println(valores);
						}
						reader = fillTemplate.remplazaTokensXValor(valores,htmlText);
					} catch (NotariaException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					if (reader != null) {
						System.out.println("********************************");
						try {
							htmlText = IOUtils.toString(reader);
							htmlText = EscrituraUtills.replaceMarkerLetra(htmlText);
							NotariaUtils.guardarArchivoServidor(urlDoc,nombre + ".html", IOUtils.toInputStream(htmlText, "UTF-8"));
							OpenOfficeService officeHelper = new OpenOfficeService();
							officeHelper.convertFile(urlDoc+nombre + ".html", urlDoc+nombre+".doc");
							NotariaUtils.eliminarArchivo(urlDoc+nombre + ".html");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(htmlText);
						System.out.println("********************************");
					}
					
				
				} catch (Exception e) {
					e.printStackTrace();
					throw new NotariaException(
							"Algo malo paso al generar el documento ",
							e.getCause());
				}

				NotariaUtils.recuperaDocWord(urlDoc+nombre+".doc", response,nombre+".doc");
				
			} catch (NotariaException e) {
				Response.status(200).entity(
						"Algo malo pasó al generar el documento:"
								+ e.getMessage());
			} catch (Exception e) {
				Response.status(200).entity(
						"Algo malo pasó al recuperar el documento:"
								+ e.getMessage());
			}

		
		return null;
	}
	
	
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public PizarronElementoEnvio guardar(PizarronElementoEnvio pizenv){
		PizarronElementoEnvio respuesta = new PizarronElementoEnvio();

		try {
			validaPassword(pizenv.getPassword());
		} catch (NotariaException e1) {
			
			respuesta.setEstatus(e1.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
		if (pizenv == null || pizenv.getPizarron() ==null || pizenv.getUsuario() == null
				|| pizenv.getUsuario().getIdusuario() == null || pizenv.getUsuario().getIdusuario().isEmpty()
				|| pizenv.getUsuario().getIdsesionactual() == null || pizenv.getUsuario().getIdsesionactual().isEmpty()
				|| pizenv.getPizarron().getFecha()==null || pizenv.getPizarron().getCantidadfolios()==null 
				|| pizenv.getPizarron().getNumeroescritura()==null
				) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pizenv.getUsuario().getIdsesionactual(),pizenv.getUsuario().getIdusuario())){
			try{
				DatoElementoPizarron pizarron = pizenv.getPizarron();
				if(pizenv.getPizarron().getIdpizarronelemento()==null){
					pizarronBo.validaFecha(pizarron.getFecha());
				}
				
				pizarronBo.save(pizarron,pizenv.getActualizatodo(),pizenv.getUsuario());
				
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	@Path("/borrar")
	@Produces(MediaType.APPLICATION_JSON)
	public PizarronElementoEnvio borrar(PizarronElementoEnvio pizenv){
		
		PizarronElementoEnvio respuesta = new PizarronElementoEnvio();
		if (pizenv == null || pizenv.getPizarron() ==null || pizenv.getUsuario() == null
				|| pizenv.getUsuario().getIdusuario() == null || pizenv.getUsuario().getIdusuario().isEmpty()
				|| pizenv.getUsuario().getIdsesionactual() == null || pizenv.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pizenv.getUsuario().getIdsesionactual(),pizenv.getUsuario().getIdusuario())){
			try{
				if(pizenv.getPizarron().isIscierrelibro()){
					respuesta.setEstatus("El elemento corresponde al cierre de libro y no es posible eliminarlo");
					respuesta.setExito(false);
					return respuesta;
				}
				
				
				pizarronBo.borrar(pizenv.getPizarron().getIdpizarronelemento(),pizenv.getPizarron().getNumeroescritura());
				
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	
	/*
	 *OBTIENE EL HISTORICO DE LOS AVISOS DECENA 	 
	 */
	@POST
	@Path("/avisosdecena")
	@Produces(MediaType.APPLICATION_JSON)
	public PizarronElementoEnvio avisosdecena(PizarronElementoEnvio pizenv){
		
		PizarronElementoEnvio respuesta = new PizarronElementoEnvio();
		if (pizenv == null || pizenv.getUsuario() == null
				|| pizenv.getUsuario().getIdusuario() == null || pizenv.getUsuario().getIdusuario().isEmpty()
				|| pizenv.getUsuario().getIdsesionactual() == null || pizenv.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pizenv.getUsuario().getIdsesionactual(),pizenv.getUsuario().getIdusuario())){
			try{
				
				AvisoDecenaFoliosBo avisodecenaBo = new AvisoDecenaFoliosBoImpl();
				List<DatoAvisoDecena> avisos = avisodecenaBo.getAll();
				respuesta.setAvisos(avisos);
				
			}catch(NotariaException e){
				e.printStackTrace(System.out);
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
	@Path("/subirevidencia")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoEnvio subirArchivoOriginal(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("file") FormDataBodyPart body,
			@FormDataParam("idsesionactual") String idsesionactual,
			@FormDataParam("idusuario") String idusuario,
			@FormDataParam("idavisodecena") String idavisodecena,
			@FormDataParam("tipo") String tipo,
			@Context ServletContext context) {

		DocumentoEnvio respuesta = new DocumentoEnvio();
		AvisoDecenaFoliosBo avisoBo = new AvisoDecenaFoliosBoImpl();
		AvisoDecena avisodecena;
		try {
			avisodecena = avisoBo.findById(idavisodecena);
		} catch (NotariaException e2) {
			respuesta.setEstatus("El aviso solicitado no fué localizado");
			respuesta.setExito(false);
			return respuesta;
		}
		if (idsesionactual == null || idsesionactual.isEmpty()
				|| idusuario == null || idusuario.isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(idsesionactual, idusuario)) {
			//try {
				if (!body.getMediaType().toString().equals("application/pdf")
						&& !body.getMediaType().toString().equals("image/jpeg")
						&& !body.getMediaType().toString().equals("image/png")) {
					respuesta.setEstatus("El formato no es imagen o pdf");
					respuesta.setExito(false);
					return respuesta;
				}
				Long l = avisodecena.getLibroapertura().getInnumlibro();
				String urlsave="avisos"+File.separator+l+"_"+(l+9);
				try {
				urlsave = ArchivoUtils.checkAndCreateDir(urlsave);
				} catch (NotariaException e1) {
					respuesta.setEstatus(e1.getMessage());
					respuesta.setExito(false);
					return respuesta;
				}
				String ext = body.getMediaType().toString();
						ext = ext.substring(ext.indexOf("/")+1,ext.length());
				String uploadedFileLocation = urlsave +File.separator+tipo+"."+ext;

				boolean b = NotariaUtils.writeToFile(uploadedInputStream,uploadedFileLocation);
			
				
				
				if (b) {
					
					try {
						switch (tipo) {
						case "apertura":
							avisodecena.setUrlDocApertura(uploadedFileLocation);
							break;
						case "cierre":
							avisodecena.setUrlDocCierre(uploadedFileLocation);
							break;
						case "notificacion":
							avisodecena.setUrlDocAviso(uploadedFileLocation);
							break;
						default:
							throw new NotariaException();
						}
						
						avisoBo.update(avisodecena);
						
					} catch (NotariaException e) {
						NotariaUtils.eliminarArchivo(uploadedFileLocation);
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_SUBIR_DOC);
						respuesta.setExito(false);
						return respuesta;
					}
					
					/**
					 * Cuando suben un archivo se guarda el campo 'fechaEntrega'
					 **/
				
					respuesta.setRutaArchivoDescarga(uploadedFileLocation);
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
				} else {
					uploadedInputStream = null;
					respuesta.setRutaArchivoDescarga("");
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
	@Path("/descargarDocumento")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response descargarDocumento(@QueryParam("idavisodecena") String idavisodecena,
			@QueryParam("tipoaviso") String tipo, @Context ServletContext context,
			@Context HttpServletResponse response) {
		if (idavisodecena == null || idavisodecena.isEmpty()) {
			Response.ok(Constantes.ESTATUS_FALTAN_PARAMETROS).build();
		}
 			String archivo=null;
			try {
				AvisoDecenaFoliosBoImpl avisobo = new AvisoDecenaFoliosBoImpl();
				AvisoDecena aviso = avisobo.findById(idavisodecena);
				if(aviso ==null){
					throw new NotariaException();
				}
				switch (tipo) {
					case "apertura":
						archivo = aviso.getUrlDocApertura();
						break;
					case "cierre":
						archivo = aviso.getUrlDocCierre();
						break;
					case "notificacion":
						archivo = aviso.getUrlDocAviso();
						break;
					default:
						break;
				}
				NotariaUtils.recuperaDoc(archivo, response);
 
				
			} catch (NotariaException e) {
				e.printStackTrace();
				Response.status(200).entity(Constantes.ESTATUS_ERROR_CONSULTA).build();
			
			}catch(Exception e1){
				e1.printStackTrace();
				Response.status(200).entity(Constantes.ESTATUS_ERROR_CONSULTAR_ARCHIVO).build();
			}
		return null;
	}
	
	public void validaPassword(String password)throws NotariaException{
		String ditopass = NotariaUtils.getProperties("dito.password");
		if(ditopass.isEmpty() || password==null || password.isEmpty()){
				throw new NotariaException("No existe un password registrado, notifique a sistemas");
		}else{
			if(!password.equals(ditopass)){
				throw new NotariaException("Password o usuario no válido, notifique a sistemas");
			}
		}
	}
	
	

}
