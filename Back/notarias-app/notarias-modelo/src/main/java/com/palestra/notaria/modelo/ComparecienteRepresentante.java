package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm39 database table.
 * 
 */
@Entity
@Table(name="tbbsnm39")
public class ComparecienteRepresentante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idcomrep;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Compareciente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idrepresentante")
	private Compareciente representante;

	//bi-directional many-to-one association to Compareciente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idrepresentado")
	private Compareciente representado;

    public ComparecienteRepresentante() {
    }

	public String getIdcomrep() {
		return this.idcomrep;
	}

	public void setIdcomrep(String idcomrep) {
		this.idcomrep = idcomrep;
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

	public Compareciente getRepresentante() {
		return representante;
	}
	
	public void setRepresentante(Compareciente representante) {
		this.representante = representante;
	}
	
	public Compareciente getRepresentado() {
		return representado;
	}
	
	public void setRepresentado(Compareciente representado) {
		this.representado = representado;
	}
	
}