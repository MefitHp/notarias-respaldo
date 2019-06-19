package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm14 database table.
 * 
 */
@Entity
@Table(name="tbbsnm14")
public class Municipio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idmunicipio;

	private String dsnombre;

	private String idsesion;

	private Timestamp tmstmp;

    public Municipio() {
    }

	public String getIdmunicipio() {
		return this.idmunicipio;
	}

	public void setIdmunicipio(String idmunicipio) {
		this.idmunicipio = idmunicipio;
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