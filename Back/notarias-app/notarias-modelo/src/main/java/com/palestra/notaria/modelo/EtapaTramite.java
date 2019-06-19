package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm41 database table.
 * 
 */
@Entity
@Table(name="tbbsnm41")
public class EtapaTramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idetatra;

	private String dsdescripcion;

	private String dsnombre;

	private String idsesion;

	private Integer inorden;

	private Timestamp tmstmp;

//	//bi-directional many-to-one association to Tramite
//	@OneToMany(mappedBy="tbbsnm41")
//	private List<Tramite> tbbsnm40s;

    public EtapaTramite() {
    }

	public String getIdetatra() {
		return this.idetatra;
	}

	public void setIdetatra(String idetatra) {
		this.idetatra = idetatra;
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

	public Integer getInorden() {
		return this.inorden;
	}

	public void setInorden(Integer inorden) {
		this.inorden = inorden;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

//	public List<Tramite> getTbbsnm40s() {
//		return this.tbbsnm40s;
//	}
//
//	public void setTbbsnm40s(List<Tramite> tbbsnm40s) {
//		this.tbbsnm40s = tbbsnm40s;
//	}
	
}