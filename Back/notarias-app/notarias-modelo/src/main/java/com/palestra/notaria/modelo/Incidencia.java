package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbbsnm79")
public class Incidencia implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3407486903417945778L;

	@Id
	private String idincidencia;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuario",columnDefinition="idabogado")
	private Usuario abogado;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idescritura",columnDefinition="idescritura")
	private Escritura escritura;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	private String descripcion;
	
	@Column(columnDefinition="INT")
	private Boolean status;
	
	private Timestamp tmstmp;
	

	
	public Incidencia() {
		// TODO Auto-generated constructor stub
	}



	public String getIdincidencia() {
		return idincidencia;
	}



	public void setIdincidencia(String idincidencia) {
		this.idincidencia = idincidencia;
	}



	public Usuario getAbogado() {
		return abogado;
	}



	public void setAbogado(Usuario abogado) {
		this.abogado = abogado;
	}



	public Escritura getEscritura() {
		return escritura;
	}



	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public Boolean getStatus() {
		return status;
	}



	public void setStatus(Boolean status) {
		this.status = status;
	}



	public Timestamp getTmstmp() {
		return tmstmp;
	}



	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

}
