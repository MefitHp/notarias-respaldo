package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the tbbsnm20 database table.
 * 
 */
@Entity
@Table(name="tbbsnm20")
public class BitacoraExpediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idbitacoraexpediente;

	private String cdacto;

    @Temporal( TemporalType.DATE)
	private Date fechaesperada;

    @Temporal( TemporalType.DATE)
	private Date fechafinal;

    @Temporal( TemporalType.DATE)
	private Date fechainicial;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Expediente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idexpediente")
	private Expediente tbbsnm32;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuarioavanza")
	private Usuario tbcfgm03;

    public BitacoraExpediente() {
    }

	public String getIdbitacoraexpediente() {
		return this.idbitacoraexpediente;
	}

	public void setIdbitacoraexpediente(String idbitacoraexpediente) {
		this.idbitacoraexpediente = idbitacoraexpediente;
	}

	public String getCdacto() {
		return this.cdacto;
	}

	public void setCdacto(String cdacto) {
		this.cdacto = cdacto;
	}

	public Date getFechaesperada() {
		return this.fechaesperada;
	}

	public void setFechaesperada(Date fechaesperada) {
		this.fechaesperada = fechaesperada;
	}

	public Date getFechafinal() {
		return this.fechafinal;
	}

	public void setFechafinal(Date fechafinal) {
		this.fechafinal = fechafinal;
	}

	public Date getFechainicial() {
		return this.fechainicial;
	}

	public void setFechainicial(Date fechainicial) {
		this.fechainicial = fechainicial;
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
	
	public Usuario getTbcfgm03() {
		return this.tbcfgm03;
	}

	public void setTbcfgm03(Usuario tbcfgm03) {
		this.tbcfgm03 = tbcfgm03;
	}
	
}