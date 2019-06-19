package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.dato.DatoActoDeTarjeta;
import com.palestra.notaria.dato.DatoBusquedaCompareciente;
import com.palestra.notaria.dato.DatoCalculosTarjeta;
import com.palestra.notaria.dato.DatoTarjetaAmarilla;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.CamposTarjetaAmarilla;
import com.palestra.notaria.modelo.TarjetaAmarilla;

public class TarjetaAmarillaEnvio extends GenericEnvio implements Serializable{
	
	private static final long serialVersionUID = 6005068805314600211L;
	
	private Acto acto = null;
	private TarjetaAmarilla tarjetaAmarilla = null;
	private DatoActoDeTarjeta datoActoDeTarjeta = null;
	private ArrayList<DatoBusquedaCompareciente> listaComparecientes = null;
	private ArrayList<DatoTarjetaAmarilla> listaTarjetas = null;
	private DatoCalculosTarjeta calculados = null;
	private ArrayList<CamposTarjetaAmarilla> camposTarjetaAmarillaList = null;

	public TarjetaAmarillaEnvio(){
		
	}
	public ArrayList<CamposTarjetaAmarilla> getCamposTarjetaAmarillaList() {
		return camposTarjetaAmarillaList;
	}
	
	public void setCamposTarjetaAmarillaList(
			ArrayList<CamposTarjetaAmarilla> camposTarjetaAmarillaList) {
		this.camposTarjetaAmarillaList = camposTarjetaAmarillaList;
	}
	
	public Acto getActo() {
		return acto;
	}
	public void setActo(Acto acto) {
		this.acto = acto;
	}
	public TarjetaAmarilla getTarjetaAmarilla() {
		return tarjetaAmarilla;
	}
	public void setTarjetaAmarilla(TarjetaAmarilla tarjetaAmarilla) {
		this.tarjetaAmarilla = tarjetaAmarilla;
	}
	public ArrayList<DatoTarjetaAmarilla> getListaTarjetas() {
		return listaTarjetas;
	}
	public void setListaTarjetas(ArrayList<DatoTarjetaAmarilla> listaTarjetas) {
		this.listaTarjetas = listaTarjetas;
	}
	public DatoActoDeTarjeta getDatoActoDeTarjeta() {
		return datoActoDeTarjeta;
	}
	public void setDatoActoDeTarjeta(DatoActoDeTarjeta datoActoDeTarjeta) {
		this.datoActoDeTarjeta = datoActoDeTarjeta;
	}
	public ArrayList<DatoBusquedaCompareciente> getListaComparecientes() {
		return listaComparecientes;
	}
	public void setListaComparecientes(
			ArrayList<DatoBusquedaCompareciente> listaComparecientes) {
		this.listaComparecientes = listaComparecientes;
	}
	public DatoCalculosTarjeta getCalculados() {
		return calculados;
	}
	public void setCalculados(DatoCalculosTarjeta calculados) {
		this.calculados = calculados;
	}
}
