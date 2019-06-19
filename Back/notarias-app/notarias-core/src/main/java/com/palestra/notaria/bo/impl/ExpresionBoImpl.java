package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import com.palestra.notaria.bo.ComponenteBo;
import com.palestra.notaria.bo.ExpresionBo;
import com.palestra.notaria.bo.FuncionBo;
import com.palestra.notaria.bo.GrupoBo;
import com.palestra.notaria.bo.VariableBo;
import com.palestra.notaria.dao.ExpresionDao;
import com.palestra.notaria.dao.impl.ExpresionDaoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Expresion;
import com.palestra.notaria.modelo.Grupo;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.util.FuncionPojo;

public class ExpresionBoImpl extends GenericBoImpl<Expresion> implements
		ExpresionBo {

	private ExpresionDao expresionDao;
	
	public ExpresionBoImpl(){
		this.expresionDao = new ExpresionDaoImpl();
		super.dao = this.expresionDao;
	}
	
	@Override
	public List<Expresion> listarPorVariable(String idvariable) throws NotariaException{
		return this.expresionDao.listarPorVariable(idvariable);
	}

	@Override
	public List<Expresion> getByComponente(String idcomponente)throws NotariaException{
		return this.expresionDao.getByComponente(idcomponente);
	}
	
	@Override
	public Expresion getExpresionByIdComp(String idcomp) throws NotariaException{
		return this.expresionDao.getExpresionByIdComp(idcomp);
	}
	
	/**
	 * Valida que una función exista y el número de parámetros corresponda al declarado
	 * 
	 * @param expresion
	 * @return objeto expresionEnvio con exito = true si es correcta la funcion, false de otra manera
	 */
	@Override
	public List<CodigoError> validaFunciones(String expresion){
//		ExpresionEnvio respuesta = new ExpresionEnvio();
		FuncionBo funcionBo = new FuncionBoImpl();
		List<CodigoError> errores = new ArrayList<CodigoError>();
//		respuesta.setErrores(errores);
		List<FuncionPojo> funciones = new ArrayList<FuncionPojo>(); 
		String current = expresion;
		System.out.println("expresion en rest "+expresion);
		System.out.println("ANALIZA EXPRESION **********************");
		while(current.contains("f:")){
			current = funcionBo.analizaExpresion(current, funciones);
		}
		System.out.println("ANALIZA FUNCIONES **********************");
		for(FuncionPojo funcion:funciones){
			System.out.println("funcion al analizar funciones "+funcion.getNombre());
			try {
				errores.addAll((funcionBo.analizaFuncion(funcion).getErrores()));
			} catch (NotariaException e) {
				e.printStackTrace();
			}
		}
		for(CodigoError cderr:errores){
			System.out.println("error "+cderr.getMensaje());
		}
		if(errores.size()>0){
//			respuesta.setEstatus("Errores al validar funciones");
//			respuesta.setExito(false);
		}else{
//			respuesta.setEstatus("Validacion correcta");
//			respuesta.setExito(true);
		}
		return errores;
	}
	
	/**
	 * Valida que existan en BD los componentes especificados en un String
	 * 
	 * @param expresion
	 * @return objeto expresionEnvio con exito = true si existen, false de otra manera
	 */
	@Override
	public List<CodigoError> validaComponentes(String expresion){
//		ExpresionEnvio respuesta = new ExpresionEnvio();
		ComponenteBo componenteBo = new ComponenteBoImpl();
//		ConFormularioBo conformBo = new ConFormularioBoImpl();
		List<CodigoError> errores = new ArrayList<CodigoError>();
		if(StringUtils.substringsBetween(expresion, "${cmp:", "}")==null){
//			respuesta.setEstatus("Sin coincidencias");
//			respuesta.setExito(true);
			return errores;
		}else{
			for(String str:StringUtils.substringsBetween(expresion, "${cmp:", "}")){
				CodigoError error = new CodigoError();
				try{
					String nomVar = StringUtils.substringBetween(str, "[", "]");
					String nomForm = StringUtils.substringBeforeLast(str, "[");
					if(nomVar == null || nomForm == null){
						error.setMensaje("Falta el nombre de variable o formulario, o esta mal formada la sintaxis para el elemento: "+str);
						errores.add(error);
					}else{
						// Victor: valido que se puedan ingresar identificadores de subformulario para que se pueda esconder completo
						if(!nomVar.startsWith("frm_dinamic")){
							Componente componenteFound = componenteBo.buscarPorNombreCortoConForm(nomForm, nomVar);
							if(componenteFound == null){
								error.setMensaje("No se encontró el componente: "+str);
								errores.add(error);
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
	//				agregar mensaje a lista de errores
				}
			}
		}
		
		if(StringUtils.substringsBetween(expresion, "${frm:", "}")==null){
//			respuesta.setEstatus("Sin coincidencias");
//			respuesta.setExito(true);
			return errores;
		}else{
			for(String str:StringUtils.substringsBetween(expresion, "${frm:", "}")){
				CodigoError error = new CodigoError();
				try{
					String nomVar = StringUtils.substringBetween(str, "[", "]");
					String nomForm = StringUtils.substringBeforeLast(str, "[");
					if(nomVar == null || nomForm == null){
						error.setMensaje("Falta el nombre de variable o formulario, o esta mal formada la sintaxis para el elemento: "+str);
						errores.add(error);
					}else{
						Componente componenteFound = componenteBo.buscarPorNombreCortoConForm(nomForm, nomVar);
						if(componenteFound == null){
							error.setMensaje("No se encontró el componente: "+str);
							errores.add(error);
						}
					}
				}catch(Exception e){
					e.printStackTrace();
	//				agregar mensaje a lista de errores
				}
			}
		}
		
		if(errores.size()>0){
//			respuesta.setErrores(errores);
//			respuesta.setExito(false);
		}else{
//			respuesta.setExito(true);
		}
		return errores;
	}
	
	/**
	 * Valida que los grupos existan en la BD
	 * 
	 * @param expresion
	 * @return objeto expresionEnvio con exito = true si existen los grupos, false de otra manera
	 */
	@Override
	public List<CodigoError> existenGrupos(String expresion){
//		ExpresionEnvio respuesta = new ExpresionEnvio();
		GrupoBo grupoBo = new GrupoBoImpl();
		List<CodigoError> errores = new ArrayList<CodigoError>();
		if(StringUtils.substringsBetween(expresion, "${gpo:", "}")==null){
//			respuesta.setEstatus("Sin coincidencias");
//			respuesta.setExito(true);
			return errores;
		}
		
		for(String str:StringUtils.substringsBetween(expresion, "${gpo:", "}")){
			CodigoError error = new CodigoError();
			try{
				System.out.println("str "+str);
				List<Grupo> gpo = grupoBo.findByProperties(new Grupo(str));
				if(gpo.size()==0){
					error.setMensaje("No existe el grupo "+str);
					System.out.println(error.getMensaje());
					errores.add(error);
//					System.out.println("Agrega error");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(errores.size()>0){
//			respuesta.setErrores(errores);
//			respuesta.setExito(false);
		}else{
//			respuesta.setExito(true);
		}
		return errores;
	}
	
	/**
	 * Valida que las variables dentro de una expresion existan en la BD
	 * 
	 * @param expresion
	 * @return true si las variables existen, falso de otra manera
	 */
	@Override
	public List<CodigoError> existenVariables(String expresion){
//		ExpresionEnvio respuesta = new ExpresionEnvio();
//		List<String> variables = new ArrayList<String>();
//		variables = Arrays.asList(StringUtils.substringsBetween(expresion, "${var:", "}"));
		VariableBo varBo = new VariableBoImpl();
		List<CodigoError> errores = new ArrayList<CodigoError>();
		if(StringUtils.substringsBetween(expresion, "${var:", "}")==null){
//			respuesta.setEstatus("Sin coincidencias");
//			respuesta.setExito(true);
			return errores;
		}

		CodigoError error = new CodigoError();
		for(String str:StringUtils.substringsBetween(expresion, "${var:", "}")){
			try{
				System.out.println("str "+str);
				Variable var = varBo.findByName(str);
				System.out.println("objeto var "+var);
				if(var==null){
					
					error.setMensaje("No existe la variable "+str);
					errores.add(error);
					System.out.println("Agrega error");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(errores.size()>0){
//			respuesta.setErrores(errores);
//			respuesta.setExito(false);
		}else{
//			respuesta.setExito(true);
		}
		
		return errores;
	}
	
	/**
	 * Valida que una expresión que contenga ( ), { } y [ ] sea simétrica 
	 * 
	 * @param expresion
	 * @return expresionEnvio.exito = true si es simétrica, false de otra manera
	 */
	@Override
	public List<CodigoError> isSimetrica(String expresion){
//		ExpresionEnvio respuesta = new ExpresionEnvio();
		Stack<Character> stack = new Stack<Character>();
		List<CodigoError> errores = new ArrayList<CodigoError>(); 
		for(Character c:expresion.toCharArray()){
			if(stack.size()>0){
				if(stack.get(stack.size()-1).equals('\'')){
					if(c == '\''){
						stack.remove(stack.size()-1);
						continue;
					}else{
						continue;
					}
				}else if(stack.get(stack.size()-1).equals('\"')){
					if(c == '\"'){
						stack.remove(stack.size()-1);
						continue;
					}else{
						continue;
					}
				}
			}
			switch(c){
			case '(':
				stack.add(c);
				break;
			case '[':
				stack.add(c);
				break;
			case '{':
				stack.add(c);
				break;
			case ')':
				cerrarElemento(stack,')',errores);
				break;
			case ']':
				cerrarElemento(stack, ']',errores);
				break;
			case '}':
				cerrarElemento(stack, '}',errores);
				break;
			case '\'':
					stack.add(c);
				break;
			case '\"':
					stack.add(c);
				break;
			}
		}
		for(CodigoError c:errores){
			System.out.println(c.getMensaje());
		}
		if(stack.size()>0){
			CodigoError error = new CodigoError();
			error.setMensaje("No se cerraron apropiadamente los siguientes elementos: "+stack);
			errores.add(error);
		}
		
//		if(stack.size()==0 && errores.size()==0){
//			respuesta.setErrores(errores);
//			respuesta.setExito(true);
//			return respuesta;
//		}else{
//			respuesta.setErrores(errores);
//			respuesta.setExito(false);
			return errores;
//		}
	}
	
	/**
	 * Verifica que el elemento que se envia para cerrar corresponda con el ultimo elemento del stack
	 * 
	 * @param stack
	 * @param currentElement
	 * @param errores
	 */
	@Override
	public void cerrarElemento(Stack<Character> stack, Character currentElement,List<CodigoError> errores){
		CodigoError error = new CodigoError();
		if(stack.size()<1){
			error.setMensaje("No existe apertura para el elemento: "+currentElement);
			errores.add(error);
			return;
		}
		switch(stack.get(stack.size()-1)){
		case '(':
			if(currentElement == ')'){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: ) pero se encontró: "+currentElement);
				errores.add(error);
			}
			break;
		case '[':
			if(currentElement == ']'){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: ] pero se encontró: "+currentElement);
				errores.add(error);
			}
			break;
		case '{':
			if(currentElement == '}'){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: } pero se encontró: "+currentElement);
				errores.add(error);
			}
			break;
		case '\'':
			if(currentElement == '\''){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: \' pero se encontró: "+currentElement);
				errores.add(error);
			}
			break;
		case '\"':
			if(currentElement == '\"'){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: \" pero se encontró: "+currentElement);
				errores.add(error);
			break;
			}
		}
	}

}
