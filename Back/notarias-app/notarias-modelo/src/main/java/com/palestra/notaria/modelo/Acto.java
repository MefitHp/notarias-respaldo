package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm18 database table.
 * 
 */
@Entity
@Table(name="tbbsnm18")
public class Acto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idacto;

	private String dsdescripcion;

	private String dsnombre;
	
	private Integer numacto;
	
	@Column(columnDefinition="INT")
	private Boolean isactivo;
	
	@Column(columnDefinition="INT")
	private Boolean hasdim;
	
	@Column(columnDefinition="INT")
	private Boolean hasanexo5;
	
	@Column(columnDefinition="INT")
	private Boolean hasProceso;
	
	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Suboperacion
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idsuboperacion")
	private Suboperacion suboperacion;

	//bi-directional many-to-one association to Expediente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idexpediente")
	private Expediente expediente;

    public Acto() {
    }
    
    public Acto(String idacto){
    	this.idacto = idacto;
    }
    
    public Boolean getHasProceso() {
		return hasProceso;
	}
    public void setHasProceso(Boolean hasProceso) {
		this.hasProceso = hasProceso;
	}
    public Boolean getIsactivo() {
		return isactivo;
	}
    public void setIsactivo(Boolean isactivo) {
		this.isactivo = isactivo;
	}

	public String getIdacto() {
		return this.idacto;
	}

	public void setIdacto(String idacto) {
		this.idacto = idacto;
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
	
	public Suboperacion getSuboperacion() {
		return suboperacion;
	}

	public void setSuboperacion(Suboperacion suboperacion) {
		this.suboperacion = suboperacion;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;

	}

	public Integer getNumacto() {
		return numacto;
	}

	public void setNumacto(Integer numacto) {
		this.numacto = numacto;
	}

	public Boolean getHasdim() {
		return hasdim;
	}

	public void setHasdim(Boolean hasdim) {
		this.hasdim = hasdim;
	}

	public Boolean getHasanexo5() {
		return hasanexo5;
	}

	public void setHasanexo5(Boolean hasanexo5) {
		this.hasanexo5 = hasanexo5;
	}
	
	
}