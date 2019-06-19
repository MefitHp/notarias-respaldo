package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tbcfgm11 database table.
 * 
 */
@Entity
@Table(name="tbcfgm11")
public class VariableGrupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idgpovars;

	private Integer dsorden;

	//bi-directional many-to-one association to Variable
	@ManyToOne
	@JoinColumn(name="idvariable")
	private Variable variable;

	//bi-directional many-to-one association to Grupo
	@ManyToOne
	@JoinColumn(name="idgrupo")
	private Grupo grupo;

    public VariableGrupo() {
    }

	public String getIdgpovars() {
		return this.idgpovars;
	}

	public void setIdgpovars(String idgpovars) {
		this.idgpovars = idgpovars;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Integer getDsorden() {
		return dsorden;
	}
	
	public void setDsorden(Integer dsorden) {
		this.dsorden = dsorden;
	}
}