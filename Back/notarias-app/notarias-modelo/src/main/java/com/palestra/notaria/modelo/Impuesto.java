package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm43")
public class Impuesto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String idimpuesto;
	private String dsnombre;
	private Double tasa;
	private Double porcentaje;
	private String dssiglas;
	
	public String getIdimpuesto() {
		return idimpuesto;
	}
	public void setIdimpuesto(String idimpuesto) {
		this.idimpuesto = idimpuesto;
	}
	public String getDsnombre() {
		return dsnombre;
	}
	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}
	public Double getTasa() {
		return tasa;
	}
	public void setTasa(Double tasa) {
		this.tasa = tasa;
	}
	public Double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}
	public String getDssiglas() {
		return dssiglas;
	}
	public void setDssiglas(String dssiglas) {
		this.dssiglas = dssiglas;
	}
	
	
}
