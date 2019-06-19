package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.InmuebleBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.InmuebleBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoInmueble;
import com.palestra.notaria.envio.InmuebleEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Domicilio;
import com.palestra.notaria.modelo.Inmueble;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

/**
 * Clase que atiente las peticiones CRUD para Inmueble.
 * 
 */

@Path("/inmueble")
public class InmuebleRest {

	static Logger logger = Logger.getLogger(InmuebleRest.class);
	private InmuebleBo inmuebleBo = new InmuebleBoImpl();
	private BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();
	private ActoBo actoBo = new ActoBoImpl();

	public InmuebleRest() {

	}

//	@GET
//	@Path("/obtenerInmueblesPorExpediente")
//	@Produces(MediaType.APPLICATION_JSON)
//	public ArrayList<Inmueble> obtenerInmueblesPorExpediente(String expedienteId) {
//		List<Inmueble> lista = this.inmuebleBo
//				.obtenerInmueblesPorExpediente(expedienteId);
//		if (lista != null)
//			return new ArrayList<Inmueble>(lista);
//		else
//			return new ArrayList<Inmueble>();
//	}

	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public InmuebleEnvio obtenerPorIdCompleto(InmuebleEnvio inmuebleRequest,
			@Context HttpServletRequest request) throws JSONException {

		Inmueble inmueble = inmuebleRequest.getInmueble();
		InmuebleEnvio respuesta = new InmuebleEnvio();

		if (inmueble == null || inmueble.getIdinmueble() == null
				|| inmueble.getIdinmueble().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(inmuebleRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(inmuebleRequest.getUsuario()
				.getIdsesionactual(), inmuebleRequest.getUsuario().getIdusuario())) {
			try {

				Inmueble iPersisted = this.inmuebleBo
						.buscarPorIdCompleto(inmueble);

				if (iPersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}

				respuesta.setInmueble(iPersisted);
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
	@Produces({ "application/json", "application/xml" })
	public InmuebleEnvio regitraInmueble(InmuebleEnvio inmuebleRequest,
			@Context HttpServletRequest request) {

		InmuebleEnvio respuesta = new InmuebleEnvio();
		Inmueble inmueble = inmuebleRequest.getInmueble();
		Usuario usuario = inmuebleRequest.getUsuario();

		if (NotariaUtils.faltanRequeridosUsuario(inmuebleRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(inmuebleRequest.getUsuario()
				.getIdsesionactual(), inmuebleRequest.getUsuario().getIdusuario())) {

			try {

				respuesta = this.validarRequeridos(inmuebleRequest);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}

				if (inmueble.getIsasistido() && inmueble.getDomicilio() == null) {
					CodigoError error = new CodigoError();
					error.setCodigo(ErrorCodigoMensaje.CODIGO_E09B4);
					error.setMensaje(ErrorCodigoMensaje.MENSAJE_E09B4);
					ArrayList<CodigoError> errores = new ArrayList<CodigoError>();
					errores.add(error);
					respuesta.setErrores(errores);
					respuesta.setExito(false);
				}

				/** asignar valores que no se capturan desde pantalla **/
				inmueble.setIdinmueble(GeneradorId.generaId(inmueble));
				inmueble.setIdsesion(usuario.getIdsesionactual());
				inmueble.setTmstmp(new Timestamp((new Date()).getTime()));

				// Siempre se asocia un domicilio a un inmueble, aunque sea no
				// asistido.
				Domicilio domicilio = inmueble.getDomicilio();
				if (domicilio == null) {
					domicilio = new Domicilio();
				}
				domicilio.setIsasistido(inmueble.getIsasistido());
				domicilio.setIddomicilio(GeneradorId.generaId(domicilio));
				domicilio.setDsdircompleta(inmueble.getDsdomcompleto());
				domicilio.setIdsesion(usuario.getIdsesionactual());
				domicilio.setTmstmp(new Timestamp((new Date()).getTime()));
				inmueble.setDomicilio(domicilio);

				/** registrar inmueble **/
				Boolean res = inmuebleBo.registrarInmueble(inmueble);
				if (!res) {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
					return respuesta;
				}

				// Obtener expediente del acto, es necesario para bitacora
				String idExpediente = actoBo.getExpedienteIdByActoId(inmueble
						.getActo().getIdacto());
				if (idExpediente == null || idExpediente.isEmpty()) {
					logger.info("No se pudo obtener el idExpediente y por lo que no se registró en bitácora.");
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
				}

				/** registrar en bitacora, el registro de domicilio **/
				bitacoraGeneralHelper.registrarEnBitacora(
						usuario.getIdusuario(), idExpediente, null,
						"Domicilio", Constantes.OPERACION_REGISTRO,
						"Se guarda un Domicilio");

				/** registrar en bitacora, el registro de inmueble **/
				bitacoraGeneralHelper.registrarEnBitacora(
						usuario.getIdusuario(), idExpediente, null, "Inmueble",
						Constantes.OPERACION_REGISTRO, "Se guarda un Inmueble");

				logger.info("===> Registro exitoso.");
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

	@POST
	@Path("/actualizar")
	@Produces({ "application/json", "application/xml" })
	public InmuebleEnvio actualizar(InmuebleEnvio inmuebleRequest,
			@Context HttpServletRequest request) {

		InmuebleEnvio respuesta = new InmuebleEnvio();
		Inmueble inmueble = inmuebleRequest.getInmueble();
		Usuario usuario = inmuebleRequest.getUsuario();

		if (inmueble == null || inmueble.getIdinmueble() == null
				|| inmueble.getIdinmueble().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(inmuebleRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(inmuebleRequest.getUsuario()
				.getIdsesionactual(), inmuebleRequest.getUsuario().getIdusuario())) {
			try {

				respuesta = this.validarRequeridos(inmuebleRequest);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}

				Inmueble iPersisted = inmuebleBo.buscarPorIdCompleto(inmueble);
				if (iPersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}

				/** asignar valores que no se capturan desde pantalla **/
				inmueble.setIdsesion(usuario.getIdsesionactual());
				inmueble.setTmstmp(new Timestamp((new Date()).getTime()));

				// Por regla de negocio, el domicilio nunca cambia a pesar de
				// que sea asistido.
				Domicilio domicilio = new Domicilio();
				domicilio.setIddomicilio(iPersisted.getDomicilio()
						.getIddomicilio());
				inmueble.setDomicilio(domicilio);

				/** actualizar la inmueble **/
				Inmueble resUpdate = inmuebleBo.update(inmueble);
				if (resUpdate == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
					respuesta.setExito(false);
					return respuesta;
				}

				// El idExpiente es obligatorio para la bitacora, obtenerlo a
				// partir del acto.
				String idExpediente = iPersisted.getActo().getExpediente()
						.getIdexpediente();
				/** registrar en bitacora, actualizacion inmueble **/
				bitacoraGeneralHelper.registrarEnBitacora(
						usuario.getIdusuario(), idExpediente, null, "Inmueble",
						Constantes.OPERACION_ACTUALIZACION,
						"Se actualiza un inmueble");
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);

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
	@Path("/eliminar")
	@Produces({ "application/json", "application/xml" })
	public InmuebleEnvio eliminar(InmuebleEnvio inmuebleRequest,
			@Context HttpServletRequest request) throws JSONException {

		InmuebleEnvio respuesta = new InmuebleEnvio();
		Inmueble inmueble = inmuebleRequest.getInmueble();

		if (inmueble == null || inmueble.getIdinmueble() == null
				|| inmueble.getIdinmueble().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(inmuebleRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(inmuebleRequest.getUsuario()
				.getIdsesionactual(), inmuebleRequest.getUsuario().getIdusuario())) {

			try {
				/** obtener el inmueble persistida en bd. **/
				Inmueble iRegistrada = inmuebleBo.buscarPorIdCompleto(inmueble);
				if (iRegistrada == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}

				/**
				 * tomar el id de expediente del acto del inmueble para bitacora
				 **/
				String idExpediente = iRegistrada.getActo().getExpediente()
						.getIdexpediente();
				
				//Antes de eliminar asignar el 
				/** eliminar inmueble **/
				boolean resultado = inmuebleBo.eliminarInmueble(iRegistrada);
				if (!resultado) {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
					return respuesta;
				}

				bitacoraGeneralHelper.registrarEnBitacora(inmuebleRequest
						.getUsuario().getIdusuario(), idExpediente, null,
						"Inmueble", Constantes.OPERACION_ELIMINACION,
						"Se ha eliminado un inmueble.");
				respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
				respuesta.setExito(true);
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
		}
		return respuesta;
	}

	/**
	 * Devuelve una lista de inmueble filtrado ya se por acto, expediente o
	 * ambos. En caso de no propocionar ningun filtro devuelve todos los
	 * inmuebles.
	 * 
	 * @param inmuebleRequest
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/obtenListaInmuebles")
	@Produces({ "application/json", "application/xml" })
	public InmuebleEnvio obtenListaInmuebles(InmuebleEnvio inmuebleRequest,
			@Context HttpServletRequest request) throws JSONException {

		Inmueble inmueble = inmuebleRequest.getInmueble();
		InmuebleEnvio respuesta = new InmuebleEnvio();

		if (inmueble == null || NotariaUtils.faltanRequeridosUsuario(inmuebleRequest) ) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(inmuebleRequest.getUsuario()
				.getIdsesionactual(), inmuebleRequest.getUsuario().getIdusuario())) {
			try{
				List<DatoInmueble> lista = this.inmuebleBo
						.obtenListaInmuebles(inmueble);
				if (lista != null) {
					respuesta.setListaInmuebles(new ArrayList<DatoInmueble>(lista));
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
					respuesta.setExito(true);
					return respuesta;
				} else {
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
					respuesta.setExito(false);
					return respuesta;
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR+" "+e.getMessage());
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
	 * @param inmuebleEnvio
	 * 
	 * @return inmuebleEnvio con respuesta
	 * 
	 */
	private InmuebleEnvio validarRequeridos(InmuebleEnvio inmuebleRequest) {

		Inmueble inmueble = inmuebleRequest.getInmueble();

		InmuebleEnvio respuesta = new InmuebleEnvio();

		ArrayList<CodigoError> errores = new ArrayList<CodigoError>();
		CodigoError error = null;

		if (inmueble.getActo() == null
				|| inmueble.getActo().getIdacto() == null
				|| inmueble.getActo().getIdacto().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E10B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E10B2);
			errores.add(error);
			respuesta.setExito(false);
		}

		if (inmueble.getFchinscripcion() == null) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E09B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E09B1);
			errores.add(error);
			respuesta.setExito(false);
		}

		if (inmueble.getDsdomcompleto() == null
				|| inmueble.getDsdomcompleto().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E09B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E09B2);
			errores.add(error);
			respuesta.setExito(false);
		}

		if (inmueble.getIsasistido() == null) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E09B3);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E09B3);
			errores.add(error);
			respuesta.setExito(false);
		}

		respuesta.setErrores(errores);
		
		//TODO: al menos una vocacion se debe capturar

		return respuesta;
	}
	
}
