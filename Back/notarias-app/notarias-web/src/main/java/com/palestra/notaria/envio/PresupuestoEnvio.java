package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.dato.DatoPresupuesto;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Presupuesto;

public class PresupuestoEnvio extends GenericEnvio implements java.io.Serializable{

	private static final long serialVersionUID = -6137344732376317947L;
	private Presupuesto presupuesto;
	private ArrayList<Presupuesto> listaPorActo;
	private String idExpediente;
	private String idActo;
	private Double sumaImporte = null;
	private Double sumaIva = null;
	private Double sumaSubtotal = null;
	private Double sumaTotal = null;
	private ArrayList<DatoPresupuesto> listaPresupuestosExp;
	private Expediente expediente;
	private Acto acto;
	
	public Presupuesto getPresupuesto() {
		return presupuesto;
	}
	public void setPresupuesto(Presupuesto presupuesto) {
		this.presupuesto = presupuesto;
	}
	
	public ArrayList<Presupuesto> getListaPorActo() {
		return listaPorActo;
	}
	public void setListaPorActo(ArrayList<Presupuesto> listaPorActo) {
		this.listaPorActo = listaPorActo;
	}
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}
	public String getIdActo() {
		return idActo;
	}
	public void setIdActo(String idActo) {
		this.idActo = idActo;
	}
	public Double getSumaImporte() {
		return sumaImporte;
	}
	public void setSumaImporte(Double sumaImporte) {
		this.sumaImporte = sumaImporte;
	}
	public Double getSumaIva() {
		return sumaIva;
	}
	public void setSumaIva(Double sumaIva) {
		this.sumaIva = sumaIva;
	}
	public Double getSumaSubtotal() {
		return sumaSubtotal;
	}
	public void setSumaSubtotal(Double sumaSubtotal) {
		this.sumaSubtotal = sumaSubtotal;
	}
	public Double getSumaTotal() {
		return sumaTotal;
	}
	public void setSumaTotal(Double sumaTotal) {
		this.sumaTotal = sumaTotal;
	}
	public ArrayList<DatoPresupuesto> getListaPresupuestosExp() {
		return listaPresupuestosExp;
	}
	public void setListaPresupuestosExp(
			ArrayList<DatoPresupuesto> listaPresupuestosExp) {
		this.listaPresupuestosExp = listaPresupuestosExp;
	}
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public Acto getActo() {
		return acto;
	}
	public void setActo(Acto acto) {
		this.acto = acto;
	}
	
	
	
}
