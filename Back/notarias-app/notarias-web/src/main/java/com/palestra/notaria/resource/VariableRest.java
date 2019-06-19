package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ComponenteBo;
import com.palestra.notaria.bo.UbicacionVarEstaticaBo;
import com.palestra.notaria.bo.ValidacionBo;
import com.palestra.notaria.bo.VariableBo;
import com.palestra.notaria.bo.impl.ComponenteBoImpl;
import com.palestra.notaria.bo.impl.UbicacionVarEstaticaBoImpl;
import com.palestra.notaria.bo.impl.ValidacionBoImpl;
import com.palestra.notaria.bo.impl.VariableBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoVariableFormulario;
import com.palestra.notaria.envio.VariableEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.UbicacionVarEstatica;
import com.palestra.notaria.modelo.Validacion;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/variables")
public class VariableRest {
	
	static Logger logger = Logger.getLogger(VariableRest.class);
	
	VariableBo variableBo;
	
	ValidacionBo validacionBo;
	
	ComponenteBo componenteBo;
	
	public VariableRest(){
		variableBo = new VariableBoImpl();
		validacionBo = new ValidacionBoImpl();
		componenteBo = new ComponenteBoImpl();
	}


	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public VariableEnvio getVariables(VariableEnvio ve) {
		VariableEnvio respuesta = new VariableEnvio();
		if (ve == null || ve.getUsuario() == null
				|| ve.getUsuario().getIdusuario() == null || ve.getUsuario().getIdusuario().isEmpty()
				|| ve.getUsuario().getIdsesionactual() == null || ve.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ve.getUsuario().getIdsesionactual(),ve.getUsuario().getIdusuario())){
			try{
				List<Variable> variables = getVariableBo().findAll();
				List<Variable> variablesSistema = new ArrayList<Variable>();
				List<Variable> variablesCompareciente = new ArrayList<Variable>();				
				if(variables!=null && variables.size()>0){
					for(Variable variable:variables){
						if(variable.getTipoVariable().equalsIgnoreCase("var")){
							variablesSistema.add(variable);
						} else if(variable.getTipoVariable().equalsIgnoreCase("cte")){
							variablesCompareciente.add(variable);
						} else {
							logger.warn("====>  listado ::: Tipo de variable no identificado: "+variable.getTipoVariable());
						}
					}					
				}
				respuesta.setVariableList(variablesSistema);
				respuesta.setVariableCompareciente(variablesCompareciente);
				
				
				/**Obtener variables formularios con nombrecorto conformulario **/
				List<DatoVariableFormulario> listaCompo = componenteBo.listarVariableComponente();
				List<DatoVariableFormulario> componentesSubformulario = componenteBo.listarComponentesSubformulario();
				if(listaCompo!=null){
					respuesta.setVarFormDinamicos(new ArrayList<DatoVariableFormulario>(listaCompo));
				}
				if(componentesSubformulario != null){
					respuesta.setComponentesSubformulario(componentesSubformulario);
				}
				
			}catch(NotariaException e){

				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getVariableList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	

	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public VariableEnvio registraVariable(VariableEnvio ve){
		VariableEnvio respuesta = new VariableEnvio();
//		TODO: se quita getValidacionesList porque por el momento no se envia la lista de validaciones
		if (ve == null || ve.getUsuario() == null
				|| ve.getUsuario().getIdusuario() == null || ve.getUsuario().getIdusuario().isEmpty()
				|| ve.getUsuario().getIdsesionactual() == null || ve.getUsuario().getIdsesionactual().isEmpty()
				|| ve.getVariable() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ve.getUsuario().getIdsesionactual(),ve.getUsuario().getIdusuario())){
			ve.getVariable().setIdvariable(GeneradorId.generaId(ve.getVariable()));
			ve.getVariable().setIdsesion(ve.getUsuario().getIdsesionactual());
			ve.getVariable().setIsactivo(true);
//			ArrayList<Validacion> validacionesList = new ArrayList<Validacion>();
			try{
				respuesta.setVariable(getVariableBo().save(ve.getVariable()));
//				for(Validacion val:ve.getValidacionesList()){
//					val.setIdrestriccion(GeneradorId.generaId(val));
//					val.setIdsesion(ve.getUsuario().getIdsesionactual());
//					val.setVariable(respuesta.getVariable());
//					validacionesList.add(getValidacionBo().save(val));
//				}
//				respuesta.setValidacionesList(validacionesList);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/actualizar")
	@Produces({"application/json", "application/xml" })
	public VariableEnvio actualizar(VariableEnvio ve) {
		VariableEnvio respuesta = new VariableEnvio();
		if (ve == null || ve.getUsuario() == null
				|| ve.getUsuario().getIdusuario() == null || ve.getUsuario().getIdusuario().isEmpty()
				|| ve.getUsuario().getIdsesionactual() == null || ve.getUsuario().getIdsesionactual().isEmpty()
				|| ve.getVariable() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ve.getUsuario().getIdsesionactual(),ve.getUsuario().getIdusuario())){
			ve.getVariable().setIdsesion(ve.getUsuario().getIdsesionactual());
			ArrayList<Validacion> validacionesList = new ArrayList<Validacion>();
			try{
				respuesta.setVariable(getVariableBo().update(ve.getVariable()));
//				for(Validacion val:ve.getValidacionesList()){
//					val.setIdsesion(ve.getUsuario().getIdsesionactual());
//					val.setVariable(respuesta.getVariable());
//					validacionesList.add(getValidacionBo().update(val));
//				}
				respuesta.setValidacionesList(validacionesList);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getVariable()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/eliminar")
	@Produces({ "application/json", "application/xml" })
	public Response eliminar(Variable variable) {
		
		try{
			getVariableBo().delete(variable);
		}catch(Exception e){
			e.printStackTrace();
		}
		return Response.status(201).entity(variable).build();

	}

	
	@POST
	@Path("/asignarcalculo")
	@Produces(MediaType.APPLICATION_JSON)
	public VariableEnvio setCalculo(VariableEnvio datoEnvio){
		VariableEnvio respuesta = new VariableEnvio();
		
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null 
				|| datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null 
				|| datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getCalculo()==null
				|| datoEnvio.getCalculo().getVariable() == null 
				|| datoEnvio.getCalculo().getVariable().getIdvariable() == null
				|| datoEnvio.getCalculo().getVariable().getIdvariable().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		System.out.println("=====> Se recibio una variable para agregar su calculo: "+datoEnvio.getCalculo().getVariable().getIdvariable());
		if(NotariaUtils.isSesionValida(datoEnvio.getUsuario().getIdsesionactual(),datoEnvio.getUsuario().getIdusuario())){
			UbicacionVarEstatica calculo = datoEnvio.getCalculo();
			if(calculo.getDsentidad().isEmpty()
					|| calculo.getDsfiltro().isEmpty()
					|| calculo.getDspropiedad().isEmpty()){
				respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
				respuesta.setExito(false);
				return respuesta;
			}else{
				try{
					UbicacionVarEstaticaBo bo = new UbicacionVarEstaticaBoImpl();					
					calculo.setIdsesion(datoEnvio.getUsuario().getIdsesionactual());
					calculo.setTmstmp(new Timestamp(new Date().getTime()));
					int actualizo = bo.actualizar(calculo);
					if(actualizo>=0){
						respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
						respuesta.setExito(true);
						return respuesta;						
					}else{
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
						respuesta.setExito(false);
						return respuesta;						
					}
				}catch(NotariaException e){
					e.printStackTrace(System.out);
					respuesta.setEstatus(e.getMessage());
					respuesta.setExito(false);
					return respuesta;
				}
			}		
		}else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;			
		}		
	}
	@POST
	@Path("/obtenercalculo")
	@Produces(MediaType.APPLICATION_JSON)
	public VariableEnvio getCalculo(VariableEnvio datoEnvio){
		VariableEnvio respuesta = new VariableEnvio();		
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null 
				|| datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null 
				|| datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getVariable() == null 
				|| datoEnvio.getVariable().getIdvariable() == null
				|| datoEnvio.getVariable().getIdvariable().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		System.out.println("=====> Se recibio una variable para buscar su calculo: "+datoEnvio.getVariable().getIdvariable());
		if(NotariaUtils.isSesionValida(datoEnvio.getUsuario().getIdsesionactual(),datoEnvio.getUsuario().getIdusuario())){			
			try{
				UbicacionVarEstaticaBo bo = new UbicacionVarEstaticaBoImpl();					
				UbicacionVarEstatica calculo = bo.obtieneVariableEstatica(datoEnvio.getVariable());
				if(calculo!=null){
					respuesta.setCalculo(calculo);
					respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
				}else{
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_INEXISTENTE);
					respuesta.setExito(true);
					return respuesta;
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;			
		}		
	}
	
	@POST
	@Path("/obtenerPorId")
	@Produces({ "application/json", "application/xml" })
	public VariableEnvio obtenerPorId(VariableEnvio ve) {
		VariableEnvio respuesta = new VariableEnvio();
		if (ve == null || ve.getUsuario() == null
				|| ve.getUsuario().getIdusuario() == null || ve.getUsuario().getIdusuario().isEmpty()
				|| ve.getUsuario().getIdsesionactual() == null || ve.getUsuario().getIdsesionactual().isEmpty()
				|| ve.getVariable() == null || ve.getVariable().getIdvariable() == null
				|| ve.getVariable().getIdvariable().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ve.getUsuario().getIdsesionactual(),ve.getUsuario().getIdusuario())){
			try{
				respuesta.setVariable(getVariableBo().findById(ve.getVariable().getIdvariable()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getVariable()==null){
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}

	}		

	@POST
	@Path("/listaCampos")
	@Produces(MediaType.APPLICATION_JSON)
	public VariableEnvio getListaCampos(VariableEnvio datoEnvio){
		VariableEnvio respuesta = new VariableEnvio();
		if (datoEnvio == null 
				|| datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null 
				|| datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null 
				|| datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getCalculo().getDsentidad()==null
				|| datoEnvio.getCalculo().getDsentidad().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if(NotariaUtils.isSesionValida(datoEnvio.getUsuario().getIdsesionactual(),datoEnvio.getUsuario().getIdusuario())){
			try{
				UbicacionVarEstaticaBo bo = new UbicacionVarEstaticaBoImpl();
				System.out.println("=====> Entra a buscar los campos de "+datoEnvio.getCalculo().getDsentidad());
				List<String> campos = bo.obtieneCamposEntidad(datoEnvio.getCalculo().getDsentidad());
				System.out.println("=====> Localizo los campos: "+campos.size());
				respuesta.getCampos().addAll(campos);
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	
	public VariableBo getVariableBo() {
		return variableBo;
	}
	
	public void setVariableBo(VariableBo variableBo) {
		this.variableBo = variableBo;
	}
	
	public ValidacionBo getValidacionBo() {
		return validacionBo;
	}
	
	public void setValidacionBo(ValidacionBo validacionBo) {
		this.validacionBo = validacionBo;
	}
}
