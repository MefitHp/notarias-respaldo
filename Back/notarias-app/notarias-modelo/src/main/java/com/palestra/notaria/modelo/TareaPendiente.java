package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm02 database table.
 * 
 */
@Entity
@Table(name="tbbsnm02")
public class TareaPendiente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idtareapend;

	private String dsdescripcion;

	private String idprioridad;

	private String idsesion;

	private Integer inprioritaria;
	
	private Integer ismanual;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Tramite22
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idtramite")
	private Tramite tramite;
	
	//bi-directional many-to-one association to Tramite22
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idexpediente")
	private Expediente expediente;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuarioasigna")
	private Usuario usuarioasigna;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuariorecibe")
	private Usuario usuariorecibe;

    public TareaPendiente() {
    }

	public String getIdtareapend() {
		return this.idtareapend;
	}

	public void setIdtareapend(String idtareapend) {
		this.idtareapend = idtareapend;
	}

	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getIdprioridad() {
		return this.idprioridad;
	}

	public void setIdprioridad(String idprioridad) {
		this.idprioridad = idprioridad;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Integer getInprioritaria() {
		return this.inprioritaria;
	}

	public void setInprioritaria(Integer inprioritaria) {
		this.inprioritaria = inprioritaria;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	
	public Tramite getTramite() {
		return tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public Usuario getUsuarioasigna() {
		return usuarioasigna;
	}

	public void setUsuarioasigna(Usuario usuarioasigna) {
		this.usuarioasigna = usuarioasigna;
	}

	public Usuario getUsuariorecibe() {
		return usuariorecibe;
	}

	public void setUsuariorecibe(Usuario usuariorecibe) {
		this.usuariorecibe = usuariorecibe;
	}

	public Integer getIsmanual() {
		return ismanual;
	}

	public void setIsmanual(Integer ismanual) {
		this.ismanual = ismanual;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	
}