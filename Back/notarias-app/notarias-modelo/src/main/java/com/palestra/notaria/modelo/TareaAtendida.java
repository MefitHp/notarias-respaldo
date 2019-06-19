package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm06 database table.
 * 
 */
@Entity
@Table(name="tbbsnm06")
public class TareaAtendida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idtareaaten;

	private String dscomentario;

	private String dsdescripcion;

	private String dsnombre;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Expediente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idexpediente")
	private Expediente tbbsnm32;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuarioasigna")
	private Usuario tbcfgm031;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuarioatiende")
	private Usuario tbcfgm032;

    public TareaAtendida() {
    }

	public String getIdtareaaten() {
		return this.idtareaaten;
	}

	public void setIdtareaaten(String idtareaaten) {
		this.idtareaaten = idtareaaten;
	}

	public String getDscomentario() {
		return this.dscomentario;
	}

	public void setDscomentario(String dscomentario) {
		this.dscomentario = dscomentario;
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

	public Expediente getTbbsnm32() {
		return this.tbbsnm32;
	}

	public void setTbbsnm32(Expediente tbbsnm32) {
		this.tbbsnm32 = tbbsnm32;
	}
	
	public Usuario getTbcfgm031() {
		return this.tbcfgm031;
	}

	public void setTbcfgm031(Usuario tbcfgm031) {
		this.tbcfgm031 = tbcfgm031;
	}
	
	public Usuario getTbcfgm032() {
		return this.tbcfgm032;
	}

	public void setTbcfgm032(Usuario tbcfgm032) {
		this.tbcfgm032 = tbcfgm032;
	}
	
}