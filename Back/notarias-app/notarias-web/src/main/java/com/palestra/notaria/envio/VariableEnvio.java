package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.DatoVariableFormulario;
import com.palestra.notaria.modelo.UbicacionVarEstatica;
import com.palestra.notaria.modelo.Validacion;
import com.palestra.notaria.modelo.Variable;

public class VariableEnvio extends GenericEnvio {
	
	Variable variable=null;
	
	ArrayList<Validacion> validacionesList=null;
	
	List<Variable> variableList=null;
	
	private List<Variable> variableCompareciente;
	
	ArrayList<DatoVariableFormulario> varFormDinamicos=null;
	
	List<DatoVariableFormulario> componentesSubformulario=new ArrayList<DatoVariableFormulario>();
	
	private List<String> campos = new ArrayList<>();

	private UbicacionVarEstatica calculo;
	
	public List<DatoVariableFormulario> getComponentesSubformulario() {
		return componentesSubformulario;
	}
	public void setComponentesSubformulario(
			List<DatoVariableFormulario> componentesSubformulario) {
		this.componentesSubformulario = componentesSubformulario;
	}
	public ArrayList<Validacion> getValidacionesList() {
		return validacionesList;
	}
	
	public void setValidacionesList(ArrayList<Validacion> validacionesList) {
		this.validacionesList = validacionesList;
	}
	
	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public List<Variable> getVariableList() {
		return variableList;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}
	public ArrayList<DatoVariableFormulario> getVarFormDinamicos() {
		return varFormDinamicos;
	}
	public void setVarFormDinamicos(
			ArrayList<DatoVariableFormulario> varFormDinamicos) {
		this.varFormDinamicos = varFormDinamicos;
	}

	public UbicacionVarEstatica getCalculo() {
		return calculo;
	}

	public void setCalculo(UbicacionVarEstatica calculo) {
		this.calculo = calculo;
	}

	public List<String> getCampos() {
		return campos;
	}

	public void setCampos(List<String> campos) {
		this.campos = campos;
	}
	public List<Variable> getVariableCompareciente() {
		return variableCompareciente;
	}
	public void setVariableCompareciente(List<Variable> variableCompareciente) {
		this.variableCompareciente = variableCompareciente;
	}
}