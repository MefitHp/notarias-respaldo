package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm11 database table.
 * 
 */
@Entity
@Table(name="tbbsnm11")
public class Localidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idlocalidad;

	private String dsnombre;

	private String idsesion;

	private Timestamp tmstmp;

    public Localidad() {
    }

	public String getIdlocalidad() {
		return this.idlocalidad;
	}

	public void setIdlocalidad(String idlocalidad) {
		this.idlocalidad = idlocalidad;
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