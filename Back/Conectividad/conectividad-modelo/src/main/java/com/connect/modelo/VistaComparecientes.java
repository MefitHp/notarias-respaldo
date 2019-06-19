package com.connect.modelo;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="vista_compas")
public class VistaComparecientes implements Serializable {

	private static final long serialVersionUID = -4672892317291663463L;

	@Id
	@Column(name="id_com")
	private Double idcompareciente;
	
	@ManyToOne
	@JoinColumn(name="exp")
	private VistaExpediente vistaExpediente;
	
	@Column(name="ope")
	private String operacion;
	
	private Double libro;
	
	@Column(name="esc")
	private Double escritura;
	
	@Column(columnDefinition="varbinary")
	private String folios;
	
	private Date fecha;
	
	private String estado;
	
	private String nombre;
	
	@Column(columnDefinition="char")
	private String rol;

	public Double getIdcompareciente() {
		return idcompareciente;
	}
	public void setIdcompareciente(Double idcompareciente) {
		this.idcompareciente = idcompareciente;
	}
	public Double getEscritura() {
		return escritura;
	}
	public void setEscritura(Double escritura) {
		this.escritura = escritura;
	}
	public VistaExpediente getVistaExpediente() {
		return vistaExpediente;
	}
	public void setVistaExpediente(VistaExpediente vistaExpediente) {
		this.vistaExpediente = vistaExpediente;
	}
	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
}
