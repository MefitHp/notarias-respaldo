package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm63")
public class Gestor implements Serializable {

	private static final long serialVersionUID = 8982166734163548366L;
	
	@Id
	private String idgestor;
	
	private String dsnombre;
	
	private String dspaterno;
	
	private String dsmaterno; 
	
	private String dstelefono;
	
	private String dscorreo;
	
	private String dsrfc;
	
	private String dscurp; 
	
	private String dsempresa;
	
	private Double inprecio;
	
	@ManyToOne
	@JoinColumn(name="idlocacion")
	private ElementoCatalogo locacion;
	
	@Column(columnDefinition="INT")
	private Boolean inestatus;
	
	private String idsesion;
	
	private Timestamp tmstmp;

	public String getIdgestor() {
		return idgestor;
	}

	public void setIdgestor(String idgestor) {
		this.idgestor = idgestor;
	}

	public String getDsnombre() {
		return dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public String getDspaterno() {
		return dspaterno;
	}

	public void setDspaterno(String dspaterno) {
		this.dspaterno = dspaterno;
	}

	public String getDsmaterno() {
		return dsmaterno;
	}

	public void setDsmaterno(String dsmaterno) {
		this.dsmaterno = dsmaterno;
	}

	public String getDstelefono() {
		return dstelefono;
	}

	public void setDstelefono(String dstelefono) {
		this.dstelefono = dstelefono;
	}

	public String getDscorreo() {
		return dscorreo;
	}

	public void setDscorreo(String dscorreo) {
		this.dscorreo = dscorreo;
	}

	public String getDsrfc() {
		return dsrfc;
	}

	public void setDsrfc(String dsrfc) {
		this.dsrfc = dsrfc;
	}

	public String getDscurp() {
		return dscurp;
	}

	public void setDscurp(String dscurp) {
		this.dscurp = dscurp;
	}

	public String getDsempresa() {
		return dsempresa;
	}

	public void setDsempresa(String dsempresa) {
		this.dsempresa = dsempresa;
	}

	public Double getInprecio() {
		return inprecio;
	}

	public void setInprecio(Double inprecio) {
		this.inprecio = inprecio;
	}

	public ElementoCatalogo getLocacion() {
		return locacion;
	}
	
	public void setLocacion(ElementoCatalogo locacion) {
		this.locacion = locacion;
	}
	
	public Boolean getInestatus() {
		return inestatus;
	}

	public void setInestatus(Boolean inestatus) {
		this.inestatus = inestatus;
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
