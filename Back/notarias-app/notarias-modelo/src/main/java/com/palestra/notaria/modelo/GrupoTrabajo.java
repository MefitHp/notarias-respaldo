package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the tbcfgm11 database table.
 * 
 */
@Entity
@Table(name="tbcfgm12")
public class GrupoTrabajo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idgrupotrabajo;
	
	private String dsnombre;
	private String dsdescripcion;
	
	@ManyToOne
	@JoinColumn(name="idresponsable")
	private Usuario responsable;
	
	@Column(columnDefinition="INT")
	private Boolean bstatus;
		
	private String codigo;
	private String idsesion;
	private Timestamp tmstmp;

	

    public GrupoTrabajo() {
    }



	public String getIdgrupotrabajo() {
		return idgrupotrabajo;
	}



	public void setIdgrupotrabajo(String idgrupotrabajo) {
		this.idgrupotrabajo = idgrupotrabajo;
	}



	public String getDsnombre() {
		return dsnombre;
	}



	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}



	public String getDsdescripcion() {
		return dsdescripcion;
	}



	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}



	public Usuario getResponsable() {
		return responsable;
	}



	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}



	public Boolean getBstatus() {
		return bstatus;
	}



	public void setBstatus(Boolean bstatus) {
		this.bstatus = bstatus;
	}



	public String getCodigo() {
		return codigo;
	}



	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}



	public String getIdsesion() {
		return idsesion;
	}



	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}



	public Timestamp getTmstmp() {
		return tmstmp;
	}



	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	
}