package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import com.palestra.notaria.bo.ComponenteBo;
import com.palestra.notaria.bo.PlantillaDocumentoNotarialBo;
import com.palestra.notaria.bo.VariableBo;
import com.palestra.notaria.dao.PlantillaDocumentoNotarialDao;
import com.palestra.notaria.dao.impl.PlantillaDocumentoNotarialDaoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarialPK;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarialSubOperacion;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.util.GeneradorId;

public class PlantillaDocumentoNotarialBoImpl extends GenericBoImpl<PlantillaDocumentoNotarial> implements PlantillaDocumentoNotarialBo{

	private PlantillaDocumentoNotarialDao plantillaDocumentoNotarialDao;
	
	public PlantillaDocumentoNotarialBoImpl() {
		this.plantillaDocumentoNotarialDao = new PlantillaDocumentoNotarialDaoImpl();
		super.dao = this.plantillaDocumentoNotarialDao;
	}
	
	@Override
	public PlantillaDocumentoNotarial getPublishBySubOperacionId(String id) throws NotariaException{
		return this.plantillaDocumentoNotarialDao.getPublishBySubOperacionId(id);
	}
	
	@Override
	public PlantillaDocumentoNotarial getLastVersion(String idplantilla) throws NotariaException{
		return this.plantillaDocumentoNotarialDao.getLastVersion(idplantilla);
	}
	
	@Override
	public List<PlantillaDocumentoNotarial> getNoPublicados() throws NotariaException{
		return this.plantillaDocumentoNotarialDao.getNoPublicados();
	}
	
	@Override
	public List<PlantillaDocumentoNotarial> getPublicados() throws NotariaException{
		return this.plantillaDocumentoNotarialDao.getPublicados();
	}
	
	@Override
	public PlantillaDocumentoNotarial findById(PlantillaDocumentoNotarialPK id) throws NotariaException{
		return this.plantillaDocumentoNotarialDao.findById(id);
	}

	@Override
	public Integer findMaxVersion(String iddocnot) throws NotariaException {
		return this.plantillaDocumentoNotarialDao.findMaxVersion(iddocnot);
	}

	@Override
	public PlantillaDocumentoNotarial findDocumentoPublicado(String iddocnot) throws NotariaException {
		return this.plantillaDocumentoNotarialDao.findDocumentoPublicado(iddocnot);
	}

	@Override
	public PlantillaDocumentoNotarial buscarPorIdCompleto(String iddocnot,Integer inversion) throws NotariaException {
		return this.plantillaDocumentoNotarialDao.buscarPorIdCompleto(iddocnot,inversion);
	}
	
//	@Override
//	public PlantillaDocumentoNotarial save(PlantillaDocumentoNotarial plantilla) throws NotariaException {
//		//verifica si existe ya una plantilla publicada para esta suboperacion y localidad
//		// --> ElementoCatalogo locacion = plantilla.getLocacion();
//		// --> Suboperacion suboperacion = plantilla.getSuboperacion();
//		// --> PlantillaDocumentoNotarial plantillaExiste = plantillaDocumentoNotarialDao.buscarPorSuboperacionLocacion(plantilla.getSuboperacion(), plantilla.getLocacion());
//		// --> if(plantillaExiste!=null){
//			// en caso de que sí, se cancela la operaciòn de persistencia y se envia el mensaje
//		// --> 	throw new NotariaException("La plantilla ha sído asociada a esta suboperacion y locacion, imposible volver a asignar.");
//		// --> } else {
//			// en caso de que no, se inserta el registro
//			return super.save(plantilla);
//		// --> }
//	}
	
	@Override
	public List<CodigoError> validarReferencias(String plantilla){
		List<CodigoError> errores = new ArrayList<CodigoError>();
		List<String> plantillaRefs = new ArrayList<String>();
		List<String> referencias = new ArrayList<String>();
		if(StringUtils.substringsBetween(plantilla, "&amp;", ";")==null ||
				(plantilla.indexOf("REFERENCIAS")<1 && plantilla.length()<1)){
			System.out.println("entra if de no encontrar coincidencias");
			return errores;
		}
//		se obtiene el bloque de las referencias
		String bloqueReferencias = plantilla.substring(plantilla.indexOf("REFERENCIAS"), plantilla.length()).trim();
		System.out.println("bloque referencias "+bloqueReferencias);
		for(String str:StringUtils.substringsBetween(bloqueReferencias, "**", ";")){
			referencias.add(str);
		}
//			se obtienen referencias del documento
		for(String str:StringUtils.substringsBetween(plantilla, "&amp;", ";")){
			CodigoError error = new CodigoError();
			plantillaRefs.add(str);
			if(!referencias.contains(str)){
				error.setMensaje("El apartado REFERENCIAS no contiene la referencia: "+str);
				errores.add(error);
				System.out.println("no contiene la referencia "+str);
			}
		}
		for(String str:plantillaRefs){
			System.out.println("Referencia: "+str);
		}

//			for(String )
//			se valida sintaxis del bloque de referencias 
//			String regex = "REFERENCIAS{"+
//						
//							"}";
//		System.out.println("regex "+bloqueReferencias.matches(regex));
//			se obtienen referencias del bloque de referencias y se comparan con las del doc
		
//			
		System.out.println("plantilla.indexof(referencias) "+plantilla.indexOf("REFERENCIAS"));
		System.out.println("substring "+plantilla.substring(plantilla.indexOf("REFERENCIAS"), plantilla.length()));
//			plantilla.s
		System.out.println("errores size "+errores.size());
		return errores;
	}
	
	/**
	 * Este metodo valida que existan variables dentro de una plantilla y que si contiene funciones, tengan una sintaxis
	 * correcta
	 * 
	 */
	@Override
	public List<CodigoError> validaVariables(String plantilla){
//		ExpresionEnvio respuesta = new ExpresionEnvio();
//		List<String> variables = new ArrayList<String>();
//		variables = Arrays.asList(StringUtils.substringsBetween(expresion, "${var:", "}"));
		VariableBo varBo = new VariableBoImpl();
		ComponenteBo compBo = new ComponenteBoImpl();
		List<CodigoError> errores = new ArrayList<CodigoError>();
		String [] varArray = StringUtils.substringsBetween(plantilla, "${frm:", "}");
		String [] frmArray = StringUtils.substringsBetween(plantilla, "${frm:", "}");
		if(varArray==null && frmArray==null){
//			respuesta.setEstatus("Sin coincidencias");
//			respuesta.setExito(true);
			return errores;
		}
		System.out.println("frm "+frmArray.length);
//		System.out.println("var "+varArray.length);
		
		if(varArray!=null){
			for(String str:varArray){
				CodigoError error = new CodigoError();
				try{
					System.out.println("variable "+str);
					if(str.contains("(")){
						int inicio = str.indexOf("(");
						int fin = str.lastIndexOf(")");
						if(inicio!= -1 && fin != -1){
							String varFuncion = str.substring(inicio, fin+1);
							str = str.replace(varFuncion, "");
							errores.addAll(validaFuncionesDeVariable(varFuncion));
							System.out.println("errores despues de validar funciones de variable "+errores.size());
	//						if(errores.size()>0)
	//							return errores;
						}
					}
					Variable var = varBo.findByName(str);
					System.out.println("variable a buscar "+str);
					if(var!=null)
						System.out.println("objeto var "+var.getDsnombre());
					if(var==null){
						error.setMensaje("No existe la variable "+str);
						errores.add(error);
						System.out.println("Agrega error");
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		if(frmArray!=null){
			for(String str:frmArray){
				CodigoError error = new CodigoError();
				try{
					System.out.println("variable frm: "+str);
					if(str.contains("(")){
						int inicio = str.indexOf("(");
						int fin = str.indexOf(")");
						if(inicio!= -1 && fin != -1){
							String varFuncion = str.substring(inicio, fin+1);
							System.out.println("str antes de replace: "+str);
							str = str.replace(varFuncion, "");
							System.out.println("str despues de replace: "+str);
							System.out.println("varfuncion substring: "+varFuncion);
							errores.addAll(validaFuncionesDeVariable(varFuncion));
							System.out.println("errores despues de validar funciones de frm "+errores.size());
	//						if(errores.size()>0)
	//							return errores;
						}
					}
					String nomVar = StringUtils.substringBetween(str, "[", "]");
					String nomForm = StringUtils.substringBeforeLast(str, "[");
					if(nomVar == null || nomForm == null){
						System.out.println("entro en error: nombre var "+nomVar);
						System.out.println("entro en error: nombre form "+nomForm);
						error.setMensaje("Falta el nombre de variable o formulario, o esta mal formada la sintaxis para el elemento: "+str);
						errores.add(error);
					}else{
						System.out.println("nombre de var frm: "+nomVar);
						System.out.println("nombre form frm: "+nomForm);
						Componente compFound = compBo.buscarPorNombreCortoConForm(nomForm, nomVar);
						if(compFound == null){
							System.out.println("no se encontro var de frm: "+nomVar);
							error.setMensaje("No se encontró la variable de form: "+str);
							errores.add(error);
						}else
						System.out.println("objeto frm "+compFound.getDsnombrevariable());
					}
				}catch(Exception e){
					e.printStackTrace();
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
	 * Esta madre valida las funciones dentro de variables var y frm
	 * 
	 * @param varFuncion
	 * @return
	 */
	public List<CodigoError> validaFuncionesDeVariable(String varFuncion){
		List<CodigoError> errores = new ArrayList<CodigoError>();
		int primerParentesis = varFuncion.indexOf("(");
		int ultimoParentesis = varFuncion.lastIndexOf(")");
		System.out.println("varfuncion antes "+varFuncion);
		varFuncion = varFuncion.substring(primerParentesis+1, ultimoParentesis);
		
		System.out.println("varfuncion despues "+varFuncion);
		String tipoFuncion = StringUtils.substringBefore(varFuncion, "=");
		String contenido = StringUtils.substringAfter(varFuncion, "=");
		System.out.println("tipo funcion "+tipoFuncion);
		System.out.println("contenido "+contenido);
		switch(tipoFuncion){
		case"tipo":
			if(contenido.trim().isEmpty()){
				System.out.println("error: contenido vacio");
				errores.add(new CodigoError(null, "No hay contenido para la funcion 'tipo'"));
			}else if(contenido.contains(",")) {
				String[] args = contenido.split(",");
				for(String param:args){
					System.out.println("parametro de tipo "+param);
					if(param.trim().isEmpty()){
						errores.add(new CodigoError(null,"Falta especificar un elemento para el tipo ya que existe una ','"));
					}
				}
			}else{
				System.out.println("el tipo es "+contenido);
			}
			break;
		case"contenido":
			if(contenido.trim().isEmpty()){
				System.out.println("error: contenido vacio");
				errores.add(new CodigoError(null, "No hay texto para la funcion 'contenido'"));
			}else if(contenido.contains("|") && !contenido.contains("::")){
				String[] args = contenido.split("\\|");
				if(args.length!=2){
					System.out.println("error: hay mas de 1 pipe");
					
				}else if(args[0].trim().isEmpty() && args[1].trim().isEmpty()){
					System.out.println("error: tiene que haber texto en bloque si o bloque no");
				}
			}else if(contenido.contains("|") && contenido.contains("::")){
				
				String[] args=contenido.split("\\|");
				for(String casoDeTexto:args){
					System.out.println("parametro de contenido: "+casoDeTexto);
					if(casoDeTexto.contains("::")){
						String[] map = casoDeTexto.split("::");
						if(map.length!=2){
							System.out.println("error: no esta equilibrado el caso de contenido");
							errores.add(new CodigoError(null, "No esta equilibrado el caso de contenido"));
						}
						if(map[0].trim().isEmpty()){
							System.out.println("error: debe haber nombre de condicion");
							errores.add(new CodigoError(null, "Debe haber nombre de condicion "));
						}
					}else{
						errores.add(new CodigoError(null, "Debe haber una especificacion de variable"));
					}
				}
			}else{
				System.out.println("parece que trae puro texto");
			}
			break;
		case"funcion":
			if(contenido.trim().isEmpty()){
				errores.add(new CodigoError(null, "No hay funciones declaradas para 'funcion'"));
			}else if(contenido.contains(",")){
				String[]args = contenido.split(",");
				for(String param:args){
					System.out.println("parametro de funcion "+param);
					if(param.trim().isEmpty()){
						errores.add(new CodigoError(null, "Falta especificar una función o sobra una ','"));
					}
				}
				
			}else if(contenido.trim().isEmpty()){
				errores.add(new CodigoError(null, "Falta especificar una función"));
			}else{
				System.out.println("trae una funcion "+contenido);
			}
			break;
		}

		
		return errores;
	}
	
	/**
	 * Valida que una plantilla que contenga ( ), { } y [ ] sea simétrica 
	 * 
	 * @param plantilla
	 * @return exito = true si es simétrica, false de otra manera
	 */
	@Override
	public List<CodigoError> isSimetrica(String plantilla){
//		ExpresionEnvio respuesta = new ExpresionEnvio();
		Stack<Character> stack = new Stack<Character>();
		List<CodigoError> errores = new ArrayList<CodigoError>(); 
		int index=0;
		for(Character c:plantilla.toCharArray()){
			if(stack.size()>0){
				if(stack.get(stack.size()-1).equals('\'')){
					if(c == '\''){
						stack.remove(stack.size()-1);
						index++;
						continue;
					}else{
						index++;
						continue;
					}
				}else if(stack.get(stack.size()-1).equals('\"')){
					if(c == '\"'){
						stack.remove(stack.size()-1);
						index++;
						continue;
					}else{
						index++;
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
				Character nextChar = plantilla.charAt(index+1);
				if(!nextChar.equals(']') && !nextChar.equals('}')
						&& !nextChar.equals('.')){
//					es un parentesis de inciso
					break;
				}else{
					cerrarElemento(stack,')',errores,index);
					break;
				}
			case ']':
				cerrarElemento(stack, ']',errores,index);
				break;
			case '}':
				cerrarElemento(stack, '}',errores,index);
				break;
			case '\'':
					stack.add(c);
				break;
			case '\"':
					stack.add(c);
				break;
			}
			index++;
			System.out.println("indice "+index);
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
	
	public void cerrarElemento(Stack<Character> stack, Character currentElement,List<CodigoError> errores,int index){
		CodigoError error = new CodigoError();
		if(stack.size()<1){
//			if(currentElement==')'){
//				permitirCierre=true;
//			}else{
				error.setMensaje("No existe apertura para el elemento: "+currentElement);
				errores.add(error);
				return;
//			}
		}
		switch(stack.get(stack.size()-1)){
		case '(':
			if(currentElement == ')'){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: ) pero se encontró: "+currentElement+" en el indice "+index);
				errores.add(error);
			}
			break;
		case '[':
			if(currentElement == ']'){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: ] pero se encontró: "+currentElement+" en el indice "+index);
				errores.add(error);
			}
			break;
		case '{':
			if(currentElement == '}'){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: } pero se encontró: "+currentElement+" en el indice "+index);
				errores.add(error);
			}
			break;
		case '\'':
			if(currentElement == '\''){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: \' pero se encontró: "+currentElement+" en el indice "+index);
				errores.add(error);
			}
			break;
		case '\"':
			if(currentElement == '\"'){
//				stack.remove(stack.get(stack.size()-1));
				stack.pop();
			}else{
				error.setMensaje("Error, se esperaba: \" pero se encontró: "+currentElement+" en el indice "+index);
				errores.add(error);
			break;
			}
		}
	}

	@Override
	public PlantillaDocumentoNotarial getPublishBySubOperacionId(String id,
			ElementoCatalogo locacion) throws NotariaException {
		return plantillaDocumentoNotarialDao.getPublishBySubOperacionId(id, locacion);
	}

	@Override
	public PlantillaDocumentoNotarial obtienePublicadaPorSuboperacionLocacion(ElementoCatalogo localidad, List<Suboperacion> suboperaciones)throws NotariaException{
		
		try{
			List<PlantillaDocumentoNotarialSubOperacion> subOperaciones = new ArrayList<PlantillaDocumentoNotarialSubOperacion>();
			for (Suboperacion suboperacion: suboperaciones){
				PlantillaDocumentoNotarialSubOperacion subOperacion = new PlantillaDocumentoNotarialSubOperacion();
				subOperacion.setSuboperacion(suboperacion);
				subOperaciones.add(subOperacion);
			}
			PlantillaDocumentoNotarial pdn = plantillaDocumentoNotarialDao.buscarPorSuboperacionLocacion(subOperaciones, localidad);
			if(pdn!=null){
				return pdn;
			} else {
				throw new NotariaException("No se ha logrado obtener la plantilla publicada para la localidad y suboperación requerida.");
			}
		}catch(NotariaException e){
			throw new NotariaException("Exception: No se ha logrado obtener la plantilla publicada para la localidad y suboperación requerida.", e);
		}
	}
	
	@Override
	public boolean delete(PlantillaDocumentoNotarial plantilla) {
		try{
			plantilla = plantillaDocumentoNotarialDao.findById(plantilla.getId());
			plantillaDocumentoNotarialDao.delete(plantilla);
			return true;
		}catch(NotariaException e){
			System.out.printf("=====> No se logro eliminar el valor de plantilla: %s", plantilla.getId().getIddocnot());
			return false;
		}
	}
	
	@Override
	public PlantillaDocumentoNotarial save(PlantillaDocumentoNotarial plantilla) throws NotariaException {
		//verifica si existe ya una plantilla publicada para esta suboperacion y localidad		
		PlantillaDocumentoNotarial plantillaExiste = plantillaDocumentoNotarialDao.buscarPorSuboperacionLocacion(plantilla.getListaPlantillaDocumentoNotarialSubOperacion(), plantilla.getLocacion());
		if(plantillaExiste!=null){
			// en caso de que sí, se cancela la operaciòn de persistencia y se envia el mensaje
			throw new NotariaException("La plantilla ha sído asociada a esta suboperacion y locacion, imposible volver a asignar.");
		} else {
			
			// en caso de que no, se inserta el registro
			PlantillaDocumentoNotarialPK key = new PlantillaDocumentoNotarialPK();
			key.setIddocnot(plantilla.getId()!=null&&plantilla.getId().getIddocnot()!=null?plantilla.getId().getIddocnot():GeneradorId.generaId(plantilla));
			key.setInversion(plantilla.getId()!=null&&plantilla.getId().getInversion()!=null?plantilla.getId().getInversion():0);
			plantilla.setId(key);
			
			List<PlantillaDocumentoNotarialSubOperacion> subOperaciones = plantilla.getListaPlantillaDocumentoNotarialSubOperacion();
			if(subOperaciones.size()>0){
				for(PlantillaDocumentoNotarialSubOperacion suboperacion:subOperaciones){
					suboperacion.setPlantillaDocumentoNotarialSubOperacion(GeneradorId.generaId(suboperacion));
					suboperacion.setPlantillaDocumentoNotarial(plantilla);
					suboperacion.setSesion(plantilla.getIdsesion());
				}
			}
			plantilla = plantillaDocumentoNotarialDao.save(plantilla);
		}

		return plantilla;
	}
	
	@Override
	public PlantillaDocumentoNotarial update(PlantillaDocumentoNotarial plantilla) throws NotariaException {
		
		PlantillaDocumentoNotarial docOld = buscarPorIdCompleto(plantilla.getId().getIddocnot(), plantilla.getId().getInversion());
		Integer maxVersion = findMaxVersion(plantilla.getId().getIddocnot());
		if(docOld.getId().getInversion() < maxVersion){
			throw new NotariaException("Se debe de actualizar a una versión más reciente.");
		}
		if(plantilla.getIspublicado()){ //se publicará una nueva versión
			// buscar si existe un doc publicado y actualizarlo a false
			if(plantilla.getListaPlantillaDocumentoNotarialSubOperacion()!=null && plantilla.getListaPlantillaDocumentoNotarialSubOperacion().size()>0){
				plantilla.getId().setInversion(maxVersion+1);
				PlantillaDocumentoNotarial docPublicado = findDocumentoPublicado(plantilla.getId().getIddocnot());
				if(docPublicado != null){
					docPublicado.setIspublicado(false);
					plantillaDocumentoNotarialDao.update(docPublicado);
				}			
				save(plantilla);				
			} else {
				// TODO verificar listado
				throw new NotariaException("Se debe de tener al menos una suboperación relacionada a la plantilla, de otra forma no es posible guardarla.");
			}
				
		}else{
//			si no esta publicada la version anterior solamente se actualizan los cambios en la misma version
//			si el doc recuperado esta publicado se aumenta la version a uno
			if(docOld.getIspublicado())
				plantilla.getId().setInversion(maxVersion+1);
			plantilla.setListaPlantillaDocumentoNotarialSubOperacion(null);
			plantillaDocumentoNotarialDao.update(plantilla);
		}
		
		return plantilla;
	}
	
}
