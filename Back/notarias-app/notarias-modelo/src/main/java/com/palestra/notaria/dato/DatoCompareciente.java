package com.palestra.notaria.dato;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notaria.modelo.Firma;

public class DatoCompareciente implements java.io.Serializable{

	private static final long serialVersionUID = -3495089295247992115L;
	private Compareciente compareciente;
	private ArrayList<Compareciente> representantes;
	private List<Compareciente> hijos;
	private ComparecienteConyuge conyuge;
	private Compareciente representante;
	private List<Compareciente> autorizantes = new ArrayList<Compareciente>();
	
	public List<Compareciente> getAutorizantes() {
		return autorizantes;
	}
	public void setAutorizantes(List<Compareciente> autorizantes) {
		this.autorizantes = autorizantes;
	}
	
	public Compareciente getRepresentante() {
		return representante;
	}
	public void setRepresentante(Compareciente representante) {
		this.representante = representante;
	}
	
	public ComparecienteConyuge getConyuge() {
		return conyuge;
	}
	public void setConyuge(ComparecienteConyuge conyuge) {
		this.conyuge = conyuge;
	}
	public Compareciente getCompareciente() {
		return compareciente;
	}
	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}
	public ArrayList<Compareciente> getRepresentantes() {
		return representantes;
	}
	public void setRepresentantes(ArrayList<Compareciente> representantes) {
		this.representantes = representantes;
	}
	public List<Compareciente> getHijos() {
		return hijos;
	}
	public void setHijos(List<Compareciente> hijos) {
		this.hijos = hijos;
	}
	
}
