package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.BitacoraFirmaBo;
import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.impl.BitacoraFirmaBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.BitacoraFirmaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.BitacoraFirma;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.util.NotariaUtils;

/**
 * Clase que atiende las peticiones CRUD para BitacoraFirma.
 * 
 * @author sofia
 * 
 */
@Path("/bitacorafirma")
public class BitacoraFirmaRest {

	static Logger logger = Logger.getLogger(BitacoraFirmaRest.class);
	private BitacoraFirmaBo bitacoraFirmaBo = null;
	private BitacoraGeneralHelper bitacoraGeneralHelper = null;
	private ComparecienteBo comparecienteBo = null;
	private EscrituraBo escrituraBo = null;

	/**
	 * Filtra los compracientes de los actos que conforman la escritura dada.
	 * 
	 * @param bfe
	 * @return
	 */
	@POST
	@Path("/comparecientes")
	@Produces(MediaType.APPLICATION_JSON)
	public BitacoraFirmaEnvio comparecientePorActoEscritura(
			BitacoraFirmaEnvio bfe) {

		BitacoraFirmaEnvio respuesta = new BitacoraFirmaEnvio();
		try {
			if (bfe.getEscritura() == null
					|| bfe.getEscritura().getIdescritura() == null
					|| bfe.getEscritura().getIdescritura().isEmpty()
					|| NotariaUtils.faltanRequeridosUsuario(bfe)) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if (NotariaUtils.isSesionValida(bfe.getUsuario()
					.getIdsesionactual(), bfe.getUsuario().getIdusuario())) {

				comparecienteBo = new ComparecienteBoImpl();
				List<Compareciente> lista = this.comparecienteBo
						.disponiblesPorActoEscritura(bfe.getEscritura()
								.getIdescritura());
				respuesta.setListaComparecientes(new ArrayList<Compareciente>(
						lista));
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;

			} else {
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				respuesta.setExito(false);
				return respuesta;
			}

		} catch(NotariaException e){
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public BitacoraFirmaEnvio getBitacoraFirma(BitacoraFirmaEnvio bfe) {

		BitacoraFirmaEnvio respuesta = new BitacoraFirmaEnvio();
		try {
			if (NotariaUtils.faltanRequeridosUsuario(bfe)) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if (NotariaUtils.isSesionValida(bfe.getUsuario()
					.getIdsesionactual(), bfe.getUsuario().getIdusuario())) {
				bitacoraFirmaBo = new BitacoraFirmaBoImpl();
				if (bfe.getBitacoraFirma() == null) {
					BitacoraFirma bitacoraFirma = new BitacoraFirma();
					bfe.setBitacoraFirma(bitacoraFirma);
				}
				List<BitacoraFirma> lista = this.bitacoraFirmaBo
						.obtenerListaBitacoraFirma(bfe.getBitacoraFirma());
				respuesta.setListaBitacora(new ArrayList<BitacoraFirma>(lista));
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;

			} else {
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				respuesta.setExito(false);
				return respuesta;
			}

		} catch(NotariaException e){
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public BitacoraFirmaEnvio guardar(BitacoraFirmaEnvio bfe) {
		BitacoraFirmaEnvio respuesta = new BitacoraFirmaEnvio();
		try {
			if (bfe.getEscritura() == null
					|| bfe.getEscritura().getIdescritura() == null
					|| bfe.getEscritura().getIdescritura().isEmpty()
					|| bfe.getEscritura().getExpediente() == null
					|| bfe.getEscritura().getExpediente().getIdexpediente() == null
					|| bfe.getEscritura().getExpediente().getIdexpediente()
							.isEmpty() || NotariaUtils.faltanRequeridosUsuario(bfe)) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if (bfe.getComparecientesSelected() == null
					|| bfe.getComparecientesSelected().isEmpty()) {
				respuesta
						.setEstatus("Se debe seleccionar al menos un compareciente");
				respuesta.setExito(false);
				return respuesta;
			}
			if (bfe.getIsfirmaditto() == null) {
				respuesta.setEstatus("Firma ditto es requerido");
				respuesta.setExito(false);
				return respuesta;
			}
			if (NotariaUtils.isSesionValida(bfe.getUsuario()
					.getIdsesionactual(), bfe.getUsuario().getIdusuario())) {

				/** id expediente requerido para bitacora, tomado del envio. **/
				String idExpediente = bfe.getEscritura().getExpediente()
						.getIdexpediente();
				this.bitacoraFirmaBo = new BitacoraFirmaBoImpl();

				BitacoraFirmaEnvio validacion = this
						.validaComparecientesRepetidos(bfe
								.getComparecientesSelected());
				if (validacion != null) {
					return validacion;
				}

				/** registrar bitacora firma **/
				Boolean bsu = this.bitacoraFirmaBo.saveUpdateBitacora(bfe
						.getComparecientesSelected(), bfe.getUsuario(), bfe
						.getEscritura().getIdescritura(), bfe.getEscritura()
						.getExpediente().getIdexpediente());
				if(!bsu){
					respuesta.setExito(false);
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					return respuesta;
				}

				/** actualizar la escritura asignando la firma de ditto **/
				this.escrituraBo = new EscrituraBoImpl();
				Boolean b = this.escrituraBo
						.setFirmaDittoByEscrituraId(bfe.getEscritura()
								.getIdescritura(), bfe.getIsfirmaditto());
				if (!b) {
					respuesta.setExito(false);
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
					return respuesta;
				}

				/** registrar en bitacora, el actualización de escritura **/
				bitacoraGeneralHelper = new BitacoraGeneralHelper();
				bitacoraGeneralHelper.registrarEnBitacora(bfe.getUsuario()
						.getIdusuario(), idExpediente, null, "Escritura",
						Constantes.OPERACION_REGISTRO,
						"Se asigna firma ditto de escritura.");

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
	 * Valida que de la lista de comparecientes dados no este repetidos
	 * 
	 * @param representados
	 * @return
	 */
	private BitacoraFirmaEnvio validaComparecientesRepetidos(
			ArrayList<Compareciente> comparecientes) {

		BitacoraFirmaEnvio respuesta = new BitacoraFirmaEnvio();

		ArrayList<Compareciente> conDuplicados = comparecientes;
		if (conDuplicados != null && !conDuplicados.isEmpty()) {
			// add elements to sinDuplicados, including duplicate
			HashSet<Compareciente> hs = new HashSet<Compareciente>();
			hs.addAll(conDuplicados);
			ArrayList<Compareciente> sinDuplicados = new ArrayList<Compareciente>();
			sinDuplicados.addAll(hs);
			if (sinDuplicados != null && !sinDuplicados.isEmpty()) {
				if (sinDuplicados.size() != conDuplicados.size()) {
					respuesta.setExito(false);
					respuesta
							.setEstatus("Existen comparecientes repetidos en la lista dada");
					return respuesta;
				}
			}
		}
		/** si no hubo repetidos retornar nullo . **/
		return null;
	}

}