package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ComponenteBo;
import com.palestra.notaria.bo.ConFormularioBo;
import com.palestra.notaria.bo.ExpresionBo;
import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.bo.ValorFormularioBo;
import com.palestra.notaria.bo.VariablesTipoBo;
import com.palestra.notaria.bo.impl.ComponenteBoImpl;
import com.palestra.notaria.bo.impl.ConFormularioBoImpl;
import com.palestra.notaria.bo.impl.ExpresionBoImpl;
import com.palestra.notaria.bo.impl.FormularioBoImpl;
import com.palestra.notaria.bo.impl.ValorFormularioBoImpl;
import com.palestra.notaria.bo.impl.VariablesTipoBoImpl;
import com.palestra.notaria.dao.impl.ConsultasPredefinidas;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.ConsultaPredefinida;
import com.palestra.notaria.envio.ConsultaPredefinidaEnvio;
import com.palestra.notaria.envio.FormularioDinamicoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.ActoFormulario;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;
import com.palestra.notaria.modelo.Expresion;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.PermisoRol;
import com.palestra.notaria.modelo.ValorFormulario;
import com.palestra.notaria.modelo.ValorSubFormulario;
import com.palestra.notaria.modelo.VariablesTipo;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/formularioDinamico")
public class FormularioDinamicoRest {

	static Logger logger = Logger.getLogger(FormularioDinamicoRest.class);
	private ConFormularioBo conFormularioBo = new ConFormularioBoImpl();
	private FormularioBo formularioBo = new FormularioBoImpl();
	private BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();
	private ComponenteBo componenteBo = new ComponenteBoImpl();

	@POST
	@Path("/listarComponentesPorSuboperacion")
	@Produces(MediaType.APPLICATION_JSON)
	public FormularioDinamicoEnvio listarComponentes(FormularioDinamicoEnvio fe) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (fe == null || fe.getUsuario() == null
				|| fe.getUsuario().getIdusuario() == null
				|| fe.getUsuario().getIdusuario().trim().isEmpty()
				|| fe.getUsuario().getIdsesionactual() == null
				|| fe.getUsuario().getIdsesionactual().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(fe.getUsuario().getIdsesionactual(), fe
				.getUsuario().getIdusuario())) {
			try {
				respuesta
						.setComponentesList((ArrayList<Componente>) this.componenteBo
								.listarPorSuboperacion(fe.getIdsuboperacion()));
			} catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		// ComponenteBo comp = new ComponenteBoImpl();
		// ConFormularioBo formularioBo = new ConFormularioBoImpl();
		// try {
		// respuesta.setFormularioList((ArrayList<ConFormulario>)formularioBo.listarPorActo("02e74f10e0327ad868d138f2b4fdd6f0"));
		// respuesta.setComponentesList((ArrayList<Componente>)comp.listarPorSuboperacion("02e74f10e0327ad868d138f2b4fdd6f0"));
		// } catch (NotariaException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// return respuesta;
	}

	@POST
	@Path("/listarConFormulario")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio listarConFormulario(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {

			List<ConFormulario> formularioList = null;
			try {
				
				
				
				// ConFormularioPK id = datoEnvio.getConformulario().getId();
				// id.setIdconFormulario(datoEnvio.getIdConformulario());
				// id.setVersion(datoEnvio.getVersion());
				
				formularioList = this.conFormularioBo
						.listarPorActo(datoEnvio.getIdsuboperacion());
				System.out.println("tiene "+formularioList.size());
				// System.out.println("tiene "+conformulario.getListaPermisosRol().size()+" permisos");
				// System.out.println("tiene "+conformulario.getListaComponentes().size()+" componentes");
				// System.out.println("tiene "+conformulario.getListaSubFormularios().size()+" subformularios");
				respuesta.setFormularioList(new ArrayList<>(formularioList));
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
	
	
	
	/* 
	 * ESTE SERVICIO SE OCUPA EN ALGÚN LADO*/
	@GET
	@Path("/buscarConfigFormulario")
	@Produces(MediaType.APPLICATION_JSON)
	public ConFormulario buscarConfigFormularioGet(@QueryParam("id")String id,@QueryParam("version")Integer version) throws NotariaException{
		ConFormularioPK pk = new ConFormularioPK();
		pk.setIdconFormulario(id);
		pk.setVersion(version);
		try {
			return conFormularioBo.buscarFormularioCompleto(pk);
		} catch (NotariaException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@POST
	@Path("/buscarConfigFormulario")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio buscarConfigFormulario(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getConformulario() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {

			ConFormulario conformulario = null;
			try {
				ConFormularioPK id = datoEnvio.getConformulario().getId();
				// id.setIdconFormulario(datoEnvio.getIdConformulario());
				// id.setVersion(datoEnvio.getVersion());
				
				if(datoEnvio.getActo()!=null && datoEnvio.getActo().getIdacto()!=null && !datoEnvio.getActo().getIdacto().isEmpty()){
					conformulario = this.conFormularioBo
							.buscarFormularioCompleto(id, datoEnvio.getActo());					
				}else{
					conformulario = this.conFormularioBo
							.buscarFormularioCompleto(id);
				}
				// System.out.println("tiene "+conformulario.getListaActoFormularios().size()+" actos");
				// System.out.println("tiene "+conformulario.getListaPermisosRol().size()+" permisos");
				// System.out.println("tiene "+conformulario.getListaComponentes().size()+" componentes");
				// System.out.println("tiene "+conformulario.getListaSubFormularios().size()+" subformularios");
				respuesta.setConformulario(conformulario);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
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
	@Path("/eliminarConfigFormulario")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio eliminarConFormulario(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getConformulario() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			try {
				if(datoEnvio.getConformulario().getId()!=null) {
					ConFormularioPK id = datoEnvio.getConformulario().getId();
					ValorFormularioBo valorFormBo = new ValorFormularioBoImpl();
//					se valida que los componentes del formulario no existan en la tabla de ValorFormulario
//					porque si existen no se puede eliminar el formulario
					ConFormulario conFormFound = conFormularioBo.buscarFormularioCompleto(id);
					for(Componente comp:conFormFound.getListaComponentes()){
						List<ValorFormulario> valorFormFound = valorFormBo.getOnlyByComponente(comp);
						if(valorFormFound.size()>0){
							respuesta.setEstatus("No es posible eliminar el formulario porque ya tiene valores");
							respuesta.setExito(false);
							return respuesta;
						}
					}
					this.conFormularioBo.elimninarFormulario(id.getIdconFormulario(), id.getVersion());
					respuesta.setConformulario(null);
					respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
					respuesta.setExito(true);
					return respuesta;			
				}else{
					respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
					respuesta.setExito(false);
					return respuesta;					
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
	
	private String inicalizaFormulario(ConFormulario formDinamico,
			String idsesion) throws NotariaException {
		Date fecha = new Date();
		Timestamp tstmp = new Timestamp(fecha.getTime());
		List<Componente> listaComponentes = formDinamico.getListaComponentes();
		List<ConSubFormulario> listaSubFormularios = formDinamico
				.getListaSubFormularios();
		List<ActoFormulario> listaActoFormularios = formDinamico
				.getListaActoFormularios();
		List<PermisoRol> listaPermisosRol = formDinamico.getListaPermisosRol();

		ConFormularioPK id = formDinamico.getId();
		Integer version = null;
		if (id != null && id.getIdconFormulario() != null
				&& !id.getIdconFormulario().isEmpty()
				&& id.getVersion() != null) {
			// buscar la ultima version
			version = this.conFormularioBo.buscarUltimaVersion(id.getIdconFormulario());
			if (version != null)
				version++;
			else
				version = 1;
			id.setVersion(version);

		} else {
			id = new ConFormularioPK();
			version = 1;
			id.setVersion(version);
			id.setIdconFormulario(GeneradorId.generaId(id));
			formDinamico.setId(id);
		}
		
		formDinamico.setIdsesion(idsesion);
		formDinamico.setTmstmp(tstmp);

		if (listaComponentes != null && !listaComponentes.isEmpty()) {
			for (Componente c : listaComponentes) {

				c.setConFormulario(formDinamico);
				c.setIdcomponente(GeneradorId.generaId(c));
				c.setIdsesion(idsesion);
				c.setTmstmp(tstmp);
			}
		}

		if (listaSubFormularios != null && !listaSubFormularios.isEmpty()) {
			for (ConSubFormulario csf : listaSubFormularios) {
				csf.setIdconsubform(GeneradorId.generaId(csf));
				csf.setConFormulario(formDinamico);
				csf.setIdsesion(idsesion);
				csf.setTmstmp(tstmp);
				listaComponentes = csf.getListaComponentes();
				if (listaComponentes != null && !listaComponentes.isEmpty()) {

					for (Componente c : listaComponentes) {
						c.setSubformulario(csf);
						c.setIsparasubform(true);
						c.setIdcomponente(GeneradorId.generaId(c));
						c.setIdsesion(idsesion);
						c.setTmstmp(tstmp);
					}
				}
			}
		}

		if (listaActoFormularios != null && !listaActoFormularios.isEmpty()) {
			for (ActoFormulario af : listaActoFormularios) {
				af.setConFormulario(formDinamico);
				af.setIdactoformulario(GeneradorId.generaId(af));
				af.setInestatus(" ");
				af.setIdsesion(idsesion);
				af.setTmstmp(tstmp);

			}
		}

		if (listaPermisosRol != null && !listaPermisosRol.isEmpty()) {
			for (PermisoRol pr : listaPermisosRol) {
				pr.setIdpermisorol(GeneradorId.generaId(pr));
				pr.setConFormulario(formDinamico);
				//pr.setInpermiso(0);
				pr.setIdsesion(idsesion);
				pr.setTmstmp(tstmp);

			}
		}
		return "";
	}

	private String buscarVariablesRepetidas(ConFormulario formDinamico) {
		StringBuilder resp = new StringBuilder();
		resp.append("");
		StringBuilder sb = new StringBuilder();
		HashMap<String, String> nombres = new HashMap<String, String>();
		StringBuilder sbaux = new StringBuilder();
		List<Componente> listaComponentes = formDinamico.getListaComponentes();
		List<ConSubFormulario> listaSubFormularios = formDinamico
				.getListaSubFormularios();
		sb.append("");

		if (listaComponentes != null && !listaComponentes.isEmpty()) {

			for (Componente c : listaComponentes) {
				if (!nombres.containsKey(c.getDsnombrevariable())) {
					// sb.append(c.getDsnombrevariable());
					nombres.put(c.getDsnombrevariable(), "1");
				} else {
					resp.append(c.getDsnombrevariable());
					resp.append(", ");
					break;
				}
			}
		}

		if (listaSubFormularios != null && !listaSubFormularios.isEmpty()) {
			sbaux.append("");
			for (ConSubFormulario csf : listaSubFormularios) {
				if (!nombres.containsKey(csf.getDsnombrecorto())) {
					nombres.put(csf.getDsnombrecorto(), "1");
					sbaux.append(csf.getDsnombrecorto());
				} else {
					resp.append(" nombre corto de subformulario repetido :"
							+ csf.getDsnombrecorto());
					resp.append(", ");
					break;
				}

				listaComponentes = csf.getListaComponentes();
				if (listaComponentes != null && listaComponentes.isEmpty()) {
					for (Componente c : listaComponentes) {
						if (sb.lastIndexOf(c.getDsnombrevariable()) == -1) {
							sb.append(c.getDsnombrevariable());
						} else {

							resp.append(c.getDsnombrevariable());
							resp.append(" del subformulario " + csf.getNombre());
							resp.append(", ");
							break;
						}
					}
				}
			}
		}

		String r = resp.toString();
		if (resp.length() > 2)
			return r.substring(0, resp.length() - 2);
		else
			return r;
	}

	@POST
	@Path("/listaFormulariosParaActos")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio listaFormulariosParaActos(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getExpediente() == null
				|| datoEnvio.getExpediente().getIdexpediente() == null
				|| datoEnvio.getExpediente().getIdexpediente().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {

			try {
				List<ActoFormulario> lista = null;

				// 180614 cafaray -> Se cambia el llamado al método que filtra por tipo de formulario
				if (datoEnvio.getActo() != null) {
					lista = this.conFormularioBo.formulariosPorExpediente(
							datoEnvio.getExpediente().getIdexpediente(),
							datoEnvio.getActo().getIdacto(), datoEnvio.getConformulario().getTipoform());
				} else {
					lista = this.conFormularioBo.formulariosPorExpediente(
							datoEnvio.getExpediente().getIdexpediente(), null, datoEnvio.getConformulario().getTipoform());
				}
				// 180614 cafaray <-
				
				if (lista != null){
					respuesta.setListaFormularios(new ArrayList<ActoFormulario>(lista));
				}else{
					respuesta.setListaFormularios(new ArrayList<ActoFormulario>());
				}
				
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

	@GET
	@Path("/impactoConFormulario")
	@Produces("application/json")
	public FormularioDinamicoEnvio evaluaImpactoActualizacionConFormulario(FormularioDinamicoEnvio datoEnvio){
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getConformulario() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {

			ConFormulario formDinamico = datoEnvio.getConformulario();
			try {

				String aux = buscarVariablesRepetidas(formDinamico);
				if (aux.isEmpty()) {
					String mensaje;
					mensaje = this.conFormularioBo.testActualizaFormulario(formDinamico);						
					respuesta.setEstatus(mensaje);
					respuesta.setExito(true);
					respuesta.setIdConformulario(formDinamico.getId()
							.getIdconFormulario());
					respuesta.setVersion(formDinamico.getId().getVersion());
				} else {
					respuesta.setExito(false);
					respuesta.setEstatus(Constantes.NOBRE_VAR_REPEDITA + aux);
				}
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
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
	@Path("/guardarConfigFormulario")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio guardarConfigFormulario(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getConformulario() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {

			ConFormulario formDinamico = datoEnvio.getConformulario();
			try {

				String aux = buscarVariablesRepetidas(formDinamico);
				if (aux.isEmpty()) {
					String mensaje;
					formDinamico.setIspublicado(formDinamico.getIspublicado()==null?false:formDinamico.getIspublicado());
					if(!formDinamico.getIspublicado()){
					//if(formDinamico.getId().getIdconFormulario()==null && formDinamico.getId().getVersion()==null){
						inicalizaFormulario(formDinamico, datoEnvio.getUsuario().getIdsesionactual());					
						this.conFormularioBo.save(formDinamico);
						mensaje = Constantes.ESTATUS_REGISTRO_CORRECTO;
					} else {
						formDinamico.setIdsesion(datoEnvio.getUsuario().getIdsesionactual());
						mensaje = this.conFormularioBo.actualizaFormulario(formDinamico);		
						respuesta.setEstatus(mensaje);
						respuesta.setExito(true);
						respuesta.setIdConformulario(formDinamico.getId().getIdconFormulario());
						respuesta.setVersion(formDinamico.getId().getVersion());
						return respuesta;
					}
					formDinamico = this.conFormularioBo.buscarFormularioCompleto(formDinamico.getId());
//					@omarete 110914 aqui se copian las expresiones para los componentes que se generaron
					if(formDinamico.getId().getVersion()>1){						
						ConFormulario formAnterior = new ConFormulario();
						ConFormularioPK formPk = new ConFormularioPK();
						formPk.setIdconFormulario(formDinamico.getId().getIdconFormulario());
						formPk.setVersion(formDinamico.getId().getVersion()-1);
						formAnterior.setId(formPk);
						formAnterior = this.conFormularioBo.buscarFormularioCompleto(formPk);
						ExpresionBo expresionBo = new ExpresionBoImpl();
						for(Componente compAnterior:formAnterior.getListaComponentes()){
							Expresion expresion = expresionBo.getExpresionByIdComp(compAnterior.getIdcomponente());
							if(expresion != null){
								for(Componente compNuevo:formDinamico.getListaComponentes()){
									if(compNuevo.getDsnombrevariable().equals(compAnterior.getDsnombrevariable())){
										Expresion exp = new Expresion();
										exp.setDsexpresion(expresion.getDsexpresion());
										exp.setIdexpresion(GeneradorId.generaId(exp));
										exp.setIdsesion(datoEnvio.getUsuario().getIdsesionactual());
										exp.setIsvalido(true);
										exp.setIfnulo(expresion.getIfnulo());
										exp.setVariable(new VariablesTipo());
										exp.getVariable().setComponente(compNuevo);
										exp.getVariable().setIdvarstipo(GeneradorId.generaId(exp.getVariable()));
										VariablesTipoBo varTipoBo = new VariablesTipoBoImpl();
										varTipoBo.save(exp.getVariable());
										expresionBo.save(exp);
									}
								}
							}
						}
					}
					respuesta.setEstatus(mensaje);
					respuesta.setExito(true);
					respuesta.setIdConformulario(formDinamico.getId().getIdconFormulario());
					respuesta.setVersion(formDinamico.getId().getVersion());
				} else {
					respuesta.setExito(false);
					respuesta.setEstatus(Constantes.NOBRE_VAR_REPEDITA + aux);
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

	@POST
	@Path("/publicarConfigFormulario")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio publicarConfigFormulario(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getConformulario() == null
				|| datoEnvio.getConformulario().getId() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			// ConFormulario formDinamico = datoEnvio.getConformulario();
			try {
				boolean b = this.conFormularioBo.publicarFormulario(datoEnvio
						.getConformulario().getId().getIdconFormulario(),
						datoEnvio.getConformulario().getId().getVersion());
				if (b)
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				else {
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
	@Path("/obtenerValoresFormulario")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio obtenerValoresFormulario(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getActo() == null
				|| datoEnvio.getActo().getIdacto() == null
				|| datoEnvio.getActo().getIdacto().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {

			try {
				List<Formulario> lista = this.formularioBo
						.buscarFormulariosPorActo(datoEnvio.getActo()
								.getIdacto());
				respuesta.setListaValoresFormularios(new ArrayList<Formulario>(
						lista));
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
	@Path("/valoresFormulario")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio obtenerValorFormulario(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getActo() == null
				|| datoEnvio.getActo().getIdacto() == null
				|| datoEnvio.getActo().getIdacto().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {

			try {
				Formulario formulario = this.formularioBo
						.buscarFormulariosPorActo(datoEnvio.getActo()
								.getIdacto(), datoEnvio.getConformulario()
								.getId());
				respuesta
						.setListaValoresFormularios(new ArrayList<Formulario>());
				respuesta.getListaValoresFormularios().add(formulario);
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
	@Path("/guardarValoresFormulario")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio guardarValoresFormulario(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		respuesta.setErrores(listaErrores);
		CodigoError error;
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getListaValoresFormularios() == null
				|| datoEnvio.getExpediente() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {

			try {
				Date fecha = new Date();
				ArrayList<Formulario> listaValoresFormularios = datoEnvio.getListaValoresFormularios();
				if (listaValoresFormularios != null
						&& !listaValoresFormularios.isEmpty()) {
					List<ValorFormulario> listaValFormulario;
					List<ValorSubFormulario> listaValSubFormulario;
					for (Formulario form : listaValoresFormularios) {

						listaValFormulario = form.getListaValFormulario();
						if (listaValFormulario != null
								&& !listaValFormulario.isEmpty()) {
							for (ValorFormulario vf : listaValFormulario) {
								if (vf.getComponente().getIsrequerido() != null
										&& vf.getComponente().getIsrequerido()
										&& (vf.getValorcadena() == null || vf
												.getValorcadena().trim()
												.isEmpty())) {
									error = new CodigoError();
									error.setCodigo("");
									error.setMensaje("El campo "
											+ vf.getComponente()
													.getDsetiqueta()
											+ " es requerido");
									respuesta
											.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
									respuesta.setExito(false);
									listaErrores.add(error);
								}
							}
						}

						listaValSubFormulario = form.getListaValSubFormulario();
						if (listaValSubFormulario != null
								&& !listaValSubFormulario.isEmpty()) {
							for (ValorSubFormulario vsf : listaValSubFormulario) {
								if (vsf.getComponente().getIsrequerido() != null
										&& vsf.getComponente().getIsrequerido()
										&& (vsf.getValorcadena() == null || vsf
												.getValorcadena().trim()
												.isEmpty())) {
									error = new CodigoError();
									error.setCodigo("");
									error.setMensaje("El campo "
											+ vsf.getComponente()
													.getDsetiqueta()
											+ " es requerido");
									respuesta
											.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
									respuesta.setExito(false);
									listaErrores.add(error);
								}
							}
						}

						if (!respuesta.isExito()) {
							return respuesta;
						}

						form.setIdformulario(GeneradorId.generaId(form));
						form.setTmstmp(new Timestamp(fecha.getTime()));
						form.setIdsesion(datoEnvio.getUsuario()
								.getIdsesionactual());
						boolean b = this.formularioBo
								.guardarValoresFormulario(form,datoEnvio.getUsuario());
						if (b) {
							this.bitacoraGeneralHelper
									.registrarEnBitacora(datoEnvio.getUsuario()
											.getIdusuario(), datoEnvio
											.getExpediente().getIdexpediente(),
											null, "Formulario",
											Constantes.OPERACION_REGISTRO,
											"Se registra un Formulario");
							respuesta
									.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						}
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
	@Path("/actualizarValoresFormulario")
	@Produces({ "application/json", "application/xml" })
	public FormularioDinamicoEnvio actualizarValoresFormulario(
			FormularioDinamicoEnvio datoEnvio) {
		FormularioDinamicoEnvio respuesta = new FormularioDinamicoEnvio();
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		respuesta.setErrores(listaErrores);
		CodigoError error;
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getListaValoresFormularios() == null
				|| datoEnvio.getExpediente() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {

			try {
				Date fecha = new Date();
				ArrayList<Formulario> listaValoresFormularios = datoEnvio
						.getListaValoresFormularios();
				if (listaValoresFormularios != null
						&& !listaValoresFormularios.isEmpty()) {
					List<ValorFormulario> listaValFormulario;
					List<ValorSubFormulario> listaValSubFormulario;
					for (Formulario f : listaValoresFormularios) {
						listaValFormulario = f.getListaValFormulario();
						if (listaValFormulario != null
								&& !listaValFormulario.isEmpty()) {
							for (ValorFormulario vf : listaValFormulario) {
								if (vf.getComponente().getIsrequerido() != null
										&& vf.getComponente().getIsrequerido()
										&& (vf.getValorcadena() == null || vf
												.getValorcadena().trim()
												.isEmpty())) {
									error = new CodigoError();
									error.setCodigo("");
									error.setMensaje("El campo "
											+ vf.getComponente()
													.getDsetiqueta()
											+ " es requerido");
									respuesta
											.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
									respuesta.setExito(false);
									listaErrores.add(error);
								}
							}
						}

						listaValSubFormulario = f.getListaValSubFormulario();
						if (listaValSubFormulario != null
								&& !listaValSubFormulario.isEmpty()) {
							for (ValorSubFormulario vsf : listaValSubFormulario) {
								if (vsf.getComponente().getIsrequerido() != null
										&& vsf.getComponente().getIsrequerido()
										&& (vsf.getValorcadena() == null || vsf
												.getValorcadena().trim()
												.isEmpty())) {
									error = new CodigoError();
									error.setCodigo("");
									error.setMensaje("El campo "
											+ vsf.getComponente()
													.getDsetiqueta()
											+ " es requerido");
									respuesta
											.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
									respuesta.setExito(false);
									listaErrores.add(error);
								}
							}
						}

						if (!respuesta.isExito()) {
							return respuesta;
						}
						// f.setIdformulario(GeneradorId.generaId(f));
						if (f.getIdformulario() == null
								|| f.getIdformulario().trim().isEmpty()
								|| f.getActo() == null
								|| f.getConFormulario() == null) {
							respuesta
									.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
							respuesta.setExito(false);
							return respuesta;
						}
						f.setTmstmp(new Timestamp(fecha.getTime()));
						f.setIdsesion(datoEnvio.getUsuario()
								.getIdsesionactual());
						boolean b = this.formularioBo
								.actualizarValoresFormulario(f);
						if (b) {
							this.bitacoraGeneralHelper
									.registrarEnBitacora(datoEnvio.getUsuario()
											.getIdusuario(), datoEnvio
											.getExpediente().getIdexpediente(),
											null, "Formulario",
											Constantes.OPERACION_ACTUALIZACION,
											"Se actualiza un Formulario");
							respuesta
									.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						}
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
	@Path("/listadoValoresCaonsulta")
	@Produces({ "application/json", "application/xml" })
	public ConsultaPredefinidaEnvio listaValoresConsulta(
			ConsultaPredefinidaEnvio datoEnvio) {
		ConsultaPredefinidaEnvio respuesta = new ConsultaPredefinidaEnvio();
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		respuesta.setErrores(listaErrores);
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getConsulta() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			try {
				ConsultaPredefinida cp = datoEnvio.getConsulta();
				datoEnvio.setValores(ConsultasPredefinidas.getInstance()
						.obtenerValores(cp.getHql()));
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
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
			return respuesta;
		}
	}

}
