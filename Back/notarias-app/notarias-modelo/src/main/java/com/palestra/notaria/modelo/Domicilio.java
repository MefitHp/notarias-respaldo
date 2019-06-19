package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * The persistent class for the tbbsnm12 database table.
 * 
 */
@Entity
@Table(name="tbbsnm12")
@XmlRootElement(name="Domicilio")
public class Domicilio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iddomicilio;

	private String dscalle;

	private String dsciudad;

	private String dscodpos;

	private String dscolonia;

	private String dsestado;

	private String dslocalidad;

	private String dslote;

	private String dsmanzana;

	private String dsmunicipio;

	private String dsnumext;

	private String dsnumint;

	@Column(length = 65535,columnDefinition="text")
	private String dsdircompleta;

	private String idsesion;
	
	@Column(length = 65535,columnDefinition="text")
	private String dsreferencia;

	@Column(columnDefinition="INT")
	private Boolean isasistido;

	@XmlTransient
	private Timestamp tmstmp;

    public Domicilio() {
    }

    @XmlElement(name="iddomicilio")
	public String getIddomicilio() {
		return this.iddomicilio;
	}

	public void setIddomicilio(String iddomicilio) {
		this.iddomicilio = iddomicilio;
	}

	@XmlElement(name="dscalle")
	public String getDscalle() {
		return this.dscalle;
	}

	public void setDscalle(String dscalle) {
		this.dscalle = dscalle;
	}

	@XmlElement(name="dsciudad")
	public String getDsciudad() {
		return this.dsciudad;
	}

	public void setDsciudad(String dsciudad) {
		this.dsciudad = dsciudad;
	}

	@XmlElement(name="dscodpos")
	public String getDscodpos() {
		return this.dscodpos;
	}

	public void setDscodpos(String dscodpos) {
		this.dscodpos = dscodpos;
	}

	@XmlElement(name="dscolonia")
	public String getDscolonia() {
		return this.dscolonia;
	}

	public void setDscolonia(String dscolonia) {
		this.dscolonia = dscolonia;
	}

	@XmlElement(name="dsestado")
	public String getDsestado() {
		return this.dsestado;
	}

	public void setDsestado(String dsestado) {
		this.dsestado = dsestado;
	}

	@XmlElement(name="dslocalidad")
	public String getDslocalidad() {
		return this.dslocalidad;
	}

	public void setDslocalidad(String dslocalidad) {
		this.dslocalidad = dslocalidad;
	}

	@XmlElement(name="dslote")
	public String getDslote() {
		return this.dslote;
	}

	public void setDslote(String dslote) {
		this.dslote = dslote;
	}

	@XmlElement(name="dsmanzana")
	public String getDsmanzana() {
		return this.dsmanzana;
	}

	public void setDsmanzana(String dsmanzana) {
		this.dsmanzana = dsmanzana;
	}

	@XmlElement(name="dsmunicipio")
	public String getDsmunicipio() {
		return this.dsmunicipio;
	}

	public void setDsmunicipio(String dsmunicipio) {
		this.dsmunicipio = dsmunicipio;
	}

	@XmlElement(name="dsnumext")
	public String getDsnumext() {
		return this.dsnumext;
	}

	public void setDsnumext(String dsnumext) {
		this.dsnumext = dsnumext;
	}

	@XmlElement(name="dsnumint")
	public String getDsnumint() {
		return this.dsnumint;
	}

	public void setDsnumint(String dsnumint) {
		this.dsnumint = dsnumint;
	}

	@XmlElement(name="dsdircompleta")
	public String getDsdircompleta() {
		return dsdircompleta;
	}

	public void setDsdircompleta(String dsdircompleta) {
		this.dsdircompleta = dsdircompleta;
	}

	@XmlElement(name="idsesion")
	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	@XmlElement(name="isasistido")
	public Boolean getIsasistido() {
		return isasistido;
	}
	
	public void setIsasistido(Boolean isasistido) {
		this.isasistido = isasistido;
	}

	
	@XmlElement(name="tmstmp")
	@XmlJavaTypeAdapter(value=TimestampAdapter.class)
	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	
	@XmlElement(name="dsreferencia")
	public String getDsreferencia() {
		return dsreferencia;
	}
	
	public void setDsreferencia(String dsreferencia) {
		this.dsreferencia = dsreferencia;
	}

}