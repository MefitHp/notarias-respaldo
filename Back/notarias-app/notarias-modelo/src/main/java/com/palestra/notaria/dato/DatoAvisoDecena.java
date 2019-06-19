package com.palestra.notaria.dato;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class DatoAvisoDecena implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4740706644000680927L;

	public DatoAvisoDecena(){
		// TODO Auto-generated constructor stub
	}
	
	String idavisodecena;
	Long libroinicial;
	Timestamp fechaapertura;
	Timestamp fechacierre;
	Boolean has_evidencia_cierre = false;
	Boolean has_evidencia_apertura= false;
	Boolean  has_evidencia_notificacion= false;
	
	

	public Long getLibroinicial() {
		return libroinicial;
	}
	public void setLibroinicial(Long libroinicial) {
		this.libroinicial = libroinicial;
	}
	public Timestamp getFechaapertura() {
		return fechaapertura;
	}
	public void setFechaapertura(Timestamp fechaapertura) {
		this.fechaapertura = fechaapertura;
	}
	public Timestamp getFechacierre() {
		return fechacierre;
	}
	public void setFechacierre(Timestamp fechacierre) {
		this.fechacierre = fechacierre;
	}
	public String getIdavisodecena() {
		return idavisodecena;
	}
	public void setIdavisodecena(String idavisodecena) {
		this.idavisodecena = idavisodecena;
	}
	public Boolean getHas_evidencia_cierre() {
		return has_evidencia_cierre;
	}
	public void setHas_evidencia_cierre(Boolean has_evidencia_cierre) {
		this.has_evidencia_cierre = has_evidencia_cierre;
	}
	public Boolean getHas_evidencia_apertura() {
		return has_evidencia_apertura;
	}
	public void setHas_evidencia_apertura(Boolean has_evidencia_apertura) {
		this.has_evidencia_apertura = has_evidencia_apertura;
	}
	public Boolean getHas_evidencia_notificacion() {
		return has_evidencia_notificacion;
	}
	public void setHas_evidencia_notificacion(Boolean has_evidencia_notificacion) {
		this.has_evidencia_notificacion = has_evidencia_notificacion;
	}
	
	

}
