package com.palestra.notarias.pojo;

import java.util.List;

/**
 * Contiene los textos a reemplazar en la plantilla y la lista de componentes
 * con valor que componen el registro del indice(i) de la tabla.
 * 
 * @author sofia
 * 
 */
public class RegisterTableValues {

	/** Cadena de texto ya con valores de variables reemplazados de la fila **/
	String valueRegisterText;

	/** Indice de registro **/
	Integer indexRegister;

	/**
	 * Lista de variables que son componentes de subformularios, aqui se guardar
	 * nombre-valor del registro i
	 **/
	List<FormTokens> listaVarSubComponente;

	public List<FormTokens> getListaVarSubComponente() {
		return listaVarSubComponente;
	}

	public void setListaVarSubComponente(List<FormTokens> listaVarSubComponente) {
		this.listaVarSubComponente = listaVarSubComponente;
	}

	public String getValueRegisterText() {
		return valueRegisterText;
	}

	public void setValueRegisterText(String valueRegisterText) {
		this.valueRegisterText = valueRegisterText;
	}

	public Integer getIndexRegister() {
		return indexRegister;
	}

	public void setIndexRegister(Integer indexRegister) {
		this.indexRegister = indexRegister;
	}
}
