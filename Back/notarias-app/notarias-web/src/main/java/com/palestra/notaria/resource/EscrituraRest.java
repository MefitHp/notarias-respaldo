package com.palestra.notaria.resource;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.DocumentoNotarialMasterBo;
import com.palestra.notaria.bo.DocumentoNotarialParcialBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.impl.DocumentoNotarialMasterBoImpl;
import com.palestra.notaria.bo.impl.DocumentoNotarialParcialBoImpl;
import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.dao.DocumentoNotarialParcialDao;
import com.palestra.notaria.dao.impl.DocumentoNotarialParcialDaoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoActoMultiSelect;
import com.palestra.notaria.dato.DatoEscritura;
import com.palestra.notaria.envio.EscrituraEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.DocumentoNotarialParcial;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;
import com.palestra.notaria.util.VerificaEscritura;
import com.palestra.notarias.escritura.GeneraWord;
import com.palestra.notarias.service.GeneradorDocumentoService;
import com.palestra.notarias.utils.ArchivoUtils;
import com.palestra.notarias.utils.EscrituraUtills;
import com.palestra.notarias.utils.WordUtils;

import pojos.pojos.BonitaCommonBean;

/**
 * Clase que atiente peticiones CRUD para Escritura
 * 
 * @author sofia
 * 
 */
@Path("/escritura")
public class EscrituraRest {

	static Logger logger = Logger.getLogger(EscrituraRest.class);

	private EscrituraBo escrituraBo;
	private EscrituraActoBo escrituraActoBo;
	private BitacoraGeneralHelper bitacoraGeneralHelper;
	private GeneradorDocumentoService generadorDocumentoSrv;
	private DocumentoNotarialMasterBo masterBo;

	// private DocumentoNotarialParcialBackupBo parcialBackBo;

	public EscrituraRest() {
		escrituraBo = new EscrituraBoImpl();
		escrituraActoBo = new EscrituraActoBoImpl();
		bitacoraGeneralHelper = new BitacoraGeneralHelper();
		generadorDocumentoSrv = new GeneradorDocumentoService();
		masterBo = new DocumentoNotarialMasterBoImpl();
		// parcialBackBo = new DocumentoNotarialParcialBackupBoImpl();
	}

	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio obtenerPorIdCompleto(EscrituraEnvio envio,
			@Context HttpServletRequest request) throws JSONException {

		Escritura escritura = envio.getEscritura();
		EscrituraEnvio respuesta = new EscrituraEnvio();

		if (escritura == null || escritura.getIdescritura() == null
				|| escritura.getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(envio)
				|| envio.getExpediente() == null
				|| envio.getExpediente().getIdexpediente() == null
				|| envio.getExpediente().getIdexpediente().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(envio.getUsuario().getIdsesionactual(),
				envio.getUsuario().getIdusuario())) {
			try {
				Escritura ePersisted = this.escrituraBo
						.buscarPorIdCompleto(escritura);
				if (ePersisted == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(false);
					return respuesta;
				}
				/**
				 * Lista de actos asociados a la escritura + actos disponibles
				 * para seleccion.
				 **/
				List<DatoActoMultiSelect> lista = this.escrituraActoBo
						.buscarActosDisponibleParaEscritura(envio
								.getEscritura().getIdescritura(), envio
								.getExpediente().getIdexpediente());
				if (lista != null) {
					respuesta
							.setActos(new ArrayList<DatoActoMultiSelect>(lista));
				} else {
					respuesta.setActos(null);
				}
				StringBuilder numexpediente = new StringBuilder(envio.getExpediente().getNumexpediente());
				ArrayList<File> words = ArchivoUtils.wordsInDir(Constantes.EXPEDIENTES_HOME+"/"+numexpediente.toString());
				File masterExpediente = ArchivoUtils.getUltimaVersionExpediente(numexpediente.toString().replace("/", "-"), words);
				if(masterExpediente !=null){

				try {
					WordUtils word = new WordUtils(masterExpediente.getPath(),39);
					respuesta.setArchivofinal(masterExpediente.getName());
					respuesta.setPaginas(word.getpaginas());
					respuesta.setPorcentajeUltimaPag(word.getPorcentajeUltimaPag(word.getFilas()));
					
				} catch (Exception e) {
					respuesta.setArchivofinal(null);
					
				}
				}

				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setEscritura(ePersisted);
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

	/**
	 * Se guarda/actualiza un registro escritura y se genera/regenera el texto
	 * de un documento notarial parcial.
	 * 
	 * @param escrituraEnvio
	 * @param request
	 * @return
	 */
	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio registraExpediente(EscrituraEnvio escrituraEnvio,
			@Context HttpServletRequest request) {

		EscrituraEnvio respuesta = new EscrituraEnvio();

		if (escrituraEnvio == null
				|| escrituraEnvio.getEscritura() == null
				|| escrituraEnvio.getExpediente() == null
				|| escrituraEnvio.getExpediente().getIdexpediente() == null
				|| escrituraEnvio.getExpediente().getIdexpediente().trim()
						.isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(escrituraEnvio)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		Escritura escritura = escrituraEnvio.getEscritura();
		Usuario usuario = escrituraEnvio.getUsuario();
		System.out.printf("Expediente : %s", escrituraEnvio.getExpediente()
				.getIdexpediente());
		if (NotariaUtils.isSesionValida(escrituraEnvio.getUsuario()
				.getIdsesionactual(), escrituraEnvio.getUsuario()
				.getIdusuario())) {
			try {
				respuesta = this.validarRequeridosRegistro(escrituraEnvio);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					respuesta.setExito(false);
					return respuesta;
				}
				/** asignar el expediente dado en el envio a la escritura **/
				escritura.setExpediente(escrituraEnvio.getExpediente());

				/** si contiene id escritura es una actualizacion **/
				if (escritura.getIdescritura() != null
						&& !escritura.getIdescritura().trim().isEmpty()) {
					/*
					 * if (masterBo.existeMasterDeEscritura(escritura
					 * .getIdescritura())) {
					 * respuesta.setEstatus(Constantes.ESTATUS_EXISTE_MASTER);
					 * respuesta.setExito(false); return respuesta; }
					 */
					logger.info("=====> La escritura tiene id, actualizar()");
					/** actualizaar notario y actos de la escritura **/
					return this.actualizarActosNotario(escrituraEnvio);
				}

				/** si no contiene idescritura se registra **/
				escritura.setIdescritura(GeneradorId.generaId(escritura));
				escritura.setIdsesion(usuario.getIdsesionactual());
				escritura.setTmstmp(new Timestamp((new Date()).getTime()));
				escritura.setNopaso(false);
				/**
				 * a nivel de BD este campo es obligatorio, pero por el proceso
				 * en este punto no se captura, por lo que inicialmente es
				 * asingado por el sistema, pero en otro punto del proceso, se
				 * actualiza esta fecha siendo capturada por el usuario.
				 **/
				escritura.setFechacreacion(new Date());

				if (escrituraEnvio.getActos() != null) {
					String idActos = this.obtenActosId(escrituraEnvio
							.getActos());
					/**
					 * Verificar que los actos enviados no esten ya asociados a
					 * otra escritura.
					 **/
					if (escrituraActoBo.isValidAddActos(idActos)) {
						List<EscrituraActo> actosDeEscritura = this
								.convertToEscrituraActo(escrituraEnvio);
						/** registrar escritura y sus actos asociados **/
						escrituraBo.registrarEscritura(escritura,
								actosDeEscritura);
					} else {
						logger.info("===> Alguno de los actos ya estan asociados a otra escritura.");
						respuesta
								.setEstatus("Alguno de los actos ya estan asociados a otra escritura.");
						respuesta.setExito(false);
						return respuesta;
					}
				}
				/** Generar el documento notarial parcial(Texto de escritura) **/
				logger.info("==============> GENERANDO DOCUMENTO PARCIAL "
						+ escritura.getIdescritura());
				generadorDocumentoSrv.versionInicialDocParcial(escritura
						.getIdescritura());

				String idExpediente = escrituraEnvio.getExpediente()
						.getIdexpediente();
				/** registrar en bitacora, el registro de escritura **/
				bitacoraGeneralHelper.registrarEnBitacora(
						usuario.getIdusuario(), idExpediente, null,
						"Escritura", Constantes.OPERACION_REGISTRO,
						"Se guarda una Escritura");
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;

				// TODO: falta ponder mensaje si se duplica dsnumescritura, por
				// que tiene unique constraint. Hay que atrapar la excepcion
				// y enviar mensaje que ya existe ese numer de escritura
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} catch (Exception e) {
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
	@Path("/actualizaPropsEscritura")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio actualizaPropsEscritura(
			EscrituraEnvio escrituraEnvio, @Context HttpServletRequest request) {

		Usuario usuario = escrituraEnvio.getUsuario();
		EscrituraEnvio respuesta = new EscrituraEnvio();

		if (escrituraEnvio.getEscritura() == null
				|| escrituraEnvio.getExpediente() == null
				|| escrituraEnvio.getExpediente().getIdexpediente() == null
				|| escrituraEnvio.getExpediente().getIdexpediente().trim()
						.isEmpty()
				|| escrituraEnvio.getEscritura().getIdescritura() == null
				|| escrituraEnvio.getEscritura().getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(escrituraEnvio)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		Escritura escritura = escrituraEnvio.getEscritura();
//		se obtiene el id del acto para que bonita actualice el flujo
		String idActo = escrituraEnvio.getActos().get(0).getIdacto();
		if (NotariaUtils.isSesionValida(escrituraEnvio.getUsuario()
				.getIdsesionactual(), escrituraEnvio.getUsuario()
				.getIdusuario())) {
			try {
				respuesta = this
						.validarRequeridosActualizarProps(escrituraEnvio);
				if (!respuesta.isExito()) {
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					return respuesta;
				}
				respuesta = this.validarNegocio(escrituraEnvio);
				if (!respuesta.isExito()) {
					respuesta
							.setEstatus(Constantes.ESTATUS_VALIDACION_NEGOCIO_INVALIDA);
					return respuesta;
				}
				// Nota: Si ya existe master no es posible modificar la
				// escritura
				/*if (masterBo
						.existeMasterDeEscritura(escritura.getIdescritura())) {
					respuesta.setEstatus(Constantes.ESTATUS_EXISTE_MASTER);
					respuesta.setExito(false);
					return respuesta;
				}*/
				/** asignar el expediente dado en el envio a la escritura **/
				escritura.setExpediente(escrituraEnvio.getExpediente());
				
				/** valido la solicitud de DIM y ANEXO 5 en caso de que sea una Traslativa**/
				if(escrituraEnvio.isIstraslativa()){
					escrituraBo.verificaDimAnexo5(escrituraEnvio.getActos());
				}

				/** si contiene id escritura es una actualizacion **/
//				16ago2017 se agrega idacto y usuario para bonita
				escrituraBo.actualizarPropsEscritura(escritura,idActo,usuario);

				String idExpediente = escrituraEnvio.getExpediente()
						.getIdexpediente();
				/** registrar en bitacora, el registro de escritura **/
				bitacoraGeneralHelper.registrarEnBitacora(
						usuario.getIdusuario(), idExpediente, null,
						"Escritura", Constantes.OPERACION_ACTUALIZACION,
						"Se actualiza una escritura");
				/*
				 * 20170521 VICTOR: se comenta por que ya se genera desde el seteo del acto
				if(escritura.getInitproceso()){
				
					if((escritura.getHasproceso()==null || !escritura.getHasproceso())){
						escrituraBo.iniciaProcesoEscritura(usuario,escritura);
						escritura.setHasproceso(true);
					}
					
				}*/
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
	
	@POST
	@Path("/saveNumEscrituraBonita")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio saveNumEscrituraBonita(BonitaCommonBean bonitaBean){
		EscrituraEnvio respuesta = new EscrituraEnvio();
		try{
			
//			if (tareaBean == null || tareaBean.getIdusuario() == null
//					|| tareaBean.getIdsesionactual() == null || 
//						tareaBean.getIdActo() == null || tareaBean.getIdproceso() == null
//						|| tareaBean.getIdtarea() == null) {
//				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
//				respuesta.setExito(false);
//				return respuesta;
//			}
			if(NotariaUtils.isSesionValida(bonitaBean.getIdsesionactual(), bonitaBean.getIdusuario())){
				escrituraBo.saveNumEscrituraBonita(bonitaBean);
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;
			}else{
				respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
				respuesta.setExito(false);
				return respuesta;
			}
//			}catch(NotariaException e){
			}catch(Exception e){
				e.printStackTrace();
				respuesta.setEstatus("ocurrio un error al guardar proceso del BPM "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
	}
	@POST
	@Path("/setProcesoIdTarea")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio setProcesoIdTarea(
			EscrituraEnvio escrituraEnvio, @Context HttpServletRequest request) {

		Usuario usuario = escrituraEnvio.getUsuario();
		EscrituraEnvio respuesta = new EscrituraEnvio();

		if (escrituraEnvio.getEscritura() == null
				|| escrituraEnvio.getExpediente() == null
				|| escrituraEnvio.getExpediente().getIdexpediente() == null
				|| escrituraEnvio.getExpediente().getIdexpediente().trim()
						.isEmpty()
				|| escrituraEnvio.getEscritura().getIdescritura() == null
				|| escrituraEnvio.getEscritura().getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(escrituraEnvio)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		Escritura escritura = escrituraEnvio.getEscritura();
		String idActo = escrituraEnvio.getActos().get(0).getIdacto();
		if (NotariaUtils.isSesionValida(escrituraEnvio.getUsuario()
				.getIdsesionactual(), escrituraEnvio.getUsuario()
				.getIdusuario())) {
			try {
				
				escritura = escrituraBo.findById(escritura.getIdescritura());
				if(escritura!=null){
					escritura.setIdtachafirma(escrituraEnvio.getEscritura().getIdtachafirma());
				}
//				TODO: probar que funcione bien la adicion del argumento idActo y usuario aqui
				escrituraBo.actualizarPropsEscritura(escritura,idActo,usuario);

				String idExpediente = escrituraEnvio.getExpediente()
						.getIdexpediente();
				/** registrar en bitacora, el registro de escritura **/
				bitacoraGeneralHelper.registrarEnBitacora(
						usuario.getIdusuario(), idExpediente, null,
						"Escritura", Constantes.OPERACION_ACTUALIZACION,
						"Se actualiza una escritura");
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

	@POST
	@Path("/eliminar")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio eliminar(EscrituraEnvio escrituraEnvio,
			@Context HttpServletRequest request) throws JSONException {

		EscrituraEnvio respuesta = new EscrituraEnvio();
		Escritura escritura = escrituraEnvio.getEscritura();

		if (escritura == null || escritura.getIdescritura() == null
				|| escritura.getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(escrituraEnvio)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(escrituraEnvio.getUsuario()
				.getIdsesionactual(), escrituraEnvio.getUsuario()
				.getIdusuario())) {
			try {
				/** obtener la escritura persistida en bd. **/
				Escritura eRegistrada = escrituraBo
						.buscarPorIdCompleto(escritura);
				if (eRegistrada == null) {
					respuesta.setEstatus("No existe escritura a eliminar.");
					respuesta.setExito(false);
					return respuesta;
				}
				/** tomar el expediente del acto de la tarjeta para bitacora **/
				String expRegistrado = eRegistrada.getExpediente()
						.getIdexpediente();
				if (eRegistrada.getDsnumescritura() != null
						&& !eRegistrada.getDsnumescritura().isEmpty()) {
					respuesta
							.setEstatus("No es posible eliminar la escritura, debido a que ya tiene asignado número de escritura.");
					respuesta.setExito(false);
					return respuesta;
				}
				/** eliminar la escritura **/
				boolean resultado = escrituraBo.eliminarEscritura(eRegistrada
						.getIdescritura());
				if (!resultado) {
					respuesta
							.setEstatus("No es posible eliminar la escritura, debido a que hay información relacionada a ella.");
					respuesta.setExito(false);
					return respuesta;
				}
				bitacoraGeneralHelper.registrarEnBitacora(escrituraEnvio
						.getUsuario().getIdusuario(), expRegistrado, null,
						"Escritura", Constantes.OPERACION_ELIMINACION,
						"Se ha eliminado una escritura.");
				respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ELIMINAR);
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
	@Path("/listarPorExpediente")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio obtenerEscrituras(EscrituraEnvio escrituraEnvio,
			@Context HttpServletRequest request) throws JSONException {

		EscrituraEnvio respuesta = new EscrituraEnvio();

		if (escrituraEnvio.getExpediente() == null
				|| escrituraEnvio.getExpediente().getIdexpediente() == null
				|| escrituraEnvio.getExpediente().getIdexpediente().trim()
						.isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(escrituraEnvio)) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(escrituraEnvio.getUsuario()
				.getIdsesionactual(), escrituraEnvio.getUsuario()
				.getIdusuario())) {
			try {
				List<Escritura> lista = this.escrituraBo
						.findByExpedienteId(escrituraEnvio.getExpediente()
								.getIdexpediente());
				if (lista != null && !lista.isEmpty()) {
					ArrayList<DatoEscritura> listaresultado = new ArrayList<DatoEscritura>();
					DatoEscritura de;
					List<DatoActoMultiSelect> listaactos;
					for (Escritura esc : lista) {
						
						de = new DatoEscritura();
						de.setEscritura(esc);
						
						/** obtener los actos de la escritura **/
						listaactos = this.escrituraActoBo
								.obtenActoNombrePorEscrituraId(esc
										.getIdescritura());
						/** convetir la lista en un string con los nombres . **/
						if (listaactos != null) {
							de.setActos((ArrayList<DatoActoMultiSelect>) listaactos);
						}
						listaresultado.add(de);
					}
					respuesta.setListaEscrituras(listaresultado);
				}
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
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
	@Path("/obtenActosDisponibles")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio obtenActosDisponibles(
			EscrituraEnvio escrituraRequest, @Context HttpServletRequest request)
			throws JSONException {

		EscrituraEnvio respuesta = new EscrituraEnvio();

		if (NotariaUtils.faltanRequeridosUsuario(escrituraRequest)
				|| (escrituraRequest.getExpediente() == null
						|| escrituraRequest.getExpediente().getIdexpediente() == null || escrituraRequest
						.getExpediente().getIdexpediente().isEmpty())) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(escrituraRequest.getUsuario()
				.getIdsesionactual(), escrituraRequest.getUsuario()
				.getIdusuario())) {
			List<DatoActoMultiSelect> lista = null;
			try {
				lista = this.escrituraActoBo
						.buscarActosDisponibles(escrituraRequest
								.getExpediente().getIdexpediente());
			} catch (NotariaException e) {
				respuesta.setExito(false);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA + " "
						+ e.getMessage());
				e.printStackTrace(System.out);
				return respuesta;
			}
			if (lista != null) {
				respuesta.setActos(new ArrayList<DatoActoMultiSelect>(lista));
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(true);
			} else {
				respuesta.setActos(new ArrayList<DatoActoMultiSelect>());
				respuesta.setEstatus("No hay actos disponibles.");
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}

	@POST
	@Path("/obtenActosConforman")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio obtenActosConforman(EscrituraEnvio escrituraRequest,
			@Context HttpServletRequest request) throws JSONException {

		EscrituraEnvio respuesta = new EscrituraEnvio();

		if (escrituraRequest.getEscritura() == null
				|| escrituraRequest.getEscritura().getIdescritura() == null
				|| escrituraRequest.getEscritura().getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(escrituraRequest)) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(escrituraRequest.getUsuario()
				.getIdsesionactual(), escrituraRequest.getUsuario()
				.getIdusuario())) {
			List<DatoActoMultiSelect> lista = null;

			try {
				lista = this.escrituraActoBo
						.obtenActoNombrePorEscrituraId(escrituraRequest
								.getEscritura().getIdescritura());
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA + " "
						+ e.getMessage());
				respuesta.setExito(false);
				e.printStackTrace(System.out);
				return respuesta;
			}
			if (lista != null) {
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setActosConforman(this.obtenActosConforman(lista));
				respuesta.setActos(new ArrayList<DatoActoMultiSelect>(lista));
				respuesta.setExito(true);
			} else {
				respuesta.setEstatus("La escritura no tiene asociado actos.");
				respuesta.setExito(false);
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}

	@POST
	@Path("/switchNotario")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio switchNotario(EscrituraEnvio ee,
			@Context HttpServletRequest request) throws JSONException {

		EscrituraEnvio respuesta = new EscrituraEnvio();

		if (ee.getEscritura() == null
				|| ee.getEscritura().getIdescritura() == null
				|| ee.getEscritura().getIdescritura().isEmpty()
				|| ee.getEscritura().getNotario() == null
				|| ee.getEscritura().getNotario().getIdusuario() == null
				|| ee.getEscritura().getNotario().getIdusuario().isEmpty()
				|| ee.getEscritura().getNotario().getRol() == null
				|| ee.getEscritura().getNotario().getRol().getIdrol() == null
				|| ee.getEscritura().getNotario().getRol().getIdrol().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(ee)) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ee.getUsuario().getIdsesionactual(), ee
				.getUsuario().getIdusuario())) {

			Boolean b = null;

			try {
				b = escrituraBo.switchNotario(ee.getEscritura());
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta
						.setEstatus(Constantes.ESTATUS_SWITCH_NOTARIO_INCORRECTO
								+ " " + e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if (!b) {
				respuesta
						.setEstatus(Constantes.ESTATUS_SWITCH_NOTARIO_INCORRECTO);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_SWITCH_NOTARIO_CORRECTO);
			return respuesta;

		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/nopaso")
	@Produces({ "application/json", "application/xml" })
	public EscrituraEnvio nopaso(EscrituraEnvio ee,
			@Context HttpServletRequest request) throws JSONException {

		EscrituraEnvio respuesta = new EscrituraEnvio();

		if (ee.getEscritura() == null
				|| ee.getEscritura().getIdescritura() == null
				|| ee.getEscritura().getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(ee)) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ee.getUsuario().getIdsesionactual(), ee
				.getUsuario().getIdusuario())) {

			try {
				if(ee.getEscritura().getDsnumescritura()==null || ee.getEscritura().getDsnumescritura().isEmpty()){
					respuesta.setExito(false);
					respuesta.setEstatus("Aún no tiene un número de escritura asignado");
					return respuesta;
				}
				VerificaEscritura.nopaso(ee.getEscritura().getIdescritura());
				Escritura esc = escrituraBo.findById(ee.getEscritura().getIdescritura());
				if(esc!=null){
					
					esc.setNopaso(true);
					escrituraBo.nopaso(esc,ee.getUsuario());
				}
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta
						.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION
								+ " " + e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			return respuesta;

		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	/**
	 * Devuelve una lista de EscrituraActo con todas las propiedades necesarias
	 * para guardar en la BD.
	 * 
	 * @param escrituraEnvio
	 * @return List<EscrituraActo> que se guardara en la BD associdas a la
	 *         escritura correspondientes.
	 */
	private List<EscrituraActo> convertToEscrituraActo(
			EscrituraEnvio escrituraEnvio) {
		ArrayList<DatoActoMultiSelect> actosSelected = escrituraEnvio
				.getActos();
		Escritura escritura = escrituraEnvio.getEscritura();
		Usuario usuario = escrituraEnvio.getUsuario();
		List<EscrituraActo> escrituraActos = new ArrayList<EscrituraActo>();
		EscrituraActo escActo = null;
		Acto acto = null;
		for (DatoActoMultiSelect selec : actosSelected) {
			acto = new Acto();
			acto.setIdacto(selec.getIdacto());

			escActo = new EscrituraActo();
			escActo.setIdescacto(GeneradorId.generaId(escActo));
			escActo.setEscritura(escritura);
			escActo.setActo(acto);
			escActo.setIdsesion(usuario.getIdsesionactual());
			escActo.setTmstmp(new Timestamp((new Date()).getTime()));

			escrituraActos.add(escActo);
		}
		return escrituraActos;
	}

	/**
	 * De una lista ActoMultiSelect se obtiene una cadena con todos los ids
	 * separados por comas.
	 * 
	 * @param valores
	 *            Lista de valores
	 * @return
	 */
	private String obtenActosId(List<DatoActoMultiSelect> actos) {
		if (actos != null && actos.size() > 0 && !actos.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append("'" + actos.get(0).getIdacto() + "'");
			if (actos.size() == 1) {
				return sb.toString();
			}
			for (int i = 1; i < actos.size(); i++) {
				sb.append(", '" + actos.get(i).getIdacto() + "'");
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * Procesa una lista de actos para devolver una cadena con los nombres de
	 * los actos asociados a la escritura.
	 * 
	 * @param actos
	 *            Lista de actos
	 * @return Cadena con la lista de nombres de actos
	 */
	private String obtenActosConforman(List<DatoActoMultiSelect> actos) {

		if (actos != null && actos.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(actos.get(0).getNombreActo() + " - "
					+ actos.get(0).getDescripcionActo());
			if (actos.size() == 1) {
				return sb.toString();
			}
			for (int i = 1; i < actos.size() - 1; i++) {
				sb.append(", " + actos.get(i).getNombreActo() + " - "
						+ actos.get(i).getDescripcionActo());
			}
			sb.append(" y " + actos.get(actos.size() - 1).getNombreActo()
					+ " - " + actos.get(actos.size() - 1).getDescripcionActo());
			return sb.toString();
		}
		return null;
	}

	/**
	 * Valida los campos que son requeridos a nivel de BD para actualizar
	 * propiedades de la escritura
	 * 
	 * @param escrituraEnvio
	 * 
	 * @return
	 * 
	 */
	private EscrituraEnvio validarRequeridosActualizarProps(
			EscrituraEnvio escrituraEnvio) {
		EscrituraEnvio respuesta = new EscrituraEnvio();
		Escritura escritura = escrituraEnvio.getEscritura();
		ArrayList<CodigoError> errores = new ArrayList<CodigoError>();
		CodigoError error = null;
		if (escritura.getFechacreacion() == null) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E24B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E24B2);
			errores.add(error);
			respuesta.setExito(false);
		}
		respuesta.setErrores(errores);
		return respuesta;
	}

	/**
	 * Valida los campos requeridos para la pantalla de registro de escritura
	 * 
	 * @param escrituraEnvio
	 * @return
	 */
	private EscrituraEnvio validarRequeridosRegistro(
			EscrituraEnvio escrituraEnvio) {

		EscrituraEnvio respuesta = new EscrituraEnvio();

		Escritura escritura = escrituraEnvio.getEscritura();

		ArrayList<DatoActoMultiSelect> actos = escrituraEnvio.getActos();

		ArrayList<CodigoError> errores = new ArrayList<CodigoError>();
		CodigoError error = null;

		if (escritura.getNotario() == null
				|| escritura.getNotario().getIdusuario() == null
				|| escritura.getNotario().getIdusuario().isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E24B3);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E24B3);
			errores.add(error);
			respuesta.setExito(false);
		}
		if (actos == null || actos.isEmpty()) {
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E24B5);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E24B5);
			errores.add(error);
			respuesta.setExito(false);
		}

		respuesta.setErrores(errores);
		return respuesta;
	}

	/**
	 * Validacion de las reglas de negocio
	 * 
	 * @param escrituraEnvio
	 * @return escrituraEnvio con validacion si paso las reglas
	 */
	private EscrituraEnvio validarNegocio(EscrituraEnvio escrituraEnvio) {

		Escritura escritura = escrituraEnvio.getEscritura();

		EscrituraEnvio respuesta = new EscrituraEnvio();
		ArrayList<CodigoError> errores = new ArrayList<CodigoError>();
		CodigoError error = null;

		if (escritura == null) {
			logger.info("Objeto escritura nulo");
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA);
			respuesta.setExito(false);
			return respuesta;
		}
		Long folioInicial = escritura.getFolioini();
		Long folioFinal = escritura.getFoliofin();

		if (folioInicial != null && folioFinal != null) {
			if (folioInicial > folioFinal) {
				error = new CodigoError();
				error.setCodigo(ErrorCodigoMensaje.CODIGO_E24B1);
				error.setMensaje(ErrorCodigoMensaje.MENSAJE_E24B1);
				errores.add(error);
				respuesta.setExito(false);
			}
		}
		respuesta.setErrores(errores);
		return respuesta;
	}

	/**
	 * Actualiza una escritura y la relacion EscrituraActos
	 * 
	 * @param escrituraEnvio
	 * @return
	 */
	private EscrituraEnvio actualizarActosNotario(EscrituraEnvio escrituraEnvio) {
		EscrituraEnvio respuesta = new EscrituraEnvio();
		Escritura escritura = escrituraEnvio.getEscritura();
		try {
			Escritura escPersisted = escrituraBo.findById(escritura
					.getIdescritura());
			if (escPersisted == null) {
				respuesta.setEstatus("No existe la escritura a actualizar.");
				respuesta.setExito(false);
				return respuesta;
			}
			/** asignar valores que no se caputran de pantalla **/
			escritura.setIdsesion(escrituraEnvio.getUsuario()
					.getIdsesionactual());
			escritura.setTmstmp(new Timestamp((new Date()).getTime()));

			if (escrituraEnvio.getActos() != null) {
				List<EscrituraActo> actosDeEscritura = this
						.convertToEscrituraActo(escrituraEnvio);
				/** registrar escritura y sus actos asociados **/
				Boolean resultado = escrituraBo.actualizarActosNotario(
						escritura, actosDeEscritura);
				if (!resultado) {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
					respuesta.setExito(false);
					return respuesta;
				}
			} else {
				/** Si no hay actos asociados, registrar solo escritura **/
				Escritura eUpdated = escrituraBo.update(escritura);
				if (eUpdated == null) {
					respuesta
							.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
					respuesta.setExito(false);
					return respuesta;
				}
			}
			/**
			 * De la escritura actualizada regenerar el texto del documento
			 * notarial parcial
			 **/
			this.regenerarTextoEscritura(escritura.getIdescritura());
			/** registrar en bitacora, actualizacion de escritura **/
			bitacoraGeneralHelper.registrarEnBitacora(escrituraEnvio
					.getUsuario().getIdusuario(), escrituraEnvio.getEscritura()
					.getExpediente().getIdexpediente(), null, "Escritura",
					Constantes.OPERACION_ACTUALIZACION,
					"Se actualiza una Escritura.");
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			respuesta.setEscritura(null);
			return respuesta;
		} catch (NotariaException e) {
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}

	/**
	 * Se vuelve a generar el texto del documento notarial parcial
	 * 
	 * @param idEscritura
	 */
	private void regenerarTextoEscritura(String idEscritura) throws Exception {

		Escritura escritura = new Escritura();
		escritura.setIdescritura(idEscritura);
		// TODO:
		logger.info("======> ELIMNAR VERSIONES ANTERIORES DOCUMENTO PARCIAL ");
		// boolean b = this.parcialBackBo.makeBackupDocNotParciales(escritura);
		// if (b) {
		// logger.info("======> REGENERAR EL TEXTO DEL DOCUMENTO NOTARIAL PARCIAL ");
		/** generar nuevo documento notarial parcial **/
		// DocumentoNotarialParcialBo boDP = new
		// DocumentoNotarialParcialBoImpl();
		DocumentoNotarialParcialDao dnpDao = new DocumentoNotarialParcialDaoImpl();

		List<DocumentoNotarialParcial> lista = dnpDao.getByEscritura(escritura);
		for (DocumentoNotarialParcial doc : lista) {
			// System.out.print("===>>>>> "+doc.getIddocnotpar());
			// DocumentoNotarialParcial x
			// =dnpDao.findById(doc.getIddocnotpar());
			// if(doc!=null){
			// dnpDao.delete(x);
			dnpDao.deleteById(doc.getIddocnotpar());
			// }else{
			// System.out.println("=====>>>>>  No existe el "+doc.getIddocnotpar());
			// }
		}

		this.generadorDocumentoSrv.versionInicialDocParcial(idEscritura);
		// }
	}

	/**
	 * Descarga de archivo .docx con formato para impresión
	 * 
	 * @param idEscritura
	 * @return
	 */

	@GET
	@Path("/obtieneWord")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response descargarDocumento(
			@QueryParam("idescritura") String idescritura,
			@QueryParam("idtramite") String idtramite,
			@QueryParam("idusuario") String idusuario,
			@QueryParam("idsesionactual") String idsesionactual,
			@Context HttpServletResponse response) {

		if (idsesionactual == null || idsesionactual.isEmpty()
				|| idusuario == null || idusuario.isEmpty()
				|| idescritura == null || idescritura.isEmpty()
				|| idtramite == null || idtramite.isEmpty()) {

			Response.ok(Constantes.ESTATUS_FALTAN_PARAMETROS).build();
		}

		boolean sesion = NotariaUtils.isSesionValida(idsesionactual, idusuario);
		if (sesion) {

			EscrituraBo esBo = new EscrituraBoImpl();

			try {
				Escritura esc = new Escritura();
				esc.setIdescritura(idescritura);
				esc = esBo.buscarPorIdCompleto(esc);
				if (esc.getDsnumescritura() == null) {
					Response.ok("NO TIENE UN NÚMERO DE ESCRITURA ASIGNADO")
							.build();
				}

				String urlDoc = "";
				DocumentoNotarialParcialBo dnpBo = new DocumentoNotarialParcialBoImpl();
				DocumentoNotarialParcial dnp = dnpBo
						.getLastVersion(idescritura);

				GeneraWord gw = new GeneraWord();
				try {
					StringBuilder txtEsc = new StringBuilder(
							EscrituraUtills.replaceMarkerLetra(dnp.getTxtdoc()));
					urlDoc = gw.creaEscrituraWord(txtEsc.toString(),
							esc.getDsnumescritura(), idtramite);
				} catch (IOException e) {
					throw new NotariaException(
							"Algo malo paso al obtener el documento",
							e.getCause());
				} catch (Exception e) {
					throw new NotariaException(
							"Algo malo paso al generar el documento ",
							e.getCause());
				}

				NotariaUtils.recuperaDocWord(urlDoc, response,
						"esc_" + esc.getDsnumescritura() + ".docx");
			} catch (NotariaException e) {
				Response.status(200).entity(
						"Algo malo pasó al generar el documento:"
								+ e.getMessage());
			} catch (Exception e) {
				Response.status(200).entity(
						"Algo malo pasó al recuperar el documento:"
								+ e.getMessage());
			}

		} else {
			Response.status(200).entity(Constantes.ESTATUS_SESION_INVALIDA)
					.build();
		}
		return null;
	}
	
	@POST
	@Path("/verificaCertificado")
	public EscrituraEnvio verificaCertificado(EscrituraEnvio ee){
		EscrituraEnvio respuesta = new EscrituraEnvio();
		if (ee.getEscritura() == null
				|| ee.getEscritura().getIdescritura() == null
				|| ee.getEscritura().getIdescritura().isEmpty()
				|| NotariaUtils.faltanRequeridosUsuario(ee)) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(ee.getUsuario().getIdsesionactual(), ee
				.getUsuario().getIdusuario())) {
			
			try {
				String fecha = this.escrituraActoBo.validaCertificado(ee.getEscritura());
				if(!fecha.isEmpty()){
					respuesta.setFechacertificado(fecha);
					respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				}
			} catch (NotariaException e) {
				respuesta.setExito(false);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
			}
			
		}else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}	
		
		return respuesta;
	}
	

}
