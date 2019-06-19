package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm38 database table.
 * 
 */
@Entity
@Table(name="tbbsnm38")
public class EtapaTestimonio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idetates;

	private String dsdescripcion;

	private String dsnombre;

	private String idsesion;

	private Integer inorden;

	private Timestamp tmstmp;

//	//bi-directional many-to-one association to Testimonio
//	@OneToMany(mappedBy="tbbsnm38")
//	private List<Testimonio> tbbsnm30s;

    public EtapaTestimonio() {
    }

	public String getIdetates() {
		return this.idetates;
	}

	public void setIdetates(String idetates) {
		this.idetates = idetates;
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

//	public List<Testimonio> getTbbsnm30s() {
//		return this.tbbsnm30s;
//	}
//
//	public void setTbbsnm30s(List<Testimonio> tbbsnm30s) {
//		this.tbbsnm30s = tbbsnm30s;
//	}
	
}