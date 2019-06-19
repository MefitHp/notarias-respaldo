package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbcfgm16 database table.
 * 
 */
@Entity
@Table(name="tbcfgm16")
@NamedQueries(value={
	@NamedQuery(name="UbicacionVarEstatica.findByVariable", query="SELECT ve FROM UbicacionVarEstatica ve WHERE ve.variable.idvariable = :idvariable"),
	@NamedQuery(name="UbicacionVarEstatica.findByNombre", query = "SELECT ve FROM UbicacionVarEstatica ve WHERE ve.variable.dsnombre = :dsnombre")
})
public class UbicacionVarEstatica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idvarest;

	private String dsentidad;

	private String dsfiltro;

	private String dspropiedad;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Variable
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idvariable")
	private Variable variable;

    public UbicacionVarEstatica() {
    }

	public String getIdvarest() {
		return this.idvarest;
	}

	public void setIdvarest(String idvarest) {
		this.idvarest = idvarest;
	}

	public String getDsentidad() {
		return this.dsentidad;
	}

	public void setDsentidad(String dsentidad) {
		this.dsentidad = dsentidad;
	}

	public String getDsfiltro() {
		return this.dsfiltro;
	}

	public void setDsfiltro(String dsfiltro) {
		this.dsfiltro = dsfiltro;
	}

	public String getDspropiedad() {
		return this.dspropiedad;
	}

	public void setDspropiedad(String dspropiedad) {
		this.dspropiedad = dspropiedad;
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
	
}