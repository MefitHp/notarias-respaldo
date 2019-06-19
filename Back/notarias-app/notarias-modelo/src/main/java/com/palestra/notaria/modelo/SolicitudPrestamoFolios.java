package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tbbsnm68")
public class SolicitudPrestamoFolios implements Serializable {

	private static final long serialVersionUID = -9024188849334403184L;

	@Id
	private String idsolicitudprestamo;
	
	@ManyToOne
	@JoinColumn(name="idusuariopresta")
	private Usuario usuarioPrestador;
	
	@ManyToOne
	@JoinColumn(name="idusuariorecibe")
	private Usuario usuarioRecibe;
	
	private Long infolioinicio;
	
	private Long infoliofin;
	
	@Transient
	private char status;
	
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	@Column(name="fechaentrega")
	private Date fechaEntrega;
	
	@Column(name="fechadevolucion")
	private Date fechaDevolucion;
	
	@Column(columnDefinition="INT",name="estanprestados")
	private Boolean estanPrestados;
	
	@Column(columnDefinition="INT",name="isprestamoterminado")
	private Boolean isPrestamoTerminado;
	
	private String idsesion;
	
	private Timestamp tmstmp;
	
	@Column(name="dsnumescritura")
	private String numeroEscritura;
	
	public Boolean getIsPrestamoTerminado() {
		return isPrestamoTerminado;
	}
	public void setIsPrestamoTerminado(Boolean isPrestamoTerminado) {
		this.isPrestamoTerminado = isPrestamoTerminado;
	}
	

	public String getIdsolicitudprestamo() {
		return idsolicitudprestamo;
	}

	public void setIdsolicitudprestamo(String idsolicitudprestamo) {
		this.idsolicitudprestamo = idsolicitudprestamo;
	}

	public Usuario getUsuarioPrestador() {
		return usuarioPrestador;
	}

	public void setUsuarioPrestador(Usuario usuarioPrestador) {
		this.usuarioPrestador = usuarioPrestador;
	}

	public Usuario getUsuarioRecibe() {
		return usuarioRecibe;
	}

	public void setUsuarioRecibe(Usuario usuarioRecibe) {
		this.usuarioRecibe = usuarioRecibe;
	}

	public Long getInfolioinicio() {
		return infolioinicio;
	}
	public void setInfolioinicio(Long infolioinicio) {
		this.infolioinicio = infolioinicio;
	}
	public Long getInfoliofin() {
		return infoliofin;
	}
	public void setInfoliofin(Long infoliofin) {
		this.infoliofin = infoliofin;
	}
	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public Boolean getEstanPrestados() {
		return estanPrestados;
	}

	public void setEstanPrestados(Boolean estanPrestados) {
		this.estanPrestados = estanPrestados;
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
	public String getNumeroEscritura() {
		return numeroEscritura;
	}
	public void setNumeroEscritura(String numeroEscritura) {
		this.numeroEscritura = numeroEscritura;
	}
	
	
}
