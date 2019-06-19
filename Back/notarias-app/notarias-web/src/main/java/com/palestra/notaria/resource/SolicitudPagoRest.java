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

import com.palestra.notaria.bo.DatoFiscalPagoBo;
import com.palestra.notaria.bo.DatoPagoBo;
import com.palestra.notaria.bo.ElementoCatalogoBo;
import com.palestra.notaria.bo.SolicitudPagoBo;
import com.palestra.notaria.bo.impl.DatoFiscalPagoBoImpl;
import com.palestra.notaria.bo.impl.DatoPagoBoImpl;
import com.palestra.notaria.bo.impl.ElementoCatalogoBoImpl;
import com.palestra.notaria.bo.impl.SolicitudPagoBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.SolicitudPagoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Catalogo;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.DatoFiscalPago;
import com.palestra.notaria.modelo.DatoPago;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.SolicitudPago;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/solicitudAnticipo")
public class SolicitudPagoRest {

	static Logger logger = Logger.getLogger(SolicitudPagoRest.class);

	private SolicitudPagoBo solicitudPagoBo = new SolicitudPagoBoImpl();
	private BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();
	private DatoFiscalPagoBo datoFiscalPagoBo = new DatoFiscalPagoBoImpl();
	private DatoPagoBo datoPagoBo = new DatoPagoBoImpl();
	private ElementoCatalogoBo elementoCatalogoBo = new ElementoCatalogoBoImpl();

	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public SolicitudPagoEnvio obtenerPorIdCompleto(SolicitudPagoEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		SolicitudPagoEnvio respuesta = new SolicitudPagoEnvio();
		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getSolicitudPago() == null
				|| envio.getSolicitudPago().getIdsolpago() == null
				|| envio.getSolicitudPago().getIdsolpago().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {

			try {
				SolicitudPago solicitudPago = this.solicitudPagoBo
						.obtenerPorIdCompleto(envio.getSolicitudPago()
								.getIdsolpago());
				if(solicitudPago!=null){
					this.asignarImporteSaldo(solicitudPago);
				}

				respuesta.setSolicitudPago(solicitudPago);
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
	@Path("/guardarSolicitud")
	@Produces({ "application/json", "application/xml" })
	public SolicitudPagoEnvio guardarSolicitud(SolicitudPagoEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		SolicitudPagoEnvio respuesta = new SolicitudPagoEnvio();
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		respuesta.setErrores(listaErrores);
		CodigoError error;
		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getSolicitudPago() == null
				|| envio.getExpediente() == null
				|| envio.getExpediente().getIdexpediente() == null
				|| envio.getExpediente().getIdexpediente().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (envio.getSolicitudPago().getValor() == null) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E54B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E54B1);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if (envio.getSolicitudPago().getConcepto() == null
				|| envio.getSolicitudPago().getConcepto().trim().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E54B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E54B2);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}

		if (!respuesta.isExito()) {
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {

			try {
				Date fecha = new Date();
				if (envio.getSolicitudPago().getIdsolpago() != null
						&& !envio.getSolicitudPago().getIdsolpago().trim()
								.isEmpty()) {
					SolicitudPago solicitudPago = envio.getSolicitudPago();
					solicitudPago.setTmstmp(new Timestamp(fecha.getTime()));
					solicitudPago.setExpediente(envio.getExpediente());
					solicitudPago = this.solicitudPagoBo.update(envio
							.getSolicitudPago());
					if (solicitudPago != null) {
						respuesta
								.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						this.bitacoraGeneralHelper.registrarEnBitacora(envio
								.getUsuario().getIdusuario(), envio
								.getExpediente().getIdexpediente(), null,
								"Solicitud Pago",
								Constantes.OPERACION_ACTUALIZACION,
								"Se actualiza una solicitud de pago");
					} else {
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
						respuesta.setExito(false);
					}
				} else {
					SolicitudPago solicitudPago = envio.getSolicitudPago();
					solicitudPago.setIdsolpago(GeneradorId
							.generaId(solicitudPago));
					solicitudPago.setIdsesion(envio.getUsuario()
							.getIdsesionactual());
					solicitudPago.setTmstmp(new Timestamp(fecha.getTime()));
					solicitudPago.setExpediente(envio.getExpediente());

					solicitudPago = this.solicitudPagoBo.save(solicitudPago);
					if (solicitudPago != null) {
						// solicitudPago.setExpediente(null);
						// solicitudPago.setListaDatosFiscalPago(null);
						respuesta
								.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						// respuesta.setSolicitudPago(solicitudPago);
						this.bitacoraGeneralHelper.registrarEnBitacora(envio
								.getUsuario().getIdusuario(), envio
								.getExpediente().getIdexpediente(), null,
								"Solicitud Pago",
								Constantes.OPERACION_REGISTRO,
								"Se crea una solicitud de pago");
					} else {
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
						respuesta.setExito(false);
					}
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
	@Path("/eliminarSolicitud")
	@Produces({ "application/json", "application/xml" })
	public SolicitudPagoEnvio eliminarSolicitud(SolicitudPagoEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		SolicitudPagoEnvio respuesta = new SolicitudPagoEnvio();

		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getSolicitudPago() == null
				|| envio.getExpediente() == null
				|| envio.getExpediente().getIdexpediente() == null
				|| envio.getExpediente().getIdexpediente().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {

			try {

				boolean b = this.solicitudPagoBo.eliminarSolicitud(envio
						.getSolicitudPago(), envio.getUsuario().getIdusuario(),
						envio.getExpediente().getIdexpediente());

				if (b) {
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);

				} else {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
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
	@Path("/guardarDatosPagador")
	@Produces({ "application/json", "application/xml" })
	public SolicitudPagoEnvio guardarDatosPagador(SolicitudPagoEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		SolicitudPagoEnvio respuesta = new SolicitudPagoEnvio();
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		respuesta.setErrores(listaErrores);
		CodigoError error;
		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getExpediente() == null
				|| envio.getDatosPagador() == null
				|| envio.getSolicitudPago() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (envio.getDatosPagador().getDsrfc() == null
				|| envio.getDatosPagador().getDsrfc().trim().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E55B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E55B1);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		// TODO: actualmente no se envia desde la vista la curp
		// if(envio.getDatosPagador().getDscurp()==null ||
		// envio.getDatosPagador().getDscurp().trim().isEmpty()){
		// error = new CodigoError();
		// error.setCodigo(ErrorCodigoMensaje.CODIGO_E55B2);
		// error.setMensaje(ErrorCodigoMensaje.MENSAJE_E55B2);
		// respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
		// respuesta.setExito(false);
		// listaErrores.add(error);
		// }
		if (envio.getDatoNombrePagador() == null
				|| envio.getDatoNombrePagador().getNombreCompletoPagador() == null
				|| envio.getDatoNombrePagador().getNombreCompletoPagador()
						.trim().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E55B3);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E55B3);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if (!respuesta.isExito()) {
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {

			try {
				Date fecha = new Date();
				DatoFiscalPago dfp = envio.getDatosPagador();
				dfp.setSolicitudPago(envio.getSolicitudPago());
				/** si se trae el id compareciente se asigna al dato fiscal pago **/
				if (envio.getDatoNombrePagador().getIdCompareciente() != null) {
					Compareciente compareciente = new Compareciente();
					compareciente.setIdcompareciente(envio
							.getDatoNombrePagador().getIdCompareciente());
					dfp.setCompareciente(compareciente);
				}
				/** asignar nombre del pagador **/
				dfp.setDsnombrepagador(envio.getDatoNombrePagador()
						.getNombreCompletoPagador());
				if (envio.getDatosPagador().getIddatofiscapago() != null
						&& !envio.getDatosPagador().getIddatofiscapago().trim()
								.isEmpty()) {
					dfp.setTmstmp(new Timestamp(fecha.getTime()));
					dfp.setIdsesion(envio.getUsuario().getIdsesionactual());
					/** actualizar el datoFiscalPago **/
					dfp = this.datoFiscalPagoBo.update(dfp);

					if (dfp != null) {
						respuesta
								.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						this.bitacoraGeneralHelper.registrarEnBitacora(envio
								.getUsuario().getIdusuario(), envio
								.getExpediente().getIdexpediente(), null,
								"DatoFiscalPago",
								Constantes.OPERACION_ACTUALIZACION,
								"Se actualiza una DatoFiscalPago");
					} else {
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
						respuesta.setExito(false);
					}
				} else {
					// SolicitudPago sp =
					// envio.getDatosPagador().getSolicitudPago();
					// sp.setExpediente(null);
					// DatoFiscalPago dfp = envio.getDatosPagador();
					Integer i = this.datoFiscalPagoBo
							.obtenerNumeroPagador(envio.getSolicitudPago()
									.getIdsolpago());
					if (i == null)
						i = 1;
					else
						i++;
					dfp.setInnumpago(i);
					dfp.setIddatofiscapago(GeneradorId.generaId(dfp));
					// **** NOTA:
					// es el numero de pagador en una transaccion, si un pago
					// tiene un monto de 10,000 y se divide entre 2
					// pagadores entonces el numpago del primer pagador es 1 y
					// asi
					// dfp.setInnumpago(0);
					dfp.setTmstmp(new Timestamp(fecha.getTime()));
					dfp.setIdsesion(envio.getUsuario().getIdsesionactual());
					/** asignar estatus pendiente **/
					dfp.setStatus(this
							.obtenerStatusPorNombre("Pendiente de Pago"));
					/** guardar el datoFiscalPago **/
					dfp = this.datoFiscalPagoBo.save(dfp);
					if (dfp != null) {
						DatoFiscalPago newDato = new DatoFiscalPago();
						newDato.setIddatofiscapago(dfp.getIddatofiscapago());
						respuesta.setDatosPagador(newDato);
						respuesta
								.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						this.bitacoraGeneralHelper.registrarEnBitacora(envio
								.getUsuario().getIdusuario(), envio
								.getExpediente().getIdexpediente(), null,
								"DatoFiscalPago",
								Constantes.OPERACION_REGISTRO,
								"Se crea una DatoFiscalPago");
					} else {
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
						respuesta.setExito(false);
					}
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
	@Path("/eliminarPagador")
	@Produces({ "application/json", "application/xml" })
	public SolicitudPagoEnvio eliminarPagador(SolicitudPagoEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		SolicitudPagoEnvio respuesta = new SolicitudPagoEnvio();

		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getExpediente() == null
				|| envio.getDatosPagador() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {

			try {

				boolean b = this.datoFiscalPagoBo.eliminarPagador(envio
						.getDatosPagador(), envio.getUsuario().getIdusuario(),
						envio.getExpediente().getIdexpediente());

				if (b) {
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);

				} else {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
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
	@Path("/guardarDatosPago")
	@Produces({ "application/json", "application/xml" })
	public SolicitudPagoEnvio guardarDatosPago(SolicitudPagoEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		SolicitudPagoEnvio respuesta = new SolicitudPagoEnvio();
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		respuesta.setErrores(listaErrores);
		CodigoError error;
		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getExpediente() == null || envio.getDatoPago() == null
				|| envio.getDatosPagador() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (envio.getDatoPago().getFacturafolio() == null
				|| envio.getDatoPago().getFacturafolio().trim().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E56B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E56B1);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if (envio.getDatoPago().getFacturaserie() == null
				|| envio.getDatoPago().getFacturaserie().trim().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E56B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E56B2);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if (envio.getDatoPago().getMediopago() == null) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E56B3);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E56B3);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if (envio.getDatoPago().getImportepago() == null) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E55B4);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E55B4);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		// TODO: validacion cuando se tenga funcionalidad de guardar xml-
		// if(envio.getDatoPago().getRutaarchivoxml()==null ||
		// envio.getDatoPago().getRutaarchivoxml().trim().isEmpty()){
		// error = new CodigoError();
		// error.setCodigo(ErrorCodigoMensaje.CODIGO_E56B4);
		// error.setMensaje(ErrorCodigoMensaje.MENSAJE_E56B4);
		// respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
		// respuesta.setExito(false);
		// listaErrores.add(error);
		// }

		if (!respuesta.isExito()) {
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {

			try {
				Date fecha = new Date();
				// DatoFiscalPago dfp = envio.getDatosPagador();
				DatoPago dp = envio.getDatoPago();
				dp.setDatofiscapago(envio.getDatosPagador());

				if (envio.getDatoPago().getIddatopago() != null
						&& !envio.getDatoPago().getIddatopago().trim()
								.isEmpty()) {

					dp.setUsuarioelaboro(envio.getUsuario());
					dp.setIdsesion(envio.getUsuario().getIdsesionactual());
					dp = this.datoPagoBo.update(dp);
					if (dp != null) {
						respuesta
								.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						this.bitacoraGeneralHelper.registrarEnBitacora(envio
								.getUsuario().getIdusuario(), envio
								.getExpediente().getIdexpediente(), null,
								"DatoPago", Constantes.OPERACION_ACTUALIZACION,
								"Se actualiza una DatoPago");
					} else {
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
						respuesta.setExito(false);
					}
				} else {

					// dfp.setSolicitudPago(null);
					// dfp.setListaDatoPago(null);

					// DatoPago dp = envio.getDatoPago();
					dp.setIddatopago(GeneradorId.generaId(dp));
					dp.setTmstmp(new Timestamp(fecha.getTime()));
					dp.setUsuarioelaboro(envio.getUsuario());
					dp.setIdsesion(envio.getUsuario().getIdsesionactual());
					ElementoCatalogo status = this
							.obtenerStatusPorNombre("Pagado");
					Boolean b = this.datoPagoBo.guardarDatoPago(dp, envio
							.getDatosPagador().getIddatofiscapago(), status);

					if (b) {
						respuesta
								.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						this.bitacoraGeneralHelper.registrarEnBitacora(envio
								.getUsuario().getIdusuario(), envio
								.getExpediente().getIdexpediente(), null,
								"DatoPago", Constantes.OPERACION_REGISTRO,
								"Se crea una DatoPago");
					} else {
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
						respuesta.setExito(false);
					}
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
	@Path("/eliminarDatosPago")
	@Produces({ "application/json", "application/xml" })
	public SolicitudPagoEnvio eliminarDatosPago(SolicitudPagoEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		SolicitudPagoEnvio respuesta = new SolicitudPagoEnvio();
		// ArrayList<CodigoError> listaErrores = new ArrayList<>();
		// respuesta.setErrores(listaErrores);
		// CodigoError error;
		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getExpediente() == null || envio.getDatoPago() == null
				|| envio.getDatoPago().getIddatopago() == null
				|| envio.getExpediente() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {

			try {

				boolean b = this.datoPagoBo.delete(envio.getDatoPago());

				if (b) {
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				} else {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
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
	@Path("/obtenerSolicitudesPorExp")
	@Produces({ "application/json", "application/xml" })
	public SolicitudPagoEnvio obtenerSolicitudesPorExp(SolicitudPagoEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		SolicitudPagoEnvio respuesta = new SolicitudPagoEnvio();
		if (envio == null || envio.getUsuario() == null
				|| envio.getUsuario().getIdusuario() == null
				|| envio.getUsuario().getIdsesionactual() == null
				|| envio.getExpediente()==null || envio.getExpediente().getIdexpediente()==null || envio.getExpediente().getIdexpediente().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(), envio
				.getUsuario().getIdusuario())) {

			try {

				List<SolicitudPago> lista = this.solicitudPagoBo.obtenerSolicitudesporExp(envio.getExpediente().getIdexpediente());
				
				if(lista!=null){
					for(SolicitudPago sp: lista){
						this.asignarImporteSaldo(sp);
					}
					respuesta.setListaSolicitudes(new ArrayList<SolicitudPago>(lista));
				}else{
					respuesta.setListaSolicitudes(new ArrayList<SolicitudPago>());
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
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
	
	/**
	 * Calcula el importe y se lo asigna al objeto solicitud pago.
	 *   Importe = suma de importe de datoPago - solicitudPago.valor
	 * @param sp
	 * @throws NotariaException 
	 */
	private void asignarImporteSaldo(SolicitudPago sp) throws NotariaException{
		Double total = this.datoPagoBo.sumaImportePagoPorSoliciutd(sp.getIdsolpago());
		if(total!=null && sp!=null && sp.getValor()!=null){
			Double importeSaldo = total - sp.getValor();
			sp.setImportesaldo(importeSaldo);
		}
		else{
			sp.setImportesaldo(0.0 - sp.getValor());
		}
	}

	private ElementoCatalogo obtenerStatusPorNombre(String status) throws NotariaException {
		Catalogo catalogo = new Catalogo();
		catalogo.setDsnombre("status_anticipo");
		List<ElementoCatalogo> elementos;
			elementos = elementoCatalogoBo.listarPorCatalogo(catalogo);
			if (elementos != null && !elementos.isEmpty()) {
				for (ElementoCatalogo ec : elementos) {
					if (ec.getDselemento().equals(status)) {
						return ec;
					}
				}
			}
			/** si no se encontro el elemento retornar nullo **/
			return null;
	}
}
