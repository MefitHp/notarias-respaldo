package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tbbsnm05 database table.
 * 
 */
@Entity
@Table(name="tbbsnm05")
public class DocumentoEscaneado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iddocescaneado;

	private String dsruta;

	private String dsdescripcion;
	
	//bi-directional many-to-one association to Expediente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idexpediente")
	private Expediente expediente;
	
	@Temporal( TemporalType.DATE)
	private Date fecha;

	private String idsesion;

	private Timestamp tmstmp;


    public DocumentoEscaneado() {
    }

	public String getIddocescaneado() {
		return this.iddocescaneado;
	}

	public void setIddocescaneado(String iddocescaneado) {
		this.iddocescaneado = iddocescaneado;
	}

	public String getDsruta() {
		return this.dsruta;
	}

	public void setDsruta(String dsruta) {
		this.dsruta = dsruta;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
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

	public String getDsdescripcion() {
		return dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
}