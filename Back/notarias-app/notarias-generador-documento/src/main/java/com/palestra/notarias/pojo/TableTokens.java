package com.palestra.notarias.pojo;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Define los tokens para las tablas. Que tienen la forma:
 * 
 * $(tbl:nombreCortoConSubForm[definicionTextoRow])
 * 
 * @author sofia
 * 
 */
public class TableTokens extends VariablesTokens {

	/** id de confsubformulario **/
	String idConSubFormulario;

	/** nombre corto del subformulario **/
	String nombreCortoSubForm;

	/** Texto de la fila(registro) de la tabla **/
	String definicionTextoRow;

	/** Registros del subformulario(tabla) **/
	Integer numRegistros;

	/** Valores de los registros de la tabla **/
	List<RegisterTableValues> registerValues;
	
	// 100914 --> cafaray: integracion de los texto dentro de una variable de subformulario
	private List<String> textos;
	// 100914 --> cafaray: integracion de las variables y referencias dentro del subformulario
	private List<String> variables;

	/**
	 * Definicion de la tabla, aqui sabemos cuales son las columnas(componentes)
	 * de la tabla
	 **/
	List<FormTokens> columnDefinicions;

	public String getIdConSubFormulario() {
		return idConSubFormulario;
	}

	public void setIdConSubFormulario(String idConSubFormulario) {
		this.idConSubFormulario = idConSubFormulario;
	}

	public String getNombreCortoSubForm() {
		return nombreCortoSubForm;
	}

	public void setNombreCortoSubForm(String nombreCortoSubForm) {
		this.nombreCortoSubForm = nombreCortoSubForm;
	}

	public String getDefinicionTextoRow() {
		return definicionTextoRow;
	}

	public void setDefinicionTextoRow(String definicionTextoRow) {
		this.definicionTextoRow = definicionTextoRow;
	}

	public Integer getNumRegistros() {
		return numRegistros;
	}

	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}

	public List<RegisterTableValues> getRegisterValues() {
		return registerValues;
	}
	
	public void setRegisterValues(List<RegisterTableValues> registerValues) {
		this.registerValues = registerValues;
	}
	
	public List<FormTokens> getColumnDefinicions() {
		return columnDefinicions;
	}

	public void setColumnDefinicions(List<FormTokens> columnDefinicions) {
		this.columnDefinicions = columnDefinicions;
	}

	public List<String> getTextos() {
		return textos;
	}

	public void setTextos(List<String> textos) {
		this.textos = textos;
	}

	public List<String> getVariables() {
		return variables;
	}

	public void setVariables(List<String> variables) {
		this.variables = variables;
	}

	@Override
	public String toString() {
		return new ToStringBuilder("{")
				.append("originalName: ", this.getOriginalName())
				.append("name: ", this.getName())
				.append("value: ", this.getValue())
				.append("type: ", this.getType())
				.append("longitud: ", this.getLongitud())
				.append("idConSubFormulario: ", this.getIdConSubFormulario())
				.append("nombreCortoSubForm: ", this.getNombreCortoSubForm())
				.append("definicionTextoRow: ", this.getDefinicionTextoRow())
				.append("textos: ", this.getTextos().size())
				.append("variables: ", this.getVariables().size())
				.append("}")
				.toString();
	}

}
