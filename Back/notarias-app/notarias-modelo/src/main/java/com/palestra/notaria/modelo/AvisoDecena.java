package com.palestra.notaria.modelo;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Table(name="tbbsnm26a")
@Entity
public class AvisoDecena implements Serializable {

	private static final long serialVersionUID = 8641554452076155413L;

	public AvisoDecena() {
		// TODO Auto-generated constructor stub
	}																																																	
	
	@Id
	String idavisodecena;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idlibro")
	private Libro libroapertura;
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idnotario")
	private Usuario notario;
	
	
	private String urlDocApertura;
	private String urlDocCierre;
	private String urlDocAviso;
	
    private Timestamp fechaApertura;
    
    private Timestamp fechaCierre;
	
	
    private String status;
    
	private Timestamp tmstmp;

	public String getIdavisodecena() {
		return idavisodecena;
	}


	public void setIdavisodecena(String idavisodecena) {
		this.idavisodecena = idavisodecena;
	}


	public Libro getLibroapertura() {
		return libroapertura;
	}


	public void setLibroapertura(Libro libroapertura) {
		this.libroapertura = libroapertura;
	}



	public Timestamp getTmstmp() {
		return tmstmp;
	}


	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}


	public Usuario getNotario() {
		return notario;
	}


	public void setNotario(Usuario notario) {
		this.notario = notario;
	}


	public String getUrlDocApertura() {
		return urlDocApertura;
	}


	public void setUrlDocApertura(String urlDocApertura) {
		this.urlDocApertura = urlDocApertura;
	}


	public String getUrlDocCierre() {
		return urlDocCierre;
	}


	public void setUrlDocCierre(String urlDocCierre) {
		this.urlDocCierre = urlDocCierre;
	}


	public String getUrlDocAviso() {
		return urlDocAviso;
	}


	public void setUrlDocAviso(String urlDocAviso) {
		this.urlDocAviso = urlDocAviso;
	}


	public Timestamp getFechaApertura() {
		return fechaApertura;
	}


	public void setFechaApertura(Timestamp fechaApertura) {
		this.fechaApertura = fechaApertura;
	}


	public Timestamp getFechaCierre() {
		return fechaCierre;
	}


	public void setFechaCierre(Timestamp fechaCierre) {
		this.fechaCierre = fechaCierre;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
	

}
