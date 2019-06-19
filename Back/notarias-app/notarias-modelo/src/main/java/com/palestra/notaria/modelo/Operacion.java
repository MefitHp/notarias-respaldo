package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm27 database table.
 * 
 */
@Entity
@Table(name="tbbsnm27")
public class Operacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idoperacion;

	private String dsdescripcion;

	private String dsnombre;

	private String idsesion;

	private Timestamp tmstmp;

//	//bi-directional many-to-one association to Suboperacion
//	@OneToMany(mappedBy="tbbsnm27")
//	private List<Suboperacion> tbbsnm17s;

    public Operacion() {
    }

	public String getIdoperacion() {
		return this.idoperacion;
	}

	public void setIdoperacion(String idoperacion) {
		this.idoperacion = idoperacion;
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

//	public List<Suboperacion> getTbbsnm17s() {
//		return this.tbbsnm17s;
//	}
//
//	public void setTbbsnm17s(List<Suboperacion> tbbsnm17s) {
//		this.tbbsnm17s = tbbsnm17s;
//	}
	
}