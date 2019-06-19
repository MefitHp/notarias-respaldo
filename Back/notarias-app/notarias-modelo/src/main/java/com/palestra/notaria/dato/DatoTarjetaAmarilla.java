package com.palestra.notaria.dato;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DatoTarjetaAmarilla implements java.io.Serializable{

	private static final long serialVersionUID = -889125122765833851L;
	private String idtrjamarilla;
	private String nombreCliente;
	private String actoNombre;
	private String idacto;
	private String usuarioElaboro;
	private Double total;
	private Date fecha;
	
	public String getIdtrjamarilla() {
		return idtrjamarilla;
	}
	public void setIdtrjamarilla(String idtrjamarilla) {
		this.idtrjamarilla = idtrjamarilla;
	}
	public Double getTotal() {
		return total;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getActoNombre() {
		return actoNombre;
	}
	public void setActoNombre(String actoNombre) {
		this.actoNombre = actoNombre;
	}
	public String getIdacto() {
		return idacto;
	}
	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}
	public String getUsuarioElaboro() {
		return usuarioElaboro;
	}
	public void setUsuarioElaboro(String usuarioElaboro) {
		this.usuarioElaboro = usuarioElaboro;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append("idtrjamarilla", this.getIdtrjamarilla())
		        .append("nombreCliente", this.getNombreCliente())
		        .append("actoNombre", this.getActoNombre())
		        .append("idacto", this.getIdacto())
		        .append("usuarioElaboro", this.getUsuarioElaboro())
		        .append("total", this.getTotal())
		        .append("fecha",this.getFecha())
                .toString();
	}
}
