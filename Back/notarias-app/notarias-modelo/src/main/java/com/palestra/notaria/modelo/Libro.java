package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tbbsnm26 database table.
 * 
 */
@Entity
@Table(name="tbbsnm26")
public class Libro implements Serializable {
	private static final long serialVersionUID = 1L;

	public Libro() {
    }
	
	@Id
	private String idlibro;

	private String dsdescripcion;

    @Temporal( TemporalType.DATE)
	private Date fecha;

	private String idsesion;

	private Long infolioinicial;
	
	private Long innumlibro;

	private Timestamp tmstmp;

    

	public String getIdlibro() {
		return this.idlibro;
	}

	public void setIdlibro(String idlibro) {
		this.idlibro = idlibro;
	}

	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}
	
	public Long getInfolioinicial() {
		return infolioinicial;
	}
	public void setInfolioinicial(Long infolioinicial) {
		this.infolioinicial = infolioinicial;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	public Long getInnumlibro() {
		return innumlibro;
	}
	public void setInnumlibro(Long innumlibro) {
		this.innumlibro = innumlibro;
	}
	
}