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

import com.palestra.notaria.bo.CalendarioCitaBo;
import com.palestra.notaria.bo.DocumentoAnexoBo;
import com.palestra.notaria.bo.InvitadoCalendarioCitaBo;
import com.palestra.notaria.bo.impl.CalendarioCitaBoImpl;
import com.palestra.notaria.bo.impl.DocumentoAnexoBoImpl;
import com.palestra.notaria.bo.impl.InvitadoCalendarioCitaBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoCita;
import com.palestra.notaria.dato.DatoInvitadoCita;
import com.palestra.notaria.enums.EnumCitaEstatus;
import com.palestra.notaria.envio.CalendarioCitaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.CalendarioCitaPK;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.DocumentoAnexo;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;
import com.palestra.notaria.modelo.InvitadoCalendarioCita;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.EnviaCorreo;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/calendariocita")
public class CalendarioCitaRest {

	static Logger logger = Logger.getLogger(CalendarioCitaRest.class);
	private static final Integer VERSION_INICIAL = 1;
	private static final String FINALIZAR_CITA = "F";
	private static final String CANCELAR_CITA = "C"; 

	/**
	 * Obtiene el calendario de cita completo.
	 * @param cce
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public CalendarioCitaEnvio obtenerPorIdCompleto(CalendarioCitaEnvio cce,
			@Context HttpServletRequest request) throws JSONException {

		DatoCita cita = cce.getDatocita();
		CalendarioCitaEnvio respuesta = new CalendarioCitaEnvio();

		if (cita == null || cita.getCalendarioCita().getId() == null
				|| cita.getCalendarioCita().getId().getIdcita() == null
				|| cita.getCalendarioCita().getId().getIdcita().isEmpty()
				|| cita.getCalendarioCita().getId().getVersion() == null
				|| cce.getExpediente() == null
				|| cce.getExpediente().getIdexpediente() == null
				|| cce.getExpediente().getIdexpediente().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(cce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(cce.getUsuario().getIdsesionactual(),
				cce.getUsuario().getIdusuario())) {
			try {

				CalendarioCitaBo calendarioCitaBo = new CalendarioCitaBoImpl();
				CalendarioCita ccPersisted = calendarioCitaBo
						.buscarCalendarioCitaCompleto(cita.getCalendarioCita()
								.getId());
				if (ccPersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				/** instanciar bo **/
				DocumentoAnexoBo documentoAnexoBo = new DocumentoAnexoBoImpl();
				InvitadoCalendarioCitaBo invitadoBo = new InvitadoCalendarioCitaBoImpl();

				/** asignar calendario cita **/
				DatoCita dc = new DatoCita();
				dc.setCalendarioCita(ccPersisted);

				/** obtener documentos agendados de la cita dada. **/
				List<DocumentoNotarialMaster> docsCita = documentoAnexoBo
						.obtenMasterAgendados(cita.getCalendarioCita());
				if (docsCita != null) {
					dc.setDocumentos(new ArrayList<DocumentoNotarialMaster>(
							docsCita));
				}
				/** obtener invitados agendados del la cita dada. **/
				List<DatoInvitadoCita> invAgendados = invitadoBo
						.findInvitadosAgendados(cita.getCalendarioCita());
				if (invAgendados != null) {
					dc.setInvitados(new ArrayList<DatoInvitadoCita>(
							invAgendados));
				}
				// CitaCalendario persitido.
				respuesta.setDatocita(dc);

				/** obtener docs master del expediente **/
				String idexpediente = cce.getExpediente().getIdexpediente();
				List<DocumentoNotarialMaster> docsDisponibles = documentoAnexoBo
						.obtenMasterDisponibles(idexpediente);
				if (docsDisponibles != null) {
					respuesta
							.setComboDocumentos(new ArrayList<DocumentoNotarialMaster>(
									docsDisponibles));
				}
				/** obtener invitados disponibles para la cita **/
				List<DatoInvitadoCita> invDisponibles = invitadoBo
						.findInvitadosDisponibles(idexpediente);
				if (invDisponibles != null) {
					respuesta
							.setComboInvitados(new ArrayList<DatoInvitadoCita>(
									invDisponibles));
				}
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
	@Path("/inicializaCombos")
	@Produces({ "application/json", "application/xml" })
	public CalendarioCitaEnvio inicializaCombos(CalendarioCitaEnvio cce,
			@Context HttpServletRequest request) throws JSONException {

		CalendarioCitaEnvio respuesta = new CalendarioCitaEnvio();

		if ( cce.getExpediente() == null
				|| cce.getExpediente().getIdexpediente() == null
				|| cce.getExpediente().getIdexpediente().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(cce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(cce.getUsuario().getIdsesionactual(),
				cce.getUsuario().getIdusuario())) {
			try {
				
				/** instanciar bo **/
				DocumentoAnexoBo documentoAnexoBo = new DocumentoAnexoBoImpl();
				InvitadoCalendarioCitaBo invitadoBo = new InvitadoCalendarioCitaBoImpl();

				/** obtener docs master del expediente **/
				String idexpediente = cce.getExpediente().getIdexpediente();
				List<DocumentoNotarialMaster> docsDisponibles = documentoAnexoBo
						.obtenMasterDisponibles(idexpediente);
				if (docsDisponibles != null) {
					respuesta
							.setComboDocumentos(new ArrayList<DocumentoNotarialMaster>(
									docsDisponibles));
				}
				/** obtener invitados disponibles para la cita **/
				List<DatoInvitadoCita> invDisponibles = invitadoBo
						.findInvitadosDisponibles(idexpediente);
				if (invDisponibles != null) {
					respuesta
							.setComboInvitados(new ArrayList<DatoInvitadoCita>(
									invDisponibles));
				}
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
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
	

	/**
	 * Listar calendarios envio.
	 * @param cce
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/listar")
	@Produces({ "application/json", "application/xml" })
	public CalendarioCitaEnvio listar(CalendarioCitaEnvio cce,
			@Context HttpServletRequest request) throws JSONException {

		DatoCita cita = cce.getDatocita();
		CalendarioCitaEnvio respuesta = new CalendarioCitaEnvio();

		if (cita == null || cita.getCalendarioCita() == null
				|| NotariaUtils.faltanRequeridosUsuario(cce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(cce.getUsuario().getIdsesionactual(),
				cce.getUsuario().getIdusuario())) {
			try {
				CalendarioCitaBo calendarioCitaBo = new CalendarioCitaBoImpl();
				List<CalendarioCita> lista = calendarioCitaBo.findCitas(cita
						.getCalendarioCita());
				if (lista == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				respuesta.setListaCitas(new ArrayList<CalendarioCita>(lista));
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

	/**
	 * Listar cita calendario por invitado.
	 * @param cce
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/listarPorInvitado")
	@Produces({ "application/json", "application/xml" })
	public CalendarioCitaEnvio listarPorInvitado(CalendarioCitaEnvio cce,
			@Context HttpServletRequest request) throws JSONException {

		CalendarioCitaEnvio respuesta = new CalendarioCitaEnvio();

		if (cce.getNombreInvitado() == null
				|| cce.getNombreInvitado().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(cce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(cce.getUsuario().getIdsesionactual(),
				cce.getUsuario().getIdusuario())) {
			try {
				CalendarioCitaBo calendarioCitaBo = new CalendarioCitaBoImpl();
				List<CalendarioCita> lista = calendarioCitaBo
						.findCitasByInvitado(cce.getNombreInvitado());
				if (lista == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				respuesta.setListaCitas(new ArrayList<CalendarioCita>(lista));
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

	/**
	 * Guardar cita calendario
	 * @param cce
	 * @return
	 */
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public CalendarioCitaEnvio guardar(CalendarioCitaEnvio cce) {
		CalendarioCitaEnvio respuesta = new CalendarioCitaEnvio();
		DatoCita datoCita = cce.getDatocita();
		try {
			if (datoCita == null || datoCita.getCalendarioCita() == null
					|| NotariaUtils.faltanRequeridosUsuario(cce)
					|| cce.getExpediente() == null
					|| cce.getExpediente().getIdexpediente() == null
					|| cce.getExpediente().getIdexpediente().isEmpty()) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if (NotariaUtils.isSesionValida(cce.getUsuario()
					.getIdsesionactual(), cce.getUsuario().getIdusuario())) {
				
				respuesta = this.validarNegocio(cce);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}

				CalendarioCitaBo calendarioCitaBo = new CalendarioCitaBoImpl();
				BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();
				CalendarioCita cc = datoCita.getCalendarioCita();
				/** asignar propiedades que no se capturan en pantalla **/
				CalendarioCitaPK idcc = new CalendarioCitaPK();
				idcc.setIdcita(GeneradorId.generaId(cc));
				// Por defecto un registro en es version 1.
				idcc.setVersion(VERSION_INICIAL);
				cc.setId(idcc);
				// Por defecto se crea cita en pendiente.
				cc.setDsestatus(EnumCitaEstatus.PENDIENTE.toString());
				// Al registrar una cita poner isreprogramada falsa.
				cc.setIsreprogramdo(false);
				// Al registrar no se envia correo.
				cc.setNotificarcorreo(false);
				cc.setIdsesion(cce.getUsuario().getIdsesionactual());
				cc.setTmstmp(new Timestamp((new Date()).getTime()));
				// Asignar el expediente al cc
				cc.setExpediente(cce.getExpediente());

				List<InvitadoCalendarioCita> lsInvCita = this
						.transformerInvitado(cce);
				List<DocumentoAnexo> lsDocAnexo = this
						.transformerDocumento(cce);

				/** registrar compareciente completo **/
				Boolean b = calendarioCitaBo.registrarCita(cc, lsInvCita,
						lsDocAnexo);
				if (!b) {
					respuesta.setExito(false);
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					return respuesta;
				}
				// idExpediente necesario para bitacora
				String idExpediente = cce.getExpediente().getIdexpediente();
				if (idExpediente == null || idExpediente.isEmpty()) {
					logger.info("No se pudo obtener el idExpediente y por lo que no se registró en bitácora.");
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
				}
				/** registrar en bitacora, el registro de cita **/
				bitacoraGeneralHelper.registrarEnBitacora(cce.getUsuario()
						.getIdusuario(), idExpediente, null, "Calendario cita",
						Constantes.OPERACION_REGISTRO,
						"Se guarda un calendario cita.");

				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;
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
		}
	}

	/**
	 * Cambiar el status de calendario cita.
	 * @param cce
	 * @param request
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/cambiarStatus")
	@Produces({ "application/json", "application/xml" })
	public CalendarioCitaEnvio cambiarStatus(CalendarioCitaEnvio cce,
			@Context HttpServletRequest request) throws JSONException {

		CalendarioCitaEnvio respuesta = new CalendarioCitaEnvio();
		DatoCita datoCita = cce.getDatocita();

		if (datoCita == null 
				|| datoCita.getCalendarioCita() == null
				|| datoCita.getCalendarioCita().getId() == null
				|| datoCita.getCalendarioCita().getId().getIdcita() == null
				|| datoCita.getCalendarioCita().getId().getIdcita().isEmpty()
				|| datoCita.getCalendarioCita().getId().getVersion() == null
				|| cce.getExpediente() == null
				|| cce.getExpediente().getIdexpediente() == null
				|| cce.getExpediente().getIdexpediente().isEmpty()
				|| cce.getStatusCita() == null
				|| cce.getStatusCita().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(cce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(cce.getUsuario().getIdsesionactual(),
				cce.getUsuario().getIdusuario())) {
			try {
				CalendarioCitaBo calendarioCitaBo = new CalendarioCitaBoImpl();
				BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();
				/** Expediente id para regisrar bitacora **/
				String idExpediente = cce.getExpediente().getIdexpediente();
				//C-Cancelado, F- Finalizado
				String status=null;
				if(cce.getStatusCita().equals(CANCELAR_CITA)){
					status = EnumCitaEstatus.CANCELADA.toString();
				}
				else if(cce.getStatusCita().equals(FINALIZAR_CITA)){
					status = EnumCitaEstatus.FINALIZADA.toString();
				}
				else{
					respuesta.setEstatus(Constantes.ESTATUS_ESTATUS_INCORRECTOS);
					respuesta.setExito(false);
					return respuesta;
				}
				/** Cambiar status de la cita **/
				boolean resultado = calendarioCitaBo.cambiarEstatusCitaPorId(datoCita
						.getCalendarioCita().getId(), status);
				if (!resultado) {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_CANCELACION);
					respuesta.setExito(false);
					return respuesta;
				}
				bitacoraGeneralHelper.registrarEnBitacora(cce.getUsuario()
						.getIdusuario(), idExpediente, null, "CalendarioCita",
						Constantes.OPERACION_ACTUALIZACION,
						"Se ha actualizado el estatus un calendario cita.");
				respuesta.setEstatus(Constantes.ESTATUS_CAMBIO_ESTATUS_CORRECTO + status);
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
	 * Actualizar calendario cita.
	 * @param cce
	 * @param request
	 * @return
	 */
	@POST
	@Path("/actualizar")
	@Produces({ "application/json", "application/xml" })
	public CalendarioCitaEnvio actualizar(CalendarioCitaEnvio cce,
			@Context HttpServletRequest request) {

		CalendarioCitaEnvio respuesta = new CalendarioCitaEnvio();
		DatoCita datoCita = cce.getDatocita();

		if (datoCita == null || datoCita.getCalendarioCita() == null
				|| datoCita.getCalendarioCita().getId() == null
				|| datoCita.getCalendarioCita().getId().getIdcita() == null
				|| datoCita.getCalendarioCita().getId().getIdcita().isEmpty()
				|| datoCita.getCalendarioCita().getId().getVersion() == null
				|| cce.getExpediente() == null
				|| cce.getExpediente().getIdexpediente() == null
				|| cce.getExpediente().getIdexpediente().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(cce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(cce.getUsuario().getIdsesionactual(),
				cce.getUsuario().getIdusuario())) {
			try {
				respuesta = this.validarNegocio(cce);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}
				
				CalendarioCitaBo calendarioCitaBo = new CalendarioCitaBoImpl();
				BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();

				/** cita que viene de pantalla y se actualizara **/
				CalendarioCita cCita = datoCita.getCalendarioCita();
				CalendarioCita ccPersisted = calendarioCitaBo
						.buscarCalendarioCitaCompleto(datoCita
								.getCalendarioCita().getId());
				if (ccPersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}

				CalendarioCitaPK oldCitaPK = null;
				if (datoCita.getCalendarioCita().getIsreprogramdo()) {
					// Guardar la vieja id
					oldCitaPK = new CalendarioCitaPK();
					oldCitaPK.setIdcita(cCita.getId().getIdcita());
					oldCitaPK.setVersion(cCita.getId().getVersion());
					Integer version = cCita.getId().getVersion();
					/** Se crea una nueva version+1, con el mismo id **/
					cCita.getId().setVersion(version + 1);
				}
				/** asignar valores que no se capturan desde pantalla **/
				cCita.setIdsesion(cce.getUsuario().getIdsesionactual());
				cCita.setTmstmp(new Timestamp((new Date()).getTime()));
				cCita.setExpediente(cce.getExpediente());
				/** transformar listas **/
				List<InvitadoCalendarioCita> lsInvCita = this
						.transformerInvitado(cce);
				List<DocumentoAnexo> lsDocAnexo = this
						.transformerDocumento(cce);
				Boolean b = false;
				if (cCita.getIsreprogramdo()) {
					/**
					 * La nueva cita se guarda como que no ha sido reprogramada.
					 **/
					cCita.setIsreprogramdo(false);
					b = calendarioCitaBo.reprogramarCita(cCita, lsInvCita,
							lsDocAnexo, oldCitaPK);
				} else {
					/** actualizar cita **/
					b = calendarioCitaBo.actualizarCita(cCita, lsInvCita,
							lsDocAnexo);
				}
				if (!b) {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
					respuesta.setExito(false);
					return respuesta;
				}
				/** Enviar notificacion via mail **/
				if(cCita.getNotificarcorreo()!= null && cCita.getNotificarcorreo()){
					// TODO: se cambiaron datos de conexion. revisar!
					this.enviaCorreoInvitados(cce);
				}
				String idExpediente = cce.getExpediente().getIdexpediente();
				/** registrar en bitacora, actualizacion calendario cita **/
				bitacoraGeneralHelper.registrarEnBitacora(cce.getUsuario()
						.getIdusuario(), idExpediente, null, "Calendario Cita",
						Constantes.OPERACION_ACTUALIZACION,
						"Se actualiza un calendario cita");
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

	/**
	 * Transforma la lista de tipo DatoInvitadoCita a la entidad
	 * InvitadoCalendarioCita. Donde InvitadoCalendarioCita es la entidad que se
	 * persiste en BD.
	 * 
	 * @param cce
	 *            CalendarioCitaEnvio
	 * @return
	 */
	private List<InvitadoCalendarioCita> transformerInvitado(
			CalendarioCitaEnvio cce) {
		List<InvitadoCalendarioCita> lsInvitados = new ArrayList<InvitadoCalendarioCita>();
		InvitadoCalendarioCita icita = null;
		Compareciente compareciente = null;
		Usuario usuario = null;

		if (cce.getDatocita().getInvitados() == null) {
			return null;
		}
		for (DatoInvitadoCita dic : cce.getDatocita().getInvitados()) {
			icita = new InvitadoCalendarioCita();
			if (dic.getTipo().equals("compareciente")) {
				compareciente = new Compareciente();
				compareciente.setIdcompareciente(dic.getId());
				icita.setCompareciente(compareciente);
			} else {
				usuario = new Usuario();
				usuario.setIdusuario(dic.getId());
				icita.setUsuario(usuario);
			}
			/** asignar propiedades que no se capturan en pantalla **/
			icita.setIdinvitado(GeneradorId.generaId(icita));
			icita.setIdsesion(cce.getUsuario().getIdsesionactual());
			icita.setTmstmp(new Timestamp((new Date()).getTime()));
			icita.setCita(cce.getDatocita().getCalendarioCita());
			lsInvitados.add(icita);
		}
		return lsInvitados;
	}

	/**
	 * Transforma una lista de tipo DocumentoNotarialMaster a Lista de tipo
	 * DocumentoAnexo, donde este ultimo es la entidad que se persiste en Bd.
	 * 
	 * @param cce
	 *            CalendarioCitaEnvio
	 * @return
	 */
	private List<DocumentoAnexo> transformerDocumento(CalendarioCitaEnvio cce) {
		List<DocumentoAnexo> lsDocAnexo = new ArrayList<DocumentoAnexo>();
		DocumentoAnexo danexo = null;
		if (cce.getDatocita().getDocumentos() == null) {
			return null;
		}
		for (DocumentoNotarialMaster doc : cce.getDatocita().getDocumentos()) {
			danexo = new DocumentoAnexo();
			danexo.setDocnotmas(doc);
			danexo.setIdanexo(GeneradorId.generaId(danexo));
			danexo.setIdsesion(cce.getUsuario().getIdsesionactual());
			danexo.setTmstmp(new Timestamp((new Date()).getTime()));
			danexo.setCita(cce.getDatocita().getCalendarioCita());
			lsDocAnexo.add(danexo);
		}
		return lsDocAnexo;
	}

	/**
	 * Envia notificacion via correo electronico que la cita ha sido reagendada.
	 */
	private void enviaCorreoInvitados(CalendarioCitaEnvio cce) {
		if (cce.getDatocita().getInvitados() == null) {
			return;
		}
		String actividad = cce.getDatocita().getCalendarioCita()
				.getDsactividad();
		for (DatoInvitadoCita dic : cce.getDatocita().getInvitados()) {
			if (dic.getDscorreoelectronico() != null
					&& !dic.getDscorreoelectronico().isEmpty()) {
				String mensaje = " Se ha reprogramado la cita: " + actividad;
				try {
					EnviaCorreo.enviar(dic.getDscorreoelectronico(), mensaje,
							"Cita reprogramada. ", false);
				} catch (Exception e) {
					e.printStackTrace(System.out);
				}
			}
		}
	}
	
	/**
	 * Valida los campos acorde a reglas de negocio.
	 * @param cie
	 * @return
	 */
	private CalendarioCitaEnvio validarNegocio(CalendarioCitaEnvio cie){
		
		CalendarioCita cc = cie.getDatocita().getCalendarioCita();

		CalendarioCitaEnvio respuesta = new CalendarioCitaEnvio();
		ArrayList<CodigoError> errores = new ArrayList<CodigoError>();
		CodigoError error = null;

		Timestamp fechainicio = cc.getFechainicio();
		Timestamp fechatermino = cc.getFechatermino();
		
		if(fechainicio == null){
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E57B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E57B2);
			errores.add(error);
			respuesta.setExito(false);
		}

		if(fechatermino == null){
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E57B3);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E57B3);
			errores.add(error);
			respuesta.setExito(false);
		}
		
		if (fechainicio != null && fechatermino != null) {
			if (fechatermino.before(fechainicio)) {
				error = new CodigoError();
				error.setCodigo(ErrorCodigoMensaje.CODIGO_E57B1);
				error.setMensaje(ErrorCodigoMensaje.MENSAJE_E57B1);
				errores.add(error);
				respuesta.setExito(false);
			}
		}
		respuesta.setErrores(errores);
		return respuesta;
	}
}
