package com.palestra.notaria.modelo;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Variable;

@Entity
@Table(name="tbcfgm32")
public class VariablesTipo implements Serializable {

	private static final long serialVersionUID = 9222613662252889247L;

	@Id
	private String idvarstipo;
	
	@OneToOne
	@JoinColumn(name="idvariable")
	private Variable variable;
	
	@OneToOne
	@JoinColumn(name="idcomponente")
	private Componente componente;

	public String getIdvarstipo() {
		return idvarstipo;
	}

	public void setIdvarstipo(String idvarstipo) {
		this.idvarstipo = idvarstipo;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}
	
}
