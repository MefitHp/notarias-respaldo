package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm45 database table.
 */

@Entity
@Table(name="tbbsnm45")
public class RelEtapaTestimonio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idreletapatesti;

	private Timestamp fechaaprobada;

	private String idsesion;

	@Column(columnDefinition="INT")
	private Boolean isaprobada;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Testimonio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idtestimonio")
	private Testimonio testimonio;

	//bi-directional many-to-one association to EtapaTestimonio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idetapatestimonio")
	private EtapaTestimonio etapatestimonio;

	public RelEtapaTestimonio() {
	}

	public String getIdreletapatesti() {
		return this.idreletapatesti;
	}

	public void setIdreletapatesti(String idreletapatesti) {
		this.idreletapatesti = idreletapatesti;
	}

	public Timestamp getFechaaprobada() {
		return this.fechaaprobada;
	}

	public void setFechaaprobada(Timestamp fechaaprobada) {
		this.fechaaprobada = fechaaprobada;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Boolean getIsaprobada() {
		return isaprobada;
	}

	public void setIsaprobada(Boolean isaprobada) {
		this.isaprobada = isaprobada;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Testimonio getTestimonio() {
		return testimonio;
	}

	public void setTestimonio(Testimonio testimonio) {
		this.testimonio = testimonio;
	}

	public EtapaTestimonio getEtapatestimonio() {
		return etapatestimonio;
	}

	public void setEtapatestimonio(EtapaTestimonio etapatestimonio) {
		this.etapatestimonio = etapatestimonio;
	}

}