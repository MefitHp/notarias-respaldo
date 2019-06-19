package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm13 database table.
 * 
 */
@Entity
@Table(name="tbbsnm13")
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idestado;

	private String dsnombre;

	private String idsesion;

	private Timestamp tmstmp;

    public Estado() {
    }

	public String getIdestado() {
		return this.idestado;
	}

	public void setIdestado(String idestado) {
		this.idestado = idestado;
	}

	public String getDsnombre() {
		return this.dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
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

}