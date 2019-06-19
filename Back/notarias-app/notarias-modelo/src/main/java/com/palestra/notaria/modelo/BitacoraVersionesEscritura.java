package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm07 database table.
 * 
 */
@Entity
@Table(name="tbbsnm07")
public class BitacoraVersionesEscritura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idbitversesc;

	private String dsnombre;

	private String dsruta;

	private String dsversion;

	private String idnodoalfresco;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Escritura
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idescritura")
	private Escritura escritura;

    public BitacoraVersionesEscritura() {
    }

	public String getIdbitversesc() {
		return this.idbitversesc;
	}

	public void setIdbitversesc(String idbitversesc) {
		this.idbitversesc = idbitversesc;
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

	public String getDsversion() {
		return this.dsversion;
	}

	public void setDsversion(String dsversion) {
		this.dsversion = dsversion;
	}

	public String getIdnodoalfresco() {
		return this.idnodoalfresco;
	}

	public void setIdnodoalfresco(String idnodoalfresco) {
		this.idnodoalfresco = idnodoalfresco;
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
	
}