package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.ComponenteBo;
import com.palestra.notaria.bo.ExpresionBo;
import com.palestra.notaria.bo.VariableBo;
import com.palestra.notaria.bo.VariablesTipoBo;
import com.palestra.notaria.bo.impl.ComponenteBoImpl;
import com.palestra.notaria.bo.impl.ExpresionBoImpl;
import com.palestra.notaria.bo.impl.VariableBoImpl;
import com.palestra.notaria.bo.impl.VariablesTipoBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.ExpresionEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Expresion;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/expresion")
public class ExpresionRest {

	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public ExpresionEnvio guardar(ExpresionEnvio ex){
		ExpresionEnvio respuesta = new ExpresionEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ex) || ex.getExpresion() == null 
				|| ex.getExpresion().getIdvariable() == null){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ex.getUsuario().getIdsesionactual(), ex.getUsuario().getIdusuario())){
			try{
				if(validaSimetria(ex).isExito()){
					ExpresionBo expresionBo = new ExpresionBoImpl();
					VariableBo varBo = new VariableBoImpl();
					ComponenteBo compBo = new ComponenteBoImpl();
					VariablesTipoBo varTipoBo = new VariablesTipoBoImpl();
					Variable variable = varBo.findById(ex.getExpresion().getIdvariable());
					if(variable != null){
						if(varTipoBo.findByVariable(variable).size()==0){
							ex.getExpresion().getVariable().setVariable(variable);
							ex.getExpresion().getVariable().setIdvarstipo(GeneradorId.generaId(ex.getExpresion().getVariable()));
						}else{
							respuesta.setEstatus("Ésta variable ya tiene una expresión");
							respuesta.setExito(false);
							return respuesta;
						}
					}else{
						Componente componente = compBo.findById(ex.getExpresion().getIdvariable());
						if(varTipoBo.findByComponente(componente).size()==0){
							ex.getExpresion().getVariable().setComponente(componente);
							ex.getExpresion().getVariable().setIdvarstipo(GeneradorId.generaId(ex.getExpresion().getVariable()));
						}else{
							respuesta.setEstatus("Éste componente ya tiene una expresión");
							respuesta.setExito(false);
							return respuesta;
						}
					}
					varTipoBo.save(ex.getExpresion().getVariable());
					ex.getExpresion().setIdexpresion(GeneradorId.generaId(ex.getExpresion()));
					ex.getExpresion().setIdsesion(ex.getUsuario().getIdsesionactual());
					ex.getExpresion().setIsvalido(true);
					expresionBo.save(ex.getExpresion());
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
				}else{
					respuesta.setEstatus("la validacion de la expresion tuvo errores");
					respuesta.setErrores(validaSimetria(ex).getErrores());
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
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/actualizar")
	@Produces(MediaType.APPLICATION_JSON)
	public ExpresionEnvio actualizar(ExpresionEnvio ex){
		ExpresionEnvio respuesta = new ExpresionEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ex)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ex.getUsuario().getIdsesionactual(), ex.getUsuario().getIdusuario())){
			try{
				if(validaSimetria(ex).isExito()){
					ExpresionBo expresionBo = new ExpresionBoImpl();
//					ex.getExpresion().setIdexpresion(GeneradorId.generaId(ex.getExpresion()));
					ex.getExpresion().setIdsesion(ex.getUsuario().getIdsesionactual());
					expresionBo.update(ex.getExpresion());
					respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
					respuesta.setExito(true);
					return respuesta;
				}else{
					respuesta.setEstatus("la validacion de la expresion tuvo errores");
					respuesta.setErrores(validaSimetria(ex).getErrores());
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
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public ExpresionEnvio listar(ExpresionEnvio ex){
		ExpresionEnvio respuesta = new ExpresionEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ex)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ex.getUsuario().getIdsesionactual(), ex.getUsuario().getIdusuario())){
			try{
				ExpresionBo expresionBo = new ExpresionBoImpl();
				ex.getExpresion().setIdexpresion(GeneradorId.generaId(ex.getExpresion()));
				ex.getExpresion().setIdsesion(ex.getUsuario().getIdsesionactual());
				respuesta.getExpresionList().addAll(expresionBo.findAll());
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/listarPorVariable")
	@Produces(MediaType.APPLICATION_JSON)
	public ExpresionEnvio listarPorVariable(ExpresionEnvio ex){
		ExpresionEnvio respuesta = new ExpresionEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ex)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ex.getUsuario().getIdsesionactual(), ex.getUsuario().getIdusuario())){
			try{
				ExpresionBo expresionBo = new ExpresionBoImpl();
				ex.getExpresion().setIdexpresion(GeneradorId.generaId(ex.getExpresion()));
				ex.getExpresion().setIdsesion(ex.getUsuario().getIdsesionactual());
				List<Expresion> expresionesFound = expresionBo.listarPorVariable(ex.getExpresion().getIdvariable());
				if(expresionesFound != null){
					respuesta.getExpresionList().addAll(expresionBo.listarPorVariable(ex.getExpresion().getIdvariable()));
				}
				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/validaSimetria")
	@Produces(MediaType.APPLICATION_JSON)
	public ExpresionEnvio validaSimetria(ExpresionEnvio ex){
		ExpresionEnvio respuesta = new ExpresionEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ex)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		ExpresionBo expBo = new ExpresionBoImpl();
		List<CodigoError> errores = new ArrayList<CodigoError>();
		if(NotariaUtils.isSesionValida(ex.getUsuario().getIdsesionactual(), ex.getUsuario().getIdusuario())){
			if(expBo.isSimetrica(ex.getExpresion().getDsexpresion()).size()==0){
				if(expBo.existenVariables(ex.getExpresion().getDsexpresion()).size()==0
					&& expBo.existenGrupos(ex.getExpresion().getDsexpresion()).size()==0
					&& expBo.validaComponentes(ex.getExpresion().getDsexpresion()).size()==0){
					if(expBo.validaFunciones(ex.getExpresion().getDsexpresion()).size()==0){
						respuesta.setEstatus("Todo correcto en validacion de expresion");
						respuesta.setExito(true);
					}else{
						respuesta.setEstatus("Ocurrieron errores de validacion");
						errores.addAll(expBo.validaFunciones(ex.getExpresion().getDsexpresion()));
						respuesta.setErrores(errores);
						respuesta.setExito(false);
					}
				}else{
					errores.addAll(expBo.existenVariables(ex.getExpresion().getDsexpresion()));
					errores.addAll(expBo.existenGrupos(ex.getExpresion().getDsexpresion()));
					errores.addAll(expBo.validaComponentes(ex.getExpresion().getDsexpresion()));
					respuesta.setErrores(errores);
					respuesta.setEstatus("Hubo errores al validar elementos");
					respuesta.setExito(false);
				}
			}else{
				errores.addAll(expBo.isSimetrica(ex.getExpresion().getDsexpresion()));
				respuesta.setErrores(errores);
				respuesta.setEstatus("Hubo errores de sintaxis");
				respuesta.setExito(false);
			}
		return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
}
