package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm66")
public class ComparecienteAutorizante implements Serializable {

	private static final long serialVersionUID = -4366094161245923499L;

	@EmbeddedId
	private ComparecienteAutorizantePK id;
	
	@ManyToOne
	@JoinColumn(name="idcompareciente",insertable=false,updatable=false)
	private Compareciente compareciente;
	
	@ManyToOne
	@JoinColumn(name="idautorizante",referencedColumnName="idcompareciente",insertable=false,updatable=false)
	private Compareciente autorizante;
	
	private String idsesion;
	
	private Timestamp tmstmp;

	public ComparecienteAutorizantePK getId() {
		return id;
	}

	public void setId(ComparecienteAutorizantePK id) {
		this.id = id;
	}
	
	public Compareciente getAutorizante() {
		return autorizante;
	}
	public void setAutorizante(Compareciente autorizante) {
		this.autorizante = autorizante;
	}
	
	public Compareciente getCompareciente() {
		return compareciente;
	}
	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
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
