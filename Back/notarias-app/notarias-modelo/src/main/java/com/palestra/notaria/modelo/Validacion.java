package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbcfgm09 database table.
 * 
 */
@Entity
@Table(name="tbcfgm09")
public class Validacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idrestriccion;

	private String dsexpresion;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Variable
	@ManyToOne
	@JoinColumn(name="idvariable")
	private Variable variable;

	//bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne
	@JoinColumn(name="idoperador")
	private ElementoCatalogo operador;

    public Validacion() {
    }

	public String getIdrestriccion() {
		return this.idrestriccion;
	}

	public void setIdrestriccion(String idrestriccion) {
		this.idrestriccion = idrestriccion;
	}

	public String getDsexpresion() {
		return this.dsexpresion;
	}

	public void setDsexpresion(String dsexpresion) {
		this.dsexpresion = dsexpresion;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Variable getVariable() {
		return variable;
	}
	
	public void setVariable(Variable variable) {
		this.variable = variable;
	}
	
	public ElementoCatalogo getOperador() {
		return operador;
	}
	
	public void setOperador(ElementoCatalogo operador) {
		this.operador = operador;
	}
	
}