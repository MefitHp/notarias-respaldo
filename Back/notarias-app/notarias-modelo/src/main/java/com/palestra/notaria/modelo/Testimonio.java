package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm30 database table.
 * 
 */
@Entity
@Table(name="tbbsnm30")
public class Testimonio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idtestimonio;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuarioelaboro")
	private Usuario usuarioelaboro;

	private String dscodigobarras;

	private String dsrutacaratula;

	private String dsrutaescritura;
	
	@Column(columnDefinition="INT")
	private Boolean isgenerado;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Escritura
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idescritura")
	private Escritura escritura;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idnotario")
	private Usuario notario;

    public Testimonio() {
    }

	public String getIdtestimonio() {
		return this.idtestimonio;
	}

	public void setIdtestimonio(String idtestimonio) {
		this.idtestimonio = idtestimonio;
	}

	public String getDscodigobarras() {
		return this.dscodigobarras;
	}

	public void setDscodigobarras(String dscodigobarras) {
		this.dscodigobarras = dscodigobarras;
	}

	public String getDsrutacaratula() {
		return this.dsrutacaratula;
	}

	public void setDsrutacaratula(String dsrutacaratula) {
		this.dsrutacaratula = dsrutacaratula;
	}

	public String getDsrutaescritura() {
		return this.dsrutaescritura;
	}

	public void setDsrutaescritura(String dsrutaescritura) {
		this.dsrutaescritura = dsrutaescritura;
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

	public Escritura getEscritura() {
		return escritura;
	}

	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}

	public Usuario getUsuarioelaboro() {
		return usuarioelaboro;
	}

	public void setUsuarioelaboro(Usuario usuarioelaboro) {
		this.usuarioelaboro = usuarioelaboro;
	}

	public Usuario getNotario() {
		return notario;
	}

	public void setNotario(Usuario notario) {
		this.notario = notario;
	}

	public Boolean getIsgenerado() {
		return isgenerado;
	}

	public void setIsgenerado(Boolean isgenerado) {
		this.isgenerado = isgenerado;
	}

}