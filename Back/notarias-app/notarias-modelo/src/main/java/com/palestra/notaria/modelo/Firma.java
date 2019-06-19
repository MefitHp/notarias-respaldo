package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;



/**
 * The persistent class for the tbbsnm26 database table.
 * 
 */
@Entity
@Table(name="tbbsnm73")
@NamedQueries({
	@NamedQuery(name="Firma.findByCompareciente", query="SELECT f FROM Firma f WHERE f.compareciente.idcompareciente = :identificador")
})
public class Firma implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idfirma;
	
	@OneToOne
	@JoinColumn(name="idcompareciente")
	private Compareciente compareciente;
	
	private Timestamp tmstmp;
	
	private String idsesion;

    public Firma() {
    }

    public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}
	
	public String getIdfirma() {
		return idfirma;
	}

	public void setIdfirma(String idfirma) {
		this.idfirma = idfirma;
	}

	public Compareciente getCompareciente() {
		return compareciente;
	}

	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}

	

	
	
}