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
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.CamposTarjetaAmarillaBo;
import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.TarjetaAmarillaBo;
import com.palestra.notaria.bo.impl.CamposTarjetaAmarillaBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.TarjetaAmarillaBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoActoDeTarjeta;
import com.palestra.notaria.dato.DatoBusquedaCompareciente;
import com.palestra.notaria.dato.DatoCalculosTarjeta;
import com.palestra.notaria.dato.DatoTarjetaAmarilla;
import com.palestra.notaria.envio.TarjetaAmarillaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.CamposTarjetaAmarilla;
import com.palestra.notaria.modelo.TarjetaAmarilla;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

/**
 * Clase que atiende las peticiones CRUD para TarjetaAmarilla
 * 
 * @author sofia
 * 
 */
@Path("/tarjetaAmarilla")
public class TarjetaAmarillaRest {

	static Logger logger = Logger.getLogger(TarjetaAmarillaRest.class);

	private static final Double IVA = 0.16;

	private TarjetaAmarillaBo tarjetaAmarillaBo = null;
	private ComparecienteBo comparecienteBo = null;
	private BitacoraGeneralHelper bitacoraGeneralHelper = null;
	private CamposTarjetaAmarillaBo camposTarjetaBo;

	public TarjetaAmarillaRest() {
		tarjetaAmarillaBo = new TarjetaAmarillaBoImpl();
		comparecienteBo = new ComparecienteBoImpl();
		bitacoraGeneralHelper = new BitacoraGeneralHelper();
		this.camposTarjetaBo = new CamposTarjetaAmarillaBoImpl();
	}

	/**
	 * Obtener numero de escritura y nombre de operacion asociada al acto del
	 * que se desea crear la tarjeta amarilla
	 * 
	 * @param tarjetaRequest
	 * @param request
	 * @return TarjetaAmarillaEnvio con numero de escritura y nombre de
	 *         operacion.
	 * @throws JSONException
	 */
	@POST
	@Path("/dataFromActo")
	@Produces({ "application/json", "application/xml" })
	public TarjetaAmarillaEnvio dataFromActo(
			TarjetaAmarillaEnvio tarjetaRequest,
			@Context HttpServletRequest request) throws JSONException {

		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();
		Acto acto = tarjetaRequest.getActo();

		if (acto == null || acto.getIdacto() == null
				|| acto.getIdacto().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(tarjetaRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tarjetaRequest.getUsuario()
				.getIdsesionactual(), tarjetaRequest.getUsuario().getIdusuario())) {
			try{
				DatoActoDeTarjeta dataFromActo = tarjetaAmarillaBo
						.obtenDataFromActo(acto.getIdacto());
				if (dataFromActo == null) {
					respuesta
							.setEstatus("El acto dado no esta asociado a ninguna escritura.");
					respuesta.setExito(false);
					return respuesta;
				}
	
				/** Obtener los comparecientes asociados al acto **/
				List<DatoBusquedaCompareciente> lista = comparecienteBo
						.findByActoId(acto.getIdacto());
				if (lista != null) {
					respuesta
							.setListaComparecientes(new ArrayList<DatoBusquedaCompareciente>(
									lista));
				}
	
				respuesta.setDatoActoDeTarjeta(dataFromActo);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
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
	@Path("/listarCamposPorOperacion")
	@Produces(MediaType.APPLICATION_JSON)
	public TarjetaAmarillaEnvio listarCamposPorOperacion(TarjetaAmarillaEnvio tae){
		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();
		if (tae.getActo() == null || tae.getActo().getIdacto() == null
				|| tae.getActo().getIdacto().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(tae)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if(NotariaUtils.isSesionValida(tae.getUsuario().getIdsesionactual(), tae.getUsuario().getIdusuario())){
			try{
				respuesta.setCamposTarjetaAmarillaList((ArrayList<CamposTarjetaAmarilla>)getCamposTarjetaBo().getCamposPorOperacion(tae.getActo().getIdacto()));
				if(respuesta.getCamposTarjetaAmarillaList() != null){
					respuesta.setEstatus("listado correcto");
					respuesta.setExito(true);
					return respuesta;
				}else{
					respuesta.setEstatus("ocurrio un error al listar");
					respuesta.setExito(false);
					return respuesta;
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus("sesion invalida");
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public TarjetaAmarillaEnvio obtenerPorIdCompleto(
			TarjetaAmarillaEnvio tarjetaRequest,
			@Context HttpServletRequest request) throws JSONException {

		TarjetaAmarilla tarjeta = tarjetaRequest.getTarjetaAmarilla();
		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();

		if (tarjeta == null || tarjeta.getIdtrjamarilla() == null
				|| tarjeta.getIdtrjamarilla().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(tarjetaRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tarjetaRequest.getUsuario()
				.getIdsesionactual(), tarjetaRequest.getUsuario().getIdusuario())) {
			try {

				TarjetaAmarilla tPersisted = this.tarjetaAmarillaBo
						.buscarPorIdCompleto(tarjeta);

				if (tPersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}

				respuesta.setTarjetaAmarilla(tPersisted);
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
	public TarjetaAmarillaEnvio registraTarjeta(
			TarjetaAmarillaEnvio tarjetaRequest,
			@Context HttpServletRequest request) {

		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();
		TarjetaAmarilla tarjeta = tarjetaRequest.getTarjetaAmarilla();
		Usuario usuario = tarjetaRequest.getUsuario();

		if (NotariaUtils.faltanRequeridosUsuario(tarjetaRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tarjetaRequest.getUsuario()
				.getIdsesionactual(), tarjetaRequest.getUsuario().getIdusuario())) {

			try {

				respuesta = this.validarRequeridos(tarjetaRequest);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}

				// Es necesario el expediente para registrar la bitacora.
				if (tarjeta.getActo().getExpediente() == null
						|| tarjeta.getActo().getExpediente().getIdexpediente() == null
						|| tarjeta.getActo().getExpediente().getIdexpediente()
								.isEmpty()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
					respuesta.setExito(false);
					return respuesta;
				}

				/** asignar valores que no se capturan desde pantalla **/
				tarjeta.setIdtrjamarilla(GeneradorId.generaId(tarjeta));
				tarjeta.setIdsesion(usuario.getIdsesionactual());
				tarjeta.setTmstmp(new Timestamp((new Date()).getTime()));
//				tarjeta.setFecha(new Date());

				/**
				 * Guardar idExpediente antes de persistir o lanza
				 * LazyInitializationException
				 **/
				String idExpediente = tarjeta.getActo().getExpediente()
						.getIdexpediente();

				/** registrar tarjeta amarilla **/
				TarjetaAmarilla res = tarjetaAmarillaBo.save(tarjeta);
				if (res == null) {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
					return respuesta;
				}

				/** registrar en bitacora, el registro de tarjeta amarilla **/
				bitacoraGeneralHelper.registrarEnBitacora(
						usuario.getIdusuario(), idExpediente, null,
						"Tarjeta Amarilla", Constantes.OPERACION_REGISTRO,
						"Se guarda una Tarjeta amarilla");

				logger.info("===> Registro exitoso.");
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);

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
	@Path("/actualizar")
	@Produces({ "application/json", "application/xml" })
	public TarjetaAmarillaEnvio actualizar(TarjetaAmarillaEnvio tarjetaRequest,
			@Context HttpServletRequest request) {

		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();
		TarjetaAmarilla tarjeta = tarjetaRequest.getTarjetaAmarilla();

		if (tarjeta == null
				|| tarjeta.getIdtrjamarilla() == null
				|| tarjeta.getIdtrjamarilla().isEmpty()
				|| tarjeta.getActo() == null
				|| tarjeta.getActo().getExpediente() == null
				|| tarjeta.getActo().getExpediente().getIdexpediente() == null
				|| tarjeta.getActo().getExpediente().getIdexpediente()
						.isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(tarjetaRequest)) {

			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tarjetaRequest.getUsuario()
				.getIdsesionactual(), tarjetaRequest.getUsuario().getIdusuario())) {
			try {

				respuesta = this.validarRequeridos(tarjetaRequest);

				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}

				TarjetaAmarilla tPersisted = tarjetaAmarillaBo
						.buscarPorIdCompleto(tarjeta);
				if (tPersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				
				/** asignar valores que no se toman de pantalla **/
				tarjeta.setIdsesion(tarjetaRequest.getUsuario().getIdsesionactual());
				tarjeta.setTmstmp(new Timestamp((new Date()).getTime()));
//				tarjeta.setFecha(tPersisted.getFecha());
				
				
				/** actualizar la tarjeta amarilla **/
				TarjetaAmarilla resUpdate = tarjetaAmarillaBo.update(tarjeta);
				if (resUpdate == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
					respuesta.setExito(false);
					return respuesta;
				}

				/** registrar en bitacora, actualizacion **/
				bitacoraGeneralHelper.registrarEnBitacora(tarjetaRequest
						.getUsuario().getIdusuario(), tarjetaRequest
						.getTarjetaAmarilla().getActo().getExpediente()
						.getIdexpediente(), null, "Tarjeta Amarilla",
						Constantes.OPERACION_ACTUALIZACION,
						"Se actualiza una Tarjeta Amarilla");
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);

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
	@Path("/eliminar")
	@Produces({ "application/json", "application/xml" })
	public TarjetaAmarillaEnvio eliminar(TarjetaAmarillaEnvio tarjetaRequest,
			@Context HttpServletRequest request) throws JSONException {

		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();
		TarjetaAmarilla tarjeta = tarjetaRequest.getTarjetaAmarilla();

		if (tarjeta == null || tarjeta.getIdtrjamarilla() == null
				|| tarjeta.getIdtrjamarilla().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(tarjetaRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tarjetaRequest.getUsuario()
				.getIdsesionactual(), tarjetaRequest.getUsuario().getIdusuario())) {

			try {

				/** obtener la tarjeta amarilla persistida en bd. **/
				TarjetaAmarilla tRegistrada = tarjetaAmarillaBo
						.buscarPorIdCompleto(tarjeta);
				if (tRegistrada == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}

				/** tomar el expediente del acto de la tarjeta para bitacora **/
				String expRegistrado = tRegistrada.getActo().getExpediente()
						.getIdexpediente();

				/** eliminar la tarjeta amarilla **/
				boolean resultado = tarjetaAmarillaBo.delete(tarjeta);
				if (!resultado) {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
					return respuesta;
				}

				bitacoraGeneralHelper.registrarEnBitacora(tarjetaRequest
						.getUsuario().getIdusuario(), expRegistrado, null,
						"Tarjeta Amarilla", Constantes.OPERACION_ELIMINACION,
						"Se ha eliminado una tarjeta amarilla.");
				respuesta.setEstatus("Se ha eliminado la tarjeta amarilla.");
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
	 * Obtiene un listado de tarjetas amarillas filtrado.
	 * 
	 * @param tarjetaRequest
	 *            TarjetaEnvio con un acto definido
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/obtenListaTarjetas")
	@Produces({ "application/json", "application/xml" })
	public TarjetaAmarillaEnvio obtenListaTarjetas(
			TarjetaAmarillaEnvio tarjetaRequest,
			@Context HttpServletRequest request) throws JSONException {

		TarjetaAmarilla tarjeta = tarjetaRequest.getTarjetaAmarilla();
		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();

		if (tarjeta == null || NotariaUtils.faltanRequeridosUsuario(tarjetaRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tarjetaRequest.getUsuario()
				.getIdsesionactual(), tarjetaRequest.getUsuario().getIdusuario())) {
			try{
				List<DatoTarjetaAmarilla> lista = this.tarjetaAmarillaBo
						.obtenListaTarjetas(tarjeta);
				if (lista != null) {
					respuesta.setListaTarjetas(new ArrayList<DatoTarjetaAmarilla>(
							lista));
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
	 * Calcular valores de tarjeta amarilla
	 * 
	 * @param tarjetaRequest
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/calcularValores")
	@Produces({ "application/json", "application/xml" })
	public TarjetaAmarillaEnvio calcularValores(
			TarjetaAmarillaEnvio tarjetaRequest,
			@Context HttpServletRequest request) throws JSONException {

		DatoCalculosTarjeta calculos = tarjetaRequest.getCalculados();
		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();

		if (calculos == null || calculos.getHonorarios() == null
				|| calculos.getIde() == null
				|| NotariaUtils.faltanRequeridosUsuario(tarjetaRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tarjetaRequest.getUsuario()
				.getIdsesionactual(), tarjetaRequest.getUsuario().getIdusuario())) {

			return this.realizaCalculos(tarjetaRequest);

		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	/**
	 * Calcular valores de tarjeta amarilla
	 * 
	 * @param tarjetaRequest
	 * 
	 * @return Valores calculados.
	 */
	private TarjetaAmarillaEnvio realizaCalculos(
			TarjetaAmarillaEnvio tarjetaRequest) {
		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();
		DatoCalculosTarjeta calculo = tarjetaRequest.getCalculados();

		Double porcientoIVA = calculo.getHonorarios() * IVA;
		calculo.setPorcientoIVA(porcientoIVA);

		Double subHonorarioIVA = calculo.getHonorarios() + porcientoIVA;
		calculo.setSubHonorarioIVA(subHonorarioIVA);

		Double total = subHonorarioIVA + calculo.getIde();
		calculo.setTotal(total);

		respuesta.setCalculados(calculo);

		respuesta.setEstatus("Se realizaron operaciones.");
		respuesta.setExito(true);

		return respuesta;
	}

	/**
	 * Valida los campos que son requeridos a nivel de BD para persistencia
	 * 
	 * @param tarjetaEnvio
	 * 
	 * @return
	 * 
	 */
	private TarjetaAmarillaEnvio validarRequeridos(
			TarjetaAmarillaEnvio tarjetaRequest) {

		TarjetaAmarilla tarjeta = tarjetaRequest.getTarjetaAmarilla();

		TarjetaAmarillaEnvio respuesta = new TarjetaAmarillaEnvio();

		ArrayList<CodigoError> errores = new ArrayList<CodigoError>();
		CodigoError error = null;

		if (tarjeta.getUsuarioelab() == null
				|| tarjeta.getUsuarioelab().getIdusuario() == null
				|| tarjeta.getUsuarioelab().getIdusuario().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E10B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E10B1);
			errores.add(error);
			respuesta.setExito(false);
		}

		if (tarjeta.getActo() == null || tarjeta.getActo().getIdacto() == null
				|| tarjeta.getActo().getIdacto().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E10B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E10B2);
			errores.add(error);
			respuesta.setExito(false);
		}

		respuesta.setErrores(errores);

		return respuesta;
	}

	public CamposTarjetaAmarillaBo getCamposTarjetaBo() {
		return this.camposTarjetaBo;
	}
}
