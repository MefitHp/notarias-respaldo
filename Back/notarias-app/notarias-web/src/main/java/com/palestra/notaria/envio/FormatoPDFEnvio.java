package com.palestra.notaria.envio;

import java.util.List;

import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.modelo.FormatoPDFDetalle;

public class FormatoPDFEnvio extends GenericEnvio {

	private FormatoPDF formatoPdf;
	private List<FormatoPDFDetalle> detalles;
	
	
	public FormatoPDFEnvio() {}


	public FormatoPDF getFormatoPdf() {
		return formatoPdf;
	}


	public void setFormatoPdf(FormatoPDF formatoPdf) {
		this.formatoPdf = formatoPdf;
	}


	public List<FormatoPDFDetalle> getDetalles() {
		return detalles;
	}


	public void setDetalles(List<FormatoPDFDetalle> detalles) {
		this.detalles = detalles;
	}
}
