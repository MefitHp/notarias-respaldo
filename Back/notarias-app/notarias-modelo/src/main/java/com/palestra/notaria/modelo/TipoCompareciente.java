package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm31 database table.
 * 
 */
@Entity
@Table(name="tbbsnm31")
public class TipoCompareciente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idtipocompareciente;

	private String dsdescripcion;

	private String dsnombre;

	private String idsesion;

	private Timestamp tmstmp;

    public TipoCompareciente() {
    }
    
    public TipoCompareciente(String id){
    	this.idtipocompareciente = id;
    }

	public String getIdtipocompareciente() {
		return this.idtipocompareciente;
	}

	public void setIdtipocompareciente(String idtipocompareciente) {
		this.idtipocompareciente = idtipocompareciente;
	}

	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
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