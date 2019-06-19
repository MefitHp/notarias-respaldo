package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="tbbsnm71")
@Entity
public class ControlFolios  implements Serializable {

	private static final long serialVersionUID = 4915735554471922017L;

	
	@Id
	@Column(name="idcontrolfolios")
	private String idcontrolfolios;
	
	@Temporal(TemporalType.DATE)
	private Date actualizacion;
	
	
	@Column(name="foliosdisponibles")
	private Long foliosDisponibles;
	
	@Column(name="folioactual")
	private Long folioActual;

	
	private String idsesion;

	private Timestamp tmstmp;
	

	public Long getFoliosDisponibles() {
		return foliosDisponibles;
	}

	public void setFoliosDisponibles(Long foliosDisponibles) {
		this.foliosDisponibles = foliosDisponibles;
	}

	public Long getFolioActual() {
		return folioActual;
	}

	public void setFolioActual(Long folioActual) {
		this.folioActual = folioActual;
	}

	public String getIdcontrolfolios() {
		return idcontrolfolios;
	}

	public void setIdcontrolfolios(String idcontrolfolios) {
		this.idcontrolfolios = idcontrolfolios;
	}

	public Date getActualizacion() {
		return actualizacion;
	}

	public void setActualizacion(Date actualizacion) {
		this.actualizacion = actualizacion;
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

