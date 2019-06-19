package com.palestra.notaria.dato;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Dato que contiene la informacion del acto que sera asignado a la tarjeta
 * amarilla.
 * 
 * @author sofia
 * 
 */
public class DatoActoDeTarjeta implements Serializable {

	private static final long serialVersionUID = -5954799980425046339L;

	private String idExpediente;

	private String idEscritura;

	private String numEscritura;

	private String operacionNombre;
	
	private String operacionDescripcion;

	public DatoActoDeTarjeta() {
	}

	public String getIdExpediente() {
		return idExpediente;
	}
	
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}
	
	
	public String getIdEscritura() {
		return idEscritura;
	}

	public void setIdEscritura(String idEscritura) {
		this.idEscritura = idEscritura;
	}

	public String getNumEscritura() {
		return numEscritura;
	}

	public void setNumEscritura(String numEscritura) {
		this.numEscritura = numEscritura;
	}

	public String getOperacionNombre() {
		return operacionNombre;
	}

	public void setOperacionNombre(String operacionNombre) {
		this.operacionNombre = operacionNombre;
	}

	public String getOperacionDescripcion() {
		return operacionDescripcion;
	}

	public void setOperacionDescripcion(String operacionDescripcion) {
		this.operacionDescripcion = operacionDescripcion;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append("idExpediente", this.getIdExpediente())
		        .append("idEscritura", this.getIdEscritura())
		        .append("numEscritura", this.getNumEscritura())
		        .append("operacionNombre", this.getOperacionNombre())
		        .append("operacionDescripcion", this.getOperacionDescripcion())
                .toString();
	}
	
}
