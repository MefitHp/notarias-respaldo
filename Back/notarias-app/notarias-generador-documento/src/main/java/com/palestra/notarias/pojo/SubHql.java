package com.palestra.notarias.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Clase que guarda los parametros necesarios
 * para armar filtros para obtener valores
 * de variables completas. 
 * Estos subhql estan definidios en querys_variables.xml
 * 
 * @author sofia
 *
 */
public class SubHql {
	
	/** identificador del subuqery. Se obtiene de la definicion de la variable,
	 * Ejemplo: ${compareciente(tipo=BENEFICIARIO)} , idsubhql->tipo **/
	private String idsubhql;
	
	/**
	 * Valor con que se filtrara en bd. Se obtiene de la definicion de la variable,
	 * Ejemplo: ${compareciente(tipo=BENEFICIARION)}, valor -> BENEFICIARION
	 */
	private String valor;
	
	/**
	 * Query en hql, que se arma con los valores de los camps idsubhql y valor.
	 */
	private String sbhql;
	
	public String getIdsubhql() {
		return idsubhql;
	}
	
	public void setIdsubhql(String idsubhql) {
		this.idsubhql = idsubhql;
	}
	
	public String getSbhql() {
		return sbhql;
	}
	
	public void setSbhql(String sbhql) {
		this.sbhql = sbhql;
	}
	
	public String getValor() {
		return valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("idsubhql", this.getIdsubhql())
				.append("sbhql", this.getSbhql())
				.append("valor", this.getValor())
				.toString();
	}

}
