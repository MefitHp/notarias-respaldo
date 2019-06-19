package com.palestra.notaria.envio;

import java.util.List;

import com.palestra.notaria.modelo.FormatoPDFDetalle;

public class FormatoPDFDetalleEnvio extends GenericEnvio {

	private FormatoPDFDetalle detalle;
	private List<FormatoPDFDetalle> detalles;
	
	public FormatoPDFDetalleEnvio() {
	}

	public FormatoPDFDetalleEnvio(FormatoPDFDetalle detalle) {
		this.detalle = detalle;
	}
	
	public FormatoPDFDetalle getDetalle(){
		return this.detalle;		
	}
	
	public void setDetalle(FormatoPDFDetalle detalle){
		this.detalle = detalle;
	}

	public List<FormatoPDFDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<FormatoPDFDetalle> detalles) {
		this.detalles = detalles;
	}
	
}
