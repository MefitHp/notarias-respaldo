package com.connect.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="vista_exp")
public class VistaExpediente implements Serializable{

	private static final long serialVersionUID = -6851958396336746023L;

	@Id
	@Column(name="exp")
	private String expediente;
	
	@Column(name="esc")
	private Double escritura;
		
	@Column(name="ope")
	private String ope;
	
	@Column(name="FechaFirma",columnDefinition="char")
	private String fechaFirma;
	
	@Column(name="iniciales",columnDefinition="char")
	private String iniciales;
	
	@Column(name="ruta")
	private String ruta;

	@Transient
	private Integer pag;
	
	@Transient
	private String rutaExt;
	
	@OneToMany(mappedBy="vistaExpediente")
	private List<VistaComparecientes> comparecientes = new ArrayList<VistaComparecientes>();
		
	public List<VistaComparecientes> getComparecientes() {
		return comparecientes;
	}
	public void setComparecientes(List<VistaComparecientes> comparecientes) {
		this.comparecientes = comparecientes;
	}
	public String getRutaExt() {
		return rutaExt;
	}
	public void setRutaExt(String rutaExt) {
		this.rutaExt = rutaExt;
	}
	public Integer getPag() {
		return pag;
	}
	public void setPag(Integer pag) {
		this.pag = pag;
	}
	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public Double getEscritura() {
		return escritura;
	}
	public void setEscritura(Double escritura) {
		this.escritura = escritura;
	}

	public String getOpe() {
		return ope;
	}

	public void setOpe(String ope) {
		this.ope = ope;
	}

	public String getFechaFirma() {
		return fechaFirma;
	}

	public void setFechaFirma(String fechaFirma) {
		this.fechaFirma = fechaFirma;
	}

	public String getIniciales() {
		return iniciales;
	}

	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
}
