package com.palestra.notaria.dato;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DatoDocumentoTarjeton {

	private String dsnombre;
	private String status;
	private Date fechaactualizacion;
	private String entregado;
	private String baja;
	private String tieneevidencia;
	
	public DatoDocumentoTarjeton() {
		// TODO Auto-generated constructor stub
	}

	

	public String getDsnombre() {
		return dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFechaactualizacion() {
		return fechaactualizacion;
	}

	public void setFechaactualizacion(Date fechaactualizacion) {
		this.fechaactualizacion = fechaactualizacion;
	}

	public String getEntregado() {
		return entregado;
	}

	public void setEntregado(String entregado) {
		this.entregado = entregado;
	}

	public String getBaja() {
		return baja;
	}

	public void setBaja(String baja) {
		this.baja = baja;
	}

	public String getTieneevidencia() {
		return tieneevidencia;
	}

	public void setTieneevidencia(String tieneevidencia) {
		this.tieneevidencia = tieneevidencia;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append("dsnombre", this.getDsnombre())
                .toString();
	}
	
}
