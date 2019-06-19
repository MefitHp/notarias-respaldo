package com.palestra.notaria.dato;

import java.util.ArrayList;

import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Operacion;

public class DatoOperacion implements java.io.Serializable{

	private static final long serialVersionUID = -9149022224208886826L;

	private String idoperacion;

	private String dsnombre;
	
	private String status = "X";
	
	private Operacion operacion = null;

	private ElementoCatalogo locacion = null;
	
	private ArrayList<DatoActo> listaActos = null;

	public ElementoCatalogo getLocacion() {
		return locacion;
	}
	public void setLocacion(ElementoCatalogo locacion) {
		this.locacion = locacion;
	}
	
	public String getIdoperacion() {
		return idoperacion;
	}

	public void setIdoperacion(String idoperacion) {
		this.idoperacion = idoperacion;
	}

	public String getDsnombre() {
		return dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public ArrayList<DatoActo> getListaActos() {
		return listaActos;
	}

	public void setListaActos(ArrayList<DatoActo> listaActos) {
		this.listaActos = listaActos;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
		if(operacion!=null)
		{
			this.idoperacion = this.operacion.getIdoperacion();
			this.dsnombre = this.operacion.getDsnombre();
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
