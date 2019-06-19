package com.palestra.notaria.bo.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import com.palestra.notaria.bo.FuncionBo;
import com.palestra.notaria.dao.FuncionDao;
import com.palestra.notaria.dao.impl.FuncionDaoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Funcion;
import com.palestra.notaria.modelo.FuncionParametro;
import com.palestra.notaria.util.FuncionPojo;

public class FuncionBoImpl extends GenericBoImpl<Funcion> implements FuncionBo {
	FuncionDao daoFuncion;
	public FuncionBoImpl(){
		daoFuncion = new FuncionDaoImpl();
		super.dao = this.daoFuncion;
		System.out.println("Se ha asignado el DAO al Generic correctamente.");
	}
	
	@Override
	public List<Funcion> listarTodas() throws NotariaException {
		//FuncionDao dao = new FuncionDaoImpl();
		return daoFuncion.listaFunciones();
	}

	@Override
	public List<FuncionParametro> listarParametros(Funcion funcion)
			throws NotariaException {
		//FuncionDao dao = new FuncionDaoImpl();
		return daoFuncion.listaParametros(funcion);
	}

	@Override
	public Integer actualizarParametros(Funcion funcion)
			throws NotariaException {
		//FuncionDao dao = new FuncionDaoImpl();
		return daoFuncion.actualizaParametros(funcion);
	}

	@Override
	public void eliminaParametros(Funcion funcion) throws NotariaException {
		//FuncionDao dao = new FuncionDaoImpl();
		daoFuncion.eliminaParametros(funcion);
	}
	
	@Override
	public void eliminarFuncion(Funcion funcion) throws NotariaException{
		System.out.println("=====> Se eliminan los parametros de la unidad de persistencia " + funcion.getIdentificador() + "[" +funcion.getDetalleList().size()+"]");
		eliminaParametros(funcion);
		System.out.println("=====> Se elimina el objeto de la unidad de persistencia " + funcion.getIdentificador());
		this.delete(funcion);
		System.out.println("=====> Se elimino la funcion de la unidad de persistencia");
	}

	@Override
	public Funcion obtenerFuncion(String identificador) throws NotariaException {
		//FuncionDao dao = new FuncionDaoImpl();
		return daoFuncion.findById(identificador);
	}

	@Override
	public Funcion obtenerPorNombre(String nombreFuncion)
			throws NotariaException {
		//FuncionDao dao = new FuncionDaoImpl();
		return daoFuncion.findByNombre(nombreFuncion);
	}

	/**
	 * Este método analiza la expresión y agrega a FuncionPojo las funciones que va encontrando
	 */
	@Override
	public String analizaExpresion(String expresion,List<FuncionPojo> funciones){
		char[] cadena = expresion.toCharArray();		
		Stack<String> pila = new Stack<>();
		int indexFuncion=0;
		for(int x = 0;x<cadena.length;x++){
			if(cadena[x]=='('){
				pila.push(String.valueOf(cadena[x]));
			}else if(cadena[x]==')'){ //encontro el primer cierre de funcion, por lo que encontraremos la funcion y la registraremos:
				pila.pop();
				String funcion = "";
				if(x+1>=cadena.length){
					funcion = expresion.substring(indexFuncion);
				}else{
					funcion = expresion.substring(indexFuncion,x+1);
				}
				funciones.add(new FuncionPojo(funcion));
				expresion = expresion.replace(funcion, "funcion-"+funciones.size());
				System.out.println("expresion actualizada "+expresion);
				System.out.println("funcion que se agrega a lista de funciones "+funcion);
				return expresion;
			}else if(cadena[x]==':' && cadena[x-1]=='f'){ //se trata de una funcion, habrá que marcar el inicio
				indexFuncion = x-1;
			}
		}
		return "";
	}
	
	/**
	 * Este método analiza una función y sus parámetros
	 */
	@Override
	public FuncionPojo analizaFuncion(FuncionPojo funcion) throws NotariaException{
		String laFuncion = funcion.getNombre();
		//extrae el nombre:
		System.out.println("funcion completa "+funcion.getNombre());
		funcion.setNombre(laFuncion.substring(laFuncion.indexOf("f:")+2, laFuncion.indexOf("(")));
				
		laFuncion = laFuncion.substring(laFuncion.indexOf("("),laFuncion.length());
		System.out.println("contenido de funcion "+laFuncion);
		int inicia = 1;
		int index = 0;
		Stack<String> comillas = new Stack<>();
		boolean inicioComilla = false;
		char[] cfuncion = laFuncion.toCharArray();
		for(char c:cfuncion){
//			System.out.println("char "+c);
			if((c==',' && !inicioComilla) || c==')'){
//				System.out.println("entra al if , AND inicioComilla OR )");
				String param = laFuncion.substring(inicia, index).trim();
//				HAY QUE PASAR LA VALIDACION DE OPERADORES DESPUES DE LA VALIDACION DE SI EXISTE LA FUNCION


				System.out.println("agrega parametro \""+param+"\"");
				funcion.agregaParametro(param);
				inicia = index+1;
			}else if(c=='\"' && !inicioComilla){
				inicioComilla = true;
				comillas.push("\"");
			} else if(c=='\"' && inicioComilla){
				inicioComilla = false;
				comillas.pop();
			}
			index++;
		}
		Funcion funcionFound = obtenerPorNombre(funcion.getNombre());
		if(funcionFound!=null){
//			se valida que el numero de parametros concuerde
			if(funcion.getParametros().size()!=funcionFound.getDetalleList().size()){
				funcion.getErrores().add(new CodigoError("","el numero de parametros de la funcion "+funcionFound.getNombre()+" no corresponde"));
				System.out.println("el numero de parametros de la funcion "+funcionFound.getNombre()+" no corresponde");
				return funcion;
			}
//			VALIDAR PARAM REQ
		//	se iteran los parametros de la funcion para validar los requeridos
			for(int i=0;i<funcionFound.getDetalleList().size();i++){
//				@omarete ENTRE OTRAS PENDEJADAS LE PUSE UN TRIM AL currentParam
				String currentParam = funcion.getParametros().get(i).getParam().trim();
				FuncionParametro paramFuncionFound = funcionFound.getDetalleList().get(i);
				System.out.println("valor de current param \""+currentParam+"\"");
				System.out.println("funcion found param requerido? "+paramFuncionFound.getIsrequerido());
				System.out.println("param introducido "+currentParam.trim().isEmpty());
				if(paramFuncionFound.getIsrequerido() && currentParam.trim().isEmpty()){
					System.out.println("error: el param "+paramFuncionFound.getParametro()+" es requerido en la ubicacion "+(i+1)+
							" de la funcion "+funcionFound.getNombre());
				}
//				-------------------------------------------------------------------------
				else{
					if(paramFuncionFound.getParametro().equals(Constantes.COMPONENTE_STRING)){
						String cmp = StringUtils.substringBetween(currentParam, "${cmp:", "}");
						if(cmp==null){
							System.out.println("error se esperaba componente, no un \""+cmp+"\"");
							funcion.getErrores().add(new CodigoError("","Se esperaba un componente en lugar de \'"+cmp+"\'"));
						}
					}else if(paramFuncionFound.getParametro().equals(Constantes.CIERTOFALSO_STRING)){
						if(!currentParam.equals("true") && !currentParam.equals("false")){
							System.out.println("error se esperaba true o false, no un \""+currentParam+"\"");
							funcion.getErrores().add(new CodigoError("","Se esperaba un true o false en lugar de \'"+currentParam+"\'"));
						}
					}else if(paramFuncionFound.getParametro().equals(Constantes.VALOR_STRING)){
						if(!currentParam.matches("^[a-zA-Z0-9\'\"\\s_]*$")){
							System.out.println("error se encontraron otros caracteres, no \""+currentParam+"\"");
							funcion.getErrores().add(new CodigoError("","Se esperaban caracteres alfanuméricos en lugar de \'"+currentParam+"\'"));
						}
					}else if(paramFuncionFound.getParametro().equals(Constantes.FUNCION_STRING)){
						if(!paramFuncionFound.getIsrequerido() && currentParam.equals("")){
							System.out.println("debe haber funcion pero es opcional");
						}else if(!currentParam.contains("funcion")){
							System.out.println("no es funcion \""+currentParam+"\"");
							funcion.getErrores().add(new CodigoError("","Se esperaba una función en lugar de \'"+currentParam+"\'"));
						}
					}else if(paramFuncionFound.getParametro().equals(Constantes.CONDICION_STRING)){
//						VALIDACION DE OPERADORES
						String []operadores = {"+","-","/","*",">","<",">=","<=","=="};
						for(String oper:operadores){
							if(currentParam.contains(oper)){
//								System.out.println("p contains "+str);
								String [] elements = currentParam.split(oper);
								if(elements.length<2){
									funcion.getErrores().add(new CodigoError("","operador con sintaxis incorrecta en parámetro: "+currentParam));
								}else{
									for(String var:elements){
										var = var.trim();
										String tipo="";
										System.out.println("var \""+var+"\"");
										if(var.trim().isEmpty()){
											funcion.getErrores().add(new CodigoError("","operador con sintaxis incorrecta en parámetro: "+currentParam));
											System.out.println("error: operador con sintaxis incorrecta");
										}
//										SE COMENTAN VALIDACIONES DE PARAMETROS DE FUNCIONES
										else{
											String varTemp = StringUtils.substringBetween(var, "${cmp:", "}");
											
											if(varTemp!=null){
//												TODO: obtener tipo del comp y asignar a tipo para comparar
											}else if(var.equals("true") || var.equals("false")){
												if(tipo.equals("") || tipo.equals("boolean")){
													tipo = "boolean";
												}else{
													System.out.println("error se esperaba "+tipo);
													funcion.getErrores().add(new CodigoError("","Se esperaba una variable de tipo: "+tipo));
												}
											}else if(var.matches("^[a-zA-Z0-9\'\"\\s_]*$")){
												if(tipo.equals("")|| tipo.equals("alfanum")){
													tipo = "alfanum";
												}else{
													System.out.println("error se esperaba "+tipo);
													funcion.getErrores().add(new CodigoError("","Se esperaba una variable de tipo: "+tipo));
												}
											}else{
												SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
												try {
													fmt.parse(var);
													tipo = "fecha";
												} catch (ParseException e) {
													if(!tipo.equals("")){
														System.out.println("error se esperaba "+tipo);
														funcion.getErrores().add(new CodigoError("","Se esperaba una variable de tipo: "+tipo));
													}
													if(tipo.equals(""))
														System.out.println("tampoco es fecha, que chingados es!!");
													e.printStackTrace();
													funcion.getErrores().add(new CodigoError("","error al tratar de parsear a fecha el string "+var));
												}
											}
										}
									}
								}
							}
						}
					}				
				}
//				-------------------------------------------------------------------------------------
			}
//			for(FuncionPojo.Parametro param:funcion.getParametros())	
				System.out.println("nombre de funcion "+funcionFound.getNombre());
				System.out.println("forma de funcion "+funcionFound.getForma());
				for(FuncionParametro param:funcionFound.getDetalleList()){
					System.out.println("parametro de funcion "+param.getParametro());
				}

		}else{
			funcion.getErrores().add(new CodigoError("","no existe la función: "+funcion.getNombre()));
			System.out.println("no existe la funcion "+funcion.getNombre());
		}
		return funcion;
	}
}
