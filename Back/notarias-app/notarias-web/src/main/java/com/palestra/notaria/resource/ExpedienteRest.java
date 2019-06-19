package com.palestra.notaria.resource;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.TramiteUsuarioBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.FormularioBoImpl;
import com.palestra.notaria.bo.impl.TramiteBoImpl;
import com.palestra.notaria.bo.impl.TramiteUsuarioBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoActo;
import com.palestra.notaria.dato.DatoCompareciente;
import com.palestra.notaria.dato.DatoOperacion;
import com.palestra.notaria.envio.ComparecienteEnvio;
import com.palestra.notaria.envio.ExpedienteEnvio;
import com.palestra.notaria.envio.OperacionTarjetonEnvio;
import com.palestra.notaria.envio.TramiteEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notaria.modelo.EnumEstatus;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.ValorFormulario;
import com.palestra.notaria.modelo.ValorSubFormulario;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

import pojos.pojos.BonitaCommonBean;


@Path("/expediente")
public class ExpedienteRest {

	static Logger logger = Logger.getLogger(ExpedienteRest.class);

	private ExpedienteBo expedienteBo;
	
	private BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();

	// private OperacionBo operacionBo = new OperacionBoImpl();
	// private DocumentoBo documentoBo = new DocumentoBoImpl();

	public ExpedienteRest() {
		expedienteBo = new ExpedienteBoImpl();
	}

	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public ExpedienteEnvio getExpedientes(ExpedienteEnvio expedienteEnvio) {
		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		if(NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
				.getIdsesionactual(), expedienteEnvio.getUsuario()
				.getIdusuario())){
			try{
				List<Expediente> listaExpedientes = new ArrayList<Expediente>();
				logger.info("======> Accede a la extraccion de expedientes");
				listaExpedientes = this.expedienteBo.expedientesListar();		
				logger.info("======> Expedientes "+ listaExpedientes.size());
				respuesta.setLista((ArrayList<Expediente>)listaExpedientes);
				respuesta.setErrores(null);
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setUsuario(expedienteEnvio.getUsuario());
				respuesta.setExito(true);
				
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR+" "+e.getMessage());
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio registraExpediente(ExpedienteEnvio expedienteEnvio,
			@Context HttpServletRequest request) {
		Date fecha = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		Usuario usuario = expedienteEnvio.getUsuario();
		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		Expediente expediente = expedienteEnvio.getExpediente();

		//List<Comentario> listaComentarios;

		if (expedienteEnvio == null
				|| expedienteEnvio.getUsuario() == null
				|| expedienteEnvio.getUsuario().getIdusuario() == null
				|| expedienteEnvio.getUsuario().getIdsesionactual() == null
				|| expedienteEnvio.getExpediente() == null
				|| expedienteEnvio.getExpediente().getTramite() == null
				|| expedienteEnvio.getExpediente().getTramite().getIdtramite() == null
				|| expedienteEnvio.getExpediente().getTramite().getIdtramite()
						.isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
				.getIdsesionactual(), expedienteEnvio.getUsuario()
				.getIdusuario())) {
			try {
				Boolean b = this.expedienteBo
						.exiteTramiteRegistrado(expedienteEnvio.getExpediente()
								.getTramite().getIdtramite());
				if (b) {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_TRAMITE_YA_ASOCIADO);
					respuesta.setExito(false);
					return respuesta;
				}
	
	
				Expediente exp;
				
				expediente.setIdsesion(usuario.getIdsesionactual());
				expediente.setFechainicial(new Timestamp(fecha.getTime()));
				expediente.setIdexpediente(GeneradorId.generaId(expediente));
				int mes = cal.get(Calendar.MONTH) + 1;
				String aux;
				if (mes < 10)
					aux = "0" + mes;
				else
					aux = "" + mes;
				Random rand = new Random();
				int nRandom = rand.nextInt(900) + 100;
				/**** VICTOR: SE COMENTA POR LA NUEVA ESTRUCTURA DEL NÚMERO DE EXPEDIENTE  */
				/*expediente.setNumexpediente(cal.get(Calendar.YEAR) + aux + "_"
						+ usuario.getDsiniciales() + nRandom);*/
				expediente.setNumexpediente(expediente.getTramite().getDsdirectorio());

				
				expediente.setEstatus(EnumEstatus.ABIERTO);
				exp = this.expedienteBo.save(expediente);

				// Registro el estatus del expediente @Omar

				respuesta.setExpediente(exp);
				respuesta.getExpediente().getTramite().getCliente().setNacionalidad(null);
				respuesta.getExpediente().getTramite().getCliente().setTipopersona(null);
				if (exp != null) {
					this.bitacoraGeneralHelper.registrarEnBitacora(
							expedienteEnvio.getUsuario().getIdusuario(),
							exp.getIdexpediente(), null, "Expediente",
							"Registrar", "Se registra un expediente");
					// se buscan comentarios
					/*for (Comentario com : listaComentarios) {
						com.setIdcomentario(GeneradorId.generaId(com));
						com.setIdsesion(usuario.getIdsesionactual());
						com.setTmstmp(new Timestamp(fecha.getTime()));
						com.setUsuario(usuario);
						com.setExpediente(exp);
						comAux = this.comentarioBo.save(com);
						if (comAux == null) {
							respuesta
									.setEstatus("No se guardaron todos los comentarios");
							respuesta.setExito(false);
						} else {
							this.bitacoraGeneralHelper
									.registrarEnBitacora(expedienteEnvio
											.getUsuario().getIdusuario(), exp
											.getIdexpediente(), null,
											"Expediente", "Registrar",
											"Se registra un comentario");
						}
					}*/
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);

				} else {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
				}
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO+" "+e.getMessage());
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
	@Path("/actualizar")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio actualizar(ExpedienteEnvio expedienteEnvio,
			@Context HttpServletRequest request) {

		Date fecha = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		Usuario usuario = expedienteEnvio.getUsuario();
		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		Expediente expediente = expedienteEnvio.getExpediente();

		//List<Comentario> listaComentarios;
		if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
				.getIdsesionactual(), expedienteEnvio.getUsuario()
				.getIdusuario())) {
			if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
					|| expedienteEnvio.getUsuario().getIdusuario() == null
					|| expedienteEnvio.getUsuario().getIdsesionactual() == null
					|| expedienteEnvio.getExpediente() == null) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}

			

			Expediente exp;
			Comentario comAux;
			expediente.setIdsesion(usuario.getIdsesionactual());

			try {
				
				exp = this.expedienteBo.update(expediente);
				if (exp != null) {
					this.bitacoraGeneralHelper.registrarEnBitacora(
							expedienteEnvio.getUsuario().getIdusuario(),
							exp.getIdexpediente(), null, "Expediente",
							"Actualizar", "Se actualiza un expediente");
					// se buscan comentarios
					/*for (Comentario com : listaComentarios) {
						// com.setIdcomentario(GeneradorId.generaId(com));
						com.setIdsesion(usuario.getIdsesionactual());
						com.setTmstmp(new Timestamp(fecha.getTime()));
						com.setUsuario(usuario);
						com.setExpediente(exp);
						comAux = this.comentarioBo.update(com);
						if (comAux == null) {
							respuesta
									.setEstatus("No se actualizaron todos los comentarios");
							respuesta.setExito(false);
						} else {
							this.bitacoraGeneralHelper
									.registrarEnBitacora(expedienteEnvio
											.getUsuario().getIdusuario(), exp
											.getIdexpediente(), null,
											"Comentario", "Actualizar",
											"Se actualiza un comentario");
						}
					}*/
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				} else {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
				}
				respuesta.setExpediente(null);

			}catch(NotariaException e){
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
	@Path("/obtenerPorId")
	@Produces({ "application/json", "application/xml" })
	public Response obtenerPorId(String id) {

		Expediente doc = null;
		try {
			doc = this.expedienteBo.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(201).entity(doc).build();

	}
	
	@POST
	@Path("/listarXusuario")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio listarXusuario(ExpedienteEnvio envio,
				@Context HttpServletRequest request) throws JSONException {
		
		ExpedienteEnvio respuesta = new ExpedienteEnvio();

		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {
			
			TramiteUsuarioBo tramiteUsuarioBo = new TramiteUsuarioBoImpl();
			try {
				ArrayList<Expediente> expedientesFound = tramiteUsuarioBo.buscarExpedientesXTramiteUsuario(envio.getUsuario());
				respuesta.setLista(expedientesFound);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(true);
				
				
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);	
			}
			
			
		}else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		
		return respuesta;
		
	}
	
	@POST
	@Path("/listarUsuariosAsociados")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio listarUsuariosAsociados(ExpedienteEnvio envio,
				@Context HttpServletRequest request) throws JSONException {
		
		ExpedienteEnvio respuesta = new ExpedienteEnvio();

		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getExpediente() == null
				|| envio.getExpediente().getTramite() == null
				|| envio.getExpediente().getTramite().getIdtramite() == null
				) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {
			
			TramiteUsuarioBo tramiteUsuarioBo = new TramiteUsuarioBoImpl();
			
			try {
				String idtramite = envio.getExpediente().getTramite().getIdtramite();
				List<Usuario> usuariosFound = tramiteUsuarioBo.buscarUsuariosXtramite(idtramite);
				respuesta.setUsuariosList(usuariosFound);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(true);
				
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);	
			}
			
			
		}else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		
		return respuesta;
		
	}
	
	@POST
	@Path("/asociarUsuario")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio asociarUsuario(ExpedienteEnvio envio,
				@Context HttpServletRequest request) throws JSONException {
		
		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getExpediente() == null
				|| envio.getExpediente().getTramite() == null
				|| envio.getExpediente().getTramite().getIdtramite() == null
				|| envio.getUsrAsoc() == null
				){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}		
		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {
			
			TramiteUsuarioBo tramiteUsuarioBo = new TramiteUsuarioBoImpl();
			Tramite tramite = envio.getExpediente().getTramite();
			Usuario usuAsoc = envio.getUsrAsoc();
			String usuExp = envio.getUsuario().getIdsesionactual();
			
			try {
				
				tramiteUsuarioBo.saveTramiteUsuario(usuAsoc,tramite,usuExp);
				
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				respuesta.setExito(true);
				
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);	
			}
			
		}else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}		
		return respuesta;		
	}
	
	
	@POST
	@Path("/desasociarUsuario")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio desasociarUsuario(ExpedienteEnvio envio,
				@Context HttpServletRequest request) throws JSONException {
		
		ExpedienteEnvio respuesta = new ExpedienteEnvio();

		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getExpediente() == null
				|| envio.getExpediente().getTramite() == null
				|| envio.getExpediente().getTramite().getIdtramite() == null
				|| envio.getUsrAsoc() == null
				) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
			if(envio.getUsrAsoc().getRol().getDsprefijo().equals("abog")){
				respuesta.setEstatus("No se puede eliminar al abogado que tiene asignado el expediente");
				respuesta.setExito(false);
				return respuesta;
			}
		
		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {
			
			Tramite tramite = envio.getExpediente().getTramite();
			Usuario usuAsoc = envio.getUsrAsoc();
			String usuExp = envio.getUsuario().getIdsesionactual();
			
			TramiteUsuarioBo tramiteUsuarioBo = new TramiteUsuarioBoImpl();
			
			try {
				tramiteUsuarioBo.deleteTramiteUsuario(usuAsoc,tramite,usuExp);
				respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
				respuesta.setExito(true);
				
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);	
			}
			
			
		}else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		
		return respuesta;
		
	}
	
	
	
	
	
	
	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio obtenerPorIdCompleto(ExpedienteEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		Expediente exp = null;
		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getExpediente() == null
				|| envio.getExpediente().getIdexpediente() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {

			try {

				exp = this.expedienteBo.buscarPorIdCompleto(envio
						.getExpediente());

				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExpediente(exp);

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
	@Path("/obtenerGeneralesExpediente")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio obtenerGeneralesExpediente(
			ExpedienteEnvio expedienteEnvio, @Context HttpServletRequest request) {

		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		Expediente expRes;
		
		if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
				|| expedienteEnvio.getUsuario().getIdusuario() == null
				|| expedienteEnvio.getUsuario().getIdsesionactual() == null
				|| expedienteEnvio.getExpediente() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
				.getIdsesionactual(), expedienteEnvio.getUsuario()
				.getIdusuario())) {
			try{
				expRes = this.expedienteBo.buscarPorIdCompleto(expedienteEnvio
						.getExpediente());
				
				respuesta.setExpediente(expRes);
				ArrayList<DatoOperacion> listaOperaciones;
				if (expRes != null) {
	
					listaOperaciones = this.expedienteBo
							.buscarOperaciones(expedienteEnvio.getExpediente()
									.getIdexpediente(), null);
					respuesta.setListaOperaciones(listaOperaciones);
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}

	@POST
	@Path("/obtenerOperacion")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio obtenerOperacion(ExpedienteEnvio expedienteEnvio,
			@Context HttpServletRequest request) {

		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
				|| expedienteEnvio.getUsuario().getIdusuario() == null
				|| expedienteEnvio.getUsuario().getIdsesionactual() == null
				|| expedienteEnvio.getExpediente() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
				.getIdsesionactual(), expedienteEnvio.getUsuario()
				.getIdusuario())) {
			try{
				ArrayList<DatoOperacion> listaOperaciones;
				listaOperaciones = this.expedienteBo.buscarOperaciones(
						expedienteEnvio.getExpediente().getIdexpediente(),
						expedienteEnvio.getIdOperacion());
				if (listaOperaciones != null && listaOperaciones.size() > 0)
					respuesta.setOperacion(listaOperaciones.get(0));
			}catch(NotariaException e){
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
	@Path("/clonarExpediente")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio clonarExpediente(ExpedienteEnvio expedienteEnvio,
			@Context HttpServletRequest request) throws JSONException {

		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		ExpedienteEnvio datos = new ExpedienteEnvio();
		if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
				|| expedienteEnvio.getUsuario().getIdusuario() == null
				|| expedienteEnvio.getUsuario().getIdsesionactual() == null
				|| expedienteEnvio.getExpediente() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
				.getIdsesionactual(), expedienteEnvio.getUsuario()
				.getIdusuario())) {
			try{
				TramiteRest tramiteRest = new TramiteRest();
				TramiteEnvio tramiteEnvio = new TramiteEnvio();
				Date fecha = new Date();
				tramiteEnvio.setUsuario(expedienteEnvio.getUsuario());
				tramiteEnvio.setTramite(expedienteEnvio.getExpediente().getTramite());
				tramiteEnvio = tramiteRest.registraTramite(tramiteEnvio, request);
				if(!tramiteEnvio.isExito()){
					respuesta.setEstatus("Ocurrió un error al registrar el trámite");
					respuesta.setExito(false);
					return respuesta;
				}
				
				Expediente expedienteClone = expedienteEnvio.getExpediente();
				// Victor: Creo el nuevo expediente
				Expediente newExpediente = new Expediente();
				Usuario user = expedienteEnvio.getUsuario();
				newExpediente.setIdsesion(user.getIdsesionactual());
				newExpediente.setAbogado(expedienteEnvio.getExpediente().getAbogado());
				newExpediente.setFechainicial(new Timestamp(fecha.getTime()));
				newExpediente.setIdexpediente(GeneradorId.generaId(newExpediente));
				newExpediente.setNumexpediente(tramiteEnvio.getDirectorio());
				Tramite tramite = new Tramite();
				tramite.setIdtramite(tramiteEnvio.getIdTramite());
				newExpediente.setTramite(tramite);
				newExpediente.setEstatus(EnumEstatus.ABIERTO);
				newExpediente.setDsreferencia(expedienteEnvio.getExpediente().getDsreferencia());
				//Fin de nuevo expediente
				expedienteBo.save(newExpediente);
				
				//Obtengo las suboperaciones del expediente
				datos.setExpediente(newExpediente);
				datos.setUsuario(user);
				expedienteEnvio = obtenerGeneralesExpediente(expedienteEnvio, request);
				
				for(DatoOperacion dop : expedienteEnvio.getListaOperaciones()){
					dop.setStatus("C");
					datos.setOperacion(dop);
					guardarOperacion(datos, request);
				}
				expedienteEnvio.setUsuario(user);
				expedienteEnvio = obtenerGeneralesExpediente(expedienteEnvio, request);
				datos = obtenerGeneralesExpediente(datos, request);
				
				ComparecienteBo cpBo = new ComparecienteBoImpl();
				List<Compareciente> listCompareciente = new ArrayList<Compareciente>();
				
				
				int iexp = 0;
				for(DatoOperacion dop:expedienteEnvio.getListaOperaciones()){
					int iact = 0;
					DatoOperacion dopDatos = datos.getListaOperaciones().get(iexp);
					for(DatoActo act : dop.getListaActos()){
						Compareciente tmpcpo = new Compareciente();
						Acto tmpact = new Acto();
						tmpact.setIdacto(act.getIdacto());
						tmpcpo.setActo(tmpact);
						ComparecienteEnvio tmpcpmEnv = new ComparecienteEnvio();
						tmpcpmEnv.setCompareciente(tmpcpo);
						tmpcpmEnv.setUsuario(user);
						ComparecienteRest tmpcompRest = new ComparecienteRest();
						tmpcpmEnv = tmpcompRest.listadoCompleto(tmpcpmEnv, request);
						
						Acto tmpActoClon = new Acto();
						tmpActoClon.setIdacto(dopDatos.getListaActos().get(iact).getIdacto());

						///OBTENGO LOS COMPARECIENTES Y LOS ASIGNO
						if(tmpcpmEnv.getComparecienteCompletos()!=null && tmpcpmEnv.getComparecienteCompletos().size()>0){
							for(DatoCompareciente compa:tmpcpmEnv.getComparecienteCompletos()){
								// VERIFICO QUE NO SEA UN AUTORIZANTE PARA QUE NO SE REPITA AL AGREGARLO COMO CONYUGE
								if(compa.getCompareciente().getTipoCompareciente().getDsnombre().equals("Autorizante")){
									continue;
								}
								
								ComparecienteEnvio tmpcompEnvio2 = new ComparecienteEnvio();
								// ASIGNO EL NUEVO ACTO A OCUPAR
								tmpcompEnvio2.setUsuario(user);
								
								compa.getCompareciente().setActo(tmpActoClon);
								tmpcompEnvio2.setCompareciente(compa.getCompareciente());
								tmpcompEnvio2.getCompareciente().setIdcompareciente(null);
								tmpcompEnvio2.getCompareciente().setFirma(null);
								
								
								//quitar firma
								tmpcompRest.guardar(tmpcompEnvio2);
								if(compa.getRepresentantes()!=null && compa.getRepresentantes().size()>0){
									for(Compareciente cteRepresentante:compa.getRepresentantes()){
										cteRepresentante.setActo(tmpActoClon);
										cteRepresentante.setFirma(null);
										tmpcompEnvio2.setRepresentante(cteRepresentante);
										//quitar firma
										tmpcompRest.guardarRepresentante(tmpcompEnvio2);
									}
									
								}
								if(compa.getConyuge()!=null){
									//VALIDO LOS CONYUGES EN LOS COMPARECIENTES
									ComparecienteConyuge tmpcomconyuge = new ComparecienteConyuge();
									if(compa.getConyuge().getConyugeCompra()!=null){
										tmpcomconyuge.setConyugeCompra(compa.getConyuge().getConyugeCompra());
										tmpcomconyuge.getConyugeCompra().setIdcompareciente(null);
										tmpcomconyuge.getConyugeCompra().setFirma(null);
									}
									
									if(compa.getCompareciente() !=null){
										tmpcomconyuge.setSujeto(compa.getCompareciente());
										
									}
									
									if(compa.getConyuge().getConyugeActual()!=null){
										tmpcomconyuge.setConyugeActual(compa.getConyuge().getConyugeActual());
										tmpcomconyuge.getConyugeActual().setIdcompareciente(null);
										tmpcomconyuge.getConyugeActual().setFirma(null);
									}
									tmpcomconyuge.setIsmismoconyuge(compa.getConyuge().getIsmismoconyuge());
									tmpcompEnvio2.setCompConyuge(tmpcomconyuge);
									tmpcompRest.guardarConyuge(tmpcompEnvio2);
								}
								
								//Asigno autorizantes
								
							}
						}
										
						
					FormularioBo frmBo = new FormularioBoImpl();
					// OBTENGO LA LISTA DE LOS FORMULARIOS CON SUS VALORES YA SOLO FALTA GUARDARLOS Y VUALA! TENEMOS CLONE DE EXPEDIENTES. SALUD!
					List<Formulario> listaformularios = frmBo.buscarFormulariosPorActo(act.getIdacto());
					
					for(Formulario frm:listaformularios){
						Formulario tmpFrm  = new Formulario();
						tmpFrm.setActo(tmpActoClon);
						tmpFrm.setIdsesion(user.getIdsesionactual());
						tmpFrm.setIdformulario(GeneradorId.generaId(tmpFrm));

						
						tmpFrm.setConFormulario(frm.getConFormulario());
						frmBo.save(tmpFrm);
						
						tmpFrm.setListaValFormulario(frm.getListaValFormulario());
						tmpFrm.setListaValSubFormulario(frm.getListaValSubFormulario());
						if(tmpFrm.getListaValFormulario().size()>0){
							for(ValorFormulario tmpValFrm:tmpFrm.getListaValFormulario()){
								tmpValFrm.setIdvalorform(GeneradorId.generaId(tmpValFrm));
								tmpValFrm.setIdsesion(user.getIdsesionactual());
								tmpValFrm.setFormulario(tmpFrm);
							}
						}
						if(tmpFrm.getListaValSubFormulario().size()>0){
							for(ValorSubFormulario tmpValSfFrm:tmpFrm.getListaValSubFormulario()){
								tmpValSfFrm.setIdvalorsubform(GeneradorId.generaId(tmpValSfFrm));
								tmpValSfFrm.setIdsesion(user.getIdsesionactual());
								tmpValSfFrm.setFormulario(tmpFrm);
							}
						}
						frmBo.actualizarValoresFormulario(tmpFrm);
					}
					
					
					
					//ACTO DEL CLON ACTUAL:tmpActoClon
					//tmpActoClon
					
					/*for(ActoFormulario actfrm: listaActoFrm){
						
					}*/
					
					System.out.println("Temino----");
					iact++;

						
					}
					iexp++;
				}
				
				
				
				
				
				
			}catch(NotariaException e){
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
	@Path("/eliminarObjetoActo")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio eliminarOperacion(ExpedienteEnvio expedienteEnvio,
			@Context HttpServletRequest request) {

		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		if (expedienteEnvio == null
				|| expedienteEnvio.getUsuario() == null
				|| expedienteEnvio.getUsuario().getIdusuario() == null
				|| expedienteEnvio.getUsuario().getIdsesionactual() == null
				|| expedienteEnvio.getIdacto() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
				.getIdsesionactual(), expedienteEnvio.getUsuario()
				.getIdusuario())) {
			try{
				
//				boolean b = this.expedienteBo.eliminarOperacion(expedienteEnvio
//						.getExpediente().getIdexpediente(), expedienteEnvio
//						.getOperacion().getOperacion().getIdoperacion(),
//						expedienteEnvio.getUsuario().getIdusuario());
				
				ActoBo actoBo = new ActoBoImpl();
				Acto acto = actoBo.desactivarActo(expedienteEnvio.getIdacto());
				if(!acto.getIsactivo()){
					respuesta.setExito(true);
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				}else{
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
				}
					
//				if (b) {
//					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
//				} else {
//					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
//					respuesta.setExito(false);
//				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ELIMINAR+" "+e.getMessage());
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
	@Path("/guardarOperacion")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio guardarOperacion(ExpedienteEnvio expedienteEnvio,
			@Context HttpServletRequest request) {
		Date fecha = new Date();
		ExpedienteEnvio respuesta = new ExpedienteEnvio();

		if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
				|| expedienteEnvio.getUsuario().getIdusuario() == null
				|| expedienteEnvio.getUsuario().getIdsesionactual() == null
				|| expedienteEnvio.getExpediente() == null
				|| expedienteEnvio.getOperacion() == null
				|| expedienteEnvio.getOperacion().getStatus() == null
				|| expedienteEnvio.getOperacion().getStatus().trim().isEmpty()
				|| (expedienteEnvio.getOperacion().getLocacion() == null 
						&& expedienteEnvio.getExpediente().getTramite().getLocacion() == null)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
				.getIdsesionactual(), expedienteEnvio.getUsuario()
				.getIdusuario())) {
			
			try{
				boolean b = false;
				String idtramite = expedienteBo.obtenerTramitePorExpedienteId(expedienteEnvio.getExpediente().getIdexpediente());
				TramiteBo tramiteBo = new TramiteBoImpl();
	//			System.out.println("ID TRAMITE "+idtramite);
				Tramite tramite = new Tramite();
				tramite.setIdtramite(idtramite);
				tramite = tramiteBo.buscarPorIdCompleto(tramite);
	//			System.out.println("ID ABOGADO "+tramite.getAbogado().getIdusuario());
	//			System.out.println("ID PERSONA "+tramite.getCliente().getIdpersona());
				if(tramite.getLocacion()==null){
					tramite.setLocacion(expedienteEnvio.getOperacion().getLocacion());
					tramiteBo.update(tramite);

				}
				if (expedienteEnvio.getOperacion().getStatus().equals("A")) {
					System.out.println("estatus A");
					for(DatoActo dato:expedienteEnvio.getOperacion().getListaActos()){
						System.out.println("nombre del acto rest "+dato.getDsnombre());
					}
					b = this.expedienteBo.actualizarOperacion(expedienteEnvio
							.getOperacion(), expedienteEnvio.getExpediente()
							.getIdexpediente(), expedienteEnvio.getUsuario()
							.getIdsesionactual(), new Timestamp(fecha.getTime()),
							expedienteEnvio.getUsuario().getIdusuario());
				} else {
					System.out.println("estatus C");
					for(DatoActo dato:expedienteEnvio.getOperacion().getListaActos()){
						System.out.println("nombre acto rest "+dato.getDsnombre());
					}
					if (expedienteEnvio.getOperacion().getStatus().equals("C")) {
//	@omarete 07/10/2014 se comenta el codigo de abajo porque esa parte del codigo genera una restriccion de poder guardar 
//		más de 1 acto
//						b = this.expedienteBo.esoperacionUnica(expedienteEnvio
//								.getExpediente().getIdexpediente(), expedienteEnvio
//								.getOperacion().getOperacion().getIdoperacion());
//						if (b)
//					@omarete TODO: hay que reparar esta mamada del "b"
							b = this.expedienteBo.guardarOperacion(expedienteEnvio
									.getOperacion(), expedienteEnvio
									.getExpediente().getIdexpediente(),
									expedienteEnvio.getUsuario()
											.getIdsesionactual(), new Timestamp(
											fecha.getTime()), expedienteEnvio
											.getUsuario().getIdusuario());
//						else
//							b = false;
					}
	
				}
	
				if (b){
					if(expedienteEnvio.getOperacion().getDsnombre().equalsIgnoreCase("compraventa")){
						for(DatoActo dato:expedienteEnvio.getOperacion().getListaActos()){
							if(dato.getHasProceso() == null || !dato.getHasProceso()){
								BonitaCommonBean commonBean = new BonitaCommonBean();
								commonBean.setIdActo(dato.getIdacto());
								commonBean.setNombreOperacion(expedienteEnvio.getOperacion().getDsnombre());
								commonBean.setNumExpediente(expedienteEnvio.getExpediente().getNumexpediente());
								commonBean.setIdExpediente(expedienteEnvio.getExpediente().getIdexpediente());
								commonBean.setUsuario(expedienteEnvio.getUsuario().getCdusuario());
								commonBean.setIdsesionactual(expedienteEnvio.getUsuario().getIdsesionactual());
								commonBean.setIdusuario(expedienteEnvio.getUsuario().getIdusuario());
								commonBean.setIsCompraventa(Boolean.TRUE);
								/* TODO SE COMENTA LA PARTE QUE INICIA EL PROCESO EN BONITA */
//								expedienteBo.iniciarProcesoOperacion(commonBean);
							}
						}
					}
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				}else {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO+" "+e.getMessage());
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
	@Path("/actualizarOperacion")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio actualizarOperacion(ExpedienteEnvio expedienteEnvio,
			@Context HttpServletRequest request) {
		Date fecha = new Date();
		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
				|| expedienteEnvio.getUsuario().getIdusuario() == null
				|| expedienteEnvio.getUsuario().getIdsesionactual() == null
				|| expedienteEnvio.getExpediente() == null
				|| expedienteEnvio.getOperacion() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
				.getIdsesionactual(), expedienteEnvio.getUsuario()
				.getIdusuario())) {
			try{
				// if (expedienteEnvio == null || expedienteEnvio.getUsuario() ==
				// null
				// || expedienteEnvio.getUsuario().getIdusuario() == null
				// || expedienteEnvio.getUsuario().getIdsesionactual() == null
				// || expedienteEnvio.getExpediente() == null
				// || expedienteEnvio.getIdOperacion() == null) {
				// respuesta.setEstatus("Faltan parametros");
				// respuesta.setExito(false);
				// return respuesta;
				// }
				boolean b = this.expedienteBo.actualizarOperacion(expedienteEnvio
						.getOperacion(), expedienteEnvio.getExpediente()
						.getIdexpediente(), expedienteEnvio.getUsuario()
						.getIdsesionactual(), new Timestamp(fecha.getTime()),
						expedienteEnvio.getUsuario().getIdusuario());
	
				if (b)
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				else {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {

			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}

	// @POST
	// @Path("/listarOperaciones")
	// @Produces({ "application/json", "application/xml" })
	// public ExpedienteEnvio listarOperaciones(ExpedienteEnvio expedienteEnvio,
	// @Context HttpServletRequest request) {
	//
	// ExpedienteEnvio respuesta = new ExpedienteEnvio();
	// if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
	// || expedienteEnvio.getUsuario().getIdusuario() == null
	// || expedienteEnvio.getUsuario().getIdsesionactual() == null
	// ) {
	// respuesta.setEstatus("Faltan parametros");
	// respuesta.setExito(false);
	// return respuesta;
	// }
	//
	// if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
	// .getIdsesionactual(), expedienteEnvio.getUsuario().getIdusuario())) {
	//
	// try {
	// List<Operacion> lista = this.operacionBo.findAll();
	// ArrayList<DatoOperacion> listaOperaciones = new
	// ArrayList<DatoOperacion>();
	// DatoOperacion aux;
	// if(lista!=null && !lista.isEmpty()){
	// for(Operacion o:lista){
	// aux = new DatoOperacion();
	// aux.setOperacion(o);
	// listaOperaciones.add(aux);
	// }
	// }
	//
	// respuesta.setComboOperaciones(listaOperaciones);
	//
	// } catch (NotariaException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	//
	// } else {
	//
	// respuesta.setEstatus("La sesion no es válida");
	// respuesta.setExito(false);
	// }
	// return respuesta;
	// }
	//
	// @POST
	// @Path("/listarSubOperacionesPorOper")
	// @Produces({ "application/json", "application/xml" })
	// public ExpedienteEnvio listarSubOperacionesPorOper(ExpedienteEnvio
	// expedienteEnvio,
	// @Context HttpServletRequest request) {
	//
	// ExpedienteEnvio respuesta = new ExpedienteEnvio();
	// if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
	// || expedienteEnvio.getUsuario().getIdusuario() == null
	// || expedienteEnvio.getUsuario().getIdsesionactual() == null
	// || expedienteEnvio.getOperacion()==null) {
	// respuesta.setEstatus("Faltan parametros");
	// respuesta.setExito(false);
	// return respuesta;
	// }
	//
	// if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
	// .getIdsesionactual(), expedienteEnvio.getUsuario().getIdusuario())) {
	// List<Suboperacion> lista =
	// this.operacionBo.OperacionesPorOperacion(expedienteEnvio.getOperacion().getIdoperacion());
	// ArrayList<DatoActo> listaSubOperaciones = new ArrayList<DatoActo>();
	// DatoActo aux;
	// if(lista!=null && !lista.isEmpty()){
	// for(Suboperacion o:lista){
	// aux = new DatoActo();
	// aux.setSuboperacion(o);
	// listaSubOperaciones.add(aux);
	// }
	// }
	//
	// respuesta.setComboSubOperaciones(listaSubOperaciones);
	// } else {
	//
	// respuesta.setEstatus("La sesion no es válida");
	// respuesta.setExito(false);
	// }
	// return respuesta;
	// }
	//
	// @POST
	// @Path("/listarPrevios")
	// @Produces({ "application/json", "application/xml" })
	// public ExpedienteEnvio listarPrevios(ExpedienteEnvio expedienteEnvio,
	// @Context HttpServletRequest request) {
	//
	// ExpedienteEnvio respuesta = new ExpedienteEnvio();
	// if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
	// || expedienteEnvio.getUsuario().getIdusuario() == null
	// || expedienteEnvio.getUsuario().getIdsesionactual() == null) {
	// respuesta.setEstatus("Faltan parametros");
	// respuesta.setExito(false);
	// return respuesta;
	// }
	//
	// if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
	// .getIdsesionactual(), expedienteEnvio.getUsuario().getIdusuario())) {
	// List<Documento> lista = this.documentoBo.obtenerDocumento("Previo");
	// ArrayList<DatoActoDocumento> listaDocumentos = new
	// ArrayList<DatoActoDocumento>();
	// DatoActoDocumento aux;
	// if(lista!=null && !lista.isEmpty()){
	// for(Documento d:lista){
	// aux = new DatoActoDocumento();
	// aux.setDocumento(d);
	// listaDocumentos.add(aux);
	// }
	// }
	//
	// respuesta.setComboPrevios(listaDocumentos);
	// } else {
	//
	// respuesta.setEstatus("La sesion no es válida");
	// respuesta.setExito(false);
	// }
	// return respuesta;
	// }
	//
	// @POST
	// @Path("/listarPosteriores")
	// @Produces({ "application/json", "application/xml" })
	// public ExpedienteEnvio listarPosteriores(ExpedienteEnvio expedienteEnvio,
	// @Context HttpServletRequest request) {
	//
	// ExpedienteEnvio respuesta = new ExpedienteEnvio();
	// if (expedienteEnvio == null || expedienteEnvio.getUsuario() == null
	// || expedienteEnvio.getUsuario().getIdusuario() == null
	// || expedienteEnvio.getUsuario().getIdsesionactual() == null) {
	// respuesta.setEstatus("Faltan parametros");
	// respuesta.setExito(false);
	// return respuesta;
	// }
	//
	// if (NotariaUtils.isSesionValida(expedienteEnvio.getUsuario()
	// .getIdsesionactual(), expedienteEnvio.getUsuario().getIdusuario())) {
	// List<Documento> lista = this.documentoBo.obtenerDocumento("Posterior");
	// ArrayList<DatoActoDocumento> listaDocumentos = new
	// ArrayList<DatoActoDocumento>();
	// DatoActoDocumento aux;
	// if(lista!=null && !lista.isEmpty()){
	// for(Documento d:lista){
	// aux = new DatoActoDocumento();
	// aux.setDocumento(d);
	// listaDocumentos.add(aux);
	// }
	// }
	//
	// respuesta.setComboPosteriores(listaDocumentos);
	// } else {
	//
	// respuesta.setEstatus("La sesion no es válida");
	// respuesta.setExito(false);
	// }
	// return respuesta;
	// }

	@POST
	@Path("/finalizarTarea")
	@Produces({ "application/json", "application/xml" })
	public ExpedienteEnvio finalizarTarea(ExpedienteEnvio ex) {
		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		if (ex == null || ex.getUsuario() == null
				|| ex.getUsuario().getIdusuario() == null
				|| ex.getUsuario().getIdsesionactual() == null
				|| ex.getExpediente() == null
				|| ex.getExpediente().getIdexpediente() == null
				|| ex.getExpediente().getEstatus() == null
				|| ex.getExpediente().getDsmotivocierre() == null
				|| ex.getExpediente().getDsmotivocierre().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ex.getUsuario().getIdsesionactual(), ex
				.getUsuario().getIdusuario())) {
			ex.getExpediente().setIdsesion(ex.getUsuario().getIdsesionactual());

			try {
				respuesta
						.setExpediente(expedienteBo.update(ex.getExpediente()));
			} catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		return respuesta;
	}
	
	@POST
	@Path("/busquedaAvanzada")
	@Produces(MediaType.APPLICATION_JSON)
	public ExpedienteEnvio busquedaAvanzada(ExpedienteEnvio expEnv) throws ParseException{
		ExpedienteEnvio respuesta = new ExpedienteEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(expEnv)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(expEnv.getUsuario().getIdsesionactual(), expEnv
				.getUsuario().getIdusuario())) {
			try {
				if((expEnv.getExpediente().getFechainicial()!=null && expEnv.getExpediente().getFechafinal()==null)
						|| (expEnv.getExpediente().getFechafinal()!=null && expEnv.getExpediente().getFechainicial()==null)){
					respuesta.setEstatus("Ambos campos de fecha deben contener una fecha válida");
					respuesta.setExito(false);
					return respuesta;
				}
				if(expEnv.getExpediente().getFechainicial() !=null 
					&& expEnv.getExpediente().getFechafinal() != null){


					if(expEnv.getExpediente().getFechainicial().getTime()>expEnv.getExpediente().getFechafinal().getTime()){
						respuesta.setEstatus("La fecha inicial no puede ser mayor a la fecha final");
						respuesta.setExito(false);
						return respuesta;
					}
					 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					 String fechainicialstr = sdf.format(expEnv.getExpediente().getFechainicial());
					 String fechafinalstr = sdf.format(expEnv.getExpediente().getFechafinal());
					 expEnv.getExpediente().setFechainicialstr(fechainicialstr);
					 expEnv.getExpediente().setFechafinalstr(fechafinalstr);
				}
				
				respuesta.setLista(new ArrayList<Expediente>(expedienteBo.obtenerExpedientes(expEnv.getExpediente(),expEnv.getCompareciente())));
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/obtenerExpedientes")
	@Produces(MediaType.APPLICATION_JSON)
	public ExpedienteEnvio obtenerExpedientes(ExpedienteEnvio ee,
			@Context HttpServletRequest request) throws JSONException {

		ExpedienteEnvio respuesta = new ExpedienteEnvio();

		if (NotariaUtils.faltanRequeridosUsuario(ee)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ee.getUsuario().getIdsesionactual(), ee
				.getUsuario().getIdusuario())) {
			try {
				Expediente expediente = null;
				if (ee.getExpediente() == null) {
					expediente = new Expediente();
					ee.setExpediente(expediente);
				}
				if (ee.getPersona() != null) {
					Tramite tramite = new Tramite();
					tramite.setCliente(ee.getPersona());
					expediente.setTramite(tramite);
				}
				List<Expediente> lista = this.expedienteBo
						.obtenerExpedientes(expediente);

				if (lista == null) {
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
				} else {
					respuesta.setLista(new ArrayList<Expediente>(lista));
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				}
				return respuesta;
			}catch(NotariaException e){
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
	@Path("/obtenerTarjeton")
	@Produces(MediaType.APPLICATION_JSON)
	public OperacionTarjetonEnvio obtenerTarjeton(OperacionTarjetonEnvio ee,
			@Context HttpServletRequest request) throws JSONException {
			OperacionTarjetonEnvio ote = new OperacionTarjetonEnvio();
			String operacion = NotariaUtils.removeSpecials(ee.getOperacionNombre().toUpperCase());
			operacion = operacion.replace(" ","");
			try {
				Field tarjeton = Constantes.class.getField("TARJETON_"+operacion);
				String[]resultado = new String[3];
				ote.setTarjeton((String[])tarjeton.get(resultado));
				
				ActoDocumentoBo actdocBo = new ActoDocumentoBoImpl();
				ote.setTieneDocumentos(actdocBo.tieneDocActo(ee.getIdActo()));
				
			} catch (Exception e) {
				ote.setExito(false);
				ote.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" :: No se encontró el Acto en las constantes");
			}
			
		return ote;
	}
	
	@POST
	@Path("/obtenerClientesDelTramite")
	@Produces(MediaType.APPLICATION_JSON)
	public ExpedienteEnvio obtenerClientesDelTramite(ExpedienteEnvio ee,
			@Context HttpServletRequest request) throws JSONException {

		ExpedienteEnvio respuesta = new ExpedienteEnvio();

		if (NotariaUtils.faltanRequeridosUsuario(ee)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ee.getUsuario().getIdsesionactual(), ee
				.getUsuario().getIdusuario())) {
			try {

				List<Persona> lista = this.expedienteBo
						.obtenerClientesDeExpediente();
				if (lista == null) {
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
				} else {
					respuesta.setListaPersona(new ArrayList<Persona>(lista));
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				}
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
}
