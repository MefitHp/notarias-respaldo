package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the tbbsnm44 database table.
 * 
 */
@Entity
@Table(name="tbbsnm44")
public class RegistroRi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idregri;

	private String dsnombre;

	private String dsruta;

	@Temporal(TemporalType.DATE)
	private Date fechaadjuntado;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idexpedidopor")
	private ElementoCatalogo expedidopor;

	private String idsesion;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idtipo")
	private ElementoCatalogo tipo;

	private String numclave;
	
	@Column(columnDefinition="INT")
	private Boolean isvalidadonotario;

	private Timestamp tmstmp;

	public RegistroRi() {
	}

	public String getIdregri() {
		return this.idregri;
	}

	public void setIdregri(String idregri) {
		this.idregri = idregri;
	}

	public String getDsnombre() {
		return this.dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public String getDsruta() {
		return this.dsruta;
	}

	public void setDsruta(String dsruta) {
		this.dsruta = dsruta;
	}

	public Date getFechaadjuntado() {
		return this.fechaadjuntado;
	}

	public void setFechaadjuntado(Date fechaadjuntado) {
		this.fechaadjuntado = fechaadjuntado;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public String getNumclave() {
		return this.numclave;
	}

	public void setNumclave(String numclave) {
		this.numclave = numclave;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public ElementoCatalogo getExpedidopor() {
		return expedidopor;
	}

	public void setExpedidopor(ElementoCatalogo expedidopor) {
		this.expedidopor = expedidopor;
	}

	public ElementoCatalogo getTipo() {
		return tipo;
	}

	public void setTipo(ElementoCatalogo tipo) {
		this.tipo = tipo;
	}
	
	public Boolean getIsvalidadonotario() {
		return isvalidadonotario;
	}
	
	public void setIsvalidadonotario(Boolean isvalidadonotario) {
		this.isvalidadonotario = isvalidadonotario;
	}

}