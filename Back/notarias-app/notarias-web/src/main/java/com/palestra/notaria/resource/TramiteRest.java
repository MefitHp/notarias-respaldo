package com.palestra.notaria.resource;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.ComentarioBo;
import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.ComponenteBo;
import com.palestra.notaria.bo.ConFormularioBo;
import com.palestra.notaria.bo.ConSubFormularioBo;
import com.palestra.notaria.bo.ContactoBo;
import com.palestra.notaria.bo.DocumentosOriginalesBo;
import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.bo.PersonaBo;
import com.palestra.notaria.bo.TareaPendienteBo;
import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.TramiteUsuarioBo;
import com.palestra.notaria.bo.UsuarioGrupoTrabajoBo;
import com.palestra.notaria.bo.ValorFormularioBo;
import com.palestra.notaria.bo.ValorSubFormularioBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.ComentarioBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.ComponenteBoImpl;
import com.palestra.notaria.bo.impl.ConFormularioBoImpl;
import com.palestra.notaria.bo.impl.ConSubFormularioBoImpl;
import com.palestra.notaria.bo.impl.ContactoBoImpl;
import com.palestra.notaria.bo.impl.DocumentosOriginalesBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.FormularioBoImpl;
import com.palestra.notaria.bo.impl.PersonaBoImpl;
import com.palestra.notaria.bo.impl.TareaPendienteBoImpl;
import com.palestra.notaria.bo.impl.TramiteBoImpl;
import com.palestra.notaria.bo.impl.TramiteUsuarioBoImpl;
import com.palestra.notaria.bo.impl.UsuarioGrupoTrabajoBoImp;
import com.palestra.notaria.bo.impl.ValorFormularioBoImpl;
import com.palestra.notaria.bo.impl.ValorSubFormularioBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.dato.DatoCompareciente;
import com.palestra.notaria.dato.DatoDocumentoTarjeton;
import com.palestra.notaria.envio.TramiteEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.ActoFormulario;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConSubFormulario;
import com.palestra.notaria.modelo.Documento;
import com.palestra.notaria.modelo.DocumentosOriginales;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.TareaPendiente;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;
import com.palestra.notaria.modelo.ValorFormulario;
import com.palestra.notaria.modelo.ValorSubFormulario;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;
import com.sun.jersey.multipart.FormDataParam;

@Path("/tramite")
public class TramiteRest {

	static Logger logger = Logger.getLogger(TramiteRest.class);
	private TramiteBo tramiteBo = new TramiteBoImpl();
	private PersonaBo personaBo = new PersonaBoImpl();
	private TareaPendienteBo tareaPendienteBo = new TareaPendienteBoImpl();
	BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();

	private TramiteEnvio validarRequeridos(TramiteEnvio tramiteEnvio) {
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		TramiteEnvio resp = new TramiteEnvio();
		if (tramiteEnvio == null || tramiteEnvio.getUsuario() == null
				|| tramiteEnvio.getUsuario().getIdusuario() == null
				|| tramiteEnvio.getUsuario().getIdsesionactual() == null) {

			resp.setExito(false);
			tramiteEnvio.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
		} else {

			if (tramiteEnvio.getTramite().getAbogado() == null
					|| tramiteEnvio.getTramite().getAbogado().getIdusuario() == null
					|| tramiteEnvio.getTramite().getAbogado().getIdusuario()
							.isEmpty()) {
				CodigoError e = new CodigoError();
				e.setCodigo(ErrorCodigoMensaje.CODIGO_E40B1);
				e.setMensaje(ErrorCodigoMensaje.MENSAJE_E40B1);
				listaErrores.add(e);
				resp.setExito(false);

			}
			if (tramiteEnvio.getTramite().getCliente() == null
					|| tramiteEnvio.getTramite().getCliente().getDsnombre() == null
					|| tramiteEnvio.getTramite().getCliente().getDsnombre()
							.isEmpty()) {
				CodigoError e = new CodigoError();
				e.setCodigo("E40B2");
				e.setMensaje("El campo Nombre es requerido");
				listaErrores.add(e);
				resp.setExito(false);
			}

			/*
			 * 20160212 VICTOR:Se comentó por el caso de las personas morales
			 * 
			 * if (tramiteEnvio.getTramite().getCliente() == null ||
			 * tramiteEnvio.getTramite().getCliente() .getDsapellidopat() ==
			 * null || tramiteEnvio.getTramite().getCliente()
			 * .getDsapellidopat().isEmpty()) { CodigoError e = new
			 * CodigoError(); e.setCodigo("E40B3");
			 * e.setMensaje("El campo Apellido Paterno es requerido");
			 * listaErrores.add(e); resp.setExito(false); }
			 */

			// if (tramiteEnvio.getTramite().getCliente().getNacionalidad() ==
			// null
			// || tramiteEnvio.getTramite().getCliente().getNacionalidad()
			// .getIdelemento() == null
			// || tramiteEnvio.getTramite().getCliente().getNacionalidad()
			// .getIdelemento().isEmpty()) {
			//
			// CodigoError e = new CodigoError();
			// e.setCodigo("E40B6");
			// e.setMensaje("El campo Nacionalidad es requerido");
			// listaErrores.add(e);
			// resp.setExito(false);
			// }

		}
		resp.setErrores(listaErrores);

		return resp;
	}

	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public TramiteEnvio registraTramite(TramiteEnvio tramiteEnvio,
			@Context HttpServletRequest request) throws JSONException {

		// antes que nada verificar si el id de la sesion esta activo
		Date fecha = new Date();
		TramiteEnvio respuesta = new TramiteEnvio();
		Tramite tramite = tramiteEnvio.getTramite();

		if (NotariaUtils.isSesionValida(tramiteEnvio.getUsuario()
				.getIdsesionactual(), tramiteEnvio.getUsuario().getIdusuario())) {

			respuesta = validarRequeridos(tramiteEnvio);
			if (!respuesta.isExito()) {
				respuesta.setEstatus("Falta llenar campos requeridos");
				return respuesta;
			}
			// respuesta = validarNegocio(tramiteEnvio);
			// if (!respuesta.isExito())
			// return respuesta;

			tramite.setIdtramite(GeneradorId.generaId(tramite));
			tramite.setIdsesion(request.getSession().getId());
			tramite.setTmstmp(new Timestamp(fecha.getTime()));
			Persona p;

			Tramite t = null;
			// EtapaTramite etapaTramite = new EtapaTramite();
			// etapaTramite.setIdetatra("2");
			// tramite.setStatus(etapaTramite);
			// BitacoraGeneral bg;
			// BitacoraGeneralHelper bitacoraGeneralHelper = new
			// BitacoraGeneralHelper();

			try {
				// se guarda la persona si es nueva
				if (tramite.getCliente() != null) {
					if (tramite.getCliente().getIdpersona() == null) {
						p = tramite.getCliente();
						p.setIdpersona(GeneradorId.generaId(p));
						p.setIdsesion(request.getSession().getId());
						p.setTmstmp(new Timestamp(fecha.getTime()));
						p = this.personaBo.save(tramite.getCliente());
						ContactoBo contactoBo = new ContactoBoImpl();
						if(tramiteEnvio.getContacto()!=null){
							tramiteEnvio.getContacto()
							.setIdcontacto(
									GeneradorId.generaId(tramiteEnvio
											.getContacto()));

							tramiteEnvio.getContacto().setIdsesion(
									tramiteEnvio.getUsuario().getIdsesionactual());
							tramiteEnvio.getContacto().setPersona(p);
							contactoBo.save(tramiteEnvio.getContacto());
						}
						
						
						tramite.setCliente(p);

					} else {
						tramite.setCliente(tramite.getCliente());
					}

				}

				/* Creo carpeta para guardar los documentos */
				SimpleDateFormat yearFormat = new SimpleDateFormat("yy");
				String year = yearFormat.format(fecha);

				StringBuilder numexp = new StringBuilder();
				numexp.append(year);
				numexp.append(File.separator);
				numexp.append(tramite.getAbogado().getDsnumabogado());
				numexp.append(File.separator);
				File directorio = null;

				try {
					System.out.println("Numero de expediente:"
							+ numexp.toString());
					directorio = new File(Constantes.EXPEDIENTES_HOME
							+ File.separator + numexp.toString());
					// directorio = new File(numexp.toString());
					directorio.setWritable(true, false);
					if (!directorio.exists() || !directorio.isDirectory()) {
						FileUtils.forceMkdir(directorio);
						directorio.setWritable(true, false);

						System.out.println("=========> Se creo nuevo folder:"
								+ numexp);
					}

					if (directorio != null) {
						// int numDir =
						// NotariaUtils.numberDirectory(Constantes.EXPEDIENTES_HOME+File.separator+numexp.toString());
						ExpedienteBo exbo = new ExpedienteBoImpl();

						int numDir = exbo.obtenerExpedienteXAbogado(tramite.getAbogado().getIdusuario(),year).size();
						numexp.append(numDir + 1);
						
						File directorioExp = new File(
								Constantes.EXPEDIENTES_HOME + File.separator
										+ numexp.toString());
						// File directorioExp = new File(numexp.toString());
						directorioExp.setWritable(true, false);

						if (!directorioExp.exists()
								|| !directorioExp.isDirectory()) {
							FileUtils.forceMkdir(directorioExp);
							directorioExp.setWritable(true, false);
							System.out
									.println("=========> Se creo nuevo folder:"
											+ numexp);
						}

						tramite.setDsdirectorio(numexp.toString());

					}

				} catch (IOException e) {
					System.out
							.println("==============Algo malo paso al crear el directorio: "
									+ numexp.toString() + " ================");
					e.printStackTrace();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();

				}

				t = this.tramiteBo.save(tramite);

				if (t != null) {

					// VÍCTOR ESPINOSA - im.vicoy@gmail.com
					// ASOCIO EL TRÁMITE AL ABOGADO CORRESPONDIENTE
					// //////////////////

					TramiteUsuarioBo tramusu = new TramiteUsuarioBoImpl();
					UsuarioGrupoTrabajoBo usugpoBo = new UsuarioGrupoTrabajoBoImp();
					List<UsuarioGrupoTrabajo> usugpo = usugpoBo
							.buscarXresponsable(tramiteEnvio.getTramite()
									.getAbogado());
					try {
						if (usugpo.size() > 0) {
							for (UsuarioGrupoTrabajo gpo : usugpo) {
								tramusu.saveTramiteUsuario(gpo.getUsuario(), t,
										tramiteEnvio.getUsuario()
												.getIdsesionactual());
							}
						}
						tramusu.saveTramiteUsuario(tramiteEnvio.getTramite()
								.getAbogado(), t, tramiteEnvio.getUsuario()
								.getIdsesionactual());

					} catch (NotariaException e) {
						e.printStackTrace(System.out);
						respuesta.setEstatus(e.getMessage());
						respuesta.setExito(false);
						return respuesta;
					}

					// // FIN DE COMENTARIOS VÍCTOR ESPINOSA

					this.bitacoraGeneralHelper.registrarEnBitacora(tramiteEnvio
							.getUsuario().getIdusuario(), null, t
							.getIdtramite(), "Persona", "Registrar",
							"Se guarda una Persona en la creacion del tramite");

					this.bitacoraGeneralHelper.registrarEnBitacora(tramiteEnvio
							.getUsuario().getIdusuario(), null, t
							.getIdtramite(), "Tramite", "Registrar",
							"Se guarda un Tramite en la creacion del tramite");

					/*
					 * 31 julio 2015 VICTOR: Al desaparecer el panel de tareas
					 * se comenta la asignación de tarea hasta nuevo aviso
					 * 
					 * TareaPendiente tareaPendiente = new TareaPendiente();
					 * tareaPendiente
					 * .setDsdescripcion("Creacion de un nuevo expediente");
					 * tareaPendiente.setTramite(t);
					 * tareaPendiente.setIdtareapend(GeneradorId
					 * .generaId(tareaPendiente));
					 * tareaPendiente.setIdsesion(request.getSession().getId());
					 * tareaPendiente.setTmstmp(new Timestamp(fecha.getTime()));
					 * 
					 * // asignar usuario que crea la tarea (se obtiene el
					 * usuario // por medio del id se la sesion //
					 * request.getSession().getId() buscando en la tabla de //
					 * sesiones) Usuario usuarioAsigna = new Usuario();
					 * usuarioAsigna
					 * .setIdusuario(tramiteEnvio.getUsuario().getIdusuario());
					 * tareaPendiente.setUsuarioasigna(usuarioAsigna);
					 * 
					 * // asignar usuario a quien se le asigna la tarea
					 * 
					 * 
					 * 
					 * tareaPendiente.setUsuariorecibe(tramite.getAbogado());
					 * tareaPendiente.setIsmanual(0); tareaPendiente =
					 * this.tareaPendienteBo.save(tareaPendiente);
					 * 
					 * if (tareaPendiente != null) { this.bitacoraGeneralHelper
					 * .registrarEnBitacora(tramiteEnvio.getUsuario()
					 * .getIdusuario(), null, t.getIdtramite(),
					 * "TareaPendiente", "Registrar",
					 * "Se guarda una TareaPendiente en la creacion del tramite"
					 * );
					 * 
					 * } else{
					 * respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS
					 * ); }
					 */
					respuesta.setTramite(null);
					respuesta.setDirectorio(tramite.getDsdirectorio());
					respuesta.setIdTramite(t.getIdtramite());
					respuesta.setExito(true);

				} else {
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setTramite(tramite);
					respuesta.setExito(false);
				}

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

	/*
	 * @FormDataParam("idtramite") String idtramite,
			@FormDataParam("idsesionactual") String idsesionactual,
			@FormDataParam("idusuario")String idusuario,
	 * */
	
	@GET
	@Path("/tarjetonprint")
	@Produces({ "application/json", "application/xml"})
	@Consumes({ "application/json", "application/xml"})
	public HttpServletResponse printTarjeton(@Context HttpServletRequest request,@Context HttpServletResponse response) throws JSONException {
		
		
		TramiteEnvio respuesta = new TramiteEnvio();
		respuesta.setExito(false);
		respuesta.setEstatus("prueba de estatus"+request.getParameter("idtramite"));
		return response;
	}
	
	
	@POST
	@Path("/editar")
	@Produces({ "application/json", "application/xml" })
	public TramiteEnvio editarExpediente(TramiteEnvio tramiteEnvio,
			@Context HttpServletRequest request) throws JSONException {

		TramiteEnvio respuesta = new TramiteEnvio();

		if (tramiteEnvio == null || tramiteEnvio.getUsuario() == null
				|| tramiteEnvio.getUsuario().getIdusuario() == null
				|| tramiteEnvio.getUsuario().getIdsesionactual() == null
				|| tramiteEnvio.getExpediente() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tramiteEnvio.getUsuario()
				.getIdsesionactual(), tramiteEnvio.getUsuario().getIdusuario())) {

			ExpedienteBo expBo = new ExpedienteBoImpl();
			PersonaBo perBo = new PersonaBoImpl();
			TramiteBo tramiteBo = new TramiteBoImpl();

			Expediente exp = tramiteEnvio.getExpediente();
			Tramite tramite = exp.getTramite();
			Persona persona = tramite.getCliente();

			try {

				expBo.update(exp);
				perBo.update(persona);
				tramiteBo.update(tramite);

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
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public TramiteEnvio obtenerPorIdCompleto(TramiteEnvio tramiteEnvio,
			@Context HttpServletRequest request) throws JSONException {

		Tramite t = null;
		TramiteEnvio respuesta = new TramiteEnvio();

		if (tramiteEnvio == null || tramiteEnvio.getUsuario() == null
				|| tramiteEnvio.getUsuario().getIdusuario() == null
				|| tramiteEnvio.getUsuario().getIdsesionactual() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tramiteEnvio.getUsuario()
				.getIdsesionactual(), tramiteEnvio.getUsuario().getIdusuario())) {

			if (tramiteEnvio == null || tramiteEnvio.getUsuario() == null
					|| tramiteEnvio.getUsuario().getIdusuario() == null
					|| tramiteEnvio.getUsuario().getIdsesionactual() == null
					|| tramiteEnvio.getTramite() == null) {
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
				respuesta.setExito(false);
				return respuesta;
			}

			try {
				t = this.tramiteBo.buscarPorIdCompleto(tramiteEnvio
						.getTramite());
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setTramite(t);
				respuesta.setExito(true);

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
	@Path("/obtenerExpedientePorTramite")
	@Produces({ "application/json", "application/xml" })
	public TramiteEnvio obtenerExpedientePorTramite(TramiteEnvio tramiteEnvio,
			@Context HttpServletRequest request) {
	
		Tramite t = null;
		TramiteEnvio respuesta = new TramiteEnvio();
		
		if (tramiteEnvio == null || tramiteEnvio.getUsuario() == null
				|| tramiteEnvio.getUsuario().getIdusuario() == null
				|| tramiteEnvio.getUsuario().getIdsesionactual() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tramiteEnvio.getUsuario()
				.getIdsesionactual(), tramiteEnvio.getUsuario().getIdusuario())) {
			try {
				Expediente exp = this.tramiteBo.buscarIdExpediente(tramiteEnvio
						.getTramite().getIdtramite());
				if (exp != null) {
				}
				exp.getTramite().getCliente().setNacionalidad(null);
				exp.getTramite().getCliente().setTipopersona(null);
				respuesta.setExpediente(exp);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setTramite(t);
				respuesta.setExito(true);

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

	/*
	 * @POST
	 * 
	 * @Path("/listarPorUsuario")
	 * 
	 * @Produces({ "application/json", "application/xml" }) public TramiteEnvio
	 * obtenerTramitesPorCliente(TramiteEnvio tramiteEnvio,
	 * 
	 * @Context HttpServletRequest request) {
	 * 
	 * TramiteEnvio respuesta = new TramiteEnvio();
	 * 
	 * if (tramiteEnvio == null || tramiteEnvio.getUsuario() == null ||
	 * tramiteEnvio.getUsuario().getIdusuario() == null ||
	 * tramiteEnvio.getUsuario().getIdsesionactual() == null) {
	 * respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
	 * respuesta.setExito(false); return respuesta; }
	 * 
	 * if (NotariaUtils.isSesionValida(
	 * tramiteEnvio.getUsuario().getIdsesionactual(), tramiteEnvio
	 * .getUsuario().getIdusuario())) {
	 * 
	 * TramiteUsuarioBo tramiteBo = new TramiteUsuarioBoImpl(); try {
	 * 
	 * respuesta.setLista(tramiteBo.buscarTramitesPorUsuario(tramiteEnvio.getUsuario
	 * ()));
	 * 
	 * }catch(NotariaException e){ e.printStackTrace(System.out);
	 * respuesta.setEstatus(e.getMessage()); respuesta.setExito(false); return
	 * respuesta; }
	 * 
	 * 
	 * }else { respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
	 * respuesta.setExito(false); }
	 * 
	 * 
	 * return respuesta;
	 * 
	 * }
	 */

	@POST
	@Path("/listarPorAbogado")
	@Produces(MediaType.APPLICATION_JSON)
	public TramiteEnvio listarPorAbogado(TramiteEnvio te) {
		TramiteEnvio respuesta = new TramiteEnvio();

		if (te.getUsuario() == null || te.getUsuario().getIdusuario() == null
				|| te.getUsuario().getIdsesionactual() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(te.getUsuario().getIdsesionactual(), te
				.getUsuario().getIdusuario())) {
			try {
				List<Tramite> tramiteList = this.tramiteBo.findByAbogado(te
						.getUsuario().getIdusuario());
				respuesta.setLista(tramiteList);
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
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public TramiteEnvio listar(TramiteEnvio te) {
		TramiteEnvio respuesta = new TramiteEnvio();
		if (NotariaUtils.isSesionValida(te.getUsuario().getIdsesionactual(), te
				.getUsuario().getIdusuario())) {
			try {
				List<Tramite> lista = this.tramiteBo.findAll();
				if (lista == null) {
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
					respuesta.setExito(false);
					return respuesta;
				}
				respuesta.setLista(new ArrayList<Tramite>(lista));
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
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
	@Path("/obtenerDatosTarjeton")
	@Produces({ "application/json", "application/xml" })
	public TramiteEnvio obtenerDatosTarjeton(TramiteEnvio tramiteEnvio,
			@Context HttpServletRequest request){

		Tramite t = null;
		TramiteEnvio respuesta = new TramiteEnvio();

		if (tramiteEnvio == null || tramiteEnvio.getUsuario() == null
				|| tramiteEnvio.getUsuario().getIdusuario() == null
				|| tramiteEnvio.getUsuario().getIdsesionactual() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tramiteEnvio.getUsuario()
				.getIdsesionactual(), tramiteEnvio.getUsuario().getIdusuario())) {
			try {
				Expediente exp = this.tramiteBo.buscarIdExpediente(tramiteEnvio
						.getTramite().getIdtramite());
				if (exp != null) {
					ActoBo actoBo = new ActoBoImpl();

					
					ComentarioBo commentsBo = new ComentarioBoImpl();
					List<Acto> actos = null;
					ComparecienteBo comBo = new ComparecienteBoImpl();
					ComparecienteRest comRe = new ComparecienteRest();
					ValorFormularioBo valInmBo = new ValorFormularioBoImpl();

					ArrayList<DatoCompareciente> comparecientes = new ArrayList<DatoCompareciente>();
					actos = actoBo.filterActoByIdExpediente(exp
							.getIdexpediente());
					LinkedHashMap<String, String> valoresTarjeton = new LinkedHashMap<String, String>();

					for (Acto act : actos) {
						Compareciente compareciente = new Compareciente();
						act.setExpediente(exp);
						compareciente.setActo(act);
						compareciente.setIdsesion(tramiteEnvio.getUsuario()
								.getIdsesion());
						List<Compareciente> comList = comBo
								.listadoComparecientes(compareciente);
						if (comList != null && comList.size() > 0) {
							ArrayList<DatoCompareciente> lsDatoCompareciente = comRe
									.obtieneDatosCompletos(comList);
							comparecientes.addAll(lsDatoCompareciente);
						}
						ValorFormulario valSup = valInmBo
								.valorPorNombreVariableActo(act.getIdacto(),
										"superficie");
						ValorFormulario valInm = valInmBo
								.valorPorNombreVariableActo(act.getIdacto(),
										"descripcion");
						ValorFormulario valFol = valInmBo
								.valorPorNombreVariableActo(act.getIdacto(),
										"escritura_folio_real");
						ValorFormulario valAux = valInmBo
								.valorPorNombreVariableActo(act.getIdacto(),
										"anexo");

						if (valInm != null && valInm.getValorcadena() != null)
							valoresTarjeton.put("inmueble",
									valInm.getValorcadena());
						if (valFol != null && valFol.getValorcadena() != null) {
							valoresTarjeton.put("folio",
									valFol.getValorcadena());
						} else {
							ValorFormulario valFolElec = valInmBo
									.valorPorNombreVariableActo(
											act.getIdacto(),
											"folio_real_electronico");
							ValorFormulario seccion = valInmBo
									.valorPorNombreVariableActo(
											act.getIdacto(),
											"escritura_seccion");
							ValorFormulario libro = valInmBo
									.valorPorNombreVariableActo(
											act.getIdacto(), "escritura_libro");
							ValorFormulario volumen = valInmBo
									.valorPorNombreVariableActo(
											act.getIdacto(),
											"escritura_volumen");
							ValorFormulario partida = valInmBo
									.valorPorNombreVariableActo(
											act.getIdacto(),
											"escritura_partida");
							if (valFolElec != null
									&& valFolElec.getValorcadena() != null) {
								valoresTarjeton.put("folio_real_electronico",
										valFolElec.getValorcadena());
							}
							if (seccion != null
									&& seccion.getValorcadena() != null) {
								valoresTarjeton.put("seccion",
										seccion.getValorcadena());
							}
							if (libro != null && libro.getValorcadena() != null) {
								valoresTarjeton.put("libro",
										libro.getValorcadena());
							}
							if (volumen != null
									&& volumen.getValorcadena() != null) {
								valoresTarjeton.put("volumen",
										volumen.getValorcadena());
							}
							if (partida != null
									&& partida.getValorcadena() != null) {
								valoresTarjeton.put("partida",
										partida.getValorcadena());
							}

						}
						if (valAux != null && valAux.getValorcadena() != null)
							valoresTarjeton.put("auxiliar",
									valAux.getValorcadena());
						if (valSup != null && valSup.getValorcadena() != null)
							valoresTarjeton.put("superficie",
									valSup.getValorcadena());

						// VALORES SUBFORMuLARIO
						ConSubFormularioBo conSubFormBo = new ConSubFormularioBoImpl();
						List<ConSubFormulario> consubforms = conSubFormBo
								.obtenerConSubFormsByShortName("inmueble_pvc_subform_0");
						ConSubFormulario conSubForm = consubforms.get(0);
						
						FormularioBo frmBo = new FormularioBoImpl();
						Formulario frm = frmBo.buscarFormulariosPorActo(act.getIdacto(),conSubForm.getConFormulario().getId());
						if(frm !=null && frm.getListaValSubFormulario() !=null){	
						for(ValorSubFormulario vsf:frm.getListaValSubFormulario()){
							if(vsf.getComponente().getDsnombrevariable().equals("descripcion_condominio_objeto")){
								valoresTarjeton.put("inmueble",vsf.getValorcadena());
							}else if(vsf.getComponente().getDsnombrevariable().equals("superficie_condominio_objeto")){
								valoresTarjeton.put("superficie",vsf.getValorcadena());
							}
						}
						}
						
						
						respuesta.setComparecientesCompletos(comparecientes);
						respuesta.setComentariosExpediente(commentsBo
								.buscarPorObjeto(exp.getIdexpediente()));

					}

					respuesta.setValoresTarjeton(valoresTarjeton);

					// exp.setListaComentarios(null);
					// exp.setTramite(null);
					exp.getTramite().getCliente().setNacionalidad(null);
					exp.getTramite().getCliente().setTipopersona(null);
				}
				respuesta.setExpediente(exp);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setTramite(t);
				respuesta.setExito(true);

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
	@Path("/obtenerDatosTarjetonCompleto")
	@Produces({ "application/json", "application/xml" })
	public TramiteEnvio obtenerDatosTarjetonCompleto(TramiteEnvio tramiteEnvio,
			@Context HttpServletRequest request){

		Tramite t = null;
		TramiteEnvio respuesta = new TramiteEnvio();

		if (tramiteEnvio == null || tramiteEnvio.getUsuario() == null
				|| tramiteEnvio.getUsuario().getIdusuario() == null
				|| tramiteEnvio.getUsuario().getIdsesionactual() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(tramiteEnvio.getUsuario()
				.getIdsesionactual(), tramiteEnvio.getUsuario().getIdusuario())) {
			try {
				Expediente exp = this.tramiteBo.buscarIdExpediente(tramiteEnvio
						.getTramite().getIdtramite());
				if (exp != null) {
					ActoBo actoBo = new ActoBoImpl();

					
					List<Acto> actos = null;
					ComparecienteBo comBo = new ComparecienteBoImpl();
					//documentos
					ActoDocumentoBo actodocBo = new ActoDocumentoBoImpl();
					ComentarioBo commentsBo = new ComentarioBoImpl();
					ComparecienteRest comRe = new ComparecienteRest();
					FormularioBo formBo = new FormularioBoImpl();
					
					LinkedHashMap<String,LinkedHashMap<String,String>> datosCompletosFormularios = new LinkedHashMap<String,LinkedHashMap<String,String>>();
					ArrayList<DatoCompareciente> comparecientes = new ArrayList<DatoCompareciente>();
					actos = actoBo.filterActoByIdExpediente(exp
							.getIdexpediente());
					List<Acto> actosrespuesta = new ArrayList<Acto>();
					
					for (Acto act : actos) {
						//Genero un nuevo acto para responder
						if(!act.getIsactivo()) continue;
						
						Acto acres = new Acto();
						acres.setDsnombre(act.getDsnombre());
						actosrespuesta.add(acres);
						
						Compareciente compareciente = new Compareciente();
						act.setExpediente(exp);
						compareciente.setActo(act);
						compareciente.setIdsesion(tramiteEnvio.getUsuario()
								.getIdsesion());
						List<Compareciente> comList = comBo
								.listadoComparecientes(compareciente);
						if (comList != null && comList.size() > 0) {
							ArrayList<DatoCompareciente> lsDatoCompareciente = comRe
									.obtieneDatosCompletos(comList);
							comparecientes.addAll(lsDatoCompareciente);
						}
						
						List<Formulario> formularios = formBo.buscarFormulariosPorActo(act.getIdacto());
						for (Formulario form : formularios){
							LinkedHashMap<String, String> valoresTarjeton = new LinkedHashMap<String, String>();
							if(form.getListaValFormulario()!=null && form.getListaValFormulario().size()>0){
								for(ValorFormulario vf: form.getListaValFormulario()){
									if(vf.getValorcadena()!=null && !vf.getValorcadena().equals("")&& !vf.getValorcadena().isEmpty()){
										vf.setValorcadena(interpretaValor(vf.getValorcadena()));
										valoresTarjeton.put(vf.getComponente().getDsetiqueta(),vf.getValorcadena());
									}
								}
								
								
							}
							if(form.getListaValSubFormulario()!=null && form.getListaValSubFormulario().size()>0){
								for(ValorSubFormulario vsf: form.getListaValSubFormulario()){
									if(vsf.getValorcadena()!=null && !vsf.getValorcadena().equals("")&& !vsf.getValorcadena().isEmpty()){
										valoresTarjeton.put(vsf.getComponente().getDsetiqueta(),vsf.getValorcadena());
									}
								}
							}
							System.out.println(form.getConFormulario().getDstitulo());
							datosCompletosFormularios.put(form.getConFormulario().getDstitulo(), valoresTarjeton);
						}
						
						
						
						
						
						
						
						// obtengo documentos
						List<DatoDocumentoTarjeton> listadocs = actodocBo.obtenerXidActo(act);
						
						respuesta.setComparecientesCompletos(comparecientes);
						respuesta.setDocumentos(listadocs);

					}
					respuesta.setActos(actosrespuesta);
					respuesta.setComentariosExpediente(commentsBo
							.buscarPorObjeto(exp.getIdexpediente()));
					respuesta.setDatosFormulariosTarjeton(datosCompletosFormularios);
					System.out.println(datosCompletosFormularios);
					// exp.setListaComentarios(null);
					// exp.setTramite(null);
					exp.getTramite().getCliente().setNacionalidad(null);
					exp.getTramite().getCliente().setTipopersona(null);
				}
				respuesta.setExpediente(exp);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setTramite(t);
				respuesta.setExito(true);

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
	
	private String interpretaValor(String valor){
		switch (valor) {
		case "true":
			 valor = "Si";
			break;
		case "false":
			 valor = "No";
			break;
		case "grav_lib":
			 valor = "Libre de gravámenes";
			break;
		case "grav_mis":
			 valor = "Cancelado mismo instrumento";
			break;
		case "grav_pre":
			 valor = "Cancelado por separado previo";
			break;
		case "grav_pos":
			 valor = "Cancelado por separado posterior";
			break;
		
		default:
			
			break;
		}
		
		return valor;
		
		
	}
	
	
}
