package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm65")
public class Valuador implements Serializable {

	private static final long serialVersionUID = -4843261167965268627L;
	
	@Id
	private String idvaluador;
	
	private String dsnombre;
	
	private String dspaterno;
	
	private String dsmaterno;
	
	private String dsempresa;
	
	private String dsrfc;
	
	private String dscurp;
	
	private String dscorreo;
	
	private String dstelefono;
	
	private String dsmovil;
	
	private Double inprecio;
	
	@Column(columnDefinition="INT")
	private Boolean inestatus;
	
	private String idsesion;
	
	private Timestamp tmstmp;

	public String getIdvaluador() {
		return idvaluador;
	}

	public void setIdvaluador(String idvaluador) {
		this.idvaluador = idvaluador;
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

	public String getDsempresa() {
		return dsempresa;
	}

	public void setDsempresa(String dsempresa) {
		this.dsempresa = dsempresa;
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

	public String getDscorreo() {
		return dscorreo;
	}

	public void setDscorreo(String dscorreo) {
		this.dscorreo = dscorreo;
	}

	public String getDstelefono() {
		return dstelefono;
	}

	public void setDstelefono(String dstelefono) {
		this.dstelefono = dstelefono;
	}

	public String getDsmovil() {
		return dsmovil;
	}

	public void setDsmovil(String dsmovil) {
		this.dsmovil = dsmovil;
	}

	public Double getInprecio() {
		return inprecio;
	}

	public void setInprecio(Double inprecio) {
		this.inprecio = inprecio;
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
