package com.connect.modelo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="vista_esc")
public class VistaEscritura implements Serializable {

	private static final long serialVersionUID = -1464445897340575370L;

//	@Id
	@Column(name="esc")
	private Double escritura;
	
	@Id
	@Column(name="exp")
	private String expediente;
	
	@Column(name="ope")
	private String operacion;
	
	@Column(name="FechaFirma",columnDefinition="char")
	private String fechaFirma;
	
	@Column(columnDefinition="char")
	private String iniciales;
	
	private Double libro;
	
	@Column(columnDefinition="varbinary")
	private String folios;
	
	private Date pase;
	
	private String ruta;
	
	@Transient
	private String rutaExt;
	
	public String getRutaExt() {
		return rutaExt;
	}
	public void setRutaExt(String rutaExt) {
		this.rutaExt = rutaExt;
	}

	public Double getEscritura() {
		return escritura;
	}

	public void setEscritura(Double escritura) {
		this.escritura = escritura;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
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

	public Double getLibro() {
		return libro;
	}

	public void setLibro(Double libro) {
		this.libro = libro;
	}

	public String getFolios() {
		return folios;
	}

	public void setFolios(String folios) {
		this.folios = folios;
	}

	public Date getPase() {
		return pase;
	}

	public void setPase(Date pase) {
		this.pase = pase;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
}
