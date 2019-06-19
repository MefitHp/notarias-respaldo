package com.palestra.notaria.resource;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jackson.*;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ComparecienteAutorizanteBo;
import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.ComparecienteConyugeBo;
import com.palestra.notaria.bo.ComparecienteHijoBo;
import com.palestra.notaria.bo.ComparecienteRepresentanteBo;
import com.palestra.notaria.bo.ContactoBo;
import com.palestra.notaria.bo.DomicilioBo;
import com.palestra.notaria.bo.ElementoCatalogoBo;
import com.palestra.notaria.bo.FirmaBo;
import com.palestra.notaria.bo.PersonaBo;
import com.palestra.notaria.bo.TipoComparecienteBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteAutorizanteBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteConyugeBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteHijoBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteRepresentanteBoImpl;
import com.palestra.notaria.bo.impl.ContactoBoImpl;
import com.palestra.notaria.bo.impl.DomicilioBoImpl;
import com.palestra.notaria.bo.impl.ElementoCatalogoBoImpl;
import com.palestra.notaria.bo.impl.FirmaBoImp;
import com.palestra.notaria.bo.impl.PersonaBoImpl;
import com.palestra.notaria.bo.impl.TipoComparecienteBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoCompareciente;
import com.palestra.notaria.envio.ComparecienteEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteAutorizante;
import com.palestra.notaria.modelo.ComparecienteAutorizantePK;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notaria.modelo.ComparecienteHijo;
import com.palestra.notaria.modelo.ComparecienteRepresentante;
import com.palestra.notaria.modelo.Contacto;
import com.palestra.notaria.modelo.Domicilio;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Firma;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.TipoCompareciente;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;
import com.palestra.notarias.enums.EnumVariables;

@Path("/compareciente")
public class ComparecienteRest {

	static Logger logger = Logger.getLogger(ComparecienteRest.class);
	private ComparecienteBo comparecienteBo;
	private ComparecienteRepresentanteBo comparecienteRepresentanteBo;
	private ComparecienteHijoBo comparecienteHijoBo;
	private ActoBo actoBo;
	private BitacoraGeneralHelper bitacoraGeneralHelper = null;
	private ComparecienteConyugeBo conyugeBo;

	public ComparecienteRest() {
		comparecienteBo = new ComparecienteBoImpl();
		comparecienteRepresentanteBo = new ComparecienteRepresentanteBoImpl();
		actoBo = new ActoBoImpl();
		bitacoraGeneralHelper = new BitacoraGeneralHelper();
		conyugeBo = new ComparecienteConyugeBoImpl();
	}

	@POST
	@Path("/listarDomiciliosDeActo")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio listarDomiciliosPorActo(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {
			try {
				DomicilioBo domicilioBo = new DomicilioBoImpl();
				respuesta.setDomiciliosDeActo(domicilioBo
						.listarDomiciliosDeActo(ce.getActo().getIdacto()));
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			} catch (NotariaException e) {
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
	@Path("/listarPorTipoCompareciente")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio listarPorTipo(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {
			try {
				List<Compareciente> lista = comparecienteBo
						.listadoComparecientes(ce.getCompareciente(),
								ce.getTipoCompareciente());
				if (lista == null) {
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
					respuesta.setExito(false);
					return respuesta;
				}
				for (Compareciente c : lista) {
					c.getActo().getExpediente().setTramite(null);
					//c.getActo().getExpediente().setListaComentarios(null);
				}
				respuesta.setComparecienteList(new ArrayList<Compareciente>(
						lista));
				respuesta.setExito(true);
				return respuesta;
			} catch (NotariaException e) {
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
	@Path("/guardaAutorizante")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio guardaAutorizante(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {
			if (ce.getCompareciente().getIdcompareciente()
					.equals(ce.getAutorizante().getIdcompareciente())) {
				respuesta.setExito(false);
				respuesta
						.setEstatus(Constantes.ESTATUS_ERROR_COMPARECIENTE_DUPLICADO);
				return respuesta;
			}

			// si el autorizante no existe se guarda
			if (ce.getAutorizante().getIdcompareciente() == null) {
				ce.getAutorizante().setIdsesion(
						ce.getUsuario().getIdsesionactual());
				ce.getAutorizante().setIdcompareciente(
						GeneradorId.generaId(ce.getAutorizante()));
				ce.getAutorizante().getPersona()
						.setIdsesion(ce.getUsuario().getIdsesionactual());
				ce.getAutorizante()
						.getPersona()
						.setIdpersona(
								GeneradorId.generaId(ce.getAutorizante()
										.getPersona()));
				ce.getAutorizante().setTipoCompareciente(
						new TipoCompareciente(Constantes.ID_AUTORIZANTE));
				try {
					ce.setAutorizante(this.comparecienteBo.save(ce
							.getAutorizante()));
				} catch (NotariaException e) {
					e.printStackTrace(System.out);
					respuesta.setEstatus(e.getMessage());
					respuesta.setExito(false);
					return respuesta;
				}
			}
			ComparecienteAutorizantePK id = new ComparecienteAutorizantePK();
			id.setIdautorizante(ce.getAutorizante().getIdcompareciente());
			id.setIdcompareciente(ce.getCompareciente().getIdcompareciente());

			ComparecienteAutorizanteBo compAutorizanteBo = new ComparecienteAutorizanteBoImpl();

			// se busca si existe ya un autorizante y compareciente asociados
			try {
				if (compAutorizanteBo.findById(id) != null) {
					respuesta
							.setEstatus("ya existe este autorizante para el otorgante");
					respuesta.setExito(false);
					return respuesta;
				}
				ComparecienteAutorizante compAut = new ComparecienteAutorizante();
				compAut.setIdsesion(ce.getUsuario().getIdsesionactual());
				compAut.setId(id);

				compAutorizanteBo.save(compAut);

			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/eliminarAutorizante")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio eliminarAutorizante(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {

			ComparecienteAutorizantePK id = new ComparecienteAutorizantePK();
			id.setIdautorizante(ce.getAutorizante().getIdcompareciente());
			id.setIdcompareciente(ce.getCompareciente().getIdcompareciente());
			ComparecienteAutorizante compAut = new ComparecienteAutorizante();
			compAut.setId(id);
			ComparecienteAutorizanteBo compAutorizanteBo = new ComparecienteAutorizanteBoImpl();
			try {
				compAutorizanteBo.delete(compAut);
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
			respuesta.setExito(true);
			return respuesta;
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/guardarRepresentante")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio guardarRepresentante(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {
			// Compareciente representanteExists =
			// this.comparecienteBo.buscarPorIdCompleto(ce.getRepresentante());
			try {

				if (ce.getCompareciente().getIdcompareciente()
						.equals(ce.getRepresentante().getIdcompareciente())) {
					respuesta.setExito(false);
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_COMPARECIENTE_DUPLICADO);
					return respuesta;
				}
				if (this.comparecienteRepresentanteBo.findBy(
						ce.getCompareciente(), ce.getRepresentante()) != null) {
					respuesta
							.setEstatus("ya existe este representante para el otorgante");
					respuesta.setExito(false);
					return respuesta;
				}
				ComparecienteRepresentante compRep = new ComparecienteRepresentante();
				compRep.setIdcomrep(GeneradorId.generaId(ce.getCompareciente()));
				compRep.setRepresentante(ce.getRepresentante());
				compRep.setRepresentado(ce.getCompareciente());
				compRep.setIdsesion(ce.getUsuario().getIdsesionactual());
				this.comparecienteRepresentanteBo.save(compRep);

			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public ComparecienteEnvio obtenerPorIdCompleto(
			ComparecienteEnvio comparecienteRequest,
			@Context HttpServletRequest request) throws JSONException {

		Compareciente compareciente = comparecienteRequest.getCompareciente();
		ComparecienteEnvio respuesta = new ComparecienteEnvio();

		if (compareciente == null || compareciente.getIdcompareciente() == null
				|| compareciente.getIdcompareciente().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(comparecienteRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(comparecienteRequest.getUsuario()
				.getIdsesionactual(), comparecienteRequest.getUsuario()
				.getIdusuario())) {
			try {
				Compareciente cPersisted = this.comparecienteBo
						.buscarPorIdCompleto(compareciente);
				if (cPersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				respuesta.setCompareciente(cPersisted);
				// List<Compareciente> lsRepresentados =
				// comparecienteRepresentanteBo
				// .findByRepresentadoId(compareciente
				// .getIdcompareciente());
				// if (lsRepresentados != null) {
				// respuesta
				// .setRepresentadosList(new ArrayList<Compareciente>(
				// lsRepresentados));
				// }
				List<Compareciente> lsRepresentantes = comparecienteRepresentanteBo
						.findByRepresentadoId(compareciente
								.getIdcompareciente());
				if (lsRepresentantes != null) {
					respuesta
							.setRepresentantesList(new ArrayList<Compareciente>(
									lsRepresentantes));
				}
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				return respuesta;
			} catch (NotariaException e) {
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
	@Path("/listadoSimple")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio listadoSimple(
			ComparecienteEnvio comparecienteRequest,
			@Context HttpServletRequest request) throws JSONException {

		Compareciente compareciente = comparecienteRequest.getCompareciente();
		ComparecienteEnvio respuesta = new ComparecienteEnvio();

		if (compareciente == null
				|| NotariaUtils.faltanRequeridosUsuario(comparecienteRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(comparecienteRequest.getUsuario()
				.getIdsesionactual(), comparecienteRequest.getUsuario()
				.getIdusuario())) {
			try {
				List<Compareciente> lista = comparecienteBo
						.listadoComparecientes(compareciente);
				if (lista == null) {
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
				} else {
					// 240614 omarete: se setea el tramite y listacomentarios a
					// null
					for (Compareciente c : lista) {
						c.getActo().getExpediente().setTramite(null);
						//c.getActo().getExpediente().setListaComentarios(null);
					}
					respuesta
							.setComparecienteList(new ArrayList<Compareciente>(
									lista));
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				}
				return respuesta;
			} catch (NotariaException e) {
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
	@Path("/listadoCompleto")
	@Produces(MediaType.APPLICATION_JSON)
	
	public ComparecienteEnvio listadoCompleto(
			ComparecienteEnvio comparecienteRequest,
			@Context HttpServletRequest request) throws JSONException {

		Compareciente compareciente = comparecienteRequest.getCompareciente();
		ComparecienteEnvio respuesta = new ComparecienteEnvio();

		if (compareciente == null
				|| NotariaUtils.faltanRequeridosUsuario(comparecienteRequest)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(comparecienteRequest.getUsuario()
				.getIdsesionactual(), comparecienteRequest.getUsuario()
				.getIdusuario())) {
			try {

				List<Compareciente> lista = comparecienteBo
						.listadoComparecientes(compareciente);
				if (lista == null) {
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
					respuesta.setExito(false);
					return respuesta;
				}
				/** Por cada compareciente obtener su lista de representantes **/
				ArrayList<DatoCompareciente> lsDatoCompareciente = this.obtieneDatosCompletos(lista);
				
				respuesta
						.setComparecienteCompletos(new ArrayList<DatoCompareciente>(
								lsDatoCompareciente));
				respuesta.setEscritura(comparecienteBo.obtieneEscritura(compareciente.getActo().getIdacto()));
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);

				return respuesta;
			} catch (NotariaException e) {
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
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio guardar(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		Compareciente comp = ce.getCompareciente();
		try {
			if (comp.getPersona() == null || comp.getActo() == null
					|| comp.getActo().getIdacto() == null
					|| comp.getActo().getIdacto().isEmpty()
					|| NotariaUtils.faltanRequeridosUsuario(ce)) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if (NotariaUtils.isSesionValida(
					ce.getUsuario().getIdsesionactual(), ce.getUsuario()
							.getIdusuario())) {

				respuesta = this.validarRequeridos(ce);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}
				/** asignar propiedades que no se capturan en pantalla **/
				// SE COMENTA PORQUE LO MANDA EL FRONT Y NO DEBERIA ASUMIRSE QUE
				// SI NO HAY TIPO DE COMPARECIENTE SE PONGA
				// REPRESENTANTE
				// if(comp.getTipoCompareciente()==null ||
				// comp.getTipoCompareciente().getIdtipocompareciente() ==
				// null){
				// comp.setTipoCompareciente(new
				// TipoCompareciente(Constantes.ID_REPRESENTANTE));
				// }
				comp.setIdcompareciente(GeneradorId.generaId(ce
						.getCompareciente()));
				comp.setIdsesion(ce.getUsuario().getIdsesionactual());
				comp.setTmstmp(new Timestamp((new Date()).getTime()));
				/** se valida despues de asignar el id. **/
				ComparecienteEnvio validacion = this
						.validaComparecientesRepetidos(comp,
								ce.getRepresentadosList());
				if (validacion != null) {
					return validacion;
				}
				List<ComparecienteRepresentante> listaCR = new ArrayList<ComparecienteRepresentante>();

				Boolean res = this.creaListaCR(listaCR, ce);
				if (!res) {
					respuesta.setExito(false);
					respuesta
							.setEstatus("Error al guardar lista de representados. Falta id de algún representado.");
					return respuesta;
				}

				/** registrar compareciente completo **/
				// @omarete se hace cambio en que si una persona ya esta creada
				// y llega con id entonces solo se actualiza
				// de otra forma se guarda
				Persona persona = comp.getPersona();
				PersonaBo personaBo = new PersonaBoImpl();
				if (persona.getIdpersona() == null) {
					persona.setIdpersona(GeneradorId.generaId(persona));
					persona.setIdsesion(ce.getUsuario().getIdsesionactual());
					persona.setTmstmp(new Timestamp((new Date()).getTime()));
					
					persona = personaBo.save(comp.getPersona());
				} else {
					persona = personaBo.update(persona);
				}
				// SE GUARDA EL CONTACTO
				// if(ce.getContacto()!=null){
				if (ce.getCompareciente().getContacto() != null) {
					ContactoBo contactoBo = new ContactoBoImpl();
					comp.getContacto().setIdsesion(
							ce.getUsuario().getIdsesionactual());
					comp.getContacto().setPersona(persona);
					comp.getContacto().setIdcontacto(
							GeneradorId.generaId(comp.getContacto()));
					comp.setContacto(contactoBo.save(comp.getContacto()));
				}
				// SE GUARDA EL DOMICILIO
				DomicilioBo domicilioBo = new DomicilioBoImpl();
				// @omarete: si no trae iddomicilio se guarda el domicilio
				if (comp.getDomicilio().getIddomicilio() == null) {
					comp.getDomicilio().setIddomicilio(
							GeneradorId.generaId(comp.getDomicilio()));
					comp.getDomicilio().setIdsesion(
							ce.getUsuario().getIdsesionactual());
					comp.setDomicilio(domicilioBo.save(comp.getDomicilio()));
				}else{
					domicilioBo.update(comp.getDomicilio());
				}

				Boolean b = comparecienteBo.registrarCompareciente(comp,
						listaCR);
				if (!b) {
					respuesta.setExito(false);
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					return respuesta;
				}
				// Obtener expediente del acto, es necesario para bitacora
				String idExpediente = actoBo.getExpedienteIdByActoId(comp
						.getActo().getIdacto());
				if (idExpediente == null || idExpediente.isEmpty()) {
					logger.info("No se pudo obtener el idExpediente y por lo que no se registró en bitácora.");
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
				}
				/** registrar en bitacora, el registro de compareciente **/
				bitacoraGeneralHelper.registrarEnBitacora(ce.getUsuario()
						.getIdusuario(), idExpediente, null, "Compareciente",
						Constantes.OPERACION_REGISTRO,
						"Se guarda un compareciente.");

				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;
			} else {
				respuesta.setExito(false);
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				return respuesta;
			}
		} catch (NotariaException e) {
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	@POST
	@Path("/guardarHijo")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio guardarHijo(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		Compareciente comp = ce.getCompareciente();
		Compareciente hijo = ce.getHijo();
		try {
			if (comp.getPersona() == null || comp.getActo() == null
					|| comp.getActo().getIdacto() == null
					|| comp.getActo().getIdacto().isEmpty()
					|| NotariaUtils.faltanRequeridosUsuario(ce)) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if (NotariaUtils.isSesionValida(
					ce.getUsuario().getIdsesionactual(), ce.getUsuario()
							.getIdusuario())) 
			{
				
				ComparecienteBo hijoBo = new ComparecienteBoImpl();
				comparecienteHijoBo = new ComparecienteHijoBoImpl();
				ComparecienteHijo compHijo = new ComparecienteHijo();
				Persona persona = hijo.getPersona();
				PersonaBo personaBo = new PersonaBoImpl();
				TipoCompareciente hijoTipo = new TipoCompareciente();
				hijoTipo.setIdtipocompareciente(Constantes.ID_HIJO);
				persona.setIdsesion(ce.getUsuario().getIdsesionactual());
				persona.setTmstmp(new Timestamp((new Date()).getTime()));
				persona.setDsnombrecompleto(persona.getDsnombre() + " " +persona.getDsapellidopat()+" "+persona.getDsapellidomat());
				if(persona.getIdpersona()==null || persona.getIdpersona().isEmpty()){
					if(persona.getTipopersona()==null){
						ElementoCatalogoBo elementoCatalogo = new ElementoCatalogoBoImpl();
						ElementoCatalogo tipoPersona = elementoCatalogo.buscarXcodigo("PF");
						persona.setTipopersona(tipoPersona);	
					}
					persona.setIdpersona(GeneradorId.generaId(persona));
					persona = personaBo.save(persona);
				}else{
					persona = personaBo.update(persona);
					return respuesta;
				}
				
				
				hijo.setPersona(persona);
				hijo.setActo(comp.getActo());
				hijo.setTipoCompareciente(hijoTipo);
				hijo.setIdcompareciente(GeneradorId.generaId(hijo));
				hijo.setIdsesion(ce.getUsuario().getIdsesionactual());
				hijo.setTmstmp(new Timestamp((new Date()).getTime()));
				hijoBo.save(hijo);
				
				compHijo.setCompareciente(ce.getCompareciente());
				compHijo.setHijo(hijo);
				compHijo.setIdsesion(ce.getUsuario().getIdsesionactual());
				compHijo.setTmstmp(new Timestamp((new Date()).getTime()));
				compHijo.setIdcomhijo(GeneradorId.generaId(compHijo));
				
				comparecienteHijoBo.save(compHijo);
				
				return respuesta;
				
			}else {
				respuesta.setExito(false);
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				return respuesta;
			}
		} catch (NotariaException e) {
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	@POST
	@Path("/eliminarHijo")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio eliminarHijo(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		Compareciente comp = ce.getCompareciente();
		Compareciente hijo = ce.getHijo();
		try {
			if (comp.getPersona() == null || comp.getActo() == null
					|| comp.getActo().getIdacto() == null
					|| comp.getActo().getIdacto().isEmpty()
					|| NotariaUtils.faltanRequeridosUsuario(ce)) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}
			if (NotariaUtils.isSesionValida(
					ce.getUsuario().getIdsesionactual(), ce.getUsuario()
							.getIdusuario())) 
			{
				
				ComparecienteBo hijoBo = new ComparecienteBoImpl();
				comparecienteHijoBo = new ComparecienteHijoBoImpl();
				Persona persona = hijo.getPersona();
				
				ComparecienteHijo compHijoDato = comparecienteHijoBo.findBy(ce.getCompareciente(), ce.getHijo());
				
				if(compHijoDato!=null){
					comparecienteHijoBo.delete(compHijoDato);
					hijoBo.eliminarCompareciente(ce.getHijo());
					PersonaBo personaBo = new PersonaBoImpl();
					persona = personaBo.buscarPorIdCompleto(persona);
					personaBo.eliminaPersona(persona);
				}
				return respuesta;
				
			}else {
				respuesta.setExito(false);
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				return respuesta;
			}
		} catch (NotariaException e) {
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	

	@POST
	@Path("/agregarConyuge")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio guardarConyuge(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		if (NotariaUtils.faltanRequeridosUsuario(ce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {
			try {
				/*if (ce.getCompConyuge().getConyugeCompra().getTratamiento() == null
						|| ce.getCompConyuge().getConyugeCompra()
								.getTratamiento().getIdelemento() == null) {
					respuesta.setEstatus("El tratamiento es requerido");
					respuesta.setExito(false);
					return respuesta;
				}
				if (ce.getCompConyuge().getConyugeCompra().getRegimen() == null
						|| ce.getCompConyuge().getConyugeCompra().getRegimen()
								.getIdelemento() == null) {
					respuesta.setEstatus("El régimen es requerido");
					respuesta.setExito(false);
					return respuesta;
				}
				if (ce.getCompConyuge().getConyugeCompra().getPersona()
						.getDsapellidopat() == null) {
					respuesta.setEstatus("El apellido paterno es requerido");
					respuesta.setExito(false);
					return respuesta;
				}*/
				ComparecienteBo compBo = new ComparecienteBoImpl();
				ComparecienteConyuge compToSave = new ComparecienteConyuge();
				// se guarda el conyuge actual
				
				
				Compareciente conyugeActual = ce.getCompConyuge()
						.getConyugeActual();
				Compareciente conyugeCompra = ce.getCompConyuge()
						.getConyugeCompra();
				
				
					if(conyugeCompra==null || conyugeCompra.getPersona() == null  || conyugeCompra.getPersona().getDsnombre() ==null || conyugeCompra.getPersona().getDsnombrecompleto().equals(""))
					{
						conyugeCompra = null;
					}	
				
				
				if(ce.getCompConyuge()!=null && conyugeActual!=null){
					if(ce.getCompConyuge().getIsmismoconyuge() || (conyugeActual.getPersona() == null  || conyugeActual.getPersona().getDsnombre()==null|| conyugeActual.getPersona().getDsnombrecompleto().equals("")))
					{
						conyugeActual = null;
					}
				}
				
				
				if (conyugeActual == null && conyugeCompra == null) {
					respuesta.setEstatus("es soltero");
					respuesta.setExito(true);
					return respuesta;
				} else if (conyugeActual != null) {
					conyugeActual.setIdsesion(ce.getUsuario()
							.getIdsesionactual());
					conyugeActual.setIdcompareciente(GeneradorId
							.generaId(conyugeActual));
					conyugeActual.getPersona().setIdpersona(
							GeneradorId.generaId(conyugeActual.getPersona()));
					conyugeActual.getPersona().setIdsesion(
							ce.getUsuario().getIdsesionactual());
					// conyugeActual.getPersona().getDomicilio().setIddomicilio(GeneradorId.generaId(conyugeActual.getPersona().getDomicilio()));
					// conyugeActual.getPersona().getDomicilio().setIdsesion(ce.getUsuario().getIdsesionactual());
					conyugeActual.setTipoCompareciente(new TipoCompareciente(
							Constantes.ID_CONYUGE));
					conyugeActual.setActo(ce.getCompConyuge().getSujeto()
							.getActo());
					
					
					
					PersonaBo personaBo = new PersonaBoImpl();
					String sesActual = ce.getUsuario().getIdsesionactual();
					Persona persona = null;
					if(conyugeActual.getPersona().getIdpersona()==null){
						conyugeActual.getPersona().setIdpersona(GeneradorId.generaId(conyugeActual.getPersona()));
						
						ElementoCatalogoBo elementoCatalogo = new ElementoCatalogoBoImpl();
						ElementoCatalogo tipoPersona = elementoCatalogo.buscarXcodigo("PF");
						conyugeActual.getPersona().setTipopersona(tipoPersona);
						

						persona = personaBo.save(conyugeActual.getPersona());
					}else{
						persona = personaBo.update(conyugeActual.getPersona());
					}
					if(conyugeCompra!=null){
						saveContacto(conyugeCompra, persona, sesActual);
						saveDomicilio(conyugeCompra, sesActual);
					}
						
					
					ce.getCompConyuge().setConyugeActual(
							compBo.save(conyugeActual));
					compToSave.setConyugeActual(ce.getCompConyuge()
							.getConyugeActual());
					if (ce.getCompConyuge().getIsmismoconyuge()) {
						compToSave.setConyugeCompra(ce.getCompConyuge()
								.getConyugeActual());
					}
				}
				// se pregunta si el conyuge de la compra es el mismo al actual
				if(conyugeCompra !=null){
					if (!ce.getCompConyuge().getIsmismoconyuge()
							&& ce.getCompConyuge().getConyugeCompra() != null) {
						// obtener comparecientes de persona que llega
						boolean esConyuge = false;
						boolean mismoConyuge = false;
						System.out.println("conyugeCompra "
								+ conyugeCompra.getPersona().getIdpersona());
						String status_conyuge = "";

						if(conyugeCompra.getIdcompareciente()!=null){
							ComparecienteConyuge conyugeFound = conyugeBo.findComparecienteConyuge(conyugeCompra.getIdcompareciente());
							
							if (conyugeFound != null) {

								if (conyugeFound.getConyugeCompra().equals(
										ce.getCompConyuge().getConyugeCompra())) {
									mismoConyuge = true;
									status_conyuge = "es el mismo para actualizar";
								} else {
									esConyuge = true;
									status_conyuge = "La persona ya tiene conyuge";
								}

							}
							
						}
						/*List<Compareciente> list = comparecienteBo
								.getByIdPersona(conyugeCompra.getPersona()
										.getIdpersona());
						String status_conyuge = "";
						for (Compareciente compa : list) {
							// ver si tiene conyuge
							System.out.println("compa "
									+ compa.getIdcompareciente());

							ComparecienteConyuge conyugeFound = conyugeBo
									.findComparecienteConyuge(compa
											.getIdcompareciente());
							if (conyugeFound != null) {

								if (conyugeFound.getConyugeCompra().equals(
										ce.getCompConyuge().getConyugeCompra())) {
									mismoConyuge = true;
									status_conyuge = "es el mismo para actualizar";
								} else {
									esConyuge = true;
									status_conyuge = "La persona ya tiene conyuge";
								}

							}

						}*/
						if (esConyuge) {
							respuesta.setEstatus(status_conyuge);
							respuesta.setExito(false);
							return respuesta;
						}

						conyugeCompra.setIdsesion(ce.getUsuario()
								.getIdsesionactual());
						conyugeCompra.setActo(ce.getCompConyuge().getSujeto()
								.getActo());
						conyugeCompra
						.setTipoCompareciente(new TipoCompareciente(
								Constantes.ID_CONYUGE));

						
						
						PersonaBo personaBo = new PersonaBoImpl();
						String sesActual = ce.getUsuario().getIdsesionactual();
						Persona persona = null;
						if(conyugeCompra.getAmboscompran()==false && conyugeCompra.getRegimen().getDselemento().toUpperCase().equals("SOCIEDAD CONYUGAL")){
							TipoComparecienteBo tcBo = new TipoComparecienteBoImpl();
							TipoCompareciente tipCompa = tcBo.findById(Constantes.ID_AUTORIZANTE);
							conyugeCompra.setTipoCompareciente(tipCompa);
						}
						if (!mismoConyuge) {
							conyugeCompra.setIdcompareciente(GeneradorId.generaId(conyugeCompra));
							conyugeCompra.getPersona().setIdsesion(sesActual);
							if(conyugeCompra.getPersona().getIdpersona()==null){
								conyugeCompra.getPersona().setIdpersona(GeneradorId.generaId(conyugeCompra.getPersona()));
								
								ElementoCatalogoBo elementoCatalogo = new ElementoCatalogoBoImpl();
								ElementoCatalogo tipoPersona = elementoCatalogo.buscarXcodigo("PF");
								conyugeCompra.getPersona().setTipopersona(tipoPersona);
								
								
								persona = personaBo.save(conyugeCompra.getPersona());
							}else{
								persona = personaBo.update(conyugeCompra.getPersona());
							}
								saveContacto(conyugeCompra, persona, sesActual);
								saveDomicilio(conyugeCompra, sesActual);
							ce.getCompConyuge().setConyugeCompra(compBo.save(conyugeCompra));
						} else {
							persona = personaBo.update(conyugeCompra.getPersona());
							saveContacto(conyugeCompra, persona, sesActual);
							saveDomicilio(conyugeCompra, sesActual);
							ce.getCompConyuge().setConyugeCompra(compBo.update(conyugeCompra));
						}
						
						compToSave.setConyugeCompra(ce.getCompConyuge().getConyugeCompra());
					}else{
						conyugeCompra.setIdsesion(ce.getUsuario()
								.getIdsesionactual());
						conyugeCompra.setActo(ce.getCompConyuge().getSujeto()
								.getActo());
						conyugeCompra
						.setTipoCompareciente(new TipoCompareciente(
								Constantes.ID_CONYUGE));
						
						PersonaBo personaBo = new PersonaBoImpl();
						String sesActual = ce.getUsuario().getIdsesionactual();
						Persona persona = null;
						if(conyugeCompra.getAmboscompran()==false && conyugeCompra.getRegimen().getDselemento().toUpperCase().equals("SOCIEDAD CONYUGAL")){
							TipoComparecienteBo tcBo = new TipoComparecienteBoImpl();
							TipoCompareciente tipCompa = tcBo.findById(Constantes.ID_AUTORIZANTE);
							conyugeCompra.setTipoCompareciente(tipCompa);
						}
						
							conyugeCompra.setIdcompareciente(GeneradorId.generaId(conyugeCompra));
							conyugeCompra.getPersona().setIdsesion(sesActual);
							if(conyugeCompra.getPersona().getIdpersona()==null){
								ElementoCatalogoBo elementoCatalogo = new ElementoCatalogoBoImpl();
								ElementoCatalogo tipoPersona = elementoCatalogo.buscarXcodigo("PF");
								conyugeCompra.getPersona().setTipopersona(tipoPersona);
								conyugeCompra.getPersona().setIdpersona(GeneradorId.generaId(conyugeCompra.getPersona()));
								persona = personaBo.save(conyugeCompra.getPersona());
							}else{
								persona = personaBo.update(conyugeCompra.getPersona());
							}
								saveContacto(conyugeCompra, persona, sesActual);
								saveDomicilio(conyugeCompra, sesActual);
								Compareciente cmismo = compBo.save(conyugeCompra);
								ce.getCompConyuge().setConyugeCompra(cmismo);					
						
						
					}
				}
				
				compToSave = ce.getCompConyuge();
				
				
				
				if(ce.getCompConyuge().getConyugeCompra()!=null){
					ce.getCompConyuge()
					.getSujeto()
					.setRegimen(
							ce.getCompConyuge().getConyugeCompra()
									.getRegimen());
					
					ce.getCompConyuge()
							.getSujeto()
							.setAmboscompran(
									ce.getCompConyuge().getConyugeCompra()
											.getAmboscompran());
				}
				
				if(ce.getCompConyuge().getConyugeActual()!=null){
					
					ce.getCompConyuge()
					.getSujeto()
					.setRegimen(
							ce.getCompConyuge().getConyugeActual()
									.getRegimen());
					
					ce.getCompConyuge()
							.getSujeto()
							.setAmboscompran(
									ce.getCompConyuge().getConyugeActual()
											.getAmboscompran());
				}

				// ComparecienteDao compDao = new ComparecienteDaoImpl();
				// compDao.update(ce.getCompConyuge().getSujeto());
				compBo.update(ce.getCompConyuge().getSujeto());

				compToSave.setIdsesion(ce.getUsuario().getIdsesionactual());
				
				if(conyugeCompra != null || conyugeActual !=null){
					
					if(conyugeCompra ==null){
						compToSave.setConyugeCompra(conyugeCompra);
					}
					if(conyugeActual ==null){
						compToSave.setConyugeActual(conyugeActual);
					}
				}
				
				
				
				compToSave.setConyugeCompra(conyugeCompra);
			
				// se guarda el compconyuge
				ComparecienteConyuge compFound = getConyugeBo().findById(
						compToSave.getSujeto().getIdcompareciente());
				
				if (compFound != null) {
					getConyugeBo().update(compToSave);
				} else {
					getConyugeBo().save(compToSave);
				}

				// se invierten los roles de conyuge y sujeto para guardar la
				// otra direccion de la relacion
				// Compareciente conyuge = ce.getCompConyuge().getSujeto();
				// Compareciente sujeto = ce.getCompConyuge().getConyuge();
				// ComparecienteConyuge relacionInversa = new
				// ComparecienteConyuge();
				// relacionInversa.setConyuge(conyuge);
				// relacionInversa.setSujeto(sujeto);
				// relacionInversa.setIdsesion(ce.getUsuario().getIdsesionactual());
				// ce.getCompConyuge().setIdsesion(ce.getUsuario().getIdsesionactual());

				// para actualizar se valida si existe ya un conyuge para el
				// compareciente y viceversa
				// ComparecienteConyuge sujetoExists =
				// getConyugeBo().findById(ce.getCompConyuge().getSujeto().getIdcompareciente());
				// ComparecienteConyuge conyugeExists =
				// getConyugeBo().findById(ce.getCompConyuge().getConyuge().getIdcompareciente());
				// si no existe se hace un registro nuevo
				// if(sujetoExists == null && conyugeExists == null){
				// se guarda la relacion de sujeto y conyuge
				// getConyugeBo().save(ce.getCompConyuge());
				// getConyugeBo().save(relacionInversa);
				// }else if(conyugeExists != null){
				// respuesta.setEstatus("El conyuge seleccionado existe asociado con otro compareciente");
				// respuesta.setExito(false);
				// return respuesta;
				// }else if(sujetoExists != null && conyugeExists == null){

				// si existe un registro de sujeto con conyuge se eliminan los
				// registros anteriores antes de insertar el nuevo
				// se obtiene el conyuge actual del sujeto para eliminarlo
				// ComparecienteConyuge inversaDelete =
				// getConyugeBo().findById(sujetoExists.getConyuge().getIdcompareciente());
				// getConyugeBo().delete(sujetoExists);
				// getConyugeBo().delete(inversaDelete);
				// // se guarda la nueva relacion
				// getConyugeBo().save(ce.getCompConyuge());
				// getConyugeBo().save(relacionInversa);

				// }
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;

		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}

	}
	
	private Contacto saveContacto(Compareciente comp,Persona persona,String sesActual) throws NotariaException{
		
		Contacto contactoreturn = null;
		//VICTOR:ASIGNO CONTACTO A CONYUGE
		if (comp.getContacto() != null) {
			ContactoBo contactoBo = new ContactoBoImpl();
			Contacto tmpcontacto = comp.getContacto();
			tmpcontacto.setIdsesion(sesActual);
			tmpcontacto.setPersona(persona);
			
			if(comp.getContacto().getIdcontacto()!=null){
				contactoreturn = contactoBo.update(tmpcontacto);
				comp.setContacto(contactoreturn);

			}else{
				tmpcontacto.setIdcontacto(GeneradorId.generaId(tmpcontacto));
				contactoreturn = contactoBo.save(tmpcontacto);
				comp.setContacto(contactoreturn);
			}
		}
		
		
		return contactoreturn;
	}
	
	private Domicilio saveDomicilio(Compareciente comp,String sesActual)throws NotariaException{
		//VICTOR:ASIGNO DOMICILIO A CONYUGE
				Domicilio domreturn = null;
				DomicilioBo domicilioBo = new DomicilioBoImpl();
				// @omarete: si no trae iddomicilio se guarda el domicilio
				Domicilio tmpdomicilio = (comp.getDomicilio()!=null)?comp.getDomicilio(): new Domicilio();
				tmpdomicilio.setIdsesion(sesActual);
				if (tmpdomicilio.getIddomicilio() == null) {
						tmpdomicilio.setIddomicilio(
								GeneradorId.generaId(tmpdomicilio));
						domreturn = domicilioBo.save(tmpdomicilio); 
				}else{
						domreturn =domicilioBo.update(tmpdomicilio);	
				}
					comp.setDomicilio(domreturn);
				
		return domreturn;
	}

	@POST
	@Path("/eliminarConyuge")
	@Produces(MediaType.APPLICATION_JSON)
	public ComparecienteEnvio eliminarConyuge(ComparecienteEnvio ce) {
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		if (ce.getCompConyuge() == null
				|| NotariaUtils.faltanRequeridosUsuario(ce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {
			try {
				// se crea un objeto para setear la relacion inversa y
				// eliminarla
				// ComparecienteConyuge relacionInversa = new
				// ComparecienteConyuge();
				// relacionInversa.setConyuge(ce.getCompConyuge().getSujeto());
				// relacionInversa.setSujeto(ce.getCompConyuge().getConyuge());
				// relacionInversa =
				// getConyugeBo().getRelacionInversa(relacionInversa);
				// se eliminan las relaciones
				System.out.println("id sujeto a eliminar conyuge "
						+ ce.getCompConyuge().getSujeto().getIdcompareciente());

				// Por Yomero
				// SETEO EL AMBOS COMPRAN A FALSO
				ce.getCompConyuge().getSujeto().setAmboscompran(false);
				ComparecienteBo compBo = new ComparecienteBoImpl();
				ComparecienteConyuge ceTmp = getConyugeBo().findComparecienteConyuge(ce.getCompConyuge().getSujeto().getIdcompareciente());
				
				ce.getCompConyuge().setIdsesion(ce.getUsuario().getIdsesionactual());
				
				if(ce.getCompConyuge().getConyugeActual() == null && ceTmp.getConyugeCompra()==null){
					getConyugeBo().delete(ce.getCompConyuge());
				}else{
					if(ce.getCompConyuge().getConyugeActual() == null && ceTmp.getConyugeActual()!=null){
						getConyugeBo().update(ce.getCompConyuge());
						compBo.eliminarCompareciente(ceTmp.getConyugeActual());
						
					}
					
					if(ce.getCompConyuge().getConyugeCompra() == null && ceTmp.getConyugeCompra()!=null){
						getConyugeBo().update(ce.getCompConyuge());
						compBo.eliminarCompareciente(ceTmp.getConyugeCompra());
					}
				}
					
				
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
			respuesta.setExito(true);
			return respuesta;

		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}

	}

	@POST
	@Path("/eliminar")
	@Produces({ "application/json", "application/xml" })
	public ComparecienteEnvio eliminar(ComparecienteEnvio ce,
			@Context HttpServletRequest request) throws JSONException {

		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		Compareciente compareciente = ce.getCompareciente();

		if (compareciente == null || compareciente.getIdcompareciente() == null
				|| compareciente.getIdcompareciente().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(ce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {
			try {
				/** obtener el compareciente persistido en bd. **/
				Compareciente cRegistrado = comparecienteBo
						.buscarPorIdCompleto(compareciente);
				if (cRegistrado == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				/**
				 * tomar el id de expediente del acto del compareciente para
				 * bitacora
				 **/
				String idExpediente = cRegistrado.getActo().getExpediente()
						.getIdexpediente();
				// eliminar objeto completo obtenido, la transaccion borra
				// ComparecienteRepresentante, Compareciente, Persona,
				// Domicilio.
				boolean resultado = comparecienteBo
						.eliminarCompareciente(cRegistrado);
				if (!resultado) {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
					return respuesta;
				}
				if (cRegistrado.getRegistroRi() != null
						&& cRegistrado.getRegistroRi().getDsruta() != null
						&& !cRegistrado.getRegistroRi().getDsnombre().isEmpty()) {
					// Borrar el archivo del registroRi del compareciente.
					String rutaArchivo = cRegistrado.getRegistroRi()
							.getDsruta();
					logger.info("===> Archivo de registro ri a eliminar: "
							+ rutaArchivo);
					File ruta = new File(rutaArchivo);
					ruta.delete();
				}
				bitacoraGeneralHelper.registrarEnBitacora(ce.getUsuario()
						.getIdusuario(), idExpediente, null, "Compareciente",
						Constantes.OPERACION_ELIMINACION,
						"Se ha eliminado un compareciente.");
				respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			} catch (NotariaException e) {
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
	public ComparecienteEnvio actualizar(ComparecienteEnvio ce,
			@Context HttpServletRequest request) {

		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		Compareciente compareciente = ce.getCompareciente();

		if (compareciente == null || compareciente.getIdcompareciente() == null
				|| compareciente.getIdcompareciente().isEmpty()
				|| compareciente.getPersona() == null
				|| compareciente.getPersona().getIdpersona() == null
				|| compareciente.getPersona().getIdpersona().isEmpty()
				// || compareciente.getPersona().getDomicilio() == null
				// || compareciente.getPersona().getDomicilio().getIddomicilio()
				// == null
				// ||
				// compareciente.getPersona().getDomicilio().getIddomicilio().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(ce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {
			try {
				respuesta = this.validarRequeridos(ce);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}
				ComparecienteEnvio validacion = this
						.validaComparecientesRepetidos(compareciente,
								ce.getRepresentadosList());
				if (validacion != null) {
					return validacion;
				}

				Compareciente cPersisted = comparecienteBo
						.buscarPorIdCompleto(compareciente);
				if (cPersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}

				/** asignar valores que no se capturan desde pantalla **/
				compareciente.setIdsesion(ce.getUsuario().getIdsesionactual());
				compareciente.setTmstmp(new Timestamp((new Date()).getTime()));

				// TODO: Se cambiara la forma en hacer el update sobre persona y
				// domicilio.

				List<ComparecienteRepresentante> listaCR = new ArrayList<ComparecienteRepresentante>();
				Boolean res = this.creaListaCR(listaCR, ce);
				if (!res) {
					respuesta.setExito(false);
					respuesta
							.setEstatus("Error al guardar lista de representados. Falta id de algún representado.");
					return respuesta;
				}
				/** actualizar la compareciente **/
				// @omarete COMO SE QUITO EL GUARDADO/ACTUALIZACION EN CASCADA
				// SE TIENE QUE HACER A MANO
				PersonaBo personaBo = new PersonaBoImpl();
				personaBo.update(compareciente.getPersona());
				DomicilioBo domicilioBo = new DomicilioBoImpl();

				if (compareciente.getDomicilio().getIddomicilio() == null) {
					compareciente.getDomicilio().setIddomicilio(
							GeneradorId.generaId(compareciente.getDomicilio()));
					compareciente.getDomicilio().setIdsesion(
							ce.getUsuario().getIdsesionactual());
					compareciente.setDomicilio(domicilioBo.save(compareciente
							.getDomicilio()));
				} else {
					domicilioBo.update(compareciente.getDomicilio());
				}

				ContactoBo contactoBo = new ContactoBoImpl();
				if (compareciente.getContacto() != null) {
					if (compareciente.getContacto().getIdcontacto() != null)
						contactoBo.update(compareciente.getContacto());
					else if (compareciente.getContacto().getIdcontacto() == null) {
						compareciente.getContacto().setIdcontacto(
								GeneradorId.generaId(compareciente
										.getContacto()));
						compareciente.getContacto().setIdsesion(
								ce.getUsuario().getIdsesionactual());
						compareciente.getContacto().setPersona(
								compareciente.getPersona());
						contactoBo.save(compareciente.getContacto());
					}
				}
				Boolean b = comparecienteBo.actualizarCompareciente(
						compareciente, listaCR);
				if (!b) {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
					respuesta.setExito(false);
					return respuesta;
				}
				// El idExpiente es obligatorio para la bitacora, obtenerlo a
				// partir del acto.
				String idExpediente = cPersisted.getActo().getExpediente()
						.getIdexpediente();
				/** registrar en bitacora, actualizacion compareciente **/
				bitacoraGeneralHelper.registrarEnBitacora(ce.getUsuario()
						.getIdusuario(), idExpediente, null, "Compareciente",
						Constantes.OPERACION_ACTUALIZACION,
						"Se actualiza un compareciente");
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			} catch (NotariaException e) {
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
	@Path("/actualizarRepresentados")
	@Produces({ "application/json", "application/xml" })
	public ComparecienteEnvio actualizarRepresentados(ComparecienteEnvio ce,
			@Context HttpServletRequest request) {

		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		DatoCompareciente dato = ce.getDatoCompareciente();

		if (dato == null
				|| dato.getCompareciente().getIdcompareciente() == null
				|| dato.getCompareciente().getIdcompareciente().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(ce)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(), ce
				.getUsuario().getIdusuario())) {
			try {
				Compareciente compareciente = dato.getCompareciente();
				Compareciente representante = dato.getRepresentante();
				ComparecienteEnvio validacion = this
						.validaComparecientesRepetidos(compareciente, ce
								.getDatoCompareciente().getRepresentantes());
				if (validacion != null) {
					return validacion;
				}

				Compareciente cPersisted = comparecienteBo
						.buscarPorIdCompleto(compareciente);
				if (cPersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				/** asignar valores que no se capturan desde pantalla **/
				compareciente.setIdsesion(ce.getUsuario().getIdsesionactual());
				compareciente.setTmstmp(new Timestamp((new Date()).getTime()));

				List<ComparecienteRepresentante> listaCR = new ArrayList<ComparecienteRepresentante>();
				Boolean res = this.creaListaCRFromDato(listaCR, ce);
				if (!res) {
					respuesta.setExito(false);
					respuesta
							.setEstatus("Error al guardar lista de representados. Falta id de algún representado.");
					return respuesta;
				}
				/** actualizar la compareciente **/

				// Boolean b = comparecienteBo.actualizarRepresentantes(
				// compareciente, representante,listaCR);
				// if (!b) {
				// respuesta
				// .setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				// respuesta.setExito(false);
				// return respuesta;
				// }
				ComparecienteRepresentante compRepFound = this.comparecienteRepresentanteBo
						.findBy(compareciente, representante);
				if (compRepFound != null) {
					System.out.println("id " + compRepFound);
					this.comparecienteRepresentanteBo.delete(compRepFound);
				} else {
					respuesta.setEstatus("no existe registro a eliminar");
					respuesta.setExito(false);
					return respuesta;
				}

				// El idExpiente es obligatorio para la bitacora, obtenerlo a
				// partir del acto.
				String idExpediente = cPersisted.getActo().getExpediente()
						.getIdexpediente();
				/** registrar en bitacora, actualizacion compareciente **/
				bitacoraGeneralHelper.registrarEnBitacora(ce.getUsuario()
						.getIdusuario(), idExpediente, null, "Compareciente",
						Constantes.OPERACION_ACTUALIZACION,
						"Se actualiza un compareciente");
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				return respuesta;
			} catch (NotariaException e) {
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
	 * Valida los campos que son requeridos a nivel de BD para persistencia
	 * 
	 * @param comparecienteRequest
	 * 
	 * @return comparecienteEnvio con respuesta
	 * 
	 */
	private ComparecienteEnvio validarRequeridos(
			ComparecienteEnvio comparecienteRequest) {
		Compareciente compareciente = comparecienteRequest.getCompareciente();
		ComparecienteEnvio respuesta = new ComparecienteEnvio();
		ArrayList<CodigoError> errores = new ArrayList<CodigoError>();
		CodigoError error = null;

		if (compareciente.getActo() == null
				|| compareciente.getActo().getIdacto() == null
				|| compareciente.getActo().getIdacto().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E10B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E10B2);
			errores.add(error);
			respuesta.setExito(false);
		}
		if (compareciente.getTipoCompareciente() == null
				|| compareciente.getTipoCompareciente()
						.getIdtipocompareciente() == null
				|| compareciente.getTipoCompareciente()
						.getIdtipocompareciente().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E21B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E21B1);
			errores.add(error);
			respuesta.setExito(false);
		}
		if (compareciente.getPersona() == null
				|| compareciente.getPersona().getDsnombre() == null
				|| compareciente.getPersona().getDsnombre().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E21B3);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E21B3);
			errores.add(error);
			respuesta.setExito(false);
		}

		ElementoCatalogoBo elementoCatalogo = new ElementoCatalogoBoImpl();
		ElementoCatalogo tipoPersona = null;
		try {
			tipoPersona = elementoCatalogo.findById(compareciente.getPersona()
					.getTipopersona().getDscodigo());
		} catch (NotariaException e) {
			e.printStackTrace();
		}

		if (tipoPersona != null
				&& !tipoPersona.getDselemento().toUpperCase().equals("MORAL")) {
			if (compareciente.getPersona() == null
					|| compareciente.getPersona().getDsapellidopat() == null
					|| compareciente.getPersona().getDsapellidopat().isEmpty()) {
				error = new CodigoError();
				error.setCodigo(ErrorCodigoMensaje.CODIGO_E21B3);
				error.setMensaje("El apellido paterno es requerido");
				errores.add(error);
				respuesta.setExito(false);
			}
			if (compareciente.getTratamiento() == null) {
				error = new CodigoError();
				error.setCodigo(ErrorCodigoMensaje.CODIGO_E21B3);
				error.setMensaje("El tratamiento es requerido");
				errores.add(error);
				respuesta.setExito(false);
			}
		}
		// if (compareciente.getPersona() == null
		// || compareciente.getPersona().getDomicilio() == null
		// || compareciente.getPersona().getDomicilio().getDsdircompleta() ==
		// null
		// || compareciente.getPersona().getDomicilio().getDsdircompleta()
		// .isEmpty()) {
		// error = new CodigoError();
		// error.setCodigo(ErrorCodigoMensaje.CODIGO_E09B2);
		// error.setMensaje(ErrorCodigoMensaje.MENSAJE_E09B2);
		// errores.add(error);
		// respuesta.setExito(false);
		// }
		// if (compareciente.getPersona() == null
		// || compareciente.getPersona().getDomicilio() == null
		// || compareciente.getPersona().getDomicilio().getIsasistido() == null)
		// {
		// error = new CodigoError();
		// error.setCodigo(ErrorCodigoMensaje.CODIGO_E09B3);
		// error.setMensaje(ErrorCodigoMensaje.MENSAJE_E09B3);
		// errores.add(error);
		// respuesta.setExito(false);
		// }
		respuesta.setErrores(errores);
		return respuesta;
	}

	/**
	 * Crea la lista de ComparecienteRepresentante
	 * 
	 * @param cr
	 * @param ce
	 * @return
	 */
	private boolean creaListaCR(List<ComparecienteRepresentante> listaCR,
			ComparecienteEnvio ce) {

		/** por defecto false, si tiene representandos es true **/
		ce.getCompareciente().setIsrepresentante(false);

		if (ce.getRepresentadosList() != null
				&& ce.getRepresentadosList().size() > 0) {
			// Si se envio lista de representado, indica que el
			// compareciente a guardar
			// es representante.
			ce.getCompareciente().setIsrepresentante(true);
			for (Compareciente cp : ce.getRepresentadosList()) {
				if (cp != null) {
					ComparecienteRepresentante cr = new ComparecienteRepresentante();
					cr.setIdcomrep(GeneradorId.generaId(cr));
					cr.setIdsesion(ce.getUsuario().getIdsesionactual());
					cr.setTmstmp(new Timestamp((new Date()).getTime()));
					/** asingar el representante **/
					cr.setRepresentante(ce.getCompareciente());
					/**
					 * validar que se asigno id compareciente representado
					 **/
					if (cp.getIdcompareciente() == null
							|| cp.getIdcompareciente().isEmpty()) {
						return false;
					}
					/** asignar el representado **/
					cr.setRepresentado(cp);
					listaCR.add(cr);
				}
			}
		}
		return true;
	}

	/**
	 * Crea la lista de ComparecienteRepresentante de un datoCompareciente
	 * 
	 * @param listaCR
	 * @param ce
	 * @return
	 */
	private boolean creaListaCRFromDato(
			List<ComparecienteRepresentante> listaCR, ComparecienteEnvio ce) {

		/** por defecto false, si tiene representandos es true **/
		ce.getDatoCompareciente().getCompareciente().setIsrepresentante(false);

		if (ce.getDatoCompareciente().getRepresentantes() != null
				&& !ce.getDatoCompareciente().getRepresentantes().isEmpty()) {
			// Si se envio lista de representantes, indica que el
			// compareciente a guardar
			// es representado.
			ce.getDatoCompareciente().getCompareciente()
					.setIsrepresentante(true);
			for (Compareciente cp : ce.getDatoCompareciente()
					.getRepresentantes()) {
				if (cp != null) {
					ComparecienteRepresentante cr = new ComparecienteRepresentante();
					cr.setIdcomrep(GeneradorId.generaId(cr));
					cr.setIdsesion(ce.getUsuario().getIdsesionactual());
					cr.setTmstmp(new Timestamp((new Date()).getTime()));
					/** asingar el representante **/
					cr.setRepresentante(ce.getDatoCompareciente()
							.getCompareciente());
					/**
					 * validar que se asigno id compareciente representado
					 **/
					if (cp.getIdcompareciente() == null
							|| cp.getIdcompareciente().isEmpty()) {
						return false;
					}
					/** asignar el representado **/
					cr.setRepresentado(cp);
					listaCR.add(cr);
				}
			}
		}
		return true;
	}

	/**
	 * Valida que en la lista de representados propocionada no existan repetidos
	 * , también que un compareciente no se represente a si mismo.
	 * 
	 * @param representante
	 * @param representados
	 * @return
	 */
	private ComparecienteEnvio validaComparecientesRepetidos(
			Compareciente representante, ArrayList<Compareciente> representados) {

		ComparecienteEnvio respuesta = new ComparecienteEnvio();

		ArrayList<Compareciente> conDuplicados = representados;
		if (conDuplicados != null && !conDuplicados.isEmpty()) {
			// add elements to sinDuplicados, including duplicate
			HashSet<Compareciente> hs = new HashSet<Compareciente>();
			hs.addAll(conDuplicados);
			ArrayList<Compareciente> sinDuplicados = new ArrayList<Compareciente>();
			sinDuplicados.addAll(hs);
			if (!sinDuplicados.isEmpty()) {
				if (sinDuplicados.size() != conDuplicados.size()) {
					respuesta.setExito(false);
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_REPRESNTADOS_DUPLICADOS);
					return respuesta;
				}
			}
			if (representante != null
					&& representante.getIdcompareciente() != null) {
				for (Compareciente c : hs) {
					if (c.getIdcompareciente().equals(
							representante.getIdcompareciente())) {
						respuesta.setExito(false);
						respuesta
								.setEstatus(Constantes.ESTATUS_ERROR_COMPARECIENTE_DUPLICADO);
						return respuesta;
					}
				}
			}
		}
		/** si no hubo repetidos retornar nullo . **/
		return null;
	}

	public ArrayList<DatoCompareciente> obtieneDatosCompletos(List<Compareciente> lista) throws NotariaException{
		ArrayList<DatoCompareciente> lsDatoCompareciente = new ArrayList<DatoCompareciente>();
		for (Compareciente c : lista) {
			// 240614 omarete: se setea el tramite y listacomentarios a
			// null
			c.getActo().getExpediente().setTramite(null);
//			c.getActo().getExpediente().setListaComentarios(null);
			if (c.getContacto() != null) {
				c.getContacto().setPersona(null);
			}
			// se busca si el compareciente tiene conyuge
			ComparecienteConyuge compConyuge = getConyugeBo().findById(
					c.getIdcompareciente());
			// 270614 omarete: se obtienen los autorizantes del
			// otorgantes
			ComparecienteAutorizanteBo compAutBo = new ComparecienteAutorizanteBoImpl();
			List<ComparecienteAutorizante> autorizantes = compAutBo
					.findByCompareciente(c.getIdcompareciente());
			DatoCompareciente dc = new DatoCompareciente();
			dc.setCompareciente(c);
			
			// se setea el conyuge al compareciente
			if (compConyuge != null)
				dc.setConyuge(compConyuge);
			// System.out.println("dato compareciente "+dc.getCompareciente().getPersona().getNacionalidad());
			// System.out.println("dato compareciente "+dc.getCompareciente().getPersona().getNacionalidad().getDselemento());
			if (autorizantes != null) {
				for (ComparecienteAutorizante ca : autorizantes) {
					ca.getAutorizante().setActo(null);
					ca.getAutorizante().setTipoCompareciente(null);
					ca.getAutorizante().setRegistroRi(null);
					dc.getAutorizantes().add(ca.getAutorizante());
				}
			}

			// 260614 omarete: se cambia el query para obtener
			// representantes de cada compareciente y no representados
			List<Compareciente> representantes = comparecienteRepresentanteBo
					.findByRepresentadoId(c.getIdcompareciente());
			
			comparecienteHijoBo = new ComparecienteHijoBoImpl();
			
			List<Compareciente> hijos = comparecienteHijoBo.findByEsposaId(c.getIdcompareciente());
			if(hijos !=null){
				dc.setHijos(hijos);
			}
			if (representantes != null) {
				dc.setRepresentantes(new ArrayList<Compareciente>(
						representantes));
			}
			lsDatoCompareciente.add(dc);
		}
		return lsDatoCompareciente;
	}
	
	public ComparecienteConyugeBo getConyugeBo() {
		return conyugeBo;
	}

}
