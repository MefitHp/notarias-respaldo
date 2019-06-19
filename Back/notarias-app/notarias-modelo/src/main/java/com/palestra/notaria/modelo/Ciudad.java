package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm34 database table.
 * 
 */
@Entity
@Table(name="tbbsnm34")
public class Ciudad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idciudad;

	private String dsnombre;

	private String idsesion;

	private Timestamp tmstmp;

    public Ciudad() {
    }

	public String getIdciudad() {
		return this.idciudad;
	}

	public void setIdciudad(String idciudad) {
		this.idciudad = idciudad;
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