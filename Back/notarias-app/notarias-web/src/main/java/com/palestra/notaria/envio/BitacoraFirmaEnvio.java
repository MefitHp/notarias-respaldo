package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.BitacoraFirma;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Escritura;

public class BitacoraFirmaEnvio extends GenericEnvio{
	
	Compareciente compareciente = null;
	
	BitacoraFirma bitacoraFirma = null;
	
	Escritura escritura = null;
	
	ArrayList<BitacoraFirma> listaBitacora = null;
	
	ArrayList<Compareciente> listaComparecientes = null;
	
	ArrayList<Compareciente> comparecientesSelected = null;
	
	Boolean isfirmaditto;
	
	public Compareciente getCompareciente() {
		return compareciente;
	}
	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}
	public ArrayList<BitacoraFirma> getListaBitacora() {
		return listaBitacora;
	}
	public void setListaBitacora(ArrayList<BitacoraFirma> listaBitacora) {
		this.listaBitacora = listaBitacora;
	}
	public BitacoraFirma getBitacoraFirma() {
		return bitacoraFirma;
	}
	public void setBitacoraFirma(BitacoraFirma bitacoraFirma) {
		this.bitacoraFirma = bitacoraFirma;
	}
	public Escritura getEscritura() {
		return escritura;
	}
	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}
	public ArrayList<Compareciente> getListaComparecientes() {
		return listaComparecientes;
	}
	public void setListaComparecientes(
			ArrayList<Compareciente> listaComparecientes) {
		this.listaComparecientes = listaComparecientes;
	}
	public ArrayList<Compareciente> getComparecientesSelected() {
		return comparecientesSelected;
	}
	public void setComparecientesSelected(
			ArrayList<Compareciente> comparecientesSelected) {
		this.comparecientesSelected = comparecientesSelected;
	}
	public Boolean getIsfirmaditto() {
		return isfirmaditto;
	}
	public void setIsfirmaditto(Boolean isfirmaditto) {
		this.isfirmaditto = isfirmaditto;
	}
}
