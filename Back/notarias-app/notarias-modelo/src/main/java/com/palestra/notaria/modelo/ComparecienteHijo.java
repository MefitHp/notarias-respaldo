package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm39 database table.
 * 
 */
@Entity
@Table(name="tbbsnm39a")
public class ComparecienteHijo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idcomhijo;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Compareciente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idcompareciente")
	private Compareciente compareciente;

	//bi-directional many-to-one association to Compareciente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idhijo")
	private Compareciente hijo;

	public String getIdcomhijo() {
		return idcomhijo;
	}

	public void setIdcomhijo(String idcomhijo) {
		this.idcomhijo = idcomhijo;
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

	public Compareciente getCompareciente() {
		return compareciente;
	}

	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}

	public Compareciente getHijo() {
		return hijo;
	}

	public void setHijo(Compareciente hijo) {
		this.hijo = hijo;
	}

    
	
}