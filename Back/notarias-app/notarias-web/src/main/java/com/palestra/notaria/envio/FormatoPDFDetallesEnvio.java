package com.palestra.notaria.envio;

import java.util.List;

import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.modelo.FormatoPDFDetalle;

public class FormatoPDFDetallesEnvio extends GenericEnvio {

	private FormatoPDF formato;
	private List<FormatoPDFDetalle> detalles;
	
	public FormatoPDFDetallesEnvio() {}

	public FormatoPDF getFormato(){
		return this.formato;
	}
	
	public void setFormato(FormatoPDF formato){
		this.formato = formato;
	}
	
	public List<FormatoPDFDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<FormatoPDFDetalle> detalles) {
		this.detalles = detalles;
	}

}
