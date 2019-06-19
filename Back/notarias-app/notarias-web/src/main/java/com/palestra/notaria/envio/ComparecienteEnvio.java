package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.DatoCompareciente;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notaria.modelo.Domicilio;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.TipoCompareciente;

public class ComparecienteEnvio extends GenericEnvio {

	ArrayList<Compareciente> comparecienteList = null;

	ArrayList<TipoCompareciente> tipoComparecienteList = null;

	Compareciente compareciente = null;
	
	Compareciente representante = null;
	
	Compareciente autorizante = null;
	
	Compareciente hijo = null;
	
	Acto acto = null;

	String tipoCompareciente = "";
	
	ArrayList<Compareciente> representadosList = null;
	
	List<Compareciente> representantesList = null;

	ArrayList<DatoCompareciente> comparecienteCompletos = null;
	
	Escritura escritura = null;

	public Escritura getEscritura() {
		return escritura;
	}
	public void setEscritura(Escritura escrituranopaso) {
		this.escritura = escrituranopaso;
	}

	DatoCompareciente datoCompareciente = null;
	
	ComparecienteConyuge compConyuge = null;
	
	List<Domicilio> domiciliosDeActo = new ArrayList<Domicilio>();
	
	public Compareciente getHijo() {
		return hijo;
	}
	public void setHijo(Compareciente hijo) {
		this.hijo = hijo;
	}
	
	
	
	public List<Domicilio> getDomiciliosDeActo() {
		return domiciliosDeActo;
	}
	public void setDomiciliosDeActo(List<Domicilio> domiciliosDeActo) {
		this.domiciliosDeActo = domiciliosDeActo;
	}
	
	public String getTipoCompareciente() {
		return tipoCompareciente;
	}
	public void setTipoCompareciente(String tipoCompareciente) {
		this.tipoCompareciente = tipoCompareciente;
	}
	public Compareciente getAutorizante() {
		return autorizante;
	}
	public void setAutorizante(Compareciente autorizante) {
		this.autorizante = autorizante;
	}
	
	public List<Compareciente> getRepresentantesList() {
		return representantesList;
	}
	public void setRepresentantesList(List<Compareciente> representantesList) {
		this.representantesList = representantesList;
	}
	public Compareciente getRepresentante() {
		return representante;
	}
	public void setRepresentante(Compareciente representante) {
		this.representante = representante;
	}
	public ComparecienteConyuge getCompConyuge() {
		return compConyuge;
	}
	
	public void setCompConyuge(ComparecienteConyuge compConyuge) {
		this.compConyuge = compConyuge;
	}

	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}

	public Compareciente getCompareciente() {
		return compareciente;
	}

	public Acto getActo() {
		return acto;
	}

	public void setActo(Acto acto) {
		this.acto = acto;
	}

	public ArrayList<Compareciente> getComparecienteList() {
		return comparecienteList;
	}

	public void setComparecienteList(ArrayList<Compareciente> comparecienteList) {
		this.comparecienteList = comparecienteList;
	}

	public ArrayList<Compareciente> getRepresentadosList() {
		return representadosList;
	}

	public void setRepresentadosList(ArrayList<Compareciente> representadosList) {
		this.representadosList = representadosList;
	}

	public ArrayList<TipoCompareciente> getTipoComparecienteList() {
		return tipoComparecienteList;
	}

	public void setTipoComparecienteList(
			ArrayList<TipoCompareciente> tipoComparecienteList) {
		this.tipoComparecienteList = tipoComparecienteList;
	}
	
	public ArrayList<DatoCompareciente> getComparecienteCompletos() {
		return comparecienteCompletos;
	}
	
	public void setComparecienteCompletos(
			ArrayList<DatoCompareciente> comparecienteCompletos) {
		this.comparecienteCompletos = comparecienteCompletos;
	}
	
	public DatoCompareciente getDatoCompareciente() {
		return datoCompareciente;
	}
	
	public void setDatoCompareciente(DatoCompareciente datoCompareciente) {
		this.datoCompareciente = datoCompareciente;
	}
}
