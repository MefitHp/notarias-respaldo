package com.palestra.notaria.dato;

import java.io.Serializable;

/**
 * Clase que contiene los valores calculado por defecto de tarjeta amarilla
 * @author sofia
 *
 */
public class DatoCalculosTarjeta implements Serializable{

	private static final long serialVersionUID = -7692030953661083065L;
	
	private Double honorarios;
	private Double ide;
	
	/** Valores a calcular**/
	private Double porcientoIVA;
	private Double subHonorarioIVA;
	private Double total; 
	
	
	public DatoCalculosTarjeta(){
		
	}
	
	public Double getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(Double honorarios) {
		this.honorarios = honorarios;
	}

	public Double getIde() {
		return ide;
	}

	public void setIde(Double ide) {
		this.ide = ide;
	}

	public Double getPorcientoIVA() {
		return porcientoIVA;
	}

	public void setPorcientoIVA(Double porcientoIVA) {
		this.porcientoIVA = porcientoIVA;
	}

	public Double getSubHonorarioIVA() {
		return subHonorarioIVA;
	}

	public void setSubHonorarioIVA(Double subHonorarioIVA) {
		this.subHonorarioIVA = subHonorarioIVA;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
