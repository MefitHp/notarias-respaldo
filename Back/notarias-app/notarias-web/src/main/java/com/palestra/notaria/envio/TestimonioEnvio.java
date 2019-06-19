package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.RelEtapaTestimonio;
import com.palestra.notaria.modelo.Testimonio;

public class TestimonioEnvio extends GenericEnvio{

	private String idExpediente;
	private String idtestimonio;
	private String idreletapatesti;
	private Testimonio testimonio;
	private ArrayList<Testimonio> lista;
	private String rutaTemTestimonio= null;
	private String rutaArchivoDescarga = null;
	private ArrayList<RelEtapaTestimonio> listaEtapas = null;
	
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}
	
	public Testimonio getTestimonio() {
		return testimonio;
	}
	public void setTestimonio(Testimonio testimonio) {
		this.testimonio = testimonio;
	}
	public ArrayList<Testimonio> getLista() {
		return lista;
	}
	public void setLista(ArrayList<Testimonio> lista) {
		this.lista = lista;
	}
	public String getRutaTemTestimonio() {
		return rutaTemTestimonio;
	}
	public void setRutaTemTestimonio(String rutaTemTestimonio) {
		this.rutaTemTestimonio = rutaTemTestimonio;
	}
	public String getRutaArchivoDescarga() {
		return rutaArchivoDescarga;
	}
	public void setRutaArchivoDescarga(String rutaArchivoDescarga) {
		this.rutaArchivoDescarga = rutaArchivoDescarga;
	}
	public String getIdtestimonio() {
		return idtestimonio;
	}
	public void setIdtestimonio(String idtestimonio) {
		this.idtestimonio = idtestimonio;
	}
	public ArrayList<RelEtapaTestimonio> getListaEtapas() {
		return listaEtapas;
	}
	public void setListaEtapas(ArrayList<RelEtapaTestimonio> listaEtapas) {
		this.listaEtapas = listaEtapas;
	}
	public String getIdreletapatesti() {
		return idreletapatesti;
	}
	public void setIdreletapatesti(String idreletapatesti) {
		this.idreletapatesti = idreletapatesti;
	}
	

}
