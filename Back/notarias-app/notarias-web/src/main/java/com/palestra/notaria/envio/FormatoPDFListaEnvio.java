package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.modelo.FormatoPDF;

public class FormatoPDFListaEnvio extends GenericEnvio {

	
	private List<FormatoPDF> formatos;
	private List<FormatoPDF> posteriores = new ArrayList<FormatoPDF>();
	private List<FormatoPDF> previos=new ArrayList<FormatoPDF>();;
	public FormatoPDFListaEnvio() {}

	public List<FormatoPDF> getPrevios() {
		return previos;
	}
	public void setPrevios(List<FormatoPDF> previos) {
		this.previos = previos;
	}
	public List<FormatoPDF> getPosteriores() {
		return posteriores;
	}
	public void setPosteriores(List<FormatoPDF> posteriores) {
		this.posteriores = posteriores;
	}
	public FormatoPDFListaEnvio(List<FormatoPDF> formatos){
		this.setFormatos(formatos);		
	}

	public List<FormatoPDF> getFormatos() {
		return formatos;
	}

	public void setFormatos(List<FormatoPDF> formatos) {
		this.formatos = formatos;
	}
	
	
}
