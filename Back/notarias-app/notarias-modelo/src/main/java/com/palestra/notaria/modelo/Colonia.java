package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm15 database table.
 * 
 */
@Entity
@Table(name="tbbsnm15")
public class Colonia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idcolonia;

	private String dsnombre;

	private String idsesion;

	private Timestamp tmstmp;

    public Colonia() {
    }

	public String getIdcolonia() {
		return this.idcolonia;
	}

	public void setIdcolonia(String idcolonia) {
		this.idcolonia = idcolonia;
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