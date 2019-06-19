package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm03 database table.
 * 
 */
@Entity
@Table(name="tbbsnm03")
public class BitacoraFirma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idbitfirma;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Expediente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idescritura")
	private Escritura escritura;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuariofirma")
	private Usuario usuariofirma;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idcompareciente")
	private Compareciente compareciente;

	
    public BitacoraFirma() {
    }

	public String getIdbitfirma() {
		return this.idbitfirma;
	}

	public void setIdbitfirma(String idbitfirma) {
		this.idbitfirma = idbitfirma;
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
	
	public Usuario getUsuariofirma() {
		return usuariofirma;
	}
	
	public void setUsuariofirma(Usuario usuariofirma) {
		this.usuariofirma = usuariofirma;
	}
	
	public Compareciente getCompareciente() {
		return compareciente;
	}
	
	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}
}