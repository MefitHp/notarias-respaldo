package com.palestra.notarias.variables;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.ComparecienteConyugeBo;
import com.palestra.notaria.bo.ComparecienteHijoBo;
import com.palestra.notaria.bo.ComparecienteRepresentanteBo;
import com.palestra.notaria.bo.impl.ComparecienteBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteConyugeBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteHijoBoImpl;
import com.palestra.notaria.bo.impl.ComparecienteRepresentanteBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notarias.utils.VariableUtils;

public class VariableValueAssignerComparecientes {
	
	public final static String TIPO_PERSONA_MORAL = "MORAL";
	public final static String TIPO_PERSONA_FISICA = "FÍSICA";
	public final static String TIPO_COMPARECIENTE_REPRESENTANTE = "REPRESENTANTE";
	public final static String TIPO_COMPARECIENTE_TESTADOR = "TESTADOR";

	private Compareciente compareciente;
	private List<Compareciente> comparecientes;
	
	
	public VariableValueAssignerComparecientes(){}
	
	public VariableValueAssignerComparecientes(List<Compareciente>comparecientes) {
		setComparecientes(comparecientes);
	}
	
	public String calculaAutorizante(){
		StringBuilder valorRetorno = new StringBuilder("");

		try {
			valorRetorno.append(calculaTratamientoComparecientes(comparecientes, "nombreCompleto|calculaAutorizante"));
		} catch (NotariaException e) {
			e.printStackTrace();
		}
		return valorRetorno.toString();
	}
	
	
	
	public String calculaRepresentante(){
		StringBuilder valorRetorno = new StringBuilder("");

		try {
			valorRetorno.append(calculaTratamientoComparecientes(comparecientes, "nombreCompleto|representante(insertar=inicio; representada por )"));
		} catch (NotariaException e) {
			e.printStackTrace();
		}
		return valorRetorno.toString();
	}
	
	public String calculaAutorizanteRepresentante(){
		StringBuilder valorRetorno = new StringBuilder("");

		try {
			valorRetorno.append(calculaTratamientoComparecientes(comparecientes, "nombreCompleto|calculaAutorizante|representante(insertar=inicio; representada por )"));
		} catch (NotariaException e) {
			e.printStackTrace();
		}
		return valorRetorno.toString();
	}

	
	
	public String tratamientoNombreCompleto(){
			
			StringBuilder valorRetorno = new StringBuilder("");
						
			try {
				valorRetorno.append(calculaTratamientoComparecientes(comparecientes, ""));
			} catch (NotariaException e) {
				e.printStackTrace();
			}
			
			return valorRetorno.toString();
		}	
	
	public List<String> reflect() throws NotariaException {
		Method[] metodos = this.getClass().getMethods();
		List<String> listado = new ArrayList<String>();
		for(Method metodo:metodos){
			if(!(metodo.getName().startsWith("get")||metodo.getName().startsWith("set")) 
					&& !metodo.getName().equals("reflect")
					&& !metodo.getName().equals("wait")
					&& !metodo.getName().equals("equals")
					&& !metodo.getName().equals("toString")
					&& !metodo.getName().equals("hashCode")
					&& !metodo.getName().equals("notify")
					&& !metodo.getName().equals("notifyAll")){
				listado.add(metodo.getName());
			}			
		}
		return listado;		
	}
	
	
	
	
	private Map<String,List<Compareciente>> splitComparecientesTipo(List<Compareciente> comparecientes){
		ArrayList<Compareciente> senores = new ArrayList<Compareciente>();
		ArrayList<Compareciente> senoras = new ArrayList<Compareciente>();
		ArrayList<Compareciente> senoritas = new ArrayList<Compareciente>();
		ArrayList<Compareciente> morales = new ArrayList<Compareciente>();
		HashMap<String, List<Compareciente>> splitMap = new HashMap<String, List<Compareciente>>();
		
		for(Compareciente c: comparecientes){
			if(c.getTratamiento()!=null){
				switch (c.getTratamiento().getDscodigo()) {
				case "ELSE":
					senores.add(c);
					break;
				case "LASE":
					senoras.add(c);
					break;
				case "LITA":
					senoritas.add(c);
					break;
				default:
					break;
				}	
			}else{
				morales.add(c);
			}
					
		}
		
		splitMap.put("senores", senores);
		splitMap.put("senoras", senoras);
		splitMap.put("senoritas", senoritas);		
		splitMap.put("morales", morales);		
		return splitMap;
		
	}
	
	private StringBuilder setGeneroPlural(Map filtro, String variable) throws NotariaException{
		StringBuilder valorVariable = new StringBuilder();
		List<Compareciente> senores = (List<Compareciente>) filtro.get("senores");
		List<Compareciente> senoras = (List<Compareciente>) filtro.get("senoras");
		List<Compareciente> senoritas = (List<Compareciente>) filtro.get("senoritas");
		List<Compareciente> morales = (List<Compareciente>) filtro.get("morales");
		VariableValueAssigner vva = new VariableValueAssigner();
		
		if(senores.size()>0){
			if(senores.size()>1)
				valorVariable.append("los señores ");
			else{
				valorVariable.append(senores.get(0).getTratamiento().getDselemento());
				valorVariable.append(" ");
			}
			
			valorVariable.append(vva.calculaLetraComaCompareciente(senores, variable));

		}
		
		if(senoras.size()>0){
			if(senores.size()>0){
				if(senoritas.size()>0)
					valorVariable.append(", ");
				else
					valorVariable.append(" y ");
			}
			if(senoras.size()>1)
				valorVariable.append("las señoras ");
			else{
				valorVariable.append(senoras.get(0).getTratamiento().getDselemento());
				valorVariable.append(" ");
			}
			valorVariable.append(vva.calculaLetraComaCompareciente(senoras, variable));

		}
		if(senoritas.size()>0){
			if(senores.size()>0 || senoras.size()>0){
				if(morales.size()>0)
					valorVariable.append(", ");
				else
					valorVariable.append(" y ");
			}
			if(senoritas.size()>1)
				valorVariable.append("las señoritas ");
			else{
				valorVariable.append(senoritas.get(0).getTratamiento().getDselemento());
				valorVariable.append(" ");
			}
			valorVariable.append(vva.calculaLetraComaCompareciente(senoritas, variable));
		}
		if(morales.size()>0){
			if(senores.size()>0 || senoras.size()>0 || senoritas.size()>0){
				valorVariable.append(" y ");
			}
			valorVariable.append(vva.calculaLetraComaCompareciente(morales, variable));
		}
		return valorVariable;
	}
	
	public String calculaTratamientoComparecientes(List<Compareciente> comparecientes, String variable) throws NotariaException{
		ArrayList<String> norepeatList = new ArrayList<String>();
		String[] tmpString = variable.split("\\|");
		
		
		for (String s:tmpString){
			if (s.contains("(norepeat)")){
				norepeatList.add(s.replace("(norepeat)", ""));
				variable = variable.replace("|"+s, "");
			}
		}
		
		
		Map filtro = splitComparecientesTipo(comparecientes);
		StringBuilder valorVariable = setGeneroPlural(filtro, variable);
		
		
		if(norepeatList.size()>0 && comparecientes.size()>0){
			for(String datos:norepeatList){
				valorVariable.append(datos);
			}
		}
		
		return valorVariable.toString();
	}
	
	
	/*
	 * MANEJO DE PALABRAS CON PLURALES
	 * 
	 */
	
	public String pluralVenta(){
		return calculaPlural("vende", "n");
	}
	
	public String pluralAdquisicion(){
		StringBuilder valorRetorno = new StringBuilder();
		valorRetorno.append(calculaPlural("quien", "es"));
		valorRetorno.append(" ");
		valorRetorno.append(calculaPlural("adquiere","n"));
		return valorRetorno.toString();
	}
	
	/*
	 *@Param palabra: La palabra a usar como base para el plural
	 *@Param finletter: Letra que se agrega al convertir en plural
	 *@return: Regresa un String 
	 * */
	private String calculaPlural(String palabra,String finletter){
		
		StringBuilder valorRetorno = new StringBuilder(" "+palabra);
		
		if(comparecientes.size()>1){
			valorRetorno.append(finletter);
		}
		
		return valorRetorno.toString();
	}

	public List<Compareciente> getComparecientes() {
		return comparecientes;
	}

	public void setComparecientes(List<Compareciente> comparecientes) {
		this.comparecientes = comparecientes;
	}
	
}
